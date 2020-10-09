package com.epam.gtc.web.models.builders;

import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.builders.Builder;
import com.epam.gtc.web.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModelBuilder extends Builder<UserDomain, UserModel> {
    public UserModel create(UserDomain user) throws BuilderException {
        return build(user, UserModel.class);
    }


    public List<UserModel> create(List<UserDomain> userList) throws BuilderException {
        List<UserModel> userModels = new ArrayList<>();
        for (UserDomain userDomain : userList) {
            UserModel userModel = create(userDomain);
            userModels.add(userModel);
        }
        return userModels;
    }
}
