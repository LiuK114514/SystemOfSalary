package com.dao;

import com.model.PagedSalaryResult;
import com.model.SalaryRecord;
import com.model.SalaryView;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryRecordDao extends BaseDao{
    public boolean insert(SalaryRecord r) {
        String sql = "INSERT INTO liuk_salary_record (lk_staff_code, lk_salary_month, lk_base_salary, lk_position_allowance, " +
                "lk_lunch_allowance, lk_overtime_pay, lk_full_attendance_bonus, lk_social_insurance, lk_housing_fund, " +
                "lk_personal_income_tax, lk_leave_deduction, lk_actual_salary, lk_created_at,lk_status,lk_finance_approve_time,lk_manager_approve_time,lk_reject_reason,lk_paid_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, current_timestamp,?, null, null, null, null)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getStaffCode());
            ps.setString(2, r.getSalaryMonth().toString());
            ps.setBigDecimal(3, r.getBaseSalary());
            ps.setBigDecimal(4, r.getPositionAllowance());
            ps.setBigDecimal(5, r.getLunchAllowance());
            ps.setBigDecimal(6, r.getOvertimePay());
            ps.setBigDecimal(7, r.getFullAttendanceBonus());
            ps.setBigDecimal(8, r.getSocialInsurance());
            ps.setBigDecimal(9, r.getHousingFund());
            ps.setBigDecimal(10, r.getPersonalIncomeTax());
            ps.setBigDecimal(11, r.getLeaveDeduction());
            ps.setBigDecimal(12, r.getActualSalary());
            ps.setString(13, "draft");
            int result = ps.executeUpdate();
            if (result > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<SalaryView> findAllWithStaffInfo() {
        List<SalaryView> list = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM liuk_salary_overview " +
                "ORDER BY lk_salary_month DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SalaryView view = new SalaryView();
                view.setId(rs.getInt("lk_id"));
                view.setStaffCode(rs.getInt("lk_staff_code"));
                view.setStaffName(rs.getString("lk_staff_name"));
                view.setDepartment(rs.getString("lk_department_name"));
                view.setSalaryMonth(rs.getString("lk_salary_month"));
                view.setBaseSalary(rs.getBigDecimal("lk_base_salary"));
                view.setPositionAllowance(rs.getBigDecimal("lk_position_allowance"));
                view.setLunchAllowance(rs.getBigDecimal("lk_lunch_allowance"));
                view.setOvertimePay(rs.getBigDecimal("lk_overtime_pay"));
                view.setFullAttendanceBonus(rs.getBigDecimal("lk_full_attendance_bonus"));
                view.setSocialInsurance(rs.getBigDecimal("lk_social_insurance"));
                view.setHousingFund(rs.getBigDecimal("lk_housing_fund"));
                view.setPersonalIncomeTax(rs.getBigDecimal("lk_personal_income_tax"));
                view.setLeaveDeduction(rs.getBigDecimal("lk_leave_deduction"));
                view.setActualSalary(rs.getBigDecimal("lk_actual_salary"));
                view.setStatus(rs.getString("lk_status"));
                view.setFinanceApproveTime(rs.getTimestamp("lk_finance_approve_time"));
                view.setManagerApproveTime(rs.getTimestamp("lk_manager_approve_time"));
                view.setRejectReason(rs.getString("lk_reject_reason"));
                view.setPaidTime(rs.getTimestamp("lk_paid_time"));
                list.add(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<SalaryView> FuzzyfindWithStaffInfo(String staffName, String departmentName, String startDate, String endDate) {
        List<SalaryView> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
               "SELECT * " +
                "FROM liuk_salary_overview " +
                        "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        // 动态拼接条件
        if (staffName != null && !staffName.trim().isEmpty()) {
            sql.append("AND lk_staff_name LIKE ? ");
            params.add("%" + staffName.trim() + "%");
        }
        if (departmentName != null && !departmentName.trim().isEmpty()) {
            sql.append("AND lk_department_name LIKE ? ");
            params.add("%" + departmentName.trim() + "%");
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append("AND lk_salary_month >= ? ");
            params.add(startDate.trim());
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append("AND lk_salary_month <= ? ");
            params.add(endDate.trim());
        }

        sql.append("ORDER BY lk_salary_month DESC");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // 设置动态参数
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SalaryView view = new SalaryView();
                    view.setId(rs.getInt("lk_id"));
                    view.setStaffCode(rs.getInt("lk_staff_code"));
                    view.setStaffName(rs.getString("lk_staff_name"));
                    view.setDepartment(rs.getString("lk_department_name"));
                    view.setSalaryMonth(rs.getString("lk_salary_month"));
                    view.setBaseSalary(rs.getBigDecimal("lk_base_salary"));
                    view.setPositionAllowance(rs.getBigDecimal("lk_position_allowance"));
                    view.setLunchAllowance(rs.getBigDecimal("lk_lunch_allowance"));
                    view.setOvertimePay(rs.getBigDecimal("lk_overtime_pay"));
                    view.setFullAttendanceBonus(rs.getBigDecimal("lk_full_attendance_bonus"));
                    view.setSocialInsurance(rs.getBigDecimal("lk_social_insurance"));
                    view.setHousingFund(rs.getBigDecimal("lk_housing_fund"));
                    view.setPersonalIncomeTax(rs.getBigDecimal("lk_personal_income_tax"));
                    view.setLeaveDeduction(rs.getBigDecimal("lk_leave_deduction"));
                    view.setActualSalary(rs.getBigDecimal("lk_actual_salary"));
                    view.setStatus(rs.getString("lk_status"));
                    view.setFinanceApproveTime(rs.getTimestamp("lk_finance_approve_time"));
                    view.setManagerApproveTime(rs.getTimestamp("lk_manager_approve_time"));
                    view.setRejectReason(rs.getString("lk_reject_reason"));
                    view.setPaidTime(rs.getTimestamp("lk_paid_time"));
                    list.add(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public SalaryRecord findById(int id) {
        String sql = "SELECT * FROM liuk_salary_record WHERE lk_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalaryRecord record = new SalaryRecord();
                    record.setId(rs.getInt("lk_id"));
                    record.setStaffCode(rs.getInt("lk_staff_code"));
                    record.setSalaryMonth(rs.getString("lk_salary_month"));
                    record.setBaseSalary(rs.getBigDecimal("lk_base_salary"));
                    record.setPositionAllowance(rs.getBigDecimal("lk_position_allowance"));
                    record.setLunchAllowance(rs.getBigDecimal("lk_lunch_allowance"));
                    record.setOvertimePay(rs.getBigDecimal("lk_overtime_pay"));
                    record.setFullAttendanceBonus(rs.getBigDecimal("lk_full_attendance_bonus"));
                    record.setSocialInsurance(rs.getBigDecimal("lk_social_insurance"));
                    record.setHousingFund(rs.getBigDecimal("lk_housing_fund"));
                    record.setPersonalIncomeTax(rs.getBigDecimal("lk_personal_income_tax"));
                    record.setLeaveDeduction(rs.getBigDecimal("lk_leave_deduction"));
                    record.setActualSalary(rs.getBigDecimal("lk_actual_salary"));
                    record.setCreatedAt(rs.getTimestamp("lk_created_at"));
                    record.setStatus(rs.getString("lk_status"));
                    record.setFinanceApproveTime(rs.getTimestamp("lk_finance_approve_time"));
                    record.setManagerApproveTime(rs.getTimestamp("lk_manager_approve_time"));
                    record.setRejectReason(rs.getString("lk_reject_reason"));
                    record.setPaidTime(rs.getTimestamp("lk_paid_time"));

                    return record;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;}
        return null;
    }



    public SalaryRecord getByStaffCode(String staffCodeStr) {
        String sql = "SELECT * FROM lk_salary_record WHERE lk_staff_code = ? ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(staffCodeStr));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SalaryRecord record = new SalaryRecord();
                    record.setId(rs.getInt("lk_id"));
                    record.setStaffCode(rs.getInt("lk_staff_code"));
                    record.setSalaryMonth(rs.getString("lk_salary_month"));
                    record.setBaseSalary(rs.getBigDecimal("lk_base_salary"));
                    record.setPositionAllowance(rs.getBigDecimal("lk_position_allowance"));
                    record.setLunchAllowance(rs.getBigDecimal("lk_lunch_allowance"));
                    record.setOvertimePay(rs.getBigDecimal("lk_overtime_pay"));
                    record.setFullAttendanceBonus(rs.getBigDecimal("lk_full_attendance_bonus"));
                    record.setSocialInsurance(rs.getBigDecimal("lk_social_insurance"));
                    record.setHousingFund(rs.getBigDecimal("lk_housing_fund"));
                    record.setPersonalIncomeTax(rs.getBigDecimal("lk_personal_income_tax"));
                    record.setLeaveDeduction(rs.getBigDecimal("lk_leave_deduction"));
                    record.setActualSalary(rs.getBigDecimal("lk_actual_salary"));
                    record.setCreatedAt(rs.getTimestamp("lk_created_at"));
                    record.setStatus(rs.getString("lk_status"));
                    record.setFinanceApproveTime(rs.getTimestamp("lk_finance_approve_time"));
                    record.setManagerApproveTime(rs.getTimestamp("lk_manager_approve_time"));
                    record.setRejectReason(rs.getString("lk_reject_reason"));
                    record.setPaidTime(rs.getTimestamp("lk_paid_time"));

                    return record;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        return null;
        }
        return null;
    }


    public boolean updateSalary(String staffCode, String salaryMonth, BigDecimal baseSalary, BigDecimal positionAllowance, BigDecimal lunchAllowance, BigDecimal overtimePay, BigDecimal fullAttendanceBonus, BigDecimal socialInsurance, BigDecimal housingFund, BigDecimal personalIncomeTax, BigDecimal leaveDeduction, BigDecimal actualSalary) {
        String sql = "UPDATE liuk_salary_record SET " +
                "lk_base_salary = ?, " +
                "lk_position_allowance = ?, " +
                "lk_lunch_allowance = ?, " +
                "lk_overtime_pay = ?, " +
                "lk_full_attendance_bonus = ?, " +
                "lk_social_insurance = ?, " +
                "lk_housing_fund = ?, " +
                "lk_personal_income_tax = ?, " +
                "lk_leave_deduction = ?, " +
                "lk_actual_salary = ? " +
                "WHERE lk_staff_code = ? AND lk_salary_month = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps= conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, baseSalary);
            ps.setBigDecimal(2, positionAllowance);
            ps.setBigDecimal(3, lunchAllowance);
            ps.setBigDecimal(4, overtimePay);
            ps.setBigDecimal(5, fullAttendanceBonus);
            ps.setBigDecimal(6, socialInsurance);
            ps.setBigDecimal(7, housingFund);
            ps.setBigDecimal(8, personalIncomeTax);
            ps.setBigDecimal(9, leaveDeduction);
            ps.setBigDecimal(10, actualSalary);
            ps.setInt(11, Integer.parseInt(staffCode));
            ps.setString(12, salaryMonth);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
           return false;
        }
    }

    public PagedSalaryResult findByPageWithProc(int page, int pageSize, String staffName, String departmentName, String startDate, String endDate) {
        PagedSalaryResult pagedResult = new PagedSalaryResult();
        List<SalaryView> result = new ArrayList<>();
        int totalPages = 0;

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            CallableStatement cs = conn.prepareCall("{call get_salary_overview_by_page(?, ?, ?, ?, ?, ?, ?, ?)}");

            cs.setInt(1, page);
            cs.setInt(2, pageSize);
           if(staffName != null && !staffName.isEmpty()) {
                cs.setString(3, "%" + staffName + "%");
            } else {
                cs.setNull(3, Types.VARCHAR);
            }
            if (departmentName != null && !departmentName.isEmpty()) {
                cs.setString(4, "%" + departmentName + "%");
            } else {
                cs.setNull(4, Types.VARCHAR);
            }
            if (startDate != null && !startDate.isEmpty()) {
                cs.setTimestamp(5, java.sql.Timestamp.valueOf(startDate + " 00:00:00"));
            } else {
                cs.setNull(5, java.sql.Types.TIMESTAMP);
            }

            if (endDate != null && !endDate.isEmpty()) {
                cs.setTimestamp(6, java.sql.Timestamp.valueOf(endDate + " 23:59:59"));
            } else {
                cs.setNull(6, java.sql.Types.TIMESTAMP);
            }

            cs.setInt(7, 0);
            cs.registerOutParameter(7, Types.INTEGER);
            cs.registerOutParameter(8, Types.REF_CURSOR);

            cs.execute();

            totalPages = cs.getInt(7);
            ResultSet rs = (ResultSet) cs.getObject(8);
            while (rs.next()) {
                SalaryView view = new SalaryView();
                view.setId(rs.getInt("lk_id"));
                view.setStaffCode(rs.getInt("lk_staff_code"));
                view.setStaffName(rs.getString("lk_staff_name"));
                view.setDepartment(rs.getString("lk_department_name"));
                view.setSalaryMonth(rs.getString("lk_salary_month"));
                view.setBaseSalary(rs.getBigDecimal("lk_base_salary"));
                view.setPositionAllowance(rs.getBigDecimal("lk_position_allowance"));
                view.setLunchAllowance(rs.getBigDecimal("lk_lunch_allowance"));
                view.setOvertimePay(rs.getBigDecimal("lk_overtime_pay"));
                view.setFullAttendanceBonus(rs.getBigDecimal("lk_full_attendance_bonus"));
                view.setSocialInsurance(rs.getBigDecimal("lk_social_insurance"));
                view.setHousingFund(rs.getBigDecimal("lk_housing_fund"));
                view.setPersonalIncomeTax(rs.getBigDecimal("lk_personal_income_tax"));
                view.setLeaveDeduction(rs.getBigDecimal("lk_leave_deduction"));
                view.setActualSalary(rs.getBigDecimal("lk_actual_salary"));
                if(rs.getString("lk_status") == null) {
                    view.setStatus("draft");
                }
                else view.setStatus(rs.getString("lk_status"));
                view.setFinanceApproveTime(rs.getTimestamp("lk_finance_approve_time"));
                view.setManagerApproveTime(rs.getTimestamp("lk_manager_approve_time"));
                view.setRejectReason(rs.getString("lk_reject_reason"));
                view.setPaidTime(rs.getTimestamp("lk_paid_time"));
                result.add(view);
            }
            pagedResult.setSalaryViews(result);
            pagedResult.setTotalPages(totalPages);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return pagedResult;
    }

    public void processSalary(List<Integer> salarys, String role, String action) {
        String sql = "CALL process_salary(?,?,?)";

        try (
                Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(sql)
        ) {
            Integer[] idsArray = salarys.toArray(new Integer[0]);
            Array sqlArray = conn.createArrayOf("INTEGER", idsArray);

            stmt.setArray(1, sqlArray);
            stmt.setString(2, role);
            stmt.setString(3, action);

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateSalaryReject(String idStr, String rejectReason) {
        String sql="UPDATE liuk_salary_record SET lk_status=?,lk_reject_reason=? where lk_id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps= conn.prepareStatement(sql)) {
            ps.setString(1, "rejected");
            ps.setString(2, rejectReason);
            ps.setString(3, idStr);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public String getStaffNameByUsername(String username) {
        String sql = "SELECT lk_staff_name FROM liuk_salary_overview WHERE lk_staff_code = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("lk_staff_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
