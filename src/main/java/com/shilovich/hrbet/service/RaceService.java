package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Set;


public interface RaceService extends Service {
    List<Race> showAllActive(String page) throws ServiceException;

    List<Race> showAll(String page) throws ServiceException;

    int pageNumberActive() throws ServiceException;

    int pageNumberAll() throws ServiceException;

    boolean addRace(Set<Long> horseSet, String location, String dateTime) throws ServiceException;

    boolean delete(String raceId) throws ServiceException;

    Race findInfo(String raceId) throws ServiceException;
}