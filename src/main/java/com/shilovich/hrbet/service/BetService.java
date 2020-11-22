package com.shilovich.hrbet.service;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BetService extends Service {
    List<Bet> showByUser(Long userId) throws ServiceException;

    boolean add(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException;
}
