//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.push.service.task.impl;

import com.abtnetworks.data.totems.log.client.LogClientSimple;
import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.advanced.service.AdvancedSettingService;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.constants.CommonConstants;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.dto.CmdDTO;
import com.abtnetworks.totems.common.dto.TaskDTO;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.common.enums.ActionEnum;
import com.abtnetworks.totems.common.enums.DeviceModelNumberEnum;
import com.abtnetworks.totems.common.enums.DeviceTypeEnum;
import com.abtnetworks.totems.common.enums.IpTypeEnum;
import com.abtnetworks.totems.common.enums.MoveSeatEnum;
import com.abtnetworks.totems.common.enums.PolicyEnum;
import com.abtnetworks.totems.common.enums.TaskTypeEnum;
import com.abtnetworks.totems.common.enums.UrlTypeEnum;
import com.abtnetworks.totems.common.executor.ExecutorDto;
import com.abtnetworks.totems.common.executor.ExtendedExecutor;
import com.abtnetworks.totems.common.executor.ExtendedLatchRunnable;
import com.abtnetworks.totems.common.executor.ExtendedRunnable;
import com.abtnetworks.totems.common.lang.TotemsStringUtils;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.DateUtil;
import com.abtnetworks.totems.common.utils.EntityUtils;
import com.abtnetworks.totems.common.utils.InputValueUtils;
import com.abtnetworks.totems.disposal.dto.DisposalScenesDTO;
import com.abtnetworks.totems.disposal.entity.DisposalScenesEntity;
import com.abtnetworks.totems.disposal.service.DisposalScenesService;
import com.abtnetworks.totems.generate.service.CommandlineService;
import com.abtnetworks.totems.generate.task.CmdTaskService;
import com.abtnetworks.totems.issued.annotation.PushTimeLockCheck;
import com.abtnetworks.totems.push.dao.mysql.SystemParamMapper;
import com.abtnetworks.totems.push.dto.BatchCommandTaskDTO;
import com.abtnetworks.totems.push.dto.CommandTaskDTO;
import com.abtnetworks.totems.push.dto.MailServerConfDTO;
import com.abtnetworks.totems.push.enums.PushStatusEnum;
import com.abtnetworks.totems.push.service.PushService;
import com.abtnetworks.totems.push.service.task.PushTaskService;
import com.abtnetworks.totems.push.utils.CustomMailTool;
import com.abtnetworks.totems.push.vo.NewPolicyPushVO;
import com.abtnetworks.totems.recommend.dao.mysql.CommandTaskEdiableMapper;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.PushAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.abtnetworks.totems.recommend.manager.WhaleManager;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendSecurityPolicyVO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
import com.alibaba.fastjson.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PushTaskServiceImpl implements PushTaskService {
    private static Logger logger = LoggerFactory.getLogger(PushTaskServiceImpl.class);
    private static final String STOP_TYPE_INTERUPT = "interrupt";
    private static final String STOP_TYPE_STOP = "stop";
    private Integer WAIT_TIME = 60000;
    private static CountDownLatch scheduleLatch;
    private static Map<String, String> scheduleMap = new LinkedHashMap();
    @Autowired
    private PushService pushService;
    @Autowired
    private PushTaskService pushTaskServiceImpl;
    @Autowired
    public RecommendTaskManager taskService;
    @Autowired
    private RecommendTaskManager recommendTaskService;
    @Autowired
    private CommandTaskManager commandTaskManager;
    @Autowired
    private WhaleManager whaleManager;
    @Autowired
    private CommandlineService commandlineService;
    @Autowired
    private CmdTaskService cmdTaskService;
    @Autowired
    private SystemParamMapper systemParamMapper;
    @Resource
    CommandTaskEdiableMapper commandTaskEditableMapper;
    @Resource
    RemoteBranchService remoteBranchService;
    @Autowired
    private LogClientSimple logClientSimple;
    @Autowired
    AdvancedSettingService advancedSettingService;
    @Autowired
    @Qualifier("commandExecutor")
    private Executor pushExecutor;
    @Autowired
    @Qualifier("pushScheduleExecutor")
    private Executor pushScheduleExecutor;
    @Autowired
    @Qualifier("commandlineExecutor")
    private Executor commandlineExecutor;
    @Autowired
    @Qualifier("pushExecutor")
    private Executor batchPushExecutor;
    @Autowired
    public DisposalScenesService disposalScenesService;

    public PushTaskServiceImpl() {
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @PushTimeLockCheck
    public int addCommandTaskList(List<CommandTaskDTO> taskList, boolean doPush) {
        if (Boolean.FALSE.equals(doPush)) {
            return 133;
        } else {
            long start = System.currentTimeMillis();
            logger.info("开始批量下发命令行...,开始时间【{}】", start);
            Iterator var5 = taskList.iterator();

            while(var5.hasNext()) {
                final CommandTaskDTO commandTaskDTO = (CommandTaskDTO)var5.next();
                boolean revert = commandTaskDTO.isRevert();
                if (revert) {
                    this.recommendTaskService.updateCommandTaskRevertStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_QUEUED);
                } else {
                    this.recommendTaskService.updateCommandTaskPushStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_QUEUED);
                }

                final String id = "PT_" + commandTaskDTO.getTaskId();
                if (ExtendedExecutor.containsKey(id)) {
                    logger.info("策略下发线程[" + id + "]已经存在！");
                } else {
                    logger.info("开始下发任务：" + id);
                    this.pushExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "", "", new Date())) {
                        protected void start() throws InterruptedException, Exception {
                            try {
                                long start = System.currentTimeMillis();
                                PushTaskServiceImpl.logger.info("开始下发...线程id【{}】,开始时间【{}】", id, start);
                                PushTaskServiceImpl.this.pushService.pushCommand(commandTaskDTO);
                                long end = System.currentTimeMillis();
                                long consume = end - start;
                                PushTaskServiceImpl.logger.info("下发结束...线程id【{}】,结束时间【{}】,耗时【{}】毫秒", new Object[]{id, end, consume});
                            } catch (Exception var7) {
                                PushTaskServiceImpl.logger.error(var7.getClass().toString());
                                PushTaskServiceImpl.this.taskService.updateTaskStatus(commandTaskDTO.getTaskId(), 19);
                                PushTaskServiceImpl.this.taskService.updateCommandTaskStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_RESULT_STATUS_ERROR);
                                throw var7;
                            }
                        }
                    });
                }
            }

            long end = System.currentTimeMillis();
            long consume = end - start;
            logger.info("批量下发命令行结束...,结束时间【{}】,耗时【{}】毫秒", end, consume);
            return 0;
        }
    }

    @PushTimeLockCheck
    public int addCommandTaskListV2(List<CommandTaskDTO> taskList, boolean doPush) throws Exception {
        if (Boolean.FALSE.equals(doPush)) {
            return 133;
        } else {
            long start = System.currentTimeMillis();
            logger.info("开始批量下发命令行...,开始时间【{}】", start);
            List<BatchCommandTaskDTO> batchTaskLists = new ArrayList();
            boolean isRever = ((CommandTaskDTO)taskList.get(0)).isRevert();
            Map<String, List<CommandTaskEditableEntity>> listMap = new HashMap();
            Iterator var8 = taskList.iterator();

            while(true) {
                List commandList;
                do {
                    if (!var8.hasNext()) {
                        var8 = listMap.keySet().iterator();

                        BatchCommandTaskDTO batchCommandTaskDTO;
                        List taskEditableEntityList;
                        while(var8.hasNext()) {
                            String deviceUuid = (String)var8.next();
                            batchCommandTaskDTO = new BatchCommandTaskDTO();
                            batchCommandTaskDTO.setDeviceUuid(deviceUuid);
                            batchCommandTaskDTO.setList((List)listMap.get(deviceUuid));
                            taskEditableEntityList = (List)(listMap.get(deviceUuid)).stream().map(CommandTaskEditableEntity::getTaskId).distinct().collect(Collectors.toList());
                            batchCommandTaskDTO.setTaskIds(taskEditableEntityList);
                            batchCommandTaskDTO.setRevert(isRever);
                            batchTaskLists.add(batchCommandTaskDTO);
                        }

                        logger.info("本次批量下发--合并处理之后的涉及到的下发设备有:{}台", batchTaskLists.size());
                        CountDownLatch latch = new CountDownLatch(batchTaskLists.size());
                        Iterator var17 = batchTaskLists.iterator();

                        while(var17.hasNext()) {
                            batchCommandTaskDTO = (BatchCommandTaskDTO)var17.next();
                            String deviceUuid = batchCommandTaskDTO.getDeviceUuid();
                            NodeEntity nodeEntity = this.recommendTaskService.getTheNodeByUuid(deviceUuid);
                            if (null == nodeEntity) {
                                logger.info("根据设备uuid:{}查询设备信息为空", deviceUuid);
                            } else {
                                batchCommandTaskDTO.setModelNumber(nodeEntity.getModelNumber());
                                String id = "batch_push_device_" + batchCommandTaskDTO.getDeviceUuid();
                                if (ExtendedExecutor.containsKey(id)) {
                                    logger.warn(String.format("设备[%s]下发任务已经存在！任务不重复添加", nodeEntity.getModelNumber()));
                                    latch.countDown();
                                } else {
                                    this.batchPushCommand(latch, batchCommandTaskDTO, id);
                                }
                            }
                        }

                        try {
                            latch.await();
                        } catch (Exception var14) {
                            logger.error("批量下发命令行异常,异常原因:{}", var14.getMessage());
                            throw var14;
                        }

                        var17 = taskList.iterator();

                        while(var17.hasNext()) {
                            CommandTaskDTO commandTaskDTO = (CommandTaskDTO)var17.next();
                            taskEditableEntityList = this.commandTaskManager.getCommandTaskByTaskId(commandTaskDTO.getTaskId());
                            int pushStatusInTaskList = this.recommendTaskService.getPushStatusInTaskList(taskEditableEntityList);
                            int policyStatusByPushStatus = this.recommendTaskService.getPolicyStatusByPushStatus(pushStatusInTaskList);
                            this.recommendTaskService.updateTaskStatus(commandTaskDTO.getTaskId(), policyStatusByPushStatus);
                        }

                        long end = System.currentTimeMillis();
                        long consume = end - start;
                        logger.info("批量下发命令行结束...,结束时间【{}】,耗时【{}】毫秒", end, consume);
                        return 0;
                    }

                    CommandTaskDTO taskDTO = (CommandTaskDTO)var8.next();
                    commandList = taskDTO.getList();
                } while(CollectionUtils.isEmpty(commandList));

                Iterator var11 = commandList.iterator();

                while(var11.hasNext()) {
                    CommandTaskEditableEntity commandEntity = (CommandTaskEditableEntity)var11.next();
                    if (listMap.containsKey(commandEntity.getDeviceUuid())) {
                        List<CommandTaskEditableEntity> existCommands = (List)listMap.get(commandEntity.getDeviceUuid());
                        existCommands.add(commandEntity);
                    } else {
                        List<CommandTaskEditableEntity> newCommandTask = new ArrayList();
                        newCommandTask.add(commandEntity);
                        listMap.put(commandEntity.getDeviceUuid(), newCommandTask);
                    }
                }
            }
        }
    }

    @Transactional(
            rollbackFor = {Exception.class},
            propagation = Propagation.SUPPORTS
    )
    public void batchPushCommand(CountDownLatch latch, BatchCommandTaskDTO batchCommandTaskDTO, String id) {
        this.batchPushExecutor.execute(new ExtendedLatchRunnable(new ExecutorDto(id, "批量下发策略", "", new Date()), latch) {
            protected void start() throws InterruptedException, Exception {
                boolean revert = batchCommandTaskDTO.isRevert();
                PushTaskServiceImpl.this.recommendTaskService.updateCommandTaskPushOrRevertStatus(batchCommandTaskDTO.getList(), PushConstants.PUSH_INT_PUSH_QUEUED, revert);
                String singeId = "single_push_" + DateUtil.getTimeStamp();
                final CountDownLatch countLatch = new CountDownLatch(1);
                PushTaskServiceImpl.this.pushExecutor.execute(new ExtendedRunnable(new ExecutorDto(singeId, "", "", new Date())) {
                    protected void start() throws InterruptedException, Exception {
                        try {
                            long start = System.currentTimeMillis();
                            PushTaskServiceImpl.logger.info("开始下发设备:{},开始时间{}：", batchCommandTaskDTO.getModelNumber(), start);
                            PushTaskServiceImpl.this.pushService.pushCommandV2(batchCommandTaskDTO);
                            long end = System.currentTimeMillis();
                            long consume = end - start;
                            PushTaskServiceImpl.logger.info("开始下发设备:{},结束时间【{}】,耗时【{}】毫秒", new Object[]{batchCommandTaskDTO, end, consume});
                        } catch (Exception var10) {
                            PushTaskServiceImpl.logger.error("设备:{}批量下发命令行异常,异常原因:{}", batchCommandTaskDTO.getModelNumber(), var10.getMessage());
                            Iterator var2 = batchCommandTaskDTO.getTaskIds().iterator();

                            while(var2.hasNext()) {
                                Integer taskId = (Integer)var2.next();
                                PushTaskServiceImpl.this.taskService.updateTaskStatus(taskId, 19);
                            }

                            PushTaskServiceImpl.this.taskService.updateCommandTaskStatus(batchCommandTaskDTO.getList(), PushConstants.PUSH_INT_PUSH_RESULT_STATUS_ERROR);
                            throw var10;
                        } finally {
                            countLatch.countDown();
                        }

                    }
                });
                countLatch.await();
            }
        });
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    @PushTimeLockCheck
    public int addDeviceCommandTaskList(CommandTaskDTO commandTaskDTO, boolean doPush) {
        if (!doPush) {
            return 133;
        } else {
            List<CommandTaskEditableEntity> list = commandTaskDTO.getList();
            if (ObjectUtils.isEmpty(list)) {
                logger.info("下发命令行设备为0！不进行下发！");
                return 61;
            } else {
                Iterator var4 = list.iterator();

                while(var4.hasNext()) {
                    CommandTaskEditableEntity editableEntity = (CommandTaskEditableEntity)var4.next();
                    this.recommendTaskService.updateCommandTaskStatusById(editableEntity.getId(), PushConstants.PUSH_INT_PUSH_QUEUED);
                }

                final String id = "PTD_" + commandTaskDTO.getTaskId() + ((CommandTaskEditableEntity)commandTaskDTO.getList().get(0)).getId();
                if (ExtendedExecutor.containsKey(id)) {
                    logger.info("策略下发线程[" + id + "]已经存在！");
                    return 27;
                } else {
                    logger.info("开始下发任务：" + id);
                    this.pushExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "", "", new Date())) {
                        protected void start() throws InterruptedException, Exception {
                            try {
                                long start = System.currentTimeMillis();
                                PushTaskServiceImpl.logger.info("开始下发...线程id【{}】,开始时间【{}】", id, start);
                                PushTaskServiceImpl.this.pushService.pushCommandDevice(commandTaskDTO);
                                long end = System.currentTimeMillis();
                                long consume = end - start;
                                PushTaskServiceImpl.logger.info("下发结束...线程id【{}】,结束时间【{}】,耗时【{}】毫秒", new Object[]{id, end, consume});
                            } catch (Exception var7) {
                                PushTaskServiceImpl.logger.error(var7.getClass().toString());
                                PushTaskServiceImpl.this.taskService.updateTaskStatus(commandTaskDTO.getTaskId(), 19);
                                PushTaskServiceImpl.this.taskService.updateCommandTaskStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_RESULT_STATUS_ERROR);
                                throw var7;
                            }
                        }
                    });
                    return 0;
                }
            }
        }
    }

    public List<String> stopTaskList(List<String> taskList, Integer isRevert) {
        List<String> failedList = new ArrayList();
        Iterator var4 = taskList.iterator();

        while(true) {
            while(true) {
                while(var4.hasNext()) {
                    String taskId = (String)var4.next();
                    String id = "PT_" + taskId;
                    if (ExtendedExecutor.containsKey(id)) {
                        logger.info("停止任务:" + id);
                        List<CommandTaskEditableEntity> commandTaskEditableEntities = this.commandTaskManager.getCommandTaskByTaskId(Integer.parseInt(taskId));
                        if (CollectionUtils.isNotEmpty(commandTaskEditableEntities)) {
                            CommandTaskEditableEntity commandTaskEditableEntity = (CommandTaskEditableEntity)commandTaskEditableEntities.get(0);
                            Integer pushStatus = commandTaskEditableEntity.getPushStatus();
                            Integer revertStatus = commandTaskEditableEntity.getRevertStatus();
                            if (pushStatus != PushConstants.PUSH_INT_PUSH_QUEUED && isRevert == 0) {
                                failedList.add(taskId);
                                continue;
                            }

                            if (revertStatus != PushConstants.PUSH_INT_PUSH_QUEUED && isRevert == 1) {
                                failedList.add(taskId);
                                continue;
                            }
                        }

                        boolean stopped = ExtendedExecutor.stop(id, "stop");
                        if (!stopped) {
                            failedList.add(taskId);
                        } else if (ObjectUtils.isNotEmpty(scheduleLatch) && scheduleMap.containsKey(id)) {
                            logger.info("下发执行计划任务计数器减一，对应线程id：" + id);
                            scheduleLatch.countDown();
                            scheduleMap.remove(id);
                        }
                    } else {
                        ThreadPoolTaskExecutor threadPoolTaskExecutor = (ThreadPoolTaskExecutor)this.pushExecutor;
                        BlockingQueue<Runnable> queue = threadPoolTaskExecutor.getThreadPoolExecutor().getQueue();
                        boolean rc = queue.removeIf((runnable) -> {
                            ExtendedRunnable extendedRunnable = (ExtendedRunnable)runnable;
                            logger.info("队列中任务名称：" + extendedRunnable.getExecutorDto().toString() + "，要停止的线程名称" + id);
                            return id.equals(extendedRunnable.getExecutorDto().getId());
                        });
                        if (!rc) {
                            logger.info("停止任务失败：" + id);
                            failedList.add(taskId);
                        } else if (ObjectUtils.isNotEmpty(scheduleLatch) && scheduleMap.containsKey(id)) {
                            logger.info("下发执行计划任务计数器减一，对应线程id：" + id);
                            scheduleLatch.countDown();
                            scheduleMap.remove(id);
                        }
                    }
                }

                return failedList;
            }
        }
    }

    public List<Integer> stopAllTasks() {
        List<Integer> list = new LinkedList();
        return list;
    }

    public boolean checkTaskRunning(int taskId) {
        return false;
    }

    public int addGenerateCmdTask(CmdDTO cmdDTO) {
        final TaskDTO task = cmdDTO.getTask();
        String id = "CMD_" + String.valueOf(task.getId());
        if (ExtendedExecutor.containsKey(id)) {
            logger.warn(String.format("命令行生成任务(%s)已经存在！任务不重复添加", id));
            return 27;
        } else {
            this.commandlineExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "", "", new Date())) {
                protected void start() throws InterruptedException, Exception {
                    try {
                        PushTaskServiceImpl.logger.info("开始生成命令行...");
                        PushTaskServiceImpl.this.commandlineService.generate(cmdDTO);
                    } catch (Exception var2) {
                        PushTaskServiceImpl.logger.info("未知错误:", var2);
                        PushTaskServiceImpl.this.taskService.updateCommandTaskStatus(task.getId(), PushConstants.PUSH_INT_PUSH_GENERATING_ERROR);
                        throw var2;
                    }
                }
            });
            return 0;
        }
    }

    public int pushPeriod() {
        String id = "PUSH_PERIOD";
        if (ExtendedExecutor.containsKey(id)) {
            return 27;
        } else {
            this.pushScheduleExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "", "", new Date())) {
                protected void start() throws InterruptedException, Exception {
                    try {
                        PushTaskServiceImpl.logger.info("开始检查定时下发任务...");
                        PushTaskServiceImpl.this.checkScheduleAndPush();
                    } catch (Exception var2) {
                        PushTaskServiceImpl.logger.error("未知错误:", var2);
                        throw var2;
                    }
                }
            });
            return 0;
        }
    }

    public int newPolicyPush(NewPolicyPushVO vo) throws Exception {
        if (vo == null) {
            return 82;
        } else {
            logger.info("新建策略为" + JSONObject.toJSONString(vo));
            int rc = 0;
            if (vo.getIpType() == IpTypeEnum.IPV4.getCode()) {
                rc = InputValueUtils.checkIp(vo.getSrcIp());
            } else if (vo.getIpType() == IpTypeEnum.IPV6.getCode()) {
                rc = InputValueUtils.checkIpV6(vo.getSrcIp());
            } else if (vo.getUrlType() == UrlTypeEnum.IPV4.getCode()) {
                rc = InputValueUtils.checkIp(vo.getSrcIp());
            } else if (vo.getUrlType() == UrlTypeEnum.IPV6.getCode()) {
                rc = InputValueUtils.checkIpV6(vo.getSrcIp());
            }

            if (rc != 0 && rc != 73 && rc != 1) {
                return rc;
            } else {
                if (rc == 73 && vo.getIpType() == IpTypeEnum.IPV4.getCode()) {
                    vo.setSrcIp(InputValueUtils.autoCorrect(vo.getSrcIp()));
                }

                if (vo.getIpType() == IpTypeEnum.IPV4.getCode()) {
                    rc = InputValueUtils.checkIp(vo.getDstIp());
                } else if (vo.getIpType() == IpTypeEnum.IPV6.getCode()) {
                    rc = InputValueUtils.checkIpV6(vo.getDstIp());
                }

                if (rc != 0 && rc != 73 && rc != 1) {
                    return rc;
                } else {
                    if (rc == 73 && vo.getIpType() == IpTypeEnum.IPV4.getCode()) {
                        vo.setDstIp(InputValueUtils.autoCorrect(vo.getDstIp()));
                    }

                    String userName = vo.getUserName();
                    String theme = vo.getTheme();
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    String orderNumber = "A" + simpleDateFormat.format(date);
                    String deviceUuid = vo.getDeviceUuid();
                    String scenesUuid = vo.getScenesUuid();
                    if (!StringUtils.isAllBlank(new CharSequence[]{deviceUuid, scenesUuid}) && !StringUtils.isNoneBlank(new CharSequence[]{deviceUuid, scenesUuid})) {
                        List<DisposalScenesDTO> scenesDTOList = null;
                        if (StringUtils.isNotBlank(scenesUuid)) {
                            DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                            if (scenesEntity == null) {
                                logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                                return 84;
                            }

                            scenesDTOList = this.disposalScenesService.findByScenesUuid(scenesUuid);
                            if (CollectionUtils.isEmpty(scenesDTOList)) {
                                logger.error(String.format("场景UUID：%s 查询场景下设备信息不存在。", scenesUuid));
                                return 84;
                            }
                        }

                        if (StringUtils.isNotBlank(deviceUuid)) {
                            NodeEntity node = this.taskService.getTheNodeByUuid(deviceUuid);
                            if (node == null) {
                                logger.error(String.format("设备UUID：%s 查询设备信息为空。", deviceUuid));
                                return 84;
                            }
                        }

                        RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
                        BeanUtils.copyProperties(vo, recommendTaskEntity);
                        recommendTaskEntity.setSrcIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getSrcIpSystem()));
                        recommendTaskEntity.setDstIpSystem(TotemsStringUtils.trim2(recommendTaskEntity.getDstIpSystem()));
                        recommendTaskEntity.setOrderNumber(orderNumber);
                        Integer ipType = ObjectUtils.isNotEmpty(vo.getIpType()) ? vo.getIpType() : IpTypeEnum.IPV4.getCode();
                        Integer urlType = ObjectUtils.isNotEmpty(vo.getUrlType()) ? vo.getUrlType() : IpTypeEnum.IPV4.getCode();
                        recommendTaskEntity.setIpType(ipType);
                        recommendTaskEntity.setUrlType(urlType);
                        List<ServiceDTO> serviceDTOList = new ArrayList();
                        if (vo.getServiceList() != null && vo.getServiceList().size() != 0) {
                            logger.info("新建策略服务不为空" + JSONObject.toJSONString(vo.getServiceList()));
                            serviceDTOList = vo.getServiceList();
                            Iterator var36 = serviceDTOList.iterator();

                            while(var36.hasNext()) {
                                ServiceDTO service = (ServiceDTO)var36.next();
                                if (!AliStringUtils.isEmpty(service.getDstPorts())) {
                                    service.setDstPorts(InputValueUtils.autoCorrectPorts(service.getDstPorts()));
                                }
                            }

                            recommendTaskEntity.setServiceList(JSONObject.toJSONString(vo.getServiceList()));
                        } else {
                            logger.info("新建策略服务为空");
                            ServiceDTO serviceDTO = new ServiceDTO();
                            serviceDTO.setProtocol("0");
                            serviceDTO.setSrcPorts("any");
                            serviceDTO.setDstPorts("any");
                            serviceDTOList.add(serviceDTO);
                            recommendTaskEntity.setServiceList(JSONObject.toJSONString(serviceDTOList));
                        }

                        recommendTaskEntity.setCreateTime(date);
                        if (vo.getStartTime() != null) {
                            recommendTaskEntity.setStartTime(new Date(vo.getStartTime()));
                        }

                        if (vo.getEndTime() != null) {
                            recommendTaskEntity.setEndTime(new Date(vo.getEndTime()));
                        }

                        recommendTaskEntity.setTaskType(3);
                        UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(userName);
                        if(vo.getBranchLevel() == null || vo.getBranchLevel().equals("")){
                            if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
                                recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
                            } else {
                                recommendTaskEntity.setBranchLevel("00");
                            }
                        }else{
                            recommendTaskEntity.setBranchLevel(vo.getBranchLevel());
                        }

                        PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
                        additionalInfoEntity.setScenesUuid(scenesUuid);
                        additionalInfoEntity.setScenesDTOList(scenesDTOList);
                        additionalInfoEntity.setDeviceUuid(deviceUuid);
                        if (StringUtils.isNotBlank(deviceUuid)) {
                            if (vo.getSrcZone() != null) {
                                additionalInfoEntity.setSrcZone(vo.getSrcZone().equals("-1") ? null : vo.getSrcZone());
                            }

                            if (vo.getDstZone() != null) {
                                additionalInfoEntity.setDstZone(vo.getDstZone().equals("-1") ? null : vo.getDstZone());
                            }

                            additionalInfoEntity.setInDevItf(vo.getInDevIf());
                            additionalInfoEntity.setOutDevItf(vo.getOutDevIf());
                            additionalInfoEntity.setInDevItfAlias(vo.getInDevItfAlias());
                            additionalInfoEntity.setOutDevItfAlias(vo.getOutDevItfAlias());
                        }

                        additionalInfoEntity.setAction(vo.getAction());
                        recommendTaskEntity.setAdditionInfo(JSONObject.toJSONString(additionalInfoEntity));
                        recommendTaskEntity.setStatus(0);
                        Integer idleTimeout = vo.getIdleTimeout();
                        if (ObjectUtils.isNotEmpty(idleTimeout)) {
                            recommendTaskEntity.setIdleTimeout(vo.getIdleTimeout() * CommonConstants.HOUR_SECOND);
                        } else {
                            recommendTaskEntity.setIdleTimeout((Integer)null);
                        }

                        List<RecommendTaskEntity> list = new ArrayList();
                        list.add(recommendTaskEntity);
                        this.taskService.insertRecommendTaskList(list);
                        logger.info("策略下发新增任务" + JSONObject.toJSONString(recommendTaskEntity));
                        String message = String.format("新建安全策略%s成功", recommendTaskEntity.getTheme());
                        this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                        ActionEnum action = ActionEnum.PERMIT;
                        if (!vo.getAction().equalsIgnoreCase("PERMIT")) {
                            action = ActionEnum.DENY;
                        }

                        MoveSeatEnum moveSeat = MoveSeatEnum.FIRST;
                        List<Integer> pushTaskIdList = new ArrayList();
                        if (scenesDTOList != null && scenesDTOList.size() > 0) {
                            Iterator var23 = scenesDTOList.iterator();

                            while(var23.hasNext()) {
                                DisposalScenesDTO scenesDTO = (DisposalScenesDTO)var23.next();
                                boolean isVsys = false;
                                String rootDeviceUuid = "";
                                String vsysName = "";
                                NodeEntity node = null;
                                DeviceDataRO deviceData = null;
                                if (StringUtils.isNotBlank(scenesDTO.getDeviceUuid())) {
                                    node = this.taskService.getTheNodeByUuid(scenesDTO.getDeviceUuid());
                                    if (node == null) {
                                        logger.error(String.format("场景：%s 设备UUID：%s 查询设备信息为空。", scenesDTO.getName(), scenesDTO.getDeviceUuid()));
                                        return 84;
                                    }

                                    DeviceRO device = this.whaleManager.getDeviceByUuid(scenesDTO.getDeviceUuid());
                                    deviceData = (DeviceDataRO)device.getData().get(0);
                                    if (deviceData.getIsVsys() != null) {
                                        isVsys = deviceData.getIsVsys();
                                        rootDeviceUuid = deviceData.getRootDeviceUuid();
                                        vsysName = deviceData.getVsysName();
                                    }
                                }

                                String srcZone = null;
                                String dstZone = null;
                                if (StringUtils.isNotEmpty(scenesDTO.getSrcZoneUuid())) {
                                    srcZone = scenesDTO.getSrcZoneUuid().equals("-1") ? null : scenesDTO.getSrcZoneName();
                                }

                                if (StringUtils.isNotEmpty(scenesDTO.getDstZoneUuid())) {
                                    dstZone = scenesDTO.getDstZoneUuid().equals("-1") ? null : scenesDTO.getDstZoneName();
                                }

                                int pushTaskId = this.addTaskToNewTable(recommendTaskEntity, node, deviceData, vo, userInfoDTO, userName, theme, scenesDTO.getDeviceUuid(), action, isVsys, vsysName, srcZone, dstZone, scenesDTO.getSrcItf(), scenesDTO.getDstItf(), scenesDTO.getSrcItfAlias(), scenesDTO.getDstItfAlias(), moveSeat);
                                pushTaskIdList.add(pushTaskId);
                            }
                        }

                        if (StringUtils.isNotBlank(deviceUuid)) {
                            boolean isVsys = false;
                            String rootDeviceUuid = "";
                            String vsysName = "";
                            NodeEntity node = null;
                            DeviceDataRO deviceData = null;
                            if (StringUtils.isNotBlank(deviceUuid)) {
                                node = this.taskService.getTheNodeByUuid(deviceUuid);
                                if (node == null) {
                                    logger.error(String.format("设备UUID：%s 查询设备信息为空。", deviceUuid));
                                    return 84;
                                }

                                DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
                                deviceData = (DeviceDataRO)device.getData().get(0);
                                if (deviceData.getIsVsys() != null) {
                                    isVsys = deviceData.getIsVsys();
                                    rootDeviceUuid = deviceData.getRootDeviceUuid();
                                    vsysName = deviceData.getVsysName();
                                }
                            }

                            int pushTaskId = this.addTaskToNewTable(recommendTaskEntity, node, deviceData, vo, userInfoDTO, userName, theme, deviceUuid, action, isVsys, vsysName, additionalInfoEntity.getSrcZone(), additionalInfoEntity.getDstZone(), additionalInfoEntity.getInDevItf(), additionalInfoEntity.getOutDevItf(), additionalInfoEntity.getInDevItfAlias(), additionalInfoEntity.getOutDevItfAlias(), moveSeat);
                            pushTaskIdList.add(pushTaskId);
                        }

                        vo.setPushTaskId(pushTaskIdList);
                        vo.setTaskId(recommendTaskEntity.getId());
                        return 0;
                    } else {
                        return 84;
                    }
                }
            }
        }
    }

    public int newCustomizeCmd(NewPolicyPushVO vo) {
        if (vo == null) {
            return 82;
        } else {
            logger.info("新建策略为" + JSONObject.toJSONString(vo));
            String userName = vo.getUserName();
            String theme = vo.getTheme();
            if (StringUtils.isBlank(vo.getCommandLine())) {
                logger.error(String.format("自定义命令行：%s 为空。", theme));
                return 82;
            } else {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String orderNumber = "A" + simpleDateFormat.format(date);
                String deviceUuid = vo.getDeviceUuid();
                String scenesUuid = vo.getScenesUuid();
                if (!StringUtils.isAllBlank(new CharSequence[]{deviceUuid, scenesUuid}) && !StringUtils.isNoneBlank(new CharSequence[]{deviceUuid, scenesUuid})) {
                    PushAdditionalInfoEntity additionalInfoEntity = new PushAdditionalInfoEntity();
                    List<DisposalScenesDTO> scenesDTOList = null;
                    if (StringUtils.isNotBlank(scenesUuid)) {
                        DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(scenesUuid);
                        if (null == scenesEntity) {
                            logger.error(String.format("场景UUID：%s 查询场景不存在。", scenesUuid));
                            return 84;
                        }

                        scenesDTOList = this.disposalScenesService.findByScenesUuid(scenesUuid);
                        if (CollectionUtils.isEmpty(scenesDTOList)) {
                            logger.error(String.format("场景UUID：%s 查询场景下设备信息不存在。", scenesUuid));
                            return 84;
                        }

                        additionalInfoEntity.setScenesUuid(scenesUuid);
                        additionalInfoEntity.setScenesDTOList(scenesDTOList);
                    }

                    if (StringUtils.isNotBlank(deviceUuid)) {
                        NodeEntity node = this.taskService.getTheNodeByUuid(deviceUuid);
                        if (node == null) {
                            logger.error(String.format("设备UUID：%s 查询设备信息为空。", deviceUuid));
                            return 84;
                        }

                        additionalInfoEntity.setDeviceUuid(deviceUuid);
                    }

                    RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
                    BeanUtils.copyProperties(vo, recommendTaskEntity);
                    recommendTaskEntity.setOrderNumber(orderNumber);
                    recommendTaskEntity.setCreateTime(date);
                    recommendTaskEntity.setTaskType(16);
                    UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(userName);
                    if (userInfoDTO != null && StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
                        recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
                    } else {
                        recommendTaskEntity.setBranchLevel("00");
                    }

                    recommendTaskEntity.setStatus(0);
                    recommendTaskEntity.setAdditionInfo(JSONObject.toJSONString(additionalInfoEntity));
                    recommendTaskEntity.setSrcIp("0.0.0.0/0");
                    recommendTaskEntity.setDstIp("0.0.0.0/0");
                    List<RecommendTaskEntity> list = new ArrayList();
                    list.add(recommendTaskEntity);
                    this.taskService.insertRecommendTaskList(list);
                    logger.info("策略下发新增任务" + JSONObject.toJSONString(recommendTaskEntity));
                    String message = String.format("新建自定义命令行%s成功", recommendTaskEntity.getTheme());
                    this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                    List<Integer> pushTaskIdList = new ArrayList();
                    if (scenesDTOList != null && scenesDTOList.size() > 0) {
                        Iterator var16 = scenesDTOList.iterator();

                        while(var16.hasNext()) {
                            DisposalScenesDTO scenesDTO = (DisposalScenesDTO)var16.next();
                            int pushTaskId = this.addTaskToNewCustomizeCmd(recommendTaskEntity, userName, theme, scenesDTO.getDeviceUuid(), vo.getCommandLine());
                            pushTaskIdList.add(pushTaskId);
                        }
                    }

                    if (StringUtils.isNotBlank(deviceUuid)) {
                        int pushTaskId = this.addTaskToNewCustomizeCmd(recommendTaskEntity, userName, theme, deviceUuid, vo.getCommandLine());
                        pushTaskIdList.add(pushTaskId);
                    }

                    vo.setPushTaskId(pushTaskIdList);
                    vo.setTaskId(recommendTaskEntity.getId());
                    return 0;
                } else {
                    return 84;
                }
            }
        }
    }

    public int addTaskToNewTable(RecommendTaskEntity recommendTaskEntity, NodeEntity node, DeviceDataRO deviceData, NewPolicyPushVO vo, UserInfoDTO userInfoDTO, String userName, String theme, String deviceUuid, ActionEnum action, boolean isVsys, String vsysName, String srcZone, String dstZone, String inItf, String outItf, String inItfAlias, String outItfAlias, MoveSeatEnum moveSeat) {
        CommandTaskEditableEntity entity = EntityUtils.createCommandTask(3, recommendTaskEntity.getId(), userName, theme, deviceUuid);
        entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeString = recommendTaskEntity.getStartTime() == null ? null : sdf.format(recommendTaskEntity.getStartTime());
        String endTimeString = recommendTaskEntity.getEndTime() == null ? null : sdf.format(recommendTaskEntity.getEndTime());
        this.commandTaskManager.addCommandEditableEntityTask(entity);
        PolicyEnum type = null;
        String modelNumber = node.getModelNumber();
        if (AliStringUtils.isEmpty(modelNumber) || !DeviceTypeEnum.ROUTER.name().equalsIgnoreCase(deviceData.getDeviceType()) || !modelNumber.equals("Cisco IOS") && !modelNumber.equals("Cisco NX-OS") && !modelNumber.equals("Ruijie RGOS")) {
            type = PolicyEnum.SECURITY;
        } else {
            type = PolicyEnum.ACL;
        }

        CmdDTO cmdDTO = EntityUtils.createCmdDTO(type, entity.getId(), entity.getTaskId(), deviceUuid, theme, userName, recommendTaskEntity.getSrcIp(), recommendTaskEntity.getDstIp(), (String)null, (String)null, vo.getServiceList(), (List)null, srcZone, dstZone, inItf, outItf, inItfAlias, outItfAlias, startTimeString, endTimeString, recommendTaskEntity.getDescription(), action, isVsys, vsysName, moveSeat, (String)null, (String)null, recommendTaskEntity.getIdleTimeout(), recommendTaskEntity.getSrcIpSystem(), recommendTaskEntity.getDstIpSystem(), recommendTaskEntity.getIpType(), (String)null, (Integer)null, (String)null, (String)null, (PolicyRecommendSecurityPolicyVO)null, (String)null);
        TaskDTO task = cmdDTO.getTask();
        task.setMergeCheck(recommendTaskEntity.getMergeCheck());
        task.setRangeFilter(recommendTaskEntity.getRangeFilter());
        task.setBeforeConflict(recommendTaskEntity.getBeforeConflict());
        task.setTaskTypeEnum(TaskTypeEnum.SECURITY_TYPE);
        cmdDTO.getDevice().setNodeEntity(node);
        cmdDTO.getPolicy().setPolicyUserNames(vo.getPolicyUserNames());
        cmdDTO.getPolicy().setUrlType(recommendTaskEntity.getUrlType());
        cmdDTO.getPolicy().setPolicyApplications(vo.getPolicyApplications());
        DeviceModelNumberEnum deviceModelNumberEnum = DeviceModelNumberEnum.fromString(modelNumber);
        if (DeviceModelNumberEnum.isRangeHillStoneCode(deviceModelNumberEnum.getCode())) {
            String paramValue = this.advancedSettingService.getParamValue("hillstone_rollback_type");
            if (StringUtils.isNotEmpty(paramValue)) {
                if (paramValue.equals("0")) {
                    cmdDTO.getSetting().setRollbackType(true);
                } else {
                    cmdDTO.getSetting().setRollbackType(false);
                }
            }
        }

        this.cmdTaskService.getRuleMatchFlow2Generate(cmdDTO, userInfoDTO);
        return entity.getId();
    }

    private int addTaskToNewCustomizeCmd(RecommendTaskEntity recommendTaskEntity, String userName, String theme, String deviceUuid, String commandLine) {
        CommandTaskEditableEntity entity = EntityUtils.createCommandTask(16, recommendTaskEntity.getId(), userName, theme, deviceUuid);
        entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
        entity.setCommandline(commandLine);
        entity.setStatus(PushStatusEnum.PUSH_NOT_START.getCode());
        this.commandTaskManager.addCommandEditableEntityTask(entity);
        return entity.getId();
    }

    public void startCommandTaskEditableEmail(List<CommandTaskEditableEntity> taskList) {
        String toAddress = ((CommandTaskEditableEntity)taskList.get(0)).getReceiverEmail();
        Date pushSchedule = ((CommandTaskEditableEntity)taskList.get(0)).getPushSchedule();
        Date pushTime = ((CommandTaskEditableEntity)taskList.get(0)).getPushTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subject = "策略下发-计划时间点：" + sdf.format(pushSchedule) + "-任务总数：" + taskList.size();
        List<CommandTaskEditableEntity> failList = (List)taskList.stream().filter((task) -> {
            return 3 == task.getPushStatus();
        }).collect(Collectors.toList());
        List<CommandTaskEditableEntity> successList = (List)taskList.stream().filter((task) -> {
            return 2 == task.getPushStatus();
        }).collect(Collectors.toList());
        String content = "执行开始时间：" + sdf.format(pushSchedule) + "~执行结束时间：" + sdf.format(pushTime) + "<br/>成功：<font color=\"green\">" + (null == successList ? 0 : successList.size()) + "个</font> ；失败：<font color=\"red\">" + (null == failList ? 0 : failList.size()) + "个</font>";

        try {
            if (StringUtils.isNotBlank(toAddress)) {
                logger.info("开始发送邮件，subject:{},toAddress:{}", subject, toAddress);
                MailServerConfDTO emialDTO = this.systemParamMapper.findEmailParam("mailServer");
                if (emialDTO != null) {
                    CustomMailTool.sendEmail(emialDTO, toAddress, subject, content, (String[])null);
                } else {
                    logger.error("查询系统邮箱配置，返回空");
                }
            }
        } catch (Exception var11) {
            logger.error("任务下发时，发送邮件失败", var11);
        }

    }

    private List<CommandTaskEditableEntity> getSendEmailList(Date pushSchedule) {
        Map<String, Object> cond = new HashMap();
        cond.put("pushSchedule", pushSchedule);
        List<CommandTaskEditableEntity> taskEditableEntityList = this.commandTaskEditableMapper.selectAllPushList(cond);
        List<CommandTaskEditableEntity> successList = (List)taskEditableEntityList.stream().filter((task) -> {
            return 2 == task.getPushStatus();
        }).collect(Collectors.toList());
        List<CommandTaskEditableEntity> failList = (List)taskEditableEntityList.stream().filter((task) -> {
            return 3 == task.getPushStatus();
        }).collect(Collectors.toList());
        return taskEditableEntityList.size() == successList.size() + failList.size() ? taskEditableEntityList : null;
    }

    @Transactional(
            propagation = Propagation.SUPPORTS
    )
    public void checkScheduleAndPush() {
        while(true) {
            List<CommandTaskEditableEntity> list = this.commandTaskManager.getScheduledCommand();
            if (list != null && list.size() != 0) {
                Map<Integer, CommandTaskDTO> idTaskMap = new LinkedHashMap();
                Iterator var3 = list.iterator();

                while(var3.hasNext()) {
                    CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var3.next();
                    Integer taskId = entity.getTaskId();
                    CommandTaskDTO dto;
                    if (idTaskMap.containsKey(taskId)) {
                        dto = (CommandTaskDTO)idTaskMap.get(taskId);
                        List<CommandTaskEditableEntity> taskList = dto.getList();
                        taskList.add(entity);
                    } else {
                        dto = new CommandTaskDTO();
                        dto.setRevert(false);
                        List<CommandTaskEditableEntity> taskList = new ArrayList();
                        taskList.add(entity);
                        dto.setList(taskList);
                        dto.setTaskId(taskId);
                        dto.setTheme(entity.getTheme());
                        idTaskMap.put(taskId, dto);
                    }
                }

                List<CommandTaskDTO> commandTaskList = new ArrayList(idTaskMap.values());
                this.pushTaskServiceImpl.addCommandTaskListForSchedule(commandTaskList, true);

                try {
                    logger.info("等待工单处理...");
                    Thread.sleep((long)this.WAIT_TIME);
                } catch (Exception var8) {
                    logger.error("等待异常！", var8);
                }
            } else {
                try {
                    logger.info("等待...");
                    Thread.sleep((long)this.WAIT_TIME);
                } catch (Exception var9) {
                    logger.error("等待异常！", var9);
                }
            }
        }
    }

    @PushTimeLockCheck
    public void addCommandTaskListForSchedule(List<CommandTaskDTO> taskList, boolean doPush) {
        if (!doPush) {
            logger.info("当前时间不允许下发！");
        } else {
            scheduleLatch = new CountDownLatch(taskList.size());
            Iterator var3 = taskList.iterator();

            while(var3.hasNext()) {
                final CommandTaskDTO commandTaskDTO = (CommandTaskDTO)var3.next();
                boolean revert = commandTaskDTO.isRevert();
                if (revert) {
                    this.recommendTaskService.updateCommandTaskRevertStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_QUEUED);
                } else {
                    this.recommendTaskService.updateCommandTaskPushStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_QUEUED);
                }

                final String id = "PT_" + commandTaskDTO.getTaskId();
                scheduleMap.put(id, id);
                if (ExtendedExecutor.containsKey(id)) {
                    logger.info("策略下发线程[" + id + "]已经存在！");
                    scheduleLatch.countDown();
                    scheduleMap.remove(id);
                } else {
                    logger.info("开始下发任务：" + id);
                    this.pushExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "", "", new Date())) {
                        protected void start() throws InterruptedException, Exception {
                            try {
                                long start = System.currentTimeMillis();
                                PushTaskServiceImpl.logger.info("开始下发...线程id【{}】,开始时间【{}】", id, start);
                                PushTaskServiceImpl.this.pushService.pushCommand(commandTaskDTO);
                                long end = System.currentTimeMillis();
                                long consume = end - start;
                                PushTaskServiceImpl.logger.info("下发结束...线程id【{}】,结束时间【{}】,耗时【{}】毫秒", new Object[]{id, end, consume});
                                PushTaskServiceImpl.scheduleLatch.countDown();
                                PushTaskServiceImpl.scheduleMap.remove(id);
                            } catch (Exception var7) {
                                PushTaskServiceImpl.logger.info(var7.getClass().toString());
                                PushTaskServiceImpl.this.taskService.updateTaskStatus(commandTaskDTO.getTaskId(), 19);
                                PushTaskServiceImpl.this.taskService.updateCommandTaskStatus(commandTaskDTO.getTaskId(), PushConstants.PUSH_INT_PUSH_RESULT_STATUS_ERROR);
                                throw var7;
                            }
                        }
                    });
                }
            }

            try {
                scheduleLatch.await();
            } catch (Exception var11) {
                logger.error(String.format("策略下发任务异常"), var11);
            }

            List<Integer> taskIds = (List)taskList.stream().map((taskx) -> {
                return taskx.getTaskId();
            }).collect(Collectors.toList());
            Map<String, Object> cond = new HashMap();
            cond.put("enableEmail", "true");
            List<CommandTaskEditableEntity> taskEditableEntityList = this.commandTaskEditableMapper.selectAllPushList(cond);
            List<CommandTaskEditableEntity> taskEditableEntityListNew = new ArrayList();
            Iterator var7 = taskIds.iterator();

            while(var7.hasNext()) {
                Integer id = (Integer)var7.next();
                Iterator var9 = taskEditableEntityList.iterator();

                while(var9.hasNext()) {
                    CommandTaskEditableEntity task = (CommandTaskEditableEntity)var9.next();
                    if (id.equals(task.getTaskId())) {
                        taskEditableEntityListNew.add(task);
                    }
                }
            }

            if (null != taskEditableEntityListNew) {
                List<CommandTaskEditableEntity> taskEditableEntityListNewMetoo = (List)taskEditableEntityListNew.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> {
                    return new TreeSet<>(Comparator.comparing((task) -> {
                        return task.getPushSchedule();
                    }));
                }), ArrayList::new));
                List<Date> pushScheduleList = (List)taskEditableEntityListNew.stream().map((taskx) -> {
                    return taskx.getPushSchedule();
                }).collect(Collectors.toList());
                if (null != pushScheduleList) {
                    Iterator var18 = pushScheduleList.iterator();

                    while(var18.hasNext()) {
                        Date pushSchedule = (Date)var18.next();
                        List<CommandTaskEditableEntity> sendEmailList = this.getSendEmailList(pushSchedule);
                        if (null != sendEmailList) {
                            this.startCommandTaskEditableEmail(sendEmailList);
                        }
                    }
                }
            }
        }

    }

    @PushTimeLockCheck
    public void addCommandTaskListForScheduleV2(List<CommandTaskDTO> taskList, boolean doPush) throws Exception {
        if (!doPush) {
            logger.info("当前时间不允许下发！");
        } else {
            long start = System.currentTimeMillis();
            logger.info("开始批量下发命令行...,开始时间【{}】", start);
            List<BatchCommandTaskDTO> batchTaskLists = new ArrayList();
            boolean isRever = ((CommandTaskDTO)taskList.get(0)).isRevert();
            Map<String, List<CommandTaskEditableEntity>> listMap = new HashMap();
            Iterator var8 = taskList.iterator();

            while(true) {
                List commandList;
                List pushScheduleList;
                do {
                    if (!var8.hasNext()) {
                        var8 = listMap.keySet().iterator();

                        BatchCommandTaskDTO batchCommandTaskDTO;
                        List taskEditableEntityList;
                        while(var8.hasNext()) {
                            String deviceUuid = (String)var8.next();
                            batchCommandTaskDTO = new BatchCommandTaskDTO();
                            batchCommandTaskDTO.setDeviceUuid(deviceUuid);
                            batchCommandTaskDTO.setList((List)listMap.get(deviceUuid));
                            taskEditableEntityList = (listMap.get(deviceUuid)).stream().map(CommandTaskEditableEntity::getTaskId).distinct().collect(Collectors.toList());
                            batchCommandTaskDTO.setTaskIds(taskEditableEntityList);
                            batchCommandTaskDTO.setRevert(isRever);
                            batchTaskLists.add(batchCommandTaskDTO);
                        }

                        logger.info("本次批量下发--合并处理之后的涉及到的下发设备有:{}台", batchTaskLists.size());
                        CountDownLatch latch = new CountDownLatch(batchTaskLists.size());
                        Iterator var20 = batchTaskLists.iterator();

                        while(var20.hasNext()) {
                            batchCommandTaskDTO = (BatchCommandTaskDTO)var20.next();
                            String deviceUuid = batchCommandTaskDTO.getDeviceUuid();
                            NodeEntity nodeEntity = this.recommendTaskService.getTheNodeByUuid(deviceUuid);
                            if (null == nodeEntity) {
                                logger.info("根据设备uuid:{}查询设备信息为空", deviceUuid);
                            } else {
                                batchCommandTaskDTO.setModelNumber(nodeEntity.getModelNumber());
                                String id = "PT_" + batchCommandTaskDTO.getDeviceUuid();
                                scheduleMap.put(id, id);
                                if (ExtendedExecutor.containsKey(id)) {
                                    logger.warn(String.format("设备[%s]下发任务已经存在！任务不重复添加", nodeEntity.getModelNumber()));
                                    scheduleMap.remove(id);
                                    latch.countDown();
                                } else {
                                    this.batchPushCommand(latch, batchCommandTaskDTO, id);
                                }
                            }
                        }

                        try {
                            latch.await();
                        } catch (Exception var17) {
                            logger.error("批量下发命令行异常,异常原因:{}", var17.getMessage());
                            throw var17;
                        }

                        var20 = taskList.iterator();

                        while(var20.hasNext()) {
                            CommandTaskDTO commandTaskDTO = (CommandTaskDTO)var20.next();
                            taskEditableEntityList = this.commandTaskManager.getCommandTaskByTaskId(commandTaskDTO.getTaskId());
                            int pushStatusInTaskList = this.recommendTaskService.getPushStatusInTaskList(taskEditableEntityList);
                            int policyStatusByPushStatus = this.recommendTaskService.getPolicyStatusByPushStatus(pushStatusInTaskList);
                            this.recommendTaskService.updateTaskStatus(commandTaskDTO.getTaskId(), policyStatusByPushStatus);
                        }

                        List<Integer> taskIds = (List)taskList.stream().map((taskx) -> {
                            return taskx.getTaskId();
                        }).collect(Collectors.toList());
                        Map<String, Object> cond = new HashMap();
                        cond.put("enableEmail", "true");
                        taskEditableEntityList = this.commandTaskEditableMapper.selectAllPushList(cond);
                        List<CommandTaskEditableEntity> taskEditableEntityListNew = new ArrayList();
                        Iterator var34 = taskIds.iterator();

                        while(var34.hasNext()) {
                            Integer id = (Integer)var34.next();
                            Iterator var15 = taskEditableEntityList.iterator();

                            while(var15.hasNext()) {
                                CommandTaskEditableEntity task = (CommandTaskEditableEntity)var15.next();
                                if (id.equals(task.getTaskId())) {
                                    taskEditableEntityListNew.add(task);
                                }
                            }
                        }

                        if (null != taskEditableEntityListNew) {
                            List<CommandTaskEditableEntity> taskEditableEntityListNewMetoo =
                                    taskEditableEntityListNew.stream()
                                            .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> {
                                return new TreeSet<>(Comparator.comparing((task) -> {
                                    return task.getPushSchedule();
                                }));
                            }), ArrayList::new));
                            pushScheduleList = (List)taskEditableEntityListNew.stream().map((taskx) -> {
                                return taskx.getPushSchedule();
                            }).collect(Collectors.toList());
                            if (null != pushScheduleList) {
                                Iterator var35 = pushScheduleList.iterator();

                                while(var35.hasNext()) {
                                    Date pushSchedule = (Date)var35.next();
                                    List<CommandTaskEditableEntity> sendEmailList = this.getSendEmailList(pushSchedule);
                                    if (null != sendEmailList) {
                                        this.startCommandTaskEditableEmail(sendEmailList);
                                    }
                                }
                            }
                        }

                        long end = System.currentTimeMillis();
                        long consume = end - start;
                        logger.info("批量下发命令行结束...,结束时间【{}】,耗时【{}】毫秒", end, consume);
                        return;
                    }

                    CommandTaskDTO taskDTO = (CommandTaskDTO)var8.next();
                    commandList = taskDTO.getList();
                } while(CollectionUtils.isEmpty(commandList));

                Iterator var11 = commandList.iterator();

                while(var11.hasNext()) {
                    CommandTaskEditableEntity commandEntity = (CommandTaskEditableEntity)var11.next();
                    if (listMap.containsKey(commandEntity.getDeviceUuid())) {
                        pushScheduleList = (List)listMap.get(commandEntity.getDeviceUuid());
                        pushScheduleList.add(commandEntity);
                    } else {
                        List<CommandTaskEditableEntity> newCommandTask = new ArrayList();
                        newCommandTask.add(commandEntity);
                        listMap.put(commandEntity.getDeviceUuid(), newCommandTask);
                    }
                }
            }
        }
    }

    @PostConstruct
    public void initPushTaskStatus() {
        logger.info("**********重启push重置策略下发状态方法执行开始**********");
        List<CommandTaskEditableEntity> list = this.commandTaskManager.getExecuteTask();
        if (ObjectUtils.isEmpty(list)) {
            logger.info("**********策略下发没有执行中状态数据**********");
        } else {
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                CommandTaskEditableEntity task = (CommandTaskEditableEntity)var2.next();
                if (1 == task.getPushStatus()) {
                    this.recommendTaskService.updateCommandTaskStatusById(task.getId(), 3);
                } else if (PushConstants.PUSH_INT_PUSH_QUEUED == task.getPushStatus()) {
                    this.recommendTaskService.updateCommandTaskStatusById(task.getId(), 0);
                } else {
                    if (1 == task.getRevertStatus()) {
                        this.recommendTaskService.updateCommandTaskRevertStatusById(task.getId(), 3);
                    }

                    if (PushConstants.PUSH_INT_PUSH_QUEUED == task.getRevertStatus()) {
                        this.recommendTaskService.updateCommandTaskRevertStatusById(task.getId(), 0);
                    }
                }
            }

            logger.info("**********重启push重置策略下发状态方法执行结束**********");
        }
    }
}
