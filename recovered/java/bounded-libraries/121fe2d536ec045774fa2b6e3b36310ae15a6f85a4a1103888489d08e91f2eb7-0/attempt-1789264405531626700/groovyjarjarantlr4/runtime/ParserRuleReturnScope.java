/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.RuleReturnScope;
import groovyjarjarantlr4.runtime.Token;

public class ParserRuleReturnScope
extends RuleReturnScope {
    public Token start;
    public Token stop;

    public Object getStart() {
        return this.start;
    }

    public Object getStop() {
        return this.stop;
    }

    public Object getTree() {
        return null;
    }
}

