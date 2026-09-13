/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.inspektr.audit;

import java.io.Serializable;

public interface AuditPointRuntimeInfo
extends Serializable {
    default public String asString() {
        return null;
    }
}

