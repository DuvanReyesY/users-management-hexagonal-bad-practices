package com.jcaa.usersmanagement.application.service.dto;

import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;

public record UserAccessPolicy(
        UserId userId,
        UserEmail email,
        UserStatus status,
        int maxInactivityDays) {
}