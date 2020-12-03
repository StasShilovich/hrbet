package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.*;
import com.shilovich.hrbet.dao.BetDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class BetDaoImpl extends BetDao {
    private static final Logger logger = LogManager.getLogger(BetDaoImpl.class);

    private static BetDao instance;
    private static final String SHOW_BET_BY_USER_SQL =
            "SELECT b.id,b.time,b.race_id,r.location,b.cash,b.ratio,t.name,b.bet_horse_id,h.name,b.is_win " +
                    " FROM bets b " +
                    " INNER JOIN races r ON b.race_id = r.id" +
                    " INNER JOIN bet_types t ON b.type_id = t.id" +
                    " INNER JOIN horses h ON b.bet_horse_id = h.id" +
                    " WHERE b.user_id = ?";
    private static final String ADD_BET_SQL = "INSERT INTO bets (user_id,time,race_id,cash,ratio,type_id,bet_horse_id) " +
            "VALUES (?,CURRENT_TIMESTAMP,?,?,?,?,?)";
    private static final String SHOW_USER_CASH_SQL = "SELECT u.cash FROM users u WHERE id=?";
    private static final String UPDATE_USER_CASH_SQL = "UPDATE users u SET u.cash=? WHERE u.id=?";
    private static final String ADD_RACE_RESULT_SQL = "INSERT INTO race_result(race_id,first_horse,_second_horse," +
            "third_horse,fourth_horse,fifth_horse,sixth_horse) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_BETS_BY_RACE_SQL = "SELECT b.id,b.user_id,b.cash,b.ratio,t.name,b.bet_horse_id " +
            "FROM bets b INNER JOIN bet_types t ON b.type_id = t.id WHERE b.race_id=?";
    private static final String UPDATE_BET_STATUS_SQL = "UPDATE bets SET is_win=? WHERE id=?";

    private BetDaoImpl() {
    }

    public static BetDao getInstance() {
        if (instance == null) {
            instance = new BetDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Bet> findByUser(Long userId) throws DaoException {
        List<Bet> bets = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SHOW_BET_BY_USER_SQL);
            statement.setString(1, userId.toString());
            set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(BET_ID);
                LocalDateTime date = set.getTimestamp(BET_TIME).toLocalDateTime();
                Race race = new Race();
                race.setId(set.getLong(BET_RACE_ID));
                race.setLocation(set.getString(RACE_LOCATION));
                BigDecimal cash = set.getBigDecimal(BET_CASH);
                BigDecimal ratio = set.getBigDecimal(BET_RATIO);
                BetType type = BetType.valueOf(set.getString(BET_TYPE_NAME).toUpperCase());
                Horse horse = new Horse();
                horse.setId(set.getLong(BET_HORSE_ID));
                horse.setName(set.getString(BET_HORSE_NAME));
                Status status = Status.values()[set.getInt(BET_STATUS)];
                bets.add(new Bet(id, userId, date, race, cash, ratio, type, horse, status));
            }
            return bets;
        } catch (SQLException e) {
            logger.error("Show all races exception!", e);
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public boolean enterResult(Map<Integer, Long> resultMap, Long raceId) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ADD_RACE_RESULT_SQL);
            statement.setLong(1, raceId);
            for (int i = 1; i <= 6; i++) {
                statement.setLong(i++, resultMap.get(i));
            }
            statement.executeUpdate();
            statement = connection.prepareStatement(SELECT_BETS_BY_RACE_SQL);
            statement.setLong(1, raceId);
            set = statement.executeQuery();
            Set<Bet> bets = new HashSet<>();
            while (set.next()) {
                Bet bet = new Bet();
                bet.setId(set.getLong(BET_ID));
                bet.setUserId(set.getLong(BET_USER_ID));
                bet.setCash(set.getBigDecimal(BET_CASH));
                bet.setRatio(set.getBigDecimal(BET_RATIO));
                bet.setType(BetType.valueOf(set.getString(BET_TYPE_NAME).toUpperCase()));
                Horse horse = new Horse();
                horse.setId(set.getLong(BET_HORSE_ID));
                bet.setHorse(horse);
                bets.add(bet);
            }
            for (Bet bet : bets) {
                statement = connection.prepareStatement(SHOW_USER_CASH_SQL);
                statement.setLong(1, bet.getUserId());
                set = statement.executeQuery();
                BigDecimal cash = BigDecimal.ZERO;
                while (set.next()) {
                    cash = set.getBigDecimal(USER_CASH);
                }
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
                statement = connection.prepareStatement(UPDATE_USER_CASH_SQL);
                statement.setBigDecimal(1, newCash);
                statement.setLong(2, bet.getUserId());
                statement.executeUpdate();

                statement = connection.prepareStatement(UPDATE_BET_STATUS_SQL);
                Status status = isWin ? Status.WIN : Status.LOSE;
                statement.setInt(1, status.ordinal());
                statement.setLong(2, bet.getId());
                statement.executeUpdate();
            }
            connection.commit();
            return false;
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Show all races exception!", e);
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<Bet> create(Bet bet) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ADD_BET_SQL);
            statement.setLong(1, bet.getUserId());
            statement.setLong(2, bet.getRace().getId());
            statement.setBigDecimal(3, bet.getCash());
            statement.setBigDecimal(4, bet.getRatio());
            statement.setLong(5, bet.getType().ordinal() + 1L);
            statement.setLong(6, bet.getHorse().getId());
            int rowsEffected = statement.executeUpdate();
            if (rowsEffected <= 0) {
                return Optional.empty();
            }
            statement = connection.prepareStatement(SHOW_USER_CASH_SQL);
            statement.setLong(1, bet.getUserId());
            set = statement.executeQuery();
            while (set.next()) {
                BigDecimal cash = set.getBigDecimal(USER_CASH);
                BigDecimal newCash = cash.subtract(bet.getCash());
                statement = connection.prepareStatement(UPDATE_USER_CASH_SQL);
                statement.setBigDecimal(1, newCash);
                statement.setLong(2, bet.getUserId());
                statement.executeUpdate();
            }
            connection.commit();
            return Optional.of(bet);
        } catch (SQLException e) {
            rollback(connection);
            logger.error("Add bet exception!", e);
            throw new DaoException("Add bet exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}