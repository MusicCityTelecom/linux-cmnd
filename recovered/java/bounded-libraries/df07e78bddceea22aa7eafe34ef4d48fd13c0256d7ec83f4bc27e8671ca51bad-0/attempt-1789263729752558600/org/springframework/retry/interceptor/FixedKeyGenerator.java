/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.interceptor;

import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;

public class FixedKeyGenerator
implements MethodArgumentsKeyGenerator {
    private String label;

    public FixedKeyGenerator(String label) {
        this.label = label;
    }

    @Override
    public Object getKey(Object[] item) {
        return this.label;
    }
}

