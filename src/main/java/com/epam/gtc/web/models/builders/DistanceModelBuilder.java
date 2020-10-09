package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.DistanceModel;

import java.util.ArrayList;
import java.util.List;

public class DistanceModelBuilder extends Builder<DistanceDomain, DistanceModel> {
    public DistanceModel create(DistanceDomain distance) throws BuilderException {
        return build(distance, DistanceModel.class);
    }


    public List<DistanceModel> create(List<DistanceDomain> distanceList) throws BuilderException {
        List<DistanceModel> distanceModels = new ArrayList<>();
        for (DistanceDomain distanceDomain : distanceList) {
            DistanceModel distanceModel = create(distanceDomain);
            distanceModels.add(distanceModel);
        }
        return distanceModels;
    }
}
