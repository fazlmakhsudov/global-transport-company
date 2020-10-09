package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.builders.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) DistanceEntity object from DistanceDomain object
 */
public class DistanceEntityBuilder extends Builder<DistanceDomain, DistanceEntity> {
    public DistanceEntity create(DistanceDomain distance) throws BuilderException {
        return build(distance, DistanceEntity.class);
    }

    public List<DistanceEntity> create(List<DistanceDomain> distanceList) throws BuilderException {
        List<DistanceEntity> distanceEntities = new ArrayList<>();
        for (DistanceDomain distanceDomain : distanceList) {
            DistanceEntity distanceEntity = create(distanceDomain);
            distanceEntities.add(distanceEntity);
        }
        return distanceEntities;
    }
}
