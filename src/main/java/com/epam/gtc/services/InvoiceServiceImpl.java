package com.epam.gtc.services;


import com.epam.gtc.dao.InvoiceDAO;
import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.Builder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Invoice service implementation
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceDAO invoiceDAO;
    private static final Logger LOG = Logger.getLogger(InvoiceServiceImpl.class);
    private final Builder<InvoiceDomain, InvoiceEntity> invoiceEntitybuilder;
    private final Builder<InvoiceEntity, InvoiceDomain> invoiceDomainBuilder;

    public InvoiceServiceImpl(InvoiceDAO invoiceDAO, Builder<InvoiceDomain, InvoiceEntity> invoiceEntitybuilder,
                              Builder<InvoiceEntity, InvoiceDomain> invoiceDomainBuilder) {
        this.invoiceDAO = invoiceDAO;
        this.invoiceEntitybuilder = invoiceEntitybuilder;
        this.invoiceDomainBuilder = invoiceDomainBuilder;
    }

    @Override
    public int add(InvoiceDomain invoice) throws ServiceException {
        int inserted;
        try {
            inserted = invoiceDAO.create(invoiceEntitybuilder.create(invoice));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_INVOICE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_INVOICE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
        return inserted;
    }

    @Override
    public InvoiceDomain find(int id) throws ServiceException {
        InvoiceEntity invoice;
        try {
            invoice = invoiceDAO.read(id);
            return invoiceDomainBuilder.create(invoice);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_INVOICE_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_INVOICE_BY_ID, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
    }

    @Override
    public boolean save(InvoiceDomain Invoice) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = invoiceDAO.update(invoiceEntitybuilder.create(Invoice));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_INVOICE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_INVOICE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = invoiceDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_INVOICE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_INVOICE, e);
        }
        return deletedFlag;
    }

    @Override
    public List<InvoiceDomain> findAll() throws ServiceException {
        List<InvoiceEntity> invoiceList;
        try {
            invoiceList = invoiceDAO.readAll();
            return invoiceDomainBuilder.create(invoiceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_INVOICES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_INVOICES, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
    }

    @Override
    public int countAllInvoices() throws ServiceException {
        try {
            return invoiceDAO.countAllInvoices();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_INVOICES, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_ALL_INVOICES, e);
        }
    }

    @Override
    public int countUserInvoices(int userId) throws ServiceException {
        try {
            return invoiceDAO.countUserInvoices(userId);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
        }
    }

    @Override
    public int countInvoices(InvoiceStatus invoiceStatus) throws ServiceException {
        try {
            return invoiceDAO.countInvoices(InvoiceStatus.getId(invoiceStatus));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
        }
    }

    @Override
    public List<InvoiceDomain> findAll(int page, int itemsPerPage) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<InvoiceEntity> invoiceList;
        try {
            invoiceList = invoiceDAO.readInvoices(offset, itemsPerPage);
            return invoiceDomainBuilder.create(invoiceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_INVOICES_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_INVOICES_WITH_LIMITATION, e);

        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
    }

    @Override
    public List<InvoiceDomain> findAll(int page, int itemsPerPage, int userId) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<InvoiceEntity> invoiceList;
        try {
            invoiceList = invoiceDAO.readInvoices(offset, itemsPerPage, userId);
            return invoiceDomainBuilder.create(invoiceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_INVOICES_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_INVOICES_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_INVOICE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_INVOICE, e);
        }
    }

    @Override
    public int countDeliveriesOfRequest(int requestId) throws ServiceException {
        try {
            return invoiceDAO.countInvoicesOfRequest(requestId);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION, e);
        }
    }

}
