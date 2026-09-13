/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.r2dbc;

class MultipleConnectionPoolConfigurationsException
extends RuntimeException {
    MultipleConnectionPoolConfigurationsException() {
        super("R2DBC connection pooling configuration should be provided by either the spring.r2dbc.pool.* properties or the spring.r2dbc.url property but both have been used.");
    }
}

