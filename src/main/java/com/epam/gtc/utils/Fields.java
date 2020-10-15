package com.epam.gtc.utils;


import java.util.Arrays;

/**
 * Holder for fields names of DB tables and beans.
 *
 * @author Fazliddin Makhsudov
 */
public final class Fields {

    public static final String ENTITY_ID = "id";
    public static final String ENTITY_NAME = "name";

    public static final String USER_SURNAME = "surname";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_ROLE_ID = "role_id";
    public static final String USER_ID = "user_id";

    public static final String CARGO_MAX_WEIGHT = "max_weight";
    public static final String CARGO_MAX_LENGTH = "max_length";
    public static final String CARGO_MAX_WIDTH = "max_width";
    public static final String CARGO_MAX_HEIGHT = "max_height";
    public static final String MAX_DISTANCE = "max_distance";
    public static final String CARGO_WEIGHT = "weight";
    public static final String CARGO_LENGTH = "length";
    public static final String CARGO_WIDTH = "width";
    public static final String CARGO_HEIGHT = "height";
    public static final String CARGO_DELIVERY_DATE = "delivery_date";

    public static final String INVOICE_STATUS_ID = "invoice_status_id";
    public static final String REQUEST_ID = "request_id";
    public static final String REQUEST_STATUS_ID = "request_status_id";
    public static final String DELIVERY_STATUS_ID = "delivery_status_id";

    public static final String CITY_FROM_ID = "city_from_id";
    public static final String CITY_TO_ID = "city_to_id";
    public static final String FROM_CITY_ID = "from_city_id";
    public static final String TO_CITY_ID = "to_city_id";
    public static final String CREATED_DATE = "created_date";
    public static final String UPDATED_DATE = "updated_date";
    public static final String COST = "cost";
    public static final String DISTANCE = "distance";
    public static final String CONTENT_TYPE_ID = "content_type_id";


    private Fields() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static String findAndMapField(String name) {

        String pattern = "(ENTITY_)?(USER_)?(CARGO_)?" + name.toUpperCase();
        String beanName = null;
        try {
            beanName = (String) Arrays.stream(Fields.class.getDeclaredFields())
                    .filter(field -> field.getName().matches(pattern))
                    .findFirst().get().get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String[] parts = beanName.split("_");
        beanName = Arrays.stream(parts).map(part -> Character.toUpperCase(part.charAt(0)) + part.substring(1))
                .reduce("", String::concat);
        beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
        return beanName;
    }

}