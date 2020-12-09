package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class RaceDao implements Dao<Race, Long> {
    public abstract List<Race> findActive(int limit, int offset) throws DaoException;

    public abstract List<Race> findAll(int limit, int offset) throws DaoException;

    public abstract long countActual() throws DaoException;

    public abstract long countAll() throws DaoException;

    public abstract boolean addHorse(Long raceId, Set<Long> horseSet) throws DaoException;

    protected abstract boolean addRaceResult(ProxyConnection connection, Map<Integer, Long> resultMap, Long raceId) throws SQLException;

    protected abstract void deleteParticipant(ProxyConnection connection, long raceId) throws SQLException;

    protected abstract boolean delete(ProxyConnection connection, long raceId) throws SQLException;

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Race> update(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }
}