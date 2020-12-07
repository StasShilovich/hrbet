package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class RatioDao implements Dao<Ratio, Long> {

    public abstract boolean setRatios(Set<Ratio> ratioSet) throws DaoException;

    public abstract List<Ratio> findRatio(Long raceId) throws DaoException;

    protected abstract void deleteByRace(ProxyConnection connection,long raceId) throws SQLException;

    @Override
    public final Optional<Ratio> create(Ratio ratio) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Ratio> read(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<Ratio> update(Ratio ratio) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
