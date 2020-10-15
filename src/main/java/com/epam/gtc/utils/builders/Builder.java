package com.epam.gtc.utils.builders;

import com.epam.gtc.exceptions.BuilderException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Builder superclass,
 * transforms relational classes: entity <-> domain <-> model
 *
 * @param <F> transform from class object
 * @param <T> transform to class object
 */
public abstract class Builder<F, T> {
    private static final Logger LOG = Logger.getLogger(Builder.class);

    /**
     * Transforms single object of Class<F>
     * to object of class<T>
     *
     * @param object from class object
     * @return transformed object
     *
     * @throws BuilderException exception
     */
    public abstract T create(F object) throws BuilderException;

    /**
     * Transforms list of F objects
     * to list of T objects
     *
     * @param list of F objects
     * @return list of T objects
     *
     * @throws BuilderException exception
     */
    public abstract List<T> create(List<F> list) throws BuilderException;

    protected T build(F objectFrom, Class<T> clazzTo) throws BuilderException {

        Field[] fieldsFrom = objectFrom.getClass().getDeclaredFields();
        Field[] fieldsTo = clazzTo.getDeclaredFields();

        Map<String, Object> mapOfInitializedFields = new HashMap<>();
        putInitializedFields(objectFrom, fieldsFrom, mapOfInitializedFields, fieldsTo);
        T objectTo;
        try {
            objectTo = clazzTo.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error(e.getMessage());
            throw new BuilderException(e.getMessage(), e);
        }
        initializeObjectToFields(fieldsTo, mapOfInitializedFields, objectTo);
        return objectTo;
    }

    private void putInitializedFields(F objectFrom, Field[] fieldsFrom, Map<String, Object> mapOfInitializedFields,
                                      Field[] fieldsTo) {
        Arrays.stream(fieldsFrom)
                .filter(field -> !field.getName().equalsIgnoreCase("serialVersionUID"))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        boolean isPrimitiveFlag = field.getType().isPrimitive();
                        boolean isPrimitiveEqualZero = false;
                        if (isPrimitiveFlag) {
                            String s = field.get(objectFrom) + "";
                            isPrimitiveEqualZero = s.matches("[0.]+");
                        }
                        if (!isPrimitiveFlag && !Objects.isNull(field.get(objectFrom))
                                || isPrimitiveFlag && !isPrimitiveEqualZero) {
                            Object value = getValueOfField(objectFrom, field, fieldsTo);
                            BuilderField builderField = field.getDeclaredAnnotation(BuilderField.class);
                            if (Objects.isNull(builderField)) {
                                mapOfInitializedFields.put(field.getName(), value);
                            } else {
                                String crossQueryName = builderField.crossQueryName().isEmpty() ?
                                        field.getName() : builderField.crossQueryName();
                                mapOfInitializedFields.put(crossQueryName, value);
                            }

                        }
                    } catch (IllegalAccessException | BuilderException e) {
                        LOG.error(e.getMessage());
                    }
                });
    }

    private Object getValueOfField(F objectFrom, Field field, Field[] fieldsTo) throws IllegalAccessException, BuilderException {
        Object value = null;
        BuilderField[] hasAnnotatedField = field.getAnnotationsByType(BuilderField.class);
        if (hasAnnotatedField.length > 1) {
            int[] annotationNumber = new int[1];
            Arrays.stream(fieldsTo)
                    .filter(fieldTo -> !Objects.isNull(fieldTo.getDeclaredAnnotation(BuilderField.class)))
                    .forEach(fieldTo -> {
                        BuilderField builderFieldFieldTo = fieldTo.getDeclaredAnnotation(BuilderField.class);
                        String crossQueryNameOfFieldTo = builderFieldFieldTo.crossQueryName();
                        String crossQueryNameOfFieldFrom = hasAnnotatedField[0].crossQueryName();
                        if (crossQueryNameOfFieldFrom.equalsIgnoreCase(crossQueryNameOfFieldTo)) {
                            annotationNumber[0] = fieldTo.getType().getSimpleName().equalsIgnoreCase("int") ?
                                    0 : 1;
                        }
                    });
            value = getValueAccordingAnnotaionFields(objectFrom, field.get(objectFrom), value,
                    hasAnnotatedField[annotationNumber[0]]);
        } else if (hasAnnotatedField.length == 1) {
            value = getValueAccordingAnnotaionFields(objectFrom, field.get(objectFrom), value, hasAnnotatedField[0]);
        } else {
            value = field.get(objectFrom);
        }
        return value;
    }

    private Object getValueAccordingAnnotaionFields(F objectFrom, Object obj, Object value,
                                                    BuilderField annotatedField1) throws IllegalAccessException, BuilderException {
        BuilderFieldConstant transferTo = annotatedField1.transferTo();
        Class<?> enumClass = annotatedField1.enumClass();
        Method methodGetName;
        switch (transferTo) {
            case ID:
                Method methodGetId;
                try {
                    methodGetId = enumClass.getDeclaredMethod(BuilderFieldConstant.GET_ID.getName(), String.class);
                    methodGetName = enumClass.getDeclaredMethod(BuilderFieldConstant.GET_NAME.getName());
                    System.out.println(methodGetName);
                    String name = (String) methodGetName.invoke(obj);
                    value = methodGetId.invoke(obj, name);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOG.error(e.getMessage());
                    throw new BuilderException(e.getMessage(), e);
                }
                break;
            case NAME:
                try {
                    methodGetName = enumClass.getDeclaredMethod(BuilderFieldConstant.GET_NAME.getName());
                    value = methodGetName.invoke(obj);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOG.error(e.getMessage());
                    throw new BuilderException(e.getMessage(), e);
                }
                break;
            case ENUM_FROM_NAME:
                Method methodGetEnumFromName;
                try {
                    methodGetEnumFromName = enumClass.getDeclaredMethod(BuilderFieldConstant.GET_ENUM_FROM_NAME.getName(), String.class);
                    methodGetEnumFromName.setAccessible(true);
                    value = methodGetEnumFromName.invoke(objectFrom, obj);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOG.error(e.getMessage());
                    throw new BuilderException(e.getMessage(), e);
                }
                break;
            case ENUM_FROM_ID:
                Method methodGetEnumFromId;
                try {
                    methodGetEnumFromId = enumClass.getDeclaredMethod(BuilderFieldConstant.GET_ENUM_FROM_ID.getName(), int.class);
                    methodGetEnumFromId.setAccessible(true);
                    value = methodGetEnumFromId.invoke(objectFrom, obj);
                } catch (NoSuchMethodException | InvocationTargetException e) {
                    LOG.error(e.getMessage());
                    throw new BuilderException(e.getMessage(), e);
                }
                break;
            case LOCALDATE_FROM_DATE:
                Instant current = ((Date) obj).toInstant();
                value = LocalDateTime.ofInstant(current, ZoneId.systemDefault());
                break;
            case TIMESTAMP:
                value = Timestamp.valueOf((LocalDateTime) obj);
                break;
            case LOCALDATE_FROM_TIMESTAMP:
                value = ((Timestamp) obj).toLocalDateTime();
                break;
        }
        return value;
    }

    private void initializeObjectToFields(Field[] fieldsTo, Map<String, Object> mapOfInitializedFields,
                                          final T objectTo) {
        Arrays.stream(fieldsTo)
                .filter(field -> {
                    if (!Objects.isNull(field.getDeclaredAnnotation(BuilderField.class))) {
                        return true;
                    }
                    return mapOfInitializedFields.containsKey(field.getName());
                })
                .forEach(field -> {
                    field.setAccessible(true);
                    BuilderField builderField = field.getDeclaredAnnotation(BuilderField.class);
                    Object key;
                    if (Objects.isNull(builderField)) {
                        key = field.getName();
                    } else {
                        key = builderField.crossQueryName().isEmpty() ?
                                field.getName() : builderField.crossQueryName();
                    }
                    try {
                        field.set(objectTo, mapOfInitializedFields.get(key));
                    } catch (IllegalAccessException e) {
                        LOG.error(e.getMessage());
                    }
                });
    }
}
