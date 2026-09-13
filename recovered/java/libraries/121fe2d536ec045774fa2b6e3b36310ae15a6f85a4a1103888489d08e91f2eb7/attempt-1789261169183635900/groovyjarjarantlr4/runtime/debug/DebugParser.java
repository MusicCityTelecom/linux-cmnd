/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.debug;

import groovyjarjarantlr4.runtime.Parser;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.debug.DebugEventListener;
import groovyjarjarantlr4.runtime.debug.DebugTokenStream;
import java.io.IOException;

public class DebugParser
extends Parser {
    protected DebugEventListener dbg = null;
    public boolean isCyclicDecision = false;

    public DebugParser(TokenStream input, DebugEventListener dbg2, RecognizerSharedState state) {
        super(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, dbg2), state);
        this.setDebugListener(dbg2);
    }

    public DebugParser(TokenStream input, RecognizerSharedState state) {
        super(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, null), state);
    }

    public DebugParser(TokenStream input, DebugEventListener dbg2) {
        this(input instanceof DebugTokenStream ? input : new DebugTokenStream(input, dbg2), dbg2, null);
    }

    public void setDebugListener(DebugEventListener dbg2) {
        if (this.input instanceof DebugTokenStream) {
            ((DebugTokenStream)this.input).setDebugListener(dbg2);
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

    public void reportError(RecognitionException e) {
        super.reportError(e);
        this.dbg.recognitionException(e);
    }
}

