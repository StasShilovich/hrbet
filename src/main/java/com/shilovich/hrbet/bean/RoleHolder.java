package com.shilovich.hrbet.bean;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RoleHolder {
    private ConcurrentMap<Long, Set<Permission>> roles;

    public RoleHolder() {
        roles = new ConcurrentHashMap<>();
    }

    public ConcurrentMap<Long, Set<Permission>> getRoles() {
        return roles;
    }

    public void add(Long roleId, Long permissionId, String permissionName) {
        Set<Permission> set = null;
        if (!roles.containsKey(roleId)) {
            set = new HashSet<>();
        } else {
            set = roles.get(roleId);
        }
        set.add(new Permission(permissionId, permissionName));
        roles.put(roleId, set);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("RoleHolder{");
        builder.append("roles=").append(roles);
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
        RoleHolder that = (RoleHolder) o;
        return roles.equals(that.roles);
    }

    @Override
    public int hashCode() {
        return roles.hashCode();
    }
}
