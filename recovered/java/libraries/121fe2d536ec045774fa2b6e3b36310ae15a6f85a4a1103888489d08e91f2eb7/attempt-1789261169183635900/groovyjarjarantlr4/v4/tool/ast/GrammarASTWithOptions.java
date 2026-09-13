/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class GrammarASTWithOptions
extends GrammarAST {
    protected Map<String, GrammarAST> options;

    public GrammarASTWithOptions(GrammarASTWithOptions node) {
        super(node);
        this.options = node.options;
    }

    public GrammarASTWithOptions(Token t) {
        super(t);
    }

    public GrammarASTWithOptions(int type) {
        super(type);
    }

    public GrammarASTWithOptions(int type, Token t) {
        super(type, t);
    }

    public GrammarASTWithOptions(int type, Token t, String text) {
        super(type, t, text);
    }

    public void setOption(String key, GrammarAST node) {
        if (this.options == null) {
            this.options = new HashMap<String, GrammarAST>();
        }
        this.options.put(key, node);
    }

    public String getOptionString(String key) {
        GrammarAST value = this.getOptionAST(key);
        if (value == null) {
            return null;
        }
        if (value instanceof ActionAST) {
            return value.getText();
        }
        String v = value.getText();
        if ((v.startsWith("'") || v.startsWith("\"")) && (v = CharSupport.getStringFromGrammarStringLiteral(v)) == null) {
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_ESCAPE_SEQUENCE, this.g.fileName, value.getToken(), value.getText());
            v = "";
        }
        return v;
    }

    public GrammarAST getOptionAST(String key) {
        if (this.options == null) {
            return null;
        }
        return this.options.get(key);
    }

    public int getNumberOfOptions() {
        return this.options == null ? 0 : this.options.size();
    }

    @Override
    public abstract GrammarASTWithOptions dupNode();

    @NotNull
    public Map<String, GrammarAST> getOptions() {
        if (this.options == null) {
            return Collections.emptyMap();
        }
        return this.options;
    }
}

