/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.RangeTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;

public abstract class CodePointTransitions {
    public static Transition createWithCodePoint(ATNState target, int codePoint) {
        if (Character.isSupplementaryCodePoint(codePoint)) {
            return new SetTransition(target, IntervalSet.of(codePoint));
        }
        return new AtomTransition(target, codePoint);
    }

    public static Transition createWithCodePointRange(ATNState target, int codePointFrom, int codePointTo) {
        if (Character.isSupplementaryCodePoint(codePointFrom) || Character.isSupplementaryCodePoint(codePointTo)) {
            return new SetTransition(target, IntervalSet.of(codePointFrom, codePointTo));
        }
        return new RangeTransition(target, codePointFrom, codePointTo);
    }
}

