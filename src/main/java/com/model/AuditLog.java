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
public class AuditLog {
    private Integer id;
    private String username;
    private String role;
    private String operationType;
    private String operationObject;
    private String ipAddress;
    private Timestamp timestamp;
    private String result;
    private String remarks;
}