package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RateEntity object from RateDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class RateEntityBuilder extends Builder<RateDomain, RateEntity> {
    public RateEntity create(RateDomain rate) throws BuilderException {
        return build(rate, RateEntity.class);
    }

    public List<RateEntity> create(List<RateDomain> rateList) throws BuilderException {
        List<RateEntity> rateEntities = new ArrayList<>();
        for (RateDomain cityDomain : rateList) {
            RateEntity cityEntity = create(cityDomain);
            rateEntities.add(cityEntity);
        }
        return rateEntities;
    }
}
