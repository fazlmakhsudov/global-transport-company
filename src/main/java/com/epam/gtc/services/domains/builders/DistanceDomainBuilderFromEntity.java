package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.builders.Builder;

import java.util.ArrayList;
import java.util.List;

public class DistanceDomainBuilderFromEntity extends Builder<DistanceEntity, DistanceDomain> {
    public DistanceDomain create(DistanceEntity distance) throws BuilderException {
        return build(distance, DistanceDomain.class);
    }

    public List<DistanceDomain> create(List<DistanceEntity> distanceList) throws BuilderException {
        List<DistanceDomain> distanceDomains = new ArrayList<>();
        for (DistanceEntity distanceEntity : distanceList) {
            DistanceDomain distanceDomain = create(distanceEntity);
            distanceDomains.add(distanceDomain);
        }
        return distanceDomains;
    }
}
