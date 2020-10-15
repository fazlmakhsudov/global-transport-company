package com.epam.gtc.dao.entities.constants;

import com.epam.gtc.dao.entities.RequestEntity;

import java.util.Arrays;
import java.util.Objects;

/**
 * Content types
 *
 * @author Fazliddin Makhsudov
 */
public enum ContentType {
    CARGO,
    PARCEL_POST,
    DOCUMENT,
    JEWELERY;

    /**
     * Get content type status.
     *
     * @param request entity
     * @return ContentTypeStatus
     */
    public static ContentType getContentTypeStatus(final RequestEntity request) {
        int contentStatusId = request.getContentTypeId() - 1;
        return ContentType.values()[contentStatusId];
    }

    /**
     * Get ContentType.
     *
     * @param order of content type
     * @return ContentType
     */
    public static ContentType getEnumFromId(final int order) {
        int ContentTypeId = order - 1;
        return ContentType.values()[ContentTypeId];
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
     * Get ContentType order by name.
     *
     * @return int.
     */
    public static int getId(String name) {
        return Arrays.stream(ContentType.values())
                .filter(ContentType -> ContentType.name().equalsIgnoreCase(name))
                .findFirst().orElse(ContentType.PARCEL_POST).ordinal() + 1;
    }

    /**
     * Get ContentType order by ContentType.
     *
     * @return int.
     */
    public static int getId(ContentType ContentTypeB) {
        return Arrays.stream(ContentType.values())
                .filter(ContentTypeA -> Objects.equals(ContentTypeA, ContentTypeB))
                .findFirst().orElse(ContentType.PARCEL_POST).ordinal() + 1;
    }

    /**
     * Get ContentType by name.
     *
     * @return int.
     */
    public static ContentType getEnumFromName(String name) {
        return Arrays.stream(ContentType.values())
                .filter(ContentType -> ContentType.name().equalsIgnoreCase(name))
                .findFirst().orElse(ContentType.PARCEL_POST);
    }

    @Override
    public String toString() {
        return this.name();
    }
}
