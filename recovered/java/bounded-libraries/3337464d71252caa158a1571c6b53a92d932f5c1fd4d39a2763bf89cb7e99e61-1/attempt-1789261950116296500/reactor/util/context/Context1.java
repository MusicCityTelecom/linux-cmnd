/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.context;

import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import reactor.util.context.Context;
import reactor.util.context.Context2;
import reactor.util.context.ContextN;
import reactor.util.context.CoreContext;

final class Context1
implements CoreContext {
    final Object key;
    final Object value;

    Context1(Object key, Object value) {
        this.key = Objects.requireNonNull(key, "key");
        this.value = Objects.requireNonNull(value, "value");
    }

    @Override
    public Context put(Object key, Object value) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        if (this.key.equals(key)) {
            return new Context1(key, value);
        }
        return new Context2(this.key, this.value, key, value);
    }

    @Override
    public Context delete(Object key) {
        Objects.requireNonNull(key, "key");
        if (this.key.equals(key)) {
            return Context.empty();
        }
        return this;
    }

    @Override
    public boolean hasKey(Object key) {
        return this.key.equals(key);
    }

    @Override
    public <T> T get(Object key) {
        if (this.hasKey(key)) {
            return (T)this.value;
        }
        throw new NoSuchElementException("Context does not contain key: " + key);
    }

    @Override
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.of(new AbstractMap.SimpleImmutableEntry<Object, Object>(this.key, this.value));
    }

    @Override
    public void forEach(BiConsumer<Object, Object> action) {
        action.accept(this.key, this.value);
    }

    @Override
    public Context putAllInto(Context base) {
        return base.put(this.key, this.value);
    }

    @Override
    public void unsafePutAllInto(ContextN other) {
        other.accept(this.key, this.value);
    }

    @Override
    public int size() {
        return 1;
    }

    public String toString() {
        return "Context1{" + this.key + '=' + this.value + '}';
    }
}

