package com.epam.gtc.services;

import com.epam.gtc.dao.DistanceDAO;
import com.epam.gtc.dao.entities.DistanceEntity;
import com.epam.gtc.dao.entities.builders.DistanceEntityBuilder;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.DistanceDomain;
import com.epam.gtc.services.domains.builders.DistanceDomainBuilderFromEntity;
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

class DistanceServiceImplTest {

    @Mock
    private DistanceDAO dao;
    @Mock
    private DistanceEntityBuilder entityBuilder;
    @Mock
    private DistanceDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private DistanceServiceImpl service;

    private DistanceDomain domain = getDomain();

    private DistanceEntity entity = getEntity();

    private List<DistanceEntity> entities = new ArrayList<>();

    private List<DistanceDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new DistanceServiceImpl(dao, entityBuilder, domainBuilder);
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(DistanceDomain.class));
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(DistanceEntity.class));
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(DistanceDomain.class));
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
            when(dao.readDistances(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<DistanceDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readDistances(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readDistances(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private DistanceDomain getDomain() {
        DistanceDomain item = new DistanceDomain();
        item.setDistance(1);
        item.setId(1);
        item.setFromCityId(1);
        item.setToCityId(1);
        return item;
    }

    private DistanceEntity getEntity() {
        DistanceEntity item = new DistanceEntity();
        item.setDistance(1);
        item.setId(1);
        item.setFromCityId(1);
        item.setToCityId(1);
        return item;
    }


    @Test
    void findAllFrom() {
        try {
            when(dao.readAll(anyInt())).thenReturn(new ArrayList<>());
            List<DistanceDomain> list = service.findAll(1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllFrom_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readAll(anyInt());
            service.findAll(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllFrom_throws_builder_exception() {
        try {
            when(dao.readAll(anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllDistance() {
        try {
            when(dao.readAll(anyDouble())).thenReturn(new ArrayList<>());
            List<DistanceDomain> list = service.findAll(1D);
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


    @Test
    void countAllDistances() {
        try {
            int expected = 1;
            when(dao.countAllDistances()).thenReturn(expected);
            int actual = service.countAllDistances();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllDistances_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllDistances();
            service.countAllDistances();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findFromTo() {
        try {
            when(dao.read(anyInt(), anyInt())).thenReturn(entity);
            DistanceDomain domain = service.find(1, 1);
            assertEquals(entity.getId(), domain.getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findFromTo_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).read(anyInt(), anyInt());
            service.find(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findFromTo_throws_builder_exception() {
        try {
            when(dao.read(anyInt(), anyInt())).thenReturn(entity);
            doThrow(BuilderException.class).when(domainBuilder).create(any(entity.getClass()));
            service.find(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

}