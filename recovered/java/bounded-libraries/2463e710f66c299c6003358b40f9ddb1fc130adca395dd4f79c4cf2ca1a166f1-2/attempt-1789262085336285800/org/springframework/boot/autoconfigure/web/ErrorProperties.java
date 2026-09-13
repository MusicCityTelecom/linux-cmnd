/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.annotation.Value
 */
package org.springframework.boot.autoconfigure.web;

import org.springframework.beans.factory.annotation.Value;

public class ErrorProperties {
    @Value(value="${error.path:/error}")
    private String path = "/error";
    private boolean includeException;
    private IncludeAttribute includeStacktrace = IncludeAttribute.NEVER;
    private IncludeAttribute includeMessage = IncludeAttribute.NEVER;
    private IncludeAttribute includeBindingErrors = IncludeAttribute.NEVER;
    private final Whitelabel whitelabel = new Whitelabel();

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIncludeException() {
        return this.includeException;
    }

    public void setIncludeException(boolean includeException) {
        this.includeException = includeException;
    }

    public IncludeAttribute getIncludeStacktrace() {
        return this.includeStacktrace;
    }

    public void setIncludeStacktrace(IncludeAttribute includeStacktrace) {
        this.includeStacktrace = includeStacktrace;
    }

    public IncludeAttribute getIncludeMessage() {
        return this.includeMessage;
    }

    public void setIncludeMessage(IncludeAttribute includeMessage) {
        this.includeMessage = includeMessage;
    }

    public IncludeAttribute getIncludeBindingErrors() {
        return this.includeBindingErrors;
    }

    public void setIncludeBindingErrors(IncludeAttribute includeBindingErrors) {
        this.includeBindingErrors = includeBindingErrors;
    }

    public Whitelabel getWhitelabel() {
        return this.whitelabel;
    }

    public static class Whitelabel {
        private boolean enabled = true;

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static enum IncludeAttribute {
        NEVER,
        ALWAYS,
        ON_PARAM;

    }

    public static enum IncludeStacktrace {
        NEVER,
        ALWAYS,
        ON_PARAM;

    }
}

