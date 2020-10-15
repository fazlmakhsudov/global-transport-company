package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) DeliveryDomain object from DeliveryEntity object
 *
 * @author Fazliddin Makhsudov
 */
public class DeliveryDomainBuilderFromEntity extends Builder<DeliveryEntity, DeliveryDomain> {
    public DeliveryDomain create(DeliveryEntity delivery) throws BuilderException {
        return build(delivery, DeliveryDomain.class);
    }

    public List<DeliveryDomain> create(List<DeliveryEntity> deliveryList) throws BuilderException {
        List<DeliveryDomain> deliveryDomains = new ArrayList<>();
        for (DeliveryEntity deliveryEntity : deliveryList) {
            DeliveryDomain deliveryDomain = create(deliveryEntity);
            deliveryDomains.add(deliveryDomain);
        }
        return deliveryDomains;
    }
}
