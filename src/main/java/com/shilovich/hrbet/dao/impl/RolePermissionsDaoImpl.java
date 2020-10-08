package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.beans.RoleHolder;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RolePermissionsDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RolePermissionsDaoImpl implements RolePermissionsDao {
    private static final String ROLE_PERMISSIONS_SQL =
            "SELECT rp.role_id,p.id, p.name FROM role_permissions rp " +
                    "INNER JOIN permission p ON rp.permission_id=p.id";

    @Override
    public RoleHolder findAll() throws DaoException {
        RoleHolder roleHolder = new RoleHolder();
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            DaoFactory factory = DaoFactory.getInstance();
            connection = factory.getConnectionPool().getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(ROLE_PERMISSIONS_SQL);
            while (set.next()) {
                Long roleId = set.getLong("rp.role_id");
                Long permissionId = set.getLong("p.id");
                String permissionName = set.getString("p.name");
                roleHolder.add(roleId, permissionId, permissionName);
            }
            return roleHolder;
        } catch (SQLException e) {
            throw new DaoException("Role permissions dao fail!", e);

        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}