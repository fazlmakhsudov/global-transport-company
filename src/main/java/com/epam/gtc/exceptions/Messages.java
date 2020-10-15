package com.epam.gtc.exceptions;

/**
 * Holder for messages of exceptions.
 *
 * @author Fazliddin Makhsudov
 */
public final class Messages {

    private Messages() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    // DB exceptions
    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";
    public static final String ERR_CANNOT_CLOSE_CONNECTION = "Cannot close a connection";
    public static final String ERR_CANNOT_CLOSE_RESULTSET = "Cannot close a result set";
    public static final String ERR_CANNOT_CLOSE_STATEMENT = "Cannot close a statement";
    public static final String ERR_CANNOT_CLOSE_PREPARED_STATEMENT = "Cannot close a prepared statement";
    public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
    public static final String CANNOT_ROLLBACK_TRANSACTION = "Cannot rollback transaction";
    //user entity exceptions
    public static final String ERR_CANNOT_INSERT_USER = "Cannot insert new user";
    public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id";
    public static final String ERR_CANNOT_OBTAIN_USER_BY_EMAIL = "Cannot obtain a user by its email";
    public static final String ERR_CANNOT_UPDATE_USER = "Cannot update a user";
    public static final String ERR_CANNOT_DELETE_USER = "Cannot delete new user";
    public static final String ERR_CANNOT_READ_ALL_USERS = "Cannot read all users";
    public static final String ERR_CANNOT_COUNT_ALL_USERS = "Cannot count all users";
    public static final String ERR_CANNOT_READ_USERS_WITH_LIMITATION = "Cannot read users with limitation";
    // city entity exceptions
    public static final String ERR_CANNOT_INSERT_CITY = "Cannot insert new city";
    public static final String ERR_CANNOT_OBTAIN_CITY_BY_ID = "Cannot obtain a city by its id";
    public static final String ERR_CANNOT_OBTAIN_CITY_BY_NAME = "Cannot obtain a city by its name";
    public static final String ERR_CANNOT_UPDATE_CITY = "Cannot update a city";
    public static final String ERR_CANNOT_DELETE_CITY = "Cannot delete new city";
    public static final String ERR_CANNOT_READ_ALL_CITIES = "Cannot read all cities";
    public static final String ERR_CANNOT_COUNT_ALL_CITIES = "Cannot count all cities";
    public static final String ERR_CANNOT_READ_CITIES_WITH_LIMITATION = "Cannot read cities with limitation";
    // request entity exceptions
    public static final String ERR_CANNOT_INSERT_REQUEST = "Cannot insert new request";
    public static final String ERR_CANNOT_OBTAIN_REQUEST_BY_ID = "Cannot obtain a request by its id";
    public static final String ERR_CANNOT_OBTAIN_REQUEST_BY_NAME = "Cannot obtain a request by its name";
    public static final String ERR_CANNOT_UPDATE_REQUEST = "Cannot update a request";
    public static final String ERR_CANNOT_DELETE_REQUEST = "Cannot delete new request";
    public static final String ERR_CANNOT_READ_ALL_REQUESTS = "Cannot read all requests";
    public static final String ERR_CANNOT_COUNT_ALL_REQUESTS = "Cannot count all requests";
    public static final String ERR_CANNOT_COUNT_REQUESTS_WITH_CONDITION = "Cannot count requests with condition";
    public static final String ERR_CANNOT_READ_REQUESTS_WITH_LIMITATION = "Cannot read requests with limitation";

    // invoice entity exceptions
    public static final String ERR_CANNOT_INSERT_INVOICE = "Cannot insert new invoice";
    public static final String ERR_CANNOT_OBTAIN_INVOICE_BY_ID = "Cannot obtain a invoice by its id";
    public static final String ERR_CANNOT_OBTAIN_INVOICE_BY_NAME = "Cannot obtain a invoice by its name";
    public static final String ERR_CANNOT_UPDATE_INVOICE = "Cannot update a invoice";
    public static final String ERR_CANNOT_DELETE_INVOICE = "Cannot delete new invoice";
    public static final String ERR_CANNOT_READ_ALL_INVOICES = "Cannot read all invoices";
    public static final String ERR_CANNOT_COUNT_ALL_INVOICES = "Cannot count all invoices";
    public static final String ERR_CANNOT_COUNT_INVOICES_WITH_CONDITION = "Cannot count invoices with condition";
    public static final String ERR_CANNOT_READ_INVOICES_WITH_LIMITATION = "Cannot read incoices with limitation";
    // delivery entity exceptions
    public static final String ERR_CANNOT_INSERT_DELIVERY = "Cannot insert new delivery";
    public static final String ERR_CANNOT_OBTAIN_DELIVERY_BY_ID = "Cannot obtain a delivery by its id";
    public static final String ERR_CANNOT_OBTAIN_DELIVERY_BY_NAME = "Cannot obtain a delivery by its name";
    public static final String ERR_CANNOT_UPDATE_DELIVERY = "Cannot update a delivery";
    public static final String ERR_CANNOT_DELETE_DELIVERY = "Cannot delete new delivery";
    public static final String ERR_CANNOT_READ_ALL_DELIVERIES = "Cannot read all deliveries";
    public static final String ERR_CANNOT_COUNT_ALL_DELIVERIES = "Cannot count all deliveries";
    public static final String ERR_CANNOT_COUNT_DELIVERIES_WITH_CONDITION = "Cannot count deliveries with condition";
    public static final String ERR_CANNOT_READ_DELIVERIES_WITH_LIMITATION = "Cannot read deliveries with limitation";
    // rate entity exceptions
    public static final String ERR_CANNOT_INSERT_RATE = "Cannot insert new rate";
    public static final String ERR_CANNOT_OBTAIN_RATE_BY_ID = "Cannot obtain a rate by its id";
    public static final String ERR_CANNOT_OBTAIN_RATE_BY_NAME = "Cannot obtain a rate by its name";
    public static final String ERR_CANNOT_UPDATE_RATE = "Cannot update a rate";
    public static final String ERR_CANNOT_DELETE_RATE = "Cannot delete new rate";
    public static final String ERR_CANNOT_READ_ALL_RATES = "Cannot read all rates";
    public static final String ERR_CANNOT_COUNT_ALL_RATES = "Cannot count all rates";
    public static final String ERR_CANNOT_READ_RATES_WITH_LIMITATION = "Cannot read rates with limitation";
    // distance entity exceptions
    public static final String ERR_CANNOT_INSERT_DISTANCE = "Cannot insert new distance";
    public static final String ERR_CANNOT_OBTAIN_DISTANCE_BY_ID = "Cannot obtain a distance by its id";
    public static final String ERR_CANNOT_OBTAIN_DISTANCE_BY_NAME = "Cannot obtain a distance by its name";
    public static final String ERR_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS = "Cannot obtain a distance by its fields(fromCityId, toCityId)";
    public static final String ERR_CANNOT_UPDATE_DISTANCE = "Cannot update a distance";
    public static final String ERR_CANNOT_DELETE_DISTANCE = "Cannot delete new distance";
    public static final String ERR_CANNOT_READ_ALL_DISTANCES = "Cannot read all distances";
    public static final String ERR_CANNOT_COUNT_ALL_DISTANCES = "Cannot count all distances";
    public static final String ERR_CANNOT_READ_DISTANCES_WITH_LIMITATION = "Cannot read distances with limitation";

    // service layer exceptions
    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_USER_BY_ID = "Cannot obtain a user by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_USER_BY_EMAIL = "Cannot obtain a user by its email at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_USER = "Cannot insert new user at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_USER = "Cannot update a user at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_USER = "Cannot delete new user at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_USERS = "Cannot read all users at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_USERS_WITH_LIMITATION = "Cannot read users with limitation at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_ID = "Cannot obtain a city by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_CITY_BY_NAME = "Cannot obtain a city by its email at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_CITY = "Cannot insert new city at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_CITY = "Cannot update a city at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_CITY = "Cannot delete new city at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_CITIES = "Cannot read all cities at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_CITIES_WITH_LIMITATION = "Cannot read cities with limitation at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_DELIVERY_BY_ID = "Cannot obtain a delivery by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_DELIVERY = "Cannot insert new delivery at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_DELIVERY = "Cannot update a delivery at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_DELIVERY = "Cannot delete new delivery at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_DELIVERIES = "Cannot read all deliveries at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_DELIVERIES_WITH_LIMITATION = "Cannot read deliveries with limitation at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ID = "Cannot obtain a distance by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_DISTANCE = "Cannot insert new distance at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_DISTANCE = "Cannot update a distance at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_DISTANCE = "Cannot delete new distance at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_DISTANCES = "Cannot read all distances at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_DISTANCES_WITH_LIMITATION = "Cannot read distances with limitation at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_DISTANCE_BY_ITS_FIELDS = "Cannot obtain a distance by its fields(fromCityId, toCityId) at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_INVOICE_BY_ID = "Cannot obtain a invoice by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_INVOICE = "Cannot insert new invoice at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_INVOICE = "Cannot update a invoice at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_INVOICE = "Cannot delete new invoice at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_INVOICES = "Cannot read all invoice at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_INVOICES_WITH_LIMITATION = "Cannot read invoices with limitation at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_ID = "Cannot obtain a rate by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_RATE_BY_NAME = "Cannot obtain a rate by its name at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_RATE = "Cannot insert new rate at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_RATE = "Cannot update a rate at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_RATE = "Cannot delete new rate at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_RATES = "Cannot read all rate at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_RATES_WITH_LIMITATION = "Cannot read rates with limitation at service layer";

    public static final String ERR_SERVICE_LAYER_CANNOT_OBTAIN_REQUEST_BY_ID = "Cannot obtain a request by its id at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_INSERT_REQUEST = "Cannot insert new request at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_UPDATE_REQUEST = "Cannot update a request at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_DELETE_REQUEST = "Cannot delete new request at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_ALL_REQUESTS = "Cannot read all request at service layer";
    public static final String ERR_SERVICE_LAYER_CANNOT_READ_REQUESTS_WITH_LIMITATION = "Cannot read requests with limitation at service layer";


    //Extractor exceptions
    public static final String ERR_CANNOT_EXTRACT_USER = "Cannot extract new user";
    public static final String ERR_CANNOT_EXTRACT_CITY = "Cannot extract new city";
    public static final String ERR_CANNOT_EXTRACT_REQUEST = "Cannot extract new request";
    public static final String ERR_CANNOT_EXTRACT_INVOICE = "Cannot extract new invoice";
    public static final String ERR_CANNOT_EXTRACT_DELIVERY = "Cannot extract new delivery";
    public static final String ERR_CANNOT_EXTRACT_RATE = "Cannot extract new rate";
    public static final String ERR_CANNOT_EXTRACT_DISTANCE = "Cannot extract new distance";

    // Request/Response expceptions
    public static final String ERR_CANNOT_HANDLE_POST_REQUEST = "Cannot handle post request";
    public static final String ERR_CANNOT_HANDLE_GET_REQUEST = "Cannot handle get request";

    // Builder exceptions
    public static final String ERR_CANNOT_MAP_USER = "Cannot map user";
    public static final String ERR_CANNOT_MAP_CITY = "Cannot map city";
    public static final String ERR_CANNOT_MAP_DISTANCE = "Cannot map distance";
    public static final String ERR_CANNOT_MAP_DELIVERY = "Cannot map delivery";
    public static final String ERR_CANNOT_MAP_INVOICE = "Cannot map invoice";
    public static final String ERR_CANNOT_MAP_REQUEST = "Cannot map request";
    public static final String ERR_CANNOT_MAP_RATE = "Cannot map rate";

    // Command exception

}