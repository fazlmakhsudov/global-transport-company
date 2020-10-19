package com.epam.gtc.web.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryModelTest {
    private DeliveryModel item;

    @BeforeEach
    void setUp() {
        item = new DeliveryModel();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getDeliveryStatusName() {
        String expected = "name";
        item.setDeliveryStatusName(expected);
        assertEquals(expected, item.getDeliveryStatusName());
    }

    @Test
    void getRequestId() {
        int expected = 1;
        item.setRequestId(expected);
        assertEquals(expected, item.getRequestId());
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