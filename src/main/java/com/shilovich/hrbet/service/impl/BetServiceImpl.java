package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.dao.BetDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BetServiceImpl implements BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);

    private static BetService instance;

    private BetServiceImpl() {
    }

    public static BetService getInstance() {
        if (instance == null) {
            instance = new BetServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Bet> showByUser(Long userId) throws ServiceException {
        try {
            BetDao betDao = (BetDao) DaoFactory.getInstance().getClass(BetDao.class);
            List<Bet> bets = betDao.findByUser(userId);
            return bets;
        } catch (DaoException e) {
            logger.error("Show by user bets exception!", e);
            throw new ServiceException("Show by user bets exception!", e);
        }
    }
}
