package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.service_factory.ServiceFactory;
import com.epam.gtc.service_factory.ServiceType;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.RequestService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Admin main page command.
 */
public class AdminMainPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminMainPageCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response) {
        LOG.debug("AdminMainPageCommand starts");
        String forward = Path.PAGE_ADMIN_HOME;
        handleRequest(request);
        LOG.debug("AdminMainPageCommand finished");
        return forward;
    }

    private void handleRequest(final HttpServletRequest request) {

        RequestService requestService = (RequestService) ServiceFactory.createService(ServiceType.REQUEST_SERVICE);
        InvoiceService invoiceService = (InvoiceService) ServiceFactory.createService(ServiceType.INVOICE_SERVICE);
        DeliveryService deliveryService = (DeliveryService) ServiceFactory.createService(ServiceType.DELIVERY_SERVICE);

        Map<String, List<String>> dashboard = new HashMap<>();
        dashboard.put("Requests", formRequestsReport(requestService));
        dashboard.put("Invoices", formInvoiceReport(invoiceService));
        dashboard.put("Deliveries", formDeliveryReport(deliveryService));

        LOG.trace("requests --> " + dashboard.get("Requests"));
        LOG.trace("invoices --> " + dashboard.get("Invoices"));
        LOG.trace("deliveries --> " + dashboard.get("Deliveries"));

        request.setAttribute("dashboard", dashboard);
    }

    private List<String> formDeliveryReport(DeliveryService deliveryService) {
        List<String> listOfStatusData = new ArrayList<>();
        Arrays.stream(DeliveryStatus.values()).forEach(deliveryStatus -> {
            String row = new StringBuilder(formatStatusName(deliveryStatus.getName()))
                    .append(" : ")
                    .append(deliveryService.countDeliveries(deliveryStatus))
                    .append(" delivery(ies)").toString();
            listOfStatusData.add(row);
        });
        String allDeliveries = new StringBuilder("All deliveries : ")
                .append(deliveryService.countAllDeliveries())
                .append(" deliveries").toString();
        listOfStatusData.add(allDeliveries);
        return listOfStatusData;
    }

    private List<String> formInvoiceReport(InvoiceService invoiceService) {
        List<String> listOfStatusData = new ArrayList<>();
        Arrays.stream(InvoiceStatus.values()).forEach(invoiceStatus -> {
            String row = new StringBuilder(formatStatusName(invoiceStatus.getName()))
                    .append(" : ")
                    .append(invoiceService.countInvoices(invoiceStatus))
                    .append(" invoice(s)").toString();
            listOfStatusData.add(row);
        });
        String allInvoices = "All invoices : " +
                invoiceService.countAllInvoices() +
                " invoices";
        listOfStatusData.add(allInvoices);
        return listOfStatusData;
    }

    private List<String> formRequestsReport(RequestService requestService) {
        List<String> listOfStatusData = new ArrayList<>();
        Arrays.stream(RequestStatus.values()).forEach(requestStatus -> {
            String row = new StringBuilder(formatStatusName(requestStatus.getName()))
                    .append(" : ")
                    .append(requestService.countRequests(requestStatus))
                    .append(" request(s)").toString();
            listOfStatusData.add(row);
        });
        String allRequests = new StringBuilder("All requests : ")
                .append(requestService.countAllRequests())
                .append(" requests").toString();
        listOfStatusData.add(allRequests);
        return listOfStatusData;
    }


    private static String formatStatusName(String name) {
        String[] parts = name.split("_");
        name = Arrays.stream(parts).reduce((s1, s2) -> s1.concat(" ").concat(s2)).orElse(name);
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}