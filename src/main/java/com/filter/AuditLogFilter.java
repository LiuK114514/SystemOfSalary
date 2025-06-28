package com.filter;

import com.dao.AuditLogDao;
import com.model.AuditLog;
import com.model.SysUser;
import com.model.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;


@WebFilter("/*")
public class AuditLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();

        if (uri.contains("login.jsp") ) {
        }
        else if (uri.contains("RegisterServlet")) {
            this.log(req, "注册", "用户", "成功", "通过过滤器记录");
        }
        else if (uri.contains("ChangePasswordServlet")) {
            this.log(req, "修改", "密码", "成功", "通过过滤器记录");
        }
        else if (uri.contains("AuditLogQueryServlet")) {
            this.log(req, "查询", "审计日志", "成功", "通过过滤器记录");
        }
        else if (uri.contains("StaffServlet")) {

        }
        else if (uri.contains("FamilyMemberServlet")) {

        }
        else if (uri.contains("SalaryQueryServlet")) {
            this.log(req, "查询", "工资信息", "成功", "通过过滤器记录");
        }
        else if (uri.contains("SalaryImportServlet")|| uri.contains("AddSalaryServlet")) {
            this.log(req, "添加", "工资记录", "成功", "通过过滤器记录");
        }
        else if (uri.contains("SalaryEditServlet")) {
            this.log(req, "修改", "工资记录", "成功", "通过过滤器记录");
        }


        chain.doFilter(request, response);
    }

    public static void log(HttpServletRequest request, String operationType, String operationObject, String result, String remarks) {
        AuditLog log = new AuditLog();
        Object user = request.getSession().getAttribute("user");
        if (user != null ) {
            SysUser u = (SysUser) user;
            log.setUsername(u.getUsername());
            UserRole Role = u.getRole();
            String role="";
            if (Role==null) {
                role = "未授权用户";
            } else if (Role == UserRole.admin) {
                role = "系统管理员";
            } else if (Role == UserRole.staff) {
                role = "未授权用户";
            } else if (Role == UserRole.finance) {
                role = "工资管理员";
            } else if (Role == UserRole.audit) {
                role = "审计员";
            }else if(Role==UserRole.hr){
                role = "人事管理员";
            }
            else if(Role==UserRole.ceo){
                role = "总经理";
            }
            log.setRole(role);
            log.setOperationType(operationType);
            log.setOperationObject(operationObject);
            log.setIpAddress(request.getRemoteAddr());
            log.setTimestamp(new Timestamp(System.currentTimeMillis()));
            log.setResult(result);
            log.setRemarks(remarks);
        } else {
            String username=request.getParameter("username");
            log.setUsername(username);
            log.setRole("未授权用户");
            log.setOperationType("页面刷新");
            log.setOperationObject("未知操作");
            log.setIpAddress(request.getRemoteAddr());
            log.setTimestamp(new Timestamp(System.currentTimeMillis()));
            log.setResult("失败");
            log.setRemarks("页面刷新时记录");
        }

        AuditLogDao AuditLogDao = new AuditLogDao();
        AuditLogDao.insert(log);
    }
}
