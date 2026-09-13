/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.logout.slo;

import org.apereo.cas.logout.slo.SingleLogoutMessage;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;

@FunctionalInterface
public interface SingleLogoutMessageCreator {
    public SingleLogoutMessage create(SingleLogoutRequestContext var1);
}

