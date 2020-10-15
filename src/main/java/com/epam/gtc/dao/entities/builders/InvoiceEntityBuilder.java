package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) InvoiceEntity object from InvoiceDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceEntityBuilder extends Builder<InvoiceDomain, InvoiceEntity> {
    public InvoiceEntity create(InvoiceDomain invoice) throws BuilderException {
        return build(invoice, InvoiceEntity.class);
    }

    public List<InvoiceEntity> create(List<InvoiceDomain> invoiceList) throws BuilderException {
        List<InvoiceEntity> invoiceEntities = new ArrayList<>();
        for (InvoiceDomain invoiceDomain : invoiceList) {
            InvoiceEntity invoiceEntity = create(invoiceDomain);
            invoiceEntities.add(invoiceEntity);
        }
        return invoiceEntities;
    }
}
