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
    private final Extractor<DistanceEntity> distanceExtractor = new DistanceExtractor();

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
        final String query = "INSERT INTO distances (from_city_id, to_city_id, distance) VALUES (?, ?, ?);";
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
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, distance.getFromCityId());
            pstmt.setInt(k++, distance.getToCityId());
            pstmt.setDouble(k, distance.getDistance());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            // opposite distance insertion
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            k = 1;
            pstmt.setInt(k++, distance.getToCityId());
            pstmt.setInt(k++, distance.getFromCityId());
            pstmt.setDouble(k, distance.getDistance());
            pstmt.executeUpdate();
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
        final String query = "SELECT * FROM distances WHERE id = ?;";
        DistanceEntity distance = null;
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
                distance = distanceExtractor.extract(rs);
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
        final String query = "UPDATE distances SET from_city_id = ?, to_city_id = ?, distance = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setInt(k++, distance.getFromCityId());
            pstmt.setInt(k++, distance.getToCityId());
            pstmt.setDouble(k++, distance.getDistance());
            pstmt.setInt(k, distance.getId());
            pstmt.executeUpdate();
            // Opposite distance updation
            DistanceEntity oppositeDistance = read(distance.getToCityId(), distance.getFromCityId());
            if (oppositeDistance.getDistance() != distance.getDistance()) {
                pstmt = con.prepareStatement(query);
                k = 1;
                pstmt.setInt(k++, distance.getToCityId());
                pstmt.setInt(k++, distance.getFromCityId());
                pstmt.setDouble(k++, distance.getDistance());
                pstmt.setInt(k, oppositeDistance.getId());
                pstmt.executeUpdate();
            }
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
        final String query = "DELETE FROM distances where id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            DistanceEntity distance = read(id);
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, id);
            psmt.executeUpdate();
            // Oppotite distance remove
            DistanceEntity oppositeDistance = read(distance.getToCityId(), distance.getFromCityId());
            psmt = con.prepareStatement(query);
            psmt.setInt(1, oppositeDistance.getId());
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
        final String query = "SELECT * FROM distances;";
        List<DistanceEntity> distanceList = new ArrayList<>();
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
                distanceList.add(distanceExtractor.extract(rs));
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
     * Counts number of distances
     *
     * @return number of distances
     */
    @Override
    public int countAllDistances() {
        final String query = "select count(*) from distances;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int distancesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            distancesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_DISTANCES);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return distancesNumber;
    }

    /**
     * Reads distances from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return
     */
    @Override
    public List<DistanceEntity> readDistances(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM distances LIMIT ?,?;";
        List<DistanceEntity> distanceList = new ArrayList<>();
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
                distanceList.add(distanceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_DISTANCES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_DISTANCES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return distanceList;
    }

    /**
     * Read distance between two cities
     *
     * @param fromCityId city from
     * @param toCityId city to
     * @return Distance entity
     */
    @Override
    public DistanceEntity read(int fromCityId, int toCityId) throws DAOException {
        final String query = "SELECT * FROM distances WHERE from_city_id = ? and to_city_id = ? LIMIT 1;";
        DistanceEntity distance = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setInt(k++, fromCityId);
            pstmt.setInt(k, toCityId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                distance = distanceExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException | DAOException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return distance;
    }
    /**
     * Reads distances less or equal given distance
     *
     * @param distance distance between two cities
     * @return list of DistanceEntities
     */
    @Override
    public List<DistanceEntity> readAll(double distance) throws DAOException {
        final String query = "SELECT * FROM distances where distance<=?;";
        List<DistanceEntity> distanceList = new ArrayList<>();
        DBManager dbm;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setDouble(1, distance);
            rs = psmt.executeQuery();
            while (rs.next()) {
                distanceList.add(distanceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException | DAOException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_DISTANCES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_DISTANCES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
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
