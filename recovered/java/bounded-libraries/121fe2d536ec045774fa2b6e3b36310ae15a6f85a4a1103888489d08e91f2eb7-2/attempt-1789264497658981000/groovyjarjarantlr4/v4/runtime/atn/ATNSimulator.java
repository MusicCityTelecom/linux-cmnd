/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNConfigSet;
import groovyjarjarantlr4.v4.runtime.atn.ATNDeserializer;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.dfa.DFAState;
import groovyjarjarantlr4.v4.runtime.dfa.EmptyEdgeMap;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.List;
import java.util.UUID;

public abstract class ATNSimulator {
    @Deprecated
    public static final int SERIALIZED_VERSION = ATNDeserializer.SERIALIZED_VERSION;
    @Deprecated
    public static final UUID SERIALIZED_UUID = ATNDeserializer.SERIALIZED_UUID;
    public static final char RULE_VARIANT_DELIMITER = '$';
    public static final String RULE_LF_VARIANT_MARKER = "$lf$";
    public static final String RULE_NOLF_VARIANT_MARKER = "$nolf$";
    @NotNull
    public static final DFAState ERROR = new DFAState(new EmptyEdgeMap<DFAState>(0, -1), new EmptyEdgeMap<DFAState>(0, -1), new ATNConfigSet());
    @NotNull
    public final ATN atn;

    public ATNSimulator(@NotNull ATN atn) {
        this.atn = atn;
    }

    public abstract void reset();

    public void clearDFA() {
        this.atn.clearDFA();
    }

    @Deprecated
    public static ATN deserialize(@NotNull char[] data) {
        return new ATNDeserializer().deserialize(data);
    }

    @Deprecated
    public static void checkCondition(boolean condition) {
        new ATNDeserializer().checkCondition(condition);
    }

    @Deprecated
    public static void checkCondition(boolean condition, String message) {
        new ATNDeserializer().checkCondition(condition, message);
    }

    @Deprecated
    public static int toInt(char c) {
        return ATNDeserializer.toInt(c);
    }

    @Deprecated
    public static int toInt32(char[] data, int offset) {
        return ATNDeserializer.toInt32(data, offset);
    }

    @Deprecated
    public static long toLong(char[] data, int offset) {
        return ATNDeserializer.toLong(data, offset);
    }

    @Deprecated
    public static UUID toUUID(char[] data, int offset) {
        return ATNDeserializer.toUUID(data, offset);
    }

    @Deprecated
    @NotNull
    public static Transition edgeFactory(@NotNull ATN atn, int type, int src, int trg, int arg1, int arg2, int arg3, List<IntervalSet> sets) {
        return new ATNDeserializer().edgeFactory(atn, type, src, trg, arg1, arg2, arg3, sets);
    }

    @Deprecated
    public static ATNState stateFactory(int type, int ruleIndex) {
        return new ATNDeserializer().stateFactory(type, ruleIndex);
    }

    static {
        ATNSimulator.ERROR.stateNumber = Integer.MAX_VALUE;
    }
}

