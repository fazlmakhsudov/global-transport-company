package com.epam.gtc.dao.entities.constants;

import com.epam.gtc.dao.entities.InvoiceEntity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Invoice statuses
 *
 * @author Fazliddin Makhsudov
 */
public enum InvoiceStatus {
    PAID,
    UNPAID,
    REJECTED;

    /**
     * Get invoice status.
     *
     * @param invoice status
     * @return InvoiceStatus
     */
    public static InvoiceStatus getInvoiceStatus(final InvoiceEntity invoice) {
        int invoiceStatusId = invoice.getInvoiceStatusId() - 1;
        return InvoiceStatus.values()[invoiceStatusId];
    }

    /**
     * Get InvoiceStatus.
     *
     * @param order of invoice status
     * @return InvoiceStatus
     */
    public static InvoiceStatus getEnumFromId(final int order) {
        int InvoiceStatusId = order - 1;
        return InvoiceStatus.values()[InvoiceStatusId];
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
     * Get InvoiceStatus order by name.
     *
     * @return int.
     */
    public static int getId(String name) {
        return Arrays.stream(InvoiceStatus.values())
                .filter(InvoiceStatus -> InvoiceStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(InvoiceStatus.UNPAID).ordinal() + 1;
    }

    /**
     * Get InvoiceStatus order by InvoiceStatus.
     *
     * @return int.
     */
    public static int getId(InvoiceStatus InvoiceStatusB) {
        return Arrays.stream(InvoiceStatus.values())
                .filter(InvoiceStatusA -> Objects.equals(InvoiceStatusA, InvoiceStatusB))
                .findFirst().orElse(InvoiceStatus.UNPAID).ordinal() + 1;
    }

    /**
     * Get InvoiceStatus by name.
     *
     * @return int.
     */
    public static InvoiceStatus getEnumFromName(String name) {
        return Arrays.stream(InvoiceStatus.values())
                .filter(InvoiceStatus -> InvoiceStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(InvoiceStatus.UNPAID);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
