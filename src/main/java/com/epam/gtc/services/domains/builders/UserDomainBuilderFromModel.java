package com.epam.gtc.services.domains.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Builder;
import com.epam.gtc.web.models.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates(maps) UserDomain object from UserModel object
 *
 * @author Fazliddin Makhsudov
 */
public class UserDomainBuilderFromModel extends Builder<UserModel, UserDomain> {
    public UserDomain create(UserModel user) throws BuilderException {
        return build(user, UserDomain.class);
    }

    public List<UserDomain> create(List<UserModel> userList) throws BuilderException {
        List<UserDomain> userDomains = new ArrayList<>();
        for (UserModel userModel : userList) {
            UserDomain userDomain = create(userModel);
            userDomains.add(userDomain);
        }
        return userDomains;
    }
}
