package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface DeliveryDAO extends BaseDAO<DeliveryEntity> {
    int countAllDeliveries();
    int countUserDeliveries(int userId);
    int countDeliveries(int deliveryStatusId);

    List<DeliveryEntity> readDeliveries(int offset, int limit) throws DAOException;
    List<DeliveryEntity> readDeliveries(int offset, int limit, int userId) throws DAOException;
}
