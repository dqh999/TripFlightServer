package com.airline.booking.application.account.dataTransferObject.request;

import jakarta.validation.constraints.Size;

public record LogoutRequest(@Size(max = 100) String accessToken){}
