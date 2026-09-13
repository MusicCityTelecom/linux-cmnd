/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import groovyjarjarantlr4.v4.misc.MutableInt;
import java.util.HashMap;

public class FrequencySet<T>
extends HashMap<T, MutableInt> {
    private static final long serialVersionUID = -134984205245480177L;

    public int count(T key) {
        MutableInt value = (MutableInt)this.get(key);
        if (value == null) {
            return 0;
        }
        return value.v;
    }

    public void add(T key) {
        MutableInt value = (MutableInt)this.get(key);
        if (value == null) {
            value = new MutableInt(1);
            this.put(key, value);
        } else {
            ++value.v;
        }
    }
}

