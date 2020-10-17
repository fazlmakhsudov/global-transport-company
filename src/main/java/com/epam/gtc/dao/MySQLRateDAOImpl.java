package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.dao.util.DBManager;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL Rate DAO implementation
 *
 * @author Fazliddin Makhsudov
 */
public class MySQLRateDAOImpl implements RateDAO {
    private static final Logger LOG = Logger.getLogger(MySQLRateDAOImpl.class);
    private final Extractor<RateEntity> RateExtractor = new RateExtractor();

    /**
     * Adds new rate.
     *
     * @param rate rate to add
     * @return int entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final RateEntity rate) throws DAOException {
        int cond = -1;
        if (rate.getId() != 0 && rate.getId() > 0) {
            return 0;
        }
        final String query = "INSERT INTO rates (name, max_weight, max_length, max_width, max_height, " +
                "max_distance, cost) VALUES (?, ?, ?, ?, ?, ?, ?);";
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, rate.getName());
            pstmt.setDouble(k++, rate.getMaxWeight());
            pstmt.setDouble(k++, rate.getMaxLength());
            pstmt.setDouble(k++, rate.getMaxWidth());
            pstmt.setDouble(k++, rate.getMaxHeight());
            pstmt.setDouble(k++, rate.getMaxDistance());
            pstmt.setDouble(k, rate.getCost());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_RATE, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_RATE, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a rate with the given identifier.
     *
     * @param id rate identifier
     * @return Rate object
     *
     * @throws DAOException db exception
     */
    @Override
    public RateEntity read(int id) throws DAOException {
        final String query = "SELECT * FROM rates WHERE id = ?;";
        RateEntity rate = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rate = RateExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_RATE_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_RATE_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return rate;
    }

    /**
     * Returns a rate with the given name.
     *
     * @param name rate name
     * @return Rate object
     *
     * @throws DAOException db exception
     */
    @Override
    public RateEntity read(String name) throws DAOException {
        final String query = "SELECT * FROM rates WHERE name = ?;";
        RateEntity rate = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rate = RateExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_RATE_BY_NAME, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_RATE_BY_NAME, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return rate;
    }

    /**
     * Updates rate.
     *
     * @param rate rate to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(RateEntity rate) throws DAOException {
        final String query = "UPDATE rates SET name = ?, max_weight = ?, max_length = ?, max_width = ?, max_height = ?, " +
                "max_distance = ?, cost = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setString(k++, rate.getName());
            pstmt.setDouble(k++, rate.getMaxWeight());
            pstmt.setDouble(k++, rate.getMaxLength());
            pstmt.setDouble(k++, rate.getMaxWidth());
            pstmt.setDouble(k++, rate.getMaxHeight());
            pstmt.setDouble(k++, rate.getMaxDistance());
            pstmt.setDouble(k++, rate.getCost());
            pstmt.setInt(k, rate.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_RATE);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_RATE, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes rate
     *
     * @param id rate identifier
     * @return boolean operation status
     *
     * @throws DAOException exception
     */
    @Override
    public boolean delete(int id) throws DAOException {
        if (id <= 0) {
            return false;
        }
        final String query = "DELETE FROM rates where id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, id);
            psmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_DELETE_RATE);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_RATE, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all rates
     *
     * @return list of Rates
     *
     * @throws DAOException exception
     */
    @Override
    public List<RateEntity> readAll() throws DAOException {
        final String query = "SELECT * FROM rates;";
        List<RateEntity> rateList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                rateList.add(RateExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_RATES, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_RATES, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return rateList;
    }

    /**
     * Counts number of rates
     *
     * @return number of rates
     */
    @Override
    public int countAllRates() throws DAOException {
        final String query = "select count(*) from rates;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int ratesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            ratesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_RATES);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_ALL_RATES, ex);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return ratesNumber;
    }

    /**
     * Reads rates from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of RateEntities
     */
    @Override
    public List<RateEntity> readRates(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM rates LIMIT ?,?;";
        List<RateEntity> rateList = new ArrayList<>();
        DBManager dbm;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, offset);
            psmt.setInt(2, limit);
            rs = psmt.executeQuery();
            while (rs.next()) {
                rateList.add(RateExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_RATES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_RATES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return rateList;
    }

    /**
     * Reads all rates with distance less given
     * @param maxDistance distance
     * @return list of rate entities
     * @throws DAOException exception
     */
    @Override
    public List<RateEntity> readAll(double maxDistance) throws DAOException {
        final String query = "SELECT * FROM rates where max_distance<=?;";
        List<RateEntity> rateList = new ArrayList<>();
        DBManager dbm;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setDouble(1, maxDistance);
            rs = psmt.executeQuery();
            while (rs.next()) {
                rateList.add(RateExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_RATES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_RATES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return rateList;
    }

    /**
     * Extracts RateEntity from Resultset
     */
    private static class RateExtractor extends Extractor<RateEntity> {
        @Override
        public RateEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, RateEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_RATE, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_RATE, e);
            }
        }
    }
}
