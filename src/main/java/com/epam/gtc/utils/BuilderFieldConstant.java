package com.epam.gtc.utils;

import java.util.Arrays;

/**
 * Builder field constants
 *
 * @author Fazliddin Makhsudov
 */
public enum BuilderFieldConstant {
    ID,
    NAME,
    ENUM_FROM_NAME,
    ENUM_FROM_ID,
    LOCALDATE_FROM_DATE,
    LOCALDATE_FROM_TIMESTAMP,
    TIMESTAMP,
    DATE,
    GET_ID,
    GET_NAME,
    GET_ENUM_FROM_NAME,
    GET_ENUM_FROM_ID;

    public String getName() {
        String name = Arrays.stream(name().split("_"))
                .map(part -> Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase())
                .reduce("", String::concat);
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}
