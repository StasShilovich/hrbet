package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.AbstractRaceDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
            logger.debug("Show all races exception!");
            throw new ServiceException("Show all races service exception!", e);
        }
    }
}
