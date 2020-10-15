package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) UserEntity object from UserDomain object
 *
 * @author Fazliddin Makhsudov
 */
public class UserEntityBuilder extends Builder<UserDomain, UserEntity> {
    public UserEntity create(UserDomain user) throws BuilderException {
        return build(user, UserEntity.class);
    }

    public List<UserEntity> create(List<UserDomain> userList) throws BuilderException {
        List<UserEntity> userEntities = new ArrayList<>();
        for (UserDomain userDomain : userList) {
            UserEntity userEntity = create(userDomain);
            userEntities.add(userEntity);
        }
        return userEntities;
    }
}
