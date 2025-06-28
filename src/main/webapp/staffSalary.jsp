<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/28
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>员工工资条</title>
    <link rel="stylesheet" type="text/css" href="sidebar.css">
    <style>

        .salary-container {
            display: flex;
            gap: 30px;
            max-width: 1300px;
            margin: 0 auto;
        }

        .salary-left-panel {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
        }

        .salary-left-panel {
            flex: 2;
        }


        .salary-h1 {
            color: #333;
            margin-top: 0;
            margin-bottom: 20px;
        }

        .salary-button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin: 10px;
        }
        /*     !* 发放按钮 *!
             .salary-button:nth-child(1) {
                 background-color: #9C27B0;
             }
     */
        /* 工资录入 */
        .salary-button:nth-child(2) {
            background-color: #2196F3;
        }

        /* 审批通过按钮 */
        .salary-button:nth-child(3) {
            background-color: #4CAF50;
        }

        /* 驳回按钮 */
        .salary-button.reject-button {
            background-color: #f44336;
        }


        .salary-form {
            margin-bottom: 20px;
            padding: 15px;
            background: #e3f2fd;
            border-radius: 6px;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
            font-size: 14px;
        }

        .salary-form input[type="text"],
        .salary-form input[type="date"] {
            padding: 3px 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .salary-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .salary-table th, .salary-table td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
        }

        .salary-table th {
            background-color: #f2f2f2;
            color: #333;
        }

        .salary-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .salary-table tr:nth-child(odd) {
            background-color: #ffffff;
        }

        .salary-fieldset {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px 15px;
            margin-bottom: 15px;
            background-color: #fcfcfc;
        }

        .salary-legend {
            font-weight: bold;
            color: #333;
            padding: 0 5px;
        }

        .salary-input {
            width: 40%;
            padding: 6px 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .salary-modal {
            display: none;
            position: fixed;
            z-index: 999;
            left: 0; top: 0;
            width: 100%; height: 100%;
            background-color: rgba(0,0,0,0.4);
        }

        .salary-modal-content {
            background-color: #fff;
            margin: 10px auto;
            padding: 30px;
            border-radius: 8px;
            width: 500px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.3);
            position: relative;
            max-height: 90vh;
            overflow-y: auto;
        }

        .salary-modal-content h3 {
            margin-top: 0;
            color: #333;
            text-align: center;
        }

        .salary-modal-content .salary-fieldset {
            margin-bottom: 15px;
        }

        .salary-modal-close {
            position: absolute;
            top: 10px; right: 15px;
            font-size: 24px;
            font-weight: bold;
            color: #aaa;
            cursor: pointer;
        }

        .salary-modal-close:hover {
            color: #000;
        }
        .salary-manage{
            background-color: #1ABC9C;
        }
        .pagination{
            text-align: center;
            margin-top: 20px;
        }
        .pagination a {
            display: inline-block;
            padding: 6px 12px;
            margin: 0 4px;
            text-decoration: none;
            border: 1px solid #ccc;
            border-radius: 4px;
            color: #333;
            font-size: 14px;
            transition: all 0.3s;
        }

        .pagination a:hover {
            background-color: #f1f1f1;
            border-color: #1ABC9C;
            color: #1ABC9C;
        }

        .pagination a.current-page {
            background-color: #1ABC9C;
            color: white;
            border-color: #1ABC9C;
            font-weight: bold;
            cursor: default;
            pointer-events: none;
        }
        #status-draft {
            color: orange;
            font-weight: bold;
        }
        #status-finance {
            color: #1E90FF; /* DodgerBlue */
            font-weight: bold;
        }
        #status-ceo {
            color: green;
            font-weight: bold;
        }
        #status-paid {
            color: #228B22; /* ForestGreen */
            font-weight: bold;
        }
        #status-rejected {
            color: red;
            font-weight: bold;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 30px;
            border-radius: 8px;
            width: 500px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.25);
            position: relative;
        }

        .modal-content h3 {
            margin-top: 0;
            color: #333;
            text-align: center;
            font-size: 1.5em;
            margin-bottom: 20px;
        }

        .modal-content textarea {
            width: 100%;
            min-height: 120px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: vertical;
            font-family: inherit;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .modal-content textarea:focus {
            border-color: #2196F3;
            outline: none;
            box-shadow: 0 0 5px rgba(33, 150, 243, 0.3);
        }

        .modal-content button[type="submit"] {
            background-color: #f44336;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            width: 100%;
            transition: background-color 0.3s;
        }

        .modal-content button[type="submit"]:hover {
            background-color: #d32f2f;
        }

        .close {
            position: absolute;
            top: 15px;
            right: 20px;
            font-size: 24px;
            font-weight: bold;
            color: #aaa;
            cursor: pointer;
            transition: color 0.3s;
        }

        .close:hover {
            color: #333;
        }
        .action-button.edit-button {
            background-color: #FF9800; /* 橙色 */
            padding: 6px 12px;
            font-size: 13px;
            min-width: auto;
            cursor: pointer;
        }

        /* 表格中的按钮适配 */
        .salary-table .action-button {
            margin: 0;
            width: 100%;
            box-sizing: border-box;
        }

    </style>
</head>
<body>
<jsp:include page="sidebar.jsp"  />
<div class="main">
    <div class="salary-container">
        <div class="salary-left-panel">
            <h1 class="salary-h1">工资查询</h1>
            <form action="StaffSalaryServlet" method="post" class="salary-form">
                姓名：<input type="text" name="staffName" />
                部门：<input type="text" name="departmentName" />
                时间：<input type="date" name="start"> ~ <input type="date" name="end">
                <input type="submit" value="查询" />
            </form>

                <table class="salary-table">
                    <tr>
                    <tr>

                        <th>工号</th>
                        <th>姓名</th>
                        <th>部门</th>
                        <th>月份</th>
                        <th>基本工资</th>
                        <th>岗位津贴</th>
                        <th>午餐补贴</th>
                        <th>加班工资</th>
                        <th>全勤奖金</th>
                        <th>社保</th>
                        <th>公积金</th>
                        <th>个税</th>
                        <th>请假扣款</th>
                        <th>实发工资</th>
                    </tr>

                    <c:forEach var="s" items="${requestScope.salaryViews}" varStatus="status">
                        <tr>
                            <td>${s.staffCode}</td>
                            <td>${s.staffName}</td>
                            <td>${s.department}</td>
                            <td>${s.salaryMonth}</td>
                            <td>${s.baseSalary}</td>
                            <td>${s.positionAllowance}</td>
                            <td>${s.lunchAllowance}</td>
                            <td>${s.overtimePay}</td>
                            <td>${s.fullAttendanceBonus}</td>
                            <td>${s.socialInsurance}</td>
                            <td>${s.housingFund}</td>
                            <td>${s.personalIncomeTax}</td>
                            <td>${s.leaveDeduction}</td>
                            <td>${s.actualSalary}</td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="pagination" >
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="?page=${i}&staffName=${param.staffName}&department=${param.department}&start=${param.start}&end=${param.end}"
                           class="${i == currentPage ? 'current-page' : ''}">
                                ${i}
                        </a>
                    </c:forEach>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
