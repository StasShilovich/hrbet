package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 3775535822048561302L;

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;

    public User() {
    }

    public User(Long id, String name, String surname, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
        if (!name.equals(user.name)) {
            return false;
        }
        if (!surname.equals(user.surname)) {
            return false;
        }
        if (!password.equals(user.password)) {
            return false;
        }
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}