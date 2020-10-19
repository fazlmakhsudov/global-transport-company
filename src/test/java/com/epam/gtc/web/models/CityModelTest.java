package com.epam.gtc.web.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityModelTest {
    private CityModel item;

    @BeforeEach
    void setUp() {
        item = new CityModel();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getName() {
        String expected = "name";
        item.setName(expected);
        assertEquals(expected, item.getName());
    }

}