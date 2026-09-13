/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionText;
import groovyjarjarantlr4.v4.codegen.model.chunk.ArgRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.LabelRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.ListLabelRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.LocalRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.NonLocalAttrRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.QRetValueRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.RetValueRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef_ctx;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef_parser;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef_start;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef_stop;
import groovyjarjarantlr4.v4.codegen.model.chunk.RulePropertyRef_text;
import groovyjarjarantlr4.v4.codegen.model.chunk.SetAttr;
import groovyjarjarantlr4.v4.codegen.model.chunk.SetNonLocalAttr;
import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_ctx;
import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_parser;
import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_start;
import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_stop;
import groovyjarjarantlr4.v4.codegen.model.chunk.ThisRulePropertyRef_text;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_channel;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_index;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_int;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_line;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_pos;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_text;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenPropertyRef_type;
import groovyjarjarantlr4.v4.codegen.model.chunk.TokenRef;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.parse.ActionSplitter;
import groovyjarjarantlr4.v4.parse.ActionSplitterListener;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionTranslator
implements ActionSplitterListener {
    public static final Map<String, Class<? extends RulePropertyRef>> thisRulePropToModelMap = new HashMap<String, Class<? extends RulePropertyRef>>();
    public static final Map<String, Class<? extends RulePropertyRef>> rulePropToModelMap;
    public static final Map<String, Class<? extends TokenPropertyRef>> tokenPropToModelMap;
    CodeGenerator gen;
    ActionAST node;
    RuleFunction rf;
    List<ActionChunk> chunks = new ArrayList<ActionChunk>();
    OutputModelFactory factory;
    StructDecl nodeContext;

    public ActionTranslator(OutputModelFactory factory, ActionAST node) {
        this.factory = factory;
        this.node = node;
        this.gen = factory.getGenerator();
    }

    public static String toString(List<ActionChunk> chunks) {
        StringBuilder buf = new StringBuilder();
        for (ActionChunk c : chunks) {
            buf.append(c.toString());
        }
        return buf.toString();
    }

    public static List<ActionChunk> translateAction(OutputModelFactory factory, RuleFunction rf, Token tokenWithinAction, ActionAST node) {
        String action = tokenWithinAction.getText();
        if (action != null && action.length() > 0 && action.charAt(0) == '{') {
            int firstCurly = action.indexOf(123);
            int lastCurly = action.lastIndexOf(125);
            if (firstCurly >= 0 && lastCurly >= 0) {
                action = action.substring(firstCurly + 1, lastCurly);
            }
        }
        return ActionTranslator.translateActionChunk(factory, rf, action, node);
    }

    public static List<ActionChunk> translateActionChunk(OutputModelFactory factory, RuleFunction rf, String action, ActionAST node) {
        Token tokenWithinAction = node.token;
        ActionTranslator translator = new ActionTranslator(factory, node);
        translator.rf = rf;
        factory.getGrammar().tool.log("action-translator", "translate " + action);
        String altLabel = node.getAltLabel();
        if (rf != null) {
            translator.nodeContext = rf.getEffectiveRuleContext(factory.getController());
            if (altLabel != null) {
                translator.nodeContext = rf.getEffectiveAltLabelContexts(factory.getController()).get(altLabel);
            }
        }
        ANTLRStringStream in = new ANTLRStringStream(action);
        in.setLine(tokenWithinAction.getLine());
        in.setCharPositionInLine(tokenWithinAction.getCharPositionInLine());
        ActionSplitter trigger = new ActionSplitter((CharStream)in, translator);
        trigger.getActionTokens();
        return translator.chunks;
    }

    @Override
    public void attr(String expr, Token x) {
        this.gen.g.tool.log("action-translator", "attr " + x);
        Attribute a = this.node.resolver.resolveToAttribute(x.getText(), this.node);
        if (a != null) {
            switch (a.dict.type) {
                case ARG: {
                    this.chunks.add(new ArgRef(this.nodeContext, x.getText()));
                    break;
                }
                case RET: {
                    this.chunks.add(new RetValueRef(this.rf.getEffectiveRuleContext(this.factory.getController()), x.getText()));
                    break;
                }
                case LOCAL: {
                    this.chunks.add(new LocalRef(this.nodeContext, x.getText()));
                    break;
                }
                case PREDEFINED_RULE: {
                    this.chunks.add(this.getRulePropertyRef(x));
                }
            }
        }
        if (this.node.resolver.resolvesToToken(x.getText(), this.node)) {
            this.chunks.add(new TokenRef(this.nodeContext, this.getTokenLabel(x.getText())));
            return;
        }
        if (this.node.resolver.resolvesToLabel(x.getText(), this.node)) {
            this.chunks.add(new LabelRef(this.nodeContext, this.getTokenLabel(x.getText())));
            return;
        }
        if (this.node.resolver.resolvesToListLabel(x.getText(), this.node)) {
            this.chunks.add(new ListLabelRef(this.nodeContext, x.getText()));
            return;
        }
        Rule r = this.factory.getGrammar().getRule(x.getText());
        if (r != null) {
            this.chunks.add(new LabelRef(this.nodeContext, this.getRuleLabel(x.getText())));
        }
    }

    @Override
    public void qualifiedAttr(String expr, Token x, Token y) {
        this.gen.g.tool.log("action-translator", "qattr " + x + "." + y);
        if (this.node.resolver.resolveToAttribute(x.getText(), this.node) != null) {
            this.attr(expr, x);
            this.chunks.add(new ActionText(this.nodeContext, "." + y.getText()));
            return;
        }
        Attribute a = this.node.resolver.resolveToAttribute(x.getText(), y.getText(), this.node);
        if (a == null) {
            this.gen.g.tool.errMgr.grammarError(ErrorType.UNKNOWN_SIMPLE_ATTRIBUTE, this.gen.g.fileName, x, x.getText(), "rule");
            return;
        }
        switch (a.dict.type) {
            case ARG: {
                this.chunks.add(new ArgRef(this.nodeContext, y.getText()));
                break;
            }
            case RET: {
                this.chunks.add(new QRetValueRef(this.nodeContext, this.getRuleLabel(x.getText()), y.getText()));
                break;
            }
            case PREDEFINED_RULE: {
                this.chunks.add(this.getRulePropertyRef(x, y));
                break;
            }
            case TOKEN: {
                this.chunks.add(this.getTokenPropertyRef(x, y));
            }
        }
    }

    @Override
    public void setAttr(String expr, Token x, Token rhs) {
        this.gen.g.tool.log("action-translator", "setAttr " + x + " " + rhs);
        List<ActionChunk> rhsChunks = ActionTranslator.translateActionChunk(this.factory, this.rf, rhs.getText(), this.node);
        SetAttr s = new SetAttr(this.nodeContext, x.getText(), rhsChunks);
        this.chunks.add(s);
    }

    @Override
    public void nonLocalAttr(String expr, Token x, Token y) {
        this.gen.g.tool.log("action-translator", "nonLocalAttr " + x + "::" + y);
        Rule r = this.factory.getGrammar().getRule(x.getText());
        this.chunks.add(new NonLocalAttrRef(this.nodeContext, x.getText(), y.getText(), r.index));
    }

    @Override
    public void setNonLocalAttr(String expr, Token x, Token y, Token rhs) {
        this.gen.g.tool.log("action-translator", "setNonLocalAttr " + x + "::" + y + "=" + rhs);
        Rule r = this.factory.getGrammar().getRule(x.getText());
        List<ActionChunk> rhsChunks = ActionTranslator.translateActionChunk(this.factory, this.rf, rhs.getText(), this.node);
        SetNonLocalAttr s = new SetNonLocalAttr(this.nodeContext, x.getText(), y.getText(), r.index, rhsChunks);
        this.chunks.add(s);
    }

    @Override
    public void text(String text) {
        this.chunks.add(new ActionText(this.nodeContext, text));
    }

    TokenPropertyRef getTokenPropertyRef(Token x, Token y) {
        try {
            Class<? extends TokenPropertyRef> c = tokenPropToModelMap.get(y.getText());
            Constructor<? extends TokenPropertyRef> ctor = c.getConstructor(StructDecl.class, String.class);
            TokenPropertyRef ref = ctor.newInstance(this.nodeContext, this.getTokenLabel(x.getText()));
            return ref;
        }
        catch (Exception e) {
            this.factory.getGrammar().tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, e, new Object[0]);
            return null;
        }
    }

    RulePropertyRef getRulePropertyRef(Token prop) {
        try {
            Class<? extends RulePropertyRef> c = thisRulePropToModelMap.get(prop.getText());
            Constructor<? extends RulePropertyRef> ctor = c.getConstructor(StructDecl.class, String.class);
            RulePropertyRef ref = ctor.newInstance(this.nodeContext, this.getRuleLabel(prop.getText()));
            return ref;
        }
        catch (Exception e) {
            this.factory.getGrammar().tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, e, new Object[0]);
            return null;
        }
    }

    RulePropertyRef getRulePropertyRef(Token x, Token prop) {
        Grammar g = this.factory.getGrammar();
        try {
            Class<? extends RulePropertyRef> c = rulePropToModelMap.get(prop.getText());
            Constructor<? extends RulePropertyRef> ctor = c.getConstructor(StructDecl.class, String.class);
            RulePropertyRef ref = ctor.newInstance(this.nodeContext, this.getRuleLabel(x.getText()));
            return ref;
        }
        catch (Exception e) {
            g.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, e, prop.getText());
            return null;
        }
    }

    public String getTokenLabel(String x) {
        if (this.node.resolver.resolvesToLabel(x, this.node)) {
            return x;
        }
        return this.factory.getTarget().getImplicitTokenLabel(x);
    }

    public String getRuleLabel(String x) {
        if (this.node.resolver.resolvesToLabel(x, this.node)) {
            return x;
        }
        return this.factory.getTarget().getImplicitRuleLabel(x);
    }

    static {
        thisRulePropToModelMap.put("start", ThisRulePropertyRef_start.class);
        thisRulePropToModelMap.put("stop", ThisRulePropertyRef_stop.class);
        thisRulePropToModelMap.put("text", ThisRulePropertyRef_text.class);
        thisRulePropToModelMap.put("ctx", ThisRulePropertyRef_ctx.class);
        thisRulePropToModelMap.put("parser", ThisRulePropertyRef_parser.class);
        rulePropToModelMap = new HashMap<String, Class<? extends RulePropertyRef>>();
        rulePropToModelMap.put("start", RulePropertyRef_start.class);
        rulePropToModelMap.put("stop", RulePropertyRef_stop.class);
        rulePropToModelMap.put("text", RulePropertyRef_text.class);
        rulePropToModelMap.put("ctx", RulePropertyRef_ctx.class);
        rulePropToModelMap.put("parser", RulePropertyRef_parser.class);
        tokenPropToModelMap = new HashMap<String, Class<? extends TokenPropertyRef>>();
        tokenPropToModelMap.put("text", TokenPropertyRef_text.class);
        tokenPropToModelMap.put("type", TokenPropertyRef_type.class);
        tokenPropToModelMap.put("line", TokenPropertyRef_line.class);
        tokenPropToModelMap.put("index", TokenPropertyRef_index.class);
        tokenPropToModelMap.put("pos", TokenPropertyRef_pos.class);
        tokenPropToModelMap.put("channel", TokenPropertyRef_channel.class);
        tokenPropToModelMap.put("int", TokenPropertyRef_int.class);
    }
}

