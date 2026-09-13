/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ValidationException
extends Exception {
    private static final long serialVersionUID = -1697832087051681357L;
    private Collection<Exception> validationExceptions = new ArrayList<Exception>();

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Collection<Exception> errors) {
        this();
        this.validationExceptions = Collections.unmodifiableCollection(this.validationExceptions);
        this.initCause(errors.iterator().next());
    }

    public ValidationException(String message, Collection<Exception> errors) {
        this(message);
        this.validationExceptions = Collections.unmodifiableCollection(this.validationExceptions);
        this.initCause(errors.iterator().next());
    }

    public Collection<Exception> getValidationExceptions() {
        return this.validationExceptions;
    }

    @Override
    public String getMessage() {
        if (this.getValidationExceptions().size() == 0) {
            return super.getMessage();
        }
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Exception e : this.getValidationExceptions()) {
            if (!first) {
                sb.append('\n');
                first = false;
            }
            sb.append(e.getMessage());
        }
        return sb.toString();
    }
}

