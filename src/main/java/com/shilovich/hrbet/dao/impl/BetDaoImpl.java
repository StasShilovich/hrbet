package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.dao.BetDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.util.List;

public class BetDaoImpl implements BetDao {
    @Override
    public Bet findById(Long id) throws DaoException {
        return null;
    }

    @Override
    public List<Bet> findUserBets(Long userId) throws DaoException {
        return null;
    }

    @Override
    public List<Bet> findRaceBets(Long raceId) throws DaoException {
        return null;
    }

    @Override
    public boolean changeStatus(Long id) throws DaoException {
        return false;
    }
}
