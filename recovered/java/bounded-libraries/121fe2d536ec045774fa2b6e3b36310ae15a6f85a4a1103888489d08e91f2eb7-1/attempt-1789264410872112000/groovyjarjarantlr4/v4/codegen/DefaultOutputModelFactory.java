/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.codegen.BlankOutputModelFactory;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.OutputModelController;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.CodeBlock;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public abstract class DefaultOutputModelFactory
extends BlankOutputModelFactory {
    @NotNull
    public final Grammar g;
    @NotNull
    public final CodeGenerator gen;
    public OutputModelController controller;

    protected DefaultOutputModelFactory(@NotNull CodeGenerator gen) {
        this.gen = gen;
        this.g = gen.g;
        if (gen.getTarget() == null) {
            throw new UnsupportedOperationException("Cannot build an output model without a target.");
        }
    }

    @Override
    public void setController(OutputModelController controller) {
        this.controller = controller;
    }

    @Override
    public OutputModelController getController() {
        return this.controller;
    }

    @Override
    public List<SrcOp> rulePostamble(RuleFunction function, Rule r) {
        if (r.namedActions.containsKey("after") || r.namedActions.containsKey("finally")) {
            CodeGenerator gen = this.getGenerator();
            STGroup codegenTemplates = gen.getTemplates();
            ST setStopTokenAST = codegenTemplates.getInstanceOf("recRuleSetStopToken");
            Action setStopTokenAction = new Action((OutputModelFactory)this, function.getEffectiveRuleContext(this.controller), setStopTokenAST);
            ArrayList<SrcOp> ops = new ArrayList<SrcOp>(1);
            ops.add(setStopTokenAction);
            return ops;
        }
        return super.rulePostamble(function, r);
    }

    @Override
    @NotNull
    public Grammar getGrammar() {
        return this.g;
    }

    @Override
    public CodeGenerator getGenerator() {
        return this.gen;
    }

    @Override
    public Target getTarget() {
        Target target = this.getGenerator().getTarget();
        assert (target != null);
        return target;
    }

    @Override
    public OutputModelObject getRoot() {
        return this.controller.getRoot();
    }

    @Override
    public RuleFunction getCurrentRuleFunction() {
        return this.controller.getCurrentRuleFunction();
    }

    @Override
    public Alternative getCurrentOuterMostAlt() {
        return this.controller.getCurrentOuterMostAlt();
    }

    @Override
    public CodeBlock getCurrentBlock() {
        return this.controller.getCurrentBlock();
    }

    @Override
    public CodeBlockForOuterMostAlt getCurrentOuterMostAlternativeBlock() {
        return this.controller.getCurrentOuterMostAlternativeBlock();
    }

    @Override
    public int getCodeBlockLevel() {
        return this.controller.codeBlockLevel;
    }

    @Override
    public int getTreeLevel() {
        return this.controller.treeLevel;
    }

    @NotNull
    public static List<SrcOp> list(SrcOp ... values) {
        return new ArrayList<SrcOp>(Arrays.asList(values));
    }

    @NotNull
    public static List<SrcOp> list(Collection<? extends SrcOp> values) {
        return new ArrayList<SrcOp>(values);
    }

    @Nullable
    public Decl getCurrentDeclForName(String name) {
        if (this.getCurrentBlock().locals == null) {
            return null;
        }
        for (Decl d : this.getCurrentBlock().locals.elements()) {
            if (!d.name.equals(name)) continue;
            return d;
        }
        return null;
    }
}

