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
import reactor.util.context.Context1;
import reactor.util.context.Context3;
import reactor.util.context.ContextN;
import reactor.util.context.CoreContext;

final class Context2
implements CoreContext {
    final Object key1;
    final Object value1;
    final Object key2;
    final Object value2;

    Context2(Object key1, Object value1, Object key2, Object value2) {
        if (Objects.requireNonNull(key1, "key1").equals(key2)) {
            throw new IllegalArgumentException("Key #1 (" + key1 + ") is duplicated");
        }
        this.key1 = key1;
        this.value1 = Objects.requireNonNull(value1, "value1");
        this.key2 = Objects.requireNonNull(key2, "key2");
        this.value2 = Objects.requireNonNull(value2, "value2");
    }

    @Override
    public Context put(Object key, Object value) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        if (this.key1.equals(key)) {
            return new Context2(key, value, this.key2, this.value2);
        }
        if (this.key2.equals(key)) {
            return new Context2(this.key1, this.value1, key, value);
        }
        return new Context3(this.key1, this.value1, this.key2, this.value2, key, value);
    }

    @Override
    public Context delete(Object key) {
        Objects.requireNonNull(key, "key");
        if (this.key1.equals(key)) {
            return new Context1(this.key2, this.value2);
        }
        if (this.key2.equals(key)) {
            return new Context1(this.key1, this.value1);
        }
        return this;
    }

    @Override
    public boolean hasKey(Object key) {
        return this.key1.equals(key) || this.key2.equals(key);
    }

    @Override
    public <T> T get(Object key) {
        if (this.key1.equals(key)) {
            return (T)this.value1;
        }
        if (this.key2.equals(key)) {
            return (T)this.value2;
        }
        throw new NoSuchElementException("Context does not contain key: " + key);
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public Stream<Map.Entry<Object, Object>> stream() {
        return Stream.of(new AbstractMap.SimpleImmutableEntry<Object, Object>(this.key1, this.value1), new AbstractMap.SimpleImmutableEntry<Object, Object>(this.key2, this.value2));
    }

    @Override
    public void forEach(BiConsumer<Object, Object> action) {
        action.accept(this.key1, this.value1);
        action.accept(this.key2, this.value2);
    }

    @Override
    public Context putAllInto(Context base) {
        return base.put(this.key1, this.value1).put(this.key2, this.value2);
    }

    @Override
    public void unsafePutAllInto(ContextN other) {
        other.accept(this.key1, this.value1);
        other.accept(this.key2, this.value2);
    }

    public String toString() {
        return "Context2{" + this.key1 + '=' + this.value1 + ", " + this.key2 + '=' + this.value2 + '}';
    }
}

