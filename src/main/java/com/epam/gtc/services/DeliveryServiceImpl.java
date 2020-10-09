package com.epam.gtc.services;

import com.epam.gtc.dao.DeliveryDAO;
import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.utils.builders.Builder;
import org.apache.log4j.Logger;

import java.util.List;

public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryDAO deliveryDAO;
    private static final Logger LOG = Logger.getLogger(DeliveryServiceImpl.class);
    private final Builder<DeliveryDomain, DeliveryEntity> deliveryEntitybuilder;
    private final Builder<DeliveryEntity, DeliveryDomain> deliveryDomainBuilder;

    public DeliveryServiceImpl(DeliveryDAO deliveryDAO, Builder<DeliveryDomain, DeliveryEntity> builder1,
                               Builder<DeliveryEntity, DeliveryDomain> builder2) {
        this.deliveryDAO = deliveryDAO;
        this.deliveryEntitybuilder = builder1;
        this.deliveryDomainBuilder = builder2;
    }

    @Override
    public int add(DeliveryDomain delivery) throws ServiceException {
        int inserted;
        try {
            inserted = deliveryDAO.create(deliveryEntitybuilder.create(delivery));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_DELIVERY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_DELIVERY, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DELIVERY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DELIVERY, e);
        }
        return inserted;
    }

    @Override
    public DeliveryDomain find(int id) throws ServiceException {
        DeliveryEntity delivery;
        try {
            delivery = deliveryDAO.read(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DELIVERY_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_DELIVERY_BY_ID, e);
        }
        try {
            return deliveryDomainBuilder.create(delivery);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DELIVERY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DELIVERY, e);
        }
    }

    @Override
    public boolean save(DeliveryDomain Delivery) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = deliveryDAO.update(deliveryEntitybuilder.create(Delivery));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_DELIVERY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_DELIVERY, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DELIVERY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DELIVERY, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = deliveryDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_DELIVERY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_DELIVERY, e);
        }
        return deletedFlag;
    }

    @Override
    public List<DeliveryDomain> findAll() throws ServiceException {
        List<DeliveryEntity> DeliveryList;
        try {
            DeliveryList = deliveryDAO.readAll();
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DELIVERIES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_DELIVERIES, e);
        }
        try {
            return deliveryDomainBuilder.create(DeliveryList);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_DELIVERY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_DELIVERY, e);
        }
    }

    @Override
    public int countAllDeliveries() {
        return deliveryDAO.countAllDeliveries();
    }

    @Override
    public int countDeliveries(DeliveryStatus deliveryStatus) {
        return deliveryDAO.countDeliveries(DeliveryStatus.getId(deliveryStatus));
    }
}
