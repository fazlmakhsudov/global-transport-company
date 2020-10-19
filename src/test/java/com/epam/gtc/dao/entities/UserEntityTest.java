package com.epam.gtc.dao.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserEntityTest {
    private UserEntity item;

    @BeforeEach
    void setUp() {
        item = new UserEntity();
    }

    @Test
    void getId() {
        int expected = 1;
        item.setId(expected);
        assertEquals(expected, item.getId());
    }

    @Test
    void getName() {
        String expected = "name";
        item.setName(expected);
        assertEquals(expected, item.getName());
    }

    @Test
    void getSurname() {
        String expected = "name";
        item.setSurname(expected);
        assertEquals(expected, item.getSurname());
    }

    @Test
    void getEmail() {
        String expected = "name";
        item.setEmail(expected);
        assertEquals(expected, item.getEmail());
    }

    @Test
    void getPassword() {
        String expected = "name";
        item.setPassword(expected);
        assertEquals(expected, item.getPassword());
    }

    @Test
    void getRoleId() {
        int expected = 1;
        item.setRoleId(expected);
        assertEquals(expected, item.getRoleId());
    }

//    @Test
//    void getCreatedDate() {
//        Timestamp expected = Timestamp.from(Instant.now());
//        item.setCreatedDate(expected);
//        assertEquals(expected, item.getCreatedDate());
//    }
}