package com.epam.gtc.services.domains;

import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryDomainTest {
    private DeliveryDomain deliveryDomain;

    @BeforeEach
    void setUp() {
        deliveryDomain = new DeliveryDomain();
    }

    @Test
    void getId() {
        assertEquals(0, deliveryDomain.getId());
    }

    @Test
    void setId() {
        deliveryDomain.setId(1);
        assertEquals(1, deliveryDomain.getId());
    }

    @Test
    void getDeliveryStatus() {
        assertEquals(null, deliveryDomain.getDeliveryStatus());
    }

    @Test
    void setDeliveryStatus() {
        deliveryDomain.setDeliveryStatus(DeliveryStatus.DELIVERED);
        assertEquals(DeliveryStatus.DELIVERED, deliveryDomain.getDeliveryStatus());
    }
}