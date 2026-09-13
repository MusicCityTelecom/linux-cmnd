/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public interface Tree {
    public static final Tree INVALID_NODE = new CommonTree(Token.INVALID_TOKEN);

    public Tree getChild(int var1);

    public int getChildCount();

    public Tree getParent();

    public void setParent(Tree var1);

    public boolean hasAncestor(int var1);

    public Tree getAncestor(int var1);

    public List<?> getAncestors();

    public int getChildIndex();

    public void setChildIndex(int var1);

    public void freshenParentAndChildIndexes();

    public void addChild(Tree var1);

    public void setChild(int var1, Tree var2);

    public Object deleteChild(int var1);

    public void replaceChildren(int var1, int var2, Object var3);

    public boolean isNil();

    public int getTokenStartIndex();

    public void setTokenStartIndex(int var1);

    public int getTokenStopIndex();

    public void setTokenStopIndex(int var1);

    public Tree dupNode();

    public int getType();

    public String getText();

    public int getLine();

    public int getCharPositionInLine();

    public String toStringTree();

    public String toString();
}

