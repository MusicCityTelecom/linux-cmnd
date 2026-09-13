/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.OkHttpClient$Builder
 */
package org.springframework.boot.autoconfigure.influx;

import java.util.function.Supplier;
import okhttp3.OkHttpClient;

@FunctionalInterface
public interface InfluxDbOkHttpClientBuilderProvider
extends Supplier<OkHttpClient.Builder> {
}

