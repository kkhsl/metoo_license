//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.layer.service;

import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.layer.dto.LayerSplitSubnetRO;
import com.abtnetworks.totems.layer.dto.ListSubnetsDTO;
import com.abtnetworks.totems.layer.vo.MemoryPagination;
import com.abtnetworks.totems.layer.vo.PathAnalyzeModifyVO;
import com.abtnetworks.totems.layer.vo.RealSubnetModifyVO;
import com.abtnetworks.totems.layer.vo.SubnetListVO;
import com.abtnetworks.totems.layer.vo.TrafficFileVO;
import com.abtnetworks.totems.layer.vo.ZoneVO;
import com.abtnetworks.totems.whale.baseapi.dto.DetailPathSubnetDTO;
import com.abtnetworks.totems.whale.baseapi.dto.SubnetDTO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceSummaryRO;
import com.abtnetworks.totems.whale.baseapi.ro.InterfaceExtendDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.InterfacesRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetLinkedDeviceRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetListRO;
import com.abtnetworks.totems.whale.policy.dto.AccessQueryDTO;
import com.abtnetworks.totems.whale.policy.dto.PathAnalyzeDTO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryRO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryTrafficResultRO;
import com.abtnetworks.totems.whale.policy.ro.AnalysisCancel;
import com.abtnetworks.totems.whale.policy.ro.AnalysisStartRO;
import com.abtnetworks.totems.whale.system.ro.TaskStatusRO;
import com.abtnetworks.totems.whale.topo.dto.LinkVpnDTO;
import com.abtnetworks.totems.whale.topo.dto.SplitSubnetDTO;
import com.abtnetworks.totems.whale.topo.ro.LinkVpnRO;
import com.abtnetworks.totems.whale.topo.ro.SplitSubnetDataRO;
import com.abtnetworks.totems.whale.topo.ro.TopoUnDoRO;
import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.util.Map;

public interface WhaleAgencyService {
    MemoryPagination<InterfaceExtendDataRO> listSubnets(ListSubnetsDTO listSubnetsDTO) throws Exception;

    SubnetLinkedDeviceRO listSubnetLinkedDevice(String subnetUuid) throws Exception;

    DeviceSummaryRO getDevicesSummary(Boolean skipHost) throws Exception;

    JSONObject getDeviceObject(String deviceUuid, String type) throws Exception;

    ZoneVO getDeviceZoneObject(String deviceUuid, String zoneUuid) throws Exception;

    AccessQueryRO getAccessQueryObject(AccessQueryDTO accessQueryDTO) throws Exception;

    AccessQueryTrafficResultRO getAccessQueryTraffic(AccessQueryDTO accessQueryDTO);

    TrafficFileVO queryExport(AccessQueryDTO accessQueryDTO);

    PathAnalyzeModifyVO getDetailPathObject(PathAnalyzeDTO analyzeDTO, Boolean isFilterNoPath) throws Exception;

    Map<String, Map<String, Map<String, Object>>> listTopoNodeByLayerUuid(String layerUuid, Boolean isFilter, String userId) throws Exception;

    Map<String, Map<String, Map<String, Object>>> listTopoNodeByLayerUuidMetoo(String layerUuid, Boolean isFilter, String userId, String branchLevel) throws Exception;

    LayerSplitSubnetRO splitSubnet(SplitSubnetDTO splitSubnetDTO) throws Exception;

    LinkVpnRO linkVpn(LinkVpnDTO linkVpnDTO) throws Exception;

    LinkVpnRO getLinkVpn() throws Exception;

    LinkVpnRO deleteLinkVpn(String uuid) throws Exception;

    Map<String, Object> listVpnSubnets(String deviceUuid) throws Exception;

    SubnetListVO getSubnetObject(String subnetUuid, String ip4Addr) throws Exception;

    SubnetListRO updateSubnetObject(SubnetDTO subnetDTO) throws Exception;

    TopoUnDoRO splitSubnetUnDo(String opUuid) throws Exception;

    ResultRO<List<SplitSubnetDataRO>> getAllSplitSubnetSummary() throws Exception;

    AnalysisStartRO analysisAccessStart() throws Exception;

    AnalysisCancel analysisAccessCancel() throws Exception;

    TaskStatusRO getTaskStatus(String taskType, String taskRunUuid, Integer page, Integer psize) throws Exception;

    RealSubnetModifyVO getDetailPathSubnet(DetailPathSubnetDTO detailPathSubnetDTO) throws Exception;

    ResultRO<List<InterfacesRO>> findNetInterfaceByUuid(String uuid);
}
