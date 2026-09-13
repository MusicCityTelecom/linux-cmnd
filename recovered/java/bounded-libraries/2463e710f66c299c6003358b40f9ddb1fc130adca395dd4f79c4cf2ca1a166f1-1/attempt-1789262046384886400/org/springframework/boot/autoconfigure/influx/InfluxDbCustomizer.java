/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.influxdb.InfluxDB
 */
package org.springframework.boot.autoconfigure.influx;

import org.influxdb.InfluxDB;

@FunctionalInterface
public interface InfluxDbCustomizer {
    public void customize(InfluxDB var1);
}

