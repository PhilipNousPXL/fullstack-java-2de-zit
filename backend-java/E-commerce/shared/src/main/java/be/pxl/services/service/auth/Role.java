package be.pxl.services.service.auth;

public enum Role {
    ADMIN("admin"), USER("user");

    public final String name;

    Role(String name) {
        this.name = name;
    }
}
