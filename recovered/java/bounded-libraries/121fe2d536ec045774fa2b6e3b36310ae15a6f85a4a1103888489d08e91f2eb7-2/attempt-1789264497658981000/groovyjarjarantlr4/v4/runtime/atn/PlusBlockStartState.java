/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;

public final class PlusBlockStartState
extends BlockStartState {
    public PlusLoopbackState loopBackState;

    @Override
    public int getStateType() {
        return 4;
    }
}

