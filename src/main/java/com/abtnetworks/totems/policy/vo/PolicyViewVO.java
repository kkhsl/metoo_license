//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.policy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@ApiModel("策略概览")
public class PolicyViewVO implements Comparator<PolicyViewVO> {
    @ApiModelProperty("类型code")
    Integer typeCode;
    @ApiModelProperty("类型")
    String type;
    @ApiModelProperty("厂商Id")
    String vendorId;
    @ApiModelProperty("厂商名字")
    String vendorName;
    @ApiModelProperty("设备名称")
    String deviceName;
    @ApiModelProperty("型号")
    private String version;
    @ApiModelProperty("采集时间")
    private String createdTime;
    @ApiModelProperty("采集状态")
    private Byte state;
    @ApiModelProperty("ip")
    String ip;
    @ApiModelProperty("对象总数")
    Integer objectTotal;
    @ApiModelProperty("对象总数详情，方便前端循环")
    List<Map<String, Integer>> objectTotalDetail;
    @ApiModelProperty("地址数")
    Integer netWorkTotal;
    @ApiModelProperty("地址组数")
    Integer netWorkGroupTotal;
    @ApiModelProperty("服务数")
    Integer serviceTotal;
    @ApiModelProperty("服务组数")
    Integer servieGroupTotal;
    @ApiModelProperty("时间数")
    Integer timeTotal;
    @ApiModelProperty("对象检查总数")
    Integer objectCheckTotal;
    @ApiModelProperty("对象检查总数详情，方便前端循环")
    List<Map<String, Integer>> objectCheckTotalDetail;
    @ApiModelProperty("地址检查数")
    Integer netWorkCheckTotal;
    @ApiModelProperty("地址组检查数")
    Integer netWorkGroupCheckTotal;
    @ApiModelProperty("服务检查数")
    Integer serviceCheckTotal;
    @ApiModelProperty("服务组检查数")
    Integer servieGroupCheckTotal;
    @ApiModelProperty("时间检查数")
    Integer timeCheckTotal;
    @ApiModelProperty("策略总数")
    Integer policyTotal;
    @ApiModelProperty("策略总数详情，方便前端循环")
    List<Map<String, Integer>> policyTotalDetail;
    @ApiModelProperty("安全策略数")
    Integer safeTotal;
    @ApiModelProperty("nat数")
    Integer natTotal;
    @ApiModelProperty("acl数")
    Integer aclTotal;
    @ApiModelProperty("策略路由数")
    Integer policyRoutTotal;
    @ApiModelProperty("静态路由数")
    Integer staticRoutTotal;
    @ApiModelProperty("路由表数")
    Integer routTableTotal;
    @ApiModelProperty("策略检查数")
    Integer policyCheckTotal;
    @ApiModelProperty("策略检查总数详情，方便前端循环")
    List<Map<String, Integer>> policyCheckTotalDetail;
    @ApiModelProperty("隐藏策略检查数")
    Integer hiddenCheckTotal;
    @ApiModelProperty("合并策略检查数")
    Integer mergeCheckTotal;
    @ApiModelProperty("空策略检查数")
    Integer emptyCheckTotal;
    @ApiModelProperty("过期策略检查数")
    Integer expiredCheckTotal;
    @ApiModelProperty("临过期策略检查数")
    Integer willExpiringCheckTotal;
    @ApiModelProperty("冗余策略检查数")
    Integer redundancyCheckTotal;
    @ApiModelProperty("域外策略检查数")
    Integer outZoneCheckTotal;
    @ApiModelProperty("acl未调用策略检查数")
    Integer aclNoUseCheckTotal;

    public int compare(PolicyViewVO o1, PolicyViewVO o2) {
        int result = 0;
        int typeSeq = o1.getTypeCode() - o2.getTypeCode();
        if (typeSeq != 0) {
            result = typeSeq > 0 ? 4 : -1;
        } else {
            typeSeq = o1.getVendorId().compareTo(o2.getVendorId());
            if (typeSeq != 0) {
                result = typeSeq > 0 ? 3 : -3;
            } else {
                typeSeq = o1.getIp().compareTo(o2.getIp());
                if (typeSeq != 0) {
                    result = typeSeq > 0 ? 2 : -2;
                }
            }
        }

        return result;
    }

    public PolicyViewVO() {
    }

    public Byte getState(){
        return this.state;
    }
    public Integer getTypeCode() {
        return this.typeCode;
    }

    public String getType() {
        return this.type;
    }

    public String getVendorId() {
        return this.vendorId;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getVersion() {
        return this.version;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public String getIp() {
        return this.ip;
    }

    public Integer getObjectTotal() {
        return this.objectTotal;
    }

    public List<Map<String, Integer>> getObjectTotalDetail() {
        return this.objectTotalDetail;
    }

    public Integer getNetWorkTotal() {
        return this.netWorkTotal;
    }

    public Integer getNetWorkGroupTotal() {
        return this.netWorkGroupTotal;
    }

    public Integer getServiceTotal() {
        return this.serviceTotal;
    }

    public Integer getServieGroupTotal() {
        return this.servieGroupTotal;
    }

    public Integer getTimeTotal() {
        return this.timeTotal;
    }

    public Integer getObjectCheckTotal() {
        return this.objectCheckTotal;
    }

    public List<Map<String, Integer>> getObjectCheckTotalDetail() {
        return this.objectCheckTotalDetail;
    }

    public Integer getNetWorkCheckTotal() {
        return this.netWorkCheckTotal;
    }

    public Integer getNetWorkGroupCheckTotal() {
        return this.netWorkGroupCheckTotal;
    }

    public Integer getServiceCheckTotal() {
        return this.serviceCheckTotal;
    }

    public Integer getServieGroupCheckTotal() {
        return this.servieGroupCheckTotal;
    }

    public Integer getTimeCheckTotal() {
        return this.timeCheckTotal;
    }

    public Integer getPolicyTotal() {
        return this.policyTotal;
    }

    public List<Map<String, Integer>> getPolicyTotalDetail() {
        return this.policyTotalDetail;
    }

    public Integer getSafeTotal() {
        return this.safeTotal;
    }

    public Integer getNatTotal() {
        return this.natTotal;
    }

    public Integer getAclTotal() {
        return this.aclTotal;
    }

    public Integer getPolicyRoutTotal() {
        return this.policyRoutTotal;
    }

    public Integer getStaticRoutTotal() {
        return this.staticRoutTotal;
    }

    public Integer getRoutTableTotal() {
        return this.routTableTotal;
    }

    public Integer getPolicyCheckTotal() {
        return this.policyCheckTotal;
    }

    public List<Map<String, Integer>> getPolicyCheckTotalDetail() {
        return this.policyCheckTotalDetail;
    }

    public Integer getHiddenCheckTotal() {
        return this.hiddenCheckTotal;
    }

    public Integer getMergeCheckTotal() {
        return this.mergeCheckTotal;
    }

    public Integer getEmptyCheckTotal() {
        return this.emptyCheckTotal;
    }

    public Integer getExpiredCheckTotal() {
        return this.expiredCheckTotal;
    }

    public Integer getWillExpiringCheckTotal() {
        return this.willExpiringCheckTotal;
    }

    public Integer getRedundancyCheckTotal() {
        return this.redundancyCheckTotal;
    }

    public Integer getOutZoneCheckTotal() {
        return this.outZoneCheckTotal;
    }

    public Integer getAclNoUseCheckTotal() {
        return this.aclNoUseCheckTotal;
    }

    public void setState(Byte state){
        this.state = state;
    }
    public void setTypeCode(final Integer typeCode) {
        this.typeCode = typeCode;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setVendorId(final String vendorId) {
        this.vendorId = vendorId;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public void setCreatedTime(final String createdTime) {
        this.createdTime = createdTime;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public void setObjectTotal(final Integer objectTotal) {
        this.objectTotal = objectTotal;
    }

    public void setObjectTotalDetail(final List<Map<String, Integer>> objectTotalDetail) {
        this.objectTotalDetail = objectTotalDetail;
    }

    public void setNetWorkTotal(final Integer netWorkTotal) {
        this.netWorkTotal = netWorkTotal;
    }

    public void setNetWorkGroupTotal(final Integer netWorkGroupTotal) {
        this.netWorkGroupTotal = netWorkGroupTotal;
    }

    public void setServiceTotal(final Integer serviceTotal) {
        this.serviceTotal = serviceTotal;
    }

    public void setServieGroupTotal(final Integer servieGroupTotal) {
        this.servieGroupTotal = servieGroupTotal;
    }

    public void setTimeTotal(final Integer timeTotal) {
        this.timeTotal = timeTotal;
    }

    public void setObjectCheckTotal(final Integer objectCheckTotal) {
        this.objectCheckTotal = objectCheckTotal;
    }

    public void setObjectCheckTotalDetail(final List<Map<String, Integer>> objectCheckTotalDetail) {
        this.objectCheckTotalDetail = objectCheckTotalDetail;
    }

    public void setNetWorkCheckTotal(final Integer netWorkCheckTotal) {
        this.netWorkCheckTotal = netWorkCheckTotal;
    }

    public void setNetWorkGroupCheckTotal(final Integer netWorkGroupCheckTotal) {
        this.netWorkGroupCheckTotal = netWorkGroupCheckTotal;
    }

    public void setServiceCheckTotal(final Integer serviceCheckTotal) {
        this.serviceCheckTotal = serviceCheckTotal;
    }

    public void setServieGroupCheckTotal(final Integer servieGroupCheckTotal) {
        this.servieGroupCheckTotal = servieGroupCheckTotal;
    }

    public void setTimeCheckTotal(final Integer timeCheckTotal) {
        this.timeCheckTotal = timeCheckTotal;
    }

    public void setPolicyTotal(final Integer policyTotal) {
        this.policyTotal = policyTotal;
    }

    public void setPolicyTotalDetail(final List<Map<String, Integer>> policyTotalDetail) {
        this.policyTotalDetail = policyTotalDetail;
    }

    public void setSafeTotal(final Integer safeTotal) {
        this.safeTotal = safeTotal;
    }

    public void setNatTotal(final Integer natTotal) {
        this.natTotal = natTotal;
    }

    public void setAclTotal(final Integer aclTotal) {
        this.aclTotal = aclTotal;
    }

    public void setPolicyRoutTotal(final Integer policyRoutTotal) {
        this.policyRoutTotal = policyRoutTotal;
    }

    public void setStaticRoutTotal(final Integer staticRoutTotal) {
        this.staticRoutTotal = staticRoutTotal;
    }

    public void setRoutTableTotal(final Integer routTableTotal) {
        this.routTableTotal = routTableTotal;
    }

    public void setPolicyCheckTotal(final Integer policyCheckTotal) {
        this.policyCheckTotal = policyCheckTotal;
    }

    public void setPolicyCheckTotalDetail(final List<Map<String, Integer>> policyCheckTotalDetail) {
        this.policyCheckTotalDetail = policyCheckTotalDetail;
    }

    public void setHiddenCheckTotal(final Integer hiddenCheckTotal) {
        this.hiddenCheckTotal = hiddenCheckTotal;
    }

    public void setMergeCheckTotal(final Integer mergeCheckTotal) {
        this.mergeCheckTotal = mergeCheckTotal;
    }

    public void setEmptyCheckTotal(final Integer emptyCheckTotal) {
        this.emptyCheckTotal = emptyCheckTotal;
    }

    public void setExpiredCheckTotal(final Integer expiredCheckTotal) {
        this.expiredCheckTotal = expiredCheckTotal;
    }

    public void setWillExpiringCheckTotal(final Integer willExpiringCheckTotal) {
        this.willExpiringCheckTotal = willExpiringCheckTotal;
    }

    public void setRedundancyCheckTotal(final Integer redundancyCheckTotal) {
        this.redundancyCheckTotal = redundancyCheckTotal;
    }

    public void setOutZoneCheckTotal(final Integer outZoneCheckTotal) {
        this.outZoneCheckTotal = outZoneCheckTotal;
    }

    public void setAclNoUseCheckTotal(final Integer aclNoUseCheckTotal) {
        this.aclNoUseCheckTotal = aclNoUseCheckTotal;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PolicyViewVO)) {
            return false;
        } else {
            PolicyViewVO other = (PolicyViewVO)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label491: {
                    Object this$typeCode = this.getTypeCode();
                    Object other$typeCode = other.getTypeCode();
                    if (this$typeCode == null) {
                        if (other$typeCode == null) {
                            break label491;
                        }
                    } else if (this$typeCode.equals(other$typeCode)) {
                        break label491;
                    }

                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if (this$type == null) {
                    if (other$type != null) {
                        return false;
                    }
                } else if (!this$type.equals(other$type)) {
                    return false;
                }

                Object this$vendorId = this.getVendorId();
                Object other$vendorId = other.getVendorId();
                if (this$vendorId == null) {
                    if (other$vendorId != null) {
                        return false;
                    }
                } else if (!this$vendorId.equals(other$vendorId)) {
                    return false;
                }

                label470: {
                    Object this$vendorName = this.getVendorName();
                    Object other$vendorName = other.getVendorName();
                    if (this$vendorName == null) {
                        if (other$vendorName == null) {
                            break label470;
                        }
                    } else if (this$vendorName.equals(other$vendorName)) {
                        break label470;
                    }

                    return false;
                }

                label463: {
                    Object this$deviceName = this.getDeviceName();
                    Object other$deviceName = other.getDeviceName();
                    if (this$deviceName == null) {
                        if (other$deviceName == null) {
                            break label463;
                        }
                    } else if (this$deviceName.equals(other$deviceName)) {
                        break label463;
                    }

                    return false;
                }

                label456: {
                    Object this$version = this.getVersion();
                    Object other$version = other.getVersion();
                    if (this$version == null) {
                        if (other$version == null) {
                            break label456;
                        }
                    } else if (this$version.equals(other$version)) {
                        break label456;
                    }

                    return false;
                }

                Object this$createdTime = this.getCreatedTime();
                Object other$createdTime = other.getCreatedTime();
                if (this$createdTime == null) {
                    if (other$createdTime != null) {
                        return false;
                    }
                } else if (!this$createdTime.equals(other$createdTime)) {
                    return false;
                }

                label442: {
                    Object this$ip = this.getIp();
                    Object other$ip = other.getIp();
                    if (this$ip == null) {
                        if (other$ip == null) {
                            break label442;
                        }
                    } else if (this$ip.equals(other$ip)) {
                        break label442;
                    }

                    return false;
                }

                Object this$objectTotal = this.getObjectTotal();
                Object other$objectTotal = other.getObjectTotal();
                if (this$objectTotal == null) {
                    if (other$objectTotal != null) {
                        return false;
                    }
                } else if (!this$objectTotal.equals(other$objectTotal)) {
                    return false;
                }

                label428: {
                    Object this$objectTotalDetail = this.getObjectTotalDetail();
                    Object other$objectTotalDetail = other.getObjectTotalDetail();
                    if (this$objectTotalDetail == null) {
                        if (other$objectTotalDetail == null) {
                            break label428;
                        }
                    } else if (this$objectTotalDetail.equals(other$objectTotalDetail)) {
                        break label428;
                    }

                    return false;
                }

                Object this$netWorkTotal = this.getNetWorkTotal();
                Object other$netWorkTotal = other.getNetWorkTotal();
                if (this$netWorkTotal == null) {
                    if (other$netWorkTotal != null) {
                        return false;
                    }
                } else if (!this$netWorkTotal.equals(other$netWorkTotal)) {
                    return false;
                }

                Object this$netWorkGroupTotal = this.getNetWorkGroupTotal();
                Object other$netWorkGroupTotal = other.getNetWorkGroupTotal();
                if (this$netWorkGroupTotal == null) {
                    if (other$netWorkGroupTotal != null) {
                        return false;
                    }
                } else if (!this$netWorkGroupTotal.equals(other$netWorkGroupTotal)) {
                    return false;
                }

                label407: {
                    Object this$serviceTotal = this.getServiceTotal();
                    Object other$serviceTotal = other.getServiceTotal();
                    if (this$serviceTotal == null) {
                        if (other$serviceTotal == null) {
                            break label407;
                        }
                    } else if (this$serviceTotal.equals(other$serviceTotal)) {
                        break label407;
                    }

                    return false;
                }

                label400: {
                    Object this$servieGroupTotal = this.getServieGroupTotal();
                    Object other$servieGroupTotal = other.getServieGroupTotal();
                    if (this$servieGroupTotal == null) {
                        if (other$servieGroupTotal == null) {
                            break label400;
                        }
                    } else if (this$servieGroupTotal.equals(other$servieGroupTotal)) {
                        break label400;
                    }

                    return false;
                }

                Object this$timeTotal = this.getTimeTotal();
                Object other$timeTotal = other.getTimeTotal();
                if (this$timeTotal == null) {
                    if (other$timeTotal != null) {
                        return false;
                    }
                } else if (!this$timeTotal.equals(other$timeTotal)) {
                    return false;
                }

                Object this$objectCheckTotal = this.getObjectCheckTotal();
                Object other$objectCheckTotal = other.getObjectCheckTotal();
                if (this$objectCheckTotal == null) {
                    if (other$objectCheckTotal != null) {
                        return false;
                    }
                } else if (!this$objectCheckTotal.equals(other$objectCheckTotal)) {
                    return false;
                }

                label379: {
                    Object this$objectCheckTotalDetail = this.getObjectCheckTotalDetail();
                    Object other$objectCheckTotalDetail = other.getObjectCheckTotalDetail();
                    if (this$objectCheckTotalDetail == null) {
                        if (other$objectCheckTotalDetail == null) {
                            break label379;
                        }
                    } else if (this$objectCheckTotalDetail.equals(other$objectCheckTotalDetail)) {
                        break label379;
                    }

                    return false;
                }

                Object this$netWorkCheckTotal = this.getNetWorkCheckTotal();
                Object other$netWorkCheckTotal = other.getNetWorkCheckTotal();
                if (this$netWorkCheckTotal == null) {
                    if (other$netWorkCheckTotal != null) {
                        return false;
                    }
                } else if (!this$netWorkCheckTotal.equals(other$netWorkCheckTotal)) {
                    return false;
                }

                Object this$netWorkGroupCheckTotal = this.getNetWorkGroupCheckTotal();
                Object other$netWorkGroupCheckTotal = other.getNetWorkGroupCheckTotal();
                if (this$netWorkGroupCheckTotal == null) {
                    if (other$netWorkGroupCheckTotal != null) {
                        return false;
                    }
                } else if (!this$netWorkGroupCheckTotal.equals(other$netWorkGroupCheckTotal)) {
                    return false;
                }

                label358: {
                    Object this$serviceCheckTotal = this.getServiceCheckTotal();
                    Object other$serviceCheckTotal = other.getServiceCheckTotal();
                    if (this$serviceCheckTotal == null) {
                        if (other$serviceCheckTotal == null) {
                            break label358;
                        }
                    } else if (this$serviceCheckTotal.equals(other$serviceCheckTotal)) {
                        break label358;
                    }

                    return false;
                }

                label351: {
                    Object this$servieGroupCheckTotal = this.getServieGroupCheckTotal();
                    Object other$servieGroupCheckTotal = other.getServieGroupCheckTotal();
                    if (this$servieGroupCheckTotal == null) {
                        if (other$servieGroupCheckTotal == null) {
                            break label351;
                        }
                    } else if (this$servieGroupCheckTotal.equals(other$servieGroupCheckTotal)) {
                        break label351;
                    }

                    return false;
                }

                label344: {
                    Object this$timeCheckTotal = this.getTimeCheckTotal();
                    Object other$timeCheckTotal = other.getTimeCheckTotal();
                    if (this$timeCheckTotal == null) {
                        if (other$timeCheckTotal == null) {
                            break label344;
                        }
                    } else if (this$timeCheckTotal.equals(other$timeCheckTotal)) {
                        break label344;
                    }

                    return false;
                }

                Object this$policyTotal = this.getPolicyTotal();
                Object other$policyTotal = other.getPolicyTotal();
                if (this$policyTotal == null) {
                    if (other$policyTotal != null) {
                        return false;
                    }
                } else if (!this$policyTotal.equals(other$policyTotal)) {
                    return false;
                }

                label330: {
                    Object this$policyTotalDetail = this.getPolicyTotalDetail();
                    Object other$policyTotalDetail = other.getPolicyTotalDetail();
                    if (this$policyTotalDetail == null) {
                        if (other$policyTotalDetail == null) {
                            break label330;
                        }
                    } else if (this$policyTotalDetail.equals(other$policyTotalDetail)) {
                        break label330;
                    }

                    return false;
                }

                Object this$safeTotal = this.getSafeTotal();
                Object other$safeTotal = other.getSafeTotal();
                if (this$safeTotal == null) {
                    if (other$safeTotal != null) {
                        return false;
                    }
                } else if (!this$safeTotal.equals(other$safeTotal)) {
                    return false;
                }

                label316: {
                    Object this$natTotal = this.getNatTotal();
                    Object other$natTotal = other.getNatTotal();
                    if (this$natTotal == null) {
                        if (other$natTotal == null) {
                            break label316;
                        }
                    } else if (this$natTotal.equals(other$natTotal)) {
                        break label316;
                    }

                    return false;
                }

                Object this$aclTotal = this.getAclTotal();
                Object other$aclTotal = other.getAclTotal();
                if (this$aclTotal == null) {
                    if (other$aclTotal != null) {
                        return false;
                    }
                } else if (!this$aclTotal.equals(other$aclTotal)) {
                    return false;
                }

                Object this$policyRoutTotal = this.getPolicyRoutTotal();
                Object other$policyRoutTotal = other.getPolicyRoutTotal();
                if (this$policyRoutTotal == null) {
                    if (other$policyRoutTotal != null) {
                        return false;
                    }
                } else if (!this$policyRoutTotal.equals(other$policyRoutTotal)) {
                    return false;
                }

                label295: {
                    Object this$staticRoutTotal = this.getStaticRoutTotal();
                    Object other$staticRoutTotal = other.getStaticRoutTotal();
                    if (this$staticRoutTotal == null) {
                        if (other$staticRoutTotal == null) {
                            break label295;
                        }
                    } else if (this$staticRoutTotal.equals(other$staticRoutTotal)) {
                        break label295;
                    }

                    return false;
                }

                label288: {
                    Object this$routTableTotal = this.getRoutTableTotal();
                    Object other$routTableTotal = other.getRoutTableTotal();
                    if (this$routTableTotal == null) {
                        if (other$routTableTotal == null) {
                            break label288;
                        }
                    } else if (this$routTableTotal.equals(other$routTableTotal)) {
                        break label288;
                    }

                    return false;
                }

                Object this$policyCheckTotal = this.getPolicyCheckTotal();
                Object other$policyCheckTotal = other.getPolicyCheckTotal();
                if (this$policyCheckTotal == null) {
                    if (other$policyCheckTotal != null) {
                        return false;
                    }
                } else if (!this$policyCheckTotal.equals(other$policyCheckTotal)) {
                    return false;
                }

                Object this$policyCheckTotalDetail = this.getPolicyCheckTotalDetail();
                Object other$policyCheckTotalDetail = other.getPolicyCheckTotalDetail();
                if (this$policyCheckTotalDetail == null) {
                    if (other$policyCheckTotalDetail != null) {
                        return false;
                    }
                } else if (!this$policyCheckTotalDetail.equals(other$policyCheckTotalDetail)) {
                    return false;
                }

                label267: {
                    Object this$hiddenCheckTotal = this.getHiddenCheckTotal();
                    Object other$hiddenCheckTotal = other.getHiddenCheckTotal();
                    if (this$hiddenCheckTotal == null) {
                        if (other$hiddenCheckTotal == null) {
                            break label267;
                        }
                    } else if (this$hiddenCheckTotal.equals(other$hiddenCheckTotal)) {
                        break label267;
                    }

                    return false;
                }

                Object this$mergeCheckTotal = this.getMergeCheckTotal();
                Object other$mergeCheckTotal = other.getMergeCheckTotal();
                if (this$mergeCheckTotal == null) {
                    if (other$mergeCheckTotal != null) {
                        return false;
                    }
                } else if (!this$mergeCheckTotal.equals(other$mergeCheckTotal)) {
                    return false;
                }

                Object this$emptyCheckTotal = this.getEmptyCheckTotal();
                Object other$emptyCheckTotal = other.getEmptyCheckTotal();
                if (this$emptyCheckTotal == null) {
                    if (other$emptyCheckTotal != null) {
                        return false;
                    }
                } else if (!this$emptyCheckTotal.equals(other$emptyCheckTotal)) {
                    return false;
                }

                label246: {
                    Object this$expiredCheckTotal = this.getExpiredCheckTotal();
                    Object other$expiredCheckTotal = other.getExpiredCheckTotal();
                    if (this$expiredCheckTotal == null) {
                        if (other$expiredCheckTotal == null) {
                            break label246;
                        }
                    } else if (this$expiredCheckTotal.equals(other$expiredCheckTotal)) {
                        break label246;
                    }

                    return false;
                }

                label239: {
                    Object this$willExpiringCheckTotal = this.getWillExpiringCheckTotal();
                    Object other$willExpiringCheckTotal = other.getWillExpiringCheckTotal();
                    if (this$willExpiringCheckTotal == null) {
                        if (other$willExpiringCheckTotal == null) {
                            break label239;
                        }
                    } else if (this$willExpiringCheckTotal.equals(other$willExpiringCheckTotal)) {
                        break label239;
                    }

                    return false;
                }

                label232: {
                    Object this$redundancyCheckTotal = this.getRedundancyCheckTotal();
                    Object other$redundancyCheckTotal = other.getRedundancyCheckTotal();
                    if (this$redundancyCheckTotal == null) {
                        if (other$redundancyCheckTotal == null) {
                            break label232;
                        }
                    } else if (this$redundancyCheckTotal.equals(other$redundancyCheckTotal)) {
                        break label232;
                    }

                    return false;
                }

                Object this$outZoneCheckTotal = this.getOutZoneCheckTotal();
                Object other$outZoneCheckTotal = other.getOutZoneCheckTotal();
                if (this$outZoneCheckTotal == null) {
                    if (other$outZoneCheckTotal != null) {
                        return false;
                    }
                } else if (!this$outZoneCheckTotal.equals(other$outZoneCheckTotal)) {
                    return false;
                }

                Object this$aclNoUseCheckTotal = this.getAclNoUseCheckTotal();
                Object other$aclNoUseCheckTotal = other.getAclNoUseCheckTotal();
                if (this$aclNoUseCheckTotal == null) {
                    if (other$aclNoUseCheckTotal != null) {
                        return false;
                    }
                } else if (!this$aclNoUseCheckTotal.equals(other$aclNoUseCheckTotal)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PolicyViewVO;
    }

    public int hashCode() {
        int result = 1;
        Object $typeCode = this.getTypeCode();
        result = result * 59 + ($typeCode == null ? 43 : $typeCode.hashCode());
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $vendorId = this.getVendorId();
        result = result * 59 + ($vendorId == null ? 43 : $vendorId.hashCode());
        Object $vendorName = this.getVendorName();
        result = result * 59 + ($vendorName == null ? 43 : $vendorName.hashCode());
        Object $deviceName = this.getDeviceName();
        result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
        Object $version = this.getVersion();
        result = result * 59 + ($version == null ? 43 : $version.hashCode());
        Object $createdTime = this.getCreatedTime();
        result = result * 59 + ($createdTime == null ? 43 : $createdTime.hashCode());
        Object $ip = this.getIp();
        result = result * 59 + ($ip == null ? 43 : $ip.hashCode());
        Object $objectTotal = this.getObjectTotal();
        result = result * 59 + ($objectTotal == null ? 43 : $objectTotal.hashCode());
        Object $objectTotalDetail = this.getObjectTotalDetail();
        result = result * 59 + ($objectTotalDetail == null ? 43 : $objectTotalDetail.hashCode());
        Object $netWorkTotal = this.getNetWorkTotal();
        result = result * 59 + ($netWorkTotal == null ? 43 : $netWorkTotal.hashCode());
        Object $netWorkGroupTotal = this.getNetWorkGroupTotal();
        result = result * 59 + ($netWorkGroupTotal == null ? 43 : $netWorkGroupTotal.hashCode());
        Object $serviceTotal = this.getServiceTotal();
        result = result * 59 + ($serviceTotal == null ? 43 : $serviceTotal.hashCode());
        Object $servieGroupTotal = this.getServieGroupTotal();
        result = result * 59 + ($servieGroupTotal == null ? 43 : $servieGroupTotal.hashCode());
        Object $timeTotal = this.getTimeTotal();
        result = result * 59 + ($timeTotal == null ? 43 : $timeTotal.hashCode());
        Object $objectCheckTotal = this.getObjectCheckTotal();
        result = result * 59 + ($objectCheckTotal == null ? 43 : $objectCheckTotal.hashCode());
        Object $objectCheckTotalDetail = this.getObjectCheckTotalDetail();
        result = result * 59 + ($objectCheckTotalDetail == null ? 43 : $objectCheckTotalDetail.hashCode());
        Object $netWorkCheckTotal = this.getNetWorkCheckTotal();
        result = result * 59 + ($netWorkCheckTotal == null ? 43 : $netWorkCheckTotal.hashCode());
        Object $netWorkGroupCheckTotal = this.getNetWorkGroupCheckTotal();
        result = result * 59 + ($netWorkGroupCheckTotal == null ? 43 : $netWorkGroupCheckTotal.hashCode());
        Object $serviceCheckTotal = this.getServiceCheckTotal();
        result = result * 59 + ($serviceCheckTotal == null ? 43 : $serviceCheckTotal.hashCode());
        Object $servieGroupCheckTotal = this.getServieGroupCheckTotal();
        result = result * 59 + ($servieGroupCheckTotal == null ? 43 : $servieGroupCheckTotal.hashCode());
        Object $timeCheckTotal = this.getTimeCheckTotal();
        result = result * 59 + ($timeCheckTotal == null ? 43 : $timeCheckTotal.hashCode());
        Object $policyTotal = this.getPolicyTotal();
        result = result * 59 + ($policyTotal == null ? 43 : $policyTotal.hashCode());
        Object $policyTotalDetail = this.getPolicyTotalDetail();
        result = result * 59 + ($policyTotalDetail == null ? 43 : $policyTotalDetail.hashCode());
        Object $safeTotal = this.getSafeTotal();
        result = result * 59 + ($safeTotal == null ? 43 : $safeTotal.hashCode());
        Object $natTotal = this.getNatTotal();
        result = result * 59 + ($natTotal == null ? 43 : $natTotal.hashCode());
        Object $aclTotal = this.getAclTotal();
        result = result * 59 + ($aclTotal == null ? 43 : $aclTotal.hashCode());
        Object $policyRoutTotal = this.getPolicyRoutTotal();
        result = result * 59 + ($policyRoutTotal == null ? 43 : $policyRoutTotal.hashCode());
        Object $staticRoutTotal = this.getStaticRoutTotal();
        result = result * 59 + ($staticRoutTotal == null ? 43 : $staticRoutTotal.hashCode());
        Object $routTableTotal = this.getRoutTableTotal();
        result = result * 59 + ($routTableTotal == null ? 43 : $routTableTotal.hashCode());
        Object $policyCheckTotal = this.getPolicyCheckTotal();
        result = result * 59 + ($policyCheckTotal == null ? 43 : $policyCheckTotal.hashCode());
        Object $policyCheckTotalDetail = this.getPolicyCheckTotalDetail();
        result = result * 59 + ($policyCheckTotalDetail == null ? 43 : $policyCheckTotalDetail.hashCode());
        Object $hiddenCheckTotal = this.getHiddenCheckTotal();
        result = result * 59 + ($hiddenCheckTotal == null ? 43 : $hiddenCheckTotal.hashCode());
        Object $mergeCheckTotal = this.getMergeCheckTotal();
        result = result * 59 + ($mergeCheckTotal == null ? 43 : $mergeCheckTotal.hashCode());
        Object $emptyCheckTotal = this.getEmptyCheckTotal();
        result = result * 59 + ($emptyCheckTotal == null ? 43 : $emptyCheckTotal.hashCode());
        Object $expiredCheckTotal = this.getExpiredCheckTotal();
        result = result * 59 + ($expiredCheckTotal == null ? 43 : $expiredCheckTotal.hashCode());
        Object $willExpiringCheckTotal = this.getWillExpiringCheckTotal();
        result = result * 59 + ($willExpiringCheckTotal == null ? 43 : $willExpiringCheckTotal.hashCode());
        Object $redundancyCheckTotal = this.getRedundancyCheckTotal();
        result = result * 59 + ($redundancyCheckTotal == null ? 43 : $redundancyCheckTotal.hashCode());
        Object $outZoneCheckTotal = this.getOutZoneCheckTotal();
        result = result * 59 + ($outZoneCheckTotal == null ? 43 : $outZoneCheckTotal.hashCode());
        Object $aclNoUseCheckTotal = this.getAclNoUseCheckTotal();
        result = result * 59 + ($aclNoUseCheckTotal == null ? 43 : $aclNoUseCheckTotal.hashCode());
        return result;
    }

    public String toString() {
        return "PolicyViewVO(typeCode=" + this.getTypeCode() + ", type=" + this.getType() + ", vendorId=" + this.getVendorId() + ", vendorName=" + this.getVendorName() + ", deviceName=" + this.getDeviceName() + ", version=" + this.getVersion() + ", createdTime=" + this.getCreatedTime() + ", ip=" + this.getIp() + ", objectTotal=" + this.getObjectTotal() + ", objectTotalDetail=" + this.getObjectTotalDetail() + ", netWorkTotal=" + this.getNetWorkTotal() + ", netWorkGroupTotal=" + this.getNetWorkGroupTotal() + ", serviceTotal=" + this.getServiceTotal() + ", servieGroupTotal=" + this.getServieGroupTotal() + ", timeTotal=" + this.getTimeTotal() + ", objectCheckTotal=" + this.getObjectCheckTotal() + ", objectCheckTotalDetail=" + this.getObjectCheckTotalDetail() + ", netWorkCheckTotal=" + this.getNetWorkCheckTotal() + ", netWorkGroupCheckTotal=" + this.getNetWorkGroupCheckTotal() + ", serviceCheckTotal=" + this.getServiceCheckTotal() + ", servieGroupCheckTotal=" + this.getServieGroupCheckTotal() + ", timeCheckTotal=" + this.getTimeCheckTotal() + ", policyTotal=" + this.getPolicyTotal() + ", policyTotalDetail=" + this.getPolicyTotalDetail() + ", safeTotal=" + this.getSafeTotal() + ", natTotal=" + this.getNatTotal() + ", aclTotal=" + this.getAclTotal() + ", policyRoutTotal=" + this.getPolicyRoutTotal() + ", staticRoutTotal=" + this.getStaticRoutTotal() + ", routTableTotal=" + this.getRoutTableTotal() + ", policyCheckTotal=" + this.getPolicyCheckTotal() + ", policyCheckTotalDetail=" + this.getPolicyCheckTotalDetail() + ", hiddenCheckTotal=" + this.getHiddenCheckTotal() + ", mergeCheckTotal=" + this.getMergeCheckTotal() + ", emptyCheckTotal=" + this.getEmptyCheckTotal() + ", expiredCheckTotal=" + this.getExpiredCheckTotal() + ", willExpiringCheckTotal=" + this.getWillExpiringCheckTotal() + ", redundancyCheckTotal=" + this.getRedundancyCheckTotal() + ", outZoneCheckTotal=" + this.getOutZoneCheckTotal() + ", aclNoUseCheckTotal=" + this.getAclNoUseCheckTotal() + ")";
    }
}
