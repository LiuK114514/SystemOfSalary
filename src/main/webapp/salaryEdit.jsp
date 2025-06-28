
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/8
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head><title>修改工资信息</title></head>
<link rel="stylesheet" type="text/css" href="sidebar.css" />
<style>
  .container {
    max-width: 700px;
    margin: 0 auto;
    background-color: white;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    height: 95%;
  }

  h2 {
    text-align: center;
    color: #333;
    margin-bottom: 20px;
  }

  p {
    margin: 10px 0;
    font-size: 16px;
  }

  label {
    display: block;
    margin-top: 15px;
    font-weight: bold;
    color: #444;
  }

  input[type="text"] {
    width: 100%;
    padding: 4px 5px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 14px;
  }

  input[type="submit"] {
    margin-top: 30px;
    padding: 6px 12px;
    background-color: #2196F3;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  input[type="submit"]:hover {
    background-color: #1976d2;
  }
  .salary-manage{
    background-color: #1ABC9C;
  }
</style>
<body>
<jsp:include page="sidebar.jsp" />
<div class="main">
  <div class="container">
    <h2>修改工资信息</h2>
    <form action="SalaryEditServlet" method="post">
      <input type="hidden" name="staffCode" value="${staff.staffCode}" />
      <p><strong>姓名：</strong>${staff.name}</p>
      <input type="hidden" name="staffName" value="${staff.name}" />
      <p><strong>部门：</strong>${department.name}</p>
      <input type="hidden" name="departmentName" value="${department.name}" />
      <p><strong>月份：</strong>  ${salary.salaryMonth} </p>
      <input type="hidden" name="salaryMonth" value="${salary.salaryMonth}" />

      <label>基本工资：</label>
      <input type="text" name="baseSalary" value="${salary.baseSalary}" />

      <label>岗位津贴：</label>
      <input type="text" name="positionAllowance" value="${salary.positionAllowance}" />

      <label>午餐补贴：</label>
      <input type="text" name="lunchAllowance" value="${salary.lunchAllowance}" />

      <label>加班工资：</label>
      <input type="text" name="overtimePay" value="${salary.overtimePay}" />

      <label>全勤奖金：</label>
      <input type="text" name="fullAttendanceBonus" value="${salary.fullAttendanceBonus}" />

      <label>社保：</label>
      <input type="text" name="socialInsurance" value="${salary.socialInsurance}" />

      <label>公积金：</label>
      <input type="text" name="housingFund" value="${salary.housingFund}" />


      <label>请假扣款：</label>
      <input type="text" name="leaveDeduction" value="${salary.leaveDeduction}" />

      <input type="submit" value="保存修改" />
    </form>
  </div>
</div>
</body>
</body>
</html>
