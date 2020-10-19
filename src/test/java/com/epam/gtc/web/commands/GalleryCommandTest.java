package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GalleryCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private GalleryCommand command;


    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new GalleryCommand(cityService, distanceService);
    }

    @Test
    void execute_post() {
        when(request.getMethod()).thenReturn("POST");
        String forward = command.execute(request, response);
        assertEquals(Path.COMMAND_INDEX, forward);
    }

    @Test
    void execute_get() {
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_GALLERY, forward);
    }

    @Test
    void execute_throws_exception() {
        try {
            when(cityService.findAll()).thenThrow(ServiceException.class);
            when(request.getMethod()).thenReturn("GET");
            command.execute(request, response);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}