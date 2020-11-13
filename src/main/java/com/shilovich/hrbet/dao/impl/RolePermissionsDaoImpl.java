package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Permission;
import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.dao.RolePermissionsDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class RolePermissionsDaoImpl extends RolePermissionsDao {
    private static final Logger logger = LogManager.getLogger(RolePermissionsDaoImpl.class);

    private static RolePermissionsDao instance;
    private static final String ROLE_PERMISSIONS_SQL =
            "SELECT r.id, r.name, p.id, p.name from roles r join role_permissions rp on rp.role_id = r.id " +
                    "join permission p on rp.permission_id = p.id";

    private RolePermissionsDaoImpl() {
    }

    public static RolePermissionsDao getInstance() {
        if (instance == null) {
            instance = new RolePermissionsDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Role> findAll() throws DaoException {
        List<Role> roles = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(ROLE_PERMISSIONS_SQL);
            while (set.next()) {
                Long roleId = set.getLong(ROLE_ID);
                String roleName = set.getString(ROLE_NAME);
                Long permissionId = set.getLong(P_ID);
                String permissionName = set.getString(P_NAME);
                int roleIndex = roleExist(roles, roleId);
                if (roleIndex < 0) {
                    Role role = new Role();
                    role.setId(roleId);
                    role.setName(roleName);
                    Set<Permission> permissions = new HashSet<>();
                    permissions.add(new Permission(permissionId, permissionName));
                    role.setPermissions(permissions);
                    roles.add(role);
                } else {
                    Role role = roles.get(roleIndex);
                    role.getPermissions().add(new Permission(permissionId, permissionName));
                }
            }
            return roles;
        } catch (SQLException e) {
            logger.error("Role permissions dao fail!", e);
            throw new DaoException("Role permissions dao fail!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    private int roleExist(List<Role> roles, Long roleId) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getId().equals(roleId)) {
                return i;
            }
        }
        return -1;
    }
}