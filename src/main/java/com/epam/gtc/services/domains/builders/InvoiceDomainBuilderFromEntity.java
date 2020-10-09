package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.builders.Builder;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDomainBuilderFromEntity extends Builder<InvoiceEntity, InvoiceDomain> {
    public InvoiceDomain create(InvoiceEntity invoice) throws BuilderException {
        return build(invoice, InvoiceDomain.class);
    }

    public List<InvoiceDomain> create(List<InvoiceEntity> invoiceList) throws BuilderException {
        List<InvoiceDomain> invoiceDomains = new ArrayList<>();
        for (InvoiceEntity invoiceEntity : invoiceList) {
            InvoiceDomain invoiceDomain = create(invoiceEntity);
            invoiceDomains.add(invoiceDomain);
        }
        return invoiceDomains;
    }
}
