package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.InvoiceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) InvoiceModel object from InvoiceDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class InvoiceModelBuilder extends Builder<InvoiceDomain, InvoiceModel> {
    public InvoiceModel create(InvoiceDomain invoice) throws BuilderException {
        return build(invoice, InvoiceModel.class);
    }


    public List<InvoiceModel> create(List<InvoiceDomain> invoiceList) throws BuilderException {
        List<InvoiceModel> invoiceModels = new ArrayList<>();
        for (InvoiceDomain invoiceDomain : invoiceList) {
            InvoiceModel invoiceModel = create(invoiceDomain);
            invoiceModels.add(invoiceModel);
        }
        return invoiceModels;
    }
}
