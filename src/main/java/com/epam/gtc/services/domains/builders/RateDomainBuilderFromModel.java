package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.RateModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) RateDomain object from RateModel object
 *
 * @author Fazliddin Makhsudov
 */
public class RateDomainBuilderFromModel extends Builder<RateModel, RateDomain> {
    public RateDomain create(RateModel rate) throws BuilderException {
        return build(rate, RateDomain.class);
    }

    public List<RateDomain> create(List<RateModel> rateList) throws BuilderException {
        List<RateDomain> rateDomains = new ArrayList<>();
        for (RateModel rateModel : rateList) {
            RateDomain rateDomain = create(rateModel);
            rateDomains.add(rateDomain);
        }
        return rateDomains;
    }
}
