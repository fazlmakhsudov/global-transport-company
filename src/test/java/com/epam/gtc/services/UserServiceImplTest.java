package com.epam.gtc.services;

import com.epam.gtc.dao.UserDAO;
import com.epam.gtc.dao.entities.UserEntity;
import com.epam.gtc.dao.entities.builders.UserEntityBuilder;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.services.domains.builders.UserDomainBuilderFromEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserDAO dao;
    @Mock
    private UserEntityBuilder entityBuilder;
    @Mock
    private UserDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private UserServiceImpl service;

    private UserDomain domain = getDomain();

    private UserEntity entity = getEntity();

    private List<UserEntity> entities = new ArrayList<>();

    private List<UserDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new UserServiceImpl(dao, entityBuilder, domainBuilder);
        when(entityBuilder.create(domains)).thenReturn(entities);
        when(entityBuilder.create(domain)).thenReturn(entity);
        when(domainBuilder.create(entities)).thenReturn(domains);
        when(domainBuilder.create(entity)).thenReturn(domain);
    }

    @Test
    void add() {
        try {
            when(dao.create(any(entity.getClass()))).thenReturn(1);
            int expected = 1;
            int actual = service.add(domain);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void add_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).create(any(entity.getClass()));
            service.add(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void add_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(entityBuilder).create(any(UserDomain.class));
            service.add(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void find() {
        try {
            when(dao.read(anyInt())).thenReturn(entity);
            assertEquals(entity.getId(), service.find(1).getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void find_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).read(anyInt());
            service.find(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void find_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(domainBuilder).create(any(UserEntity.class));
            when(dao.read(anyInt())).thenReturn(entity);
            service.find(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void save() {
        try {
            when(dao.update(any(entity.getClass()))).thenReturn(true);
            boolean actual = service.save(domain);
            assertEquals(true, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void save_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).update(any(entity.getClass()));
            service.save(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void save_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(entityBuilder).create(any(UserDomain.class));
            service.save(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void remove() {
        try {
            when(dao.delete(anyInt())).thenReturn(true);
            boolean actual = service.remove(1);
            assertEquals(true, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void remove_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).delete(anyInt());
            service.remove(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAll() {
        try {
            when(dao.readAll()).thenReturn(new ArrayList<>());
            assertTrue(service.findAll().isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAll_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readAll();
            service.findAll();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAll_throws_builder_exception() {
        try {
            when(dao.readAll()).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit() {
        try {
            when(dao.readUsers(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<UserDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readUsers(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readUsers(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private UserDomain getDomain() {
        UserDomain item = new UserDomain();
        item.setId(1);
        item.setName("name");
        return item;
    }

    private UserEntity getEntity() {
        UserEntity item = new UserEntity();
        item.setId(1);
        item.setName("name");
        return item;
    }


    @Test
    void countAllUsers() {
        try {
            int expected = 1;
            when(dao.countAllUsers()).thenReturn(expected);
            int actual = service.countAllUsers();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllUsers_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllUsers();
            service.countAllUsers();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findName() {
        try {
            when(dao.read(anyString())).thenReturn(entity);
            assertEquals(entity.getId(), service.find("name").getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findName_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).read(anyString());
            service.find("name");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findName_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(domainBuilder).create(any(UserEntity.class));
            when(dao.read(anyString())).thenReturn(entity);
            service.find("name");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}