package com.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRecord {
    private Integer id;
    private Integer staffCode;               // 员工编号
    private String salaryMonth;              // 格式如 "2025-06"
    private BigDecimal baseSalary;           // 基本工资
    private BigDecimal positionAllowance;    // 岗位津贴
    private BigDecimal lunchAllowance;       // 午餐补贴
    private BigDecimal overtimePay;           // 加班工资
    private BigDecimal fullAttendanceBonus;  // 全勤奖
    private BigDecimal socialInsurance;      // 社保扣款
    private BigDecimal housingFund;          // 公积金扣款
    private BigDecimal personalIncomeTax;    // 个人所得税
    private BigDecimal leaveDeduction;       // 请假扣款
    private BigDecimal actualSalary;         // 实际工资
    private Timestamp createdAt;             // 创建时间

    private String status;                   // 工资状态（draft、finance_approved、ceo_approved、paid、rejected）
    private Timestamp financeApproveTime;   // 财务初审时间
    private Timestamp managerApproveTime;   // 总经理复审时间
    private String rejectReason;             // 驳回原因
    private Timestamp paidTime;              // 发放时间


}