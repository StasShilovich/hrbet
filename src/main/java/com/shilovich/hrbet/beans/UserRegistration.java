package com.shilovich.hrbet.beans;

import java.io.Serializable;

public class UserRegistration implements Serializable {
    private transient String serialVersionUID;
    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;

    public UserRegistration() {
    }

    public UserRegistration(Long id, String name, String surname, String password, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    final void setSerialVersionUID(String uid) {
        this.serialVersionUID = uid;
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
    public String toString() {
        final StringBuilder builder = new StringBuilder("UserRegistration{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append(", surname='").append(surname).append('\'');
        builder.append(", password='").append(password).append('\'');
        builder.append(", email='").append(email).append('\'');
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        UserRegistration that = (UserRegistration) o;
        if (!id.equals(that.id)){
            return false;
        }
        if (!name.equals(that.name)){
            return false;
        }
        if (!surname.equals(that.surname)){
            return false;
        }
        if (!password.equals(that.password)){
            return false;
        }
        return email.equals(that.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}