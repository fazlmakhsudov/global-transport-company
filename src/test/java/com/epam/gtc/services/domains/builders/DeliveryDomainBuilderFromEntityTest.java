package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DeliveryDomainBuilderFromEntityTest {
    private DeliveryDomainBuilderFromEntity domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new DeliveryDomainBuilderFromEntity();
    }

    @Test
    void create() {
        DeliveryEntity entity = getEntity();
        try {
            DeliveryDomain domain = domainBuilder.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getRequestId(), domain.getRequestId());
            assertEquals(entity.getDeliveryStatusId(), domain.getDeliveryStatus().ordinal() + 1);
            assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
            assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DeliveryEntity> entities = Arrays.asList(getEntity());
        try {
            List<DeliveryDomain> domains = domainBuilder.create(entities);
            for (DeliveryEntity entity : entities) {
                DeliveryDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getRequestId(), domain.getRequestId());
                assertEquals(entity.getDeliveryStatusId(), domain.getDeliveryStatus().ordinal() + 1);
                assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
                assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private DeliveryEntity getEntity() {
        DeliveryEntity entity = new DeliveryEntity();
        entity.setId(1);
        entity.setRequestId(1);
        entity.setDeliveryStatusId(DeliveryStatus.DELIVERED.ordinal() + 1);
        entity.setCreatedDate(Timestamp.from(Instant.now()));
        entity.setUpdatedDate(Timestamp.from(Instant.now()));
        return entity;
    }
}