package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.InvoiceEntity;

public interface InvoiceDAO extends BaseDAO<InvoiceEntity> {
    int countAllInvoices();

    int countInvoices(int invoiceStatusId);
}
