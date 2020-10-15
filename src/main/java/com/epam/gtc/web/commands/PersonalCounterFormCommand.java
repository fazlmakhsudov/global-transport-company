package com.epam.gtc.web.commands;

import com.epam.gtc.Path;
import com.epam.gtc.dao.entities.constants.ContentType;
import com.epam.gtc.utils.CostCounter;
import com.epam.gtc.web.models.RequestModel;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;


/**
 * Personal Counter Form command.
 */
public class PersonalCounterFormCommand implements Command {

    private static final long serialVersionUID = -3071536593627692473L;

    private static final Logger LOG = Logger.getLogger(PersonalCounterFormCommand.class);

    @Override
    public final String execute(final HttpServletRequest request, final HttpServletResponse response) {
        LOG.debug("PersonalCounterFormCommand starts");
        String forward = handleRequest(request);
        LOG.debug("PersonalCounterFormCommand finished");
        return forward;
    }

    private String handleRequest(final HttpServletRequest request) {
        int cityFromId = request.getParameter("requestcityfromid") != null ?
                Integer.parseInt(request.getParameter("requestcityfromid")) : -1;
        LOG.trace("City from id --> " + cityFromId);
        int cityToId = request.getParameter("requestcitytoid") != null ?
                Integer.parseInt(request.getParameter("requestcitytoid")) : -1;
        LOG.trace("City to id --> " + cityToId);
        double weight = request.getParameter("requestweight") != null ?
                Double.parseDouble(request.getParameter("requestweight")) : -1d;
        LOG.trace("Request weight --> " + weight);
        double length = request.getParameter("requestlength") != null ?
                Double.parseDouble(request.getParameter("requestlength")) : -1d;
        LOG.trace("Request length --> " + length);
        double width = request.getParameter("requestwidth") != null ?
                Double.parseDouble(request.getParameter("requestwidth")) : -1d;
        LOG.trace("Request width --> " + width);
        double height = request.getParameter("requestheight") != null ?
                Double.parseDouble(request.getParameter("requestheight")) : -1d;
        LOG.trace("Request height --> " + height);

        String deliveryDateString = request.getParameter("requestdeliverydate");
        LOG.trace("Request delivery date string --> " + deliveryDateString);
        LocalDateTime deliveryDate = Objects.isNull(deliveryDateString) || deliveryDateString.isEmpty() ?
                LocalDateTime.now() : LocalDateTime.parse(deliveryDateString);
        LOG.trace("Request delivery date --> " + deliveryDate);

        String contentTypeName = request.getParameter("requestcontenttypename") != null ?
                request.getParameter("requestcontenttypename") : ContentType.PARCEL_POST.getName();
        LOG.trace("Request content type name --> " + contentTypeName);
        RequestModel requestModel = new RequestModel();
        requestModel.setCityFromId(cityFromId);
        requestModel.setCityToId(cityToId);
        requestModel.setWeight(weight);
        requestModel.setLength(length);
        requestModel.setWidth(width);
        requestModel.setHeight(height);
        requestModel.setDeliveryDate(Date.from(deliveryDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        double cost = CostCounter.countCost(requestModel);
        LOG.trace("Estimated cost for request --> " + cost);
        request.setAttribute("result", String.format("{\"requestcost\" : %.2f}", cost));
        return Path.AJAX_COMMAND_PERSONAL_COUNTER_FORM;
    }
}