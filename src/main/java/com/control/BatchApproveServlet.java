package com.control;

import com.dao.SalaryRecordDao;
import com.filter.AuditLogFilter;
import com.model.SalaryRecord;
import com.model.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BatchApproveServlet")
public class BatchApproveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] salaryIds = request.getParameterValues("salaryId");
        String action = request.getParameter("action");
        String role="" ;
        HttpSession session = request.getSession();
        UserRole Role = (UserRole) session.getAttribute("userRole");
        if(action.equals("approve")||action.equals("paid")){
            if(Role==UserRole.finance){
                role = String.valueOf(UserRole.finance);
            }
            else if(Role==UserRole.admin){
                role = String.valueOf(UserRole.admin);
            }
            else if(Role==UserRole.ceo){
                role = String.valueOf(UserRole.ceo);
            }

            List<Integer> salarys = new ArrayList<>();
            for (String idStr : salaryIds) {
                if (idStr != null && !idStr.isEmpty()) {
                    salarys.add(Integer.parseInt(idStr));
                }
            }

            SalaryRecordDao dao = new SalaryRecordDao();
            dao.processSalary(salarys, role,action);
        }
        else {
            String rejectReason= request.getParameter("rejectReason");
            SalaryRecordDao dao = new SalaryRecordDao();
            for(String idStr : salaryIds) {
                if (idStr != null && !idStr.isEmpty()) {
                    boolean success = dao.updateSalaryReject(idStr,rejectReason);
                    if (success ==false) {
                        AuditLogFilter.log(request, "驳回", "总经理", "失败", rejectReason);
                        request.setAttribute("msg", "驳回出错");
                        request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
                    }
                }
            }
        }

        response.sendRedirect("SalaryQueryServlet");
    }
}
