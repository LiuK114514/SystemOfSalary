package com.control;

import com.dao.SysUserDao;
import com.model.SysUser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Timestamp;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = (String) request.getAttribute("encrypted_realName");
        String password = (String) request.getAttribute("encrypted_password");
        String idNumber = (String) request.getAttribute("encrypted_idNumber");
        String phone = (String) request.getAttribute("encrypted_phone");
        String address = (String) request.getAttribute("encrypted_address");
        String username = request.getParameter("username");
        if (name == null || password == null || idNumber == null || phone == null || address == null || username == null) {
            request.setAttribute("errorMessage", "所有字段都是必填的");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        SysUserDao userDao = new SysUserDao();
        if (userDao.getUserByUsername(username) != null) {
            request.setAttribute("errorMessage", "用户名已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            if(userDao.addUser(username,password,name,idNumber,phone,address,-1,false,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()))){
            request.setAttribute("successMessage", "注册成功，请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);}
            else {
                request.setAttribute("errorMessage", "注册失败，请稍后再试");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "注册失败，请稍后再试");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }
}
