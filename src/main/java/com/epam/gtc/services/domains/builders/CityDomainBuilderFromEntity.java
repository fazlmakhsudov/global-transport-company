package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) CityDomain object from CityEntity object
 *
 * @author Fazliddin Makhsudov
 */
public class CityDomainBuilderFromEntity extends Builder<CityEntity, CityDomain> {
    public CityDomain create(CityEntity city) throws BuilderException {
        return build(city, CityDomain.class);
    }

    public List<CityDomain> create(List<CityEntity> cityList) throws BuilderException {
        List<CityDomain> cityDomains = new ArrayList<>();
        for (CityEntity cityEntity : cityList) {
            CityDomain cityDomain = create(cityEntity);
            cityDomains.add(cityDomain);
        }
        return cityDomains;
    }
}
