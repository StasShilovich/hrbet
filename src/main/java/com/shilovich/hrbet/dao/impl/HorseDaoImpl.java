package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Horse;
import com.shilovich.hrbet.dao.AbstractHorseDao;
import com.shilovich.hrbet.dao.connection.ConnectionManager;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.shilovich.hrbet.dao.DaoTableField.*;

public class HorseDaoImpl extends AbstractHorseDao {
    private static final Logger logger = LogManager.getLogger(HorseDaoImpl.class);
    private final ConnectionManager manager = ConnectionManager.getInstance();

    private static final String HORSE_SHOW_BY_RACE_SQL = "SELECT h.id,h.name,h.age,h.jockey FROM horse_participatings hp " +
            "INNER JOIN horses h ON hp.horse_id=h.id WHERE hp.races_id=?";

    @Override
    public Set<Horse> showByRace(Long raceId) throws DaoException {
        Set<Horse> horses = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(HORSE_SHOW_BY_RACE_SQL);
            statement.setString(1, raceId.toString());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong(HORSE_ID);
                String name = set.getString(HORSE_NAME);
                Integer age = set.getInt(HORSE_AGE);
                String jockey = set.getString(HORSE_JOCKEY);
                horses.add(new Horse(id, name, age, jockey));
            }
            return horses;
        } catch (SQLException e) {
            logger.error("Show horses by race fail!");
            throw new DaoException("Show horses by race fail!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}