package com.shilovich.hrbet.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable {
    private static final long serialVersionUID = 3775535822048561302L;

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private BigDecimal cash;
    private Role role;


    public User() {
    }

    public User(String name, String surname, String password, String email) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public User(Long id, String name, String surname, String password, String email, BigDecimal cash, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.cash = cash;
        this.role = role;
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

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("User{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append(", surname='").append(surname).append('\'');
        builder.append(", password='").append(password).append('\'');
        builder.append(", email='").append(email).append('\'');
        builder.append(", cash=").append(cash);
        builder.append(", role=").append(role);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getName() != null ? !getName().equals(user.getName()) : user.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(user.getSurname()) : user.getSurname() != null) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (getCash() != null ? !getCash().equals(user.getCash()) : user.getCash() != null) return false;
        return getRole().equals(user.getRole());
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + (getCash() != null ? getCash().hashCode() : 0);
        result = 31 * result + getRole().hashCode();
        return result;
    }
}