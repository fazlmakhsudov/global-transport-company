package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.web.models.CityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CityModelBuilderTest {
    private CityModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new CityModelBuilder();
    }

    @Test
    void create() {
        CityDomain domain = getDomain();
        try {
            CityModel model = modelBuilder.create(domain);
            assertEquals(domain.getId(), model.getId());
            assertEquals(domain.getName(), model.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<CityDomain> domains = Arrays.asList(getDomain());
        try {
            List<CityModel> models = modelBuilder.create(domains);
            for (CityDomain domain : domains) {
                CityModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), model.getId());
                assertEquals(domain.getName(), model.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private CityDomain getDomain() {
        CityDomain domain = new CityDomain();
        domain.setName("name");
        domain.setId(1);
        return domain;
    }
}