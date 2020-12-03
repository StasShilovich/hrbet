package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class BetDao implements Dao<Bet, Long> {

    public abstract List<Bet> findByUser(Long userId) throws DaoException;

    public abstract boolean enterResult(Map<Integer, Long> resultMap, Long raceId) throws DaoException;

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