package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.RequestService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminMainPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestService requestService;
    @Mock
    private InvoiceService invoiceService;
    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private AdminMainPageCommand command;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new AdminMainPageCommand(requestService, invoiceService, deliveryService);
        when(request.getSession()).thenReturn(session);
    }


    @Test
    void execute_get() throws AppException {
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_ADMIN_HOME, forward);
    }

    @Test
    void execute_throws_exception() throws ServiceException {
        when(invoiceService.countAllInvoices()).thenThrow(ServiceException.class);
        when(request.getMethod()).thenReturn("POST");
        doNothing().when(request).setAttribute("errorMessage", null);
        command.execute(request, response);
        verify(request, times(1)).setAttribute("errorMessage", null);

    }
}