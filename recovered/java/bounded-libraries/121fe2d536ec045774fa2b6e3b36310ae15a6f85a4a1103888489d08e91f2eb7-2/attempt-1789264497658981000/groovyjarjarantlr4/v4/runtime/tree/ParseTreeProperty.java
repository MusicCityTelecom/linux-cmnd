/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree;

import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import java.util.IdentityHashMap;
import java.util.Map;

public class ParseTreeProperty<V> {
    protected Map<ParseTree, V> annotations = new IdentityHashMap<ParseTree, V>();

    public V get(ParseTree node) {
        return this.annotations.get(node);
    }

    public void put(ParseTree node, V value) {
        this.annotations.put(node, value);
    }

    public V removeFrom(ParseTree node) {
        return this.annotations.remove(node);
    }
}

