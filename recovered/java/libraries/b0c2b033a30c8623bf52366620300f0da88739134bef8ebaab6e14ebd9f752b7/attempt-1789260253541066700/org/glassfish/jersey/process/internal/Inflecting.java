/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.process.internal;

import org.glassfish.jersey.process.Inflector;

public interface Inflecting<DATA, RESULT> {
    public Inflector<DATA, RESULT> inflector();
}

