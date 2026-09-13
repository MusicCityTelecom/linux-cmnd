/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextListDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;

public class LeftRecursiveRuleFunction
extends RuleFunction {
    public LeftRecursiveRuleFunction(OutputModelFactory factory, LeftRecursiveRule r) {
        super(factory, r);
        for (Tuple2<GrammarAST, String> pair : r.leftRecursiveRuleRefLabels) {
            GrammarAST idAST = pair.getItem1();
            String altLabel = pair.getItem2();
            String label = idAST.getText();
            GrammarAST rrefAST = (GrammarAST)idAST.getParent().getChild(1);
            if (rrefAST.getType() != 57) continue;
            Rule targetRule = factory.getGrammar().getRule(rrefAST.getText());
            String ctxName = factory.getTarget().getRuleFunctionContextStructName(targetRule);
            RuleContextDecl d = idAST.getParent().getType() == 10 ? new RuleContextDecl(factory, label, ctxName) : new RuleContextListDecl(factory, label, ctxName);
            StructDecl struct = this.getEffectiveRuleContext(factory.getController());
            StructDecl s = this.getEffectiveAltLabelContexts(factory.getController()).get(altLabel);
            if (s != null) {
                struct = s;
            }
            struct.addDecl(d);
        }
    }
}

