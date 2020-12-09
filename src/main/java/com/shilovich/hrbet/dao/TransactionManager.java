package com.shilovich.hrbet.dao;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.Status;
import com.shilovich.hrbet.dao.pool.ConnectionManager;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionManager {
    private static final Logger logger = LogManager.getLogger(TransactionManager.class);
    private static TransactionManager instance;

    private static final ConnectionManager manager = ConnectionManager.getInstance();
    private final RaceDao raceDao = (RaceDao) DaoFactory.getInstance().getClass(RaceDao.class);
    private final BetDao betDao = (BetDao) DaoFactory.getInstance().getClass(BetDao.class);
    private final UserDao userDao = (UserDao) DaoFactory.getInstance().getClass(UserDao.class);
    private final RatioDao ratioDao = (RatioDao) DaoFactory.getInstance().getClass(RatioDao.class);

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public void enterResult(Map<Integer, Long> resultMap, Long raceId) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            boolean isAddRaceResult = raceDao.addRaceResult(connection, resultMap, raceId);
            if (!isAddRaceResult) {
                throw new SQLException("Race result not added!");
            }
            List<Bet> betByRace = betDao.findByRace(connection, raceId);
            for (Bet bet : betByRace) {
                BigDecimal cash = userDao.findCash(connection, bet.getUserId());
                boolean isWin = false;
                switch (bet.getType()) {
                    case WIN -> {
                        if (bet.getHorse().getId().equals(resultMap.get(1))) {
                            isWin = true;
                        }
                    }
                    case SHOW -> {
                        if (bet.getHorse().getId().equals(resultMap.get(1)) ||
                                bet.getHorse().getId().equals(resultMap.get(2)) ||
                                bet.getHorse().getId().equals(resultMap.get(3))) {
                            isWin = true;
                        }
                    }
                }
                BigDecimal newCash = cash;
                if (isWin) {
                    BigDecimal betWin = bet.getCash().multiply(bet.getRatio());
                    newCash = cash.add(betWin);
                }
                boolean isUpdateCash = userDao.updateCash(connection, newCash, bet.getUserId());
                if (!isUpdateCash) {
                    throw new SQLException("User cash not update!");
                }
                Status status = isWin ? Status.WIN : Status.LOSE;
                boolean isUpdateStatus = betDao.updateStatus(connection, status, bet.getId());
                if (!isUpdateStatus) {
                    throw new SQLException("Status not update!");
                }
            }
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Enter result exception!", e);
            throw new DaoException("Enter result exception!", e);
        } finally {
            close(connection);
        }
    }

    public Optional<Bet> addBet(Bet bet) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            BigDecimal cash = userDao.findCash(connection, bet.getUserId());
            if (bet.getCash().compareTo(cash) > 0) {
                return Optional.empty();
            }
            Optional<Bet> betDB = betDao.create(connection, bet);
            if (betDB.isEmpty()) {
                return Optional.empty();
            }
            BigDecimal newCash = cash.subtract(bet.getCash());
            boolean result = userDao.updateCash(connection, newCash, bet.getUserId());
            connection.commit();
            return result ? Optional.of(bet) : Optional.empty();
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Add bet exception!", e);
            throw new DaoException("Add bet exception!", e);
        } finally {
            close(connection);
        }
    }

    public boolean deleteRace(Long id) throws DaoException {
        ProxyConnection connection = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            List<Bet> bets = betDao.findByRace(connection, id);
            for (Bet bet : bets) {
                if (bet.getStatus() == Status.LOSE || bet.getStatus() == Status.WIN) {
                    return false;
                }
            }
            for (Bet bet : bets) {
                BigDecimal userCash = userDao.findCash(connection, bet.getUserId());
                BigDecimal newCash = userCash.add(bet.getCash());
                userDao.updateCash(connection, newCash, bet.getUserId());
            }
            betDao.deleteByRace(connection, id);
            ratioDao.deleteByRace(connection, id);
            raceDao.deleteParticipant(connection, id);
            boolean result = raceDao.delete(connection, id);
            connection.commit();
            return result;
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Delete race exception!", e);
            throw new DaoException("Delete race exception!", e);
        } finally {
            close(connection);
        }
    }

    private void close(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("Connection close fail!");
            }
        }
    }

    private void rollback(ProxyConnection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                logger.info("Connection rollback!");
            } catch (SQLException ex) {
                logger.error("Error while rollback!");
            }
            try {
                connection.setAutoCommit(true);
                logger.info("Set auto commit true!");
            } catch (SQLException ex) {
                logger.error("Error while set auto commit!");
            }
        }
    }
}