package com.epam.gtc.services;


import com.epam.gtc.dao.RequestDAO;
import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.builders.Builder;
import org.apache.log4j.Logger;

import java.util.List;

public class RequestServiceImpl implements RequestService {
    private final RequestDAO requestDAO;
    private static final Logger LOG = Logger.getLogger(RequestServiceImpl.class);
    private final Builder<RequestDomain, RequestEntity> requestEntitybuilder;
    private final Builder<RequestEntity, RequestDomain> requestDomainBuilder;

    public RequestServiceImpl(RequestDAO requestDAO, Builder<RequestDomain, RequestEntity> builder1,
                              Builder<RequestEntity, RequestDomain> builder2) {
        this.requestDAO = requestDAO;
        this.requestEntitybuilder = builder1;
        this.requestDomainBuilder = builder2;
    }

    @Override
    public int add(RequestDomain request) throws ServiceException {
        int inserted;
        try {
            inserted = requestDAO.create(requestEntitybuilder.create(request));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_REQUEST, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_REQUEST, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
        return inserted;
    }

    @Override
    public RequestDomain find(int id) throws ServiceException {
        RequestEntity request;
        try {
            request = requestDAO.read(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_REQUEST_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_REQUEST_BY_ID, e);
        }
        try {
            return requestDomainBuilder.create(request);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
    }

    @Override
    public boolean save(RequestDomain request) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = requestDAO.update(requestEntitybuilder.create(request));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_REQUEST, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_REQUEST, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = requestDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_REQUEST, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_REQUEST, e);
        }
        return deletedFlag;
    }

    @Override
    public List<RequestDomain> findAll() throws ServiceException {
        List<RequestEntity> requestList;
        try {
            requestList = requestDAO.readAll();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_REQUESTS, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_REQUESTS, e);
        }
        try {
            return requestDomainBuilder.create(requestList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
    }

    @Override
    public int countAllRequests() {
        return requestDAO.countAllRequests();
    }

    @Override
    public int countUserRequests(int userId) {
        return requestDAO.countUserRequests(userId);
    }

    @Override
    public int countRequests(RequestStatus requestStatus) {
        return requestDAO.countRequests(RequestStatus.getId(requestStatus));
    }

    @Override
    public List<RequestDomain> findAll(int page, int itemsPerPage) {
        int offset = (page - 1) * itemsPerPage;
        List<RequestEntity> requestList = null;
        try {
            requestList = requestDAO.readRequests(offset, itemsPerPage);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_CITIES_WITH_LIMITATION, e);
        }
        try {
            return requestDomainBuilder.create(requestList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
        return null;
    }

    @Override
    public List<RequestDomain> findAll(int page, int itemsPerPage, int userId) {
        int offset = (page - 1) * itemsPerPage;
        List<RequestEntity> requestList = null;
        try {
            requestList = requestDAO.readRequests(offset, itemsPerPage, userId);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_CITIES_WITH_LIMITATION, e);
        }
        try {
            return requestDomainBuilder.create(requestList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
        return null;
    }
}
