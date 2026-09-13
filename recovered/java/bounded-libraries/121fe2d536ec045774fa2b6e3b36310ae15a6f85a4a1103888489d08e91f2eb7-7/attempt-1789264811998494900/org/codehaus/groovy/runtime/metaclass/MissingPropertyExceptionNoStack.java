/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.metaclass;

import groovy.lang.MissingPropertyException;

public class MissingPropertyExceptionNoStack
extends MissingPropertyException {
    private static final long serialVersionUID = 8993570436675442348L;

    public MissingPropertyExceptionNoStack(String propertyName, Class theClass) {
        super(propertyName, theClass);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

