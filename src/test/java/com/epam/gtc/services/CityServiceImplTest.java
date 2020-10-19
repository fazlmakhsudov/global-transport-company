package com.epam.gtc.services;

import com.epam.gtc.dao.CityDAO;
import com.epam.gtc.dao.entities.CityEntity;
import com.epam.gtc.dao.entities.builders.CityEntityBuilder;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.CityDomain;
import com.epam.gtc.services.domains.builders.CityDomainBuilderFromEntity;
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

class CityServiceImplTest {
    @Mock
    private CityDAO cityDAO;
    @Mock
    private CityEntityBuilder cityEntitybuilder;
    @Mock
    private CityDomainBuilderFromEntity cityDomainBuilder;

    @InjectMocks
    private CityServiceImpl cityService;

    private CityDomain domain;

    private CityEntity entity;

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        cityService = new CityServiceImpl(cityDAO, cityEntitybuilder, cityDomainBuilder);
        List<CityEntity> entityList = new ArrayList<>();
        List<CityDomain> domains = new ArrayList<>();
        domain = new CityDomain();
        entity = new CityEntity();
        domain.setId(1);
        domain.setName("name");
        entity.setId(1);
        entity.setName("name");
        when(cityEntitybuilder.create(domains)).thenReturn(entityList);
        when(cityEntitybuilder.create(any(CityDomain.class))).thenReturn(entity);
        when(cityDomainBuilder.create(entityList)).thenReturn(domains);
        when(cityDomainBuilder.create(any(CityEntity.class))).thenReturn(domain);
    }

    @Test
    void add() {
        try {
            when(cityDAO.create(any(entity.getClass()))).thenReturn(1);
            int expected = 1;
            int actual = cityService.add(domain);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void add_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).create(any(entity.getClass()));
            cityService.add(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void add_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(cityEntitybuilder).create(any(CityDomain.class));
            cityService.add(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }


    @Test
    void find() {
        try {
            when(cityDAO.read(anyInt())).thenReturn(entity);
            CityDomain actual = cityService.find(1);
            assertEquals(entity.getId(), actual.getId());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void find_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).read(anyInt());
            cityService.find(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void find_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(cityDomainBuilder).create(any(CityEntity.class));
            when(cityDAO.read(anyInt())).thenReturn(entity);
            cityService.find(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findByName() {
        try {
            when(cityDAO.read(anyString())).thenReturn(entity);
            CityDomain actual = cityService.find("name");
            assertEquals(entity.getName(), actual.getName());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findByName_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).read(anyString());
            cityService.find("name");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findByName_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(cityDomainBuilder).create(any(CityEntity.class));
            when(cityDAO.read(anyString())).thenReturn(entity);
            cityService.find("name");
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void save() {
        try {
            when(cityDAO.update(any(entity.getClass()))).thenReturn(true);
            boolean actual = cityService.save(domain);
            assertEquals(true, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void save_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).update(any(entity.getClass()));
            cityService.save(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void save_throws_builder_exception() {
        try {
            doThrow(BuilderException.class).when(cityEntitybuilder).create(any(CityDomain.class));
            cityService.save(domain);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void remove() {
        try {
            when(cityDAO.delete(anyInt())).thenReturn(true);
            boolean actual = cityService.remove(1);
            assertEquals(true, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void remove_throws_dao_exception() {
        try {
            when(cityDAO.delete(anyInt())).thenThrow(DAOException.class);
            cityService.remove(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAll() {
        try {
            when(cityDAO.readAll()).thenReturn(new ArrayList<>());
            List<CityDomain> list = cityService.findAll();
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAll_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).readAll();
            cityService.findAll();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAll_throws_builder_exception() {
        try {
            when(cityDAO.readAll()).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(cityDomainBuilder).create(anyList());
            cityService.findAll();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countAllCities() {
        try {
            int expected = 1;
            when(cityDAO.countAllCities()).thenReturn(expected);
            int actual = cityService.countAllCities();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllCities_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).countAllCities();
            cityService.countAllCities();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit() {
        try {
            when(cityDAO.readCities(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<CityDomain> list = cityService.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(cityDAO).readCities(anyInt(), anyInt());
            cityService.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(cityDAO.readCities(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(cityDomainBuilder).create(anyList());
            cityService.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}