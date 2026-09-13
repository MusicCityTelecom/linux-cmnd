/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.RewriteRuleElementStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RewriteRuleTokenStream
extends RewriteRuleElementStream {
    public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription) {
        super(adaptor, elementDescription);
    }

    public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
        super(adaptor, elementDescription, oneElement);
    }

    public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, List<Object> elements) {
        super(adaptor, elementDescription, elements);
    }

    public Object nextNode() {
        Token t = (Token)this._next();
        return this.adaptor.create(t);
    }

    public Token nextToken() {
        return (Token)this._next();
    }

    @Override
    protected Object toTree(Object el) {
        return el;
    }

    @Override
    protected Object dup(Object el) {
        throw new UnsupportedOperationException("dup can't be called for a token stream.");
    }
}

