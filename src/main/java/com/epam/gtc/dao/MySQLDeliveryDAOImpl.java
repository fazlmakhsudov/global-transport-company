package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDeliveryDAOImpl implements DeliveryDAO {
    private static final Logger LOG = Logger.getLogger(MySQLDeliveryDAOImpl.class);
    private final Extractor<DeliveryEntity> DeliveryExtractor = new DeliveryExtractor();
    // Queries
    private static final String COUNT_ALL_DELIVERIES = "select count(*) from deliveries;";
    private static final String COUNT_DELIVERIES_CONDITION_IS_STATUS = "select count(*) from deliveries where delivery_status_id = ?;";


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
            pstmt = con.prepareStatement(Queries.SQL__CREATE_DELIVERY.getQuery(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, delivery.getDeliveryStatusId());
            pstmt.setInt(k++, delivery.getRequestId());
            pstmt.setTimestamp(k++, delivery.getCreatedDate());
            pstmt.setTimestamp(k, delivery.getUpdatedDate());
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
        DeliveryEntity delivery = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__READ_DELIVERY_BY_ID.getQuery());
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                delivery = DeliveryExtractor.extract(rs);
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
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__UPDATE_DELIVERY_BY_ID.getQuery());
            int k = 1;
            pstmt.setInt(k++, delivery.getDeliveryStatusId());
            pstmt.setInt(k++, delivery.getRequestId());
            pstmt.setTimestamp(k++, delivery.getCreatedDate());
            pstmt.setTimestamp(k++, delivery.getUpdatedDate());
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
        if (id <= 0) {
            return false;
        }
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(Queries.SQL__DELETE_DELIVERY_BY_ID.getQuery());
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
        List<DeliveryEntity> deliveryList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Queries.SQL__READ_ALL_DELIVERIES.getQuery());
            while (rs.next()) {
                deliveryList.add(DeliveryExtractor.extract(rs));
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
    public int countAllDeliveries() {
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(COUNT_ALL_DELIVERIES);
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_DELIVERIES);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return deliveriesNumber;
    }

    /**
     * Counts deliveries with certain status id
     *
     * @param deliveryStatusId order of status
     * @return number of deliverys
     */
    @Override
    public int countDeliveries(int deliveryStatusId) {
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int deliveriesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(COUNT_DELIVERIES_CONDITION_IS_STATUS);
            psmt.setInt(1, deliveryStatusId);
            rs = psmt.executeQuery();
            rs.next();
            deliveriesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION);
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
