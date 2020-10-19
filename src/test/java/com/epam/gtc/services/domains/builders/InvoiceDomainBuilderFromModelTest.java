package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.web.models.InvoiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class InvoiceDomainBuilderFromModelTest {
    private InvoiceDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new InvoiceDomainBuilderFromModel();
    }

    @Test
    void create() {
        InvoiceModel model = getModel();
        try {
            InvoiceDomain domain = domainBuilder.create(model);
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
        List<InvoiceModel> models = Arrays.asList(getModel());
        try {
            List<InvoiceDomain> domains = domainBuilder.create(models);
            for (InvoiceModel model : models) {
                InvoiceDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
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

    private InvoiceModel getModel() {
        InvoiceModel model = new InvoiceModel();
        model.setId(1);
        model.setRequestId(1);
        model.setCost(200);
        model.setInvoiceStatusName(InvoiceStatus.REJECTED.getName());
        model.setCreatedDate(Timestamp.from(Instant.now()));
        model.setUpdatedDate(Timestamp.from(Instant.now()));
        return model;
    }
}