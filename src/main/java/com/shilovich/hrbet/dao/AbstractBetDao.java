package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBetDao implements Dao<Bet, Long> {

    public abstract List<Bet> showByUser(Long userId) throws DaoException;

    @Override
    public final Optional<Bet>  create(Bet bet) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> update(Bet bet) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Bet bet) throws DaoException {
        throw new UnsupportedOperationException();
    }
}