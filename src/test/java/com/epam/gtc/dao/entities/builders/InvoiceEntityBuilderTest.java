package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.InvoiceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceEntityBuilderTest {
    private InvoiceEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new InvoiceEntityBuilder();
    }

    @Test
    void create() {
        InvoiceDomain domain = getDomain();
        try {
            InvoiceEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getRequestId(), entity.getRequestId());
            assertEquals(domain.getCost(), entity.getCost());
            assertEquals(domain.getInvoiceStatus().ordinal() + 1, entity.getInvoiceStatusId());
            assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
            assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<InvoiceDomain> domains = Arrays.asList(getDomain());
        try {
            List<InvoiceEntity> entities = entityBuilder.create(domains);
            for (InvoiceDomain domain : domains) {
                InvoiceEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getRequestId(), entity.getRequestId());
                assertEquals(domain.getCost(), entity.getCost());
                assertEquals(domain.getInvoiceStatus().ordinal() + 1, entity.getInvoiceStatusId());
                assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
                assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
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
        domain.setInvoiceStatus(InvoiceStatus.UNPAID);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());
        return domain;
    }

}