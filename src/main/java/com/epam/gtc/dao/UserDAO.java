package com.epam.gtc.dao;


import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.exceptions.DAOException;

import java.util.List;

public interface UserDAO extends BaseDAO<UserEntity> {
    UserEntity read(final String email) throws DAOException;

    int countAllUsers();

    List<UserEntity> readUsers(int offset, int limit) throws DAOException;
}
