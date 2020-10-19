package com.epam.gtc.web.models.builders;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.web.models.DeliveryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DeliveryModelBuilderTest {
    private DeliveryModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new DeliveryModelBuilder();
    }

    @Test
    void create() {
        DeliveryDomain domain = getDomain();
        try {
            DeliveryModel model = modelBuilder.create(domain);
            assertEquals(domain.getId(), model.getId());
            assertEquals(domain.getRequestId(), model.getRequestId());
            assertEquals(domain.getDeliveryStatus().getName(), model.getDeliveryStatusName());
            assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
            assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DeliveryDomain> domains = Arrays.asList(getDomain());
        try {
            List<DeliveryModel> models = modelBuilder.create(domains);
            for (DeliveryDomain domain : domains) {
                DeliveryModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), model.getId());
                assertEquals(domain.getRequestId(), model.getRequestId());
                assertEquals(domain.getDeliveryStatus().getName(), model.getDeliveryStatusName());
                assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
                assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
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