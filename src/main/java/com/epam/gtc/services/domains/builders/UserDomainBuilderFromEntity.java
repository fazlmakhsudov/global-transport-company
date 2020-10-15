package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) UserDomain object from UserEntity object
 *
 * @author Fazliddin Makhsudov
 */
public class UserDomainBuilderFromEntity extends Builder<UserEntity, UserDomain> {
    public UserDomain create(UserEntity user) throws BuilderException {
        return build(user, UserDomain.class);
    }

    public List<UserDomain> create(List<UserEntity> userList) throws BuilderException {
        List<UserDomain> userDomains = new ArrayList<>();
        for (UserEntity userEntity : userList) {
            UserDomain userDomain = create(userEntity);
            userDomains.add(userDomain);
        }
        return userDomains;
    }
}
