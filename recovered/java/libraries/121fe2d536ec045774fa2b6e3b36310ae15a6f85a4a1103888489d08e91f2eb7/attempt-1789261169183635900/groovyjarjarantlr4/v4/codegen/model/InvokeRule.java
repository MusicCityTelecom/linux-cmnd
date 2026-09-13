/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.ActionTranslator;
import groovyjarjarantlr4.v4.codegen.ParserFactory;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.RuleElement;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextListDecl;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class InvokeRule
extends RuleElement
implements LabeledOp {
    public String name;
    public OrderedHashSet<Decl> labels = new OrderedHashSet();
    public String ctxName;
    @ModelElement
    public List<ActionChunk> argExprsChunks;

    public InvokeRule(ParserFactory factory, GrammarAST ast, GrammarAST labelAST) {
        super(factory, ast);
        ActionAST arg;
        if (ast.atnState != null) {
            RuleTransition ruleTrans = (RuleTransition)ast.atnState.transition(0);
            this.stateNumber = ast.atnState.stateNumber;
        }
        this.name = ast.getText();
        Rule r = factory.getGrammar().getRule(this.name);
        this.ctxName = factory.getTarget().getRuleFunctionContextStructName(r);
        RuleFunction rf = factory.getCurrentRuleFunction();
        if (labelAST != null) {
            String label = labelAST.getText();
            if (labelAST.parent.getType() == 46) {
                factory.defineImplicitLabel(ast, this);
                String listLabel = factory.getTarget().getListLabel(label);
                RuleContextListDecl l = new RuleContextListDecl(factory, listLabel, this.ctxName);
                rf.addContextDecl(ast.getAltLabel(), l);
            } else {
                RuleContextDecl d = new RuleContextDecl(factory, label, this.ctxName);
                this.labels.add(d);
                rf.addContextDecl(ast.getAltLabel(), d);
            }
        }
        if ((arg = (ActionAST)ast.getFirstChildWithType(8)) != null) {
            this.argExprsChunks = ActionTranslator.translateAction(factory, rf, arg.token, arg);
        }
        if (factory.getCurrentOuterMostAlt().ruleRefsInActions.containsKey(ast.getText())) {
            String label = factory.getTarget().getImplicitRuleLabel(ast.getText());
            RuleContextDecl d = new RuleContextDecl(factory, label, this.ctxName);
            this.labels.add(d);
            rf.addContextDecl(ast.getAltLabel(), d);
        }
    }

    @Override
    public List<Decl> getLabels() {
        return this.labels.elements();
    }
}

