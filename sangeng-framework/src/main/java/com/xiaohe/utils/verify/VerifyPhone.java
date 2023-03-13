package com.xiaohe.utils.verify;

import com.xiaohe.utils.VerifyUtils;
import com.xiaohe.utils.annotitions.Phone;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-03-13 21:15
 */
public class VerifyPhone implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return VerifyUtils.isPhoneLegal(value);
    }
}
