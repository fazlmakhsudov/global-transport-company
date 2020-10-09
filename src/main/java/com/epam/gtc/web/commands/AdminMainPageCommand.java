package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.MySQLDeliveryDAOImpl;
import com.epam.gtc.dao.MySQLInvoiceDAOImpl;
import com.epam.gtc.dao.MySQLRequestDAOImpl;
import com.epam.gtc.dao.entities.builders.DeliveryEntityBuilder;
import com.epam.gtc.dao.entities.builders.InvoiceEntityBuilder;
import com.epam.gtc.dao.entities.builders.RequestEntityBuilder;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.services.*;
import com.epam.gtc.services.domains.builders.DeliveryDomainBuilderFromEntity;
import com.epam.gtc.services.domains.builders.InvoiceDomainBuilderFromEntity;
import com.epam.gtc.services.domains.builders.RequestDomainBuilderFromEntity;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Login command.
 */
public class AdminMainPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminMainPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminMainPageCommand starts");
        String forward = Path.PAGE_ADMIN_HOME;
        handleRequest(request);
        LOG.debug("AdminMainPageCommand finished");
        return forward;
    }

    private void handleRequest(final HttpServletRequest request) throws AppException {

        RequestService requestService = new RequestServiceImpl(new MySQLRequestDAOImpl(), new RequestEntityBuilder(),
                new RequestDomainBuilderFromEntity());
        InvoiceService invoiceService = new InvoiceServiceImpl(new MySQLInvoiceDAOImpl(), new InvoiceEntityBuilder(),
                new InvoiceDomainBuilderFromEntity());
        DeliveryService deliveryService = new DeliveryServiceImpl(new MySQLDeliveryDAOImpl(), new DeliveryEntityBuilder(),
                new DeliveryDomainBuilderFromEntity());

        Map<String, List<String>> dashboard = new HashMap<>();

        List<String> listOfStatusData = new ArrayList<>();
        List<String> finalListOfStatusData1 = listOfStatusData;
        Arrays.stream(RequestStatus.values()).forEach(requestStatus -> {
            String row = new StringBuilder(formatStatusName(requestStatus.getName()))
                    .append(" : ")
                    .append(requestService.countRequests(requestStatus))
                    .append(" request(s)").toString();
            finalListOfStatusData1.add(row);
        });
        String allRequests = new StringBuilder("All requests : ")
                .append(requestService.countAllRequests())
                .append(" requests").toString();
        listOfStatusData.add(allRequests);
        dashboard.put("Requests", listOfStatusData);

        listOfStatusData = new ArrayList<>();
        List<String> finalListOfStatusData = listOfStatusData;
        Arrays.stream(InvoiceStatus.values()).forEach(invoiceStatus -> {
            String row = new StringBuilder(formatStatusName(invoiceStatus.getName()))
                    .append(" : ")
                    .append(invoiceService.countInvoices(invoiceStatus))
                    .append(" invoice(s)").toString();
            finalListOfStatusData.add(row);
        });
        String allInvoices = new StringBuilder("All invoices : ")
                .append(requestService.countAllRequests())
                .append(" invoices").toString();
        listOfStatusData.add(allInvoices);
        dashboard.put("Invoices", listOfStatusData);

        listOfStatusData = new ArrayList<>();
        List<String> finalListOfStatusData2 = listOfStatusData;
        Arrays.stream(DeliveryStatus.values()).forEach(deliveryStatus -> {
            String row = new StringBuilder(formatStatusName(deliveryStatus.getName()))
                    .append(" : ")
                    .append(deliveryService.countDeliveries(deliveryStatus))
                    .append(" delivery(ies)").toString();
            finalListOfStatusData2.add(row);
        });
        String allDeliveries = new StringBuilder("All deliveries : ")
                .append(requestService.countAllRequests())
                .append(" deliveries").toString();
        listOfStatusData.add(allDeliveries);
        dashboard.put("Deliveries", listOfStatusData);

        LOG.trace("requests --> " + dashboard.get("Requests"));
        LOG.trace("invoices --> " + dashboard.get("Invoices"));
        LOG.trace("deliveries --> " + dashboard.get("Deliveries"));

        request.setAttribute("dashboard", dashboard);
    }


    private static String formatStatusName(String name) {
        String[] parts = name.split("_");
        name = Arrays.stream(parts).reduce((s1, s2) -> s1.concat(" ").concat(s2)).orElse(name);
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}