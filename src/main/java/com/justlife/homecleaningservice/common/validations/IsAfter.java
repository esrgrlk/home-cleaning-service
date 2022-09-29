package com.justlife.homecleaningservice.common.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { TimeValidator.class })
@Documented
public @interface IsAfter {

    String message() default "{message.key}";

    String time();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}