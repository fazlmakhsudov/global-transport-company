package com.epam.gtc.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Builder fields annotation
 *
 * @author Fazliddin Makhsudov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BuilderFields {
    BuilderField[] value();
}
