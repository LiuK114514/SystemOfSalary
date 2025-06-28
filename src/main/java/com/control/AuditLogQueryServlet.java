package com.control;

import com.dao.AuditLogDao;
import com.model.AuditLog;
import com.model.PagedLogResult;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/AuditLogQueryServlet")
public class AuditLogQueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取分页参数，默认第一页
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {}
        }

        // 获取查询条件参数
        String username = request.getParameter("username");
        String type = request.getParameter("type");
        String startDate = request.getParameter("start");

        String endDate = request.getParameter("end");

        AuditLogDao auditLogDao = new AuditLogDao();
        PagedLogResult pagedResult = auditLogDao.getAuditLogsByPage(page, pageSize, username, type, startDate, endDate);

        request.setAttribute("logs", pagedResult.getLogs());
        request.setAttribute("totalPages", pagedResult.getTotalPages());
        request.setAttribute("currentPage", page);


        request.getRequestDispatcher("/auditLog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }
}
