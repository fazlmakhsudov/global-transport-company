package com.epam.gtc.dao.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityEntityTest {
    private CityEntity item;

    @BeforeEach
    void setUp() {
        item = new CityEntity();
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