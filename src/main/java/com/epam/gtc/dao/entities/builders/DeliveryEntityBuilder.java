package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.utils.builders.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) DeliveryEntity object from DeliveryDomain object
 */
public class DeliveryEntityBuilder extends Builder<DeliveryDomain, DeliveryEntity> {
    public DeliveryEntity create(DeliveryDomain delivery) throws BuilderException {
        return build(delivery, DeliveryEntity.class);
    }

    public List<DeliveryEntity> create(List<DeliveryDomain> deliveryList) throws BuilderException {
        List<DeliveryEntity> deliveryEntities = new ArrayList<>();
        for (DeliveryDomain deliveryDomain : deliveryList) {
            DeliveryEntity deliveryEntity = create(deliveryDomain);
            deliveryEntities.add(deliveryEntity);
        }
        return deliveryEntities;
    }
}
