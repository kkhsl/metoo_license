package com.abtnetworks.totems.generate.controller;

import com.abtnetworks.data.totems.log.client.LogClientSimple;
import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.branch.dto.UserInfoDTO;
import com.abtnetworks.totems.branch.service.RemoteBranchService;
import com.abtnetworks.totems.common.BaseController;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.constants.ReturnCode;
import com.abtnetworks.totems.common.dto.CmdDTO;
import com.abtnetworks.totems.common.dto.commandline.DNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.NatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.SNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.common.dto.commandline.StaticNatPolicyDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.common.enums.ActionEnum;
import com.abtnetworks.totems.common.enums.DeviceModelNumberEnum;
import com.abtnetworks.totems.common.enums.DeviceTypeEnum;
import com.abtnetworks.totems.common.enums.IpTypeEnum;
import com.abtnetworks.totems.common.enums.MoveSeatEnum;
import com.abtnetworks.totems.common.enums.PolicyEnum;
import com.abtnetworks.totems.common.enums.TaskTypeEnum;
import com.abtnetworks.totems.common.executor.ExecutorDto;
import com.abtnetworks.totems.common.executor.ExtendedExecutor;
import com.abtnetworks.totems.common.executor.ExtendedRunnable;
import com.abtnetworks.totems.common.executor.PolicyGenerateThread;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.tools.excel.ExcelParser;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.DateUtil;
import com.abtnetworks.totems.common.utils.EntityUtils;
import com.abtnetworks.totems.common.utils.FileUtils;
import com.abtnetworks.totems.common.utils.StringUtils;
import com.abtnetworks.totems.disposal.ReturnT;
import com.abtnetworks.totems.disposal.dto.DisposalScenesDTO;
import com.abtnetworks.totems.generate.service.CommandlineService;
import com.abtnetworks.totems.generate.task.CmdTaskService;
import com.abtnetworks.totems.push.dto.PushRecommendStaticRoutingDTO;
import com.abtnetworks.totems.push.service.PushTaskStaticRoutingService;
import com.abtnetworks.totems.push.service.task.PushTaskService;
import com.abtnetworks.totems.push.vo.NewPolicyPushVO;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.PushAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.StaticNatAdditionalInfoEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.abtnetworks.totems.recommend.manager.WhaleManager;
import com.abtnetworks.totems.recommend.service.RecommendExcelAndDownloadService;
import com.abtnetworks.totems.recommend.vo.PolicyRecommendSecurityPolicyVO;
import com.abtnetworks.totems.recommend.vo.PolicyTaskDetailVO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(
        tags = {"命令行生成列表"}
)
@RestController
@RequestMapping({"/recommend/"})
public class PolicyGenerateController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PolicyGenerateController.class);
    @Autowired
    public RecommendTaskManager taskService;
    @Autowired
    RecommendTaskManager policyRecommendTaskService;
    @Autowired
    CommandTaskManager commandTaskManager;
    @Autowired
    PushTaskService pushTaskService;
    @Autowired
    ExcelParser generateExcelParser;
    @Autowired
    WhaleManager whaleManager;
    @Autowired
    CommandlineService commandlineService;
    @Resource
    RecommendExcelAndDownloadService recommendExcelAndDownloadService;
    @Autowired
    PushTaskStaticRoutingService pushTaskStaticRoutingService;
    @Value("${resourceHandler}")
    private String resourceHandler;
    @Value("${importSecurityExcelFile}")
    private String securityExcelFile;
    @Value("${server.root.basedir}")
    private String serverRootBasedir;
    @Value("${push.download-file}")
    String dirPath;
    @Autowired
    private LogClientSimple logClientSimple;
    @Resource
    RemoteBranchService remoteBranchService;
    @Autowired
    CmdTaskService cmdTaskService;
    @Autowired
    @Qualifier("generateExecutor")
    private Executor generateExecutor;
    @Autowired
    @Qualifier("batchImportExecutor")
    private Executor batchImportExecutor;

    public PolicyGenerateController() {
    }

    @ApiOperation("NAT策略生成列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "psize",
            value = "每页条数",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "id集合",
            required = false,
            dataType = "String"
    )})
    @PostMapping({"task/searchnatpolicytasklist"})
    public JSONObject getNatPolicyTaskList(String theme, String type, int page, int psize, String ids, Integer id, String userName, String deviceUuid, String pushStatus, Authentication authentication, String branchLevel) {
        PageInfo<PolicyTaskDetailVO> pageInfo = this.policyRecommendTaskService.getNatPolicyTaskList(theme, type, page, psize, ids, id, userName, deviceUuid, pushStatus, authentication, branchLevel);
        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("安全策略生成列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
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
    @PostMapping({"task/searchsecuritypolicytasklist"})
    public JSONObject getSecurityPolicyTaskList(String theme, int page, int psize, String userName, String deviceUuid, String pushStatus, Authentication authentication, String branchLevel) {
        PageInfo<PolicyTaskDetailVO> pageInfo = this.policyRecommendTaskService.getSecurityPolicyTaskList(theme, page, psize, userName, deviceUuid, pushStatus, authentication, branchLevel);
        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
    }

    @ApiOperation("自定义命令行列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            required = true,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "psize",
            value = "每页条数",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/searchcustomizecmdtasklist"})
    public ReturnT getCustomizeCmdTaskList(String theme, int page, int psize, String userName, String deviceUuid, Integer pushStatus, Authentication authentication) {
        PageInfo<PolicyTaskDetailVO> pageInfo = this.policyRecommendTaskService.getCustomizeCmdTaskList(theme, page, psize, userName, deviceUuid, pushStatus, authentication);
        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return new ReturnT(jsonObject);
    }

    @ApiOperation("策略生成excel导出")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "isReload",
            value = "是否重新下载",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "startTime",
            value = "开始时间",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "endTime",
            value = "结束时间",
            required = false,
            dataType = "String"
    )})
    @ApiResponses({@ApiResponse(
            code = 200,
            message = ""
    )})
    @RequestMapping(
            value = {"task/exportTask"},
            produces = {"application/json; charset=utf-8"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public ResultRO<JSONObject> download(HttpServletResponse response, String isReload, String startTime, String endTime, Authentication authentication) throws Exception {
        List<PolicyTaskDetailVO> list = this.policyRecommendTaskService.getNatTaskList((String)null, "5", (String)null, (Integer)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO> list1 = this.policyRecommendTaskService.getNatTaskList((String)null, "6", (String)null, (Integer)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO> list2 = this.policyRecommendTaskService.getNatTaskList((String)null, "7", (String)null, (Integer)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO> list3 = this.policyRecommendTaskService.getNatTaskList((String)null, "9", (String)null, (Integer)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO> list4 = this.policyRecommendTaskService.getSecurityTaskList((String)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO> list5 = this.policyRecommendTaskService.getNatTaskList((String)null, "20", (String)null, (Integer)null, (String)null, (String)null, startTime, endTime, authentication);
        List<PolicyTaskDetailVO>[] taskArrayList = new List[]{list4, list1, list2, list, list3, list5};
        this.policyRecommendTaskService.getTaskListByTime(startTime, endTime, authentication);
        ResultRO<JSONObject> resultRO = new ResultRO(true);
        JSONObject jsonObject = new JSONObject();
        String standardDateTime = DateUtil.getTimeStamp();
        String preFilename = "策略生成导出";

        try {
            preFilename = new String(preFilename.getBytes("UTF-8"), "UTF-8");
            preFilename = preFilename + "_" + standardDateTime;
        } catch (UnsupportedEncodingException var26) {
            logger.error("生成策略生成报表文件名称异常", var26);
        }

        String destDirName = this.dirPath + "/policyGenerateExcel";
        String filePath = destDirName + "/" + preFilename + ".xlsx";
        String doingFileTemp = destDirName + "/policyGeneratedoing.temp";

        try {
            if (!(new File(destDirName)).exists()) {
                FileUtils.createDir(destDirName);
            }

            String fileIsExistsName = FileUtils.isDirExistFile(destDirName);
            boolean doingFileTempIsExists = FileUtils.fileIsExists(doingFileTemp);
            boolean fileIsExists = FileUtils.fileIsExists(destDirName + "/" + fileIsExistsName);
            File doingFile;
            if (null == isReload) {
                if (fileIsExists && !doingFileTempIsExists) {
                    resultRO.setMessage("文件生成成功");
                    jsonObject.put("filePath", fileIsExistsName);
                    jsonObject.put("status", 1);
                    resultRO.setData(jsonObject);
                    return resultRO;
                }

                if (doingFileTempIsExists) {
                    resultRO.setMessage("文件生成中");
                    jsonObject.put("filePath", preFilename + ".xlsx");
                    jsonObject.put("status", 2);
                    resultRO.setData(jsonObject);
                    return resultRO;
                }

                if (!doingFileTempIsExists && !fileIsExists) {
                    doingFile = new File(doingFileTemp);
                    doingFile.createNewFile();
                    resultRO.setMessage("生成成功");
                    jsonObject.put("filePath", preFilename + ".xlsx");
                    jsonObject.put("status", 2);
                    (new PolicyGenerateThread(filePath, doingFile, taskArrayList)).start();
                    resultRO.setData(jsonObject);
                    return resultRO;
                }
            }

            if ("true".equals(isReload)) {
                FileUtils.deleteFileByPath(destDirName + "/" + fileIsExistsName);
                doingFile = new File(doingFileTemp);
                doingFile.createNewFile();
                (new PolicyGenerateThread(filePath, doingFile, taskArrayList)).start();
                resultRO.setMessage("正在生成文件");
                jsonObject.put("filePath", preFilename + ".xlsx");
                jsonObject.put("status", 2);
                resultRO.setData(jsonObject);
                return resultRO;
            } else {
                this.recommendExcelAndDownloadService.downLoadPolicyAdd(response, destDirName + "/" + fileIsExistsName);
                return null;
            }
        } catch (Exception var25) {
            File doingFile = new File(doingFileTemp);
            doingFile.delete();
            logger.error("下载策略生成Excel表格失败:", var25);
            resultRO.setMessage("数据导出失败");
            resultRO.setSuccess(false);
            jsonObject.put("filePath", filePath);
            jsonObject.put("status", 3);
            resultRO.setData(jsonObject);
            return resultRO;
        }
    }

    @ApiOperation("策略生成批量删除")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "ids",
            value = "策略生成任务id数组",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"task/deletesecuritypolicytasklist"})
    public JSONObject deletePolicyTaskList(@RequestParam String ids) {
        ids = String.format("[%s]", ids);
        JSONArray jsonArray = JSONArray.parseArray(ids);
        if (jsonArray != null && jsonArray.size() != 0) {
            List idList = null;

            try {
                idList = StringUtils.parseIntArrayList(ids);
            } catch (Exception var11) {
                logger.error("解析下发策略任务出错！", var11);
            }

            StringBuilder errMsg = new StringBuilder();
            int rc = 0;
            List<String> themeList = new ArrayList();
            int tasking = 0;
            Iterator var8 = idList.iterator();

            while(true) {
                CommandTaskEditableEntity taskEntity;
                do {
                    do {
                        if (!var8.hasNext()) {
                            if (tasking > 0) {
                                errMsg.deleteCharAt(errMsg.length() - 1);
                                errMsg.append(":" + ReturnCode.getMsg(rc));
                            }

                            if (rc != 0) {
                                return this.getReturnJSON(rc, errMsg.toString());
                            }

                            this.policyRecommendTaskService.removePolicyTasks(idList);
                            String message = String.format("删除策略生成和下发工单：%s 成功", org.apache.commons.lang3.StringUtils.join(themeList, ","));
                            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
                            return this.getReturnJSON(0);
                        }

                        int id = (Integer)var8.next();
                        logger.info(String.format("获取任务(%d)", id));
                        taskEntity = this.policyRecommendTaskService.getRecommendTaskById(id);
                    } while(null == taskEntity);

                    themeList.add(taskEntity.getTheme());
                } while(taskEntity.getPushStatus() != 1 && taskEntity.getRevertStatus() != 1);

                ++tasking;
                rc = 42;
                errMsg.append(taskEntity.getTheme() + ",");
            }
        } else {
            logger.error("要删除的下发策略任务为空！");
            return this.getReturnJSON(101);
        }
    }

    @ApiOperation("静态策略生成")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
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
    @PostMapping({"task/addstaticnatpolicy"})
    public JSONObject addStaticNatPolicyPolicy(@RequestBody StaticNatPolicyDTO staticNatPolicyDTO, Authentication auth) {
        logger.info("添加静态nat策略" + JSONObject.toJSONString(staticNatPolicyDTO));
        if (staticNatPolicyDTO == null) {
            return this.getReturnJSON(82);
        } else {
            String deviceUuid = staticNatPolicyDTO.getDeviceUuid();
            NodeEntity node = this.taskService.getTheNodeByUuid(deviceUuid);
            if (node == null) {
                return this.getReturnJSON(83);
            } else {
                String userName = staticNatPolicyDTO.getUserName();
                if(staticNatPolicyDTO.getUserName() == null || staticNatPolicyDTO.getUserName().equals("")){
                    userName = auth.getName();
                }
                staticNatPolicyDTO.setSrcZone(this.getZone(staticNatPolicyDTO.getSrcZone()));
                staticNatPolicyDTO.setDstZone(this.getZone(staticNatPolicyDTO.getDstZone()));
                StaticNatAdditionalInfoEntity additionalInfoEntity = new StaticNatAdditionalInfoEntity(deviceUuid, staticNatPolicyDTO.getSrcZone(), staticNatPolicyDTO.getDstZone(), staticNatPolicyDTO.getInDevItf(), staticNatPolicyDTO.getOutDevItf(), staticNatPolicyDTO.getPreIpAddress(), staticNatPolicyDTO.getPostIpAddress(), staticNatPolicyDTO.getPrePort(), staticNatPolicyDTO.getPostPort(), staticNatPolicyDTO.getProtocol(), staticNatPolicyDTO.getInDevItfAlias(), staticNatPolicyDTO.getOutDevItfAlias());
                Integer ipType = ObjectUtils.isNotEmpty(staticNatPolicyDTO.getIpType()) ? staticNatPolicyDTO.getIpType() : IpTypeEnum.IPV4.getCode();
                RecommendTaskEntity recommendTaskEntity = EntityUtils.createRecommendTask(staticNatPolicyDTO.getTheme(), userName, "255.255.255.255", "255.255.255.255", (String)null, 5, 0, JSONObject.toJSONString(additionalInfoEntity), (String)null, (String)null, (String)null, (String)null, ipType);
                if(staticNatPolicyDTO.getBranchLevel() == null ||staticNatPolicyDTO.getBranchLevel().equals("")){
                    this.getBranch(userName, recommendTaskEntity);
                }else{
                    recommendTaskEntity.setBranchLevel(staticNatPolicyDTO.getBranchLevel());
                }
                this.addRecommendTask(recommendTaskEntity);
                CommandTaskEditableEntity entity = this.createCommandTask(5, recommendTaskEntity.getId(), userName, staticNatPolicyDTO.getTheme(), staticNatPolicyDTO.getDeviceUuid());
                BeanUtils.copyProperties(staticNatPolicyDTO, entity);
                entity.setBranchLevel(recommendTaskEntity.getBranchLevel());
                this.commandTaskManager.addCommandEditableEntityTask(entity);
                DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
                boolean isVsys = false;
                String rootDeviceUuid = "";
                String vsysName = "";
                if (device != null) {
                    DeviceDataRO deviceData = (DeviceDataRO)device.getData().get(0);
                    if (deviceData.getIsVsys() != null) {
                        isVsys = deviceData.getIsVsys();
                        rootDeviceUuid = deviceData.getRootDeviceUuid();
                        vsysName = deviceData.getVsysName();
                    }
                }

                CmdDTO cmdDTO = EntityUtils.createCmdDTO(PolicyEnum.STATIC, entity.getId(), entity.getTaskId(), deviceUuid, staticNatPolicyDTO.getTheme(), userName, (String)null, additionalInfoEntity.getGlobalAddress(), (String)null, additionalInfoEntity.getInsideAddress(), EntityUtils.getServiceList(additionalInfoEntity.getProtocol(), additionalInfoEntity.getGlobalPort()), EntityUtils.getServiceList(additionalInfoEntity.getProtocol(), additionalInfoEntity.getInsidePort()), staticNatPolicyDTO.getSrcZone(), staticNatPolicyDTO.getDstZone(), staticNatPolicyDTO.getInDevItf(), staticNatPolicyDTO.getOutDevItf(), staticNatPolicyDTO.getInDevItfAlias(), staticNatPolicyDTO.getOutDevItfAlias(), "", isVsys, vsysName, (String)null, (String)null, (String)null);
                logger.info("命令行生成任务为:" + JSONObject.toJSONString(cmdDTO));
                this.pushTaskService.addGenerateCmdTask(cmdDTO);
                JSONObject rs = this.getReturnJSON(0);
                rs.put("taskId", entity.getTaskId());
                rs.put("pushTaskId", entity.getId());
                return rs;
            }
        }
    }

    @ApiOperation("源Nat策略生成")
    @ApiImplicitParams({})
    @PostMapping({"task/addsrcnatpolicy"})
    public JSONObject addSrcNatPolicyPolicy(@RequestBody SNatPolicyDTO sNatPolicyDTO, Authentication auth) {
        logger.info("添加sNat策略" + JSONObject.toJSONString(sNatPolicyDTO));
        if (sNatPolicyDTO == null) {
            return this.getReturnJSON(82);
        } else {
            int rc = this.taskService.insertSrcNatPolicy(sNatPolicyDTO, auth);
            JSONObject rs = this.getReturnJSON(rc);
            rs.put("taskId", sNatPolicyDTO.getTaskId());
            rs.put("pushTaskId", sNatPolicyDTO.getId());
            return rs;
        }
    }

    @ApiOperation("目的Nat策略生成")
    @ApiImplicitParams({})
    @PostMapping({"task/adddstnatpolicy"})
    public JSONObject addDstNatPolicyPolicy(@RequestBody DNatPolicyDTO policyDTO, Authentication auth) {
        logger.info("添加dnat策略" + JSONObject.toJSONString(policyDTO));
        if (policyDTO == null) {
            return this.getReturnJSON(82);
        } else {
            int rc = this.taskService.insertDstNatPolicy(policyDTO, auth);
            JSONObject rs = this.getReturnJSON(rc);
            rs.put("taskId", policyDTO.getTaskId());
            rs.put("pushTaskId", policyDTO.getId());
            return rs;
        }
    }

    private void getBranch(String userName, RecommendTaskEntity recommendTaskEntity) {
        UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(userName);
        if (userInfoDTO != null && org.apache.commons.lang3.StringUtils.isNotEmpty(userInfoDTO.getBranchLevel())) {
            recommendTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
        } else {
            recommendTaskEntity.setBranchLevel("00");
        }

    }

    @ApiOperation("双向Nat策略生成")
    @PostMapping({"task/addbothnatpolicy"})
    public JSONObject addBothNatPolicy(@RequestBody NatPolicyDTO policyDTO, Authentication auth) {
        logger.info("添加双向nat策略" + JSONObject.toJSONString(policyDTO));
        if (policyDTO == null) {
            return this.getReturnJSON(82);
        } else {
            int rc = this.taskService.insertBothNatPolicy(policyDTO, auth);
            JSONObject rs = this.getReturnJSON(rc);
            rs.put("taskId", policyDTO.getTaskId());
            rs.put("pushTaskId", policyDTO.getId());
            return rs;
        }
    }

    @ApiOperation(
            value = "新建策略",
            httpMethod = "POST",
            notes = "新建策略，根据策略参数生成建议策略，并生成命令行"
    )
    @RequestMapping(
            value = {"task/new-policy-push"},
            method = {RequestMethod.POST}
    )
    public JSONObject newPolicyPush(@ApiParam(name = "newPolicyPushVO",value = "新建策略",required = true) @RequestBody NewPolicyPushVO newPolicyPushVO, Authentication auth) throws Exception {
        if(newPolicyPushVO.getUserName() == null || newPolicyPushVO.getUserName().equals("")){
            newPolicyPushVO.setUserName(auth.getName());
        }
        int rc = this.pushTaskService.newPolicyPush(newPolicyPushVO);
        JSONObject rs = this.getReturnJSON(rc);
        rs.put("taskId", newPolicyPushVO.getTaskId());
        rs.put("pushTaskId", newPolicyPushVO.getTaskId());
        return this.getReturnJSON(rc);
    }

    @ApiOperation(
            value = "新建自定义命令行",
            httpMethod = "POST",
            notes = "新建自定义命令行，用户可自由输入下发命令行"
    )
    @RequestMapping(
            value = {"task/customizecmd"},
            method = {RequestMethod.POST}
    )
    public ReturnT newCustomizeCmd(@ApiParam(name = "newPolicyPushVO",value = "新建策略",required = true) @RequestBody NewPolicyPushVO newPolicyPushVO, Authentication auth) throws Exception {
        newPolicyPushVO.setUserName(auth.getName());
        int rc = this.pushTaskService.newCustomizeCmd(newPolicyPushVO);
        JSONObject rs = this.getReturnJSON(rc);
        rs.put("taskId", newPolicyPushVO.getTaskId());
        rs.put("pushTaskId", newPolicyPushVO.getTaskId());
        return new ReturnT(rs);
    }

    @ApiOperation(
            value = "批量策略生成",
            httpMethod = "POST",
            notes = "根据导入Excel表，批量生成命令行"
    )
    @PostMapping({"task/generatesecurity"})
    public JSONObject importPolicyGenerate(MultipartFile file, Authentication auth, HttpServletResponse response) {
        logger.info("批量添加策略生成任务");
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        final String userName = auth.getName();
        JSONObject jsonObject = new JSONObject();
        final List<RecommendTaskEntity> tmpList = new ArrayList();
        List<RecommendTaskEntity> tmpNatList = new ArrayList();
        List<PushRecommendStaticRoutingDTO> tmpRouteList = new ArrayList();
        final UserInfoDTO userInfoDTO = this.remoteBranchService.findOne(userName);
        errmsg = this.generateExcelParser.parse(file, userInfoDTO, tmpList, tmpNatList, tmpRouteList);
        if (!AliStringUtils.isEmpty(errmsg)) {
            return this.returnJSON(status, jsonObject, errcode, errmsg);
        } else {
            String id = "batch_import_" + DateUtil.getTimeStamp();
            this.batchImportExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "批量新增策略", "", new Date())) {
                protected void start() throws InterruptedException, Exception {
                    Iterator var1 = tmpList.iterator();

                    while(var1.hasNext()) {
                        final RecommendTaskEntity entity = (RecommendTaskEntity)var1.next();
                        String id = "generate_" + entity.getId();
                        if (ExtendedExecutor.containsKey(id)) {
                            PolicyGenerateController.logger.warn(String.format("策略仿真任务(%s)已经存在！任务不重复添加", id));
                        } else {
                            PolicyGenerateController.this.generateExecutor.execute(new ExtendedRunnable(new ExecutorDto(id, "批量新增策略生成", "", new Date())) {
                                protected void start() throws InterruptedException, Exception {
                                    String additioncalInfoString = entity.getAdditionInfo();
                                    JSONObject object = JSONObject.parseObject(additioncalInfoString);
                                    PushAdditionalInfoEntity additionalInfoEntity = (PushAdditionalInfoEntity)object.toJavaObject(PushAdditionalInfoEntity.class);
                                    if (CollectionUtils.isNotEmpty(additionalInfoEntity.getScenesDTOList())) {
                                        List<DisposalScenesDTO> scenesDTOList = additionalInfoEntity.getScenesDTOList();
                                        Iterator var5 = scenesDTOList.iterator();

                                        while(var5.hasNext()) {
                                            DisposalScenesDTO disposalScenesDTO = (DisposalScenesDTO)var5.next();
                                            NodeEntity node = PolicyGenerateController.this.taskService.getTheNodeByUuid(disposalScenesDTO.getDeviceUuid());
                                            PolicyGenerateController.this.generateCommandLineTask(entity, additionalInfoEntity, userInfoDTO, node, userName, disposalScenesDTO.getSrcZoneName(), disposalScenesDTO.getDstZoneName());
                                        }
                                    } else {
                                        NodeEntity nodex = PolicyGenerateController.this.policyRecommendTaskService.getDeviceByManageIp(entity.getDeviceIp());
                                        PolicyGenerateController.this.generateCommandLineTask(entity, additionalInfoEntity, userInfoDTO, nodex, userName, additionalInfoEntity.getSrcZone(), additionalInfoEntity.getDstZone());
                                    }

                                }
                            });
                        }
                    }

                }
            });
            if (tmpNatList.size() > 0) {
                logger.info("添加NAT策略...");
                this.policyRecommendTaskService.insertRecommendTaskList(tmpNatList);
                this.generateExcelParser.createNatCommandTask(tmpNatList, auth);
            }

            if (tmpRouteList.size() > 0) {
                logger.info("添加静态路由策略...");
                Iterator var14 = tmpRouteList.iterator();

                while(var14.hasNext()) {
                    PushRecommendStaticRoutingDTO routeDTO = (PushRecommendStaticRoutingDTO)var14.next();
                    this.pushTaskStaticRoutingService.createPushTaskStaticRouting(routeDTO);
                }
            }

            String message = String.format("批量导入策略生成数据成功，共计%s条", tmpNatList.size() + tmpList.size());
            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_PUSH.getId(), message);
            status = "0";
            return this.returnJSON(status, jsonObject, errcode, errmsg);
        }
    }

    public static void main(String[] args) {
        JSONObject object = JSONObject.parseObject("");
        int a = object.size();
        JSONObject object1 = JSONObject.parseObject("{\"key\":12}");
        int b = object1.size();
        PushAdditionalInfoEntity additionalInfoEntity = (PushAdditionalInfoEntity)object.toJavaObject(PushAdditionalInfoEntity.class);
        if (additionalInfoEntity != null && CollectionUtils.isNotEmpty(additionalInfoEntity.getScenesDTOList())) {
            System.out.println("aa");
        } else {
            System.out.println("ss");
        }

    }

    @RequestMapping({"task/generatesecurityresult"})
    public JSONObject importPolicyGenerateResult(HttpServletResponse response) {
        logger.info("批量添加任务");
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        new JSONObject();
        String fileUrl = this.serverRootBasedir + "/service/push/cmd/策略批量生成结果.xls";
        File resultFile = new File(fileUrl);
        InputStream fin = null;
        ServletOutputStream out = null;

        try {
            fin = new FileInputStream(resultFile);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("策略批量生成结果.xls", "UTF-8"))));
            response.setCharacterEncoding("utf-8");
            out = response.getOutputStream();
            byte[] buffer = new byte[512];
            boolean var11 = true;

            int bytesToRead;
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } catch (Exception var20) {
            logger.error("压缩包下载异常", var20);
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }

                if (out != null) {
                    out.close();
                }

                if (resultFile != null) {
                    resultFile.delete();
                }
            } catch (IOException var19) {
                logger.error("io流异常");
            }

        }

        return null;
    }

    @ApiOperation("下载批量策略生成Excel任务模板")
    @PostMapping({"task/downloadsecuritytemplate"})
    public JSONObject downloadHostTemplate() {
        String status = "-1";
        String errcode = "";
        String errmsg = "";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("fileName", this.resourceHandler.replace("**", "") + this.securityExcelFile);
            status = "0";
        } catch (Exception var6) {
            errmsg = errmsg + var6;
            logger.error("downloadHostTemplate：" + var6);
        }

        return this.returnJSON(status, jsonObject, errcode, errmsg);
    }

    protected String getZone(String zone) {
        if (zone == null) {
            return "";
        } else {
            return zone.equals("-1") ? "" : zone;
        }
    }

    protected void addRecommendTask(RecommendTaskEntity entity) {
        logger.info("策略下发新增任务:" + JSONObject.toJSONString(entity));
        List<RecommendTaskEntity> list = new ArrayList();
        list.add(entity);
        int count = this.taskService.insertRecommendTaskList(list);
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

    private void generateCommandLineTask(RecommendTaskEntity entity, PushAdditionalInfoEntity additionalInfoEntity, UserInfoDTO userInfoDTO, NodeEntity node, String userName, String srcZone, String dstZone) {
        List<ServiceDTO> serviceList = new ArrayList();
        if (entity.getServiceList() == null) {
            logger.info("新建策略服务为空");
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setProtocol("0");
            serviceDTO.setSrcPorts("any");
            serviceDTO.setDstPorts("any");
            ((List)serviceList).add(serviceDTO);
        } else {
            JSONArray array = JSONArray.parseArray(entity.getServiceList());
            serviceList = array.toJavaList(ServiceDTO.class);
        }

        String deviceUuid = node.getUuid();
        CommandTaskEditableEntity commandTaskEntity = this.createCommandTask(3, entity.getId(), entity.getUserName(), entity.getTheme(), deviceUuid);
        commandTaskEntity.setBranchLevel(userInfoDTO.getBranchLevel());
        this.commandTaskManager.addCommandEditableEntityTask(commandTaskEntity);
        DeviceRO device = this.whaleManager.getDeviceByUuid(deviceUuid);
        DeviceDataRO deviceData = (DeviceDataRO)device.getData().get(0);
        boolean isVsys = false;
        String vsysName = "";
        if (deviceData.getIsVsys() != null) {
            isVsys = deviceData.getIsVsys();
            vsysName = deviceData.getVsysName();
        }

        ActionEnum action = ActionEnum.PERMIT;
        if (additionalInfoEntity.getAction().equalsIgnoreCase("DENY")) {
            action = ActionEnum.DENY;
        }

        MoveSeatEnum moveSeat = MoveSeatEnum.FIRST;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTimeString = entity.getStartTime() == null ? null : sdf.format(entity.getStartTime());
        String endTimeString = entity.getEndTime() == null ? null : sdf.format(entity.getEndTime());
        PolicyEnum type = null;
        String modelNumber = node.getModelNumber();
        if (AliStringUtils.isEmpty(modelNumber) || !DeviceTypeEnum.ROUTER.name().equalsIgnoreCase(deviceData.getDeviceType()) || !modelNumber.equals("Cisco IOS") && !modelNumber.equals("Cisco NX-OS")) {
            type = PolicyEnum.SECURITY;
        } else {
            type = PolicyEnum.ACL;
        }

        String inItfAlias = additionalInfoEntity.getInDevItfAlias();
        String outItfAlias = additionalInfoEntity.getOutDevItfAlias();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(modelNumber) && DeviceModelNumberEnum.CISCO.getKey().equals(modelNumber)) {
            inItfAlias = org.apache.commons.lang3.StringUtils.isNotEmpty(additionalInfoEntity.getInDevItf()) ? additionalInfoEntity.getInDevItf() : additionalInfoEntity.getSrcZone();
            outItfAlias = org.apache.commons.lang3.StringUtils.isNotEmpty(additionalInfoEntity.getOutDevItf()) ? additionalInfoEntity.getOutDevItf() : additionalInfoEntity.getDstZone();
        }

        CmdDTO cmdDTO = EntityUtils.createCmdDTO(type, commandTaskEntity.getId(), commandTaskEntity.getTaskId(), node.getUuid(), entity.getTheme(), userName, entity.getSrcIp(), entity.getDstIp(), (String)null, (String)null, (List)serviceList, (List)null, srcZone, dstZone, additionalInfoEntity.getInDevItf(), additionalInfoEntity.getOutDevItf(), inItfAlias, outItfAlias, startTimeString, endTimeString, entity.getDescription(), action, isVsys, vsysName, moveSeat, (String)null, (String)null, entity.getIdleTimeout(), entity.getSrcIpSystem(), entity.getDstIpSystem(), entity.getIpType(), entity.getPostSrcIpSystem(), (Integer)null, (String)null, (String)null, (PolicyRecommendSecurityPolicyVO)null, (String)null);
        cmdDTO.getTask().setBeforeConflict(entity.getBeforeConflict());
        cmdDTO.getTask().setMergeCheck(entity.getMergeCheck());
        cmdDTO.getTask().setRangeFilter(entity.getRangeFilter());
        cmdDTO.getTask().setTaskTypeEnum(3 == entity.getTaskType() ? TaskTypeEnum.SECURITY_TYPE : null);
        cmdDTO.getDevice().setNodeEntity(node);
        logger.info("命令行生成任务为:" + JSONObject.toJSONString(cmdDTO));
        this.cmdTaskService.getRuleMatchFlow2Generate(cmdDTO, userInfoDTO);
    }
}
