package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.services.domains.RequestDomain;

public interface RequestService extends BaseService<RequestDomain> {
    int countAllRequests();

    int countRequests(RequestStatus requestStatus);
}

