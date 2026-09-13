/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.autoconfigure.r2dbc;

class MissingR2dbcPoolDependencyException
extends RuntimeException {
    MissingR2dbcPoolDependencyException() {
        super("R2DBC connection pooling has been configured but the io.r2dbc.pool.ConnectionPool class is not present.");
    }
}

