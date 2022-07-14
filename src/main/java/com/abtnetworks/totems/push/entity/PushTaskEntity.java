package com.abtnetworks.totems.push.entity;

import java.util.Date;

public class PushTaskEntity {
    private int id;
    private int policyId;
    private String orderNo;
    private int orderType;
    private String deviceUuid;
    private String deviceName;
    private String manageIp;
    private Date createTime;
    private Date pushTime;
    private int status;
    private String userName;
    private String command;
    private String pushResult;

    public PushTaskEntity() {
    }


    public int getId() {
        return this.id;
    }

    public int getPolicyId() {
        return this.policyId;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public int getOrderType() {
        return this.orderType;
    }

    public String getDeviceUuid() {
        return this.deviceUuid;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getManageIp() {
        return this.manageIp;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getPushTime() {
        return this.pushTime;
    }

    public int getStatus() {
        return this.status;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getCommand() {
        return this.command;
    }

    public String getPushResult() {
        return this.pushResult;
    }


    public void setId(final int id) {
        this.id = id;
    }

    public void setPolicyId(final int policyId) {
        this.policyId = policyId;
    }

    public void setOrderNo(final String orderNo) {
        this.orderNo = orderNo;
    }

    public void setOrderType(final int orderType) {
        this.orderType = orderType;
    }

    public void setDeviceUuid(final String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public void setDeviceName(final String deviceName) {
        this.deviceName = deviceName;
    }

    public void setManageIp(final String manageIp) {
        this.manageIp = manageIp;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }

    public void setPushTime(final Date pushTime) {
        this.pushTime = pushTime;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public void setPushResult(final String pushResult) {
        this.pushResult = pushResult;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PushTaskEntity)) {
            return false;
        } else {
            PushTaskEntity other = (PushTaskEntity)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getId() != other.getId()) {
                return false;
            } else if (this.getPolicyId() != other.getPolicyId()) {
                return false;
            } else {
                label132: {
                    Object this$orderNo = this.getOrderNo();
                    Object other$orderNo = other.getOrderNo();
                    if (this$orderNo == null) {
                        if (other$orderNo == null) {
                            break label132;
                        }
                    } else if (this$orderNo.equals(other$orderNo)) {
                        break label132;
                    }

                    return false;
                }

                if (this.getOrderType() != other.getOrderType()) {
                    return false;
                } else {
                    label124: {
                        Object this$deviceUuid = this.getDeviceUuid();
                        Object other$deviceUuid = other.getDeviceUuid();
                        if (this$deviceUuid == null) {
                            if (other$deviceUuid == null) {
                                break label124;
                            }
                        } else if (this$deviceUuid.equals(other$deviceUuid)) {
                            break label124;
                        }

                        return false;
                    }

                    Object this$deviceName = this.getDeviceName();
                    Object other$deviceName = other.getDeviceName();
                    if (this$deviceName == null) {
                        if (other$deviceName != null) {
                            return false;
                        }
                    } else if (!this$deviceName.equals(other$deviceName)) {
                        return false;
                    }

                    Object this$manageIp = this.getManageIp();
                    Object other$manageIp = other.getManageIp();
                    if (this$manageIp == null) {
                        if (other$manageIp != null) {
                            return false;
                        }
                    } else if (!this$manageIp.equals(other$manageIp)) {
                        return false;
                    }

                    label103: {
                        Object this$createTime = this.getCreateTime();
                        Object other$createTime = other.getCreateTime();
                        if (this$createTime == null) {
                            if (other$createTime == null) {
                                break label103;
                            }
                        } else if (this$createTime.equals(other$createTime)) {
                            break label103;
                        }

                        return false;
                    }

                    Object this$pushTime = this.getPushTime();
                    Object other$pushTime = other.getPushTime();
                    if (this$pushTime == null) {
                        if (other$pushTime != null) {
                            return false;
                        }
                    } else if (!this$pushTime.equals(other$pushTime)) {
                        return false;
                    }

                    if (this.getStatus() != other.getStatus()) {
                        return false;
                    } else {
                        Object this$userName = this.getUserName();
                        Object other$userName = other.getUserName();
                        if (this$userName == null) {
                            if (other$userName != null) {
                                return false;
                            }
                        } else if (!this$userName.equals(other$userName)) {
                            return false;
                        }

                        Object this$command = this.getCommand();
                        Object other$command = other.getCommand();
                        if (this$command == null) {
                            if (other$command != null) {
                                return false;
                            }
                        } else if (!this$command.equals(other$command)) {
                            return false;
                        }

                        Object this$pushResult = this.getPushResult();
                        Object other$pushResult = other.getPushResult();
                        if (this$pushResult == null) {
                            if (other$pushResult != null) {
                                return false;
                            }
                        } else if (!this$pushResult.equals(other$pushResult)) {
                            return false;
                        }

                        return true;
                    }
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PushTaskEntity;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getPolicyId();
        Object $orderNo = this.getOrderNo();
        result = result * 59 + ($orderNo == null ? 43 : $orderNo.hashCode());
        result = result * 59 + this.getOrderType();
        Object $deviceUuid = this.getDeviceUuid();
        result = result * 59 + ($deviceUuid == null ? 43 : $deviceUuid.hashCode());
        Object $deviceName = this.getDeviceName();
        result = result * 59 + ($deviceName == null ? 43 : $deviceName.hashCode());
        Object $manageIp = this.getManageIp();
        result = result * 59 + ($manageIp == null ? 43 : $manageIp.hashCode());
        Object $createTime = this.getCreateTime();
        result = result * 59 + ($createTime == null ? 43 : $createTime.hashCode());
        Object $pushTime = this.getPushTime();
        result = result * 59 + ($pushTime == null ? 43 : $pushTime.hashCode());
        result = result * 59 + this.getStatus();
        Object $userName = this.getUserName();
        result = result * 59 + ($userName == null ? 43 : $userName.hashCode());
        Object $command = this.getCommand();
        result = result * 59 + ($command == null ? 43 : $command.hashCode());
        Object $pushResult = this.getPushResult();
        result = result * 59 + ($pushResult == null ? 43 : $pushResult.hashCode());
        return result;
    }

    public String toString() {
        return "PushTaskEntity(id=" + this.getId() + ", policyId=" + this.getPolicyId() + ", orderNo=" + this.getOrderNo() + ", orderType=" + this.getOrderType() + ", deviceUuid=" + this.getDeviceUuid() + ", deviceName=" + this.getDeviceName() + ", manageIp=" + this.getManageIp() + ", createTime=" + this.getCreateTime() + ", pushTime=" + this.getPushTime() + ", status=" + this.getStatus() + ", userName=" + this.getUserName() + ", command=" + this.getCommand() + ", pushResult=" + this.getPushResult() + ")";
    }
}
