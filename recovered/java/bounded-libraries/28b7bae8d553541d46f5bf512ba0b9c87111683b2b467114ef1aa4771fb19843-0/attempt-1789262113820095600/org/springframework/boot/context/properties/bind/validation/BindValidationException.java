/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.context.properties.bind.validation;

import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.util.Assert;

public class BindValidationException
extends RuntimeException {
    private final ValidationErrors validationErrors;

    BindValidationException(ValidationErrors validationErrors) {
        super(BindValidationException.getMessage(validationErrors));
        Assert.notNull((Object)validationErrors, (String)"ValidationErrors must not be null");
        this.validationErrors = validationErrors;
    }

    public ValidationErrors getValidationErrors() {
        return this.validationErrors;
    }

    private static String getMessage(ValidationErrors errors) {
        StringBuilder message = new StringBuilder("Binding validation errors");
        if (errors != null) {
            message.append(" on ").append(errors.getName());
            errors.getAllErrors().forEach(error -> message.append(String.format("%n   - %s", error)));
        }
        return message.toString();
    }
}

