package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.exception.DaoException;
import com.shilovich.hrbet.service.RaceService;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.util.List;

public class RaceServiceImpl extends RaceService {
    @Override
    public List<Race> showAll() throws ServiceException {
        // TODO: 30.09.2020 Some logic
        try {
            RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
            List<Race> races = raceDao.showAll();
            return races;
        } catch (DaoException e) {
            throw new ServiceException("Show all races service exception!", e);
        }
    }
}
