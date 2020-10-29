package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractRaceDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class RaceServiceImpl implements RaceService {
    private static final Logger logger = LogManager.getLogger(RaceServiceImpl.class);

    @Override
    public List<Race> showAll() throws ServiceException {
        // TODO: 30.09.2020 Some logic
        try {
            AbstractRaceDao raceDao = (AbstractRaceDao) DaoFactory.getInstance().getClass(AbstractRaceDao.class);
            List<Race> races = raceDao.showAll();
            return races;
        } catch (DaoException e) {
            logger.error("Show all races exception!");
            throw new ServiceException("Show all races service exception!", e);
        }
    }

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
}
