package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.dao.HorseDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.dao.DaoTableField.HORSE_AGE;
import static com.shilovich.hrbet.dao.DaoTableField.HORSE_ID;
import static com.shilovich.hrbet.dao.DaoTableField.HORSE_JOCKEY;
import static com.shilovich.hrbet.dao.DaoTableField.HORSE_NAME;

public class HorseDaoImpl extends HorseDao {
    private static final Logger logger = LogManager.getLogger(HorseDaoImpl.class);

    private static HorseDao instance;
    private static final String HORSE_SHOW_BY_RACE_SQL = "SELECT h.id,h.name,h.age,h.jockey FROM horse_participatings hp " +
            "INNER JOIN horses h ON hp.horse_id=h.id WHERE hp.races_id=?";
    private static final String FIND_ALL_HORSES_SQL = "SELECT h.id,h.name,h.age,h.jockey FROM horses h";

    private HorseDaoImpl() {
    }

    public static HorseDao getInstance() {
        if (instance == null) {
            instance = new HorseDaoImpl();
        }
        return instance;
    }

    @Override
    public Set<Horse> findByRace(Long raceId) throws DaoException {
        Set<Horse> horses = new HashSet<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(HORSE_SHOW_BY_RACE_SQL);
            statement.setString(1, raceId.toString());
            set = statement.executeQuery();
            while (set.next()) {
                horses.add(createHorse(set));
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Show horses by race fail!", e);
            throw new DaoException("Show horses by race fail!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Horse> findAll() throws DaoException {
        List<Horse> horses = new ArrayList<>();
        ProxyConnection connection = null;
        Statement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.createStatement();
            set = statement.executeQuery(FIND_ALL_HORSES_SQL);
            while (set.next()) {
                horses.add(createHorse(set));
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Find all horses fail!", e);
            throw new DaoException("Find all horses fail!", e);
        } finally {
            close(set);
            close(statement);
            close(connection);
        }
    }

    private Horse createHorse(ResultSet set) throws SQLException {
        Long id = set.getLong(HORSE_ID);
        String name = set.getString(HORSE_NAME);
        Integer age = set.getInt(HORSE_AGE);
        String jockey = set.getString(HORSE_JOCKEY);
        return new Horse(id, name, age, jockey);
    }
}