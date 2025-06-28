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
public class StaffVeiw {

    private Integer staffCode;
    private String staffName;
    private String department;
    private String position;
    private String staffIdNumber;
    private String phone;
    private String address;
    private String familyId;
    private String familyMember;
    private String relation;
    private String familyIdNumber;
    private String birthDate;
    private String isStudent;
    private String isMajorDisease;
}
