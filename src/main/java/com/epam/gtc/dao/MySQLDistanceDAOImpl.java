package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDistanceDAOImpl implements DistanceDAO {
    private static final Logger LOG = Logger.getLogger(MySQLDistanceDAOImpl.class);
    private final Extractor<DistanceEntity> DistanceExtractor = new DistanceExtractor();

    /**
     * Adds new distance.
     *
     * @param distance distance to add
     * @return int entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final DistanceEntity distance) throws DAOException {
        int cond = -1;
        if (distance.getId() != 0 && distance.getId() > 0) {
            return 0;
        }
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__CREATE_DISTANCE.getQuery(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, distance.getFromCityId());
            pstmt.setInt(k++, distance.getToCityId());
            pstmt.setDouble(k, distance.getDistance());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_DISTANCE, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_DISTANCE, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a distance with the given identifier.
     *
     * @param id distance identifier
     * @return Distance
     *
     * @throws DAOException db exception
     */
    @Override
    public DistanceEntity read(int id) throws DAOException {
        DistanceEntity distance = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__READ_DISTANCE_BY_ID.getQuery());
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                distance = DistanceExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DISTANCE_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_DISTANCE_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return distance;
    }

    /**
     * Updates distance.
     *
     * @param distance distance to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(DistanceEntity distance) throws DAOException {
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__UPDATE_DELIVERY_BY_ID.getQuery());
            int k = 1;
            pstmt.setInt(k++, distance.getFromCityId());
            pstmt.setInt(k++, distance.getToCityId());
            pstmt.setDouble(k++, distance.getDistance());
            pstmt.setInt(k, distance.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_DISTANCE);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_DISTANCE, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes distance
     *
     * @param id distance identifier
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
            psmt = con.prepareStatement(Queries.SQL__DELETE_DISTANCE_BY_ID.getQuery());
            psmt.setInt(1, id);
            psmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_DELETE_DISTANCE);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_DISTANCE, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all distances
     *
     * @return list of distances
     *
     * @throws DAOException exception
     */
    @Override
    public List<DistanceEntity> readAll() throws DAOException {
        List<DistanceEntity> distanceList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Queries.SQL__READ_ALL_DISTANCES.getQuery());
            while (rs.next()) {
                distanceList.add(DistanceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_DISTANCES, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_DISTANCES, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return distanceList;
    }

    /**
     * Extracts DistanceEntity from Resultset
     */
    private static class DistanceExtractor extends Extractor<DistanceEntity> {
        @Override
        public DistanceEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, DistanceEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_DISTANCE, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_DISTANCE, e);
            }
        }
    }
}
