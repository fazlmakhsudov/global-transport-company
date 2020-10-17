package com.epam.gtc.services;


import com.epam.gtc.dao.DistanceDAO;
import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.utils.Builder;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * Distance service implementation
 *
 * @author Fazliddin Makhsudov
 */
public class DistanceServiceImpl implements DistanceService {
    private final DistanceDAO distanceDAO;
    private static final Logger LOG = Logger.getLogger(DistanceServiceImpl.class);
    private final Builder<DistanceDomain, DistanceEntity> distanceEntitybuilder;
    private final Builder<DistanceEntity, DistanceDomain> distanceDomainBuilder;

    public DistanceServiceImpl(DistanceDAO distanceDAO, Builder<DistanceDomain, DistanceEntity> distanceEntitybuilder,
                               Builder<DistanceEntity, DistanceDomain> distanceDomainBuilder) {
        this.distanceDAO = distanceDAO;
        this.distanceEntitybuilder = distanceEntitybuilder;
        this.distanceDomainBuilder = distanceDomainBuilder;
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
            return distanceDomainBuilder.create(distance);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ID, e);
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
            return distanceDomainBuilder.create(distanceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DISTANCES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DISTANCES, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }

    @Override
    public int countAllDistances() throws ServiceException {
        try {
            return distanceDAO.countAllDistances();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_DISTANCES);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_ALL_DISTANCES, e);
        }
    }

    @Override
    public List<DistanceDomain> findAll(int page, int itemsPerPage) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<DistanceEntity> distanceList;
        try {
            distanceList = distanceDAO.readDistances(offset, itemsPerPage);
            return distanceDomainBuilder.create(distanceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);

        }
    }

    @Override
    public DistanceDomain find(int fromCityId, int toCityId) throws ServiceException {
        DistanceEntity distance;
        try {
            distance = distanceDAO.read(fromCityId, toCityId);
            return Objects.isNull(distance) ? null : distanceDomainBuilder.create(distance);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }

    @Override
    public List<DistanceDomain> findAll(double distance) throws ServiceException {
        List<DistanceEntity> distanceList;
        try {
            distanceList = distanceDAO.readAll(distance);
            return distanceDomainBuilder.create(distanceList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DISTANCE, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DISTANCE, e);
        }
    }
}
