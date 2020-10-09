package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.DistanceModel;

import java.util.ArrayList;
import java.util.List;

public class DistanceDomainBuilderFromModel extends Builder<DistanceModel, DistanceDomain> {
    public DistanceDomain create(DistanceModel distance) throws BuilderException {
        return build(distance, DistanceDomain.class);
    }

    public List<DistanceDomain> create(List<DistanceModel> distanceList) throws BuilderException {
        List<DistanceDomain> distanceDomains = new ArrayList<>();
        for (DistanceModel distanceModel : distanceList) {
            DistanceDomain distanceDomain = create(distanceModel);
            distanceDomains.add(distanceDomain);
        }
        return distanceDomains;
    }
}
