package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.util.List;

public interface BetDao {
    Bet findById(Long id) throws DaoException;

    List<Bet> findUserBets(Long userId) throws DaoException;

    List<Bet> findRaceBets(Long raceId) throws DaoException;

    boolean changeStatus(Long id) throws DaoException;
}
