/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class InterpreterRuleContext
extends ParserRuleContext {
    private final int ruleIndex;

    public InterpreterRuleContext(@Nullable ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        super(parent, invokingStateNumber);
        this.ruleIndex = ruleIndex;
    }

    private InterpreterRuleContext(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    @Override
    public int getRuleIndex() {
        return this.ruleIndex;
    }
}

