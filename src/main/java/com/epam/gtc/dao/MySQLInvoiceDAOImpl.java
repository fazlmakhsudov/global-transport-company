package com.epam.gtc.dao;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.utils.DBManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLInvoiceDAOImpl implements InvoiceDAO {
    private static final Logger LOG = Logger.getLogger(MySQLInvoiceDAOImpl.class);
    private final Extractor<InvoiceEntity> invoiceExtractor = new InvoiceExtractor();
    // Queries
    private static final String COUNT_ALL_INVOICES = "select count(*) from invoices;";
    private static final String COUNT_INVOICES_CONDITION_IS_STATUS = "select count(*) from invoices where invoice_status_id = ?;";

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
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__CREATE_INVOICE.getQuery(),
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setDouble(k++, invoice.getCost());
            pstmt.setInt(k++, invoice.getInvoiceStatusId());
            pstmt.setInt(k++, invoice.getRequestId());
            pstmt.setTimestamp(k++, invoice.getCreatedDate());
            pstmt.setTimestamp(k, invoice.getUpdatedDate());
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
        InvoiceEntity invoice = null;
        DBManager dbm;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__READ_INVOICE_BY_ID.getQuery());
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
        DBManager dbm;
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            pstmt = con.prepareStatement(Queries.SQL__UPDATE_DELIVERY_BY_ID.getQuery());
            int k = 1;
            pstmt.setDouble(k++, invoice.getCost());
            pstmt.setInt(k++, invoice.getInvoiceStatusId());
            pstmt.setInt(k++, invoice.getRequestId());
            pstmt.setTimestamp(k++, invoice.getCreatedDate());
            pstmt.setTimestamp(k++, invoice.getUpdatedDate());
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
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(Queries.SQL__DELETE_INVOICE_BY_ID.getQuery());
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
        List<InvoiceEntity> invoiceList = new ArrayList<>();
        DBManager dbm;
        Statement stmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(Queries.SQL__READ_ALL_INVOICES.getQuery());
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
    public int countAllInvoices() {
        DBManager dbm;
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        int invoicesNumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            smt = con.createStatement();
            rs = smt.executeQuery(COUNT_ALL_INVOICES);
            rs.next();
            invoicesNumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_INVOICES);
        } finally {
            DBManager.close(con, smt, rs);
        }
        return invoicesNumber;
    }

    /**
     * Counts Invoices with certain status id
     *
     * @param InvoiceStatusId order
     * @return number of invoices
     */
    @Override
    public int countInvoices(int InvoiceStatusId) {
        DBManager dbm;
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        int invoicesnumber = 0;
        try {
            dbm = DBManager.getInstance();
            con = dbm.getConnection();
            psmt = con.prepareStatement(COUNT_INVOICES_CONDITION_IS_STATUS);
            psmt.setInt(1, InvoiceStatusId);
            rs = psmt.executeQuery();
            rs.next();
            invoicesnumber = rs.getInt(1);
            con.commit();
        } catch (SQLException ex) {
            DBManager.rollback(con);
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION);
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
