package com.dao;

import com.filter.SensitiveDataEncryptFilter;
import com.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDao extends BaseDao{


    public Staff getStaffByStaffCode(String staffCode) {
        Staff staff = null;
        String sql = "SELECT * FROM staff WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setStaffCode(rs.getString("staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
                staff.setPosition(rs.getString("position"));
                staff.setTitle(rs.getString("title"));
                staff.setIdNumber(rs.getString("id_number"));
                staff.setPhone(rs.getString("phone"));
                staff.setAddress(rs.getString("address"));
                staff.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public void addStaff(Staff staff) {
        String sql = "INSERT INTO staff (staff_code,name,department_id,position,title,id_number,phone,address,created_at) VALUES (?, ?, ?,?,?,?,?,?,now())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getStaffCode());
            ps.setString(2, staff.getName());
            ps.setInt(3, staff.getDepartmentId());
            ps.setString(4, staff.getPosition());
            ps.setString(5, staff.getTitle());
            ps.setString(6, staff.getIdNumber());
            ps.setString(7, staff.getPhone());
            ps.setString(8, staff.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        String sql = "UPDATE staff SET name = ?, department_id = ?, position = ?, title = ?, id_number = ?, phone = ?, address = ? WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setInt(2, staff.getDepartmentId());
            ps.setString(3, staff.getPosition());
            ps.setString(4, staff.getTitle());
            ps.setString(5, staff.getIdNumber());
            ps.setString(6, staff.getPhone());
            ps.setString(7, staff.getAddress());
            ps.setString(8, staff.getStaffCode());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();}
    }

    public void deleteStaff(String staffId) {
        String sql = "DELETE FROM staff WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setStaffCode(rs.getString("staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
                staff.setPosition(rs.getString("position"));
                staff.setTitle(rs.getString("title"));
                String encrypted_idNumber=rs.getString("id_number");
                staff.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                String encrypted_phone=rs.getString("phone");
                staff.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                String encrypted_address= rs.getString("address");
                staff.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                staff.setCreatedAt(rs.getTimestamp("created_at"));
                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff findByStaffCode(Integer staffCode) {
    Staff staff = null;
        String sql = "SELECT * FROM staff WHERE staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("id"));
                staff.setName(rs.getString("name"));
                staff.setStaffCode(rs.getString("staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
                staff.setPosition(rs.getString("position"));
                staff.setTitle(rs.getString("title"));
                staff.setIdNumber(rs.getString("id_number"));
                staff.setPhone(rs.getString("phone"));
                staff.setAddress(rs.getString("address"));
                staff.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
    public List<Staff> searchStaff(String keyword) {
        List<Staff> staffList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM staff WHERE 1=1");

        // 添加关键字条件
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR staff_code LIKE ?)");
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                pstmt.setString(paramIndex++, likeKeyword);
                pstmt.setString(paramIndex++, likeKeyword);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("id"));
                    staff.setName(rs.getString("name"));
                    staff.setStaffCode(rs.getString("staff_code"));
                    staff.setDepartmentId(Integer.valueOf(rs.getString("department_id")));
                    staff.setPosition(rs.getString("position"));
                    staff.setTitle(rs.getString("title"));
                    String encrypted_idNumber=rs.getString("id_number");
                    staff.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                    String encrypted_phone=rs.getString("phone");
                    staff.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                    String encrypted_address= rs.getString("address");
                    staff.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                    staff.setCreatedAt(rs.getTimestamp("created_at"));
                    staffList.add(staff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

}
