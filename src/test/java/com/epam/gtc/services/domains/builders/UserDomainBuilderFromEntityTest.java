package com.epam.gtc.services.domains.builders;

import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.services.domains.UserDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class UserDomainBuilderFromEntityTest {
    private UserDomainBuilderFromEntity domainBuilder;


    @BeforeEach
    void setUp() {
        domainBuilder = new UserDomainBuilderFromEntity();
    }

    @Test
    void create() {
        UserEntity entity = getEntity();
        try {
            UserDomain domain = domainBuilder.create(entity);
            assertEquals(entity.getId(), domain.getId());
            assertEquals(entity.getName(), domain.getName());
            assertEquals(entity.getSurname(), domain.getSurname());
            assertEquals(entity.getRoleId(), domain.getRole().ordinal() + 1);
            assertEquals(entity.getEmail(), domain.getEmail());
            assertEquals(entity.getPassword(), domain.getPassword());
//            assertEquals(domain.getCreatedDate(), entity.getCreatedDate().toLocalDateTime());
        } catch (BuilderException e) {
            fail();
        }
    }

    @Test
    void createList() {
        List<UserEntity> entities = Arrays.asList(getEntity());
        try {
            List<UserDomain> domains = domainBuilder.create(entities);
            for (UserEntity entity : entities) {
                UserDomain domain = domains.stream().
                        filter(item -> entity.getId() == item.getId()).
                        findAny().orElseThrow(BuilderException::new);
                assertEquals(entity.getId(), domain.getId());
                assertEquals(entity.getName(), domain.getName());
                assertEquals(entity.getSurname(), domain.getSurname());
                assertEquals(entity.getRoleId(), domain.getRole().ordinal() + 1);
                assertEquals(entity.getEmail(), domain.getEmail());
                assertEquals(entity.getPassword(), domain.getPassword());
//                assertEquals(domain.getCreatedDate(), entity.getCreatedDate().toLocalDateTime());
            }
        } catch (BuilderException e) {
            fail();
        }
    }

    private UserEntity getEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setName("name");
        entity.setSurname("surname");
        entity.setRoleId(Role.MANAGER.ordinal() + 1);
        entity.setEmail("email@email.com");
        entity.setPassword("password");
//        entity.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        return entity;
    }
}