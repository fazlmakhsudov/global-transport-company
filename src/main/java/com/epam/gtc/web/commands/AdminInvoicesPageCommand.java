package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.InvoiceModel;
import com.epam.gtc.web.models.builders.InvoiceModelBuilder;
import org.apache.log4j.Logger;
import org.graalvm.compiler.nodes.extended.ValueAnchorNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Admin invoice page command.
 *
 * @author Fazliddin Makhsudov
 */
public class AdminInvoicesPageCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(AdminInvoicesPageCommand.class);
    private final InvoiceService invoiceService;


    public AdminInvoicesPageCommand(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response)
            throws AppException {
        LOG.debug("AdminInvoicesPageCommand starts");
        String forward = handleRequest(request);
        LOG.debug("AdminInvoicesPageCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) throws AppException {

        int invoicesNumber = invoiceService.countAllInvoices();
        LOG.trace("Number of invoices : " + invoicesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);

        int page = optionalPage.isPresent() && Validator.isValidNumber(optionalPage.get())
                ? optionalPage.map(Integer::parseInt).orElse(1) : 1;

        int itemsPerPage = optionalItemsPerPage.isPresent() && Validator.isValidNumber(optionalItemsPerPage.get()) ?
                optionalItemsPerPage.map(Integer::parseInt).orElse(5) : 5;

        String forward = Path.PAGE_ADMIN_INVOICES;
        if (Method.isPost(request)) {
            forward = doPost(request, invoiceService, page, itemsPerPage);
        }
        List<InvoiceModel> invoiceModels = new InvoiceModelBuilder().create(invoiceService.findAll(page, itemsPerPage));
        LOG.trace("Invoices : " + invoiceModels);
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("invoicesNumber", invoicesNumber);
        request.setAttribute("adminInvoices", invoiceModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, InvoiceService invoiceService, int page, int itemsPerPage) throws com.epam.gtc.exceptions.ServiceException {
        String forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_ADMIN_INVOICES_PAGE,
                page, itemsPerPage);
        StringBuilder errorInvoices = new StringBuilder();
        boolean errorFlag = false;

        LOG.trace("Method is Post");
        request.getSession().removeAttribute("errorInvoices");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);
        if (!Validator.isValidString(action)) {
            errorFlag = true;
            errorInvoices.append("Invalid action").append("<br/>");
        }

        boolean isRemoveMethod = action.equalsIgnoreCase("remove");

        String invoiceStatusNameString = request.getParameter(FormRequestParametersNames.INVOICE_STATUS_NAME);
        LOG.trace("Invoice status name --> " + invoiceStatusNameString);
        if (!Validator.isValidString(invoiceStatusNameString) && !isRemoveMethod) {
            errorFlag = true;
            errorInvoices.append("Invalid status name").append("<br/>");
        }

        String invoiceIdString = request.getParameter(FormRequestParametersNames.INVOICE_ID);
        LOG.trace("Invoice id --> " + invoiceIdString);
        if (!action.equalsIgnoreCase("add") && !Validator.isValidNumber(invoiceIdString)) {
            errorFlag = true;
            errorInvoices.append("Invalid invoice id").append("<br/>");
        }

        if (errorFlag) {
            request.getSession().setAttribute("errorInvoices", errorInvoices.toString());
            return forward;
        }

        doPostIfAllRight(request, invoiceService, action, isRemoveMethod, invoiceStatusNameString, invoiceIdString);

        return forward;
    }

    private void doPostIfAllRight(HttpServletRequest request, InvoiceService invoiceService, String action, boolean isRemoveMethod, String invoiceStatusNameString, String invoiceIdString) throws ServiceException {
        int invoiceId =  action.equalsIgnoreCase("add") ? -1 : Integer.parseInt(invoiceIdString);
        String invoiceStatusName = isRemoveMethod ? "" : invoiceStatusNameString;
        switch (action) {
            case "add":
                String requestIdString = request.getParameter(FormRequestParametersNames.INVOICE_REQUEST_ID);
                LOG.trace("Request id --> " + requestIdString);
                if (Validator.isValidNumber(requestIdString) &&
                    invoiceService.countDeliveriesOfRequest(Integer.parseInt(requestIdString))== 0) {
                    InvoiceDomain newInvoiceDomain = new InvoiceDomain();
                    String costString = request.getParameter(FormRequestParametersNames.INVOICE_COST);
                    LOG.trace("invoice cost --> " + costString);
                    int requestId = Integer.parseInt(requestIdString);

                    boolean costFlag = Objects.isNull(costString) || costString.isEmpty()
                            || Double.parseDouble(costString) <= 1d;
                    newInvoiceDomain.setCost(costFlag ? CostCounter.countCost(requestId) : Double.parseDouble(costString));
                    newInvoiceDomain.setInvoiceStatus(InvoiceStatus.getEnumFromName(invoiceStatusName));
                    newInvoiceDomain.setRequestId(requestId);

                    int newId = invoiceService.add(newInvoiceDomain);
                    LOG.trace("Added status(new id) --> " + newId);
                } else {
                    LOG.error("Invalid request id, or request has invoice already");
                    request.getSession().setAttribute("errorInvoices", "Invalid request id, or request has invoice already");
                }
                break;
            case "save":
                InvoiceDomain invoiceDomain = invoiceService.find(invoiceId);
                invoiceDomain.setInvoiceStatus(InvoiceStatus.getEnumFromName(invoiceStatusName));
                boolean savedFlag = invoiceService.save(invoiceDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            case "remove":
                boolean removedFlag = invoiceService.remove(invoiceId);
                LOG.trace("Removed status --> " + removedFlag);
                break;

        }
    }

}