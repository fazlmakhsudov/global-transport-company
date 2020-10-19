package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.web.models.CityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CityDomainBuilderFromModelTest {
    private CityDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new CityDomainBuilderFromModel();
    }

    @Test
    void create() {
        CityModel model = getModel();
        try {
            CityDomain domain = domainBuilder.create(model);
            assertEquals(domain.getId(), model.getId());
            assertEquals(domain.getName(), model.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<CityModel> models = Arrays.asList(getModel());
        try {
            List<CityDomain> domains = domainBuilder.create(models);
            for (CityModel model : models) {
                CityDomain domain = domains.stream().
                        filter(cityEntity -> model.getId() == cityEntity.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), model.getId());
                assertEquals(domain.getName(), model.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private CityModel getModel() {
        CityModel model = new CityModel();
        model.setName("name");
        model.setId(1);
        return model;
    }
}