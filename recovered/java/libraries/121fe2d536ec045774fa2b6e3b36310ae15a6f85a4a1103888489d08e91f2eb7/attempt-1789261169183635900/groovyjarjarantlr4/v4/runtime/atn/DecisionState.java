/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;

public abstract class DecisionState
extends ATNState {
    public int decision = -1;
    public boolean nonGreedy;
    public boolean sll;
}

