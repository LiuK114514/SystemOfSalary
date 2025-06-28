package com.control;

import com.dao.SalaryRecordDao;

import com.model.*;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import javax.swing.text.html.CSS;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SalaryQueryServlet")
public class SalaryQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

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
                page, pageSize, staffName, departmentName, startDate, endDate
        );
        request.setAttribute("salaryViews", pagedResult.getSalaryViews());
        request.setAttribute("totalPages", pagedResult.getTotalPages());
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/salaryManage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
