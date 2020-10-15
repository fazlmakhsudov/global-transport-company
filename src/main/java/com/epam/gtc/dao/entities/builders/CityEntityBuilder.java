package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) CityEntity object from CityDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class CityEntityBuilder extends Builder<CityDomain, CityEntity> {
    public CityEntity create(CityDomain city) throws BuilderException {
        return build(city, CityEntity.class);
    }

    public List<CityEntity> create(List<CityDomain> cityList) throws BuilderException {
        List<CityEntity> cityEntities = new ArrayList<>();
        for (CityDomain cityDomain : cityList) {
            CityEntity cityEntity = create(cityDomain);
            cityEntities.add(cityEntity);
        }
        return cityEntities;
    }
}
