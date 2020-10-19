package com.epam.gtc.web.models.builders;

import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.web.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UserModelBuilderTest {
    private UserModelBuilder modelBuilder;


    @BeforeEach
    void setUp() {
        modelBuilder = new UserModelBuilder();
    }

    @Test
    void create() {
        UserDomain domain = getDomain();
        try {
            UserModel model = modelBuilder.create(domain);
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
        List<UserDomain> domains = Arrays.asList(getDomain());
        try {
            List<UserModel> models = modelBuilder.create(domains);
            for (UserDomain domain : domains) {
                UserModel model = models.stream().
                        filter(item -> domain.getId() == item.getId()).
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

    private UserDomain getDomain() {
        UserDomain domain = new UserDomain();
        domain.setId(1);
        domain.setName("name");
        domain.setSurname("surname");
        domain.setRole(Role.USER);
        domain.setEmail("email@email.com");
        domain.setPassword("password");
        domain.setCreatedDate(LocalDateTime.now());
        return domain;
    }
}