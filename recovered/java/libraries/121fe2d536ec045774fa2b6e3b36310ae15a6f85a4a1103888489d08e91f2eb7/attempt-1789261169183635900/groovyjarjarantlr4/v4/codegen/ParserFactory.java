/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.analysis.AnalysisPipeline;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.DefaultOutputModelFactory;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.AddToLabelList;
import groovyjarjarantlr4.v4.codegen.model.AltBlock;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.InvokeRule;
import groovyjarjarantlr4.v4.codegen.model.LL1AltBlock;
import groovyjarjarantlr4.v4.codegen.model.LL1OptionalBlock;
import groovyjarjarantlr4.v4.codegen.model.LL1OptionalBlockSingleAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1PlusBlockSingleAlt;
import groovyjarjarantlr4.v4.codegen.model.LL1StarBlockSingleAlt;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.LeftFactoredRuleFunction;
import groovyjarjarantlr4.v4.codegen.model.LeftRecursiveRuleFunction;
import groovyjarjarantlr4.v4.codegen.model.LeftUnfactoredRuleFunction;
import groovyjarjarantlr4.v4.codegen.model.MatchNotSet;
import groovyjarjarantlr4.v4.codegen.model.MatchSet;
import groovyjarjarantlr4.v4.codegen.model.MatchToken;
import groovyjarjarantlr4.v4.codegen.model.OptionalBlock;
import groovyjarjarantlr4.v4.codegen.model.Parser;
import groovyjarjarantlr4.v4.codegen.model.ParserFile;
import groovyjarjarantlr4.v4.codegen.model.PlusBlock;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.SemPred;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.StarBlock;
import groovyjarjarantlr4.v4.codegen.model.TestSetInline;
import groovyjarjarantlr4.v4.codegen.model.Wildcard;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.codegen.model.decl.RuleContextDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.TokenListDecl;
import groovyjarjarantlr4.v4.runtime.atn.DecisionState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserFactory
extends DefaultOutputModelFactory {
    private final Map<Rule, RuleFunction> ruleFunctions = new HashMap<Rule, RuleFunction>();

    public ParserFactory(CodeGenerator gen) {
        super(gen);
    }

    @Override
    public ParserFile parserFile(String fileName) {
        return new ParserFile((OutputModelFactory)this, fileName);
    }

    @Override
    public Parser parser(ParserFile file) {
        return new Parser((OutputModelFactory)this, file);
    }

    @Override
    public RuleFunction rule(Rule r) {
        RuleFunction rf = this.ruleFunctions.get(r);
        if (rf != null) {
            return rf;
        }
        rf = r instanceof LeftRecursiveRule ? new LeftRecursiveRuleFunction((OutputModelFactory)this, (LeftRecursiveRule)r) : (r.name.contains("$lf$") ? new LeftFactoredRuleFunction((OutputModelFactory)this, r) : (r.name.contains("$nolf$") ? new LeftUnfactoredRuleFunction((OutputModelFactory)this, r) : new RuleFunction((OutputModelFactory)this, r)));
        this.ruleFunctions.put(r, rf);
        return rf;
    }

    @Override
    public CodeBlockForAlt epsilon(Alternative alt, boolean outerMost) {
        return this.alternative(alt, outerMost);
    }

    @Override
    public CodeBlockForAlt alternative(Alternative alt, boolean outerMost) {
        if (outerMost) {
            return new CodeBlockForOuterMostAlt((OutputModelFactory)this, alt);
        }
        return new CodeBlockForAlt(this);
    }

    @Override
    public CodeBlockForAlt finishAlternative(CodeBlockForAlt blk, List<SrcOp> ops) {
        blk.ops = ops;
        return blk;
    }

    @Override
    public List<SrcOp> action(ActionAST ast) {
        return ParserFactory.list(new Action((OutputModelFactory)this, ast));
    }

    @Override
    public List<SrcOp> sempred(ActionAST ast) {
        return ParserFactory.list(new SemPred((OutputModelFactory)this, ast));
    }

    @Override
    public List<SrcOp> ruleRef(GrammarAST ID, GrammarAST label, GrammarAST args) {
        InvokeRule invokeOp = new InvokeRule(this, ID, label);
        if (this.controller.needsImplicitLabel(ID, invokeOp)) {
            this.defineImplicitLabel(ID, invokeOp);
        }
        AddToLabelList listLabelOp = this.getAddToListOpIfListLabelPresent(invokeOp, label);
        return ParserFactory.list(invokeOp, listLabelOp);
    }

    @Override
    public List<SrcOp> tokenRef(GrammarAST ID, GrammarAST labelAST, GrammarAST args) {
        MatchToken matchOp = new MatchToken((OutputModelFactory)this, (TerminalAST)ID);
        if (labelAST != null) {
            String label = labelAST.getText();
            RuleFunction rf = this.getCurrentRuleFunction();
            if (labelAST.parent.getType() == 46) {
                this.defineImplicitLabel(ID, matchOp);
                TokenListDecl l = this.getTokenListLabelDecl(label);
                rf.addContextDecl(ID.getAltLabel(), l);
            } else {
                Decl d = this.getTokenLabelDecl(label);
                matchOp.labels.add(d);
                rf.addContextDecl(ID.getAltLabel(), d);
            }
        }
        if (this.controller.needsImplicitLabel(ID, matchOp)) {
            this.defineImplicitLabel(ID, matchOp);
        }
        AddToLabelList listLabelOp = this.getAddToListOpIfListLabelPresent(matchOp, labelAST);
        return ParserFactory.list(matchOp, listLabelOp);
    }

    public Decl getTokenLabelDecl(String label) {
        return new TokenDecl((OutputModelFactory)this, label);
    }

    public TokenListDecl getTokenListLabelDecl(String label) {
        return new TokenListDecl((OutputModelFactory)this, this.getTarget().getListLabel(label));
    }

    @Override
    public List<SrcOp> set(GrammarAST setAST, GrammarAST labelAST, boolean invert) {
        MatchSet matchOp = invert ? new MatchNotSet((OutputModelFactory)this, setAST) : new MatchSet((OutputModelFactory)this, setAST);
        if (labelAST != null) {
            String label = labelAST.getText();
            RuleFunction rf = this.getCurrentRuleFunction();
            if (labelAST.parent.getType() == 46) {
                this.defineImplicitLabel(setAST, matchOp);
                TokenListDecl l = this.getTokenListLabelDecl(label);
                rf.addContextDecl(setAST.getAltLabel(), l);
            } else {
                Decl d = this.getTokenLabelDecl(label);
                matchOp.labels.add(d);
                rf.addContextDecl(setAST.getAltLabel(), d);
            }
        }
        if (this.controller.needsImplicitLabel(setAST, matchOp)) {
            this.defineImplicitLabel(setAST, matchOp);
        }
        AddToLabelList listLabelOp = this.getAddToListOpIfListLabelPresent(matchOp, labelAST);
        return ParserFactory.list(matchOp, listLabelOp);
    }

    @Override
    public List<SrcOp> wildcard(GrammarAST ast, GrammarAST labelAST) {
        Wildcard wild = new Wildcard((OutputModelFactory)this, ast);
        if (labelAST != null) {
            String label = labelAST.getText();
            Decl d = this.getTokenLabelDecl(label);
            wild.labels.add(d);
            this.getCurrentRuleFunction().addContextDecl(ast.getAltLabel(), d);
            if (labelAST.parent.getType() == 46) {
                TokenListDecl l = this.getTokenListLabelDecl(label);
                this.getCurrentRuleFunction().addContextDecl(ast.getAltLabel(), l);
            }
        }
        if (this.controller.needsImplicitLabel(ast, wild)) {
            this.defineImplicitLabel(ast, wild);
        }
        AddToLabelList listLabelOp = this.getAddToListOpIfListLabelPresent(wild, labelAST);
        return ParserFactory.list(wild, listLabelOp);
    }

    @Override
    public Choice getChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts, GrammarAST labelAST) {
        int decision = ((DecisionState)blkAST.atnState).decision;
        Choice c = !this.g.tool.force_atn && AnalysisPipeline.disjoint(this.g.decisionLOOK.get(decision)) ? this.getLL1ChoiceBlock(blkAST, alts) : this.getComplexChoiceBlock(blkAST, alts);
        if (labelAST != null) {
            Decl d;
            String label = labelAST.getText();
            c.label = d = this.getTokenLabelDecl(label);
            this.getCurrentRuleFunction().addContextDecl(labelAST.getAltLabel(), d);
            if (labelAST.parent.getType() == 46) {
                String listLabel = this.getTarget().getListLabel(label);
                TokenListDecl l = new TokenListDecl((OutputModelFactory)this, listLabel);
                this.getCurrentRuleFunction().addContextDecl(labelAST.getAltLabel(), l);
            }
        }
        return c;
    }

    @Override
    public Choice getEBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        int decision;
        if (!this.g.tool.force_atn && AnalysisPipeline.disjoint(this.g.decisionLOOK.get(decision = ebnfRoot.getType() == 90 ? ((PlusLoopbackState)ebnfRoot.atnState).decision : (ebnfRoot.getType() == 80 ? ((StarLoopEntryState)ebnfRoot.atnState).decision : ((DecisionState)ebnfRoot.atnState).decision)))) {
            return this.getLL1EBNFBlock(ebnfRoot, alts);
        }
        return this.getComplexEBNFBlock(ebnfRoot, alts);
    }

    @Override
    public Choice getLL1ChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts) {
        return new LL1AltBlock(this, blkAST, alts);
    }

    @Override
    public Choice getComplexChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts) {
        return new AltBlock(this, blkAST, alts);
    }

    @Override
    public Choice getLL1EBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        int ebnf = 0;
        if (ebnfRoot != null) {
            ebnf = ebnfRoot.getType();
        }
        Choice c = null;
        switch (ebnf) {
            case 89: {
                if (alts.size() == 1) {
                    c = new LL1OptionalBlockSingleAlt(this, ebnfRoot, alts);
                    break;
                }
                c = new LL1OptionalBlock(this, ebnfRoot, alts);
                break;
            }
            case 80: {
                if (alts.size() == 1) {
                    c = new LL1StarBlockSingleAlt(this, ebnfRoot, alts);
                    break;
                }
                c = this.getComplexEBNFBlock(ebnfRoot, alts);
                break;
            }
            case 90: {
                c = alts.size() == 1 ? new LL1PlusBlockSingleAlt(this, ebnfRoot, alts) : this.getComplexEBNFBlock(ebnfRoot, alts);
            }
        }
        return c;
    }

    @Override
    public Choice getComplexEBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        int ebnf = 0;
        if (ebnfRoot != null) {
            ebnf = ebnfRoot.getType();
        }
        Choice c = null;
        switch (ebnf) {
            case 89: {
                c = new OptionalBlock(this, ebnfRoot, alts);
                break;
            }
            case 80: {
                c = new StarBlock(this, ebnfRoot, alts);
                break;
            }
            case 90: {
                c = new PlusBlock(this, ebnfRoot, alts);
            }
        }
        return c;
    }

    @Override
    public List<SrcOp> getLL1Test(IntervalSet look, GrammarAST blkAST) {
        return ParserFactory.list(new TestSetInline(this, blkAST, look, this.gen.getTarget().getInlineTestSetWordSize()));
    }

    @Override
    public boolean needsImplicitLabel(GrammarAST ID, LabeledOp op) {
        Alternative currentOuterMostAlt = this.getCurrentOuterMostAlt();
        boolean actionRefsAsToken = currentOuterMostAlt.tokenRefsInActions.containsKey(ID.getText());
        boolean actionRefsAsRule = currentOuterMostAlt.ruleRefsInActions.containsKey(ID.getText());
        return op.getLabels().isEmpty() && (actionRefsAsToken || actionRefsAsRule);
    }

    public void defineImplicitLabel(GrammarAST ast, LabeledOp op) {
        Decl d;
        if (ast.getType() == 98 || ast.getType() == 100) {
            String implLabel = this.getTarget().getImplicitSetLabel(String.valueOf(ast.token.getTokenIndex()));
            d = this.getTokenLabelDecl(implLabel);
            ((TokenDecl)d).isImplicit = true;
        } else if (ast.getType() == 57) {
            Rule r = this.g.getRule(ast.getText());
            String implLabel = this.getTarget().getImplicitRuleLabel(ast.getText());
            String ctxName = this.getTarget().getRuleFunctionContextStructName(r);
            d = new RuleContextDecl(this, implLabel, ctxName);
            ((RuleContextDecl)d).isImplicit = true;
        } else {
            String implLabel = this.getTarget().getImplicitTokenLabel(ast.getText());
            d = this.getTokenLabelDecl(implLabel);
            ((TokenDecl)d).isImplicit = true;
        }
        op.getLabels().add(d);
        this.getCurrentRuleFunction().addContextDecl(ast.getAltLabel(), d);
    }

    public AddToLabelList getAddToListOpIfListLabelPresent(LabeledOp op, GrammarAST label) {
        AddToLabelList labelOp = null;
        if (label != null && label.parent.getType() == 46) {
            String listLabel = this.getTarget().getListLabel(label.getText());
            labelOp = new AddToLabelList(this, listLabel, op.getLabels().get(0));
        }
        return labelOp;
    }
}

