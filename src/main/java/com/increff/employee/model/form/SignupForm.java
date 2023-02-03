package com.increff.employee.model.form;

public class SignupForm {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm{" +
                "signup_email='" + email + '\'' +
                ", signup_password='" + password + '\'' +
                '}';
    }
}
