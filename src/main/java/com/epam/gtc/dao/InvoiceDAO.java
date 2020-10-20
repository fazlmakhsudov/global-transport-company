package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

/**
 * Invoice DAO interface
 *
 * @author Fazliddin Makhsudov
 */
public interface InvoiceDAO extends BaseDAO<InvoiceEntity> {
    /**
     * Counts number of all invoice entities
     *
     * @return number of invoice entities
     *
     * @throws DAOException exception
     */
    int countAllInvoices() throws DAOException;

    /**
     * Counts user invoice entities
     *
     * @param userId user identifier
     * @return number of invoice entities
     *
     * @throws DAOException exception
     */
    int countUserInvoices(int userId) throws DAOException;

    /**
     * Counts invoices with given status
     *
     * @param invoiceStatusId status identifier
     * @return number of invoices
     *
     * @throws DAOException exception
     */
    int countInvoices(int invoiceStatusId) throws DAOException;

    /**
     * Reads invoices from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @return list of invoices
     *
     * @throws DAOException exception
     */
    List<InvoiceEntity> readInvoices(int offset, int limit) throws DAOException;

    /**
     * Reads user invoices from offset and limit number
     *
     * @param offset start position
     * @param limit  limit number
     * @param userId user entity identifier
     * @return list of user invoice entities
     *
     * @throws DAOException exception
     */

    List<InvoiceEntity> readInvoices(int offset, int limit, int userId) throws DAOException;

    /**
     * Counts invoice entities with certain request identifier
     *
     * @param requestId request identifier
     * @return number of invoice entities
     *
     * @throws DAOException exception
     */
    int countInvoicesOfRequest(int requestId) throws DAOException;

}
