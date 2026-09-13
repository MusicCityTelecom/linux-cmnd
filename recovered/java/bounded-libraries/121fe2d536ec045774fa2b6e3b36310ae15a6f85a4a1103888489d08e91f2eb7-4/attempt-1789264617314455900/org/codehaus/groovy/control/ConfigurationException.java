/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import org.codehaus.groovy.GroovyExceptionInterface;

public class ConfigurationException
extends RuntimeException
implements GroovyExceptionInterface {
    private static final long serialVersionUID = -3844401402301111613L;
    protected Exception cause;

    public ConfigurationException(Exception cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    public ConfigurationException(String message) {
        super(message);
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public boolean isFatal() {
        return true;
    }

    @Override
    public void setFatal(boolean fatal) {
    }
}

