package com.epam.gtc.web.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestModelTest {
    private RequestModel item;

    @BeforeEach
    void setUp() {
        item = new RequestModel();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getCityFromId() {
        int expected = 1;
        item.setCityFromId(expected);
        assertEquals(expected, item.getCityFromId());
    }

    @Test
    void getCityToId() {
        int expected = 1;
        item.setCityToId(expected);
        assertEquals(expected, item.getCityToId());
    }

    @Test
    void getWeight() {
        double expected = 1;
        item.setWeight(expected);
        assertEquals(expected, item.getWeight());
    }

    @Test
    void getLength() {
        double expected = 1;
        item.setLength(expected);
        assertEquals(expected, item.getLength());
    }

    @Test
    void getWidth() {
        double expected = 1;
        item.setWidth(expected);
        assertEquals(expected, item.getWidth());
    }

    @Test
    void getHeight() {
        double expected = 1;
        item.setHeight(expected);
        assertEquals(expected, item.getHeight());
    }

    @Test
    void getDeliveryDate() {
        Timestamp expected = Timestamp.from(Instant.now());
        item.setDeliveryDate(expected);
        assertEquals(expected, item.getDeliveryDate());
    }

    @Test
    void getUserId() {
        int expected = 1;
        item.setUserId(expected);
        assertEquals(expected, item.getUserId());
    }

    @Test
    void getContentTypeName() {
        String expected = "status";
        item.setContentTypeName(expected);
        assertEquals(expected, item.getContentTypeName());
    }

    @Test
    void getRequestStatusId() {
        String expected = "status";
        item.setRequestStatusName(expected);
        assertEquals(expected, item.getRequestStatusName());
    }

    @Test
    void getCreatedDate() {
        Timestamp expected = Timestamp.from(Instant.now());
        item.setCreatedDate(expected);
        assertEquals(expected, item.getCreatedDate());
    }

    @Test
    void getUpdatedDate() {
        Timestamp expected = Timestamp.from(Instant.now());
        item.setUpdatedDate(expected);
        assertEquals(expected, item.getUpdatedDate());
    }
}