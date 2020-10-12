package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.RoleHolder;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class RolePermissionsDao implements DaoCRUD<RoleHolder, Long> {
    public abstract RoleHolder findAll() throws DaoException;

    @Override
    public final RoleHolder create(RoleHolder roleHolder) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final RoleHolder read(Long id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final RoleHolder update(RoleHolder roleHolder) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(RoleHolder roleHolder) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void close(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Connection close fail!", e);
            }
        }
    }

    public void close(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException("Statement close fail!", e);
            }
        }
    }

    public void close(ResultSet set) throws DaoException {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new DaoException("ResultSet close fail!", e);
            }
        }
    }
}