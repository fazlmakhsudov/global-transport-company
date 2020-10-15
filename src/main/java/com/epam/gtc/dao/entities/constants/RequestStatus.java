package com.epam.gtc.dao.entities.constants;

import com.epam.gtc.dao.entities.RequestEntity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Request statuses
 *
 * @author Fazliddin Makhsudov
 */
public enum RequestStatus {
    WAITING_FOR_MANAGER_REVIEW,
    WAITING_FOR_PAYMENT,
    PROCESSED,
    CANCELLED;

    /**
     * Get request status.
     *
     * @param request entity
     * @return RequestStatus
     */
    public static RequestStatus getInvoiceStatus(final RequestEntity request) {
        int requestStatusId = request.getRequestStatusId() - 1;
        return RequestStatus.values()[requestStatusId];
    }

    /**
     * Get Request.
     *
     * @param order of request status
     * @return Request status
     */
    public static RequestStatus getEnumFromId(final int order) {
        int RequestStatusId = order - 1;
        return RequestStatus.values()[RequestStatusId];
    }

    /**
     * Get name.
     *
     * @return String.
     */
    public String getName() {
        return name().toLowerCase();
    }

    /**
     * Get RequestStatus order by name.
     *
     * @return int.
     */
    public static int getId(String name) {
        return Arrays.stream(RequestStatus.values())
                .filter(RequestStatus -> RequestStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(RequestStatus.WAITING_FOR_MANAGER_REVIEW).ordinal() + 1;
    }

    /**
     * Get RequestStatus order by RequestStatus.
     *
     * @return int.
     */
    public static int getId(RequestStatus RequestStatusB) {
        return Arrays.stream(RequestStatus.values())
                .filter(RequestStatusA -> Objects.equals(RequestStatusA, RequestStatusB))
                .findFirst().orElse(RequestStatus.WAITING_FOR_MANAGER_REVIEW).ordinal() + 1;
    }

    /**
     * Get RequestStatus by name.
     *
     * @return int.
     */
    public static RequestStatus getEnumFromName(String name) {
        return Arrays.stream(RequestStatus.values())
                .filter(RequestStatus -> RequestStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(RequestStatus.WAITING_FOR_MANAGER_REVIEW);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
