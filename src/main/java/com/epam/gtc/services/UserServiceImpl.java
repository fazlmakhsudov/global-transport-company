package com.epam.gtc.services;


import com.epam.gtc.dao.UserDAO;
import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.exceptions.Messages;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.builders.Builder;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    private final Builder<UserDomain, UserEntity> userEntitybuilder;
    private final Builder<UserEntity, UserDomain> userDomainBuilder;

    public UserServiceImpl(UserDAO userDAO, Builder<UserDomain, UserEntity> userEntitybuilder,
                           Builder<UserEntity, UserDomain> userDomainBuilder) {
        this.userDAO = userDAO;
        this.userEntitybuilder = userEntitybuilder;
        this.userDomainBuilder = userDomainBuilder;
    }

    @Override
    public int add(UserDomain user) {
        int inserted = -1;
        try {
            inserted = userDAO.create(userEntitybuilder.create(user));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_INSERT_USER, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return inserted;
    }

    @Override
    public UserDomain find(int id) {
        UserEntity user;
        try {
            user = userDAO.read(id);
            return userDomainBuilder.create(user);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_USER_BY_ID, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return null;
    }

    @Override
    public UserDomain find(String email) {
        UserEntity user;
        try {
            user = userDAO.read(email);
            return userDomainBuilder.create(user);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_OBTAIN_USER_BY_EMAIL, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return null;
    }

    @Override
    public boolean save(UserDomain user) {
        boolean updatedFlag = false;
        try {
            updatedFlag = userDAO.update(userEntitybuilder.create(user));
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_UPDATE_USER, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return updatedFlag;
    }

    @Override
    public boolean remove(int id) {
        boolean deletedFlag = false;
        try {
            deletedFlag = userDAO.delete(id);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_DELETE_USER, e);
        }
        return deletedFlag;
    }

    @Override
    public List<UserDomain> findAll() {
        List<UserEntity> userList;
        try {
            userList = userDAO.readAll();
            return userDomainBuilder.create(userList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_ALL_USERS, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return new ArrayList<>();
    }

    @Override
    public int countAllUsers() {
        return userDAO.countAllUsers();
    }

    @Override
    public List<UserDomain> findAll(int page, int itemsPerPage) {
        int offset = (page - 1) * itemsPerPage;
        List<UserEntity> userList;
        try {
            userList = userDAO.readUsers(offset, itemsPerPage);
            return userDomainBuilder.create(userList);
        } catch (DAOException e) {
            LOG.error(Messages.ERR_SERVICE_LAYER_CANNOT_READ_USERS_WITH_LIMITATION, e);
        } catch (BuilderException e) {
            LOG.error(Messages.ERR_CANNOT_MAP_USER, e);
        }
        return null;
    }
}
