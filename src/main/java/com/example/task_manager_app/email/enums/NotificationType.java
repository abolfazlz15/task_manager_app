package com.example.task_manager_app.email.enums;

public enum NotificationType {
    OTP("otpTemplate");

    public final String value;

    NotificationType(String value) {
        this.value = value;
    }
}
