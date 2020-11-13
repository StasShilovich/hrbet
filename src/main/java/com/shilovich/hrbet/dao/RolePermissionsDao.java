package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Role;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class RolePermissionsDao implements Dao<Role, Long> {
    public abstract List<Role> findAll() throws DaoException;

    @Override
    public final Optional<Role>  create(Role roleHolder) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Role> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Role> update(Role roleHolder) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}