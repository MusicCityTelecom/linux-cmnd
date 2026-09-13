/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import org.apache.groovy.parser.antlr4.Antlr4ParserPlugin;
import org.codehaus.groovy.control.ParserPlugin;
import org.codehaus.groovy.control.ParserPluginFactory;

public class Antlr4PluginFactory
extends ParserPluginFactory {
    @Override
    public ParserPlugin createParserPlugin() {
        return new Antlr4ParserPlugin();
    }
}

