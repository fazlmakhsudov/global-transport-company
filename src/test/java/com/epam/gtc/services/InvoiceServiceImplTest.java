package com.epam.gtc.services;

import com.epam.gtc.dao.InvoiceDAO;
import com.epam.gtc.dao.entities.InvoiceEntity;
import com.epam.gtc.dao.entities.builders.InvoiceEntityBuilder;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.services.domains.builders.InvoiceDomainBuilderFromEntity;
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

class InvoiceServiceImplTest {
    @Mock
    private InvoiceDAO dao;
    @Mock
    private InvoiceEntityBuilder entityBuilder;
    @Mock
    private InvoiceDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private InvoiceServiceImpl service;

    private InvoiceDomain domain = getDomain();

    private InvoiceEntity entity = getEntity();

    private List<InvoiceEntity> entities = new ArrayList<>();

    private List<InvoiceDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new InvoiceServiceImpl(dao, entityBuilder, domainBuilder);
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(InvoiceDomain.class));
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(InvoiceEntity.class));
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(InvoiceDomain.class));
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
            when(dao.readInvoices(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<InvoiceDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readInvoices(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readInvoices(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private InvoiceDomain getDomain() {
        InvoiceDomain item = new InvoiceDomain();
        item.setId(1);
        item.setCost(1D);
        item.setRequestId(1);
        return item;
    }

    private InvoiceEntity getEntity() {
        InvoiceEntity item = new InvoiceEntity();
        item.setId(1);
        item.setCost(1D);
        item.setRequestId(1);
        return item;
    }


    @Test
    void countAllInvoices() {
        try {
            int expected = 1;
            when(dao.countAllInvoices()).thenReturn(expected);
            int actual = service.countAllInvoices();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllInvoices_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllInvoices();
            service.countAllInvoices();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimitUser() {
        try {
            when(dao.readInvoices(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<InvoiceDomain> list = service.findAll(1, 1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimitUser_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readInvoices(anyInt(), anyInt(), anyInt());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimitUser_throws_builder_exception() {
        try {
            when(dao.readInvoices(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countUserInvoices() {
        try {
            int expected = 1;
            when(dao.countUserInvoices(anyInt())).thenReturn(expected);
            int actual = service.countUserInvoices(1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countUserInvoices_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countUserInvoices(anyInt());
            service.countUserInvoices(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countInvoices() {
        try {
            int expected = 1;
            when(dao.countInvoices(anyInt())).thenReturn(expected);
            int actual = service.countInvoices(InvoiceStatus.PAID);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countDeliveries_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countInvoices(anyInt());
            service.countInvoices(InvoiceStatus.PAID);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

}