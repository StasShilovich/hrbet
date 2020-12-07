package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Bet;
import com.shilovich.hrbet.bean.Race;
import com.shilovich.hrbet.dao.RaceDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class RaceDaoImpl extends RaceDao {
    private static final Logger logger = LogManager.getLogger(RaceDaoImpl.class);

    private static RaceDao instance;
    private static final String SHOW_ACTIVE_RACES_SQL =
            "SELECT r.id,r.location,r.time FROM races r WHERE r.time > CURRENT_TIMESTAMP order by r.time limit ? offset ?";
    private static final String SHOW_ALL_RACES_SQL =
            "SELECT r.id,r.location,r.time FROM races r order by r.time DESC limit ? offset ?";
    private static final String COUNT_ACTUAL_RACES_SQL = "SELECT count(id) FROM races WHERE time > CURRENT_TIMESTAMP";
    private static final String COUNT_ALL_RACES_SQL = "SELECT count(id) FROM races";
    private static final String ADD_RACE_SQL = "INSERT INTO races (location, time) VALUES (?, ?)";
    private static final String RACE_ID_SQL = "SELECT r.id FROM races r WHERE r.location=? AND r.time=?";
    private static final String ADD_RACE_PARTICIPANTS_SQL =
            "INSERT INTO horse_participatings (races_id, horse_id) VALUES (?,?)";
    private static final String DELETE_RACE_PARTICIPATING_SQL = "DELETE FROM horse_participatings WHERE races_id=?";
    private static final String DELETE_RACE_SQL = "DELETE FROM races WHERE id=?";
    private static final String SELECT_RACE_SQL =
            "SELECT r.location,r.time,COUNT(b.id),SUM(b.cash) FROM races r " +
                    "LEFT JOIN bets b on r.id = b.race_id WHERE r.id=?";
    private static final String ADD_RACE_RESULT_SQL = "INSERT INTO race_result(race_id,first_horse,second_horse," +
            "third_horse,fourth_horse,fifth_horse,sixth_horse) VALUES (?,?,?,?,?,?,?)";

    private RaceDaoImpl() {
    }

    public static RaceDao getInstance() {
        if (instance == null) {
            instance = new RaceDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Race> findActive(int limit, int offset) throws DaoException {
        return findAll(SHOW_ACTIVE_RACES_SQL, limit, offset);
    }

    @Override
    public List<Race> findAll(int limit, int offset) throws DaoException {
        return findAll(SHOW_ALL_RACES_SQL, limit, offset);
    }

    @Override
    public long countActual() throws DaoException {
        return count(COUNT_ACTUAL_RACES_SQL);
    }

    @Override
    public long countAll() throws DaoException {
        return count(COUNT_ALL_RACES_SQL);
    }

    @Override
    public boolean addHorse(Long raceId, Set<Long> horseSet) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(ADD_RACE_PARTICIPANTS_SQL);
            for (Long id : horseSet) {
                if (id != 0) {
                    statement.setLong(1, raceId);
                    statement.setLong(2, id);
                    statement.addBatch();
                }
            }
            statement.executeBatch();
            return true;
        } catch (SQLException e) {
            logger.error("Add horse exception!", e);
            throw new DaoException("Add horse exception!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    protected boolean addRaceResult(ProxyConnection connection, Map<Integer, Long> resultMap, Long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_RACE_RESULT_SQL);
        statement.setLong(1, raceId);
        for (int i = 1; i <= resultMap.size(); i++) {
            if (resultMap.get(i) == 0L) {
                statement.setNull(i + 1, Types.NULL);
            }
            statement.setLong(i + 1, resultMap.get(i));
        }
        int rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    @Override
    protected void deleteParticipant(ProxyConnection connection, long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_PARTICIPATING_SQL);
        statement.setLong(1, raceId);
        statement.executeUpdate();
    }

    @Override
    protected boolean delete(ProxyConnection connection, long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_SQL);
        statement.setLong(1, raceId);
        int rowsEffected = statement.executeUpdate();
        return rowsEffected > 0;
    }

    @Override
    public Optional<Race> create(Race race) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(ADD_RACE_SQL);
            statement.setString(1, race.getLocation());
            String dateTime = Timestamp.valueOf(race.getDate()).toString();
            statement.setString(2, dateTime);
            int rowsEffected = statement.executeUpdate();
            if (rowsEffected <= 0) {
                return Optional.empty();
            }
            statement = connection.prepareStatement(RACE_ID_SQL);
            statement.setString(1, race.getLocation());
            statement.setString(2, dateTime);
            set = statement.executeQuery();
            while (set.next()) {
                race.setId(set.getLong(RACE_ID));
            }
            return Optional.of(race);
        } catch (SQLException e) {
            logger.error("Create race exception!", e);
            throw new DaoException("Create race exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public Optional<Race> read(Long id) throws DaoException {
        Race race = new Race();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(SELECT_RACE_SQL);
            statement.setLong(1, id);
            set = statement.executeQuery();
            while (set.next()) {
                race.setLocation(set.getString(RACE_LOCATION));
                race.setDate(set.getTimestamp(RACE_TIME).toLocalDateTime());
                race.setBetCount(set.getLong(BET_COUNT));
                race.setBetSum(set.getBigDecimal(BET_SUM));
            }
            return Optional.of(race);
        } catch (SQLException e) {
            logger.error("Read race exception!", e);
            throw new DaoException("Read race exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
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
            logger.error("Show all races exception!", e);
            throw new DaoException("Show all races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    private long count(String sql) throws DaoException {
        long count = -1L;
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(sql);
            while (set.next()) {
                count = set.getLong(ENTITY_COUNT);
            }
            return count;
        } catch (SQLException e) {
            logger.error("Count races exception!", e);
            throw new DaoException("Count races exception!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }
}