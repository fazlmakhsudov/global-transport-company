package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.InvoiceStatus;
import com.epam.gtc.exceptions.AppException;
import com.epam.gtc.exceptions.ServiceException;
import com.epam.gtc.services.InvoiceService;
import com.epam.gtc.services.domains.InvoiceDomain;
import com.epam.gtc.services.factory.ServiceFactory;
import com.epam.gtc.services.factory.ServiceType;
import com.epam.gtc.utils.Method;
import com.epam.gtc.web.models.InvoiceModel;
import com.epam.gtc.web.models.UserModel;
import com.epam.gtc.web.models.builders.InvoiceModelBuilder;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * User Invoices tab command.
 *
 * @author Fazliddin Makhsudov
 */
public class UserInvoicesTabCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(UserInvoicesTabCommand.class);

    @Override
    public final String execute(final HttpServletRequest invoice, final HttpServletResponse response)
            throws AppException {
        LOG.debug("UserInvoicesTabCommand starts");
        String forward;
        Object sessionUser = invoice.getSession().getAttribute("user");
        LOG.trace("Session user --> " + sessionUser);
        if (Objects.isNull(sessionUser)) {
            forward = Path.COMMAND_INDEX;
        } else {
            forward = handleRequest(invoice, sessionUser);
        }
        LOG.debug("UserInvoicesTabCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request, Object sessionUser) throws AppException {
        InvoiceService invoiceService = (InvoiceService) ServiceFactory.createService(ServiceType.INVOICE_SERVICE);
        UserModel userModel = (UserModel) sessionUser;
        int invoicesNumber = invoiceService.countUserInvoices(userModel.getId());
        LOG.trace("Number of user invoices : " + invoicesNumber);
        Optional<String> optionalPage = Optional.ofNullable(request.getParameter("page"));
        LOG.trace("optional page : " + optionalPage);
        Optional<String> optionalItemsPerPage = Optional.ofNullable(request.getParameter("itemsPerPage"));
        LOG.trace("optional items per page : " + optionalItemsPerPage);
        int page = optionalPage.map(Integer::parseInt).orElse(1);
        int itemsPerPage = optionalItemsPerPage.map(Integer::parseInt).orElse(5);
        String forward = Path.PAGE_USER_CABINET;
        if (Method.isPost(request)) {
            forward = doPost(request, invoiceService, page, itemsPerPage);
        }
        List<InvoiceModel> invoiceModels = new InvoiceModelBuilder().create(invoiceService.findAll(page, itemsPerPage, userModel.getId()));
        LOG.trace("Invoices : " + invoiceModels);
        manageSessionAttributes(request.getSession());
        request.setAttribute("page", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("invoicesNumber", invoicesNumber);
        request.setAttribute("userInvoices", invoiceModels);
        return forward;
    }

    private String doPost(HttpServletRequest request, InvoiceService invoiceService, int page, int itemsPerPage) throws ServiceException {
        String forward;
        LOG.trace("Method is Post");
        String action = request.getParameter(FormRequestParametersNames.ACTION);
        LOG.trace("Action --> " + action);

        switch (action) {
            case "save":
                String invoiceStatusName = request.getParameter(FormRequestParametersNames.INVOICE_STATUS_NAME);
                LOG.trace("Invoice status name --> " + invoiceStatusName);
                int invoiceId = Integer.parseInt(request.getParameter(FormRequestParametersNames.INVOICE_ID));
                LOG.trace("Invoice id --> " + invoiceId);
                InvoiceDomain invoiceDomain = invoiceService.find(invoiceId);
                invoiceDomain.setInvoiceStatus(InvoiceStatus.getEnumFromName(invoiceStatusName));

                boolean savedFlag = invoiceService.save(invoiceDomain);
                LOG.trace("Saved status --> " + savedFlag);
                break;
            default:
                //TODO no action error
        }

        forward = String.format("%s&page=%s&itemsPerPage=%s", Path.COMMAND_USER_INVOICES_TAB,
                page, itemsPerPage);
        return forward;
    }

    private void manageSessionAttributes(HttpSession session) {
        session.removeAttribute("profiletab");
        session.removeAttribute("profiletabbody");
        session.removeAttribute("requeststab");
        session.removeAttribute("requeststabbody");
        session.removeAttribute("deliveriestab");
        session.removeAttribute("deliveriestabbody");

        session.setAttribute("invoicestab", "active");
        session.setAttribute("invoicestabbody", "show active");
    }
}