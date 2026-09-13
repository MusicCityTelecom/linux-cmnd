/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import javax.ws.rs.client.CompletionStageRxInvoker;
import org.glassfish.jersey.client.JerseyInvocation;

public class JerseyCompletionStageRxInvoker
extends JerseyInvocation.AsyncInvoker
implements CompletionStageRxInvoker {
    JerseyCompletionStageRxInvoker(JerseyInvocation.Builder builder) {
        super(builder);
    }
}

