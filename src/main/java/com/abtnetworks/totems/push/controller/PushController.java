package com.abtnetworks.totems.push.controller;

import com.abtnetworks.data.totems.log.client.LogClientSimple;
import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.BaseController;
import com.abtnetworks.totems.common.config.VmwareInterfaceStatusConfig;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.constants.ReturnCode;
import com.abtnetworks.totems.common.lang.TotemsStringUtils;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.StringUtils;
import com.abtnetworks.totems.push.dao.mysql.PushPwdStrategyMapper;
import com.abtnetworks.totems.push.dto.CommandTaskDTO;
import com.abtnetworks.totems.push.dto.PushStatus;
import com.abtnetworks.totems.push.entity.PushPwdStrategyEntity;
import com.abtnetworks.totems.push.entity.PushTaskEntity;
import com.abtnetworks.totems.push.manager.PushTaskManager;
import com.abtnetworks.totems.push.service.PushService;
import com.abtnetworks.totems.push.service.task.GlobalPushTaskService;
import com.abtnetworks.totems.push.service.task.PushTaskService;
import com.abtnetworks.totems.push.vo.NewPolicyPushVO;
import com.abtnetworks.totems.push.vo.PushPwdStrategyVO;
import com.abtnetworks.totems.push.vo.PushStatusVO;
import com.abtnetworks.totems.push.vo.PushTaskPageVO;
import com.abtnetworks.totems.push.vo.PushTaskVO;
import com.abtnetworks.totems.recommend.dao.mysql.CommandTaskEdiableMapper;
import com.abtnetworks.totems.recommend.dto.push.TaskStatusBranchLevelsDTO;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(
        tags = {"策略下发列表"}
)
@RestController
@RequestMapping({"/task/"})
public class PushController extends BaseController {
    private static Logger logger = Logger.getLogger(PushController.class);
    private static final String ORDER_SEPERATOR = ",";
    @Autowired
    private PushTaskService pushTaskService;
    @Autowired
    private RecommendTaskManager recommendTaskService;
    @Autowired
    private PushTaskManager pushTaskManager;
    @Autowired
    private CommandTaskManager commandTaskManager;
    @Autowired
    private LogClientSimple logClientSimple;
    @Autowired
    CommandTaskEdiableMapper commandTaskEdiableMapper;
    @Resource
    RemoteBranchService remoteBranchService;
    @Autowired
    private PushService pushService;
    @Autowired
    VmwareInterfaceStatusConfig vmwareInterfaceStatusConfig;
    @Autowired
    private PushPwdStrategyMapper pushPwdStrategyMapper;
    @Autowired
    GlobalPushTaskService globalPushTaskService;

    public PushController() {
    }

    @ApiOperation("策略下发任务列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "orderNo",
            value = "工单号",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "type",
            value = "状态",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "status",
            value = "类型",
            required = false,
            dataType = "String"
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
    @PostMapping({"pushtasklist_old"})
    public JSONObject list(String orderNo, String type, String status, int page, int psize) {
        PushTaskPageVO pageVO = this.pushTaskManager.getPushTaskList(orderNo, type, status, page, psize);
        String jsonObjectString = JSONObject.toJSONString(pageVO);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation(
            value = "新建策略",
            httpMethod = "POST",
            notes = "新建策略，根据策略参数生成建议策略，并生成命令行",
            nickname = "鲁薇"
    )
    @RequestMapping(
            value = {"/new-policy-push"},
            method = {RequestMethod.POST}
    )
    public JSONObject newPolicyPush(@ApiParam(name = "newPolicyPushVO",value = "新建策略",required = true) @RequestBody NewPolicyPushVO newPolicyPushVO, Authentication auth) throws Exception {
        newPolicyPushVO.setUserName(auth.getName());
        int rc = this.pushTaskService.newPolicyPush(newPolicyPushVO);
        JSONObject rs = this.getReturnJSON(rc);
        rs.put("taskId", newPolicyPushVO.getTaskId());
        rs.put("pushTaskId", newPolicyPushVO.getPushTaskId());
        return rs;
    }

    @ApiOperation("策略检查添加策略下发任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "tasks",
            value = "添加策略下发任务",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"addpushtasks"})
    public JSONObject addPushTask(String tasks, String branchLevel, Authentication auth) {
        logger.info("添加策略下发任务：" + tasks);
        JSONArray jsonArray = JSONArray.parseArray(tasks);
        if (jsonArray != null && jsonArray.size() != 0) {
            List<PushTaskEntity> list = this.parseTaskEntity(tasks);
            UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(auth.getName());
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
                PushTaskEntity entity = (PushTaskEntity)var6.next();
                if(entity.getUserName() == null || entity.getUserName().equals("")){
                    entity.setUserName(auth.getName());
                }
            }
            List<CommandTaskEditableEntity> commandTaskEntityList = null;
            if(branchLevel == null || branchLevel.equals("")){
                commandTaskEntityList = this.processTaskList(list, userInfoDTO);
            }else{
               commandTaskEntityList = this.processTaskList(list, branchLevel);
            }

            CommandTaskEditableEntity entity;
            for(Iterator var10 = commandTaskEntityList.iterator(); var10.hasNext(); this.recommendTaskService.addCommandTaskEditableEntity(entity)) {
                entity = (CommandTaskEditableEntity)var10.next();
                if(branchLevel == null || branchLevel.equals("")){
                    if (userInfoDTO != null) {
                        entity.setBranchLevel(userInfoDTO.getBranchLevel());
                    } else {
                        entity.setBranchLevel("00");
                    }
                }else{
                    entity.setBranchLevel("00");
                }
            }
            return this.getReturnJSON(0);
        } else {
            logger.error("解析策略下发任务列表出错！\n" + tasks);
            return this.getReturnJSON(101);
        }
    }

    @ApiOperation("批量删除任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"delpushtasks"})
    public JSONObject deletePushTask(@RequestParam String ids) {
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List list = null;

            try {
                list = StringUtils.parseIntArrayList(ids);
            } catch (Exception var6) {
                logger.error("解析任务id列表出错", var6);
            }

            if (list == null) {
                return this.getReturnJSON(103);
            } else {
                Iterator var4 = list.iterator();

                while(var4.hasNext()) {
                    int id = (Integer)var4.next();
                    this.pushTaskManager.deletePushTask(id);
                }

                return this.getReturnJSON(0);
            }
        } else {
            return this.getReturnJSON(101);
        }
    }

    @ApiOperation("策略下发任务列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "orderNo",
            value = "工单号",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "type",
            value = "状态",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "status",
            value = "类型",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "pushStatus",
            value = "下发结果",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "revertStatus",
            value = "回滚结果",
            required = false,
            dataType = "String"
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
    @PostMapping({"pushtasklist"})
    public JSONObject getlist(String taskId, String orderNo, String type, String status, String pushStatus, String revertStatus, int page, int psize, String userName, Authentication authentication) {
        String branchLevel;
        if (org.apache.commons.lang3.StringUtils.isBlank(userName)) {
            branchLevel = this.remoteBranchService.likeBranch(authentication.getName());
        } else {
            branchLevel = this.remoteBranchService.likeBranch(userName);
        }

        PageInfo<PushTaskVO> pageVO = this.recommendTaskService.getPushTaskList(taskId, orderNo, type, status, pushStatus, revertStatus, page, psize, userName, branchLevel);
        String jsonObjectString = JSONObject.toJSONString(pageVO);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("批量开始下发任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"startGlobalPushTask"})
    public JSONObject startGlobalPushTask(@RequestParam String ids, String isRevert) {
        if (!this.vmwareInterfaceStatusConfig.isVmInterfaceAvailable()) {
            return this.startCommandTask(ids, isRevert);
        } else {
            List idList = null;

            try {
                if (StringUtils.isEmpty(ids)) {
                    logger.error("开始策略仿真任务为空！");
                    return this.getReturnJSON(102);
                }

                ids = String.format("[%s]", ids);
                idList = StringUtils.parseIntArrayList(ids);
            } catch (Exception var14) {
                logger.error("解析任务列表出错！ids=" + ids, var14);
                return this.getReturnJSON(255);
            }

            boolean revert = false;
            if (isRevert.equalsIgnoreCase("true")) {
                revert = true;
            }

            StringBuilder errMsg = new StringBuilder();
            List<String> themeList = new ArrayList();
            List<Integer> weTaskIds = new ArrayList();
            List<CommandTaskDTO> taskDTOList = this.globalPushTaskService.getCommandTaskDTOListByTaskid(idList, revert, errMsg, themeList, weTaskIds);
            int wePushTaskCnt = 0;
            if (!CollectionUtils.isEmpty(weTaskIds)) {
                wePushTaskCnt = this.globalPushTaskService.getWePushTaskListByTaskId(weTaskIds);
            }

            if (taskDTOList.size() == 0 && wePushTaskCnt == 0) {
                logger.error("没有可开始的策略下发任务！");
                return this.getReturnJSON(255, errMsg.toString());
            } else {
                String message = String.format("工单：%s ，开始%s", org.apache.commons.lang3.StringUtils.join(themeList, ","), revert ? "回滚" : "下发");
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                if (!CollectionUtils.isEmpty(taskDTOList)) {
                    logger.info(String.format("%d个任务开始物理策略下发", taskDTOList.size()));
                    boolean var11 = false;

                    int returnCode;
                    try {
                        returnCode = this.pushTaskService.addCommandTaskList(taskDTOList, true);
                    } catch (Exception var13) {
                        logger.error("批量下发报错,报错原因:{}", var13);
                        return this.getReturnJSON(255, ReturnCode.getMsg(104));
                    }

                    if (133 == returnCode) {
                        logger.info("当前时间不允许下发！");
                        errMsg.append("当前时间不允许下发！");
                        return this.getReturnJSON(255, errMsg.toString());
                    }
                }

                if (wePushTaskCnt > 0) {
                    logger.info(String.format("%d个任务开始云策略下发", wePushTaskCnt));
                    this.globalPushTaskService.pushWeTask(weTaskIds, revert);
                }

                logger.info(errMsg);
                if (revert) {
                    errMsg.insert(0, String.format("开始回滚,%d个物理策略，%d个云策略", taskDTOList.size(), wePushTaskCnt));
                } else {
                    errMsg.insert(0, String.format("开始下发,%d个物理策略，%d个云策略", taskDTOList.size(), wePushTaskCnt));
                }

                return this.getReturnJSON(0, errMsg.toString());
            }
        }
    }

    @ApiOperation("批量开始下发任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"startpushtasks"})
    public JSONObject startCommandTask(@RequestParam String ids, String isRevert) {
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            boolean revert = false;
            if (isRevert.equalsIgnoreCase("true")) {
                revert = true;
            }

            List idList = null;

            try {
                idList = StringUtils.parseIntArrayList(ids);
            } catch (Exception var17) {
                logger.error("解析任务列表出错！", var17);
            }

            StringBuilder errMsg = new StringBuilder();
            List<String> themeList = new ArrayList();
            List<CommandTaskDTO> taskDTOList = new ArrayList();
            Iterator var9 = idList.iterator();

            while(true) {
                while(var9.hasNext()) {
                    Integer id = (Integer)var9.next();
                    logger.info(String.format("获取任务(%d)", id));
                    List<CommandTaskEditableEntity> taskEntityList = this.commandTaskManager.getCommandTaskByTaskId(id);
                    if (taskEntityList.size() == 0) {
                        logger.error(String.format("获取任务(%d)失败，任务下没有命令行数据...", id));
                    } else {
                        boolean ignore = false;
                        Iterator var13 = taskEntityList.iterator();

                        while(var13.hasNext()) {
                            CommandTaskEditableEntity taskEntity = (CommandTaskEditableEntity)var13.next();
                            String command = taskEntity.getCommandline();
                            if (org.apache.commons.lang3.StringUtils.isNotBlank(command) && command.startsWith("无法生成该设备的命令行")) {
                                errMsg.append(String.format("[%s]开始下发失败，存在未生成命令行的设备！", taskEntity.getTheme()));
                                ignore = true;
                                break;
                            }
                        }

                        if (!ignore) {
                            CommandTaskDTO taskDTO = new CommandTaskDTO();
                            taskDTO.setList(taskEntityList);
                            taskDTO.setRevert(revert);
                            RecommendTaskEntity taskEntity = this.recommendTaskService.getRecommendTaskByTaskId(id);
                            taskDTO.setTaskId(taskEntity.getId());
                            taskDTO.setTheme(taskEntity.getTheme());
                            taskDTOList.add(taskDTO);
                            themeList.add(taskEntity.getTheme());
                        }
                    }
                }

                if (taskDTOList.size() == 0) {
                    logger.error("没有可开始的策略下发任务！");
                    return this.getReturnJSON(255, errMsg.toString());
                }

                String message = String.format("工单：%s ，开始%s", org.apache.commons.lang3.StringUtils.join(themeList, ","), revert ? "回滚" : "下发");
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                boolean var19 = false;

                int returnCode;
                try {
                    returnCode = this.pushTaskService.addCommandTaskList(taskDTOList, true);
                } catch (Exception var16) {
                    logger.error("批量下发报错,报错原因:{}", var16);
                    return this.getReturnJSON(255, ReturnCode.getMsg(104));
                }

                if (133 == returnCode) {
                    logger.info("当前时间不允许下发！");
                    errMsg.append("当前时间不允许下发！");
                    return this.getReturnJSON(255, errMsg.toString());
                }

                logger.info(errMsg);
                if (revert) {
                    errMsg.insert(0, "开始回滚！");
                } else {
                    errMsg.insert(0, "开始下发！");
                }

                return this.getReturnJSON(0, errMsg.toString());
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("批量下发提示涉及多少设备")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"getdevicenum"})
    public JSONObject getDeviceNum(@RequestParam String ids) {
        StringBuilder returnMsg = new StringBuilder();
        returnMsg.append("本次下发设备数量：");
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = StringUtils.parseIntArrayList(ids);
            } catch (Exception var9) {
                logger.error("解析任务列表出错！", var9);
            }

            List<CommandTaskEditableEntity> taskList = new ArrayList();
            Iterator var6 = idList.iterator();

            while(var6.hasNext()) {
                Integer id = (Integer)var6.next();
                logger.info(String.format("获取任务(%d)", id));
                List<CommandTaskEditableEntity> taskEntityList = this.commandTaskManager.getCommandTaskByTaskId(id);
                taskList.addAll(taskEntityList);
            }

            if (taskList.size() == 0) {
                logger.error("没有可开始的策略下发任务！");
                return this.getReturnJSON(255, returnMsg.toString());
            } else {
                List<String> deviceUuidList = (List)taskList.stream().map((task) -> {
                    return task.getDeviceUuid();
                }).distinct().collect(Collectors.toList());
                returnMsg.append(deviceUuidList.size());
                return this.getReturnJSON(0, returnMsg.toString());
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("同批次设备维度下发任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "taskId",
            value = "任务taskId",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"startdevicepushtasks"})
    public JSONObject startCommandTaskDevice(@RequestParam String taskId, String ids) {
        if (!org.apache.commons.lang3.StringUtils.isEmpty(taskId) && !org.apache.commons.lang3.StringUtils.isEmpty(ids)) {
            StringBuilder errMsg = new StringBuilder();
            logger.info(String.format("获取任务(%s)", ids));
            List<String> list = new ArrayList();
            String[] idArray = ids.split(",");
            String[] var6 = idArray;
            int var7 = idArray.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String id = var6[var8];
                list.add(id);
            }

            List<CommandTaskEditableEntity> taskEditableEntityList = new ArrayList();
            Iterator var13 = list.iterator();

            while(true) {
                String message;
                while(var13.hasNext()) {
                    String id = (String)var13.next();
                    CommandTaskEditableEntity taskEditableEntity = this.commandTaskManager.selectByPrimaryKey(Integer.parseInt(id));
                    message = taskEditableEntity.getCommandline();
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(message) && message.startsWith("无法生成该设备的命令行")) {
                        errMsg.append(String.format("[%s]开始下发失败，存在未生成命令行的设备！", taskEditableEntity.getTheme()));
                    } else {
                        taskEditableEntityList.add(taskEditableEntity);
                    }
                }

                if (ObjectUtils.isEmpty(taskEditableEntityList)) {
                    logger.error(String.format("获取任务(%s)失败，任务下没有命令行数据...", ids));
                    return this.getReturnJSON(255, errMsg.toString());
                }

                boolean ignore = false;
                CommandTaskDTO taskDTO = new CommandTaskDTO();
                taskDTO.setList(taskEditableEntityList);
                taskDTO.setRevert(false);
                RecommendTaskEntity taskEntity = this.recommendTaskService.getRecommendTaskByTaskId(Integer.parseInt(taskId));
                taskDTO.setTaskId(taskEntity.getId());
                taskDTO.setTheme(taskEntity.getTheme());
                message = String.format("工单：%s ，开始%s", taskEntity.getTheme(), "下发");
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                int returnCode = this.pushTaskService.addDeviceCommandTaskList(taskDTO, true);
                if (133 == returnCode) {
                    logger.info("当前时间不允许下发！");
                    errMsg.append("当前时间不允许下发！");
                    return this.getReturnJSON(255, errMsg.toString());
                }

                logger.info(errMsg);
                errMsg.insert(0, "开始下发！");
                return this.getReturnJSON(0, errMsg.toString());
            }
        } else {
            logger.error("开始策略仿真任务为空！");
            return this.getReturnJSON(102);
        }
    }

    @ApiOperation("停止全部任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"stoppushtasks"})
    public JSONObject stopPushTask(String ids, Integer isRevert) {
        if (AliStringUtils.isEmpty(ids)) {
            return this.getReturnJSON(102);
        } else {
            List<String> list = new ArrayList();
            String[] idArray = ids.split(",");
            String[] var5 = idArray;
            int var6 = idArray.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String id = var5[var7];
                list.add(id);
            }

            String typeDesc = "下发";
            if (isRevert != null && isRevert == 1) {
                typeDesc = "回滚";
            }

            boolean hasFailed = false;
            StringBuilder sb = new StringBuilder();
            List<String> failedList = this.pushTaskService.stopTaskList(list, isRevert);
            Iterator var9 = list.iterator();

            while(true) {
                while(var9.hasNext()) {
                    String id = (String)var9.next();
                    if (failedList.contains(id)) {
                        logger.info(String.format("停止任务（%s）失败", id));
                        sb.append(String.format("停止任务（%s）失败", id));
                        hasFailed = true;
                        String message = String.format("停止%s任务[id=" + id + "]失败", typeDesc);
                        this.logClientSimple.addBusinessLog(LogLevel.ERROR.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                    } else {
                        Integer idNum = 0;

                        try {
                            idNum = Integer.valueOf(id);
                        } catch (Exception var13) {
                            logger.error(String.format("转换任务id类型失败！id为(%s)", id));
                        }

                        if (isRevert != null && isRevert == 1) {
                            this.recommendTaskService.updateCommandTaskRevertStatus(idNum, 0);
                        } else {
                            this.recommendTaskService.updateStopTaskPushStatus(idNum, 0);
                        }

                        String message = String.format("停止%s任务[id=" + id + "]成功", typeDesc);
                        this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                    }
                }

                String msg = "停止策略下发任务成功！";
                int code = 0;
                if (hasFailed) {
                    sb.insert(0, "停止策略下发任务完成！");
                    code = 72;
                    msg = sb.toString();
                }

                return this.getReturnJSON(code, msg);
            }
        }
    }

    @PostMapping({"pushtaskstatuslist"})
    public JSONObject getstatuslist(Authentication authentication) {
        List<PushStatusVO> pushStatusVOList = new LinkedList();
        String userName = authentication.getName();
        TaskStatusBranchLevelsDTO taskStatusBranchLevelsDTO = this.recommendTaskService.getPushTaskStatusList(userName);
        this.getStatistic(pushStatusVOList, taskStatusBranchLevelsDTO);
        String jsonObjectString = JSONObject.toJSONString(pushStatusVOList);
        JSONArray jsonArray = JSONObject.parseArray(jsonObjectString);
        return this.getReturnJSON(0, jsonArray);
    }

    @ApiOperation("停止全部任务")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "任务id列表",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "schedule",
            value = "时间",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "enableEmail",
            value = "启用邮件通知",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "receiverEmail",
            value = "收件人邮箱",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"setschedule"})
    public JSONObject setSchedule(String ids, String schedule, String enableEmail, String receiverEmail) {
        logger.info(String.format("%s. %s", ids, schedule));
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = StringUtils.parseIntArrayList(ids);
            } catch (Exception var16) {
                logger.error("解析任务列表出错！", var16);
            }

            Date scheduleTime = null;
            if (!AliStringUtils.isEmpty(schedule) && !schedule.equalsIgnoreCase("NaN")) {
                try {
                    Long time = Long.valueOf(schedule);
                    scheduleTime = new Date(time);
                } catch (Exception var15) {
                    return this.getReturnJSON(255);
                }
            }

            if (!AliStringUtils.isEmpty(enableEmail) || "true".equals(enableEmail) && "false".equals(enableEmail)) {
                new ArrayList();
                Iterator var9 = idList.iterator();

                while(true) {
                    while(var9.hasNext()) {
                        Integer id = (Integer)var9.next();
                        logger.info(String.format("获取任务(%d)", id));
                        List<CommandTaskEditableEntity> taskEntityList = this.commandTaskManager.getCommandTaskByTaskId(id);
                        if (taskEntityList.size() == 0) {
                            logger.error(String.format("获取任务(%d)失败，任务下没有命令行数据...", id));
                        } else {
                            Iterator var12 = taskEntityList.iterator();

                            while(var12.hasNext()) {
                                CommandTaskEditableEntity entity = (CommandTaskEditableEntity)var12.next();
                                CommandTaskEditableEntity taskEntity = new CommandTaskEditableEntity();
                                taskEntity.setId(entity.getId());
                                taskEntity.setPushSchedule(scheduleTime);
                                taskEntity.setEnableEmail(enableEmail);
                                taskEntity.setReceiverEmail(receiverEmail);
                                this.commandTaskManager.setPushSchedule(taskEntity);
                            }
                        }
                    }

                    return this.getReturnJSON(0);
                }
            } else {
                return this.getReturnJSON(255);
            }
        } else {
            logger.error("设置定时下发任务为空！");
            return this.getReturnJSON(102);
        }
    }

    void getStatistic(List<PushStatusVO> pushStatusVOList, TaskStatusBranchLevelsDTO taskStatusBranchLevelsDTO) {
        PushStatusVO recommendStatusVO = new PushStatusVO();
        PushStatusVO checkStatusVO = new PushStatusVO();
        PushStatusVO securityStatusVO = new PushStatusVO();
        PushStatusVO staticNatStatusVO = new PushStatusVO();
        PushStatusVO srcNatStaticVO = new PushStatusVO();
        PushStatusVO dstNatStaticVO = new PushStatusVO();
        PushStatusVO bothNatStaticVO = new PushStatusVO();
        PushStatusVO f5DstNatStaticVO = new PushStatusVO();
        PushStatusVO f5BothNatStaticVO = new PushStatusVO();
        PushStatusVO staticRoutingVO = new PushStatusVO();
        PushStatusVO optimizeVO = new PushStatusVO();
        recommendStatusVO.setType(1);
        recommendStatusVO.setName("业务开通");
        checkStatusVO.setType(2);
        checkStatusVO.setName("策略优化检查");
        securityStatusVO.setType(3);
        securityStatusVO.setName("策略生成-安全策略");
        staticNatStatusVO.setType(5);
        staticNatStatusVO.setName("策略生成-静态NAT");
        srcNatStaticVO.setType(6);
        srcNatStaticVO.setName("策略生成-源NAT");
        dstNatStaticVO.setType(7);
        dstNatStaticVO.setName("策略生成-目的NAT");
        bothNatStaticVO.setType(9);
        bothNatStaticVO.setName("策略生成-BothNAT");
        optimizeVO.setType(17);
        optimizeVO.setName("对象优化检查");
        f5DstNatStaticVO.setType(18);
        f5DstNatStaticVO.setName("F5策略-目的NAT");
        f5BothNatStaticVO.setType(19);
        f5BothNatStaticVO.setName("F5策略-BothNAT");
        staticRoutingVO.setType(20);
        staticRoutingVO.setName("静态路由");
        List<PushStatus> pushStatusList = taskStatusBranchLevelsDTO.getPushStatuses();
        Iterator var15 = pushStatusList.iterator();

        while(var15.hasNext()) {
            PushStatus pushStatus = (PushStatus)var15.next();
            switch(pushStatus.getTaskType()) {
                case 1:
                case 8:
                case 14:
                case 15:
                    this.statistic(recommendStatusVO, pushStatus);
                    break;
                case 2:
                    this.statistic(checkStatusVO, pushStatus);
                    break;
                case 3:
                    this.statistic(securityStatusVO, pushStatus);
                case 4:
                case 10:
                case 11:
                case 12:
                case 13:
                case 16:
                default:
                    break;
                case 5:
                    this.statistic(staticNatStatusVO, pushStatus);
                    break;
                case 6:
                    this.statistic(srcNatStaticVO, pushStatus);
                    break;
                case 7:
                    this.statistic(dstNatStaticVO, pushStatus);
                    break;
                case 9:
                    this.statistic(bothNatStaticVO, pushStatus);
                    break;
                case 17:
                    this.statistic(optimizeVO, pushStatus);
                    break;
                case 18:
                    this.statistic(f5DstNatStaticVO, pushStatus);
                    break;
                case 19:
                    this.statistic(f5BothNatStaticVO, pushStatus);
                    break;
                case 20:
                    this.statistic(staticRoutingVO, pushStatus);
            }
        }

        String param = "1,8,14,15";
        String branchLevel = taskStatusBranchLevelsDTO.getBranchLevel();
        recommendStatusVO.setTotal(this.countTotal(param, branchLevel));
        pushStatusVOList.add(recommendStatusVO);
        securityStatusVO.setTotal(this.countTotal(String.valueOf(3), branchLevel));
        pushStatusVOList.add(securityStatusVO);
        staticNatStatusVO.setTotal(this.countTotal(String.valueOf(5), branchLevel));
        pushStatusVOList.add(staticNatStatusVO);
        dstNatStaticVO.setTotal(this.countTotal(String.valueOf(7), branchLevel));
        pushStatusVOList.add(dstNatStaticVO);
        srcNatStaticVO.setTotal(this.countTotal(String.valueOf(6), branchLevel));
        pushStatusVOList.add(srcNatStaticVO);
        bothNatStaticVO.setTotal(this.countTotal(String.valueOf(9), branchLevel));
        pushStatusVOList.add(bothNatStaticVO);
        checkStatusVO.setTotal(this.countTotal(String.valueOf(2), branchLevel));
        pushStatusVOList.add(checkStatusVO);
        optimizeVO.setTotal(this.countTotal(String.valueOf(17), branchLevel));
        pushStatusVOList.add(optimizeVO);
        f5DstNatStaticVO.setTotal(this.countTotal(String.valueOf(18), branchLevel));
        pushStatusVOList.add(f5DstNatStaticVO);
        f5BothNatStaticVO.setTotal(this.countTotal(String.valueOf(19), branchLevel));
        pushStatusVOList.add(f5BothNatStaticVO);
        staticRoutingVO.setTotal(this.countTotal(String.valueOf(20), branchLevel));
        pushStatusVOList.add(staticRoutingVO);
    }

    private int countTotal(String param, String branchLevel) {
        Map<String, String> params = new HashMap(2);
        params.put("taskType", param);
        params.put("branchLevel", branchLevel);
        Integer listCount = this.commandTaskEdiableMapper.getPushTaskStatusListTotal(params);
        return listCount != null ? listCount : 0;
    }

    void statistic(PushStatusVO pushStatusVO, PushStatus pushStatus) {
        Integer total = pushStatusVO.getTotal();
        total = total + pushStatus.getCount();
        pushStatusVO.setTotal(total);
        int count;
        switch(pushStatus.getPushStatus()) {
            case 0:
                count = pushStatusVO.getNotStart();
                count += pushStatus.getCount();
                pushStatusVO.setNotStart(count);
            case 1:
            default:
                break;
            case 2:
                count = pushStatusVO.getFinished();
                count += pushStatus.getCount();
                pushStatusVO.setFinished(count);
                break;
            case 3:
                count = pushStatusVO.getFailed();
                count += pushStatus.getCount();
                pushStatusVO.setFailed(count);
        }

        switch(pushStatus.getRevertStatus()) {
            case 2:
                count = pushStatusVO.getReverted();
                count += pushStatus.getCount();
                pushStatusVO.setReverted(count);
                break;
            case 3:
                count = pushStatusVO.getRevertFailed();
                count += pushStatus.getCount();
                pushStatusVO.setRevertFailed(count);
        }

    }

    private List<PushTaskEntity> parseTaskEntity(String taskString) {
        JSONArray jsonArray = JSONArray.parseArray(taskString);
        ArrayList list = new ArrayList();

        try {
            for(int index = 0; index < jsonArray.size(); ++index) {
                PushTaskEntity entity = new PushTaskEntity();
                JSONObject jsonObject = jsonArray.getJSONObject(index);
                entity.setOrderType(jsonObject.getInteger("orderType"));
                if (entity.getOrderType() == PushConstants.PUSH_INT_PUSH_TASK_TYPE_POLICY_RECOMMEND) {
                    entity.setPolicyId(jsonObject.getInteger("policyId"));
                } else {
                    entity.setPolicyId(PushConstants.PUSH_INT_PUSH_TASK_NO_POLICY_ID);
                }

                entity.setOrderNo(jsonObject.getString("orderNo"));
                entity.setDeviceUuid(jsonObject.getString("deviceUuid"));
                entity.setDeviceName(jsonObject.getString("deviceName"));
                entity.setManageIp(jsonObject.getString("manageIp"));
                entity.setUserName(jsonObject.getString("userName"));
                entity.setCommand(jsonObject.getString("command"));
                list.add(entity);
            }
        } catch (Exception var7) {
            logger.error("解析任务数据出错！\n" + taskString);
        }

        return list;
    }

    List<CommandTaskEditableEntity> processTaskList(List<PushTaskEntity> list, String branchLevel) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = "P" + simpleDateFormat.format(date);
        byte index = 1;
        List<CommandTaskEditableEntity> commandTaskEntityList = new ArrayList();

        byte var10000;
        for(Iterator var9 = list.iterator(); var9.hasNext(); index = var10000) {
            PushTaskEntity entity = (PushTaskEntity)var9.next();
            String theme = String.format("%s%d", orderNo, Integer.valueOf(index));
            Date createDate = new Date();
            RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
            recommendTaskEntity.setTheme(theme);
            recommendTaskEntity.setOrderNumber(theme);
            recommendTaskEntity.setUserName(entity.getUserName());
            recommendTaskEntity.setSrcIp("255.255.255.255");
            recommendTaskEntity.setDstIp("255.255.255.255");
            recommendTaskEntity.setCreateTime(createDate);
            recommendTaskEntity.setBranchLevel(branchLevel);
            recommendTaskEntity.setStatus(0);
            recommendTaskEntity.setTaskType(2);
            if (entity.getOrderType() == 17) {
                recommendTaskEntity.setTaskType(17);
            } else {
                recommendTaskEntity.setTaskType(2);
            }

            recommendTaskEntity.setIpType(0);
            List<RecommendTaskEntity> recommendTaskEntityListlist = new ArrayList();
            recommendTaskEntityListlist.add(recommendTaskEntity);
            this.recommendTaskService.insertRecommendTaskList(recommendTaskEntityListlist);
            CommandTaskEditableEntity commandTaskEntity = new CommandTaskEditableEntity();
            commandTaskEntity.setDeviceUuid(entity.getDeviceUuid());
            String model = this.recommendTaskService.getDeviceModelNumber(entity.getDeviceUuid());
            if (entity.getOrderType() == 17) {
                commandTaskEntity.setTaskType(17);
            } else {
                commandTaskEntity.setTaskType(2);
            }

            commandTaskEntity.setStatus(PushConstants.PUSH_INT_PUSH_RESULT_STATUS_NOT_START);
            commandTaskEntity.setCommandline(entity.getCommand());
            commandTaskEntity.setTheme(theme);
            commandTaskEntity.setCreateTime(createDate);
            commandTaskEntity.setTaskId(recommendTaskEntity.getId());
            commandTaskEntity.setUserName(entity.getUserName());
            commandTaskEntityList.add(commandTaskEntity);
            var10000 = index;
            int var17 = index + 1;
        }

        return commandTaskEntityList;
    }

    List<CommandTaskEditableEntity> processTaskList(List<PushTaskEntity> list, UserInfoDTO userInfoDTO) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNo = "P" + simpleDateFormat.format(date);
        byte index = 1;
        List<CommandTaskEditableEntity> commandTaskEntityList = new ArrayList();
        String branchLevel;
        if (userInfoDTO != null && org.apache.commons.lang3.StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
            branchLevel = userInfoDTO.getBranchLevel();
        } else {
            branchLevel = "00";
        }

        byte var10000;
        for(Iterator var9 = list.iterator(); var9.hasNext(); index = var10000) {
            PushTaskEntity entity = (PushTaskEntity)var9.next();
            String theme = String.format("%s%d", orderNo, Integer.valueOf(index));
            Date createDate = new Date();
            RecommendTaskEntity recommendTaskEntity = new RecommendTaskEntity();
            recommendTaskEntity.setTheme(theme);
            recommendTaskEntity.setOrderNumber(theme);
            recommendTaskEntity.setUserName(entity.getUserName());
            recommendTaskEntity.setSrcIp("255.255.255.255");
            recommendTaskEntity.setDstIp("255.255.255.255");
            recommendTaskEntity.setCreateTime(createDate);
            recommendTaskEntity.setBranchLevel(branchLevel);
            recommendTaskEntity.setStatus(0);
            recommendTaskEntity.setTaskType(2);
            if (entity.getOrderType() == 17) {
                recommendTaskEntity.setTaskType(17);
            } else {
                recommendTaskEntity.setTaskType(2);
            }

            recommendTaskEntity.setIpType(0);
            List<RecommendTaskEntity> recommendTaskEntityListlist = new ArrayList();
            recommendTaskEntityListlist.add(recommendTaskEntity);
            this.recommendTaskService.insertRecommendTaskList(recommendTaskEntityListlist);
            CommandTaskEditableEntity commandTaskEntity = new CommandTaskEditableEntity();
            commandTaskEntity.setDeviceUuid(entity.getDeviceUuid());
            String model = this.recommendTaskService.getDeviceModelNumber(entity.getDeviceUuid());
            if (entity.getOrderType() == 17) {
                commandTaskEntity.setTaskType(17);
            } else {
                commandTaskEntity.setTaskType(2);
            }

            commandTaskEntity.setStatus(PushConstants.PUSH_INT_PUSH_RESULT_STATUS_NOT_START);
            commandTaskEntity.setCommandline(entity.getCommand());
            commandTaskEntity.setTheme(theme);
            commandTaskEntity.setCreateTime(createDate);
            commandTaskEntity.setTaskId(recommendTaskEntity.getId());
            commandTaskEntity.setUserName(entity.getUserName());
            commandTaskEntityList.add(commandTaskEntity);
            var10000 = index;
            int var17 = index + 1;
        }

        return commandTaskEntityList;
    }

    @ApiOperation("密码策略增删改")
    @PostMapping({"/pwdStrategyOperation"})
    public JSONObject pwdStrategyOperation(@RequestBody PushPwdStrategyVO pwdStrategyVO) {
        if (TotemsStringUtils.equals("0", pwdStrategyVO.getPwdEnable())) {
            pwdStrategyVO.setPwdDefaultMinLengthType("NOT_CHECK");
            pwdStrategyVO.setPwdDaysType("NOT_CHECK");
            pwdStrategyVO.setPwdContainType("NOT_CHECK");
        } else {
            pwdStrategyVO.setPwdDefaultMinLengthType("CHECK");
            pwdStrategyVO.setPwdDaysType("CHECK");
            pwdStrategyVO.setPwdContainType("CHECK");
        }

        int cr = this.pushService.pwdStrategyOperation(pwdStrategyVO);
        return this.getReturnJSON(cr);
    }

    @ApiOperation("密码策略展示")
    @PostMapping({"/searchCpwdStrategy"})
    public JSONObject searchCmdDevicelist(HttpServletRequest request) {
        PushPwdStrategyEntity pushPwdStrategyEntity = this.pushService.searchCmdDevicelist();
        String jsonObjectString = JSONObject.toJSONString(pushPwdStrategyEntity);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }
}
