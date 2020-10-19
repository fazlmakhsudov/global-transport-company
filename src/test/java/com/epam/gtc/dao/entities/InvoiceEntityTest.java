package com.epam.gtc.dao.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceEntityTest {

    private InvoiceEntity item;

    @BeforeEach
    void setUp() {
        item = new InvoiceEntity();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getCost() {
        double expected = 1;
        item.setCost(expected);
        assertEquals(expected, item.getCost());
    }

    @Test
    void getInvoiceStatusId() {
        int expected = 1;
        item.setInvoiceStatusId(expected);
        assertEquals(expected, item.getInvoiceStatusId());
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