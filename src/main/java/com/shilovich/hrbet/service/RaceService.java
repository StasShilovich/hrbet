package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.Set;

public interface RaceService extends Service {
    Page<Race> showAll(int limit, int offset) throws ServiceException;

    Set<Horse> showByRace(Long raceId) throws ServiceException;

    int getRacesPagesCount() throws ServiceException;
}