package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DistanceDomain;

import java.util.List;

public interface DistanceService extends BaseService<DistanceDomain> {
    int countAllDistances();

    List<DistanceDomain> findAll(int page, int itemsPerPage);

    DistanceDomain find(int fromCityId, int toCityId) throws ServiceException;

    List<DistanceDomain> findAll(double distance) throws ServiceException;
}

