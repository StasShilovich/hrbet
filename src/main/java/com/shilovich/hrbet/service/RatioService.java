package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * The interface Ratio service.
 */
public interface RatioService extends Service {
    /**
     * Find ratio list by race id.
     *
     * @param raceId the race id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Ratio> findRatio(String raceId) throws ServiceException;

    /**
     * Add ratios.
     *
     * @param parameterMap the parameter map
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addRatios(Map<String, String> parameterMap) throws ServiceException;
}