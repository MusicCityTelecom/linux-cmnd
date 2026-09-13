/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import java.util.HashMap;
import java.util.Map;

public class GrammarRootAST
extends GrammarASTWithOptions {
    public static final Map<String, String> defaultOptions = new HashMap<String, String>();
    public int grammarType;
    public boolean hasErrors;
    @NotNull
    public final TokenStream tokenStream;
    public Map<String, String> cmdLineOptions;
    public String fileName;

    public GrammarRootAST(GrammarRootAST node) {
        super(node);
        this.grammarType = node.grammarType;
        this.hasErrors = node.hasErrors;
        this.tokenStream = node.tokenStream;
    }

    public GrammarRootAST(Token t, TokenStream tokenStream) {
        super(t);
        if (tokenStream == null) {
            throw new NullPointerException("tokenStream");
        }
        this.tokenStream = tokenStream;
    }

    public GrammarRootAST(int type, Token t, TokenStream tokenStream) {
        super(type, t);
        if (tokenStream == null) {
            throw new NullPointerException("tokenStream");
        }
        this.tokenStream = tokenStream;
    }

    public GrammarRootAST(int type, Token t, String text, TokenStream tokenStream) {
        super(type, t, text);
        if (tokenStream == null) {
            throw new NullPointerException("tokenStream");
        }
        this.tokenStream = tokenStream;
    }

    public String getGrammarName() {
        Tree t = this.getChild(0);
        if (t != null) {
            return t.getText();
        }
        return null;
    }

    @Override
    public String getOptionString(String key) {
        if (this.cmdLineOptions != null && this.cmdLineOptions.containsKey(key)) {
            return this.cmdLineOptions.get(key);
        }
        String value = super.getOptionString(key);
        if (value == null) {
            value = defaultOptions.get(key);
        }
        return value;
    }

    @Override
    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }

    @Override
    public GrammarRootAST dupNode() {
        return new GrammarRootAST(this);
    }

    static {
        defaultOptions.put("language", "Java");
        defaultOptions.put("abstract", "false");
    }
}

