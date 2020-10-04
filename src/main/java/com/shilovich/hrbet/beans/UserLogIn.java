package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class UserLogIn implements Serializable {
    private transient String serialVersionUID;
    private String email;
    private String password;

    public UserLogIn() {
    }

    public UserLogIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
    }

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
        final StringBuilder builder = new StringBuilder("UserLogIn{");
        builder.append("email='").append(email).append('\'');
        builder.append(", password='").append(password).append('\'');
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserLogIn userLogIn = (UserLogIn) o;
        if (!email.equals(userLogIn.email)) {
            return false;
        }
        return password.equals(userLogIn.password);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}