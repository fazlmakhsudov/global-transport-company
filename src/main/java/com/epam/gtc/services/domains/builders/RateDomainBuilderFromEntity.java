package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RateDomain object from RateEntity object
 *
 * @author Fazliddin Makhsudov
 */

public class RateDomainBuilderFromEntity extends Builder<RateEntity, RateDomain> {
    public RateDomain create(RateEntity rate) throws BuilderException {
        return build(rate, RateDomain.class);
    }

    public List<RateDomain> create(List<RateEntity> rateList) throws BuilderException {
        List<RateDomain> rateDomains = new ArrayList<>();
        for (RateEntity rateEntity : rateList) {
            RateDomain rateDomain = create(rateEntity);
            rateDomains.add(rateDomain);
        }
        return rateDomains;
    }
}
