package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DistanceEntityBuilderTest {
    private DistanceEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new DistanceEntityBuilder();
    }

    @Test
    void create() {
        DistanceDomain domain = getDomain();
        try {
            DistanceEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getDistance(), entity.getDistance());
            assertEquals(domain.getFromCityId(), entity.getFromCityId());
            assertEquals(domain.getToCityId(), entity.getToCityId());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DistanceDomain> domains = Arrays.asList(getDomain());
        try {
            List<DistanceEntity> entities = entityBuilder.create(domains);
            for (DistanceDomain domain : domains) {
                DistanceEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getDistance(), entity.getDistance());
                assertEquals(domain.getFromCityId(), entity.getFromCityId());
                assertEquals(domain.getToCityId(), entity.getToCityId());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private DistanceDomain getDomain() {
        DistanceDomain domain = new DistanceDomain();
        domain.setId(1);
        domain.setToCityId(2);
        domain.setFromCityId(1);
        domain.setDistance(2D);
        return domain;
    }
}