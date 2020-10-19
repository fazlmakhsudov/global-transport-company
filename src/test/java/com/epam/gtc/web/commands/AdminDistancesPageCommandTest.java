package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.domains.DistanceDomain;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class AdminDistancesPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private DistanceService distanceService;

    @Mock
    private CityService cityService;

    @InjectMocks
    private AdminDistancesPageCommand command;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new AdminDistancesPageCommand(distanceService, cityService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void execute_post_add() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("add");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_DISTANCE)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_FROM_CITY_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_TO_CITY_ID)).thenReturn("1");
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_DISTANCES_PAGE));
    }


    @Test
    void execute_post_remove() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("remove");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_DISTANCE)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_FROM_CITY_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_TO_CITY_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_ID)).thenReturn("1");
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_DISTANCES_PAGE));
    }

    @Test
    void execute_post_save() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("save");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_DISTANCE)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_FROM_CITY_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_TO_CITY_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.DISTANCE_ID)).thenReturn("1");
        when(distanceService.find(anyInt())).thenReturn(new DistanceDomain());
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_DISTANCES_PAGE));
    }

    @Test
    void execute_get() throws AppException {
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_ADMIN_DISTANCES, forward);
    }

    @Test
    void execute_throws_exception() {
        try {
            when(distanceService.countAllDistances()).thenThrow(ServiceException.class);
            when(request.getMethod()).thenReturn("POST");
            command.execute(request, response);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}