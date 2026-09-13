/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STGroupFile
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAltInfo;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.parse.LeftRecursiveRuleWalker;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.RuleRefAST;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class LeftRecursiveRuleAnalyzer
extends LeftRecursiveRuleWalker {
    public Tool tool;
    public String ruleName;
    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> binaryAlts = new LinkedHashMap();
    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> ternaryAlts = new LinkedHashMap();
    public LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> suffixAlts = new LinkedHashMap();
    public List<LeftRecursiveRuleAltInfo> prefixAndOtherAlts = new ArrayList<LeftRecursiveRuleAltInfo>();
    public List<Tuple2<GrammarAST, String>> leftRecursiveRuleRefLabels = new ArrayList<Tuple2<GrammarAST, String>>();
    public final TokenStream tokenStream;
    public GrammarAST retvals;
    @NotNull
    public STGroup recRuleTemplates;
    @NotNull
    public STGroup codegenTemplates;
    public String language;
    public Map<Integer, ASSOC> altAssociativity = new HashMap<Integer, ASSOC>();

    public LeftRecursiveRuleAnalyzer(GrammarAST ruleAST, Tool tool, String ruleName, String language) {
        super(new CommonTreeNodeStream(new GrammarASTAdaptor(ruleAST.token.getInputStream()), ruleAST));
        this.tool = tool;
        this.ruleName = ruleName;
        this.language = language;
        this.tokenStream = ruleAST.g.tokenStream;
        if (this.tokenStream == null) {
            throw new NullPointerException("grammar must have a token stream");
        }
        this.loadPrecRuleTemplates();
    }

    public void loadPrecRuleTemplates() {
        CodeGenerator gen;
        STGroup templates;
        String templateGroupFile = "groovyjarjarantlr4/v4/tool/templates/LeftRecursiveRules.stg";
        this.recRuleTemplates = new STGroupFile(templateGroupFile);
        if (!this.recRuleTemplates.isDefined("recRule")) {
            this.tool.errMgr.toolError(ErrorType.MISSING_CODE_GEN_TEMPLATES, "LeftRecursiveRules");
        }
        if ((templates = (gen = new CodeGenerator(this.tool, null, this.language)).getTemplates()) == null) {
            templates = new CodeGenerator(this.tool, null, "Java").getTemplates();
            assert (templates != null);
        }
        this.codegenTemplates = templates;
    }

    @Override
    public void setReturnValues(GrammarAST t) {
        this.retvals = t;
    }

    @Override
    public void setAltAssoc(AltAST t, int alt) {
        String a;
        ASSOC assoc = ASSOC.left;
        if (t.getOptions() != null && (a = t.getOptionString("assoc")) != null) {
            if (a.equals(ASSOC.right.toString())) {
                assoc = ASSOC.right;
            } else if (a.equals(ASSOC.left.toString())) {
                assoc = ASSOC.left;
            } else {
                this.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION_VALUE, t.g.fileName, t.getOptionAST("assoc").getToken(), new Object[]{"assoc", assoc});
            }
        }
        if (this.altAssociativity.get(alt) != null && this.altAssociativity.get(alt) != assoc) {
            this.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, "all operators of alt " + alt + " of left-recursive rule must have same associativity");
        }
        this.altAssociativity.put(alt, assoc);
    }

    @Override
    public void binaryAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST)originalAltTree.dupTree();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        String label = null;
        boolean isListLabel = false;
        GrammarAST lrlabel = this.stripLeftRecursion(altTree);
        if (lrlabel != null) {
            label = lrlabel.getText();
            isListLabel = lrlabel.getParent().getType() == 46;
            this.leftRecursiveRuleRefLabels.add(Tuple.create(lrlabel, altLabel));
        }
        this.stripAltLabel(altTree);
        int nextPrec = this.nextPrecedence(alt);
        altTree = this.addPrecedenceArgToRules(altTree, nextPrec);
        this.stripAltLabel(altTree);
        String altText = this.text(altTree);
        altText = altText.trim();
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, label, altLabel, isListLabel, originalAltTree);
        a.nextPrec = nextPrec;
        this.binaryAlts.put(alt, a);
    }

    @Override
    public void prefixAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST)originalAltTree.dupTree();
        this.stripAltLabel(altTree);
        int nextPrec = this.precedence(alt);
        altTree = this.addPrecedenceArgToRules(altTree, nextPrec);
        String altText = this.text(altTree);
        altText = altText.trim();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, null, altLabel, false, originalAltTree);
        a.nextPrec = nextPrec;
        this.prefixAndOtherAlts.add(a);
    }

    @Override
    public void suffixAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST)originalAltTree.dupTree();
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        String label = null;
        boolean isListLabel = false;
        GrammarAST lrlabel = this.stripLeftRecursion(altTree);
        if (lrlabel != null) {
            label = lrlabel.getText();
            isListLabel = lrlabel.getParent().getType() == 46;
            this.leftRecursiveRuleRefLabels.add(Tuple.create(lrlabel, altLabel));
        }
        this.stripAltLabel(altTree);
        String altText = this.text(altTree);
        altText = altText.trim();
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, label, altLabel, isListLabel, originalAltTree);
        this.suffixAlts.put(alt, a);
    }

    @Override
    public void otherAlt(AltAST originalAltTree, int alt) {
        AltAST altTree = (AltAST)originalAltTree.dupTree();
        this.stripAltLabel(altTree);
        String altText = this.text(altTree);
        String altLabel = altTree.altLabel != null ? altTree.altLabel.getText() : null;
        LeftRecursiveRuleAltInfo a = new LeftRecursiveRuleAltInfo(alt, altText, null, altLabel, false, originalAltTree);
        this.prefixAndOtherAlts.add(a);
    }

    public String getArtificialOpPrecRule() {
        ST ruleST = this.recRuleTemplates.getInstanceOf("recRule");
        ruleST.add("ruleName", (Object)this.ruleName);
        ST ruleArgST = this.codegenTemplates.getInstanceOf("recRuleArg");
        ruleST.add("argName", (Object)ruleArgST);
        ST setResultST = this.codegenTemplates.getInstanceOf("recRuleSetResultAction");
        ruleST.add("setResultAction", (Object)setResultST);
        ruleST.add("userRetvals", (Object)this.retvals);
        LinkedHashMap<Integer, LeftRecursiveRuleAltInfo> opPrecRuleAlts = new LinkedHashMap<Integer, LeftRecursiveRuleAltInfo>();
        opPrecRuleAlts.putAll(this.binaryAlts);
        opPrecRuleAlts.putAll(this.ternaryAlts);
        opPrecRuleAlts.putAll(this.suffixAlts);
        Iterator i$ = opPrecRuleAlts.keySet().iterator();
        while (i$.hasNext()) {
            int alt = (Integer)i$.next();
            LeftRecursiveRuleAltInfo altInfo = (LeftRecursiveRuleAltInfo)opPrecRuleAlts.get(alt);
            ST altST = this.recRuleTemplates.getInstanceOf("recRuleAlt");
            ST predST = this.codegenTemplates.getInstanceOf("recRuleAltPredicate");
            predST.add("opPrec", (Object)this.precedence(alt));
            predST.add("ruleName", (Object)this.ruleName);
            altST.add("pred", (Object)predST);
            altST.add("alt", (Object)altInfo);
            altST.add("precOption", (Object)"p");
            altST.add("opPrec", (Object)this.precedence(alt));
            ruleST.add("opAlts", (Object)altST);
        }
        ruleST.add("primaryAlts", this.prefixAndOtherAlts);
        this.tool.log("left-recursion", ruleST.render());
        return ruleST.render();
    }

    public AltAST addPrecedenceArgToRules(AltAST t, int prec) {
        if (t == null) {
            return null;
        }
        List<GrammarAST> outerAltRuleRefs = t.getNodesWithTypePreorderDFS(IntervalSet.of(57));
        for (GrammarAST x : outerAltRuleRefs) {
            boolean rightmost;
            RuleRefAST rref = (RuleRefAST)x;
            boolean recursive = rref.getText().equals(this.ruleName);
            boolean bl = rightmost = rref == outerAltRuleRefs.get(outerAltRuleRefs.size() - 1);
            if (!recursive || !rightmost) continue;
            GrammarAST dummyValueNode = new GrammarAST(new CommonToken(30, "" + prec));
            rref.setOption("p", dummyValueNode);
        }
        return t;
    }

    public static boolean hasImmediateRecursiveRuleRefs(GrammarAST t, String ruleName) {
        if (t == null) {
            return false;
        }
        GrammarAST blk = (GrammarAST)t.getFirstChildWithType(78);
        if (blk == null) {
            return false;
        }
        int n = blk.getChildren().size();
        for (int i = 0; i < n; ++i) {
            GrammarAST alt = (GrammarAST)blk.getChildren().get(i);
            Tree first = alt.getChild(0);
            if (first == null || first.getType() == 82 && (first = alt.getChild(1)) == null) continue;
            if (first.getType() == 57 && first.getText().equals(ruleName)) {
                return true;
            }
            Tree rref = first.getChild(1);
            if (rref == null || rref.getType() != 57 || !rref.getText().equals(ruleName)) continue;
            return true;
        }
        return false;
    }

    public GrammarAST stripLeftRecursion(GrammarAST altAST) {
        GrammarAST lrlabel = null;
        GrammarAST first = (GrammarAST)altAST.getChild(0);
        int leftRecurRuleIndex = 0;
        if (first.getType() == 82) {
            first = (GrammarAST)altAST.getChild(1);
            leftRecurRuleIndex = 1;
        }
        Tree rref = first.getChild(1);
        if (first.getType() == 57 && first.getText().equals(this.ruleName) || rref != null && rref.getType() == 57 && rref.getText().equals(this.ruleName)) {
            if (first.getType() == 10 || first.getType() == 46) {
                lrlabel = (GrammarAST)first.getChild(0);
            }
            altAST.deleteChild(leftRecurRuleIndex);
            GrammarAST newFirstChild = (GrammarAST)altAST.getChild(leftRecurRuleIndex);
            altAST.setTokenStartIndex(newFirstChild.getTokenStartIndex());
        }
        return lrlabel;
    }

    public void stripAltLabel(GrammarAST altAST) {
        int stop;
        int start = altAST.getTokenStartIndex();
        for (int i = stop = altAST.getTokenStopIndex(); i >= start; --i) {
            if (this.tokenStream.get(i).getType() != 47) continue;
            altAST.setTokenStopIndex(i - 1);
            return;
        }
    }

    public String text(GrammarAST t) {
        if (t == null) {
            return "";
        }
        int tokenStartIndex = t.getTokenStartIndex();
        int tokenStopIndex = t.getTokenStopIndex();
        IntervalSet ignore = new IntervalSet(new int[0]);
        List<GrammarAST> optionsSubTrees = t.getNodesWithType(82);
        for (GrammarAST sub : optionsSubTrees) {
            ignore.add(sub.getTokenStartIndex(), sub.getTokenStopIndex());
        }
        IntervalSet noOptions = new IntervalSet(new int[0]);
        List<GrammarAST> labeledSubTrees = t.getNodesWithType(new IntervalSet(10, 46));
        for (GrammarAST sub : labeledSubTrees) {
            noOptions.add(sub.getChild(0).getTokenStartIndex());
        }
        StringBuilder buf = new StringBuilder();
        int i = tokenStartIndex;
        while (i <= tokenStopIndex) {
            if (ignore.contains(i)) {
                ++i;
                continue;
            }
            Token tok = this.tokenStream.get(i);
            StringBuilder elementOptions = new StringBuilder();
            if (!noOptions.contains(i)) {
                GrammarAST node = t.getNodeWithTokenIndex(tok.getTokenIndex());
                if (node != null && (tok.getType() == 66 || tok.getType() == 62 || tok.getType() == 57)) {
                    elementOptions.append("tokenIndex=").append(tok.getTokenIndex());
                }
                if (node instanceof GrammarASTWithOptions) {
                    GrammarASTWithOptions o = (GrammarASTWithOptions)node;
                    for (Map.Entry<String, GrammarAST> entry : o.getOptions().entrySet()) {
                        if (elementOptions.length() > 0) {
                            elementOptions.append(',');
                        }
                        elementOptions.append(entry.getKey());
                        elementOptions.append('=');
                        elementOptions.append(entry.getValue().getText());
                    }
                }
            }
            buf.append(tok.getText());
            if (tok.getType() == 57 && ++i <= tokenStopIndex && this.tokenStream.get(i).getType() == 8) {
                buf.append('[' + this.tokenStream.get(i).getText() + ']');
                ++i;
            }
            if (elementOptions.length() <= 0) continue;
            buf.append('<').append((CharSequence)elementOptions).append('>');
        }
        return buf.toString();
    }

    public int precedence(int alt) {
        return this.numAlts - alt + 1;
    }

    public int nextPrecedence(int alt) {
        int p = this.precedence(alt);
        if (this.altAssociativity.get(alt) == ASSOC.right) {
            return p;
        }
        return p + 1;
    }

    public String toString() {
        return "PrecRuleOperatorCollector{binaryAlts=" + this.binaryAlts + ", ternaryAlts=" + this.ternaryAlts + ", suffixAlts=" + this.suffixAlts + ", prefixAndOtherAlts=" + this.prefixAndOtherAlts + '}';
    }

    public static enum ASSOC {
        left,
        right;

    }
}

