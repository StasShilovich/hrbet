package com.shilovich.hrbet.beans;

import java.security.Permission;

public class Role {
    private String name;
    private Permission permission;

    public Role() {
    }

    public Role(String name, Permission permission) {
        this.name = name;
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Role{");
        builder.append("name='").append(name).append('\'');
        builder.append(", permission=").append(permission);
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
        Role role = (Role) o;
        if (!name.equals(role.name)) {
            return false;
        }
        return permission.equals(role.permission);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + permission.hashCode();
        return result;
    }
}
