package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RateDomain;

import java.util.List;

public interface RateService extends BaseService<RateDomain> {
    int countAllRates();
    RateDomain find(String name) throws ServiceException;
    List<RateDomain> findAll(int page, int itemsPerPage);
}

