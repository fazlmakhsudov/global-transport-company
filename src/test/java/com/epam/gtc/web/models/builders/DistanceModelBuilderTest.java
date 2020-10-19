package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.web.models.DistanceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class DistanceModelBuilderTest {
    private DistanceModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new DistanceModelBuilder();
    }

    @Test
    void create() {
        DistanceDomain domain = getDomain();
        try {
            DistanceModel model = modelBuilder.create(domain);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getDistance(), domain.getDistance());
            assertEquals(model.getFromCityId(), domain.getFromCityId());
            assertEquals(model.getToCityId(), domain.getToCityId());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<DistanceDomain> domains = Arrays.asList(getDomain());
        try {
            List<DistanceModel> models = modelBuilder.create(domains);
            for (DistanceDomain domain : domains) {
                DistanceModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getDistance(), domain.getDistance());
                assertEquals(model.getFromCityId(), domain.getFromCityId());
                assertEquals(model.getToCityId(), domain.getToCityId());
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