package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.Horse;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractHorseDao implements DaoCRUD<Horse, Long> {
    public abstract Set<Horse> showByRace(Long raceId) throws DaoException;

    @Override
    public final Optional<Horse>  create(Horse horse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  read(Long id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Horse>  update(Horse horse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Horse horse) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    public void close(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Connection close fail!", e);
            }
        }
    }

    public void close(Statement statement) throws DaoException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DaoException("Statement close fail!", e);
            }
        }
    }

    public void close(ResultSet set) throws DaoException {
        if (set != null) {
            try {
                set.close();
            } catch (SQLException e) {
                throw new DaoException("ResultSet close fail!", e);
            }
        }
    }
}
