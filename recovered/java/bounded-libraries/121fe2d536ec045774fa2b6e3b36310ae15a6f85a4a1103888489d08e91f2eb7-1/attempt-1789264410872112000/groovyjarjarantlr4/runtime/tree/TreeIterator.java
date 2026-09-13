/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.misc.FastQueue;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import java.util.Iterator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class TreeIterator
implements Iterator<Object> {
    protected TreeAdaptor adaptor;
    protected Object root;
    protected Object tree;
    protected boolean firstTime = true;
    public Object up;
    public Object down;
    public Object eof;
    protected FastQueue<Object> nodes;

    public TreeIterator(Object tree) {
        this(new CommonTreeAdaptor(), tree);
    }

    public TreeIterator(TreeAdaptor adaptor, Object tree) {
        this.adaptor = adaptor;
        this.tree = tree;
        this.root = tree;
        this.nodes = new FastQueue();
        this.down = adaptor.create(2, "DOWN");
        this.up = adaptor.create(3, "UP");
        this.eof = adaptor.create(-1, "EOF");
    }

    public void reset() {
        this.firstTime = true;
        this.tree = this.root;
        this.nodes.clear();
    }

    @Override
    public boolean hasNext() {
        if (this.firstTime) {
            return this.root != null;
        }
        if (this.nodes != null && this.nodes.size() > 0) {
            return true;
        }
        if (this.tree == null) {
            return false;
        }
        if (this.adaptor.getChildCount(this.tree) > 0) {
            return true;
        }
        return this.adaptor.getParent(this.tree) != null;
    }

    @Override
    public Object next() {
        if (this.firstTime) {
            this.firstTime = false;
            if (this.adaptor.getChildCount(this.tree) == 0) {
                this.nodes.add(this.eof);
                return this.tree;
            }
            return this.tree;
        }
        if (this.nodes != null && this.nodes.size() > 0) {
            return this.nodes.remove();
        }
        if (this.tree == null) {
            return this.eof;
        }
        if (this.adaptor.getChildCount(this.tree) > 0) {
            this.tree = this.adaptor.getChild(this.tree, 0);
            this.nodes.add(this.tree);
            return this.down;
        }
        Object parent = this.adaptor.getParent(this.tree);
        while (parent != null && this.adaptor.getChildIndex(this.tree) + 1 >= this.adaptor.getChildCount(parent)) {
            this.nodes.add(this.up);
            this.tree = parent;
            parent = this.adaptor.getParent(this.tree);
        }
        if (parent == null) {
            this.tree = null;
            this.nodes.add(this.eof);
            return this.nodes.remove();
        }
        int nextSiblingIndex = this.adaptor.getChildIndex(this.tree) + 1;
        this.tree = this.adaptor.getChild(parent, nextSiblingIndex);
        this.nodes.add(this.tree);
        return this.nodes.remove();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

