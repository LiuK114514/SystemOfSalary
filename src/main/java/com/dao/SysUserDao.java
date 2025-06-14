package com.dao;

import com.model.SysUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SysUserDao extends BaseDao{
    public SysUser getUserByUsername(String username) {
        String sql = "SELECT * FROM sys_user WHERE username = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SysUser user = new SysUser();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRealName(rs.getString("real_name"));
                    user.setIdNumber(rs.getString("id_number"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setIsLocked(rs.getBoolean("is_locked"));
                    user.setLastPasswordChange(rs.getTimestamp("last_password_change"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setFailedLoginCount(rs.getInt("failed_login_count"));
                    user.setLastFailedLoginTime(rs.getTimestamp("last_failed_login_time"));
                    user.setAccountLockedUntil(rs.getTimestamp("account_locked_until"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();}
        return null;
    }

    public boolean addUser(SysUser user) {
        String sql = "INSERT INTO sys_user (username, password, real_name, id_number, phone, address, role_id, is_locked, last_password_change, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRealName());
            ps.setString(4, user.getIdNumber());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setInt(7, user.getRole().getCode());
            ps.setBoolean(8, user.getIsLocked() != null ? user.getIsLocked() : false);
            ps.setTimestamp(9, user.getLastPasswordChange() != null ? user.getLastPasswordChange() : new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(10, user.getCreatedAt());
          return   ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(SysUser currentUser) {
        String sql = "UPDATE sys_user SET password = ?, real_name = ?, id_number = ?, phone = ?, address = ?, role_id = ?, is_locked = ?, last_password_change = ? ,failed_login_count = ? ,last_failed_login_time=?,account_locked_until=? WHERE id = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, currentUser.getPassword());
            ps.setString(2, currentUser.getRealName());
            ps.setString(3, currentUser.getIdNumber());
            ps.setString(4, currentUser.getPhone());
            ps.setString(5, currentUser.getAddress());
            ps.setInt(6, currentUser.getRole().getCode());
            ps.setBoolean(7, currentUser.getIsLocked() != null ? currentUser.getIsLocked() : false);
            ps.setTimestamp(8, currentUser.getLastPasswordChange() != null ? currentUser.getLastPasswordChange() : new Timestamp(System.currentTimeMillis()));
            ps.setInt(9, currentUser.getFailedLoginCount() != null ? currentUser.getFailedLoginCount() : 0);
            ps.setTimestamp(10, currentUser.getLastFailedLoginTime() != null ? currentUser.getLastFailedLoginTime() : null);
            ps.setTimestamp(11, currentUser.getAccountLockedUntil() != null ? currentUser.getAccountLockedUntil() : null);
            ps.setInt(12, currentUser.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SysUser> getAllUsers() {
        String sql = "SELECT * FROM sys_user";
        List<SysUser> users = new ArrayList<>();
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    SysUser user = new SysUser();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRealName(rs.getString("real_name"));
                    user.setIdNumber(rs.getString("id_number"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    user.setRoleId(rs.getInt("role_id"));
                    user.setIsLocked(rs.getBoolean("is_locked"));
                    user.setLastPasswordChange(rs.getTimestamp("last_password_change"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setFailedLoginCount(rs.getInt("failed_login_count"));
                    user.setLastFailedLoginTime(rs.getTimestamp("last_failed_login_time"));
                    user.setAccountLockedUntil(rs.getTimestamp("account_locked_until"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean updateUserRole(int userId, int role_id) {
        String sql = "UPDATE sys_user SET role_id = ? WHERE id = ?";
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
}
