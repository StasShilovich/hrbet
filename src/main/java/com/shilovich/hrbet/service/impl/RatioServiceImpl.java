package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.AbstractRatioDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.RatioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RatioServiceImpl implements RatioService {
    private static final Logger logger = LogManager.getLogger(RatioServiceImpl.class);

    @Override
    public List<Ratio> findRatio(Long raceId) throws ServiceException {
        try {
            AbstractRatioDao ratioDao = (AbstractRatioDao) DaoFactory.getInstance().getClass(AbstractRatioDao.class);
            List<Ratio> ratios = ratioDao.findRatio(raceId);
            return ratios;
        } catch (DaoException e) {
            logger.error("Find ratio list fail!");
            throw new ServiceException("Find ratio list fail!", e);
        }
    }
}
