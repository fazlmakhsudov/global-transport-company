package com.epam.gtc.services;


import com.epam.gtc.dao.RequestDAO;
import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.utils.Builder;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Request service implementation
 *
 * @author Fazliddin Makhsudov
 */
public class RequestServiceImpl implements RequestService {
    private final RequestDAO requestDAO;
    private static final Logger LOG = Logger.getLogger(RequestServiceImpl.class);
    private final Builder<RequestDomain, RequestEntity> requestEntitybuilder;
    private final Builder<RequestEntity, RequestDomain> requestDomainBuilder;

    public RequestServiceImpl(RequestDAO requestDAO, Builder<RequestDomain, RequestEntity> requestEntitybuilder,
                              Builder<RequestEntity, RequestDomain> requestDomainBuilder) {
        this.requestDAO = requestDAO;
        this.requestEntitybuilder = requestEntitybuilder;
        this.requestDomainBuilder = requestDomainBuilder;
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
            return requestDomainBuilder.create(request);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_REQUEST_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_REQUEST_BY_ID, e);
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
            return requestDomainBuilder.create(requestList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_REQUESTS, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_REQUESTS, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
    }

    @Override
    public int countAllRequests() throws ServiceException {
        try {
            return requestDAO.countAllRequests();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_ALL_REQUESTS, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_ALL_REQUESTS, e);
        }
    }

    @Override
    public int countUserRequests(int userId) throws ServiceException {
        try {
            return requestDAO.countUserRequests(userId);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION, e);
        }
    }

    @Override
    public int countRequests(RequestStatus requestStatus) throws ServiceException {
        try {
            return requestDAO.countRequests(RequestStatus.getId(requestStatus));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION, e);
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION, e);
        }
    }

    @Override
    public List<RequestDomain> findAll(int page, int itemsPerPage) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<RequestEntity> requestList;
        try {
            requestList = requestDAO.readRequests(offset, itemsPerPage);
            return requestDomainBuilder.create(requestList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_REQUESTS_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_REQUESTS_WITH_LIMITATION, e);

        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);

        }
    }

    @Override
    public List<RequestDomain> findAll(int page, int itemsPerPage, int userId) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<RequestEntity> requestList;
        try {
            requestList = requestDAO.readRequests(offset, itemsPerPage, userId);
            return requestDomainBuilder.create(requestList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_REQUESTS_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_REQUESTS_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_REQUEST, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_REQUEST, e);
        }
    }
}
