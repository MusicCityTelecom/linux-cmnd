/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.atn;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public class ATNDeserializationOptions {
    private static final ATNDeserializationOptions defaultOptions = new ATNDeserializationOptions();
    private boolean readOnly;
    private boolean verifyATN;
    private boolean generateRuleBypassTransitions;
    private boolean optimize;

    public ATNDeserializationOptions() {
        this.verifyATN = true;
        this.generateRuleBypassTransitions = false;
        this.optimize = true;
    }

    public ATNDeserializationOptions(ATNDeserializationOptions options) {
        this.verifyATN = options.verifyATN;
        this.generateRuleBypassTransitions = options.generateRuleBypassTransitions;
        this.optimize = options.optimize;
    }

    @NotNull
    public static ATNDeserializationOptions getDefaultOptions() {
        return defaultOptions;
    }

    public final boolean isReadOnly() {
        return this.readOnly;
    }

    public final void makeReadOnly() {
        this.readOnly = true;
    }

    public final boolean isVerifyATN() {
        return this.verifyATN;
    }

    public final void setVerifyATN(boolean verifyATN) {
        this.throwIfReadOnly();
        this.verifyATN = verifyATN;
    }

    public final boolean isGenerateRuleBypassTransitions() {
        return this.generateRuleBypassTransitions;
    }

    public final void setGenerateRuleBypassTransitions(boolean generateRuleBypassTransitions) {
        this.throwIfReadOnly();
        this.generateRuleBypassTransitions = generateRuleBypassTransitions;
    }

    public final boolean isOptimize() {
        return this.optimize;
    }

    public final void setOptimize(boolean optimize) {
        this.throwIfReadOnly();
        this.optimize = optimize;
    }

    protected void throwIfReadOnly() {
        if (this.isReadOnly()) {
            throw new IllegalStateException("The object is read only.");
        }
    }

    static {
        defaultOptions.makeReadOnly();
    }
}

