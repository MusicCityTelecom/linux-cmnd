/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.runtime.misc.MultiMap;
import groovyjarjarantlr4.v4.tool.ANTLRToolListener;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import java.util.List;

public class LexerGrammar
extends Grammar {
    public static final String DEFAULT_MODE_NAME = "DEFAULT_MODE";
    public Grammar implicitLexerOwner;
    public MultiMap<String, Rule> modes;

    public LexerGrammar(Tool tool, GrammarRootAST ast) {
        super(tool, ast);
    }

    public LexerGrammar(String grammarText) throws RecognitionException {
        super(grammarText);
    }

    public LexerGrammar(String grammarText, ANTLRToolListener listener) throws RecognitionException {
        super(grammarText, listener);
    }

    public LexerGrammar(String fileName, String grammarText, ANTLRToolListener listener) throws RecognitionException {
        super(fileName, grammarText, listener);
    }

    @Override
    public boolean defineRule(Rule r) {
        if (!super.defineRule(r)) {
            return false;
        }
        if (this.modes == null) {
            this.modes = new MultiMap();
        }
        this.modes.map(r.mode, r);
        return true;
    }

    @Override
    public boolean undefineRule(Rule r) {
        if (!super.undefineRule(r)) {
            return false;
        }
        boolean removed = ((List)this.modes.get(r.mode)).remove(r);
        assert (removed);
        return true;
    }
}

