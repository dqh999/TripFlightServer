package com.airline.booking.application.account.service;

import com.airline.booking.application.account.dataTransferObject.response.UserResponse;

public interface UserUseCase {
    UserResponse getCurrentUser();
}
