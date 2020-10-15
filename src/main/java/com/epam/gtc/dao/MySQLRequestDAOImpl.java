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
        final String query = "INSERT INTO requests (city_from_id, city_to_id, weight, length, width, height," +
                " delivery_date, user_id, content_type_id, request_status_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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
            pstmt.setInt(k, request.getRequestStatusId());
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
        final String query = "SELECT * FROM requests WHERE id = ?;";
        RequestEntity request = null;
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
        final String query = "UPDATE requests SET city_from_id = ?, city_to_id = ?, weight = ?, length = ?, width = ?, " +
                "height = ?, delivery_date = ?, user_id = ?, content_type_id = ?, request_status_id = ? " +
                "WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
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
        final String query = "DELETE FROM requests where id = ?;";
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
        final String query = "SELECT * FROM requests;";
        List<RequestEntity> requestList = new ArrayList<>();
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
        final String query = "select count(*) from requests;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int requestsNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
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
     * Count all user requests
     *
     * @param userId user id
     * @return number of requests
     */

    @Override
    public int countUserRequests(int userId) {
        final String query = "select count(*) from requests where user_id=?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int requestsNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, userId);
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
     * Counts requests with certain status id
     *
     * @param requestStatusId order of status
     * @return number of requests
     */
    @Override
    public int countRequests(int requestStatusId) {
        final String query = "select count(*) from requests where request_status_id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int requestsNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
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
     * Reads requests from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of RequestEntities
     */
    @Override
    public List<RequestEntity> readRequests(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM requests LIMIT ?,?;";
        List<RequestEntity> requestList = new ArrayList<>();
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
                requestList.add(RequestExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_REQUESTS_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_REQUESTS_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return requestList;
    }

    /**
     * Reads requests from start row till row number
     * of certain user
     *
     * @param userId user id
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of RequestEntities
     */
    @Override
    public List<RequestEntity> readRequests(int offset, int limit, int userId) throws DAOException {
        final String query = "SELECT * FROM requests where user_id=? LIMIT ?,?;";
        List<RequestEntity> requestList = new ArrayList<>();
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
                requestList.add(RequestExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_REQUESTS_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_REQUESTS_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return requestList;
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
