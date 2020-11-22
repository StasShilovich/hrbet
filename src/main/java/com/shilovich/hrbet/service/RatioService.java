package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface RatioService extends Service {
    List<Ratio> findRatio(String raceId) throws ServiceException;

    boolean addRatios(Map<String, String> parameterMap) throws ServiceException;
}
