package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.AbstractRaceDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class RaceDaoImpl extends AbstractRaceDao {
    private static final Logger logger = LogManager.getLogger(RaceDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String SHOW_RACES_SQL =
            "SELECT r.id,r.location,r.time,r.bank_dollars FROM races r WHERE r.time > CURRENT_TIMESTAMP order by r.time limit ? offset ?";
    private static final String COUNT_RACES_SQL = "SELECT count(id) FROM races WHERE time > CURRENT_TIMESTAMP";

    @Override
    public List<Race> showAll(int limit, int offset) throws DaoException {
        List<Race> races = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SHOW_RACES_SQL);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            set = statement.executeQuery();
            while (set.next()) {
                Race race = new Race();
                race.setId(set.getLong(RACE_ID));
                race.setLocation(set.getString(RACE_LOCATION));
                Date date = new Date(set.getTimestamp(RACE_TIME).getTime());
                race.setDate(date);
                race.setBank(set.getLong(RACE_BANK_DOLLARS));
                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            logger.error("Show all races exception!");
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public long count() throws DaoException {
        long count = -1L;
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(COUNT_RACES_SQL);
            while (set.next()) {
                count = set.getLong(RACE_COUNT);
            }
            return count;
        } catch (SQLException e) {
            logger.error("Count races exception!");
            throw new DaoException("Count races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<Race> read(Long id) throws DaoException {
        return Optional.empty();
    }
}