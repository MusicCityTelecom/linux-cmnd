/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.validation.BindException
 *  org.springframework.validation.FieldError
 *  org.springframework.validation.ObjectError
 */
package org.springframework.boot.diagnostics.analyzer;

import java.util.List;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.origin.Origin;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

class BindValidationFailureAnalyzer
extends AbstractFailureAnalyzer<Throwable> {
    BindValidationFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, Throwable cause) {
        ExceptionDetails details = this.getBindValidationExceptionDetails(rootFailure);
        if (details == null) {
            return null;
        }
        return this.analyzeBindValidationException(details);
    }

    private ExceptionDetails getBindValidationExceptionDetails(Throwable rootFailure) {
        BindValidationException validationException = this.findCause(rootFailure, BindValidationException.class);
        if (validationException != null) {
            BindException target = this.findCause(rootFailure, BindException.class);
            List<ObjectError> errors = validationException.getValidationErrors().getAllErrors();
            return new ExceptionDetails(errors, target, validationException);
        }
        org.springframework.validation.BindException bindException = this.findCause(rootFailure, org.springframework.validation.BindException.class);
        if (bindException != null) {
            List errors = bindException.getAllErrors();
            return new ExceptionDetails(errors, bindException.getTarget(), (Throwable)bindException);
        }
        return null;
    }

    private FailureAnalysis analyzeBindValidationException(ExceptionDetails details) {
        StringBuilder description = new StringBuilder(String.format("Binding to target %s failed:%n", details.getTarget()));
        for (ObjectError error : details.getErrors()) {
            if (error instanceof FieldError) {
                this.appendFieldError(description, (FieldError)error);
            }
            description.append(String.format("%n    Reason: %s%n", error.getDefaultMessage()));
        }
        return this.getFailureAnalysis(description, details.getCause());
    }

    private void appendFieldError(StringBuilder description, FieldError error) {
        Origin origin = Origin.from(error);
        description.append(String.format("%n    Property: %s", error.getObjectName() + "." + error.getField()));
        description.append(String.format("%n    Value: \"%s\"", error.getRejectedValue()));
        if (origin != null) {
            description.append(String.format("%n    Origin: %s", origin));
        }
    }

    private FailureAnalysis getFailureAnalysis(Object description, Throwable cause) {
        return new FailureAnalysis(description.toString(), "Update your application's configuration", cause);
    }

    private static class ExceptionDetails {
        private List<ObjectError> errors;
        private Object target;
        private Throwable cause;

        ExceptionDetails(List<ObjectError> errors, Object target, Throwable cause) {
            this.errors = errors;
            this.target = target;
            this.cause = cause;
        }

        Object getTarget() {
            return this.target;
        }

        List<ObjectError> getErrors() {
            return this.errors;
        }

        Throwable getCause() {
            return this.cause;
        }
    }
}

