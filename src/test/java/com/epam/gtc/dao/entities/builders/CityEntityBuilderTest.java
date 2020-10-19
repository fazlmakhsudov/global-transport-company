package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CityEntityBuilderTest {
    private CityEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new CityEntityBuilder();
    }

    @Test
    void create() {
        CityDomain domain = getDomain();
        try {
            CityEntity cityEntity = entityBuilder.create(domain);
            assertEquals(domain.getId(), cityEntity.getId());
            assertEquals(domain.getName(), cityEntity.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<CityDomain> cityDomains = Arrays.asList(getDomain());
        try {
            List<CityEntity> cityEntities = entityBuilder.create(cityDomains);
            for (CityDomain domain : cityDomains) {
                CityEntity entity = cityEntities.stream().
                        filter(cityEntity -> domain.getId() == cityEntity.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getName(), entity.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private CityDomain getDomain() {
        CityDomain cityDomain = new CityDomain();
        cityDomain.setName("name");
        cityDomain.setId(1);
        return cityDomain;
    }

}