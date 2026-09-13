/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import groovyjarjarantlr4.runtime.tree.TreeVisitor;
import groovyjarjarantlr4.runtime.tree.TreeVisitorAction;

public class TreeFilter
extends TreeParser {
    protected TokenStream originalTokenStream;
    protected TreeAdaptor originalAdaptor;
    fptr topdown_fptr = new fptr(){

        public void rule() throws RecognitionException {
            TreeFilter.this.topdown();
        }
    };
    fptr bottomup_fptr = new fptr(){

        public void rule() throws RecognitionException {
            TreeFilter.this.bottomup();
        }
    };

    public TreeFilter(TreeNodeStream input) {
        this(input, new RecognizerSharedState());
    }

    public TreeFilter(TreeNodeStream input, RecognizerSharedState state) {
        super(input, state);
        this.originalAdaptor = input.getTreeAdaptor();
        this.originalTokenStream = input.getTokenStream();
    }

    public void applyOnce(Object t, fptr whichRule) {
        if (t == null) {
            return;
        }
        try {
            this.state = new RecognizerSharedState();
            this.input = new CommonTreeNodeStream(this.originalAdaptor, t);
            ((CommonTreeNodeStream)this.input).setTokenStream(this.originalTokenStream);
            this.setBacktrackingLevel(1);
            whichRule.rule();
            this.setBacktrackingLevel(0);
        }
        catch (RecognitionException recognitionException) {
            // empty catch block
        }
    }

    public void downup(Object t) {
        TreeVisitor v = new TreeVisitor(new CommonTreeAdaptor());
        TreeVisitorAction actions = new TreeVisitorAction(){

            public Object pre(Object t) {
                TreeFilter.this.applyOnce(t, TreeFilter.this.topdown_fptr);
                return t;
            }

            public Object post(Object t) {
                TreeFilter.this.applyOnce(t, TreeFilter.this.bottomup_fptr);
                return t;
            }
        };
        v.visit(t, actions);
    }

    public void topdown() throws RecognitionException {
    }

    public void bottomup() throws RecognitionException {
    }

    public static interface fptr {
        public void rule() throws RecognitionException;
    }
}

