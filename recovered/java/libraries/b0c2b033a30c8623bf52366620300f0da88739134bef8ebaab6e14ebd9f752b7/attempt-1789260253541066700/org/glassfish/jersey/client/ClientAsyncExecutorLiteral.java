/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client;

import org.glassfish.jersey.client.ClientAsyncExecutor;
import org.glassfish.jersey.internal.inject.AnnotationLiteral;

public final class ClientAsyncExecutorLiteral
extends AnnotationLiteral<ClientAsyncExecutor>
implements ClientAsyncExecutor {
    public static final ClientAsyncExecutor INSTANCE = new ClientAsyncExecutorLiteral();

    private ClientAsyncExecutorLiteral() {
    }
}

