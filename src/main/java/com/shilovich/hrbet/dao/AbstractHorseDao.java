package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractHorseDao implements Dao<Horse, Long> {
    public abstract Set<Horse> showByRace(Long raceId) throws DaoException;

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
    public final boolean delete(Horse horse) throws DaoException {
        throw new UnsupportedOperationException();
    }
}