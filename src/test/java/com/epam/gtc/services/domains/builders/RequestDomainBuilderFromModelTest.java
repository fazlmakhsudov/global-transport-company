package com.epam.gtc.services.domains.builders;


import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.web.models.RequestModel;
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

class RequestDomainBuilderFromModelTest {
    private RequestDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new RequestDomainBuilderFromModel();
    }

    @Test
    void create() {
        RequestModel model = getModel();
        try {
            RequestDomain domain = domainBuilder.create(model);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getCityFromId(), domain.getCityFromId());
            assertEquals(model.getCityToId(), domain.getCityToId());
            assertEquals(model.getWeight(), domain.getWeight());
            assertEquals(model.getLength(), domain.getLength());
            assertEquals(model.getWidth(), domain.getWidth());
            assertEquals(model.getHeight(), domain.getHeight());
            assertEquals(model.getUserId(), domain.getUserId());
            assertEquals(LocalDateTime.ofInstant(model.getDeliveryDate().toInstant(), ZoneId.systemDefault()), domain.getDeliveryDate());
            assertEquals(model.getContentTypeName(), domain.getContentType().getName());
            assertEquals(model.getRequestStatusName(), domain.getRequestStatus().getName());
            assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
            assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RequestModel> models = Arrays.asList(getModel());
        try {
            List<RequestDomain> domains = domainBuilder.create(models);
            for (RequestModel model : models) {
                RequestDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getCityFromId(), domain.getCityFromId());
                assertEquals(model.getCityToId(), domain.getCityToId());
                assertEquals(model.getWeight(), domain.getWeight());
                assertEquals(model.getLength(), domain.getLength());
                assertEquals(model.getWidth(), domain.getWidth());
                assertEquals(model.getHeight(), domain.getHeight());
                assertEquals(model.getUserId(), domain.getUserId());
                assertEquals(LocalDateTime.ofInstant(model.getDeliveryDate().toInstant(), ZoneId.systemDefault()), domain.getDeliveryDate());
                assertEquals(model.getContentTypeName(), domain.getContentType().getName());
                assertEquals(model.getRequestStatusName(), domain.getRequestStatus().getName());
                assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
                assertEquals(LocalDateTime.ofInstant(model.getUpdatedDate().toInstant(), ZoneId.systemDefault()), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private RequestModel getModel() {
        RequestModel model = new RequestModel();
        model.setId(1);
        model.setCityFromId(1);
        model.setCityToId(1);
        model.setWeight(1);
        model.setLength(1);
        model.setWidth(1);
        model.setHeight(1);
        model.setUserId(1);
        model.setDeliveryDate(Timestamp.from(Instant.now()));
        model.setContentTypeName(ContentType.CARGO.getName());
        model.setRequestStatusName(RequestStatus.PROCESSED.getName());
        model.setCreatedDate(Timestamp.from(Instant.now()));
        model.setUpdatedDate(Timestamp.from(Instant.now()));
        return model;
    }
}