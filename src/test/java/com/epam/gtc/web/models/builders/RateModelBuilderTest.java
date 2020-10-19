package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.web.models.RateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RateModelBuilderTest {
    private RateModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new RateModelBuilder();
    }

    @Test
    void create() {
        RateDomain domain = getDomain();
        try {
            RateModel model = modelBuilder.create(domain);
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
        List<RateDomain> domains = Arrays.asList(getDomain());
        try {
            List<RateModel> models = modelBuilder.create(domains);
            for (RateDomain domain : domains) {
                RateModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
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