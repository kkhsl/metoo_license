package com.abtnetworks.totems.common.dto.commandline;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("静态NAT数据类")
public class StaticNatPolicyDTO {
    @ApiModelProperty("主题（工单号）")
    private String theme;
    @ApiModelProperty("设备UUID")
    private String deviceUuid;
    @ApiModelProperty("策略名称")
    private String policyName;
    @ApiModelProperty("源域")
    private String srcZone;
    @ApiModelProperty("目的域")
    private String dstZone;
    @ApiModelProperty("源接口")
    private String inDevItf;
    @ApiModelProperty("目的接口")
    private String outDevItf;
    @ApiModelProperty("转换前地址")
    private String preIpAddress;
    @ApiModelProperty("转换后地址")
    private String postIpAddress;
    @ApiModelProperty("转换前端口")
    private String prePort;
    @ApiModelProperty("转换后端口")
    private String postPort;
    @ApiModelProperty("指定协议")
    private String protocol;
    @ApiModelProperty("入接口别名 ")
    private String inDevItfAlias;
    @ApiModelProperty("出接口别名 ")
    private String outDevItfAlias;
    @ApiModelProperty("ip类型  0：ipv4; 1:ipv6; 2:url ")
    private Integer ipType;
    private String userName;
    private String branchLevel;

    public StaticNatPolicyDTO() {
    }

    public String getUserName(){
        return this.userName;
    }
    public String getBranchLevel(){
        return this.branchLevel;
    }
    public String getTheme() {
        return this.theme;
    }

    public String getDeviceUuid() {
        return this.deviceUuid;
    }

    public String getPolicyName() {
        return this.policyName;
    }

    public String getSrcZone() {
        return this.srcZone;
    }

    public String getDstZone() {
        return this.dstZone;
    }

    public String getInDevItf() {
        return this.inDevItf;
    }

    public String getOutDevItf() {
        return this.outDevItf;
    }

    public String getPreIpAddress() {
        return this.preIpAddress;
    }

    public String getPostIpAddress() {
        return this.postIpAddress;
    }

    public String getPrePort() {
        return this.prePort;
    }

    public String getPostPort() {
        return this.postPort;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getInDevItfAlias() {
        return this.inDevItfAlias;
    }

    public String getOutDevItfAlias() {
        return this.outDevItfAlias;
    }

    public Integer getIpType() {
        return this.ipType;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setBranchLevel(String branchLevel){
        this.branchLevel = branchLevel;
    }
    public void setTheme(final String theme) {
        this.theme = theme;
    }

    public void setDeviceUuid(final String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public void setPolicyName(final String policyName) {
        this.policyName = policyName;
    }

    public void setSrcZone(final String srcZone) {
        this.srcZone = srcZone;
    }

    public void setDstZone(final String dstZone) {
        this.dstZone = dstZone;
    }

    public void setInDevItf(final String inDevItf) {
        this.inDevItf = inDevItf;
    }

    public void setOutDevItf(final String outDevItf) {
        this.outDevItf = outDevItf;
    }

    public void setPreIpAddress(final String preIpAddress) {
        this.preIpAddress = preIpAddress;
    }

    public void setPostIpAddress(final String postIpAddress) {
        this.postIpAddress = postIpAddress;
    }

    public void setPrePort(final String prePort) {
        this.prePort = prePort;
    }

    public void setPostPort(final String postPort) {
        this.postPort = postPort;
    }

    public void setProtocol(final String protocol) {
        this.protocol = protocol;
    }

    public void setInDevItfAlias(final String inDevItfAlias) {
        this.inDevItfAlias = inDevItfAlias;
    }

    public void setOutDevItfAlias(final String outDevItfAlias) {
        this.outDevItfAlias = outDevItfAlias;
    }

    public void setIpType(final Integer ipType) {
        this.ipType = ipType;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof StaticNatPolicyDTO)) {
            return false;
        } else {
            StaticNatPolicyDTO other = (StaticNatPolicyDTO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label191: {
                    Object this$theme = this.getTheme();
                    Object other$theme = other.getTheme();
                    if (this$theme == null) {
                        if (other$theme == null) {
                            break label191;
                        }
                    } else if (this$theme.equals(other$theme)) {
                        break label191;
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

                Object this$policyName = this.getPolicyName();
                Object other$policyName = other.getPolicyName();
                if (this$policyName == null) {
                    if (other$policyName != null) {
                        return false;
                    }
                } else if (!this$policyName.equals(other$policyName)) {
                    return false;
                }

                label170: {
                    Object this$srcZone = this.getSrcZone();
                    Object other$srcZone = other.getSrcZone();
                    if (this$srcZone == null) {
                        if (other$srcZone == null) {
                            break label170;
                        }
                    } else if (this$srcZone.equals(other$srcZone)) {
                        break label170;
                    }

                    return false;
                }

                label163: {
                    Object this$dstZone = this.getDstZone();
                    Object other$dstZone = other.getDstZone();
                    if (this$dstZone == null) {
                        if (other$dstZone == null) {
                            break label163;
                        }
                    } else if (this$dstZone.equals(other$dstZone)) {
                        break label163;
                    }

                    return false;
                }

                Object this$inDevItf = this.getInDevItf();
                Object other$inDevItf = other.getInDevItf();
                if (this$inDevItf == null) {
                    if (other$inDevItf != null) {
                        return false;
                    }
                } else if (!this$inDevItf.equals(other$inDevItf)) {
                    return false;
                }

                Object this$outDevItf = this.getOutDevItf();
                Object other$outDevItf = other.getOutDevItf();
                if (this$outDevItf == null) {
                    if (other$outDevItf != null) {
                        return false;
                    }
                } else if (!this$outDevItf.equals(other$outDevItf)) {
                    return false;
                }

                label142: {
                    Object this$preIpAddress = this.getPreIpAddress();
                    Object other$preIpAddress = other.getPreIpAddress();
                    if (this$preIpAddress == null) {
                        if (other$preIpAddress == null) {
                            break label142;
                        }
                    } else if (this$preIpAddress.equals(other$preIpAddress)) {
                        break label142;
                    }

                    return false;
                }

                label135: {
                    Object this$postIpAddress = this.getPostIpAddress();
                    Object other$postIpAddress = other.getPostIpAddress();
                    if (this$postIpAddress == null) {
                        if (other$postIpAddress == null) {
                            break label135;
                        }
                    } else if (this$postIpAddress.equals(other$postIpAddress)) {
                        break label135;
                    }

                    return false;
                }

                Object this$prePort = this.getPrePort();
                Object other$prePort = other.getPrePort();
                if (this$prePort == null) {
                    if (other$prePort != null) {
                        return false;
                    }
                } else if (!this$prePort.equals(other$prePort)) {
                    return false;
                }

                label121: {
                    Object this$postPort = this.getPostPort();
                    Object other$postPort = other.getPostPort();
                    if (this$postPort == null) {
                        if (other$postPort == null) {
                            break label121;
                        }
                    } else if (this$postPort.equals(other$postPort)) {
                        break label121;
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

                label107: {
                    Object this$inDevItfAlias = this.getInDevItfAlias();
                    Object other$inDevItfAlias = other.getInDevItfAlias();
                    if (this$inDevItfAlias == null) {
                        if (other$inDevItfAlias == null) {
                            break label107;
                        }
                    } else if (this$inDevItfAlias.equals(other$inDevItfAlias)) {
                        break label107;
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

                Object this$ipType = this.getIpType();
                Object other$ipType = other.getIpType();
                if (this$ipType == null) {
                    if (other$ipType != null) {
                        return false;
                    }
                } else if (!this$ipType.equals(other$ipType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StaticNatPolicyDTO;
    }

    public int hashCode() {
        int result = 1;
        Object $theme = this.getTheme();
        result = result * 59 + ($theme == null ? 43 : $theme.hashCode());
        Object $deviceUuid = this.getDeviceUuid();
        result = result * 59 + ($deviceUuid == null ? 43 : $deviceUuid.hashCode());
        Object $policyName = this.getPolicyName();
        result = result * 59 + ($policyName == null ? 43 : $policyName.hashCode());
        Object $srcZone = this.getSrcZone();
        result = result * 59 + ($srcZone == null ? 43 : $srcZone.hashCode());
        Object $dstZone = this.getDstZone();
        result = result * 59 + ($dstZone == null ? 43 : $dstZone.hashCode());
        Object $inDevItf = this.getInDevItf();
        result = result * 59 + ($inDevItf == null ? 43 : $inDevItf.hashCode());
        Object $outDevItf = this.getOutDevItf();
        result = result * 59 + ($outDevItf == null ? 43 : $outDevItf.hashCode());
        Object $preIpAddress = this.getPreIpAddress();
        result = result * 59 + ($preIpAddress == null ? 43 : $preIpAddress.hashCode());
        Object $postIpAddress = this.getPostIpAddress();
        result = result * 59 + ($postIpAddress == null ? 43 : $postIpAddress.hashCode());
        Object $prePort = this.getPrePort();
        result = result * 59 + ($prePort == null ? 43 : $prePort.hashCode());
        Object $postPort = this.getPostPort();
        result = result * 59 + ($postPort == null ? 43 : $postPort.hashCode());
        Object $protocol = this.getProtocol();
        result = result * 59 + ($protocol == null ? 43 : $protocol.hashCode());
        Object $inDevItfAlias = this.getInDevItfAlias();
        result = result * 59 + ($inDevItfAlias == null ? 43 : $inDevItfAlias.hashCode());
        Object $outDevItfAlias = this.getOutDevItfAlias();
        result = result * 59 + ($outDevItfAlias == null ? 43 : $outDevItfAlias.hashCode());
        Object $ipType = this.getIpType();
        result = result * 59 + ($ipType == null ? 43 : $ipType.hashCode());
        return result;
    }

    public String toString() {
        return "StaticNatPolicyDTO(theme=" + this.getTheme() + ", deviceUuid=" + this.getDeviceUuid() + ", policyName=" + this.getPolicyName() + ", srcZone=" + this.getSrcZone() + ", dstZone=" + this.getDstZone() + ", inDevItf=" + this.getInDevItf() + ", outDevItf=" + this.getOutDevItf() + ", preIpAddress=" + this.getPreIpAddress() + ", postIpAddress=" + this.getPostIpAddress() + ", prePort=" + this.getPrePort() + ", postPort=" + this.getPostPort() + ", protocol=" + this.getProtocol() + ", inDevItfAlias=" + this.getInDevItfAlias() + ", outDevItfAlias=" + this.getOutDevItfAlias() + ", ipType=" + this.getIpType() + ")";
    }
}
