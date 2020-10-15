package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.DeliveryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) DeliveryModel object from DeliveryDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class DeliveryModelBuilder extends Builder<DeliveryDomain, DeliveryModel> {
    public DeliveryModel create(DeliveryDomain delivery) throws BuilderException {
        return build(delivery, DeliveryModel.class);
    }


    public List<DeliveryModel> create(List<DeliveryDomain> deliverylist) throws BuilderException {
        List<DeliveryModel> deliveryModels = new ArrayList<>();
        for (DeliveryDomain deliveryDomain : deliverylist) {
            DeliveryModel deliveryModel = create(deliveryDomain);
            deliveryModels.add(deliveryModel);
        }
        return deliveryModels;
    }
}
