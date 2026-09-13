/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CommonTokenStream;
import groovyjarjarantlr4.runtime.ParserRuleReturnScope;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAltInfo;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAnalyzer;
import groovyjarjarantlr4.v4.misc.OrderedHashMap;
import groovyjarjarantlr4.v4.parse.ANTLRLexer;
import groovyjarjarantlr4.v4.parse.ANTLRParser;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.parse.ScopeParser;
import groovyjarjarantlr4.v4.parse.ToolANTLRParser;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.semantics.BasicSemanticChecks;
import groovyjarjarantlr4.v4.semantics.RuleCollector;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.GrammarTransformPipeline;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.Collection;

public class LeftRecursiveRuleTransformer {
    public static final String PRECEDENCE_OPTION_NAME = "p";
    public static final String TOKENINDEX_OPTION_NAME = "tokenIndex";
    public GrammarRootAST ast;
    public Collection<Rule> rules;
    public Grammar g;
    public Tool tool;

    public LeftRecursiveRuleTransformer(GrammarRootAST ast, Collection<Rule> rules, Grammar g) {
        this.ast = ast;
        this.rules = rules;
        this.g = g;
        this.tool = g.tool;
    }

    public void translateLeftRecursiveRules() {
        String language = this.g.getOptionString("language");
        ArrayList<String> leftRecursiveRuleNames = new ArrayList<String>();
        for (Rule rule : this.rules) {
            boolean fitsPattern;
            if (Grammar.isTokenName(rule.name) || !LeftRecursiveRuleAnalyzer.hasImmediateRecursiveRuleRefs(rule.ast, rule.name) || !(fitsPattern = this.translateLeftRecursiveRule(this.ast, (LeftRecursiveRule)rule, language))) continue;
            leftRecursiveRuleNames.add(rule.name);
        }
        for (GrammarAST grammarAST : this.ast.getNodesWithType(57)) {
            if (grammarAST.getParent().getType() == 94 || ((GrammarASTWithOptions)grammarAST).getOptionString(PRECEDENCE_OPTION_NAME) != null || !leftRecursiveRuleNames.contains(grammarAST.getText())) continue;
            ((GrammarASTWithOptions)grammarAST).setOption(PRECEDENCE_OPTION_NAME, new GrammarASTAdaptor().create(30, "0"));
        }
    }

    public boolean translateLeftRecursiveRule(GrammarRootAST ast, LeftRecursiveRule r, String language) {
        boolean isLeftRec;
        RuleAST prevRuleAST = r.ast;
        String ruleName = prevRuleAST.getChild(0).getText();
        LeftRecursiveRuleAnalyzer leftRecursiveRuleWalker = new LeftRecursiveRuleAnalyzer(prevRuleAST, this.tool, ruleName, language);
        try {
            isLeftRec = leftRecursiveRuleWalker.rec_rule();
        }
        catch (RecognitionException re) {
            isLeftRec = false;
        }
        if (!isLeftRec) {
            return false;
        }
        r.setBaseContext(r.getBaseContext());
        GrammarAST RULES = (GrammarAST)ast.getFirstChildWithType(97);
        String newRuleText = leftRecursiveRuleWalker.getArtificialOpPrecRule();
        RuleAST t = this.parseArtificialRule(prevRuleAST.g, newRuleText);
        ((GrammarAST)t.getChild((int)0)).token = ((GrammarAST)prevRuleAST.getChild(0)).getToken();
        RULES.setChild(prevRuleAST.getChildIndex(), t);
        r.ast = t;
        GrammarTransformPipeline transform = new GrammarTransformPipeline(this.g, this.g.tool);
        transform.reduceBlocksToSets(r.ast);
        transform.expandParameterizedLoops(r.ast);
        RuleCollector ruleCollector = new RuleCollector(this.g);
        ruleCollector.visit(t, "rule");
        BasicSemanticChecks basics = new BasicSemanticChecks(this.g, ruleCollector);
        basics.checkAssocElementOption = false;
        basics.visit(t, "rule");
        r.recPrimaryAlts = new ArrayList<LeftRecursiveRuleAltInfo>();
        r.recPrimaryAlts.addAll(leftRecursiveRuleWalker.prefixAndOtherAlts);
        if (r.recPrimaryAlts.isEmpty()) {
            this.tool.errMgr.grammarError(ErrorType.NO_NON_LR_ALTS, this.g.fileName, ((GrammarAST)r.ast.getChild(0)).getToken(), r.name);
        }
        r.recOpAlts = new OrderedHashMap();
        r.recOpAlts.putAll(leftRecursiveRuleWalker.binaryAlts);
        r.recOpAlts.putAll(leftRecursiveRuleWalker.ternaryAlts);
        r.recOpAlts.putAll(leftRecursiveRuleWalker.suffixAlts);
        this.setAltASTPointers(r, t);
        ActionAST arg = (ActionAST)r.ast.getFirstChildWithType(8);
        if (arg != null) {
            r.args = ScopeParser.parseTypedArgList(arg, arg.getText(), this.g);
            r.args.type = AttributeDict.DictType.ARG;
            r.args.ast = arg;
            arg.resolver = r.alt[1];
        }
        for (Tuple2<GrammarAST, String> pair : leftRecursiveRuleWalker.leftRecursiveRuleRefLabels) {
            GrammarAST labelNode = pair.getItem1();
            GrammarAST labelOpNode = (GrammarAST)labelNode.getParent();
            GrammarAST elementNode = (GrammarAST)labelOpNode.getChild(1);
            LabelElementPair lp = new LabelElementPair(this.g, labelNode, elementNode, labelOpNode.getType());
            r.alt[1].labelDefs.map(labelNode.getText(), lp);
        }
        r.leftRecursiveRuleRefLabels = leftRecursiveRuleWalker.leftRecursiveRuleRefLabels;
        this.tool.log("grammar", "added: " + t.toStringTree());
        return true;
    }

    public RuleAST parseArtificialRule(Grammar g, String ruleText) {
        CommonTokenStream tokens;
        ANTLRLexer lexer = new ANTLRLexer(new ANTLRStringStream(ruleText));
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(lexer.getCharStream());
        lexer.tokens = tokens = new CommonTokenStream(lexer);
        ToolANTLRParser p = new ToolANTLRParser((TokenStream)tokens, this.tool);
        p.setTreeAdaptor(adaptor);
        Token ruleStart = null;
        try {
            ANTLRParser.rule_return r = p.rule();
            RuleAST tree = (RuleAST)((ParserRuleReturnScope)r).getTree();
            ruleStart = (Token)r.getStart();
            GrammarTransformPipeline.setGrammarPtr(g, tree);
            GrammarTransformPipeline.augmentTokensWithOriginalPosition(g, tree);
            return tree;
        }
        catch (Exception e) {
            this.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, e, ruleStart, "error parsing rule created during left-recursion detection: " + ruleText);
            return null;
        }
    }

    public void setAltASTPointers(LeftRecursiveRule r, RuleAST t) {
        LeftRecursiveRuleAltInfo altInfo;
        int i;
        BlockAST ruleBlk = (BlockAST)t.getFirstChildWithType(78);
        AltAST mainAlt = (AltAST)ruleBlk.getChild(0);
        BlockAST primaryBlk = (BlockAST)mainAlt.getChild(0);
        BlockAST opsBlk = (BlockAST)mainAlt.getChild(1).getChild(0);
        for (i = 0; i < r.recPrimaryAlts.size(); ++i) {
            altInfo = r.recPrimaryAlts.get(i);
            altInfo.altAST = (AltAST)primaryBlk.getChild(i);
            altInfo.altAST.leftRecursiveAltInfo = altInfo;
            altInfo.originalAltAST.leftRecursiveAltInfo = altInfo;
        }
        for (i = 0; i < r.recOpAlts.size(); ++i) {
            altInfo = r.recOpAlts.getElement(i);
            altInfo.altAST = (AltAST)opsBlk.getChild(i);
            altInfo.altAST.leftRecursiveAltInfo = altInfo;
            altInfo.originalAltAST.leftRecursiveAltInfo = altInfo;
        }
    }
}

