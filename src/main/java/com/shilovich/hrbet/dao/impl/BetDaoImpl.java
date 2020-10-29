package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.BetType;
import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.AbstractBetDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class BetDaoImpl extends AbstractBetDao {
    private static final Logger logger = LogManager.getLogger(BetDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String SHOW_BET_BY_USER_SQL =
            "SELECT b.id, b.status, b.time, b.race_id, r.location, b.cash_dollars, b.cash_cents, b.type_id, t.name," +
                    " b.bet_horse_id, h.name FROM bets b " +
                    "INNER JOIN races r ON b.race_id=r.id " +
                    "INNER JOIN bet_types t ON b.type_id=t.id " +
                    "INNER JOIN horses h ON  b.bet_horse_id=h.id " +
                    "WHERE b.user_id=?";

    @Override
    public List<Bet> showByUser(Long userId) throws DaoException {
        List<Bet> bets = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SHOW_BET_BY_USER_SQL);
            statement.setString(1, userId.toString());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(BET_ID);
                Boolean status = set.getBoolean(BET_STATUS);
                Date date = new Date(set.getTimestamp(BET_TIME).getTime());
                Race race = new Race();
                race.setId(set.getLong(BET_RACE_ID));
                race.setLocation(set.getString(RACE_LOCATION));
                Long dollars = set.getLong(BET_DOLLARS);
                Integer cents = set.getInt(BET_CENTS);
                BetType type = new BetType();
                type.setId(set.getLong(BET_TYPE_ID));
                type.setType(set.getString(BET_TYPE_NAME));
                Horse horse = new Horse();
                horse.setId(set.getLong(BET_HORSE_ID));
                horse.setName(set.getString(BET_HORSE_NAME));
                bets.add(new Bet(id, status, userId, date, race, dollars, cents, type, horse));
            }
            return bets;
        } catch (SQLException e) {
            logger.error("Show all races exception!");
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}