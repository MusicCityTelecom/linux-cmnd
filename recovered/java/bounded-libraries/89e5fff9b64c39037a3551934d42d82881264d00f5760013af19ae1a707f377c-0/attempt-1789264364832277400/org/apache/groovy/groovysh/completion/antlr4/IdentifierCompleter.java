/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovyjarjarantlr4.v4.runtime.Token
 */
package org.apache.groovy.groovysh.completion.antlr4;

import groovyjarjarantlr4.v4.runtime.Token;
import java.util.List;

public interface IdentifierCompleter {
    public boolean complete(List<Token> var1, List<CharSequence> var2);
}

