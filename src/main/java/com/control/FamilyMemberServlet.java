package com.control;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.dao.FamilyMemberDao;
import com.dao.StaffDao;
import com.filter.AuditLogFilter;
import com.model.FamilyMember;
import com.model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FamilyMemberServlet")
public class FamilyMemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FamilyMemberDao familyMemberDao = new FamilyMemberDao();
    private StaffDao staffDao = new StaffDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String staffCode = request.getParameter("staffCode");
        AuditLogFilter.log(request, "查询", "家人信息", "成功", "通过过滤器记录");
        if ("list".equals(action)) {
            Staff staff = staffDao.getStaffByStaffCode(staffCode);
            List<FamilyMember> familyMembers = familyMemberDao.getFamilyMembersByStaffCode(staffCode);
            request.setAttribute("staff", staff);
            request.setAttribute("familyMembers", familyMembers);
            request.getRequestDispatcher("familyManage.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String staffCode = request.getParameter("staffCode");

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

        response.sendRedirect("FamilyMemberServlet?action=list&staffCode=" +staffCode);
    }
}
