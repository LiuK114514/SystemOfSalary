
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量导入工资</title>
    <link rel="stylesheet" type="text/css" href="sidebar.css">
    <style>
        .page-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
        }

        .page-title {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        .page-label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
            color: #333;
        }

        .page-input-file {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .page-button {
            background-color: #2196F3;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 20px;
            transition: background-color 0.3s ease;
        }

        .page-button:hover {
            background-color: #1976d2;
        }
        .salary-import{
            background-color: #1ABC9C;
        }
    </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="main">
    <div class="page-container">
        <h2 class="page-title">批量导入工资</h2>
        <form action="SalaryImportServlet" method="post" enctype="multipart/form-data">
            <label class="page-label" for="salaryFile">选择 Excel 文件：</label>
            <input class="page-input-file" type="file" name="salaryFile" id="salaryFile" accept=".xls,.xlsx" required>
            <button class="page-button" type="submit">导入工资</button>
        </form>
    </div>
</div>
</body>
</html>