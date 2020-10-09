package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.DeliveryEntity;

public interface DeliveryDAO extends BaseDAO<DeliveryEntity> {
    int countAllDeliveries();

    int countDeliveries(int deliveryStatusId);
}
