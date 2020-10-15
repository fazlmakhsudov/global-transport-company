package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.services.domains.DeliveryDomain;

import java.util.List;

public interface DeliveryService extends BaseService<DeliveryDomain> {
    int countAllDeliveries();
    int countUserDeliveries(int userId);
    int countDeliveries(DeliveryStatus deliveryStatus);

    List<DeliveryDomain> findAll(int page, int itemsPerPage);
    List<DeliveryDomain> findAll(int page, int itemsPerPage, int userId);
}

