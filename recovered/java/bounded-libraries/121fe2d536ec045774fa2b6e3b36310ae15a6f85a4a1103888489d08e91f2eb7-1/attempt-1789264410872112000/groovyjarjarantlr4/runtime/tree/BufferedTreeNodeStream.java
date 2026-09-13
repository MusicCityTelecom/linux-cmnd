/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.misc.IntArray;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BufferedTreeNodeStream
implements TreeNodeStream {
    public static final int DEFAULT_INITIAL_BUFFER_SIZE = 100;
    public static final int INITIAL_CALL_STACK_SIZE = 10;
    protected Object down;
    protected Object up;
    protected Object eof;
    protected List<Object> nodes;
    protected Object root;
    protected TokenStream tokens;
    TreeAdaptor adaptor;
    protected boolean uniqueNavigationNodes = false;
    protected int p = -1;
    protected int lastMarker;
    protected IntArray calls;

    public BufferedTreeNodeStream(Object tree) {
        this(new CommonTreeAdaptor(), tree);
    }

    public BufferedTreeNodeStream(TreeAdaptor adaptor, Object tree) {
        this(adaptor, tree, 100);
    }

    public BufferedTreeNodeStream(TreeAdaptor adaptor, Object tree, int initialBufferSize) {
        this.root = tree;
        this.adaptor = adaptor;
        this.nodes = new ArrayList<Object>(initialBufferSize);
        this.down = adaptor.create(2, "DOWN");
        this.up = adaptor.create(3, "UP");
        this.eof = adaptor.create(-1, "EOF");
    }

    protected void fillBuffer() {
        this.fillBuffer(this.root);
        this.p = 0;
    }

    public void fillBuffer(Object t) {
        boolean nil = this.adaptor.isNil(t);
        if (!nil) {
            this.nodes.add(t);
        }
        int n = this.adaptor.getChildCount(t);
        if (!nil && n > 0) {
            this.addNavigationNode(2);
        }
        for (int c = 0; c < n; ++c) {
            Object child = this.adaptor.getChild(t, c);
            this.fillBuffer(child);
        }
        if (!nil && n > 0) {
            this.addNavigationNode(3);
        }
    }

    protected int getNodeIndex(Object node) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        for (int i = 0; i < this.nodes.size(); ++i) {
            Object t = this.nodes.get(i);
            if (t != node) continue;
            return i;
        }
        return -1;
    }

    protected void addNavigationNode(int ttype) {
        Object navNode = ttype == 2 ? (this.hasUniqueNavigationNodes() ? this.adaptor.create(2, "DOWN") : this.down) : (this.hasUniqueNavigationNodes() ? this.adaptor.create(3, "UP") : this.up);
        this.nodes.add(navNode);
    }

    @Override
    public Object get(int i) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        return this.nodes.get(i);
    }

    @Override
    public Object LT(int k) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return this.LB(-k);
        }
        if (this.p + k - 1 >= this.nodes.size()) {
            return this.eof;
        }
        return this.nodes.get(this.p + k - 1);
    }

    public Object getCurrentSymbol() {
        return this.LT(1);
    }

    protected Object LB(int k) {
        if (k == 0) {
            return null;
        }
        if (this.p - k < 0) {
            return null;
        }
        return this.nodes.get(this.p - k);
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

    public boolean hasUniqueNavigationNodes() {
        return this.uniqueNavigationNodes;
    }

    @Override
    public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
        this.uniqueNavigationNodes = uniqueNavigationNodes;
    }

    @Override
    public void consume() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        ++this.p;
    }

    @Override
    public int LA(int i) {
        return this.adaptor.getType(this.LT(i));
    }

    @Override
    public int mark() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        this.lastMarker = this.index();
        return this.lastMarker;
    }

    @Override
    public void release(int marker) {
    }

    @Override
    public int index() {
        return this.p;
    }

    @Override
    public void rewind(int marker) {
        this.seek(marker);
    }

    @Override
    public void rewind() {
        this.seek(this.lastMarker);
    }

    @Override
    public void seek(int index) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        this.p = index;
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
    public void reset() {
        this.p = 0;
        this.lastMarker = 0;
        if (this.calls != null) {
            this.calls.clear();
        }
    }

    @Override
    public int size() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        return this.nodes.size();
    }

    public Iterator<Object> iterator() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        return new StreamIterator();
    }

    @Override
    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        if (parent != null) {
            this.adaptor.replaceChildren(parent, startChildIndex, stopChildIndex, t);
        }
    }

    public String toTokenTypeString() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < this.nodes.size(); ++i) {
            Object t = this.nodes.get(i);
            buf.append(" ");
            buf.append(this.adaptor.getType(t));
        }
        return buf.toString();
    }

    public String toTokenString(int start, int stop) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        StringBuilder buf = new StringBuilder();
        for (int i = start; i < this.nodes.size() && i <= stop; ++i) {
            Object t = this.nodes.get(i);
            buf.append(" ");
            buf.append(this.adaptor.getToken(t));
        }
        return buf.toString();
    }

    @Override
    public String toString(Object start, Object stop) {
        String text;
        Object t;
        int i;
        System.out.println("toString");
        if (start == null || stop == null) {
            return null;
        }
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (start instanceof CommonTree) {
            System.out.print("toString: " + ((CommonTree)start).getToken() + ", ");
        } else {
            System.out.println(start);
        }
        if (stop instanceof CommonTree) {
            System.out.println(((CommonTree)stop).getToken());
        } else {
            System.out.println(stop);
        }
        if (this.tokens != null) {
            int beginTokenIndex = this.adaptor.getTokenStartIndex(start);
            int endTokenIndex = this.adaptor.getTokenStopIndex(stop);
            if (this.adaptor.getType(stop) == 3) {
                endTokenIndex = this.adaptor.getTokenStopIndex(start);
            } else if (this.adaptor.getType(stop) == -1) {
                endTokenIndex = this.size() - 2;
            }
            return this.tokens.toString(beginTokenIndex, endTokenIndex);
        }
        for (i = 0; i < this.nodes.size() && (t = this.nodes.get(i)) != start; ++i) {
        }
        StringBuilder buf = new StringBuilder();
        t = this.nodes.get(i);
        while (t != stop) {
            text = this.adaptor.getText(t);
            if (text == null) {
                text = " " + String.valueOf(this.adaptor.getType(t));
            }
            buf.append(text);
            t = this.nodes.get(++i);
        }
        text = this.adaptor.getText(stop);
        if (text == null) {
            text = " " + String.valueOf(this.adaptor.getType(stop));
        }
        buf.append(text);
        return buf.toString();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    protected class StreamIterator
    implements Iterator<Object> {
        int i = 0;

        protected StreamIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.i < BufferedTreeNodeStream.this.nodes.size();
        }

        @Override
        public Object next() {
            int current;
            if ((current = this.i++) < BufferedTreeNodeStream.this.nodes.size()) {
                return BufferedTreeNodeStream.this.nodes.get(current);
            }
            return BufferedTreeNodeStream.this.eof;
        }

        @Override
        public void remove() {
            throw new RuntimeException("cannot remove nodes from stream");
        }
    }
}

