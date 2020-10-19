package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.CityService;
import com.epam.gtc.services.DistanceService;
import com.epam.gtc.services.RateService;
import com.epam.gtc.services.domains.RateDomain;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RatesCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private CityService cityService;

    @Mock
    private DistanceService distanceService;

    @Mock
    private RateService rateService;

    @InjectMocks
    private RatesCommand command;


    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new RatesCommand(cityService, distanceService, rateService);
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
        assertEquals(Path.PAGE_RATES, forward);
    }

    @Test
    void execute_get_ratesFlag() throws ServiceException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter(FormRequestParametersNames.RATE_NAME)).thenReturn("true");
        when(rateService.find(anyString())).thenReturn(new RateDomain());
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_RATES, forward);
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