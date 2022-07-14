package com.abtnetworks.totems.recommend.entity;

import com.abtnetworks.totems.common.dto.commandline.ServiceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel("添加互联网开通任务对象信息")
public class AddRecommendTaskEntity {
    @ApiModelProperty("id")
    String id;
    @ApiModelProperty("主题")
    String theme;
    @ApiModelProperty("描述")
    String description;
    @ApiModelProperty("源地址")
    String srcIp;
    @ApiModelProperty("源地址所属系统")
    String srcIpSystem;
    @ApiModelProperty("入口子网")
    String entrySubnet;
    @ApiModelProperty("目的地址")
    String dstIp;
    @ApiModelProperty("目的地址所属系统")
    String dstIpSystem;
    @ApiModelProperty("出口子网")
    String exitSubnet;
    @ApiModelProperty("模拟变更场景")
    String whatIfCases;
    @ApiModelProperty("服务")
    List<ServiceDTO> serviceList;
    @ApiModelProperty("开始时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date startTime;
    @ApiModelProperty("结束时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date endTime;
    @ApiModelProperty("长连接")
    private Integer idleTimeout;
    @ApiModelProperty("关联nat")
    private String relevancyNat;
    @ApiModelProperty("仿真开通类型：1：明细开通，8：内-》外开通 14 外->内开通  15 大网段开通")
    @NotNull(
            message = "仿真开通类型不能为空"
    )
    private Integer taskType;
    @Length(
            max = 512,
            message = "起点标签长度最大不能超过512"
    )
    @ApiModelProperty("起点标签")
    private String startLabel;
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    @ApiModelProperty("标签模式 OR，AND")
    private String labelModel;
    @ApiModelProperty("转换后源地址")
    private String postSrcIp;
    @ApiModelProperty("转换后目的地址")
    private String postDstIp;
    @ApiModelProperty("范围过滤")
    private boolean rangeFilter;
    @ApiModelProperty("合并检查")
    private boolean mergeCheck;
    @ApiModelProperty("移动冲突前")
    private boolean beforeConflict;
    @ApiModelProperty("关联的东西向工单ID")
    private Integer weTaskId;
    private String userName;
    private String branchLevel;

    public AddRecommendTaskEntity() {
    }

    public String getBranchLevel(){
        return this.branchLevel;
    }
    public String getUserName(){
        return this.userName;
    }

    public String getId() {
        return this.id;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSrcIp() {
        return this.srcIp;
    }

    public String getSrcIpSystem() {
        return this.srcIpSystem;
    }

    public String getEntrySubnet() {
        return this.entrySubnet;
    }

    public String getDstIp() {
        return this.dstIp;
    }

    public String getDstIpSystem() {
        return this.dstIpSystem;
    }

    public String getExitSubnet() {
        return this.exitSubnet;
    }

    public String getWhatIfCases() {
        return this.whatIfCases;
    }

    public List<ServiceDTO> getServiceList() {
        return this.serviceList;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public Integer getIdleTimeout() {
        return this.idleTimeout;
    }

    public String getRelevancyNat() {
        return this.relevancyNat;
    }

    public Integer getTaskType() {
        return this.taskType;
    }

    public String getStartLabel() {
        return this.startLabel;
    }

    public Integer getIpType() {
        return this.ipType;
    }

    public String getLabelModel() {
        return this.labelModel;
    }

    public String getPostSrcIp() {
        return this.postSrcIp;
    }

    public String getPostDstIp() {
        return this.postDstIp;
    }

    public boolean isRangeFilter() {
        return this.rangeFilter;
    }

    public boolean isMergeCheck() {
        return this.mergeCheck;
    }

    public boolean isBeforeConflict() {
        return this.beforeConflict;
    }

    public Integer getWeTaskId() {
        return this.weTaskId;
    }

    public void setBranchLevel(String branchLevel){
        this.branchLevel=branchLevel;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setId(final String id) {
        this.id = id;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setSrcIp(final String srcIp) {
        this.srcIp = srcIp;
    }

    public void setSrcIpSystem(final String srcIpSystem) {
        this.srcIpSystem = srcIpSystem;
    }

    public void setEntrySubnet(final String entrySubnet) {
        this.entrySubnet = entrySubnet;
    }

    public void setDstIp(final String dstIp) {
        this.dstIp = dstIp;
    }

    public void setDstIpSystem(final String dstIpSystem) {
        this.dstIpSystem = dstIpSystem;
    }

    public void setExitSubnet(final String exitSubnet) {
        this.exitSubnet = exitSubnet;
    }

    public void setWhatIfCases(final String whatIfCases) {
        this.whatIfCases = whatIfCases;
    }

    public void setServiceList(final List<ServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public void setIdleTimeout(final Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public void setRelevancyNat(final String relevancyNat) {
        this.relevancyNat = relevancyNat;
    }

    public void setTaskType(final Integer taskType) {
        this.taskType = taskType;
    }

    public void setStartLabel(final String startLabel) {
        this.startLabel = startLabel;
    }

    public void setIpType(final Integer ipType) {
        this.ipType = ipType;
    }

    public void setLabelModel(final String labelModel) {
        this.labelModel = labelModel;
    }

    public void setPostSrcIp(final String postSrcIp) {
        this.postSrcIp = postSrcIp;
    }

    public void setPostDstIp(final String postDstIp) {
        this.postDstIp = postDstIp;
    }

    public void setRangeFilter(final boolean rangeFilter) {
        this.rangeFilter = rangeFilter;
    }

    public void setMergeCheck(final boolean mergeCheck) {
        this.mergeCheck = mergeCheck;
    }

    public void setBeforeConflict(final boolean beforeConflict) {
        this.beforeConflict = beforeConflict;
    }

    public void setWeTaskId(final Integer weTaskId) {
        this.weTaskId = weTaskId;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AddRecommendTaskEntity)) {
            return false;
        } else {
            AddRecommendTaskEntity other = (AddRecommendTaskEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label287: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label287;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label287;
                    }

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

                Object this$description = this.getDescription();
                Object other$description = other.getDescription();
                if (this$description == null) {
                    if (other$description != null) {
                        return false;
                    }
                } else if (!this$description.equals(other$description)) {
                    return false;
                }

                label266: {
                    Object this$srcIp = this.getSrcIp();
                    Object other$srcIp = other.getSrcIp();
                    if (this$srcIp == null) {
                        if (other$srcIp == null) {
                            break label266;
                        }
                    } else if (this$srcIp.equals(other$srcIp)) {
                        break label266;
                    }

                    return false;
                }

                label259: {
                    Object this$srcIpSystem = this.getSrcIpSystem();
                    Object other$srcIpSystem = other.getSrcIpSystem();
                    if (this$srcIpSystem == null) {
                        if (other$srcIpSystem == null) {
                            break label259;
                        }
                    } else if (this$srcIpSystem.equals(other$srcIpSystem)) {
                        break label259;
                    }

                    return false;
                }

                Object this$entrySubnet = this.getEntrySubnet();
                Object other$entrySubnet = other.getEntrySubnet();
                if (this$entrySubnet == null) {
                    if (other$entrySubnet != null) {
                        return false;
                    }
                } else if (!this$entrySubnet.equals(other$entrySubnet)) {
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

                label238: {
                    Object this$dstIpSystem = this.getDstIpSystem();
                    Object other$dstIpSystem = other.getDstIpSystem();
                    if (this$dstIpSystem == null) {
                        if (other$dstIpSystem == null) {
                            break label238;
                        }
                    } else if (this$dstIpSystem.equals(other$dstIpSystem)) {
                        break label238;
                    }

                    return false;
                }

                label231: {
                    Object this$exitSubnet = this.getExitSubnet();
                    Object other$exitSubnet = other.getExitSubnet();
                    if (this$exitSubnet == null) {
                        if (other$exitSubnet == null) {
                            break label231;
                        }
                    } else if (this$exitSubnet.equals(other$exitSubnet)) {
                        break label231;
                    }

                    return false;
                }

                Object this$whatIfCases = this.getWhatIfCases();
                Object other$whatIfCases = other.getWhatIfCases();
                if (this$whatIfCases == null) {
                    if (other$whatIfCases != null) {
                        return false;
                    }
                } else if (!this$whatIfCases.equals(other$whatIfCases)) {
                    return false;
                }

                label217: {
                    Object this$serviceList = this.getServiceList();
                    Object other$serviceList = other.getServiceList();
                    if (this$serviceList == null) {
                        if (other$serviceList == null) {
                            break label217;
                        }
                    } else if (this$serviceList.equals(other$serviceList)) {
                        break label217;
                    }

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

                label203: {
                    Object this$endTime = this.getEndTime();
                    Object other$endTime = other.getEndTime();
                    if (this$endTime == null) {
                        if (other$endTime == null) {
                            break label203;
                        }
                    } else if (this$endTime.equals(other$endTime)) {
                        break label203;
                    }

                    return false;
                }

                Object this$idleTimeout = this.getIdleTimeout();
                Object other$idleTimeout = other.getIdleTimeout();
                if (this$idleTimeout == null) {
                    if (other$idleTimeout != null) {
                        return false;
                    }
                } else if (!this$idleTimeout.equals(other$idleTimeout)) {
                    return false;
                }

                Object this$relevancyNat = this.getRelevancyNat();
                Object other$relevancyNat = other.getRelevancyNat();
                if (this$relevancyNat == null) {
                    if (other$relevancyNat != null) {
                        return false;
                    }
                } else if (!this$relevancyNat.equals(other$relevancyNat)) {
                    return false;
                }

                label182: {
                    Object this$taskType = this.getTaskType();
                    Object other$taskType = other.getTaskType();
                    if (this$taskType == null) {
                        if (other$taskType == null) {
                            break label182;
                        }
                    } else if (this$taskType.equals(other$taskType)) {
                        break label182;
                    }

                    return false;
                }

                label175: {
                    Object this$startLabel = this.getStartLabel();
                    Object other$startLabel = other.getStartLabel();
                    if (this$startLabel == null) {
                        if (other$startLabel == null) {
                            break label175;
                        }
                    } else if (this$startLabel.equals(other$startLabel)) {
                        break label175;
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

                Object this$labelModel = this.getLabelModel();
                Object other$labelModel = other.getLabelModel();
                if (this$labelModel == null) {
                    if (other$labelModel != null) {
                        return false;
                    }
                } else if (!this$labelModel.equals(other$labelModel)) {
                    return false;
                }

                label154: {
                    Object this$postSrcIp = this.getPostSrcIp();
                    Object other$postSrcIp = other.getPostSrcIp();
                    if (this$postSrcIp == null) {
                        if (other$postSrcIp == null) {
                            break label154;
                        }
                    } else if (this$postSrcIp.equals(other$postSrcIp)) {
                        break label154;
                    }

                    return false;
                }

                label147: {
                    Object this$postDstIp = this.getPostDstIp();
                    Object other$postDstIp = other.getPostDstIp();
                    if (this$postDstIp == null) {
                        if (other$postDstIp == null) {
                            break label147;
                        }
                    } else if (this$postDstIp.equals(other$postDstIp)) {
                        break label147;
                    }

                    return false;
                }

                if (this.isRangeFilter() != other.isRangeFilter()) {
                    return false;
                } else if (this.isMergeCheck() != other.isMergeCheck()) {
                    return false;
                } else if (this.isBeforeConflict() != other.isBeforeConflict()) {
                    return false;
                } else {
                    Object this$weTaskId = this.getWeTaskId();
                    Object other$weTaskId = other.getWeTaskId();
                    if (this$weTaskId == null) {
                        if (other$weTaskId != null) {
                            return false;
                        }
                    } else if (!this$weTaskId.equals(other$weTaskId)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AddRecommendTaskEntity;
    }

    public int hashCode() {
        int PRIME = 0;
        int result0 = 1;
        Object $id = this.getId();
        int result = result0 * 59 + ($id == null ? 43 : $id.hashCode());
        Object $theme = this.getTheme();
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $srcIp = this.getSrcIp();
        result = result * 59 + ($srcIp == null ? 43 : $srcIp.hashCode());
        Object $srcIpSystem = this.getSrcIpSystem();
        result = result * 59 + ($srcIpSystem == null ? 43 : $srcIpSystem.hashCode());
        Object $entrySubnet = this.getEntrySubnet();
        result = result * 59 + ($entrySubnet == null ? 43 : $entrySubnet.hashCode());
        Object $dstIp = this.getDstIp();
        result = result * 59 + ($dstIp == null ? 43 : $dstIp.hashCode());
        Object $dstIpSystem = this.getDstIpSystem();
        result = result * 59 + ($dstIpSystem == null ? 43 : $dstIpSystem.hashCode());
        Object $exitSubnet = this.getExitSubnet();
        result = result * 59 + ($exitSubnet == null ? 43 : $exitSubnet.hashCode());
        Object $whatIfCases = this.getWhatIfCases();
        result = result * 59 + ($whatIfCases == null ? 43 : $whatIfCases.hashCode());
        Object $serviceList = this.getServiceList();
        result = result * 59 + ($serviceList == null ? 43 : $serviceList.hashCode());
        Object $startTime = this.getStartTime();
        result = result * 59 + ($startTime == null ? 43 : $startTime.hashCode());
        Object $endTime = this.getEndTime();
        result = result * 59 + ($endTime == null ? 43 : $endTime.hashCode());
        Object $idleTimeout = this.getIdleTimeout();
        result = result * 59 + ($idleTimeout == null ? 43 : $idleTimeout.hashCode());
        Object $relevancyNat = this.getRelevancyNat();
        result = result * 59 + ($relevancyNat == null ? 43 : $relevancyNat.hashCode());
        Object $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        Object $startLabel = this.getStartLabel();
        result = result * 59 + ($startLabel == null ? 43 : $startLabel.hashCode());
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        Object $labelModel = this.getLabelModel();
        result = result * 59 + ($labelModel == null ? 43 : $labelModel.hashCode());
        Object $postSrcIp = this.getPostSrcIp();
        result = result * 59 + ($postSrcIp == null ? 43 : $postSrcIp.hashCode());
        Object $postDstIp = this.getPostDstIp();
        result = result * 59 + ($postDstIp == null ? 43 : $postDstIp.hashCode());
        result = result * 59 + (this.isRangeFilter() ? 79 : 97);
        result = result * 59 + (this.isMergeCheck() ? 79 : 97);
        result = result * 59 + (this.isBeforeConflict() ? 79 : 97);
        Object $weTaskId = this.getWeTaskId();
        result = result * 59 + ($weTaskId == null ? 43 : $weTaskId.hashCode());
        return result;
    }

    public String toString() {
        return "AddRecommendTaskEntity(id=" + this.getId() + ", theme=" + this.getTheme() + ", description=" + this.getDescription() + ", srcIp=" + this.getSrcIp() + ", srcIpSystem=" + this.getSrcIpSystem() + ", entrySubnet=" + this.getEntrySubnet() + ", dstIp=" + this.getDstIp() + ", dstIpSystem=" + this.getDstIpSystem() + ", exitSubnet=" + this.getExitSubnet() + ", whatIfCases=" + this.getWhatIfCases() + ", serviceList=" + this.getServiceList() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", idleTimeout=" + this.getIdleTimeout() + ", relevancyNat=" + this.getRelevancyNat() + ", taskType=" + this.getTaskType() + ", startLabel=" + this.getStartLabel() + ", ipType=" + this.getIpType() + ", labelModel=" + this.getLabelModel() + ", postSrcIp=" + this.getPostSrcIp() + ", postDstIp=" + this.getPostDstIp() + ", rangeFilter=" + this.isRangeFilter() + ", mergeCheck=" + this.isMergeCheck() + ", beforeConflict=" + this.isBeforeConflict() + ", weTaskId=" + this.getWeTaskId() + ")";
    }
}
