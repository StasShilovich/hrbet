package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class UserAuthorized implements Serializable {
    private static final long serialVersionUID = 7677463613635885247L;

    private Long id;
    private String userName;
    private String surname;
    private Role role;

    public UserAuthorized() {
    }

    public UserAuthorized(Long id, String userName, String surname, Role role) {
        this.id = id;
        this.userName = userName;
        this.surname = surname;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("UserAuthorized{");
        builder.append("id=").append(id);
        builder.append(", userName='").append(userName).append('\'');
        builder.append(", surname='").append(surname).append('\'');
        builder.append(", role=").append(role);
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
        UserAuthorized that = (UserAuthorized) o;
        if (!id.equals(that.id)) {
            return false;
        }
        if (!userName.equals(that.userName)) {
            return false;
        }
        if (!surname.equals(that.surname)) {
            return false;
        }
        return role.equals(that.role);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}