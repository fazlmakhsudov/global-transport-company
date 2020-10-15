package com.epam.gtc.exceptions;

/**
 * An exception that provides information on a DAO error.
 *
 * @author Fazliddin Makhsudov
 */
public class DAOException extends AppException {

    private static final long serialVersionUID = -3550446897536410392L;

    public DAOException() {
    }

    /**
     * DAO exception.
     *
     * @param message message
     * @param cause   cause
     */
    public DAOException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
