/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.context;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import reactor.util.annotation.Nullable;

public interface ContextView {
    public <T> T get(Object var1);

    default public <T> T get(Class<T> key) {
        T v = this.get((Object)key);
        if (key.isInstance(v)) {
            return v;
        }
        throw new NoSuchElementException("Context does not contain a value of type " + key.getName());
    }

    @Nullable
    default public <T> T getOrDefault(Object key, @Nullable T defaultValue) {
        if (!this.hasKey(key)) {
            return defaultValue;
        }
        return this.get(key);
    }

    default public <T> Optional<T> getOrEmpty(Object key) {
        if (this.hasKey(key)) {
            return Optional.of(this.get(key));
        }
        return Optional.empty();
    }

    public boolean hasKey(Object var1);

    default public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size();

    public Stream<Map.Entry<Object, Object>> stream();

    default public void forEach(BiConsumer<Object, Object> action) {
        this.stream().forEach((? super T entry) -> action.accept(entry.getKey(), entry.getValue()));
    }
}

