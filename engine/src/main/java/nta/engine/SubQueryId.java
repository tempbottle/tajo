package nta.engine;

import java.text.NumberFormat;

import nta.common.ProtoObject;
import nta.engine.query.QueryIdProtos.SubQueryIdProto;
import nta.engine.query.QueryIdProtos.SubQueryIdProtoOrBuilder;

/**
 * @author Hyunsik Choi
 */
public class SubQueryId implements Comparable<SubQueryId>, 
ProtoObject<SubQueryIdProto> {
  private static final NumberFormat idFormat = NumberFormat.getInstance();
  
  static {
    idFormat.setGroupingUsed(false);
    idFormat.setMinimumIntegerDigits(3);
  }

  private QueryId queryId = null;
  private int id = -1;
  private String finalId = null;
  
  private SubQueryIdProto proto = SubQueryIdProto.getDefaultInstance();
  private SubQueryIdProto.Builder builder = null;
  private boolean viaProto = false;
  
  public SubQueryId() {
    builder = SubQueryIdProto.newBuilder();
  }
  
  public SubQueryId(final QueryId queryId, final int id) {
    this();
    this.queryId = queryId;
    this.id = id;
  }
  
  public SubQueryId(String finalId) {
    this();
//    String[] split = finalId.split(QueryId.SEPERATOR);
    int i = finalId.lastIndexOf(QueryId.SEPERATOR);
    
    this.queryId = new QueryId(finalId.substring(0, i));
    this.id = Integer.valueOf(finalId.substring(i+1));
  }
  
  public SubQueryId(SubQueryIdProto proto) {
    this.proto = proto;
    viaProto = true;
  }
  
  public final String toString() {
    if (finalId == null) {
      finalId = queryId.toString() + QueryId.SEPERATOR 
          + idFormat.format(id);
    }
    return finalId;
  }
  
  public QueryId getQueryId() {
    return this.queryId;
  }
  
  @Override
  public final boolean equals(final Object o) {
    if (o instanceof SubQueryId) {
      SubQueryId other = (SubQueryId) o;
      return this.queryId.equals(other.queryId) &&
          this.id == other.id;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  @Override
  public final int compareTo(final SubQueryId o) {
    return this.toString().compareTo(o.toString());
  }
  
  private void mergeProtoToLocal() {
    SubQueryIdProtoOrBuilder p = viaProto ? proto : builder;
    if (queryId == null) {
      queryId = new QueryId(p.getQueryId());
    }
    if (id == -1) {
      id = p.getId();
    }
  }

  @Override
  public void initFromProto() {
    mergeProtoToLocal();
    queryId.initFromProto();
  }
  
  private void mergeLocalToBuilder() {
    if (this.builder == null) {
      this.builder = SubQueryIdProto.newBuilder(proto);
    }
    if (this.queryId != null) {
      builder.setQueryId(queryId.getProto());
    }
    if (this.id != -1) {
      builder.setId(id);
    }
  }

  @Override
  public SubQueryIdProto getProto() {
    if (!viaProto) {
      mergeLocalToBuilder();
      proto = builder.build();
      viaProto = true;
    }
    return proto;
  }
}