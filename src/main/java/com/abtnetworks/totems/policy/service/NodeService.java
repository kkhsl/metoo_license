
package com.abtnetworks.totems.policy.service;

import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.policy.domain.Node;
import com.abtnetworks.totems.policy.domain.RevisionChange;
import com.abtnetworks.totems.policy.dto.NodeFormatDTO;
import com.abtnetworks.totems.policy.vo.PolicyTotalVo;
import com.alibaba.fastjson.JSONArray;
import java.util.List;
import java.util.Map;

public interface NodeService {
    Node getTheNodeByUuid(String deviceUuid);

    List<Node> getAllDeviceUuid(String userId);

    NodeFormatDTO getNodeFormatDTO(String userId);

    JSONArray getNodeUuidJSONArray(String userId);

    List<Node> findByCondition(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, Integer start, Integer size);

    List<Node> findByConditionMetoo(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, String branchLevel, Integer start, Integer size);

    Integer findTotalByCondition(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId);

    Integer findTotalByConditionMetoo(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, String branchLevel);

    Node getFileInfo(String deviceUuid);

    Node getRouteTableFileInfo(String deviceUuid);

    List<Node> findByCondition(Integer type, String userId, String deviceUuid);

    PolicyTotalVo getPolicyTotalVoByDeviceId(String deviceId, String ip);

    List<RevisionChange> configChangeList(String deviceId, String ip);

    ResultRO<List<String>> getDeviceIdByIp(String ip);

    Map<String, Node> getDeviceListIds(List<String> ids);
}
