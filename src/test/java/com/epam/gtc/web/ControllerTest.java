package com.epam.gtc.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private Controller controller;


    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        controller = new Controller();
        when(request.getParameter("command")).thenReturn("noCommand");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet() throws ServletException, IOException {
        controller.doGet(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    void doPost() throws ServletException, IOException {
        controller.doPost(request, response);
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}