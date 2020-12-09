package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * The interface Bet service.
 */
public interface BetService extends Service {
    /**
     * Show user bets.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bet> showByUser(Long userId) throws ServiceException;

    /**
     * Add bet.
     *
     * @param info     the info
     * @param betCash  the bet cash
     * @param userCash the user cash
     * @param userId   the user id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean add(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException;

    /**
     * Enter race result and pay all bets.
     *
     * @param horseMap the horse map
     * @param raceId   the race id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException;
}