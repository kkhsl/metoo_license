package com.abtnetworks.totems.common.dto.commandline;

import com.abtnetworks.totems.common.dto.ExistObjectRefDTO;
import com.abtnetworks.totems.common.enums.MoveSeatEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModel("Nat策略对象")
public class NatPolicyDTO {
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
    @ApiModelProperty("目的地址")
    String dstIp;
    @ApiModelProperty("服务")
    List<ServiceDTO> serviceList;
    @ApiModelProperty("转换后服务")
    List<ServiceDTO> postServiceList;
    @ApiModelProperty("转换后源地址")
    String postSrcIp;
    @ApiModelProperty("转换后目的地址")
    String postDstIp;
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
    @ApiModelProperty("现有源地址对象名称")
    String srcAddressObjectName;
    @ApiModelProperty("现有目的地址对象名称")
    String dstAddressObjectName;
    @ApiModelProperty("转换后地址对象名称")
    String postSrcAddressObjectName;
    @ApiModelProperty("转换后目的地址对象名称")
    String postDstAddressObjectName;
    @ApiModelProperty("转换后服务对象名称")
    String postServiceObjectName;
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
    @ApiModelProperty("特殊：已存在对象数据，当前仅思科8.6以上版本使用")
    ExistObjectRefDTO specialExistObject = new ExistObjectRefDTO();
    @ApiModelProperty("默认static，勾选后为dynamic")
    boolean dynamic = false;
    @ApiModelProperty("是否执行回滚")
    boolean isRollback = false;
    @ApiModelProperty("还需要创建转换后源地址对象列表")
    List<String> restPostSrcAddressList = new ArrayList();
    @ApiModelProperty("已存在转换后源地址对象列表")
    List<String> existPostSrcAddressList = new ArrayList();
    @ApiModelProperty("已存在转换后目的地址对象列表")
    List<String> existPostDstAddressList = new ArrayList();
    @ApiModelProperty("还需要创建转换后目的地址对象列表")
    List<String> restPostDstAddressList = new ArrayList();
    @ApiModelProperty("入接口别名 ")
    private String inDevItfAlias;
    @ApiModelProperty("出接口别名 ")
    private String outDevItfAlias;
    @ApiModelProperty("本条策略创建的服务对象名称集合")
    List<String> serviceObjectNameList;
    @ApiModelProperty("本条策略创建的服务组对象名称集合")
    List<String> serviceObjectGroupNameList;
    @ApiModelProperty("本条策略创建的地址对象名称集合")
    List<String> addressObjectNameList;
    @ApiModelProperty("本条策略创建的地址组对象名称集合")
    List<String> addressObjectGroupNameList;
    @ApiModelProperty("已存在源地址对象列表")
    List<String> existSrcAddressList = new ArrayList();
    @ApiModelProperty("还需要创建源地址对象列表")
    List<String> restSrcAddressList = new ArrayList();
    @ApiModelProperty("已存在目的地址对象列表")
    List<String> existDstAddressList = new ArrayList();
    @ApiModelProperty("还需要创建目的地址对象列表")
    List<String> restDstAddressList = new ArrayList();
    @ApiModelProperty("源地址描述")
    String srcIpSystem;
    @ApiModelProperty("目的地址描述")
    String dstIpSystem;
    @ApiModelProperty("转换后源地址所属系统")
    String postSrcIpSystem;
    @ApiModelProperty("转换后源地址所属系统")
    String postDstIpSystem;
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    @ApiModelProperty("华3V7当前地址池id")
    String currentAddressGroupId;
    @ApiModelProperty("bothNat回滚命令行（思科）  解释：这个对于回滚的执行器又去调nat创建接口 获取回滚命令行 每次都生成了随机数的对象，导致命令行生成的和回滚中的的对象名称对不上")
    String rollbackCommandLine;
    @ApiModelProperty("回滚查询命令行")
    String rollbackShowCmd;
    @ApiModelProperty("命令行中的策略名称  策略名称在生成命令的时候要根据工单号_AO_随机数,要传到后面流程使用")
    String policyName;
    @ApiModelProperty("用于不同类型地址回滚命令行。比如每种地址类型都不同，拼接不用规则的命令行地址。 地址类型map ，key为地址名称，value为地址的类型 host主机，sub子网 rang 范围")
    Map<String, String> addressTypeMap = new HashMap();
    String userName;// Metoo
    String branchLevel;// Metoo

    public NatPolicyDTO() {
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

    public String getDstIp() {
        return this.dstIp;
    }

    public List<ServiceDTO> getServiceList() {
        return this.serviceList;
    }

    public List<ServiceDTO> getPostServiceList() {
        return this.postServiceList;
    }

    public String getPostSrcIp() {
        return this.postSrcIp;
    }

    public String getPostDstIp() {
        return this.postDstIp;
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

    public String getSrcAddressObjectName() {
        return this.srcAddressObjectName;
    }

    public String getDstAddressObjectName() {
        return this.dstAddressObjectName;
    }

    public String getPostSrcAddressObjectName() {
        return this.postSrcAddressObjectName;
    }

    public String getPostDstAddressObjectName() {
        return this.postDstAddressObjectName;
    }

    public String getPostServiceObjectName() {
        return this.postServiceObjectName;
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

    public ExistObjectRefDTO getSpecialExistObject() {
        return this.specialExistObject;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    public boolean isRollback() {
        return this.isRollback;
    }

    public List<String> getRestPostSrcAddressList() {
        return this.restPostSrcAddressList;
    }

    public List<String> getExistPostSrcAddressList() {
        return this.existPostSrcAddressList;
    }

    public List<String> getExistPostDstAddressList() {
        return this.existPostDstAddressList;
    }

    public List<String> getRestPostDstAddressList() {
        return this.restPostDstAddressList;
    }

    public String getInDevItfAlias() {
        return this.inDevItfAlias;
    }

    public String getOutDevItfAlias() {
        return this.outDevItfAlias;
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

    public List<String> getExistSrcAddressList() {
        return this.existSrcAddressList;
    }

    public List<String> getRestSrcAddressList() {
        return this.restSrcAddressList;
    }

    public List<String> getExistDstAddressList() {
        return this.existDstAddressList;
    }

    public List<String> getRestDstAddressList() {
        return this.restDstAddressList;
    }

    public String getSrcIpSystem() {
        return this.srcIpSystem;
    }

    public String getDstIpSystem() {
        return this.dstIpSystem;
    }

    public String getPostSrcIpSystem() {
        return this.postSrcIpSystem;
    }

    public String getPostDstIpSystem() {
        return this.postDstIpSystem;
    }

    public Integer getIpType() {
        return this.ipType;
    }

    public String getCurrentAddressGroupId() {
        return this.currentAddressGroupId;
    }

    public String getRollbackCommandLine() {
        return this.rollbackCommandLine;
    }

    public String getRollbackShowCmd() {
        return this.rollbackShowCmd;
    }

    public String getPolicyName() {
        return this.policyName;
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

    public void setDstIp(final String dstIp) {
        this.dstIp = dstIp;
    }

    public void setServiceList(final List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public void setPostServiceList(final List<ServiceDTO> postServiceList) {
        this.postServiceList = postServiceList;
    }

    public void setPostSrcIp(final String postSrcIp) {
        this.postSrcIp = postSrcIp;
    }

    public void setPostDstIp(final String postDstIp) {
        this.postDstIp = postDstIp;
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

    public void setSrcAddressObjectName(final String srcAddressObjectName) {
        this.srcAddressObjectName = srcAddressObjectName;
    }

    public void setDstAddressObjectName(final String dstAddressObjectName) {
        this.dstAddressObjectName = dstAddressObjectName;
    }

    public void setPostSrcAddressObjectName(final String postSrcAddressObjectName) {
        this.postSrcAddressObjectName = postSrcAddressObjectName;
    }

    public void setPostDstAddressObjectName(final String postDstAddressObjectName) {
        this.postDstAddressObjectName = postDstAddressObjectName;
    }

    public void setPostServiceObjectName(final String postServiceObjectName) {
        this.postServiceObjectName = postServiceObjectName;
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

    public void setSpecialExistObject(final ExistObjectRefDTO specialExistObject) {
        this.specialExistObject = specialExistObject;
    }

    public void setDynamic(final boolean dynamic) {
        this.dynamic = dynamic;
    }

    public void setRollback(final boolean isRollback) {
        this.isRollback = isRollback;
    }

    public void setRestPostSrcAddressList(final List<String> restPostSrcAddressList) {
        this.restPostSrcAddressList = restPostSrcAddressList;
    }

    public void setExistPostSrcAddressList(final List<String> existPostSrcAddressList) {
        this.existPostSrcAddressList = existPostSrcAddressList;
    }

    public void setExistPostDstAddressList(final List<String> existPostDstAddressList) {
        this.existPostDstAddressList = existPostDstAddressList;
    }

    public void setRestPostDstAddressList(final List<String> restPostDstAddressList) {
        this.restPostDstAddressList = restPostDstAddressList;
    }

    public void setInDevItfAlias(final String inDevItfAlias) {
        this.inDevItfAlias = inDevItfAlias;
    }

    public void setOutDevItfAlias(final String outDevItfAlias) {
        this.outDevItfAlias = outDevItfAlias;
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

    public void setExistSrcAddressList(final List<String> existSrcAddressList) {
        this.existSrcAddressList = existSrcAddressList;
    }

    public void setRestSrcAddressList(final List<String> restSrcAddressList) {
        this.restSrcAddressList = restSrcAddressList;
    }

    public void setExistDstAddressList(final List<String> existDstAddressList) {
        this.existDstAddressList = existDstAddressList;
    }

    public void setRestDstAddressList(final List<String> restDstAddressList) {
        this.restDstAddressList = restDstAddressList;
    }

    public void setSrcIpSystem(final String srcIpSystem) {
        this.srcIpSystem = srcIpSystem;
    }

    public void setDstIpSystem(final String dstIpSystem) {
        this.dstIpSystem = dstIpSystem;
    }

    public void setPostSrcIpSystem(final String postSrcIpSystem) {
        this.postSrcIpSystem = postSrcIpSystem;
    }

    public void setPostDstIpSystem(final String postDstIpSystem) {
        this.postDstIpSystem = postDstIpSystem;
    }

    public void setIpType(final Integer ipType) {
        this.ipType = ipType;
    }

    public void setCurrentAddressGroupId(final String currentAddressGroupId) {
        this.currentAddressGroupId = currentAddressGroupId;
    }

    public void setRollbackCommandLine(final String rollbackCommandLine) {
        this.rollbackCommandLine = rollbackCommandLine;
    }

    public void setRollbackShowCmd(final String rollbackShowCmd) {
        this.rollbackShowCmd = rollbackShowCmd;
    }

    public void setPolicyName(final String policyName) {
        this.policyName = policyName;
    }

    public void setAddressTypeMap(final Map<String, String> addressTypeMap) {
        this.addressTypeMap = addressTypeMap;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof NatPolicyDTO)) {
            return false;
        } else {
            NatPolicyDTO other = (NatPolicyDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label679: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label679;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label679;
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

                label665: {
                    Object this$theme = this.getTheme();
                    Object other$theme = other.getTheme();
                    if (this$theme == null) {
                        if (other$theme == null) {
                            break label665;
                        }
                    } else if (this$theme.equals(other$theme)) {
                        break label665;
                    }

                    return false;
                }

                Object this$deviceUuid = this.getDeviceUuid();
                Object other$deviceUuid = other.getDeviceUuid();
                if (this$deviceUuid == null) {
                    if (other$deviceUuid != null) {
                        return false;
                    }
                } else if (!this$deviceUuid.equals(other$deviceUuid)) {
                    return false;
                }

                label651: {
                    Object this$srcIp = this.getSrcIp();
                    Object other$srcIp = other.getSrcIp();
                    if (this$srcIp == null) {
                        if (other$srcIp == null) {
                            break label651;
                        }
                    } else if (this$srcIp.equals(other$srcIp)) {
                        break label651;
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

                label637: {
                    Object this$serviceList = this.getServiceList();
                    Object other$serviceList = other.getServiceList();
                    if (this$serviceList == null) {
                        if (other$serviceList == null) {
                            break label637;
                        }
                    } else if (this$serviceList.equals(other$serviceList)) {
                        break label637;
                    }

                    return false;
                }

                label630: {
                    Object this$postServiceList = this.getPostServiceList();
                    Object other$postServiceList = other.getPostServiceList();
                    if (this$postServiceList == null) {
                        if (other$postServiceList == null) {
                            break label630;
                        }
                    } else if (this$postServiceList.equals(other$postServiceList)) {
                        break label630;
                    }

                    return false;
                }

                Object this$postSrcIp = this.getPostSrcIp();
                Object other$postSrcIp = other.getPostSrcIp();
                if (this$postSrcIp == null) {
                    if (other$postSrcIp != null) {
                        return false;
                    }
                } else if (!this$postSrcIp.equals(other$postSrcIp)) {
                    return false;
                }

                label616: {
                    Object this$postDstIp = this.getPostDstIp();
                    Object other$postDstIp = other.getPostDstIp();
                    if (this$postDstIp == null) {
                        if (other$postDstIp == null) {
                            break label616;
                        }
                    } else if (this$postDstIp.equals(other$postDstIp)) {
                        break label616;
                    }

                    return false;
                }

                label609: {
                    Object this$postPort = this.getPostPort();
                    Object other$postPort = other.getPostPort();
                    if (this$postPort == null) {
                        if (other$postPort == null) {
                            break label609;
                        }
                    } else if (this$postPort.equals(other$postPort)) {
                        break label609;
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

                label588: {
                    Object this$dstZone = this.getDstZone();
                    Object other$dstZone = other.getDstZone();
                    if (this$dstZone == null) {
                        if (other$dstZone == null) {
                            break label588;
                        }
                    } else if (this$dstZone.equals(other$dstZone)) {
                        break label588;
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

                label567: {
                    Object this$serviceObjectName = this.getServiceObjectName();
                    Object other$serviceObjectName = other.getServiceObjectName();
                    if (this$serviceObjectName == null) {
                        if (other$serviceObjectName == null) {
                            break label567;
                        }
                    } else if (this$serviceObjectName.equals(other$serviceObjectName)) {
                        break label567;
                    }

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

                label553: {
                    Object this$dstAddressObjectName = this.getDstAddressObjectName();
                    Object other$dstAddressObjectName = other.getDstAddressObjectName();
                    if (this$dstAddressObjectName == null) {
                        if (other$dstAddressObjectName == null) {
                            break label553;
                        }
                    } else if (this$dstAddressObjectName.equals(other$dstAddressObjectName)) {
                        break label553;
                    }

                    return false;
                }

                Object this$postSrcAddressObjectName = this.getPostSrcAddressObjectName();
                Object other$postSrcAddressObjectName = other.getPostSrcAddressObjectName();
                if (this$postSrcAddressObjectName == null) {
                    if (other$postSrcAddressObjectName != null) {
                        return false;
                    }
                } else if (!this$postSrcAddressObjectName.equals(other$postSrcAddressObjectName)) {
                    return false;
                }

                label539: {
                    Object this$postDstAddressObjectName = this.getPostDstAddressObjectName();
                    Object other$postDstAddressObjectName = other.getPostDstAddressObjectName();
                    if (this$postDstAddressObjectName == null) {
                        if (other$postDstAddressObjectName == null) {
                            break label539;
                        }
                    } else if (this$postDstAddressObjectName.equals(other$postDstAddressObjectName)) {
                        break label539;
                    }

                    return false;
                }

                Object this$postServiceObjectName = this.getPostServiceObjectName();
                Object other$postServiceObjectName = other.getPostServiceObjectName();
                if (this$postServiceObjectName == null) {
                    if (other$postServiceObjectName != null) {
                        return false;
                    }
                } else if (!this$postServiceObjectName.equals(other$postServiceObjectName)) {
                    return false;
                }

                if (this.isVsys() != other.isVsys()) {
                    return false;
                } else {
                    label524: {
                        Object this$vsysName = this.getVsysName();
                        Object other$vsysName = other.getVsysName();
                        if (this$vsysName == null) {
                            if (other$vsysName == null) {
                                break label524;
                            }
                        } else if (this$vsysName.equals(other$vsysName)) {
                            break label524;
                        }

                        return false;
                    }

                    if (this.isHasVsys() != other.isHasVsys()) {
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

                        label507: {
                            Object this$swapRuleNameId = this.getSwapRuleNameId();
                            Object other$swapRuleNameId = other.getSwapRuleNameId();
                            if (this$swapRuleNameId == null) {
                                if (other$swapRuleNameId == null) {
                                    break label507;
                                }
                            } else if (this$swapRuleNameId.equals(other$swapRuleNameId)) {
                                break label507;
                            }

                            return false;
                        }

                        label500: {
                            Object this$restServiceList = this.getRestServiceList();
                            Object other$restServiceList = other.getRestServiceList();
                            if (this$restServiceList == null) {
                                if (other$restServiceList == null) {
                                    break label500;
                                }
                            } else if (this$restServiceList.equals(other$restServiceList)) {
                                break label500;
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

                        label486: {
                            Object this$existPostServiceNameList = this.getExistPostServiceNameList();
                            Object other$existPostServiceNameList = other.getExistPostServiceNameList();
                            if (this$existPostServiceNameList == null) {
                                if (other$existPostServiceNameList == null) {
                                    break label486;
                                }
                            } else if (this$existPostServiceNameList.equals(other$existPostServiceNameList)) {
                                break label486;
                            }

                            return false;
                        }

                        label479: {
                            Object this$restPostServiceList = this.getRestPostServiceList();
                            Object other$restPostServiceList = other.getRestPostServiceList();
                            if (this$restPostServiceList == null) {
                                if (other$restPostServiceList == null) {
                                    break label479;
                                }
                            } else if (this$restPostServiceList.equals(other$restPostServiceList)) {
                                break label479;
                            }

                            return false;
                        }

                        label472: {
                            Object this$specialExistObject = this.getSpecialExistObject();
                            Object other$specialExistObject = other.getSpecialExistObject();
                            if (this$specialExistObject == null) {
                                if (other$specialExistObject == null) {
                                    break label472;
                                }
                            } else if (this$specialExistObject.equals(other$specialExistObject)) {
                                break label472;
                            }

                            return false;
                        }

                        if (this.isDynamic() != other.isDynamic()) {
                            return false;
                        } else if (this.isRollback() != other.isRollback()) {
                            return false;
                        } else {
                            label462: {
                                Object this$restPostSrcAddressList = this.getRestPostSrcAddressList();
                                Object other$restPostSrcAddressList = other.getRestPostSrcAddressList();
                                if (this$restPostSrcAddressList == null) {
                                    if (other$restPostSrcAddressList == null) {
                                        break label462;
                                    }
                                } else if (this$restPostSrcAddressList.equals(other$restPostSrcAddressList)) {
                                    break label462;
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

                            Object this$existPostDstAddressList = this.getExistPostDstAddressList();
                            Object other$existPostDstAddressList = other.getExistPostDstAddressList();
                            if (this$existPostDstAddressList == null) {
                                if (other$existPostDstAddressList != null) {
                                    return false;
                                }
                            } else if (!this$existPostDstAddressList.equals(other$existPostDstAddressList)) {
                                return false;
                            }

                            label441: {
                                Object this$restPostDstAddressList = this.getRestPostDstAddressList();
                                Object other$restPostDstAddressList = other.getRestPostDstAddressList();
                                if (this$restPostDstAddressList == null) {
                                    if (other$restPostDstAddressList == null) {
                                        break label441;
                                    }
                                } else if (this$restPostDstAddressList.equals(other$restPostDstAddressList)) {
                                    break label441;
                                }

                                return false;
                            }

                            label434: {
                                Object this$inDevItfAlias = this.getInDevItfAlias();
                                Object other$inDevItfAlias = other.getInDevItfAlias();
                                if (this$inDevItfAlias == null) {
                                    if (other$inDevItfAlias == null) {
                                        break label434;
                                    }
                                } else if (this$inDevItfAlias.equals(other$inDevItfAlias)) {
                                    break label434;
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

                            Object this$serviceObjectNameList = this.getServiceObjectNameList();
                            Object other$serviceObjectNameList = other.getServiceObjectNameList();
                            if (this$serviceObjectNameList == null) {
                                if (other$serviceObjectNameList != null) {
                                    return false;
                                }
                            } else if (!this$serviceObjectNameList.equals(other$serviceObjectNameList)) {
                                return false;
                            }

                            label413: {
                                Object this$serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
                                Object other$serviceObjectGroupNameList = other.getServiceObjectGroupNameList();
                                if (this$serviceObjectGroupNameList == null) {
                                    if (other$serviceObjectGroupNameList == null) {
                                        break label413;
                                    }
                                } else if (this$serviceObjectGroupNameList.equals(other$serviceObjectGroupNameList)) {
                                    break label413;
                                }

                                return false;
                            }

                            label406: {
                                Object this$addressObjectNameList = this.getAddressObjectNameList();
                                Object other$addressObjectNameList = other.getAddressObjectNameList();
                                if (this$addressObjectNameList == null) {
                                    if (other$addressObjectNameList == null) {
                                        break label406;
                                    }
                                } else if (this$addressObjectNameList.equals(other$addressObjectNameList)) {
                                    break label406;
                                }

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

                            label392: {
                                Object this$existSrcAddressList = this.getExistSrcAddressList();
                                Object other$existSrcAddressList = other.getExistSrcAddressList();
                                if (this$existSrcAddressList == null) {
                                    if (other$existSrcAddressList == null) {
                                        break label392;
                                    }
                                } else if (this$existSrcAddressList.equals(other$existSrcAddressList)) {
                                    break label392;
                                }

                                return false;
                            }

                            Object this$restSrcAddressList = this.getRestSrcAddressList();
                            Object other$restSrcAddressList = other.getRestSrcAddressList();
                            if (this$restSrcAddressList == null) {
                                if (other$restSrcAddressList != null) {
                                    return false;
                                }
                            } else if (!this$restSrcAddressList.equals(other$restSrcAddressList)) {
                                return false;
                            }

                            label378: {
                                Object this$existDstAddressList = this.getExistDstAddressList();
                                Object other$existDstAddressList = other.getExistDstAddressList();
                                if (this$existDstAddressList == null) {
                                    if (other$existDstAddressList == null) {
                                        break label378;
                                    }
                                } else if (this$existDstAddressList.equals(other$existDstAddressList)) {
                                    break label378;
                                }

                                return false;
                            }

                            Object this$restDstAddressList = this.getRestDstAddressList();
                            Object other$restDstAddressList = other.getRestDstAddressList();
                            if (this$restDstAddressList == null) {
                                if (other$restDstAddressList != null) {
                                    return false;
                                }
                            } else if (!this$restDstAddressList.equals(other$restDstAddressList)) {
                                return false;
                            }

                            Object this$srcIpSystem = this.getSrcIpSystem();
                            Object other$srcIpSystem = other.getSrcIpSystem();
                            if (this$srcIpSystem == null) {
                                if (other$srcIpSystem != null) {
                                    return false;
                                }
                            } else if (!this$srcIpSystem.equals(other$srcIpSystem)) {
                                return false;
                            }

                            Object this$dstIpSystem = this.getDstIpSystem();
                            Object other$dstIpSystem = other.getDstIpSystem();
                            if (this$dstIpSystem == null) {
                                if (other$dstIpSystem != null) {
                                    return false;
                                }
                            } else if (!this$dstIpSystem.equals(other$dstIpSystem)) {
                                return false;
                            }

                            label350: {
                                Object this$postSrcIpSystem = this.getPostSrcIpSystem();
                                Object other$postSrcIpSystem = other.getPostSrcIpSystem();
                                if (this$postSrcIpSystem == null) {
                                    if (other$postSrcIpSystem == null) {
                                        break label350;
                                    }
                                } else if (this$postSrcIpSystem.equals(other$postSrcIpSystem)) {
                                    break label350;
                                }

                                return false;
                            }

                            Object this$postDstIpSystem = this.getPostDstIpSystem();
                            Object other$postDstIpSystem = other.getPostDstIpSystem();
                            if (this$postDstIpSystem == null) {
                                if (other$postDstIpSystem != null) {
                                    return false;
                                }
                            } else if (!this$postDstIpSystem.equals(other$postDstIpSystem)) {
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

                            label329: {
                                Object this$currentAddressGroupId = this.getCurrentAddressGroupId();
                                Object other$currentAddressGroupId = other.getCurrentAddressGroupId();
                                if (this$currentAddressGroupId == null) {
                                    if (other$currentAddressGroupId == null) {
                                        break label329;
                                    }
                                } else if (this$currentAddressGroupId.equals(other$currentAddressGroupId)) {
                                    break label329;
                                }

                                return false;
                            }

                            label322: {
                                Object this$rollbackCommandLine = this.getRollbackCommandLine();
                                Object other$rollbackCommandLine = other.getRollbackCommandLine();
                                if (this$rollbackCommandLine == null) {
                                    if (other$rollbackCommandLine == null) {
                                        break label322;
                                    }
                                } else if (this$rollbackCommandLine.equals(other$rollbackCommandLine)) {
                                    break label322;
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

                            Object this$policyName = this.getPolicyName();
                            Object other$policyName = other.getPolicyName();
                            if (this$policyName == null) {
                                if (other$policyName != null) {
                                    return false;
                                }
                            } else if (!this$policyName.equals(other$policyName)) {
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
        return other instanceof NatPolicyDTO;
    }

    public int hashCode() {
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
        Object $dstIp = this.getDstIp();
        result = result * 59 + ($dstIp == null ? 43 : $dstIp.hashCode());
        Object $serviceList = this.getServiceList();
        result = result * 59 + ($serviceList == null ? 43 : $serviceList.hashCode());
        Object $postServiceList = this.getPostServiceList();
        result = result * 59 + ($postServiceList == null ? 43 : $postServiceList.hashCode());
        Object $postSrcIp = this.getPostSrcIp();
        result = result * 59 + ($postSrcIp == null ? 43 : $postSrcIp.hashCode());
        Object $postDstIp = this.getPostDstIp();
        result = result * 59 + ($postDstIp == null ? 43 : $postDstIp.hashCode());
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
        Object $srcAddressObjectName = this.getSrcAddressObjectName();
        result = result * 59 + ($srcAddressObjectName == null ? 43 : $srcAddressObjectName.hashCode());
        Object $dstAddressObjectName = this.getDstAddressObjectName();
        result = result * 59 + ($dstAddressObjectName == null ? 43 : $dstAddressObjectName.hashCode());
        Object $postSrcAddressObjectName = this.getPostSrcAddressObjectName();
        result = result * 59 + ($postSrcAddressObjectName == null ? 43 : $postSrcAddressObjectName.hashCode());
        Object $postDstAddressObjectName = this.getPostDstAddressObjectName();
        result = result * 59 + ($postDstAddressObjectName == null ? 43 : $postDstAddressObjectName.hashCode());
        Object $postServiceObjectName = this.getPostServiceObjectName();
        result = result * 59 + ($postServiceObjectName == null ? 43 : $postServiceObjectName.hashCode());
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
        Object $specialExistObject = this.getSpecialExistObject();
        result = result * 59 + ($specialExistObject == null ? 43 : $specialExistObject.hashCode());
        result = result * 59 + (this.isDynamic() ? 79 : 97);
        result = result * 59 + (this.isRollback() ? 79 : 97);
        Object $restPostSrcAddressList = this.getRestPostSrcAddressList();
        result = result * 59 + ($restPostSrcAddressList == null ? 43 : $restPostSrcAddressList.hashCode());
        Object $existPostSrcAddressList = this.getExistPostSrcAddressList();
        result = result * 59 + ($existPostSrcAddressList == null ? 43 : $existPostSrcAddressList.hashCode());
        Object $existPostDstAddressList = this.getExistPostDstAddressList();
        result = result * 59 + ($existPostDstAddressList == null ? 43 : $existPostDstAddressList.hashCode());
        Object $restPostDstAddressList = this.getRestPostDstAddressList();
        result = result * 59 + ($restPostDstAddressList == null ? 43 : $restPostDstAddressList.hashCode());
        Object $inDevItfAlias = this.getInDevItfAlias();
        result = result * 59 + ($inDevItfAlias == null ? 43 : $inDevItfAlias.hashCode());
        Object $outDevItfAlias = this.getOutDevItfAlias();
        result = result * 59 + ($outDevItfAlias == null ? 43 : $outDevItfAlias.hashCode());
        Object $serviceObjectNameList = this.getServiceObjectNameList();
        result = result * 59 + ($serviceObjectNameList == null ? 43 : $serviceObjectNameList.hashCode());
        Object $serviceObjectGroupNameList = this.getServiceObjectGroupNameList();
        result = result * 59 + ($serviceObjectGroupNameList == null ? 43 : $serviceObjectGroupNameList.hashCode());
        Object $addressObjectNameList = this.getAddressObjectNameList();
        result = result * 59 + ($addressObjectNameList == null ? 43 : $addressObjectNameList.hashCode());
        Object $addressObjectGroupNameList = this.getAddressObjectGroupNameList();
        result = result * 59 + ($addressObjectGroupNameList == null ? 43 : $addressObjectGroupNameList.hashCode());
        Object $existSrcAddressList = this.getExistSrcAddressList();
        result = result * 59 + ($existSrcAddressList == null ? 43 : $existSrcAddressList.hashCode());
        Object $restSrcAddressList = this.getRestSrcAddressList();
        result = result * 59 + ($restSrcAddressList == null ? 43 : $restSrcAddressList.hashCode());
        Object $existDstAddressList = this.getExistDstAddressList();
        result = result * 59 + ($existDstAddressList == null ? 43 : $existDstAddressList.hashCode());
        Object $restDstAddressList = this.getRestDstAddressList();
        result = result * 59 + ($restDstAddressList == null ? 43 : $restDstAddressList.hashCode());
        Object $srcIpSystem = this.getSrcIpSystem();
        result = result * 59 + ($srcIpSystem == null ? 43 : $srcIpSystem.hashCode());
        Object $dstIpSystem = this.getDstIpSystem();
        result = result * 59 + ($dstIpSystem == null ? 43 : $dstIpSystem.hashCode());
        Object $postSrcIpSystem = this.getPostSrcIpSystem();
        result = result * 59 + ($postSrcIpSystem == null ? 43 : $postSrcIpSystem.hashCode());
        Object $postDstIpSystem = this.getPostDstIpSystem();
        result = result * 59 + ($postDstIpSystem == null ? 43 : $postDstIpSystem.hashCode());
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        Object $currentAddressGroupId = this.getCurrentAddressGroupId();
        result = result * 59 + ($currentAddressGroupId == null ? 43 : $currentAddressGroupId.hashCode());
        Object $rollbackCommandLine = this.getRollbackCommandLine();
        result = result * 59 + ($rollbackCommandLine == null ? 43 : $rollbackCommandLine.hashCode());
        Object $rollbackShowCmd = this.getRollbackShowCmd();
        result = result * 59 + ($rollbackShowCmd == null ? 43 : $rollbackShowCmd.hashCode());
        Object $policyName = this.getPolicyName();
        result = result * 59 + ($policyName == null ? 43 : $policyName.hashCode());
        Object $addressTypeMap = this.getAddressTypeMap();
        result = result * 59 + ($addressTypeMap == null ? 43 : $addressTypeMap.hashCode());
        return result;
    }

    public String toString() {
        return "NatPolicyDTO(id=" + this.getId() + ", taskId=" + this.getTaskId() + ", theme=" + this.getTheme() + ", deviceUuid=" + this.getDeviceUuid() + ", srcIp=" + this.getSrcIp() + ", dstIp=" + this.getDstIp() + ", serviceList=" + this.getServiceList() + ", postServiceList=" + this.getPostServiceList() + ", postSrcIp=" + this.getPostSrcIp() + ", postDstIp=" + this.getPostDstIp() + ", postPort=" + this.getPostPort() + ", srcZone=" + this.getSrcZone() + ", srcItf=" + this.getSrcItf() + ", dstZone=" + this.getDstZone() + ", dstItf=" + this.getDstItf() + ", description=" + this.getDescription() + ", serviceObjectName=" + this.getServiceObjectName() + ", srcAddressObjectName=" + this.getSrcAddressObjectName() + ", dstAddressObjectName=" + this.getDstAddressObjectName() + ", postSrcAddressObjectName=" + this.getPostSrcAddressObjectName() + ", postDstAddressObjectName=" + this.getPostDstAddressObjectName() + ", postServiceObjectName=" + this.getPostServiceObjectName() + ", isVsys=" + this.isVsys() + ", vsysName=" + this.getVsysName() + ", hasVsys=" + this.isHasVsys() + ", createObjFlag=" + this.isCreateObjFlag() + ", moveSeatEnum=" + this.getMoveSeatEnum() + ", swapRuleNameId=" + this.getSwapRuleNameId() + ", restServiceList=" + this.getRestServiceList() + ", existServiceNameList=" + this.getExistServiceNameList() + ", existPostServiceNameList=" + this.getExistPostServiceNameList() + ", restPostServiceList=" + this.getRestPostServiceList() + ", specialExistObject=" + this.getSpecialExistObject() + ", dynamic=" + this.isDynamic() + ", isRollback=" + this.isRollback() + ", restPostSrcAddressList=" + this.getRestPostSrcAddressList() + ", existPostSrcAddressList=" + this.getExistPostSrcAddressList() + ", existPostDstAddressList=" + this.getExistPostDstAddressList() + ", restPostDstAddressList=" + this.getRestPostDstAddressList() + ", inDevItfAlias=" + this.getInDevItfAlias() + ", outDevItfAlias=" + this.getOutDevItfAlias() + ", serviceObjectNameList=" + this.getServiceObjectNameList() + ", serviceObjectGroupNameList=" + this.getServiceObjectGroupNameList() + ", addressObjectNameList=" + this.getAddressObjectNameList() + ", addressObjectGroupNameList=" + this.getAddressObjectGroupNameList() + ", existSrcAddressList=" + this.getExistSrcAddressList() + ", restSrcAddressList=" + this.getRestSrcAddressList() + ", existDstAddressList=" + this.getExistDstAddressList() + ", restDstAddressList=" + this.getRestDstAddressList() + ", srcIpSystem=" + this.getSrcIpSystem() + ", dstIpSystem=" + this.getDstIpSystem() + ", postSrcIpSystem=" + this.getPostSrcIpSystem() + ", postDstIpSystem=" + this.getPostDstIpSystem() + ", ipType=" + this.getIpType() + ", currentAddressGroupId=" + this.getCurrentAddressGroupId() + ", rollbackCommandLine=" + this.getRollbackCommandLine() + ", rollbackShowCmd=" + this.getRollbackShowCmd() + ", policyName=" + this.getPolicyName() + ", addressTypeMap=" + this.getAddressTypeMap() + ")";
    }
}
