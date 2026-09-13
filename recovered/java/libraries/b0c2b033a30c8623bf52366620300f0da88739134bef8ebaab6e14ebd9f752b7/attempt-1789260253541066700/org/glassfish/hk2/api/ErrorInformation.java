/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ErrorType;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;

public interface ErrorInformation {
    public ErrorType getErrorType();

    public Descriptor getDescriptor();

    public Injectee getInjectee();

    public MultiException getAssociatedException();
}

