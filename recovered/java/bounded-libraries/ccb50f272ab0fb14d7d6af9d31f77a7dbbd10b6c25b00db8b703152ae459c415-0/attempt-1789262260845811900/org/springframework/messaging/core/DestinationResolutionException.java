/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.messaging.core;

import org.springframework.lang.Nullable;
import org.springframework.messaging.MessagingException;

public class DestinationResolutionException
extends MessagingException {
    public DestinationResolutionException(String description) {
        super(description);
    }

    public DestinationResolutionException(@Nullable String description, @Nullable Throwable cause) {
        super(description, cause);
    }
}

