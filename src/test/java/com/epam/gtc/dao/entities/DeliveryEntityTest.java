package com.epam.gtc.dao.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryEntityTest {
    private DeliveryEntity item;

    @BeforeEach
    void setUp() {
        item = new DeliveryEntity();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getDeliveryStatusId() {
        int expected = 1;
        item.setDeliveryStatusId(expected);
        assertEquals(expected, item.getDeliveryStatusId());
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