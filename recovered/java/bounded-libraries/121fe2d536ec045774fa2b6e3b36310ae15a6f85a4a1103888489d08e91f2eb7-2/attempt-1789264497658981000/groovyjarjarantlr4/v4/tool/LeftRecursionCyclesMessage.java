/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.Collection;

public class LeftRecursionCyclesMessage
extends ANTLRMessage {
    public LeftRecursionCyclesMessage(String fileName, Collection<? extends Collection<Rule>> cycles) {
        super(ErrorType.LEFT_RECURSION_CYCLES, LeftRecursionCyclesMessage.getStartTokenOfFirstRule(cycles), cycles);
        this.fileName = fileName;
    }

    protected static Token getStartTokenOfFirstRule(Collection<? extends Collection<Rule>> cycles) {
        if (cycles == null) {
            return null;
        }
        for (Collection<Rule> collection : cycles) {
            if (collection == null) {
                return null;
            }
            for (Rule rule : collection) {
                if (rule.ast == null) continue;
                return rule.ast.getToken();
            }
        }
        return null;
    }
}

