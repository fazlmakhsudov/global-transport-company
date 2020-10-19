package com.epam.gtc.services;

import com.epam.gtc.dao.DeliveryDAO;
import com.epam.gtc.dao.entities.DeliveryEntity;
import com.epam.gtc.dao.entities.builders.DeliveryEntityBuilder;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.DeliveryDomain;
import com.epam.gtc.services.domains.builders.DeliveryDomainBuilderFromEntity;
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

class DeliveryServiceImplTest {

    @Mock
    private DeliveryDAO dao;
    @Mock
    private DeliveryEntityBuilder entityBuilder;
    @Mock
    private DeliveryDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private DeliveryServiceImpl service;

    private DeliveryDomain domain = getDomain();

    private DeliveryEntity entity = getEntity();

    private List<DeliveryEntity> entities = new ArrayList<>();

    private List<DeliveryDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new DeliveryServiceImpl(dao, entityBuilder, domainBuilder);
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(DeliveryDomain.class));
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(DeliveryEntity.class));
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(DeliveryDomain.class));
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
    void countAllDeliveries() {
        try {
            int expected = 1;
            when(dao.countAllDeliveries()).thenReturn(expected);
            int actual = service.countAllDeliveries();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllDeliveries_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllDeliveries();
            service.countAllDeliveries();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countUserDeliveries() {
        try {
            int expected = 1;
            when(dao.countUserDeliveries(anyInt())).thenReturn(expected);
            int actual = service.countUserDeliveries(1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countUserDeliveries_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countUserDeliveries(anyInt());
            service.countUserDeliveries(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countDeliveries() {
        try {
            int expected = 1;
            when(dao.countDeliveries(anyInt())).thenReturn(expected);
            int actual = service.countDeliveries(DeliveryStatus.WAITING_FOR_PACKAGING);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countDeliveries_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countDeliveries(anyInt());
            service.countDeliveries(DeliveryStatus.WAITING_FOR_PACKAGING);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countDeliveriesOfRequest() {
        try {
            int expected = 1;
            when(dao.countDeliveriesOfRequest(anyInt())).thenReturn(expected);
            int actual = service.countDeliveriesOfRequest(1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countDeliveriesOfRequest_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countDeliveriesOfRequest(anyInt());
            service.countDeliveriesOfRequest(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit() {
        try {
            when(dao.readDeliveries(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<DeliveryDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readDeliveries(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readDeliveries(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllUserPageLimit() {
        try {
            when(dao.readDeliveries(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<DeliveryDomain> list = service.findAll(1, 1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllUserPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readDeliveries(anyInt(), anyInt(), anyInt());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllUserPageLimit_throws_builder_exception() {
        try {
            when(dao.readDeliveries(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private DeliveryDomain getDomain() {
        DeliveryDomain item = new DeliveryDomain();
        item.setRequestId(1);
        item.setId(1);
        item.setDeliveryStatus(DeliveryStatus.WAITING_FOR_PACKAGING);
        return item;
    }

    private DeliveryEntity getEntity() {
        DeliveryEntity item = new DeliveryEntity();
        item.setRequestId(1);
        item.setId(1);
        item.setDeliveryStatusId(DeliveryStatus.WAITING_FOR_PACKAGING.ordinal() + 1);
        return item;
    }
}