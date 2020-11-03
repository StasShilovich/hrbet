package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
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
import java.util.concurrent.atomic.AtomicLong;

import static com.shilovich.hrbet.cache.CacheVariables.RACE_COUNT;
import static com.shilovich.hrbet.service.ServiceParameter.ROW_ON_PAGE;

public class RaceServiceImpl implements RaceService {
    private static final Logger logger = LogManager.getLogger(RaceServiceImpl.class);

    @Override
    public Page<Race> showAll(int limit, int offset) throws ServiceException {
        // TODO: 30.09.2020 Some logic
        try {
            AbstractRaceDao raceDao = (AbstractRaceDao) DaoFactory.getInstance().getClass(AbstractRaceDao.class);
            List<Race> races = raceDao.showAll(limit, offset);
            int pageNumber = offset / ROW_ON_PAGE;
            return new Page<>(pageNumber, races);
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

    @Override
    public int getRacesPagesCount() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
            if (cache.isEmpty()) {
                AbstractRaceDao raceDao = (AbstractRaceDao) DaoFactory.getInstance().getClass(AbstractRaceDao.class);
                count = raceDao.count();
                cache.addCache(RACE_COUNT, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(RACE_COUNT);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / ROW_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Races count exception!");
            throw new ServiceException("Races count exception!", e);
        }
    }
}
