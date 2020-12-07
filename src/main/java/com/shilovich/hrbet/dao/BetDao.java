package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.Status;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class BetDao implements Dao<Bet, Long> {

    public abstract List<Bet> findByUser(Long userId) throws DaoException;

    protected abstract List<Bet> findByRace(ProxyConnection connection, Long raceId) throws SQLException;

    protected abstract boolean updateStatus(ProxyConnection connection, Status status, long betId) throws SQLException;

    protected abstract Optional<Bet> create(ProxyConnection connection, Bet bet) throws SQLException;

    protected abstract void deleteByRace(ProxyConnection connection, long raceId) throws SQLException;

    @Override
    public final Optional<Bet> read(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> update(Bet bet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> create(Bet a) {
        throw new UnsupportedOperationException();
    }
}