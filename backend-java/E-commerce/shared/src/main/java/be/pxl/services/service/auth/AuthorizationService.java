package be.pxl.services.service.auth;

public interface AuthorizationService {
    String authorize(String token, Role role);
}
