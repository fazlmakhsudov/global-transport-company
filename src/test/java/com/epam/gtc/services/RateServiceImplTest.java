package com.epam.gtc.services;

import com.epam.gtc.dao.RateDAO;
import com.epam.gtc.dao.entities.RateEntity;
import com.epam.gtc.dao.entities.builders.RateEntityBuilder;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.RateDomain;
import com.epam.gtc.services.domains.builders.RateDomainBuilderFromEntity;
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

class RateServiceImplTest {
    @Mock
    private RateDAO dao;
    @Mock
    private RateEntityBuilder entityBuilder;
    @Mock
    private RateDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private RateServiceImpl service;

    private RateDomain domain = getDomain();

    private RateEntity entity = getEntity();

    private List<RateEntity> entities = new ArrayList<>();

    private List<RateDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new RateServiceImpl(dao, entityBuilder, domainBuilder);
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(RateDomain.class));
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(RateEntity.class));
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(RateDomain.class));
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
            when(dao.readRates(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<RateDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readRates(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readRates(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private RateDomain getDomain() {
        RateDomain item = new RateDomain();
        item.setId(1);
        item.setCost(1D);
        item.setName("name");
        return item;
    }

    private RateEntity getEntity() {
        RateEntity item = new RateEntity();
        item.setId(1);
        item.setCost(1D);
        item.setName("name");
        return item;
    }


    @Test
    void countAllRates() {
        try {
            int expected = 1;
            when(dao.countAllRates()).thenReturn(expected);
            int actual = service.countAllRates();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllRates_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllRates();
            service.countAllRates();
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(RateEntity.class));
            when(dao.read(anyString())).thenReturn(entity);
            service.find("name");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllDistance() {
        try {
            when(dao.readAll(anyDouble())).thenReturn(new ArrayList<>());
            List<RateDomain> list = service.findAll(1D);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllDistance_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readAll(anyDouble());
            service.findAll(1D);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllDistance_throws_builder_exception() {
        try {
            when(dao.readAll(anyDouble())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1D);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}