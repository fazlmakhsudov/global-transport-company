package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.InvoiceDomain;

import java.util.List;

/**
 * Invoice service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface InvoiceService extends BaseService<InvoiceDomain> {
    /**
     * Counts number of all invoice domains
     *
     * @return number of invoice domains
     *
     * @throws ServiceException exception
     */
    int countAllInvoices() throws ServiceException;

    /**
     * Counts user invoice domains
     *
     * @param userId user identifier
     * @return number of invoice domains
     *
     * @throws ServiceException exception
     */
    int countUserInvoices(int userId) throws ServiceException;

    /**
     * Counts invoices with given status
     *
     * @param invoiceStatus status
     * @return number of invoices
     *
     * @throws ServiceException exception
     */
    int countInvoices(InvoiceStatus invoiceStatus) throws ServiceException;

    /**
     * Finds invoices from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @return list of invoices
     *
     * @throws ServiceException exception
     */
    List<InvoiceDomain> findAll(int page, int itemsPerPage) throws ServiceException;

    /**
     * Finds user invoices from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @param userId       user domain identifier
     * @return list of user invoice domains
     *
     * @throws ServiceException exception
     */
    List<InvoiceDomain> findAll(int page, int itemsPerPage, int userId) throws ServiceException;
}

