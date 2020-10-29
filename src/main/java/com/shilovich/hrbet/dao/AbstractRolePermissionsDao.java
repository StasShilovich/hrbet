package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.RoleHolder;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public abstract class AbstractRolePermissionsDao implements Dao<RoleHolder, Long> {
    public abstract RoleHolder findAll() throws DaoException;

    @Override
    public final Optional<RoleHolder>  create(RoleHolder roleHolder) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<RoleHolder> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<RoleHolder> update(RoleHolder roleHolder) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(RoleHolder roleHolder) throws DaoException {
        throw new UnsupportedOperationException();
    }
}