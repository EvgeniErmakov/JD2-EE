package by.newsportal.news.bean;

import jakarta.servlet.http.HttpServletRequest;

public class RegistrationInfo {
    private String name;
    private String email;
    private String enteredPassword;
    private String repeatedPassword;
    private String surname;

    public RegistrationInfo() {
    }

    public RegistrationInfo(HttpServletRequest request) {
        this.email = request.getParameter("email");
        this.enteredPassword = request.getParameter("enteredPassword");
        this.repeatedPassword = request.getParameter("repeatedPassword");
        this.name = request.getParameter("name");
        this.surname = request.getParameter("surname");
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((enteredPassword == null) ? 0 : enteredPassword.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((repeatedPassword == null) ? 0 : repeatedPassword.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RegistrationInfo other = (RegistrationInfo) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (enteredPassword == null) {
            if (other.enteredPassword != null)
                return false;
        } else if (!enteredPassword.equals(other.enteredPassword))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (repeatedPassword == null) {
            if (other.repeatedPassword != null)
                return false;
        } else if (!repeatedPassword.equals(other.repeatedPassword))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RegistrationInfo{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", enteredPassword='" + enteredPassword + '\'' +
                ", repeatedPassword='" + repeatedPassword + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
