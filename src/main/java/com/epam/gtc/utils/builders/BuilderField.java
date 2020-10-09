package com.epam.gtc.utils.builders;


import com.epam.gtc.dao.entities.constants.Role;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(BuilderFields.class)
public @interface BuilderField {
    BuilderFieldConstant transferTo() default BuilderFieldConstant.ID;

    Class<?> enumClass() default Role.class;

    String crossQueryName() default "";
}
