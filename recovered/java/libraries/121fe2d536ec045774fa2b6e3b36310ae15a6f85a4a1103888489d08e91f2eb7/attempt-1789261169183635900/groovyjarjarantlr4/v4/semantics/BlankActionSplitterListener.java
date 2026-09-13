/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.parse.ActionSplitterListener;

public class BlankActionSplitterListener
implements ActionSplitterListener {
    @Override
    public void qualifiedAttr(String expr, Token x, Token y) {
    }

    @Override
    public void setAttr(String expr, Token x, Token rhs) {
    }

    @Override
    public void attr(String expr, Token x) {
    }

    public void templateInstance(String expr) {
    }

    @Override
    public void nonLocalAttr(String expr, Token x, Token y) {
    }

    @Override
    public void setNonLocalAttr(String expr, Token x, Token y, Token rhs) {
    }

    public void indirectTemplateInstance(String expr) {
    }

    public void setExprAttribute(String expr) {
    }

    public void setSTAttribute(String expr) {
    }

    public void templateExpr(String expr) {
    }

    @Override
    public void text(String text) {
    }
}

