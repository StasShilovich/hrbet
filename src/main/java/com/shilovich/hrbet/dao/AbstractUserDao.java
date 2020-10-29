package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.User;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public abstract class AbstractUserDao implements Dao<User, String> {
    public abstract User authorization(User user) throws DaoException;

    @Override
    public abstract Optional<User>  create(User user) throws DaoException;

    @Override
    public abstract Optional<User> read(String email) throws DaoException;

    @Override
    public final Optional<User> update(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }
}