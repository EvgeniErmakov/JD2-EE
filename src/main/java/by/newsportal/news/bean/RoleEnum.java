package by.newsportal.news.bean;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    RoleEnum(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
