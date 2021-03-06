package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.cache.Cache;
import com.shilovich.hrbet.cache.CacheFactory;
import com.shilovich.hrbet.cache.CacheType;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.TransactionManager;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.validation.CommonValidator;
import com.shilovich.hrbet.validation.RaceValidator;
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
    public List<Race> showAllActive(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()) {
                offset = (Integer.parseInt(page) - 1) * RACES_ON_PAGE;
            }
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            List<Race> races = raceDao.findActive(RACES_ON_PAGE, offset);
            return races;
        } catch (DaoException e) {
            logger.error("Show all active races exception!", e);
            throw new ServiceException("Show all active races service exception!", e);
        }
    }

    @Override
    public List<Race> showAll(String page) throws ServiceException {
        try {
            int offset = 0;
            if (page != null && !page.isEmpty()) {
                offset = (Integer.parseInt(page) - 1) * RACES_ON_PAGE;
            }
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            List<Race> races = raceDao.findAll(RACES_ON_PAGE, offset);
            return races;
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
            if (!RaceValidator.isValidLocation(location) || !RaceValidator.isValidDateTime(dateTime)) {
                return false;
            }
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            Race race = new Race();
            race.setLocation(location);
            race.setDate(LocalDateTime.parse(dateTime));
            Optional<Race> raceDB = raceDao.create(race);
            if (raceDB.isEmpty()) {
                return false;
            }
            boolean result = raceDao.addHorse(raceDB.get().getId(), horseSet);
            if (result) {
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (cache.containsKey(COUNT_ACTIVE)) {
                    AtomicLong activeLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    AtomicLong newActiveLong = new AtomicLong(activeLong.incrementAndGet());
                    cache.setCacheValue(COUNT_ACTIVE, activeLong, newActiveLong);
                } else {
                    long countActive = raceDao.countActual();
                    cache.addCache(COUNT_ACTIVE, new AtomicLong(countActive));
                }
                if (cache.containsKey(COUNT_ALL)) {
                    AtomicLong allLong = (AtomicLong) cache.getCache(COUNT_ALL);
                    AtomicLong newAllALong = new AtomicLong(allLong.incrementAndGet());
                    cache.setCacheValue(COUNT_ALL, allLong, newAllALong);
                } else {
                    long countAll = raceDao.countAll();
                    cache.addCache(COUNT_ALL, new AtomicLong(countAll));
                }
            }
            return result;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public boolean delete(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return false;
            }
            Long id = Long.valueOf(raceId);
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            boolean result = TransactionManager.getInstance().deleteRace(id);
            if (result) {
                Cache cache = (Cache) CacheFactory.getInstance().getCache(CacheType.RACES_COUNT);
                if (cache.containsKey(COUNT_ACTIVE)) {
                    AtomicLong activeLong = (AtomicLong) cache.getCache(COUNT_ACTIVE);
                    AtomicLong newActiveLong = new AtomicLong(activeLong.decrementAndGet());
                    cache.setCacheValue(COUNT_ACTIVE, activeLong, newActiveLong);
                } else {
                    long countActive = raceDao.countActual();
                    cache.addCache(COUNT_ACTIVE, new AtomicLong(countActive));
                }
                if (cache.containsKey(COUNT_ALL)) {
                    AtomicLong allLong = (AtomicLong) cache.getCache(COUNT_ALL);
                    AtomicLong newAllALong = new AtomicLong(allLong.decrementAndGet());
                    cache.setCacheValue(COUNT_ALL, allLong, newAllALong);
                } else {
                    long countAll = raceDao.countAll();
                    cache.addCache(COUNT_ALL, new AtomicLong(countAll));
                }
            }
            return result;
        } catch (DaoException e) {
            logger.error("Add race exception!", e);
            throw new ServiceException("Add race exception!", e);
        }
    }

    @Override
    public Race findInfo(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return new Race();
            }
            Long id = Long.valueOf(raceId);
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            Optional<Race> raceOptional = raceDao.read(id);
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