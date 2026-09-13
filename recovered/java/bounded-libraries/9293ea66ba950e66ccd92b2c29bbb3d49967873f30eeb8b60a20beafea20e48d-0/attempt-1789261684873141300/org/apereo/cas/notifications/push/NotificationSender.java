/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Principal
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.notifications.push;

import java.util.Map;
import org.apereo.cas.authentication.principal.Principal;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface NotificationSender
extends Ordered {
    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public boolean canSend() {
        return true;
    }

    public boolean notify(Principal var1, Map<String, String> var2);

    public static NotificationSender noOp() {
        return (principal, messageData) -> true;
    }
}

