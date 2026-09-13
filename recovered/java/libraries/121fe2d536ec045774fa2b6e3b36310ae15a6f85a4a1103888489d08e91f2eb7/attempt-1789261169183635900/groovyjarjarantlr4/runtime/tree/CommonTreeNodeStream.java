/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.misc.IntArray;
import groovyjarjarantlr4.runtime.misc.LookaheadStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.PositionTrackingStream;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeIterator;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CommonTreeNodeStream
extends LookaheadStream<Object>
implements TreeNodeStream,
PositionTrackingStream<Object> {
    public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
    public static final int INITIAL_CALL_STACK_SIZE = 10;
    protected Object root;
    protected TokenStream tokens;
    TreeAdaptor adaptor;
    protected TreeIterator it;
    protected IntArray calls;
    protected boolean hasNilRoot = false;
    protected int level = 0;
    protected Object previousLocationElement;

    public CommonTreeNodeStream(Object tree) {
        this(new CommonTreeAdaptor(), tree);
    }

    public CommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
        this.root = tree;
        this.adaptor = adaptor;
        this.it = new TreeIterator(adaptor, this.root);
    }

    @Override
    public void reset() {
        super.reset();
        this.it.reset();
        this.hasNilRoot = false;
        this.level = 0;
        this.previousLocationElement = null;
        if (this.calls != null) {
            this.calls.clear();
        }
    }

    @Override
    public Object nextElement() {
        Object t = this.it.next();
        if (t == this.it.up) {
            --this.level;
            if (this.level == 0 && this.hasNilRoot) {
                return this.it.next();
            }
        } else if (t == this.it.down) {
            ++this.level;
        }
        if (this.level == 0 && this.adaptor.isNil(t)) {
            this.hasNilRoot = true;
            t = this.it.next();
            ++this.level;
            t = this.it.next();
        }
        return t;
    }

    @Override
    public Object remove() {
        Object result = super.remove();
        if (this.p == 0 && this.hasPositionInformation(this.prevElement)) {
            this.previousLocationElement = this.prevElement;
        }
        return result;
    }

    @Override
    public boolean isEOF(Object o) {
        return this.adaptor.getType(o) == -1;
    }

    @Override
    public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
    }

    @Override
    public Object getTreeSource() {
        return this.root;
    }

    @Override
    public String getSourceName() {
        return this.getTokenStream().getSourceName();
    }

    @Override
    public TokenStream getTokenStream() {
        return this.tokens;
    }

    public void setTokenStream(TokenStream tokens) {
        this.tokens = tokens;
    }

    @Override
    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException("Absolute node indexes are meaningless in an unbuffered stream");
    }

    @Override
    public int LA(int i) {
        return this.adaptor.getType(this.LT(i));
    }

    public void push(int index) {
        if (this.calls == null) {
            this.calls = new IntArray();
        }
        this.calls.push(this.p);
        this.seek(index);
    }

    public int pop() {
        int ret = this.calls.pop();
        this.seek(ret);
        return ret;
    }

    @Override
    public Object getKnownPositionElement(boolean allowApproximateLocation) {
        Object node = this.data.get(this.p);
        if (this.hasPositionInformation(node)) {
            return node;
        }
        if (!allowApproximateLocation) {
            return null;
        }
        for (int index = this.p - 1; index >= 0; --index) {
            node = this.data.get(index);
            if (!this.hasPositionInformation(node)) continue;
            return node;
        }
        return this.previousLocationElement;
    }

    @Override
    public boolean hasPositionInformation(Object node) {
        Token token = this.adaptor.getToken(node);
        if (token == null) {
            return false;
        }
        return token.getLine() > 0;
    }

    @Override
    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        if (parent != null) {
            this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
        }
    }

    @Override
    public String toString(Object start, Object stop) {
        return "n/a";
    }

    public String toTokenTypeString() {
        this.reset();
        StringBuilder buf = new StringBuilder();
        Object o = this.LT(1);
        int type = this.adaptor.getType(o);
        while (type != -1) {
            buf.append(" ");
            buf.append(type);
            this.consume();
            o = this.LT(1);
            type = this.adaptor.getType(o);
        }
        return buf.toString();
    }
}

