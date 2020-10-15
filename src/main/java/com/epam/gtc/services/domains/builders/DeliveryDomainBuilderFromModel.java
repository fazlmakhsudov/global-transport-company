package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.DeliveryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) DeliveryDomain object from DeliveryModel object
 *
 * @author Fazliddin Makhsudov
 */
public class DeliveryDomainBuilderFromModel extends Builder<DeliveryModel, DeliveryDomain> {
    public DeliveryDomain create(DeliveryModel delivery) throws BuilderException {
        return build(delivery, DeliveryDomain.class);
    }

    public List<DeliveryDomain> create(List<DeliveryModel> deliveryList) throws BuilderException {
        List<DeliveryDomain> deliveryDomains = new ArrayList<>();
        for (DeliveryModel deliveryModel : deliveryList) {
            DeliveryDomain deliveryDomain = create(deliveryModel);
            deliveryDomains.add(deliveryDomain);
        }
        return deliveryDomains;
    }
}
