package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.UserService;
import com.epam.gtc.web.models.UserModel;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SignupCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @InjectMocks
    private SignupCommand command;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new SignupCommand(userService);
    }

    @Test
    void execute_post() {
        UserModel userModel = new UserModel();
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any(String.class))).thenReturn(userModel);
        when(request.getMethod()).thenReturn("POST");
        String forward = command.execute(request, response);
        assertEquals(Path.COMMAND_INDEX, forward);
    }

    @Test
    void execute_get() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(any(String.class))).thenReturn(null);
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_SIGNUP, forward);
    }

    @Test
    void execute_throws_exception() {
        try {
            when(userService.findAll()).thenThrow(ServiceException.class);
            when(request.getMethod()).thenReturn("GET");
            command.execute(request, response);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}