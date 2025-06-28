package com.dao;

import com.model.SysUser;
import com.filter.SensitiveDataEncryptFilter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SysUserDao extends BaseDao{
    public SysUser getUserByUsername(String username) {
        String sql = "SELECT * FROM liuk_sys_user WHERE lk_username = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SysUser user = new SysUser();
                    user.setId(rs.getInt("lk_id"));
                    user.setUsername(rs.getString("lk_username"));
                    user.setPassword(rs.getString("lk_password"));
                    String encrypted_realName = rs.getString("lk_real_name");
                    user.setRealName(SensitiveDataEncryptFilter.decryptSM4(encrypted_realName));
                    String encrypted_idNumber = rs.getString("lk_id_number");
                    user.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                    String encrypted_phone = rs.getString("lk_phone");
                    user.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                    String encrypted_address = rs.getString("lk_address");
                    user.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                    user.setRoleId(rs.getInt("lk_role_id"));
                    user.setIsLocked(rs.getBoolean("lk_is_locked"));
                    user.setLastPasswordChange(rs.getTimestamp("lk_last_password_change"));
                    user.setCreatedAt(rs.getTimestamp("lk_created_at"));
                    user.setFailedLoginCount(rs.getInt("lk_failed_login_count"));
                    user.setLastFailedLoginTime(rs.getTimestamp("lk_last_failed_login_time"));
                    user.setAccountLockedUntil(rs.getTimestamp("lk_account_locked_until"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();}
        return null;
    }


    public boolean updateUser(SysUser user) {
        String sql = "UPDATE liuk_sys_user SET " +
                "lk_failed_login_count = ?, " +
                "lk_last_failed_login_time = ?, " +
                "lk_is_locked = ?, " +
                "lk_account_locked_until = ? " +
                "WHERE lk_id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, user.getFailedLoginCount() != null ? user.getFailedLoginCount() : 0);
            ps.setTimestamp(2, user.getLastFailedLoginTime());
            ps.setBoolean(3, user.getIsLocked() != null ? user.getIsLocked() : false);
            ps.setTimestamp(4, user.getAccountLockedUntil());
            ps.setInt(5, user.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SysUser> getAllUsers() {
        String sql = "SELECT * FROM liuk_sys_user";
        List<SysUser> users = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    SysUser user = new SysUser();
                    user.setId(rs.getInt("lk_id"));
                    user.setUsername(rs.getString("lk_username"));
                    user.setPassword(rs.getString("lk_password"));
                    String encrypted_realName = rs.getString("lk_real_name");
                    user.setRealName(SensitiveDataEncryptFilter.decryptSM4(encrypted_realName));
                    String encrypted_idNumber = rs.getString("lk_id_number");
                    user.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                    String encrypted_phone = rs.getString("lk_phone");
                    user.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                    String encrypted_address = rs.getString("lk_address");
                    user.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                    user.setRoleId(rs.getInt("lk_role_id"));
                    user.setIsLocked(rs.getBoolean("lk_is_locked"));
                    user.setLastPasswordChange(rs.getTimestamp("lk_last_password_change"));
                    user.setCreatedAt(rs.getTimestamp("lk_created_at"));
                    user.setFailedLoginCount(rs.getInt("lk_failed_login_count"));
                    user.setLastFailedLoginTime(rs.getTimestamp("lk_last_failed_login_time"));
                    user.setAccountLockedUntil(rs.getTimestamp("lk_account_locked_until"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean updateUserRole(int userId, int role_id) {
        String sql = "UPDATE liuk_sys_user SET lk_role_id = ? WHERE lk_id = ?";
        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, role_id);
            ps.setInt(2, userId);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(String username, String password, String name, String idNumber, String phone, String address, int i, boolean b, Timestamp timestamp, Timestamp timestamp1) {
        String sql = "INSERT INTO liuk_sys_user (lk_username, lk_password, lk_real_name, lk_id_number, lk_phone, lk_address, lk_role_id, lk_is_locked, lk_last_password_change, lk_created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, idNumber);
            ps.setString(5, phone);
            ps.setString(6, address);
            ps.setInt(7, i);
            ps.setBoolean(8, b);
            ps.setTimestamp(9, timestamp);
            ps.setTimestamp(10, timestamp1);
            return   ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
