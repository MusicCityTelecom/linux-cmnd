/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.WebApplicationService
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.logout.slo;

import java.util.Collection;
import org.apereo.cas.authentication.principal.WebApplicationService;
import org.apereo.cas.logout.SingleLogoutExecutionRequest;
import org.apereo.cas.logout.slo.SingleLogoutMessage;
import org.apereo.cas.logout.slo.SingleLogoutRequestContext;
import org.springframework.core.Ordered;

public interface SingleLogoutServiceMessageHandler
extends Ordered {
    public Collection<SingleLogoutRequestContext> handle(WebApplicationService var1, String var2, SingleLogoutExecutionRequest var3);

    default public String getName() {
        return this.getClass().getSimpleName();
    }

    default public boolean supports(SingleLogoutExecutionRequest context, WebApplicationService service) {
        return service != null;
    }

    public boolean performBackChannelLogout(SingleLogoutRequestContext var1);

    public SingleLogoutMessage createSingleLogoutMessage(SingleLogoutRequestContext var1);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

