package com.abtnetworks.totems.policy.service.impl;

import com.abtnetworks.totems.common.enums.DeviceChangeTypeEnum;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.policy.dao.mysql.NodeMapper;
import com.abtnetworks.totems.policy.dao.mysql.UmsUserMapper;
import com.abtnetworks.totems.policy.domain.Node;
import com.abtnetworks.totems.policy.domain.NodeHistory;
import com.abtnetworks.totems.policy.domain.RevisionChange;
import com.abtnetworks.totems.policy.dto.NodeFormatDTO;
import com.abtnetworks.totems.policy.general.CommonThreadPoolExecutor;
import com.abtnetworks.totems.policy.service.NodeService;
import com.abtnetworks.totems.policy.service.PolicyCheckReportService;
import com.abtnetworks.totems.policy.vo.PolicyTotalVo;
import com.abtnetworks.totems.whale.baseapi.dto.RevisionSearchDTO;
import com.abtnetworks.totems.whale.baseapi.ro.RevisionSearchRO;
import com.abtnetworks.totems.whale.baseapi.ro.VersionChangeRO;
import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceChangeClient;
import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceObjectClient;
import com.abtnetworks.totems.whale.policyoptimize.ro.SubDeviceVersionIdRO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl implements NodeService {
    private static final Logger log = LoggerFactory.getLogger(NodeServiceImpl.class);
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    UmsUserMapper umsUserMapper;
    @Autowired
    private WhaleDeviceObjectClient whaleDeviceObjectClient;
    @Autowired
    WhaleDeviceChangeClient whaleDeviceChangeClient;
    @Autowired
    PolicyCheckReportService policyCheckReportService;

    public NodeServiceImpl() {
    }

    public static void main(String[] args) {
        List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
// 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();

    }

    public void test() {
        RevisionSearchDTO searchDTO = new RevisionSearchDTO();
        searchDTO.setDeviceUuid("a");
        searchDTO.setPage(1);
        searchDTO.setPsize(10);
        RevisionSearchRO ro = new RevisionSearchRO();

        ResultRO<List<RevisionSearchRO>> revisResultRO = NodeServiceImpl.this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
        if (revisResultRO != null && revisResultRO.getSuccess()) {
            List<RevisionSearchRO> collect = (revisResultRO.getData()).stream().filter((revisionSearchROx) -> {
                return !DeviceChangeTypeEnum.IRT_NEW.getCode().equals(revisionSearchROx.getType()) && !DeviceChangeTypeEnum.IRT_UPDATE.getCode().equals(revisionSearchROx.getType());
            }).collect(Collectors.toList());
        }
    }

    public Node getTheNodeByUuid(String deviceUuid) {
        return this.nodeMapper.getTheNodeByUuid(deviceUuid);
    }

    public List<Node> getAllDeviceUuid(String userId) {
        String branchLevel = "00";
        if (!AliStringUtils.isEmpty(userId)) {
            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
        }

        return this.nodeMapper.getAllDeviceUuid(branchLevel);
    }

    public NodeFormatDTO getNodeFormatDTO(String userId) {
        NodeFormatDTO dto = new NodeFormatDTO();
        List<Node> list = this.getAllDeviceUuid(userId);
        if (list != null && !list.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            List<Node> nodeList = new ArrayList();
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
                Node node = (Node)var6.next();
                if (!StringUtils.isBlank(node.getUuid())) {
                    jsonArray.add(node.getUuid());
                    nodeList.add(node);
                }
            }

            dto.setNodeList(nodeList);
            dto.setNodeUuidList(jsonArray);
            return dto;
        } else {
            return dto;
        }
    }

    public JSONArray getNodeUuidJSONArray(String userId) {
        List<Node> list = this.getAllDeviceUuid(userId);
        JSONArray jsonArray = new JSONArray();
        if (list != null && !list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Node node = (Node)var4.next();
                if (!StringUtils.isBlank(node.getUuid())) {
                    jsonArray.add(node.getUuid());
                }
            }

            return jsonArray;
        } else {
            return jsonArray;
        }
    }

    public List<Node> findByCondition(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, Integer start, Integer size) {
        String branchLevel = "00";
        if (!AliStringUtils.isEmpty(userId)) {
            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
        }

        return this.nodeMapper.findByCondition(type, vendor, deviceUuid, filter, display, origin, branchLevel, start, size);
    }

    public List<Node> findByConditionMetoo(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, String branchLevel, Integer start, Integer size) {
        if(branchLevel == null || branchLevel.equals("")){
            if (!AliStringUtils.isEmpty(userId)) {
                branchLevel = this.umsUserMapper.getBranchLevelById(userId);
            }
        }

        return this.nodeMapper.findByCondition(type, vendor, deviceUuid, filter, display, origin, branchLevel, start, size);
    }

    public Integer findTotalByCondition(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId) {
        String branchLevel = "00";
        if (!AliStringUtils.isEmpty(userId)) {
            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
        }

        return this.nodeMapper.findTotalByCondition(type, vendor, deviceUuid, filter, display, origin, branchLevel);
    }

    public Integer findTotalByConditionMetoo(Integer type, String vendor, String deviceUuid, String filter, Integer display, Integer origin, String userId, String branchLevel) {
       if(branchLevel == null || branchLevel.equals("")){
           if (!AliStringUtils.isEmpty(userId)) {
               branchLevel = this.umsUserMapper.getBranchLevelById(userId);
           }
       }
        return this.nodeMapper.findTotalByCondition(type, vendor, deviceUuid, filter, display, origin, branchLevel);
    }

    public Node getFileInfo(String deviceUuid) {
        return this.nodeMapper.getFileInfo(deviceUuid);
    }

    public Node getRouteTableFileInfo(String deviceUuid) {
        return this.nodeMapper.getRouteTableFileInfo(deviceUuid);
    }

    public List<Node> findByCondition(Integer type, String userId, String deviceUuid) {
        String branchLevel = "00";
        if (!AliStringUtils.isEmpty(userId)) {
            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
        }

        return this.nodeMapper.findFireByCondition(type, branchLevel, deviceUuid);
    }

    public List<Node> findByConditionMetoo(Integer type, String branchLevel, String deviceUuid) {
//        String branchLevel = "00";
//        if (!AliStringUtils.isEmpty(userId)) {
//            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
//        }

        return this.nodeMapper.findFireByCondition(type, branchLevel, deviceUuid);
    }

    public PolicyTotalVo getPolicyTotalVoByDeviceId(String deviceId, String ip) {
        try {
            ThreadPoolExecutor executor = CommonThreadPoolExecutor.futureThreadPool;
            CompletionService<Object> completionService = new ExecutorCompletionService(executor);
            List<Future> results = new ArrayList();

            for(int i = 0; i < 3; ++i) {
                Future<Object> future = null;
                if (i == 0) {
                    future = completionService.submit(new Callable<Object>() {
                        public Object call() {
                            Map<String, Integer> map = new HashMap();
                            List<NodeHistory> historyList = (List)NodeServiceImpl.this.nodeMapper.getHistoryList(ip, 0, 10000).stream().filter((nodeHistoryxx) -> {
                                return nodeHistoryxx.getSubVersion() != null;
                            }).collect(Collectors.toList());
                            List<NodeHistory> nodeHistories = new LinkedList();
                            Iterator var4 = historyList.iterator();

                            while(var4.hasNext()) {
                                NodeHistory nodeHistoryx = (NodeHistory)var4.next();
                                boolean b = nodeHistories.stream().anyMatch((node) -> {
                                    return node.getSubVersion().equals(nodeHistoryx.getSubVersion());
                                });
                                if (!b) {
                                    nodeHistories.add(nodeHistoryx);
                                }
                            }

                            RevisionSearchDTO searchDTO = new RevisionSearchDTO();
                            searchDTO.setDeviceUuid(deviceId);
                            searchDTO.setPage(1);
                            searchDTO.setPsize(10);
                            ResultRO<List<RevisionSearchRO>> revisResultRO = NodeServiceImpl.this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
                            if (revisResultRO != null && revisResultRO.getSuccess()) {
                                List<RevisionSearchRO> collect = (revisResultRO.getData()).stream().filter((revisionSearchROx) -> {
                                    return !DeviceChangeTypeEnum.IRT_NEW.getCode().equals(revisionSearchROx.getType()) && !DeviceChangeTypeEnum.IRT_UPDATE.getCode().equals(revisionSearchROx.getType());
                                }).collect(Collectors.toList());
                                Iterator var7 = collect.iterator();

                                while(var7.hasNext()) {
                                    RevisionSearchRO revisionSearchRO = (RevisionSearchRO)var7.next();
                                    SubDeviceVersionIdRO subDeviceVerId = revisionSearchRO.getSubDeviceVerId();
                                    if (subDeviceVerId != null) {
                                        String version = subDeviceVerId.getVersion();
                                        if (StringUtils.isNotBlank(version)) {
                                            Iterator var11 = nodeHistories.iterator();

                                            while(var11.hasNext()) {
                                                NodeHistory nodeHistory = (NodeHistory)var11.next();
                                                if (nodeHistory.getSubVersion() != null && nodeHistory.getSubVersion().equals(Integer.valueOf(version))) {
                                                    List<RevisionChange> revisionChanges = NodeServiceImpl.this.listChangeByReversionId(Integer.valueOf(revisionSearchRO.getRevisionId()));
                                                    int size = revisionChanges.size();
                                                    if (CollectionUtils.isNotEmpty(nodeHistories) && nodeHistories.size() >= 2) {
                                                        ++size;
                                                    }

                                                    map.put("configChangeCount", size);
                                                    break;
                                                }
                                            }

                                            if (map != null) {
                                                break;
                                            }
                                        }
                                    }
                                }

                                return map;
                            } else {
                                return null;
                            }
                        }
                    });
                } else if (i == 1) {
                    future = completionService.submit(new Callable<Object>() {
                        public Object call() {
                            Map<String, Integer> map = new HashMap();
                            long l = System.currentTimeMillis();
                            JSONObject data = NodeServiceImpl.this.policyCheckReportService.getPolicyCheckData(deviceId);
                            NodeServiceImpl.log.info("获取策略耗费时间===" + (System.currentTimeMillis() - l));
                            JSONArray statistics = data.getJSONArray("statistics");
                            int wasteStrategyCount = 0;

                            for(int j = 0; j < statistics.size(); ++j) {
                                JSONObject jsonObject = JSONObject.parseObject(statistics.get(j).toString());
                                wasteStrategyCount += Integer.parseInt(String.valueOf(jsonObject.get("value")));
                            }

                            map.put("wasteStrategyCount", wasteStrategyCount);
                            return map;
                        }
                    });
                } else if (i == 2) {
                    future = completionService.submit(new Callable<Object>() {
                        public Object call() {
                            Map<String, Integer> map = new HashMap();
                            long l = System.currentTimeMillis();
                            JSONObject data = NodeServiceImpl.this.policyCheckReportService.getObjectCheckData(deviceId);
                            NodeServiceImpl.log.info("获取对象耗费时间===" + (System.currentTimeMillis() - l));
                            JSONArray statistics = data.getJSONArray("statistics");
                            int noUseObjectCount = 0;
                            int emptyObjectCount = 0;

                            for(int k = 0; k < statistics.size(); ++k) {
                                JSONObject jsonObject = JSONObject.parseObject(statistics.get(k).toString());
                                noUseObjectCount += Integer.parseInt(String.valueOf(jsonObject.get("unref")));
                                emptyObjectCount += Integer.parseInt(String.valueOf(jsonObject.get("empty")));
                            }

                            map.put("noUseObjectCount", noUseObjectCount);
                            map.put("emptyObjectCount", emptyObjectCount);
                            return map;
                        }
                    });
                }

                results.add(future);
            }

            PolicyTotalVo policyTotalVo = new PolicyTotalVo();
            Iterator var12 = results.iterator();

            while(var12.hasNext()) {
                Future result = (Future)var12.next();
                Map<String, Integer> stringMap = (Map)result.get();
                if (stringMap.containsKey("configChangeCount")) {
                    policyTotalVo.setConfigChangeCount((Integer)stringMap.get("configChangeCount"));
                }

                if (stringMap.containsKey("wasteStrategyCount")) {
                    policyTotalVo.setWasteStrategyCount((Integer)stringMap.get("wasteStrategyCount"));
                }

                if (stringMap.containsKey("noUseObjectCount")) {
                    policyTotalVo.setNoUseObjectCount((Integer)stringMap.get("noUseObjectCount"));
                }

                if (stringMap.containsKey("emptyObjectCount")) {
                    policyTotalVo.setEmptyObjectCount((Integer)stringMap.get("emptyObjectCount"));
                }
            }

            return policyTotalVo;
        } catch (Exception var10) {
            log.error("获取策略概览总数接口调用失败");
            return null;
        }
    }

    public List<RevisionChange> configChangeList(String deviceId, String ip) {
        List<RevisionChange> list = new ArrayList();
        List<NodeHistory> historyList = (List)this.nodeMapper.getHistoryList(ip, 0, 10000).stream().filter((nodeHistoryx) -> {
            return nodeHistoryx.getSubVersion() != null;
        }).collect(Collectors.toList());
        List<NodeHistory> nodeHistories = new LinkedList();
        Iterator var6 = historyList.iterator();

        while(var6.hasNext()) {
            NodeHistory nodeHistory = (NodeHistory)var6.next();
            boolean b = nodeHistories.stream().anyMatch((node) -> {
                return node.getSubVersion().equals(nodeHistory.getSubVersion());
            });
            if (!b) {
                nodeHistories.add(nodeHistory);
            }
        }

        RevisionSearchDTO searchDTO = new RevisionSearchDTO();
        searchDTO.setDeviceUuid(deviceId);
        searchDTO.setPage(1);
        searchDTO.setPsize(10);
        ResultRO<List<RevisionSearchRO>> revisResultRO = this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
        if (revisResultRO != null && revisResultRO.getSuccess() && !CollectionUtils.isEmpty((Collection)revisResultRO.getData())) {
            List<RevisionSearchRO> collect = (revisResultRO.getData()).stream().filter((revisionSearchROx) -> {
                return !DeviceChangeTypeEnum.IRT_NEW.getCode().equals(revisionSearchROx.getType()) && !DeviceChangeTypeEnum.IRT_UPDATE.getCode().equals(revisionSearchROx.getType());
            }).collect(Collectors.toList());
            Iterator var9 = collect.iterator();

            while(var9.hasNext()) {
                RevisionSearchRO revisionSearchRO = (RevisionSearchRO)var9.next();
                SubDeviceVersionIdRO subDeviceVerId = revisionSearchRO.getSubDeviceVerId();
                if (subDeviceVerId != null) {
                    String version = subDeviceVerId.getVersion();
                    if (StringUtils.isNotBlank(version)) {
                        Iterator var13 = nodeHistories.iterator();

                        while(var13.hasNext()) {
                            NodeHistory nodeHistory = (NodeHistory)var13.next();
                            if (nodeHistory.getSubVersion() != null && nodeHistory.getSubVersion().equals(Integer.valueOf(version))) {
                                list = this.listChangeByReversionId(Integer.valueOf(revisionSearchRO.getRevisionId()));
                                break;
                            }
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty((Collection)list)) {
                    break;
                }
            }

            List<String> deviceUuids = (list).stream().map((e) -> {
                return e.getDeviceUuid();
            }).collect(Collectors.toList());
            List<Node> deviceListByIds = this.nodeMapper.getDeviceListByIds(deviceUuids);
            Map<String, String> stringStringMap = (Map)deviceListByIds.stream().collect(Collectors.toMap(Node::getUuid, (e) -> {
                return e.getVendorName() + "_" + e.getIp();
            }));
            (list).stream().forEach((e) -> {
                if (stringStringMap.containsKey(e.getDeviceUuid())) {
                    e.setDeviceName((String)stringStringMap.get(e.getDeviceUuid()));
                }

            });
            return (List)list;
        } else {
            return null;
        }
    }

    public List<RevisionChange> listChangeByReversionId(int revisionId) {
        try {
            ResultRO<List<VersionChangeRO>> resultRO = this.whaleDeviceChangeClient.getDetailByVersion(revisionId);
            if (resultRO != null && resultRO.getSuccess()) {
                List<VersionChangeRO> data = (List)resultRO.getData();
                List<RevisionChange> nodeRevisions = new ArrayList();

                for(int i = 0; i < data.size(); ++i) {
                    RevisionChange revisionChange = new RevisionChange();
                    VersionChangeRO obj = (VersionChangeRO)data.get(i);
                    revisionChange.setDeviceUuid(obj.getSubDeviceVerId().getDeviceUuid());
                    revisionChange.setId(obj.getId());
                    String changeId = String.valueOf(obj.getChangeId());
                    revisionChange.setChangeId(changeId);
                    revisionChange.setChangeType(obj.getChangeType());
                    String objectType = obj.getObjectType();
                    revisionChange.setObjectType(objectType);
                    String routingTableUuid;
                    String config;
                    if (StringUtils.equals(objectType, "RULE_LIST")) {
                        revisionChange.setParent(true);
                        revisionChange.setObjectUuid(obj.getObjectUuid());
                    } else if (StringUtils.equals(objectType, "RULE_ITEM")) {
                        revisionChange.setParent(false);
                        routingTableUuid = obj.getAclUuid();
                        if (StringUtils.isNotBlank(routingTableUuid)) {
                            revisionChange.setObjectUuid(routingTableUuid);
                        } else {
                            config = obj.getDeviceName();
                            log.warn("设备[" + config + "]在版本[" + revisionId + "]的变更[" + changeId + "]中没有aclUuid");
                        }
                    } else {
                        Optional opt;
                        if (StringUtils.equals(objectType, "ROUTING_TABLE")) {
                            revisionChange.setParent(true);
                            routingTableUuid = obj.getObjectUuid();
                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
                        } else if (StringUtils.equals(objectType, "ROUTING_ENTRY")) {
                            revisionChange.setParent(false);
                            routingTableUuid = obj.getRoutingTableUuid();
                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
                        } else {
                            revisionChange.setParent(true);
                            revisionChange.setObjectUuid(obj.getObjectUuid());
                        }
                    }

                    revisionChange.setObjectName(obj.getObjectName());
                    routingTableUuid = obj.getPrevConfigText();
                    if (!StringUtils.isEmpty(routingTableUuid)) {
                        String[] prec = routingTableUuid.split(System.getProperty("line.separator"));
                        revisionChange.setPreConfigText(prec);
                    } else {
                        revisionChange.setPreConfigText(new String[0]);
                    }

                    config = obj.getConfigText();
                    if (!StringUtils.isEmpty(config)) {
                        revisionChange.setConfigText(config.split(System.getProperty("line.separator")));
                    } else {
                        revisionChange.setConfigText(new String[0]);
                    }

                    String time = obj.getRevisionTime();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
                    LocalDateTime ldt = LocalDateTime.parse(time, dtf);
                    revisionChange.setRevisionTime(ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    revisionChange.setDeviceName(obj.getDeviceName());
                    nodeRevisions.add(revisionChange);
                }

                List<RevisionChange> parentNode = new ArrayList();
                nodeRevisions.stream().filter(RevisionChange::getParent).forEach(parentNode::add);
                List<RevisionChange> nodeRevisionList = new ArrayList();
                parentNode.stream().filter((parent) -> {
                    return StringUtils.isNotBlank(parent.getObjectUuid());
                }).forEach((parent) -> {
                    nodeRevisions.stream().filter((change) -> {
                        return StringUtils.equals(parent.getObjectUuid(), change.getObjectUuid()) && !change.getParent();
                    }).forEach((change) -> {
                        parent.getRevisionChanges().add(change);
                    });
                    nodeRevisionList.add(parent);
                });
                return nodeRevisionList;
            } else {
                return null;
            }
        } catch (Exception var15) {
            log.error(var15.toString());
            return null;
        }
    }

    public RevisionChange showConfig(Integer id, Integer id2, Integer change) {
        RevisionChange otherRevisionChange = new RevisionChange();
        otherRevisionChange.setChangeType("UPDATE");
        otherRevisionChange.setObjectName("其它");
        otherRevisionChange.setParent(false);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = null;

        try {
            NodeHistory nodeHistory = this.nodeMapper.getNodeHistoryById(id);
            String ip = nodeHistory.getIp();
            String path = nodeHistory.getFilePath();
            String fileEncode = StandardCharsets.UTF_8.name();
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(fis, fileEncode));
            String tempString = null;

            while((tempString = reader.readLine()) != null) {
                stringBuffer.append(tempString);
                stringBuffer.append("\\r\\n");
            }

            fis.close();
            reader.close();
            FileInputStream file2is;
            String[] context;
            if (change != null && change == 1) {
                Integer version = nodeHistory.getVersion();
                NodeHistory nodeHistory1 = this.nodeMapper.getNodeHistoryByIpAndVersion(ip, version - 1);
                String oldPath = nodeHistory1.getFilePath();
                if (oldPath != null && oldPath.length() > 0) {
                    StringBuffer oldStringBuffer = new StringBuffer();
                    file2is = null;
                    File oldFile = new File(oldPath);
                    FileInputStream oldfis = new FileInputStream(oldFile);
                    BufferedReader oldReader = new BufferedReader(new InputStreamReader(oldfis, fileEncode));
                    context = null;

                    String oldTmp;
                    while((oldTmp = oldReader.readLine()) != null) {
                        oldStringBuffer.append(oldTmp);
                        oldStringBuffer.append("\\r\\n");
                    }

                    oldfis.close();
                    oldReader.close();
                    otherRevisionChange.setRevisionTime(nodeHistory1.getCreatedTime().getTime());
                    context = oldStringBuffer.toString().split(System.getProperty("line.separator"));
                    otherRevisionChange.setConfigText(context);
                } else {
                    otherRevisionChange.setRevisionTime(nodeHistory1.getCreatedTime().getTime());
                    otherRevisionChange.setConfigText((String[])null);
                }
            } else if (id2 != null && id2 > 0) {
                NodeHistory nodeHistory1 = this.nodeMapper.getNodeHistoryById(id2);
                String path2 = nodeHistory1.getFilePath();
                if (path2 != null && path2.length() > 0) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    File file2 = new File(path2);
                    file2is = new FileInputStream(file2);
                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(file2is, fileEncode));
                    String tempString2 = null;

                    while((tempString2 = reader2.readLine()) != null) {
                        stringBuffer2.append(tempString2);
                        stringBuffer2.append("\\r\\n");
                    }

                    file2is.close();
                    reader2.close();
                    otherRevisionChange.setRevisionTime(nodeHistory1.getCreatedTime().getTime());
                    context = stringBuffer2.toString().split(System.getProperty("line.separator"));
                    otherRevisionChange.setConfigText(context);
                } else {
                    otherRevisionChange.setRevisionTime(nodeHistory1.getCreatedTime().getTime());
                    otherRevisionChange.setConfigText((String[])null);
                }
            } else {
                otherRevisionChange.setRevisionTime((Long)null);
            }

            otherRevisionChange.setPreConfigText(stringBuffer.toString().split(System.getProperty("line.separator")));
            RevisionChange var35 = otherRevisionChange;
            return var35;
        } catch (IOException var31) {
            log.error(var31.getMessage(), var31);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException var30) {
                    ;
                }
            }

        }

        return otherRevisionChange;
    }

    public ResultRO<List<String>> getDeviceIdByIp(String ip) {
        new ArrayList();
        List deviceIdByIp;
        if (StringUtils.isEmpty(ip)) {
            deviceIdByIp = this.nodeMapper.getAllDeviceId();
        } else {
            deviceIdByIp = this.nodeMapper.getDeviceIdByIp(ip);
        }

        ResultRO resultRO = new ResultRO();
        if (CollectionUtils.isNotEmpty(deviceIdByIp)) {
            resultRO.setSuccess(true);
            resultRO.setData(deviceIdByIp);
        } else {
            resultRO.setSuccess(false);
        }

        return resultRO;
    }

    public Map<String, Node> getDeviceListIds(List<String> ids) {
        List<Node> deviceListByIds = this.nodeMapper.getDeviceListByIds(ids);
        HashMap<String, Node> map = new HashMap();
        Iterator var4 = deviceListByIds.iterator();

        while(var4.hasNext()) {
            Node node = (Node)var4.next();
            if (StringUtils.isNotEmpty(node.getUuid())) {
                map.put(node.getUuid(), node);
            }
        }

        return map;
    }
}
