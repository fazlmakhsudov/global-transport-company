package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RequestDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestEntityBuilderTest {
    private RequestEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new RequestEntityBuilder();
    }

    @Test
    void create() {
        RequestDomain domain = getDomain();
        try {
            RequestEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getCityFromId(), entity.getCityFromId());
            assertEquals(domain.getCityToId(), entity.getCityToId());
            assertEquals(domain.getWeight(), entity.getWeight());
            assertEquals(domain.getLength(), entity.getLength());
            assertEquals(domain.getWidth(), entity.getWidth());
            assertEquals(domain.getHeight(), entity.getHeight());
            assertEquals(domain.getUserId(), entity.getUserId());
            assertTrue(domain.getDeliveryDate().compareTo(entity.getDeliveryDate().toLocalDateTime()) == 0);
            assertEquals(domain.getContentType().ordinal() + 1, entity.getContentTypeId());
            assertEquals(domain.getRequestStatus().ordinal() + 1, entity.getRequestStatusId());
            assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
            assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RequestDomain> domains = Arrays.asList(getDomain());
        try {
            List<RequestEntity> entities = entityBuilder.create(domains);
            for (RequestDomain domain : domains) {
                RequestEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getCityFromId(), entity.getCityFromId());
                assertEquals(domain.getCityToId(), entity.getCityToId());
                assertEquals(domain.getWeight(), entity.getWeight());
                assertEquals(domain.getLength(), entity.getLength());
                assertEquals(domain.getWidth(), entity.getWidth());
                assertEquals(domain.getHeight(), entity.getHeight());
                assertEquals(domain.getUserId(), entity.getUserId());
                assertTrue(domain.getDeliveryDate().compareTo(entity.getDeliveryDate().toLocalDateTime()) == 0);
                assertEquals(domain.getContentType().ordinal() + 1, entity.getContentTypeId());
                assertEquals(domain.getRequestStatus().ordinal() + 1, entity.getRequestStatusId());
                assertTrue(domain.getCreatedDate().compareTo(entity.getCreatedDate().toLocalDateTime()) == 0);
                assertTrue(domain.getUpdatedDate().compareTo(entity.getUpdatedDate().toLocalDateTime()) == 0);
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
        domain.setContentType(ContentType.PARCEL_POST);
        domain.setRequestStatus(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
        domain.setCreatedDate(LocalDateTime.now());
        domain.setUpdatedDate(LocalDateTime.now());
        return domain;
    }
}