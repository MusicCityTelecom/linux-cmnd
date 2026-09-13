/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;

public class MismatchedTreeNodeException
extends RecognitionException {
    public int expecting;

    public MismatchedTreeNodeException() {
    }

    public MismatchedTreeNodeException(int expecting, TreeNodeStream input) {
        super(input);
        this.expecting = expecting;
    }

    public String toString() {
        return "MismatchedTreeNodeException(" + this.getUnexpectedType() + "!=" + this.expecting + ")";
    }
}

