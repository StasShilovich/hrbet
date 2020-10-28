package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.dao.AbstractBetDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BetServiceImpl implements BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);

    @Override
    public List<Bet> showByUser(Long userId) throws ServiceException {
        try {
            AbstractBetDao betDao = (AbstractBetDao) DaoFactory.getInstance().getClass(AbstractBetDao.class);
            List<Bet> bets = betDao.showByUser(userId);
            return bets;
        } catch (DaoException e) {
            logger.error("Show by user bets exception!");
            throw new ServiceException("Show by user bets exception!", e);
        }
    }
}
