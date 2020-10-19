package com.epam.gtc.dao.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistanceEntityTest {

    private DistanceEntity item;

    @BeforeEach
    void setUp() {
        item = new DistanceEntity();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getFromCityId() {
        int expected = 1;
        item.setFromCityId(expected);
        assertEquals(expected, item.getFromCityId());
    }

    @Test
    void getToCityId() {
        int expected = 1;
        item.setToCityId(expected);
        assertEquals(expected, item.getToCityId());
    }

    @Test
    void getDistance() {
        double expected = 1;
        item.setDistance(expected);
        assertEquals(expected, item.getDistance());
    }
}