package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBetDao implements DaoCRUD<Bet, Long> {

    public abstract List<Bet> showByUser(Long userId) throws DaoException;

    @Override
    public final Optional<Bet>  create(Bet bet) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> read(Long id) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Bet> update(Bet bet) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Bet bet) throws UnsupportedOperationException {
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