package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__CREATE_RATE.getQuery(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
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
        RateEntity rate = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__READ_RATE_BY_ID.getQuery());
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
     * Updates rate.
     *
     * @param rate rate to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(RateEntity rate) throws DAOException {
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__UPDATE_DELIVERY_BY_ID.getQuery());
            int k = 1;
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
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(Queries.SQL__DELETE_RATE_BY_ID.getQuery());
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
        List<RateEntity> rateList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Queries.SQL__READ_ALL_RATES.getQuery());
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
