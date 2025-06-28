<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="sidebar.css">
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="main">
    <div class="container">
        <h1>用户管理</h1>
        <table>
            <thead>
            <tr>
                <th>用户名</th>
                <th>真实姓名</th>
                <th>电话号码</th>
                <th>地址</th>
                <th>角色</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.realName}</td>
                    <td>${user.phone}</td>
                    <td>${user.address}</td>
                    <td>
                            <span class="role-badge role-${user.roleId}">
                                <c:choose>
                                    <c:when test="${user.roleId == 1}">人事管理员</c:when>
                                    <c:when test="${user.roleId == 2}">财务管理员</c:when>
                                    <c:when test="${user.roleId == 3}">总经理</c:when>
                                    <c:when test="${user.roleId == 4}">系统管理员</c:when>
                                    <c:when test="${user.roleId == 5}">审计员</c:when>
                                    <c:otherwise>无权限</c:otherwise>
                                </c:choose>
                            </span>
                    </td>
                    <td class="action-buttons">
                        <button class="btn btn-edit"
                                onclick="openEditModal('${user.id}', ${user.roleId})">
                            编辑权限
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty users}">
                <tr>
                    <td colspan="6" style="text-align: center;">暂无用户数据</td>
                </tr>
            </c:if>
            </tbody>
        </table>

    </div>

    <!-- 权限编辑模态框 -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('editModal').style.display='none'">&times;</span>
            <h2>编辑用户权限</h2>
            <form id="editForm" action="UserServlet" method="post">
                <input type="hidden" name="action" value="updateRole">
                <input type="hidden" id="editUserId" name="userId">
                <div class="form-group">
                    <label for="roleSelect">选择角色</label>
                    <select id="roleSelect" name="roleId" class="form-control">
                        <option value="1">人事管理员</option>
                        <option value="2">财务管理员</option>
                        <option value="3">总经理</option>
                        <option value="4">系统管理员</option>
                        <option value="5">审计员</option>
                        <option value="-1">无权限</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="button" class="btn" onclick="document.getElementById('editModal').style.display='none'">取消</button>
                    <button type="submit" class="btn btn-edit">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // 打开编辑模态框并填充数据
    function openEditModal(userId, currentRoleId) {
        document.getElementById('editUserId').value = userId;
        document.getElementById('roleSelect').value = currentRoleId;
        console.log("userId:", userId, "roleId:", currentRoleId); // 调试输出
        document.getElementById('editModal').style.display = 'block';
    }

    // 点击模态框外部关闭
    window.onclick = function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = 'none';
        }
    }
</script>
</body>
</html>

<style>
    .container {
        max-width: 1200px;
        margin: 0 auto;
        background-color: white;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    h1 {
        color: #333;
        text-align: center;
        margin-bottom: 20px;
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 12px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
        position: sticky;
        top: 0;
    }
    tr:nth-child(even) {
        background-color: #f9f9f9;
    }
    tr:hover {
        background-color: #f1f1f1;
    }
    .action-buttons {
        display: flex;
        gap: 8px;
    }
    .btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        text-decoration: none;
        color: white;
    }
    .btn-edit {
        background-color: #4CAF50;
    }
    .btn-delete {
        background-color: #f44336;
    }
    .btn-add {
        background-color: #2196F3;
        margin-bottom: 15px;
        padding: 8px 16px;
    }
    .role-badge {
        font-size: 0.75rem;
        padding: 4px 8px;
        border-radius: 12px;
        color: white;
        display: inline-block;
    }
    .role-1 { background-color: #3F51B5; } /* HR */
    .role-2 { background-color: #4CAF50; } /* 财务 */
    .role-3 { background-color: #F44336; } /* CEO */
    .role-4 { background-color: #FFC107; color: #333; } /* 系统管理员 */
    .role-5 { background-color: #00BCD4; } /* 审计员 */
    .role--1 { background-color: #9E9E9E; } /* 无权限 */

    /* 模态框样式 */
    .modal {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4);
    }
    .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 25px;
        border: 1px solid #888;
        width: 60%;
        max-width: 600px;
        border-radius: 5px;
        position: relative;
    }
    .close {
        color: #aaa;
        position: absolute;
        right: 20px;
        top: 10px;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }
    .close:hover {
        color: #333;
    }
    .form-group {
        margin-bottom: 16px;
    }
    .form-group label {
        display: block;
        margin-bottom: 6px;
        font-weight: bold;
    }
    .form-group select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    .form-actions {
        text-align: right;
        margin-top: 20px;
    }
    .user{
        background-color: #1ABC9C;
    }
</style>