package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.services.domains.DeliveryDomain;

public interface DeliveryService extends BaseService<DeliveryDomain> {
    int countAllDeliveries();

    int countDeliveries(DeliveryStatus deliveryStatus);
}

