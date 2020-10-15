package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface RateDAO extends BaseDAO<RateEntity> {
    int countAllRates();

    RateEntity read(final String name) throws DAOException;

    List<RateEntity> readRates(int offset, int limit) throws DAOException;
}
