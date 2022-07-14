//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.recommend.controller;

import com.abtnetworks.totems.common.BaseController;
import com.abtnetworks.totems.common.constants.PushConstants;
import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import com.abtnetworks.totems.recommend.dto.recommend.EditCommandDTO;
import com.abtnetworks.totems.recommend.dto.task.SearchRecommendTaskDTO;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.manager.CommandTaskManager;
import com.abtnetworks.totems.recommend.manager.RecommendTaskManager;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("策略开通扩展抽离页面增改查控制层")
@RestController
@RequestMapping({"/recommend/"})
public class RecommendApiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(RecommendApiController.class);
    @Autowired
    RecommendTaskManager policyRecommendTaskService;
    @Autowired
    private CommandTaskManager commandTaskManager;

    public RecommendApiController() {
    }

    @ApiOperation("new 策略开通搜索列表")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "batchId",
            value = "批量任务id",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "theme",
            value = "策略主题",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "orderNumber",
            value = "策略主题",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "userName",
            value = "用户名称",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "description",
            value = "描述",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "srcIp",
            value = "源地址",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "dstIp",
            value = "目的地址",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "protocol",
            value = "协议",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "dstPort",
            value = "目的端口",
            required = false,
            dataType = "String"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "status",
            value = "类型",
            required = true,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "taskType",
            value = "类型",
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
    @PostMapping({"task/searchtasklist"})
    public JSONObject getTaskList(String id, String batchId, String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, String taskType, int page, int psize, String branchLevel, Authentication authentication) {
        String protocolString = protocol;
        if (protocol != null && !protocol.equals("0")) {
            ServiceDTO serviceDTO = new ServiceDTO();
            serviceDTO.setProtocol(protocol);
            if (dstPort != null) {
                serviceDTO.setDstPorts(dstPort);
            }

            String jsonString = JSONObject.toJSONString(serviceDTO);
            protocolString = StringUtils.strip(jsonString, "{}");
        }

        SearchRecommendTaskDTO searchRecommendTaskDTO = new SearchRecommendTaskDTO();
        searchRecommendTaskDTO.setId(id);
        searchRecommendTaskDTO.setBatchId(batchId);
        searchRecommendTaskDTO.setTheme(theme);
        searchRecommendTaskDTO.setOrderNumber(orderNumber);
        searchRecommendTaskDTO.setUserName(userName);
        searchRecommendTaskDTO.setDescription(description);
        searchRecommendTaskDTO.setDstIp(dstIp);
        searchRecommendTaskDTO.setSrcIp(srcIp);
        searchRecommendTaskDTO.setStatus(status);
        searchRecommendTaskDTO.setPage(page);
        searchRecommendTaskDTO.setPSize(psize);
        searchRecommendTaskDTO.setAuthentication(authentication);
        searchRecommendTaskDTO.setProtocol(protocolString);
        searchRecommendTaskDTO.setBranchLevel(branchLevel);// metoo新增属性
        if (taskType != null) {
            searchRecommendTaskDTO.setTaskType(Integer.parseInt(taskType));
        }

        PageInfo<RecommendTaskEntity> pageInfo = this.policyRecommendTaskService.getTaskList(searchRecommendTaskDTO);
        String jsonObjectString = JSONObject.toJSONString(pageInfo);
        JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
        return this.getReturnJSON(0, jsonObject);
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
    @PostMapping({"task/editcommand"})
    public JSONObject editCommand(int taskId, String deviceUuid, String command, Authentication auth, Integer type) {
        List<CommandTaskEditableEntity> entityList = this.commandTaskManager.getCommandTaskByTaskId(taskId);
        if (entityList != null && entityList.size() != 0) {
            if (((CommandTaskEditableEntity)entityList.get(0)).getStatus() > PushConstants.PUSH_INT_PUSH_RESULT_STATUS_NOT_START) {
                return this.getReturnJSON(80);
            } else {
                EditCommandDTO editCommandDTO = new EditCommandDTO();
                editCommandDTO.setTaskId(taskId);
                editCommandDTO.setCommand(command);
                editCommandDTO.setType(type);
                editCommandDTO.setDeviceUuid(deviceUuid);
                int rc = this.commandTaskManager.editCommandEditableEntity(editCommandDTO, auth.getName());
                CommandTaskEditableEntity entity = this.commandTaskManager.getCommandEditableEntityByTaskIdAndDeviceUuid(taskId, deviceUuid);
                String jsonObjectString = JSONObject.toJSONString(entity);
                JSONObject jsonObject = JSONObject.parseObject(jsonObjectString);
                return this.getReturnJSON(rc, jsonObject);
            }
        } else {
            return this.getReturnJSON(77);
        }
    }
}
