package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.web.models.DeliveryModel;
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

class DeliveryDomainBuilderFromModelTest {
    private DeliveryDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new DeliveryDomainBuilderFromModel();
    }

    @Test
    void create() {
        DeliveryModel model = getModel();
        try {
            DeliveryDomain domain = domainBuilder.create(model);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getRequestId(), domain.getRequestId());
            assertEquals(model.getDeliveryStatusName(), domain.getDeliveryStatus().getName());
            assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
            assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DeliveryModel> models = Arrays.asList(getModel());
        try {
            List<DeliveryDomain> domains = domainBuilder.create(models);
            for (DeliveryModel model : models) {
                DeliveryDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getRequestId(), domain.getRequestId());
                assertEquals(model.getDeliveryStatusName(), domain.getDeliveryStatus().getName());
                assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
                assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private DeliveryModel getModel() {
        DeliveryModel model = new DeliveryModel();
        model.setId(1);
        model.setRequestId(1);
        model.setDeliveryStatusName(DeliveryStatus.UNDER_TRANSPORTATION.getName());
        model.setCreatedDate(Timestamp.from(Instant.now()));
        model.setUpdatedDate(Timestamp.from(Instant.now()));
        return model;
    }
}