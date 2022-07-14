//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.common.dto.commandline;

import com.abtnetworks.totems.common.enums.MoveSeatEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel("DNat数据实体")
public class DNatPolicyDTO {
    @ApiModelProperty("命令行下发任务主键id")
    Integer id;
    @ApiModelProperty("任务ID")
    Integer taskId;
    @ApiModelProperty("主题（工单号）")
    String theme;
    @ApiModelProperty("设备UUID")
    String deviceUuid;
    @ApiModelProperty("开始时间")
    String startTime;
    @ApiModelProperty("结束时间")
    String endTime;
    @ApiModelProperty("源地址")
    String srcIp;
    @ApiModelProperty("源地址描述")
    String srcIpSystem;
    @ApiModelProperty("目的地址")
    String dstIp;
    @ApiModelProperty("目的地址描述")
    String dstIpSystem;
    @ApiModelProperty("服务")
    List<ServiceDTO> serviceList;
    @ApiModelProperty("转换后地址")
    String postIpAddress;
    @ApiModelProperty("转换后目的地址描述")
    String postDstIpSystem;
    @ApiModelProperty("转换后端口")
    String postPort;
    @ApiModelProperty("源域")
    String srcZone;
    @ApiModelProperty("入接口")
    String srcItf;
    @ApiModelProperty("目的域")
    String dstZone;
    @ApiModelProperty("出接口")
    String dstItf;
    @ApiModelProperty("策略描述")
    String description;
    @ApiModelProperty("现有服务对象名称")
    String serviceObjectName;
    @ApiModelProperty("转换后服务对象名称")
    String postServiceObjectName;
    @ApiModelProperty("现有源地址对象名称")
    String srcAddressObjectName;
    @ApiModelProperty("现有目的地址对象名称")
    String dstAddressObjectName;
    @ApiModelProperty("转换后地址对象名称")
    String postAddressObjectName;
    @ApiModelProperty("是否为虚墙")
    boolean isVsys;
    @ApiModelProperty("虚墙名称")
    String vsysName;
    @ApiModelProperty("是否为虚墙")
    boolean hasVsys;
    @ApiModelProperty("是否创建对象,true标识创建, false不创建，直接引用")
    boolean createObjFlag;
    @ApiModelProperty("移动位置")
    MoveSeatEnum moveSeatEnum;
    @ApiModelProperty("交换位置的规则名或id")
    String swapRuleNameId;
    @ApiModelProperty("需要新建的服务列表")
    List<ServiceDTO> restServiceList = new ArrayList();
    @ApiModelProperty("已存在服务名称列表")
    List<String> existServiceNameList = new ArrayList();
    @ApiModelProperty("已存在转换后服务名称列表")
    List<String> existPostServiceNameList = new ArrayList();
    @ApiModelProperty("需要新建转换后的服务列表")
    List<ServiceDTO> restPostServiceList = new ArrayList();
    @ApiModelProperty("已存在源地址对象列表")
    List<String> existSrcAddressList = new ArrayList();
    @ApiModelProperty("还需要创建源地址对象列表")
    List<String> restSrcAddressList = new ArrayList();
    @ApiModelProperty("已存在转换后源地址对象列表")
    List<String> existPostSrcAddressList = new ArrayList();
    @ApiModelProperty("还需要创建转换后地址对象列表")
    List<String> restPostSrcAddressList = new ArrayList();
    @ApiModelProperty("已存在目的地址对象列表")
    List<String> existDstAddressList = new ArrayList();
    @ApiModelProperty("还需要创建目的地址对象列表")
    List<String> restDstAddressList = new ArrayList();
    @ApiModelProperty("飞塔当前策略id")
    String currentId;
    @ApiModelProperty("入接口别名 ")
    private String inDevItfAlias;
    @ApiModelProperty("出接口别名 ")
    private String outDevItfAlias;
    @ApiModelProperty("已存在转换后目的地址对象列表")
    List<String> existPostDstAddressList = new ArrayList();
    @ApiModelProperty("还需要创建转换后目的地址对象列表")
    List<String> restPostDstAddressList = new ArrayList();
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    @ApiModelProperty("华3V7当前地址池id")
    String currentAddressGroupId;
    @ApiModelProperty("本条策略创建的服务对象名称集合")
    List<String> serviceObjectNameList;
    @ApiModelProperty("本条策略创建的地址对象名称集合")
    List<String> addressObjectNameList;
    @ApiModelProperty("本条策略创建的地址组对象名称集合")
    List<String> addressObjectGroupNameList;
    @ApiModelProperty("本条策略创建的服务组对象名称集合")
    List<String> serviceObjectGroupNameList;
    @ApiModelProperty("命令行中的策略名称  策略名称在生成命令的时候要根据工单号_AO_随机数,要传到后面流程使用")
    String policyName;
    @ApiModelProperty("回滚查询命令行")
    String rollbackShowCmd;
    @ApiModelProperty("用于不同类型地址回滚命令行。比如每种地址类型都不同，拼接不用规则的命令行地址。 地址类型map ，key为地址名称，value为地址的类型 host主机，sub子网 rang 范围")
    Map<String, String> addressTypeMap = new HashMap();
    String userName;// Metoo
    String branchLevel;// Metoo

    public DNatPolicyDTO() {
    }

    public String getBranchLevel(){
        return this.branchLevel;
    }
    public String getUserName(){
        return this.userName;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getDeviceUuid() {
        return this.deviceUuid;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public String getSrcIp() {
        return this.srcIp;
    }

    public String getSrcIpSystem() {
        return this.srcIpSystem;
    }

    public String getDstIp() {
        return this.dstIp;
    }

    public String getDstIpSystem() {
        return this.dstIpSystem;
    }

    public List<ServiceDTO> getServiceList() {
        return this.serviceList;
    }

    public String getPostIpAddress() {
        return this.postIpAddress;
    }

    public String getPostDstIpSystem() {
        return this.postDstIpSystem;
    }

    public String getPostPort() {
        return this.postPort;
    }

    public String getSrcZone() {
        return this.srcZone;
    }

    public String getSrcItf() {
        return this.srcItf;
    }

    public String getDstZone() {
        return this.dstZone;
    }

    public String getDstItf() {
        return this.dstItf;
    }

    public String getDescription() {
        return this.description;
    }

    public String getServiceObjectName() {
        return this.serviceObjectName;
    }

    public String getPostServiceObjectName() {
        return this.postServiceObjectName;
    }

    public String getSrcAddressObjectName() {
        return this.srcAddressObjectName;
    }

    public String getDstAddressObjectName() {
        return this.dstAddressObjectName;
    }

    public String getPostAddressObjectName() {
        return this.postAddressObjectName;
    }

    public boolean isVsys() {
        return this.isVsys;
    }

    public String getVsysName() {
        return this.vsysName;
    }

    public boolean isHasVsys() {
        return this.hasVsys;
    }

    public boolean isCreateObjFlag() {
        return this.createObjFlag;
    }

    public MoveSeatEnum getMoveSeatEnum() {
        return this.moveSeatEnum;
    }

    public String getSwapRuleNameId() {
        return this.swapRuleNameId;
    }

    public List<ServiceDTO> getRestServiceList() {
        return this.restServiceList;
    }

    public List<String> getExistServiceNameList() {
        return this.existServiceNameList;
    }

    public List<String> getExistPostServiceNameList() {
        return this.existPostServiceNameList;
    }

    public List<ServiceDTO> getRestPostServiceList() {
        return this.restPostServiceList;
    }

    public List<String> getExistSrcAddressList() {
        return this.existSrcAddressList;
    }

    public List<String> getRestSrcAddressList() {
        return this.restSrcAddressList;
    }

    public List<String> getExistPostSrcAddressList() {
        return this.existPostSrcAddressList;
    }

    public List<String> getRestPostSrcAddressList() {
        return this.restPostSrcAddressList;
    }

    public List<String> getExistDstAddressList() {
        return this.existDstAddressList;
    }

    public List<String> getRestDstAddressList() {
        return this.restDstAddressList;
    }

    public String getCurrentId() {
        return this.currentId;
    }

    public String getInDevItfAlias() {
        return this.inDevItfAlias;
    }

    public String getOutDevItfAlias() {
        return this.outDevItfAlias;
    }

    public List<String> getExistPostDstAddressList() {
        return this.existPostDstAddressList;
    }

    public List<String> getRestPostDstAddressList() {
        return this.restPostDstAddressList;
    }

    public Integer getIpType() {
        return this.ipType;
    }

    public String getCurrentAddressGroupId() {
        return this.currentAddressGroupId;
    }

    public List<String> getServiceObjectNameList() {
        return this.serviceObjectNameList;
    }

    public List<String> getAddressObjectNameList() {
        return this.addressObjectNameList;
    }

    public List<String> getAddressObjectGroupNameList() {
        return this.addressObjectGroupNameList;
    }

    public List<String> getServiceObjectGroupNameList() {
        return this.serviceObjectGroupNameList;
    }

    public String getPolicyName() {
        return this.policyName;
    }

    public String getRollbackShowCmd() {
        return this.rollbackShowCmd;
    }

    public Map<String, String> getAddressTypeMap() {
        return this.addressTypeMap;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setBranchLevel(String branchLevel){
        this.branchLevel=branchLevel;
    }
    public void setId(final Integer id) {
        this.id = id;
    }

    public void setTaskId(final Integer taskId) {
        this.taskId = taskId;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public void setDeviceUuid(final String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    public void setSrcIp(final String srcIp) {
        this.srcIp = srcIp;
    }

    public void setSrcIpSystem(final String srcIpSystem) {
        this.srcIpSystem = srcIpSystem;
    }

    public void setDstIp(final String dstIp) {
        this.dstIp = dstIp;
    }

    public void setDstIpSystem(final String dstIpSystem) {
        this.dstIpSystem = dstIpSystem;
    }

    public void setServiceList(final List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public void setPostIpAddress(final String postIpAddress) {
        this.postIpAddress = postIpAddress;
    }

    public void setPostDstIpSystem(final String postDstIpSystem) {
        this.postDstIpSystem = postDstIpSystem;
    }

    public void setPostPort(final String postPort) {
        this.postPort = postPort;
    }

    public void setSrcZone(final String srcZone) {
        this.srcZone = srcZone;
    }

    public void setSrcItf(final String srcItf) {
        this.srcItf = srcItf;
    }

    public void setDstZone(final String dstZone) {
        this.dstZone = dstZone;
    }

    public void setDstItf(final String dstItf) {
        this.dstItf = dstItf;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setServiceObjectName(final String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
    }

    public void setPostServiceObjectName(final String postServiceObjectName) {
        this.postServiceObjectName = postServiceObjectName;
    }

    public void setSrcAddressObjectName(final String srcAddressObjectName) {
        this.srcAddressObjectName = srcAddressObjectName;
    }

    public void setDstAddressObjectName(final String dstAddressObjectName) {
        this.dstAddressObjectName = dstAddressObjectName;
    }

    public void setPostAddressObjectName(final String postAddressObjectName) {
        this.postAddressObjectName = postAddressObjectName;
    }

    public void setVsys(final boolean isVsys) {
        this.isVsys = isVsys;
    }

    public void setVsysName(final String vsysName) {
        this.vsysName = vsysName;
    }

    public void setHasVsys(final boolean hasVsys) {
        this.hasVsys = hasVsys;
    }

    public void setCreateObjFlag(final boolean createObjFlag) {
        this.createObjFlag = createObjFlag;
    }

    public void setMoveSeatEnum(final MoveSeatEnum moveSeatEnum) {
        this.moveSeatEnum = moveSeatEnum;
    }

    public void setSwapRuleNameId(final String swapRuleNameId) {
        this.swapRuleNameId = swapRuleNameId;
    }

    public void setRestServiceList(final List<ServiceDTO> restServiceList) {
        this.restServiceList = restServiceList;
    }

    public void setExistServiceNameList(final List<String> existServiceNameList) {
        this.existServiceNameList = existServiceNameList;
    }

    public void setExistPostServiceNameList(final List<String> existPostServiceNameList) {
        this.existPostServiceNameList = existPostServiceNameList;
    }

    public void setRestPostServiceList(final List<ServiceDTO> restPostServiceList) {
        this.restPostServiceList = restPostServiceList;
    }

    public void setExistSrcAddressList(final List<String> existSrcAddressList) {
        this.existSrcAddressList = existSrcAddressList;
    }

    public void setRestSrcAddressList(final List<String> restSrcAddressList) {
        this.restSrcAddressList = restSrcAddressList;
    }

    public void setExistPostSrcAddressList(final List<String> existPostSrcAddressList) {
        this.existPostSrcAddressList = existPostSrcAddressList;
    }

    public void setRestPostSrcAddressList(final List<String> restPostSrcAddressList) {
        this.restPostSrcAddressList = restPostSrcAddressList;
    }

    public void setExistDstAddressList(final List<String> existDstAddressList) {
        this.existDstAddressList = existDstAddressList;
    }

    public void setRestDstAddressList(final List<String> restDstAddressList) {
        this.restDstAddressList = restDstAddressList;
    }

    public void setCurrentId(final String currentId) {
        this.currentId = currentId;
    }

    public void setInDevItfAlias(final String inDevItfAlias) {
        this.inDevItfAlias = inDevItfAlias;
    }

    public void setOutDevItfAlias(final String outDevItfAlias) {
        this.outDevItfAlias = outDevItfAlias;
    }

    public void setExistPostDstAddressList(final List<String> existPostDstAddressList) {
        this.existPostDstAddressList = existPostDstAddressList;
    }

    public void setRestPostDstAddressList(final List<String> restPostDstAddressList) {
        this.restPostDstAddressList = restPostDstAddressList;
    }

    public void setIpType(final Integer ipType) {
        this.ipType = ipType;
    }

    public void setCurrentAddressGroupId(final String currentAddressGroupId) {
        this.currentAddressGroupId = currentAddressGroupId;
    }

    public void setServiceObjectNameList(final List<String> serviceObjectNameList) {
        this.serviceObjectNameList = serviceObjectNameList;
    }

    public void setAddressObjectNameList(final List<String> addressObjectNameList) {
        this.addressObjectNameList = addressObjectNameList;
    }

    public void setAddressObjectGroupNameList(final List<String> addressObjectGroupNameList) {
        this.addressObjectGroupNameList = addressObjectGroupNameList;
    }

    public void setServiceObjectGroupNameList(final List<String> serviceObjectGroupNameList) {
        this.serviceObjectGroupNameList = serviceObjectGroupNameList;
    }

    public void setPolicyName(final String policyName) {
        this.policyName = policyName;
    }

    public void setRollbackShowCmd(final String rollbackShowCmd) {
        this.rollbackShowCmd = rollbackShowCmd;
    }

    public void setAddressTypeMap(final Map<String, String> addressTypeMap) {
        this.addressTypeMap = addressTypeMap;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DNatPolicyDTO)) {
            return false;
        } else {
            DNatPolicyDTO other = (DNatPolicyDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label635: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label635;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label635;
                    }

                    return false;
                }

                Object this$taskId = this.getTaskId();
                Object other$taskId = other.getTaskId();
                if (this$taskId == null) {
                    if (other$taskId != null) {
                        return false;
                    }
                } else if (!this$taskId.equals(other$taskId)) {
                    return false;
                }

                Object this$theme = this.getTheme();
                Object other$theme = other.getTheme();
                if (this$theme == null) {
                    if (other$theme != null) {
                        return false;
                    }
                } else if (!this$theme.equals(other$theme)) {
                    return false;
                }

                label614: {
                    Object this$deviceUuid = this.getDeviceUuid();
                    Object other$deviceUuid = other.getDeviceUuid();
                    if (this$deviceUuid == null) {
                        if (other$deviceUuid == null) {
                            break label614;
                        }
                    } else if (this$deviceUuid.equals(other$deviceUuid)) {
                        break label614;
                    }

                    return false;
                }

                label607: {
                    Object this$startTime = this.getStartTime();
                    Object other$startTime = other.getStartTime();
                    if (this$startTime == null) {
                        if (other$startTime == null) {
                            break label607;
                        }
                    } else if (this$startTime.equals(other$startTime)) {
                        break label607;
                    }

                    return false;
                }

                label600: {
                    Object this$endTime = this.getEndTime();
                    Object other$endTime = other.getEndTime();
                    if (this$endTime == null) {
                        if (other$endTime == null) {
                            break label600;
                        }
                    } else if (this$endTime.equals(other$endTime)) {
                        break label600;
                    }

                    return false;
                }

                Object this$srcIp = this.getSrcIp();
                Object other$srcIp = other.getSrcIp();
                if (this$srcIp == null) {
                    if (other$srcIp != null) {
                        return false;
                    }
                } else if (!this$srcIp.equals(other$srcIp)) {
                    return false;
                }

                label586: {
                    Object this$srcIpSystem = this.getSrcIpSystem();
                    Object other$srcIpSystem = other.getSrcIpSystem();
                    if (this$srcIpSystem == null) {
                        if (other$srcIpSystem == null) {
                            break label586;
                        }
                    } else if (this$srcIpSystem.equals(other$srcIpSystem)) {
                        break label586;
                    }

                    return false;
                }

                Object this$dstIp = this.getDstIp();
                Object other$dstIp = other.getDstIp();
                if (this$dstIp == null) {
                    if (other$dstIp != null) {
                        return false;
                    }
                } else if (!this$dstIp.equals(other$dstIp)) {
                    return false;
                }

                label572: {
                    Object this$dstIpSystem = this.getDstIpSystem();
                    Object other$dstIpSystem = other.getDstIpSystem();
                    if (this$dstIpSystem == null) {
                        if (other$dstIpSystem == null) {
                            break label572;
                        }
                    } else if (this$dstIpSystem.equals(other$dstIpSystem)) {
                        break label572;
                    }

                    return false;
                }

                Object this$serviceList = this.getServiceList();
                Object other$serviceList = other.getServiceList();
                if (this$serviceList == null) {
                    if (other$serviceList != null) {
                        return false;
                    }
                } else if (!this$serviceList.equals(other$serviceList)) {
                    return false;
                }

                Object this$postIpAddress = this.getPostIpAddress();
                Object other$postIpAddress = other.getPostIpAddress();
                if (this$postIpAddress == null) {
                    if (other$postIpAddress != null) {
                        return false;
                    }
                } else if (!this$postIpAddress.equals(other$postIpAddress)) {
                    return false;
                }

                label551: {
                    Object this$postDstIpSystem = this.getPostDstIpSystem();
                    Object other$postDstIpSystem = other.getPostDstIpSystem();
                    if (this$postDstIpSystem == null) {
                        if (other$postDstIpSystem == null) {
                            break label551;
                        }
                    } else if (this$postDstIpSystem.equals(other$postDstIpSystem)) {
                        break label551;
                    }

                    return false;
                }

                label544: {
                    Object this$postPort = this.getPostPort();
                    Object other$postPort = other.getPostPort();
                    if (this$postPort == null) {
                        if (other$postPort == null) {
                            break label544;
                        }
                    } else if (this$postPort.equals(other$postPort)) {
                        break label544;
                    }

                    return false;
                }

                Object this$srcZone = this.getSrcZone();
                Object other$srcZone = other.getSrcZone();
                if (this$srcZone == null) {
                    if (other$srcZone != null) {
                        return false;
                    }
                } else if (!this$srcZone.equals(other$srcZone)) {
                    return false;
                }

                Object this$srcItf = this.getSrcItf();
                Object other$srcItf = other.getSrcItf();
                if (this$srcItf == null) {
                    if (other$srcItf != null) {
                        return false;
                    }
                } else if (!this$srcItf.equals(other$srcItf)) {
                    return false;
                }

                label523: {
                    Object this$dstZone = this.getDstZone();
                    Object other$dstZone = other.getDstZone();
                    if (this$dstZone == null) {
                        if (other$dstZone == null) {
                            break label523;
                        }
                    } else if (this$dstZone.equals(other$dstZone)) {
                        break label523;
                    }

                    return false;
                }

                Object this$dstItf = this.getDstItf();
                Object other$dstItf = other.getDstItf();
                if (this$dstItf == null) {
                    if (other$dstItf != null) {
                        return false;
                    }
                } else if (!this$dstItf.equals(other$dstItf)) {
                    return false;
                }

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                label502: {
                    Object this$serviceObjectName = this.getServiceObjectName();
                    Object other$serviceObjectName = other.getServiceObjectName();
                    if (this$serviceObjectName == null) {
                        if (other$serviceObjectName == null) {
                            break label502;
                        }
                    } else if (this$serviceObjectName.equals(other$serviceObjectName)) {
                        break label502;
                    }

                    return false;
                }

                label495: {
                    Object this$postServiceObjectName = this.getPostServiceObjectName();
                    Object other$postServiceObjectName = other.getPostServiceObjectName();
                    if (this$postServiceObjectName == null) {
                        if (other$postServiceObjectName == null) {
                            break label495;
                        }
                    } else if (this$postServiceObjectName.equals(other$postServiceObjectName)) {
                        break label495;
                    }

                    return false;
                }

                label488: {
                    Object this$srcAddressObjectName = this.getSrcAddressObjectName();
                    Object other$srcAddressObjectName = other.getSrcAddressObjectName();
                    if (this$srcAddressObjectName == null) {
                        if (other$srcAddressObjectName == null) {
                            break label488;
                        }
                    } else if (this$srcAddressObjectName.equals(other$srcAddressObjectName)) {
                        break label488;
                    }

                    return false;
                }

                Object this$dstAddressObjectName = this.getDstAddressObjectName();
                Object other$dstAddressObjectName = other.getDstAddressObjectName();
                if (this$dstAddressObjectName == null) {
                    if (other$dstAddressObjectName != null) {
                        return false;
                    }
                } else if (!this$dstAddressObjectName.equals(other$dstAddressObjectName)) {
                    return false;
                }

                label474: {
                    Object this$postAddressObjectName = this.getPostAddressObjectName();
                    Object other$postAddressObjectName = other.getPostAddressObjectName();
                    if (this$postAddressObjectName == null) {
                        if (other$postAddressObjectName == null) {
                            break label474;
                        }
                    } else if (this$postAddressObjectName.equals(other$postAddressObjectName)) {
                        break label474;
                    }

                    return false;
                }

                if (this.isVsys() != other.isVsys()) {
                    return false;
                } else {
                    Object this$vsysName = this.getVsysName();
                    Object other$vsysName = other.getVsysName();
                    if (this$vsysName == null) {
                        if (other$vsysName != null) {
                            return false;
                        }
                    } else if (!this$vsysName.equals(other$vsysName)) {
                        return false;
                    }

                    if (this.isHasVsys() != other.isHasVsys()) {
                        return false;
                    } else if (this.isCreateObjFlag() != other.isCreateObjFlag()) {
                        return false;
                    } else {
                        label456: {
                            Object this$moveSeatEnum = this.getMoveSeatEnum();
                            Object other$moveSeatEnum = other.getMoveSeatEnum();
                            if (this$moveSeatEnum == null) {
                                if (other$moveSeatEnum == null) {
                                    break label456;
                                }
                            } else if (this$moveSeatEnum.equals(other$moveSeatEnum)) {
                                break label456;
                            }

                            return false;
                        }

                        Object this$swapRuleNameId = this.getSwapRuleNameId();
                        Object other$swapRuleNameId = other.getSwapRuleNameId();
                        if (this$swapRuleNameId == null) {
                            if (other$swapRuleNameId != null) {
                                return false;
                            }
                        } else if (!this$swapRuleNameId.equals(other$swapRuleNameId)) {
                            return false;
                        }

                        label442: {
                            Object this$restServiceList = this.getRestServiceList();
                            Object other$restServiceList = other.getRestServiceList();
                            if (this$restServiceList == null) {
                                if (other$restServiceList == null) {
                                    break label442;
                                }
                            } else if (this$restServiceList.equals(other$restServiceList)) {
                                break label442;
                            }

                            return false;
                        }

                        Object this$existServiceNameList = this.getExistServiceNameList();
                        Object other$existServiceNameList = other.getExistServiceNameList();
                        if (this$existServiceNameList == null) {
                            if (other$existServiceNameList != null) {
                                return false;
                            }
                        } else if (!this$existServiceNameList.equals(other$existServiceNameList)) {
                            return false;
                        }

                        Object this$existPostServiceNameList = this.getExistPostServiceNameList();
                        Object other$existPostServiceNameList = other.getExistPostServiceNameList();
                        if (this$existPostServiceNameList == null) {
                            if (other$existPostServiceNameList != null) {
                                return false;
                            }
                        } else if (!this$existPostServiceNameList.equals(other$existPostServiceNameList)) {
                            return false;
                        }

                        Object this$restPostServiceList = this.getRestPostServiceList();
                        Object other$restPostServiceList = other.getRestPostServiceList();
                        if (this$restPostServiceList == null) {
                            if (other$restPostServiceList != null) {
                                return false;
                            }
                        } else if (!this$restPostServiceList.equals(other$restPostServiceList)) {
                            return false;
                        }

                        label414: {
                            Object this$existSrcAddressList = this.getExistSrcAddressList();
                            Object other$existSrcAddressList = other.getExistSrcAddressList();
                            if (this$existSrcAddressList == null) {
                                if (other$existSrcAddressList == null) {
                                    break label414;
                                }
                            } else if (this$existSrcAddressList.equals(other$existSrcAddressList)) {
                                break label414;
                            }

                            return false;
                        }

                        label407: {
                            Object this$restSrcAddressList = this.getRestSrcAddressList();
                            Object other$restSrcAddressList = other.getRestSrcAddressList();
                            if (this$restSrcAddressList == null) {
                                if (other$restSrcAddressList == null) {
                                    break label407;
                                }
                            } else if (this$restSrcAddressList.equals(other$restSrcAddressList)) {
                                break label407;
                            }

                            return false;
                        }

                        Object this$existPostSrcAddressList = this.getExistPostSrcAddressList();
                        Object other$existPostSrcAddressList = other.getExistPostSrcAddressList();
                        if (this$existPostSrcAddressList == null) {
                            if (other$existPostSrcAddressList != null) {
                                return false;
                            }
                        } else if (!this$existPostSrcAddressList.equals(other$existPostSrcAddressList)) {
                            return false;
                        }

                        label393: {
                            Object this$restPostSrcAddressList = this.getRestPostSrcAddressList();
                            Object other$restPostSrcAddressList = other.getRestPostSrcAddressList();
                            if (this$restPostSrcAddressList == null) {
                                if (other$restPostSrcAddressList == null) {
                                    break label393;
                                }
                            } else if (this$restPostSrcAddressList.equals(other$restPostSrcAddressList)) {
                                break label393;
                            }

                            return false;
                        }

                        Object this$existDstAddressList = this.getExistDstAddressList();
                        Object other$existDstAddressList = other.getExistDstAddressList();
                        if (this$existDstAddressList == null) {
                            if (other$existDstAddressList != null) {
                                return false;
                            }
                        } else if (!this$existDstAddressList.equals(other$existDstAddressList)) {
                            return false;
                        }

                        label379: {
                            Object this$restDstAddressList = this.getRestDstAddressList();
                            Object other$restDstAddressList = other.getRestDstAddressList();
                            if (this$restDstAddressList == null) {
                                if (other$restDstAddressList == null) {
                                    break label379;
                                }
                            } else if (this$restDstAddressList.equals(other$restDstAddressList)) {
                                break label379;
                            }

                            return false;
                        }

                        Object this$currentId = this.getCurrentId();
                        Object other$currentId = other.getCurrentId();
                        if (this$currentId == null) {
                            if (other$currentId != null) {
                                return false;
                            }
                        } else if (!this$currentId.equals(other$currentId)) {
                            return false;
                        }

                        label365: {
                            Object this$inDevItfAlias = this.getInDevItfAlias();
                            Object other$inDevItfAlias = other.getInDevItfAlias();
                            if (this$inDevItfAlias == null) {
                                if (other$inDevItfAlias == null) {
                                    break label365;
                                }
                            } else if (this$inDevItfAlias.equals(other$inDevItfAlias)) {
                                break label365;
                            }

                            return false;
                        }

                        label358: {
                            Object this$outDevItfAlias = this.getOutDevItfAlias();
                            Object other$outDevItfAlias = other.getOutDevItfAlias();
                            if (this$outDevItfAlias == null) {
                                if (other$outDevItfAlias == null) {
                                    break label358;
                                }
                            } else if (this$outDevItfAlias.equals(other$outDevItfAlias)) {
                                break label358;
                            }

                            return false;
                        }

                        Object this$existPostDstAddressList = this.getExistPostDstAddressList();
                        Object other$existPostDstAddressList = other.getExistPostDstAddressList();
                        if (this$existPostDstAddressList == null) {
                            if (other$existPostDstAddressList != null) {
                                return false;
                            }
                        } else if (!this$existPostDstAddressList.equals(other$existPostDstAddressList)) {
                            return false;
                        }

                        label344: {
                            Object this$restPostDstAddressList = this.getRestPostDstAddressList();
                            Object other$restPostDstAddressList = other.getRestPostDstAddressList();
                            if (this$restPostDstAddressList == null) {
                                if (other$restPostDstAddressList == null) {
                                    break label344;
                                }
                            } else if (this$restPostDstAddressList.equals(other$restPostDstAddressList)) {
                                break label344;
                            }

                            return false;
                        }

                        Object this$ipType = this.getIpType();
                        Object other$ipType = other.getIpType();
                        if (this$ipType == null) {
                            if (other$ipType != null) {
                                return false;
                            }
                        } else if (!this$ipType.equals(other$ipType)) {
                            return false;
                        }

                        label330: {
                            Object this$currentAddressGroupId = this.getCurrentAddressGroupId();
                            Object other$currentAddressGroupId = other.getCurrentAddressGroupId();
                            if (this$currentAddressGroupId == null) {
                                if (other$currentAddressGroupId == null) {
                                    break label330;
                                }
                            } else if (this$currentAddressGroupId.equals(other$currentAddressGroupId)) {
                                break label330;
                            }

                            return false;
                        }

                        label323: {
                            Object this$serviceObjectNameList = this.getServiceObjectNameList();
                            Object other$serviceObjectNameList = other.getServiceObjectNameList();
                            if (this$serviceObjectNameList == null) {
                                if (other$serviceObjectNameList == null) {
                                    break label323;
                                }
                            } else if (this$serviceObjectNameList.equals(other$serviceObjectNameList)) {
                                break label323;
                            }

                            return false;
                        }

                        Object this$addressObjectNameList = this.getAddressObjectNameList();
                        Object other$addressObjectNameList = other.getAddressObjectNameList();
                        if (this$addressObjectNameList == null) {
                            if (other$addressObjectNameList != null) {
                                return false;
                            }
                        } else if (!this$addressObjectNameList.equals(other$addressObjectNameList)) {
                            return false;
                        }

                        Object this$addressObjectGroupNameList = this.getAddressObjectGroupNameList();
                        Object other$addressObjectGroupNameList = other.getAddressObjectGroupNameList();
                        if (this$addressObjectGroupNameList == null) {
                            if (other$addressObjectGroupNameList != null) {
                                return false;
                            }
                        } else if (!this$addressObjectGroupNameList.equals(other$addressObjectGroupNameList)) {
                            return false;
                        }

                        label302: {
                            Object this$serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
                            Object other$serviceObjectGroupNameList = other.getServiceObjectGroupNameList();
                            if (this$serviceObjectGroupNameList == null) {
                                if (other$serviceObjectGroupNameList == null) {
                                    break label302;
                                }
                            } else if (this$serviceObjectGroupNameList.equals(other$serviceObjectGroupNameList)) {
                                break label302;
                            }

                            return false;
                        }

                        label295: {
                            Object this$policyName = this.getPolicyName();
                            Object other$policyName = other.getPolicyName();
                            if (this$policyName == null) {
                                if (other$policyName == null) {
                                    break label295;
                                }
                            } else if (this$policyName.equals(other$policyName)) {
                                break label295;
                            }

                            return false;
                        }

                        Object this$rollbackShowCmd = this.getRollbackShowCmd();
                        Object other$rollbackShowCmd = other.getRollbackShowCmd();
                        if (this$rollbackShowCmd == null) {
                            if (other$rollbackShowCmd != null) {
                                return false;
                            }
                        } else if (!this$rollbackShowCmd.equals(other$rollbackShowCmd)) {
                            return false;
                        }

                        Object this$addressTypeMap = this.getAddressTypeMap();
                        Object other$addressTypeMap = other.getAddressTypeMap();
                        if (this$addressTypeMap == null) {
                            if (other$addressTypeMap != null) {
                                return false;
                            }
                        } else if (!this$addressTypeMap.equals(other$addressTypeMap)) {
                            return false;
                        }

                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DNatPolicyDTO;
    }

    public int hashCode() {
        int PRIME = 0;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $taskId = this.getTaskId();
        result = result * 59 + ($taskId == null ? 43 : $taskId.hashCode());
        Object $theme = this.getTheme();
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        Object $deviceUuid = this.getDeviceUuid();
        result = result * 59 + ($deviceUuid == null ? 43 : $deviceUuid.hashCode());
        Object $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        Object $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
        Object $srcIp = this.getSrcIp();
        result = result * 59 + ($srcIp == null ? 43 : $srcIp.hashCode());
        Object $srcIpSystem = this.getSrcIpSystem();
        result = result * 59 + ($srcIpSystem == null ? 43 : $srcIpSystem.hashCode());
        Object $dstIp = this.getDstIp();
        result = result * 59 + ($dstIp == null ? 43 : $dstIp.hashCode());
        Object $dstIpSystem = this.getDstIpSystem();
        result = result * 59 + ($dstIpSystem == null ? 43 : $dstIpSystem.hashCode());
        Object $serviceList = this.getServiceList();
        result = result * 59 + ($serviceList == null ? 43 : $serviceList.hashCode());
        Object $postIpAddress = this.getPostIpAddress();
        result = result * 59 + ($postIpAddress == null ? 43 : $postIpAddress.hashCode());
        Object $postDstIpSystem = this.getPostDstIpSystem();
        result = result * 59 + ($postDstIpSystem == null ? 43 : $postDstIpSystem.hashCode());
        Object $postPort = this.getPostPort();
        result = result * 59 + ($postPort == null ? 43 : $postPort.hashCode());
        Object $srcZone = this.getSrcZone();
        result = result * 59 + ($srcZone == null ? 43 : $srcZone.hashCode());
        Object $srcItf = this.getSrcItf();
        result = result * 59 + ($srcItf == null ? 43 : $srcItf.hashCode());
        Object $dstZone = this.getDstZone();
        result = result * 59 + ($dstZone == null ? 43 : $dstZone.hashCode());
        Object $dstItf = this.getDstItf();
        result = result * 59 + ($dstItf == null ? 43 : $dstItf.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $serviceObjectName = this.getServiceObjectName();
        result = result * 59 + ($serviceObjectName == null ? 43 : $serviceObjectName.hashCode());
        Object $postServiceObjectName = this.getPostServiceObjectName();
        result = result * 59 + ($postServiceObjectName == null ? 43 : $postServiceObjectName.hashCode());
        Object $srcAddressObjectName = this.getSrcAddressObjectName();
        result = result * 59 + ($srcAddressObjectName == null ? 43 : $srcAddressObjectName.hashCode());
        Object $dstAddressObjectName = this.getDstAddressObjectName();
        result = result * 59 + ($dstAddressObjectName == null ? 43 : $dstAddressObjectName.hashCode());
        Object $postAddressObjectName = this.getPostAddressObjectName();
        result = result * 59 + ($postAddressObjectName == null ? 43 : $postAddressObjectName.hashCode());
        result = result * 59 + (this.isVsys() ? 79 : 97);
        Object $vsysName = this.getVsysName();
        result = result * 59 + ($vsysName == null ? 43 : $vsysName.hashCode());
        result = result * 59 + (this.isHasVsys() ? 79 : 97);
        result = result * 59 + (this.isCreateObjFlag() ? 79 : 97);
        Object $moveSeatEnum = this.getMoveSeatEnum();
        result = result * 59 + ($moveSeatEnum == null ? 43 : $moveSeatEnum.hashCode());
        Object $swapRuleNameId = this.getSwapRuleNameId();
        result = result * 59 + ($swapRuleNameId == null ? 43 : $swapRuleNameId.hashCode());
        Object $restServiceList = this.getRestServiceList();
        result = result * 59 + ($restServiceList == null ? 43 : $restServiceList.hashCode());
        Object $existServiceNameList = this.getExistServiceNameList();
        result = result * 59 + ($existServiceNameList == null ? 43 : $existServiceNameList.hashCode());
        Object $existPostServiceNameList = this.getExistPostServiceNameList();
        result = result * 59 + ($existPostServiceNameList == null ? 43 : $existPostServiceNameList.hashCode());
        Object $restPostServiceList = this.getRestPostServiceList();
        result = result * 59 + ($restPostServiceList == null ? 43 : $restPostServiceList.hashCode());
        Object $existSrcAddressList = this.getExistSrcAddressList();
        result = result * 59 + ($existSrcAddressList == null ? 43 : $existSrcAddressList.hashCode());
        Object $restSrcAddressList = this.getRestSrcAddressList();
        result = result * 59 + ($restSrcAddressList == null ? 43 : $restSrcAddressList.hashCode());
        Object $existPostSrcAddressList = this.getExistPostSrcAddressList();
        result = result * 59 + ($existPostSrcAddressList == null ? 43 : $existPostSrcAddressList.hashCode());
        Object $restPostSrcAddressList = this.getRestPostSrcAddressList();
        result = result * 59 + ($restPostSrcAddressList == null ? 43 : $restPostSrcAddressList.hashCode());
        Object $existDstAddressList = this.getExistDstAddressList();
        result = result * 59 + ($existDstAddressList == null ? 43 : $existDstAddressList.hashCode());
        Object $restDstAddressList = this.getRestDstAddressList();
        result = result * 59 + ($restDstAddressList == null ? 43 : $restDstAddressList.hashCode());
        Object $currentId = this.getCurrentId();
        result = result * 59 + ($currentId == null ? 43 : $currentId.hashCode());
        Object $inDevItfAlias = this.getInDevItfAlias();
        result = result * 59 + ($inDevItfAlias == null ? 43 : $inDevItfAlias.hashCode());
        Object $outDevItfAlias = this.getOutDevItfAlias();
        result = result * 59 + ($outDevItfAlias == null ? 43 : $outDevItfAlias.hashCode());
        Object $existPostDstAddressList = this.getExistPostDstAddressList();
        result = result * 59 + ($existPostDstAddressList == null ? 43 : $existPostDstAddressList.hashCode());
        Object $restPostDstAddressList = this.getRestPostDstAddressList();
        result = result * 59 + ($restPostDstAddressList == null ? 43 : $restPostDstAddressList.hashCode());
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        Object $currentAddressGroupId = this.getCurrentAddressGroupId();
        result = result * 59 + ($currentAddressGroupId == null ? 43 : $currentAddressGroupId.hashCode());
        Object $serviceObjectNameList = this.getServiceObjectNameList();
        result = result * 59 + ($serviceObjectNameList == null ? 43 : $serviceObjectNameList.hashCode());
        Object $addressObjectNameList = this.getAddressObjectNameList();
        result = result * 59 + ($addressObjectNameList == null ? 43 : $addressObjectNameList.hashCode());
        Object $addressObjectGroupNameList = this.getAddressObjectGroupNameList();
        result = result * 59 + ($addressObjectGroupNameList == null ? 43 : $addressObjectGroupNameList.hashCode());
        Object $serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
        result = result * 59 + ($serviceObjectGroupNameList == null ? 43 : $serviceObjectGroupNameList.hashCode());
        Object $policyName = this.getPolicyName();
        result = result * 59 + ($policyName == null ? 43 : $policyName.hashCode());
        Object $rollbackShowCmd = this.getRollbackShowCmd();
        result = result * 59 + ($rollbackShowCmd == null ? 43 : $rollbackShowCmd.hashCode());
        Object $addressTypeMap = this.getAddressTypeMap();
        result = result * 59 + ($addressTypeMap == null ? 43 : $addressTypeMap.hashCode());
        return result;
    }

    public String toString() {
        return "DNatPolicyDTO(id=" + this.getId() + ", taskId=" + this.getTaskId() + ", theme=" + this.getTheme() + ", deviceUuid=" + this.getDeviceUuid() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", srcIp=" + this.getSrcIp() + ", srcIpSystem=" + this.getSrcIpSystem() + ", dstIp=" + this.getDstIp() + ", dstIpSystem=" + this.getDstIpSystem() + ", serviceList=" + this.getServiceList() + ", postIpAddress=" + this.getPostIpAddress() + ", postDstIpSystem=" + this.getPostDstIpSystem() + ", postPort=" + this.getPostPort() + ", srcZone=" + this.getSrcZone() + ", srcItf=" + this.getSrcItf() + ", dstZone=" + this.getDstZone() + ", dstItf=" + this.getDstItf() + ", description=" + this.getDescription() + ", serviceObjectName=" + this.getServiceObjectName() + ", postServiceObjectName=" + this.getPostServiceObjectName() + ", srcAddressObjectName=" + this.getSrcAddressObjectName() + ", dstAddressObjectName=" + this.getDstAddressObjectName() + ", postAddressObjectName=" + this.getPostAddressObjectName() + ", isVsys=" + this.isVsys() + ", vsysName=" + this.getVsysName() + ", hasVsys=" + this.isHasVsys() + ", createObjFlag=" + this.isCreateObjFlag() + ", moveSeatEnum=" + this.getMoveSeatEnum() + ", swapRuleNameId=" + this.getSwapRuleNameId() + ", restServiceList=" + this.getRestServiceList() + ", existServiceNameList=" + this.getExistServiceNameList() + ", existPostServiceNameList=" + this.getExistPostServiceNameList() + ", restPostServiceList=" + this.getRestPostServiceList() + ", existSrcAddressList=" + this.getExistSrcAddressList() + ", restSrcAddressList=" + this.getRestSrcAddressList() + ", existPostSrcAddressList=" + this.getExistPostSrcAddressList() + ", restPostSrcAddressList=" + this.getRestPostSrcAddressList() + ", existDstAddressList=" + this.getExistDstAddressList() + ", restDstAddressList=" + this.getRestDstAddressList() + ", currentId=" + this.getCurrentId() + ", inDevItfAlias=" + this.getInDevItfAlias() + ", outDevItfAlias=" + this.getOutDevItfAlias() + ", existPostDstAddressList=" + this.getExistPostDstAddressList() + ", restPostDstAddressList=" + this.getRestPostDstAddressList() + ", ipType=" + this.getIpType() + ", currentAddressGroupId=" + this.getCurrentAddressGroupId() + ", serviceObjectNameList=" + this.getServiceObjectNameList() + ", addressObjectNameList=" + this.getAddressObjectNameList() + ", addressObjectGroupNameList=" + this.getAddressObjectGroupNameList() + ", serviceObjectGroupNameList=" + this.getServiceObjectGroupNameList() + ", policyName=" + this.getPolicyName() + ", rollbackShowCmd=" + this.getRollbackShowCmd() + ", addressTypeMap=" + this.getAddressTypeMap() + ")";
    }
}
