package com.control;

import com.dao.FamilyMemberDao;
import com.dao.SalaryRecordDao;
import com.dao.StaffDao;
import com.filter.AuditLogFilter;
import com.model.FamilyMember;
import com.model.SalaryView;
import com.model.Staff;
import com.model.StaffVeiw;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/StaffInfoServlet")
public class StaffInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        StaffDao staffDao = new StaffDao();
        List<StaffVeiw>  staffViews = staffDao.getStaffInfoByStaffCode(username);
        if (staffViews.isEmpty()) {
            request.setAttribute("msg", "未找到家人信息");
            request.getRequestDispatcher("/salaryResult.jsp").forward(request, response);
            return;
        } else {
            StaffVeiw staffView = staffViews.get(0);
            request.setAttribute("staffView", staffView);
            request.setAttribute("staffViews", staffViews);
            request.getRequestDispatcher("/staffInfo.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String staffCode = request.getParameter("staffCode");
        FamilyMemberDao familyMemberDao = new FamilyMemberDao();
        if ("add".equals(action)) {
            // 添加新家庭成员
            AuditLogFilter.log(request, "添加", "家人信息", "成功", "通过过滤器记录");
            FamilyMember member = new FamilyMember();
            member.setStaffCode(Integer.valueOf(staffCode));
            member.setName(request.getParameter("name"));
            member.setIdNumber((String) request.getAttribute("encrypted_idNumber"));
            member.setRelation(request.getParameter("relation"));
            member.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
            member.setIsStudent(Boolean.valueOf(request.getParameter("isStudent")));
            member.setIsMajorDisease(Boolean.valueOf(request.getParameter("isMajorDisease")));
            familyMemberDao.addFamilyMember(member);

        } else if ("delete".equals(action)) {
            // 删除家庭成员
            AuditLogFilter.log(request, "删除", "家人信息", "成功", "通过过滤器记录");
            String memberId = request.getParameter("memberId");
            familyMemberDao.deleteFamilyMember(memberId);
        }
        response.sendRedirect("StaffInfoServlet");
    }
}
