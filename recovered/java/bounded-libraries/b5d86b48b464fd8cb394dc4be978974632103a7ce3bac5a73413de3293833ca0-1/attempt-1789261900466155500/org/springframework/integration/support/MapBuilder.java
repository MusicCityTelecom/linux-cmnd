/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 */
package org.springframework.integration.support;

import java.util.HashMap;
import java.util.Map;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class MapBuilder<B extends MapBuilder<B, K, V>, K, V> {
    protected static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private final Map<K, V> map = new HashMap();

    public B put(K key, V value) {
        this.map.put(key, value);
        return this._this();
    }

    public Map<K, V> get() {
        return this.map;
    }

    protected final B _this() {
        return (B)this;
    }
}

