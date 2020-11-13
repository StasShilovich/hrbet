package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.RatioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RatioServiceImpl implements RatioService {
    private static final Logger logger = LogManager.getLogger(RatioServiceImpl.class);

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
    public List<Ratio> findRatio(Long raceId) throws ServiceException {
        try {
            RatioDao ratioDao = (RatioDao) DaoFactory.getInstance().getClass(RatioDao.class);
            List<Ratio> ratios = ratioDao.findRatio(raceId);
            return ratios;
        } catch (DaoException e) {
            logger.error("Find ratio list fail!", e);
            throw new ServiceException("Find ratio list fail!", e);
        }
    }
}
