package com.control;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.dao.SysUserDao;
import com.model.SysUser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// UserManagementServlet.java
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private SysUserDao userDao = new SysUserDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SysUser> sysUsers = userDao.getAllUsers();
        request.setAttribute("users", sysUsers);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/userManage.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateRole".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int newRoleId = Integer.parseInt(request.getParameter("roleId"));
            boolean success = userDao.updateUserRole(userId, newRoleId);
        }
        response.sendRedirect("UserServlet");
    }
}
