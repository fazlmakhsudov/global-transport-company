package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.CityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) CityModel object from CityDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class CityModelBuilder extends Builder<CityDomain, CityModel> {
    public CityModel create(CityDomain city) throws BuilderException {
        return build(city, CityModel.class);
    }


    public List<CityModel> create(List<CityDomain> cityList) throws BuilderException {
        List<CityModel> cityModels = new ArrayList<>();
        for (CityDomain cityDomain : cityList) {
            CityModel cityModel = create(cityDomain);
            cityModels.add(cityModel);
        }
        return cityModels;
    }
}
