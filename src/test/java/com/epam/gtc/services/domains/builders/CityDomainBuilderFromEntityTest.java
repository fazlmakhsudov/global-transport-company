package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CityDomainBuilderFromEntityTest {
    private CityDomainBuilderFromEntity domainBuilderFromEntity;


    @BeforeEach
    void setUp() {
        domainBuilderFromEntity = new CityDomainBuilderFromEntity();
    }

    @Test
    void create() {
        CityEntity entity = getEntity();
        try {
            CityDomain domain = domainBuilderFromEntity.create(entity);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getName(), entity.getName());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<CityEntity> entities = Arrays.asList(getEntity());
        try {
            List<CityDomain> domains = domainBuilderFromEntity.create(entities);
            for (CityEntity entity : entities) {
                CityDomain domain = domains.stream().
                        filter(cityEntity -> entity.getId() == cityEntity.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getName(), entity.getName());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private CityEntity getEntity() {
        CityEntity entity = new CityEntity();
        entity.setName("name");
        entity.setId(1);
        return entity;
    }

}