package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.InvoiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) InvoiceDomain object from InvoiceModel object
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceDomainBuilderFromModel extends Builder<InvoiceModel, InvoiceDomain> {
    public InvoiceDomain create(InvoiceModel invoice) throws BuilderException {
        return build(invoice, InvoiceDomain.class);
    }

    public List<InvoiceDomain> create(List<InvoiceModel> invoiceList) throws BuilderException {
        List<InvoiceDomain> invoiceDomains = new ArrayList<>();
        for (InvoiceModel invoicemodel : invoiceList) {
            InvoiceDomain invoiceDomain = create(invoicemodel);
            invoiceDomains.add(invoiceDomain);
        }
        return invoiceDomains;
    }
}
