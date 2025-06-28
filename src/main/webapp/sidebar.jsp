<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  com.model.UserRole role = (com.model.UserRole) session.getAttribute("userRole");
  request.setAttribute("role", role);
%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="sidebar">
  <div class="sidebar-menu">
    <c:if test="${role == 'admin'}">
      <button class="sidebar-button user" onclick="location.href='UserServlet'">用户管理</button>
    </c:if>

    <c:if test="${role == 'admin' or role == 'hr' or role == 'ceo'}">
      <button class="sidebar-button staff" onclick="location.href='StaffServlet?action=list'">人员管理</button>
    </c:if>

    <c:if test="${role == 'admin' or role == 'finance'}">
      <button class="sidebar-button salary-manage" onclick="location.href='SalaryQueryServlet'">工资管理</button>
      <button class="sidebar-button salary-import" onclick="location.href='salaryImport.jsp'">工资导入</button>
    </c:if>

    <c:if test="${role == 'ceo'}">
      <button class="sidebar-button salary-manage" onclick="location.href='SalaryQueryServlet'">工资管理</button>

    </c:if>

    <c:if test="${role == 'audit' or role == 'admin'}">
      <button class="sidebar-button audit" onclick="location.href='AuditLogQueryServlet'">日志审计</button>
    </c:if>

    <c:if test="${role == 'staff'}">
      <button class="sidebar-button salary-manage" onclick="location.href='StaffSalaryServlet'">工资条</button>
      <button class="sidebar-button staff" onclick="location.href='StaffInfoServlet'">个人信息管理</button>
    </c:if>

  </div>
  <div>
    <button class="sidebar-button sidebar-logout" onclick="location.href='login.jsp'">退出登录</button>
  </div>
</div>