/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.tree.RewriteCardinalityException;
import groovyjarjarantlr4.runtime.tree.RewriteEmptyStreamException;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class RewriteRuleElementStream {
    protected int cursor = 0;
    protected Object singleElement;
    protected List<Object> elements;
    protected boolean dirty = false;
    protected String elementDescription;
    protected TreeAdaptor adaptor;

    public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription) {
        this.elementDescription = elementDescription;
        this.adaptor = adaptor;
    }

    public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
        this(adaptor, elementDescription);
        this.add(oneElement);
    }

    public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, List<Object> elements) {
        this(adaptor, elementDescription);
        this.singleElement = null;
        this.elements = elements;
    }

    public void reset() {
        this.cursor = 0;
        this.dirty = true;
    }

    public void add(Object el) {
        if (el == null) {
            return;
        }
        if (this.elements != null) {
            this.elements.add(el);
            return;
        }
        if (this.singleElement == null) {
            this.singleElement = el;
            return;
        }
        this.elements = new ArrayList<Object>(5);
        this.elements.add(this.singleElement);
        this.singleElement = null;
        this.elements.add(el);
    }

    public Object nextTree() {
        int n = this.size();
        if (this.dirty || this.cursor >= n && n == 1) {
            Object el = this._next();
            return this.dup(el);
        }
        Object el = this._next();
        return el;
    }

    protected Object _next() {
        int n = this.size();
        if (n == 0) {
            throw new RewriteEmptyStreamException(this.elementDescription);
        }
        if (this.cursor >= n) {
            if (n == 1) {
                return this.toTree(this.singleElement);
            }
            throw new RewriteCardinalityException(this.elementDescription);
        }
        if (this.singleElement != null) {
            ++this.cursor;
            return this.toTree(this.singleElement);
        }
        Object o = this.toTree(this.elements.get(this.cursor));
        ++this.cursor;
        return o;
    }

    protected abstract Object dup(Object var1);

    protected Object toTree(Object el) {
        return el;
    }

    public boolean hasNext() {
        return this.singleElement != null && this.cursor < 1 || this.elements != null && this.cursor < this.elements.size();
    }

    public int size() {
        int n = 0;
        if (this.singleElement != null) {
            n = 1;
        }
        if (this.elements != null) {
            return this.elements.size();
        }
        return n;
    }

    public String getDescription() {
        return this.elementDescription;
    }
}

