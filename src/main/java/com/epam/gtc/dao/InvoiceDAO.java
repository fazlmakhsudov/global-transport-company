package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface InvoiceDAO extends BaseDAO<InvoiceEntity> {
    int countAllInvoices();

    int countUserInvoices(int userId);

    int countInvoices(int invoiceStatusId);

    List<InvoiceEntity> readInvoices(int offset, int limit) throws DAOException;

    List<InvoiceEntity> readInvoices(int offset, int limit, int userId) throws DAOException;
}
