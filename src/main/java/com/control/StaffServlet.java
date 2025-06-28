package com.control;
import java.io.IOException;
import java.util.List;

import com.dao.StaffDao;
import com.filter.AuditLogFilter;
import com.model.PagedStaffResult;
import com.model.Staff;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StaffServlet")
public class StaffServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffDao staffDao = new StaffDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String keyword = request.getParameter("keyword");
            String pageParam = request.getParameter("page");
            int page = 1;
            int pageSize = 2;
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException ignored) {}
            }

            StaffDao staffDao = new StaffDao();
            PagedStaffResult pagedResult = staffDao.getStaffByPage(page, pageSize, keyword);

            request.setAttribute("staffList", pagedResult.getStaffList());
            request.setAttribute("totalPages", pagedResult.getTotalPages());
            request.setAttribute("currentPage", page);
            AuditLogFilter.log(request, "查询", "员工信息", "成功", keyword != null ? "关键词分页查询" : "分页列出全部");
            RequestDispatcher rd = request.getRequestDispatcher("/staffManage.jsp");
            rd.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // 添加新员工
            AuditLogFilter.log(request, "添加", "员工信息", "成功", "通过过滤器记录");
            Staff staff = new Staff();
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setStaffCode(request.getParameter("staffCode"));
            staff.setIdNumber((String) request.getAttribute("encrypted_idNumber"));
            staff.setPhone((String) request.getAttribute("encrypted_phone"));
            staff.setAddress((String) request.getAttribute("encrypted_address"));

            staffDao.addStaff(staff);

        } else if ("update".equals(action)) {
            // 更新员工信息
            Staff staff = new Staff();
            staff.setStaffCode(request.getParameter("staffCode"));
            staff.setName(request.getParameter("name"));
            staff.setDepartmentId(Integer.valueOf(request.getParameter("department")));
            staff.setPosition(request.getParameter("position"));
            staff.setIdNumber((String) request.getAttribute("encrypted_idNumber"));
            staff.setPhone((String) request.getAttribute("encrypted_phone"));
            staff.setAddress((String) request.getAttribute("encrypted_address"));

            staffDao.updateStaff(staff);

        } else if ("delete".equals(action)) {
            // 删除员工
            AuditLogFilter.log(request, "删除", "员工信息", "成功", "通过过滤器记录");
            String staffId = request.getParameter("staffId");
            staffDao.deleteStaff(staffId);
        }

        response.sendRedirect("StaffServlet");
    }
}