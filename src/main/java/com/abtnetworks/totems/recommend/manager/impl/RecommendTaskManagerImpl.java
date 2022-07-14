package com.abtnetworks.totems.recommend.manager.impl;

import com.abtnetworks.data.totems.log.client.LogClientSimple;
import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.advanced.dto.DeviceDTO;
import com.abtnetworks.totems.advanced.service.AdvancedSettingService;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.config.VmwareInterfaceStatusConfig;
import com.abtnetworks.totems.common.constants.AdvancedSettingsConstants;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.dto.CmdDTO;
import com.abtnetworks.totems.common.dto.PolicyDTO;
import com.abtnetworks.totems.common.dto.TaskDTO;
import com.abtnetworks.totems.common.dto.commandline.DNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.NatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.SNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.common.enums.ConnectTypeEnum;
import com.abtnetworks.totems.common.enums.IpTypeEnum;
import com.abtnetworks.totems.common.enums.PolicyEnum;
import com.abtnetworks.totems.common.enums.TaskTypeEnum;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.DateUtil;
import com.abtnetworks.totems.common.utils.DateUtils;
import com.abtnetworks.totems.common.utils.EntityUtils;
import com.abtnetworks.totems.common.utils.FileUtils;
import com.abtnetworks.totems.common.utils.ProtocolUtils;
import com.abtnetworks.totems.common.utils.ZipUtil;
import com.abtnetworks.totems.disposal.entity.DisposalScenesEntity;
import com.abtnetworks.totems.disposal.service.DisposalScenesService;
import com.abtnetworks.totems.external.vo.DeviceDetailRunVO;
import com.abtnetworks.totems.external.vo.PolicyDetailVO;
import com.abtnetworks.totems.push.dao.mysql.PushRecommendTaskExpandMapper;
import com.abtnetworks.totems.push.dto.PushStatus;
import com.abtnetworks.totems.push.dto.StaticRoutingDTO;
import com.abtnetworks.totems.push.entity.PushRecommendTaskExpandEntity;
import com.abtnetworks.totems.push.service.task.PushTaskService;
import com.abtnetworks.totems.push.vo.PushTaskVO;
import com.abtnetworks.totems.recommend.dao.mysql.CheckResultMapper;
import com.abtnetworks.totems.recommend.dao.mysql.CommandTaskEdiableMapper;
import com.abtnetworks.totems.recommend.dao.mysql.MergedPolicyMapper;
import com.abtnetworks.totems.recommend.dao.mysql.NodeMapper;
import com.abtnetworks.totems.recommend.dao.mysql.PathDetailMapper;
import com.abtnetworks.totems.recommend.dao.mysql.PathDeviceDetailMapper;
import com.abtnetworks.totems.recommend.dao.mysql.PathInfoMapper;
import com.abtnetworks.totems.recommend.dao.mysql.PolicyRecommendCredentialMapper;
import com.abtnetworks.totems.recommend.dao.mysql.PolicyRiskMapper;
import com.abtnetworks.totems.recommend.dao.mysql.RecommendPolicyMapper;
import com.abtnetworks.totems.recommend.dao.mysql.RecommendTaskCheckMapper;
import com.abtnetworks.totems.recommend.dao.mysql.RecommendTaskMapper;
import com.abtnetworks.totems.recommend.dao.mysql.RiskRuleInfoMapper;
import com.abtnetworks.totems.recommend.dto.global.VmwareSdnBusinessDTO;
import com.abtnetworks.totems.recommend.dto.push.TaskStatusBranchLevelsDTO;
import com.abtnetworks.totems.recommend.dto.task.SearchRecommendTaskDTO;
import com.abtnetworks.totems.recommend.entity.CheckResultEntity;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.DNatAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.DeviceDimension;
import com.abtnetworks.totems.recommend.entity.NatAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.PathDetailEntity;
import com.abtnetworks.totems.recommend.entity.PathDeviceDetailEntity;
import com.abtnetworks.totems.recommend.entity.PathInfoEntity;
import com.abtnetworks.totems.recommend.entity.PolicyRiskEntity;
import com.abtnetworks.totems.recommend.entity.PushAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.RecommendPolicyEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskCheckEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.RiskRuleDetailEntity;
import com.abtnetworks.totems.recommend.entity.RiskRuleInfoEntity;
import com.abtnetworks.totems.recommend.entity.SNatAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.StaticNatAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.abtnetworks.totems.recommend.manager.WhaleManager;
import com.abtnetworks.totems.recommend.service.GlobalRecommendService;
import com.abtnetworks.totems.recommend.vo.BatchTaskVO;
import com.abtnetworks.totems.recommend.vo.PathDetailVO;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendSecurityPolicyVO;
import com.abtnetworks.totems.recommend.vo.PolicyTaskDetailVO;
import com.abtnetworks.totems.recommend.vo.PolicyVO;
import com.abtnetworks.totems.recommend.vo.RecommendPolicyVO;
import com.abtnetworks.totems.recommend.vo.TaskStatusVO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
import com.abtnetworks.totems.whale.baseapi.ro.ObjectDetailRO;
import com.abtnetworks.totems.whale.baseapi.service.IpServiceNameRefClient;
import com.abtnetworks.totems.whale.policy.ro.DeviceDetailRO;
import com.abtnetworks.totems.whale.policy.service.WhalePathAnalyzeClient;
import com.abtnetworks.totems.whale.policyoptimize.ro.RuleCheckResultDataRO;
import com.abtnetworks.totems.whale.policyoptimize.ro.RuleCheckResultRO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendTaskManagerImpl implements RecommendTaskManager {
    private static Logger logger = Logger.getLogger(RecommendTaskManagerImpl.class);
    private static final String NO_CONTENT_MARK = "--";
    private static final String UNKNOWN_DEVICE = "未知设备";
    @Autowired
    private NodeMapper policyRecommendNodeMapper;
    @Autowired
    private PolicyRecommendCredentialMapper credentialMapper;
    @Autowired
    private RiskRuleInfoMapper riskMapper;
    @Autowired
    private PathInfoMapper pathInfoMapper;
    @Autowired
    private RecommendTaskMapper recommendTaskMapper;
    @Autowired
    private PathDetailMapper pathDetailMapper;
    @Autowired
    private PathDeviceDetailMapper pathDeviceDetailMapper;
    @Autowired
    private PolicyRiskMapper policyRiskMapper;
    @Autowired
    private RecommendPolicyMapper recommendPolicyMapper;
    @Autowired
    private CheckResultMapper checkResultMapper;
    @Autowired
    private CommandTaskEdiableMapper commandTaskEditableMapper;
    @Autowired
    private AdvancedSettingService advancedSettingService;
    @Autowired
    private MergedPolicyMapper mergedPolicyMapper;
    @Autowired
    private CommandTaskEdiableMapper commandTaskEdiableMapper;
    @Autowired
    private RecommendTaskCheckMapper recommendTaskCheckMapper;
    @Autowired
    RemoteBranchService remoteBranchService;
    @Autowired
    private WhalePathAnalyzeClient client;
    @Value("${topology.whale-server-prefix}")
    private String whaleServerPrefix;
    @Autowired
    private IpServiceNameRefClient ipServiceNameRefClient;
    @Autowired
    public DisposalScenesService disposalScenesService;
    @Autowired
    private LogClientSimple logClientSimple;
    @Autowired
    CommandTaskManager commandTaskManager;
    @Autowired
    WhaleManager whaleManager;
    @Autowired
    PushTaskService pushTaskService;
    @Autowired
    GlobalRecommendService globalRecommendService;
    @Autowired
    VmwareInterfaceStatusConfig vmwareInterfaceStatusConfig;
    @Autowired
    private PushRecommendTaskExpandMapper pushRecommendTaskExpandMapper;

    public RecommendTaskManagerImpl() {
    }

    public int getGatherStateByDeviceUuid(String uuid) {
        return this.policyRecommendNodeMapper.getGatherStateByDeviceUuid(uuid);
    }

    public NodeEntity getTheNodeByUuid(String deviceUuid) {
        return this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
    }

    public String getDeviceModelNumber(String uuid) {
        return this.policyRecommendNodeMapper.getDeviceModelNumber(uuid);
    }

    public String getDeviceName(String uuid) {
        return this.policyRecommendNodeMapper.getDeviceName(uuid);
    }

    public Integer getDeviceGatherPort(String uuid) {
        return this.policyRecommendNodeMapper.getDeviceGatherPort(uuid);
    }

    public List<RiskRuleInfoEntity> getRiskInfoBySecondSortId(int secondSortId) {
        return this.riskMapper.getRiskInfoBySecondSortId(secondSortId);
    }

    public RiskRuleDetailEntity getRiskDetailEntityByRuleId(String ruleId) {
        List<RiskRuleDetailEntity> list = this.riskMapper.getRiskDetailByRuleId(ruleId);
        return list != null && list.size() != 0 ? (RiskRuleDetailEntity)list.get(0) : null;
    }

    public List<CommandTaskEditableEntity> listPolicyRecommendPolicyByTaskIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        } else {
            String[] arr = ids.split(",");
            List<Integer> taskIdList = new ArrayList();
            String[] var4 = arr;
            int var5 = arr.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String id = var4[var6];
                taskIdList.add(Integer.valueOf(id));
            }

            List<CommandTaskEditableEntity> list = new ArrayList();
            Iterator var9 = taskIdList.iterator();

            while(var9.hasNext()) {
                Integer id = (Integer)var9.next();
                List<CommandTaskEditableEntity> taskEntityList = this.commandTaskEditableMapper.selectByTaskId(id);
                list.addAll(taskEntityList);
            }

            return list;
        }
    }

    public String getRecommendZip(String ids, String pathPrefix) {
        List<CommandTaskEditableEntity> list = this.listPolicyRecommendPolicyByTaskIds(ids);
        if (list != null && list.size() != 0) {
            Map<String, List<String>> map = new LinkedHashMap();
            boolean hasFile = false;
            Iterator var6 = list.iterator();

            String destDirName;
            String zipFilePath;
            while(var6.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var6.next();
                if (!StringUtils.isBlank(entity.getCommandline())) {
                    destDirName = entity.getDeviceUuid();
                    NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(destDirName);
                    if (nodeEntity == null) {
                        logger.info(String.format("设备(%s)已被删除，不下载该设备命令行...", destDirName));
                    } else {
                        hasFile = true;
                        zipFilePath = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                        List<String> deviceList = (List)map.get(zipFilePath);
                        if (deviceList == null) {
                            deviceList = new ArrayList();
                        }

                        ((List)deviceList).add(entity.getCommandline());
                        map.put(zipFilePath, deviceList);
                    }
                }
            }

            if (!hasFile) {
                return null;
            } else {
                Timestamp currnetTime = DateUtil.getCurrentTimestamp();
                String standardDateTime = DateUtil.getTimeStamp(currnetTime);
                destDirName = pathPrefix + "/" + "push_command_file";
                if (!(new File(destDirName)).exists()) {
                    FileUtils.createDir(destDirName);
                }

                List<String> fileList = new ArrayList();

                String filePath;
                for(Iterator var39 = map.entrySet().iterator(); var39.hasNext(); fileList.add(filePath)) {
                    Entry entry = (Entry)var39.next();
                    String key = entry.getKey().toString();
                    List<String> deviceList = (List)entry.getValue();
                    filePath = destDirName + "/" + key + "_" + standardDateTime + ".txt";
                    File file = new File(filePath);

                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException var33) {
                        logger.error("文件创建异常", var33);
                    }

                    FileWriter fw = null;
                    BufferedWriter bw = null;

                    try {
                        fw = new FileWriter(file, false);
                        bw = new BufferedWriter(fw);
                        Iterator var18 = deviceList.iterator();

                        while(var18.hasNext()) {
                            String device = (String)var18.next();
                            if (device.contains("\n")) {
                                device = device.replace("\n", "\r\n");
                            }

                            bw.write(device);
                            bw.write("\r\n");
                            bw.write("\r\n");
                            bw.write("\r\n");
                        }
                    } catch (Exception var34) {
                        logger.error("写入txt文件异常", var34);
                    } finally {
                        try {
                            if (bw != null) {
                                bw.close();
                            }

                            if (fw != null) {
                                fw.close();
                            }
                        } catch (Exception var30) {
                            logger.error("流关闭异常", var30);
                        }

                    }
                }

                if (!hasFile) {
                    zipFilePath = destDirName + "/暂无记录.txt";
                    File file = new File(zipFilePath);

                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException var32) {
                        logger.error("文件创建异常", var32);
                    }

                    fileList.add(zipFilePath);
                }

                zipFilePath = destDirName + "/策略命令行_" + standardDateTime;

                try {
                    ZipUtil.writeZip(fileList, zipFilePath);
                } catch (IOException var31) {
                    logger.error("打压缩包异常: ", var31);
                }

                return zipFilePath + ".zip";
            }
        } else {
            return null;
        }
    }

    public PageInfo<PathInfoEntity> getAnalyzePathInfoVOList(int taskId, int page, int psize) {
        PageHelper.startPage(page, psize);
        List<PathInfoEntity> list = this.pathInfoMapper.selectByTaskId(taskId);
        Iterator var5 = list.iterator();

        while(true) {
            PathInfoEntity entity;
            do {
                if (!var5.hasNext()) {
                    PageInfo<PathInfoEntity> pageInfo = new PageInfo(list);
                    return pageInfo;
                }

                entity = (PathInfoEntity)var5.next();
            } while(!StringUtils.isNotBlank(entity.getService()));

            JSONArray array = JSONArray.parseArray(entity.getService());
            List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
            Iterator var9 = serviceList.iterator();

            while(var9.hasNext()) {
                ServiceDTO service = (ServiceDTO)var9.next();
                service.setSrcPorts(AliStringUtils.isEmpty(service.getSrcPorts()) ? null : service.getSrcPorts());
                service.setDstPorts(AliStringUtils.isEmpty(service.getDstPorts()) ? null : service.getDstPorts());
                service.setProtocol(ProtocolUtils.getProtocolByString(service.getProtocol()));
            }

            entity.setService(JSONObject.toJSONString(serviceList));
        }
    }

    public PageInfo<PathInfoEntity> getVerifyPathInfoVOList(int taskId, int page, int psize) {
        return this.getAnalyzePathInfoVOList(taskId, page, psize);
    }

    public PageInfo<RecommendTaskEntity> getTaskList(SearchRecommendTaskDTO searchRecommendTaskDTO) {
        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getProtocol()) && "0".equals(searchRecommendTaskDTO.getProtocol())) {
            searchRecommendTaskDTO.setIsServiceAny(true);
        } else {
            searchRecommendTaskDTO.setIsServiceAny(false);
        }

        List<RecommendTaskEntity> list = this.searchRecommendTaskList(searchRecommendTaskDTO);
        List<PathInfoEntity> pathInfoEntityList = new ArrayList();
        if (ObjectUtils.isNotEmpty(list)) {
            List<Integer> taskIdList = (List)list.stream().map((task) -> {
                return task.getId();
            }).collect(Collectors.toList());
            Map<String, Object> cond = new HashMap();
            cond.put("ids", taskIdList);
            pathInfoEntityList = this.pathInfoMapper.selectByIdList(cond);
        }

        RecommendTaskEntity entity;
        for(Iterator var10 = list.iterator(); var10.hasNext(); entity.setNsStatus(entity.getStatus())) {
            entity = (RecommendTaskEntity)var10.next();
            if (!AliStringUtils.isEmpty(entity.getServiceList())) {
                JSONArray jsonArray = JSONArray.parseArray(entity.getServiceList());
                List<ServiceDTO> serviceList = jsonArray.toJavaList(ServiceDTO.class);
                Iterator var8 = serviceList.iterator();

                while(var8.hasNext()) {
                    ServiceDTO service = (ServiceDTO)var8.next();
                    service.setProtocol(ProtocolUtils.getProtocolByString(service.getProtocol()));
                    service.setSrcPorts(AliStringUtils.isEmpty(service.getSrcPorts()) ? null : service.getSrcPorts());
                    service.setDstPorts(AliStringUtils.isEmpty(service.getDstPorts()) ? null : service.getDstPorts());
                }

                entity.setServiceList(JSONObject.toJSONString(serviceList));
            }

            if (ObjectUtils.isNotEmpty(pathInfoEntityList)) {
                String pathAnalyzeStatus = this.getTaskPathAnalyzeStatusByTaskId(entity.getId(), (List)pathInfoEntityList);
                if (entity.getStatus() >= 21) {
                    pathAnalyzeStatus = this.getTaskVerifyPathStatusByTaskId(entity.getId(), (List)pathInfoEntityList);
                }

                if (StringUtils.isEmpty(pathAnalyzeStatus) && 0 != entity.getStatus()) {
                    entity.setPathAnalyzeStatus("11:1");
                } else {
                    entity.setPathAnalyzeStatus(pathAnalyzeStatus);
                }
            }
        }

        if (this.vmwareInterfaceStatusConfig.isVmInterfaceAvailable()) {
            this.mergeESWNTaskStatus(list);
        }

        PageInfo<RecommendTaskEntity> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    void mergeESWNTaskStatus(List<RecommendTaskEntity> list) {
        if (!CollectionUtils.isEmpty(list)) {
            Iterator var2 = list.iterator();

            while(true) {
                RecommendTaskEntity entity;
                do {
                    if (!var2.hasNext()) {
                        return;
                    }

                    entity = (RecommendTaskEntity)var2.next();
                } while(entity.getWeTaskId() == null);

                try {
                    VmwareSdnBusinessDTO vmwareSdnBusinessDTO = this.globalRecommendService.getWETaskByWETaskId(entity.getWeTaskId());
                    if (vmwareSdnBusinessDTO != null) {
                        entity.setWeStatus(vmwareSdnBusinessDTO.getStatus());
                        if ("7".equals(vmwareSdnBusinessDTO.getRemark())) {
                            entity.setWeStatus(7);
                        }

                        int weTaskStatus = vmwareSdnBusinessDTO.getStatus();
                        int nsTaskStatus = entity.getStatus();
                        int status;
                        if (entity.getTaskType() == 16) {
                            status = weTaskStatus;
                        } else if (weTaskStatus == 20 && this.isAllPathExists(entity)) {
                            status = 20;
                        } else if ((weTaskStatus != 10 || !"7".equals(vmwareSdnBusinessDTO.getRemark())) && weTaskStatus != 20) {
                            if (weTaskStatus != 9 && nsTaskStatus != 9) {
                                if (weTaskStatus != 19 && nsTaskStatus != 19) {
                                    status = weTaskStatus < nsTaskStatus ? weTaskStatus : nsTaskStatus;
                                } else {
                                    status = 19;
                                }
                            } else {
                                status = 9;
                            }
                        } else {
                            status = nsTaskStatus;
                        }

                        entity.setStatus(status);
                    }
                } catch (Exception var8) {
                    logger.error(String.format("merge task status error,nsTaskId:%s,weTaskId:%s", entity.getId(), entity.getWeTaskId()));
                }
            }
        }
    }

    boolean isAllPathExists(RecommendTaskEntity entity) {
        boolean flag = true;

        try {
            String pathAnalyzeStatus = entity.getPathAnalyzeStatus();
            if (StringUtils.isNotEmpty(pathAnalyzeStatus)) {
                String[] args = pathAnalyzeStatus.split(",");
                String[] var5 = args;
                int var6 = args.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String arg = var5[var7];
                    String pathState = arg.split(":")[0];
                    if (4 != Integer.parseInt(pathState)) {
                        flag = false;
                    }
                }
            }

            return flag;
        } catch (Exception var10) {
            logger.warn("物理/云状态合并失败,nsTask:" + JSON.toJSONString(entity));
            return false;
        }
    }

    public PageInfo<PolicyTaskDetailVO> getNatPolicyTaskList(String theme, String type, int page, int psize, String taskIds, Integer id, String userName, String deviceUUID, String status, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        List<RecommendTaskEntity> list = this.searchNatTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, status, type, page, psize, taskIds, id, branchLevel, deviceUUID);
        PageInfo<RecommendTaskEntity> originalPageInfo = new PageInfo(list);
        List<PolicyTaskDetailVO> policyList = new ArrayList();
        Iterator var15 = list.iterator();

        while(true) {
            while(var15.hasNext()) {
                RecommendTaskEntity entity = (RecommendTaskEntity)var15.next();
                PolicyTaskDetailVO policyDetailVO = new PolicyTaskDetailVO();
                policyDetailVO.setUserName(entity.getUserName());
                policyDetailVO.setCreateTime(entity.getCreateTime());
                policyDetailVO.setTaskId(entity.getId());
                policyDetailVO.setPolicyName(entity.getTheme());
                policyDetailVO.setDeviceName("Unknown Device");
                policyDetailVO.setId(entity.getId());
                policyDetailVO.setPostSrcIpSystem(entity.getPostSrcIpSystem());
                policyDetailVO.setPostDstIpSystem(entity.getPostDstIpSystem());
                policyDetailVO.setPushStatus(entity.getStatus());
                if (!StringUtils.isNotBlank(entity.getServiceList())) {
                    ServiceDTO postService = new ServiceDTO();
                    postService.setProtocol("any");
                    List<ServiceDTO> serviceList = new ArrayList();
                    serviceList.add(postService);
                    policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                } else {
                    JSONArray array = JSONObject.parseArray(entity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    if (serviceList.size() <= 0) {
                        ServiceDTO postService = new ServiceDTO();
                        postService.setProtocol("any");
                        serviceList.add(postService);
                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    } else {
                        Iterator var20 = serviceList.iterator();

                        while(var20.hasNext()) {
                            ServiceDTO serviceDTO = (ServiceDTO)var20.next();
                            serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                        }

                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    }
                }

                JSONObject object;
                String deviceUuid;
                String deviceIp;
                if (entity.getTaskType().equals(5)) {
                    BeanUtils.copyProperties(entity, policyDetailVO);
                    policyDetailVO.setPushStatus(entity.getStatus());
                    logger.debug("static nat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                    object = JSONObject.parseObject(entity.getAdditionInfo());
                    if (object != null) {
                        StaticNatAdditionalInfoEntity staticNatAdditionalInfoEntity = (StaticNatAdditionalInfoEntity)object.toJavaObject(StaticNatAdditionalInfoEntity.class);
                        policyDetailVO.setPublicAddress(staticNatAdditionalInfoEntity.getGlobalAddress());
                        policyDetailVO.setPrivateAddress(staticNatAdditionalInfoEntity.getInsideAddress());
                        policyDetailVO.setPublicPort(staticNatAdditionalInfoEntity.getGlobalPort());
                        policyDetailVO.setPrivatePort(staticNatAdditionalInfoEntity.getInsidePort());
                        deviceUuid = staticNatAdditionalInfoEntity.getProtocol();
                        deviceIp = staticNatAdditionalInfoEntity.getDeviceUuid();
                        deviceIp = String.format("未知设备(%s)", deviceIp);
                        if (deviceIp != null) {
                            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceIp);
                            if (nodeEntity != null) {
                                deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                            }
                        }

                        policyDetailVO.setDeviceIp(deviceIp);
                        if (AliStringUtils.isEmpty(deviceUuid)) {
                            policyDetailVO.setProtocol("any");
                        } else {
                            policyDetailVO.setProtocol(ProtocolUtils.getProtocolByString(deviceUuid));
                        }

                        policyDetailVO.setSrcDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getFromZone(), staticNatAdditionalInfoEntity.getInDevItf()));
                        policyDetailVO.setDstDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getToZone(), staticNatAdditionalInfoEntity.getOutDevItf()));
                    }
                } else {
                    NodeEntity nodeEntity;
                    if (entity.getTaskType().equals(6)) {
                        BeanUtils.copyProperties(entity, policyDetailVO);
                        policyDetailVO.setPushStatus(entity.getStatus());
                        logger.debug("snat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                        object = JSONObject.parseObject(entity.getAdditionInfo());
                        if (object != null) {
                            SNatAdditionalInfoEntity sNatAdditionalInfoEntity = (SNatAdditionalInfoEntity)object.toJavaObject(SNatAdditionalInfoEntity.class);
                            policyDetailVO.setSrcDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getSrcZone(), sNatAdditionalInfoEntity.getSrcItf()));
                            policyDetailVO.setDstDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getDstZone(), sNatAdditionalInfoEntity.getDstItf()));
                            policyDetailVO.setPreSrcIp(entity.getSrcIp());
                            policyDetailVO.setPostSrcIp(sNatAdditionalInfoEntity.getPostIpAddress());
                            deviceUuid = sNatAdditionalInfoEntity.getDeviceUuid();
                            deviceIp = String.format("未知设备(%s)", deviceUuid);
                            if (deviceUuid != null) {
                                nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                if (nodeEntity != null) {
                                    deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                        }
                    } else {
                        List serviceList;
                        ServiceDTO serviceDTO;
                        String protocol;
                        JSONArray array;
                        ArrayList postServiceList;
                        ServiceDTO postService;
                        Iterator var48;
                        if (entity.getTaskType().equals(7)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                DNatAdditionalInfoEntity dnatAdditionalInfoEntity = (DNatAdditionalInfoEntity)object.toJavaObject(DNatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getSrcZone(), dnatAdditionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getDstZone(), dnatAdditionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(dnatAdditionalInfoEntity.getPostIpAddress());
                                deviceUuid = dnatAdditionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var48 = serviceList.iterator(); var48.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var48.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(9)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                NatAdditionalInfoEntity additionalInfoEntity = (NatAdditionalInfoEntity)object.toJavaObject(NatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreSrcIp(entity.getSrcIp());
                                policyDetailVO.setPostSrcIp(additionalInfoEntity.getPostSrcIp());
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(additionalInfoEntity.getPostDstIp());
                                deviceUuid = additionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var48 = serviceList.iterator(); var48.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var48.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            postService.setProtocol(protocol);
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(additionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(additionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(additionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(20)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            policyDetailVO.setUserName(entity.getUserName());
                            PushRecommendTaskExpandEntity expandEntity = this.pushRecommendTaskExpandMapper.getByTaskId(entity.getId());
                            if (null == expandEntity) {
                                logger.info(String.format("根据任务id:%d查询拓展数据为空", entity.getId()));
                                continue;
                            }

                            deviceIp = "未知设备";
                            deviceUuid = expandEntity.getDeviceUuid();
                            policyDetailVO.setDescription(entity.getDescription());
                            if (StringUtils.isNotBlank(deviceUuid)) {
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                        policyDetailVO.setDeviceName(nodeEntity.getDeviceName());
                                    }
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                            if (StringUtils.isNotEmpty(expandEntity.getStaticRoutingInfo())) {
                                StaticRoutingDTO staticRoutingDTO = (StaticRoutingDTO)JSONObject.toJavaObject(JSONObject.parseObject(expandEntity.getStaticRoutingInfo()), StaticRoutingDTO.class);
                                policyDetailVO.setSrcVirtualRouter(staticRoutingDTO.getSrcVirtualRouter());
                                policyDetailVO.setDstVirtualRouter(staticRoutingDTO.getDstVirtualRouter());
                                policyDetailVO.setNextHop(staticRoutingDTO.getNextHop());
                                policyDetailVO.setSubnetMask(staticRoutingDTO.getSubnetMask());
                                policyDetailVO.setOutInterface(staticRoutingDTO.getOutInterface());
                                policyDetailVO.setPriority(staticRoutingDTO.getPriority());
                                policyDetailVO.setManagementDistance(staticRoutingDTO.getManagementDistance());
                            }
                        }
                    }
                }

                policyList.add(policyDetailVO);
            }

            PageInfo<PolicyTaskDetailVO> pageInfo = new PageInfo(policyList);
            pageInfo.setTotal(originalPageInfo.getTotal());
            pageInfo.setStartRow(originalPageInfo.getStartRow());
            pageInfo.setEndRow(originalPageInfo.getEndRow());
            pageInfo.setPageSize(originalPageInfo.getPageSize());
            pageInfo.setPageNum(originalPageInfo.getPageNum());
            return pageInfo;
        }
    }

    public PageInfo<PolicyTaskDetailVO> getNatPolicyTaskList(String theme, String type, int page, int psize, String taskIds, Integer id, String userName, String deviceUUID, String status, Authentication authentication, String branchLevel) {
        if(branchLevel == null || branchLevel.equals("")){
            branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        }else{
            branchLevel = branchLevel + "%";
        }
        List<RecommendTaskEntity> list = this.searchNatTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, status, type, page, psize, taskIds, id, branchLevel, deviceUUID);
        PageInfo<RecommendTaskEntity> originalPageInfo = new PageInfo(list);
        List<PolicyTaskDetailVO> policyList = new ArrayList();
        Iterator var15 = list.iterator();

        while(true) {
            while(var15.hasNext()) {
                RecommendTaskEntity entity = (RecommendTaskEntity)var15.next();
                PolicyTaskDetailVO policyDetailVO = new PolicyTaskDetailVO();
                policyDetailVO.setUserName(entity.getUserName());
                policyDetailVO.setCreateTime(entity.getCreateTime());
                policyDetailVO.setTaskId(entity.getId());
                policyDetailVO.setPolicyName(entity.getTheme());
                policyDetailVO.setDeviceName("Unknown Device");
                policyDetailVO.setId(entity.getId());
                policyDetailVO.setPostSrcIpSystem(entity.getPostSrcIpSystem());
                policyDetailVO.setPostDstIpSystem(entity.getPostDstIpSystem());
                policyDetailVO.setPushStatus(entity.getStatus());
                if (!StringUtils.isNotBlank(entity.getServiceList())) {
                    ServiceDTO postService = new ServiceDTO();
                    postService.setProtocol("any");
                    List<ServiceDTO> serviceList = new ArrayList();
                    serviceList.add(postService);
                    policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                } else {
                    JSONArray array = JSONObject.parseArray(entity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    if (serviceList.size() <= 0) {
                        ServiceDTO postService = new ServiceDTO();
                        postService.setProtocol("any");
                        serviceList.add(postService);
                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    } else {
                        Iterator var20 = serviceList.iterator();

                        while(var20.hasNext()) {
                            ServiceDTO serviceDTO = (ServiceDTO)var20.next();
                            serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                        }

                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    }
                }

                JSONObject object;
                String deviceUuid;
                String deviceIp;
                if (entity.getTaskType().equals(5)) {
                    BeanUtils.copyProperties(entity, policyDetailVO);
                    policyDetailVO.setPushStatus(entity.getStatus());
                    logger.debug("static nat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                    object = JSONObject.parseObject(entity.getAdditionInfo());
                    if (object != null) {
                        StaticNatAdditionalInfoEntity staticNatAdditionalInfoEntity = (StaticNatAdditionalInfoEntity)object.toJavaObject(StaticNatAdditionalInfoEntity.class);
                        policyDetailVO.setPublicAddress(staticNatAdditionalInfoEntity.getGlobalAddress());
                        policyDetailVO.setPrivateAddress(staticNatAdditionalInfoEntity.getInsideAddress());
                        policyDetailVO.setPublicPort(staticNatAdditionalInfoEntity.getGlobalPort());
                        policyDetailVO.setPrivatePort(staticNatAdditionalInfoEntity.getInsidePort());
                        deviceUuid = staticNatAdditionalInfoEntity.getProtocol();
                        deviceIp = staticNatAdditionalInfoEntity.getDeviceUuid();
                        deviceIp = String.format("未知设备(%s)", deviceIp);
                        if (deviceIp != null) {
                            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceIp);
                            if (nodeEntity != null) {
                                deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                            }
                        }

                        policyDetailVO.setDeviceIp(deviceIp);
                        if (AliStringUtils.isEmpty(deviceUuid)) {
                            policyDetailVO.setProtocol("any");
                        } else {
                            policyDetailVO.setProtocol(ProtocolUtils.getProtocolByString(deviceUuid));
                        }

                        policyDetailVO.setSrcDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getFromZone(), staticNatAdditionalInfoEntity.getInDevItf()));
                        policyDetailVO.setDstDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getToZone(), staticNatAdditionalInfoEntity.getOutDevItf()));
                    }
                } else {
                    NodeEntity nodeEntity;
                    if (entity.getTaskType().equals(6)) {
                        BeanUtils.copyProperties(entity, policyDetailVO);
                        policyDetailVO.setPushStatus(entity.getStatus());
                        logger.debug("snat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                        object = JSONObject.parseObject(entity.getAdditionInfo());
                        if (object != null) {
                            SNatAdditionalInfoEntity sNatAdditionalInfoEntity = (SNatAdditionalInfoEntity)object.toJavaObject(SNatAdditionalInfoEntity.class);
                            policyDetailVO.setSrcDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getSrcZone(), sNatAdditionalInfoEntity.getSrcItf()));
                            policyDetailVO.setDstDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getDstZone(), sNatAdditionalInfoEntity.getDstItf()));
                            policyDetailVO.setPreSrcIp(entity.getSrcIp());
                            policyDetailVO.setPostSrcIp(sNatAdditionalInfoEntity.getPostIpAddress());
                            deviceUuid = sNatAdditionalInfoEntity.getDeviceUuid();
                            deviceIp = String.format("未知设备(%s)", deviceUuid);
                            if (deviceUuid != null) {
                                nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                if (nodeEntity != null) {
                                    deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                        }
                    } else {
                        List serviceList;
                        ServiceDTO serviceDTO;
                        String protocol;
                        JSONArray array;
                        ArrayList postServiceList;
                        ServiceDTO postService;
                        Iterator var48;
                        if (entity.getTaskType().equals(7)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                DNatAdditionalInfoEntity dnatAdditionalInfoEntity = (DNatAdditionalInfoEntity)object.toJavaObject(DNatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getSrcZone(), dnatAdditionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getDstZone(), dnatAdditionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(dnatAdditionalInfoEntity.getPostIpAddress());
                                deviceUuid = dnatAdditionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var48 = serviceList.iterator(); var48.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var48.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(9)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                NatAdditionalInfoEntity additionalInfoEntity = (NatAdditionalInfoEntity)object.toJavaObject(NatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreSrcIp(entity.getSrcIp());
                                policyDetailVO.setPostSrcIp(additionalInfoEntity.getPostSrcIp());
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(additionalInfoEntity.getPostDstIp());
                                deviceUuid = additionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var48 = serviceList.iterator(); var48.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var48.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            postService.setProtocol(protocol);
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(additionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(additionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(additionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(20)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            policyDetailVO.setUserName(entity.getUserName());
                            PushRecommendTaskExpandEntity expandEntity = this.pushRecommendTaskExpandMapper.getByTaskId(entity.getId());
                            if (null == expandEntity) {
                                logger.info(String.format("根据任务id:%d查询拓展数据为空", entity.getId()));
                                continue;
                            }

                            deviceIp = "未知设备";
                            deviceUuid = expandEntity.getDeviceUuid();
                            policyDetailVO.setDescription(entity.getDescription());
                            if (StringUtils.isNotBlank(deviceUuid)) {
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                        policyDetailVO.setDeviceName(nodeEntity.getDeviceName());
                                    }
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                            if (StringUtils.isNotEmpty(expandEntity.getStaticRoutingInfo())) {
                                StaticRoutingDTO staticRoutingDTO = (StaticRoutingDTO)JSONObject.toJavaObject(JSONObject.parseObject(expandEntity.getStaticRoutingInfo()), StaticRoutingDTO.class);
                                policyDetailVO.setSrcVirtualRouter(staticRoutingDTO.getSrcVirtualRouter());
                                policyDetailVO.setDstVirtualRouter(staticRoutingDTO.getDstVirtualRouter());
                                policyDetailVO.setNextHop(staticRoutingDTO.getNextHop());
                                policyDetailVO.setSubnetMask(staticRoutingDTO.getSubnetMask());
                                policyDetailVO.setOutInterface(staticRoutingDTO.getOutInterface());
                                policyDetailVO.setPriority(staticRoutingDTO.getPriority());
                                policyDetailVO.setManagementDistance(staticRoutingDTO.getManagementDistance());
                            }
                        }
                    }
                }

                policyList.add(policyDetailVO);
            }

            PageInfo<PolicyTaskDetailVO> pageInfo = new PageInfo(policyList);
            pageInfo.setTotal(originalPageInfo.getTotal());
            pageInfo.setStartRow(originalPageInfo.getStartRow());
            pageInfo.setEndRow(originalPageInfo.getEndRow());
            pageInfo.setPageSize(originalPageInfo.getPageSize());
            pageInfo.setPageNum(originalPageInfo.getPageNum());
            return pageInfo;
        }
    }

    public List<PolicyTaskDetailVO> getNatTaskList(String theme, String type, String taskIds, Integer id, String userName, String deviceUUID, String startTime, String endTime, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        List<RecommendTaskEntity> list = this.searchNatPolicyTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, startTime, endTime, type, taskIds, id, branchLevel, deviceUUID);
        List<PolicyTaskDetailVO> policyList = new ArrayList();
        Iterator var13 = list.iterator();

        while(true) {
            while(var13.hasNext()) {
                RecommendTaskEntity entity = (RecommendTaskEntity)var13.next();
                PolicyTaskDetailVO policyDetailVO = new PolicyTaskDetailVO();
                policyDetailVO.setUserName(entity.getUserName());
                policyDetailVO.setCreateTime(entity.getCreateTime());
                policyDetailVO.setTaskId(entity.getId());
                policyDetailVO.setPolicyName(entity.getTheme());
                policyDetailVO.setDeviceName("Unknown Device");
                policyDetailVO.setId(entity.getId());
                policyDetailVO.setPostSrcIpSystem(entity.getPostSrcIpSystem());
                policyDetailVO.setPostDstIpSystem(entity.getPostDstIpSystem());
                policyDetailVO.setPushStatus(entity.getStatus());
                if (!StringUtils.isNotBlank(entity.getServiceList())) {
                    ServiceDTO postService = new ServiceDTO();
                    postService.setProtocol("any");
                    List<ServiceDTO> serviceList = new ArrayList();
                    serviceList.add(postService);
                    policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                } else {
                    JSONArray array = JSONObject.parseArray(entity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    if (serviceList.size() <= 0) {
                        ServiceDTO postService = new ServiceDTO();
                        postService.setProtocol("any");
                        serviceList.add(postService);
                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    } else {
                        Iterator var18 = serviceList.iterator();

                        while(var18.hasNext()) {
                            ServiceDTO serviceDTO = (ServiceDTO)var18.next();
                            serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                        }

                        policyDetailVO.setService(JSONObject.toJSONString(serviceList));
                    }
                }

                JSONObject object;
                String deviceUuid;
                String deviceIp;
                if (entity.getTaskType().equals(5)) {
                    BeanUtils.copyProperties(entity, policyDetailVO);
                    policyDetailVO.setPushStatus(entity.getStatus());
                    logger.debug("static nat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                    object = JSONObject.parseObject(entity.getAdditionInfo());
                    if (object != null) {
                        StaticNatAdditionalInfoEntity staticNatAdditionalInfoEntity = (StaticNatAdditionalInfoEntity)object.toJavaObject(StaticNatAdditionalInfoEntity.class);
                        policyDetailVO.setPublicAddress(staticNatAdditionalInfoEntity.getGlobalAddress());
                        policyDetailVO.setPrivateAddress(staticNatAdditionalInfoEntity.getInsideAddress());
                        policyDetailVO.setPublicPort(staticNatAdditionalInfoEntity.getGlobalPort());
                        policyDetailVO.setPrivatePort(staticNatAdditionalInfoEntity.getInsidePort());
                        deviceUuid = staticNatAdditionalInfoEntity.getProtocol();
                        deviceIp = staticNatAdditionalInfoEntity.getDeviceUuid();
                        deviceIp = String.format("未知设备(%s)", deviceIp);
                        if (deviceIp != null) {
                            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceIp);
                            if (nodeEntity != null) {
                                deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                            }
                        }

                        policyDetailVO.setDeviceIp(deviceIp);
                        if (AliStringUtils.isEmpty(deviceUuid)) {
                            policyDetailVO.setProtocol("any");
                        } else {
                            policyDetailVO.setProtocol(ProtocolUtils.getProtocolByString(deviceUuid));
                        }

                        policyDetailVO.setSrcDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getFromZone(), staticNatAdditionalInfoEntity.getInDevItf()));
                        policyDetailVO.setDstDomain(this.formatZoneItfString(staticNatAdditionalInfoEntity.getToZone(), staticNatAdditionalInfoEntity.getOutDevItf()));
                    }
                } else {
                    NodeEntity nodeEntity;
                    if (entity.getTaskType().equals(6)) {
                        BeanUtils.copyProperties(entity, policyDetailVO);
                        policyDetailVO.setPushStatus(entity.getStatus());
                        logger.debug("snat additionalInfo is " + JSONObject.toJSONString(entity.getAdditionInfo()));
                        object = JSONObject.parseObject(entity.getAdditionInfo());
                        if (object != null) {
                            SNatAdditionalInfoEntity sNatAdditionalInfoEntity = (SNatAdditionalInfoEntity)object.toJavaObject(SNatAdditionalInfoEntity.class);
                            policyDetailVO.setSrcDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getSrcZone(), sNatAdditionalInfoEntity.getSrcItf()));
                            policyDetailVO.setDstDomain(this.formatZoneItfString(sNatAdditionalInfoEntity.getDstZone(), sNatAdditionalInfoEntity.getDstItf()));
                            policyDetailVO.setPreSrcIp(entity.getSrcIp());
                            policyDetailVO.setPostSrcIp(sNatAdditionalInfoEntity.getPostIpAddress());
                            deviceUuid = sNatAdditionalInfoEntity.getDeviceUuid();
                            deviceIp = String.format("未知设备(%s)", deviceUuid);
                            if (deviceUuid != null) {
                                nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                if (nodeEntity != null) {
                                    deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                        }
                    } else {
                        List serviceList;
                        ServiceDTO serviceDTO;
                        String protocol;
                        JSONArray array;
                        ArrayList postServiceList;
                        ServiceDTO postService;
                        Iterator var45;
                        if (entity.getTaskType().equals(7)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                DNatAdditionalInfoEntity dnatAdditionalInfoEntity = (DNatAdditionalInfoEntity)object.toJavaObject(DNatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getSrcZone(), dnatAdditionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(dnatAdditionalInfoEntity.getDstZone(), dnatAdditionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(dnatAdditionalInfoEntity.getPostIpAddress());
                                deviceUuid = dnatAdditionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var45 = serviceList.iterator(); var45.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var45.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(dnatAdditionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(9)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            object = JSONObject.parseObject(entity.getAdditionInfo());
                            if (object != null) {
                                NatAdditionalInfoEntity additionalInfoEntity = (NatAdditionalInfoEntity)object.toJavaObject(NatAdditionalInfoEntity.class);
                                policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getSrcItf()));
                                policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getDstItf()));
                                policyDetailVO.setPreSrcIp(entity.getSrcIp());
                                policyDetailVO.setPostSrcIp(additionalInfoEntity.getPostSrcIp());
                                policyDetailVO.setPreDstIp(entity.getDstIp());
                                policyDetailVO.setPostDstIp(additionalInfoEntity.getPostDstIp());
                                deviceUuid = additionalInfoEntity.getDeviceUuid();
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                    }
                                }

                                policyDetailVO.setDeviceIp(deviceIp);
                                postServiceList = new ArrayList();
                                if (StringUtils.isNotBlank(entity.getServiceList())) {
                                    array = JSONObject.parseArray(entity.getServiceList());
                                    serviceList = array.toJavaList(ServiceDTO.class);
                                    if (serviceList.size() > 0) {
                                        for(var45 = serviceList.iterator(); var45.hasNext(); postServiceList.add(postService)) {
                                            serviceDTO = (ServiceDTO)var45.next();
                                            postService = new ServiceDTO();
                                            protocol = ProtocolUtils.getProtocolByString(serviceDTO.getProtocol());
                                            postService.setProtocol(protocol);
                                            if (!protocol.equalsIgnoreCase("1")) {
                                                postService.setDstPorts(additionalInfoEntity.getPostPort());
                                            }
                                        }
                                    } else {
                                        postService = new ServiceDTO();
                                        postService.setProtocol("any");
                                        postService.setDstPorts(additionalInfoEntity.getPostPort());
                                        postServiceList.add(postService);
                                    }

                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                } else {
                                    postService = new ServiceDTO();
                                    postService.setProtocol("any");
                                    postService.setDstPorts(additionalInfoEntity.getPostPort());
                                    postServiceList.add(postService);
                                    policyDetailVO.setPostService(JSONObject.toJSONString(postServiceList));
                                }
                            }
                        } else if (entity.getTaskType().equals(20)) {
                            BeanUtils.copyProperties(entity, policyDetailVO);
                            policyDetailVO.setPushStatus(entity.getStatus());
                            policyDetailVO.setUserName(entity.getUserName());
                            PushRecommendTaskExpandEntity expandEntity = this.pushRecommendTaskExpandMapper.getByTaskId(entity.getId());
                            if (null == expandEntity) {
                                logger.info(String.format("根据任务id:%d查询拓展数据为空", entity.getId()));
                                continue;
                            }

                            deviceIp = "未知设备";
                            deviceUuid = expandEntity.getDeviceUuid();
                            policyDetailVO.setDescription(entity.getDescription());
                            if (StringUtils.isNotBlank(deviceUuid)) {
                                deviceIp = String.format("未知设备(%s)", deviceUuid);
                                if (deviceUuid != null) {
                                    nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                                    if (nodeEntity != null) {
                                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                                        policyDetailVO.setDeviceName(nodeEntity.getDeviceName());
                                    }
                                }
                            }

                            policyDetailVO.setDeviceIp(deviceIp);
                            if (StringUtils.isNotEmpty(expandEntity.getStaticRoutingInfo())) {
                                StaticRoutingDTO staticRoutingDTO = (StaticRoutingDTO)JSONObject.toJavaObject(JSONObject.parseObject(expandEntity.getStaticRoutingInfo()), StaticRoutingDTO.class);
                                policyDetailVO.setSrcVirtualRouter(staticRoutingDTO.getSrcVirtualRouter());
                                policyDetailVO.setDstVirtualRouter(staticRoutingDTO.getDstVirtualRouter());
                                policyDetailVO.setNextHop(staticRoutingDTO.getNextHop());
                                policyDetailVO.setSubnetMask(staticRoutingDTO.getSubnetMask());
                                policyDetailVO.setOutInterface(staticRoutingDTO.getOutInterface());
                                policyDetailVO.setPriority(staticRoutingDTO.getPriority());
                                policyDetailVO.setManagementDistance(staticRoutingDTO.getManagementDistance());
                            }
                        }
                    }
                }

                policyList.add(policyDetailVO);
            }

            return policyList;
        }
    }

    public PageInfo<PolicyTaskDetailVO> getSecurityPolicyTaskList(String theme, int page, int psize, String userName, String deviceUUID, String status, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        List<RecommendTaskEntity> list = this.searchTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, status, String.valueOf(3), page, psize, branchLevel, deviceUUID);
        PageInfo<RecommendTaskEntity> originalPageInfo = new PageInfo(list);
        List<PolicyTaskDetailVO> policyList = new ArrayList();

        PolicyTaskDetailVO policyDetailVO;
        for(Iterator var12 = list.iterator(); var12.hasNext(); policyList.add(policyDetailVO)) {
            RecommendTaskEntity entity = (RecommendTaskEntity)var12.next();
            policyDetailVO = new PolicyTaskDetailVO();
            String additionalInfo = entity.getAdditionInfo();
            PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
            if (additionalInfo != null) {
                JSONObject object = JSONObject.parseObject(additionalInfo);
                additionalInfoEntity = (PushAdditionalInfoEntity)JSONObject.toJavaObject(object, PushAdditionalInfoEntity.class);
            }

            String deviceIp = "未知设备";
            String deviceUuid = additionalInfoEntity.getDeviceUuid();
            if (StringUtils.isNotBlank(deviceUuid)) {
                deviceIp = String.format("未知设备(%s)", deviceUuid);
                if (deviceUuid != null) {
                    NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                    if (nodeEntity != null) {
                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                    }
                }
            } else {
                String scenesUuid = additionalInfoEntity.getScenesUuid();
                if (StringUtils.isNotBlank(scenesUuid)) {
                    DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                    if (scenesEntity == null) {
                        logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                    } else {
                        policyDetailVO.setScenesUuid(scenesUuid);
                        policyDetailVO.setScenesName(scenesEntity.getName());
                        deviceIp = String.format("场景：%s", scenesEntity.getName());
                    }
                }
            }

            policyDetailVO.setTaskId(entity.getId());
            policyDetailVO.setCreateTime(entity.getCreateTime());
            policyDetailVO.setUserName(entity.getUserName());
            policyDetailVO.setDeviceIp(deviceIp);
            policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getInDevItf()));
            policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getOutDevItf()));
            policyDetailVO.setAction(additionalInfoEntity.getAction());
            policyDetailVO.setSrcIp(entity.getSrcIp());
            policyDetailVO.setDstIp(entity.getDstIp());
            policyDetailVO.setPolicyName(entity.getTheme());
            policyDetailVO.setSrcIpSystem(entity.getSrcIpSystem());
            policyDetailVO.setDstIpSystem(entity.getDstIpSystem());
            policyDetailVO.setPushStatus(entity.getStatus());
            if (entity.getStatus() != null && entity.getEndTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                policyDetailVO.setTime(String.format("%s - %s", sdf.format(entity.getStartTime()), sdf.format(entity.getEndTime())));
            }

            policyDetailVO.setDescription(entity.getDescription());
            policyDetailVO.setIdleTimeout(null == entity.getIdleTimeout() ? null : String.valueOf(entity.getIdleTimeout()));
            if (StringUtils.isNotBlank(entity.getServiceList())) {
                JSONArray array = JSONObject.parseArray(entity.getServiceList());
                List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                Iterator var21 = serviceList.iterator();

                while(var21.hasNext()) {
                    ServiceDTO serviceDTO = (ServiceDTO)var21.next();
                    serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                }

                policyDetailVO.setService(JSONObject.toJSONString(serviceList));
            }
        }

        PageInfo<PolicyTaskDetailVO> pageInfo = new PageInfo(policyList);
        pageInfo.setTotal(originalPageInfo.getTotal());
        pageInfo.setStartRow(originalPageInfo.getStartRow());
        pageInfo.setEndRow(originalPageInfo.getEndRow());
        pageInfo.setPageSize(originalPageInfo.getPageSize());
        pageInfo.setPageNum(originalPageInfo.getPageNum());
        return pageInfo;
    }

    public PageInfo<PolicyTaskDetailVO> getSecurityPolicyTaskList(String theme, int page, int psize, String userName, String deviceUUID, String status, Authentication authentication, String branchLevel) {
        if(branchLevel == null || branchLevel.equals("")){
            branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        }else{
            branchLevel = branchLevel + "%";
        }
        List<RecommendTaskEntity> list = this.searchTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, status, String.valueOf(3), page, psize, branchLevel, deviceUUID);
        PageInfo<RecommendTaskEntity> originalPageInfo = new PageInfo(list);
        List<PolicyTaskDetailVO> policyList = new ArrayList();

        PolicyTaskDetailVO policyDetailVO;
        for(Iterator var12 = list.iterator(); var12.hasNext(); policyList.add(policyDetailVO)) {
            RecommendTaskEntity entity = (RecommendTaskEntity)var12.next();
            policyDetailVO = new PolicyTaskDetailVO();
            String additionalInfo = entity.getAdditionInfo();
            PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
            if (additionalInfo != null) {
                JSONObject object = JSONObject.parseObject(additionalInfo);
                additionalInfoEntity = (PushAdditionalInfoEntity)JSONObject.toJavaObject(object, PushAdditionalInfoEntity.class);
            }

            String deviceIp = "未知设备";
            String deviceUuid = additionalInfoEntity.getDeviceUuid();
            if (StringUtils.isNotBlank(deviceUuid)) {
                deviceIp = String.format("未知设备(%s)", deviceUuid);
                if (deviceUuid != null) {
                    NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                    if (nodeEntity != null) {
                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                    }
                }
            } else {
                String scenesUuid = additionalInfoEntity.getScenesUuid();
                if (StringUtils.isNotBlank(scenesUuid)) {
                    DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                    if (scenesEntity == null) {
                        logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                    } else {
                        policyDetailVO.setScenesUuid(scenesUuid);
                        policyDetailVO.setScenesName(scenesEntity.getName());
                        deviceIp = String.format("场景：%s", scenesEntity.getName());
                    }
                }
            }

            policyDetailVO.setTaskId(entity.getId());
            policyDetailVO.setCreateTime(entity.getCreateTime());
            policyDetailVO.setUserName(entity.getUserName());
            policyDetailVO.setDeviceIp(deviceIp);
            policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getInDevItf()));
            policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getOutDevItf()));
            policyDetailVO.setAction(additionalInfoEntity.getAction());
            policyDetailVO.setSrcIp(entity.getSrcIp());
            policyDetailVO.setDstIp(entity.getDstIp());
            policyDetailVO.setPolicyName(entity.getTheme());
            policyDetailVO.setSrcIpSystem(entity.getSrcIpSystem());
            policyDetailVO.setDstIpSystem(entity.getDstIpSystem());
            policyDetailVO.setPushStatus(entity.getStatus());
            if (entity.getStatus() != null && entity.getEndTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                policyDetailVO.setTime(String.format("%s - %s", sdf.format(entity.getStartTime()), sdf.format(entity.getEndTime())));
            }

            policyDetailVO.setDescription(entity.getDescription());
            policyDetailVO.setIdleTimeout(null == entity.getIdleTimeout() ? null : String.valueOf(entity.getIdleTimeout()));
            if (StringUtils.isNotBlank(entity.getServiceList())) {
                JSONArray array = JSONObject.parseArray(entity.getServiceList());
                List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                Iterator var21 = serviceList.iterator();

                while(var21.hasNext()) {
                    ServiceDTO serviceDTO = (ServiceDTO)var21.next();
                    serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                }

                policyDetailVO.setService(JSONObject.toJSONString(serviceList));
            }
        }

        PageInfo<PolicyTaskDetailVO> pageInfo = new PageInfo(policyList);
        pageInfo.setTotal(originalPageInfo.getTotal());
        pageInfo.setStartRow(originalPageInfo.getStartRow());
        pageInfo.setEndRow(originalPageInfo.getEndRow());
        pageInfo.setPageSize(originalPageInfo.getPageSize());
        pageInfo.setPageNum(originalPageInfo.getPageNum());
        return pageInfo;
    }

    public PageInfo<PolicyTaskDetailVO> getCustomizeCmdTaskList(String theme, int page, int psize, String userName, String deviceUUID, Integer status, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        List<RecommendTaskEntity> list = this.searchTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, String.valueOf(16), page, psize, branchLevel, deviceUUID);
        List<PolicyTaskDetailVO> policyList = new ArrayList();
        Iterator var11 = list.iterator();

        while(true) {
            while(true) {
                while(var11.hasNext()) {
                    RecommendTaskEntity entity = (RecommendTaskEntity)var11.next();
                    PolicyTaskDetailVO policyDetailVO = new PolicyTaskDetailVO();
                    String additionalInfo = entity.getAdditionInfo();
                    PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
                    if (additionalInfo != null) {
                        JSONObject object = JSONObject.parseObject(additionalInfo);
                        additionalInfoEntity = (PushAdditionalInfoEntity)JSONObject.toJavaObject(object, PushAdditionalInfoEntity.class);
                    }

                    String deviceIp = "未知设备";
                    String deviceUuid = additionalInfoEntity.getDeviceUuid();
                    if (StringUtils.isNotBlank(deviceUuid)) {
                        deviceIp = String.format("未知设备(%s)", deviceUuid);
                        if (deviceUuid != null) {
                            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                            if (nodeEntity != null) {
                                deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                            }
                        }
                    } else {
                        String scenesUuid = additionalInfoEntity.getScenesUuid();
                        if (StringUtils.isNotBlank(scenesUuid)) {
                            DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                            if (scenesEntity == null) {
                                logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                            } else {
                                policyDetailVO.setScenesUuid(scenesUuid);
                                policyDetailVO.setScenesName(scenesEntity.getName());
                                deviceIp = String.format("场景：%s", scenesEntity.getName());
                            }
                        }
                    }

                    policyDetailVO.setTaskId(entity.getId());
                    policyDetailVO.setCreateTime(entity.getCreateTime());
                    policyDetailVO.setUserName(entity.getUserName());
                    policyDetailVO.setDeviceIp(deviceIp);
                    policyDetailVO.setPolicyName(entity.getTheme());
                    List<CommandTaskEditableEntity> taskCollect = this.commandTaskEditableMapper.selectByTaskId(entity.getId());
                    int pushStatusInTaskList = this.getPushStatusInTaskList(taskCollect);
                    policyDetailVO.setPushStatus(pushStatusInTaskList);
                    if (null != status) {
                        if (0 == status && 0 == pushStatusInTaskList) {
                            policyList.add(policyDetailVO);
                        } else if (1 == status && (1 == pushStatusInTaskList || 4 == pushStatusInTaskList)) {
                            policyList.add(policyDetailVO);
                        } else if (2 == status && (2 == pushStatusInTaskList || 3 == pushStatusInTaskList || 5 == pushStatusInTaskList)) {
                            policyList.add(policyDetailVO);
                        }
                    } else {
                        policyList.add(policyDetailVO);
                    }
                }

                PageInfo<PolicyTaskDetailVO> pageInfo = new PageInfo(policyList);
                return pageInfo;
            }
        }
    }

    public List<PolicyTaskDetailVO> getSecurityTaskList(String theme, String userName, String deviceUUID, String startTime, String endTime, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        List<RecommendTaskEntity> list = this.searchPolicyTaskList(theme, (String)null, userName, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, String.valueOf(3), startTime, endTime, branchLevel, deviceUUID);
        List<PolicyTaskDetailVO> policyList = new ArrayList();

        PolicyTaskDetailVO policyDetailVO;
        for(Iterator var10 = list.iterator(); var10.hasNext(); policyList.add(policyDetailVO)) {
            RecommendTaskEntity entity = (RecommendTaskEntity)var10.next();
            policyDetailVO = new PolicyTaskDetailVO();
            String additionalInfo = entity.getAdditionInfo();
            PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
            if (additionalInfo != null) {
                JSONObject object = JSONObject.parseObject(additionalInfo);
                additionalInfoEntity = (PushAdditionalInfoEntity)JSONObject.toJavaObject(object, PushAdditionalInfoEntity.class);
            }

            String deviceIp = "未知设备";
            String deviceUuid = additionalInfoEntity.getDeviceUuid();
            if (StringUtils.isNotBlank(deviceUuid)) {
                deviceIp = String.format("未知设备(%s)", deviceUuid);
                if (deviceUuid != null) {
                    NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(deviceUuid);
                    if (nodeEntity != null) {
                        deviceIp = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
                    }
                }
            } else {
                String scenesUuid = additionalInfoEntity.getScenesUuid();
                if (StringUtils.isNotBlank(scenesUuid)) {
                    DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                    if (scenesEntity == null) {
                        logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                    } else {
                        policyDetailVO.setScenesUuid(scenesUuid);
                        policyDetailVO.setScenesName(scenesEntity.getName());
                        deviceIp = String.format("场景：%s", scenesEntity.getName());
                    }
                }
            }

            policyDetailVO.setTaskId(entity.getId());
            policyDetailVO.setCreateTime(entity.getCreateTime());
            policyDetailVO.setUserName(entity.getUserName());
            policyDetailVO.setDeviceIp(deviceIp);
            policyDetailVO.setSrcDomain(this.formatZoneItfString(additionalInfoEntity.getSrcZone(), additionalInfoEntity.getInDevItf()));
            policyDetailVO.setDstDomain(this.formatZoneItfString(additionalInfoEntity.getDstZone(), additionalInfoEntity.getOutDevItf()));
            policyDetailVO.setAction(additionalInfoEntity.getAction());
            policyDetailVO.setSrcIp(entity.getSrcIp());
            policyDetailVO.setDstIp(entity.getDstIp());
            policyDetailVO.setPolicyName(entity.getTheme());
            policyDetailVO.setSrcIpSystem(entity.getSrcIpSystem());
            policyDetailVO.setDstIpSystem(entity.getDstIpSystem());
            policyDetailVO.setPushStatus(entity.getStatus());
            if (entity.getStatus() != null && entity.getEndTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                policyDetailVO.setTime(String.format("%s - %s", sdf.format(entity.getStartTime()), sdf.format(entity.getEndTime())));
            }

            policyDetailVO.setDescription(entity.getDescription());
            if (StringUtils.isNotBlank(entity.getServiceList())) {
                JSONArray array = JSONObject.parseArray(entity.getServiceList());
                List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                Iterator var19 = serviceList.iterator();

                while(var19.hasNext()) {
                    ServiceDTO serviceDTO = (ServiceDTO)var19.next();
                    serviceDTO.setProtocol(ProtocolUtils.getProtocolByString(serviceDTO.getProtocol()));
                }

                policyDetailVO.setService(JSONObject.toJSONString(serviceList));
            }
        }

        return policyList;
    }

    public TaskStatusVO getTaskStatusByTaskId(int taskId) {
        List<PathInfoEntity> list = this.pathInfoMapper.selectByTaskId(taskId);
        TaskStatusVO taskStatusVO = new TaskStatusVO();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            PathInfoEntity pathInfoEntity = (PathInfoEntity)var4.next();
            if (taskStatusVO.getAnalyzeStatus() == null) {
                BeanUtils.copyProperties(pathInfoEntity, taskStatusVO);
            } else {
                if (taskStatusVO.getAccessAnalyzeStatus() < pathInfoEntity.getAccessAnalyzeStatus()) {
                    taskStatusVO.setAccessAnalyzeStatus(pathInfoEntity.getAccessAnalyzeStatus());
                }

                if (taskStatusVO.getAdviceStatus() < pathInfoEntity.getAdviceStatus()) {
                    taskStatusVO.setAdviceStatus(pathInfoEntity.getAdviceStatus());
                }

                if (taskStatusVO.getCheckStatus() < pathInfoEntity.getCheckStatus()) {
                    taskStatusVO.setCheckStatus(pathInfoEntity.getCheckStatus());
                }

                if (taskStatusVO.getRiskStatus() < pathInfoEntity.getRiskStatus()) {
                    taskStatusVO.setRiskStatus(pathInfoEntity.getRiskStatus());
                }

                if (taskStatusVO.getCmdStatus() < pathInfoEntity.getCmdStatus()) {
                    taskStatusVO.setCmdStatus(pathInfoEntity.getCmdStatus());
                }

                if (taskStatusVO.getPushStatus() < pathInfoEntity.getPushStatus()) {
                    taskStatusVO.setPushStatus(pathInfoEntity.getPushStatus());
                }

                if (taskStatusVO.getGatherStatus() < pathInfoEntity.getGatherStatus()) {
                    taskStatusVO.setGatherStatus(pathInfoEntity.getGatherStatus());
                }

                if (taskStatusVO.getAccessAnalyzeStatus() < pathInfoEntity.getAccessAnalyzeStatus()) {
                    taskStatusVO.setAccessAnalyzeStatus(pathInfoEntity.getAccessAnalyzeStatus());
                }

                if (taskStatusVO.getVerifyStatus() < pathInfoEntity.getVerifyStatus()) {
                    taskStatusVO.setVerifyStatus(pathInfoEntity.getVerifyStatus());
                }
            }
        }

        return taskStatusVO;
    }

    public PathDetailVO getPathDetail(int pathInfoId, boolean isVerifyData) {
        List<PathDetailEntity> entityList = this.pathDetailMapper.selectByPathInfoId(pathInfoId);
        if (entityList != null && entityList.size() != 0) {
            if (entityList.size() > 1) {
                logger.warn(String.format("Size of path detail entity of path(%d) is %d", pathInfoId, entityList.size()));
            }

            PathDetailEntity entity = (PathDetailEntity)entityList.get(0);
            PathDetailVO pathDetailVO = new PathDetailVO();
            pathDetailVO.setPathInfoId(pathInfoId);
            if (isVerifyData) {
                pathDetailVO.setDetailPath(JSONObject.parseObject(entity.getVerifyPath()));
            } else {
                pathDetailVO.setDetailPath(JSONObject.parseObject(entity.getAnalyzePath()));
            }

            return pathDetailVO;
        } else {
            return null;
        }
    }

    public PathDeviceDetailEntity getDevieceDetail(int pathInfoId, String deviceUuid, boolean isVerifyData, String index) {
        Map<String, String> params = new HashMap();
        params.put("pathInfoId", String.valueOf(pathInfoId));
        params.put("deviceUuid", deviceUuid);
        params.put("isVerifyData", isVerifyData ? String.valueOf(1) : String.valueOf(0));
        params.put("pathIndex", index);
        List<PathDeviceDetailEntity> list = this.pathDeviceDetailMapper.selectPathDeviceDetail(params);
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                logger.warn(String.format("Size of device detail entity of path(%d) device(%s) is %d.", pathInfoId, deviceUuid, list.size()));
            }

            return (PathDeviceDetailEntity)list.get(0);
        } else {
            return null;
        }
    }

    public List<PolicyRiskEntity> getRiskByPathInfoId(int pathInfoId) {
        return this.policyRiskMapper.selectByPathInfoId(pathInfoId);
    }

    public List<RecommendPolicyVO> getPolicyByPathInfoId(int pathInfoId) {
        List<RecommendPolicyEntity> list = this.recommendPolicyMapper.selectByPathInfoId(pathInfoId);
        List<RecommendPolicyVO> recommendPolicyVOList = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            RecommendPolicyEntity entity = (RecommendPolicyEntity)var4.next();
            PolicyVO policyVO = new PolicyVO();
            BeanUtils.copyProperties(entity, policyVO);
            if (!AliStringUtils.isEmpty(entity.getService())) {
                JSONArray array = JSONArray.parseArray(entity.getService());
                List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                Iterator var9 = serviceList.iterator();

                while(var9.hasNext()) {
                    ServiceDTO service = (ServiceDTO)var9.next();
                    service.setProtocol(ProtocolUtils.getProtocolByString(service.getProtocol()));
                }

                policyVO.setService(serviceList);
            }

            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(entity.getDeviceUuid());
            String name = "未知设备";
            if (nodeEntity != null) {
                name = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
            }

            boolean hasVO = false;
            Iterator var16 = recommendPolicyVOList.iterator();

            while(var16.hasNext()) {
                RecommendPolicyVO recommendPolicyVO = (RecommendPolicyVO)var16.next();
                if (recommendPolicyVO.getName().equals(name)) {
                    List<PolicyVO> policyVOList = recommendPolicyVO.getList();
                    policyVOList.add(policyVO);
                    hasVO = true;
                }
            }

            if (!hasVO) {
                RecommendPolicyVO recommendPolicyVO = new RecommendPolicyVO();
                recommendPolicyVO.setName(name);
                recommendPolicyVO.setDeviceUuid(entity.getDeviceUuid());
                List<PolicyVO> policyVOList = new ArrayList();
                policyVOList.add(policyVO);
                recommendPolicyVO.setList(policyVOList);
                recommendPolicyVOList.add(recommendPolicyVO);
            }
        }

        return recommendPolicyVOList;
    }

    public List<RecommendPolicyVO> getMergedPolicyByTaskId(int taskId) {
        List<RecommendPolicyEntity> list = this.mergedPolicyMapper.selectByTaskId(taskId);
        List<RecommendPolicyVO> recommendPolicyVOList = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            RecommendPolicyEntity entity = (RecommendPolicyEntity)var4.next();
            PolicyVO policyVO = new PolicyVO();
            BeanUtils.copyProperties(entity, policyVO);
            if (!AliStringUtils.isEmpty(entity.getService())) {
                JSONArray array = JSONArray.parseArray(entity.getService());
                List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                Iterator var9 = serviceList.iterator();

                while(var9.hasNext()) {
                    ServiceDTO service = (ServiceDTO)var9.next();
                    service.setProtocol(ProtocolUtils.getProtocolByString(service.getProtocol()));
                }

                policyVO.setService(serviceList);
            }

            NodeEntity nodeEntity = this.policyRecommendNodeMapper.getTheNodeByUuid(entity.getDeviceUuid());
            String name = "未知设备";
            if (nodeEntity != null) {
                name = String.format("%s(%s)", nodeEntity.getDeviceName(), nodeEntity.getIp());
            } else {
                name = String.format("未知设备(%s)", entity.getDeviceUuid());
            }

            boolean hasVO = false;
            Iterator var16 = recommendPolicyVOList.iterator();

            while(var16.hasNext()) {
                RecommendPolicyVO recommendPolicyVO = (RecommendPolicyVO)var16.next();
                if (recommendPolicyVO.getName().equals(name)) {
                    List<PolicyVO> policyVOList = recommendPolicyVO.getList();
                    policyVOList.add(policyVO);
                    hasVO = true;
                }
            }

            if (!hasVO) {
                RecommendPolicyVO recommendPolicyVO = new RecommendPolicyVO();
                recommendPolicyVO.setName(name);
                recommendPolicyVO.setDeviceUuid(entity.getDeviceUuid());
                List<PolicyVO> policyVOList = new ArrayList();
                policyVOList.add(policyVO);
                recommendPolicyVO.setList(policyVOList);
                recommendPolicyVOList.add(recommendPolicyVO);
            }
        }

        return recommendPolicyVOList;
    }

    public List<CheckResultEntity> getCheckResultByPolicyId(int taskId) {
        List<RecommendPolicyEntity> policyEntityList = this.recommendPolicyMapper.selectByTaskId(taskId);
        List<CheckResultEntity> checkResultEntityList = new ArrayList();
        if (policyEntityList != null && policyEntityList.size() != 0) {
            Iterator var4 = policyEntityList.iterator();

            while(var4.hasNext()) {
                RecommendPolicyEntity policyEntity = (RecommendPolicyEntity)var4.next();
                Integer policyId = policyEntity.getId();
                List<CheckResultEntity> list = this.checkResultMapper.selectByPolicyId(policyId);
                if (list != null && list.size() != 0) {
                    if (list.size() > 1) {
                        logger.warn(String.format("策略(%d)检查结果数据大于一，为(%d)", policyId, list.size()));
                    }

                    CheckResultEntity entity = (CheckResultEntity)list.get(0);
                    if (AliStringUtils.isEmpty(entity.getCheckResult())) {
                        logger.error("策略(%d)检查结果字段为空...");
                    } else {
                        String checkResult = entity.getCheckResult();
                        JSONObject checkResultObject = JSONObject.parseObject(checkResult);
                        RuleCheckResultRO checkResultRO = (RuleCheckResultRO)checkResultObject.toJavaObject(RuleCheckResultRO.class);
                        List<RuleCheckResultDataRO> ruleCheckResultDataROList = checkResultRO.getData();
                        if (ruleCheckResultDataROList == null) {
                            logger.error("策略(%d)检查结果数据为空..." + checkResultRO.toString());
                        } else {
                            checkResultEntityList.add(list.get(0));
                        }
                    }
                }
            }

            return checkResultEntityList;
        } else {
            return checkResultEntityList;
        }
    }

    public int insertSrcNatPolicy(SNatPolicyDTO sNatPolicyDTO, Authentication auth) {
        String deviceUuid = sNatPolicyDTO.getDeviceUuid();
        NodeEntity node = this.getTheNodeByUuid(deviceUuid);
        if (node == null) {
            return 83;
        } else {
            String userName = auth.getName();
            if(sNatPolicyDTO.getUserName() != null && !sNatPolicyDTO.getUserName().equals("")){
                userName = sNatPolicyDTO.getUserName();
            }
            sNatPolicyDTO.setSrcZone(this.getZone(sNatPolicyDTO.getSrcZone()));
            sNatPolicyDTO.setDstZone(this.getZone(sNatPolicyDTO.getDstZone()));
            SNatAdditionalInfoEntity additionalInfoEntity = new SNatAdditionalInfoEntity(deviceUuid, sNatPolicyDTO.getPostIpAddress(), sNatPolicyDTO.getSrcZone(), sNatPolicyDTO.getSrcItf(), sNatPolicyDTO.getDstZone(), sNatPolicyDTO.getDstItf(), sNatPolicyDTO.getMode(), sNatPolicyDTO.getInDevItfAlias(), sNatPolicyDTO.getOutDevItfAlias());
            Integer ipType = ObjectUtils.isNotEmpty(sNatPolicyDTO.getIpType()) ? sNatPolicyDTO.getIpType() : IpTypeEnum.IPV4.getCode();
            RecommendTaskEntity recommendTaskEntity = EntityUtils.createRecommendTask(sNatPolicyDTO.getTheme(), userName, sNatPolicyDTO.getSrcIp(), sNatPolicyDTO.getDstIp(), JSONObject.toJSONString(sNatPolicyDTO.getServiceList()), 6, 0, JSONObject.toJSONString(additionalInfoEntity), sNatPolicyDTO.getSrcIpSystem(), sNatPolicyDTO.getDstIpSystem(), sNatPolicyDTO.getPostSrcIpSystem(), (String)null, ipType);
            if(sNatPolicyDTO.getBranchLevel() == null || sNatPolicyDTO.getBranchLevel().equals("")){
                this.getBranch(userName, recommendTaskEntity);
            }else{
                recommendTaskEntity.setBranchLevel(sNatPolicyDTO.getBranchLevel());
            }
            this.addRecommendTask(recommendTaskEntity);
            CommandTaskEditableEntity entity = this.createCommandTask(6, recommendTaskEntity.getId(), userName, sNatPolicyDTO.getTheme(), sNatPolicyDTO.getDeviceUuid());
            entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
            this.commandTaskManager.addCommandEditableEntityTask(entity);
            DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
            DeviceDataRO deviceData = (DeviceDataRO)device.getData().get(0);
            boolean isVsys = false;
            String vsysName = "";
            if (deviceData.getIsVsys() != null) {
                isVsys = deviceData.getIsVsys();
                vsysName = deviceData.getVsysName();
            }

            CmdDTO cmdDTO = EntityUtils.createCmdDTO(PolicyEnum.SNAT, entity.getId(), entity.getTaskId(), deviceUuid, sNatPolicyDTO.getTheme(), userName, recommendTaskEntity.getSrcIp(), recommendTaskEntity.getDstIp(), additionalInfoEntity.getPostIpAddress(), (String)null, sNatPolicyDTO.getServiceList(), (List)null, sNatPolicyDTO.getSrcZone(), sNatPolicyDTO.getDstZone(), sNatPolicyDTO.getSrcItf(), sNatPolicyDTO.getDstItf(), sNatPolicyDTO.getInDevItfAlias(), sNatPolicyDTO.getOutDevItfAlias(), "", isVsys, vsysName, sNatPolicyDTO.getSrcIpSystem(), sNatPolicyDTO.getDstIpSystem(), sNatPolicyDTO.getPostSrcIpSystem());
            TaskDTO taskDTO = cmdDTO.getTask();
            taskDTO.setTaskTypeEnum(TaskTypeEnum.SNAT_TYPE);
            cmdDTO.getPolicy().setIpType(sNatPolicyDTO.getIpType());
            logger.info("命令行生成任务为:" + JSONObject.toJSONString(cmdDTO));
            this.pushTaskService.addGenerateCmdTask(cmdDTO);
            sNatPolicyDTO.setId(entity.getId());
            sNatPolicyDTO.setTaskId(entity.getTaskId());
            return 0;
        }
    }

    public int insertDstNatPolicy(DNatPolicyDTO policyDTO, Authentication auth) {
        String deviceUuid = policyDTO.getDeviceUuid();
        NodeEntity node = this.getTheNodeByUuid(deviceUuid);
        if (node == null) {
            return 83;
        } else {
            String userName = auth.getName();
            if(policyDTO.getUserName() != null && !policyDTO.getUserName().equals("")){
                userName = policyDTO.getUserName();
            }
            policyDTO.setSrcZone(this.getZone(policyDTO.getSrcZone()));
            policyDTO.setDstZone(this.getZone(policyDTO.getDstZone()));
            DNatAdditionalInfoEntity additionalInfoEntity = new DNatAdditionalInfoEntity(deviceUuid, policyDTO.getPostIpAddress(), policyDTO.getPostPort(), policyDTO.getSrcZone(), policyDTO.getSrcItf(), policyDTO.getDstZone(), policyDTO.getDstItf(), policyDTO.getInDevItfAlias(), policyDTO.getOutDevItfAlias());
            Integer ipType = ObjectUtils.isNotEmpty(policyDTO.getIpType()) ? policyDTO.getIpType() : IpTypeEnum.IPV4.getCode();
            RecommendTaskEntity recommendTaskEntity = EntityUtils.createRecommendTask(policyDTO.getTheme(), userName, policyDTO.getSrcIp(), policyDTO.getDstIp(), JSONObject.toJSONString(policyDTO.getServiceList()), 7, 0, JSONObject.toJSONString(additionalInfoEntity), policyDTO.getSrcIpSystem(), policyDTO.getDstIpSystem(), (String)null, policyDTO.getPostDstIpSystem(), ipType);
            if(policyDTO.getBranchLevel() == null || policyDTO.getBranchLevel().equals("")){
                this.getBranch(userName, recommendTaskEntity);
            }else{
                recommendTaskEntity.setBranchLevel(policyDTO.getBranchLevel());
            }
            this.addRecommendTask(recommendTaskEntity);
            CommandTaskEditableEntity entity = this.createCommandTask(7, recommendTaskEntity.getId(), userName, policyDTO.getTheme(), policyDTO.getDeviceUuid());
            entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
            this.commandTaskManager.addCommandEditableEntityTask(entity);
            List<ServiceDTO> postServiceList = EntityUtils.getPostServiceList(policyDTO.getServiceList(), policyDTO.getPostPort());
            DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
            DeviceDataRO deviceData = (DeviceDataRO)device.getData().get(0);
            boolean isVsys = false;
            String rootDeviceUuid = "";
            String vsysName = "";
            if (deviceData.getIsVsys() != null) {
                isVsys = deviceData.getIsVsys();
                rootDeviceUuid = deviceData.getRootDeviceUuid();
                vsysName = deviceData.getVsysName();
            }

            String startTimeString = null;
            String endTimeString = null;
            if (AliStringUtils.areNotEmpty(new String[]{policyDTO.getStartTime(), policyDTO.getEndTime()})) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long startTime = Long.parseLong(policyDTO.getStartTime());
                Long endTime = Long.parseLong(policyDTO.getEndTime());
                startTimeString = sdf.format(startTime);
                endTimeString = sdf.format(endTime);
            }

            CmdDTO cmdDTO = EntityUtils.createCmdDTO(PolicyEnum.DNAT, entity.getId(), entity.getTaskId(), deviceUuid, policyDTO.getTheme(), userName, policyDTO.getSrcIp(), policyDTO.getDstIp(), (String)null, policyDTO.getPostIpAddress(), policyDTO.getServiceList(), postServiceList, policyDTO.getSrcZone(), policyDTO.getDstZone(), policyDTO.getSrcItf(), policyDTO.getDstItf(), policyDTO.getInDevItfAlias(), policyDTO.getOutDevItfAlias(), "", isVsys, vsysName, startTimeString, endTimeString, policyDTO.getSrcIpSystem(), policyDTO.getDstIpSystem(), policyDTO.getPostDstIpSystem(), policyDTO.getPostPort());
            TaskDTO taskDTO = cmdDTO.getTask();
            taskDTO.setTaskTypeEnum(TaskTypeEnum.DNAT_TYPE);
            cmdDTO.getPolicy().setIpType(policyDTO.getIpType());
            cmdDTO.getPolicy().setPostDstIpSystem(policyDTO.getPostDstIpSystem());
            logger.info("命令行生成任务为:" + JSONObject.toJSONString(cmdDTO));
            this.pushTaskService.addGenerateCmdTask(cmdDTO);
            policyDTO.setId(entity.getId());
            policyDTO.setTaskId(entity.getTaskId());
            return 0;
        }
    }

    public int insertBothNatPolicy(NatPolicyDTO policyDTO, Authentication auth) {
        String deviceUuid = policyDTO.getDeviceUuid();
        NodeEntity node = this.getTheNodeByUuid(deviceUuid);
        if (node == null) {
            return 83;
        } else {
            String userName = auth.getName();
            if(policyDTO.getUserName() != null && !policyDTO.getUserName().equals("")){
                userName = policyDTO.getUserName();
            }
            policyDTO.setSrcZone(this.getZone(policyDTO.getSrcZone()));
            policyDTO.setDstZone(this.getZone(policyDTO.getDstZone()));
            NatAdditionalInfoEntity additionalInfoEntity = new NatAdditionalInfoEntity((Integer)null, policyDTO.getPostSrcIp(), policyDTO.getPostDstIp(), policyDTO.getPostPort(), deviceUuid, policyDTO.getSrcZone(), policyDTO.getDstZone(), policyDTO.getDstItf(), policyDTO.getSrcItf(), policyDTO.isDynamic(), policyDTO.getInDevItfAlias(), policyDTO.getOutDevItfAlias());
            Integer ipType = ObjectUtils.isNotEmpty(policyDTO.getIpType()) ? policyDTO.getIpType() : IpTypeEnum.IPV4.getCode();
            RecommendTaskEntity recommendTaskEntity = EntityUtils.createRecommendTask(policyDTO.getTheme(), userName, policyDTO.getSrcIp(), policyDTO.getDstIp(), JSONObject.toJSONString(policyDTO.getServiceList()), 9, 0, JSONObject.toJSONString(additionalInfoEntity), (String)null, (String)null, (String)null, (String)null, ipType);
            if(policyDTO.getBranchLevel() == null || policyDTO.getBranchLevel().equals("")){
                this.getBranch(userName, recommendTaskEntity);
            }else{
                recommendTaskEntity.setBranchLevel(policyDTO.getBranchLevel());
            }
            this.addRecommendTask(recommendTaskEntity);
            CommandTaskEditableEntity entity = this.createCommandTask(9, recommendTaskEntity.getId(), userName, policyDTO.getTheme(), policyDTO.getDeviceUuid());
            entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
            this.commandTaskManager.addCommandEditableEntityTask(entity);
            List<ServiceDTO> postServiceList = EntityUtils.getPostServiceList(policyDTO.getServiceList(), policyDTO.getPostPort());
            DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
            DeviceDataRO deviceData = (DeviceDataRO)device.getData().get(0);
            boolean isVsys = false;
            String vsysName = "";
            if (deviceData.getIsVsys() != null) {
                isVsys = deviceData.getIsVsys();
                vsysName = deviceData.getVsysName();
            }

            CmdDTO cmdDTO = EntityUtils.createCmdDTO(PolicyEnum.BOTH, entity.getId(), entity.getTaskId(), deviceUuid, policyDTO.getTheme(), userName, policyDTO.getSrcIp(), policyDTO.getDstIp(), policyDTO.getPostSrcIp(), policyDTO.getPostDstIp(), policyDTO.getServiceList(), postServiceList, policyDTO.getSrcZone(), policyDTO.getDstZone(), policyDTO.getSrcItf(), policyDTO.getDstItf(), policyDTO.getInDevItfAlias(), policyDTO.getOutDevItfAlias(), "", isVsys, vsysName, (String)null, (String)null, (String)null, (String)null, (String)null, policyDTO.getPostPort());
            PolicyDTO policy = cmdDTO.getPolicy();
            policy.setDynamic(policyDTO.isDynamic());
            policy.setIpType(policyDTO.getIpType());
            cmdDTO.setPolicy(policy);
            logger.info("命令行生成任务为:" + JSONObject.toJSONString(cmdDTO));
            this.pushTaskService.addGenerateCmdTask(cmdDTO);
            policyDTO.setId(entity.getId());
            policyDTO.setTaskId(entity.getTaskId());
            return 0;
        }
    }

    public int insertRecommendTaskList(List<RecommendTaskEntity> list) {
        return this.recommendTaskMapper.addRecommendTaskList(list);
    }

    public RecommendTaskEntity getRecommendTaskByTaskId(int taskId) {
        List<RecommendTaskEntity> list = this.recommendTaskMapper.selectByTaskId(taskId);
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                logger.warn(String.format("策略开通任务(%d)数据大于1", taskId));
            }

            return (RecommendTaskEntity)list.get(0);
        } else {
            return null;
        }
    }

    public CommandTaskEditableEntity getRecommendTaskById(int Id) {
        List<CommandTaskEditableEntity> list = this.commandTaskEdiableMapper.selectByTaskId(Id);
        if (list != null && list.size() != 0) {
            if (list.size() > 1) {
                logger.warn(String.format("策略下发任务(%d)数据大于1", Id));
            }

            return (CommandTaskEditableEntity)list.get(0);
        } else {
            return null;
        }
    }

    public int addPathInfo(PathInfoEntity entity) {
        return this.pathInfoMapper.insert(entity);
    }

    public int addRecommendPolicyList(List<RecommendPolicyEntity> entityList) {
        if (entityList != null && entityList.size() != 0) {
            int rc = this.recommendPolicyMapper.insertRecommendPolicyList(entityList);
            if (rc != entityList.size()) {
                logger.error(String.format("任务(%d)存储错误，SQL影响行数与存储行数不一致！%d:%d", ((RecommendPolicyEntity)entityList.get(0)).getTaskId(), entityList.size(), rc));
            }

            return 0;
        } else {
            return 95;
        }
    }

    public int addCheckResult(CheckResultEntity entity) {
        return this.checkResultMapper.insert(entity);
    }

    public int addCommandTaskEditableEntity(CommandTaskEditableEntity entity) {
        return this.commandTaskEdiableMapper.insert(entity);
    }

    public List<PathInfoEntity> getPathInfoByTaskId(int taskId) {
        return this.pathInfoMapper.selectByTaskId(taskId);
    }

    public PathInfoEntity getPathInfoByPathInfoId(int taskId) {
        List<PathInfoEntity> list = this.pathInfoMapper.selectById(taskId);
        return list != null && list.size() != 0 ? (PathInfoEntity)list.get(0) : null;
    }

    public List<RecommendPolicyEntity> getPolicyListByPathInfoId(int pathInfoId) {
        return this.recommendPolicyMapper.selectByPathInfoId(pathInfoId);
    }

    public int savePathDeviceDetail(PathDeviceDetailEntity entity) {
        Map<String, String> params = new HashMap();
        params.put("pathInfoId", String.valueOf(entity.getPathInfoId()));
        params.put("deviceUuid", entity.getDeviceUuid());
        params.put("isVerifyData", String.valueOf(entity.getIsVerifyData()));
        params.put("pathIndex", String.valueOf(entity.getPathIndex()));
        List<PathDeviceDetailEntity> list = this.pathDeviceDetailMapper.selectPathDeviceDetail(params);
        int rc = 0;
        if (list != null && list.size() != 0) {
            rc = this.pathDeviceDetailMapper.update(entity);
        } else {
            rc = this.pathDeviceDetailMapper.insert(entity);
        }

        return rc;
    }

    public int insertpathDeviceDetailList(List<PathDeviceDetailEntity> list) {
        if (list != null && list.size() != 0) {
            int rc = this.pathDeviceDetailMapper.insertList(list);
            if (rc != list.size()) {
                logger.error(String.format("路径( %d )设备%s详情存储错误，SQL影响行数与存储行数不一致！ %d:%d", ((PathDeviceDetailEntity)list.get(0)).getPathInfoId(), list.size(), rc));
            }

            return 0;
        } else {
            return 95;
        }
    }

    public int saveVerifyDeitailPath(int pathInfoId, String detailPath) {
        Map<String, String> params = new HashMap();
        params.put("pathInfoId", String.valueOf(pathInfoId));
        params.put("verifyPath", detailPath);
        return this.pathDetailMapper.updateVerifyPath(params);
    }

    public void saveAnalyzeDetailPath(int pathInfoId, String detailPath) {
        PathDetailEntity entity = new PathDetailEntity();
        entity.setPathInfoId(pathInfoId);
        entity.setAnalyzePath(detailPath);
        int rc = this.pathDetailMapper.insert(entity);
        if (rc != 1) {
            logger.error(String.format("路径(%d)详情存储出错，SQL执行影响行数不正确:%d", pathInfoId, rc));
        }

    }

    public int updatePathStatus(PathInfoEntity entity) {
        return this.pathInfoMapper.updateStatusById(entity);
    }

    public void updateTaskStatus(int taskId, int status) {
        RecommendTaskEntity entity = new RecommendTaskEntity();
        entity.setId(taskId);
        entity.setStatus(status);
        if (status == 2) {
            entity.setTaskStart(new Date());
        } else if (status == 10) {
            entity.setTaskEnd(new Date());
        }

        int rc = this.recommendTaskMapper.updateByPrimaryKeySelective(entity);
        if (rc != 1) {
            logger.error(String.format("任务(%d)更新任务状态出错，SQL执行影响行数不正确:%d", taskId, rc));
        }

    }

    public void updatePathPathStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("pathStatus", String.valueOf(status));
        int rc = this.pathInfoMapper.updateStatusByPathInfoId(params);
        if (rc != 1) {
            logger.error(String.format("路径(%d)更新路径路径状态出错，SQL执行影响行数不正确:%d", pathInfoId, rc));
        }

    }

    public void updatePathAnalyzeStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("analyzeStatus", String.valueOf(status));
        int rc = this.pathInfoMapper.updateStatusByPathInfoId(params);
        if (rc != 1) {
            logger.error(String.format("路径(%d)更新路径分析状态出错，SQL执行影响行数不正确:%d", pathInfoId, rc));
        }

    }

    public void updatePathAdviceStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("adviceStatus", String.valueOf(status));
        int rc = this.pathInfoMapper.updateStatusByPathInfoId(params);
        if (rc != 1) {
            logger.error(String.format("路径(%d)更新路径策略建议状态出错，SQL执行影响行数不正确:%d", pathInfoId, rc));
        }

    }

    public int updatePathCheckStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("checkStatus", String.valueOf(status));
        return this.pathInfoMapper.updateStatusByPathInfoId(params);
    }

    public void updatePathRiskStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("riskStatus", String.valueOf(status));
        int rc = this.pathInfoMapper.updateStatusByPathInfoId(params);
        if (rc == 0) {
            logger.error(String.format("路径(%d)更新风险分析状态出错，SQL执行影响行数为0", pathInfoId));
        }

    }

    public void updatePathCmdStatusByTaskId(int taskId, int status) {
        Map<String, String> params = new HashMap();
        params.put("taskId", String.valueOf(taskId));
        params.put("cmdStatus", String.valueOf(status));
        int rc = this.pathInfoMapper.updateStatusByTaskId(params);
        if (rc == 0) {
            logger.error(String.format("任务(%d)更新路径分析状态出错，SQL执行影响行数不正确:%d", taskId, rc));
        }

    }

    public int updatePathPushStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("pushStatus", String.valueOf(status));
        return this.pathInfoMapper.updateStatusByPathInfoId(params);
    }

    public int updatePathGatherStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("gatherStatus", String.valueOf(status));
        return this.pathInfoMapper.updateStatusByPathInfoId(params);
    }

    public int updatePathVerifyStatus(int pathInfoId, int status) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("verifyStatus", String.valueOf(status));
        return this.pathInfoMapper.updateStatusByPathInfoId(params);
    }

    public void getAdvancedSettings(RecommendPolicyEntity entity) {
        String uuid = entity.getDeviceUuid();
        Integer longConnect = null == entity.getIdleTimeout() ? ConnectTypeEnum.SHORT_CONNECT.getCode() : ConnectTypeEnum.LONG_CONNECT.getCode();
        String connectType = longConnect.toString();
        int zoneSettings = AdvancedSettingsConstants.PARAM_INT_SET_BOTH_ZONE;
        if (this.advancedSettingService.isDeviceInTheList("config_no_zone", uuid)) {
            logger.debug(String.format("设备(%s)不指定域信息...命令行生成不指定域", uuid));
            zoneSettings = AdvancedSettingsConstants.PARAM_INT_SET_NO_ZONE;
        } else if (this.advancedSettingService.isDeviceInTheList("config_src_zone", uuid)) {
            logger.debug(String.format("设备(%s)指定源域信息...命令行生成指定源域", uuid));
            zoneSettings = AdvancedSettingsConstants.PARAM_INT_SET_SRC_ZONE;
        } else if (this.advancedSettingService.isDeviceInTheList("config_dst_zone", uuid)) {
            logger.debug(String.format("设备(%s)指定目的域信息...命令行生成指定目的域", uuid));
            zoneSettings = AdvancedSettingsConstants.PARAM_INT_SET_DST_ZONE;
        } else {
            logger.debug(String.format("设备(%s)使用默认方式设置域...命令行生成指定源域和目的域", uuid));
        }

        entity.setSpecifyZone(zoneSettings);
        int aclDirection = AdvancedSettingsConstants.PARAM_INT_CISCO_POLICY_IN_DIRECTION;
        if (this.advancedSettingService.isDeviceInTheList("cisco_out_itf", uuid)) {
            logger.debug(String.format("设备（%s）指定策略下发到出接口", uuid));
            aclDirection = AdvancedSettingsConstants.PARAM_INT_CISCO_POLICY_OUT_DIRECTION;
        }

        entity.setAclDirection(aclDirection);
        String isCreateRule = this.advancedSettingService.getParamValue("create_rules");
        int createRule = AdvancedSettingsConstants.PARAM_INT_CREATE_RULE;
        if (isCreateRule.equals("1")) {
            logger.debug("该策略生成命令行时生成命令行优先合并策略");
            createRule = AdvancedSettingsConstants.PARAM_INT_MERGE_RULE;
        }

        entity.setCreatePolicy(createRule);
        String isCreateObject = this.advancedSettingService.getParamValue("create_object");
        int createObject = AdvancedSettingsConstants.PARAM_INT_CREATE_OBJECT;
        if (isCreateObject.equals("1")) {
            createObject = AdvancedSettingsConstants.PARAM_INT_REFERENCE_CONTENT;
        }

        entity.setCreateObject(createObject);
        DeviceDTO beforeDevice = this.advancedSettingService.getMovePolicyDeviceByType("move_rule_before", uuid, connectType);
        DeviceDTO afterDevice = this.advancedSettingService.getMovePolicyDeviceByType("move_rule_after", uuid, connectType);
        DeviceDTO topDevice = this.advancedSettingService.getMovePolicyDeviceByType("move_rule_top", uuid, connectType);
        int rulePosition = AdvancedSettingsConstants.PARAM_INT_MOVE_POLICY_FIRST;
        String relatedRule = "";
        if (null != topDevice) {
            rulePosition = AdvancedSettingsConstants.PARAM_INT_NOT_MOVE_POLICY;
            logger.debug("该条生成策略不移动...");
        } else if (beforeDevice != null) {
            rulePosition = AdvancedSettingsConstants.PARAM_INT_MOVE_POLICY_BEFORE;
            relatedRule = beforeDevice.getRelatedRule() == null ? "" : beforeDevice.getRelatedRule().trim();
            logger.debug(String.format("该条生成策移动到策略[%s]之前...", relatedRule));
        } else if (afterDevice != null) {
            rulePosition = AdvancedSettingsConstants.PARAM_INT_MOVE_POLICY_AFTER;
            relatedRule = afterDevice.getRelatedRule() == null ? "" : afterDevice.getRelatedRule().trim();
            logger.debug(String.format("该条生成策移动到策略[%s]之前...", relatedRule));
        } else {
            logger.debug("该条生成策略需要移动到最前...");
        }

        entity.setMovePolicy(rulePosition);
        entity.setSpecificPosition(relatedRule);
    }

    public PageInfo<PushTaskVO> getPushTaskList(String taskId, String theme, String taskType, String status, String pushStatus, String revertStatus, int page, int psize, String userName, String branchLevel) {
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(taskId)) {
            params.put("taskId", taskId);
        }

        if (!AliStringUtils.isEmpty(theme)) {
            params.put("theme", theme);
        }

        if (!AliStringUtils.isEmpty(taskType)) {
            params.put("taskType", taskType);
        }

        if (!AliStringUtils.isEmpty(status)) {
            params.put("status", status);
        }

        if (!AliStringUtils.isEmpty(revertStatus)) {
            params.put("revertStatus", revertStatus);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        PageHelper.startPage(page, psize);
        List<PushTaskVO> list = this.commandTaskEditableMapper.getPushTaskList(params);
        if (ObjectUtils.isNotEmpty(list)) {
            List<Integer> taskIds = (List)list.stream().map((task) -> {
                return task.getTaskId();
            }).distinct().collect(Collectors.toList());
            Map<String, Object> cond = new HashMap();
            cond.put("taskIds", taskIds);
            List<CommandTaskEditableEntity> taskEditableEntityList = this.commandTaskEditableMapper.selectPushStuasByTaskIdList(cond);
            Iterator var16 = list.iterator();

            while(var16.hasNext()) {
                PushTaskVO pushTaskVO = (PushTaskVO)var16.next();
                List<CommandTaskEditableEntity> taskCollect = (List)taskEditableEntityList.stream().filter((task) -> {
                    return pushTaskVO.getTaskId().equals(task.getTaskId());
                }).collect(Collectors.toList());
                int pushStatusInTaskList = this.getPushStatusInTaskList(taskCollect);
                pushTaskVO.setPushStatus(pushStatusInTaskList);
            }

            if (!AliStringUtils.isEmpty(pushStatus)) {
                list = (List)list.stream().filter((s) -> {
                    return pushStatus.equals(String.valueOf(s.getPushStatus()));
                }).collect(Collectors.toList());
            }
        }

        PageInfo<PushTaskVO> pageInfo = new PageInfo(list);
        return pageInfo;
    }

    public int updateCommandTaskStatus(int taskId, int status) {
        List<CommandTaskEditableEntity> list = this.commandTaskEditableMapper.selectByTaskId(taskId);
        if (list != null && list.size() != 0) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var4.next();
                CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
                newEntity.setId(entity.getId());
                newEntity.setStatus(status);
                this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
            }

            return 0;
        } else {
            return 255;
        }
    }

    public int updateCommandTaskStatus(List<CommandTaskEditableEntity> list, int status) {
        if (CollectionUtils.isEmpty(list)) {
            return 255;
        } else {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var3.next();
                CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
                newEntity.setId(entity.getId());
                newEntity.setStatus(status);
                this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
            }

            return 0;
        }
    }

    public int updateCommandTaskPushStatus(int taskId, int status) {
        List<CommandTaskEditableEntity> list = this.commandTaskEditableMapper.selectByTaskId(taskId);
        if (list != null && list.size() != 0) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var4.next();
                CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
                newEntity.setId(entity.getId());
                newEntity.setPushStatus(status);
                newEntity.setRevertStatus((Integer)null);
                this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
            }

            return 0;
        } else {
            return 255;
        }
    }

    public int updateCommandTaskRevertStatus(int taskId, int status) {
        List<CommandTaskEditableEntity> list = this.commandTaskEditableMapper.selectByTaskId(taskId);
        if (list != null && list.size() != 0) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var4.next();
                CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
                newEntity.setId(entity.getId());
                newEntity.setRevertStatus(status);
                newEntity.setPushStatus((Integer)null);
                this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
            }

            return 0;
        } else {
            return 255;
        }
    }

    public int updateCommandTaskPushOrRevertStatus(List<CommandTaskEditableEntity> list, int status, boolean isRever) {
        if (CollectionUtils.isEmpty(list)) {
            return 255;
        } else {
            CommandTaskEditableEntity newEntity;
            for(Iterator var4 = list.iterator(); var4.hasNext(); this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity)) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var4.next();
                newEntity = new CommandTaskEditableEntity();
                newEntity.setId(entity.getId());
                if (isRever) {
                    newEntity.setRevertStatus(status);
                    newEntity.setPushStatus((Integer)null);
                } else {
                    newEntity.setPushStatus(status);
                    newEntity.setRevertStatus((Integer)null);
                }
            }

            return 0;
        }
    }

    public int updateCommandTaskStatusById(int id, int status) {
        CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
        newEntity.setId(id);
        newEntity.setPushStatus(status);
        newEntity.setRevertStatus((Integer)null);
        this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
        return 0;
    }

    public int updateCommandTaskRevertStatusById(int id, int status) {
        CommandTaskEditableEntity newEntity = new CommandTaskEditableEntity();
        newEntity.setId(id);
        newEntity.setRevertStatus(status);
        newEntity.setPushStatus((Integer)null);
        this.commandTaskEditableMapper.updateByPrimaryKeySelective(newEntity);
        return 0;
    }

    public int updateStopTaskPushStatus(int taskId, int status) {
        List<CommandTaskEditableEntity> list = this.commandTaskEditableMapper.selectByTaskId(taskId);
        if (list != null && list.size() != 0) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var4.next();
                Map<String, String> parmas = new HashMap();
                parmas.put("id", String.valueOf(entity.getId()));
                parmas.put("pushStatus", String.valueOf(status));
                parmas.put("pushScheduleInit", "Y");
                parmas.put("enableEmail", "false");
                parmas.put("receiverEmailInit", "Y");
                this.commandTaskEditableMapper.updateForStopTask(parmas);
            }

            return 0;
        } else {
            return 255;
        }
    }

    public void addTaskRisk(int pathInfoId, String riskId) {
        PolicyRiskEntity riskEntity = new PolicyRiskEntity();
        riskEntity.setPathInfoId(pathInfoId);
        riskEntity.setRuleId(riskId);
        int rc = this.policyRiskMapper.insert(riskEntity);
        if (rc == 0) {
            logger.error(String.format("路径(%d)增加风险数据出错，SQL执行影响行数为0", pathInfoId));
        }

    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void removePolicyTasks(List<Integer> list) {
        Iterator var2 = list.iterator();

        while(var2.hasNext()) {
            int taskId = (Integer)var2.next();
            logger.info(String.format("删除任务[%d]任务生成数据信息...", taskId));
            this.recommendTaskMapper.deleteByTaskId(taskId);
            List<CommandTaskEditableEntity> editableList = this.commandTaskEdiableMapper.selectByTaskId(taskId);
            if (!CollectionUtils.isEmpty(editableList)) {
                int status = this.getPushStatusInTaskList(editableList);
                if (3 == status || 0 == status) {
                    logger.info(String.format("删除任务(%d)所有关联的高级设置ruleId...", taskId));
                    this.advancedSettingService.removeRuleIdByTaskId(taskId);
                }
            }

            logger.info(String.format("删除任务(%d)任务下发数据信息...", taskId));
            this.commandTaskEdiableMapper.deleteByTaskId(taskId);
        }

    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void deleteTasks(List<Integer> list, int type) {
        Map<String, Object> cond = new HashMap();
        cond.put("ids", list);
        logger.info(String.format("删除任务[%s]路径详情...", list.toString()));
        this.pathDetailMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]路径风险分析结果...", list.toString()));
        this.policyRiskMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]路径策略检查结果...", list.toString()));
        this.checkResultMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]路径开通策略...", list.toString()));
        this.recommendPolicyMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]路径设备详情...", list.toString()));
        this.pathDeviceDetailMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]策略建议数据...", list.toString()));
        this.mergedPolicyMapper.deleteByTaskList(cond);
        List<CommandTaskEditableEntity> editableList = this.commandTaskEdiableMapper.selectByTaskIdList(cond);
        if (!CollectionUtils.isEmpty(editableList)) {
            int mergStatus = this.getPushStatusInTaskList(editableList);
            if (3 == mergStatus || 0 == mergStatus) {
                Iterator var6 = editableList.iterator();

                while(var6.hasNext()) {
                    CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var6.next();
                    this.advancedSettingService.removeRuleIdByTaskId(entity.getTaskId());
                }
            }
        }

        logger.info(String.format("删除任务[%s]命令行...", list.toString()));
        this.commandTaskEdiableMapper.deleteByTaskList(cond);
        logger.info(String.format("删除任务[%s]路径信息...", list.toString()));
        this.pathInfoMapper.deleteByTaskList(cond);
        if (type == 0) {
            logger.info(String.format("删除任务[%s]任务信息...", list.toString()));
            this.recommendTaskMapper.deleteByTaskList(cond);
        }

        if (type == 1) {
            logger.info(String.format("更改任务[%s]状态为0仿真未开始...", list.toString()));
            this.recommendTaskMapper.updateByTaskList(cond);
        }

    }

    public RecommendPolicyEntity getPolicyByPolicyId(Integer policyId) {
        List<RecommendPolicyEntity> list = this.recommendPolicyMapper.selectById(policyId);
        return list != null && list.size() != 0 ? (RecommendPolicyEntity)list.get(0) : null;
    }

    public int setPathEnable(Integer pathInfoId, String enable) {
        Map<String, String> params = new HashMap();
        params.put("id", String.valueOf(pathInfoId));
        params.put("enablePath", enable);
        return this.pathInfoMapper.enablePath(params);
    }

    public boolean isCheckRule() {
        String rc = this.advancedSettingService.getParamValue("config_check_rule");
        return !rc.equals("0");
    }

    public boolean isCheckRisk() {
        String rc = this.advancedSettingService.getParamValue("config_check_risk");
        return !rc.equals("0");
    }

    public List<RecommendPolicyEntity> getMergedPolicyList(int taskId) {
        return this.mergedPolicyMapper.selectByTaskId(taskId);
    }

    public int addMergedPolicyList(List<RecommendPolicyEntity> list) {
        return this.mergedPolicyMapper.insertRecommendPolicyList(list);
    }

    public int addMergedPolicy(RecommendPolicyEntity entity) {
        return this.mergedPolicyMapper.insert(entity);
    }

    public boolean isUseCurrentObject() {
        String rc = this.advancedSettingService.getParamValue("use_current_object");
        return !rc.equals("0");
    }

    public boolean isUseCurrentAddressObject() {
        String rc = this.advancedSettingService.getParamValue("use_address_object");
        return !rc.equals("0");
    }

    public void removeCommandsByTask(int taskId) {
        logger.info(String.format("删除任务[%d]命令行...", taskId));
        this.commandTaskEditableMapper.deleteByTaskId(taskId);
        logger.info(String.format("删除任务[%d]合并后策略...", taskId));
        this.mergedPolicyMapper.deleteByTaskId(taskId);
    }

    public void updateTaskById(RecommendTaskEntity entity) {
        this.recommendTaskMapper.updateByPrimaryKey(entity);
    }

    public List<DeviceDimension> searchDeviceDimensionByTaskId(Integer taskId) {
        return this.recommendPolicyMapper.selectDeviceDimensionByTaskId(taskId);
    }

    public List<RecommendPolicyEntity> selectByDeviceDimension(DeviceDimension deviceDimension, Integer taskId) {
        Map<String, String> params = new HashMap();
        params.put("taskId", String.valueOf(taskId));
        params.put("deviceUuid", deviceDimension.getDeviceUuid());
        params.put("srcZone", deviceDimension.getSrcZone());
        params.put("dstZone", deviceDimension.getDstZone());
        params.put("inDevItf", deviceDimension.getInDevItf());
        params.put("outDevItf", deviceDimension.getOutDevItf());
        return this.recommendPolicyMapper.selectByDeviceDimension(params);
    }

    public int addPathInfoList(List<PathInfoEntity> list) {
        if (list != null && list.size() != 0) {
            int rc = this.pathInfoMapper.addPathInfoList(list);
            if (rc != list.size()) {
                logger.error(String.format("任务(%d)存储错误，SQL影响行数与存储行数不一致！%d:%d", ((PathInfoEntity)list.get(0)).getTaskId(), list.size(), rc));
            }

            return 0;
        } else {
            return 95;
        }
    }

    public int getAclDirection(String deviceUuid) {
        int aclDirection = AdvancedSettingsConstants.PARAM_INT_CISCO_POLICY_IN_DIRECTION;
        if (this.advancedSettingService.isDeviceInTheList("cisco_out_itf", deviceUuid)) {
            logger.debug(String.format("设备（%s）指定策略下发到出接口", deviceUuid));
            aclDirection = AdvancedSettingsConstants.PARAM_INT_CISCO_POLICY_OUT_DIRECTION;
        }

        return aclDirection;
    }

    public NodeEntity getDeviceByManageIp(String deviceIp) {
        return this.policyRecommendNodeMapper.getTheNodeByIp(deviceIp);
    }

    private List<RecommendTaskEntity> searchTaskList(String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, String taskType, int page, int psize, String branchLevel, String deviceUuid) {
        PageHelper.startPage(page, psize);
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(theme)) {
            params.put("theme", theme);
        }

        if (!AliStringUtils.isEmpty(orderNumber)) {
            params.put("orderNumber", orderNumber);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        if (!AliStringUtils.isEmpty(description)) {
            params.put("description", description);
        }

        if (!AliStringUtils.isEmpty(srcIp)) {
            params.put("srcIp", srcIp);
        }

        if (!AliStringUtils.isEmpty(dstIp)) {
            params.put("dstIp", dstIp);
        }

        if (!AliStringUtils.isEmpty(dstPort)) {
            params.put("dstPort", dstPort);
        }

        if (!AliStringUtils.isEmpty(protocol)) {
            params.put("protocol", protocol);
        }

        if (!AliStringUtils.isEmpty(status)) {
            if (status.contains(",")) {
                status = status.split(",")[0];
            }

            params.put("status", String.valueOf(status));
        }

        if (!AliStringUtils.isEmpty(taskType)) {
            params.put("taskType", taskType);
        }

        if (!AliStringUtils.isEmpty(deviceUuid)) {
            params.put("deviceUuid", deviceUuid);
        }

        List<RecommendTaskEntity> list = this.recommendTaskMapper.searchTask(params);
        return list;
    }

    private List<RecommendTaskEntity> searchPolicyTaskList(String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, String taskType, String startTime, String endTime, String branchLevel, String deviceUuid) {
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(theme)) {
            params.put("theme", theme);
        }

        if (!AliStringUtils.isEmpty(orderNumber)) {
            params.put("orderNumber", orderNumber);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        if (!AliStringUtils.isEmpty(startTime)) {
            params.put("startTime", startTime);
        }

        if (!AliStringUtils.isEmpty(endTime)) {
            params.put("endTime", endTime);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        if (!AliStringUtils.isEmpty(description)) {
            params.put("description", description);
        }

        if (!AliStringUtils.isEmpty(srcIp)) {
            params.put("srcIp", srcIp);
        }

        if (!AliStringUtils.isEmpty(dstIp)) {
            params.put("dstIp", dstIp);
        }

        if (!AliStringUtils.isEmpty(dstPort)) {
            params.put("dstPort", dstPort);
        }

        if (!AliStringUtils.isEmpty(protocol)) {
            params.put("protocol", protocol);
        }

        if (!AliStringUtils.isEmpty(status)) {
            if (status.contains(",")) {
                status = status.split(",")[0];
            }

            params.put("status", String.valueOf(status));
        }

        if (!AliStringUtils.isEmpty(taskType)) {
            params.put("taskType", taskType);
        }

        if (!AliStringUtils.isEmpty(deviceUuid)) {
            params.put("deviceUuid", deviceUuid);
        }

        List<RecommendTaskEntity> list = this.recommendTaskMapper.searchPolicyTask(params);
        return list;
    }

    private List<RecommendTaskEntity> searchNatTaskList(String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, String taskType, int page, int psize, String taskIds, Integer id, String branchLevel, String deviceUuid) {
        PageHelper.startPage(page, psize);
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(theme)) {
            params.put("theme", theme);
        }

        if (!AliStringUtils.isEmpty(orderNumber)) {
            params.put("orderNumber", orderNumber);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        if (!AliStringUtils.isEmpty(description)) {
            params.put("description", description);
        }

        if (!AliStringUtils.isEmpty(srcIp)) {
            params.put("srcIp", srcIp);
        }

        if (!AliStringUtils.isEmpty(dstIp)) {
            params.put("dstIp", dstIp);
        }

        if (!AliStringUtils.isEmpty(dstPort)) {
            params.put("dstPort", dstPort);
        }

        if (!AliStringUtils.isEmpty(protocol)) {
            params.put("protocol", protocol);
        }

        if (!AliStringUtils.isEmpty(status)) {
            if (status.contains(",")) {
                status = status.split(",")[0];
            }

            params.put("status", String.valueOf(status));
        }

        if (!AliStringUtils.isEmpty(taskType)) {
            params.put("taskType", taskType);
        }

        if (!AliStringUtils.isEmpty(deviceUuid)) {
            params.put("deviceUuid", deviceUuid);
        }

        if (!AliStringUtils.isEmpty(taskIds)) {
            params.put("taskIds", taskIds);
        }

        params.put("id", id == null ? "" : String.valueOf(id));
        List<RecommendTaskEntity> list = this.recommendTaskMapper.searchNatTask(params);
        return list;
    }

    private List<RecommendTaskEntity> searchNatPolicyTaskList(String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, String startTime, String endTime, String taskType, String taskIds, Integer id, String branchLevel, String deviceUuid) {
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(theme)) {
            params.put("theme", theme);
        }

        if (!AliStringUtils.isEmpty(orderNumber)) {
            params.put("orderNumber", orderNumber);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        if (!AliStringUtils.isEmpty(startTime)) {
            params.put("startTime", startTime);
        }

        if (!AliStringUtils.isEmpty(endTime)) {
            params.put("endTime", endTime);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        if (!AliStringUtils.isEmpty(description)) {
            params.put("description", description);
        }

        if (!AliStringUtils.isEmpty(srcIp)) {
            params.put("srcIp", srcIp);
        }

        if (!AliStringUtils.isEmpty(dstIp)) {
            params.put("dstIp", dstIp);
        }

        if (!AliStringUtils.isEmpty(dstPort)) {
            params.put("dstPort", dstPort);
        }

        if (!AliStringUtils.isEmpty(protocol)) {
            params.put("protocol", protocol);
        }

        if (!AliStringUtils.isEmpty(status)) {
            if (status.contains(",")) {
                status = status.split(",")[0];
            }

            params.put("status", String.valueOf(status));
        }

        if (!AliStringUtils.isEmpty(taskType)) {
            params.put("taskType", taskType);
        }

        if (!AliStringUtils.isEmpty(deviceUuid)) {
            params.put("deviceUuid", deviceUuid);
        }

        params.put("taskIds", taskIds);
        params.put("id", id == null ? "" : String.valueOf(id));
        List<RecommendTaskEntity> list = this.recommendTaskMapper.searchNatPolicyTask(params);
        return list;
    }

    private List<RecommendTaskEntity> searchRecommendTaskList(SearchRecommendTaskDTO searchRecommendTaskDTO) {
        PageHelper.startPage(searchRecommendTaskDTO.getPage(), searchRecommendTaskDTO.getPSize());
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getBatchId())) {
            params.put("batchId", searchRecommendTaskDTO.getBatchId());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getId())) {
            params.put("id", searchRecommendTaskDTO.getId());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getTheme())) {
            params.put("theme", searchRecommendTaskDTO.getTheme());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getOrderNumber())) {
            params.put("orderNumber", searchRecommendTaskDTO.getOrderNumber());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getUserName())) {
            params.put("userName", searchRecommendTaskDTO.getUserName());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getDescription())) {
            params.put("description", searchRecommendTaskDTO.getDescription());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getSrcIp())) {
            params.put("srcIp", searchRecommendTaskDTO.getSrcIp());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getDstIp())) {
            params.put("dstIp", searchRecommendTaskDTO.getDstIp());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getDstPort())) {
            params.put("dstPort", searchRecommendTaskDTO.getDstPort());
        }

        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getProtocol()) && !searchRecommendTaskDTO.getIsServiceAny()) {
            params.put("protocol", searchRecommendTaskDTO.getProtocol());
        }

        String min;
        if (!AliStringUtils.isEmpty(searchRecommendTaskDTO.getStatus()) && !searchRecommendTaskDTO.getStatus().equals(String.valueOf(0))) {
            if (searchRecommendTaskDTO.getStatus().equals(String.valueOf(1))) {
                params.put("status", String.valueOf(0));
            } else {
                min = "1";
                String max = "10";
                String var5 = searchRecommendTaskDTO.getStatus();
                byte var6 = -1;
                switch(var5.hashCode()) {
                    case 50:
                        if (var5.equals("2")) {
                            var6 = 0;
                        }
                        break;
                    case 51:
                        if (var5.equals("3")) {
                            var6 = 1;
                        }
                        break;
                    case 52:
                        if (var5.equals("4")) {
                            var6 = 2;
                        }
                }

                switch(var6) {
                    case 0:
                        min = "1";
                        max = "10";
                        break;
                    case 1:
                        min = "11";
                        max = "20";
                        break;
                    case 2:
                        min = "21";
                        max = "31";
                }

                params.put("min", min);
                params.put("max", max);
            }
        }

        if(searchRecommendTaskDTO.getBranchLevel() == null || searchRecommendTaskDTO.getBranchLevel().equals("")){
            min = this.remoteBranchService.likeBranch(searchRecommendTaskDTO.getAuthentication().getName());
        }else{
            min = searchRecommendTaskDTO.getBranchLevel() + "%";
        }

        if (StringUtils.isNotEmpty(min)) {
            params.put("branchLevel", min);
        }

        if (searchRecommendTaskDTO.getTaskType() != null) {
            params.put("taskType", searchRecommendTaskDTO.getTaskType());
        }

        new ArrayList();
        List list;
        if (searchRecommendTaskDTO.getIsServiceAny()) {
            list = this.recommendTaskMapper.searchRecommendTaskWithServiceAny(params);
        } else {
            list = this.recommendTaskMapper.searchRecommendTask(params);
        }

        return list;
    }

    public PageInfo<BatchTaskVO> searchBatchTaskList(String theme, String userName, int page, int psize) {
        PageHelper.startPage(page, psize);
        Map<String, Object> params = new HashMap();
        if (!AliStringUtils.isEmpty(theme)) {
            params.put("orderNumber", theme);
        }

        if (!AliStringUtils.isEmpty(userName)) {
            params.put("userName", userName);
        }

        List<RecommendTaskCheckEntity> list = this.recommendTaskCheckMapper.searchTask(params);
        PageInfo<RecommendTaskCheckEntity> tmpPageInfo = new PageInfo(list);
        List<BatchTaskVO> batchTaskVOList = new ArrayList();
        Iterator var9 = list.iterator();

        while(var9.hasNext()) {
            RecommendTaskCheckEntity entity = (RecommendTaskCheckEntity)var9.next();
            BatchTaskVO vo = new BatchTaskVO();
            vo.setId(entity.getId());
            params = new HashMap();
            params.put("batchId", String.valueOf(entity.getId()));
            List<RecommendTaskEntity> entityList = this.recommendTaskMapper.searchRecommendTask(params);
            vo.setCount(entityList.size());
            this.setStatistics(vo, entityList);
            vo.setTaskIds(entity.getTaskId());
            vo.setCreateTime(entity.getCreateTime());
            vo.setUserName(entity.getUserName());
            vo.setTheme(entity.getOrderNumber());
            vo.setType(entity.getBatchType());
            vo.setStatus(entity.getStatus());
            vo.setResult(entity.getResult());
            batchTaskVOList.add(vo);
        }

        PageInfo<BatchTaskVO> pageInfo = new PageInfo();
        BeanUtils.copyProperties(tmpPageInfo, pageInfo);
        pageInfo.setList(batchTaskVOList);
        return pageInfo;
    }

    public RecommendTaskCheckEntity selectBatchTaskById(Integer id) {
        return this.recommendTaskCheckMapper.selectByPrimaryKey(id);
    }

    public void addBatchTask(RecommendTaskCheckEntity entity) {
        this.recommendTaskCheckMapper.insert(entity);
    }

    public void updateBatchTask(RecommendTaskCheckEntity entity) {
        this.recommendTaskCheckMapper.updateByPrimaryKeySelective(entity);
    }

    public RiskRuleInfoEntity getRiskInfoByRuleId(String ruleId) {
        List<RiskRuleInfoEntity> list = this.riskMapper.getRiskInfoByRuleId(ruleId);
        return list != null && list.size() != 0 ? (RiskRuleInfoEntity)list.get(0) : null;
    }

    public List<NodeEntity> getNodeList() {
        return this.policyRecommendNodeMapper.getNodeList();
    }

    public TaskStatusBranchLevelsDTO getPushTaskStatusList(String userName) {
        TaskStatusBranchLevelsDTO statusBranchLevelsDTO = new TaskStatusBranchLevelsDTO();
        String branchLevel = this.remoteBranchService.likeBranch(userName);
        statusBranchLevelsDTO.setBranchLevel(branchLevel);
        List<PushStatus> pushTaskStatusList = this.commandTaskEditableMapper.getPushTaskStatusList(branchLevel);
        statusBranchLevelsDTO.setPushStatuses(pushTaskStatusList);
        return statusBranchLevelsDTO;
    }

    private void setStatistics(BatchTaskVO vo, List<RecommendTaskEntity> list) {
        int notStart = 0;
        int waiting = 0;
        int running = 0;
        int analyzed = 0;
        Date start = null;
        Date end = null;
        Boolean hasUnfinished = false;
        Iterator var10 = list.iterator();

        while(var10.hasNext()) {
            RecommendTaskEntity entity = (RecommendTaskEntity)var10.next();
            switch(entity.getStatus()) {
                case 0:
                    ++notStart;
                    break;
                case 1:
                    ++waiting;
                    break;
                case 2:
                    ++running;
                    break;
                default:
                    ++analyzed;
            }

            if (entity.getTaskStart() != null) {
                if (start == null) {
                    start = entity.getTaskStart();
                } else if (start.after(entity.getTaskStart())) {
                    start = entity.getTaskStart();
                }
            }

            if (entity.getTaskEnd() != null) {
                if (end == null) {
                    end = entity.getTaskEnd();
                } else if (end.before(entity.getTaskEnd())) {
                    end = entity.getTaskEnd();
                }
            } else {
                hasUnfinished = true;
            }
        }

        if (hasUnfinished) {
            vo.setTaskEnd((Date)null);
            end = new Date();
        } else {
            vo.setTaskEnd(end);
        }

        vo.setTaskStart(start);
        if (start != null) {
            Long duration = end.getTime() - start.getTime();
            vo.setDuration(this.formatTime(duration));
        }

        vo.setNotStart(notStart);
        vo.setRunning(running);
        vo.setWaiting(waiting);
        vo.setAnalyzed(analyzed);
    }

    String formatTime(Long duration) {
        Long seconds = duration / 1000L;
        if (seconds < 60L) {
            return String.format("%d秒", seconds);
        } else if (seconds < 3600L) {
            return String.format("%d分%d秒", seconds / 60L, seconds % 60L);
        } else if (seconds < 86400L) {
            return String.format("%d小时%d分%d秒", seconds / 3600L, seconds % 3600L / 60L, seconds % 60L);
        } else {
            return seconds < 604800L ? String.format("%d天%d小时%d分%d秒", seconds / 86400L, seconds % 86400L / 3600L, seconds % 3600L / 60L, seconds % 60L) : "大于一周";
        }
    }

    String formatZoneItfString(String zone, String itf) {
        if (AliStringUtils.isEmpty(zone)) {
            return AliStringUtils.isEmpty(itf) ? "" : itf;
        } else {
            return AliStringUtils.isEmpty(itf) ? zone : zone + ", " + itf;
        }
    }

    public List<RecommendTaskEntity> getTaskListByTime(String startTime, String endTime, Authentication authentication) {
        List<RecommendTaskEntity> list = this.searchRecommendTaskListByTime(startTime, endTime, authentication);
        Iterator var5 = list.iterator();

        while(true) {
            RecommendTaskEntity entity;
            do {
                if (!var5.hasNext()) {
                    return list;
                }

                entity = (RecommendTaskEntity)var5.next();
            } while(AliStringUtils.isEmpty(entity.getServiceList()));

            JSONArray jsonArray = JSONArray.parseArray(entity.getServiceList());
            List<ServiceDTO> serviceList = jsonArray.toJavaList(ServiceDTO.class);
            Iterator var9 = serviceList.iterator();

            while(var9.hasNext()) {
                ServiceDTO service = (ServiceDTO)var9.next();
                service.setProtocol(ProtocolUtils.getProtocolByString(service.getProtocol()));
                service.setSrcPorts(AliStringUtils.isEmpty(service.getSrcPorts()) ? null : service.getSrcPorts());
                service.setDstPorts(AliStringUtils.isEmpty(service.getDstPorts()) ? null : service.getDstPorts());
            }

            entity.setServiceList(JSONObject.toJSONString(serviceList));
        }
    }

    private String getTaskPathAnalyzeStatusByTaskId(Integer taskId, List<PathInfoEntity> pathList) {
        StringBuilder sb = new StringBuilder();
        List<PathInfoEntity> pathInfos = (List)pathList.stream().filter((path) -> {
            return taskId.equals(path.getTaskId());
        }).collect(Collectors.toList());
        List<Integer> ayalyzeStatusList = (List)pathInfos.stream().map((path) -> {
            return path.getAnalyzeStatus();
        }).distinct().collect(Collectors.toList());
        ayalyzeStatusList.forEach((status) -> {
            Long statusNum = pathInfos.stream().filter((path) -> {
                return path.getAnalyzeStatus().equals(status);
            }).count();
            sb.append(status).append(":").append(statusNum).append(",");
        });
        return sb.length() > 1 ? sb.deleteCharAt(sb.length() - 1).toString() : sb.toString();
    }

    private String getTaskVerifyPathStatusByTaskId(Integer taskId, List<PathInfoEntity> pathList) {
        StringBuilder sb = new StringBuilder();
        List<PathInfoEntity> pathInfos = (List)pathList.stream().filter((path) -> {
            return taskId.equals(path.getTaskId());
        }).collect(Collectors.toList());
        List<Integer> verifyStatusList = (List)pathInfos.stream().map((path) -> {
            return path.getPathStatus();
        }).distinct().collect(Collectors.toList());
        verifyStatusList.forEach((status) -> {
            Long statusNum = pathInfos.stream().filter((path) -> {
                return path.getPathStatus().equals(status);
            }).count();
            int recommendStatus = status;
            if (status.equals(1)) {
                recommendStatus = 4;
            } else if (status.equals(2)) {
                recommendStatus = 1;
            }

            sb.append(recommendStatus).append(":").append(statusNum).append(",");
        });
        return sb.length() > 1 ? sb.deleteCharAt(sb.length() - 1).toString() : sb.toString();
    }

    public List<RecommendTaskEntity> selectExecuteRecommendTask() {
        return this.recommendTaskMapper.selectExecuteRecommendTask();
    }

    private List<RecommendTaskEntity> searchRecommendTaskListByTime(String startTime, String endTime, Authentication authentication) {
        String branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        Map<String, Object> params = new HashMap();
        if (startTime != null) {
            params.put("startTime", startTime);
        }

        if (endTime != null) {
            params.put("endTime", endTime);
        }

        if (StringUtils.isNotEmpty(branchLevel)) {
            params.put("branchLevel", branchLevel);
        }

        List<RecommendTaskEntity> list = this.recommendTaskMapper.searchRecommendTask(params);
        return list;
    }

    public int getPushStatusInTaskList(List<CommandTaskEditableEntity> entityList) {
        List<Integer> pushStatusCollect = (List)entityList.stream().map((task) -> {
            return task.getPushStatus();
        }).distinct().collect(Collectors.toList());
        if (pushStatusCollect.size() == 1) {
            return (Integer)pushStatusCollect.get(0);
        } else {
            Boolean hasRun = false;
            Boolean hasUnStart = false;
            Boolean hasFail = false;
            Iterator var6 = pushStatusCollect.iterator();

            while(var6.hasNext()) {
                Integer temp = (Integer)var6.next();
                if (1 == temp) {
                    hasRun = true;
                    break;
                }

                if (0 == temp) {
                    hasUnStart = true;
                }

                if (3 == temp) {
                    hasFail = true;
                }
            }

            if (hasRun) {
                return 1;
            } else {
                return hasUnStart && hasFail ? 3 : 5;
            }
        }
    }

    public int getPolicyStatusByPushStatus(int pushStatus) {
        if (2 == pushStatus) {
            return 20;
        } else {
            return 3 == pushStatus ? 19 : 13;
        }
    }

    public void setPathInfoStatus(Integer id, Integer gatherStatus, Integer verifyStatus, Integer pathStatus, Integer pushStatus) {
        PathInfoEntity pathInfoEntity = new PathInfoEntity();
        pathInfoEntity.setId(id);
        pathInfoEntity.setGatherStatus(gatherStatus);
        pathInfoEntity.setVerifyStatus(verifyStatus);
        pathInfoEntity.setPathStatus(pathStatus);
        pathInfoEntity.setPushStatus(pushStatus);
        pathInfoEntity.setAccessAnalyzeStatus((Integer)null);
        pathInfoEntity.setAnalyzeStatus((Integer)null);
        pathInfoEntity.setAdviceStatus((Integer)null);
        pathInfoEntity.setCheckStatus((Integer)null);
        pathInfoEntity.setRiskStatus((Integer)null);
        pathInfoEntity.setCmdStatus((Integer)null);
        this.updatePathStatus(pathInfoEntity);
        this.saveVerifyDeitailPath(id, "");
    }

    private List<PolicyRecommendSecurityPolicyVO> getSecurityPolicyList(List<PathDeviceDetailEntity> deviceDetailList) {
        List<PolicyRecommendSecurityPolicyVO> securityPolicyList = new ArrayList();
        if (CollectionUtils.isEmpty(deviceDetailList)) {
            return securityPolicyList;
        } else {
            Iterator var3 = deviceDetailList.iterator();

            while(true) {
                PathDeviceDetailEntity deviceDetailEntity;
                DeviceDetailRunVO deviceDetailRunVO;
                do {
                    label40:
                    do {
                        while(true) {
                            while(var3.hasNext()) {
                                deviceDetailEntity = (PathDeviceDetailEntity)var3.next();
                                if (!StringUtils.isEmpty(deviceDetailEntity.getDeviceDetail()) && !StringUtils.isEmpty(deviceDetailEntity.getDeviceUuid())) {
                                    NodeEntity nodeEntity = this.getTheNodeByUuid(deviceDetailEntity.getDeviceUuid());
                                    if (nodeEntity != null && StringUtils.equals(nodeEntity.getType(), "0")) {
                                        String deviceDetail = deviceDetailEntity.getDeviceDetail();
                                        JSONObject deviceDetailObject = JSONObject.parseObject(deviceDetail);
                                        DeviceDetailRO detailDeviceRO = (DeviceDetailRO)deviceDetailObject.toJavaObject(DeviceDetailRO.class);
                                        deviceDetailRunVO = this.client.parseDetailRunRO(detailDeviceRO);
                                        continue label40;
                                    }

                                    logger.info("未找到设备或设备类型不是防火墙，忽略合并");
                                } else {
                                    logger.info("设备uuid或设备详情不存在，忽略合并");
                                }
                            }

                            return securityPolicyList;
                        }
                    } while(deviceDetailRunVO == null);
                } while(!CollectionUtils.isNotEmpty(deviceDetailRunVO.getSafeList()));

                List<PolicyDetailVO> safeListDetail = deviceDetailRunVO.getSafeList();
                List<PolicyRecommendSecurityPolicyVO> securityPolicyVOList = new ArrayList();
                Iterator var12 = safeListDetail.iterator();

                while(var12.hasNext()) {
                    PolicyDetailVO vo = (PolicyDetailVO)var12.next();
                    PolicyRecommendSecurityPolicyVO policyVO = new PolicyRecommendSecurityPolicyVO();
                    BeanUtils.copyProperties(vo, policyVO);
                    policyVO.setDeviceUuid(deviceDetailEntity.getDeviceUuid());
                    policyVO.setIsAble(vo.getIsAble());
                    policyVO.setDescription(vo.getDescription());
                    securityPolicyVOList.add(policyVO);
                }

                securityPolicyList.addAll(securityPolicyVOList);
            }
        }
    }

    private boolean isSameIdleTimeout(RecommendPolicyEntity newPolicy, PolicyRecommendSecurityPolicyVO securityPolicy) {
        if (ObjectUtils.isEmpty(newPolicy.getIdleTimeout()) && ObjectUtils.isEmpty(securityPolicy.getIdleTimeout())) {
            return true;
        } else {
            String securityPolicyIdleTimeout = "";
            if (ObjectUtils.isNotEmpty(newPolicy.getIdleTimeout()) && ObjectUtils.isNotEmpty(securityPolicy.getIdleTimeout())) {
                if (StringUtils.containsIgnoreCase(securityPolicy.getIdleTimeout(), "s")) {
                    securityPolicyIdleTimeout = securityPolicy.getIdleTimeout().replace("s", "");
                }

                if (newPolicy.getIdleTimeout() == Integer.parseInt(securityPolicyIdleTimeout)) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean isSameTime(RecommendPolicyEntity newPolicy, PolicyRecommendSecurityPolicyVO securityPolicy) {
        if (ObjectUtils.isEmpty(newPolicy.getStartTime()) && ObjectUtils.isEmpty(newPolicy.getEndTime()) && StringUtils.isEmpty(securityPolicy.getTime())) {
            return true;
        } else {
            try {
                if (StringUtils.isNotEmpty(securityPolicy.getTime())) {
                    ResultRO<List<ObjectDetailRO>> timeResult = this.queryObjectDetail(newPolicy.getDeviceUuid(), securityPolicy.getTime(), 3);
                    if (ObjectUtils.isNotEmpty(newPolicy.getStartTime()) && ObjectUtils.isNotEmpty(newPolicy.getEndTime()) && ObjectUtils.isEmpty(timeResult)) {
                        return false;
                    }

                    if (ObjectUtils.isNotEmpty(timeResult)) {
                        List<ObjectDetailRO> resultList = (List)timeResult.getData();
                        Iterator var5 = resultList.iterator();

                        String startTimeStr;
                        String endTimeStr;
                        String startTime;
                        String endTime;
                        do {
                            ObjectDetailRO object;
                            do {
                                if (!var5.hasNext()) {
                                    return false;
                                }

                                object = (ObjectDetailRO)var5.next();
                            } while(!StringUtils.startsWith(object.getValue(), "单次时间"));

                            String timeStr = object.getValue();
                            startTimeStr = timeStr.substring(5, 15) + " " + timeStr.substring(16, 24);
                            endTimeStr = timeStr.substring(36, 46) + " " + timeStr.substring(47, 55);
                            if (ObjectUtils.isEmpty(newPolicy.getStartTime()) || ObjectUtils.isEmpty(newPolicy.getEndTime())) {
                                return false;
                            }

                            startTime = DateUtils.formatDateTime(newPolicy.getStartTime());
                            endTime = DateUtils.formatDateTime(newPolicy.getEndTime());
                        } while(!StringUtils.equals(startTimeStr, startTime) || !StringUtils.equals(endTimeStr, endTime));

                        return true;
                    }
                }
            } catch (Exception var12) {
                logger.error("获取时间对象异常");
            }

            return false;
        }
    }

    private void buildEditPolicyData(RecommendPolicyEntity newPolicy, PolicyRecommendSecurityPolicyVO securityPolicy, Integer mergeProperty, String mergePolicyName, String mergeValue) {
        newPolicy.setMergeProperty(mergeProperty);
        newPolicy.setEditPolicyName(mergePolicyName);
        newPolicy.setMergeValue(mergeValue);
        newPolicy.setSecurityPolicy(securityPolicy);
    }

    public ResultRO<List<ObjectDetailRO>> queryObjectDetail(String deviceUuid, String name, int type) throws Exception {
        ResultRO<List<ObjectDetailRO>> resultRO = new ResultRO(true);
        List<ObjectDetailRO> list = new ArrayList();
        name = URLDecoder.decode(name, "UTF-8");
        String[] arr = name.split(";");
        int number = 1;
        String[] var8 = arr;
        int var9 = arr.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            String str = var8[var10];
            int splitIndex = str.indexOf("()");
            if (splitIndex == -1) {
                ObjectDetailRO obj = new ObjectDetailRO();
                obj.setValue(str);
                obj.setNumber(number);
                list.add(obj);
            } else {
                String nameRef = str.substring(0, splitIndex);
                ObjectDetailRO obj = null;
                if (type == 1) {
                    obj = this.ipServiceNameRefClient.queryIpByName(deviceUuid, nameRef);
                } else if (type == 2) {
                    obj = this.ipServiceNameRefClient.queryServiceByName(deviceUuid, nameRef);
                } else if (type == 3) {
                    obj = this.ipServiceNameRefClient.queryTimeByName(deviceUuid, nameRef);
                }

                if (obj != null) {
                    obj.setNumber(number);
                    list.add(obj);
                }
            }

            ++number;
        }

        resultRO.setData(list);
        return resultRO;
    }

    protected CommandTaskEditableEntity createCommandTask(Integer taskType, Integer id, String userName, String theme, String deviceUuid) {
        CommandTaskEditableEntity entity = new CommandTaskEditableEntity();
        entity.setCreateTime(new Date());
        entity.setStatus(PushConstants.PUSH_INT_PUSH_GENERATING);
        entity.setUserName(userName);
        entity.setTheme(theme);
        entity.setDeviceUuid(deviceUuid);
        entity.setTaskId(id);
        entity.setTaskType(taskType);
        return entity;
    }

    protected String getZone(String zone) {
        if (zone == null) {
            return "";
        } else {
            return zone.equals("-1") ? "" : zone;
        }
    }

    private void getBranch(String userName, RecommendTaskEntity recommendTaskEntity) {
        UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(userName);
        if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
            recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
        } else {
            recommendTaskEntity.setBranchLevel("00");
        }

    }

    protected void addRecommendTask(RecommendTaskEntity entity) {
        logger.info("策略下发新增任务:" + JSONObject.toJSONString(entity));
        List<RecommendTaskEntity> list = new ArrayList();
        list.add(entity);
        int count = this.insertRecommendTaskList(list);
        String policyTypeDesc = "";
        if (entity.getTaskType() == 5) {
            policyTypeDesc = "静态Nat";
        } else if (entity.getTaskType() == 6) {
            policyTypeDesc = "源Nat";
        } else if (entity.getTaskType() == 7) {
            policyTypeDesc = "目的Nat";
        } else if (entity.getTaskType() == 9) {
            policyTypeDesc = "Both Nat";
        } else {
            policyTypeDesc = "未知";
        }

        String message = String.format("新建%s策略%s%s", policyTypeDesc, entity.getTheme(), count > 0 ? "成功" : "失败");
        this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
    }

    public void updateWeTaskId(RecommendTaskEntity entity) {
        this.recommendTaskMapper.updateWeTaskId(entity);
    }
}
