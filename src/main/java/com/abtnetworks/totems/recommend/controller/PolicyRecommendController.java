package com.abtnetworks.totems.recommend.controller;

import com.abtnetworks.data.totems.log.client.LogClientSimple;
import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.BaseController;
import com.abtnetworks.totems.common.config.VmwareInterfaceStatusConfig;
import com.abtnetworks.totems.common.constants.CommonConstants;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.constants.ReturnCode;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.common.enums.PolicyCheckTypeEnum;
import com.abtnetworks.totems.common.lang.TotemsStringUtils;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.tools.excel.ExcelParser;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.InputValueUtils;
import com.abtnetworks.totems.disposal.ReturnT;
import com.abtnetworks.totems.external.utils.PolicyCheckCommonUtil;
import com.abtnetworks.totems.external.vo.DeviceDetailRunVO;
import com.abtnetworks.totems.external.vo.PolicyCheckListVO;
import com.abtnetworks.totems.external.vo.PolicyDetailVO;
import com.abtnetworks.totems.issued.exception.IssuedExecutorException;
import com.abtnetworks.totems.recommend.dto.recommend.EditCommandDTO;
import com.abtnetworks.totems.recommend.dto.task.SimulationTaskDTO;
import com.abtnetworks.totems.recommend.entity.AddRecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.CheckResultEntity;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.InternetAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.PathDeviceDetailEntity;
import com.abtnetworks.totems.recommend.entity.PathInfoEntity;
import com.abtnetworks.totems.recommend.entity.PolicyRiskEntity;
import com.abtnetworks.totems.recommend.entity.PushRecommendTaskHistoryEntity;
import com.abtnetworks.totems.recommend.entity.RecommendPolicyEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.RiskRuleInfoEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.abtnetworks.totems.recommend.manager.WhaleManager;
import com.abtnetworks.totems.recommend.service.GlobalRecommendService;
import com.abtnetworks.totems.recommend.service.RecommendBussCommonService;
import com.abtnetworks.totems.recommend.service.RecommendTaskHistoryService;
import com.abtnetworks.totems.recommend.service.WhatIfService;
import com.abtnetworks.totems.recommend.task.impl.SimulationTaskServiceImpl;
import com.abtnetworks.totems.recommend.task.impl.VerifyTaskServiceImpl;
import com.abtnetworks.totems.recommend.vo.CommandVO;
import com.abtnetworks.totems.recommend.vo.DevicePolicyVO;
import com.abtnetworks.totems.recommend.vo.PathDetailVO;
import com.abtnetworks.totems.recommend.vo.PolicyCheckVO;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendNatPolicyVO;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendPolicyRouterVO;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendSecurityPolicyVO;
import com.abtnetworks.totems.recommend.vo.RecommendPolicyVO;
import com.abtnetworks.totems.recommend.vo.TaskStatusVO;
import com.abtnetworks.totems.whale.baseapi.ro.WhatIfRO;
import com.abtnetworks.totems.whale.baseapi.service.IpServiceNameRefClient;
import com.abtnetworks.totems.whale.policy.ro.DeviceDetailRO;
import com.abtnetworks.totems.whale.policy.ro.PathAnalyzeRO;
import com.abtnetworks.totems.whale.policy.service.WhalePathAnalyzeClient;
import com.abtnetworks.totems.whale.policyoptimize.ro.RuleCheckResultDataRO;
import com.abtnetworks.totems.whale.policyoptimize.ro.RuleCheckResultRO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("策略开通任务列表")
@RestController
@RequestMapping({"/recommend/"})
public class PolicyRecommendController extends BaseController {
    private static Logger logger = Logger.getLogger(PolicyRecommendController.class);
    private static final String SAME_SUBNET = "来源于同一个子网";
    @Autowired
    GlobalRecommendService globalRecommendService;
    @Autowired
    RecommendTaskManager policyRecommendTaskService;
    @Autowired
    private CommandTaskManager commandTaskManager;
    @Autowired
    SimulationTaskServiceImpl recommendTaskManager;
    @Autowired
    VerifyTaskServiceImpl verifyTaskManager;
    @Autowired
    WhalePathAnalyzeClient client;
    @Autowired
    private IpServiceNameRefClient ipServiceNameRefClient;
    @Autowired
    private WhaleManager whaleService;
    @Autowired
    WhatIfService whatIfService;
    @Autowired
    RecommendTaskHistoryService recommendTaskHistoryService;
    @Autowired
    ExcelParser excelParser;
    @Autowired
    LogClientSimple logClientSimple;
    @Resource
    RecommendBussCommonService recommendBussCommonService;
    @Autowired
    VmwareInterfaceStatusConfig vmwareInterfaceStatusConfig;
    @Resource
    RemoteBranchService remoteBranchService;

    public PolicyRecommendController() {
    }

    @PostMapping({"task/addAll"})
    public JSONObject addRecommendTaskAll(@RequestBody AddRecommendTaskEntity entity, Authentication auth) {
        int rc = 0;
        new JSONObject();
        JSONObject returnJSON;
        Integer taskId;
        if (entity.getTaskType() == 14) {
            logger.info("新建开通工单接口Start参数{}" + JSONObject.toJSONString(entity));

            try {
                this.validatorParam(entity, new Class[0]);
                List<ServiceDTO> serviceList = entity.getServiceList();
                if (CollectionUtils.isNotEmpty(serviceList)) {
                    Iterator var7 = serviceList.iterator();

                    while(var7.hasNext()) {
                        ServiceDTO service = (ServiceDTO)var7.next();
                        if (!AliStringUtils.isEmpty(service.getDstPorts())) {
                            service.setDstPorts(InputValueUtils.autoCorrectPorts(service.getDstPorts()));
                        }
                    }
                }

                RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
                InternetAdditionalInfoEntity additionalInfoEntity = new InternetAdditionalInfoEntity();
                BeanUtils.copyProperties(entity, recommendTaskEntity);
                recommendTaskEntity.setSrcIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getSrcIpSystem()));
                recommendTaskEntity.setDstIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getDstIpSystem()));
                recommendTaskEntity.setServiceList(entity.getServiceList() == null ? null : JSONObject.toJSONString(entity.getServiceList()));
                UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(auth.getName());
                if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
                    recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
                } else {
                    recommendTaskEntity.setBranchLevel("00");
                }

                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String orderNumber = "A" + simpleDateFormat.format(date);
                recommendTaskEntity.setCreateTime(date);
                recommendTaskEntity.setOrderNumber(orderNumber);
                recommendTaskEntity.setUserName(auth.getName());
                recommendTaskEntity.setAdditionInfo(JSONObject.toJSONString(additionalInfoEntity));
                if (entity.getIdleTimeout() != null) {
                    recommendTaskEntity.setIdleTimeout(entity.getIdleTimeout() * CommonConstants.HOUR_SECOND);
                } else {
                    recommendTaskEntity.setIdleTimeout((Integer)null);
                }

                recommendTaskEntity.setTaskType(entity.getTaskType());
                recommendTaskEntity.setStatus(0);
                int returnCode = this.recommendBussCommonService.checkPostRelevancyNat(recommendTaskEntity, auth);
                if (0 != returnCode) {
                    return this.getReturnJSON(255, ReturnCode.getMsg(returnCode));
                }

                List<RecommendTaskEntity> list = new ArrayList();
                list.add(recommendTaskEntity);
                this.policyRecommendTaskService.insertRecommendTaskList(list);
                String message = String.format("新建开通工单：%s 成功", recommendTaskEntity.getTheme());
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                List<Integer> collect = (List)list.stream().map((r) -> {
                    return r.getId();
                }).collect(Collectors.toList());
                taskId = (Integer)collect.get(0);
                returnJSON = this.getReturnJSON(rc, "");
                returnJSON.put("taskId", taskId);
            } catch (IllegalArgumentException var17) {
                rc = 3;
                logger.error("新建开通工单接口End参数异常", var17);
                return this.getReturnJSON(rc);
            }
        } else {
            logger.info("新建业务开通工单接口Start参数{}" + JSONObject.toJSONString(entity));
            returnJSON = this.addRecommendTask(entity, auth);
            taskId = (Integer)returnJSON.get("taskId");
        }

        if (taskId != null) {
            logger.info("======================仿真开始============================");
            String id = String.valueOf(taskId);
            this.startRecommendTaskList(id, auth);
            logger.info("======================仿真结束============================");
        }

        return returnJSON;
    }

    @ApiOperation("new 获取任执行务状态列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/gettaskstatus"})
    public JSONObject getTaskStatus(int taskId) {
        TaskStatusVO taskStatusVO = this.policyRecommendTaskService.getTaskStatusByTaskId(taskId);
        String jsonObjectString = JSONObject.toJSONString(taskStatusVO);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("new 获取路径分析状态列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "psize",
            value = "每页条数",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/analyzepathinfolist"})
    public JSONObject getAnalyzePathInfoList(int taskId, int page, int psize) {
        PageInfo<PathInfoEntity> pageInfo = this.policyRecommendTaskService.getAnalyzePathInfoVOList(taskId, page, psize);
        List<PathInfoEntity> list = pageInfo.getList();
        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            PathInfoEntity entity = (PathInfoEntity)var6.next();
            String srcSubnetDevices = this.getSubnetDeviceList(entity.getSrcNodeUuid());
            entity.setSrcSubnetDevices(srcSubnetDevices);
            String dstSubnetDevices = this.getSubnetDeviceList(entity.getDstNodeUuid());
            entity.setDstSubnetDevices(dstSubnetDevices);
        }

        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("new 获取路径验证信息列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "psize",
            value = "每页条数",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/verifypathinfolist"})
    public JSONObject getVerifyPathInfoList(int taskId, int page, int psize) {
        PageInfo<PathInfoEntity> pageInfo = this.policyRecommendTaskService.getVerifyPathInfoVOList(taskId, page, psize);
        List<PathInfoEntity> list = pageInfo.getList();
        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            PathInfoEntity entity = (PathInfoEntity)var6.next();
            String srcSubnetDevices = this.getSubnetDeviceList(entity.getSrcNodeUuid());
            entity.setSrcSubnetDevices(srcSubnetDevices);
            String dstSubnetDevices = this.getSubnetDeviceList(entity.getDstNodeUuid());
            entity.setDstSubnetDevices(dstSubnetDevices);
        }

        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("new 获取路径信息列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoId",
            value = "路径信息id",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "isVerifyData",
            value = "是否为验证路径数据",
            required = true,
            dataType = "Boolean"
    )})
    @PostMapping({"task/pathdetail"})
    public JSONObject getPathDetail(int pathInfoId, boolean isVerifyData) {
        PathDetailVO pathInfoVO = this.policyRecommendTaskService.getPathDetail(pathInfoId, isVerifyData);
        String jsonObjectString = JSONObject.toJSONString(pathInfoVO);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("new 获取路径设备详情")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoId",
            value = "路径信息id",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "deviceUuid",
            value = "设备uuid",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "isVerifyData",
            value = "是否为验证路径数据",
            required = true,
            dataType = "Boolean"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "pathIndex",
            value = "路径序号",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/devicedetail"})
    public JSONObject getDeviceDetail(int pathInfoId, String deviceUuid, boolean isVerifyData, String pathIndex) {
        DevicePolicyVO devicePolicyVO = new DevicePolicyVO();
        if (pathIndex == null) {
            pathIndex = "0";
        }

        PathDeviceDetailEntity entity = this.policyRecommendTaskService.getDevieceDetail(pathInfoId, deviceUuid, isVerifyData, pathIndex);
        boolean isEmptyData = false;
        String deviceDetail = null;
        if (entity == null) {
            logger.error(String.format("数据库中没有设备详情数据...路径：%d, 设备：%s", pathInfoId, deviceUuid));
            isEmptyData = true;
        } else {
            deviceDetail = entity.getDeviceDetail();
            if (deviceDetail == null) {
                logger.error(String.format("设备详情数据中路径数据为空...路径：%d，设备：%s", pathInfoId, deviceUuid));
                isEmptyData = true;
            }
        }

        if (isEmptyData) {
            String jsonObjectString = JSONObject.toJSONString(devicePolicyVO);
            JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
            return this.getReturnJSON(0, jsonObject);
        } else {
            JSONObject deviceDetailObject = JSONObject.parseObject(deviceDetail);
            DeviceDetailRO detailDeviceRO = (DeviceDetailRO)deviceDetailObject.toJavaObject(DeviceDetailRO.class);
            DeviceDetailRunVO deviceDetailRunVO = this.client.parseDetailRunRO(detailDeviceRO);
            List<PolicyDetailVO> safeListDetail = deviceDetailRunVO.getSafeList();
            List<PolicyDetailVO> natListDetail = deviceDetailRunVO.getNatList();
            List<PolicyDetailVO> routTableListDetail = deviceDetailRunVO.getRoutList();
            devicePolicyVO.setAclList(deviceDetailRunVO.getAclList());
            devicePolicyVO.setPolicyRoutList(deviceDetailRunVO.getPolicyRoutList());
            ArrayList routerVOList;
            Iterator var16;
            PolicyDetailVO vo;
            if (safeListDetail != null) {
                routerVOList = new ArrayList();
                var16 = safeListDetail.iterator();

                while(var16.hasNext()) {
                    vo = (PolicyDetailVO)var16.next();
                    PolicyRecommendSecurityPolicyVO policyVO = new PolicyRecommendSecurityPolicyVO();
                    BeanUtils.copyProperties(vo, policyVO);
                    policyVO.setIsAble(vo.getIsAble());
                    policyVO.setDescription(vo.getDescription());
                    routerVOList.add(policyVO);
                }

                devicePolicyVO.setSecurityPolicyList(routerVOList);
            }

            if (natListDetail != null) {
                routerVOList = new ArrayList();
                var16 = natListDetail.iterator();

                while(var16.hasNext()) {
                    vo = (PolicyDetailVO)var16.next();
                    PolicyRecommendNatPolicyVO natPolicyVO = new PolicyRecommendNatPolicyVO();
                    BeanUtils.copyProperties(vo, natPolicyVO);
                    natPolicyVO.setDescription(vo.getDescription());
                    natPolicyVO.setIsAble(vo.getIsAble());
                    routerVOList.add(natPolicyVO);
                }

                devicePolicyVO.setNatPolicList(routerVOList);
            }

            if (routTableListDetail != null) {
                routerVOList = new ArrayList();
                var16 = routTableListDetail.iterator();

                while(var16.hasNext()) {
                    vo = (PolicyDetailVO)var16.next();
                    PolicyRecommendPolicyRouterVO routerVO = new PolicyRecommendPolicyRouterVO();
                    routerVO.setNumber(vo.getNumber());
                    routerVO.setSrcIp(vo.getSrcIp());
                    routerVO.setMask(vo.getMask());
                    routerVO.setNextHop(vo.getNextStep());
                    routerVO.setNetDoor(vo.getNetDoor());
                    routerVO.setDistance(vo.getDistance());
                    routerVO.setWeight(vo.getWeight());
                    routerVO.setProtocol(vo.getProtocol());
                    routerVO.setDescription(vo.getDescription());
                    routerVOList.add(routerVO);
                }

                devicePolicyVO.setRouterList(routerVOList);
            }

            String jsonObjectString = JSONObject.toJSONString(devicePolicyVO);
            JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
            return this.getReturnJSON(0, jsonObject);
        }
    }

    @ApiOperation("new 获取策略风险分析结果")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoId",
            value = "路径信息id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/getrisk"})
    public JSONObject getRiskByPathInfoId(int pathInfoId) {
        List<PolicyRiskEntity> riskEntityList = this.policyRecommendTaskService.getRiskByPathInfoId(pathInfoId);
        List<RiskRuleInfoEntity> riskInfoList = new ArrayList();
        Iterator var4 = riskEntityList.iterator();

        while(var4.hasNext()) {
            PolicyRiskEntity riskEntity = (PolicyRiskEntity)var4.next();
            RiskRuleInfoEntity riskInfo = this.policyRecommendTaskService.getRiskInfoByRuleId(riskEntity.getRuleId());
            riskInfoList.add(riskInfo);
        }

        String jsonObjectString = JSONObject.toJSONString(riskInfoList);
        JSONArray jsonArray = JSONArray.parseArray(jsonObjectString);
        return this.getReturnJSON(0, jsonArray);
    }

    @ApiOperation("new 获取策略生成结果")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoId",
            value = "路径信息id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/getpolicy"})
    public JSONObject getPolicyByPathInfoId(int pathInfoId) {
        List<RecommendPolicyVO> policyVOList = this.policyRecommendTaskService.getPolicyByPathInfoId(pathInfoId);
        String jsonObjectString = JSONObject.toJSONString(policyVOList);
        JSONArray jsonArray = JSONArray.parseArray(jsonObjectString);
        return this.getReturnJSON(0, jsonArray);
    }

    @ApiOperation("new 获取合并策略结果")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoId",
            value = "路径信息id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/getmergedpolicy"})
    public JSONObject getMergedPolicyByTaskId(int taskId) {
        List<RecommendPolicyVO> policyVOList = this.policyRecommendTaskService.getMergedPolicyByTaskId(taskId);
        String jsonObjectString = JSONObject.toJSONString(policyVOList);
        JSONArray jsonArray = JSONArray.parseArray(jsonObjectString);
        return this.getReturnJSON(0, jsonArray);
    }

    @ApiOperation("new 获取策略检查结果列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/getcheckresult"})
    public JSONObject getCheckResultByPolicyId(@Param("policyId") int taskId) {
        List<CheckResultEntity> entityList = this.policyRecommendTaskService.getCheckResultByPolicyId(taskId);
        if (entityList.size() == 0) {
            return this.getReturnJSON(0);
        } else {
            Map<String, List<RuleCheckResultDataRO>> deviceMergeListMap = new HashMap();
            Map<String, List<RuleCheckResultDataRO>> deviceHideListMap = new HashMap();
            Map<String, List<RuleCheckResultDataRO>> deviceRedundancyListMap = new HashMap();
            Iterator var6 = entityList.iterator();

            while(true) {
                label93:
                while(var6.hasNext()) {
                    CheckResultEntity entity = (CheckResultEntity)var6.next();
                    RecommendPolicyEntity recommendPolicyEntity = this.policyRecommendTaskService.getPolicyByPolicyId(entity.getPolicyId());
                    if (recommendPolicyEntity == null) {
                        logger.info(String.format("策略(%d)数据对象不存在...", entity.getPolicyId()));
                    } else {
                        String deviceUuid = recommendPolicyEntity.getDeviceUuid();
                        String checkResult = entity.getCheckResult();
                        JSONObject checkResultObject = JSONObject.parseObject(checkResult);
                        RuleCheckResultRO checkResultRO = (RuleCheckResultRO)checkResultObject.toJavaObject(RuleCheckResultRO.class);
                        List<RuleCheckResultDataRO> ruleCheckResultDataROList = checkResultRO.getData();
                        Iterator var14 = ruleCheckResultDataROList.iterator();

                        while(true) {
                            while(true) {
                                if (!var14.hasNext()) {
                                    continue label93;
                                }

                                RuleCheckResultDataRO data = (RuleCheckResultDataRO)var14.next();
                                List<RuleCheckResultDataRO> redundancyList;
                                if (!data.getBpcCode().equals("RuleCheck_1") && !data.getBpcCode().equals("RC_HIDDEN_SAME") && !data.getBpcCode().equals("RC_HIDDEN_CONFLICT")) {
                                    if (data.getBpcCode().equals("RC_MERGE_RULE")) {
                                        this.ipServiceNameRefClient.packRuleObject(deviceUuid, data.getPrimaryRule(), data.getOtherPrimaryRules(), data.getRelatedRules());
                                        redundancyList = deviceMergeListMap.get(deviceUuid);
                                        if (redundancyList == null) {
                                            redundancyList = new ArrayList();
                                            deviceMergeListMap.put(deviceUuid, redundancyList);
                                        }

                                        ((List)redundancyList).add(data);
                                    } else if (data.getBpcCode().equals("RuleCheck_3")) {
                                        this.ipServiceNameRefClient.packRuleObject(deviceUuid, data.getPrimaryRule(), data.getOtherPrimaryRules(), data.getRelatedRules());
                                        redundancyList = (List)deviceRedundancyListMap.get(deviceUuid);
                                        if (redundancyList == null) {
                                            redundancyList = new ArrayList();
                                            deviceRedundancyListMap.put(deviceUuid, redundancyList);
                                        }

                                        ((List)redundancyList).add(data);
                                    } else {
                                        logger.error("invalid data bpc type: " + data.getBpcCode());
                                    }
                                } else {
                                    this.ipServiceNameRefClient.packRuleObject(deviceUuid, data.getPrimaryRule(), data.getOtherPrimaryRules(), data.getRelatedRules());
                                    redundancyList = (List)deviceHideListMap.get(deviceUuid);
                                    if (redundancyList == null) {
                                        redundancyList = new ArrayList();
                                        deviceHideListMap.put(deviceUuid, redundancyList);
                                    }

                                    ((List)redundancyList).add(data);
                                }
                            }
                        }
                    }
                }

                JSONObject jsonObject = new JSONObject();
                Set<String> deviceSet = new HashSet();
                Set<String> hideDeviceSet = deviceHideListMap.keySet();
                Set<String> mergeDeviceSet = deviceMergeListMap.keySet();
                Set<String> redundancyDeviceSet = deviceRedundancyListMap.keySet();
                deviceSet.addAll(hideDeviceSet);
                deviceSet.addAll(mergeDeviceSet);
                deviceSet.addAll(redundancyDeviceSet);
                Iterator var28 = deviceSet.iterator();

                while(var28.hasNext()) {
                    String deviceUuid = (String)var28.next();
                    List<RuleCheckResultDataRO> hideList = (List)deviceHideListMap.get(deviceUuid);
                    if (hideList == null) {
                        hideList = new ArrayList();
                    }

                    PolicyCheckVO policyResultList = new PolicyCheckVO();
                    ResultRO<List<RuleCheckResultDataRO>> hideCheckResultRO = new ResultRO();
                    hideCheckResultRO.setData(hideList);
                    logger.debug("hideCheckResultRO.setData:" + hideList.toString());
                    List<PolicyCheckListVO> checkListVO = PolicyCheckCommonUtil.getCheckList(hideCheckResultRO, PolicyCheckTypeEnum.HIDDEN);
                    if (checkListVO != null && checkListVO.size() > 0) {
                        policyResultList.setHiddenPolicy(checkListVO);
                    }

                    List<RuleCheckResultDataRO> mergeList = (List)deviceMergeListMap.get(deviceUuid);
                    if (mergeList == null) {
                        mergeList = new ArrayList();
                    }

                    ResultRO<List<RuleCheckResultDataRO>> mergeCheckResultRO = new ResultRO();
                    mergeCheckResultRO.setData(mergeList);
                    checkListVO = PolicyCheckCommonUtil.getCheckList(mergeCheckResultRO, PolicyCheckTypeEnum.MERGE);
                    if (checkListVO != null && checkListVO.size() > 0) {
                        policyResultList.setMergePolicy(checkListVO);
                    }

                    List<RuleCheckResultDataRO> redundancyList = (List)deviceRedundancyListMap.get(deviceUuid);
                    if (redundancyList == null) {
                        redundancyList = new ArrayList();
                    }

                    ResultRO<List<RuleCheckResultDataRO>> redundancyCheckResultRO = new ResultRO();
                    redundancyCheckResultRO.setData(redundancyList);
                    logger.debug("hideCheckResultRO.setData:" + redundancyList.toString());
                    checkListVO = PolicyCheckCommonUtil.getCheckList(redundancyCheckResultRO, PolicyCheckTypeEnum.REDUNDANCY);
                    if (checkListVO != null && checkListVO.size() > 0) {
                        policyResultList.setRedundancyPolicy(checkListVO);
                    }

                    String jsonObjectString = JSONObject.toJSONString(policyResultList);
                    JSONObject object = JSONObject.parseObject(jsonObjectString);
                    jsonObject.put(deviceUuid, object);
                }

                return this.getReturnJSON(0, jsonObject);
            }
        }
    }

    @ApiOperation("new 获取生成命令行")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"task/getcommand"})
    public JSONObject getCommand(int taskId) {
        List<CommandVO> commandVOList = this.commandTaskManager.getCommandByTaskId(taskId);
        String jsonObjectString = JSONObject.toJSONString(commandVOList);
        JSONArray jsonArray = JSONArray.parseArray(jsonObjectString);
        return this.getReturnJSON(0, jsonArray);
    }

    @ApiOperation("new 编辑生成命令行")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "deviceUuid",
            value = "设备uuid",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "command",
            value = "命令行",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/editcommand.action"})
    public JSONObject editCommand(@RequestBody EditCommandDTO editCommandDTO, Authentication auth) {
        List<CommandTaskEditableEntity> entityList = this.commandTaskManager.getCommandTaskByTaskId(editCommandDTO.getTaskId());
        if (entityList != null && entityList.size() != 0) {
            if (((CommandTaskEditableEntity)entityList.get(0)).getStatus() > PushConstants.PUSH_INT_PUSH_RESULT_STATUS_NOT_START) {
                return this.getReturnJSON(80);
            } else {
                int rc = this.commandTaskManager.editCommandEditableEntity(editCommandDTO, auth.getName());
                CommandTaskEditableEntity entity = this.commandTaskManager.getCommandEditableEntityByTaskIdAndDeviceUuid(editCommandDTO.getTaskId(), editCommandDTO.getDeviceUuid());
                String jsonObjectString = JSONObject.toJSONString(entity);
                JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
                return this.getReturnJSON(rc, jsonObject);
            }
        } else {
            return this.getReturnJSON(77);
        }
    }

    @ApiOperation("开始全网策略仿真")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略开通任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"recommend/startGlobalRecommendTaskList"})
    public JSONObject startGlobalRecommendTaskList(String ids, Authentication authentication) {
        if (!this.vmwareInterfaceStatusConfig.isVmInterfaceAvailable()) {
            return this.startRecommendTaskList(ids, authentication);
        } else {
            ids = String.format("[%s]", ids);
            JSONArray jsonArray = JSONArray.parseArray(ids);
            if (jsonArray != null && jsonArray.size() != 0) {
                String errmsg = "";

                try {
                    errmsg = this.globalRecommendService.startGlobalRecommendTaskList(ids, authentication);
                    return this.getReturnJSON(0, errmsg);
                } catch (Exception var6) {
                    return this.getReturnJSON(255, var6.getMessage());
                }
            } else {
                logger.error("开始策略仿真任务为空！");
                return this.getReturnJSON(102);
            }
        }
    }

    @ApiOperation("开始策略仿真")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略开通任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"recommend/start"})
    public JSONObject startRecommendTaskList(String ids, Authentication authentication) {
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = com.abtnetworks.totems.common.utils.StringUtils.parseIntArrayList(ids);
            } catch (Exception var13) {
                logger.error("解析任务列表出错！", var13);
            }

            List<String> themeList = new ArrayList();
            List<RecommendTaskEntity> taskEntitylist = new ArrayList();
            Iterator var7 = idList.iterator();

            RecommendTaskEntity taskEntity;
            while(var7.hasNext()) {
                int id = (Integer)var7.next();
                logger.info(String.format("获取任务(%d)", id));
                taskEntity = this.policyRecommendTaskService.getRecommendTaskByTaskId(id);
                if (taskEntity == null) {
                    logger.error(String.format("获取任务(%d)失败, 任务不存在, 继续查找下一个...", id));
                } else {
                    if (taskEntity.getTaskType() == 16) {
                        return this.getReturnJSON(0, "东西向仿真开始");
                    }

                    if (taskEntity.getStatus() > 1) {
                        logger.error(String.format("无法开始任务(%s), 任务已完成仿真！\n", taskEntity.getOrderNumber()));
                    } else {
                        taskEntitylist.add(taskEntity);
                        themeList.add(taskEntity.getTheme());
                    }
                }
            }

            List<SimulationTaskDTO> taskDtoList = new ArrayList();
            Iterator var15 = taskEntitylist.iterator();

            while(var15.hasNext()) {
                taskEntity = (RecommendTaskEntity)var15.next();
                SimulationTaskDTO taskDTO = new SimulationTaskDTO();
                BeanUtils.copyProperties(taskEntity, taskDTO);
                taskDTO.setWhatIfCaseUuid(taskEntity.getWhatIfCase());
                if (taskEntity.getServiceList() == null) {
                    taskDTO.setServiceList((List)null);
                } else {
                    JSONArray array = JSONArray.parseArray(taskEntity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    taskDTO.setServiceList(serviceList);
                }

                WhatIfRO whatIf = this.recommendBussCommonService.createWhatIfCaseUuid(taskEntity);
                if (whatIf != null && !AliStringUtils.isEmpty(whatIf.getUuid())) {
                    logger.info("创建模拟开通环境UUID为:" + whatIf.getUuid());
                    taskDTO.setWhatIfCaseUuid(whatIf.getUuid());
                    taskDTO.setDeviceWhatifs(whatIf.getDeviceWhatifs());
                } else {
                    logger.error("创建模拟开通数据失败！" + taskEntity.getRelevancyNat());
                }

                taskDtoList.add(taskDTO);
                this.policyRecommendTaskService.updateTaskStatus(taskDTO.getId(), 1);
            }

            this.recommendTaskManager.addSimulationTaskList(taskDtoList, authentication);
            String message = String.format("工单：%s 进行仿真", StringUtils.join(themeList, ","));
            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
            String errmsg = String.format("%d个任务已加入策略仿真任务队列。\n", taskEntitylist.size());
            if (taskEntitylist.size() == 0) {
                errmsg = "没有策略仿真任务加入策略仿真队列。";
            }

            return this.getReturnJSON(0, errmsg);
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("重新开始策略仿真")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略开通任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"recommend/restart"})
    public JSONObject restartRecommendTaskList(String ids, Authentication authentication) {
        String idString = ids;
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = com.abtnetworks.totems.common.utils.StringUtils.parseIntArrayList(ids);
            } catch (Exception var14) {
                logger.error("解析任务列表出错！", var14);
            }

            StringBuilder errMsg = new StringBuilder();
            int rc = 0;
            List<String> themeList = new ArrayList();
            Iterator var9 = idList.iterator();

            while(true) {
                RecommendTaskEntity taskEntity;
                List taskEntityList;
                int taskStatus;
                label58:
                do {
                    while(var9.hasNext()) {
                        int id = (Integer)var9.next();
                        logger.info(String.format("获取任务(%d)", id));
                        taskEntity = this.policyRecommendTaskService.getRecommendTaskByTaskId(id);
                        taskEntityList = this.commandTaskManager.getCommandTaskByTaskId(id);
                        taskStatus = taskEntity.getStatus();
                        if (taskEntityList != null && taskEntityList.size() > 0) {
                            themeList.add(taskEntity.getTheme());
                            continue label58;
                        }

                        themeList.add(taskEntity.getTheme());
                        if (taskStatus == 0 || taskStatus == 2 || taskStatus == 21) {
                            rc = 123;
                            errMsg.append(taskEntity.getTheme() + ":" + ReturnCode.getMsg(rc));
                        }
                    }

                    if (rc != 0) {
                        return this.getReturnJSON(rc, errMsg.toString());
                    }

                    int type = 1;
                    this.policyRecommendTaskService.deleteTasks(idList, type);
                    JSONObject jsonObject = this.startRecommendTaskList(idString, authentication);
                    return jsonObject;
                } while(taskStatus != 0 && taskStatus != 2 && taskStatus != 21 && ((CommandTaskEditableEntity)taskEntityList.get(0)).getPushStatus() != 1 && ((CommandTaskEditableEntity)taskEntityList.get(0)).getRevertStatus() != 1);

                rc = 123;
                errMsg.append(taskEntity.getTheme() + ":" + ReturnCode.getMsg(rc));
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("添加策略开通任务并开始")
    @PostMapping({"task/newstart"})
    public JSONObject addAndStartRecommendTask(@RequestBody AddRecommendTaskEntity entity, Authentication auth) {
        JSONObject jsonObject = this.addRecommendTask(entity, auth);
        if (jsonObject.getString("status").equals("0")) {
            String taskId = jsonObject.getString("errmsg");
            this.startRecommendTaskList(taskId, auth);
        }

        return jsonObject;
    }

    @PostMapping({"task/addGlobalRecommendTask"})
    public JSONObject addGlobalRecommendTask(@RequestBody AddRecommendTaskEntity entity, Authentication auth) {
        if (this.vmwareInterfaceStatusConfig.checkVmInterfaceAvailableNow() && entity.getIpType() == 0) {
            entity.setDstIp(InputValueUtils.formatIpAddress(entity.getDstIp()));
            int rc = this.recommendBussCommonService.checkParamForSrcAddress(entity);
            List<ServiceDTO> serviceList = entity.getServiceList();
            Set<String> serviceSet = new HashSet();
            Iterator var6 = serviceList.iterator();

            ServiceDTO service;
            while(var6.hasNext()) {
                service = (ServiceDTO)var6.next();
                if (serviceSet.contains(service.getProtocol())) {
                    return this.getReturnJSON(255, "服务中同类型协议只能添加一条！");
                }

                serviceSet.add(service.getProtocol());
            }

            var6 = serviceList.iterator();

            while(var6.hasNext()) {
                service = (ServiceDTO)var6.next();
                if (!AliStringUtils.isEmpty(service.getDstPorts())) {
                    service.setDstPorts(InputValueUtils.autoCorrectPorts(service.getDstPorts()));
                }
            }

            var6 = null;

            RecommendTaskEntity recommendTaskEntity;
            try {
                recommendTaskEntity = this.globalRecommendService.addGlobalRecommendTask(entity, auth);
            } catch (IssuedExecutorException var9) {
                return this.getReturnJSON(255, var9.getMessage());
            }

            String message = String.format("新建业务开通工单：%s 成功", recommendTaskEntity.getTheme());
            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
            JSONObject rs = this.getReturnJSON(rc, "");
            rs.put("taskId", recommendTaskEntity.getId());
            return rs;
        } else {
            return this.addRecommendTask(entity, auth);
        }
    }

    @ApiOperation("new 添加策略开通任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "description",
            value = "申请描述",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "srcIp",
            value = "源IP",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "dstIp",
            value = "目的IP",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "serviceList",
            value = "协议号",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "startTime",
            value = "策略开始时间",
            required = false,
            dataType = "Long"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "endTime",
            value = "策略结束时间",
            required = false,
            dataType = "Long"
    )})
    @PostMapping({"task/add"})
    public JSONObject addRecommendTask(@RequestBody AddRecommendTaskEntity entity, Authentication auth) {
        entity.setDstIp(InputValueUtils.formatIpAddress(entity.getDstIp()));
        int rc = this.recommendBussCommonService.checkParamForSrcAddress(entity);
        List<ServiceDTO> serviceList = entity.getServiceList();
        Set<String> serviceSet = new HashSet();
        Iterator var6 = serviceList.iterator();

        ServiceDTO service;
        while(var6.hasNext()) {
            service = (ServiceDTO)var6.next();
            if (serviceSet.contains(service.getProtocol())) {
                return this.getReturnJSON(255, "服务中同类型协议只能添加一条！");
            }

            serviceSet.add(service.getProtocol());
        }

        logger.info("新建任务的whatIfCases is " + entity.getWhatIfCases());
        var6 = serviceList.iterator();

        while(var6.hasNext()) {
            service = (ServiceDTO)var6.next();
            if (!AliStringUtils.isEmpty(service.getDstPorts())) {
                service.setDstPorts(InputValueUtils.autoCorrectPorts(service.getDstPorts()));
            }
        }

        var6 = null;

        RecommendTaskEntity recommendTaskEntity;
        try {
            recommendTaskEntity = this.recommendBussCommonService.addAutoNatGenerate(entity, auth);
        } catch (IssuedExecutorException var9) {
            return this.getReturnJSON(255, var9.getMessage());
        }

        String message = String.format("新建业务开通工单：%s 成功", recommendTaskEntity.getTheme());
        this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
        JSONObject rs = this.getReturnJSON(rc, "");
        rs.put("taskId", recommendTaskEntity.getId());
        return rs;
    }

    @ApiOperation("停止策略仿真")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略开通任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"recommend/stop"})
    public JSONObject stopRecommendTaskList(String ids) {
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = com.abtnetworks.totems.common.utils.StringUtils.parseIntArrayList(ids);
            } catch (Exception var15) {
                logger.error("解析任务列表出错！", var15);
            }

            Map<String, String> themeMap = new HashMap();
            List<String> taskList = new ArrayList();
            StringBuilder errMsg = new StringBuilder();
            boolean hasFailed = false;
            Iterator var8 = idList.iterator();

            while(true) {
                while(var8.hasNext()) {
                    int id = (Integer)var8.next();
                    logger.info(String.format("获取任务(%d)", id));
                    RecommendTaskEntity entity = this.policyRecommendTaskService.getRecommendTaskByTaskId(id);
                    if (entity == null) {
                        logger.error(String.format("获取任务(%d)失败, 任务不存在, 继续查找下一个...", id));
                        hasFailed = true;
                    } else if (entity.getStatus() != 1 && entity.getStatus() != 2) {
                        taskList.add(String.valueOf(id));
                        themeMap.put(String.valueOf(id), entity.getTheme());
                    } else {
                        errMsg.append(String.format("无法停止任务(%s), 任务未开始或者已完成仿真！\n", entity.getTheme()));
                        hasFailed = true;
                    }
                }

                if (taskList.size() == 0) {
                    logger.error("没有任务可以停止！");
                    errMsg.insert(0, "没有任务可以停止！");
                    return this.getReturnJSON(38, "没有任务可以停止！");
                }

                String message = String.format("工单：%s 停止仿真", StringUtils.join(themeMap.values(), ","));
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                List<String> failedList = this.recommendTaskManager.stopTaskList(taskList);
                Iterator var18 = taskList.iterator();

                while(var18.hasNext()) {
                    String id = (String)var18.next();
                    if (failedList.contains(id)) {
                        logger.info(String.format("停止任务（%s）失败", id));
                        errMsg.append(String.format("停止任务（%s）失败", id));
                        hasFailed = true;
                        this.logClientSimple.addBusinessLog(LogLevel.ERROR.getId(), BusinessLogType.POLICY_PUSH.getId(), String.format("停止工单: %s 失败", themeMap.get(id)));
                    } else {
                        Integer idNum = 0;

                        try {
                            idNum = Integer.valueOf(id);
                        } catch (Exception var14) {
                            logger.error(String.format("转换任务id类型失败！id为(%s)", id));
                        }

                        this.policyRecommendTaskService.updateTaskStatus(idNum, 7);
                    }
                }

                String msg = "停止策略下发任务成功！";
                int code = 0;
                if (hasFailed) {
                    errMsg.insert(0, "停止策略下发任务完成！");
                    code = 72;
                    msg = errMsg.toString();
                }

                return this.getReturnJSON(code, msg);
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("批量开始验证任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略开通任务id",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"verify/startverify"})
    public JSONObject startVerify(String ids) {
        logger.info(String.format("开始批量验证[%s]... ", ids));
        if (this.verifyTaskManager.isVerifyRunning()) {
            return this.getReturnJSON(55);
        } else {
            ids = String.format("[%s]", ids);
            JSONArray jsonArray = JSONArray.parseArray(ids);
            if (jsonArray != null && jsonArray.size() != 0) {
                StringBuilder sb = new StringBuilder();
                List idList = null;

                try {
                    idList = com.abtnetworks.totems.common.utils.StringUtils.parseIntArrayList(ids);
                } catch (Exception var11) {
                    logger.error("解析任务列表出错！", var11);
                }

                List<String> themeList = new ArrayList();
                List<RecommendTaskEntity> taskList = new ArrayList();
                Iterator var7 = idList.iterator();

                while(var7.hasNext()) {
                    Integer id = (Integer)var7.next();
                    RecommendTaskEntity task = this.policyRecommendTaskService.getRecommendTaskByTaskId(id);
                    WhatIfRO whatIf = this.recommendBussCommonService.createWhatIfCaseUuid(task);
                    if (whatIf != null && !AliStringUtils.isEmpty(whatIf.getUuid())) {
                        logger.info("创建模拟开通环境UUID为:" + whatIf.getUuid());
                        task.setWhatIfCase(whatIf.getUuid());
                    } else {
                        logger.error("创建模拟开通数据失败！" + task.getRelevancyNat());
                    }

                    taskList.add(task);
                    sb.append(String.format(",%s[%s]开始验证！\n", task.getTheme(), task.getOrderNumber()));
                    themeList.add(task.getTheme());
                }

                String msg = sb.toString().replaceFirst(",", "");
                logger.info(msg);
                int rc = this.verifyTaskManager.startVerify(taskList);
                String message = String.format("工单：%s 开始验证", StringUtils.join(themeList, ","));
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                return this.getReturnJSON(rc, "开始验证");
            } else {
                logger.error("添加到下发列表任务为空！");
                return this.getReturnJSON(102);
            }
        }
    }

    @ApiOperation("new 设置路径有效")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "pathInfoIds",
            value = "路径信息id",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "enable",
            value = "是否启用(0:diable,1:enable)",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/enablepath"})
    public JSONObject enablePath(String pathInfoIds, String enable, Authentication authentication) {
        pathInfoIds = String.format("[%s]", pathInfoIds);
        JSONArray jsonArray = JSONArray.parseArray(pathInfoIds);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = com.abtnetworks.totems.common.utils.StringUtils.parseIntArrayList(pathInfoIds);
            } catch (Exception var13) {
                logger.error("解析任务列表出错！", var13);
            }

            PathInfoEntity entity = this.policyRecommendTaskService.getPathInfoByPathInfoId((Integer)idList.get(0));
            int taskId = entity.getTaskId();
            RecommendTaskEntity taskEntity = this.policyRecommendTaskService.getRecommendTaskByTaskId(taskId);
            if (taskEntity.getStatus() > 10) {
                return this.getReturnJSON(81);
            } else {
                Iterator var9 = idList.iterator();

                while(var9.hasNext()) {
                    Integer pathInfoId = (Integer)var9.next();
                    this.policyRecommendTaskService.setPathEnable(pathInfoId, enable);
                }

                String message = String.format("修改工单：%s ，路径id: %s，将路径设置为 %s", taskEntity.getTheme(), pathInfoIds, enable.equals("0") ? "无效" : "有效");
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                SimulationTaskDTO task = new SimulationTaskDTO();
                BeanUtils.copyProperties(taskEntity, task);
                if (taskEntity.getServiceList() == null) {
                    task.setServiceList((List)null);
                } else {
                    JSONArray array = JSONArray.parseArray(taskEntity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    task.setServiceList(serviceList);
                }

                int rc = this.recommendTaskManager.addReassembleCommandLineTask(task, authentication);
                return this.getReturnJSON(rc);
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("new 获取单个任务详细信息")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "taskId",
            value = "策略开通任务id",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/getpathstatic"})
    public JSONObject getPathStaticByTaskId(String taskId) {
        int taskIdNum = 0;

        try {
            taskIdNum = Integer.valueOf(taskId);
        } catch (Exception var22) {
            this.getReturnJSON(255, "工单号不正确!");
        }

        RecommendTaskEntity taskEntity = this.policyRecommendTaskService.getRecommendTaskByTaskId(taskIdNum);
        if (taskEntity.getStatus().equals(0)) {
            return this.getReturnJSON(0, "模拟仿真未开始");
        } else {
            JSONObject object = new JSONObject();
            List<PathInfoEntity> list = this.policyRecommendTaskService.getPathInfoByTaskId(taskIdNum);
            if (list.size() == 0) {
                object.put("danger", "无路径生成");
                this.getReturnJSON(0, object);
            }

            int success = 0;
            int failed = 0;
            int notStart = 0;
            int error = 0;
            int access = 0;
            int noSrcSubnet = 0;
            int noDstSubnet = 0;
            int pathNoExists = 0;
            int srcDstHasSameSubnet = 0;
            int bigInternetNatExits = 0;
            String message = "";
            Iterator var17 = list.iterator();

            while(var17.hasNext()) {
                PathInfoEntity entity = (PathInfoEntity)var17.next();
                PathDetailVO pathDetailVO = this.policyRecommendTaskService.getPathDetail(entity.getId(), false);
                if (null != pathDetailVO) {
                    JSONObject detailPathObject = pathDetailVO.getDetailPath();
                    PathAnalyzeRO pathAnalyzeRO = (PathAnalyzeRO)detailPathObject.toJavaObject(PathAnalyzeRO.class);
                    message = message + " " + pathAnalyzeRO.getMessage();
                }

                switch(entity.getAnalyzeStatus()) {
                    case 0:
                        ++notStart;
                        break;
                    case 1:
                        ++success;
                        break;
                    case 2:
                        ++error;
                        ++failed;
                        break;
                    case 3:
                        ++pathNoExists;
                        ++failed;
                        break;
                    case 4:
                        ++access;
                        break;
                    case 7:
                        ++noSrcSubnet;
                        ++failed;
                        break;
                    case 9:
                        ++noDstSubnet;
                        ++failed;
                        break;
                    case 12:
                        ++srcDstHasSameSubnet;
                        break;
                    case 121:
                        ++bigInternetNatExits;
                        ++failed;
                }
            }

            if (notStart > 0) {
                object.put("info", String.format("未开始（未开始 %d 条路径）", notStart));
            }

            if (success > 0) {
                object.put("success", String.format("开通成功（开通 %d 条路径）", success));
            }

            if (access > 0 && srcDstHasSameSubnet > 0) {
                object.put("access", String.format("无需开通（已有通路 %d 条;%s %d 条）", access, ReturnCode.getMsg(128), srcDstHasSameSubnet));
            } else if (access > 0) {
                object.put("access", String.format("无需开通（已有通路 %d 条）", access));
            } else if (srcDstHasSameSubnet > 0) {
                object.put("access", String.format("无需开通（%s %d 条）", ReturnCode.getMsg(128), srcDstHasSameSubnet));
            }

            if (failed > 0) {
                StringBuilder sb = new StringBuilder();
                if (error > 0) {
                    sb.append(String.format(";系统执行异常 %d 条", error));
                }

                if (noSrcSubnet > 0) {
                    sb.append(String.format(";源地址无对应子网 %d 条", noSrcSubnet));
                }

                if (noDstSubnet > 0) {
                    sb.append(String.format(";未找到可达路径 %d 条", noDstSubnet));
                }

                if (pathNoExists > 0) {
                    sb.append(String.format("; %d 条路径不存在", pathNoExists));
                }

                if (bigInternetNatExits > 0) {
                    sb.append(String.format("; 大网段有 %d 条路径经过NAT,仿真失败", bigInternetNatExits));
                }

                if (sb.length() > 0) {
                    sb.deleteCharAt(0);
                }

                object.put("danger", String.format("开通失败（%s）", sb.toString()));
            }

            if (StringUtils.isNotBlank(message)) {
                object.put("message", message);
            }

            return this.getReturnJSON(0, object);
        }
    }

    String getSubnetDeviceList(String subnetUuid) {
        String devices = new String();
        if (AliStringUtils.isEmpty(subnetUuid)) {
            logger.info("子网uuid为空，没有关联设备");
            return devices;
        } else {
            List<String> deviceUuidList = this.whaleService.getSubnetDeviceUuidList(subnetUuid);
            if (deviceUuidList == null) {
                logger.error("获取子网关联设备uuidList失败");
                return devices;
            } else {
                StringBuilder sb = new StringBuilder();
                Iterator var5 = deviceUuidList.iterator();

                while(var5.hasNext()) {
                    String deviceUuid = (String)var5.next();
                    NodeEntity node = this.policyRecommendTaskService.getTheNodeByUuid(deviceUuid);
                    if (node == null) {
                        logger.error(String.format("设备(%s)不存在...", deviceUuid));
                    } else {
                        sb.append(",");
                        sb.append(String.format("%s(%s)", node.getDeviceName(), node.getIp()));
                    }
                }

                if (sb.length() > 0) {
                    sb.deleteCharAt(0);
                }

                return sb.toString();
            }
        }
    }

    @ApiOperation("仿真导入历史列表，查询传参JSON格式")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "limit",
            value = "每页条数",
            dataType = "Integer"
    )})
    @PostMapping({"/pageList"})
    public ReturnT pageList(@RequestBody PushRecommendTaskHistoryEntity historyEntity) {
        try {
            PageInfo<PushRecommendTaskHistoryEntity> pageInfoList = this.recommendTaskHistoryService.findList(historyEntity, historyEntity.getPage(), historyEntity.getLimit());
            return new ReturnT(pageInfoList);
        } catch (Exception var3) {
            logger.error("分页查询仿真导入历史列表异常", var3);
            return ReturnT.FAIL;
        }
    }
}
