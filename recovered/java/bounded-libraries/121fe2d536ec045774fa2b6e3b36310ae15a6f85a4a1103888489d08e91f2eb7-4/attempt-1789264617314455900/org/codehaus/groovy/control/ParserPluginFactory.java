/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import org.apache.groovy.parser.antlr4.Antlr4PluginFactory;
import org.codehaus.groovy.control.ParserPlugin;

public abstract class ParserPluginFactory {
    public abstract ParserPlugin createParserPlugin();

    public static ParserPluginFactory antlr4() {
        return new Antlr4PluginFactory();
    }

    @Deprecated
    public static ParserPluginFactory antlr2() {
        throw new UnsupportedOperationException("Antlr2 is no longer supported");
    }
}

