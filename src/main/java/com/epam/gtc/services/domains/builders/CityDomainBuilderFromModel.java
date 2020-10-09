package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.CityModel;

import java.util.ArrayList;
import java.util.List;

public class CityDomainBuilderFromModel extends Builder<CityModel, CityDomain> {
    public CityDomain create(CityModel city) throws BuilderException {
        return build(city, CityDomain.class);
    }

    public List<CityDomain> create(List<CityModel> cityList) throws BuilderException {
        List<CityDomain> cityDomains = new ArrayList<>();
        for (CityModel cityModel : cityList) {
            CityDomain cityDomain = create(cityModel);
            cityDomains.add(cityDomain);
        }
        return cityDomains;
    }
}
