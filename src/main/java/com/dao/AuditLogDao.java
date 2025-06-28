package com.dao;

import com.model.AuditLog;
import com.model.PagedLogResult;
import com.model.UserRole;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDao extends BaseDao {

    public ArrayList<AuditLog> fingAllLogs() {
        String sql = "SELECT * FROM liuk_audit_log";
        ArrayList<AuditLog> logs = new ArrayList<>();
        try(Connection cn=dataSource.getConnection();
            PreparedStatement ps= cn.prepareStatement(sql)){
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                AuditLog log = new AuditLog();
                log.setUsername(rst.getString("lk_username"));
                log.setRole(rst.getString("lk_role"));
                log.setOperationType(rst.getString("lk_operation_type"));
                log.setOperationObject(rst.getString("lk_operation_object"));
                log.setIpAddress(rst.getString("lk_ip_address"));
                log.setTimestamp(rst.getTimestamp("lk_timestamp"));
                log.setResult(rst.getString("lk_result"));
                log.setRemarks(rst.getString("lk_remarks"));
                logs.add(log);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return logs;
    }

    public ArrayList<AuditLog> queryLogs(String username, String type, String startDate, String endDate) {
        ArrayList<AuditLog> logs = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM liuk_audit_log WHERE 1=1");
        List<Object> paramList = new ArrayList<>();

        if (username != null && !username.trim().isEmpty()) {
            sql.append(" AND lk_username LIKE ?");
            paramList.add("%" + username.trim() + "%");
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND lk_operation_type LIKE ?");
            paramList.add("%" + type.trim() + "%");
        }

        if (startDate != null && !startDate.trim().isEmpty()
                && endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND lk_timestamp BETWEEN ? AND ?");
            paramList.add(startDate.trim());
            paramList.add(endDate.trim());
        }
        try (Connection cn = dataSource.getConnection();
                PreparedStatement ps = cn.prepareStatement(String.valueOf(sql))) {
                    for (int i = 0; i < paramList.size(); i++) {
                        ps.setObject(i + 1, paramList.get(i));
                    }
                 ResultSet rst = ps.executeQuery();
                while (rst.next()) {
                    AuditLog log = new AuditLog();
                    log.setUsername(rst.getString("lk_username"));
                    log.setRole(rst.getString("lk_role"));
                    log.setOperationType(rst.getString("lk_operation_type"));
                    log.setOperationObject(rst.getString("lk_operation_object"));
                    log.setIpAddress(rst.getString("lk_ip_address"));
                    log.setTimestamp(rst.getTimestamp("lk_timestamp"));
                    log.setResult(rst.getString("lk_result"));
                    log.setRemarks(rst.getString("lk_remarks"));
                    logs.add(log);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        return logs;

    }

    public void insert(AuditLog log) {
        String sql = "INSERT INTO liuk_audit_log (lk_username, lk_role, lk_operation_type, lk_operation_object, lk_ip_address, lk_timestamp, lk_result, lk_remarks) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = dataSource.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, log.getUsername());
            ps.setString(2, log.getRole());
            ps.setString(3, log.getOperationType());
            ps.setString(4, log.getOperationObject());
            ps.setString(5, log.getIpAddress());
            ps.setTimestamp(6, log.getTimestamp());
            ps.setString(7, log.getResult());
            ps.setString(8, log.getRemarks());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PagedLogResult getAuditLogsByPage(int page, int pageSize, String username, String type, String startDate, String endDate) {
        PagedLogResult result = new PagedLogResult();
        ArrayList<AuditLog> logs = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);//阻止事务提交，否则获取不到游标
            CallableStatement cs = conn.prepareCall("{CALL get_audit_log_by_page(?, ?, ?, ?, ?, ?, ?,?)}");

            // 设置输入参数
            cs.setInt(1, page);
            cs.setInt(2, pageSize);
            if (username != null && !username.isEmpty()) {
                cs.setString(3, username);
            } else {
                cs.setNull(3, Types.VARCHAR);
            }

            if (type != null && !type.isEmpty()) {
                cs.setString(4, type);
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

            int totalPages = cs.getInt(7);
            ResultSet rs = (ResultSet) cs.getObject(8);

            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setUsername(rs.getString("lk_username"));
                log.setRole(rs.getString("lk_role"));
                log.setOperationType(rs.getString("lk_operation_type"));
                log.setOperationObject(rs.getString("lk_operation_object"));
                log.setIpAddress(rs.getString("lk_ip_address"));
                log.setTimestamp(rs.getTimestamp("lk_timestamp"));
                log.setResult(rs.getString("lk_result"));
                log.setRemarks(rs.getString("lk_remarks"));
                logs.add(log);
            }

            result.setLogs(logs);
            result.setTotalPages(totalPages);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
