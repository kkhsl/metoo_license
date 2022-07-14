//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.recommend.dto.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.Authentication;

@ApiModel("仿真搜索条件")
public class SearchRecommendTaskDTO {
    @ApiModelProperty("ID")
    private String id;
    @ApiModelProperty("批量id")
    private String batchId;
    @ApiModelProperty("主题")
    private String theme;
    @ApiModelProperty("工单号")
    private String orderNumber;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("源地址")
    private String srcIp;
    @ApiModelProperty("目的地之")
    private String dstIp;
    @ApiModelProperty("协议")
    private String protocol;
    @ApiModelProperty("目的端口")
    private String dstPort;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("当前页")
    private Integer page;
    @ApiModelProperty("页面大小")
    private Integer pSize;
    @ApiModelProperty("是否服务any")
    private Boolean isServiceAny;
    private Authentication authentication;
    private Integer taskType;
    private String branchLevel;

    public SearchRecommendTaskDTO() {
    }

    public SearchRecommendTaskDTO(String id, String batchId, String theme, String orderNumber, String userName, String description, String srcIp, String dstIp, String protocol, String dstPort, String status, Integer page, Integer pSize, Boolean isServiceAny, Authentication authentication, Integer taskType) {
        this.id = id;
        this.batchId = batchId;
        this.theme = theme;
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.description = description;
        this.srcIp = srcIp;
        this.dstIp = dstIp;
        this.protocol = protocol;
        this.dstPort = dstPort;
        this.status = status;
        this.page = page;
        this.pSize = pSize;
        this.isServiceAny = isServiceAny;
        this.authentication = authentication;
        this.taskType = taskType;
    }

    public String getBranchLevel(){
        return this.branchLevel;
    }
    public String getId() {
        return this.id;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public String getTheme() {
        return this.theme;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSrcIp() {
        return this.srcIp;
    }

    public String getDstIp() {
        return this.dstIp;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getDstPort() {
        return this.dstPort;
    }

    public String getStatus() {
        return this.status;
    }

    public Integer getPage() {
        return this.page;
    }

    public Integer getPSize() {
        return this.pSize;
    }

    public Boolean getIsServiceAny() {
        return this.isServiceAny;
    }

    public Authentication getAuthentication() {
        return this.authentication;
    }

    public Integer getTaskType() {
        return this.taskType;
    }

    public void setBranchLevel(String branchLevel){
        this.branchLevel = branchLevel;
    }
    public void setId(final String id) {
        this.id = id;
    }

    public void setBatchId(final String batchId) {
        this.batchId = batchId;
    }

    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setSrcIp(final String srcIp) {
        this.srcIp = srcIp;
    }

    public void setDstIp(final String dstIp) {
        this.dstIp = dstIp;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public void setDstPort(final String dstPort) {
        this.dstPort = dstPort;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

    public void setPSize(final Integer pSize) {
        this.pSize = pSize;
    }

    public void setIsServiceAny(final Boolean isServiceAny) {
        this.isServiceAny = isServiceAny;
    }

    public void setAuthentication(final Authentication authentication) {
        this.authentication = authentication;
    }

    public void setTaskType(final Integer taskType) {
        this.taskType = taskType;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SearchRecommendTaskDTO)) {
            return false;
        } else {
            SearchRecommendTaskDTO other = (SearchRecommendTaskDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label203: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label203;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label203;
                    }

                    return false;
                }

                Object this$batchId = this.getBatchId();
                Object other$batchId = other.getBatchId();
                if (this$batchId == null) {
                    if (other$batchId != null) {
                        return false;
                    }
                } else if (!this$batchId.equals(other$batchId)) {
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

                label182: {
                    Object this$orderNumber = this.getOrderNumber();
                    Object other$orderNumber = other.getOrderNumber();
                    if (this$orderNumber == null) {
                        if (other$orderNumber == null) {
                            break label182;
                        }
                    } else if (this$orderNumber.equals(other$orderNumber)) {
                        break label182;
                    }

                    return false;
                }

                label175: {
                    Object this$userName = this.getUserName();
                    Object other$userName = other.getUserName();
                    if (this$userName == null) {
                        if (other$userName == null) {
                            break label175;
                        }
                    } else if (this$userName.equals(other$userName)) {
                        break label175;
                    }

                    return false;
                }

                label168: {
                    Object this$description = this.getDescription();
                    Object other$description = other.getDescription();
                    if (this$description == null) {
                        if (other$description == null) {
                            break label168;
                        }
                    } else if (this$description.equals(other$description)) {
                        break label168;
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

                label154: {
                    Object this$dstIp = this.getDstIp();
                    Object other$dstIp = other.getDstIp();
                    if (this$dstIp == null) {
                        if (other$dstIp == null) {
                            break label154;
                        }
                    } else if (this$dstIp.equals(other$dstIp)) {
                        break label154;
                    }

                    return false;
                }

                Object this$protocol = this.getProtocol();
                Object other$protocol = other.getProtocol();
                if (this$protocol == null) {
                    if (other$protocol != null) {
                        return false;
                    }
                } else if (!this$protocol.equals(other$protocol)) {
                    return false;
                }

                label140: {
                    Object this$dstPort = this.getDstPort();
                    Object other$dstPort = other.getDstPort();
                    if (this$dstPort == null) {
                        if (other$dstPort == null) {
                            break label140;
                        }
                    } else if (this$dstPort.equals(other$dstPort)) {
                        break label140;
                    }

                    return false;
                }

                Object this$status = this.getStatus();
                Object other$status = other.getStatus();
                if (this$status == null) {
                    if (other$status != null) {
                        return false;
                    }
                } else if (!this$status.equals(other$status)) {
                    return false;
                }

                Object this$page = this.getPage();
                Object other$page = other.getPage();
                if (this$page == null) {
                    if (other$page != null) {
                        return false;
                    }
                } else if (!this$page.equals(other$page)) {
                    return false;
                }

                label119: {
                    Object this$pSize = this.getPSize();
                    Object other$pSize = other.getPSize();
                    if (this$pSize == null) {
                        if (other$pSize == null) {
                            break label119;
                        }
                    } else if (this$pSize.equals(other$pSize)) {
                        break label119;
                    }

                    return false;
                }

                label112: {
                    Object this$isServiceAny = this.getIsServiceAny();
                    Object other$isServiceAny = other.getIsServiceAny();
                    if (this$isServiceAny == null) {
                        if (other$isServiceAny == null) {
                            break label112;
                        }
                    } else if (this$isServiceAny.equals(other$isServiceAny)) {
                        break label112;
                    }

                    return false;
                }

                Object this$authentication = this.getAuthentication();
                Object other$authentication = other.getAuthentication();
                if (this$authentication == null) {
                    if (other$authentication != null) {
                        return false;
                    }
                } else if (!this$authentication.equals(other$authentication)) {
                    return false;
                }

                Object this$taskType = this.getTaskType();
                Object other$taskType = other.getTaskType();
                if (this$taskType == null) {
                    if (other$taskType != null) {
                        return false;
                    }
                } else if (!this$taskType.equals(other$taskType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchRecommendTaskDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $batchId = this.getBatchId();
        result = result * 59 + ($batchId == null ? 43 : $batchId.hashCode());
        Object $theme = this.getTheme();
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        Object $orderNumber = this.getOrderNumber();
        result = result * 59 + ($orderNumber == null ? 43 : $orderNumber.hashCode());
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Object $description = this.getDescription();
        result = result * 59 + ($description == null ? 43 : $description.hashCode());
        Object $srcIp = this.getSrcIp();
        result = result * 59 + ($srcIp == null ? 43 : $srcIp.hashCode());
        Object $dstIp = this.getDstIp();
        result = result * 59 + ($dstIp == null ? 43 : $dstIp.hashCode());
        Object $protocol = this.getProtocol();
        result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
        Object $dstPort = this.getDstPort();
        result = result * 59 + ($dstPort == null ? 43 : $dstPort.hashCode());
        Object $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : $status.hashCode());
        Object $page = this.getPage();
        result = result * 59 + ($page == null ? 43 : $page.hashCode());
        Object $pSize = this.getPSize();
        result = result * 59 + ($pSize == null ? 43 : $pSize.hashCode());
        Object $isServiceAny = this.getIsServiceAny();
        result = result * 59 + ($isServiceAny == null ? 43 : $isServiceAny.hashCode());
        Object $authentication = this.getAuthentication();
        result = result * 59 + ($authentication == null ? 43 : $authentication.hashCode());
        Object $taskType = this.getTaskType();
        result = result * 59 + ($taskType == null ? 43 : $taskType.hashCode());
        return result;
    }

    public String toString() {
        return "SearchRecommendTaskDTO(id=" + this.getId() + ", batchId=" + this.getBatchId() + ", theme=" + this.getTheme() + ", orderNumber=" + this.getOrderNumber() + ", userName=" + this.getUserName() + ", description=" + this.getDescription() + ", srcIp=" + this.getSrcIp() + ", dstIp=" + this.getDstIp() + ", protocol=" + this.getProtocol() + ", dstPort=" + this.getDstPort() + ", status=" + this.getStatus() + ", page=" + this.getPage() + ", pSize=" + this.getPSize() + ", isServiceAny=" + this.getIsServiceAny() + ", authentication=" + this.getAuthentication() + ", taskType=" + this.getTaskType() + ")";
    }
}
