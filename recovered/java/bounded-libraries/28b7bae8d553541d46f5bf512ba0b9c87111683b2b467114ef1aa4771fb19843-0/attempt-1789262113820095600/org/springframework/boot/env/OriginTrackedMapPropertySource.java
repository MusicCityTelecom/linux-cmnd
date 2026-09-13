/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.MapPropertySource
 */
package org.springframework.boot.env;

import java.util.Map;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.MapPropertySource;

public final class OriginTrackedMapPropertySource
extends MapPropertySource
implements OriginLookup<String> {
    private final boolean immutable;

    public OriginTrackedMapPropertySource(String name, Map source) {
        this(name, source, false);
    }

    public OriginTrackedMapPropertySource(String name, Map source, boolean immutable) {
        super(name, source);
        this.immutable = immutable;
    }

    public Object getProperty(String name) {
        Object value = super.getProperty(name);
        if (value instanceof OriginTrackedValue) {
            return ((OriginTrackedValue)value).getValue();
        }
        return value;
    }

    @Override
    public Origin getOrigin(String name) {
        Object value = super.getProperty(name);
        if (value instanceof OriginTrackedValue) {
            return ((OriginTrackedValue)value).getOrigin();
        }
        return null;
    }

    @Override
    public boolean isImmutable() {
        return this.immutable;
    }
}

