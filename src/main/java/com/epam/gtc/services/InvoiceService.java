package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.services.domains.InvoiceDomain;

import java.util.List;

public interface InvoiceService extends BaseService<InvoiceDomain> {
    int countAllInvoices();

    int countUserInvoices(int userId);

    int countInvoices(InvoiceStatus invoiceStatus);

    List<InvoiceDomain> findAll(int page, int itemsPerPage);

    List<InvoiceDomain> findAll(int page, int itemsPerPage, int userId);
}

