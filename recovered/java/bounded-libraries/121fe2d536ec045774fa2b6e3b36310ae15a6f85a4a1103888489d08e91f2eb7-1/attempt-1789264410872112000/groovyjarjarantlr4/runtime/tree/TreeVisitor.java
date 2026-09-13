/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeVisitorAction;

public class TreeVisitor {
    protected TreeAdaptor adaptor;

    public TreeVisitor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    public TreeVisitor() {
        this(new CommonTreeAdaptor());
    }

    public Object visit(Object t, TreeVisitorAction action) {
        boolean isNil = this.adaptor.isNil(t);
        if (action != null && !isNil) {
            t = action.pre(t);
        }
        for (int i = 0; i < this.adaptor.getChildCount(t); ++i) {
            Object childAfterVisit;
            Object child = this.adaptor.getChild(t, i);
            Object visitResult = this.visit(child, action);
            if (visitResult == (childAfterVisit = this.adaptor.getChild(t, i))) continue;
            this.adaptor.setChild(t, i, visitResult);
        }
        if (action != null && !isNil) {
            t = action.post(t);
        }
        return t;
    }
}

