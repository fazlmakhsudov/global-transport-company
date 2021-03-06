package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.DeliveryStatus;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.dao.entities.constants.RequestStatus;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.DeliveryService;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.RequestService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Admin main page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminMainPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminMainPageCommand.class);
    private final RequestService requestService;
    private final InvoiceService invoiceService;
    private final DeliveryService deliveryService;

    public AdminMainPageCommand(RequestService requestService, InvoiceService invoiceService, DeliveryService deliveryService) {
        this.requestService = requestService;
        this.invoiceService = invoiceService;
        this.deliveryService = deliveryService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response) {
        LOG.debug("AdminMainPageCommand starts");
        String forward = Path.PAGE_ADMIN_HOME;
        handleRequest(request);
        LOG.debug("AdminMainPageCommand finished");
        return forward;
    }

    private void handleRequest(final HttpServletRequest request) {


        Map<String, List<String>> dashboard = new HashMap<>();
        Map<String, Integer> alerts = new HashMap<>();
        try {
            dashboard.put("Requests", formRequestsReport(requestService));
            dashboard.put("Invoices", formInvoiceReport(invoiceService));
            dashboard.put("Deliveries", formDeliveryReport(deliveryService));
            alerts.put(formatStatusName(RequestStatus.WAITING_FOR_MANAGER_REVIEW.getName()),
                    requestService.countRequests(RequestStatus.WAITING_FOR_MANAGER_REVIEW));
            alerts.put(formatStatusName(InvoiceStatus.PAID.getName()),
                    invoiceService.countInvoices(InvoiceStatus.PAID));
            alerts.put(formatStatusName(InvoiceStatus.REJECTED.getName()),
                    invoiceService.countInvoices(InvoiceStatus.REJECTED));
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
            request.setAttribute("errorMessage", e.getMessage());
        }

        LOG.trace("requests --> " + dashboard.get("Requests"));
        LOG.trace("invoices --> " + dashboard.get("Invoices"));
        LOG.trace("deliveries --> " + dashboard.get("Deliveries"));
        LOG.trace("alerts --> " + alerts);
        request.getSession().setAttribute("alerts", alerts);
        request.setAttribute("dashboard", dashboard);
    }

    private List<String> formDeliveryReport(DeliveryService deliveryService) throws ServiceException {
        List<String> listOfStatusData = new ArrayList<>();
        for (DeliveryStatus deliveryStatus : DeliveryStatus.values()) {
            String row = new StringBuilder(formatStatusName(deliveryStatus.getName()))
                    .append(" : ")
                    .append(deliveryService.countDeliveries(deliveryStatus))
                    .append(" delivery(ies)").toString();
            listOfStatusData.add(row);
        }
        String allDeliveries = new StringBuilder("All deliveries : ")
                .append(deliveryService.countAllDeliveries())
                .append(" deliveries").toString();
        listOfStatusData.add(allDeliveries);
        return listOfStatusData;
    }

    private List<String> formInvoiceReport(InvoiceService invoiceService) throws ServiceException {
        List<String> listOfStatusData = new ArrayList<>();
        for (InvoiceStatus invoiceStatus : InvoiceStatus.values()) {
            String row = new StringBuilder(formatStatusName(invoiceStatus.getName()))
                    .append(" : ")
                    .append(invoiceService.countInvoices(invoiceStatus))
                    .append(" invoice(s)").toString();
            listOfStatusData.add(row);
        }
        String allInvoices = "All invoices : " +
                invoiceService.countAllInvoices() +
                " invoices";
        listOfStatusData.add(allInvoices);
        return listOfStatusData;
    }

    private List<String> formRequestsReport(RequestService requestService) throws ServiceException {
        List<String> listOfStatusData = new ArrayList<>();
        for (RequestStatus requestStatus : RequestStatus.values()) {
            String row = new StringBuilder(formatStatusName(requestStatus.getName()))
                    .append(" : ")
                    .append(requestService.countRequests(requestStatus))
                    .append(" request(s)").toString();
            listOfStatusData.add(row);
        }
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