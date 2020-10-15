package com.epam.gtc.services;


import com.epam.gtc.dao.RateDAO;
import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.utils.builders.Builder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RateServiceImpl implements RateService {
    private final RateDAO rateDAO;
    private static final Logger LOG = Logger.getLogger(RateServiceImpl.class);
    private final Builder<RateDomain, RateEntity> rateEntitybuilder;
    private final Builder<RateEntity, RateDomain> rateDomainBuilder;

    public RateServiceImpl(RateDAO rateDAO, Builder<RateDomain, RateEntity> rateEntitybuilder,
                           Builder<RateEntity, RateDomain> rateDomainBuilder) {
        this.rateDAO = rateDAO;
        this.rateEntitybuilder = rateEntitybuilder;
        this.rateDomainBuilder = rateDomainBuilder;
    }

    @Override
    public int add(RateDomain rate) throws ServiceException {
        int inserted;
        try {
            inserted = rateDAO.create(rateEntitybuilder.create(rate));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_RATE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_RATE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_RATE, e);
        }
        return inserted;
    }

    @Override
    public RateDomain find(int id) throws ServiceException {
        RateEntity rate;
        try {
            rate = rateDAO.read(id);
            return rateDomainBuilder.create(rate);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_ID, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_RATE, e);
        }
    }

    @Override
    public RateDomain find(String name) throws ServiceException {
        RateEntity rate;
        try {
            rate = rateDAO.read(name);
            return rateDomainBuilder.create(rate);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_NAME, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_NAME, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_RATE, e);
        }
    }

    @Override
    public boolean save(RateDomain rate) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = rateDAO.update(rateEntitybuilder.create(rate));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_RATE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_RATE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_RATE, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = rateDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_RATE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_RATE, e);
        }
        return deletedFlag;
    }

    @Override
    public List<RateDomain> findAll() throws ServiceException {
        List<RateEntity> rateList;
        try {
            rateList = rateDAO.readAll();
            return rateDomainBuilder.create(rateList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_RATES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_RATES, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_RATE, e);
        }
    }

    @Override
    public int countAllRates() {
        return rateDAO.countAllRates();
    }


    @Override
    public List<RateDomain> findAll(int page, int itemsPerPage) {
        int offset = (page - 1) * itemsPerPage;
        List<RateEntity> rateList;
        try {
            rateList = rateDAO.readRates(offset, itemsPerPage);
            return rateDomainBuilder.create(rateList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_RATES_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_RATE, e);
        }
        return new ArrayList<>();
    }
}
