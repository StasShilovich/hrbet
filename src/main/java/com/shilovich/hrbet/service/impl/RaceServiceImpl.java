package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.validation.RaceValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static com.shilovich.hrbet.cache.CacheVariables.COUNT_ACTIVE;
import static com.shilovich.hrbet.cache.CacheVariables.COUNT_ALL;
import static com.shilovich.hrbet.service.ServiceParameter.RACES_ON_PAGE;

public class RaceServiceImpl implements RaceService {
    private static final Logger logger = LogManager.getLogger(RaceServiceImpl.class);

    private static RaceService instance;

    private RaceServiceImpl() {
    }

    public static RaceService getInstance() {
        if (instance == null) {
            instance = new RaceServiceImpl();
        }
        return instance;
    }

    @Override
    public Page<Race> showAllActive(int limit, int offset) throws ServiceException {
        // TODO: 30.09.2020 Some logic
        try {
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            List<Race> races = raceDao.findActive(limit, offset);
            int pageNumber = offset / RACES_ON_PAGE;
            return new Page<>(pageNumber, races);
        } catch (DaoException e) {
            logger.error("Show all active races exception!", e);
            throw new ServiceException("Show all active races service exception!", e);
        }
    }

    @Override
    public Page<Race> showAll(int limit, int offset) throws ServiceException {
        try {
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            List<Race> races = raceDao.findAll(limit, offset);
            int pageNumber = offset / RACES_ON_PAGE;
            return new Page<>(pageNumber, races);
        } catch (DaoException e) {
            logger.error("Show all races exception!", e);
            throw new ServiceException("Show all races service exception!", e);
        }
    }

    @Override
    public int pageNumberActive() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
            if (!cache.containsKey(COUNT_ACTIVE)) {
                RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
                count = raceDao.countActual();
                cache.addCache(COUNT_ACTIVE, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / RACES_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Races count actual exception!", e);
            throw new ServiceException("Races count actual exception!", e);
        }
    }


    @Override
    public int pageNumberAll() throws ServiceException {
        try {
            long count;
            Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
            if (!cache.containsKey(COUNT_ALL)) {
                RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
                count = raceDao.countAll();
                cache.addCache(COUNT_ALL, new AtomicLong(count));
            } else {
                AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ALL);
                count = aLong.get();
            }
            return (int) Math.ceil((double) count / RACES_ON_PAGE);
        } catch (DaoException e) {
            logger.error("Races count all exception!", e);
            throw new ServiceException("Races count all exception!", e);
        }
    }

    @Override
    public boolean addRace(Set<Long> horseSet, String location, String dateTime) throws ServiceException {
        try {
            if (!RaceValidation.isValidLocation(location) || !RaceValidation.isValidDateTime(dateTime)) {
                return false;
            }
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            Race race = new Race();
            race.setLocation(location);
            race.setDate(LocalDateTime.parse(dateTime));
            Optional<Race> raceDB = raceDao.create(race);
            boolean result = raceDao.addHorse(raceDB.get().getId(), horseSet);
            if (result) {
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (!cache.isEmpty()) {
                    AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    long newLong = aLong.incrementAndGet();
                    AtomicLong newALong = new AtomicLong(newLong);
                    cache.setCacheValue(COUNT_ACTIVE, aLong, newALong);
                }
            }
            return result;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public boolean delete(Long raceId) throws ServiceException {
        try {
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            boolean result = raceDao.delete(raceId);
            if (result) {
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (!cache.isEmpty()) {
                    AtomicLong aLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    logger.error(aLong.toString());
                    long newLong = aLong.decrementAndGet();
                    AtomicLong newALong = new AtomicLong(newLong);
                    logger.error(newALong);
                    cache.setCacheValue(COUNT_ACTIVE, aLong, newALong);
                }
            }
            return result;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public Race findInfo(Long raceId) throws ServiceException {
        try {
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            Optional<Race> raceOptional = raceDao.read(raceId);
            Race race = new Race();
            if (raceOptional.isPresent()) {
                race = raceOptional.get();
            }
            return race;
        } catch (DaoException e) {
            logger.error("Find race info exception!", e);
            throw new ServiceException("Find race info exception!", e);
        }
    }
}