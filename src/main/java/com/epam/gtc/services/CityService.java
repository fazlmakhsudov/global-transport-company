package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.CityDomain;

public interface CityService extends BaseService<CityDomain> {
    CityDomain find(String name) throws ServiceException;
}

