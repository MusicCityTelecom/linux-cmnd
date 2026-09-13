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
public class RewriteRuleSubtreeStream
extends RewriteRuleElementStream {
    public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription) {
        super(adaptor, elementDescription);
    }

    public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
        super(adaptor, elementDescription, oneElement);
    }

    public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, List<Object> elements) {
        super(adaptor, elementDescription, elements);
    }

    public Object nextNode() {
        int n = this.size();
        if (this.dirty || this.cursor >= n && n == 1) {
            Object el = this._next();
            return this.adaptor.dupNode(el);
        }
        Object tree = this._next();
        while (this.adaptor.isNil(tree) && this.adaptor.getChildCount(tree) == 1) {
            tree = this.adaptor.getChild(tree, 0);
        }
        Object el = this.adaptor.dupNode(tree);
        return el;
    }

    @Override
    protected Object dup(Object el) {
        return this.adaptor.dupTree(el);
    }
}

