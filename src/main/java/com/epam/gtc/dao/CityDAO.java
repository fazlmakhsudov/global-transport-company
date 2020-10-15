package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface CityDAO extends BaseDAO<CityEntity> {
    CityEntity read(final String name) throws DAOException;

    int countAllCities();

    List<CityEntity> readCities(int offset, int limit) throws DAOException;
}
