/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeRuleReturnScope;
import groovyjarjarantlr4.runtime.tree.TreeVisitor;
import groovyjarjarantlr4.runtime.tree.TreeVisitorAction;

public class TreeRewriter
extends TreeParser {
    protected boolean showTransformations = false;
    protected TokenStream originalTokenStream;
    protected TreeAdaptor originalAdaptor;
    fptr topdown_fptr = new fptr(){

        public Object rule() throws RecognitionException {
            return TreeRewriter.this.topdown();
        }
    };
    fptr bottomup_ftpr = new fptr(){

        public Object rule() throws RecognitionException {
            return TreeRewriter.this.bottomup();
        }
    };

    public TreeRewriter(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public TreeRewriter(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
        this.originalAdaptor = input.getTreeAdaptor();
        this.originalTokenStream = input.getTokenStream();
    }

    public Object applyOnce(Object t, fptr whichRule) {
        if (t == null) {
            return null;
        }
        try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
            ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
            this.setBacktrackingLevel(1);
            TreeRuleReturnScope r = (TreeRuleReturnScope)whichRule.rule();
            this.setBacktrackingLevel(0);
            if (this.failed()) {
                return t;
            }
            if (this.showTransformations && r != null && !t.equals(r.getTree()) && r.getTree() != null) {
                this.reportTransformation(t, r.getTree());
            }
            if (r != null && r.getTree() != null) {
                return r.getTree();
            }
            return t;
        }
        catch (RecognitionException recognitionException) {
            return t;
        }
    }

    public Object applyRepeatedly(Object t, fptr whichRule) {
        boolean treeChanged = true;
        while (treeChanged) {
            Object u = this.applyOnce(t, whichRule);
            treeChanged = !t.equals(u);
            t = u;
        }
        return t;
    }

    public Object downup(Object t) {
        return this.downup(t, false);
    }

    public Object downup(Object t, boolean showTransformations) {
        this.showTransformations = showTransformations;
        TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
        TreeVisitorAction actions = new TreeVisitorAction(){

            public Object pre(Object t) {
                return TreeRewriter.this.applyOnce(t, TreeRewriter.this.topdown_fptr);
            }

            public Object post(Object t) {
                return TreeRewriter.this.applyRepeatedly(t, TreeRewriter.this.bottomup_ftpr);
            }
        };
        t = v.visit(t, actions);
        return t;
    }

    public void reportTransformation(Object oldTree, Object newTree) {
        System.out.println(((Tree)oldTree).toStringTree() + " -> " + ((Tree)newTree).toStringTree());
    }

    public Object topdown() throws RecognitionException {
        return null;
    }

    public Object bottomup() throws RecognitionException {
        return null;
    }

    public static interface fptr {
        public Object rule() throws RecognitionException;
    }
}

