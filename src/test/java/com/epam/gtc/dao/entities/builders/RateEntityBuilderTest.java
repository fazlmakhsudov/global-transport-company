package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RateEntityBuilderTest {

    private RateEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new RateEntityBuilder();
    }

    @Test
    void create() {
        RateDomain domain = getDomain();
        try {
            RateEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getCost(), entity.getCost());
            assertEquals(domain.getMaxDistance(), entity.getMaxDistance());
            assertEquals(domain.getMaxHeight(), entity.getMaxHeight());
            assertEquals(domain.getMaxLength(), entity.getMaxLength());
            assertEquals(domain.getMaxWeight(), entity.getMaxWeight());
            assertEquals(domain.getMaxWidth(), entity.getMaxWidth());
            assertEquals(domain.getName(), entity.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RateDomain> domains = Arrays.asList(getDomain());
        try {
            List<RateEntity> entities = entityBuilder.create(domains);
            for (RateDomain domain : domains) {
                RateEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getCost(), entity.getCost());
                assertEquals(domain.getMaxDistance(), entity.getMaxDistance());
                assertEquals(domain.getMaxHeight(), entity.getMaxHeight());
                assertEquals(domain.getMaxLength(), entity.getMaxLength());
                assertEquals(domain.getMaxWeight(), entity.getMaxWeight());
                assertEquals(domain.getMaxWidth(), entity.getMaxWidth());
                assertEquals(domain.getName(), entity.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private RateDomain getDomain() {
        RateDomain domain = new RateDomain();
        domain.setId(1);
        domain.setCost(1);
        domain.setMaxDistance(1);
        domain.setMaxHeight(1);
        domain.setMaxLength(1);
        domain.setMaxWeight(1);
        domain.setMaxWidth(1);
        domain.setName("rate");
        return domain;
    }

}