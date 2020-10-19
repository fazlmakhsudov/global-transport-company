package com.epam.gtc.dao.entities.builders;

import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityBuilderTest {
    private UserEntityBuilder entityBuilder;


    @BeforeEach
    void setUp() {
        entityBuilder = new UserEntityBuilder();
    }

    @Test
    void create() {
        UserDomain domain = getDomain();
        try {
            UserEntity entity = entityBuilder.create(domain);
            assertEquals(domain.getId(), entity.getId());
            assertEquals(domain.getName(), entity.getName());
            assertEquals(domain.getSurname(), entity.getSurname());
            assertEquals(domain.getRole().ordinal() + 1, entity.getRoleId());
            assertEquals(domain.getEmail(), entity.getEmail());
            assertEquals(domain.getPassword(), entity.getPassword());
//            assertEquals(domain.getCreatedDate(), entity.getCreatedDate().toLocalDateTime());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<UserDomain> domains = Arrays.asList(getDomain());
        try {
            List<UserEntity> entities = entityBuilder.create(domains);
            for (UserDomain domain : domains) {
                UserEntity entity = entities.stream().
                        filter(item -> domain.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(domain.getId(), entity.getId());
                assertEquals(domain.getName(), entity.getName());
                assertEquals(domain.getSurname(), entity.getSurname());
                assertEquals(domain.getRole().ordinal() + 1, entity.getRoleId());
                assertEquals(domain.getEmail(), entity.getEmail());
                assertEquals(domain.getPassword(), entity.getPassword());
//                assertEquals(domain.getCreatedDate(), entity.getCreatedDate().toLocalDateTime());
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
        domain.setCreatedDate(LocalDateTime.MIN);
        return domain;
    }

}