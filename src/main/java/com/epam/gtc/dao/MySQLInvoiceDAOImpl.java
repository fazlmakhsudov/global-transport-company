package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.dao.util.DBManager;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MySQL Invoice DAO implementation
 *
 * @author Fazliddin Makhsudov
 */
public class MySQLInvoiceDAOImpl implements InvoiceDAO {
    private static final Logger LOG = Logger.getLogger(MySQLInvoiceDAOImpl.class);
    private final Extractor<InvoiceEntity> invoiceExtractor = new InvoiceExtractor();

    /**
     * Adds new invoice.
     *
     * @param invoice invoice to add
     * @return int entity id
     *
     * @throws DAOException db exception
     */
    @Override
    public int create(final InvoiceEntity invoice) throws DAOException {
        int cond = -1;
        if (invoice.getId() != 0 && invoice.getId() > 0) {
            return 0;
        }
        final String query = "INSERT INTO invoices (cost, invoice_status_id, request_id) VALUES (?, ?, ?);";
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setDouble(k++, invoice.getCost());
            pstmt.setInt(k++, invoice.getInvoiceStatusId());
            pstmt.setInt(k, invoice.getRequestId());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                cond = rs.getInt(1);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_INSERT_INVOICE, ex);
            throw new DAOException(Messages.ERR_CANNOT_INSERT_INVOICE, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return cond;
    }

    /**
     * Returns an invoice with the given identifier.
     *
     * @param id invoice identifier
     * @return invoice entity object
     *
     * @throws DAOException db exception
     */
    @Override
    public InvoiceEntity read(int id) throws DAOException {
        final String query = "SELECT * FROM invoices WHERE id = ?;";
        InvoiceEntity invoice = null;
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
                invoice = invoiceExtractor.extract(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_OBTAIN_INVOICE_BY_ID, ex);
            throw new DAOException(Messages.ERR_CANNOT_OBTAIN_INVOICE_BY_ID, ex);
        } finally {
            DBManager.close(con, pstmt, rs);
        }
        return invoice;
    }

    /**
     * Updates invoice.
     *
     * @param invoice invoice to update.
     * @throws DAOException db exception
     */
    @Override
    public boolean update(InvoiceEntity invoice) throws DAOException {
        final String query = "UPDATE invoices SET cost = ?, invoice_status_id = ?, request_id = ? WHERE id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(query);
            int k = 1;
            pstmt.setDouble(k++, invoice.getCost());
            pstmt.setInt(k++, invoice.getInvoiceStatusId());
            pstmt.setInt(k++, invoice.getRequestId());
            pstmt.setInt(k, invoice.getId());
            pstmt.executeUpdate();
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_UPDATE_INVOICE);
            throw new DAOException(Messages.ERR_CANNOT_UPDATE_INVOICE, ex);
        } finally {
            DBManager.close(con, pstmt);
        }
        return true;
    }

    /**
     * Deletes invoice
     *
     * @param id invoice identifier
     * @return boolean operation status
     *
     * @throws DAOException exception
     */
    @Override
    public boolean delete(int id) throws DAOException {
        if (id <= 0) {
            return false;
        }
        final String query = "DELETE FROM invoices where id = ?;";
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
            LOG.error(Messages.ERR_CANNOT_DELETE_INVOICE);
            throw new DAOException(Messages.ERR_CANNOT_DELETE_INVOICE, ex);
        } finally {
            DBManager.close(con, psmt);
        }
        return true;
    }

    /**
     * Reads all invoices
     *
     * @return list of invoices
     *
     * @throws DAOException exception
     */
    @Override
    public List<InvoiceEntity> readAll() throws DAOException {
        final String query = "SELECT * FROM invoices;";
        List<InvoiceEntity> invoiceList = new ArrayList<>();
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
                invoiceList.add(invoiceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_ALL_INVOICES, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_ALL_INVOICES, ex);
        } finally {
            DBManager.close(con, stmt, rs);
        }
        return invoiceList;
    }

    /**
     * Counts number of invoices
     *
     * @return number of invoices
     */
    @Override
    public int countAllInvoices() throws DAOException {
        final String query = "select count(*) from invoices;";
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int invoicesNumber;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            rs.next();
            invoicesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_INVOICES);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_ALL_INVOICES, ex);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return invoicesNumber;
    }

    /**
     * Counts Invoices of certain user
     *
     * @param userId user id
     * @return number of invoices
     */
    @Override
    public int countUserInvoices(int userId) throws DAOException {
        final String query = "SELECT count(*) FROM invoices as i inner join requests as r on r.id=i.request_id WHERE r.user_id=?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int invoicesnumber;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, userId);
            rs = psmt.executeQuery();
            rs.next();
            invoicesnumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return invoicesnumber;
    }

    /**
     * Counts Invoices with certain status id
     *
     * @param invoiceStatusId order
     * @return number of invoices
     */
    @Override
    public int countInvoices(int invoiceStatusId) throws DAOException {
        final String query = "select count(*) from invoices where invoice_status_id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int invoicesnumber;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, invoiceStatusId);
            rs = psmt.executeQuery();
            rs.next();
            invoicesnumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return invoicesnumber;
    }

    /**
     * Reads invoices from start row till row number
     *
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of InvoiceEntities
     */
    @Override
    public List<InvoiceEntity> readInvoices(int offset, int limit) throws DAOException {
        final String query = "SELECT * FROM invoices LIMIT ?,?;";
        List<InvoiceEntity> invoiceList = new ArrayList<>();
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
                invoiceList.add(invoiceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_INVOICES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_INVOICES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return invoiceList;
    }

    /**
     * Reads invoices from start row till row number
     * of certain user
     *
     * @param userId user id
     * @param offset row from which starts reading
     * @param limit  number
     * @return list of InvoiceEntities
     */
    @Override
    public List<InvoiceEntity> readInvoices(int offset, int limit, int userId) throws DAOException {
        final String query = "SELECT i.id, i.cost, i.invoice_status_id, i.request_id, i.created_date, " +
                "i.updated_date FROM invoices as i inner join requests as r on r.id=i.request_id WHERE r.user_id=? LIMIT ?,?;";
        List<InvoiceEntity> invoiceList = new ArrayList<>();
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
                invoiceList.add(invoiceExtractor.extract(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_READ_INVOICES_WITH_LIMITATION, ex);
            throw new DAOException(Messages.ERR_CANNOT_READ_INVOICES_WITH_LIMITATION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return invoiceList;
    }

    /**
     * Counts invoices of certain request
     *
     * @param requestId request identifier
     * @return number of invoices
     *
     * @throws DAOException exception
     */
    @Override
    public int countInvoicesOfRequest(int requestId) throws DAOException {
        final String query = "select count(*) from invoices where request_id = ?;";
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int invoicesnumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(query);
            psmt.setInt(1, requestId);
            rs = psmt.executeQuery();
            rs.next();
            invoicesnumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION);
            throw new DAOException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, ex);
        } finally {
            DBManager.close(con, psmt, rs);
        }
        return invoicesnumber;
    }

    /**
     * Extracts invoiceEntity from Resultset
     */
    private static class InvoiceExtractor extends Extractor<InvoiceEntity> {
        @Override
        public InvoiceEntity extract(ResultSet rs) throws DAOException {
            try {
                return mapper(rs, InvoiceEntity.class);
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_EXTRACT_INVOICE, e);
                throw new DAOException(Messages.ERR_CANNOT_EXTRACT_INVOICE, e);
            }
        }
    }
}
