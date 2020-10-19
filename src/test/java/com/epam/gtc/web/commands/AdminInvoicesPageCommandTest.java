package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.domains.InvoiceDomain;
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

class AdminInvoicesPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private AdminInvoicesPageCommand command;

    @BeforeEach
    void setUp() throws ServletException, IOException {
        MockitoAnnotations.openMocks(this);
        command = new AdminInvoicesPageCommand(invoiceService);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void execute_post_add() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("add");
        when(request.getParameter(FormRequestParametersNames.INVOICE_STATUS_NAME)).thenReturn(InvoiceStatus.PAID.name());
        when(request.getParameter(FormRequestParametersNames.INVOICE_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.INVOICE_COST)).thenReturn("10");
        when(request.getParameter(FormRequestParametersNames.INVOICE_REQUEST_ID)).thenReturn("1");
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_INVOICES_PAGE));
    }


    @Test
    void execute_post_remove() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("remove");
        when(request.getParameter(FormRequestParametersNames.INVOICE_STATUS_NAME)).thenReturn(InvoiceStatus.PAID.name());
        when(request.getParameter(FormRequestParametersNames.INVOICE_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.INVOICE_COST)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.INVOICE_REQUEST_ID)).thenReturn("1");
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_INVOICES_PAGE));
    }

    @Test
    void execute_post_save() throws AppException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(FormRequestParametersNames.ACTION)).thenReturn("save");
        when(request.getParameter(FormRequestParametersNames.INVOICE_STATUS_NAME)).thenReturn(InvoiceStatus.PAID.name());
        when(request.getParameter(FormRequestParametersNames.INVOICE_ID)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.INVOICE_COST)).thenReturn("1");
        when(request.getParameter(FormRequestParametersNames.INVOICE_REQUEST_ID)).thenReturn("1");
        when(invoiceService.find(anyInt())).thenReturn(new InvoiceDomain());
        String forward = command.execute(request, response);
        assertTrue(forward.contains(Path.COMMAND_ADMIN_INVOICES_PAGE));
    }

    @Test
    void execute_get() throws AppException {
        when(request.getMethod()).thenReturn("GET");
        String forward = command.execute(request, response);
        assertEquals(Path.PAGE_ADMIN_INVOICES, forward);
    }

    @Test
    void execute_throws_exception() {
        try {
            when(invoiceService.countAllInvoices()).thenThrow(ServiceException.class);
            when(request.getMethod()).thenReturn("POST");
            command.execute(request, response);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}