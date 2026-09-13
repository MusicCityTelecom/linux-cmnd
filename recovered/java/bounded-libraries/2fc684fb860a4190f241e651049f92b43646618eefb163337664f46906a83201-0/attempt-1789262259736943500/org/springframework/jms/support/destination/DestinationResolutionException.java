/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.jms.support.destination;

import org.springframework.jms.JmsException;
import org.springframework.lang.Nullable;

public class DestinationResolutionException
extends JmsException {
    public DestinationResolutionException(String msg) {
        super(msg);
    }

    public DestinationResolutionException(String msg, @Nullable Throwable cause) {
        super(msg, cause);
    }
}

