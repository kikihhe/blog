package com.xiaohe.utils.annotitions;

import com.xiaohe.utils.verify.VerifyPhone;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 21:13
 */
@Documented
@Constraint(validatedBy = {VerifyPhone.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Phone {
    String message();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
