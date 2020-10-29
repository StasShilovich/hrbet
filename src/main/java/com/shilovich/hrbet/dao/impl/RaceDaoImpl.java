package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.AbstractRaceDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class RaceDaoImpl extends AbstractRaceDao {
    private static final Logger logger = LogManager.getLogger(RaceDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String SHOW_ALL_RACES_SQL =
            "SELECT r.id,r.location,r.time,r.bank_dollars FROM races r;";

    @Override
    public List<Race> showAll() throws DaoException {
        List<Race> races = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SHOW_ALL_RACES_SQL);
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
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<Race> read(Long id) throws DaoException {
        return Optional.empty();
    }
}