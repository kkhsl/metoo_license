//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.push.vo;

import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel("新建策略进行下发时，页面传递到后台")
public class NewPolicyPushVO {
    @ApiModelProperty("主题/工单号")
    private String theme;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("场景UUID")
    String scenesUuid;
    @ApiModelProperty("设备uuid")
    private String deviceUuid;
    @ApiModelProperty("源ip")
    private String srcIp;
    @ApiModelProperty("目的ip")
    private String dstIp;
    @ApiModelProperty("源域")
    private String srcZone;
    @ApiModelProperty("目的域")
    private String dstZone;
    @ApiModelProperty("协议")
    private List<ServiceDTO> serviceList;
    @ApiModelProperty("源接口")
    private String inDevIf;
    @ApiModelProperty("目的接口")
    private String outDevIf;
    @ApiModelProperty("开始时间-时间戳格式")
    private Long startTime;
    @ApiModelProperty("结束时间-时间戳格式")
    private Long endTime;
    @ApiModelProperty("当前登录的用户名")
    private String userName;
    @ApiModelProperty("动作")
    private String action;
    @ApiModelProperty("源地址所属系统")
    String srcIpSystem;
    @ApiModelProperty("目的地址所属系统")
    String dstIpSystem;
    @ApiModelProperty("自定义命令行")
    String commandLine;
    private List<Integer> pushTaskId;
    private int taskId;
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    @ApiModelProperty("选择域名策略的ip类型用于生成命令行  0：ipv4; 1:ipv6; ")
    private Integer urlType;
    @ApiModelProperty("范围过滤")
    private Boolean rangeFilter;
    @ApiModelProperty("合并检查")
    private Boolean mergeCheck;
    @ApiModelProperty("移动到冲突前")
    private Boolean beforeConflict;
    @ApiModelProperty("长连接")
    private Integer IdleTimeout;
    @ApiModelProperty("入接口别名 ")
    private String inDevItfAlias;
    @ApiModelProperty("出接口别名 ")
    private String outDevItfAlias;
    @ApiModelProperty("策略用户")
    private List<String> policyUserNames;
    @ApiModelProperty("策略应用")
    private List<String> policyApplications;
    private String branchLevel;// Metoo

    public NewPolicyPushVO() {
    }

    public String getBranchLevel() {
        return this.branchLevel;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getDescription() {
        return this.description;
    }

    public String getScenesUuid() {
        return this.scenesUuid;
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

    public String getSrcZone() {
        return this.srcZone;
    }

    public String getDstZone() {
        return this.dstZone;
    }

    public List<ServiceDTO> getServiceList() {
        return this.serviceList;
    }

    public String getInDevIf() {
        return this.inDevIf;
    }

    public String getOutDevIf() {
        return this.outDevIf;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getAction() {
        return this.action;
    }

    public String getSrcIpSystem() {
        return this.srcIpSystem;
    }

    public String getDstIpSystem() {
        return this.dstIpSystem;
    }

    public String getCommandLine() {
        return this.commandLine;
    }

    public List<Integer> getPushTaskId() {
        return this.pushTaskId;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public Integer getIpType() {
        return this.ipType;
    }

    public Integer getUrlType() {
        return this.urlType;
    }

    public Boolean getRangeFilter() {
        return this.rangeFilter;
    }

    public Boolean getMergeCheck() {
        return this.mergeCheck;
    }

    public Boolean getBeforeConflict() {
        return this.beforeConflict;
    }

    public Integer getIdleTimeout() {
        return this.IdleTimeout;
    }

    public String getInDevItfAlias() {
        return this.inDevItfAlias;
    }

    public String getOutDevItfAlias() {
        return this.outDevItfAlias;
    }

    public List<String> getPolicyUserNames() {
        return this.policyUserNames;
    }

    public List<String> getPolicyApplications() {
        return this.policyApplications;
    }

    public void setBranchLevel(String branchLevel){
        this.branchLevel=branchLevel;
    }
    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setScenesUuid(final String scenesUuid) {
        this.scenesUuid = scenesUuid;
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

    public void setSrcZone(final String srcZone) {
        this.srcZone = srcZone;
    }

    public void setDstZone(final String dstZone) {
        this.dstZone = dstZone;
    }

    public void setServiceList(final List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public void setInDevIf(final String inDevIf) {
        this.inDevIf = inDevIf;
    }

    public void setOutDevIf(final String outDevIf) {
        this.outDevIf = outDevIf;
    }

    public void setStartTime(final Long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final Long endTime) {
        this.endTime = endTime;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public void setSrcIpSystem(final String srcIpSystem) {
        this.srcIpSystem = srcIpSystem;
    }

    public void setDstIpSystem(final String dstIpSystem) {
        this.dstIpSystem = dstIpSystem;
    }

    public void setCommandLine(final String commandLine) {
        this.commandLine = commandLine;
    }

    public void setPushTaskId(final List<Integer> pushTaskId) {
        this.pushTaskId = pushTaskId;
    }

    public void setTaskId(final int taskId) {
        this.taskId = taskId;
    }

    public void setIpType(final Integer ipType) {
        this.ipType = ipType;
    }

    public void setUrlType(final Integer urlType) {
        this.urlType = urlType;
    }

    public void setRangeFilter(final Boolean rangeFilter) {
        this.rangeFilter = rangeFilter;
    }

    public void setMergeCheck(final Boolean mergeCheck) {
        this.mergeCheck = mergeCheck;
    }

    public void setBeforeConflict(final Boolean beforeConflict) {
        this.beforeConflict = beforeConflict;
    }

    public void setIdleTimeout(final Integer IdleTimeout) {
        this.IdleTimeout = IdleTimeout;
    }

    public void setInDevItfAlias(final String inDevItfAlias) {
        this.inDevItfAlias = inDevItfAlias;
    }

    public void setOutDevItfAlias(final String outDevItfAlias) {
        this.outDevItfAlias = outDevItfAlias;
    }

    public void setPolicyUserNames(final List<String> policyUserNames) {
        this.policyUserNames = policyUserNames;
    }

    public void setPolicyApplications(final List<String> policyApplications) {
        this.policyApplications = policyApplications;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof NewPolicyPushVO)) {
            return false;
        } else {
            NewPolicyPushVO other = (NewPolicyPushVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label363: {
                    Object this$theme = this.getTheme();
                    Object other$theme = other.getTheme();
                    if (this$theme == null) {
                        if (other$theme == null) {
                            break label363;
                        }
                    } else if (this$theme.equals(other$theme)) {
                        break label363;
                    }

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

                Object this$scenesUuid = this.getScenesUuid();
                Object other$scenesUuid = other.getScenesUuid();
                if (this$scenesUuid == null) {
                    if (other$scenesUuid != null) {
                        return false;
                    }
                } else if (!this$scenesUuid.equals(other$scenesUuid)) {
                    return false;
                }

                label342: {
                    Object this$deviceUuid = this.getDeviceUuid();
                    Object other$deviceUuid = other.getDeviceUuid();
                    if (this$deviceUuid == null) {
                        if (other$deviceUuid == null) {
                            break label342;
                        }
                    } else if (this$deviceUuid.equals(other$deviceUuid)) {
                        break label342;
                    }

                    return false;
                }

                label335: {
                    Object this$srcIp = this.getSrcIp();
                    Object other$srcIp = other.getSrcIp();
                    if (this$srcIp == null) {
                        if (other$srcIp == null) {
                            break label335;
                        }
                    } else if (this$srcIp.equals(other$srcIp)) {
                        break label335;
                    }

                    return false;
                }

                label328: {
                    Object this$dstIp = this.getDstIp();
                    Object other$dstIp = other.getDstIp();
                    if (this$dstIp == null) {
                        if (other$dstIp == null) {
                            break label328;
                        }
                    } else if (this$dstIp.equals(other$dstIp)) {
                        break label328;
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

                label314: {
                    Object this$dstZone = this.getDstZone();
                    Object other$dstZone = other.getDstZone();
                    if (this$dstZone == null) {
                        if (other$dstZone == null) {
                            break label314;
                        }
                    } else if (this$dstZone.equals(other$dstZone)) {
                        break label314;
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

                label300: {
                    Object this$inDevIf = this.getInDevIf();
                    Object other$inDevIf = other.getInDevIf();
                    if (this$inDevIf == null) {
                        if (other$inDevIf == null) {
                            break label300;
                        }
                    } else if (this$inDevIf.equals(other$inDevIf)) {
                        break label300;
                    }

                    return false;
                }

                Object this$outDevIf = this.getOutDevIf();
                Object other$outDevIf = other.getOutDevIf();
                if (this$outDevIf == null) {
                    if (other$outDevIf != null) {
                        return false;
                    }
                } else if (!this$outDevIf.equals(other$outDevIf)) {
                    return false;
                }

                Object this$startTime = this.getStartTime();
                Object other$startTime = other.getStartTime();
                if (this$startTime == null) {
                    if (other$startTime != null) {
                        return false;
                    }
                } else if (!this$startTime.equals(other$startTime)) {
                    return false;
                }

                label279: {
                    Object this$endTime = this.getEndTime();
                    Object other$endTime = other.getEndTime();
                    if (this$endTime == null) {
                        if (other$endTime == null) {
                            break label279;
                        }
                    } else if (this$endTime.equals(other$endTime)) {
                        break label279;
                    }

                    return false;
                }

                label272: {
                    Object this$userName = this.getUserName();
                    Object other$userName = other.getUserName();
                    if (this$userName == null) {
                        if (other$userName == null) {
                            break label272;
                        }
                    } else if (this$userName.equals(other$userName)) {
                        break label272;
                    }

                    return false;
                }

                Object this$action = this.getAction();
                Object other$action = other.getAction();
                if (this$action == null) {
                    if (other$action != null) {
                        return false;
                    }
                } else if (!this$action.equals(other$action)) {
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

                label251: {
                    Object this$dstIpSystem = this.getDstIpSystem();
                    Object other$dstIpSystem = other.getDstIpSystem();
                    if (this$dstIpSystem == null) {
                        if (other$dstIpSystem == null) {
                            break label251;
                        }
                    } else if (this$dstIpSystem.equals(other$dstIpSystem)) {
                        break label251;
                    }

                    return false;
                }

                Object this$commandLine = this.getCommandLine();
                Object other$commandLine = other.getCommandLine();
                if (this$commandLine == null) {
                    if (other$commandLine != null) {
                        return false;
                    }
                } else if (!this$commandLine.equals(other$commandLine)) {
                    return false;
                }

                Object this$pushTaskId = this.getPushTaskId();
                Object other$pushTaskId = other.getPushTaskId();
                if (this$pushTaskId == null) {
                    if (other$pushTaskId != null) {
                        return false;
                    }
                } else if (!this$pushTaskId.equals(other$pushTaskId)) {
                    return false;
                }

                if (this.getTaskId() != other.getTaskId()) {
                    return false;
                } else {
                    Object this$ipType = this.getIpType();
                    Object other$ipType = other.getIpType();
                    if (this$ipType == null) {
                        if (other$ipType != null) {
                            return false;
                        }
                    } else if (!this$ipType.equals(other$ipType)) {
                        return false;
                    }

                    Object this$urlType = this.getUrlType();
                    Object other$urlType = other.getUrlType();
                    if (this$urlType == null) {
                        if (other$urlType != null) {
                            return false;
                        }
                    } else if (!this$urlType.equals(other$urlType)) {
                        return false;
                    }

                    label215: {
                        Object this$rangeFilter = this.getRangeFilter();
                        Object other$rangeFilter = other.getRangeFilter();
                        if (this$rangeFilter == null) {
                            if (other$rangeFilter == null) {
                                break label215;
                            }
                        } else if (this$rangeFilter.equals(other$rangeFilter)) {
                            break label215;
                        }

                        return false;
                    }

                    label208: {
                        Object this$mergeCheck = this.getMergeCheck();
                        Object other$mergeCheck = other.getMergeCheck();
                        if (this$mergeCheck == null) {
                            if (other$mergeCheck == null) {
                                break label208;
                            }
                        } else if (this$mergeCheck.equals(other$mergeCheck)) {
                            break label208;
                        }

                        return false;
                    }

                    Object this$beforeConflict = this.getBeforeConflict();
                    Object other$beforeConflict = other.getBeforeConflict();
                    if (this$beforeConflict == null) {
                        if (other$beforeConflict != null) {
                            return false;
                        }
                    } else if (!this$beforeConflict.equals(other$beforeConflict)) {
                        return false;
                    }

                    Object this$IdleTimeout = this.getIdleTimeout();
                    Object other$IdleTimeout = other.getIdleTimeout();
                    if (this$IdleTimeout == null) {
                        if (other$IdleTimeout != null) {
                            return false;
                        }
                    } else if (!this$IdleTimeout.equals(other$IdleTimeout)) {
                        return false;
                    }

                    label187: {
                        Object this$inDevItfAlias = this.getInDevItfAlias();
                        Object other$inDevItfAlias = other.getInDevItfAlias();
                        if (this$inDevItfAlias == null) {
                            if (other$inDevItfAlias == null) {
                                break label187;
                            }
                        } else if (this$inDevItfAlias.equals(other$inDevItfAlias)) {
                            break label187;
                        }

                        return false;
                    }

                    label180: {
                        Object this$outDevItfAlias = this.getOutDevItfAlias();
                        Object other$outDevItfAlias = other.getOutDevItfAlias();
                        if (this$outDevItfAlias == null) {
                            if (other$outDevItfAlias == null) {
                                break label180;
                            }
                        } else if (this$outDevItfAlias.equals(other$outDevItfAlias)) {
                            break label180;
                        }

                        return false;
                    }

                    Object this$policyUserNames = this.getPolicyUserNames();
                    Object other$policyUserNames = other.getPolicyUserNames();
                    if (this$policyUserNames == null) {
                        if (other$policyUserNames != null) {
                            return false;
                        }
                    } else if (!this$policyUserNames.equals(other$policyUserNames)) {
                        return false;
                    }

                    Object this$policyApplications = this.getPolicyApplications();
                    Object other$policyApplications = other.getPolicyApplications();
                    if (this$policyApplications == null) {
                        if (other$policyApplications != null) {
                            return false;
                        }
                    } else if (!this$policyApplications.equals(other$policyApplications)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NewPolicyPushVO;
    }

    public int hashCode() {
        int result = 1;
        Object $theme = this.getTheme();
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $scenesUuid = this.getScenesUuid();
        result = result * 59 + ($scenesUuid == null ? 43 : $scenesUuid.hashCode());
        Object $deviceUuid = this.getDeviceUuid();
        result = result * 59 + ($deviceUuid == null ? 43 : $deviceUuid.hashCode());
        Object $srcIp = this.getSrcIp();
        result = result * 59 + ($srcIp == null ? 43 : $srcIp.hashCode());
        Object $dstIp = this.getDstIp();
        result = result * 59 + ($dstIp == null ? 43 : $dstIp.hashCode());
        Object $srcZone = this.getSrcZone();
        result = result * 59 + ($srcZone == null ? 43 : $srcZone.hashCode());
        Object $dstZone = this.getDstZone();
        result = result * 59 + ($dstZone == null ? 43 : $dstZone.hashCode());
        Object $serviceList = this.getServiceList();
        result = result * 59 + ($serviceList == null ? 43 : $serviceList.hashCode());
        Object $inDevIf = this.getInDevIf();
        result = result * 59 + ($inDevIf == null ? 43 : $inDevIf.hashCode());
        Object $outDevIf = this.getOutDevIf();
        result = result * 59 + ($outDevIf == null ? 43 : $outDevIf.hashCode());
        Object $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        Object $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Object $action = this.getAction();
        result = result * 59 + ($action == null ? 43 : $action.hashCode());
        Object $srcIpSystem = this.getSrcIpSystem();
        result = result * 59 + ($srcIpSystem == null ? 43 : $srcIpSystem.hashCode());
        Object $dstIpSystem = this.getDstIpSystem();
        result = result * 59 + ($dstIpSystem == null ? 43 : $dstIpSystem.hashCode());
        Object $commandLine = this.getCommandLine();
        result = result * 59 + ($commandLine == null ? 43 : $commandLine.hashCode());
        Object $pushTaskId = this.getPushTaskId();
        result = result * 59 + ($pushTaskId == null ? 43 : $pushTaskId.hashCode());
        result = result * 59 + this.getTaskId();
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        Object $urlType = this.getUrlType();
        result = result * 59 + ($urlType == null ? 43 : $urlType.hashCode());
        Object $rangeFilter = this.getRangeFilter();
        result = result * 59 + ($rangeFilter == null ? 43 : $rangeFilter.hashCode());
        Object $mergeCheck = this.getMergeCheck();
        result = result * 59 + ($mergeCheck == null ? 43 : $mergeCheck.hashCode());
        Object $beforeConflict = this.getBeforeConflict();
        result = result * 59 + ($beforeConflict == null ? 43 : $beforeConflict.hashCode());
        Object $IdleTimeout = this.getIdleTimeout();
        result = result * 59 + ($IdleTimeout == null ? 43 : $IdleTimeout.hashCode());
        Object $inDevItfAlias = this.getInDevItfAlias();
        result = result * 59 + ($inDevItfAlias == null ? 43 : $inDevItfAlias.hashCode());
        Object $outDevItfAlias = this.getOutDevItfAlias();
        result = result * 59 + ($outDevItfAlias == null ? 43 : $outDevItfAlias.hashCode());
        Object $policyUserNames = this.getPolicyUserNames();
        result = result * 59 + ($policyUserNames == null ? 43 : $policyUserNames.hashCode());
        Object $policyApplications = this.getPolicyApplications();
        result = result * 59 + ($policyApplications == null ? 43 : $policyApplications.hashCode());
        return result;
    }

    public String toString() {
        return "NewPolicyPushVO(theme=" + this.getTheme() + ", description=" + this.getDescription() + ", scenesUuid=" + this.getScenesUuid() + ", deviceUuid=" + this.getDeviceUuid() + ", srcIp=" + this.getSrcIp() + ", dstIp=" + this.getDstIp() + ", srcZone=" + this.getSrcZone() + ", dstZone=" + this.getDstZone() + ", serviceList=" + this.getServiceList() + ", inDevIf=" + this.getInDevIf() + ", outDevIf=" + this.getOutDevIf() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", userName=" + this.getUserName() + ", action=" + this.getAction() + ", srcIpSystem=" + this.getSrcIpSystem() + ", dstIpSystem=" + this.getDstIpSystem() + ", commandLine=" + this.getCommandLine() + ", pushTaskId=" + this.getPushTaskId() + ", taskId=" + this.getTaskId() + ", ipType=" + this.getIpType() + ", urlType=" + this.getUrlType() + ", rangeFilter=" + this.getRangeFilter() + ", mergeCheck=" + this.getMergeCheck() + ", beforeConflict=" + this.getBeforeConflict() + ", IdleTimeout=" + this.getIdleTimeout() + ", inDevItfAlias=" + this.getInDevItfAlias() + ", outDevItfAlias=" + this.getOutDevItfAlias() + ", policyUserNames=" + this.getPolicyUserNames() + ", policyApplications=" + this.getPolicyApplications() + ")";
    }
}
