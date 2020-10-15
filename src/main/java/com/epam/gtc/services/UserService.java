package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.UserDomain;

import java.util.List;

public interface UserService extends BaseService<UserDomain> {
    UserDomain find(String email) throws ServiceException;

    int countAllUsers();

    List<UserDomain> findAll(int page, int itemsPerPage);
}

