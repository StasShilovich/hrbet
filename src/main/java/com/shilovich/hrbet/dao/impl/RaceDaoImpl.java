package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.beans.Race;
import com.shilovich.hrbet.dao.DaoFactory;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RaceDaoImpl implements RaceDao {
    private static final String SHOW_ALL_RACES_SQL =
            "SELECT id,location,time,bank_dollars FROM races;";

    @Override
    public List<Race> showAll() throws DaoException {
        List<Race> races = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            DaoFactory factory = DaoFactory.getInstance();
            connection = factory.getConnectionPool().getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(SHOW_ALL_RACES_SQL);
            while (set.next()) {
                Race race = new Race();
                // TODO: 30.09.2020 Make constants class for column names
                race.setId(set.getLong("id"));
                race.setLocation(set.getString("location"));
                Date date = new Date(set.getTimestamp("time").getTime());
                race.setDate(date);
                race.setBank(set.getLong("bank_dollars"));
                races.add(race);
            }
            return races;
        } catch (SQLException e) {
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}