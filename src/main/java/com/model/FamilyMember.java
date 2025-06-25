package com.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamilyMember {
    private int id;                  // 主键ID
    private Integer staffCode;        // 员工编号
    private String name;              // 姓名
    private String relation;          // 关系（子女、父亲、母亲、配偶）
    private String idNumber;          // 身份证号
    private Date birthDate;           // 出生日期
    private Boolean isStudent;        // 是否学生
/*   private Boolean hasMortgage;      // 是否有房贷
   private Boolean isRenting;        // 是否租房*/
    private Boolean isMajorDisease;   // 是否患重大疾病
    private Timestamp createdAt;      // 创建时间
}