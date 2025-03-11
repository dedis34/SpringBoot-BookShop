package org.example.customAnnotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.UserRegistrationRequestDto;
import org.example.customAnnotations.FieldMatch;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, UserRegistrationRequestDto> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(UserRegistrationRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        String fieldValue = getFieldValue(dto, field);
        String fieldMatchValue = getFieldValue(dto, fieldMatch);

        return fieldValue != null && fieldValue.equals(fieldMatchValue);
    }

    private String getFieldValue(UserRegistrationRequestDto dto, String fieldName) {
        try {
            return (String) dto.getClass().getDeclaredField(fieldName).get(dto);
        } catch (Exception e) {
            return null;
        }
    }
}
