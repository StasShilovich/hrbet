package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.beans.RoleHolder;
import com.shilovich.hrbet.dao.AbstractRolePermissionsDao;
import com.shilovich.hrbet.dao.connection.pool.MySqlConnectionPool;
import com.shilovich.hrbet.dao.connection.pool.impl.MySqlConnectionPoolImpl;
import com.shilovich.hrbet.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.shilovich.hrbet.constant.CommonConstant.P_ID;
import static com.shilovich.hrbet.constant.CommonConstant.P_NAME;
import static com.shilovich.hrbet.constant.CommonConstant.RP_ROLE_ID;

public class RolePermissionsDaoImpl extends AbstractRolePermissionsDao {
    private static final Logger logger = LogManager.getLogger(RolePermissionsDaoImpl.class);

    private final MySqlConnectionPool pool = MySqlConnectionPoolImpl.getInstance();
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
            connection = pool.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(ROLE_PERMISSIONS_SQL);
            while (set.next()) {
                Long roleId = set.getLong(RP_ROLE_ID);
                Long permissionId = set.getLong(P_ID);
                String permissionName = set.getString(P_NAME);
                roleHolder.add(roleId, permissionId, permissionName);
            }
            return roleHolder;
        } catch (SQLException e) {
            logger.error("Role permissions dao fail!");
            throw new DaoException("Role permissions dao fail!", e);

        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}