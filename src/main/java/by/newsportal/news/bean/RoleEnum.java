package by.newsportal.news.bean;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private final String ROLE;

    RoleEnum(String role) {
        this.ROLE = role;
    }

    public String getRole() {
        return ROLE;
    }
}
