package com.epam.gtc.dao;


import com.epam.gtc.utils.Fields;

public enum Queries {
    //user queries
    SQL__CREATE_USER_SHORT(querydFormer("INSERT INTO users (", Fields.ENTITY_NAME, Fields.USER_SURNAME,
            Fields.USER_EMAIL, Fields.USER_PASSWORD, ") VALUES (?, ?, ?, ?);")),
    SQL__CREATE_USER(querydFormer("INSERT INTO users (", Fields.ENTITY_NAME, Fields.USER_SURNAME,
            Fields.USER_EMAIL, Fields.USER_PASSWORD, Fields.USER_ROLE_ID, ") VALUES (?, ?, ?, ?, ?);")),
    SQL__READ_USER_BY_ID(querydFormer("SELECT * FROM users WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_USER_BY_EMAIL(querydFormer("SELECT * FROM users WHERE ", Fields.USER_EMAIL, " = ?;")),
    SQL__UPDATE_USER_BY_ID(querydFormer("UPDATE users SET ", Fields.ENTITY_NAME + " = ?, ",
            Fields.USER_SURNAME + " = ?, ", Fields.USER_EMAIL + " = ?, ", Fields.USER_PASSWORD + " = ?, ",
            Fields.USER_ROLE_ID + " = ? ", "WHERE", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_USER_BY_ID(querydFormer("DELETE FROM users where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_USERS("SELECT * FROM users;"),
    // city queries
    SQL__CREATE_CITY(querydFormer("INSERT INTO cities (", Fields.ENTITY_NAME, ") VALUES (?);")),
    SQL__READ_CITY_BY_ID(querydFormer("SELECT * FROM cities WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_CITY_BY_NAME(querydFormer("SELECT * FROM cities WHERE ", Fields.ENTITY_NAME, " = ?;")),
    SQL__UPDATE_CITY_BY_ID(querydFormer("UPDATE cities SET ", Fields.ENTITY_NAME + " = ? ",
            "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_CITY_BY_ID(querydFormer("DELETE FROM cities where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_CITIES("SELECT * FROM cities;"),
    // delivery queries
    SQL__CREATE_DELIVERY(querydFormer("INSERT INTO deliveries (", Fields.DELIVERY_STATUS_ID,
            Fields.REQUEST_ID, Fields.CREATED_DATE, Fields.UPDATED_DATE, ") VALUES (?, ?, ?, ?);")),
    SQL__READ_DELIVERY_BY_ID(querydFormer("SELECT * FROM deliveries WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__UPDATE_DELIVERY_BY_ID(querydFormer("UPDATE deliveries SET ", Fields.DELIVERY_STATUS_ID + " = ? ",
            Fields.REQUEST_ID + " = ? ", Fields.CREATED_DATE + " = ? ", Fields.UPDATED_DATE + " = ? ",
            "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_DELIVERY_BY_ID(querydFormer("DELETE FROM deliveries where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_DELIVERIES("SELECT * FROM deliveries;"),
    // distance queries
    SQL__CREATE_DISTANCE(querydFormer("INSERT INTO distances (", Fields.FROM_CITY_ID,
            Fields.TO_CITY_ID, Fields.DISTANCE, ") VALUES (?, ?, ?);")),
    SQL__READ_DISTANCE_BY_ID(querydFormer("SELECT * FROM distances WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__UPDATE_DISTANCE_BY_ID(querydFormer("UPDATE distances SET ", Fields.FROM_CITY_ID + " = ? ",
            Fields.TO_CITY_ID + " = ? ", Fields.DISTANCE + " = ? ", "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_DISTANCE_BY_ID(querydFormer("DELETE FROM distances where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_DISTANCES("SELECT * FROM distances;"),
    // invoice queries
    SQL__CREATE_INVOICE(querydFormer("INSERT INTO invoices (", Fields.COST, Fields.INVOICE_STATUS_ID,
            Fields.REQUEST_ID, Fields.CREATED_DATE, Fields.UPDATED_DATE, ") VALUES (?, ?, ?, ?, ?);")),
    SQL__READ_INVOICE_BY_ID(querydFormer("SELECT * FROM invoices WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__UPDATE_INVOICE_BY_ID(querydFormer("UPDATE invoices SET ", Fields.COST + " = ? ",
            Fields.INVOICE_STATUS_ID + " = ? ", Fields.REQUEST_ID + " = ? ", Fields.CREATED_DATE + " = ? ",
            Fields.UPDATED_DATE + " = ? ", "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_INVOICE_BY_ID(querydFormer("DELETE FROM invoices where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_INVOICES("SELECT * FROM invoices;"),
    // rate queries
    SQL__CREATE_RATE(querydFormer("INSERT INTO rates (", Fields.CARGO_MAX_WEIGHT, Fields.CARGO_MAX_LENGTH,
            Fields.CARGO_MAX_WIDTH, Fields.CARGO_MAX_HEIGHT, Fields.MAX_DISTANCE, Fields.COST,
            ") VALUES (?, ?, ?, ?, ?, ?);")),
    SQL__READ_RATE_BY_ID(querydFormer("SELECT * FROM rates WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__UPDATE_RATE_BY_ID(querydFormer("UPDATE rates SET ", Fields.CARGO_MAX_WEIGHT + " = ? ",
            Fields.CARGO_MAX_LENGTH + " = ? ", Fields.CARGO_MAX_WIDTH + " = ? ", Fields.CARGO_MAX_HEIGHT + " = ? ",
            Fields.MAX_DISTANCE + " = ? ", Fields.COST + " = ? ", "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_RATE_BY_ID(querydFormer("DELETE FROM rates where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_RATES("SELECT * FROM rates;"),
    // request queries
    SQL__CREATE_REQUEST(querydFormer("INSERT INTO requests (",
            Fields.CITY_FROM_ID, Fields.CITY_TO_ID, Fields.CARGO_WEIGHT, Fields.CARGO_LENGTH,
            Fields.CARGO_WIDTH, Fields.CARGO_HEIGHT, Fields.CARGO_DELIVERY_DATE, Fields.USER_ID,
            Fields.CONTENT_TYPE_ID, Fields.REQUEST_STATUS_ID, Fields.CREATED_DATE, Fields.UPDATED_DATE,
            ") VALUES (?, ?, ?, ?, ?, ?);")),
    SQL__READ_REQUEST_BY_ID(querydFormer("SELECT * FROM requests WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__UPDATE_REQUEST_BY_ID(querydFormer("UPDATE requests SET ", Fields.CITY_FROM_ID + " = ? ",
            Fields.CITY_TO_ID + " = ? ", Fields.CARGO_WEIGHT + " = ? ", Fields.CARGO_LENGTH + " = ? ",
            Fields.CARGO_WIDTH + " = ? ", Fields.CARGO_HEIGHT + " = ? ", Fields.CARGO_DELIVERY_DATE + " = ? ",
            Fields.USER_ID + " = ? ", Fields.CONTENT_TYPE_ID + " = ? ", Fields.REQUEST_STATUS_ID + " = ? ",
            Fields.CREATED_DATE + " = ? ", Fields.UPDATED_DATE + " = ? ", "WHERE ", Fields.ENTITY_ID, " = ?;")),
    SQL__DELETE_REQUEST_BY_ID(querydFormer("DELETE FROM requests where ", Fields.ENTITY_ID, " = ?;")),
    SQL__READ_ALL_REQUESTS("SELECT * FROM requests;"),
    ;


    /**
     * Templates:
     * <p>
     * <p>
     * READ_BOOK_BY_ID("select b.id bookId, b.name bookName, b.publishDate, " +
     * "b.authorId, a.id, a.name, a.birthdate from book b inner join author a " +
     * "ON b.authorId = a.id WHERE b.id = ?;"),
     * <p>
     * READ_BOOK_BY_NAME("select b.id bookId, b.name bookName, b.publishDate, b.authorId, " +
     * "a.id, a.name, a.birthdate from book b inner join author a ON b.authorId = a.id WHERE b.name LIKE ?;"),
     * <p>
     * READ_BOOK_BY_AUTHOR_NAME("select b.id bookId, b.name bookName, b.publishDate, b.authorId, " +
     * "a.id, a.name, a.birthdate from book b inner join author a ON b.authorId = a.id WHERE a.name LIKE ?;"),
     * <p>
     * READ_ALL_BOOKS("select b.id bookId, b.name bookName, b.publishDate, b.authorId, " +
     * "a.id, a.name, a.birthdate FROM book b inner join author a ON b.authorId = a.id;"),
     * UPDATE_BOOK("UPDATE book SET name = ?, publishDate = ?, authorId = ? WHERE id = ?;"),
     * DELETE_BOOK("DELETE FROM book where id = ?;"),
     */

    private final String query;

    public String getQuery() {
        return query;
    }

    Queries(String query) {
        this.query = query;
    }

    /**
     * Forms query
     *
     * @param fields
     * @return
     */
    private static synchronized String querydFormer(String... fields) {
        StringBuilder sb = new StringBuilder();
        sb.append(fields[0]);
        for (int i = 1; i < fields.length - 1; i++) {
            sb.append(fields[i]);
            if (fields.length - 2 != i) {
                sb.append(", ");
            }
        }
        sb.append(fields[fields.length - 1]);
        return sb.toString();
    }
}
