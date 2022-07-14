//package com.abtnetworks.totems.combing.suggest.controller;
//
//import com.abtnetworks.data.totems.log.client.LogClientSimple;
//import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
//import com.abtnetworks.data.totems.log.common.enums.LogLevel;
//import com.abtnetworks.totems.combing.hit.BaseController;
//import com.abtnetworks.totems.combing.hit.entity.mysql.PolicyDevConfigEntity;
//import com.abtnetworks.totems.combing.hit.service.PolicyDevConfigService;
//import com.abtnetworks.totems.combing.suggest.entity.mysql.PolicySuggestTaskEntity;
//import com.abtnetworks.totems.combing.suggest.service.NodeService;
//import com.abtnetworks.totems.combing.suggest.service.SuggestTaskService;
//import com.abtnetworks.totems.common.config.ProtocolMapConfig;
//import com.abtnetworks.totems.common.lang.TotemsStringUtils;
//import com.abtnetworks.totems.common.utils.AliStringUtils;
//import com.abtnetworks.totems.common.utils.IpUtils;
//import com.alibaba.fastjson.JSONObject;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(
//        tags = {"策略建议任务管理"}
//)
//@RestController
//@RequestMapping({"${startPath}/${suggestPath}/task"})
//public class SuggestTaskController extends BaseController {
//    @Value("${typeId.suggest}")
//    private String typeId;
//    @Autowired
//    private SuggestTaskService suggestTaskService;
//    @Autowired
//    private PolicyDevConfigService policyDevConfigService;
//    @Autowired
//    private NodeService nodeService;
//    @Autowired
//    private ProtocolMapConfig protocolMapConfig;
//    @Autowired
//    private LogClientSimple logClientSimple;
//    @Value("${suggest.validate_accurate_merge_ip_num}")
//    private int validateAccurateMergeIpNum;
//    @Value("${suggest.validate_ip_group_num}")
//    private int validateIpGroupNum;
//    @Value("${suggest.download-suggest-file}")
//    private String dirPath;
//
//    public SuggestTaskController() {
//    }
//
//    @ApiOperation("策略建议任务列表")
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "taskName",
//            value = "任务名称",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "devName",
//            value = "防火墙名称",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "page",
//            value = "页数",
//            required = true,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "limit",
//            value = "每页条数",
//            required = true,
//            dataType = "Integer"
//    )})
//    @PostMapping({"/list"})
//    public JSONObject list(String taskName, String devName, Authentication attributePrincipal, Integer page, Integer limit) {
//        JSONObject jsonObject = new JSONObject();
//        String userId = null;
//        if (attributePrincipal != null) {
//            userId = attributePrincipal.getName();
//        }
//
//        jsonObject.put("taskList", this.suggestTaskService.listPolicySuggestTask(taskName, devName, userId, page, limit));
//        return this.returnJSON("0", jsonObject, "", "");
//    }
//
//    @ApiOperation("防火墙日志接收配置信息列表")
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "vendorName",
//            value = "厂商名称",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "name",
//            value = "防火墙设备IP或名称",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "typeStatus",
//            value = "接收日志状态（0未开启，1已开启）",
//            required = false,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "page",
//            value = "页数",
//            required = true,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "limit",
//            value = "每页条数",
//            required = true,
//            dataType = "Integer"
//    )})
//    @PostMapping({"/listDevNodeLogConfig"})
//    public JSONObject listDevNodeLogConfig(String vendorName, String name, Integer typeStatus, Authentication attributePrincipal, Integer page, Integer limit) {
//        JSONObject jsonObject = new JSONObject();
//        String userId = null;
//        if (attributePrincipal != null) {
//            userId = attributePrincipal.getName();
//        }
//
//        jsonObject.put("list", this.nodeService.listDevNodeLogConfig(vendorName, name, this.typeId, typeStatus, userId, page, limit));
//        return this.returnJSON("0", jsonObject, "", "");
//    }
//
//    @ApiOperation("获取已开启梳理日志接收的设备(废弃)")
//    @PostMapping({"/getDevNodeBySuggestLog"})
//    public JSONObject getDevNodeBySuggestLog(Authentication attributePrincipal) {
//        JSONObject jsonObject = new JSONObject();
//        String userId = null;
//        if (attributePrincipal != null) {
//            userId = attributePrincipal.getName();
//        }
//
//        jsonObject.put("devNode", this.nodeService.getDevNode(this.typeId, userId));
//        return this.returnJSON("0", jsonObject, "", "");
//    }
//
//    @ApiOperation("获取设备")
//    @PostMapping({"/getDevNode"})
//    public JSONObject getDevNode(Authentication attributePrincipal,
//                                 @RequestParam(required = true) String branchLevel) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("devNode", this.nodeService.getAllDevNodeByBranchLevel(branchLevel));
//        return this.returnJSON("0", jsonObject, "", "");
//    }
//
//    @ApiOperation("检查设备是否开启策略梳理日志接收")
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "devId",
//            value = "设备id uuid",
//            required = true,
//            dataType = "String"
//    )})
//    @PostMapping({"/checkDevLog"})
//    public JSONObject checkDevLog(String devId) {
//        JSONObject jsonObject = new JSONObject();
//        String status = "-1";
//        String errcode = "";
//        String errmsg = "";
//        if (!AliStringUtils.isEmpty(devId)) {
//            PolicyDevConfigEntity entity = this.policyDevConfigService.getDevConfig(this.typeId, devId);
//            if (entity == null) {
//                errmsg = "请先设置日志接收管理配置！";
//                return this.returnJSON(status, jsonObject, errcode, errmsg);
//            }
//
//            if (!entity.getTypeStatus() && !entity.getLogCenterEnabled()) {
//                errmsg = "日志接收管理配置已关闭，暂未开启！";
//            } else {
//                Map<String, Object> map = this.policyDevConfigService.getQueryParameter(this.typeId, entity);
//                jsonObject.put("map", map);
//                status = "0";
//            }
//        }
//
//        return this.returnJSON(status, jsonObject, errcode, errmsg);
//    }
//
//    @ApiOperation("新增策略建议任务")
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "taskName",
//            value = "任务名称",
//            required = true,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "devId",
//            value = "设备id uuid,多个用英文逗号分隔",
//            required = true,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "timeType",
//            value = "时间类型：0最近一天；1最近一周；2最近两周；3最近一个月；4自定义；",
//            required = false,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "beginTime",
//            value = "梳理开始时间（时间类型为自定义时必填）",
//            required = false,
//            dataType = "Long"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "endTime",
//            value = "梳理结束时间（时间类型为自定义时必填）",
//            required = false,
//            dataType = "Long"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "inbound",
//            value = "源接口name",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "outbound",
//            value = "目的接口name",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "srcIp",
//            value = "源ip，IP或IP段，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "dstIp",
//            value = "目的ip，IP或IP段，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "filterSrcIp",
//            value = "过滤源ip，IP或IP段，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "filterDstIp",
//            value = "过滤目的ip，IP或IP段，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "filterService",
//            value = "过滤服务，格式：TCP/80，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "policyId",
//            value = "指定策略ID，可多个，逗号分隔",
//            required = false,
//            dataType = "String"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "isGather",
//            value = "是否合并",
//            required = true,
//            dataType = "Boolean"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "gatherObj",
//            value = "合并标准：0源地址；1目的地址；2源地址（精确）；3目的地址（精确）",
//            required = false,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "gatherMaskBit",
//            value = "合并掩码位",
//            required = false,
//            dataType = "Integer"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "isInternet",
//            value = "是否梳理互联网地址，默认否",
//            required = true,
//            dataType = "Boolean"
//    ), @ApiImplicitParam(
//            paramType = "query",
//            name = "policyUuid",
//            value = "策略id",
//            required = false,
//            dataType = "String"
//    )})
//    @PostMapping({"/add"})
//    public JSONObject add(PolicySuggestTaskEntity policySuggestTask) {
//        JSONObject jsonObject = new JSONObject();
//        String status = "-1";
//        String errcode = "";
//        String errmsg = "";
//        if (policySuggestTask == null) {
//            errcode = "Tfpcs_1_0002";
//            errmsg = "参数有误";
//            return this.returnJSON(status, jsonObject, errcode, errmsg);
//        } else {
//            int id;
//            int i;
//            if (!AliStringUtils.isEmpty(policySuggestTask.getDevId())) {
//                String[] devIds = policySuggestTask.getDevId().split(",");
//                String devIpStr = "";
//                String devdevAliasStr = "";
//
//                for(id = 0; id < devIds.length; ++id) {
//                    for(i = id + 1; i < devIds.length; ++i) {
//                        if (devIds[id].equals(devIds[i])) {
//                            errmsg = "设备重复请重新选择！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//                    }
//
//                    PolicyDevConfigEntity entity = this.policyDevConfigService.getDevConfig(this.typeId, devIds[id]);
//                    if (entity == null) {
//                        errmsg = "请先设置日志接收管理配置！";
//                        return this.returnJSON(status, jsonObject, errcode, errmsg);
//                    }
//
//                    Map<String, Object> map = this.policyDevConfigService.getQueryParameter(this.typeId, entity);
//                    String strDevIp = map.get("strDevIp") == null ? null : String.valueOf(map.get("strDevIp"));
//                    String devAlias = map.get("devAlias") == null ? null : String.valueOf(map.get("devAlias"));
//                    if (!devIpStr.contains(strDevIp)) {
//                        devIpStr = devIpStr + "," + strDevIp;
//                    }
//
//                    if (!AliStringUtils.isEmpty(devAlias) && !devdevAliasStr.contains(devAlias)) {
//                        devdevAliasStr = devdevAliasStr + "," + devAlias;
//                    }
//                }
//
//                policySuggestTask.setDevIp(devIpStr.substring(1));
//                if (devdevAliasStr.contains(",")) {
//                    devdevAliasStr = devdevAliasStr.substring(1);
//                }
//
//                policySuggestTask.setLogCenterUniqueNames(devdevAliasStr);
//            }
//
//            if (this.suggestTaskService.countPolicySuggestTaskByNoFinish() >= 5) {
//                errmsg = "当前未完成任务过多，请稍后再创建任务！";
//                return this.returnJSON(status, jsonObject, errcode, errmsg);
//            } else {
//                String regEx = "^[a-zA-Z0-9_\\.\\@\\-一-龥]+$";
//                Pattern p = Pattern.compile(regEx);
//                Matcher m = p.matcher(policySuggestTask.getTaskName());
//                if (!m.find()) {
//                    errmsg = "任务名称不能包含“@”、“-”、“.”和“_”以外的符号,请重新输入！";
//                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                } else if (this.suggestTaskService.findPolicySuggestTaskByTaskName(policySuggestTask.getTaskName()) > 0) {
//                    errmsg = "存在相同名称的任务！";
//                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                } else {
//                    if (policySuggestTask.getTimeType() != null && policySuggestTask.getTimeType() == 4) {
//                        if (policySuggestTask.getBeginTime() == null || policySuggestTask.getEndTime() == null) {
//                            errmsg = "自定义时间必须输入！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//
//                        policySuggestTask.setSuggestStartTime(new Date(policySuggestTask.getBeginTime() * 1000L));
//                        policySuggestTask.setSuggestEndTime(new Date((policySuggestTask.getEndTime() + 86400L) * 1000L));
//                    }
//
//                    String[] ipStrs;
//                    int j;
//                    if (!AliStringUtils.isEmpty(policySuggestTask.getSrcIp())) {
//                        ipStrs = policySuggestTask.getSrcIp().split(",");
//                        if (policySuggestTask.getIsGather() && policySuggestTask.getGatherObj() == 2 && TotemsStringUtils.endsWithIgnoreCase("any", policySuggestTask.getSrcIp())) {
//                            errmsg = "合并标准为源地址（精确）时，源地址数量不能大于5*256个！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (!IpUtils.isIP(ipStrs[i]) && !IpUtils.isIPSegment(ipStrs[i]) && !IpUtils.isIPRange(ipStrs[i])) {
//                                errmsg = "源地址中 " + ipStrs[i] + " 格式不正确！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            if (ipStrs[i].contains("/") && Integer.parseInt(ipStrs[i].split("/")[1]) < 16) {
//                                errmsg = "源地址网段只支持掩码位大于等于16！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            for(j = i + 1; j < ipStrs.length; ++j) {
//                                if (ipStrs[i].equals(ipStrs[j])) {
//                                    errmsg = "源地址存在重复元素！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            }
//                        }
//                    }
//
//                    if (!AliStringUtils.isEmpty(policySuggestTask.getDstIp())) {
//                        ipStrs = policySuggestTask.getDstIp().split(",");
//                        if (policySuggestTask.getIsGather() && policySuggestTask.getGatherObj() == 3 && TotemsStringUtils.endsWithIgnoreCase("any", policySuggestTask.getDstIp())) {
//                            errmsg = "合并标准为目的地址（精确）时，目的地址数量不能大于5*256个";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (!IpUtils.isIP(ipStrs[i]) && !IpUtils.isIPSegment(ipStrs[i]) && !IpUtils.isIPRange(ipStrs[i])) {
//                                errmsg = "目的地址中 " + ipStrs[i] + " 格式不正确！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            if (ipStrs[i].contains("/") && Integer.parseInt(ipStrs[i].split("/")[1]) < 16) {
//                                errmsg = "目的地址网段只支持掩码位大于等于16！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            for(j = i + 1; j < ipStrs.length; ++j) {
//                                if (ipStrs[i].equals(ipStrs[j])) {
//                                    errmsg = "目的地址存在重复元素！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            }
//                        }
//                    }
//
//                    if (!AliStringUtils.isEmpty(policySuggestTask.getFilterSrcIp())) {
//                        ipStrs = policySuggestTask.getFilterSrcIp().split(",");
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (!IpUtils.isIP(ipStrs[i]) && !IpUtils.isIPSegment(ipStrs[i]) && !IpUtils.isIPRange(ipStrs[i])) {
//                                errmsg = "排除源地址中 " + ipStrs[i] + " 格式不正确！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            if (ipStrs[i].contains("/") && Integer.parseInt(ipStrs[i].split("/")[1]) < 16) {
//                                errmsg = "排除源地址网段只支持掩码位大于等于16！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            for(j = i + 1; j < ipStrs.length; ++j) {
//                                if (ipStrs[i].equals(ipStrs[j])) {
//                                    errmsg = "排除源地址存在重复元素！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            }
//                        }
//                    }
//
//                    if (!AliStringUtils.isEmpty(policySuggestTask.getFilterDstIp())) {
//                        ipStrs = policySuggestTask.getFilterDstIp().split(",");
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (!IpUtils.isIP(ipStrs[i]) && !IpUtils.isIPSegment(ipStrs[i]) && !IpUtils.isIPRange(ipStrs[i])) {
//                                errmsg = "排除目的地址中 " + ipStrs[i] + " 格式不正确！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            if (ipStrs[i].contains("/") && Integer.parseInt(ipStrs[i].split("/")[1]) < 16) {
//                                errmsg = "排除目的地址网段只支持掩码位大于等于16！";
//                                return this.returnJSON(status, jsonObject, errcode, errmsg);
//                            }
//
//                            for(j = i + 1; j < ipStrs.length; ++j) {
//                                if (ipStrs[i].equals(ipStrs[j])) {
//                                    errmsg = "排除目的地址存在重复元素！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            }
//                        }
//                    }
//                    String[] ipStrs1;
//                    long num;
//                    if (policySuggestTask.getIsGather() && policySuggestTask.getGatherObj() == 2) {
//                        if (AliStringUtils.isEmpty(policySuggestTask.getSrcIp())) {
//                            errmsg = "合并标准为源地址（精确）时，源地址不能为空！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//
//                        ipStrs = policySuggestTask.getSrcIp().split(",");
//                        num = 0L;
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (ipStrs[i].contains("-")) {
//                                ipStrs1 = ipStrs[i].split("-");
//                                num += IpUtils.IPv4StringToNum(ipStrs1[1]) - IpUtils.IPv4StringToNum(ipStrs1[0]);
//                            } else if (ipStrs[i].contains("/")) {
//                                num += IpUtils.IPv4StringToNum(IpUtils.getEndIp(ipStrs[i])) - IpUtils.IPv4StringToNum(IpUtils.getStartIp(ipStrs[i]));
//                            } else {
//                                ++num;
//                            }
//                        }
//
//                        if (num > (long)(256 * this.validateAccurateMergeIpNum)) {
//                            errmsg = "合并标准为源地址（精确）时，源地址数量不能大于" + this.validateAccurateMergeIpNum + "*256个！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//                    }
//
//                    if (policySuggestTask.getIsGather() && policySuggestTask.getGatherObj() == 3) {
//                        if (AliStringUtils.isEmpty(policySuggestTask.getDstIp())) {
//                            errmsg = "合并标准目的地址（精确）时，目的地址不能为空！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//
//                        ipStrs = policySuggestTask.getDstIp().split(",");
//                        num = 0L;
//
//                        for(i = 0; i < ipStrs.length; ++i) {
//                            if (ipStrs[i].contains("-")) {
//                                ipStrs1 = ipStrs[i].split("-");
//                                num += IpUtils.IPv4StringToNum(ipStrs1[1]) - IpUtils.IPv4StringToNum(ipStrs1[0]);
//                            } else if (ipStrs[i].contains("/")) {
//                                num += IpUtils.IPv4StringToNum(IpUtils.getEndIp(ipStrs[i])) - IpUtils.IPv4StringToNum(IpUtils.getStartIp(ipStrs[i]));
//                            } else {
//                                ++num;
//                            }
//                        }
//
//                        if (num > (long)(256 * this.validateAccurateMergeIpNum)) {
//                            errmsg = "合并标准为目的地址（精确）时，目的地址数量不能大于" + this.validateAccurateMergeIpNum + "*256个！";
//                            return this.returnJSON(status, jsonObject, errcode, errmsg);
//                        }
//                    }
//
//                    if (!AliStringUtils.isEmpty(policySuggestTask.getFilterService())) {
//                        Map<String, String> protocolMap = this.protocolMapConfig.getStrMap();
//                        String[] filterServices = policySuggestTask.getFilterService().split(",");
//                        String[] var27 = filterServices;
//                        i = filterServices.length;
//
//                        for(int var28 = 0; var28 < i; ++var28) {
//                            String service = var27[var28];
//                            String[] services = service.split("/");
//                            if (services.length == 1) {
//                                if (AliStringUtils.isEmpty((String)protocolMap.get(services[0].toUpperCase()))) {
//                                    errmsg = "排除服务输入内容不正确！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            } else {
//                                if (services.length != 2) {
//                                    errmsg = "排除服务输入内容不正确！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//
//                                if (AliStringUtils.isEmpty((String)protocolMap.get(services[0].toUpperCase()))) {
//                                    errmsg = "排除服务输入内容不正确！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//
//                                if (services[1].contains("-")) {
//                                    String[] ports = services[1].split("-");
//                                    if (!AliStringUtils.isNumeric(ports[0]) || !AliStringUtils.isNumeric(ports[1]) || Integer.parseInt(ports[0]) < 0 || Integer.parseInt(ports[0]) > 65535 || Integer.parseInt(ports[1]) < 0 || Integer.parseInt(ports[1]) > 65535) {
//                                        errmsg = "排除服务输入内容不正确！";
//                                        return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                    }
//                                } else if (!AliStringUtils.isNumeric(services[1]) || Integer.parseInt(services[1]) < 0 || Integer.parseInt(services[1]) > 65535) {
//                                    errmsg = "排除服务输入内容不正确！";
//                                    return this.returnJSON(status, jsonObject, errcode, errmsg);
//                                }
//                            }
//                        }
//                    }
//
//                    if (policySuggestTask.getIsGather() && policySuggestTask.getGatherMaskBit() != null && (policySuggestTask.getGatherMaskBit() < 16 || policySuggestTask.getGatherMaskBit() > 32)) {
//                        errmsg = "掩码位只支持16-32位";
//                        return this.returnJSON(status, jsonObject, errcode, errmsg);
//                    } else {
//                        id = this.suggestTaskService.addPolicySuggestTask(policySuggestTask);
//                        if (id > 0) {
//                            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "新增策略梳理任务【" + policySuggestTask.getTaskName() + "】成功。");
//                        } else {
//                            this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "新增策略梳理任务【" + policySuggestTask.getTaskName() + "】失败。");
//                        }
//
//                        jsonObject.put("taskId", id);
//                        status = "0";
//                        return this.returnJSON(status, jsonObject, errcode, errmsg);
//                    }
//                }
//            }
//        }
//    }
//
//    @ApiOperation("重新梳理策略建议任务")
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "taskId",
//            value = "任务Id",
//            required = true,
//            dataType = "Integer"
//    )})
//    @PostMapping({"/again"})
//    public JSONObject again(Integer taskId) {
//        JSONObject jsonObject = new JSONObject();
//        String status = "-1";
//        String errcode = "";
//        String errmsg = "";
//        if (taskId == null) {
//            errcode = "Tfpcs_1_0002";
//            errmsg = "参数有误";
//            return this.returnJSON(status, jsonObject, errcode, errmsg);
//        } else {
//            PolicySuggestTaskEntity policySuggestTask = this.suggestTaskService.findPolicySuggestTaskById(taskId);
//            if (policySuggestTask == null) {
//                errcode = "Tfpcs_1_0002";
//                errmsg = "参数有误";
//                return this.returnJSON(status, jsonObject, errcode, errmsg);
//            } else if (this.suggestTaskService.countPolicySuggestTaskByNoFinish() >= 5) {
//                errmsg = "当前未完成任务过多，请稍后再创建任务！";
//                return this.returnJSON(status, jsonObject, errcode, errmsg);
//            } else {
//                File fileZip = new File(this.dirPath + policySuggestTask.getTaskName() + "梳理结果.zip");
//                if (fileZip.exists()) {
//                    fileZip.delete();
//                }
//
//                File fileXls = new File(this.dirPath + policySuggestTask.getTaskName() + "梳理结果.xlsx");
//                if (fileXls.exists()) {
//                    fileXls.delete();
//                }
//
//                int id = this.suggestTaskService.addPolicySuggestTask(policySuggestTask);
//                if (id > 0) {
//                    this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "重新策略梳理任务【" + policySuggestTask.getTaskName() + "】成功。");
//                } else {
//                    this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "重新策略梳理任务【" + policySuggestTask.getTaskName() + "】失败。");
//                }
//
//                jsonObject.put("taskId", id);
//                status = "0";
//                return this.returnJSON(status, jsonObject, errcode, errmsg);
//            }
//        }
//    }
//
//    @ApiImplicitParams({@ApiImplicitParam(
//            paramType = "query",
//            name = "taskIds",
//            value = "taskIds",
//            required = true,
//            dataType = "String"
//    )})
//    @ApiOperation("删除策略建议任务")
//    @PostMapping({"/delete"})
//    public JSONObject delete(String taskIds) {
//        JSONObject jsonObject = new JSONObject();
//        String status = "-1";
//        String errcode = "";
//        String errmsg = "";
//        String[] taskIdsStr = taskIds.split(",");
//        List<Integer> taskIdList = new ArrayList();
//
//        for(int i = 0; i < taskIdsStr.length; ++i) {
//            taskIdList.add(Integer.parseInt(taskIdsStr[i]));
//        }
//
//        Integer[] ids = (Integer[])taskIdList.toArray(new Integer[0]);
//        if (this.suggestTaskService.checkPolicySuggestTaskByRunning(ids)) {
//            errmsg = "您选择的任务中有正在运行的任务，请重新选择！";
//            return this.returnJSON(status, jsonObject, errcode, errmsg);
//        } else {
//            int num = this.suggestTaskService.deletePolicySuggestTask(ids);
//            if (num > 0) {
//                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "删除策略梳理任务" + Arrays.toString(ids) + "成功。");
//            } else {
//                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.POLICY_ANALYSE.getId(), "删除策略梳理任务" + Arrays.toString(ids) + "失败。");
//            }
//
//            jsonObject.put("num", num);
//            status = "0";
//            return this.returnJSON(status, jsonObject, errcode, errmsg);
//        }
//    }
//}
