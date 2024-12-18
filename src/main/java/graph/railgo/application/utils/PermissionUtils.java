package graph.railgo.application.utils;

import graph.railgo.application.utils.exception.ApplicationException;
import graph.railgo.infrastructure.security.UserDetail;

public class PermissionUtils {

    public static void hasPermission(UserDetail userDetail,
                                        String requiredRole) {
        if (!userDetail.getRole().equals(requiredRole)){
            throw new ApplicationException("");
        };
    }
}