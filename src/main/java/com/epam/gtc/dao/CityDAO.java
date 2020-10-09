package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.DAOException;

public interface CityDAO extends BaseDAO<CityEntity> {
    CityEntity read(final String name) throws DAOException;
}
