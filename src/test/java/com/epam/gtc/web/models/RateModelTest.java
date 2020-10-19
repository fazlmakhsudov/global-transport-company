package com.epam.gtc.web.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateModelTest {
    private RateModel item;

    @BeforeEach
    void setUp() {
        item = new RateModel();
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

    @Test
    void getMaxWeight() {
        double expected = 1;
        item.setMaxWeight(expected);
        assertEquals(expected, item.getMaxWeight());
    }

    @Test
    void getMaxLength() {
        double expected = 1;
        item.setMaxLength(expected);
        assertEquals(expected, item.getMaxLength());
    }

    @Test
    void getMaxWidth() {
        double expected = 1;
        item.setMaxWidth(expected);
        assertEquals(expected, item.getMaxWidth());
    }

    @Test
    void getMaxHeight() {
        double expected = 1;
        item.setMaxHeight(expected);
        assertEquals(expected, item.getMaxHeight());
    }

    @Test
    void getMaxDistance() {
        double expected = 1;
        item.setMaxDistance(expected);
        assertEquals(expected, item.getMaxDistance());
    }

    @Test
    void getCost() {
        double expected = 1;
        item.setCost(expected);
        assertEquals(expected, item.getCost());
    }
}