package com.shilovich.hrbet.beans;

import java.io.Serializable;
import java.util.Set;

public class Role implements Serializable {
    private static final long serialVersionUID = 4404594163784224613L;

    private Long id;
    private String name;
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(Long id, String name, Set<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Role{");
        builder.append("id=").append(id);
        builder.append(", name='").append(name).append('\'');
        builder.append(", permissions=").append(permissions);
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
        if (!id.equals(role.id)) {
            return false;
        }
        if (!name.equals(role.name)) {
            return false;
        }
        return permissions.equals(role.permissions);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + permissions.hashCode();
        return result;
    }
}