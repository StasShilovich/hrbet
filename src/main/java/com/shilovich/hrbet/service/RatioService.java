package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;

public interface RatioService extends Service {
    List<Ratio> findRatio(Long raceId) throws ServiceException;
}
