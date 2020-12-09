package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class UserDao implements Dao<User, String> {
    public abstract User authorization(User user) throws DaoException;

    public abstract List<User> findAll(int limit, int offset) throws DaoException;

    public abstract long count() throws DaoException;

    public abstract boolean updateCash(BigDecimal cash, Long userId) throws DaoException;

    protected abstract boolean updateCash(ProxyConnection connection, BigDecimal cash, Long userId) throws SQLException;

    protected abstract BigDecimal findCash(ProxyConnection connection, Long userId) throws SQLException;

    @Override
    public final Optional<User> update(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }
}