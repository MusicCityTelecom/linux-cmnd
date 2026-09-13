/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.Tree;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class BaseTree
implements Tree {
    protected List<Object> children;

    public BaseTree() {
    }

    public BaseTree(Tree node) {
    }

    @Override
    public Tree getChild(int i) {
        if (this.children == null || i >= this.children.size()) {
            return null;
        }
        return (Tree)this.children.get(i);
    }

    public List<? extends Object> getChildren() {
        return this.children;
    }

    public Tree getFirstChildWithType(int type) {
        for (int i = 0; this.children != null && i < this.children.size(); ++i) {
            Tree t = (Tree)this.children.get(i);
            if (t.getType() != type) continue;
            return t;
        }
        return null;
    }

    @Override
    public int getChildCount() {
        if (this.children == null) {
            return 0;
        }
        return this.children.size();
    }

    @Override
    public void addChild(Tree t) {
        if (t == null) {
            return;
        }
        BaseTree childTree = (BaseTree)t;
        if (childTree.isNil()) {
            if (this.children != null && this.children == childTree.children) {
                throw new RuntimeException("attempt to add child list to itself");
            }
            if (childTree.children != null) {
                if (this.children != null) {
                    int n = childTree.children.size();
                    for (int i = 0; i < n; ++i) {
                        Tree c = (Tree)childTree.children.get(i);
                        this.children.add(c);
                        c.setParent(this);
                        c.setChildIndex(this.children.size() - 1);
                    }
                } else {
                    this.children = childTree.children;
                    this.freshenParentAndChildIndexes();
                }
            }
        } else {
            if (this.children == null) {
                this.children = this.createChildrenList();
            }
            this.children.add(t);
            childTree.setParent(this);
            childTree.setChildIndex(this.children.size() - 1);
        }
    }

    public void addChildren(List<? extends Tree> kids) {
        for (int i = 0; i < kids.size(); ++i) {
            Tree t = kids.get(i);
            this.addChild(t);
        }
    }

    @Override
    public void setChild(int i, Tree t) {
        if (t == null) {
            return;
        }
        if (t.isNil()) {
            throw new IllegalArgumentException("Can't set single child to a list");
        }
        if (this.children == null) {
            this.children = this.createChildrenList();
        }
        this.children.set(i, t);
        t.setParent(this);
        t.setChildIndex(i);
    }

    public void insertChild(int i, Object t) {
        if (i < 0 || i > this.getChildCount()) {
            throw new IndexOutOfBoundsException(i + " out or range");
        }
        if (this.children == null) {
            this.children = this.createChildrenList();
        }
        this.children.add(i, t);
        this.freshenParentAndChildIndexes(i);
    }

    @Override
    public Object deleteChild(int i) {
        if (this.children == null) {
            return null;
        }
        Tree killed = (Tree)this.children.remove(i);
        this.freshenParentAndChildIndexes(i);
        return killed;
    }

    @Override
    public void replaceChildren(int startChildIndex, int stopChildIndex, Object t) {
        List<Object> newChildren;
        if (this.children == null) {
            throw new IllegalArgumentException("indexes invalid; no children in list");
        }
        int replacingHowMany = stopChildIndex - startChildIndex + 1;
        BaseTree newTree = (BaseTree)t;
        if (newTree.isNil()) {
            newChildren = newTree.children;
        } else {
            newChildren = new ArrayList<Object>(1);
            newChildren.add(newTree);
        }
        int replacingWithHowMany = newChildren.size();
        int numNewChildren = newChildren.size();
        int delta = replacingHowMany - replacingWithHowMany;
        if (delta == 0) {
            int j = 0;
            for (int i = startChildIndex; i <= stopChildIndex; ++i) {
                BaseTree child = (BaseTree)newChildren.get(j);
                this.children.set(i, child);
                child.setParent(this);
                child.setChildIndex(i);
                ++j;
            }
        } else if (delta > 0) {
            int indexToDelete;
            for (int j = 0; j < numNewChildren; ++j) {
                this.children.set(startChildIndex + j, newChildren.get(j));
            }
            for (int c = indexToDelete = startChildIndex + numNewChildren; c <= stopChildIndex; ++c) {
                this.children.remove(indexToDelete);
            }
            this.freshenParentAndChildIndexes(startChildIndex);
        } else {
            for (int j = 0; j < replacingHowMany; ++j) {
                this.children.set(startChildIndex + j, newChildren.get(j));
            }
            int numToInsert = replacingWithHowMany - replacingHowMany;
            for (int j = replacingHowMany; j < replacingWithHowMany; ++j) {
                this.children.add(startChildIndex + j, newChildren.get(j));
            }
            this.freshenParentAndChildIndexes(startChildIndex);
        }
    }

    protected List<Object> createChildrenList() {
        return new ArrayList<Object>();
    }

    @Override
    public boolean isNil() {
        return false;
    }

    @Override
    public void freshenParentAndChildIndexes() {
        this.freshenParentAndChildIndexes(0);
    }

    public void freshenParentAndChildIndexes(int offset) {
        int n = this.getChildCount();
        for (int c = offset; c < n; ++c) {
            Tree child = this.getChild(c);
            child.setChildIndex(c);
            child.setParent(this);
        }
    }

    public void freshenParentAndChildIndexesDeeply() {
        this.freshenParentAndChildIndexesDeeply(0);
    }

    public void freshenParentAndChildIndexesDeeply(int offset) {
        int n = this.getChildCount();
        for (int c = offset; c < n; ++c) {
            BaseTree child = (BaseTree)this.getChild(c);
            child.setChildIndex(c);
            child.setParent(this);
            child.freshenParentAndChildIndexesDeeply();
        }
    }

    public void sanityCheckParentAndChildIndexes() {
        this.sanityCheckParentAndChildIndexes(null, -1);
    }

    public void sanityCheckParentAndChildIndexes(Tree parent, int i) {
        if (parent != this.getParent()) {
            throw new IllegalStateException("parents don't match; expected " + parent + " found " + this.getParent());
        }
        if (i != this.getChildIndex()) {
            throw new IllegalStateException("child indexes don't match; expected " + i + " found " + this.getChildIndex());
        }
        int n = this.getChildCount();
        for (int c = 0; c < n; ++c) {
            CommonTree child = (CommonTree)this.getChild(c);
            child.sanityCheckParentAndChildIndexes(this, c);
        }
    }

    @Override
    public int getChildIndex() {
        return 0;
    }

    @Override
    public void setChildIndex(int index) {
    }

    @Override
    public Tree getParent() {
        return null;
    }

    @Override
    public void setParent(Tree t) {
    }

    @Override
    public boolean hasAncestor(int ttype) {
        return this.getAncestor(ttype) != null;
    }

    @Override
    public Tree getAncestor(int ttype) {
        Tree t = this;
        for (t = t.getParent(); t != null; t = t.getParent()) {
            if (t.getType() != ttype) continue;
            return t;
        }
        return null;
    }

    public List<? extends Tree> getAncestors() {
        if (this.getParent() == null) {
            return null;
        }
        ArrayList<Tree> ancestors = new ArrayList<Tree>();
        Tree t = this;
        for (t = t.getParent(); t != null; t = t.getParent()) {
            ancestors.add(0, t);
        }
        return ancestors;
    }

    @Override
    public String toStringTree() {
        if (this.children == null || this.children.isEmpty()) {
            return this.toString();
        }
        StringBuilder buf = new StringBuilder();
        if (!this.isNil()) {
            buf.append("(");
            buf.append(this.toString());
            buf.append(' ');
        }
        for (int i = 0; this.children != null && i < this.children.size(); ++i) {
            Tree t = (Tree)this.children.get(i);
            if (i > 0) {
                buf.append(' ');
            }
            buf.append(t.toStringTree());
        }
        if (!this.isNil()) {
            buf.append(")");
        }
        return buf.toString();
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public int getCharPositionInLine() {
        return 0;
    }

    @Override
    public abstract String toString();
}

