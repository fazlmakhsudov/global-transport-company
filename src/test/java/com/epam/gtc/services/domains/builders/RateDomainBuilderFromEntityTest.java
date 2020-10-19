package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RateDomainBuilderFromEntityTest {

    private RateDomainBuilderFromEntity domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new RateDomainBuilderFromEntity();
    }

    @Test
    void create() {
        RateEntity entity = getEntity();
        try {
            RateDomain domain = domainBuilder.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getCost(), domain.getCost());
            assertEquals(entity.getMaxDistance(), domain.getMaxDistance());
            assertEquals(entity.getMaxHeight(), domain.getMaxHeight());
            assertEquals(entity.getMaxLength(), domain.getMaxLength());
            assertEquals(entity.getMaxWeight(), domain.getMaxWeight());
            assertEquals(entity.getMaxWidth(), domain.getMaxWidth());
            assertEquals(entity.getName(), domain.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RateEntity> entities = Arrays.asList(getEntity());
        try {
            List<RateDomain> domains = domainBuilder.create(entities);
            for (RateEntity entity : entities) {
                RateDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getCost(), domain.getCost());
                assertEquals(entity.getMaxDistance(), domain.getMaxDistance());
                assertEquals(entity.getMaxHeight(), domain.getMaxHeight());
                assertEquals(entity.getMaxLength(), domain.getMaxLength());
                assertEquals(entity.getMaxWeight(), domain.getMaxWeight());
                assertEquals(entity.getMaxWidth(), domain.getMaxWidth());
                assertEquals(entity.getName(), domain.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private RateEntity getEntity() {
        RateEntity entity = new RateEntity();
        entity.setId(1);
        entity.setCost(1);
        entity.setMaxDistance(1);
        entity.setMaxHeight(1);
        entity.setMaxLength(1);
        entity.setMaxWeight(1);
        entity.setMaxWidth(1);
        entity.setName("rate");
        return entity;
    }
}