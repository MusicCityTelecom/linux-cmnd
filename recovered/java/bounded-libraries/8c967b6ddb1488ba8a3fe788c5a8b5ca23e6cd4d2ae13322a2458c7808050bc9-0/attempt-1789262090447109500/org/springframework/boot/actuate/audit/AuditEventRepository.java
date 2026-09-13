/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.audit;

import java.time.Instant;
import java.util.List;
import org.springframework.boot.actuate.audit.AuditEvent;

public interface AuditEventRepository {
    public void add(AuditEvent var1);

    public List<AuditEvent> find(String var1, Instant var2, String var3);
}

