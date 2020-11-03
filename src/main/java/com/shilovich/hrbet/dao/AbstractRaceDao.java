package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class AbstractRaceDao implements Dao<Race, Long> {
    public abstract List<Race> showAll(int limit, int offset) throws DaoException;

    public abstract long count() throws DaoException;

    @Override
    public final Optional<Race> create(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract Optional<Race> read(Long id) throws DaoException;

    @Override
    public final Optional<Race> update(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Race race) throws DaoException {
        throw new UnsupportedOperationException();
    }
}