package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Set;

/**
 * The interface Horse service.
 */
public interface HorseService extends Service {
    /**
     * Show horse set by race id.
     *
     * @param raceId the race id
     * @return the set
     * @throws ServiceException the service exception
     */
    Set<Horse> showByRace(String raceId) throws ServiceException;

    /**
     * Find all horses.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Horse> findAll() throws ServiceException;
}