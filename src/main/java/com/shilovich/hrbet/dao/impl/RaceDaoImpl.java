package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.AbstractRaceDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.dao.connection.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class RaceDaoImpl extends AbstractRaceDao {
    private static final Logger logger = LogManager.getLogger(RaceDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String SHOW_ACTIVE_RACES_SQL =
            "SELECT r.id,r.location,r.time FROM races r WHERE r.time > CURRENT_TIMESTAMP order by r.time limit ? offset ?";
    private static final String SHOW_ALL_RACES_SQL =
            "SELECT r.id,r.location,r.time FROM races r order by r.time limit ? offset ?";
    private static final String COUNT_RACES_SQL = "SELECT count(id) FROM races WHERE time > CURRENT_TIMESTAMP";

    @Override
    public List<Race> showAllActive(int limit, int offset) throws DaoException {
        return findAll(SHOW_ACTIVE_RACES_SQL, limit, offset);
    }

    @Override
    public List<Race> showAll(int limit, int offset) throws DaoException {
        return findAll(SHOW_ALL_RACES_SQL, limit, offset);
    }

    @Override
    public long count() throws DaoException {
        long count = -1L;
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(COUNT_RACES_SQL);
            while (set.next()) {
                count = set.getLong(ENTITY_COUNT);
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

    private List<Race> findAll(String sql, int limit, int offset) throws DaoException {
        List<Race> races = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(RACE_ID);
                String location = set.getString(RACE_LOCATION);
                LocalDateTime date = set.getTimestamp(RACE_TIME).toLocalDateTime();
                races.add(new Race(id, location, date));
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
}