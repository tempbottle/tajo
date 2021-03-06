/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tajo.service;

import org.apache.tajo.conf.TajoConf;
import org.apache.tajo.util.TUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class BaseServiceTracker implements ServiceTracker {
  private final TajoConf conf;
  private TajoMasterInfo tajoMasterInfo;
  private List<TajoMasterInfo> tajoMasterInfos;

  @SuppressWarnings("unused")
  public BaseServiceTracker(TajoConf conf) {
    this.conf = conf;

    tajoMasterInfo = new TajoMasterInfo();
    tajoMasterInfo.setActive(true);
    tajoMasterInfo.setAvailable(true);
    tajoMasterInfo.setTajoMasterAddress(conf.getSocketAddrVar(TajoConf.ConfVars.TAJO_MASTER_UMBILICAL_RPC_ADDRESS));
    tajoMasterInfo.setTajoClientAddress(conf.getSocketAddrVar(TajoConf.ConfVars.TAJO_MASTER_CLIENT_RPC_ADDRESS));
    tajoMasterInfo.setWorkerResourceTrackerAddr(conf.getSocketAddrVar(TajoConf.ConfVars.RESOURCE_TRACKER_RPC_ADDRESS));
    tajoMasterInfo.setCatalogAddress(conf.getSocketAddrVar(TajoConf.ConfVars.CATALOG_ADDRESS));
    tajoMasterInfo.setWebServerAddress(conf.getSocketAddrVar(TajoConf.ConfVars.TAJO_MASTER_INFO_ADDRESS));

    tajoMasterInfos = TUtil.newList(tajoMasterInfo);
  }

  @Override
  public boolean isHighAvailable() {
    return false;
  }

  @Override
  public InetSocketAddress getUmbilicalAddress() {
    return tajoMasterInfo.getTajoMasterAddress();
  }

  @Override
  public InetSocketAddress getClientServiceAddress() {
    return tajoMasterInfo.getTajoClientAddress();
  }

  @Override
  public InetSocketAddress getResourceTrackerAddress() {
    return tajoMasterInfo.getWorkerResourceTrackerAddr();
  }

  @Override
  public InetSocketAddress getCatalogAddress() {
    return tajoMasterInfo.getCatalogAddress();
  }

  @Override
  public InetSocketAddress getMasterHttpInfo() throws ServiceTrackerException {
    return tajoMasterInfo.getWebServerAddress();
  }

  @Override
  public void register() throws IOException {
  }

  @Override
  public void delete() throws IOException {
  }

  @Override
  public boolean isActiveStatus() {
    return true;
  }

  @Override
  public List<TajoMasterInfo> getMasters() throws IOException {
    return tajoMasterInfos;
  }

}
