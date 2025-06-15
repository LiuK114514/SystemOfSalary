<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2025/6/9
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人专项附加扣除</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        p {
            font-size: 16px;
            margin: 10px 0;
        }

        form {
            margin-top: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }

        button, a {
            padding: 8px 16px;
            font-size: 14px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button {
            background-color: #28a745;
            color: white;
            margin-right: 10px;
        }

        button:hover {
            background-color: #218838;
        }

        a {
            background-color: #6c757d;
            color: white;
        }

        a:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>专项附加扣除信息</h2>

    <p><strong>员工姓名：</strong>${staff.getName()}</p>
    <p><strong>员工编号：</strong>${ staff.getStaffCode() }</p>

    <form action="SpecialDedutionImportServlet" method="post">
        <input type="hidden" name="staffCode" value="${deduction.staffCode}">

        <label>子女教育：</label>
        <input type="text" name="childEducation" value="${deduction.childEducation}"> 元

        <label>继续教育：</label>
        <select name="continueEducation">
            <option value="无" ${deduction.continueEducation == '无' ? 'selected' : ''}>无</option>
            <option value="有" ${deduction.continueEducation == '有' ? 'selected' : ''}>有（400元）</option>
        </select>

        <label>住房贷款：</label>
        <select name="housingLoanInterest">
            <option value="无" ${deduction.housingLoanInterest == '无' ? 'selected' : ''}>无</option>
            <option value="有" ${deduction.housingLoanInterest == '有' ? 'selected' : ''}>有（1000元）</option>
        </select>

        <label>住房租金：</label>
        <select name="housingRent">
            <option value="无" ${deduction.housingRent == '无' ? 'selected' : ''}>无</option>
            <option value="有" ${deduction.housingRent == '有' ? 'selected' : ''}>有（1500元）</option>
        </select>

        <label>赡养老人：</label>
        <input type="text" name="elderlySupport" value="${deduction.elderlySupport}"> 元

        <label>大病医疗：</label>
        <input type="text" name="seriousIllness" value="${deduction.seriousIllness}"> 元

        <button type="submit">保存修改</button>
        <a href="javascript:history.back();">返回</a>
    </form>
</div>
</body>
</html>