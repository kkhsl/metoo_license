//
//package com.abtnetworks.data.totems.topology.basic.node.service;
//
//import com.abtnetworks.data.totems.log.client.LogClientSimple;
//import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
//import com.abtnetworks.data.totems.log.common.enums.LogLevel;
//import com.abtnetworks.data.totems.topology.alarm.service.AlarmService;
//import com.abtnetworks.data.totems.topology.basic.business.dao.BusinessRepository;
//import com.abtnetworks.data.totems.topology.basic.node.dao.CycleDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.EngineDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeGroupDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeHistoryDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeHistoryRepository;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeHistoryTextDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeProbeConfigMapper;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeRepository;
//import com.abtnetworks.data.totems.topology.basic.node.dao.RouteTableHistoryDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.RouteTableHistoryRepository;
//import com.abtnetworks.data.totems.topology.basic.node.dao.SystemParamMapper;
//import com.abtnetworks.data.totems.topology.basic.node.dao.TopoNodeSynMapper;
//import com.abtnetworks.data.totems.topology.basic.node.domain.CredentialEntity;
//import com.abtnetworks.data.totems.topology.basic.node.domain.Engine;
//import com.abtnetworks.data.totems.topology.basic.node.domain.Node;
//import com.abtnetworks.data.totems.topology.basic.node.domain.NodeGroup;
//import com.abtnetworks.data.totems.topology.basic.node.domain.NodeHistory;
//import com.abtnetworks.data.totems.topology.basic.node.domain.NodeProbeConfig;
//import com.abtnetworks.data.totems.topology.basic.node.domain.NodeRevision;
//import com.abtnetworks.data.totems.topology.basic.node.domain.RevisionChange;
//import com.abtnetworks.data.totems.topology.basic.node.domain.RouteTableHistory;
//import com.abtnetworks.data.totems.topology.basic.node.domain.TopoNodeSynEntity;
//import com.abtnetworks.data.totems.topology.basic.node.dto.BatchImportNodeDTO;
//import com.abtnetworks.data.totems.topology.basic.node.dto.ImportCredentialDTO;
//import com.abtnetworks.data.totems.topology.basic.node.dto.InterfaceLinkMapping;
//import com.abtnetworks.data.totems.topology.basic.node.dto.MailServerConfDTO;
//import com.abtnetworks.data.totems.topology.basic.node.dto.NodeHistoryDTO;
//import com.abtnetworks.data.totems.topology.basic.node.dto.ProbeLinkResponse;
//import com.abtnetworks.data.totems.topology.basic.node.dto.SessionMonitorContext;
//import com.abtnetworks.data.totems.topology.basic.node.enums.NodeSynStatusEnum;
//import com.abtnetworks.data.totems.topology.basic.node.enums.NodeType;
//import com.abtnetworks.data.totems.topology.basic.node.utils.CustomMailTool;
//import com.abtnetworks.data.totems.topology.common.Result;
//import com.abtnetworks.data.totems.topology.common.ReturnT;
//import com.abtnetworks.data.totems.topology.common.tool.DateUtils;
//import com.abtnetworks.data.totems.topology.common.tool.FileUtil;
//import com.abtnetworks.data.totems.topology.common.tool.FileUtils;
//import com.abtnetworks.data.totems.topology.common.tool.HttpClientUtils;
//import com.abtnetworks.data.totems.topology.common.tool.StringUtils;
//import com.abtnetworks.data.totems.topology.common.tool.md5sumUtils;
//import com.abtnetworks.data.totems.topology.constant.Constants;
//import com.abtnetworks.data.totems.topology.relevance.dto.CascadeNodeForUploadDTO;
//import com.abtnetworks.data.totems.topology.relevance.service.CascadeNodeService;
//import com.abtnetworks.data.totems.topology.userms.data.domain.User;
//import com.abtnetworks.data.totems.topology.userms.data.repository.BranchRepository;
//import com.abtnetworks.data.totems.topology.userms.data.repository.UserRepository;
//import com.abtnetworks.data.totems.topology.userms.service.BranchService;
//import com.abtnetworks.data.totems.topology.userms.service.UMSUserService;
//import com.abtnetworks.totems.common.enums.DeviceChangeTypeEnum;
//import com.abtnetworks.totems.common.enums.VendorEnum;
//import com.abtnetworks.totems.common.ro.ResultRO;
//import com.abtnetworks.totems.whale.baseapi.dto.BatchDeviceDTO;
//import com.abtnetworks.totems.whale.baseapi.dto.RevisionBatchSearchDTO;
//import com.abtnetworks.totems.whale.baseapi.dto.RevisionSearchDTO;
//import com.abtnetworks.totems.whale.baseapi.ro.BundleVersionRO;
//import com.abtnetworks.totems.whale.baseapi.ro.ChangeCountsRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceSummaryDataRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceSummaryRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceUploadRO;
//import com.abtnetworks.totems.whale.baseapi.ro.ImportedRoutingTableDataVersionRO;
//import com.abtnetworks.totems.whale.baseapi.ro.InterfacesRO;
//import com.abtnetworks.totems.whale.baseapi.ro.LayerTwoDeviceRO;
//import com.abtnetworks.totems.whale.baseapi.ro.RevisionSearchRO;
//import com.abtnetworks.totems.whale.baseapi.ro.SkipAnalysisRO;
//import com.abtnetworks.totems.whale.baseapi.ro.ToSameInboundRO;
//import com.abtnetworks.totems.whale.baseapi.ro.VersionChangeRO;
//import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceChangeClient;
//import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceObjectClient;
//import com.abtnetworks.totems.whale.policyoptimize.ro.SubDeviceVersionIdRO;
//import com.abtnetworks.totems.whale.system.ro.CaptchaImageRO;
//import com.abtnetworks.totems.whale.system.ro.ControllerSettingsRO;
//import com.abtnetworks.totems.whale.system.ro.CreateProfileRO;
//import com.abtnetworks.totems.whale.system.ro.DeviceProfileRO;
//import com.abtnetworks.totems.whale.system.ro.MiscParamsRO;
//import com.abtnetworks.totems.whale.system.service.WhaleDeviceProfile;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONException;
//import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.google.common.collect.Maps;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.function.BinaryOperator;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.collections4.CollectionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//public class NodeService {
//    private static Logger logger = LoggerFactory.getLogger(NodeService.class);
//    @Value("${topology.dir-path}")
//    protected String dirPath;
//    @Autowired
//    private NodeRepository nodeRepository;
//    @Autowired
//    private NodeHistoryRepository nodeHistoryRepository;
//    @Autowired
//    private NodeHistoryDAO nodeHistoryDAO;
//    @Autowired
//    private NodeHistoryTextDAO nodeHistoryTextDAO;
//    @Autowired
//    private BusinessRepository businessRepository;
//    @Autowired
//    private NodeDAO nodeDAO;
//    @Autowired
//    private NodeGroupDAO nodeGroupDAO;
//    @Autowired
//    private EngineDAO engineDAO;
//    @Autowired
//    private LogClientSimple logClientSimple;
//    @Autowired
//    private NodeProbeConfigMapper nodeProbeConfigMapper;
//    @Autowired
//    private AlarmService alarmService;
//    @Autowired
//    private CycleDAO cycleDAO;
//    @Autowired
//    private RouteTableHistoryRepository routeTableHistoryRepository;
//    @Autowired
//    private RouteTableHistoryDAO routeTableHistoryDAO;
//    @Autowired
//    private BranchService branchService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private SystemParamMapper paramMapper;
//    @Autowired
//    private BranchRepository branchRepository;
//    private String gatherId = "";
//    private String sessionUuid = "";
//    private Node savedNode;
//    @Autowired
//    private WhaleDeviceObjectClient whaleDeviceObjectClient;
//    @Autowired
//    private WhaleDeviceProfile whaleDeviceProfile;
//    @Autowired
//    WhaleDeviceChangeClient whaleDeviceChangeClient;
//    @Value("${topology.push-prefix}")
//    private String pushServerPrefix;
//    @Qualifier("clientCredentialsOAuth2RestTemplate")
//    @Autowired
//    private OAuth2RestTemplate clientCredentialsOAuth2RestTemplate;
//    @Autowired
//    private NodeExcelService nodeExcelService;
//    @Autowired
//    private TopoNodeSynMapper topoNodeSynMapper;
//    @Autowired
//    CascadeNodeService cascadeNodeService;
//    @Autowired
//    UMSUserService umsUserService;
//
//    public NodeService() {
//    }
//
//    public String saveTmpFile(MultipartFile file, String ip, String pluginId, String fileType) {
//        try {
//            if (!file.isEmpty()) {
//                try {
//                    String pathname = this.dirPath + "/history/";
//                    File dir = new File(pathname);
//                    if (!dir.exists()) {
//                        FileUtils.createDir(pathname);
//                    }
//
//                    String fileName = null;
//                    if ("BASIC".equals(fileType)) {
//                        fileName = pluginId + "_" + ip + "_." + DateUtils.getTimeStamp() + ".conf";
//                    } else if ("ROUTETABLE".equals(fileType)) {
//                        fileName = pluginId + "_" + ip + "_." + DateUtils.getTimeStamp() + ".rt.conf";
//                    }
//
//                    String filePath = pathname + fileName;
//                    File fileTmp = new File(filePath);
//                    if (!fileTmp.exists()) {
//                        fileTmp.createNewFile();
//                    }
//
//                    file.transferTo(fileTmp);
//                    return filePath;
//                } catch (Exception var10) {
//                    logger.error(var10.getMessage(), var10);
//                }
//            }
//        } catch (Exception var11) {
//            logger.error(var11.getMessage(), var11);
//        }
//
//        return null;
//    }
//
//    public JSONObject upload(String filepath, String cmbDeviceVendor, String cmbDeviceType, String deviceName, String deviceType, String importedName, String ip, String pluginId, Integer nodeGroup, String modelNumber, String description, String branchLevel, boolean isAddFlag, String nextHopIp) {
//        JSONObject result = new JSONObject();
//
//        try {
//            Map<String, String> textMap = new HashMap();
//            textMap.put("cmbDeviceVendor", cmbDeviceVendor);
//            textMap.put("cmbDeviceType", cmbDeviceType);
//            Map<String, String> fileMap = new HashMap();
//            fileMap.put("files", filepath);
//            String ret = this.formUpload(textMap, fileMap);
//            JSONObject json = JSONObject.parseObject(ret);
//            JSONArray dataArray = json.getJSONArray("data");
//            String fileName = this.getFileNameByPath(filepath);
//
//            for(int dataIndex = 0; dataIndex < dataArray.size(); ++dataIndex) {
//                JSONObject data = dataArray.getJSONObject(dataIndex);
//                String successFlag = data.getString("operationStatus");
//                if (!"SUCCESS".equals(successFlag) && !"WARNING".equals(successFlag)) {
//                    JSONObject errorObject = data.getJSONObject("error");
//                    String errorCode = errorObject.getString("errorCode");
//                    String msg = "文件导入失败";
//                    if ("WHALE-13004".equals(errorCode)) {
//                        msg = "配置文件解析错误，请确定你上传的文件是（" + cmbDeviceVendor + "）的配置文件。错误码为:" + errorCode;
//                    } else {
//                        msg = "文件导入失败,错误码为:" + errorCode;
//                    }
//
//                    File file = new File(filepath);
//                    if (file.exists()) {
//                        file.delete();
//                    }
//
//                    result.put("result", false);
//                    result.put("msg", msg);
//                    result.put("data", json);
//                    return result;
//                }
//
//                String deviceUuid = data.getJSONObject("subDeviceVersionId").getString("deviceUuid");
//                Node ipNode = this.nodeDAO.getTheNodeByIP(ip);
//                if (ipNode != null && ipNode.getGatherId() != null) {
//                    this.whaleDeviceProfile.deleteProfile(ipNode.getGatherId());
//                }
//
//                Node uuidNode = this.nodeDAO.getTheNodeByUuid(deviceUuid);
//                Node node = new Node();
//                String tmpIp;
//                if (uuidNode != null) {
//                    node.setId(uuidNode.getId());
//                    node.setIp(uuidNode.getIp());
//                    node.setProbeIp(uuidNode.getProbeIp());
//                    node.setProbeToken(uuidNode.getProbeToken());
//                    node.setSkipAnalysis(uuidNode.getSkipAnalysis());
//                    node.setLayerTwoDevice(uuidNode.getLayerTwoDevice());
//                    node.setToSameInbound(uuidNode.getToSameInbound());
//                    node.setBusinessSubnet(uuidNode.getBusinessSubnet());
//                } else if (ipNode != null && (ipNode.getUuid() == null || ipNode.getUuid().equals(deviceUuid))) {
//                    node.setId(ipNode.getId());
//                    node.setIp(ip);
//                    node.setProbeIp(ipNode.getProbeIp());
//                    node.setProbeToken(ipNode.getProbeToken());
//                } else {
//                    int i = 0;
//                    tmpIp = null;
//
//                    while(true) {
//                        if (i == 0) {
//                            tmpIp = ip;
//                        } else {
//                            tmpIp = ip + "(" + i + ")";
//                        }
//
//                        Node theNodeByIP = this.nodeDAO.getTheNodeByIP(tmpIp);
//                        if (theNodeByIP == null) {
//                            node.setIp(tmpIp);
//                            break;
//                        }
//
//                        ++i;
//                    }
//                }
//
//                node.setUuid(deviceUuid);
//                node.setType(deviceType);
//                node.setDeviceName(deviceName);
//                node.setImportedName(importedName);
//                node.setHostname((String)null);
//                node.setOrigin(1);
//                node.setState(1);
//                node.setFilePath(fileName);
//                node.setVendorName(cmbDeviceVendor);
//                node.setVendorId(cmbDeviceType);
//                node.setPluginId(pluginId);
//                node.setVersion("0");
//                node.setNodeGroup(nodeGroup);
//                node.setModelNumber(modelNumber);
//                node.setDescription(description);
//                node.setBranchLevel(branchLevel);
//                node.setIsChangeWarn(0);
//                node.setNextHopIp(nextHopIp);
//                if ("3".equals(deviceType)) {
//                    LayerTwoDeviceRO layerTwoDeviceRO = this.whaleDeviceObjectClient.layerTwoDevice(node.getUuid(), true);
//                    if (!layerTwoDeviceRO.getSuccess()) {
//                        logger.warn("设定设备是否为二层设备失败:" + node.getIp());
//                    } else {
//                        node.setLayerTwoDevice("true");
//                    }
//                }
//                this.nodeRepository.save(node);
//                logger.info("开始执行同步策略数据");
//                CascadeNodeForUploadDTO cascadeNodeForUploadDTO = new CascadeNodeForUploadDTO();
//                BeanUtils.copyProperties(node, cascadeNodeForUploadDTO);
//                cascadeNodeForUploadDTO.setFilePath(filepath);
//                cascadeNodeForUploadDTO.setTypeCode("0");
//                cascadeNodeForUploadDTO.setDeviceUuid(node.getUuid());
//                this.cascadeNodeService.uploadCascadeNode(cascadeNodeForUploadDTO);
//                if (!"linux-host".equals(pluginId)) {
//                    this.whaleDeviceObjectClient.addAlias(deviceUuid, deviceName, deviceType);
//                }
//
//                tmpIp = data.getJSONObject("subDeviceVersionId").getString("version");
//                NodeHistory nodeHistory = new NodeHistory();
//                nodeHistory.setSubVersion(Integer.valueOf(tmpIp));
//                nodeHistory.setIp(node.getIp());
//                nodeHistory.setFilePath(fileName);
//                nodeHistory.setOrigin(1);
//                String md5sum = md5sumUtils.MD5Str(filepath);
//                nodeHistory.setMd5sum(md5sum);
//                nodeHistory.setTextId(-1);
//                Integer version = this.nodeHistoryDAO.getCurrentVersionByIP(node.getIp());
//                this.setVersionPathMd5(node.getIp(), nodeHistory, md5sum, version);
//                this.restrictSumHistory(node.getIp());
//                this.nodeHistoryRepository.save(nodeHistory);
//            }
//
//            result.put("result", true);
//            result.put("msg", "文件导入成功");
//            result.put("data", json);
//            return result;
//        } catch (Exception var34) {
//            File file = new File(filepath);
//            if (file.exists()) {
//                file.delete();
//            }
//
//            result.put("result", false);
//            result.put("msg", "系统异常,请联系系统管理员");
//            logger.error(var34.getMessage(), var34);
//            return result;
//        }
//    }
//
//    public JSONObject uploadRouteTable(RouteTableHistory routeTableHistory) {
//        JSONObject uploadResult = new JSONObject();
//
//        try {
//            Map<String, String> fileMap = new HashMap();
//            fileMap.put("files", routeTableHistory.getFilePath());
//            String ret = this.formUpload((Map)null, fileMap);
//            JSONObject json = JSONObject.parseObject(ret);
//            JSONObject resultJson = json.getJSONArray("data").getJSONObject(0);
//            String successFlag = resultJson.getString("operationStatus");
//            JSONObject dataVid = resultJson.getJSONObject("importedRoutingDataVid");
//            if (dataVid != null) {
//                Integer dataVersion = dataVid.getInteger("version");
//                routeTableHistory.setDataVersion(dataVersion);
//            }
//
//            if (!"SUCCESS".equals(successFlag) && !"WARNING".equals(successFlag)) {
//                JSONObject errorObject = resultJson.getJSONObject("error");
//                String errorCode = errorObject.getString("errorCode");
//                routeTableHistory.setState(Constants.FAILURE_STATUS_NUMBER);
//                routeTableHistory.setMsg(errorObject.toString());
//                uploadResult.put("result", false);
//                uploadResult.put("msg", "路由表导入失败,错误码为:" + errorCode);
//            } else {
//                routeTableHistory.setMd5sum(md5sumUtils.MD5Str(routeTableHistory.getFilePath()));
//                RouteTableHistory routeTableHistoryLast = this.routeTableHistoryDAO.getLastCorrectByIP(routeTableHistory.getIp());
//                if (routeTableHistoryLast != null && !routeTableHistory.getMd5sum().equals(routeTableHistoryLast.getMd5sum())) {
//                    routeTableHistory.setChangeFlog(Constants.YES_STATUS_NUMBER);
//                } else {
//                    routeTableHistory.setChangeFlog(Constants.NO_STATUS_NUMBER);
//                }
//
//                routeTableHistory.setState(Constants.SUCCESS_STATUS_NUMBER);
//                routeTableHistory.setMsg(json.toJSONString());
//                uploadResult.put("result", true);
//                uploadResult.put("msg", "文件导入成功");
//            }
//        } catch (Exception var11) {
//            routeTableHistory.setState(Constants.FAILURE_STATUS_NUMBER);
//            routeTableHistory.setMsg("系统异常,具体内容如下:" + var11.getMessage());
//            uploadResult.put("result", false);
//            uploadResult.put("msg", "系统异常,请联系系统管理员");
//            logger.error(var11.getMessage(), var11);
//        }
//
//        Integer lastVersion = this.routeTableHistoryDAO.getCurrentVersionByIP(routeTableHistory.getIp());
//        if (lastVersion == null) {
//            lastVersion = 0;
//        }
//
//        routeTableHistory.setVersion(lastVersion + 1);
//        this.restrictRouteTableHistory(routeTableHistory.getIp());
//        routeTableHistory.setFilePath(this.getFileNameByPath(routeTableHistory.getFilePath()));
//        this.routeTableHistoryRepository.save(routeTableHistory);
//        return uploadResult;
//    }
//
//    public void deleteRouteTable(String ip) {
//        List<String> paths = this.routeTableHistoryDAO.getPathByIP(ip);
//        Iterator var3 = paths.iterator();
//
//        while(var3.hasNext()) {
//            String filePath = (String)var3.next();
//            File file = new File(filePath);
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//
//        this.routeTableHistoryDAO.deleteByIP(ip);
//    }
//
//    public void restrictRouteTableHistory(String ip) {
//        try {
//            Integer total = this.routeTableHistoryDAO.getCount(ip);
//            if (total >= 500) {
//                List<RouteTableHistory> routeTableHistories = this.routeTableHistoryDAO.getQualifierRecord(ip, total - 499);
//                RouteTableHistory routeTableHistory;
//                if (routeTableHistories != null && routeTableHistories.size() > 0) {
//                    for(Iterator var4 = routeTableHistories.iterator(); var4.hasNext(); this.routeTableHistoryDAO.deleteById(routeTableHistory.getId())) {
//                        routeTableHistory = (RouteTableHistory)var4.next();
//                        File file = new File(routeTableHistory.getFilePath());
//                        if (file.exists()) {
//                            file.delete();
//                        }
//                    }
//                }
//            }
//        } catch (Exception var7) {
//            logger.error(var7.getMessage(), var7);
//        }
//
//    }
//
//    public void setVersionPathMd5(String ip, NodeHistory nodeHistory, String md5sum, Integer version) {
//        if (version != null) {
//            String preFilePath = this.nodeHistoryDAO.getPrePathByIPAndVersion(ip, version);
//            if (preFilePath != null && preFilePath.length() > 0) {
//                String md5sumPre = md5sumUtils.MD5Str(this.buildNodeHistoryPath(preFilePath));
//                if (!md5sum.equals(md5sumPre)) {
//                    nodeHistory.setChangeFlog(1);
//                }
//            } else {
//                nodeHistory.setChangeFlog(0);
//            }
//
//            nodeHistory.setVersion(version + 1);
//        }
//
//    }
//
//    public String formUpload(Map<String, String> textMap, Map<String, String> fileMap) {
//        DeviceUploadRO uploadRO = this.whaleDeviceObjectClient.deviceFileUpload(textMap, fileMap);
//        return JSONObject.toJSONString(uploadRO);
//    }
//
//    public JSONObject findNodeByPage(Integer type, String vendor, String filter, Integer display, Integer origin, Integer state, String branchLevel, String skipAnalysis, String toSameInbound, String layerTwoDevice, Integer start, Integer size, String uuid) {
//        JSONObject result = new JSONObject();
//
//        try {
//            if (filter != null) {
//                filter = "%" + filter + "%";
//            }
//
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime lastDay = now.plus(-24L, ChronoUnit.HOURS);
//            ZonedDateTime zdt = now.atZone(ZoneOffset.UTC);
//            String isoNow = zdt.toString();
//            ZonedDateTime zdt1 = lastDay.atZone(ZoneOffset.UTC);
//            String isoLastDay = zdt1.toString();
//            Integer notType = 4;
//            if (type != null && type == 4) {
//                notType = null;
//            }
//
//            if (start <= 0) {
//                start = 1;
//            }
//
//            int startRow = (start - 1) * size;
//            List<Node> resultPage = this.nodeDAO.findAll(type, notType, vendor, filter, display, origin, state, branchLevel, skipAnalysis, toSameInbound, layerTwoDevice, startRow, size, uuid);
//            Map<String, String> branchNameMap = this.branchService.getBranchNameMap(branchLevel);
//            List<NodeHistory> historyList = this.nodeHistoryDAO.listLastGatherRecord();
//            Map<String, NodeHistory> historyMap = new HashMap();
//            if (historyList != null && !historyList.isEmpty()) {
//                Iterator var27 = historyList.iterator();
//
//                while(var27.hasNext()) {
//                    NodeHistory history = (NodeHistory)var27.next();
//                    historyMap.put(history.getIp(), history);
//                }
//            }
//
//            ResultRO<List<DeviceDataRO>> listResultRO = null;
//            ResultRO<List<RevisionSearchRO>> revisionSearchResultRO = null;
//            if (CollectionUtils.isNotEmpty(resultPage)) {
//                List<String> deviceUuids = (List)resultPage.stream().map(Node::getUuid).collect(Collectors.toList());
//                BatchDeviceDTO dto = new BatchDeviceDTO();
//                dto.setDeviceUuids(deviceUuids);
//                listResultRO = this.whaleDeviceObjectClient.getDeviceROByUuidList(dto);
//                RevisionBatchSearchDTO searchDTO = new RevisionBatchSearchDTO();
//                searchDTO.setDeviceUuids(deviceUuids);
//                searchDTO.setStartTime(isoLastDay);
//                searchDTO.setEndTime(isoNow);
//                searchDTO.setRevisionTimeGroup(true);
//                searchDTO.setPage(1);
//                searchDTO.setPsize(size);
//                revisionSearchResultRO = this.whaleDeviceObjectClient.getBatchRevisionSearch(searchDTO);
//            }
//
//            Map<String, DeviceDataRO> deviceMap = Maps.newHashMap();
//            Map<String, RevisionSearchRO> revisionMap = Maps.newHashMap();
//            if (listResultRO != null && listResultRO.getSuccess() && CollectionUtils.isNotEmpty((Collection)listResultRO.getData())) {
//                deviceMap = (Map)((List)listResultRO.getData()).stream().collect(Collectors.toMap(DeviceDataRO::getUuid, (DeviceDataRO) -> {
//                    return DeviceDataRO;
//                }, (key1, key2) -> {
//                    return key1;
//                }));
//            }
//
//            Iterator var46;
//            if (revisionSearchResultRO != null && revisionSearchResultRO.getSuccess() && CollectionUtils.isNotEmpty((Collection)revisionSearchResultRO.getData())) {
//                var46 = ((List)revisionSearchResultRO.getData()).iterator();
//
//                while(var46.hasNext()) {
//                    RevisionSearchRO revisionSearchRO = (RevisionSearchRO)var46.next();
//                    if (revisionSearchRO != null && revisionSearchRO.getSubDeviceVerId() != null && !StringUtils.isEmpty(revisionSearchRO.getSubDeviceVerId().getDeviceUuid())) {
//                        revisionMap.put(revisionSearchRO.getSubDeviceVerId().getDeviceUuid(), revisionSearchRO);
//                    }
//                }
//            }
//
//            var46 = resultPage.iterator();
//
//            while(var46.hasNext()) {
//                Node node = (Node)var46.next();
//                node.setBranchName((String)branchNameMap.get(node.getBranchLevel()));
//                String deviceUuid = node.getUuid();
//                RevisionSearchRO revisResultRO2 = (RevisionSearchRO)revisionMap.get(deviceUuid);
//                String msg;
//                if (revisionSearchResultRO != null && revisionSearchResultRO.getSuccess()) {
//                    if (null == revisResultRO2) {
//                        node.setChangeStatus(0);
//                    } else {
//                        Map<String, ChangeCountsRO> map = new HashMap();
//                        msg = revisResultRO2.getType();
//                        map.put(msg, revisResultRO2.getChangeCounts());
//                        Boolean show = false;
//                        Boolean policyShow = false;
//                        Iterator var39 = map.keySet().iterator();
//
//                        while(var39.hasNext()) {
//                            String string = (String)var39.next();
//                            if (!string.equals(DeviceChangeTypeEnum.NEW_DEVICE.getCode()) && !string.equals(DeviceChangeTypeEnum.IRT_NEW.getCode())) {
//                                show = true;
//                                if (map.get(string) != null) {
//                                    policyShow = true;
//                                }
//                            }
//                        }
//
//                        if (show) {
//                            if (policyShow) {
//                                node.setChangeStatus(1);
//                            } else {
//                                node.setChangeStatus(2);
//                            }
//                        } else {
//                            node.setChangeStatus(0);
//                        }
//                    }
//                } else {
//                    node.setChangeStatus(3);
//                }
//
//                NodeHistory currentHistoryByIP = (NodeHistory)historyMap.get(node.getIp());
//                if (currentHistoryByIP != null) {
//                    node.setChangeFlog(currentHistoryByIP.getChangeFlog());
//                    node.setHistoryId(currentHistoryByIP.getId());
//                    msg = currentHistoryByIP.getMsg();
//                    node.setErrorMess(msg);
//                    node.setGatherTime(currentHistoryByIP.getCreatedTime());
//                } else {
//                    node.setGatherTime(node.getModifiedTime());
//                }
//
//                if (node.getNodeGroup() != null) {
//                    NodeGroup nodeGroupById = this.nodeGroupDAO.getNodeGroupById(node.getNodeGroup());
//                    node.setNodeGroupName(nodeGroupById.getName());
//                }
//
//                if (deviceUuid != null && !"".equals(deviceUuid)) {
//                    DeviceDataRO dataObject = (DeviceDataRO)((Map)deviceMap).get(deviceUuid);
//                    if (null != dataObject) {
//                        String name = dataObject.getName();
//                        Optional<Boolean> isVsysOptional = Optional.ofNullable(dataObject.getIsVsys());
//                        isVsysOptional.ifPresent(node::setIsVsys);
//                        String rootDeviceUuid = dataObject.getRootDeviceUuid();
//                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(rootDeviceUuid)) {
//                            node.setRootDeviceUuid(rootDeviceUuid);
//                            name = dataObject.getVsysName();
//                        }
//
//                        node.setvSysName(name);
//                    }
//                }
//            }
//
//            Integer num = this.nodeDAO.getTotalNum(type, notType, vendor, filter, origin, state, display, branchLevel, skipAnalysis, toSameInbound, layerTwoDevice, uuid);
//            result.put("total", num);
//            result.put("data", resultPage);
//        } catch (Exception var41) {
//            logger.error(var41.getMessage(), var41);
//        }
//
//        return result;
//    }
//
//    public JSONObject getTheNode(Integer id) {
//        JSONObject result = new JSONObject();
//
//        try {
//            Node node = this.nodeDAO.getTheNode(id);
//            result.put("data", node);
//        } catch (Exception var4) {
//            logger.error(var4.getMessage(), var4);
//        }
//
//        return result;
//    }
//
//    public JSONObject getNavigation(Integer type, Integer nodeGroup, String branchLevel) {
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            List<Integer> nodeGroupIds = null;
//            List VendorNames;
//            if (type == 1) {
//                nodeGroupIds = this.nodeDAO.getDistinctNodeGroup();
//                Iterator var15 = nodeGroupIds.iterator();
//
//                while(var15.hasNext()) {
//                    Integer nodeGroupId = (Integer)var15.next();
//                    JSONObject nodeGroupResult = new JSONObject();
//                    if (nodeGroupId != null) {
//                        NodeGroup nodeGroupById = this.nodeGroupDAO.getNodeGroupById(nodeGroupId);
//                        VendorNames = this.nodeDAO.getDistinctType(nodeGroupId, branchLevel);
//                        this.integratedData(nodeGroupId, nodeGroupResult, VendorNames, branchLevel);
//                        jsonObject.put(nodeGroupById.getName(), nodeGroupResult);
//                    }
//                }
//
//                return jsonObject;
//            }
//
//            List Types;
//            if (type == 2) {
//                Types = null;
//                JSONObject nodeGroupResult = new JSONObject();
//                List<String> Types = this.nodeDAO.getDistinctType(Types, branchLevel);
//                this.integratedData(Types, nodeGroupResult, Types, branchLevel);
//                return nodeGroupResult;
//            }
//
//            Types = this.nodeDAO.getDistinctType(nodeGroup, branchLevel);
//
//            String t;
//            JSONObject result;
//            label62:
//            for(Iterator var7 = Types.iterator(); var7.hasNext(); jsonObject.put(t, result)) {
//                t = (String)var7.next();
//                result = new JSONObject();
//                VendorNames = this.nodeDAO.getAllVendorNameByType(t, nodeGroup, branchLevel);
//                Iterator var11 = VendorNames.iterator();
//
//                while(true) {
//                    while(true) {
//                        if (!var11.hasNext()) {
//                            continue label62;
//                        }
//
//                        String v = (String)var11.next();
//                        if ("Linux".equals(v)) {
//                            logger.debug("排除主机节点");
//                        } else {
//                            Integer total = this.nodeDAO.getNumByVT(v, t, nodeGroup, branchLevel);
//                            if (v != null && !v.equals("")) {
//                                result.put(v, total);
//                            } else if (!result.containsKey("null")) {
//                                result.put("null", total);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception var14) {
//            logger.error(var14.getMessage(), var14);
//        }
//
//        return jsonObject;
//    }
//
//    private void integratedData(Integer nodeGroupId, JSONObject nodeGroupResult, List<String> types, String branchLevel) {
//        String nodeType;
//        JSONObject result;
//        label34:
//        for(Iterator var5 = types.iterator(); var5.hasNext(); nodeGroupResult.put(nodeType, result)) {
//            nodeType = (String)var5.next();
//            result = new JSONObject();
//            List<String> VendorNames = this.nodeDAO.getAllVendorNameByType(nodeType, nodeGroupId, branchLevel);
//            Iterator var9 = VendorNames.iterator();
//
//            while(true) {
//                while(true) {
//                    if (!var9.hasNext()) {
//                        continue label34;
//                    }
//
//                    String verdorName = (String)var9.next();
//                    if ("Linux".equals(verdorName)) {
//                        logger.debug("排除主机节点");
//                    } else {
//                        JSONObject tmp = new JSONObject();
//                        Integer total = this.nodeDAO.getNumByVT(verdorName, nodeType, nodeGroupId, branchLevel);
//                        if (verdorName != null && !verdorName.equals("")) {
//                            List<Node> nodes = this.nodeDAO.getAllIdsByVendorNamesTypeNodeGroup(verdorName, nodeType, nodeGroupId);
//                            tmp.put("total", total);
//                            tmp.put("data", nodes);
//                            result.put(verdorName, tmp);
//                        } else if (!tmp.containsKey("null")) {
//                            tmp.put("null", total);
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    public JSONObject nodeDelete(String ids) {
//        JSONObject result = new JSONObject();
//        result.put("result", true);
//        if (ids != null) {
//            try {
//                JSONArray tmpArray = new JSONArray();
//                String[] split = ids.split(",");
//                logger.info("处理主墙虚墙删除的问题");
//                DeviceSummaryRO deviceSummaryRO = this.whaleDeviceObjectClient.getDevicesSummaryRO(true, true);
//                boolean message;
//                if (deviceSummaryRO.getSuccess()) {
//                    List<DeviceSummaryDataRO> deviceSummaryDataROS = deviceSummaryRO.getData();
//                    Map<String, String> virtualRootUuidMap = (Map)deviceSummaryDataROS.stream().filter((x) -> {
//                        return org.apache.commons.lang3.StringUtils.equals(x.getIsVsys(), "true");
//                    }).collect(Collectors.toMap(DeviceSummaryDataRO::getUuid, DeviceSummaryDataRO::getRootDeviceUuid));
//                    List<Node> nodeList = this.nodeDAO.getAllDeviceUuid();
//                    Map<String, String> allIdUuidMap = (Map)nodeList.stream().filter((x) -> {
//                        return org.apache.commons.lang3.StringUtils.isNoneEmpty(new CharSequence[]{x.getUuid()});
//                    }).collect(Collectors.toMap(Node::getId, Node::getUuid));
//                    Map<String, String> allUuidIdMap = (Map)nodeList.stream().filter((x) -> {
//                        return org.apache.commons.lang3.StringUtils.isNoneEmpty(new CharSequence[]{x.getUuid()});
//                    }).collect(Collectors.toMap(Node::getUuid, Node::getId));
//                    Set<String> idSet = (Set)Arrays.stream(split).collect(Collectors.toSet());
//                    message = idSet.stream().anyMatch((idx) -> {
//                        String uuid = (String)allIdUuidMap.get(idx);
//                        if (virtualRootUuidMap.containsKey(uuid)) {
//                            String rootUuid = (String)virtualRootUuidMap.get(uuid);
//                            String rootId = (String)allUuidIdMap.get(rootUuid);
//                            if (!idSet.contains(rootId)) {
//                                logger.error("虚设备{}不可单独删除", idx);
//                                return true;
//                            }
//                        }
//
//                        return false;
//                    });
//                    if (message) {
//                        result.put("result", false);
//                        result.put("msg", "虚墙不可单独删除");
//                        return result;
//                    }
//
//                    Set<String> virtualIds = new HashSet();
//                    idSet.forEach((idx) -> {
//                        String uuid = (String)allIdUuidMap.get(idx);
//                        if (virtualRootUuidMap.containsValue(allIdUuidMap.get(idx))) {
//                            virtualRootUuidMap.entrySet().stream().filter((x) -> {
//                                return uuid.equals(x.getValue());
//                            }).forEach((x) -> {
//                                virtualIds.add(allUuidIdMap.get(x.getKey()));
//                            });
//                        }
//
//                    });
//                    if (!virtualIds.isEmpty()) {
//                        idSet.addAll(virtualIds);
//                    }
//
//                    split = (String[])idSet.toArray(new String[0]);
//                }
//
//                for(int i = 0; i < split.length; ++i) {
//                    if (split[i] != null && !split[i].equals("") && split[i].length() > 0) {
//                        JSONObject tmpJson = new JSONObject();
//                        Node theNode = this.nodeDAO.getTheNode(Integer.parseInt(split[i]));
//                        if (theNode != null) {
//                            String uuid = theNode.getUuid();
//                            tmpJson.put("ip", theNode.getIp());
//
//                            try {
//                                String gatherId = theNode.getGatherId();
//                                if (gatherId != null) {
//                                    this.whaleDeviceProfile.deleteProfile(gatherId);
//                                }
//                            } catch (Exception var18) {
//                                logger.error(var18.getMessage(), var18);
//                            }
//
//                            Integer state = theNode.getState();
//                            logger.info("NodeState:" + state);
//                            if (state != null && state < 0) {
//                                tmpJson.put("result", false);
//                                tmpJson.put("msg", theNode.getIp() + "正常采集中，暂不可删除。请稍后重试");
//                                tmpArray.add(tmpJson);
//                            } else {
//                                Iterator var14;
//                                String filePath;
//                                File file;
//                                TopoNodeSynEntity nodeSyn;
//                                List paths;
//                                List textIds;
//                                Iterator var31;
//                                List nodeSynList;
//                                String textId;
//                                Iterator var34;
//                                if (StringUtils.isEmpty(uuid)) {
//                                    int id = Integer.parseInt(split[i]);
//                                    String ipById = this.nodeDAO.getIpById(id);
//                                    this.nodeDAO.deleteById(Integer.parseInt(split[i]));
//                                    paths = this.nodeHistoryDAO.getPathByIP(ipById);
//                                    var14 = paths.iterator();
//
//                                    while(var14.hasNext()) {
//                                        filePath = (String)var14.next();
//                                        file = new File(filePath);
//                                        if (file.exists()) {
//                                            file.delete();
//                                        }
//                                    }
//
//                                    textIds = this.nodeHistoryDAO.getTextIdByIp(ipById);
//                                    var31 = textIds.iterator();
//
//                                    while(var31.hasNext()) {
//                                        textId = (String)var31.next();
//                                        if (textId != null && textId.length() > 0) {
//                                            this.nodeHistoryTextDAO.deleteById(textId);
//                                        }
//                                    }
//
//                                    this.nodeHistoryDAO.deleteByIP(ipById);
//                                    nodeSynList = this.topoNodeSynMapper.findByIp(theNode.getIp());
//                                    if (CollectionUtils.isNotEmpty(nodeSynList)) {
//                                        var34 = nodeSynList.iterator();
//
//                                        while(var34.hasNext()) {
//                                            nodeSyn = (TopoNodeSynEntity)var34.next();
//                                            nodeSyn.setStatus(NodeSynStatusEnum.TO_BE_CONFIRMED.getCode());
//                                            nodeSyn.setUpdateDate(new Date());
//                                            this.topoNodeSynMapper.updateByPrimaryKey(nodeSyn);
//                                        }
//                                    }
//
//                                    tmpJson.put("result", true);
//                                    tmpJson.put("msg", "删除成功");
//                                    tmpArray.add(tmpJson);
//                                } else {
//                                    ResultRO deleteResultRO = this.whaleDeviceObjectClient.deleteDevice(uuid);
//                                    message = false;
//                                    if (deleteResultRO != null && !StringUtils.isEmpty(deleteResultRO.getMessage()) && deleteResultRO.getMessage().contains("Resource not found Device with uuid")) {
//                                        message = true;
//                                    }
//
//                                    if ((deleteResultRO == null || !deleteResultRO.getSuccess()) && !message) {
//                                        tmpJson.put("result", false);
//                                        tmpJson.put("msg", "删除失败");
//                                        if (deleteResultRO != null && !StringUtils.isEmpty(deleteResultRO.getMessage())) {
//                                            tmpJson.put("message", deleteResultRO.getMessage());
//                                        }
//
//                                        tmpArray.add(tmpJson);
//                                    } else {
//                                        this.deleteRouteTable(theNode.getIp());
//                                        this.nodeDAO.deleteById(Integer.parseInt(split[i]));
//                                        paths = this.nodeHistoryDAO.getPathByIP(theNode.getIp());
//                                        var14 = paths.iterator();
//
//                                        while(var14.hasNext()) {
//                                            filePath = (String)var14.next();
//                                            file = new File(filePath);
//                                            if (file.exists()) {
//                                                file.delete();
//                                            }
//                                        }
//
//                                        textIds = this.nodeHistoryDAO.getTextIdByIp(theNode.getIp());
//                                        logger.info("----------------" + textIds.toString());
//                                        var31 = textIds.iterator();
//
//                                        while(var31.hasNext()) {
//                                            textId = (String)var31.next();
//                                            logger.info("-------------------" + textId);
//                                            if (textId != null && textId.length() > 0) {
//                                                this.nodeHistoryTextDAO.deleteById(textId);
//                                            }
//                                        }
//
//                                        this.nodeHistoryDAO.deleteByIP(theNode.getIp());
//                                        nodeSynList = this.topoNodeSynMapper.findByIp(theNode.getIp());
//                                        if (CollectionUtils.isNotEmpty(nodeSynList)) {
//                                            var34 = nodeSynList.iterator();
//
//                                            while(var34.hasNext()) {
//                                                nodeSyn = (TopoNodeSynEntity)var34.next();
//                                                nodeSyn.setStatus(NodeSynStatusEnum.TO_BE_CONFIRMED.getCode());
//                                                nodeSyn.setUpdateDate(new Date());
//                                                this.topoNodeSynMapper.updateByPrimaryKey(nodeSyn);
//                                            }
//                                        }
//
//                                        tmpJson.put("result", true);
//                                        tmpJson.put("msg", "删除成功");
//                                        tmpJson.put("message", deleteResultRO.getMessage());
//                                        tmpArray.add(tmpJson);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                result.put("allDate", tmpArray);
//            } catch (NumberFormatException var19) {
//                logger.error(var19.getMessage(), var19);
//            }
//        }
//
//        return result;
//    }
//
//    public JSONObject batchImportNode(List<BatchImportNodeDTO> list, List<ImportCredentialDTO> credDataList, String userName) throws Exception {
//        logger.info("批量导入节点service begin");
//        JSONObject result = new JSONObject();
//        if (list != null && !list.isEmpty() || credDataList != null && !credDataList.isEmpty()) {
//            User user = this.umsUserService.findOne(userName);
//            String branchLevel;
//            String branchName;
//            if (user != null && org.apache.commons.lang3.StringUtils.isNotEmpty(user.getBranchLevel())) {
//                branchLevel = user.getBranchLevel();
//                branchName = user.getBranchName();
//            } else {
//                branchLevel = "00";
//                branchName = "总部";
//            }
//
//            Map<String, String> credUuidMap = new HashMap();
//            Iterator var28;
//            if (credDataList != null && !credDataList.isEmpty()) {
//                List<Thread> threadList = new ArrayList();
//                logger.info("凭证信息非空，开始入库");
//                Iterator var10 = credDataList.iterator();
//
//                while(var10.hasNext()) {
//                    ImportCredentialDTO dto = (ImportCredentialDTO)var10.next();
//                    final String fomatUrl = this.pushServerPrefix + "credential/create";
//                    final Map<String, String> map = new HashMap();
//                    map.put("name", dto.getName());
//                    map.put("loginName", dto.getLoginName());
//                    map.put("loginPassword", dto.getLoginPassword());
//                    if (!StringUtils.isEmpty(dto.getEnablePassword()) && !StringUtils.isEmpty(dto.getEnableUserName())) {
//                        map.put("enableUserName", dto.getEnableUserName());
//                        map.put("enablePassword", dto.getEnablePassword());
//                    }
//
//                    map.put("userName", userName);
//                    threadList.add(new Thread(new Runnable() {
//                        public void run() {
//                            NodeService.this.clientCredentialsOAuth2RestTemplate.postForObject(fomatUrl, map, JSONObject.class, new Object[0]);
//                        }
//                    }));
//                }
//
//                var10 = threadList.iterator();
//
//                Thread t;
//                while(var10.hasNext()) {
//                    t = (Thread)var10.next();
//                    t.start();
//                }
//
//                try {
//                    var10 = threadList.iterator();
//
//                    while(var10.hasNext()) {
//                        t = (Thread)var10.next();
//                        t.join();
//                    }
//                } catch (Exception var23) {
//                    logger.error("线程异常: " + var23.getMessage());
//                }
//
//                logger.info("凭证信息非空，入库完成");
//                List<CredentialEntity> credPoList = this.nodeExcelService.getAllCredential(branchLevel);
//                var28 = credPoList.iterator();
//
//                while(var28.hasNext()) {
//                    CredentialEntity po = (CredentialEntity)var28.next();
//                    credUuidMap.put(po.getName(), po.getUuid());
//                }
//            }
//
//            if (list != null && !list.isEmpty()) {
//                String version = "0";
//                List<Node> waitSaveList = new ArrayList();
//                var28 = list.iterator();
//
//                while(var28.hasNext()) {
//                    BatchImportNodeDTO dto = (BatchImportNodeDTO)var28.next();
//                    if (dto.getIncludeRouting() == null) {
//                        dto.setIncludeRouting(true);
//                    }
//
//                    if (dto.getCredentialRefExcel() != null && dto.getCredentialRefExcel()) {
//                        String uuid = (String)credUuidMap.get(dto.getCredentialName());
//                        if (StringUtils.isEmpty(uuid)) {
//                            throw new RuntimeException("导入的设备有引用excel中的凭证，但无法获取到该凭证的uuid, name=" + dto.getCredentialName());
//                        }
//
//                        dto.setCredentialUuid(uuid);
//                    }
//
//                    new Node();
//                    Integer portNumber = null;
//                    if (!StringUtils.isEmpty(dto.getPortNumber())) {
//                        portNumber = Integer.valueOf(dto.getPortNumber());
//                    }
//
//                    Integer timeOut = 180;
//                    if (!StringUtils.isEmpty(dto.getTimeout())) {
//                        timeOut = Integer.valueOf(dto.getTimeout());
//                    }
//
//                    boolean isSxf = false;
//                    if (dto.getVendorId().equalsIgnoreCase(VendorEnum.SHENXINFU.getCode())) {
//                        isSxf = true;
//                    }
//
//                    Node tmpNode = this.nodeDAO.getTheNodeByIP(dto.getIp());
//                    CreateProfileRO createProfileRO = this.structureJson(dto.getVendorType(), dto.getVersionId(), dto.getVendorVersion(), (String)null, dto.getControllerId(), portNumber, timeOut, dto.getCredentialUuid(), dto.getDeviceTemplateId(), dto.getIp(), dto.getPluginId(), dto.getVendorId(), version, (String)null, dto.getIncludeRouting(), dto.getWebUrl(), isSxf, dto.getCharset(), (String)null);
//                    logger.info("批量导入节点时，根据参数创建的采集配置信息,createProfileRO:{}", JSON.toJSONString(createProfileRO));
//                    String gatherId = null;
//                    ControllerSettingsRO controllerSettings = null;
//                    if (tmpNode != null && !StringUtils.isEmpty(tmpNode.getGatherId())) {
//                        logger.error("当前ip:{}在系统中已存在，更新采集ID后，会造成采集失败的情况", dto.getIp());
//                        throw new RuntimeException("管理IP已存在，不可重复导入");
//                    }
//
//                    logger.info("设备不存在，创建采集配置信息，开始");
//                    ResultRO<List<CreateProfileRO>> resultRO = this.whaleDeviceProfile.createConfig(createProfileRO);
//                    if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && ((List)resultRO.getData()).size() > 0) {
//                        CreateProfileRO data = (CreateProfileRO)((List)resultRO.getData()).get(0);
//                        gatherId = data.getId();
//                        logger.info("获取采集配置信息，gatherId:{}", gatherId);
//                        if (data.getControllerSettings() != null && data.getControllerSettings().size() > 0) {
//                            controllerSettings = (ControllerSettingsRO)data.getControllerSettings().get(0);
//                        }
//                    } else {
//                        logger.error("批量导入节点时，获取在线采集配置错误，返回空, createProfileRO:{}", JSON.toJSONString(createProfileRO));
//                    }
//
//                    if (StringUtils.isEmpty(gatherId)) {
//                        logger.error("创建采集配置失败，采集ID为空，当前设备ip:{}", dto.getIp());
//                        throw new RuntimeException("采集id为空，当前设备IP:" + dto.getIp());
//                    }
//
//                    Node node = this.constructNode(tmpNode, dto.getControllerId(), portNumber, timeOut, dto.getCredentialUuid(), dto.getDeviceTemplateId(), dto.getIp(), dto.getPluginId(), dto.getVendorId(), version, dto.getIncludeRouting(), dto.getDeviceName(), dto.getTypeCode(), dto.getVendorName(), dto.getModelNumber(), dto.getDescription(), -1, (String)null, gatherId, dto.getWebUrl(), dto.getBranchLevel(), (Integer)null, dto.getCharset(), (String)null, (String)null);
//                    node = this.predefindOrCustom(node, controllerSettings);
//                    node.setBranchLevel(branchLevel);
//                    node.setBranchName(branchName);
//                    waitSaveList.add(node);
//                }
//
//                if (!waitSaveList.isEmpty()) {
//                    logger.info("采集配置生成完成，对象封装完成，批量入库");
//                    this.nodeRepository.saveAll(waitSaveList);
//                    logger.info("批量入库结束");
//                }
//
//                logger.info("批量导入节点service end");
//                result.put("result", true);
//                return result;
//            } else {
//                logger.warn("本次导入没有设备信息");
//                result.put("result", true);
//                return result;
//            }
//        } else {
//            result.put("result", false);
//            result.put("msg", "参数为空");
//            return result;
//        }
//    }
//
//    public JSONObject addGatherNode(String type, String id, String version1, String customPythonPath, String controllerId, Integer portNumber, Integer timeout, String credentialUuid, String deviceTemplateId, String hostAddress, String pluginId, String vendorId, String version2, String deviceName, String deviceType, String deviceVendor, String gatherCycle, String modelNumber, String description, Integer nodeGroup, Boolean includeRouting, String captchaCode, String branchLevel, Integer isChangeWarn, String webUrl, String charset, String pushCredentialUuid, String authorizationCode, String webHref) {
//        JSONObject result = new JSONObject();
//
//        try {
//            if (this.savedNode == null) {
//                logger.warn("参数savedNode为空, 如果是深信服防火墙，该参数为空时,异常，非深信服，可忽略");
//            }
//
//            Node save = new Node();
//            Node node = new Node();
//            logger.info("厂商,vendorId:{},deviceType:{}", vendorId, deviceType);
//            boolean isSxfFlag = false;
//            List nodes;
//            Boolean ifSuccess;
//            if (this.isShenxinfuFwWeb(vendorId, deviceType, modelNumber)) {
//                nodes = this.nodeDAO.getAllDeviceUuid();
//                ifSuccess = nodes.stream().filter((nodee) -> {
//                    return nodee.getWebUrl() != null;
//                }).anyMatch((nodee) -> {
//                    return nodee.getWebUrl().equals(this.savedNode.getWebUrl());
//                });
//                if (ifSuccess) {
//                    result.put("result", false);
//                    result.put("msg", "该WEBURL与已有的重复");
//                    return result;
//                }
//
//                this.savedNode.setBranchLevel(branchLevel);
//                this.savedNode.setIsChangeWarn(isChangeWarn);
//                save = (Node)this.nodeRepository.save(this.savedNode);
//                result.put("result", true);
//                result.put("msg", "添加成功");
//            } else {
//                if (this.isShenxinfuFwApi(vendorId, deviceType, modelNumber)) {
//                    nodes = this.nodeDAO.getAllDeviceUuid();
//                    ifSuccess = nodes.stream().filter((nodee) -> {
//                        return nodee.getWebUrl() != null;
//                    }).anyMatch((nodee) -> {
//                        return nodee.getWebUrl().equals(webUrl);
//                    });
//                    if (ifSuccess) {
//                        result.put("result", false);
//                        result.put("msg", "该WEBURL与已有的重复");
//                        return result;
//                    }
//
//                    isSxfFlag = true;
//                }
//
//                if (VendorEnum.SHENXINFU.getCode().equals(vendorId) && NodeType.EDR.getCode().equals(deviceType)) {
//                    isSxfFlag = true;
//                }
//
//                includeRouting = includeRouting == null ? true : includeRouting;
//                Node tmpNode = this.nodeDAO.getTheNodeByIP(hostAddress);
//                CreateProfileRO createProfileRO = this.structureJson(type, id, version1, customPythonPath, controllerId, portNumber, timeout, credentialUuid, deviceTemplateId, hostAddress, pluginId, vendorId, version2, (String)null, includeRouting, webUrl, isSxfFlag, charset, authorizationCode);
//                logger.info("jsonStr:" + JSON.toJSONString(createProfileRO));
//                String gatherId = null;
//                ControllerSettingsRO controllerSettings = null;
//                ResultRO resultRO;
//                if (tmpNode != null && !StringUtils.isEmpty(tmpNode.getGatherId())) {
//                    resultRO = this.whaleDeviceProfile.pullConfigAsync(tmpNode.getGatherId());
//                    if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && ((List)resultRO.getData()).size() > 0) {
//                        DeviceProfileRO data = (DeviceProfileRO)((List)resultRO.getData()).get(0);
//                        gatherId = data.getId();
//                        if (data.getControllerSettings() != null && data.getControllerSettings().size() > 0) {
//                            controllerSettings = (ControllerSettingsRO)data.getControllerSettings().get(0);
//                        }
//                    }
//                } else {
//                    resultRO = this.whaleDeviceProfile.createConfig(createProfileRO);
//                    if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && ((List)resultRO.getData()).size() > 0) {
//                        CreateProfileRO data = (CreateProfileRO)((List)resultRO.getData()).get(0);
//                        gatherId = data.getId();
//                        if (data.getControllerSettings() != null && data.getControllerSettings().size() > 0) {
//                            controllerSettings = (ControllerSettingsRO)data.getControllerSettings().get(0);
//                        }
//                    } else {
//                        logger.error("获取在线采集配置错误，返回空, createProfileRO:{}", JSON.toJSONString(createProfileRO));
//                    }
//                }
//
//                if (!StringUtils.isEmpty(gatherId)) {
//                    node = this.constructNode(tmpNode, controllerId, portNumber, timeout, credentialUuid, deviceTemplateId, hostAddress, pluginId, vendorId, version2, includeRouting, deviceName, deviceType, deviceVendor, modelNumber, description, nodeGroup, gatherCycle, gatherId, webUrl, branchLevel, isChangeWarn, charset, pushCredentialUuid, webHref);
//                    node = this.predefindOrCustom(node, controllerSettings);
//                    logger.info("节点信息完成，即将入库");
//                    save = (Node)this.nodeRepository;
//                    result.put("nodeId", node.getId());
//                    result.put("result", true);
//                    result.put("msg", "添加成功");
//                } else {
//                    result.put("result", false);
//                    result.put("msg", "添加失败");
//                }
//            }
//
//            logger.info("添加采集信息后，立即去采集, nodeId:{}", save.getId());
//            String[] ids = new String[]{String.valueOf(save.getId())};
//            ifSuccess = this.doGatherTask(ids, captchaCode);
//            if (ifSuccess) {
//                result.put("gatherMsg", "采集成功");
//            } else {
//                result.put("gatherMsg", "采集失败");
//                this.sendAlarmEmail(gatherCycle, node);
//            }
//        } catch (Exception var40) {
//            logger.error(var40.getMessage(), var40);
//        }
//
//        return result;
//    }
//
//    private Node constructNode(Node tmpNode, String controllerId, Integer portNumber, Integer timeout, String credentialUuid, String deviceTemplateId, String hostAddress, String pluginId, String vendorId, String version, Boolean includeRouting, String deviceName, String type, String deviceVendor, String modelNumber, String description, Integer nodeGroup, String gatherCycle, String gatherId, String webUrl, String branchLevel, Integer isChangeWarn, String charset, String pushCredentialUuid, String webHref) {
//        Node node = new Node();
//        node.setVendorName(deviceVendor);
//        node.setVendorId(vendorId);
//        node.setGatherId(gatherId);
//        if (org.apache.commons.lang3.StringUtils.isEmpty(deviceName)) {
//            deviceName = hostAddress;
//        }
//
//        node.setDeviceName(deviceName);
//        node.setType(type);
//        node.setIp(hostAddress);
//        node.setDeviceTemplateId(deviceTemplateId);
//        node.setCredentialUuid(credentialUuid);
//        node.setPushCredentialUuid(pushCredentialUuid);
//        node.setControllerId(controllerId);
//        node.setPortNumber(portNumber);
//        node.setTimeout(timeout);
//        node.setVersion(version);
//        node.setOrigin(2);
//        node.setNodeGroup(nodeGroup);
//        node.setPluginId(pluginId);
//        node.setGatherCycle(gatherCycle);
//        node.setModelNumber(modelNumber);
//        node.setDescription(description);
//        node.setIncludeRouting(includeRouting);
//        node.setWebUrl(webUrl);
//        node.setWebHref(webHref);
//        if (tmpNode != null) {
//            node.setId(tmpNode.getId());
//            node.setUuid(tmpNode.getUuid());
//            node.setProbeIp(tmpNode.getProbeIp());
//            node.setProbeToken(tmpNode.getProbeToken());
//        }
//
//        if (branchLevel != null) {
//            node.setBranchLevel(branchLevel);
//        }
//
//        if (isChangeWarn != null) {
//            node.setIsChangeWarn(isChangeWarn);
//        } else {
//            node.setIsChangeWarn(0);
//        }
//
//        if (!StringUtils.isEmpty(charset)) {
//            node.setCharset(charset);
//        }
//
//        return node;
//    }
//
//    private Boolean doGatherTask(String[] ids, String captchaCode) {
//        JSONObject jsonObject = this.doGather(ids, captchaCode);
//        return jsonObject.getJSONArray("Alldata").getJSONObject(0).getBoolean("result");
//    }
//
//    public JSONObject doGather(String[] ids, String captchaCode) {
//        JSONObject result = new JSONObject();
//        JSONArray tmpArray = new JSONArray();
//
//        try {
//            String[] var5 = ids;
//            int var6 = ids.length;
//
//            for(int var7 = 0; var7 < var6; ++var7) {
//                String id = var5[var7];
//                int integerId = Integer.parseInt(id);
//                Node theNode = this.nodeDAO.getTheNode(Integer.parseInt(id));
//                int state = theNode.getState();
//                String gid = theNode.getGatherId();
//                this.gatherId = gid;
//                if (state != -1 && state != -2) {
//                    String deviceUuid = theNode.getUuid();
//                    if (deviceUuid != null && !"".equals(deviceUuid)) {
//                        DeviceRO deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(deviceUuid);
//                        if (deviceRO != null && deviceRO.getSuccess() && deviceRO.getData() != null && deviceRO.getData().size() > 0) {
//                            DeviceDataRO dataRO = (DeviceDataRO)deviceRO.getData().get(0);
//                            Boolean isVsys = dataRO.getIsVsys();
//                            if (isVsys != null) {
//                                continue;
//                            }
//                        }
//                    }
//
//                    JSONObject parseResult;
//                    if (this.isShenxinfuFwWeb(theNode.getVendorId(), theNode.getType(), theNode.getModelNumber())) {
//                        parseResult = this.doGatherForSxf(Integer.valueOf(id), captchaCode, theNode);
//                    } else {
//                        ResultRO<List<DeviceProfileRO>> profileRO = this.whaleDeviceProfile.pullConfigAsync(gid);
//                        parseResult = this.parseJsonObject(profileRO, gid, theNode, integerId);
//                    }
//
//                    tmpArray.add(parseResult);
//                } else {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("result", true);
//                    jsonObject.put("msg", "采集中，请耐心等待...");
//                    logger.info("节点采集ID：(" + gid + ")正在采集，请等待...");
//                    tmpArray.add(jsonObject);
//                }
//            }
//
//            result.put("result", true);
//            result.put("Alldata", tmpArray);
//        } catch (Exception var17) {
//            logger.error(var17.getMessage(), var17);
//        }
//
//        return result;
//    }
//
//    private JSONObject parseJsonObject(ResultRO<List<DeviceProfileRO>> profileRO, String gid, Node theNode, Integer integerId) {
//        JSONObject result = new JSONObject();
//        if (profileRO != null && profileRO.getSuccess() && profileRO.getData() != null && ((List)profileRO.getData()).size() > 0) {
//            DeviceProfileRO profile = (DeviceProfileRO)((List)profileRO.getData()).get(0);
//            if (!profile.getStatus().equalsIgnoreCase("ERROR")) {
//                this.nodeDAO.updateCreateTime(gid);
//                this.nodeDAO.updateNodeStateBy(gid, -1);
//                this.nodeDAO.updateNodeTaskTypeUuid(gid, profile.getTaskType(), profile.getUuid());
//                result.put("result", true);
//                result.put("msg", "采集中，请耐心等待...");
//                logger.info("节点采集ID：(" + gid + ")正在采集，请等待...");
//                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.SYNTHESIZE_CONFIGURE.getId(), "节点配置-设备" + theNode.getDeviceName() + "(" + theNode.getIp() + ")触发采集了。");
//            } else {
//                this.nodeDAO.updateCreateTime(gid);
//                this.nodeDAO.updateNodeStateBy(gid, 2);
//                String ipById = this.nodeDAO.getIpById(integerId);
//                NodeHistory nodeHistory = new NodeHistory();
//                nodeHistory.setIp(ipById);
//                nodeHistory.setCreatedTime(theNode.getCreatedTime());
//                Integer version = this.nodeHistoryDAO.getCurrentVersionByIP(ipById);
//                if (version != null) {
//                    nodeHistory.setVersion(version + 1);
//                }
//
//                String msg = "调用whale接口1失败";
//                nodeHistory.setState(2);
//                nodeHistory.setMsg(msg);
//                if (msg.contains("Failed to pull running config from device profile")) {
//                    (new StringBuilder()).append("设备连接失败,").append(msg).toString();
//                } else if (msg.contains("Resource not found Device profile not found")) {
//                    (new StringBuilder()).append("解析错误！找不到资源设备配置文件,").append(msg).toString();
//                } else {
//                    (new StringBuilder()).append("其他未知异常引起,").append(msg).toString();
//                }
//
//                this.restrictSumHistory(ipById);
//                this.nodeHistoryRepository.save(nodeHistory);
//                logger.info("节点采集ID：(" + gid + ")采集失败");
//                result.put("result", false);
//                result.put("msg", "调用whale接口1失败");
//            }
//        } else {
//            this.nodeDAO.updateNodeStateBy(gid, 2);
//            String ipById = this.nodeDAO.getIpById(integerId);
//            NodeHistory nodeHistory = new NodeHistory();
//            nodeHistory.setCreatedTime(theNode.getCreatedTime());
//            nodeHistory.setIp(ipById);
//            Integer version = this.nodeHistoryDAO.getCurrentVersionByIP(ipById);
//            if (version != null) {
//                nodeHistory.setVersion(version + 1);
//            }
//
//            nodeHistory.setState(2);
//            nodeHistory.setMsg("采集失败");
//            this.restrictSumHistory(ipById);
//            this.nodeHistoryRepository.save(nodeHistory);
//            logger.info("节点采集ID：(" + gid + ")采集失败");
//            result.put("result", false);
//            result.put("msg", "whale接口调用失败");
//        }
//
//        return result;
//    }
//
//    private void updateSessionMonitorConfiguration(String[] ids) {
//        List<Node> nodes = this.nodeDAO.getNodesByIds(Arrays.asList(ids));
//        int withProbeCount = 0;
//        Iterator var4 = nodes.iterator();
//
//        while(var4.hasNext()) {
//            Node node = (Node)var4.next();
//            if (node.getProbeIp() != null) {
//                ++withProbeCount;
//            }
//        }
//
//        if (withProbeCount != 0) {
//            ExecutorService executorService = Executors.newFixedThreadPool(withProbeCount);
//            Iterator var8 = nodes.iterator();
//
//            while(var8.hasNext()) {
//                Node node = (Node)var8.next();
//                if (node.getProbeIp() != null) {
//                    executorService.submit(() -> {
//                        DeviceRO deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(node.getUuid());
//                        DeviceDataRO deviceDataRO = (DeviceDataRO)deviceRO.getData().get(0);
//                        List<InterfacesRO> interfaces = deviceDataRO.getInterfaces();
//                        List<NodeProbeConfig> nodeProbeConfigs = this.nodeProbeConfigMapper.listByDeviceId(Integer.parseInt(node.getId()));
//                        Iterator var6 = nodeProbeConfigs.iterator();
//
//                        while(true) {
//                            while(var6.hasNext()) {
//                                NodeProbeConfig probeConfig = (NodeProbeConfig)var6.next();
//                                Iterator var8 = interfaces.iterator();
//
//                                while(var8.hasNext()) {
//                                    InterfacesRO obj = (InterfacesRO)var8.next();
//                                    if (probeConfig.getInterfaceName().startsWith(obj.getName())) {
//                                        probeConfig.setInterfaceUuid(obj.getUuid());
//                                        break;
//                                    }
//                                }
//                            }
//
//                            this.nodeProbeConfigMapper.updateList(nodeProbeConfigs);
//                            return;
//                        }
//                    });
//                }
//            }
//        }
//
//    }
//
//    @Transactional
//    public JSONObject updateNode(Integer nodeId, String deviceName, Integer origin, String type, String id, String version1, String customPythonPath, String controllerId, Integer portNumber, Integer timeout, String credentialUuid, String deviceTemplateId, String hostAddress, String pluginId, String vendorId, String version2, String deviceType, String vendorName, String gatherCycle, String gatherId, String modelNumber, String description, Integer nodeGroup, Boolean includeRouting, String webUrl, String branchLevel, Integer isChangeWarn, String charset, String pushCredentialUuid, String authorizationCode, String webHref) {
//        JSONObject result = new JSONObject();
//
//        try {
//            logger.info("修改节点信息时，入参,vendorId:{}, deviceType:{}", vendorId, deviceType);
//            if (this.isShenxinfuFw(vendorId, deviceType)) {
//                List<Node> nodes = this.nodeDAO.getAllDeviceUuid();
//                Boolean ifExist = nodes.stream().filter((nodee) -> {
//                    return !nodee.getId().equals(String.valueOf(nodeId));
//                }).filter((nodee) -> {
//                    return nodee.getWebUrl() != null;
//                }).anyMatch((nodee) -> {
//                    return nodee.getWebUrl().equals(webUrl);
//                });
//                if (ifExist) {
//                    result.put("result", false);
//                    result.put("msg", "该WEBURL与已有的重复");
//                    return result;
//                }
//            }
//
//            includeRouting = includeRouting == null ? true : includeRouting;
//            Node node = this.nodeDAO.getTheNode(nodeId);
//            if (node == null) {
//                result.put("result", false);
//                result.put("msg", "id不存在");
//                return result;
//            }
//
//            String beforeDeviceName = node.getDeviceName();
//            Integer state = node.getState();
//            if (state != null && state < 0) {
//                result.put("result", false);
//                result.put("msg", node.getIp() + "正常采集中，暂不可修改。请稍后重试");
//                return result;
//            }
//
//            if (origin == 1) {
//                Integer flog = this.nodeDAO.updateNode(nodeId, deviceName, origin, modelNumber, description, nodeGroup, branchLevel, isChangeWarn);
//                if (flog <= 0) {
//                    result.put("result", false);
//                    result.put("msg", "修改失败，更新设备基本信息异常");
//                    return result;
//                }
//
//                boolean nodeFieldFlag = this.updateNodeField(node, origin, beforeDeviceName, deviceName, branchLevel, isChangeWarn, modelNumber, description, nodeGroup);
//                if (!nodeFieldFlag) {
//                    result.put("result", false);
//                    result.put("msg", "修改失败，同步更新设备名称或虚墙业务时，操作异常");
//                    return result;
//                }
//
//                result.put("result", true);
//                result.put("msg", "修改成功");
//                return result;
//            }
//
//            if (origin == 2) {
//                CreateProfileRO createProfileRO = this.structureJson(type, id, version1, customPythonPath, controllerId, portNumber, timeout, credentialUuid, deviceTemplateId, hostAddress, pluginId, vendorId, version2, gatherId, includeRouting, webUrl, false, charset, authorizationCode);
//                if (this.isShenxinfuFw(vendorId, deviceType) || VendorEnum.SHENXINFU.getCode().equals(vendorId) && NodeType.EDR.getCode().equals(deviceType)) {
//                    createProfileRO = this.structureJson(type, id, version1, customPythonPath, controllerId, portNumber, timeout, credentialUuid, deviceTemplateId, hostAddress, pluginId, vendorId, version2, gatherId, includeRouting, webUrl, true, charset, authorizationCode);
//                }
//
//                ResultRO<List<CreateProfileRO>> resultRO = this.whaleDeviceProfile.updateProfile(gatherId, createProfileRO);
//                if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && !((List)resultRO.getData()).isEmpty()) {
//                    CreateProfileRO data = (CreateProfileRO)((List)resultRO.getData()).get(0);
//                    ControllerSettingsRO controllerSettingsRO = null;
//                    if (data.getControllerSettings() != null && data.getControllerSettings().size() > 0) {
//                        controllerSettingsRO = (ControllerSettingsRO)data.getControllerSettings().get(0);
//                    }
//
//                    String version = data.getVersion().toString();
//                    node = this.predefindOrCustom(node, controllerSettingsRO);
//                    node.setCreatedTime(new Date());
//                    node.setVendorName(vendorName);
//                    node.setVendorId(vendorId);
//                    node.setGatherId(gatherId);
//                    node.setDeviceName(deviceName);
//                    node.setDeviceTemplateId(deviceTemplateId);
//                    node.setPluginId(pluginId);
//                    node.setType(deviceType);
//                    node.setIp(hostAddress);
//                    node.setCredentialUuid(credentialUuid);
//                    node.setPushCredentialUuid(pushCredentialUuid);
//                    node.setControllerId(controllerId);
//                    node.setPortNumber(portNumber);
//                    node.setTimeout(timeout);
//                    node.setCharset(charset);
//                    node.setOrigin(2);
//                    node.setVersion(version);
//                    node.setNodeGroup(nodeGroup);
//                    node.setGatherCycle(gatherCycle);
//                    node.setModelNumber(modelNumber);
//                    node.setDescription(description);
//                    node.setIncludeRouting(includeRouting);
//                    node.setWebUrl(webUrl);
//                    node.setBranchLevel(branchLevel);
//                    node.setIsChangeWarn(isChangeWarn);
//                    node.setWebHref(webHref);
//                    this.nodeRepository.save(node);
//                    boolean nodeFieldFlag = this.updateNodeField(node, origin, beforeDeviceName, deviceName, branchLevel, isChangeWarn, modelNumber, description, nodeGroup);
//                    if (!nodeFieldFlag) {
//                        result.put("result", false);
//                        result.put("msg", "修改失败，同步更新设备名称或虚墙业务时，操作异常");
//                        return result;
//                    }
//
//                    result.put("result", true);
//                    result.put("msg", "修改成功");
//                    return result;
//                }
//
//                result.put("result", false);
//                result.put("msg", "修改失败，更新设备的采集配置时，返回空");
//                return result;
//            }
//        } catch (Exception var42) {
//            logger.error(var42.getMessage(), var42);
//        }
//
//        return result;
//    }
//
//    private boolean updateNodeField(Node theNode, Integer origin, String beforeDeviceName, String deviceName, String branchLevel, Integer isChangeWarn, String modelNumber, String description, Integer nodeGroup) {
//        boolean resultFlag = true;
//
//        try {
//            boolean needUpdateName = false;
//            if (!StringUtils.isEmpty(theNode.getUuid()) && !beforeDeviceName.equals(deviceName)) {
//                needUpdateName = true;
//                logger.info("编辑节点时,设备名称产生变化,before:{},after:{}", beforeDeviceName, deviceName);
//                this.whaleDeviceObjectClient.addAlias(theNode.getUuid(), deviceName, theNode.getType());
//                logger.info("同步修改名称，完成");
//            }
//
//            List<Node> vsysList = this.nodeDAO.listVsysDetailByIp(theNode.getIp());
//            if (vsysList != null && !vsysList.isEmpty()) {
//                logger.info("设备有虚墙，size:{}，开始处理虚墙业务", vsysList.size());
//            } else {
//                logger.info("设备无虚墙，跳过虚墙业务");
//            }
//
//            List<Integer> ids = new ArrayList();
//
//            Node current;
//            for(Iterator var14 = vsysList.iterator(); var14.hasNext(); this.nodeDAO.updateNode(Integer.valueOf(current.getId()), deviceName, origin, modelNumber, description, nodeGroup, branchLevel, isChangeWarn)) {
//                current = (Node)var14.next();
//                ids.add(Integer.valueOf(current.getId()));
//                logger.info("同步修改虚墙信息：名称、厂商、型号、组织机构、变更告警 开始, ip:{}", current.getIp());
//                if (needUpdateName) {
//                    this.whaleDeviceObjectClient.addAlias(current.getUuid(), deviceName, theNode.getType());
//                }
//            }
//
//            logger.info("虚墙业务处理结束");
//        } catch (Exception var16) {
//            logger.error("修改节点其它属性字段时异常", var16);
//            resultFlag = false;
//        }
//
//        return resultFlag;
//    }
//
//    public Map<String, Object> updateNodeSkipAnalysis(Integer[] nodeIds, Boolean skipAnalysis) {
//        Map<String, Object> result = new HashMap();
//        String msg = "设定设备是否跳过路径分析成功:";
//        Integer[] var5 = nodeIds;
//        int var6 = nodeIds.length;
//
//        for(int var7 = 0; var7 < var6; ++var7) {
//            Integer nodeId = var5[var7];
//            Node theNode = this.nodeDAO.getTheNode(nodeId);
//            if (theNode != null && org.apache.commons.lang3.StringUtils.isNotEmpty(theNode.getUuid())) {
//                logger.info("设定设备是否跳过路径分析");
//                SkipAnalysisRO skipAnalysisRO = this.whaleDeviceObjectClient.skipAnalysis(theNode.getUuid(), skipAnalysis);
//                if (!skipAnalysisRO.getSuccess()) {
//                    result.put("result", false);
//                    result.put("msg", msg + "设定设备是否跳过路径分析失败:" + theNode.getIp());
//                    return result;
//                }
//
//                this.nodeDAO.updateNodeSkipAnalysis(nodeId, String.valueOf(skipAnalysis));
//                msg = msg + theNode.getIp() + ",";
//            }
//        }
//
//        result.put("result", true);
//        result.put("msg", msg.substring(0, msg.length() - 1));
//        return result;
//    }
//
//    public Map<String, Object> updateNodeToSameInbound(Integer[] nodeIds, Boolean toSameInbound) {
//        Map<String, Object> result = new HashMap();
//        String msg = "设定设备是否允许数据流同进同出成功:";
//        Integer[] var5 = nodeIds;
//        int var6 = nodeIds.length;
//
//        for(int var7 = 0; var7 < var6; ++var7) {
//            Integer nodeId = var5[var7];
//            Node theNode = this.nodeDAO.getTheNode(nodeId);
//            if (theNode != null && org.apache.commons.lang3.StringUtils.isNotEmpty(theNode.getUuid())) {
//                logger.info("设定设备是否允许数据流同进同出");
//                ToSameInboundRO toSameInboundRO = this.whaleDeviceObjectClient.trafficFlowToSameInbound(theNode.getUuid(), toSameInbound);
//                if (!toSameInboundRO.getSuccess()) {
//                    result.put("result", false);
//                    result.put("msg", msg + "设定设备是否允许数据流同进同出失败:" + theNode.getIp());
//                    return result;
//                }
//
//                this.nodeDAO.updateNodeToSameInbound(nodeId, String.valueOf(toSameInbound));
//                msg = msg + theNode.getIp() + ",";
//            }
//        }
//
//        result.put("result", true);
//        result.put("msg", msg.substring(0, msg.length() - 1));
//        return result;
//    }
//
//    public Map<String, Object> updateNodeLayerTwoDevice(Integer[] nodeIds, Boolean layerTwoDevice) {
//        Map<String, Object> result = new HashMap();
//        String msg = "设定设备是否为二层设备:";
//        Integer[] var5 = nodeIds;
//        int var6 = nodeIds.length;
//
//        for(int var7 = 0; var7 < var6; ++var7) {
//            Integer nodeId = var5[var7];
//            Node theNode = this.nodeDAO.getTheNode(nodeId);
//            if (theNode != null && org.apache.commons.lang3.StringUtils.isNotEmpty(theNode.getUuid())) {
//                logger.info("设定设备是否为二层设备");
//                LayerTwoDeviceRO layerTwoDeviceRO = this.whaleDeviceObjectClient.layerTwoDevice(theNode.getUuid(), layerTwoDevice);
//                if (!layerTwoDeviceRO.getSuccess()) {
//                    result.put("result", false);
//                    result.put("msg", msg + "设定设备是否为二层设备失败:" + theNode.getIp());
//                    return result;
//                }
//
//                this.nodeDAO.updateNodeLayerTwoDevice(nodeId, String.valueOf(layerTwoDevice));
//                msg = msg + theNode.getIp() + ",";
//            }
//        }
//
//        result.put("result", true);
//        result.put("msg", msg.substring(0, msg.length() - 1));
//        return result;
//    }
//
//    private void sendAlarmEmail(String cycleId, Node node) {
//        if (node.getIsChangeWarn() != null && node.getIsChangeWarn() == 1) {
//            Map branchNameMap = this.branchService.getBranchNameMap((String)null);
//
//            try {
//                List<User> userList = this.userRepository.findAll();
//                String toAddress = "";
//                Iterator var6 = userList.iterator();
//
//                while(var6.hasNext()) {
//                    User user = (User)var6.next();
//                    if (!StringUtils.isEmpty(user.getEmail())) {
//                        toAddress = toAddress + "," + user.getEmail();
//                    }
//                }
//
//                if (!StringUtils.isEmpty(toAddress)) {
//                    Date currentdate = new Date();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String subject = "设备采集失败告警[" + node.getIp() + "][" + sdf.format(currentdate) + "]";
//                    String content = "设备采集失败，请管理员注意查阅确认，设备信息如下：<br/>设备信息：<br/>";
//                    content = content + "<table border=\"1\" style=\"width: 100%;    font-size: 12px;    border: 1px solid #eee;    text-align: center;    border-collapse: collapse;    border-spacing: 1;    border-spacing: 0;\">    <thead style=\"background: #c6ddf7\">      <tr>        <th style=\"padding: 5px 0\">设备类型</th>        <th>设备管理IP</th>        <th>设备名称</th>        <th>所属组织机构</th>        <th>品牌</th>        <th>型号</th>        <th>最近采集时间</th>      </tr>    </thead>    <tbody>      <tr>        <td style=\"padding: 5px 0\">" + ("0".equals(node.getType()) ? "防火墙" : ("1".equals(node.getType()) ? "路由/交换" : "负载均衡")) + "</td>        <td>" + node.getIp() + "</td>        <td>" + node.getDeviceName() + "</td>        <td>" + (String)branchNameMap.get(node.getBranchLevel()) + "</td>        <td>" + node.getVendorName() + "</td>        <td>" + node.getModelNumber() + "</td>        <td>" + sdf.format(currentdate) + "</td>      </tr>    </tbody>  </table>";
//                    logger.info("设备采集失败发送到邮箱，subject:{},toAddress:{}", subject, toAddress.substring(1));
//                    MailServerConfDTO emialDTO = this.paramMapper.findEmailParam("mailServer");
//                    if (emialDTO != null) {
//                        CustomMailTool.sendEmail(emialDTO, toAddress.substring(1), subject, content, new String[0]);
//                    } else {
//                        logger.error("设备采集失败查询系统邮箱配置，返回空");
//                    }
//                }
//            } catch (Exception var11) {
//                logger.error("设备采集失败，发送邮件失败", var11);
//            }
//        }
//
//    }
//
//    public CreateProfileRO structureJson(String type, String id, String version1, String customPythonPath, String controllerId, Integer portNumber, Integer timeout, String credentialUuid, String deviceTemplateId, String hostAddress, String pluginId, String vendorId, String version2, String gatherId, Boolean includeRouting, String webUrl, boolean isSXF, String charset, String authorizationCode) {
//        MiscParamsRO miscParams = new MiscParamsRO();
//        if (!StringUtils.isEmpty(charset)) {
//            miscParams.setCHARSET(charset);
//        }
//
//        ArrayList settingsROList;
//        ControllerSettingsRO settingsRO;
//        if (customPythonPath != null && customPythonPath != "") {
//            logger.info("自定义采集,customPythonPath:{}", customPythonPath);
//            settingsROList = new ArrayList();
//            settingsRO = new ControllerSettingsRO();
//            settingsRO.setPortNumber(portNumber);
//            settingsRO.setTimeout(timeout);
//            settingsRO.setCustomPythonPath(customPythonPath);
//            settingsRO.setCredentialUuid(credentialUuid);
//            if (isSXF && !StringUtils.isEmpty(gatherId)) {
//                settingsRO.setWebuiUrl(webUrl);
//            } else if (isSXF && !StringUtils.isEmpty(webUrl)) {
//                settingsRO.setWebuiUrl(webUrl);
//            } else if (webUrl != null && !"".equals(webUrl)) {
//                if (!VendorEnum.CHECKPOINT.getCode().equalsIgnoreCase(vendorId) && !VendorEnum.PALOALTO.getCode().equalsIgnoreCase(vendorId)) {
//                    miscParams.setURL(webUrl);
//                } else {
//                    miscParams.setCHECKPOINT_MGMT_SERVER(webUrl);
//                }
//            }
//
//            if (!StringUtils.isEmpty(miscParams.getCHARSET()) || !StringUtils.isEmpty(miscParams.getURL()) || !StringUtils.isEmpty(miscParams.getCHECKPOINT_MGMT_SERVER())) {
//                settingsRO.setMiscParams(miscParams);
//            }
//
//            settingsRO.setAuthorizationCode(authorizationCode);
//            settingsROList.add(settingsRO);
//            CreateProfileRO ro = new CreateProfileRO();
//            ro.setControllerSettings(settingsROList);
//            ro.setHostAddress(hostAddress);
//            ro.setVersion(Integer.valueOf(version2));
//            ro.setIncludeRouting(includeRouting);
//            ro.setPluginId(pluginId);
//            ro.setVendorId(vendorId);
//            if (!StringUtils.isEmpty(gatherId)) {
//                ro.setId(gatherId);
//            }
//
//            return ro;
//        } else {
//            settingsROList = new ArrayList();
//            settingsRO = new ControllerSettingsRO();
//            settingsRO.setControllerId(controllerId);
//            settingsRO.setPortNumber(portNumber);
//            settingsRO.setTimeout(timeout);
//            settingsRO.setCredentialUuid(credentialUuid);
//            if (isSXF && !StringUtils.isEmpty(gatherId)) {
//                settingsRO.setWebuiUrl(webUrl);
//            } else if (isSXF && !StringUtils.isEmpty(webUrl)) {
//                settingsRO.setWebuiUrl(webUrl);
//            } else if (webUrl != null && !"".equals(webUrl)) {
//                if (!VendorEnum.CHECKPOINT.getCode().equalsIgnoreCase(vendorId) && !VendorEnum.PALOALTO.getCode().equalsIgnoreCase(vendorId)) {
//                    miscParams.setURL(webUrl);
//                } else {
//                    miscParams.setCHECKPOINT_MGMT_SERVER(webUrl);
//                }
//            }
//
//            if (!StringUtils.isEmpty(miscParams.getCHARSET()) || !StringUtils.isEmpty(miscParams.getURL()) || !StringUtils.isEmpty(miscParams.getCHECKPOINT_MGMT_SERVER())) {
//                settingsRO.setMiscParams(miscParams);
//            }
//
//            settingsRO.setAuthorizationCode(authorizationCode);
//            settingsROList.add(settingsRO);
//            BundleVersionRO bundle = new BundleVersionRO();
//            bundle.setType(type);
//            bundle.setId(id);
//            bundle.setVersion(version1);
//            CreateProfileRO ro = new CreateProfileRO();
//            ro.setBundleVersion(bundle);
//            ro.setControllerSettings(settingsROList);
//            ro.setDeviceTemplateId(deviceTemplateId);
//            ro.setHostAddress(hostAddress);
//            ro.setIncludeRouting(includeRouting);
//            ro.setPluginId(pluginId);
//            ro.setVendorId(vendorId);
//            ro.setVersion(Integer.valueOf(version2));
//            if (!StringUtils.isEmpty(gatherId)) {
//                ro.setId(gatherId);
//            }
//
//            return ro;
//        }
//    }
//
//    public JSONObject findNodeHistoryByPage(String ip, Integer origin, Integer start, Integer limit) {
//        JSONObject result = new JSONObject();
//
//        try {
//            List<NodeHistory> resultPage = this.nodeHistoryDAO.findAll(ip, origin, start, limit);
//            Integer num = this.nodeHistoryDAO.getTotalNum(ip, origin);
//            result.put("total", num);
//            result.put("data", resultPage);
//        } catch (Exception var8) {
//            logger.error(var8.getMessage(), var8);
//        }
//
//        return result;
//    }
//
//    public PageInfo<NodeHistory> findNodeHistoryByPage(NodeHistoryDTO nodeHistoryDTO) {
//        PageHelper.startPage(nodeHistoryDTO.getPageNum(), nodeHistoryDTO.getPageSize());
//        List<NodeHistory> list = this.nodeHistoryDAO.findBy(nodeHistoryDTO);
//        return new PageInfo(list);
//    }
//
//    public JSONObject findRouteTableHistoryByPage(String ip, Integer origin, Integer start, Integer limit) {
//        JSONObject result = new JSONObject();
//
//        try {
//            List<RouteTableHistory> resultPage = this.routeTableHistoryDAO.findAll(ip, origin, start, limit);
//            Integer num = this.routeTableHistoryDAO.getTotalNum(ip, origin);
//            result.put("total", num);
//            result.put("data", resultPage);
//        } catch (Exception var8) {
//            logger.error(var8.getMessage(), var8);
//        }
//
//        return result;
//    }
//
//    public void downloadHistory(Integer id, HttpServletResponse response) {
//        NodeHistory nodeHistory = this.nodeHistoryDAO.getNodeHistoryById(id);
//        String path = nodeHistory.getFilePath();
//        String ip = nodeHistory.getIp();
//        String createTime = (new SimpleDateFormat("yyyyMMddHHmmss")).format(nodeHistory.getCreatedTime());
//        String filename = ip + "_" + createTime + ".conf";
//        this.readFileToResponse(path, filename, response);
//    }
//
//    public void downloadRouteTableHistory(Integer id, HttpServletResponse response) {
//        RouteTableHistory routeTableHistory = this.routeTableHistoryDAO.getRouteTableHistoryById(id);
//        String path = routeTableHistory.getFilePath();
//        String ip = routeTableHistory.getIp();
//        String createTime = (new SimpleDateFormat("yyyyMMddHHmmss")).format(routeTableHistory.getCreatedTime());
//        String filename = ip + "_" + createTime + ".rt.conf";
//        this.readFileToResponse(path, filename, response);
//    }
//
//    public void readFileToResponse(String path, String filename, HttpServletResponse response) {
//        try {
//            path = this.buildNodeHistoryPath(path);
//            File file = new File(path);
//            InputStream fis = new BufferedInputStream(new FileInputStream(path));
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//            response.reset();
//            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            toClient.write(buffer);
//            toClient.flush();
//            toClient.close();
//        } catch (Exception var8) {
//            logger.error("readFileToResponse error", var8);
//        }
//
//    }
//
//    private String buildNodeHistoryPath(String path) {
//        String folder = this.dirPath + "/" + "history" + "/";
//        if (path.indexOf(File.separator) == -1) {
//            path = folder + path;
//        } else {
//            path = folder + path.substring(path.lastIndexOf(File.separator) + 1);
//        }
//
//        return path;
//    }
//
//    private String getFileNameByPath(String path) {
//        if (path.indexOf(File.separator) == -1) {
//            return path;
//        } else {
//            String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
//            return fileName;
//        }
//    }
//
//    public JSONObject showConfig(Integer id, Integer id2, Integer change) {
//        JSONObject result = new JSONObject();
//        StringBuffer stringBuffer = new StringBuffer();
//        BufferedReader reader = null;
//
//        try {
//            NodeHistory nodeHistory = this.nodeHistoryDAO.getNodeHistoryById(id);
//            String ip = nodeHistory.getIp();
//            String path = this.buildNodeHistoryPath(nodeHistory.getFilePath());
//            String fileEncode = StandardCharsets.UTF_8.name();
//            File file = new File(path);
//            FileInputStream fis = new FileInputStream(file);
//            reader = new BufferedReader(new InputStreamReader(fis, fileEncode));
//            String tempString = null;
//
//            while((tempString = reader.readLine()) != null) {
//                stringBuffer.append(tempString);
//                stringBuffer.append("\\r\\n");
//            }
//
//            fis.close();
//            reader.close();
//            FileInputStream file2is;
//            if (change != null && change == 1) {
//                Integer version = nodeHistory.getVersion();
//                NodeHistory nodeHistory1 = this.nodeHistoryDAO.getNodeHistoryByIpAndVersion(ip, version - 1);
//                String oldPath = this.buildNodeHistoryPath(nodeHistory1.getFilePath());
//                if (oldPath != null && oldPath.length() > 0) {
//                    StringBuffer oldStringBuffer = new StringBuffer();
//                    file2is = null;
//                    File oldFile = new File(oldPath);
//                    FileInputStream oldfis = new FileInputStream(oldFile);
//                    BufferedReader oldReader = new BufferedReader(new InputStreamReader(oldfis, fileEncode));
//                    String oldTmp = null;
//
//                    while((oldTmp = oldReader.readLine()) != null) {
//                        oldStringBuffer.append(oldTmp);
//                        oldStringBuffer.append("\\r\\n");
//                    }
//
//                    oldfis.close();
//                    oldReader.close();
//                    result.put("time2", nodeHistory1.getCreatedTime());
//                    result.put("date2", oldStringBuffer.toString());
//                } else {
//                    result.put("time2", nodeHistory1.getCreatedTime());
//                    result.put("date2", "");
//                }
//            } else if (id2 != null && id2 > 0) {
//                NodeHistory nodeHistory1 = this.nodeHistoryDAO.getNodeHistoryById(id2);
//                String path2 = this.buildNodeHistoryPath(nodeHistory1.getFilePath());
//                if (path2 != null && path2.length() > 0) {
//                    StringBuffer stringBuffer2 = new StringBuffer();
//                    File file2 = new File(path2);
//                    file2is = new FileInputStream(file2);
//                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(file2is, fileEncode));
//                    String tempString2 = null;
//
//                    while((tempString2 = reader2.readLine()) != null) {
//                        stringBuffer2.append(tempString2);
//                        stringBuffer2.append("\\r\\n");
//                    }
//
//                    file2is.close();
//                    reader2.close();
//                    result.put("time2", nodeHistory1.getCreatedTime());
//                    result.put("date2", stringBuffer2.toString());
//                } else {
//                    result.put("time2", nodeHistory1.getCreatedTime());
//                    result.put("date2", "");
//                }
//            } else {
//                result.put("date2", (Object)null);
//            }
//
//            result.put("result", true);
//            result.put("ip", ip);
//            result.put("time1", nodeHistory.getCreatedTime());
//            result.put("date", stringBuffer.toString());
//            JSONObject var33 = result;
//            return var33;
//        } catch (IOException var30) {
//            logger.error(var30.getMessage(), var30);
//            result.put("result", false);
//            result.put("msg", var30.getMessage());
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException var29) {
//                    ;
//                }
//            }
//
//        }
//
//        return result;
//    }
//
//    public JSONObject showRouteTableConfig(Integer id, Integer id2, Integer change) {
//        JSONObject result = new JSONObject();
//        StringBuffer stringBuffer = new StringBuffer();
//        BufferedReader reader = null;
//
//        try {
//            RouteTableHistory routeTableHistory = this.routeTableHistoryDAO.getRouteTableHistoryById(id);
//            String ip = routeTableHistory.getIp();
//            String path = this.buildNodeHistoryPath(routeTableHistory.getFilePath());
//            String fileEncode = FileUtil.getFileEncode(path);
//            File file = new File(path);
//            FileInputStream fis = new FileInputStream(file);
//            reader = new BufferedReader(new InputStreamReader(fis, fileEncode));
//            String tempString = null;
//
//            while((tempString = reader.readLine()) != null) {
//                stringBuffer.append(tempString);
//                stringBuffer.append("\\r\\n");
//            }
//
//            fis.close();
//            reader.close();
//            String file2Encode;
//            if (change != null && change == 1) {
//                Integer version = routeTableHistory.getVersion();
//                RouteTableHistory routeTableHistory1 = this.routeTableHistoryDAO.getRouteTableHistoryByIpAndVersion(ip, version - 1);
//                String oldPath = this.buildNodeHistoryPath(routeTableHistory1.getFilePath());
//                if (oldPath != null && oldPath.length() > 0) {
//                    StringBuffer oldStringBuffer = new StringBuffer();
//                    file2Encode = null;
//                    File oldFile = new File(oldPath);
//                    String oldFileEncode = FileUtil.getFileEncode(oldPath);
//                    FileInputStream oldfis = new FileInputStream(oldFile);
//                    BufferedReader oldReader = new BufferedReader(new InputStreamReader(oldfis, oldFileEncode));
//                    String oldTmp = null;
//
//                    while((oldTmp = oldReader.readLine()) != null) {
//                        oldStringBuffer.append(oldTmp);
//                        oldStringBuffer.append("\\r\\n");
//                    }
//
//                    oldfis.close();
//                    oldReader.close();
//                    result.put("time2", routeTableHistory1.getCreatedTime());
//                    result.put("date2", oldStringBuffer.toString());
//                } else {
//                    result.put("time2", routeTableHistory1.getCreatedTime());
//                    result.put("date2", "");
//                }
//            } else if (id2 != null && id2 > 0) {
//                RouteTableHistory routeTableHistory2 = this.routeTableHistoryDAO.getRouteTableHistoryById(id2);
//                String path2 = this.buildNodeHistoryPath(routeTableHistory2.getFilePath());
//                if (path2 != null && path2.length() > 0) {
//                    StringBuffer stringBuffer2 = new StringBuffer();
//                    File file2 = new File(path2);
//                    file2Encode = FileUtil.getFileEncode(path2);
//                    FileInputStream file2is = new FileInputStream(file2);
//                    BufferedReader reader2 = new BufferedReader(new InputStreamReader(file2is, file2Encode));
//                    String tempString2 = null;
//
//                    while((tempString2 = reader2.readLine()) != null) {
//                        stringBuffer2.append(tempString2);
//                        stringBuffer2.append("\\r\\n");
//                    }
//
//                    file2is.close();
//                    reader2.close();
//                    result.put("time2", routeTableHistory2.getCreatedTime());
//                    result.put("date2", stringBuffer2.toString());
//                } else {
//                    result.put("time2", routeTableHistory2.getCreatedTime());
//                    result.put("date2", "");
//                }
//            } else {
//                result.put("date2", (Object)null);
//            }
//
//            result.put("result", true);
//            result.put("ip", ip);
//            result.put("time1", routeTableHistory.getCreatedTime());
//            result.put("date", stringBuffer.toString());
//            JSONObject var35 = result;
//            return var35;
//        } catch (IOException var31) {
//            logger.error(var31.getMessage(), var31);
//            result.put("result", false);
//            result.put("msg", var31.getMessage());
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException var30) {
//                    ;
//                }
//            }
//
//        }
//
//        return result;
//    }
//
//    public List<Integer> getNodeIdByGatherCycle(String cycleId) {
//        return this.nodeDAO.getNodeIdsByGatherCycle(cycleId);
//    }
//
//    public List<Node> getNodesByGatherCycle(String cycleId) {
//        return this.nodeDAO.getTheNodeByGatherCycle(cycleId);
//    }
//
//    public void restrictSumHistory(String ip) {
//        try {
//            Integer total = this.nodeHistoryDAO.getCount(ip);
//            if (total >= 180) {
//                List<NodeHistory> nodeHistoryList = this.nodeHistoryDAO.getQualifierRecord(ip, total - 180);
//                NodeHistory nodeHistory;
//                if (nodeHistoryList != null && nodeHistoryList.size() > 0) {
//                    for(Iterator var4 = nodeHistoryList.iterator(); var4.hasNext(); this.nodeHistoryDAO.deleteById(nodeHistory.getId())) {
//                        nodeHistory = (NodeHistory)var4.next();
//                        File file = new File(this.buildNodeHistoryPath(nodeHistory.getFilePath()));
//                        if (file.exists()) {
//                            file.delete();
//                        }
//                    }
//                }
//            }
//        } catch (Exception var7) {
//            logger.error(var7.getMessage(), var7);
//        }
//
//    }
//
//    public boolean booleanExist(String ip) {
//        Node theNodeByIP = this.nodeDAO.getTheNodeByIP(ip);
//        return theNodeByIP != null;
//    }
//
//    public String booleanExistIps(String ips) {
//        JSONObject result = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        String[] split = ips.split(",");
//        if (split.length > 0) {
//            for(int i = 0; i < split.length; ++i) {
//                String ip = split[i];
//                if (ip != null && ip.length() > 0) {
//                    JSONObject tmp = new JSONObject();
//                    Node theNodeByIP = this.nodeDAO.getTheNodeByIP(ip);
//                    if (theNodeByIP != null) {
//                        tmp.put("ip", ip);
//                        tmp.put("booleanExist", true);
//                        jsonArray.add(tmp);
//                    } else {
//                        tmp.put("ip", ip);
//                        tmp.put("booleanExist", false);
//                        jsonArray.add(tmp);
//                    }
//                }
//            }
//        }
//
//        result.put("result", true);
//        result.put("data", jsonArray);
//        return result.toJSONString();
//    }
//
//    public String booleanExistCredentialIps(String uuids) {
//        JSONObject result = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        String[] split = uuids.split(",");
//        if (split.length > 0) {
//            for(int i = 0; i < split.length; ++i) {
//                String uuid = split[i];
//                if (uuid != null && uuid.length() > 0) {
//                    JSONObject tmp = new JSONObject();
//                    Node theNodeByCredentialUuid = this.nodeDAO.getTheNodeByCredentialUuid(uuid);
//                    if (theNodeByCredentialUuid != null) {
//                        tmp.put("uuid", uuid);
//                        tmp.put("booleanExist", true);
//                        jsonArray.add(tmp);
//                    } else {
//                        tmp.put("uuid", uuid);
//                        tmp.put("booleanExist", false);
//                        jsonArray.add(tmp);
//                    }
//                }
//            }
//        }
//
//        result.put("result", true);
//        result.put("data", jsonArray);
//        return result.toJSONString();
//    }
//
//    public JSONObject getEngineJson() {
//        JSONObject result = new JSONObject();
//        List<Engine> engineListForController = this.engineDAO.getAllController();
//        JSONArray controllers = this.getEngineJSON(engineListForController);
//        List<Engine> engineListForRouter = this.engineDAO.getAllRouter();
//        JSONArray routers = this.getEngineJSON(engineListForRouter);
//        List<Engine> engineListForFireWall = this.engineDAO.getAllFireWall();
//        JSONArray fireWalls = this.getEngineJSON(engineListForFireWall);
//        List<Engine> gapsList = this.engineDAO.getAllGap();
//        JSONArray gaps = this.getEngineJSON(gapsList);
//        List<Engine> hostList = this.engineDAO.getAllHost();
//        JSONArray hosts = this.getEngineJSON(hostList);
//        List<Engine> edrList = this.engineDAO.getAllEdr();
//        JSONArray edrs = this.getEngineJSON(edrList);
//        result.put("fireWall", fireWalls);
//        result.put("router", routers);
//        result.put("controller", controllers);
//        result.put("gap", gaps);
//        result.put("host", hosts);
//        result.put("edr", edrs);
//        return result;
//    }
//
//    private JSONArray getEngineJSON(List<Engine> list) {
//        JSONArray jsonArray = new JSONArray();
//        if (list != null && !list.isEmpty()) {
//            Iterator var3 = list.iterator();
//
//            while(var3.hasNext()) {
//                Engine e = (Engine)var3.next();
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("version", JSONObject.parseObject(e.getVersion()));
//                jsonObject.put("vendor", JSONObject.parseObject(e.getVendor()));
//                jsonObject.put("controllers", JSONArray.parseArray(e.getControllers()));
//                jsonObject.put("templates", JSONArray.parseArray(e.getTemplates()));
//                jsonObject.put("plugins", JSONArray.parseArray(e.getPlugins()));
//                jsonObject.put("models", JSONArray.parseArray(e.getModels()));
//                jsonObject.put("type", e.getType());
//                jsonArray.add(jsonObject);
//            }
//
//            return jsonArray;
//        } else {
//            return jsonArray;
//        }
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public void updateSessionMonitor(SessionMonitorContext sessionMonitorContext) {
//        int row = this.nodeDAO.updateNodeProbeIdById(sessionMonitorContext.getNodeId(), sessionMonitorContext.getProbeIp(), sessionMonitorContext.getProbeToken());
//        if (row > 0) {
//            List<NodeProbeConfig> nodeProbeConfigList = new ArrayList();
//            Iterator var4 = sessionMonitorContext.getInterfaceLinkMappings().iterator();
//
//            while(var4.hasNext()) {
//                InterfaceLinkMapping interfaceLinkMapping = (InterfaceLinkMapping)var4.next();
//                NodeProbeConfig nodeProbeConfig = new NodeProbeConfig();
//                nodeProbeConfig.setDeviceId(sessionMonitorContext.getNodeId());
//                nodeProbeConfig.setInterfaceUuid(interfaceLinkMapping.getInterfaceUuid());
//                nodeProbeConfig.setInterfaceName(interfaceLinkMapping.getInterfaceName());
//                nodeProbeConfig.setLinkId(interfaceLinkMapping.getLinkId());
//                nodeProbeConfig.setLinkName(interfaceLinkMapping.getLinkName());
//                nodeProbeConfig.setVlinkId(interfaceLinkMapping.getVlinkId());
//                nodeProbeConfig.setVlinkName(interfaceLinkMapping.getVlinkName());
//                nodeProbeConfigList.add(nodeProbeConfig);
//            }
//
//            this.nodeProbeConfigMapper.deleteByDeviceId(sessionMonitorContext.getNodeId());
//            this.nodeProbeConfigMapper.insertList(nodeProbeConfigList);
//        }
//
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public void deleteSessionMonitor(Integer nodeId) {
//        this.nodeDAO.updateNodeProbeIdById(nodeId, (String)null, (String)null);
//        this.nodeProbeConfigMapper.deleteByDeviceId(nodeId);
//    }
//
//    public SessionMonitorContext getSessionMonitorByNodeId(Integer nodeId) {
//        Node node = this.nodeDAO.getTheNode(nodeId);
//        SessionMonitorContext sessionMonitorContext = new SessionMonitorContext();
//        sessionMonitorContext.setNodeId(nodeId);
//        sessionMonitorContext.setProbeIp(node.getProbeIp());
//        sessionMonitorContext.setProbeToken(node.getProbeToken());
//        List<InterfaceLinkMapping> interfaceLinkMappings = new ArrayList();
//        List<NodeProbeConfig> nodeProbeConfigs = this.nodeProbeConfigMapper.listByDeviceId(nodeId);
//        Iterator var6 = nodeProbeConfigs.iterator();
//
//        while(var6.hasNext()) {
//            NodeProbeConfig npc = (NodeProbeConfig)var6.next();
//            InterfaceLinkMapping interfaceLinkMapping = new InterfaceLinkMapping();
//            interfaceLinkMapping.setInterfaceName(npc.getInterfaceName());
//            interfaceLinkMapping.setInterfaceUuid(npc.getInterfaceUuid());
//            interfaceLinkMapping.setLinkId(npc.getLinkId());
//            interfaceLinkMapping.setLinkName(npc.getLinkName());
//            interfaceLinkMapping.setVlinkId(npc.getVlinkId());
//            interfaceLinkMapping.setVlinkName(npc.getVlinkName());
//            interfaceLinkMappings.add(interfaceLinkMapping);
//        }
//
//        sessionMonitorContext.setInterfaceLinkMappings(interfaceLinkMappings);
//        return sessionMonitorContext;
//    }
//
//    public List<ProbeLinkResponse> listProbeLinks(String ip, String token) {
//        String url = String.format("http://%s/api/init.html/config/vrf", ip);
//        Map<String, String> headerMap = Maps.newHashMap();
//        headerMap.put("token_key", token);
//        String resultStr = HttpClientUtils.httpGet(url, headerMap);
//        List<ProbeLinkResponse> probeLinks = null;
//        if (!StringUtils.isEmpty(resultStr)) {
//            try {
//                JSONObject jsonObject = JSONObject.parseObject(resultStr);
//                if (jsonObject.getIntValue("code") == 1) {
//                    JSONArray jsonArray = jsonObject.getJSONArray("data");
//                    probeLinks = jsonArray == null ? new ArrayList() : JSON.parseArray(jsonArray.toJSONString(), ProbeLinkResponse.class);
//                }
//            } catch (JSONException var9) {
//                logger.info("获取链路列表异常", var9);
//            }
//        }
//
//        return (List)probeLinks;
//    }
//
//    public List<NodeRevision> listNodeRevision(String ip, String deviceUuid, int page, int psize, Integer start, Integer size) {
//        try {
//            List<NodeHistory> nodeHistoryResult = (List)this.nodeHistoryDAO.findAll(ip, (Integer)null, 0, 100000).stream().filter((nodeHistoryx) -> {
//                return nodeHistoryx.getSubVersion() != null;
//            }).collect(Collectors.toList());
//            List<NodeHistory> nodeHistories = new LinkedList();
//            Iterator var9 = nodeHistoryResult.iterator();
//
//            while(var9.hasNext()) {
//                NodeHistory nodeHistory = (NodeHistory)var9.next();
//                boolean b = nodeHistories.stream().anyMatch((node) -> {
//                    return node.getSubVersion().equals(nodeHistory.getSubVersion());
//                });
//                if (!b) {
//                    nodeHistories.add(nodeHistory);
//                }
//            }
//
//            List<RouteTableHistory> routeTableHistoryResult = (List)this.routeTableHistoryDAO.findAll(ip, (Integer)null, 0, 100000).stream().filter((route) -> {
//                return route.getDataVersion() != null;
//            }).collect(Collectors.toList());
//            List<RouteTableHistory> routeTableHistories = new LinkedList();
//            Iterator var31 = routeTableHistoryResult.iterator();
//
//            while(var31.hasNext()) {
//                RouteTableHistory routeTableHistory = (RouteTableHistory)var31.next();
//                boolean b = routeTableHistories.stream().anyMatch((route) -> {
//                    return route.getDataVersion().equals(routeTableHistory.getDataVersion());
//                });
//                if (!b) {
//                    routeTableHistories.add(routeTableHistory);
//                }
//            }
//
//            RevisionSearchDTO searchDTO = new RevisionSearchDTO();
//            searchDTO.setDeviceUuid(deviceUuid);
//            if (page > 0) {
//                searchDTO.setPage(page);
//            }
//
//            if (psize > 0) {
//                searchDTO.setPsize(psize);
//            }
//
//            ResultRO<List<RevisionSearchRO>> revisResultRO = this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
//            if (revisResultRO != null && revisResultRO.getSuccess()) {
//                List<RevisionSearchRO> data = (List)revisResultRO.getData();
//                int total = revisResultRO.getTotal();
//                List<NodeRevision> nodeRevisions = new ArrayList();
//
//                for(int i = 0; i < data.size(); ++i) {
//                    NodeRevision nodeRevision = new NodeRevision();
//                    RevisionSearchRO obj = (RevisionSearchRO)data.get(i);
//                    nodeRevision.setRevisionId(Integer.valueOf(obj.getRevisionId()));
//                    nodeRevision.setType(obj.getType());
//                    String time = obj.getRevisionTime();
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
//                    LocalDateTime ldt = LocalDateTime.parse(time, dtf);
//                    nodeRevision.setTime(ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli());
//                    nodeRevision.setDeviceName(obj.getDeviceName());
//                    ChangeCountsRO changeCounts = obj.getChangeCounts();
//                    if (changeCounts != null) {
//                        nodeRevision.setChangeCounts(JSONObject.toJSONString(changeCounts));
//                        SubDeviceVersionIdRO subDevice = obj.getSubDeviceVerId();
//                        if (subDevice != null) {
//                            String subDeviceVerId = subDevice.getVersion();
//                            if (org.apache.commons.lang3.StringUtils.isNotBlank(subDeviceVerId)) {
//                                Iterator var25 = nodeHistories.iterator();
//
//                                while(var25.hasNext()) {
//                                    NodeHistory nodeHistory = (NodeHistory)var25.next();
//                                    if (nodeHistory.getSubVersion() != null && nodeHistory.getSubVersion().equals(Integer.valueOf(subDeviceVerId))) {
//                                        nodeRevision.setOrigin(nodeHistory.getOrigin());
//                                        nodeRevision.setId(nodeHistory.getId());
//                                        nodeRevision.setState(nodeHistory.getState());
//                                        nodeRevisions.add(nodeRevision);
//                                    }
//                                }
//                            }
//                        }
//
//                        ImportedRoutingTableDataVersionRO importedRoutingTableDataVersion = obj.getImportedRoutingTableDataVersion();
//                        if (importedRoutingTableDataVersion != null) {
//                            Integer routeTableVersion = importedRoutingTableDataVersion.getVersion();
//                            if (routeTableVersion != null) {
//                                Iterator var37 = routeTableHistories.iterator();
//
//                                while(var37.hasNext()) {
//                                    RouteTableHistory routeTableHistory = (RouteTableHistory)var37.next();
//                                    if (routeTableHistory.getDataVersion() != null && routeTableHistory.getDataVersion().equals(routeTableVersion)) {
//                                        nodeRevision.setOrigin(routeTableHistory.getOrigin());
//                                        nodeRevision.setId(routeTableHistory.getId().toString());
//                                        nodeRevision.setState(routeTableHistory.getState());
//                                        nodeRevisions.add(nodeRevision);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return nodeRevisions;
//            } else {
//                return null;
//            }
//        } catch (Exception var28) {
//            logger.error(var28.getStackTrace().toString());
//            return null;
//        }
//    }
//
//    public List<NodeRevision> listDeviceChange(List<String> deviceUuid, String beginTime, String endTime) {
//        List<String> IPs = new ArrayList();
//        Iterator var5 = deviceUuid.iterator();
//
//        while(var5.hasNext()) {
//            String uuid = (String)var5.next();
//            Node nodeByUuid = this.nodeDAO.getTheNodeByUuid(uuid);
//            IPs.add(nodeByUuid.getIp());
//        }
//
//        RevisionSearchDTO searchDTO = new RevisionSearchDTO();
//        searchDTO.setStartTime(beginTime);
//        searchDTO.setEndTime(endTime);
//        ResultRO<List<RevisionSearchRO>> revisResultRO = this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
//        if (revisResultRO != null && revisResultRO.getSuccess()) {
//            List<RevisionSearchRO> data = (List)revisResultRO.getData();
//            int var8 = revisResultRO.getTotal();
//
//            try {
//                List<NodeRevision> nodeRevisions = new ArrayList();
//                Iterator var10 = IPs.iterator();
//
//                while(var10.hasNext()) {
//                    String ip = (String)var10.next();
//                    List<NodeHistory> nodeHistoryResult = (List)this.nodeHistoryDAO.findAll(ip, (Integer)null, 0, 100000).stream().filter((nodeHistoryx) -> {
//                        return nodeHistoryx.getSubVersion() != null;
//                    }).collect(Collectors.toList());
//                    List<NodeHistory> nodeHistories = new LinkedList();
//                    Iterator var14 = nodeHistoryResult.iterator();
//
//                    while(var14.hasNext()) {
//                        NodeHistory nodeHistory = (NodeHistory)var14.next();
//                        boolean b = nodeHistories.stream().anyMatch((node) -> {
//                            return node.getSubVersion().equals(nodeHistory.getSubVersion());
//                        });
//                        if (!b) {
//                            nodeHistories.add(nodeHistory);
//                        }
//                    }
//
//                    Node nodeByIP = this.nodeDAO.getTheNodeByIP(ip);
//                    String uuid = nodeByIP.getUuid();
//
//                    for(int i = 0; i < data.size(); ++i) {
//                        NodeRevision nodeRevision = new NodeRevision();
//                        RevisionSearchRO obj = (RevisionSearchRO)data.get(i);
//                        SubDeviceVersionIdRO subDevice = obj.getSubDeviceVerId();
//                        if (subDevice != null && subDevice.getDeviceUuid().equals(uuid)) {
//                            nodeRevision.setRevisionId(Integer.valueOf(obj.getRevisionId()));
//                            nodeRevision.setType(obj.getType());
//                            String time = obj.getRevisionTime();
//                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
//                            LocalDateTime ldt = LocalDateTime.parse(time, dtf);
//                            nodeRevision.setTime(ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli());
//                            nodeRevision.setDeviceName(obj.getDeviceName());
//                            ChangeCountsRO changeCounts = obj.getChangeCounts();
//                            if (changeCounts != null) {
//                                nodeRevision.setChangeCounts(JSONObject.toJSONString(changeCounts));
//                            } else {
//                                nodeRevision.setChangeCounts((String)null);
//                            }
//
//                            String subDeviceVerId = subDevice.getVersion();
//                            if (org.apache.commons.lang3.StringUtils.isNotBlank(subDeviceVerId)) {
//                                Iterator var25 = nodeHistories.iterator();
//
//                                while(var25.hasNext()) {
//                                    NodeHistory nodeHistory = (NodeHistory)var25.next();
//                                    if (nodeHistory.getSubVersion() != null && nodeHistory.getSubVersion().equals(Integer.valueOf(subDeviceVerId))) {
//                                        nodeRevision.setOrigin(nodeHistory.getOrigin());
//                                        nodeRevision.setId(nodeHistory.getId());
//                                        nodeRevision.setState(nodeHistory.getState());
//                                        nodeRevisions.add(nodeRevision);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return nodeRevisions;
//            } catch (Exception var27) {
//                logger.error(Arrays.toString(var27.getStackTrace()));
//                return null;
//            }
//        } else {
//            return null;
//        }
//    }
//
//    public List<RevisionChange> listChangeByReversionListId(List<NodeRevision> nodeRevisionLists) {
//        try {
//            List<Integer> revisionIds = new ArrayList();
//            Iterator var3 = nodeRevisionLists.iterator();
//
//            while(var3.hasNext()) {
//                NodeRevision nodeRevision = (NodeRevision)var3.next();
//                revisionIds.add(nodeRevision.getRevisionId());
//            }
//
//            List<RevisionChange> nodeRevisionList = new ArrayList();
//            Iterator var22 = revisionIds.iterator();
//
//            while(var22.hasNext()) {
//                int revisionId = (Integer)var22.next();
//                ResultRO<List<VersionChangeRO>> resultRO = this.whaleDeviceChangeClient.getDetailByVersion(revisionId);
//                if (resultRO == null || !resultRO.getSuccess()) {
//                    if (logger.isWarnEnabled()) {
//                        logger.warn("调用whale接口查询版本变更详情，success字段为FALSE");
//                    }
//
//                    return null;
//                }
//
//                List<VersionChangeRO> data = (List)resultRO.getData();
//                int total = resultRO.getTotal();
//                List<RevisionChange> nodeRevisions = new ArrayList();
//
//                for(int i = 0; i < data.size(); ++i) {
//                    RevisionChange revisionChange = new RevisionChange();
//                    VersionChangeRO obj = (VersionChangeRO)data.get(i);
//                    revisionChange.setId(obj.getId());
//                    String changeId = String.valueOf(obj.getChangeId());
//                    revisionChange.setChangeId(changeId);
//                    revisionChange.setChangeType(obj.getChangeType());
//                    String objectType = obj.getObjectType();
//                    revisionChange.setObjectType(objectType);
//                    String routingTableUuid;
//                    String config;
//                    if (org.apache.commons.lang3.StringUtils.equals(objectType, "RULE_LIST")) {
//                        revisionChange.setParent(true);
//                        revisionChange.setObjectUuid(obj.getObjectUuid());
//                    } else if (org.apache.commons.lang3.StringUtils.equals(objectType, "RULE_ITEM")) {
//                        revisionChange.setParent(false);
//                        routingTableUuid = obj.getAclUuid();
//                        if (org.apache.commons.lang3.StringUtils.isNotBlank(routingTableUuid)) {
//                            revisionChange.setObjectUuid(routingTableUuid);
//                        } else if (logger.isWarnEnabled()) {
//                            config = obj.getDeviceName();
//                            logger.warn("设备[" + config + "]在版本[" + revisionId + "]的变更[" + changeId + "]中没有aclUuid");
//                        }
//                    } else {
//                        Optional opt;
//                        if (org.apache.commons.lang3.StringUtils.equals(objectType, "ROUTING_TABLE")) {
//                            revisionChange.setParent(true);
//                            routingTableUuid = obj.getObjectUuid();
//                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
//                        } else if (org.apache.commons.lang3.StringUtils.equals(objectType, "ROUTING_ENTRY")) {
//                            revisionChange.setParent(false);
//                            routingTableUuid = obj.getRoutingTableUuid();
//                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
//                        } else {
//                            revisionChange.setParent(true);
//                            revisionChange.setObjectUuid(obj.getObjectUuid());
//                        }
//                    }
//
//                    revisionChange.setObjectName(obj.getObjectName());
//                    routingTableUuid = obj.getPrevConfigText();
//                    if (!StringUtils.isEmpty(routingTableUuid)) {
//                        String[] prec = routingTableUuid.split(System.getProperty("line.separator"));
//                        revisionChange.setPreConfigText(prec);
//                    } else {
//                        revisionChange.setPreConfigText(new String[0]);
//                    }
//
//                    config = obj.getConfigText();
//                    if (!StringUtils.isEmpty(config)) {
//                        revisionChange.setConfigText(config.split(System.getProperty("line.separator")));
//                    } else {
//                        revisionChange.setConfigText(new String[0]);
//                    }
//
//                    String time = obj.getRevisionTime();
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
//                    LocalDateTime ldt = LocalDateTime.parse(time, dtf);
//                    revisionChange.setRevisionTime(ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli());
//                    nodeRevisions.add(revisionChange);
//                }
//
//                List<RevisionChange> parentNode = new ArrayList();
//                nodeRevisions.stream().filter(RevisionChange::getParent).forEach(parentNode::add);
//                parentNode.stream().filter((parent) -> {
//                    return org.apache.commons.lang3.StringUtils.isNotBlank(parent.getObjectUuid());
//                }).forEach((parent) -> {
//                    nodeRevisions.stream().filter((change) -> {
//                        return org.apache.commons.lang3.StringUtils.equals(parent.getObjectUuid(), change.getObjectUuid()) && !change.getParent();
//                    }).forEach((change) -> {
//                        parent.getRevisionChanges().add(change);
//                    });
//                    nodeRevisionList.add(parent);
//                });
//            }
//
//            return nodeRevisionList;
//        } catch (Exception var20) {
//            logger.error(var20.toString());
//            return null;
//        }
//    }
//
//    public List<RevisionChange> listChangeByReversionId(int revisionId) {
//        try {
//            ResultRO<List<VersionChangeRO>> resultRO = this.whaleDeviceChangeClient.getDetailByVersion(revisionId);
//            if (resultRO != null && resultRO.getSuccess()) {
//                List<VersionChangeRO> data = (List)resultRO.getData();
//                int total = resultRO.getTotal();
//                List<RevisionChange> nodeRevisions = new ArrayList();
//
//                for(int i = 0; i < data.size(); ++i) {
//                    RevisionChange revisionChange = new RevisionChange();
//                    VersionChangeRO obj = (VersionChangeRO)data.get(i);
//                    revisionChange.setId(obj.getId());
//                    String changeId = String.valueOf(obj.getChangeId());
//                    revisionChange.setChangeId(changeId);
//                    revisionChange.setChangeType(obj.getChangeType());
//                    String objectType = obj.getObjectType();
//                    revisionChange.setObjectType(objectType);
//                    String routingTableUuid;
//                    String config;
//                    if (org.apache.commons.lang3.StringUtils.equals(objectType, "RULE_LIST")) {
//                        revisionChange.setParent(true);
//                        revisionChange.setObjectUuid(obj.getObjectUuid());
//                    } else if (org.apache.commons.lang3.StringUtils.equals(objectType, "RULE_ITEM")) {
//                        revisionChange.setParent(false);
//                        routingTableUuid = obj.getAclUuid();
//                        if (org.apache.commons.lang3.StringUtils.isNotBlank(routingTableUuid)) {
//                            revisionChange.setObjectUuid(routingTableUuid);
//                        } else if (logger.isWarnEnabled()) {
//                            config = obj.getDeviceName();
//                            logger.warn("设备[" + config + "]在版本[" + revisionId + "]的变更[" + changeId + "]中没有aclUuid");
//                        }
//                    } else {
//                        Optional opt;
//                        if (org.apache.commons.lang3.StringUtils.equals(objectType, "ROUTING_TABLE")) {
//                            revisionChange.setParent(true);
//                            routingTableUuid = obj.getObjectUuid();
//                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
//                        } else if (org.apache.commons.lang3.StringUtils.equals(objectType, "ROUTING_ENTRY")) {
//                            revisionChange.setParent(false);
//                            routingTableUuid = obj.getRoutingTableUuid();
//                            opt = Optional.ofNullable(routingTableUuid);
//                            opt.ifPresent(revisionChange::setObjectUuid);
//                        } else {
//                            revisionChange.setParent(true);
//                            revisionChange.setObjectUuid(obj.getObjectUuid());
//                        }
//                    }
//
//                    revisionChange.setObjectName(obj.getObjectName());
//                    routingTableUuid = obj.getPrevConfigText();
//                    if (!StringUtils.isEmpty(routingTableUuid)) {
//                        String[] prec = routingTableUuid.split(System.getProperty("line.separator"));
//                        revisionChange.setPreConfigText(prec);
//                    } else {
//                        revisionChange.setPreConfigText(new String[0]);
//                    }
//
//                    config = obj.getConfigText();
//                    if (!StringUtils.isEmpty(config)) {
//                        revisionChange.setConfigText(config.split(System.getProperty("line.separator")));
//                    } else {
//                        revisionChange.setConfigText(new String[0]);
//                    }
//
//                    String time = obj.getRevisionTime();
//                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS+0800");
//                    LocalDateTime ldt = LocalDateTime.parse(time, dtf);
//                    revisionChange.setRevisionTime(ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli());
//                    nodeRevisions.add(revisionChange);
//                }
//
//                List<RevisionChange> parentNode = new ArrayList();
//                nodeRevisions.stream().filter(RevisionChange::getParent).forEach(parentNode::add);
//                List<RevisionChange> nodeRevisionList = new ArrayList();
//                parentNode.stream().filter((parent) -> {
//                    return org.apache.commons.lang3.StringUtils.isNotBlank(parent.getObjectUuid());
//                }).forEach((parent) -> {
//                    nodeRevisions.stream().filter((change) -> {
//                        return org.apache.commons.lang3.StringUtils.equals(parent.getObjectUuid(), change.getObjectUuid()) && !change.getParent();
//                    }).forEach((change) -> {
//                        parent.getRevisionChanges().add(change);
//                    });
//                    nodeRevisionList.add(parent);
//                });
//                return nodeRevisionList;
//            } else {
//                if (logger.isWarnEnabled()) {
//                    logger.warn("调用whale接口查询版本变更详情，success字段为FALSE");
//                }
//
//                return null;
//            }
//        } catch (Exception var16) {
//            logger.error(var16.toString());
//            return null;
//        }
//    }
//
//    public List<RevisionChange> listChangeBySubVersion(String deviceUuid, Long subDeviceVersion) {
//        RevisionSearchDTO searchDTO = new RevisionSearchDTO();
//        searchDTO.setDeviceUuid(deviceUuid);
//        searchDTO.setSubDeviceVersion(subDeviceVersion);
//        ResultRO<List<RevisionSearchRO>> revisResultRO = this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
//        String revisionId = null;
//        if (revisResultRO.getSuccess() && revisResultRO.getData() != null && ((List)revisResultRO.getData()).size() > 0) {
//            Iterator var6 = ((List)revisResultRO.getData()).iterator();
//
//            while(var6.hasNext()) {
//                RevisionSearchRO revisionSearchRO = (RevisionSearchRO)var6.next();
//                String type = revisionSearchRO.getType();
//                if (org.apache.commons.lang3.StringUtils.equalsAny(type, new CharSequence[]{DeviceChangeTypeEnum.NEW_DEVICE.getCode(), DeviceChangeTypeEnum.UPDATE.getCode()})) {
//                    revisionId = revisionSearchRO.getRevisionId();
//                    break;
//                }
//            }
//        }
//
//        return (List)(revisionId != null ? this.listChangeByReversionId(Integer.parseInt(revisionId)) : new ArrayList());
//    }
//
//    public Result<String> getCaptureImageEncode(String hostAddress, String webUrl, String credentialUuid, String vendorId, String deviceTemplateId, String pluginId, String controllerId, String deviceType, String id, String version, String description, String deviceName, Boolean includeRouting, String version2, String modelNumber, Integer nodeGroup, String type, String charset) {
//        this.gatherId = null;
//        this.sessionUuid = null;
//        ResultRO<List<CreateProfileRO>> resultRO = null;
//        CreateProfileRO profileDTO = this.constructRequestBody(hostAddress, webUrl, credentialUuid, vendorId, deviceTemplateId, pluginId, controllerId, type, id, version, description);
//        Node tmpNode = this.nodeDAO.getTheNodeByIP(hostAddress);
//        if (tmpNode != null) {
//            return new Result(false, "failed", "IP与已有的节点配置重复");
//        } else {
//            resultRO = this.whaleDeviceProfile.createConfig(profileDTO);
//            if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && ((List)resultRO.getData()).size() > 0) {
//                CreateProfileRO profileRO = (CreateProfileRO)((List)resultRO.getData()).get(0);
//                this.gatherId = profileRO.getId();
//                ControllerSettingsRO controllerSettings = null;
//                if (profileRO.getControllerSettings() != null && profileRO.getControllerSettings().size() > 0) {
//                    controllerSettings = (ControllerSettingsRO)profileRO.getControllerSettings().get(0);
//                }
//
//                Node node = this.constructNode((Node)null, controllerId, (Integer)null, (Integer)null, credentialUuid, deviceTemplateId, hostAddress, pluginId, vendorId, version2, includeRouting, deviceName, deviceType, "深信服", modelNumber, description, nodeGroup, (String)null, this.gatherId, webUrl, (String)null, (Integer)null, charset, (String)null, (String)null);
//                node = this.predefindOrCustom(node, controllerSettings);
//                this.savedNode = node;
//            }
//
//            return this.getCaptchaImage(this.gatherId);
//        }
//    }
//
//    public Result<String> getCaptchaImmediate(String id) {
//        Node node = this.nodeDAO.getTheNode(Integer.valueOf(id));
//        return this.getCaptchaImage(node.getGatherId());
//    }
//
//    public Result<String> getCaptchaImage(String gatherId) {
//        logger.info("获取深信服设备采集的验证码, 参数gatherId:{}", gatherId);
//        ResultRO<List<CaptchaImageRO>> resultRO = this.whaleDeviceProfile.sendSXFAsync(gatherId);
//        logger.info("获取深信服设备采集的验证码  返回: " + JSON.toJSONString(resultRO));
//        if (resultRO != null && resultRO.getSuccess() && resultRO.getData() != null && ((List)resultRO.getData()).size() > 0) {
//            CaptchaImageRO captchaImageRO = (CaptchaImageRO)((List)resultRO.getData()).get(0);
//            this.sessionUuid = captchaImageRO.getSessionUuid();
//            return new Result(true, "success", captchaImageRO.getCaptchaImage());
//        } else {
//            return new Result(false, "failed", (Object)null);
//        }
//    }
//
//    private CreateProfileRO constructRequestBody(String hostAddress, String webuiUrl, String credentialUuid, String vendorId, String deviceTemplateId, String pluginId, String controllerId, String type, String id, String version, String description) {
//        CreateProfileRO dto = new CreateProfileRO();
//        dto.setHostAddress(hostAddress);
//        dto.setActive(true);
//        dto.setDescription(description);
//        dto.setVendorId(vendorId);
//        dto.setDeviceTemplateId(deviceTemplateId);
//        dto.setPluginId(pluginId);
//        List<ControllerSettingsRO> controllerSettings = new ArrayList();
//        ControllerSettingsRO settingsRO = new ControllerSettingsRO();
//        settingsRO.setCredentialUuid(credentialUuid);
//        settingsRO.setControllerId(controllerId);
//        settingsRO.setWebuiUrl(webuiUrl);
//        controllerSettings.add(settingsRO);
//        dto.setControllerSettings(controllerSettings);
//        BundleVersionRO bundleVersion = new BundleVersionRO();
//        bundleVersion.setType(type);
//        bundleVersion.setId(id);
//        bundleVersion.setVersion(String.valueOf(version));
//        dto.setBundleVersion(bundleVersion);
//        return dto;
//    }
//
//    public JSONObject doGatherForSxf(Integer id, String captchaCode, Node node) {
//        ResultRO<List<DeviceProfileRO>> resultRO = this.whaleDeviceProfile.pullConfigAsync_SXF(this.gatherId, this.sessionUuid, captchaCode);
//        JSONObject parseResult = this.parseJsonObject(resultRO, this.gatherId, node, id);
//        return parseResult;
//    }
//
//    public Node predefindOrCustom(Node node, ControllerSettingsRO controllerSettings) {
//        if (controllerSettings != null && !StringUtils.isEmpty(controllerSettings.getCustomPythonPath())) {
//            node.setGatherType(1);
//            node.setCustomPythonPath(controllerSettings.getCustomPythonPath());
//        } else {
//            node.setGatherType(0);
//            node.setCustomPythonPath("");
//        }
//
//        return node;
//    }
//
//    public List<Node> findAll() {
//        List<Node> nodes = this.nodeDAO.getAllDeviceUuid();
//        return nodes;
//    }
//
//    public ResultRO<List<Node>> getChangeDevice(Integer size) {
//        ResultRO<List<Node>> resultRO = new ResultRO(true);
//        List<Node> list = this.nodeDAO.getChangeDevice();
//        if (list != null && list.size() != 0) {
//            List<Node> dataList = new ArrayList();
//            RevisionSearchDTO searchDTO = new RevisionSearchDTO();
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime lastDay = now.plus(-24L, ChronoUnit.HOURS);
//            ZonedDateTime zdt = now.atZone(ZoneOffset.UTC);
//            String isoNow = zdt.toString();
//            ZonedDateTime zdt1 = lastDay.atZone(ZoneOffset.UTC);
//            String isoLastDay = zdt1.toString();
//            searchDTO.setEndTime(isoNow);
//            searchDTO.setStartTime(isoLastDay);
//            Iterator var12 = list.iterator();
//
//            while(var12.hasNext()) {
//                Node node = (Node)var12.next();
//                searchDTO.setDeviceUuid(node.getUuid());
//                ResultRO<List<RevisionSearchRO>> revisionResultRO = this.whaleDeviceObjectClient.getRevisionSearch(searchDTO);
//                if (revisionResultRO != null && revisionResultRO.getData() != null) {
//                    List<RevisionSearchRO> revisionSearchROList = (List)revisionResultRO.getData();
//                    Iterator var16 = revisionSearchROList.iterator();
//
//                    while(var16.hasNext()) {
//                        RevisionSearchRO ro = (RevisionSearchRO)var16.next();
//                        if (ro.getType().equals(DeviceChangeTypeEnum.UPDATE.getCode())) {
//                            ChangeCountsRO changeCountsRO = ro.getChangeCounts();
//                            if (changeCountsRO != null) {
//                                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(changeCountsRO));
//                                node.setChangeItem(jsonObject);
//                                dataList.add(node);
//                            }
//                            break;
//                        }
//                    }
//
//                    if (size != null && dataList.size() >= size) {
//                        break;
//                    }
//                }
//            }
//
//            resultRO.setTotal(dataList.size());
//            resultRO.setData(dataList);
//            return resultRO;
//        } else {
//            return resultRO;
//        }
//    }
//
//    public int updateNodeBranchLevel(Integer[] nodeIds, String branchLevel) {
//        int num = this.nodeRepository.updateNodeBranchLevel(branchLevel, nodeIds);
//        if (nodeIds != null && nodeIds.length > 0) {
//            Integer[] var4 = nodeIds;
//            int var5 = nodeIds.length;
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                Integer nodeId = var4[var6];
//                Node theNode = this.nodeDAO.getTheNode(nodeId);
//                if (theNode != null) {
//                    if (theNode.getIp().contains("(")) {
//                        Node node = this.nodeDAO.getTheNodeByIP(theNode.getIp().substring(0, theNode.getIp().indexOf("(")));
//                        List<Integer> ids = this.nodeDAO.listVsysNodeByIp(node.getIp());
//                        ids.add(Integer.parseInt(node.getId()));
//                        if (ids != null && ids.size() > 0) {
//                            this.nodeRepository.updateNodeBranchLevel(branchLevel, (Integer[])ids.toArray(new Integer[0]));
//                        }
//                    } else {
//                        List<Integer> ids = this.nodeDAO.listVsysNodeByIp(theNode.getIp());
//                        if (ids != null && ids.size() > 0) {
//                            this.nodeRepository.updateNodeBranchLevel(branchLevel, (Integer[])ids.toArray(new Integer[0]));
//                        }
//                    }
//                }
//            }
//        }
//
//        return num;
//    }
//
//    private boolean isShenxinfuFw(String vendorId, String deviceType) {
//        return !StringUtils.isEmpty(vendorId) && vendorId.equalsIgnoreCase(VendorEnum.SHENXINFU.getCode()) && deviceType.equals(NodeType.FIREWALL.getCode());
//    }
//
//    private boolean isShenxinfuFwWeb(String vendorId, String deviceType, String modelNumber) {
//        return !StringUtils.isEmpty(vendorId) && vendorId.equalsIgnoreCase(VendorEnum.SHENXINFU.getCode()) && deviceType.equals(NodeType.FIREWALL.getCode()) && (modelNumber.equalsIgnoreCase("Firewall(with captcha image)") || modelNumber.equalsIgnoreCase("Firewall v7.1"));
//    }
//
//    private boolean isShenxinfuFwApi(String vendorId, String deviceType, String modelNumber) {
//        return !StringUtils.isEmpty(vendorId) && vendorId.equalsIgnoreCase(VendorEnum.SHENXINFU.getCode()) && deviceType.equals(NodeType.FIREWALL.getCode()) && modelNumber.equalsIgnoreCase("Firewall af v8.0.25");
//    }
//
//    public ReturnT<String> updateCredential(String credentialUuid, String pushCredentialUuid, Integer[] ids) {
//        try {
//            Integer[] var4 = ids;
//            int var5 = ids.length;
//
//            for(int var6 = 0; var6 < var5; ++var6) {
//                Integer id = var4[var6];
//                Node theNode = this.nodeDAO.getTheNode(id);
//                if (org.apache.commons.lang3.StringUtils.isNotEmpty(theNode.getGatherId())) {
//                    String gatherId = theNode.getGatherId();
//                    ResultRO<List<CreateProfileRO>> profile = this.whaleDeviceProfile.getProfile(gatherId);
//                    CreateProfileRO createProfileRO = (CreateProfileRO)((List)profile.getData()).get(0);
//                    ((ControllerSettingsRO)createProfileRO.getControllerSettings().get(0)).setCredentialUuid(credentialUuid);
//                    ResultRO<List<CreateProfileRO>> resultRO = this.whaleDeviceProfile.updateProfile(gatherId, createProfileRO);
//                    if (resultRO == null || !resultRO.getSuccess() || resultRO.getData() == null || ((List)resultRO.getData()).isEmpty()) {
//                        return new ReturnT(-1, "修改失败，更新设备的采集配置时，返回空");
//                    }
//                }
//            }
//        } catch (Exception var13) {
//            logger.info("修改失败，更新设备的采集配置时异常");
//            return new ReturnT(-1, "修改失败，更新设备的采集配置时异常");
//        }
//
//        int num = this.nodeRepository.updateCredential(credentialUuid, pushCredentialUuid, ids);
//        return num > 0 ? ReturnT.SUCCESS : ReturnT.FAIL;
//    }
//
//    public ReturnT<String> updateGatherCycle(String gatherCycle, Integer[] ids) {
//        int num = this.nodeRepository.updateGatherCycle(gatherCycle, ids);
//        return num > 0 ? ReturnT.SUCCESS : ReturnT.FAIL;
//    }
//
//    public String initCharset(String vendorId, String charset) {
//        return !StringUtils.isEmpty(charset) || StringUtils.isEmpty(vendorId) || !vendorId.equalsIgnoreCase(VendorEnum.HUAWEI.getCode()) && !vendorId.equalsIgnoreCase(VendorEnum.TOPSEC.getCode()) ? "UTF-8" : "GB2312";
//    }
//}
