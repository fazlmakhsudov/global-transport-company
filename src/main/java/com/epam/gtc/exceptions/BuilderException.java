package com.epam.gtc.exceptions;

/**
 * An exception that provides information on a Builder error.
 *
 * @author Fazliddin Makhsudov
 */
public class BuilderException extends AppException {

    private static final long serialVersionUID = -3550446897536410392L;

    public BuilderException() {
    }

    /**
     * Builder exception.
     *
     * @param message message
     * @param cause   cause
     */
    public BuilderException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
