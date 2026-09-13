/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.abego.treelayout.TreeForTreeLayout
 */
package groovyjarjarantlr4.v4.gui;

import groovyjarjarantlr4.v4.runtime.tree.Tree;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.abego.treelayout.TreeForTreeLayout;

public class TreeLayoutAdaptor
implements TreeForTreeLayout<Tree> {
    private Tree root;

    public TreeLayoutAdaptor(Tree root) {
        this.root = root;
    }

    public boolean isLeaf(Tree node) {
        return node.getChildCount() == 0;
    }

    public boolean isChildOfParent(Tree node, Tree parentNode) {
        return node.getParent() == parentNode;
    }

    public Tree getRoot() {
        return this.root;
    }

    public Tree getLastChild(Tree parentNode) {
        return parentNode.getChild(parentNode.getChildCount() - 1);
    }

    public Tree getFirstChild(Tree parentNode) {
        return parentNode.getChild(0);
    }

    public Iterable<Tree> getChildrenReverse(Tree node) {
        return new AntlrTreeChildrenReverseIterable(node);
    }

    public Iterable<Tree> getChildren(Tree node) {
        return new AntlrTreeChildrenIterable(node);
    }

    private static class AntlrTreeChildrenReverseIterable
    implements Iterable<Tree> {
        private final Tree tree;

        public AntlrTreeChildrenReverseIterable(Tree tree) {
            this.tree = tree;
        }

        @Override
        public Iterator<Tree> iterator() {
            return new Iterator<Tree>(){
                private int i;
                {
                    this.i = AntlrTreeChildrenReverseIterable.this.tree.getChildCount();
                }

                @Override
                public boolean hasNext() {
                    return this.i > 0;
                }

                @Override
                public Tree next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return AntlrTreeChildrenReverseIterable.this.tree.getChild(--this.i);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    private static class AntlrTreeChildrenIterable
    implements Iterable<Tree> {
        private final Tree tree;

        public AntlrTreeChildrenIterable(Tree tree) {
            this.tree = tree;
        }

        @Override
        public Iterator<Tree> iterator() {
            return new Iterator<Tree>(){
                private int i = 0;

                @Override
                public boolean hasNext() {
                    return AntlrTreeChildrenIterable.this.tree.getChildCount() > this.i;
                }

                @Override
                public Tree next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return AntlrTreeChildrenIterable.this.tree.getChild(this.i++);
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }
}

