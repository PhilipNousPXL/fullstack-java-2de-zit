package be.pxl.services.service.auth.impl;

import be.pxl.services.exception.UnAuthorizedException;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    public String authorize(String token, Role role) {
        if (token == null || !token.startsWith("Bearer")) {
            log.warn("Endpoint accessed without or an invalid token.");
            throw new UnAuthorizedException("Invalid token");
        }

        String data = token.replace("Bearer ", "");
        String[] userData = data.split(":");
        if (userData.length != 2) {
            log.trace(String.format("Invalid token detected [%s]", token));
            throw new UnAuthorizedException("Invalid token.");
        }

        String userType = userData[1];
        String name = userData[0];

        if (role == Role.ADMIN && !userType.equals(Role.ADMIN.name)) {
            log.warn("User tried to acces admin only endpoint");
            throw new UnAuthorizedException("Only admin users are allowed.");
        }

        if (role == Role.USER && !userType.equals(Role.USER.name)) {
            throw new UnAuthorizedException("Only users are allowed.");
        }

        return name;
    }
}
