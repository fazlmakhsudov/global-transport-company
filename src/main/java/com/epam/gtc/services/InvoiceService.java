package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.services.domains.InvoiceDomain;

public interface InvoiceService extends BaseService<InvoiceDomain> {
    int countAllInvoices();

    int countInvoices(InvoiceStatus invoiceStatus);
}

