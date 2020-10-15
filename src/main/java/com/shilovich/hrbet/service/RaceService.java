package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.service.exception.ServiceException;

import java.util.List;

public interface RaceService extends Service {
    List<Race> showAll() throws ServiceException;
}