package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.dao.util.DBManager;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL Delivery DAO implementation
 *
 * @author Fazliddin Makhsudov
 */
public class MySQLDeliveryDAOImpl implements DeliveryDAO {
    private static final Logger LOG = Logger.getLogger(MySQLDeliveryDAOImpl.class);
    private final Extractor<DeliveryEntity> deliveryExtractor = new DeliveryExtractor();

    /**
     * Adds new delivery.
     *
     * @param delivery delivery to add
     * @return int entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final DeliveryEntity delivery) throws DAOException {
        final String query = "INSERT INTO deliveries (delivery_status_id, request_id) VALUES (?, ?);";
        int cond = -1;
        if (delivery.getId() != 0 && delivery.getId() > 0) {
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
            pstmt.setInt(k++, delivery.getDeliveryStatusId());
            pstmt.setInt(k, delivery.getRequestId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_DELIVERY, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_DELIVERY, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a delivery with the given identifier.
     *
     * @param id delivery identifier
     * @return delivery object
     *
     * @throws DAOException db exception
     */
    @Override
    public DeliveryEntity read(int id) throws DAOException {
        final String query = "SELECT * FROM deliveries WHERE id = ?;";
        DeliveryEntity delivery = null;
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
                delivery = deliveryExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DELIVERY_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_DELIVERY_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return delivery;
    }

    /**
     * Updates delivery.
     *
     * @param delivery delivery to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(DeliveryEntity delivery) throws DAOException {
        final String query = "UPDATE deliveries SET delivery_status_id = ?, request_id = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setInt(k++, delivery.getDeliveryStatusId());
            pstmt.setInt(k++, delivery.getRequestId());
            pstmt.setInt(k, delivery.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_DELIVERY);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_DELIVERY, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes delivery
     *
     * @param id delivery identifier
     * @return boolean operation status
     *
     * @throws DAOException exception
     */
    @Override
    public boolean delete(int id) throws DAOException {
        final String query = "DELETE FROM deliveries where id = ?;";
        if (id <= 0) {
            return false;
        }
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
            LOG.error(Messages.ERR_CANNOT_DELETE_DELIVERY);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_DELIVERY, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all deliverys
     *
     * @return list of deliverys
     *
     * @throws DAOException exception
     */
    @Override
    public List<DeliveryEntity> readAll() throws DAOException {
        final String query = "SELECT * FROM deliveries;";
        List<DeliveryEntity> deliveryList = new ArrayList<>();
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
                deliveryList.add(deliveryExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_DELIVERIES, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_DELIVERIES, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return deliveryList;
    }

    /**
     * Counts number of deliveries
     *
     * @return number of deliverys
     */
    @Override
    public int countAllDeliveries() throws DAOException {
        final String query = "select count(*) from deliveries;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_DELIVERIES);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_ALL_DELIVERIES, ex);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return deliveriesNumber;
    }

    /**
     * Counts user deliveries
     *
     * @param userId user id
     * @return number of deliveries
     */
    @Override
    public int countUserDeliveries(int userId) throws DAOException {
        final String query = "SELECT count(*) FROM deliveries as d inner join requests as r on r.id=d.request_id WHERE r.user_id=?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, userId);
            rs = psmt.executeQuery();
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return deliveriesNumber;
    }

    /**
     * Counts deliveries with certain status id
     *
     * @param deliveryStatusId order of status
     * @return number of deliveries
     */
    @Override
    public int countDeliveries(int deliveryStatusId) throws DAOException {
        final String query = "select count(*) from deliveries where delivery_status_id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, deliveryStatusId);
            rs = psmt.executeQuery();
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return deliveriesNumber;
    }

    /**
     * Reads d  deliveries from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of DeliveryEntities
     */
    @Override
    public List<DeliveryEntity> readDeliveries(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM deliveries LIMIT ?,?;";
        List<DeliveryEntity> deliveryList = new ArrayList<>();
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
                deliveryList.add(deliveryExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_DELIVERIES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_DELIVERIES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return deliveryList;
    }

    /**
     * Reads d  deliveries from start row till row number
     * of certain user
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @param userId user id
     * @return list of DeliveryEntities
     */
    @Override
    public List<DeliveryEntity> readDeliveries(int offset, int limit, int userId) throws DAOException {
        final String query = "SELECT d.id, d.delivery_status_id, d.request_id, d.created_date, " +
                "d.updated_date FROM deliveries as d inner join requests as r on r.id=d.request_id WHERE r.user_id=? LIMIT ?,?;";
        List<DeliveryEntity> deliveryList = new ArrayList<>();
        DBManager dbm;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            int k = 1;
            psmt.setInt(k++, userId);
            psmt.setInt(k++, offset);
            psmt.setInt(k, limit);
            rs = psmt.executeQuery();
            while (rs.next()) {
                deliveryList.add(deliveryExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_DELIVERIES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_DELIVERIES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return deliveryList;
    }
    /**
     * Counts deliveries of certain request
     *
     * @param requestId request
     * @return number of deliveries
     */
    @Override
    public int countDeliveriesOfRequest(int requestId) throws DAOException {
        final String query = "select count(*) from deliveries where request_id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, requestId);
            rs = psmt.executeQuery();
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return deliveriesNumber;
    }

    /**
     * Extracts DeliveryEntity from Resultset
     */
    private static class DeliveryExtractor extends Extractor<DeliveryEntity> {
        @Override
        public DeliveryEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, DeliveryEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_DELIVERY, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_DELIVERY, e);
            }
        }
    }
}
