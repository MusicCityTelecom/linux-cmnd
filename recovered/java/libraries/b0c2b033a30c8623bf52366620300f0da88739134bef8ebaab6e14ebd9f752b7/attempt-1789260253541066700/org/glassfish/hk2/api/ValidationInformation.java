/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.Operation;

public interface ValidationInformation {
    public Operation getOperation();

    public ActiveDescriptor<?> getCandidate();

    public Injectee getInjectee();

    public Filter getFilter();

    public StackTraceElement getCaller();
}

