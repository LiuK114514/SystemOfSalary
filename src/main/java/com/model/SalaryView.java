package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryView {
    private Integer id;
    private Integer staffCode;
    private String staffName;
    private String department;
    private String salaryMonth;
    private BigDecimal baseSalary;
    private BigDecimal positionAllowance;
    private BigDecimal lunchAllowance;
    private BigDecimal overtimePay;
    private BigDecimal fullAttendanceBonus;
    private BigDecimal socialInsurance;
    private BigDecimal housingFund;
    private BigDecimal personalIncomeTax;
    private BigDecimal leaveDeduction;
    private BigDecimal actualSalary;
    private String status;                   // 工资状态（draft、finance_approved、manager_approved、paid、rejected）
    private Timestamp financeApproveTime;   // 财务初审时间
    private Timestamp managerApproveTime;   // 总经理复审时间
    private String rejectReason;             // 驳回原因
    private Timestamp paidTime;              // 发放时间


}
