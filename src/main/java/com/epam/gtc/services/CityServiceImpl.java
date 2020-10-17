package com.epam.gtc.services;

import com.epam.gtc.dao.CityDAO;
import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.utils.Builder;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * City service implementation
 *
 * @author Fazliddin Makhsudov
 */
public class CityServiceImpl implements CityService {
    private final CityDAO cityDAO;
    private static final Logger LOG = Logger.getLogger(CityServiceImpl.class);
    private final Builder<CityDomain, CityEntity> cityEntitybuilder;
    private final Builder<CityEntity, CityDomain> cityDomainBuilder;

    public CityServiceImpl(CityDAO cityDAO, Builder<CityDomain, CityEntity> cityEntitybuilder,
                           Builder<CityEntity, CityDomain> cityDomainBuilder) {
        this.cityDAO = cityDAO;
        this.cityEntitybuilder = cityEntitybuilder;
        this.cityDomainBuilder = cityDomainBuilder;
    }

    @Override
    public int add(CityDomain city) throws ServiceException {
        int inserted;
        try {
            inserted = cityDAO.create(cityEntitybuilder.create(city));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_CITY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_CITY, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
        return inserted;
    }

    @Override
    public CityDomain find(int id) throws ServiceException {
        CityEntity city;
        try {
            city = cityDAO.read(id);
            return cityDomainBuilder.create(city);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_ID, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_ID, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
    }

    @Override
    public CityDomain find(String name) throws ServiceException {
        CityEntity city;
        try {
            city = cityDAO.read(name);
            return Objects.isNull(city) ? null : cityDomainBuilder.create(city);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_NAME, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_NAME, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
    }

    @Override
    public boolean save(CityDomain city) throws ServiceException {
        boolean updatedFlag;
        try {
            updatedFlag = cityDAO.update(cityEntitybuilder.create(city));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_CITY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_CITY, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) throws ServiceException {
        boolean deletedFlag;
        try {
            deletedFlag = cityDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_CITY, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_CITY, e);
        }
        return deletedFlag;
    }

    @Override
    public List<CityDomain> findAll() throws ServiceException {
        List<CityEntity> cityList;
        try {
            cityList = cityDAO.readAll();
            return cityDomainBuilder.create(cityList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_CITIES, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_CITIES, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
    }


    @Override
    public int countAllCities() throws ServiceException {
        try {
            return cityDAO.countAllCities();
        } catch (DAOException e) {
            throw new ServiceException(Messages.ERR_CANNOT_COUNT_ALL_CITIES, e);
        }
    }

    @Override
    public List<CityDomain> findAll(int page, int itemsPerPage) throws ServiceException {
        int offset = (page - 1) * itemsPerPage;
        List<CityEntity> cityList;
        try {
            cityList = cityDAO.readCities(offset, itemsPerPage);
            return cityDomainBuilder.create(cityList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_CITIES_WITH_LIMITATION, e);
            throw new ServiceException(Messages.ERR_SERVICE_LAYER_CANNOT_READ_CITIES_WITH_LIMITATION, e);

        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_CITY, e);
            throw new ServiceException(Messages.ERR_CANNOT_MAP_CITY, e);
        }
    }
}
