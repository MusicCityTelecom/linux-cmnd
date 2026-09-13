/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;

public interface TreeNodeStream
extends IntStream {
    public Object get(int var1);

    public Object LT(int var1);

    public Object getTreeSource();

    public TokenStream getTokenStream();

    public TreeAdaptor getTreeAdaptor();

    public void setUniqueNavigationNodes(boolean var1);

    public void reset();

    public String toString(Object var1, Object var2);

    public void replaceChildren(Object var1, int var2, int var3, Object var4);
}

