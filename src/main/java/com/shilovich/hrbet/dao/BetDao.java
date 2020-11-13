package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class BetDao implements Dao<Bet, Long> {

    public abstract List<Bet> findByUser(Long userId) throws DaoException;

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
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}