package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RequestDomainBuilderFromEntityTest {
    private RequestDomainBuilderFromEntity domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new RequestDomainBuilderFromEntity();
    }

    @Test
    void create() {
        RequestEntity entity = getEntity();
        try {
            RequestDomain domain = domainBuilder.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getCityFromId(), domain.getCityFromId());
            assertEquals(entity.getCityToId(), domain.getCityToId());
            assertEquals(entity.getWeight(), domain.getWeight());
            assertEquals(entity.getLength(), domain.getLength());
            assertEquals(entity.getWidth(), domain.getWidth());
            assertEquals(entity.getHeight(), domain.getHeight());
            assertEquals(entity.getUserId(), domain.getUserId());
            assertEquals(entity.getDeliveryDate().toLocalDateTime(), domain.getDeliveryDate());
            assertEquals(entity.getContentTypeId(), domain.getContentType().ordinal() + 1);
            assertEquals(entity.getRequestStatusId(), domain.getRequestStatus().ordinal() + 1);
            assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
            assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RequestEntity> entities = Arrays.asList(getEntity());
        try {
            List<RequestDomain> domains = domainBuilder.create(entities);
            for (RequestEntity entity : entities) {
                RequestDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getCityFromId(), domain.getCityFromId());
                assertEquals(entity.getCityToId(), domain.getCityToId());
                assertEquals(entity.getWeight(), domain.getWeight());
                assertEquals(entity.getLength(), domain.getLength());
                assertEquals(entity.getWidth(), domain.getWidth());
                assertEquals(entity.getHeight(), domain.getHeight());
                assertEquals(entity.getUserId(), domain.getUserId());
                assertEquals(entity.getDeliveryDate().toLocalDateTime(), domain.getDeliveryDate());
                assertEquals(entity.getContentTypeId(), domain.getContentType().ordinal() + 1);
                assertEquals(entity.getRequestStatusId(), domain.getRequestStatus().ordinal() + 1);
                assertEquals(entity.getCreatedDate().toLocalDateTime(), domain.getCreatedDate());
                assertEquals(entity.getUpdatedDate().toLocalDateTime(), domain.getUpdatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private RequestEntity getEntity() {
        RequestEntity domain = new RequestEntity();
        domain.setId(1);
        domain.setCityFromId(1);
        domain.setCityToId(1);
        domain.setWeight(1);
        domain.setLength(1);
        domain.setWidth(1);
        domain.setHeight(1);
        domain.setUserId(1);
        domain.setDeliveryDate(Timestamp.from(Instant.now()));
        domain.setContentTypeId(ContentType.CARGO.ordinal() + 1);
        domain.setRequestStatusId(RequestStatus.CANCELLED.ordinal() + 1);
        domain.setCreatedDate(Timestamp.from(Instant.now()));
        domain.setUpdatedDate(Timestamp.from(Instant.now()));
        return domain;
    }
}