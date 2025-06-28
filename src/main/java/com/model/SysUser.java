package com.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String idNumber;
    private String address;
    private Integer roleId;
    private Boolean isLocked;
    private Timestamp lastPasswordChange;
    private Timestamp createdAt;
    private Integer failedLoginCount;               // 登录失败次数
    private Timestamp lastFailedLoginTime;          // 最近一次登录失败时间
    private Timestamp accountLockedUntil;           // 自动解锁时间
    public UserRole getRole() {
        return UserRole.fromCode(this.roleId);
    }

    public void setRole(UserRole role) {
        this.roleId = role.getCode();
    }
    public String getPhone() {
        if (phone != null && phone.length() == 11) {
            return phone.substring(0, 3) + "****" + phone.substring(7);
        }
        return phone;
    }

    public String getRealName() {
        if (realName != null && realName.length() > 1) {
            return realName.substring(0, 1) + "**";
        }
        return realName;
    }

    public String getAddress() {
        if (address != null && address.length() > 1) {
            return address.substring(0, 1) + "***";
        }
        return address;
    }
}