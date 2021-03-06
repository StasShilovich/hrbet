package com.shilovich.hrbet.dao.impl;

import com.shilovich.hrbet.bean.Ratio;
import com.shilovich.hrbet.dao.RatioDao;
import com.shilovich.hrbet.dao.pool.ProxyConnection;
import com.shilovich.hrbet.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.shilovich.hrbet.dao.DaoTableField.RATIO_HORSE_ID;
import static com.shilovich.hrbet.dao.DaoTableField.RATIO_RATIO;
import static com.shilovich.hrbet.dao.DaoTableField.RATIO_TYPE_ID;

public class RatioDaoImpl extends RatioDao {
    private static final Logger logger = LogManager.getLogger(RatioDaoImpl.class);

    private static RatioDao instance;
    private static final String ADD_RATIO_SQL =
            "INSERT INTO ratio(race_id, horse_id, type_id, ratio) VALUES (?,?,?,?)";
    private static final String FIND_RATIO_LIST_SQL =
            "SELECT r.horse_id,r.type_id,r.ratio FROM ratio r WHERE r.race_id=?";
    private static final String DELETE_RACE_RATIO_SQL = "DELETE FROM ratio WHERE race_id=?";

    private RatioDaoImpl() {
    }

    public static RatioDao getInstance() {
        if (instance == null) {
            instance = new RatioDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean setRatios(Set<Ratio> ratioSet) throws DaoException {
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(ADD_RATIO_SQL);
            for (Ratio ratio : ratioSet) {
                statement.setLong(1, ratio.getRaceId());
                statement.setLong(2, ratio.getHorseId());
                statement.setLong(3, ratio.getTypeId());
                statement.setBigDecimal(4, ratio.getRatio());
                statement.addBatch();
            }
            int[] executeBatch = statement.executeBatch();
            return executeBatch.length > 0;
        } catch (SQLException e) {
            logger.error("Set ratios error!", e);
            throw new DaoException("Set ratios error!", e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Ratio> findRatio(Long raceId) throws DaoException {
        List<Ratio> ratioList = new ArrayList<>();
        ProxyConnection connection = null;
        PreparedStatement statement = null;
        ResultSet set = null;
        try {
            connection = manager.getConnection();
            statement = connection.prepareStatement(FIND_RATIO_LIST_SQL);
            statement.setLong(1, raceId);
            set = statement.executeQuery();
            while (set.next()) {
                Long horseId = set.getLong(RATIO_HORSE_ID);
                Long typeId = set.getLong(RATIO_TYPE_ID);
                BigDecimal ratio = set.getBigDecimal(RATIO_RATIO);
                ratioList.add(new Ratio(raceId, horseId, typeId, ratio));
            }
            return ratioList;
        } catch (SQLException e) {
            logger.error("Find ratios error!", e);
            throw new DaoException("Find ratios error!", e);
        }
    }

    @Override
    protected void deleteByRace(ProxyConnection connection, long raceId) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_RACE_RATIO_SQL);
        statement.setLong(1, raceId);
        statement.executeUpdate();
    }
}