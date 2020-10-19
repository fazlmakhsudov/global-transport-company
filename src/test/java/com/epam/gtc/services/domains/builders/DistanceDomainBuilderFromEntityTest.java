package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DistanceDomainBuilderFromEntityTest {
    private DistanceDomainBuilderFromEntity domainBuilderFromEntity;


    @BeforeEach
    void setUp() {
        domainBuilderFromEntity = new DistanceDomainBuilderFromEntity();
    }

    @Test
    void create() {
        DistanceEntity entity = getEntity();
        try {
            DistanceDomain domain = domainBuilderFromEntity.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getDistance(), domain.getDistance());
            assertEquals(entity.getFromCityId(), domain.getFromCityId());
            assertEquals(entity.getToCityId(), domain.getToCityId());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DistanceEntity> entities = Arrays.asList(getEntity());
        try {
            List<DistanceDomain> domains = domainBuilderFromEntity.create(entities);
            for (DistanceEntity entity : entities) {
                DistanceDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getDistance(), domain.getDistance());
                assertEquals(entity.getFromCityId(), domain.getFromCityId());
                assertEquals(entity.getToCityId(), domain.getToCityId());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private DistanceEntity getEntity() {
        DistanceEntity entity = new DistanceEntity();
        entity.setId(1);
        entity.setToCityId(2);
        entity.setFromCityId(1);
        entity.setDistance(2D);
        return entity;
    }
}