package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.web.models.RateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RateDomainBuilderFromModelTest {
    private RateDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new RateDomainBuilderFromModel();
    }

    @Test
    void create() {
        RateModel model = getModel();
        try {
            RateDomain domain = domainBuilder.create(model);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getCost(), domain.getCost());
            assertEquals(model.getMaxDistance(), domain.getMaxDistance());
            assertEquals(model.getMaxHeight(), domain.getMaxHeight());
            assertEquals(model.getMaxLength(), domain.getMaxLength());
            assertEquals(model.getMaxWeight(), domain.getMaxWeight());
            assertEquals(model.getMaxWidth(), domain.getMaxWidth());
            assertEquals(model.getName(), domain.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<RateModel> models = Arrays.asList(getModel());
        try {
            List<RateDomain> domains = domainBuilder.create(models);
            for (RateModel model : models) {
                RateDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getCost(), domain.getCost());
                assertEquals(model.getMaxDistance(), domain.getMaxDistance());
                assertEquals(model.getMaxHeight(), domain.getMaxHeight());
                assertEquals(model.getMaxLength(), domain.getMaxLength());
                assertEquals(model.getMaxWeight(), domain.getMaxWeight());
                assertEquals(model.getMaxWidth(), domain.getMaxWidth());
                assertEquals(model.getName(), domain.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private RateModel getModel() {
        RateModel model = new RateModel();
        model.setId(1);
        model.setCost(1);
        model.setMaxDistance(1);
        model.setMaxHeight(1);
        model.setMaxLength(1);
        model.setMaxWeight(1);
        model.setMaxWidth(1);
        model.setName("rate");
        return model;
    }
}