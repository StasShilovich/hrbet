package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Page;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.Set;


public interface RaceService extends Service {
    Page<Race> showAllActive(int limit, int offset) throws ServiceException;

    Page<Race> showAll(int limit, int offset) throws ServiceException;

    int pageNumberActive() throws ServiceException;

    int pageNumberAll() throws ServiceException;

    boolean addRace(Set<Long> horseSet, String location, String dateTime) throws ServiceException;

    boolean delete(Long raceId) throws ServiceException;

    Race findInfo(Long raceId) throws ServiceException;
}