package com.control;

import com.dao.SalaryRecordDao;
import com.model.PagedSalaryResult;
import com.model.SalaryView;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.Iterator;

@WebServlet("/StaffSalaryServlet")
public class StaffSalaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    HttpSession session = request.getSession();
    String username = (String) session.getAttribute("username");
    SalaryRecordDao salaryDAO = new SalaryRecordDao();
    String staffName=salaryDAO.getStaffNameByUsername(username);
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        PagedSalaryResult pagedResult = salaryDAO.findByPageWithProc(
                page, pageSize, staffName, null, null   , null
        );
        Iterator<SalaryView> iterator = pagedResult.getSalaryViews().iterator();
        while (iterator.hasNext()) {
            SalaryView salaryView = iterator.next();
            if (!salaryView.getStatus().equals("paid")) {
                iterator.remove();
            }
        }
        request.setAttribute("salaryViews", pagedResult.getSalaryViews());
        request.setAttribute("totalPages", pagedResult.getTotalPages());
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/staffSalary.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String staffName = request.getParameter("staffName");
        String departmentName = request.getParameter("department");
        String startDate = request.getParameter("start");
        String endDate = request.getParameter("end");
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        SalaryRecordDao salaryDAO = new SalaryRecordDao();
        PagedSalaryResult pagedResult = salaryDAO.findByPageWithProc(
                page, pageSize, staffName, departmentName, startDate   , endDate
        );
        Iterator<SalaryView> iterator = pagedResult.getSalaryViews().iterator();
        while (iterator.hasNext()) {
            SalaryView salaryView = iterator.next();
            if (!salaryView.getStatus().equals("paid")) {
                iterator.remove();
            }
        }
        request.setAttribute("salaryViews", pagedResult.getSalaryViews());
        request.setAttribute("totalPages", pagedResult.getTotalPages());
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/staffSalary.jsp").forward(request, response);
    }
}
