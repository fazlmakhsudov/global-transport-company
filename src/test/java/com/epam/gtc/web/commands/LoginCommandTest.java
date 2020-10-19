package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.Role;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.UserService;
import com.epam.gtc.services.domains.UserDomain;
import com.epam.gtc.utils.Fields;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoginCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginCommand command;


    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new LoginCommand(userService);
        when(request.getSession()).thenReturn(session);
    }
//
//    @Test
//    void execute_post_success_user() throws AppException {
//        when(request.getMethod()).thenReturn("POST");
//        when(request.getParameter(Fields.USER_EMAIL)).thenReturn("test@test.com");
//        when(request.getParameter(Fields.USER_PASSWORD)).thenReturn("111111");
//        when(userService.find("test@test.com")).thenReturn(getUserDomain(Role.USER));
//        String forward = command.execute(request, response);
//        assertEquals(Path.COMMAND_USER_CABINET, forward);
//    }
//
//    @Test
//    void execute_post_success_manager() throws AppException {
//        when(request.getMethod()).thenReturn("POST");
//        when(request.getParameter(Fields.USER_EMAIL)).thenReturn("test@test.com");
//        when(request.getParameter(Fields.USER_PASSWORD)).thenReturn("111111");
//        when(userService.find(anyString())).thenReturn(getUserDomain(Role.MANAGER));
//        String forward = command.execute(request, response);
//        assertEquals(Path.COMMAND_ADMIN_MAIN_PAGE, forward);
//    }
//
//    @Test
//    void execute_post_success_admin() throws AppException {
//        when(request.getMethod()).thenReturn("POST");
//        when(request.getParameter(Fields.USER_EMAIL)).thenReturn("test@test.com");
//        when(request.getParameter(Fields.USER_PASSWORD)).thenReturn("111111");
//        when(userService.find(anyString())).thenReturn(getUserDomain(Role.ADMIN));
//        String forward = command.execute(request, response);
//        assertEquals(Path.COMMAND_ADMIN_MAIN_PAGE, forward);
//    }
//
//    UserDomain getUserDomain(Role role) {
//        UserDomain domain = new UserDomain();
//        domain.setRole(role);
//        domain.setName("name");
//        domain.setId(1);
//        domain.setEmail("test@test.com");
//        domain.setPassword("96e79218965eb72c92a549dd5a330112");
//        return domain;
//    }

    @Test
    void execute_get() throws AppException {
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_LOGIN, forward);
    }

    @Test
    void execute_throws_exception() {
        try {
            when(request.getParameter(Fields.USER_EMAIL)).thenReturn("test@test.com");
            when(request.getParameter(Fields.USER_PASSWORD)).thenReturn("pass");
            when(userService.find(anyString())).thenThrow(ServiceException.class);
            when(request.getMethod()).thenReturn("POST");
            command.execute(request, response);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}