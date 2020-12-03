package com.shilovich.hrbet.service.impl;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.BetType;
import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.BetDao;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.HorseDao;
import com.shilovich.hrbet.exception.DaoException;
import com.shilovich.hrbet.exception.ServiceException;
import com.shilovich.hrbet.service.BetService;
import com.shilovich.hrbet.validation.CommonValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.shilovich.hrbet.service.ServiceParameter.MAX_HORSE_PARTICIPANTS;
import static org.apache.commons.lang3.StringUtils.*;

public class BetServiceImpl implements BetService {
    private static final Logger logger = LogManager.getLogger(BetServiceImpl.class);

    private static final String INFO_TITLE = "Ratio\\{";
    private static final String BLANK = "";
    private static final String COMMA = ",";
    private static final String EQUAL_SIGN = "=";
    private static final int INFO_ARRAY = 4;
    private static BetService instance;

    private BetServiceImpl() {
    }

    public static BetService getInstance() {
        if (instance == null) {
            instance = new BetServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Bet> showByUser(Long userId) throws ServiceException {
        try {
            BetDao betDao = (BetDao) DaoFactory.getInstance().getClass(BetDao.class);
            List<Bet> bets = betDao.findByUser(userId);
            return bets;
        } catch (DaoException e) {
            logger.error("Show by user bets exception!", e);
            throw new ServiceException("Show by user bets exception!", e);
        }
    }

    @Override
    public boolean add(String info, String betCash, BigDecimal userCash, Long userId) throws ServiceException {
        try {
//            if (!BetValidator.isInfoValid(info) || !CommonValidator.isBigDecimalValid(betCash)) {
//                return false;
//            }
            BigDecimal cash = new BigDecimal(betCash);
            if (cash.compareTo(userCash) == 1) {
                return false;
            }
            BetDao betDao = (BetDao) DaoFactory.getInstance().getClass(BetDao.class);
            String string = info.replaceAll(INFO_TITLE, BLANK).substring(0, info.length() - 1 - INFO_TITLE.length());
            String[] split = string.split(COMMA);
            String[] infoArray = new String[INFO_ARRAY];
            if (split.length == INFO_ARRAY) {
                for (int i = 0; i < split.length; i++) {
                    String str = split[i];
                    int index = str.indexOf(EQUAL_SIGN);
                    infoArray[i] = str.substring(index + 1);
                }
            }
            Bet bet = new Bet();
            bet.setUserId(userId);
            Race race = new Race();
            race.setId(Long.valueOf(infoArray[0]));
            bet.setRace(race);
            bet.setCash(cash);
            bet.setRatio(new BigDecimal(infoArray[3]));
            BetType[] values = BetType.values();
            BetType type = values[Integer.parseInt(infoArray[2]) - 1];
            bet.setType(type);
            Horse horse = new Horse();
            horse.setId(Long.valueOf(infoArray[1]));
            bet.setHorse(horse);
            Optional<Bet> betResult = betDao.create(bet);
            boolean result = false;
            if (betResult.isPresent()) {
                result = true;
            }
            return result;
        } catch (Exception e) {
            logger.error("Add bet exception!", e);
            throw new ServiceException("Add bet exception!", e);
        }
    }

    @Override
    public boolean enterResult(Map<Integer, String> horseMap, String raceId) throws ServiceException {
        try {
            if (!CommonValidator.isIdValid(raceId)) {
                return false;
            }
            HorseDao horseDao = (HorseDao) DaoFactory.getInstance().getClass(HorseDao.class);
            Long idRace = Long.valueOf(raceId);
            Set<Horse> horses = horseDao.findByRace(idRace);
            Set<Long> horsesId = horses.stream().map(Horse::getId).collect(Collectors.toSet());
            Set<Long> resultSet = new HashSet<>();
            Map<Integer, Long> resultMap = new HashMap<>();
            for (Map.Entry<Integer, String> entry : horseMap.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                if (isNotBlank(value)) {
                    if (!CommonValidator.isIdValid(value)) {
                        return false;
                    }
                    Long id = Long.valueOf(value);
                    if (horsesId.contains(id)) {
                        resultSet.add(id);
                        resultMap.put(key, id);
                    }
                } else {
                    resultMap.put(key, 0L);
                }
            }
            if (resultSet.size() != horses.size()) {
                return false;
            }
            if (resultMap.size() < MAX_HORSE_PARTICIPANTS) {
                for (int i = resultMap.size() + 1; i <= MAX_HORSE_PARTICIPANTS; i++) {
                    resultMap.put(i, 0L);
                }
            }
            BetDao betDao = (BetDao) DaoFactory.getInstance().getClass(BetDao.class);
            boolean result = betDao.enterResult(resultMap, idRace);
            return result;
        } catch (DaoException e) {
            logger.error("Enter result exception!", e);
            throw new ServiceException("Enter result exception!", e);
        }
    }
}
