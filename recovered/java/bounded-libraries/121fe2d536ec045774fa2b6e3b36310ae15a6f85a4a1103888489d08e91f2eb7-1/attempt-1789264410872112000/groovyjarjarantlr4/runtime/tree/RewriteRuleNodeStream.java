/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.tree.RewriteRuleElementStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RewriteRuleNodeStream
extends RewriteRuleElementStream {
    public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription) {
        super(adaptor, elementDescription);
    }

    public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
        super(adaptor, elementDescription, oneElement);
    }

    public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription, List<Object> elements) {
        super(adaptor, elementDescription, elements);
    }

    public Object nextNode() {
        return this._next();
    }

    @Override
    protected Object toTree(Object el) {
        return this.adaptor.dupNode(el);
    }

    @Override
    protected Object dup(Object el) {
        throw new UnsupportedOperationException("dup can't be called for a node stream.");
    }
}

