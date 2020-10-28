package com.shilovich.hrbet.service;

import com.shilovich.hrbet.beans.Bet;
import com.shilovich.hrbet.exception.ServiceException;

import java.util.List;

public interface BetService extends Service {
    List<Bet> showByUser(Long userId) throws ServiceException;
}
