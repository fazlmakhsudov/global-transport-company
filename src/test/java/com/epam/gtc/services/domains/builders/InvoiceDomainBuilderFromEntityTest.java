package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class InvoiceDomainBuilderFromEntityTest {
    private InvoiceDomainBuilderFromEntity domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new InvoiceDomainBuilderFromEntity();
    }

    @Test
    void create() {
        InvoiceEntity entity = getEntity();
        try {
            InvoiceDomain domain = domainBuilder.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getRequestId(), domain.getRequestId());
            assertEquals(entity.getCost(), domain.getCost());
            assertEquals(entity.getInvoiceStatusId(), domain.getInvoiceStatus().ordinal() + 1);
            assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
            assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<InvoiceEntity> entities = Arrays.asList(getEntity());
        try {
            List<InvoiceDomain> domains = domainBuilder.create(entities);
            for (InvoiceEntity entity : entities) {
                InvoiceDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getRequestId(), domain.getRequestId());
                assertEquals(entity.getCost(), domain.getCost());
                assertEquals(entity.getInvoiceStatusId(), domain.getInvoiceStatus().ordinal() + 1);
                assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
                assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private InvoiceEntity getEntity() {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(1);
        entity.setRequestId(1);
        entity.setCost(200);
        entity.setInvoiceStatusId(InvoiceStatus.PAID.ordinal() + 1);
        entity.setCreatedDate(Timestamp.from(Instant.now()));
        entity.setUpdatedDate(Timestamp.from(Instant.now()));
        return entity;
    }
}