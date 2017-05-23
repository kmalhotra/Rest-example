package com.assignment.birds.validator;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * Created by Karan Malhotra on 26/4/17.
 */
public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, Collection<String>> {

    @Override
    public void initialize(NotEmptyFields notEmptyFields) {
    }

    @Override
    public boolean isValid(Collection<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isNotEmpty(strings))
            return strings.stream().allMatch(element -> StringUtils.isNotBlank(element));

        return true;
    }


}
