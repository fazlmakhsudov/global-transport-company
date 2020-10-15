package com.epam.gtc.web.commands;

import com.epam.gtc.exceptions.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Fazliddin Makhsudov
 */
public interface Command extends Serializable {

    /**
     * Execution method for command.
     *
     * @param request  request
     * @param response response
     * @return String (Address to go once the command is executed.)
     *
     * @throws IOException      io exception
     * @throws ServletException servlet exception
     * @throws AppException     app exception
     */
    String execute(HttpServletRequest request,
                   HttpServletResponse response)
            throws IOException, ServletException, AppException;

}