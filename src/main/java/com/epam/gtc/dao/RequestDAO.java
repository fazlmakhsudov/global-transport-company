package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.RequestEntity;

public interface RequestDAO extends BaseDAO<RequestEntity> {
    int countAllRequests();

    int countRequests(int requestStatusId);
}
