/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.debug.DebugEventListener;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;

public class DebugTreeNodeStream
implements TreeNodeStream {
    protected DebugEventListener dbg;
    protected TreeAdaptor adaptor;
    protected TreeNodeStream input;
    protected boolean initialStreamState = true;
    protected int lastMarker;

    public DebugTreeNodeStream(TreeNodeStream input, DebugEventListener dbg2) {
        this.input = input;
        this.adaptor = input.getTreeAdaptor();
        this.input.setUniqueNavigationNodes(true);
        this.setDebugListener(dbg2);
    }

    public void setDebugListener(DebugEventListener dbg2) {
        this.dbg = dbg2;
    }

    public TreeAdaptor getTreeAdaptor() {
        return this.adaptor;
    }

    public void consume() {
        Object node = this.input.LT(1);
        this.input.consume();
        this.dbg.consumeNode(node);
    }

    public Object get(int i) {
        return this.input.get(i);
    }

    public Object LT(int i) {
        Object node = this.input.LT(i);
        int ID = this.adaptor.getUniqueID(node);
        String text = this.adaptor.getText(node);
        int type = this.adaptor.getType(node);
        this.dbg.LT(i, node);
        return node;
    }

    public int LA(int i) {
        Object node = this.input.LT(i);
        int ID = this.adaptor.getUniqueID(node);
        String text = this.adaptor.getText(node);
        int type = this.adaptor.getType(node);
        this.dbg.LT(i, node);
        return type;
    }

    public int mark() {
        this.lastMarker = this.input.mark();
        this.dbg.mark(this.lastMarker);
        return this.lastMarker;
    }

    public int index() {
        return this.input.index();
    }

    public void rewind(int marker) {
        this.dbg.rewind(marker);
        this.input.rewind(marker);
    }

    public void rewind() {
        this.dbg.rewind();
        this.input.rewind(this.lastMarker);
    }

    public void release(int marker) {
    }

    public void seek(int index) {
        this.input.seek(index);
    }

    public int size() {
        return this.input.size();
    }

    public void reset() {
    }

    public Object getTreeSource() {
        return this.input;
    }

    public String getSourceName() {
        return this.getTokenStream().getSourceName();
    }

    public TokenStream getTokenStream() {
        return this.input.getTokenStream();
    }

    public void setUniqueNavigationNodes(boolean uniqueNavigationNodes) {
        this.input.setUniqueNavigationNodes(uniqueNavigationNodes);
    }

    public void replaceChildren(Object parent, int startChildIndex, int stopChildIndex, Object t) {
        this.input.replaceChildren(parent, startChildIndex, stopChildIndex, t);
    }

    public String toString(Object start, Object stop) {
        return this.input.toString(start, stop);
    }
}

