package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.exception.DaoException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class HorseDao implements Dao<Horse, Long> {
    public abstract Set<Horse> findByRace(Long raceId) throws DaoException;

    public abstract List<Horse> findAll() throws DaoException;

    @Override
    public final Optional<Horse>  create(Horse horse) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  update(Horse horse) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}