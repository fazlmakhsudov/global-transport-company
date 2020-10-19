package com.epam.gtc.web.models.builders;

import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.web.models.RequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RequestModelBuilderTest {
    private RequestModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new RequestModelBuilder();
    }

    @Test
    void create() {
        RequestDomain domain = getDomain();
        try {
            RequestModel model = modelBuilder.create(domain);
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
        List<RequestDomain> domains = Arrays.asList(getDomain());
        try {
            List<RequestModel> models = modelBuilder.create(domains);
            for (RequestDomain domain : domains) {
                RequestModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
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

    private RequestDomain getDomain() {
        RequestDomain domain = new RequestDomain();
        domain.setId(1);
        domain.setCityFromId(1);
        domain.setCityToId(1);
        domain.setWeight(1);
        domain.setLength(1);
        domain.setWidth(1);
        domain.setHeight(1);
        domain.setUserId(1);
        domain.setDeliveryDate(LocalDateTime.now());
        domain.setContentType(ContentType.CARGO);
        domain.setRequestStatus(RequestStatus.PROCESSED);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());
        return domain;
    }
}