package com.epam.gtc.services;


import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.UserDomain;

import java.util.List;

/**
 * User service interface
 *
 * @author Fazliddin Makhsudov
 */
public interface UserService extends BaseService<UserDomain> {
    /**
     * Finds user domain with given email
     *
     * @param email user email
     * @return user domain
     *
     * @throws ServiceException exception
     */
    UserDomain find(String email) throws ServiceException;

    /**
     * Counts number of user  domains
     *
     * @return number of user  domains
     *
     * @throws ServiceException exception
     */
    int countAllUsers() throws ServiceException;

    /**
     * Finds user  domains from page and itemsPerPage number
     *
     * @param page         start position
     * @param itemsPerPage itemsPerPage number
     * @return list of user  domains
     *
     * @throws ServiceException exception
     */
    List<UserDomain> findAll(int page, int itemsPerPage) throws ServiceException;
}

