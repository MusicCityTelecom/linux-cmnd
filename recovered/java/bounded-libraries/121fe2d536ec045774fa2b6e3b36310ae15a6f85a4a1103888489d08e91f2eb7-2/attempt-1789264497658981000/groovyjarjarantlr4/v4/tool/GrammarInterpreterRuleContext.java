/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.runtime.InterpreterRuleContext;
import groovyjarjarantlr4.v4.runtime.ParserRuleContext;

public class GrammarInterpreterRuleContext
extends InterpreterRuleContext {
    protected int outerAltNum = 1;

    public GrammarInterpreterRuleContext(ParserRuleContext parent, int invokingStateNumber, int ruleIndex) {
        super(parent, invokingStateNumber, ruleIndex);
    }

    public int getOuterAltNum() {
        return this.outerAltNum;
    }

    public void setOuterAltNum(int outerAltNum) {
        this.outerAltNum = outerAltNum;
    }

    @Override
    public int getAltNumber() {
        return this.getOuterAltNum();
    }

    @Override
    public void setAltNumber(int altNumber) {
        this.setOuterAltNum(altNumber);
    }
}

