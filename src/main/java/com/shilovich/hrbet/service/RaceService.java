package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.Horse;
import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface RaceService extends Service {
    List<Race> showAll() throws ServiceException;

    Set<Horse> showByRace(Long raceId) throws ServiceException;
}