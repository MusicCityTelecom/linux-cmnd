/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.debug.DebugEventListener;
import groovyjarjarantlr4.runtime.debug.DebugTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeParser;
import java.io.IOException;

public class DebugTreeParser
extends TreeParser {
    protected DebugEventListener dbg = null;
    public boolean isCyclicDecision = false;

    public DebugTreeParser(TreeNodeStream input, DebugEventListener dbg2, RecognizerSharedState state) {
        super(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, dbg2), state);
        this.setDebugListener(dbg2);
    }

    public DebugTreeParser(TreeNodeStream input, RecognizerSharedState state) {
        super(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, null), state);
    }

    public DebugTreeParser(TreeNodeStream input, DebugEventListener dbg2) {
        this(input instanceof DebugTreeNodeStream ? input : new DebugTreeNodeStream(input, dbg2), dbg2, null);
    }

    public void setDebugListener(DebugEventListener dbg2) {
        if (this.input instanceof DebugTreeNodeStream) {
            ((DebugTreeNodeStream)this.input).setDebugListener(dbg2);
        }
        this.dbg = dbg2;
    }

    public DebugEventListener getDebugListener() {
        return this.dbg;
    }

    public void reportError(IOException e) {
        System.err.println(e);
        e.printStackTrace(System.err);
    }

    public void reportError(RecognitionException e) {
        this.dbg.recognitionException(e);
    }

    protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
        Object o = super.getMissingSymbol(input, e, expectedTokenType, follow);
        this.dbg.consumeNode(o);
        return o;
    }

    public void beginResync() {
        this.dbg.beginResync();
    }

    public void endResync() {
        this.dbg.endResync();
    }

    public void beginBacktrack(int level) {
        this.dbg.beginBacktrack(level);
    }

    public void endBacktrack(int level, boolean successful) {
        this.dbg.endBacktrack(level, successful);
    }
}

