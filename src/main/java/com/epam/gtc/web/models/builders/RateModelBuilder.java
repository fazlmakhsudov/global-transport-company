package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.RateModel;

import java.util.ArrayList;
import java.util.List;

public class RateModelBuilder extends Builder<RateDomain, RateModel> {
    public RateModel create(RateDomain rate) throws BuilderException {
        return build(rate, RateModel.class);
    }


    public List<RateModel> create(List<RateDomain> rateList) throws BuilderException {
        List<RateModel> rateModels = new ArrayList<>();
        for (RateDomain rateDomain : rateList) {
            RateModel rateModel = create(rateDomain);
            rateModels.add(rateModel);
        }
        return rateModels;
    }
}
