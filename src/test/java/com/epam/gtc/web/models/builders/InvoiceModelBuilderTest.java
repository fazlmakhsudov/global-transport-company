package com.epam.gtc.web.models.builders;

import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.web.models.InvoiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class InvoiceModelBuilderTest {
    private InvoiceModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new InvoiceModelBuilder();
    }

    @Test
    void create() {
        InvoiceDomain domain = getDomain();
        try {
            InvoiceModel model = modelBuilder.create(domain);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getRequestId(), domain.getRequestId());
            assertEquals(model.getCost(), domain.getCost());
            assertEquals(model.getInvoiceStatusName(), domain.getInvoiceStatus().getName());
            assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
            assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<InvoiceDomain> domains = Arrays.asList(getDomain());
        try {
            List<InvoiceModel> models = modelBuilder.create(domains);
            for (InvoiceDomain domain : domains) {
                InvoiceModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getRequestId(), domain.getRequestId());
                assertEquals(model.getCost(), domain.getCost());
                assertEquals(model.getInvoiceStatusName(), domain.getInvoiceStatus().getName());
                assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
                assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private InvoiceDomain getDomain() {
        InvoiceDomain domain = new InvoiceDomain();
        domain.setId(1);
        domain.setRequestId(1);
        domain.setCost(200);
        domain.setInvoiceStatus(InvoiceStatus.PAID);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());
        return domain;
    }
}