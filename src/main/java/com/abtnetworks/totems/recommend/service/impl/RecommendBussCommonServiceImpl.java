package com.abtnetworks.totems.recommend.service.impl;

import com.abtnetworks.totems.advanced.dao.mysql.PushMappingNatMapper;
import com.abtnetworks.totems.advanced.entity.PushMappingNatEntity;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.constants.CommonConstants;
import com.abtnetworks.totems.common.constants.ReturnCode;
import com.abtnetworks.totems.common.dto.TwoMemberObject;
import com.abtnetworks.totems.common.dto.commandline.DNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.NatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.SNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.common.enums.IpTypeEnum;
import com.abtnetworks.totems.common.enums.NatTypeEnum;
import com.abtnetworks.totems.common.enums.RoutingEntryTypeEnum;
import com.abtnetworks.totems.common.enums.SearchRangeOpEnum;
import com.abtnetworks.totems.common.enums.SendErrorEnum;
import com.abtnetworks.totems.common.lang.TotemsStringUtils;
import com.abtnetworks.totems.common.tools.excel.ExcelParser;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.InputValueUtils;
import com.abtnetworks.totems.common.utils.IpMatchUtil;
import com.abtnetworks.totems.generate.dto.excel.ExcelTaskNatEntity;
import com.abtnetworks.totems.generate.dto.excel.ExcelTaskStaticRouteEntity;
import com.abtnetworks.totems.issued.exception.IssuedExecutorException;
import com.abtnetworks.totems.push.dto.PushRecommendStaticRoutingDTO;
import com.abtnetworks.totems.push.service.PushTaskStaticRoutingService;
import com.abtnetworks.totems.recommend.dao.mysql.NodeMapper;
import com.abtnetworks.totems.recommend.dao.mysql.RecommendTaskMapper;
import com.abtnetworks.totems.recommend.dto.task.SimulationTaskDTO;
import com.abtnetworks.totems.recommend.dto.task.WhatIfNatDTO;
import com.abtnetworks.totems.recommend.dto.task.WhatIfRouteDTO;
import com.abtnetworks.totems.recommend.entity.AddRecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.abtnetworks.totems.recommend.service.RecommendBussCommonService;
import com.abtnetworks.totems.recommend.service.WhatIfService;
import com.abtnetworks.totems.recommend.task.impl.SimulationTaskServiceImpl;
import com.abtnetworks.totems.whale.baseapi.ro.WhatIfRO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendBussCommonServiceImpl implements RecommendBussCommonService {
    private static final Logger log = LoggerFactory.getLogger(RecommendBussCommonServiceImpl.class);
    @Autowired
    WhatIfService whatIfService;
    @Autowired
    RecommendTaskManager policyRecommendTaskService;
    @Autowired
    ExcelParser excelParser;
    @Resource
    RemoteBranchService remoteBranchService;
    @Resource
    RecommendTaskMapper recommendTaskMapper;
    @Autowired
    PushMappingNatMapper pushMappingNatMapper;
    @Autowired
    private NodeMapper policyRecommendNodeMapper;
    @Qualifier("simulationTaskServiceImpl")
    @Autowired
    SimulationTaskServiceImpl recommendTaskManager;
    @Autowired
    PushTaskStaticRoutingService pushTaskStaticRoutingService;

    public RecommendBussCommonServiceImpl() {
    }

    public WhatIfRO createWhatIfCaseUuid(RecommendTaskEntity taskEntity) {
        WhatIfRO whatIf = null;
        String relevancyNat = taskEntity.getRelevancyNat();
        if (StringUtils.isNotEmpty(relevancyNat)) {
            String whatIfCaseName = String.format("A%s", String.valueOf(System.currentTimeMillis()));
            JSONArray whatIfCaseArray = JSONObject.parseArray(relevancyNat);
            List<WhatIfNatDTO> whatIfNatDTOList = new ArrayList();
            List<WhatIfRouteDTO> whatIfRouteDTOList = new ArrayList();
            if (whatIfCaseArray != null && whatIfCaseArray.size() > 0) {
                Integer ipTypeForTask = ObjectUtils.isNotEmpty(taskEntity.getIpType()) ? taskEntity.getIpType() : IpTypeEnum.IPV4.getCode();

                for(int i = 0; i < whatIfCaseArray.size(); ++i) {
                    JSONObject jsonObject = (JSONObject)whatIfCaseArray.get(i);
                    int taskId = jsonObject.getIntValue("taskId");
                    RecommendTaskEntity recommendTaskByTask = this.policyRecommendTaskService.getRecommendTaskByTaskId(taskId);
                    if (recommendTaskByTask != null) {
                        if (20 == recommendTaskByTask.getTaskType()) {
                            PushRecommendStaticRoutingDTO routeDto = this.pushTaskStaticRoutingService.getStaticRoutingByTaskId(taskId);
                            if (null != routeDto) {
                                WhatIfRouteDTO whatIfRouteDTO = new WhatIfRouteDTO();
                                whatIfRouteDTO.setName(recommendTaskByTask.getTheme());
                                whatIfRouteDTO.setDeviceUuid(routeDto.getDeviceUuid());
                                whatIfRouteDTO.setRouteType(RoutingEntryTypeEnum.STATIC);
                                Integer ipType = ObjectUtils.isNotEmpty(recommendTaskByTask.getIpType()) ? recommendTaskByTask.getIpType() : IpTypeEnum.IPV4.getCode();
                                if (ipType.equals(ipTypeForTask)) {
                                    whatIfRouteDTO.setIpType(ipType);
                                    if (IpTypeEnum.IPV4.getCode().equals(ipType)) {
                                        whatIfRouteDTO.setIpv4DstIp(recommendTaskByTask.getDstIp());
                                        whatIfRouteDTO.setIp4Gateway(routeDto.getNextHop());
                                    } else if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                                        whatIfRouteDTO.setIpv6DstIp(recommendTaskByTask.getDstIp());
                                        whatIfRouteDTO.setIp6Gateway(routeDto.getNextHop());
                                    }

                                    whatIfRouteDTO.setMaskLength(routeDto.getSubnetMask());
                                    whatIfRouteDTO.setInterfaceName(routeDto.getOutInterface());
                                    whatIfRouteDTO.setRoutingTableUuid(routeDto.getSrcVirtualRouter());
                                    whatIfRouteDTO.setDstRoutingTableUuid(routeDto.getDstVirtualRouter());
                                    whatIfRouteDTO.setDistance(StringUtils.isBlank(routeDto.getPriority()) ? 0 : Integer.valueOf(routeDto.getPriority()));
                                    whatIfRouteDTOList.add(whatIfRouteDTO);
                                }
                            }
                        } else {
                            WhatIfNatDTO whatIfNatDTO = new WhatIfNatDTO();
                            String additionInfo = recommendTaskByTask.getAdditionInfo();
                            if (!StringUtils.isEmpty(additionInfo)) {
                                JSONObject additionJson = JSONObject.parseObject(additionInfo);
                                String deviceUuid = additionJson.getString("deviceUuid");
                                if (deviceUuid == null) {
                                    log.error(String.format("设备%s不存在，无法获取设备UUID，跳过转换WhatIfNatDTO过程...", deviceUuid));
                                } else {
                                    Integer ipType = ObjectUtils.isNotEmpty(recommendTaskByTask.getIpType()) ? recommendTaskByTask.getIpType() : IpTypeEnum.IPV4.getCode();
                                    if (ipType.equals(ipTypeForTask)) {
                                        String ipTypeParam = IpTypeEnum.IPV4.getCode().equals(ipType) ? "IP4" : "IP6";
                                        whatIfNatDTO.setDeviceUuid(deviceUuid);
                                        whatIfNatDTO.setName(recommendTaskByTask.getTheme());
                                        BeanUtils.copyProperties(recommendTaskByTask, whatIfNatDTO);
                                        whatIfNatDTO.setPreDstAddress(recommendTaskByTask.getDstIp());
                                        whatIfNatDTO.setPreSrcAddress(recommendTaskByTask.getSrcIp());
                                        String preService = recommendTaskByTask.getServiceList();
                                        List<ServiceDTO> serviceDTOList = new ArrayList();
                                        if (StringUtils.isNotEmpty(preService)) {
                                            serviceDTOList = JSONArray.parseArray(preService, ServiceDTO.class);
                                            whatIfNatDTO.setPreServiceList((List)serviceDTOList);
                                        }

                                        whatIfNatDTO.setDstZone(additionJson.getString("dstZone"));
                                        whatIfNatDTO.setSrcZone(additionJson.getString("srcZone"));
                                        whatIfNatDTO.setInDevItf(additionJson.getString("srcItf"));
                                        whatIfNatDTO.setOutDevItf(additionJson.getString("dstItf"));
                                        whatIfNatDTO.setIpType(ipTypeParam);
                                        String natField = NatTypeEnum.getNatByCode(recommendTaskByTask.getTaskType()).getNatField();
                                        String postPort;
                                        if ("STATIC".equalsIgnoreCase(natField)) {
                                            whatIfNatDTO.setNatType("STATIC");
                                            postPort = additionJson.getString("insideAddress");
                                            String postDstAddress = additionJson.getString("globalAddress");
                                            if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                                                whatIfNatDTO.setPreIp6DstAddress(postPort);
                                                whatIfNatDTO.setPostIp6DstAddress(postDstAddress);
                                                whatIfNatDTO.setPreIp6SrcAddress(recommendTaskByTask.getSrcIp());
                                            } else {
                                                whatIfNatDTO.setPreDstAddress(postPort);
                                                whatIfNatDTO.setPostDstAddress(postDstAddress);
                                            }
                                        } else if ("SRC".equalsIgnoreCase(natField)) {
                                            whatIfNatDTO.setNatType("DYNAMIC");
                                            whatIfNatDTO.setNatField("SRC");
                                            if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                                                whatIfNatDTO.setPreIp6DstAddress(recommendTaskByTask.getDstIp());
                                                whatIfNatDTO.setPreIp6SrcAddress(recommendTaskByTask.getSrcIp());
                                                whatIfNatDTO.setPostIp6DstAddress((String)null);
                                                whatIfNatDTO.setPostIp6SrcAddress(additionJson.getString("postIpAddress"));
                                            } else {
                                                whatIfNatDTO.setPostDstAddress((String)null);
                                                whatIfNatDTO.setPostSrcAddress(additionJson.getString("postIpAddress"));
                                            }
                                        } else if ("DST".equalsIgnoreCase(natField)) {
                                            whatIfNatDTO.setNatField("DST");
                                            whatIfNatDTO.setNatType("DYNAMIC");
                                            if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                                                whatIfNatDTO.setPreIp6DstAddress(recommendTaskByTask.getDstIp());
                                                whatIfNatDTO.setPreIp6SrcAddress(recommendTaskByTask.getSrcIp());
                                                whatIfNatDTO.setPostIp6SrcAddress((String)null);
                                                whatIfNatDTO.setPostIp6DstAddress(additionJson.getString("postIpAddress"));
                                            } else {
                                                whatIfNatDTO.setPostSrcAddress((String)null);
                                                whatIfNatDTO.setPostDstAddress(additionJson.getString("postIpAddress"));
                                            }
                                        } else if ("BOTH".equalsIgnoreCase(natField)) {
                                            whatIfNatDTO.setNatField("BOTH");
                                            whatIfNatDTO.setNatType("DYNAMIC");
                                            if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                                                whatIfNatDTO.setPreIp6DstAddress(recommendTaskByTask.getDstIp());
                                                whatIfNatDTO.setPreIp6SrcAddress(recommendTaskByTask.getSrcIp());
                                                whatIfNatDTO.setPostIp6SrcAddress(additionJson.getString("postSrcIp"));
                                                whatIfNatDTO.setPostIp6DstAddress(additionJson.getString("postDstIp"));
                                            } else {
                                                whatIfNatDTO.setPostSrcAddress(additionJson.getString("postSrcIp"));
                                                whatIfNatDTO.setPostDstAddress(additionJson.getString("postDstIp"));
                                            }

                                            postPort = additionJson.getString("postPort");
                                            if (CollectionUtils.isNotEmpty((Collection)serviceDTOList) && StringUtils.isNotEmpty(postPort)) {
                                                List<ServiceDTO> serviceDTOListPost = new ArrayList();
                                                Iterator var24 = ((List)serviceDTOList).iterator();

                                                while(var24.hasNext()) {
                                                    ServiceDTO serviceDTO = (ServiceDTO)var24.next();
                                                    ServiceDTO serviceDTO1 = new ServiceDTO();
                                                    serviceDTO1.setProtocol(serviceDTO.getProtocol());
                                                    serviceDTO1.setDstPorts(postPort);
                                                    serviceDTOListPost.add(serviceDTO1);
                                                }

                                                whatIfNatDTO.setPostServiceList(serviceDTOListPost);
                                            }
                                        }

                                        whatIfNatDTOList.add(whatIfNatDTO);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            whatIf = this.whatIfService.createWhatIfCase(whatIfNatDTOList, whatIfRouteDTOList, whatIfCaseName, whatIfCaseName);
        }

        return whatIf;
    }

    public WhatIfRO createWhatIfCaseUuid(List<ExcelTaskNatEntity> natExcelList, List<RecommendTaskEntity> natTaskList, List<PushRecommendStaticRoutingDTO> routeTaskList, String whatIfCaseName, String user, UserInfoDTO userInfoDTO) {
        WhatIfRO whatIf = null;
        if (natExcelList != null && natExcelList.size() > 0) {
            List<WhatIfNatDTO> whatIfNatDTOList = this.getWhatIfNatDTOList(natExcelList);
            List<WhatIfRouteDTO> whatIfRouteDTOList = this.getWhatIfRouteDTOList(routeTaskList);
            whatIf = this.whatIfService.createWhatIfCase(whatIfNatDTOList, whatIfRouteDTOList, whatIfCaseName, whatIfCaseName);
            if (whatIf != null && !AliStringUtils.isEmpty(whatIf.getUuid())) {
                log.info("创建模拟开通环境UUID为:" + whatIf.getUuid());
                List<RecommendTaskEntity> tmpList = this.excelParser.getRecommendTaskEntity(natExcelList, whatIfCaseName, user, userInfoDTO);
                natTaskList.addAll(tmpList);
            } else {
                log.error("创建模拟开通数据失败！" + JSONObject.toJSONString(natExcelList));
            }
        }

        return whatIf;
    }

    public List<WhatIfNatDTO> getWhatIfNatDTOList(List<ExcelTaskNatEntity> natExcelList) {
        List<WhatIfNatDTO> whatIfNatDTOList = new ArrayList();
        Iterator var3 = natExcelList.iterator();

        while(var3.hasNext()) {
            ExcelTaskNatEntity entity = (ExcelTaskNatEntity)var3.next();
            WhatIfNatDTO whatIfNatDTO = new WhatIfNatDTO();
            NodeEntity nodeEntity = this.policyRecommendTaskService.getDeviceByManageIp(entity.getDeviceIp());
            if (nodeEntity == null) {
                log.error(String.format("设备%s不存在，无法获取设备UUID，跳过转换WhatIfNatDTO过程...", entity.getDeviceIp()));
            } else {
                BeanUtils.copyProperties(entity, whatIfNatDTO);
                Integer ipType = IpTypeEnum.covertString2Int(entity.getIpType());
                String ipTypeParam = IpTypeEnum.IPV4.getCode().equals(ipType) ? "IP4" : "IP6";
                whatIfNatDTO.setIpType(ipTypeParam);
                String deviceUuid = nodeEntity.getUuid();
                whatIfNatDTO.setDeviceUuid(deviceUuid);
                whatIfNatDTO.setName(entity.getTheme() + "-" + entity.getId());
                if (entity.getNatType().equals("STATIC")) {
                    whatIfNatDTO.setNatType("STATIC");
                    if (!AliStringUtils.isEmpty(entity.getPostSrcAddress())) {
                        whatIfNatDTO.setNatField("BI_DIR_SRC");
                    } else if (!AliStringUtils.isEmpty(entity.getPostDstAddress())) {
                        whatIfNatDTO.setNatField("BI_DIR_DST");
                    }

                    if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                        whatIfNatDTO.setPreIp6DstAddress(entity.getPreDstAddress());
                        whatIfNatDTO.setPostIp6DstAddress(entity.getPostSrcAddress());
                    }
                } else if (entity.getNatType().equals("SNAT")) {
                    whatIfNatDTO.setNatType("DYNAMIC");
                    whatIfNatDTO.setNatField("SRC");
                    whatIfNatDTO.setPostDstAddress((String)null);
                    if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                        whatIfNatDTO.setPostIp6DstAddress((String)null);
                        whatIfNatDTO.setPostIp6SrcAddress(entity.getPostSrcAddress());
                    }
                } else if (entity.getNatType().equals("DNAT")) {
                    whatIfNatDTO.setNatField("DST");
                    whatIfNatDTO.setNatType("DYNAMIC");
                    whatIfNatDTO.setPostSrcAddress((String)null);
                    if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                        whatIfNatDTO.setPostIp6SrcAddress((String)null);
                        whatIfNatDTO.setPostIp6DstAddress(entity.getPostDstAddress());
                    }
                } else if (entity.getNatType().equals("BOTH")) {
                    whatIfNatDTO.setNatField("BOTH");
                    whatIfNatDTO.setNatType("DYNAMIC");
                    if (IpTypeEnum.IPV6.getCode().equals(ipType)) {
                        whatIfNatDTO.setPostIp6SrcAddress(entity.getPostSrcAddress());
                        whatIfNatDTO.setPostIp6DstAddress(entity.getPostDstAddress());
                    }
                }

                whatIfNatDTOList.add(whatIfNatDTO);
            }
        }

        return whatIfNatDTOList;
    }

    private List<WhatIfRouteDTO> getWhatIfRouteDTOList(List<PushRecommendStaticRoutingDTO> routeTaskList) {
        if (CollectionUtils.isEmpty(routeTaskList)) {
            return new ArrayList();
        } else {
            List<WhatIfRouteDTO> resultRouteDTO = new ArrayList();
            Iterator var3 = routeTaskList.iterator();

            while(var3.hasNext()) {
                PushRecommendStaticRoutingDTO staticRoutingDTO = (PushRecommendStaticRoutingDTO)var3.next();
                WhatIfRouteDTO routeDTO = new WhatIfRouteDTO();
                routeDTO.setName(staticRoutingDTO.getTheme());
                routeDTO.setDeviceUuid(staticRoutingDTO.getDeviceUuid());
                routeDTO.setRouteType(RoutingEntryTypeEnum.STATIC);
                routeDTO.setIpType(staticRoutingDTO.getIpType());
                routeDTO.setIpv4DstIp(staticRoutingDTO.getDstIp());
                routeDTO.setIpv6DstIp(staticRoutingDTO.getDstIp());
                routeDTO.setMaskLength(staticRoutingDTO.getSubnetMask());
                routeDTO.setInterfaceName(staticRoutingDTO.getOutInterface());
                routeDTO.setIp4Gateway(staticRoutingDTO.getNextHop());
                routeDTO.setIp6Gateway(staticRoutingDTO.getNextHop());
                routeDTO.setRoutingTableUuid(staticRoutingDTO.getSrcVirtualRouter());
                routeDTO.setDstRoutingTableUuid(staticRoutingDTO.getDstVirtualRouter());
                routeDTO.setDistance(StringUtils.isBlank(staticRoutingDTO.getPriority()) ? 0 : Integer.valueOf(staticRoutingDTO.getPriority()));
                resultRouteDTO.add(routeDTO);
            }

            return resultRouteDTO;
        }
    }

    public int checkParamForDstAddress(AddRecommendTaskEntity entity) {
        Integer ipType = entity.getIpType();
        int rc;
        if (ipType != null && ipType == 1) {
            rc = InputValueUtils.checkIpV6(entity.getDstIp());
        } else {
            rc = InputValueUtils.checkIp(entity.getDstIp());
            if (rc == 73) {
                entity.setDstIp(InputValueUtils.autoCorrect(entity.getDstIp()));
                rc = 0;
            }
        }

        if (rc != 0 && rc != 73) {
            String msg = "目的地址错误！" + ReturnCode.getMsg(rc);
            throw new IllegalArgumentException(msg);
        } else {
            entity.setDstIp(InputValueUtils.formatIpAddress(entity.getDstIp()));
            return rc;
        }
    }

    public int checkParamForSrcAddress(AddRecommendTaskEntity entity) {
        Integer ipType = entity.getIpType();
        int rc;
        if (ipType != null && ipType == 1) {
            rc = InputValueUtils.checkIpV6(entity.getSrcIp());
        } else {
            rc = InputValueUtils.checkIp(entity.getSrcIp());
            if (rc == 73) {
                entity.setSrcIp(InputValueUtils.autoCorrect(entity.getSrcIp()));
                rc = 0;
            }
        }

        if (rc != 0 && rc != 73) {
            String msg = "源地址错误！" + ReturnCode.getMsg(rc);
            throw new IllegalArgumentException(msg);
        } else {
            entity.setSrcIp(InputValueUtils.formatIpAddress(entity.getSrcIp()));
            return rc;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public RecommendTaskEntity addAutoNatGenerate(AddRecommendTaskEntity entity, Authentication auth) throws IssuedExecutorException {
        RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
        BeanUtils.copyProperties(entity, recommendTaskEntity);
        recommendTaskEntity.setSrcIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getSrcIpSystem()));
        recommendTaskEntity.setDstIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getDstIpSystem()));
        recommendTaskEntity.setServiceList(entity.getServiceList() == null ? null : JSONObject.toJSONString(entity.getServiceList()));
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNumber = "A" + simpleDateFormat.format(date);
        recommendTaskEntity.setCreateTime(date);
        recommendTaskEntity.setOrderNumber(orderNumber);
        if(entity.getBranchLevel() == null || entity.getBranchLevel().equals("")){
            UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(auth.getName());
            if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
                recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
            } else {
                recommendTaskEntity.setBranchLevel("00");
            }
        }else{
            recommendTaskEntity.setBranchLevel(entity.getBranchLevel());
        }

        if(recommendTaskEntity.getUserName() == null ||  recommendTaskEntity.getUserName().equals("")){
            recommendTaskEntity.setUserName(auth.getName());
        }

        if (entity.getIdleTimeout() != null) {
            recommendTaskEntity.setIdleTimeout(entity.getIdleTimeout() * CommonConstants.HOUR_SECOND);
        } else {
            recommendTaskEntity.setIdleTimeout((Integer)null);
        }

        recommendTaskEntity.setTaskType(entity.getTaskType());
        recommendTaskEntity.setStatus(0);
        int rc = this.checkPostRelevancyNat(recommendTaskEntity, auth);
        if (10 == rc) {
            throw new IssuedExecutorException(SendErrorEnum.DEVICE_NOT_EXIST_FAIL);
        } else if (0 != rc) {
            throw new IssuedExecutorException(SendErrorEnum.NOT_FIND_NAT_ADDRESS_MAPPING);
        } else {
            this.recommendTaskMapper.insert(recommendTaskEntity);
            return recommendTaskEntity;
        }
    }

    public int checkPostRelevancyNat(RecommendTaskEntity recommendTaskEntity, Authentication auth) {
        Map<String, Object> paramMap = new HashMap();
        if (StringUtils.isBlank(recommendTaskEntity.getPostSrcIp()) && StringUtils.isBlank(recommendTaskEntity.getPostDstIp())) {
            log.info("工单:{}源转,目的转地址都不存在,跳过nat自动定位", recommendTaskEntity.getTheme());
            return 0;
        } else {
            List<PushMappingNatEntity> pushMappingNatEntities = this.pushMappingNatMapper.listPushMappingNatInfo(paramMap);
            String postSrcIp = recommendTaskEntity.getPostSrcIp();
            String srcIp = recommendTaskEntity.getSrcIp();
            String dstIp = recommendTaskEntity.getDstIp();
            String postDstIp = recommendTaskEntity.getPostDstIp();
            if (CollectionUtils.isEmpty(pushMappingNatEntities)) {
                log.info("地址映射配置为空,没有找到映射表的nat策略,退出自动创建nat策略");
                return 124;
            } else {
                List<TwoMemberObject<Long, Long>> infoSrcIpList = getListMemberByIp(srcIp);
                List<TwoMemberObject<Long, Long>> infoSrcPostList = getListMemberByIp(postSrcIp);
                List<TwoMemberObject<Long, Long>> infoDstIpList = getListMemberByIp(dstIp);
                List<TwoMemberObject<Long, Long>> infoPostDstList = getListMemberByIp(postDstIp);
                String natFlag = null;
                if (StringUtils.isNotBlank(srcIp) && StringUtils.isNotBlank(dstIp) && StringUtils.isNotBlank(postSrcIp) && StringUtils.isNotBlank(postDstIp)) {
                    natFlag = NatTypeEnum.BOTH.getNatField();
                } else if (StringUtils.isNotBlank(srcIp) && StringUtils.isNotBlank(dstIp) && StringUtils.isNotBlank(postSrcIp)) {
                    natFlag = NatTypeEnum.SRC.getNatField();
                } else if (StringUtils.isNotBlank(srcIp) && StringUtils.isNotBlank(dstIp) && StringUtils.isNotBlank(postDstIp)) {
                    natFlag = NatTypeEnum.DST.getNatField();
                }

                boolean srcOpMatch = false;
                boolean dstOpMatch = false;
                Map<String, String> param = new HashMap();
                Iterator var17 = pushMappingNatEntities.iterator();

                while(var17.hasNext()) {
                    PushMappingNatEntity pushMappingNatEntity = (PushMappingNatEntity)var17.next();
                    String preIp = pushMappingNatEntity.getPreIp();
                    String postIp = pushMappingNatEntity.getPostIp();
                    List<TwoMemberObject<Long, Long>> conditionPostSrcContainList = getListMemberByIp(postIp);
                    List<TwoMemberObject<Long, Long>> conditionPreSrcIpContainList = getListMemberByIp(preIp);
                    if (NatTypeEnum.SRC.getNatField().equals(natFlag)) {
                        srcOpMatch = IpMatchUtil.rangeOpMatch(conditionPreSrcIpContainList, infoSrcIpList, SearchRangeOpEnum.CONTAINED_BY);
                        if (srcOpMatch) {
                            srcOpMatch = IpMatchUtil.rangeOpMatch(conditionPostSrcContainList, infoSrcPostList, SearchRangeOpEnum.CONTAINED_BY);
                        }

                        if (srcOpMatch) {
                            this.buildParamMap(param, pushMappingNatEntity);
                            break;
                        }
                    } else if (NatTypeEnum.DST.getNatField().equals(natFlag)) {
                        dstOpMatch = IpMatchUtil.rangeOpMatch(conditionPreSrcIpContainList, infoDstIpList, SearchRangeOpEnum.CONTAINED_BY);
                        if (dstOpMatch) {
                            dstOpMatch = IpMatchUtil.rangeOpMatch(conditionPostSrcContainList, infoPostDstList, SearchRangeOpEnum.CONTAINED_BY);
                        }

                        if (dstOpMatch) {
                            this.buildParamMap(param, pushMappingNatEntity);
                            break;
                        }
                    } else if (NatTypeEnum.BOTH.getNatField().equals(natFlag)) {
                        srcOpMatch = IpMatchUtil.rangeOpMatch(conditionPreSrcIpContainList, infoSrcIpList, SearchRangeOpEnum.CONTAINED_BY);
                        if (srcOpMatch) {
                            srcOpMatch = IpMatchUtil.rangeOpMatch(conditionPostSrcContainList, infoSrcPostList, SearchRangeOpEnum.CONTAINED_BY);
                        }

                        dstOpMatch = IpMatchUtil.rangeOpMatch(conditionPreSrcIpContainList, infoDstIpList, SearchRangeOpEnum.CONTAINED_BY);
                        if (dstOpMatch) {
                            dstOpMatch = IpMatchUtil.rangeOpMatch(conditionPostSrcContainList, infoPostDstList, SearchRangeOpEnum.CONTAINED_BY);
                        }

                        if (srcOpMatch && dstOpMatch) {
                            this.buildParamMap(param, pushMappingNatEntity);
                            break;
                        }

                        srcOpMatch = false;
                        dstOpMatch = false;
                    }
                }

                int rc = this.insertNatPolicyAndBuildRelevancyNat(recommendTaskEntity, auth, srcOpMatch, dstOpMatch, param);
                return rc;
            }
        }
    }

    private void buildParamMap(Map<String, String> param, PushMappingNatEntity pushMappingNatEntity) {
        param.put("DEVICE_UUID", pushMappingNatEntity.getDeviceUuid());
        param.put("SRC_ZONE", pushMappingNatEntity.getSrcZone());
        param.put("SRC_ITF", pushMappingNatEntity.getInDevIf());
        param.put("DST_ZONE", pushMappingNatEntity.getDstZone());
        param.put("DST_ITF", pushMappingNatEntity.getOutDevIf());
    }

    private int insertNatPolicyAndBuildRelevancyNat(RecommendTaskEntity recommendTaskEntity, Authentication auth, boolean srcOpMatch, boolean dstOpMatch, Map<String, String> param) {
        NodeEntity node;
        if (srcOpMatch && dstOpMatch) {
            node = this.policyRecommendNodeMapper.getTheNodeByUuid((String)param.get("DEVICE_UUID"));
            if (node == null) {
                log.info("根据设备uuid:{}查询设备信息为空!", param.get("DEVICE_UUID"));
                return 10;
            } else {
                NatPolicyDTO natPolicyDTO = new NatPolicyDTO();
                natPolicyDTO.setDeviceUuid((String)param.get("DEVICE_UUID"));
                natPolicyDTO.setSrcZone((String)param.get("SRC_ZONE"));
                natPolicyDTO.setSrcItf((String)param.get("SRC_ITF"));
                natPolicyDTO.setDstZone((String)param.get("DST_ZONE"));
                natPolicyDTO.setDstItf((String)param.get("DST_ITF"));
                natPolicyDTO.setSrcIp(recommendTaskEntity.getSrcIp());
                natPolicyDTO.setDstIp(recommendTaskEntity.getDstIp());
                natPolicyDTO.setPostSrcIp(recommendTaskEntity.getPostSrcIp());
                natPolicyDTO.setPostDstIp(recommendTaskEntity.getPostDstIp());
                natPolicyDTO.setTheme(recommendTaskEntity.getTheme());
                natPolicyDTO.setServiceList(new ArrayList());
                natPolicyDTO.setPostPort("");
                this.policyRecommendTaskService.insertBothNatPolicy(natPolicyDTO, auth);
                this.buildRelevancyNat(recommendTaskEntity, node, natPolicyDTO.getTaskId(), natPolicyDTO.getTaskId(), NatTypeEnum.BOTH.getTypeCode());
                return 0;
            }
        } else if (srcOpMatch) {
            node = this.policyRecommendNodeMapper.getTheNodeByUuid((String)param.get("DEVICE_UUID"));
            if (node == null) {
                log.info("根据设备uuid:{}查询设备信息为空!", param.get("DEVICE_UUID"));
                return 10;
            } else {
                SNatPolicyDTO sNatPolicyDTO = new SNatPolicyDTO();
                sNatPolicyDTO.setDeviceUuid((String)param.get("DEVICE_UUID"));
                sNatPolicyDTO.setSrcZone((String)param.get("SRC_ZONE"));
                sNatPolicyDTO.setSrcItf((String)param.get("SRC_ITF"));
                sNatPolicyDTO.setDstZone((String)param.get("DST_ZONE"));
                sNatPolicyDTO.setDstItf((String)param.get("DST_ITF"));
                sNatPolicyDTO.setSrcIp(recommendTaskEntity.getSrcIp());
                sNatPolicyDTO.setDstIp(recommendTaskEntity.getDstIp());
                sNatPolicyDTO.setPostIpAddress(recommendTaskEntity.getPostSrcIp());
                sNatPolicyDTO.setTheme(recommendTaskEntity.getTheme());
                sNatPolicyDTO.setServiceList(new ArrayList());
                this.policyRecommendTaskService.insertSrcNatPolicy(sNatPolicyDTO, auth);
                this.buildRelevancyNat(recommendTaskEntity, node, sNatPolicyDTO.getTaskId(), sNatPolicyDTO.getTaskId(), NatTypeEnum.SRC.getTypeCode());
                return 0;
            }
        } else if (dstOpMatch) {
            node = this.policyRecommendNodeMapper.getTheNodeByUuid((String)param.get("DEVICE_UUID"));
            if (node == null) {
                log.info("根据设备uuid:{}查询设备信息为空!", param.get("DEVICE_UUID"));
                return 10;
            } else {
                DNatPolicyDTO dNatPolicyDTO = new DNatPolicyDTO();
                dNatPolicyDTO.setDeviceUuid((String)param.get("DEVICE_UUID"));
                dNatPolicyDTO.setSrcZone((String)param.get("SRC_ZONE"));
                dNatPolicyDTO.setSrcItf((String)param.get("SRC_ITF"));
                dNatPolicyDTO.setDstZone((String)param.get("DST_ZONE"));
                dNatPolicyDTO.setDstItf((String)param.get("DST_ITF"));
                dNatPolicyDTO.setSrcIp(recommendTaskEntity.getSrcIp());
                dNatPolicyDTO.setDstIp(recommendTaskEntity.getDstIp());
                dNatPolicyDTO.setPostIpAddress(recommendTaskEntity.getPostDstIp());
                dNatPolicyDTO.setTheme(recommendTaskEntity.getTheme());
                dNatPolicyDTO.setServiceList(new ArrayList());
                dNatPolicyDTO.setPostPort("");
                this.policyRecommendTaskService.insertDstNatPolicy(dNatPolicyDTO, auth);
                this.buildRelevancyNat(recommendTaskEntity, node, dNatPolicyDTO.getTaskId(), dNatPolicyDTO.getTaskId(), NatTypeEnum.DST.getTypeCode());
                return 0;
            }
        } else {
            log.info("没有找到映射表的nat策略,退出自动创建nat策略");
            return 124;
        }
    }

    private void buildRelevancyNat(RecommendTaskEntity recommendTaskEntity, NodeEntity node, Integer id, Integer taskId, Integer typeCode) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        String name = String.format("%s(%s(%s))", recommendTaskEntity.getTheme(), node.getDeviceName(), node.getIp());
        jsonObject.put("name", name);
        jsonObject.put("id", id);
        jsonObject.put("taskId", taskId);
        jsonObject.put("type", typeCode);
        jsonObject.put("flag", "auto");
        String indexField = "index";
        if (StringUtils.isBlank(recommendTaskEntity.getRelevancyNat())) {
            jsonObject.put(indexField, 1);
            jsonArray.add(jsonObject);
            recommendTaskEntity.setRelevancyNat(jsonArray.toJSONString());
        } else {
            JSONArray whatIfCaseArray = JSONObject.parseArray(recommendTaskEntity.getRelevancyNat());
            if (0 == whatIfCaseArray.size()) {
                jsonObject.put(indexField, 1);
                jsonArray.add(jsonObject);
                recommendTaskEntity.setRelevancyNat(jsonArray.toJSONString());
            } else {
                int newIndex = whatIfCaseArray.size() + 1;
                jsonObject.put(indexField, newIndex);
                whatIfCaseArray.add(jsonObject);
                recommendTaskEntity.setRelevancyNat(whatIfCaseArray.toJSONString());
            }
        }

    }

    private static List<TwoMemberObject<Long, Long>> getListMemberByIp(String ip) {
        List<TwoMemberObject<Long, Long>> infoList = new ArrayList();
        if (StringUtils.isNotBlank(ip)) {
            String[] ips = ip.split(",");
            String[] var3 = ips;
            int var4 = ips.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String address = var3[var5];
                TwoMemberObject<Long, Long> twoMemberObject = IpMatchUtil.commonConditionList(address);
                infoList.add(twoMemberObject);
            }
        }

        return infoList;
    }

    public Boolean autoStartRecommend(Boolean autoStartRecommend, List<RecommendTaskEntity> recommendTaskEntityList, Authentication auth) {
        if (autoStartRecommend != null && autoStartRecommend) {
            List<SimulationTaskDTO> taskDtoList = new ArrayList();
            Iterator var5 = recommendTaskEntityList.iterator();

            while(var5.hasNext()) {
                RecommendTaskEntity taskEntity = (RecommendTaskEntity)var5.next();
                SimulationTaskDTO taskDTO = new SimulationTaskDTO();
                BeanUtils.copyProperties(taskEntity, taskDTO);
                if (taskEntity.getWhatIfCase() != null) {
                    taskDTO.setWhatIfCaseUuid(taskEntity.getWhatIfCase());
                }

                if (taskEntity.getServiceList() == null) {
                    taskDTO.setServiceList((List)null);
                } else {
                    JSONArray array = JSONArray.parseArray(taskEntity.getServiceList());
                    List<ServiceDTO> serviceList = array.toJavaList(ServiceDTO.class);
                    taskDTO.setServiceList(serviceList);
                }

                if (taskEntity.getTaskType() != null && 15 == taskEntity.getTaskType()) {
                    taskDTO.setDeviceWhatifs(new JSONObject());
                }

                WhatIfRO whatIf = this.createWhatIfCaseUuid(taskEntity);
                if (whatIf != null && !AliStringUtils.isEmpty(whatIf.getUuid())) {
                    log.info("创建模拟开通环境UUID为:" + whatIf.getUuid());
                    taskDTO.setWhatIfCaseUuid(whatIf.getUuid());
                    taskDTO.setDeviceWhatifs(whatIf.getDeviceWhatifs());
                } else {
                    log.error("创建模拟开通数据失败！" + taskEntity.getRelevancyNat());
                }

                taskDtoList.add(taskDTO);
                this.policyRecommendTaskService.updateTaskStatus(taskDTO.getId(), 1);
            }

            this.recommendTaskManager.addSimulationTaskList(taskDtoList, auth);
            return true;
        } else {
            return false;
        }
    }

    public List<PushRecommendStaticRoutingDTO> getRouteExcelDTO(List<ExcelTaskStaticRouteEntity> routeExcelList, String user, UserInfoDTO userInfoDTO) {
        List<PushRecommendStaticRoutingDTO> routeDTOs = new ArrayList();
        if (CollectionUtils.isNotEmpty(routeExcelList)) {
            routeDTOs = this.excelParser.getRouteTaskEntity(routeExcelList, user, userInfoDTO);
        }

        return (List)routeDTOs;
    }
}
