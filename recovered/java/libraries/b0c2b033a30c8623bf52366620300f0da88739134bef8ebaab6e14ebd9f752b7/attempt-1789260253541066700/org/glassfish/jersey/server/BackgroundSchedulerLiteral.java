/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import org.glassfish.jersey.internal.inject.AnnotationLiteral;
import org.glassfish.jersey.server.BackgroundScheduler;

public final class BackgroundSchedulerLiteral
extends AnnotationLiteral<BackgroundScheduler>
implements BackgroundScheduler {
    public static final BackgroundScheduler INSTANCE = new BackgroundSchedulerLiteral();

    private BackgroundSchedulerLiteral() {
    }
}

