package com.dao;

import com.filter.SensitiveDataEncryptFilter;
import com.model.PagedStaffResult;
import com.model.Staff;
import com.model.StaffVeiw;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao extends BaseDao{


    public Staff getStaffByStaffCode(String staffCode) {
        Staff staff = null;
        String sql = "SELECT * FROM liuk_staff WHERE lk_staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("lk_id"));
                staff.setName(rs.getString("lk_name"));
                staff.setStaffCode(rs.getString("lk_staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("lk_department_id")));
                staff.setPosition(rs.getString("lk_position"));
                staff.setIdNumber(rs.getString("lk_id_number"));
                staff.setPhone(rs.getString("lk_phone"));
                staff.setAddress(rs.getString("lk_address"));
                staff.setCreatedAt(rs.getTimestamp("lk_created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }

    public void addStaff(Staff staff) {
        String sql = "INSERT INTO liuk_staff (lk_staff_code,lk_name,lk_department_id,lk_position,lk_id_number,lk_phone,lk_address,lk_created_at) VALUES (?, ?, ?,?,?,?,?,now())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getStaffCode());
            ps.setString(2, staff.getName());
            ps.setInt(3, staff.getDepartmentId());
            ps.setString(4, staff.getPosition());
            ps.setString(5, staff.getIdNumber());
            ps.setString(6, staff.getPhone());
            ps.setString(7, staff.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        String sql = "UPDATE liuk_staff SET lk_name = ?, lk_department_id = ?, lk_position = ?, lk_id_number = ?, lk_phone = ?, lk_address = ? WHERE lk_staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getName());
            ps.setInt(2, staff.getDepartmentId());
            ps.setString(3, staff.getPosition());
            ps.setString(4, staff.getIdNumber());
            ps.setString(5, staff.getPhone());
            ps.setString(6, staff.getAddress());
            ps.setString(7, staff.getStaffCode());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();}
    }

    public void deleteStaff(String staffId) {
        String sql = "DELETE FROM liuk_staff WHERE lk_id = ?";
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
        String sql = "SELECT * FROM liuk_staff";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("lk_id"));
                staff.setName(rs.getString("lk_name"));
                staff.setStaffCode(rs.getString("lk_staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("lk_department_id")));
                staff.setPosition(rs.getString("lk_position"));
                staff.setIdNumber(rs.getString("lk_id_number"));
                staff.setPhone(rs.getString("lk_phone"));
                staff.setAddress(rs.getString("lk_address"));
                staff.setCreatedAt(rs.getTimestamp("lk_created_at"));

                staffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff findByStaffCode(Integer staffCode) {
    Staff staff = null;
        String sql = "SELECT * FROM liuk_staff WHERE lk_staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                staff.setId(rs.getInt("lk_id"));
                staff.setName(rs.getString("lk_name"));
                staff.setStaffCode(rs.getString("lk_staff_code"));
                staff.setDepartmentId(rs.getInt("lk_department_id"));
                staff.setPosition(rs.getString("lk_position"));
                staff.setIdNumber(rs.getString("lk_id_number"));
                staff.setPhone(rs.getString("lk_phone"));
                staff.setAddress(rs.getString("lk_address"));
                staff.setCreatedAt(rs.getTimestamp("lk_created_at"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staff;
    }
    public List<Staff> searchStaff(String keyword) {
        List<Staff> staffList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM liuk_staff WHERE 1=1");

        // 添加关键字条件
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (lk_name LIKE ? OR staff_code LIKE ?)");
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likeKeyword = "%" + keyword + "%";
                pstmt.setString(paramIndex++, likeKeyword);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("lk_id"));
                    staff.setName(rs.getString("lk_name"));
                    staff.setStaffCode(rs.getString("lk_staff_code"));
                    staff.setDepartmentId(Integer.valueOf(rs.getString("lk_department_id")));
                    staff.setPosition(rs.getString("lk_position"));
                    String encrypted_idNumber=rs.getString("lk_id_number");
                    staff.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                    String encrypted_phone=rs.getString("lk_phone");
                    staff.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                    String encrypted_address= rs.getString("lk_address");
                    staff.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                    staff.setCreatedAt(rs.getTimestamp("lk_created_at"));
                    staffList.add(staff);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public PagedStaffResult getStaffByPage(int page, int pageSize, String keyword) {
         PagedStaffResult pagedResult = new PagedStaffResult();
        List<Staff> staffList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);//阻止事务提交，否则获取不到游标
            CallableStatement cs = conn.prepareCall("{CALL get_staff_by_page(?, ?, ?, ?, ?)}");
            cs.setInt(1, page);
            cs.setInt(2, pageSize);
            if(keyword!=null && !keyword.trim().isEmpty()) {
                cs.setString(3, keyword);
            } else {
                cs.setNull(3, Types.VARCHAR);
            }
            cs.setInt(4, 0);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.REF_CURSOR);
            cs.execute();
            int totalPages = cs.getInt(4);
            ResultSet rs = (ResultSet) cs.getObject(5);
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("lk_id"));
                staff.setName(rs.getString("lk_name"));
                staff.setStaffCode(rs.getString("lk_staff_code"));
                staff.setDepartmentId(Integer.valueOf(rs.getString("lk_department_id")));
                staff.setPosition(rs.getString("lk_position"));
                String encrypted_idNumber = rs.getString("lk_id_number");
                staff.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                String encrypted_phone = rs.getString("lk_phone");
                staff.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
                String encrypted_address = rs.getString("lk_address");
                staff.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
                staff.setCreatedAt(rs.getTimestamp("lk_created_at"));
                staffList.add(staff);
            }
            pagedResult.setStaffList(staffList);
            pagedResult.setTotalPages(totalPages);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return pagedResult;
    }

    public List<StaffVeiw> getStaffInfoByStaffCode(String username) {
    List<StaffVeiw> staffViews = new ArrayList<>();
    String sql = "SELECT * FROM liuk_staff_family WHERE lk_staff_code = ?";
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            StaffVeiw staffView = new StaffVeiw();
            staffView.setStaffName(rs.getString("lk_staff_name"));
            staffView.setStaffCode(Integer.valueOf(rs.getString("lk_staff_code")));
            staffView.setDepartment(rs.getString("lk_department"));
            staffView.setPosition(rs.getString("lk_position"));
            String encrypted_idNumber = rs.getString("lk_id_number");
            staffView.setStaffIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
            String encrypted_phone = rs.getString("lk_phone");
            staffView.setPhone(SensitiveDataEncryptFilter.decryptSM4(encrypted_phone));
            String encrypted_address = rs.getString("lk_address");
            staffView.setAddress(SensitiveDataEncryptFilter.decryptSM4(encrypted_address));
            staffView.setFamilyId(rs.getString("lk_family_member_id"));
            staffView.setFamilyMember(rs.getString("lk_family_member"));
            staffView.setRelation(rs.getString("lk_relation"));
            String encrypted_familyIdNumber = rs.getString("lk_family_id_number");
            staffView.setFamilyIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_familyIdNumber));
            staffView.setBirthDate(rs.getString("lk_birth_date"));
            staffView.setIsStudent(rs.getString("lk_is_student"));
            staffView.setIsMajorDisease(rs.getString("lk_is_major_disease"));

            staffViews.add(staffView);
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    return staffViews;
    }
}
