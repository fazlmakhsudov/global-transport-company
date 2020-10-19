package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.web.models.DistanceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistanceDomainBuilderFromModelTest {
    private DistanceDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new DistanceDomainBuilderFromModel();
    }

    @Test
    void create() {
        DistanceModel model = getModel();
        try {
            DistanceDomain domain = domainBuilder.create(model);
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
        List<DistanceModel> models = Arrays.asList(getModel());
        try {
            List<DistanceDomain> domains = domainBuilder.create(models);
            for (DistanceModel model : models) {
                DistanceDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
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

    private DistanceModel getModel() {
        DistanceModel model = new DistanceModel();
        model.setId(1);
        model.setToCityId(2);
        model.setFromCityId(1);
        model.setDistance(2D);
        return model;
    }
}