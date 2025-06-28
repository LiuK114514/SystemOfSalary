package com.model;


public enum UserRole {

        hr(1),         // 人事管理员
        finance(2),    // 财务管理员
        ceo(3),  // 总经理
        admin(4),        // 系统管理员
        audit(5),         // 审计员
        staff(-1);   // 普通员工
        private final int code;

        UserRole(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static UserRole fromCode(int code) {
            for (UserRole role : UserRole.values()) {
                if (role.code == code) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role code: " + code);
        }
}
