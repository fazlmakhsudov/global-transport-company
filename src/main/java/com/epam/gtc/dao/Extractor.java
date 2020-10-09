package com.epam.gtc.dao;

import com.epam.gtc.exceptions.DAOException;
import com.epam.gtc.utils.Fields;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Superclass for all extractors,
 * extracts entity from ResultSet object
 *
 * @param <T> entity class
 */
public abstract class Extractor<T> {
    private static final Logger LOG = Logger.getLogger(Extractor.class);

    /**
     * Extracts an entity from the result set.
     *
     * @param rs given ResulSet object
     * @return an entity  object
     */
    public abstract T extract(final ResultSet rs) throws SQLException, DAOException;

    /**
     * Maps fields and columns
     *
     * @param rs      given ResultSet object
     * @param clazzTo entity class
     * @return an entity object
     *
     * @throws SQLException
     */
    protected T mapper(ResultSet rs, Class<T> clazzTo) throws SQLException {
        T objectTo = null;
        try {
            objectTo = clazzTo.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage(), e);
        }
        Map<String, String> columnFieldMapper = getFieldsColumnsMap(rs);
        return getObjectTo(rs, clazzTo, objectTo, columnFieldMapper);
    }

    private T getObjectTo(ResultSet rs, Class<T> clazzTo, T objectTo, Map<String, String> columnFieldMapper) {
        Field[] fields = clazzTo.getDeclaredFields();
        Arrays.stream(fields).filter(field -> columnFieldMapper.containsKey(field.getName()))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        if (field.getName().matches("[A-z]+[Dd][Aa][Tt][Ee]")) {
                            field.set(objectTo, rs.getTimestamp(columnFieldMapper.get(field.getName())));
                        } else {
                            field.set(objectTo, rs.getObject(columnFieldMapper.get(field.getName())));
                        }
                    } catch (IllegalAccessException e) {
                        LOG.info(field.getName());
                        LOG.error(e.getMessage(), e);
                    } catch (SQLException e) {
                        LOG.error(e.getMessage(), e);
                    }
                });
        return objectTo;
    }

    private Map<String, String> getFieldsColumnsMap(ResultSet rs) throws SQLException {
        ResultSetMetaData rsm = rs.getMetaData();
        int columnCount = rsm.getColumnCount();
        Map<String, String> columnNames = new HashMap<>();
        for (int i = 1; i <= columnCount; i++) {
            String name = rsm.getColumnName(i);
            columnNames.put(Fields.findAndMapField(name), name);
        }
        return columnNames;
    }
}
