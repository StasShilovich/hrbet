package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.BetType;
import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.HorseService;
import com.shilovich.hrbet.service.RatioService;
import com.shilovich.hrbet.service.ServiceFactory;
import com.shilovich.hrbet.validation.CommonValidator;
import com.shilovich.hrbet.validation.RatioValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class RatioServiceImpl implements RatioService {
    private static final Logger logger = LogManager.getLogger(RatioServiceImpl.class);

    private static final String MAP_KEY_DELIMITER = "\\|";
    private static final int TYPE_COUNT = 2;
    private static RatioService instance;

    private RatioServiceImpl() {
    }

    public static RatioService getInstance() {
        if (instance == null) {
            instance = new RatioServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Ratio> findRatio(String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return new ArrayList<>();
            }
            Long id = Long.valueOf(raceId);
            RatioDao ratioDao = (RatioDao) DaoFactory.getInstance().getClass(RatioDao.class);
            List<Ratio> ratios = ratioDao.findRatio(id);
            return ratios;
        } catch (DaoException e) {
            logger.error("Find ratio list fail!", e);
            throw new ServiceException("Find ratio list fail!", e);
        }
    }

    @Override
    public boolean addRatios(Map<String, String> parameterMap) throws ServiceException {
        try {
            Set<Ratio> ratioSet = new HashSet<>();
            Long raceId = 0L;
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (RatioValidator.isMapKeyValid(key) && RatioValidator.isMapValueValid(value)) {
                    String[] keys = key.split(MAP_KEY_DELIMITER);
                    raceId = Long.parseLong(keys[0]);
                    Long horseId = Long.parseLong(keys[1]);
                    // TODO: 15.11.2020 type enum?
                    Long typeId = (long) BetType.valueOf(keys[2].toUpperCase()).ordinal();
                    typeId++;
                    BigDecimal ratio = new BigDecimal(value);
                    ratioSet.add(new Ratio(raceId, horseId, typeId, ratio));
                }
            }
            if (raceId != 0L) {
                HorseService horseService = (HorseService) ServiceFactory.getInstance().getClass(HorseService.class);
                Set<Horse> horses = horseService.showByRace(raceId.toString());
                if (ratioSet.size() != horses.size() * TYPE_COUNT) {
                    return false;
                }
            }
            RatioDao ratioDao = (RatioDao) DaoFactory.getInstance().getClass(RatioDao.class);
            boolean result = ratioDao.setRatios(ratioSet);
            return result;
        } catch (DaoException e) {
            logger.error("Set ratios fail!", e);
            throw new ServiceException("Set ratios fail!", e);
        }
    }
}
