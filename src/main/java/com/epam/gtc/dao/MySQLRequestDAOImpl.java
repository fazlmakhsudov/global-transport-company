package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLRequestDAOImpl implements RequestDAO {
    private static final Logger LOG = Logger.getLogger(MySQLRequestDAOImpl.class);
    private final Extractor<RequestEntity> RequestExtractor = new RequestExtractor();

    // Queries
    private static final String COUNT_ALL_REQUESTS = "select count(*) from requests;";
    private static final String COUNT_REQUESTS_CONDITION_IS_STATUS = "select count(*) from requests where request_status_id = ?;";


    /**
     * Adds new request.
     *
     * @param request request to add
     * @return id entity identifyer
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final RequestEntity request) throws DAOException {
        int cond = -1;
        if (request.getId() != 0 && request.getId() > 0) {
            return 0;
        }
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__CREATE_REQUEST.getQuery(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, request.getCityFromId());
            pstmt.setInt(k++, request.getCityToId());
            pstmt.setDouble(k++, request.getWeight());
            pstmt.setDouble(k++, request.getLength());
            pstmt.setDouble(k++, request.getWidth());
            pstmt.setDouble(k++, request.getHeight());
            pstmt.setTimestamp(k++, request.getDeliveryDate());
            pstmt.setInt(k++, request.getUserId());
            pstmt.setInt(k++, request.getContentTypeId());
            pstmt.setInt(k++, request.getRequestStatusId());
            pstmt.setTimestamp(k++, request.getCreatedDate());
            pstmt.setTimestamp(k, request.getUpdatedDate());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_REQUEST, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_REQUEST, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns a Request with the given identifier.
     *
     * @param id Request identifier
     * @return Request entity object
     *
     * @throws DAOException db exception
     */
    @Override
    public RequestEntity read(int id) throws DAOException {
        RequestEntity request = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__READ_REQUEST_BY_ID.getQuery());
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                request = RequestExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_REQUEST_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_REQUEST_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return request;
    }

    /**
     * Updates request.
     *
     * @param request request to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(RequestEntity request) throws DAOException {
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__UPDATE_DELIVERY_BY_ID.getQuery());
            int k = 1;
            pstmt.setInt(k++, request.getCityFromId());
            pstmt.setInt(k++, request.getCityToId());
            pstmt.setDouble(k++, request.getWeight());
            pstmt.setDouble(k++, request.getLength());
            pstmt.setDouble(k++, request.getWidth());
            pstmt.setDouble(k++, request.getHeight());
            pstmt.setTimestamp(k++, request.getDeliveryDate());
            pstmt.setInt(k++, request.getUserId());
            pstmt.setInt(k++, request.getContentTypeId());
            pstmt.setInt(k++, request.getRequestStatusId());
            pstmt.setTimestamp(k++, request.getCreatedDate());
            pstmt.setTimestamp(k++, request.getUpdatedDate());
            pstmt.setInt(k, request.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_REQUEST);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_REQUEST, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes request
     *
     * @param id request identifier
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
            psmt = con.prepareStatement(Queries.SQL__DELETE_REQUEST_BY_ID.getQuery());
            psmt.setInt(1, id);
            psmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_DELETE_REQUEST);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_REQUEST, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all requests
     *
     * @return list of requests
     *
     * @throws DAOException exception
     */
    @Override
    public List<RequestEntity> readAll() throws DAOException {
        List<RequestEntity> requestList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Queries.SQL__READ_ALL_REQUESTS.getQuery());
            while (rs.next()) {
                requestList.add(RequestExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_REQUESTS, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_REQUESTS, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return requestList;
    }

    /**
     * Counts number of requests
     *
     * @return number of requests
     */
    @Override
    public int countAllRequests() {
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int requestsNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(COUNT_ALL_REQUESTS);
            rs.next();
            requestsNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_REQUESTS);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return requestsNumber;
    }

    /**
     * Counts requests with certain status id
     *
     * @param requestStatusId order of status
     * @return number of requests
     */
    @Override
    public int countRequests(int requestStatusId) {
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int requestsNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(COUNT_REQUESTS_CONDITION_IS_STATUS);
            psmt.setInt(1, requestStatusId);
            rs = psmt.executeQuery();
            rs.next();
            requestsNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return requestsNumber;
    }

    /**
     * Extracts RequestEntity from Resultset
     */
    private static class RequestExtractor extends Extractor<RequestEntity> {
        @Override
        public RequestEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, RequestEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_REQUEST, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_REQUEST, e);
            }
        }
    }
}
