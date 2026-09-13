/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ParserRuleContext;

public class RuleContextWithAltNum
extends ParserRuleContext {
    private int altNumber;

    public RuleContextWithAltNum() {
        this.altNumber = 0;
    }

    public RuleContextWithAltNum(ParserRuleContext parent, int invokingStateNumber) {
        super(parent, invokingStateNumber);
    }

    @Override
    public int getAltNumber() {
        return this.altNumber;
    }

    @Override
    public void setAltNumber(int altNum) {
        this.altNumber = altNum;
    }
}

