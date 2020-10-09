package com.epam.gtc.dao.entities.constants;

import com.epam.gtc.dao.entities.DeliveryEntity;

import java.util.Arrays;
import java.util.Objects;

public enum DeliveryStatus {
    WAITING_FOR_PACKAGING,
    UNDER_TRANSPORTATION,
    DELIVERED;

    /**
     * Get delivery status.
     *
     * @param delivery status
     * @return DeliveryStatus
     */
    public static DeliveryStatus getDeliveryStatus(final DeliveryEntity delivery) {
        int deliveryStatusId = delivery.getDeliveryStatusId() - 1;
        return DeliveryStatus.values()[deliveryStatusId];
    }

    /**
     * Get DeliveryStatus.
     *
     * @param order of delivery status
     * @return DeliveryStatus
     */
    public static DeliveryStatus getEnumFromId(final int order) {
        int DeliveryStatusId = order - 1;
        return DeliveryStatus.values()[DeliveryStatusId];
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
     * Get DeliveryStatus order by name.
     *
     * @return int.
     */
    public static int getId(String name) {
        return Arrays.stream(DeliveryStatus.values())
                .filter(DeliveryStatus -> DeliveryStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(DeliveryStatus.WAITING_FOR_PACKAGING).ordinal() + 1;
    }

    /**
     * Get DeliveryStatus order by DeliveryStatus.
     *
     * @return int.
     */
    public static int getId(DeliveryStatus DeliveryStatusB) {
        return Arrays.stream(DeliveryStatus.values())
                .filter(DeliveryStatusA -> Objects.equals(DeliveryStatusA, DeliveryStatusB))
                .findFirst().orElse(DeliveryStatus.WAITING_FOR_PACKAGING).ordinal() + 1;
    }

    /**
     * Get DeliveryStatus by name.
     *
     * @return int.
     */
    public static DeliveryStatus getEnumFromName(String name) {
        return Arrays.stream(DeliveryStatus.values())
                .filter(DeliveryStatus -> DeliveryStatus.name().equalsIgnoreCase(name))
                .findFirst().orElse(DeliveryStatus.WAITING_FOR_PACKAGING);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
