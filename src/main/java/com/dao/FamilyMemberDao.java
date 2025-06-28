package com.dao;

import com.filter.SensitiveDataEncryptFilter;
import com.model.FamilyMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamilyMemberDao extends BaseDao{
    public List<FamilyMember> getFamilyMembersByStaffCode(String staffCode) {
        List<FamilyMember> familyMembers = new ArrayList<>();
        String sql = "SELECT * FROM liuk_family_member WHERE lk_staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FamilyMember member = new FamilyMember();
                member.setId(rs.getInt("lk_id"));
                member.setStaffCode(rs.getInt("lk_staff_code"));
                member.setName(rs.getString("lk_name"));
                member.setRelation(rs.getString("lk_relation"));
               String encrypted_idNumber= rs.getString("lk_id_number");
                member.setIdNumber(SensitiveDataEncryptFilter.decryptSM4(encrypted_idNumber));
                member.setBirthDate(rs.getDate("lk_birth_date"));
                member.setIsStudent(rs.getBoolean("lk_is_student"));
                member.setIsMajorDisease(rs.getBoolean("lk_is_major_disease"));
                member.setCreatedAt(rs.getTimestamp("lk_created_at"));
                familyMembers.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();}
        return familyMembers;
    }

    public void addFamilyMember(FamilyMember member) {
        String sql = "INSERT INTO liuk_family_member (lk_staff_code, lk_name, lk_relation, lk_id_number, lk_birth_date, lk_is_student, lk_is_major_disease, lk_created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, member.getStaffCode());
            ps.setString(2, member.getName());
            ps.setString(3, member.getRelation());
            ps.setString(4, member.getIdNumber());
            ps.setDate(5, member.getBirthDate());
            ps.setBoolean(6, member.getIsStudent());
            ps.setBoolean(7, member.getIsMajorDisease());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFamilyMember(String memberId) {
        String sql = "DELETE FROM liuk_family_member WHERE lk_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FamilyMember> getByStaffCode(String staffCode) {
    List<FamilyMember> familyMembers = new ArrayList<>();
        String sql = "SELECT * FROM liuk_family_member WHERE lk_staff_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FamilyMember member = new FamilyMember();
                member.setId(rs.getInt("lk_id"));
                member.setStaffCode(rs.getInt("lk_staff_code"));
                member.setName(rs.getString("lk_name"));
                member.setRelation(rs.getString("lk_relation"));
                member.setIdNumber(rs.getString("lk_id_number"));
                member.setBirthDate(rs.getDate("lk_birth_date"));
                member.setIsStudent(rs.getBoolean("lk_is_student"));
                member.setIsMajorDisease(rs.getBoolean("lk_is_major_disease"));
                familyMembers.add(member);
            }
        } catch (Exception e) {
            e.printStackTrace();}
        return familyMembers;
    }
}
