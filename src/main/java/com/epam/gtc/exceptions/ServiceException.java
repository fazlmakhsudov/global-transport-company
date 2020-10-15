package com.epam.gtc.exceptions;

/**
 * An exception that provides information on a service layer error.
 *
 * @author Fazliddin Makhsudov
 */
public class ServiceException extends AppException {

    private static final long serialVersionUID = -3550446897536410392L; // it's necessary to redefine

    public ServiceException() {
    }

    /**
     * service exception.
     *
     * @param message message
     * @param cause   cause
     */
    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
