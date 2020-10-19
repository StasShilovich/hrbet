package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.beans.RoleHolder;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRaceDao implements DaoCRUD<Race, Long> {
    public abstract List<Race> showAll() throws DaoException;

    @Override
    public final Optional<Race> create(Race race) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract Optional<Race> read(Long id) throws DaoException;

    @Override
    public final Optional<Race> update(Race race) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Race race) throws UnsupportedOperationException {
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
