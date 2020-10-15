package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface RequestDAO extends BaseDAO<RequestEntity> {
    int countAllRequests();

    int countUserRequests(int userId);

    int countRequests(int requestStatusId);

    List<RequestEntity> readRequests(int offset, int limit) throws DAOException;

    List<RequestEntity> readRequests(int offset, int limit, int userId) throws DAOException;
}
