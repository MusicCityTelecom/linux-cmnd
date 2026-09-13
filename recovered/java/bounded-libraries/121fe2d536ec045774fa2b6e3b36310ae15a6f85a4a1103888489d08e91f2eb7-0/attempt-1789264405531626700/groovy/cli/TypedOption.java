/*
 * Decompiled with CFR 0.152.
 */
package groovy.cli;

import java.util.HashMap;

public class TypedOption<T>
extends HashMap<String, T> {
    private static final long serialVersionUID = 8931624081859777854L;

    public T defaultValue() {
        return (T)super.get("defaultValue");
    }
}

