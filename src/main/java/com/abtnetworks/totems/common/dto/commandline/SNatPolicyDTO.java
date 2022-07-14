//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.common.dto.commandline;

import com.abtnetworks.totems.common.dto.generate.ExistAddressObjectDTO;
import com.abtnetworks.totems.common.enums.MoveSeatEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel("SNAT数据实体类")
public class SNatPolicyDTO {
    @ApiModelProperty("命令行下发任务主键id")
    Integer id;
    @ApiModelProperty("任务ID")
    Integer taskId;
    @ApiModelProperty("主题（工单号）")
    String theme;
    @ApiModelProperty("设备UUID")
    String deviceUuid;
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
    @ApiModelProperty("转换后源地址描述")
    String postSrcIpSystem;
    @ApiModelProperty("源域")
    String srcZone;
    @ApiModelProperty("目的域")
    String dstZone;
    @ApiModelProperty("入接口")
    String srcItf;
    @ApiModelProperty("出接口")
    String dstItf;
    @ApiModelProperty("模式")
    String mode;
    @ApiModelProperty("策略描述")
    String description;
    @ApiModelProperty("现有服务对象名称")
    String serviceObjectName;
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
    @ApiModelProperty("cisco是否需要Enable")
    boolean ciscoEnable = true;
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
    @ApiModelProperty("飞塔当前策略id")
    String currentId;
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
    @ApiModelProperty("已存在源地址对象列表")
    List<ExistAddressObjectDTO> existSrcAddressName;
    @ApiModelProperty("已存在目的地址对象列表")
    List<ExistAddressObjectDTO> existDstAddressName;
    @ApiModelProperty("已存在源转换后地址对象列表")
    List<ExistAddressObjectDTO> existPostSrcAddressName;
    @ApiModelProperty("入接口别名 ")
    private String inDevItfAlias;
    @ApiModelProperty("出接口别名 ")
    private String outDevItfAlias;
    @ApiModelProperty("是否执行回滚")
    boolean isRollback = false;
    @ApiModelProperty("开始时间")
    String startTime;
    @ApiModelProperty("结束时间")
    String endTime;
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    @ApiModelProperty("华3V7当前地址池id")
    String currentAddressGroupId;
    @ApiModelProperty("本条策略创建的服务对象名称集合")
    List<String> serviceObjectNameList;
    @ApiModelProperty("本条策略创建的服务组对象名称集合")
    List<String> serviceObjectGroupNameList;
    @ApiModelProperty("本条策略创建的地址对象名称集合")
    List<String> addressObjectNameList;
    @ApiModelProperty("本条策略创建的地址组对象名称集合")
    List<String> addressObjectGroupNameList;
    @ApiModelProperty("命令行中的策略名称  策略名称在生成命令的时候要根据工单号_AO_随机数,要传到后面流程使用")
    String policyName;
    @ApiModelProperty("回滚查询命令行")
    String rollbackShowCmd;
    @ApiModelProperty("用于不同类型地址回滚命令行。比如每种地址类型都不同，拼接不用规则的命令行地址。 地址类型map ，key为地址名称，value为地址的类型 host主机，sub子网 rang 范围")
    Map<String, String> addressTypeMap = new HashMap();
    String userName;// Metoo
    String branchLevel;// Metoo

    public SNatPolicyDTO() {
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

    public String getPostSrcIpSystem() {
        return this.postSrcIpSystem;
    }

    public String getSrcZone() {
        return this.srcZone;
    }

    public String getDstZone() {
        return this.dstZone;
    }

    public String getSrcItf() {
        return this.srcItf;
    }

    public String getDstItf() {
        return this.dstItf;
    }

    public String getMode() {
        return this.mode;
    }

    public String getDescription() {
        return this.description;
    }

    public String getServiceObjectName() {
        return this.serviceObjectName;
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

    public boolean isCiscoEnable() {
        return this.ciscoEnable;
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

    public String getCurrentId() {
        return this.currentId;
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

    public List<ExistAddressObjectDTO> getExistSrcAddressName() {
        return this.existSrcAddressName;
    }

    public List<ExistAddressObjectDTO> getExistDstAddressName() {
        return this.existDstAddressName;
    }

    public List<ExistAddressObjectDTO> getExistPostSrcAddressName() {
        return this.existPostSrcAddressName;
    }

    public String getInDevItfAlias() {
        return this.inDevItfAlias;
    }

    public String getOutDevItfAlias() {
        return this.outDevItfAlias;
    }

    public boolean isRollback() {
        return this.isRollback;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
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

    public List<String> getServiceObjectGroupNameList() {
        return this.serviceObjectGroupNameList;
    }

    public List<String> getAddressObjectNameList() {
        return this.addressObjectNameList;
    }

    public List<String> getAddressObjectGroupNameList() {
        return this.addressObjectGroupNameList;
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

    public void setPostSrcIpSystem(final String postSrcIpSystem) {
        this.postSrcIpSystem = postSrcIpSystem;
    }

    public void setSrcZone(final String srcZone) {
        this.srcZone = srcZone;
    }

    public void setDstZone(final String dstZone) {
        this.dstZone = dstZone;
    }

    public void setSrcItf(final String srcItf) {
        this.srcItf = srcItf;
    }

    public void setDstItf(final String dstItf) {
        this.dstItf = dstItf;
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setServiceObjectName(final String serviceObjectName) {
        this.serviceObjectName = serviceObjectName;
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

    public void setCiscoEnable(final boolean ciscoEnable) {
        this.ciscoEnable = ciscoEnable;
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

    public void setCurrentId(final String currentId) {
        this.currentId = currentId;
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

    public void setExistSrcAddressName(final List<ExistAddressObjectDTO> existSrcAddressName) {
        this.existSrcAddressName = existSrcAddressName;
    }

    public void setExistDstAddressName(final List<ExistAddressObjectDTO> existDstAddressName) {
        this.existDstAddressName = existDstAddressName;
    }

    public void setExistPostSrcAddressName(final List<ExistAddressObjectDTO> existPostSrcAddressName) {
        this.existPostSrcAddressName = existPostSrcAddressName;
    }

    public void setInDevItfAlias(final String inDevItfAlias) {
        this.inDevItfAlias = inDevItfAlias;
    }

    public void setOutDevItfAlias(final String outDevItfAlias) {
        this.outDevItfAlias = outDevItfAlias;
    }

    public void setRollback(final boolean isRollback) {
        this.isRollback = isRollback;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
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

    public void setServiceObjectGroupNameList(final List<String> serviceObjectGroupNameList) {
        this.serviceObjectGroupNameList = serviceObjectGroupNameList;
    }

    public void setAddressObjectNameList(final List<String> addressObjectNameList) {
        this.addressObjectNameList = addressObjectNameList;
    }

    public void setAddressObjectGroupNameList(final List<String> addressObjectGroupNameList) {
        this.addressObjectGroupNameList = addressObjectGroupNameList;
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
        } else if (!(o instanceof SNatPolicyDTO)) {
            return false;
        } else {
            SNatPolicyDTO other = (SNatPolicyDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label619: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label619;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label619;
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

                label598: {
                    Object this$deviceUuid = this.getDeviceUuid();
                    Object other$deviceUuid = other.getDeviceUuid();
                    if (this$deviceUuid == null) {
                        if (other$deviceUuid == null) {
                            break label598;
                        }
                    } else if (this$deviceUuid.equals(other$deviceUuid)) {
                        break label598;
                    }

                    return false;
                }

                label591: {
                    Object this$srcIp = this.getSrcIp();
                    Object other$srcIp = other.getSrcIp();
                    if (this$srcIp == null) {
                        if (other$srcIp == null) {
                            break label591;
                        }
                    } else if (this$srcIp.equals(other$srcIp)) {
                        break label591;
                    }

                    return false;
                }

                label584: {
                    Object this$srcIpSystem = this.getSrcIpSystem();
                    Object other$srcIpSystem = other.getSrcIpSystem();
                    if (this$srcIpSystem == null) {
                        if (other$srcIpSystem == null) {
                            break label584;
                        }
                    } else if (this$srcIpSystem.equals(other$srcIpSystem)) {
                        break label584;
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

                label570: {
                    Object this$dstIpSystem = this.getDstIpSystem();
                    Object other$dstIpSystem = other.getDstIpSystem();
                    if (this$dstIpSystem == null) {
                        if (other$dstIpSystem == null) {
                            break label570;
                        }
                    } else if (this$dstIpSystem.equals(other$dstIpSystem)) {
                        break label570;
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

                label556: {
                    Object this$postIpAddress = this.getPostIpAddress();
                    Object other$postIpAddress = other.getPostIpAddress();
                    if (this$postIpAddress == null) {
                        if (other$postIpAddress == null) {
                            break label556;
                        }
                    } else if (this$postIpAddress.equals(other$postIpAddress)) {
                        break label556;
                    }

                    return false;
                }

                Object this$postSrcIpSystem = this.getPostSrcIpSystem();
                Object other$postSrcIpSystem = other.getPostSrcIpSystem();
                if (this$postSrcIpSystem == null) {
                    if (other$postSrcIpSystem != null) {
                        return false;
                    }
                } else if (!this$postSrcIpSystem.equals(other$postSrcIpSystem)) {
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

                label535: {
                    Object this$dstZone = this.getDstZone();
                    Object other$dstZone = other.getDstZone();
                    if (this$dstZone == null) {
                        if (other$dstZone == null) {
                            break label535;
                        }
                    } else if (this$dstZone.equals(other$dstZone)) {
                        break label535;
                    }

                    return false;
                }

                label528: {
                    Object this$srcItf = this.getSrcItf();
                    Object other$srcItf = other.getSrcItf();
                    if (this$srcItf == null) {
                        if (other$srcItf == null) {
                            break label528;
                        }
                    } else if (this$srcItf.equals(other$srcItf)) {
                        break label528;
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

                Object this$mode = this.getMode();
                Object other$mode = other.getMode();
                if (this$mode == null) {
                    if (other$mode != null) {
                        return false;
                    }
                } else if (!this$mode.equals(other$mode)) {
                    return false;
                }

                label507: {
                    Object this$description = this.getDescription();
                    Object other$description = other.getDescription();
                    if (this$description == null) {
                        if (other$description == null) {
                            break label507;
                        }
                    } else if (this$description.equals(other$description)) {
                        break label507;
                    }

                    return false;
                }

                Object this$serviceObjectName = this.getServiceObjectName();
                Object other$serviceObjectName = other.getServiceObjectName();
                if (this$serviceObjectName == null) {
                    if (other$serviceObjectName != null) {
                        return false;
                    }
                } else if (!this$serviceObjectName.equals(other$serviceObjectName)) {
                    return false;
                }

                Object this$srcAddressObjectName = this.getSrcAddressObjectName();
                Object other$srcAddressObjectName = other.getSrcAddressObjectName();
                if (this$srcAddressObjectName == null) {
                    if (other$srcAddressObjectName != null) {
                        return false;
                    }
                } else if (!this$srcAddressObjectName.equals(other$srcAddressObjectName)) {
                    return false;
                }

                label486: {
                    Object this$dstAddressObjectName = this.getDstAddressObjectName();
                    Object other$dstAddressObjectName = other.getDstAddressObjectName();
                    if (this$dstAddressObjectName == null) {
                        if (other$dstAddressObjectName == null) {
                            break label486;
                        }
                    } else if (this$dstAddressObjectName.equals(other$dstAddressObjectName)) {
                        break label486;
                    }

                    return false;
                }

                label479: {
                    Object this$postAddressObjectName = this.getPostAddressObjectName();
                    Object other$postAddressObjectName = other.getPostAddressObjectName();
                    if (this$postAddressObjectName == null) {
                        if (other$postAddressObjectName == null) {
                            break label479;
                        }
                    } else if (this$postAddressObjectName.equals(other$postAddressObjectName)) {
                        break label479;
                    }

                    return false;
                }

                if (this.isVsys() != other.isVsys()) {
                    return false;
                } else {
                    label471: {
                        Object this$vsysName = this.getVsysName();
                        Object other$vsysName = other.getVsysName();
                        if (this$vsysName == null) {
                            if (other$vsysName == null) {
                                break label471;
                            }
                        } else if (this$vsysName.equals(other$vsysName)) {
                            break label471;
                        }

                        return false;
                    }

                    if (this.isHasVsys() != other.isHasVsys()) {
                        return false;
                    } else if (this.isCiscoEnable() != other.isCiscoEnable()) {
                        return false;
                    } else if (this.isCreateObjFlag() != other.isCreateObjFlag()) {
                        return false;
                    } else {
                        Object this$moveSeatEnum = this.getMoveSeatEnum();
                        Object other$moveSeatEnum = other.getMoveSeatEnum();
                        if (this$moveSeatEnum == null) {
                            if (other$moveSeatEnum != null) {
                                return false;
                            }
                        } else if (!this$moveSeatEnum.equals(other$moveSeatEnum)) {
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

                        label446: {
                            Object this$restServiceList = this.getRestServiceList();
                            Object other$restServiceList = other.getRestServiceList();
                            if (this$restServiceList == null) {
                                if (other$restServiceList == null) {
                                    break label446;
                                }
                            } else if (this$restServiceList.equals(other$restServiceList)) {
                                break label446;
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

                        Object this$currentId = this.getCurrentId();
                        Object other$currentId = other.getCurrentId();
                        if (this$currentId == null) {
                            if (other$currentId != null) {
                                return false;
                            }
                        } else if (!this$currentId.equals(other$currentId)) {
                            return false;
                        }

                        label425: {
                            Object this$existSrcAddressList = this.getExistSrcAddressList();
                            Object other$existSrcAddressList = other.getExistSrcAddressList();
                            if (this$existSrcAddressList == null) {
                                if (other$existSrcAddressList == null) {
                                    break label425;
                                }
                            } else if (this$existSrcAddressList.equals(other$existSrcAddressList)) {
                                break label425;
                            }

                            return false;
                        }

                        label418: {
                            Object this$restSrcAddressList = this.getRestSrcAddressList();
                            Object other$restSrcAddressList = other.getRestSrcAddressList();
                            if (this$restSrcAddressList == null) {
                                if (other$restSrcAddressList == null) {
                                    break label418;
                                }
                            } else if (this$restSrcAddressList.equals(other$restSrcAddressList)) {
                                break label418;
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

                        Object this$restPostSrcAddressList = this.getRestPostSrcAddressList();
                        Object other$restPostSrcAddressList = other.getRestPostSrcAddressList();
                        if (this$restPostSrcAddressList == null) {
                            if (other$restPostSrcAddressList != null) {
                                return false;
                            }
                        } else if (!this$restPostSrcAddressList.equals(other$restPostSrcAddressList)) {
                            return false;
                        }

                        label397: {
                            Object this$existDstAddressList = this.getExistDstAddressList();
                            Object other$existDstAddressList = other.getExistDstAddressList();
                            if (this$existDstAddressList == null) {
                                if (other$existDstAddressList == null) {
                                    break label397;
                                }
                            } else if (this$existDstAddressList.equals(other$existDstAddressList)) {
                                break label397;
                            }

                            return false;
                        }

                        label390: {
                            Object this$restDstAddressList = this.getRestDstAddressList();
                            Object other$restDstAddressList = other.getRestDstAddressList();
                            if (this$restDstAddressList == null) {
                                if (other$restDstAddressList == null) {
                                    break label390;
                                }
                            } else if (this$restDstAddressList.equals(other$restDstAddressList)) {
                                break label390;
                            }

                            return false;
                        }

                        Object this$existSrcAddressName = this.getExistSrcAddressName();
                        Object other$existSrcAddressName = other.getExistSrcAddressName();
                        if (this$existSrcAddressName == null) {
                            if (other$existSrcAddressName != null) {
                                return false;
                            }
                        } else if (!this$existSrcAddressName.equals(other$existSrcAddressName)) {
                            return false;
                        }

                        label376: {
                            Object this$existDstAddressName = this.getExistDstAddressName();
                            Object other$existDstAddressName = other.getExistDstAddressName();
                            if (this$existDstAddressName == null) {
                                if (other$existDstAddressName == null) {
                                    break label376;
                                }
                            } else if (this$existDstAddressName.equals(other$existDstAddressName)) {
                                break label376;
                            }

                            return false;
                        }

                        Object this$existPostSrcAddressName = this.getExistPostSrcAddressName();
                        Object other$existPostSrcAddressName = other.getExistPostSrcAddressName();
                        if (this$existPostSrcAddressName == null) {
                            if (other$existPostSrcAddressName != null) {
                                return false;
                            }
                        } else if (!this$existPostSrcAddressName.equals(other$existPostSrcAddressName)) {
                            return false;
                        }

                        label362: {
                            Object this$inDevItfAlias = this.getInDevItfAlias();
                            Object other$inDevItfAlias = other.getInDevItfAlias();
                            if (this$inDevItfAlias == null) {
                                if (other$inDevItfAlias == null) {
                                    break label362;
                                }
                            } else if (this$inDevItfAlias.equals(other$inDevItfAlias)) {
                                break label362;
                            }

                            return false;
                        }

                        Object this$outDevItfAlias = this.getOutDevItfAlias();
                        Object other$outDevItfAlias = other.getOutDevItfAlias();
                        if (this$outDevItfAlias == null) {
                            if (other$outDevItfAlias != null) {
                                return false;
                            }
                        } else if (!this$outDevItfAlias.equals(other$outDevItfAlias)) {
                            return false;
                        }

                        if (this.isRollback() != other.isRollback()) {
                            return false;
                        } else {
                            Object this$startTime = this.getStartTime();
                            Object other$startTime = other.getStartTime();
                            if (this$startTime == null) {
                                if (other$startTime != null) {
                                    return false;
                                }
                            } else if (!this$startTime.equals(other$startTime)) {
                                return false;
                            }

                            Object this$endTime = this.getEndTime();
                            Object other$endTime = other.getEndTime();
                            if (this$endTime == null) {
                                if (other$endTime != null) {
                                    return false;
                                }
                            } else if (!this$endTime.equals(other$endTime)) {
                                return false;
                            }

                            label333: {
                                Object this$ipType = this.getIpType();
                                Object other$ipType = other.getIpType();
                                if (this$ipType == null) {
                                    if (other$ipType == null) {
                                        break label333;
                                    }
                                } else if (this$ipType.equals(other$ipType)) {
                                    break label333;
                                }

                                return false;
                            }

                            label326: {
                                Object this$currentAddressGroupId = this.getCurrentAddressGroupId();
                                Object other$currentAddressGroupId = other.getCurrentAddressGroupId();
                                if (this$currentAddressGroupId == null) {
                                    if (other$currentAddressGroupId == null) {
                                        break label326;
                                    }
                                } else if (this$currentAddressGroupId.equals(other$currentAddressGroupId)) {
                                    break label326;
                                }

                                return false;
                            }

                            Object this$serviceObjectNameList = this.getServiceObjectNameList();
                            Object other$serviceObjectNameList = other.getServiceObjectNameList();
                            if (this$serviceObjectNameList == null) {
                                if (other$serviceObjectNameList != null) {
                                    return false;
                                }
                            } else if (!this$serviceObjectNameList.equals(other$serviceObjectNameList)) {
                                return false;
                            }

                            label312: {
                                Object this$serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
                                Object other$serviceObjectGroupNameList = other.getServiceObjectGroupNameList();
                                if (this$serviceObjectGroupNameList == null) {
                                    if (other$serviceObjectGroupNameList == null) {
                                        break label312;
                                    }
                                } else if (this$serviceObjectGroupNameList.equals(other$serviceObjectGroupNameList)) {
                                    break label312;
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

                            label298: {
                                Object this$addressObjectGroupNameList = this.getAddressObjectGroupNameList();
                                Object other$addressObjectGroupNameList = other.getAddressObjectGroupNameList();
                                if (this$addressObjectGroupNameList == null) {
                                    if (other$addressObjectGroupNameList == null) {
                                        break label298;
                                    }
                                } else if (this$addressObjectGroupNameList.equals(other$addressObjectGroupNameList)) {
                                    break label298;
                                }

                                return false;
                            }

                            Object this$policyName = this.getPolicyName();
                            Object other$policyName = other.getPolicyName();
                            if (this$policyName == null) {
                                if (other$policyName != null) {
                                    return false;
                                }
                            } else if (!this$policyName.equals(other$policyName)) {
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
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SNatPolicyDTO;
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
        Object $postSrcIpSystem = this.getPostSrcIpSystem();
        result = result * 59 + ($postSrcIpSystem == null ? 43 : $postSrcIpSystem.hashCode());
        Object $srcZone = this.getSrcZone();
        result = result * 59 + ($srcZone == null ? 43 : $srcZone.hashCode());
        Object $dstZone = this.getDstZone();
        result = result * 59 + ($dstZone == null ? 43 : $dstZone.hashCode());
        Object $srcItf = this.getSrcItf();
        result = result * 59 + ($srcItf == null ? 43 : $srcItf.hashCode());
        Object $dstItf = this.getDstItf();
        result = result * 59 + ($dstItf == null ? 43 : $dstItf.hashCode());
        Object $mode = this.getMode();
        result = result * 59 + ($mode == null ? 43 : $mode.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $serviceObjectName = this.getServiceObjectName();
        result = result * 59 + ($serviceObjectName == null ? 43 : $serviceObjectName.hashCode());
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
        result = result * 59 + (this.isCiscoEnable() ? 79 : 97);
        result = result * 59 + (this.isCreateObjFlag() ? 79 : 97);
        Object $moveSeatEnum = this.getMoveSeatEnum();
        result = result * 59 + ($moveSeatEnum == null ? 43 : $moveSeatEnum.hashCode());
        Object $swapRuleNameId = this.getSwapRuleNameId();
        result = result * 59 + ($swapRuleNameId == null ? 43 : $swapRuleNameId.hashCode());
        Object $restServiceList = this.getRestServiceList();
        result = result * 59 + ($restServiceList == null ? 43 : $restServiceList.hashCode());
        Object $existServiceNameList = this.getExistServiceNameList();
        result = result * 59 + ($existServiceNameList == null ? 43 : $existServiceNameList.hashCode());
        Object $currentId = this.getCurrentId();
        result = result * 59 + ($currentId == null ? 43 : $currentId.hashCode());
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
        Object $existSrcAddressName = this.getExistSrcAddressName();
        result = result * 59 + ($existSrcAddressName == null ? 43 : $existSrcAddressName.hashCode());
        Object $existDstAddressName = this.getExistDstAddressName();
        result = result * 59 + ($existDstAddressName == null ? 43 : $existDstAddressName.hashCode());
        Object $existPostSrcAddressName = this.getExistPostSrcAddressName();
        result = result * 59 + ($existPostSrcAddressName == null ? 43 : $existPostSrcAddressName.hashCode());
        Object $inDevItfAlias = this.getInDevItfAlias();
        result = result * 59 + ($inDevItfAlias == null ? 43 : $inDevItfAlias.hashCode());
        Object $outDevItfAlias = this.getOutDevItfAlias();
        result = result * 59 + ($outDevItfAlias == null ? 43 : $outDevItfAlias.hashCode());
        result = result * 59 + (this.isRollback() ? 79 : 97);
        Object $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        Object $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        Object $currentAddressGroupId = this.getCurrentAddressGroupId();
        result = result * 59 + ($currentAddressGroupId == null ? 43 : $currentAddressGroupId.hashCode());
        Object $serviceObjectNameList = this.getServiceObjectNameList();
        result = result * 59 + ($serviceObjectNameList == null ? 43 : $serviceObjectNameList.hashCode());
        Object $serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
        result = result * 59 + ($serviceObjectGroupNameList == null ? 43 : $serviceObjectGroupNameList.hashCode());
        Object $addressObjectNameList = this.getAddressObjectNameList();
        result = result * 59 + ($addressObjectNameList == null ? 43 : $addressObjectNameList.hashCode());
        Object $addressObjectGroupNameList = this.getAddressObjectGroupNameList();
        result = result * 59 + ($addressObjectGroupNameList == null ? 43 : $addressObjectGroupNameList.hashCode());
        Object $policyName = this.getPolicyName();
        result = result * 59 + ($policyName == null ? 43 : $policyName.hashCode());
        Object $rollbackShowCmd = this.getRollbackShowCmd();
        result = result * 59 + ($rollbackShowCmd == null ? 43 : $rollbackShowCmd.hashCode());
        Object $addressTypeMap = this.getAddressTypeMap();
        result = result * 59 + ($addressTypeMap == null ? 43 : $addressTypeMap.hashCode());
        return result;
    }

    public String toString() {
        return "SNatPolicyDTO(id=" + this.getId() + ", taskId=" + this.getTaskId() + ", theme=" + this.getTheme() + ", deviceUuid=" + this.getDeviceUuid() + ", srcIp=" + this.getSrcIp() + ", srcIpSystem=" + this.getSrcIpSystem() + ", dstIp=" + this.getDstIp() + ", dstIpSystem=" + this.getDstIpSystem() + ", serviceList=" + this.getServiceList() + ", postIpAddress=" + this.getPostIpAddress() + ", postSrcIpSystem=" + this.getPostSrcIpSystem() + ", srcZone=" + this.getSrcZone() + ", dstZone=" + this.getDstZone() + ", srcItf=" + this.getSrcItf() + ", dstItf=" + this.getDstItf() + ", mode=" + this.getMode() + ", description=" + this.getDescription() + ", serviceObjectName=" + this.getServiceObjectName() + ", srcAddressObjectName=" + this.getSrcAddressObjectName() + ", dstAddressObjectName=" + this.getDstAddressObjectName() + ", postAddressObjectName=" + this.getPostAddressObjectName() + ", isVsys=" + this.isVsys() + ", vsysName=" + this.getVsysName() + ", hasVsys=" + this.isHasVsys() + ", ciscoEnable=" + this.isCiscoEnable() + ", createObjFlag=" + this.isCreateObjFlag() + ", moveSeatEnum=" + this.getMoveSeatEnum() + ", swapRuleNameId=" + this.getSwapRuleNameId() + ", restServiceList=" + this.getRestServiceList() + ", existServiceNameList=" + this.getExistServiceNameList() + ", currentId=" + this.getCurrentId() + ", existSrcAddressList=" + this.getExistSrcAddressList() + ", restSrcAddressList=" + this.getRestSrcAddressList() + ", existPostSrcAddressList=" + this.getExistPostSrcAddressList() + ", restPostSrcAddressList=" + this.getRestPostSrcAddressList() + ", existDstAddressList=" + this.getExistDstAddressList() + ", restDstAddressList=" + this.getRestDstAddressList() + ", existSrcAddressName=" + this.getExistSrcAddressName() + ", existDstAddressName=" + this.getExistDstAddressName() + ", existPostSrcAddressName=" + this.getExistPostSrcAddressName() + ", inDevItfAlias=" + this.getInDevItfAlias() + ", outDevItfAlias=" + this.getOutDevItfAlias() + ", isRollback=" + this.isRollback() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", ipType=" + this.getIpType() + ", currentAddressGroupId=" + this.getCurrentAddressGroupId() + ", serviceObjectNameList=" + this.getServiceObjectNameList() + ", serviceObjectGroupNameList=" + this.getServiceObjectGroupNameList() + ", addressObjectNameList=" + this.getAddressObjectNameList() + ", addressObjectGroupNameList=" + this.getAddressObjectGroupNameList() + ", policyName=" + this.getPolicyName() + ", rollbackShowCmd=" + this.getRollbackShowCmd() + ", addressTypeMap=" + this.getAddressTypeMap() + ")";
    }
}
