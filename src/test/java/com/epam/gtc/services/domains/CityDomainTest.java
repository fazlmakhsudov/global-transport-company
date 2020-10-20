package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.CityEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityDomainTest {
    private CityDomain cityDomain;
    @BeforeEach
    void setUp() {
        cityDomain = new CityDomain();
    }

    @Test
    void getId() {
        assertEquals(0, cityDomain.getId());
    }

    @Test
    void setId() {
        cityDomain.setId(1);
        assertEquals(1, cityDomain.getId());
    }

    @Test
    void getName() {
        assertEquals(null, cityDomain.getName());
    }

    @Test
    void setName() {
        cityDomain.setName("f");
        assertEquals("f", cityDomain.getName());
    }
}