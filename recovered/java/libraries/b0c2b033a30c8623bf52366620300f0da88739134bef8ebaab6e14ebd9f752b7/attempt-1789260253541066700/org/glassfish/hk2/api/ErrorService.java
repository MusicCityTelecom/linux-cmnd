/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.ErrorInformation;
import org.glassfish.hk2.api.MultiException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ErrorService {
    public void onFailure(ErrorInformation var1) throws MultiException;
}

