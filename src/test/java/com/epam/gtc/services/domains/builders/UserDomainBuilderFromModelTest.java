package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.web.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UserDomainBuilderFromModelTest {
    private UserDomainBuilderFromModel domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new UserDomainBuilderFromModel();
    }

    @Test
    void create() {
        UserModel model = getModel();
        try {
            UserDomain domain = domainBuilder.create(model);
            assertEquals(model.getId(), domain.getId());
            assertEquals(model.getName(), domain.getName());
            assertEquals(model.getSurname(), domain.getSurname());
            assertEquals(model.getRoleName(), domain.getRole().getName());
            assertEquals(model.getEmail(), domain.getEmail());
            assertEquals(model.getPassword(), domain.getPassword());
            assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<UserModel> models = Arrays.asList(getModel());
        try {
            List<UserDomain> domains = domainBuilder.create(models);
            for (UserModel model : models) {
                UserDomain domain = domains.stream().
                        filter(item -> model.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(model.getId(), domain.getId());
                assertEquals(model.getName(), domain.getName());
                assertEquals(model.getSurname(), domain.getSurname());
                assertEquals(model.getRoleName(), domain.getRole().getName());
                assertEquals(model.getEmail(), domain.getEmail());
                assertEquals(model.getPassword(), domain.getPassword());
                assertEquals(LocalDateTime.ofInstant(model.getCreatedDate().toInstant(), ZoneId.systemDefault()), domain.getCreatedDate());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private UserModel getModel() {
        UserModel model = new UserModel();
        model.setId(1);
        model.setName("name");
        model.setSurname("surname");
        model.setRoleName(Role.ADMIN.getName());
        model.setEmail("email@email.com");
        model.setPassword("password");
        model.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        return model;
    }
}