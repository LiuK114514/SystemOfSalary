package com.dao;

import com.model.SpecialDeduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpecialDeductionDao extends BaseDao{



    public SpecialDeduction getByStaffCode(String staffCode) {
    String sql = "SELECT * FROM liuk_special_deduction WHERE lk_staff_code = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
            var rs = ps.executeQuery();
            if (rs.next()) {
                SpecialDeduction deduction = new SpecialDeduction();
                deduction.setId(rs.getInt("lk_id"));
                deduction.setStaffCode(rs.getInt("lk_staff_code"));
                deduction.setChildEducation(rs.getBigDecimal("lk_child_education"));
                deduction.setContinueEducation(rs.getBigDecimal("lk_continue_education"));
                deduction.setHousingLoanInterest(rs.getBigDecimal("lk_housing_loan_interest"));
                deduction.setHousingRent(rs.getBigDecimal("lk_housing_rent"));
                deduction.setElderlySupport(rs.getBigDecimal("lk_elderly_support"));
                deduction.setSeriousIllness(rs.getBigDecimal("lk_serious_illness"));
                deduction.setCreatedAt(rs.getTimestamp("lk_created_at"));
                return deduction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(SpecialDeduction deduction) {
        String sql = "UPDATE liuk_special_deduction SET lk_child_education = ?, lk_continue_education = ?, lk_housing_loan_interest = ?, lk_housing_rent = ?, lk_elderly_support = ?, lk_serious_illness = ?,lk_created_at=? WHERE lk_staff_code = ?";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBigDecimal(1, deduction.getChildEducation());
            ps.setBigDecimal(2, deduction.getContinueEducation());
            ps.setBigDecimal(3, deduction.getHousingLoanInterest());
            ps.setBigDecimal(4, deduction.getHousingRent());
            ps.setBigDecimal(5, deduction.getElderlySupport());
            ps.setBigDecimal(6, deduction.getSeriousIllness());
            ps.setInt(8, deduction.getStaffCode());
            ps.setTimestamp(7, deduction.getCreatedAt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean add(SpecialDeduction deduction) {
        String sql = "INSERT INTO liuk_special_deduction (lk_staff_code, lk_child_education, lk_continue_education, lk_housing_loan_interest, lk_housing_rent, lk_elderly_support, lk_serious_illness, lk_created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, deduction.getStaffCode());
            ps.setBigDecimal(2, deduction.getChildEducation());
            ps.setBigDecimal(3, deduction.getContinueEducation());
            ps.setBigDecimal(4, deduction.getHousingLoanInterest());
            ps.setBigDecimal(5, deduction.getHousingRent());
            ps.setBigDecimal(6, deduction.getElderlySupport());
            ps.setBigDecimal(7, deduction.getSeriousIllness());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
