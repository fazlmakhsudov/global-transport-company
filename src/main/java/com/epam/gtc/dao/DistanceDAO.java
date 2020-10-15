package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface DistanceDAO extends BaseDAO<DistanceEntity> {
    int countAllDistances();

    List<DistanceEntity> readDistances(int offset, int limit) throws DAOException;

    DistanceEntity read(int fromCityId, int toCityId) throws DAOException;

    List<DistanceEntity> readAll(double distance) throws DAOException;
}
