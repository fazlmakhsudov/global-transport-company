package com.epam.gtc.services;


import com.epam.gtc.dao.DistanceDAO;
import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.builders.Builder;
import org.apache.log4j.Logger;

import java.util.List;

public class DistanceServiceImpl implements DistanceService {
    private final DistanceDAO distanceDAO;
    private static final Logger LOG = Logger.getLogger(DistanceServiceImpl.class);
    private final Builder<DistanceDomain, DistanceEntity> distanceEntitybuilder;
    private final Builder<DistanceEntity, DistanceDomain> distanceDomainBuilder;

    public DistanceServiceImpl(DistanceDAO distanceDAO, Builder<DistanceDomain, DistanceEntity> builder1,
                               Builder<DistanceEntity, DistanceDomain> builder2) {
        this.distanceDAO = distanceDAO;
        this.distanceEntitybuilder = builder1;
        this.distanceDomainBuilder = builder2;
    }

    @Override
    public int add(DistanceDomain distance) throws ServiceException {
        int inserted;
        try {
            inserted = distanceDAO.create(distanceEntitybuilder.create(distance));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_DISTANCE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_DISTANCE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
        return inserted;
    }

    @Override
    public DistanceDomain find(int id) throws ServiceException {
        DistanceEntity distance;
        try {
            distance = distanceDAO.read(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ID, e);
        }
        try {
            return distanceDomainBuilder.create(distance);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }

    @Override
    public boolean save(DistanceDomain distance) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = distanceDAO.update(distanceEntitybuilder.create(distance));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_DISTANCE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_DISTANCE, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = distanceDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_DISTANCE, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_DISTANCE, e);
        }
        return deletedFlag;
    }

    @Override
    public List<DistanceDomain> findAll() throws ServiceException {
        List<DistanceEntity> distanceList;
        try {
            distanceList = distanceDAO.readAll();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DISTANCES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DISTANCES, e);
        }
        try {
            return distanceDomainBuilder.create(distanceList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }

    @Override
    public int countAllDistances() {
        return distanceDAO.countAllDistances();
    }

    @Override
    public List<DistanceDomain> findAll(int page, int itemsPerPage) {
        int offset = (page - 1) * itemsPerPage;
        List<DistanceEntity> distanceList = null;
        try {
            distanceList = distanceDAO.readDistances(offset, itemsPerPage);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
        }
        try {
            return distanceDomainBuilder.create(distanceList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
        return null;
    }

    @Override
    public DistanceDomain find(int fromCityId, int toCityId) throws ServiceException {
        DistanceEntity distance;
        try {
            distance = distanceDAO.read(fromCityId, toCityId);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, e);
        }
        try {
            return distanceDomainBuilder.create(distance);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }

    @Override
    public List<DistanceDomain> findAll(double distance) throws ServiceException {
        List<DistanceEntity> distanceList = null;
        try {
            distanceList = distanceDAO.readAll(distance);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
        }
        try {
            return distanceDomainBuilder.create(distanceList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
        return null;
    }
}
