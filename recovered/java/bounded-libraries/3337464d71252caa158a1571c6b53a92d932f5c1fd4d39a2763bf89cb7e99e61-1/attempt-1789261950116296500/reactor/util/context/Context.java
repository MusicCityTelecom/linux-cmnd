/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.context;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context0;
import reactor.util.context.Context1;
import reactor.util.context.Context2;
import reactor.util.context.Context3;
import reactor.util.context.Context4;
import reactor.util.context.Context5;
import reactor.util.context.ContextN;
import reactor.util.context.ContextView;
import reactor.util.context.CoreContext;

public interface Context
extends ContextView {
    public static Context empty() {
        return Context0.INSTANCE;
    }

    public static Context of(Object key, Object value) {
        return new Context1(key, value);
    }

    public static Context of(Object key1, Object value1, Object key2, Object value2) {
        return new Context2(key1, value1, key2, value2);
    }

    public static Context of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3) {
        return new Context3(key1, value1, key2, value2, key3, value3);
    }

    public static Context of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3, Object key4, Object value4) {
        return new Context4(key1, value1, key2, value2, key3, value3, key4, value4);
    }

    public static Context of(Object key1, Object value1, Object key2, Object value2, Object key3, Object value3, Object key4, Object value4, Object key5, Object value5) {
        return new Context5(key1, value1, key2, value2, key3, value3, key4, value4, key5, value5);
    }

    public static Context of(Map<?, ?> map) {
        int size = Objects.requireNonNull(map, "map").size();
        if (size == 0) {
            return Context.empty();
        }
        if (size <= 5) {
            Map.Entry[] entries = map.entrySet().toArray(new Map.Entry[size]);
            switch (size) {
                case 1: {
                    return new Context1(entries[0].getKey(), entries[0].getValue());
                }
                case 2: {
                    return new Context2(entries[0].getKey(), entries[0].getValue(), entries[1].getKey(), entries[1].getValue());
                }
                case 3: {
                    return new Context3(entries[0].getKey(), entries[0].getValue(), entries[1].getKey(), entries[1].getValue(), entries[2].getKey(), entries[2].getValue());
                }
                case 4: {
                    return new Context4(entries[0].getKey(), entries[0].getValue(), entries[1].getKey(), entries[1].getValue(), entries[2].getKey(), entries[2].getValue(), entries[3].getKey(), entries[3].getValue());
                }
                case 5: {
                    return new Context5(entries[0].getKey(), entries[0].getValue(), entries[1].getKey(), entries[1].getValue(), entries[2].getKey(), entries[2].getValue(), entries[3].getKey(), entries[3].getValue(), entries[4].getKey(), entries[4].getValue());
                }
            }
        }
        map.forEach((? super K key, ? super V value) -> {
            Objects.requireNonNull(key, "null key found");
            if (value == null) {
                throw new NullPointerException("null value for key " + key);
            }
        });
        Map<Object, Object> generifiedMap = map;
        return new ContextN(generifiedMap);
    }

    public static Context of(ContextView contextView) {
        Objects.requireNonNull(contextView, "contextView");
        if (contextView instanceof Context) {
            return (Context)contextView;
        }
        return Context.empty().putAll(contextView);
    }

    default public ContextView readOnly() {
        return this;
    }

    public Context put(Object var1, Object var2);

    default public Context putNonNull(Object key, @Nullable Object valueOrNull) {
        if (valueOrNull != null) {
            return this.put(key, valueOrNull);
        }
        return this;
    }

    public Context delete(Object var1);

    default public Context putAll(ContextView other) {
        if (other.isEmpty()) {
            return this;
        }
        if (other instanceof CoreContext) {
            CoreContext coreContext = (CoreContext)other;
            return coreContext.putAllInto(this);
        }
        ContextN newContext = new ContextN(this.size() + other.size());
        ((Stream)this.stream().sequential()).forEach(newContext);
        ((Stream)other.stream().sequential()).forEach(newContext);
        if (newContext.size() <= 5) {
            return Context.of(newContext);
        }
        return newContext;
    }

    default public Context putAllMap(Map<?, ?> from) {
        if (from.isEmpty()) {
            return this;
        }
        ContextN combined = new ContextN(this.size() + from.size());
        this.forEach(combined);
        from.forEach(combined);
        if (combined.size() <= 5) {
            return Context.of(combined);
        }
        return combined;
    }

    @Deprecated
    default public Context putAll(Context context) {
        return this.putAll(context.readOnly());
    }
}

