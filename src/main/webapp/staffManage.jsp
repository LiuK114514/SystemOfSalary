<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Staff, com.model.SysUser" %>
<%--<%--%>
<%--    // 权限检查 - 只有HR可以访问此页面--%>
<%--    SysUser currentUser = (SysUser) session.getAttribute("currentUser");--%>
<%--    if (currentUser == null || !currentUser.getRole().equals("HR")) {--%>
<%--        response.sendRedirect("access_denied.jsp");--%>
<%--        return;--%>
<%--    }--%>
<%--%>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>员工管理系统 - HR管理</title>
    <link rel="stylesheet" type="text/css" href="sidebar.css">
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="main">
    <div class="container">
        <h1>员工管理</h1>
        <div class="search-container">
            <form action="StaffServlet?action=search" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <div class="search-group">
                    <input type="text" name="keyword" placeholder="输入姓名或工号"
                           value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                    <button type="submit" class="btn btn-search">搜索</button>
                    <a href="StaffServlet?action=list" class="btn btn-reset">重置</a>
                </div>
            </form>
        </div>
        <button class="btn btn-add" onclick="document.getElementById('addModal').style.display='block'">添加新员工</button>
        <table>
            <thead>
            <tr>
                <th>员工编号</th>
                <th>姓名</th>
                <th>部门</th>
                <th>岗位</th>
                <th>身份证号</th>
                <th>手机号</th>
                <th>住址</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");

                if (staffList != null && !staffList.isEmpty()) {
                    for (Staff staff : staffList) {
            %>
            <tr>
                <td><%= staff.getStaffCode() %></td>
                <td><%= staff.getName() %></td>
                <td><%= staff.getDepartmentId() %></td>
                <td><%= staff.getPosition() %></td>
                <td><%= staff.getIdNumber() != null ? staff.getIdNumber().replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1**********$2") : ""%></td>
                <td><%= staff.getPhone() != null ? staff.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2") : "" %></td>
                <td><%= staff.getAddress() %></td>
                <td class="action-buttons">
                    <button class="btn btn-edit" onclick="document.getElementById('editModal').style.display='block'; loadStaffData('<%= staff.getStaffCode() %>')">编辑</button>
                    <form action="StaffServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="staffId" value="<%= staff.getStaffCode() %>">
                        <button type="submit" class="btn btn-delete" onclick="return confirm('确定要删除员工 <%= staff.getName() %> 吗？')">删除</button>
                    </form>
                    <a href="FamilyMemberServlet?action=list&staffCode=<%= staff.getStaffCode() %>" class="btn btn-family">管理家庭</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="8" style="text-align: center;">暂无员工数据</td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- 添加员工模态框 -->
    <div id="addModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('addModal').style.display='none'">&times;</span>
            <h2>添加新员工</h2>

            <form action="StaffServlet" method="post">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="addName">姓名</label>
                    <input type="text" id="addName" name="name" required>
                </div>
                <div class="form-group">
                    <label for="addDepartment">部门</label>
                    <input type="text" id="addDepartment" name="department" required>
                </div>
                <div class="form-group">
                    <label for="addPosition">岗位</label>
                    <input type="text" id="addPosition" name="position" required>
                </div>
                <div class="form-group">
                    <label for="addPosition">工号</label>
                    <input type="text" id="addStaffCode" name="staffCode" required>
                </div>
                <div class="form-group">
                    <label for="addIdNumber">身份证号</label>
                    <input type="text" id="addIdNumber" name="idNumber" required pattern="\d{18}">
                </div>
                <div class="form-group">
                    <label for="addPhone">手机号</label>
                    <input type="tel" id="addPhone" name="phone" required pattern="\d{11}">
                </div>
                <div class="form-group">
                    <label for="addAddress">住址</label>
                    <input type="text" id="addAddress" name="address" required>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn" onclick="document.getElementById('addModal').style.display='none'">取消</button>
                    <button type="submit" class="btn btn-edit">保存</button>
                </div>
            </form>
        </div>
    </div>

    <!-- 编辑员工模态框 -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="document.getElementById('editModal').style.display='none'">&times;</span>
            <h2>编辑员工信息</h2>
            <form action="StaffServlet" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="editStaffCode" name="staffCode">
                <div class="form-group">
                    <label for="editName">姓名</label>
                    <input type="text" id="editName" name="name" required>
                </div>
                <div class="form-group">
                    <label for="editDepartment">部门</label>
                    <input type="text" id="editDepartment" name="department" required>
                </div>
                <div class="form-group">
                    <label for="editPosition">岗位</label>
                    <input type="text" id="editPosition" name="position" required>
                </div>
                <div class="form-group">
                    <label for="editIdNumber">身份证号</label>
                    <input type="text" id="editIdNumber" name="idNumber" required pattern="\d{18}">
                </div>
                <div class="form-group">
                    <label for="editPhone">手机号</label>
                    <input type="tel" id="editPhone" name="phone" required pattern="\d{11}">
                </div>
                <div class="form-group">
                    <label for="editAddress">住址</label>
                    <input type="text" id="editAddress" name="address" required>
                </div>
                <div class="form-actions">
                    <button type="button" class="btn" onclick="document.getElementById('editModal').style.display='none'">取消</button>
                    <button type="submit" class="btn btn-edit">更新</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

<script>
    // 仅保留必要的加载员工数据函数
    function loadStaffData(staffCode) {
        document.getElementById("editStaffCode").value = staffCode;
        console.log("加载员工数据: " + staffCode);

    }

    // 关闭模态框当点击模态框外部
    window.onclick = function(event) {
        if (event.target == document.getElementById('addModal')) {
            document.getElementById('addModal').style.display = 'none';
        }
        if (event.target == document.getElementById('editModal')) {
            document.getElementById('editModal').style.display = 'none';
        }
    }
</script>

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
    }
    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 8px;
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
        gap: 5px;
    }
    .btn {
        padding: 5px 10px;
        border: none;
        border-radius: 3px;
        cursor: pointer;
    }
    .btn-edit {
        background-color: #4CAF50;
        color: white;
    }
    .btn-delete {
        background-color: #f44336;
        color: white;
    }
    .btn-add {
        background-color: #2196F3;
        color: white;
        margin-bottom: 15px;
        padding: 8px 15px;
    }
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0,0,0,0.4);
    }
    .modal-content {
        background-color: #fefefe;
        margin: 10% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 50%;
        border-radius: 5px;
    }
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
    .form-group input, .form-group select {
        width: 100%;
        padding: 8px;
        box-sizing: border-box;
        border: 1px solid #ddd;
        border-radius: 3px;
    }
    .form-actions {
        text-align: right;
        margin-top: 20px;
    }
    .sidebar-button:nth-child(2){
        background-color: #1ABC9C;
    }

    .search-container {
        margin: 20px 0;
    }

    .search-form {
        display: flex;
        justify-content: center;
    }

    .search-group {
        display: flex;
        gap: 10px;
        width: 100%;
        max-width: 800px;
    }

    .search-group input[type="text"] {
        flex: 1;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 3px;
    }

    .search-group select {
        width: 150px;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 3px;
    }

    .btn-search {
        background-color: #4CAF50;
        color: white;
        padding: 8px 15px;
    }

    .btn-reset {
        background-color: #f44336;
        color: white;
        padding: 8px 15px;
        text-decoration: none;
        display: inline-block;
        line-height: normal;
    }
</style>