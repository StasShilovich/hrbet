package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface HorseService extends Service {
    Set<Horse> showByRace(String raceId) throws ServiceException;

    List<Horse> findAll() throws ServiceException;
}
