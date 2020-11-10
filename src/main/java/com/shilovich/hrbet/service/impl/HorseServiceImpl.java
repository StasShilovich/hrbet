package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class HorseServiceImpl implements HorseService {
    private static final Logger logger = LogManager.getLogger(HorseServiceImpl.class);

    @Override
    public Set<Horse> showByRace(Long raceId) throws ServiceException {
        try {
            AbstractHorseDao horseDao = (AbstractHorseDao) DaoFactory.getInstance().getClass(AbstractHorseDao.class);
            Set<Horse> horses = horseDao.showByRace(raceId);
            return horses;
        } catch (DaoException e) {
            logger.error("Show horses by race exception!");
            throw new ServiceException("Show horses by race exception!", e);
        }
    }

    @Override
    public List<Horse> findAll() throws ServiceException {
        try {
            AbstractHorseDao horseDao = (AbstractHorseDao) DaoFactory.getInstance().getClass(AbstractHorseDao.class);
            List<Horse> horses = horseDao.findAll();
            return horses;
        } catch (DaoException e) {
            logger.error("Show all horses exception!");
            throw new ServiceException("Show all horses exception!", e);
        }
    }
}
