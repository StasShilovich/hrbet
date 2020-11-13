package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class RaceDao implements Dao<Race, Long> {
    public abstract List<Race> findActive(int limit, int offset) throws DaoException;

    public abstract List<Race> findAll(int limit, int offset) throws DaoException;

    public abstract long countActual() throws DaoException;

    public abstract long countAll() throws DaoException;

    public abstract boolean addHorse(Long raceId, Set<Long> horseSet) throws DaoException;

    @Override
    public final Optional<Race> update(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }
}