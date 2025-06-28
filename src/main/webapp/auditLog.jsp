<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/15
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>日志管理</title>
    <link rel="stylesheet" type="text/css" href="sidebar.css" />
    <style>
        .main form {
            margin-bottom: 20px;
        }
        .main label, .main select, .main input {
            margin-right: 10px;
            font-size: 14px;
        }
        .main button {
            margin-left: 10px;
            padding: 6px 12px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
            font-size: 14px;
        }
        th {
            background-color: #34495E;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .audit{
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

    </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="main">
    <form method="post" action="AuditLogQueryServlet">
        用户名：
        <input type="text" name="username" />
        操作类型：
        <select name="type">
            <option value="">全部</option>
            <option value="登录">登录</option>
            <option value="查询">查询</option>
            <option value="注册">注册</option>
            <option value="添加">添加</option>
            <option value="修改">修改</option>
            <option value="删除">删除</option>
        </select>
        时间：
        <input type="date" name="start" /> ~ <input type="date" name="end" />
        <button type="submit">查询</button>
    </form>

    <table>
        <tr>
            <th>时间</th><th>用户名</th><th>角色</th><th>操作类型</th><th>对象</th><th>结果</th><th>IP</th>
        </tr>
        <c:forEach var="log" items="${requestScope.logs}" varStatus="status">
            <tr>
                <td>
                    <fmt:formatDate value="${log.timestamp}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>${log.username}</td>
                <td>${log.role}</td>
                <td>${log.operationType}</td>
                <td>${log.operationObject}</td>
                <td>${log.result}</td>
                <td>${log.ipAddress}</td>
            </tr>
        </c:forEach>
    </table>
    <div class="pagination" >
        <c:forEach begin="1" end="${totalPages}" var="i">
            <a href="?page=${i}&username=${param.username}&type=${param.type}&start=${param.start}&end=${param.end}"
               class="${i == currentPage ? 'current-page' : ''}">
                    ${i}
            </a>
        </c:forEach>
    </div>
</div>
</body>
</html>