package com.epam.gtc.utils;


import com.epam.gtc.dao.entities.constants.Role;

import java.lang.annotation.*;

/**
 * Builder field annotation
 *
 * @author Fazliddin Makhsudov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(BuilderFields.class)
public @interface BuilderField {
    BuilderFieldConstant transferTo() default BuilderFieldConstant.ID;

    Class<?> enumClass() default Role.class;

    String crossQueryName() default "";
}
