package com.airline.booking.application.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            var remoteAddr = request.getRemoteAddr();
            if (remoteAddr == null) {
                remoteAddr = "127.0.0.1";
            }

            return remoteAddr;
        }

        return xForwardedForHeader.split(",")[0].trim();
    }
    public static String getSessionId(HttpServletRequest request) {
        return request.getHeader("X-Session-Id");
    }
}
