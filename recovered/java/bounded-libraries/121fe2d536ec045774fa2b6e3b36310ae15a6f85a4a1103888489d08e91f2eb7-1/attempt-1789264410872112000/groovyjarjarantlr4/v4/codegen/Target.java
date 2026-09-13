/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.AttributeRenderer
 *  org.stringtemplate.v4.NumberRenderer
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STErrorListener
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STGroupFile
 *  org.stringtemplate.v4.StringRenderer
 *  org.stringtemplate.v4.misc.STMessage
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import org.stringtemplate.v4.AttributeRenderer;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;
import org.stringtemplate.v4.misc.STMessage;

public abstract class Target {
    protected String[] targetCharValueEscape = new String[255];
    protected final CodeGenerator gen;
    private final String language;
    private STGroup templates;

    protected Target(CodeGenerator gen, String language) {
        this.targetCharValueEscape[10] = "\\n";
        this.targetCharValueEscape[13] = "\\r";
        this.targetCharValueEscape[9] = "\\t";
        this.targetCharValueEscape[8] = "\\b";
        this.targetCharValueEscape[12] = "\\f";
        this.targetCharValueEscape[92] = "\\\\";
        this.targetCharValueEscape[39] = "\\'";
        this.targetCharValueEscape[34] = "\\\"";
        this.gen = gen;
        this.language = language;
    }

    public CodeGenerator getCodeGenerator() {
        return this.gen;
    }

    public String getLanguage() {
        return this.language;
    }

    @NotNull
    public STGroup getTemplates() {
        if (this.templates == null) {
            this.templates = this.loadTemplates();
        }
        return this.templates;
    }

    protected void genFile(Grammar g, ST outputFileST, String fileName) {
        this.getCodeGenerator().write(outputFileST, fileName);
    }

    public String getTokenTypeAsTargetLabel(Grammar g, int ttype) {
        String name = g.getTokenName(ttype);
        if ("<INVALID>".equals(name)) {
            return String.valueOf(ttype);
        }
        return name;
    }

    public String[] getTokenTypesAsTargetLabels(Grammar g, int[] ttypes) {
        String[] labels = new String[ttypes.length];
        for (int i = 0; i < ttypes.length; ++i) {
            labels[i] = this.getTokenTypeAsTargetLabel(g, ttypes[i]);
        }
        return labels;
    }

    public String getTargetStringLiteralFromString(String s, boolean quoted) {
        int c;
        if (s == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        if (quoted) {
            buf.append('\"');
        }
        for (int i = 0; i < s.length(); i += Character.charCount(c)) {
            c = s.codePointAt(i);
            if (c != 39 && c < this.targetCharValueEscape.length && this.targetCharValueEscape[c] != null) {
                buf.append(this.targetCharValueEscape[c]);
                continue;
            }
            if (this.shouldUseUnicodeEscapeForCodePointInDoubleQuotedString(c)) {
                this.appendUnicodeEscapedCodePoint(i, buf);
                continue;
            }
            buf.appendCodePoint(c);
        }
        if (quoted) {
            buf.append('\"');
        }
        return buf.toString();
    }

    protected abstract void appendUnicodeEscapedCodePoint(int var1, StringBuilder var2);

    public String getTargetStringLiteralFromString(String s) {
        return this.getTargetStringLiteralFromString(s, true);
    }

    public abstract String getTargetStringLiteralFromANTLRStringLiteral(CodeGenerator var1, String var2, boolean var3);

    protected boolean shouldUseUnicodeEscapeForCodePointInDoubleQuotedString(int codePoint) {
        assert (codePoint != 10 && codePoint != 34);
        return codePoint < 32 || codePoint == 92 || codePoint >= 127;
    }

    public abstract String encodeIntAsCharEscape(int var1);

    public String getLoopLabel(GrammarAST ast) {
        return "loop" + ast.token.getTokenIndex();
    }

    public String getLoopCounter(GrammarAST ast) {
        return "cnt" + ast.token.getTokenIndex();
    }

    public String getListLabel(String label) {
        ST st = this.getTemplates().getInstanceOf("ListLabelName");
        st.add("label", (Object)label);
        return st.render();
    }

    public String getRuleFunctionContextStructName(Rule r) {
        if (r.g.isLexer()) {
            return this.getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = r.getBaseContext();
        return Utils.capitalize(baseName) + this.getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }

    public String getAltLabelContextStructName(String label) {
        return Utils.capitalize(label) + this.getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }

    public String getRuleFunctionContextStructName(RuleFunction function) {
        Rule r = function.rule;
        if (r.g.isLexer()) {
            return this.getTemplates().getInstanceOf("LexerRuleContext").render();
        }
        String baseName = r.getBaseContext();
        return Utils.capitalize(baseName) + this.getTemplates().getInstanceOf("RuleContextNameSuffix").render();
    }

    public String getImplicitTokenLabel(String tokenName) {
        ST st = this.getTemplates().getInstanceOf("ImplicitTokenLabel");
        int ttype = this.getCodeGenerator().g.getTokenType(tokenName);
        if (tokenName.startsWith("'")) {
            return "s" + ttype;
        }
        String text = this.getTokenTypeAsTargetLabel(this.getCodeGenerator().g, ttype);
        st.add("tokenName", (Object)text);
        return st.render();
    }

    public String getImplicitSetLabel(String id) {
        ST st = this.getTemplates().getInstanceOf("ImplicitSetLabel");
        st.add("id", (Object)id);
        return st.render();
    }

    public String getImplicitRuleLabel(String ruleName) {
        ST st = this.getTemplates().getInstanceOf("ImplicitRuleLabel");
        st.add("ruleName", (Object)ruleName);
        return st.render();
    }

    public String getElementListName(String name) {
        ST st = this.getTemplates().getInstanceOf("ElementListName");
        st.add("elemName", (Object)this.getElementName(name));
        return st.render();
    }

    public String getElementName(String name) {
        if (".".equals(name)) {
            return "_wild";
        }
        if (this.getCodeGenerator().g.getRule(name) != null) {
            return name;
        }
        int ttype = this.getCodeGenerator().g.getTokenType(name);
        if (ttype == 0) {
            return name;
        }
        return this.getTokenTypeAsTargetLabel(this.getCodeGenerator().g, ttype);
    }

    public String getRecognizerFileName(boolean header) {
        ST extST = this.getTemplates().getInstanceOf("codeFileExtension");
        String recognizerName = this.gen.g.getRecognizerName();
        return recognizerName + extST.render();
    }

    public String getListenerFileName(boolean header) {
        assert (this.gen.g.name != null);
        ST extST = this.getTemplates().getInstanceOf("codeFileExtension");
        String listenerName = this.gen.g.name + "Listener";
        return listenerName + extST.render();
    }

    public String getVisitorFileName(boolean header) {
        assert (this.gen.g.name != null);
        ST extST = this.getTemplates().getInstanceOf("codeFileExtension");
        String listenerName = this.gen.g.name + "Visitor";
        return listenerName + extST.render();
    }

    public String getBaseListenerFileName(boolean header) {
        assert (this.gen.g.name != null);
        ST extST = this.getTemplates().getInstanceOf("codeFileExtension");
        String listenerName = this.gen.g.name + "BaseListener";
        return listenerName + extST.render();
    }

    public String getBaseVisitorFileName(boolean header) {
        assert (this.gen.g.name != null);
        ST extST = this.getTemplates().getInstanceOf("codeFileExtension");
        String listenerName = this.gen.g.name + "BaseVisitor";
        return listenerName + extST.render();
    }

    public int getSerializedATNSegmentLimit() {
        return Integer.MAX_VALUE;
    }

    public int getInlineTestSetWordSize() {
        return 64;
    }

    public boolean grammarSymbolCausesIssueInGeneratedCode(GrammarAST idNode) {
        switch (idNode.getParent().getType()) {
            case 10: {
                switch (idNode.getParent().getParent().getType()) {
                    case 42: 
                    case 82: {
                        return false;
                    }
                }
                break;
            }
            case 11: 
            case 82: {
                return false;
            }
            case 86: {
                if (idNode.getChildIndex() != 0) break;
                return false;
            }
        }
        return this.visibleGrammarSymbolCausesIssueInGeneratedCode(idNode);
    }

    protected abstract boolean visibleGrammarSymbolCausesIssueInGeneratedCode(GrammarAST var1);

    @NotNull
    protected STGroup loadTemplates() {
        STGroupFile result = new STGroupFile("groovyjarjarantlr4/v4/tool/templates/codegen/" + this.getLanguage() + "/" + this.getLanguage() + STGroup.GROUP_FILE_EXTENSION);
        result.registerRenderer(Integer.class, (AttributeRenderer)new NumberRenderer());
        result.registerRenderer(String.class, (AttributeRenderer)new StringRenderer());
        result.setListener(new STErrorListener(){

            public void compileTimeError(STMessage msg) {
                this.reportError(msg);
            }

            public void runTimeError(STMessage msg) {
                this.reportError(msg);
            }

            public void IOError(STMessage msg) {
                this.reportError(msg);
            }

            public void internalError(STMessage msg) {
                this.reportError(msg);
            }

            private void reportError(STMessage msg) {
                Target.this.getCodeGenerator().tool.errMgr.toolError(ErrorType.STRING_TEMPLATE_WARNING, msg.cause, msg.toString());
            }
        });
        return result;
    }

    public boolean wantsBaseListener() {
        return true;
    }

    public boolean wantsBaseVisitor() {
        return true;
    }

    public boolean supportsOverloadedMethods() {
        return true;
    }

    public boolean needsHeader() {
        return false;
    }
}

