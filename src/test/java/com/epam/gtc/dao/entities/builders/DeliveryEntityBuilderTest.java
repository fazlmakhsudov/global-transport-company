package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryEntityBuilderTest {
    private DeliveryEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new DeliveryEntityBuilder();
    }

    @Test
    void create() {
        DeliveryDomain domain = getDomain();
        try {
            DeliveryEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getRequestId(), entity.getRequestId());
            assertEquals(domain.getDeliveryStatus().ordinal() + 1, entity.getDeliveryStatusId());
            assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
            assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DeliveryDomain> domains = Arrays.asList(getDomain());
        try {
            List<DeliveryEntity> entities = entityBuilder.create(domains);
            for (DeliveryDomain domain : domains) {
                DeliveryEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getRequestId(), entity.getRequestId());
                assertEquals(domain.getDeliveryStatus().ordinal() + 1, entity.getDeliveryStatusId());
                assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
                assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private DeliveryDomain getDomain() {
        DeliveryDomain domain = new DeliveryDomain();
        domain.setId(1);
        domain.setRequestId(1);
        domain.setDeliveryStatus(DeliveryStatus.WAITING_FOR_PACKAGING);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());
        return domain;
    }

}