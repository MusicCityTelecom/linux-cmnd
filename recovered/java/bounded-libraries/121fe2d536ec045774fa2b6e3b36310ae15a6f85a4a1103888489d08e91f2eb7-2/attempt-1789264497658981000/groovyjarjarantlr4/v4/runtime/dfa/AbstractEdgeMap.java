/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.dfa;

import groovyjarjarantlr4.v4.runtime.dfa.EdgeMap;
import java.util.AbstractSet;
import java.util.Map;

public abstract class AbstractEdgeMap<T>
implements EdgeMap<T> {
    protected final int minIndex;
    protected final int maxIndex;

    public AbstractEdgeMap(int minIndex, int maxIndex) {
        assert (maxIndex - minIndex + 1 >= 0);
        this.minIndex = minIndex;
        this.maxIndex = maxIndex;
    }

    @Override
    public abstract AbstractEdgeMap<T> put(int var1, T var2);

    @Override
    public AbstractEdgeMap<T> putAll(EdgeMap<? extends T> m) {
        EdgeMap<T> result = this;
        for (Map.Entry<Integer, T> entry : m.entrySet()) {
            result = result.put((int)entry.getKey(), (Object)entry.getValue());
        }
        return result;
    }

    @Override
    public abstract AbstractEdgeMap<T> clear();

    @Override
    public abstract AbstractEdgeMap<T> remove(int var1);

    protected abstract class AbstractEntrySet
    extends AbstractSet<Map.Entry<Integer, T>> {
        protected AbstractEntrySet() {
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)o;
            if (entry.getKey() instanceof Integer) {
                Object existing;
                int key = (Integer)entry.getKey();
                Object value = entry.getValue();
                return value == (existing = AbstractEdgeMap.this.get(key)) || existing != null && existing.equals(value);
            }
            return false;
        }

        @Override
        public int size() {
            return AbstractEdgeMap.this.size();
        }
    }
}

