package com.epam.gtc.services;


import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.services.domains.RequestDomain;

import java.util.List;

public interface RequestService extends BaseService<RequestDomain> {
    int countAllRequests();

    int countUserRequests(int userId);

    int countRequests(RequestStatus requestStatus);

    List<RequestDomain> findAll(int page, int itemsPerPage);

    List<RequestDomain> findAll(int page, int itemsPerPage, int userId);
}

