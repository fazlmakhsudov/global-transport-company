package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.CityDomain;

import java.util.List;

public interface CityService extends BaseService<CityDomain> {
    CityDomain find(String name) throws ServiceException;

    int countAllCities();

    List<CityDomain> findAll(int page, int itemsPerPage);
}

