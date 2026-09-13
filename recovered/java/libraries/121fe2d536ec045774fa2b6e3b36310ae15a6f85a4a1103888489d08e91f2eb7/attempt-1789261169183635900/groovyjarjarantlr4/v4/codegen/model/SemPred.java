/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.ActionTranslator;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class SemPred
extends Action {
    public String msg;
    public String predicate;
    @ModelElement
    public List<ActionChunk> failChunks;

    public SemPred(OutputModelFactory factory, @NotNull ActionAST ast) {
        super(factory, ast);
        assert (ast.atnState != null && ast.atnState.getNumberOfTransitions() == 1 && ast.atnState.transition(0) instanceof AbstractPredicateTransition);
        GrammarAST failNode = ast.getOptionAST("fail");
        this.predicate = ast.getText();
        if (this.predicate.startsWith("{") && this.predicate.endsWith("}?")) {
            this.predicate = this.predicate.substring(1, this.predicate.length() - 2);
        }
        this.predicate = factory.getTarget().getTargetStringLiteralFromString(this.predicate);
        if (failNode == null) {
            return;
        }
        if (failNode instanceof ActionAST) {
            ActionAST failActionNode = (ActionAST)failNode;
            RuleFunction rf = factory.getCurrentRuleFunction();
            this.failChunks = ActionTranslator.translateAction(factory, rf, failActionNode.token, failActionNode);
        } else {
            this.msg = factory.getTarget().getTargetStringLiteralFromANTLRStringLiteral(factory.getGenerator(), failNode.getText(), true);
        }
    }
}

