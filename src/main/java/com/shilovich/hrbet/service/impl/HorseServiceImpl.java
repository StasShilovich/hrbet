package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.dao.HorseDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import com.shilovich.hrbet.service.HorseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class HorseServiceImpl implements HorseService {
    private static final Logger logger = LogManager.getLogger(HorseServiceImpl.class);

    private static HorseService instance;

    private HorseServiceImpl() {
    }

    public static HorseService getInstance() {
        if (instance == null) {
            instance = new HorseServiceImpl();
        }
        return instance;
    }

    @Override
    public Set<Horse> showByRace(Long raceId) throws ServiceException {
        try {
            HorseDao horseDao = (HorseDao) DaoFactory.getInstance().getClass(HorseDao.class);
            Set<Horse> horses = horseDao.findByRace(raceId);
            return horses;
        } catch (DaoException e) {
            logger.error("Show horses by race exception!", e);
            throw new ServiceException("Show horses by race exception!", e);
        }
    }

    @Override
    public List<Horse> findAll() throws ServiceException {
        try {
            HorseDao horseDao = (HorseDao) DaoFactory.getInstance().getClass(HorseDao.class);
            List<Horse> horses = horseDao.findAll();
            return horses;
        } catch (DaoException e) {
            logger.error("Show all horses exception!", e);
            throw new ServiceException("Show all horses exception!", e);
        }
    }
}
