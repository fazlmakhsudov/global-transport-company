package com.epam.gtc.services;

import com.epam.gtc.dao.RequestDAO;
import com.epam.gtc.dao.entities.RequestEntity;
import com.epam.gtc.dao.entities.builders.RequestEntityBuilder;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.BuilderException;
import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.services.domains.RequestDomain;
import com.epam.gtc.services.domains.builders.RequestDomainBuilderFromEntity;
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

class RequestServiceImplTest {
    @Mock
    private RequestDAO dao;
    @Mock
    private RequestEntityBuilder entityBuilder;
    @Mock
    private RequestDomainBuilderFromEntity domainBuilder;

    @InjectMocks
    private RequestServiceImpl service;

    private RequestDomain domain = getDomain();

    private RequestEntity entity = getEntity();

    private List<RequestEntity> entities = new ArrayList<>();

    private List<RequestDomain> domains = new ArrayList<>();

    @BeforeEach
    void setUp() throws BuilderException {
        MockitoAnnotations.openMocks(this);
        service = new RequestServiceImpl(dao, entityBuilder, domainBuilder);
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(RequestDomain.class));
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
            doThrow(BuilderException.class).when(domainBuilder).create(any(RequestEntity.class));
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
            doThrow(BuilderException.class).when(entityBuilder).create(any(RequestDomain.class));
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
            when(dao.readRequests(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<RequestDomain> list = service.findAll(1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimit_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readRequests(anyInt(), anyInt());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimit_throws_builder_exception() {
        try {
            when(dao.readRequests(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    private RequestDomain getDomain() {
        RequestDomain item = new RequestDomain();
        item.setId(1);
        item.setUserId(1);
        item.setCityToId(1);
        item.setCityFromId(2);
        item.setContentType(ContentType.PARCEL_POST);
        item.setRequestStatus(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
        return item;
    }

    private RequestEntity getEntity() {
        RequestEntity item = new RequestEntity();
        item.setId(1);
        item.setUserId(1);
        item.setCityToId(1);
        item.setCityFromId(2);
        item.setContentTypeId(ContentType.PARCEL_POST.ordinal() + 1);
        item.setRequestStatusId(RequestStatus.WAITING_FOR_MANAGER_REVIEW.ordinal() + 1);
        return item;
    }


    @Test
    void countAllRequests() {
        try {
            int expected = 1;
            when(dao.countAllRequests()).thenReturn(expected);
            int actual = service.countAllRequests();
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countAllRequests_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countAllRequests();
            service.countAllRequests();
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countUserRequests() {
        try {
            int expected = 1;
            when(dao.countUserRequests(anyInt())).thenReturn(expected);
            int actual = service.countUserRequests(1);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countUserRequests_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countUserRequests(anyInt());
            service.countUserRequests(1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void countRequests() {
        try {
            int expected = 1;
            when(dao.countRequests(anyInt())).thenReturn(expected);
            int actual = service.countRequests(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void countRequests_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).countRequests(anyInt());
            service.countRequests(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimitUser() {
        try {
            when(dao.readRequests(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            List<RequestDomain> list = service.findAll(1, 1, 1);
            assertTrue(list.isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void findAllPageLimitUser_throws_dao_exception() {
        try {
            doThrow(DAOException.class).when(dao).readRequests(anyInt(), anyInt(), anyInt());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void findAllPageLimitUser_throws_builder_exception() {
        try {
            when(dao.readRequests(anyInt(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
            doThrow(BuilderException.class).when(domainBuilder).create(anyList());
            service.findAll(1, 1, 1);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void testFindAll1() {
    }
}