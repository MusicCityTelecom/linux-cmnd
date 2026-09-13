/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.PlusBlockAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.RuleRefAST;
import groovyjarjarantlr4.v4.tool.ast.StarBlockAST;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeftFactoringRuleTransformer {
    public static final String LEFTFACTOR = "leftfactor";
    public static final String SUPPRESS_ACCESSOR = "suppressAccessor";
    private static final Logger LOGGER = Logger.getLogger(LeftFactoringRuleTransformer.class.getName());
    public GrammarRootAST _ast;
    public Map<String, Rule> _rules;
    public Grammar _g;
    public Tool _tool;
    private final GrammarASTAdaptor adaptor = new GrammarASTAdaptor();

    public LeftFactoringRuleTransformer(@NotNull GrammarRootAST ast, @NotNull Map<String, Rule> rules, @NotNull Grammar g) {
        this._ast = ast;
        this._rules = rules;
        this._g = g;
        this._tool = g.tool;
    }

    public void translateLeftFactoredRules() {
        for (Rule r : this._rules.values()) {
            ActionAST leftFactoredRules;
            if (Grammar.isTokenName(r.name) || (leftFactoredRules = r.namedActions.get(LEFTFACTOR)) == null || !(leftFactoredRules instanceof ActionAST)) continue;
            String leftFactoredRuleAction = ((Object)leftFactoredRules).toString();
            Object[] rules = (leftFactoredRuleAction = leftFactoredRuleAction.substring(1, leftFactoredRuleAction.length() - 1)).split(",\\s*");
            if (rules.length == 0) continue;
            LOGGER.log(Level.FINE, "Left factoring {0} out of alts in grammar rule {1}", new Object[]{Arrays.toString(rules), r.name});
            HashSet<GrammarAST> translatedBlocks = new HashSet<GrammarAST>();
            List<GrammarAST> blocks = r.ast.getNodesWithType(78);
            block1: for (GrammarAST block : blocks) {
                for (GrammarAST current = (GrammarAST)block.getParent(); current != null; current = (GrammarAST)current.getAncestor(78)) {
                    if (translatedBlocks.contains(current)) continue block1;
                }
                if (rules.length != 1) {
                    throw new UnsupportedOperationException("Chained left factoring is not yet implemented.");
                }
                if (!this.translateLeftFactoredDecision(block, (String)rules[0], false, DecisionFactorMode.COMBINED_FACTOR, true)) continue;
                translatedBlocks.add(block);
            }
        }
    }

    protected boolean expandOptionalQuantifiersForBlock(GrammarAST block, boolean variant) {
        ArrayList<GrammarAST> children = new ArrayList<GrammarAST>();
        for (int i = 0; i < block.getChildCount(); ++i) {
            GrammarAST child = (GrammarAST)block.getChild(i);
            if (child.getType() != 74) {
                children.add(child);
                continue;
            }
            GrammarAST expandedAlt = this.expandOptionalQuantifiersForAlt(child);
            if (expandedAlt == null) {
                return false;
            }
            children.add(expandedAlt);
        }
        GrammarAST newChildren = this.adaptor.nil();
        newChildren.addChildren(children);
        block.replaceChildren(0, block.getChildCount() - 1, newChildren);
        block.freshenParentAndChildIndexesDeeply();
        if (!variant && block.getParent() instanceof RuleAST) {
            RuleAST ruleAST = (RuleAST)block.getParent();
            String ruleName = ruleAST.getChild(0).getText();
            Rule r = this._rules.get(ruleName);
            List<GrammarAST> blockAlts = block.getAllChildrenWithType(74);
            r.numberOfAlts = blockAlts.size();
            r.alt = new Alternative[blockAlts.size() + 1];
            for (int i = 0; i < blockAlts.size(); ++i) {
                r.alt[i + 1] = new Alternative(r, i + 1);
                r.alt[i + 1].ast = (AltAST)blockAlts.get(i);
            }
        }
        return true;
    }

    protected GrammarAST expandOptionalQuantifiersForAlt(GrammarAST alt) {
        if (alt.getChildCount() == 0) {
            return null;
        }
        if (alt.getChild(0).getType() == 89) {
            GrammarAST root = this.adaptor.nil();
            GrammarAST alt2 = alt.dupTree();
            alt2.deleteChild(0);
            if (alt2.getChildCount() == 0) {
                this.adaptor.addChild(alt2, this.adaptor.create(83, "EPSILON"));
            }
            alt.setChild(0, alt.getChild(0).getChild(0));
            if (alt.getChild(0).getType() == 78 && alt.getChild(0).getChildCount() == 1 && alt.getChild(0).getChild(0).getType() == 74) {
                GrammarAST list = this.adaptor.nil();
                for (Object object : ((GrammarAST)alt.getChild(0).getChild(0)).getChildren()) {
                    this.adaptor.addChild(list, object);
                }
                this.adaptor.replaceChildren(alt, 0, 0, list);
            }
            this.adaptor.addChild(root, alt);
            this.adaptor.addChild(root, alt2);
            return root;
        }
        if (alt.getChild(0).getType() == 80) {
            GrammarAST root = this.adaptor.nil();
            GrammarAST alt2 = alt.dupTree();
            alt2.deleteChild(0);
            if (alt2.getChildCount() == 0) {
                this.adaptor.addChild(alt2, this.adaptor.create(83, "EPSILON"));
            }
            PlusBlockAST plusBlockAST = new PlusBlockAST(90, this.adaptor.createToken(90, "+"), null);
            for (int i = 0; i < alt.getChild(0).getChildCount(); ++i) {
                plusBlockAST.addChild(alt.getChild(0).getChild(i));
            }
            alt.setChild(0, plusBlockAST);
            this.adaptor.addChild(root, alt);
            this.adaptor.addChild(root, alt2);
            return root;
        }
        return alt;
    }

    protected boolean translateLeftFactoredDecision(GrammarAST block, String factoredRule, boolean variant, DecisionFactorMode mode, boolean includeFactoredElement) {
        GrammarAST translatedAlt;
        int i;
        if (mode == DecisionFactorMode.PARTIAL_UNFACTORED && includeFactoredElement) {
            throw new IllegalArgumentException("Cannot include the factored element in unfactored alternatives.");
        }
        if (mode == DecisionFactorMode.COMBINED_FACTOR && !includeFactoredElement) {
            throw new IllegalArgumentException("Cannot return a combined answer without the factored element.");
        }
        if (!this.expandOptionalQuantifiersForBlock(block, variant)) {
            return false;
        }
        List<GrammarAST> alternatives = block.getAllChildrenWithType(74);
        GrammarAST[] factoredAlternatives = new GrammarAST[alternatives.size()];
        GrammarAST[] unfactoredAlternatives = new GrammarAST[alternatives.size()];
        IntervalSet factoredIntervals = new IntervalSet(new int[0]);
        IntervalSet unfactoredIntervals = new IntervalSet(new int[0]);
        for (i = 0; i < alternatives.size(); ++i) {
            GrammarAST factoredAlt;
            GrammarAST alternative = alternatives.get(i);
            if (mode.includeUnfactoredAlts()) {
                GrammarAST unfactoredAlt;
                unfactoredAlternatives[i] = unfactoredAlt = this.translateLeftFactoredAlternative(alternative.dupTree(), factoredRule, variant, DecisionFactorMode.PARTIAL_UNFACTORED, false);
                if (unfactoredAlt != null) {
                    unfactoredIntervals.add(i);
                }
            }
            if (!mode.includeFactoredAlts()) continue;
            factoredAlternatives[i] = factoredAlt = this.translateLeftFactoredAlternative(alternative, factoredRule, variant, mode == DecisionFactorMode.COMBINED_FACTOR ? DecisionFactorMode.PARTIAL_FACTORED : DecisionFactorMode.FULL_FACTOR, includeFactoredElement);
            if (factoredAlt == null) continue;
            factoredIntervals.add(alternative.getChildIndex());
        }
        if (factoredIntervals.isNil() && !mode.includeUnfactoredAlts()) {
            return false;
        }
        if (unfactoredIntervals.isNil() && !mode.includeFactoredAlts()) {
            return false;
        }
        if (unfactoredIntervals.isNil() && factoredIntervals.size() == alternatives.size() && mode.includeFactoredAlts() && !includeFactoredElement) {
            for (i = 0; i < factoredAlternatives.length; ++i) {
                translatedAlt = factoredAlternatives[i];
                if (translatedAlt.getChildCount() == 0) {
                    this.adaptor.addChild(translatedAlt, this.adaptor.create(83, "EPSILON"));
                }
                this.adaptor.setChild(block, i, translatedAlt);
            }
            return true;
        }
        if (factoredIntervals.isNil() && unfactoredIntervals.size() == alternatives.size() && mode.includeUnfactoredAlts()) {
            for (i = 0; i < unfactoredAlternatives.length; ++i) {
                translatedAlt = unfactoredAlternatives[i];
                if (translatedAlt.getChildCount() == 0) {
                    this.adaptor.addChild(translatedAlt, this.adaptor.create(83, "EPSILON"));
                }
                this.adaptor.setChild(block, i, translatedAlt);
            }
            return true;
        }
        if (mode == DecisionFactorMode.FULL_FACTOR) {
            return false;
        }
        GrammarAST newChildren = this.adaptor.nil();
        for (int i2 = 0; i2 < alternatives.size(); ++i2) {
            if (mode.includeFactoredAlts() && factoredIntervals.contains(i2)) {
                GrammarAST translatedAlt2;
                boolean combineWithPrevious;
                boolean bl = combineWithPrevious = i2 > 0 && factoredIntervals.contains(i2 - 1) && (!mode.includeUnfactoredAlts() || !unfactoredIntervals.contains(i2 - 1));
                if (combineWithPrevious) {
                    AltAST newAlt;
                    BlockAST newBlock;
                    translatedAlt2 = factoredAlternatives[i2];
                    if (translatedAlt2.getChildCount() == 0) {
                        this.adaptor.addChild(translatedAlt2, this.adaptor.create(83, "EPSILON"));
                    }
                    GrammarAST previous = (GrammarAST)newChildren.getChild(newChildren.getChildCount() - 1);
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, previous.toStringTree());
                        LOGGER.log(Level.FINE, translatedAlt2.toStringTree());
                    }
                    if (previous.getChildCount() == 1 || previous.getChild(1).getType() != 78) {
                        newBlock = new BlockAST(this.adaptor.createToken(78, "BLOCK"));
                        newAlt = new AltAST(this.adaptor.createToken(74, "ALT"));
                        this.adaptor.addChild(newBlock, newAlt);
                        while (previous.getChildCount() > 1) {
                            this.adaptor.addChild(newAlt, previous.deleteChild(1));
                        }
                        if (newAlt.getChildCount() == 0) {
                            this.adaptor.addChild(newAlt, this.adaptor.create(83, "EPSILON"));
                        }
                        this.adaptor.addChild(previous, newBlock);
                    }
                    if (translatedAlt2.getChildCount() == 1 || translatedAlt2.getChild(1).getType() != 78) {
                        newBlock = new BlockAST(this.adaptor.createToken(78, "BLOCK"));
                        newAlt = new AltAST(this.adaptor.createToken(74, "ALT"));
                        this.adaptor.addChild(newBlock, newAlt);
                        while (translatedAlt2.getChildCount() > 1) {
                            this.adaptor.addChild(newAlt, translatedAlt2.deleteChild(1));
                        }
                        if (newAlt.getChildCount() == 0) {
                            this.adaptor.addChild(newAlt, this.adaptor.create(83, "EPSILON"));
                        }
                        this.adaptor.addChild(translatedAlt2, newBlock);
                    }
                    GrammarAST combinedBlock = (GrammarAST)previous.getChild(1);
                    this.adaptor.addChild(combinedBlock, translatedAlt2.getChild(1).getChild(0));
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, previous.toStringTree());
                    }
                } else {
                    translatedAlt2 = factoredAlternatives[i2];
                    if (translatedAlt2.getChildCount() == 0) {
                        this.adaptor.addChild(translatedAlt2, this.adaptor.create(83, "EPSILON"));
                    }
                    this.adaptor.addChild(newChildren, translatedAlt2);
                }
            }
            if (!mode.includeUnfactoredAlts() || !unfactoredIntervals.contains(i2)) continue;
            GrammarAST translatedAlt3 = unfactoredAlternatives[i2];
            if (translatedAlt3.getChildCount() == 0) {
                this.adaptor.addChild(translatedAlt3, this.adaptor.create(83, "EPSILON"));
            }
            this.adaptor.addChild(newChildren, translatedAlt3);
        }
        this.adaptor.replaceChildren(block, 0, block.getChildCount() - 1, newChildren);
        if (!variant && block.getParent() instanceof RuleAST) {
            RuleAST ruleAST = (RuleAST)block.getParent();
            String ruleName = ruleAST.getChild(0).getText();
            Rule r = this._rules.get(ruleName);
            List<GrammarAST> blockAlts = block.getAllChildrenWithType(74);
            r.numberOfAlts = blockAlts.size();
            r.alt = new Alternative[blockAlts.size() + 1];
            for (int i3 = 0; i3 < blockAlts.size(); ++i3) {
                r.alt[i3 + 1] = new Alternative(r, i3 + 1);
                r.alt[i3 + 1].ast = (AltAST)blockAlts.get(i3);
            }
        }
        return true;
    }

    protected GrammarAST translateLeftFactoredAlternative(GrammarAST alternative, String factoredRule, boolean variant, DecisionFactorMode mode, boolean includeFactoredElement) {
        if (mode == DecisionFactorMode.PARTIAL_UNFACTORED && includeFactoredElement) {
            throw new IllegalArgumentException("Cannot include the factored element in unfactored alternatives.");
        }
        if (mode == DecisionFactorMode.COMBINED_FACTOR && !includeFactoredElement) {
            throw new IllegalArgumentException("Cannot return a combined answer without the factored element.");
        }
        assert (alternative.getChildCount() > 0);
        if (alternative.getChild(0).getType() == 83) {
            if (mode == DecisionFactorMode.PARTIAL_UNFACTORED) {
                return alternative;
            }
            return null;
        }
        GrammarAST translatedElement = this.translateLeftFactoredElement((GrammarAST)alternative.getChild(0), factoredRule, variant, mode, includeFactoredElement);
        if (translatedElement == null) {
            return null;
        }
        alternative.replaceChildren(0, 0, translatedElement);
        if (alternative.getChildCount() == 0) {
            this.adaptor.addChild(alternative, this.adaptor.create(83, "EPSILON"));
        }
        assert (alternative.getChildCount() > 0);
        return alternative;
    }

    protected GrammarAST translateLeftFactoredElement(GrammarAST element, String factoredRule, boolean variant, DecisionFactorMode mode, boolean includeFactoredElement) {
        if (mode == DecisionFactorMode.PARTIAL_UNFACTORED && includeFactoredElement) {
            throw new IllegalArgumentException("Cannot include the factored element in unfactored alternatives.");
        }
        if (mode == DecisionFactorMode.COMBINED_FACTOR) {
            throw new UnsupportedOperationException("Cannot return a combined answer.");
        }
        assert (!mode.includeFactoredAlts() || !mode.includeUnfactoredAlts());
        switch (element.getType()) {
            case 10: 
            case 46: {
                GrammarAST translatedChildElement = this.translateLeftFactoredElement((GrammarAST)element.getChild(1), factoredRule, variant, mode, includeFactoredElement);
                if (translatedChildElement == null) {
                    return null;
                }
                RuleAST ruleAST = (RuleAST)element.getAncestor(94);
                LOGGER.log(Level.WARNING, "Could not left factor ''{0}'' out of decision in rule ''{1}'': labeled rule references are not yet supported.", new Object[]{factoredRule, ruleAST.getChild(0).getText()});
                return null;
            }
            case 57: {
                if (factoredRule.equals(element.getToken().getText())) {
                    if (!mode.includeFactoredAlts()) {
                        return null;
                    }
                    if (includeFactoredElement) {
                        return element;
                    }
                    GrammarAST root = this.adaptor.nil();
                    root.addChild(this.adaptor.create(-2, "EPSILON"));
                    root.deleteChild(0);
                    return root;
                }
                Rule targetRule = this._rules.get(element.getToken().getText());
                if (targetRule == null) {
                    return null;
                }
                RuleVariants ruleVariants = this.createLeftFactoredRuleVariant(targetRule, factoredRule);
                switch (ruleVariants) {
                    case NONE: {
                        if (!mode.includeUnfactoredAlts()) {
                            return null;
                        }
                        return element;
                    }
                    case FULLY_FACTORED: {
                        if (mode.includeFactoredAlts()) break;
                        return null;
                    }
                    case PARTIALLY_FACTORED: {
                        break;
                    }
                    default: {
                        throw new IllegalStateException();
                    }
                }
                String marker = mode.includeFactoredAlts() ? "$lf$" : "$nolf$";
                element.setText(element.getText() + marker + factoredRule);
                GrammarAST root = this.adaptor.nil();
                if (includeFactoredElement) {
                    assert (mode.includeFactoredAlts());
                    RuleRefAST factoredRuleRef = new RuleRefAST(this.adaptor.createToken(57, factoredRule));
                    factoredRuleRef.setOption(SUPPRESS_ACCESSOR, this.adaptor.create(28, "true"));
                    Rule factoredRuleDef = this._rules.get(factoredRule);
                    if (factoredRuleDef instanceof LeftRecursiveRule) {
                        factoredRuleRef.setOption("p", this.adaptor.create(30, "0"));
                    }
                    if (this._rules.get((Object)factoredRule).args != null && this._rules.get((Object)factoredRule).args.size() > 0) {
                        throw new UnsupportedOperationException("Cannot left-factor rules with arguments yet.");
                    }
                    this.adaptor.addChild(root, factoredRuleRef);
                }
                this.adaptor.addChild(root, element);
                return root;
            }
            case 78: {
                GrammarAST cloned = element.dupTree();
                if (!this.translateLeftFactoredDecision(cloned, factoredRule, variant, mode, includeFactoredElement)) {
                    return null;
                }
                if (cloned.getChildCount() != 1) {
                    return null;
                }
                GrammarAST root = this.adaptor.nil();
                for (int i = 0; i < cloned.getChild(0).getChildCount(); ++i) {
                    this.adaptor.addChild(root, cloned.getChild(0).getChild(i));
                }
                return root;
            }
            case 90: {
                GrammarAST originalChildElement = (GrammarAST)element.getChild(0);
                GrammarAST translatedElement = this.translateLeftFactoredElement(originalChildElement.dupTree(), factoredRule, variant, mode, includeFactoredElement);
                if (translatedElement == null) {
                    return null;
                }
                StarBlockAST closure = new StarBlockAST(80, this.adaptor.createToken(80, "CLOSURE"), null);
                this.adaptor.addChild(closure, originalChildElement);
                GrammarAST root = this.adaptor.nil();
                if (mode.includeFactoredAlts() && includeFactoredElement) {
                    Object factoredElement = this.adaptor.deleteChild(translatedElement, 0);
                    this.adaptor.addChild(root, factoredElement);
                }
                this.adaptor.addChild(root, translatedElement);
                this.adaptor.addChild(root, closure);
                return root;
            }
            case 80: 
            case 89: {
                if (mode.includeUnfactoredAlts()) {
                    return element;
                }
                return null;
            }
            case 20: {
                if (mode.includeUnfactoredAlts()) {
                    return element;
                }
                return null;
            }
            case 4: 
            case 59: {
                if (mode.includeUnfactoredAlts()) {
                    return element;
                }
                return null;
            }
            case 39: 
            case 62: 
            case 66: 
            case 100: {
                if (mode.includeUnfactoredAlts()) {
                    return element;
                }
                return null;
            }
            case 83: {
                if (mode.includeUnfactoredAlts()) {
                    return element;
                }
                return null;
            }
        }
        return null;
    }

    protected RuleVariants createLeftFactoredRuleVariant(Rule rule, String factoredElement) {
        int i;
        RuleAST ast = (RuleAST)rule.ast.dupTree();
        BlockAST block = (BlockAST)ast.getFirstChildWithType(78);
        RuleAST unfactoredAst = null;
        BlockAST unfactoredBlock = null;
        if (!this.translateLeftFactoredDecision(block, factoredElement, true, DecisionFactorMode.FULL_FACTOR, false)) {
            ast = (RuleAST)rule.ast.dupTree();
            block = (BlockAST)ast.getFirstChildWithType(78);
            if (!this.translateLeftFactoredDecision(block, factoredElement, true, DecisionFactorMode.PARTIAL_FACTORED, false)) {
                return RuleVariants.NONE;
            }
            unfactoredAst = (RuleAST)rule.ast.dupTree();
            unfactoredBlock = (BlockAST)unfactoredAst.getFirstChildWithType(78);
            if (!this.translateLeftFactoredDecision(unfactoredBlock, factoredElement, true, DecisionFactorMode.PARTIAL_UNFACTORED, false)) {
                throw new IllegalStateException("expected unfactored alts for partial factorization");
            }
        }
        String variantName = ast.getChild(0).getText() + "$lf$" + factoredElement;
        ((GrammarAST)ast.getChild((int)0)).token = this.adaptor.createToken(ast.getChild(0).getType(), variantName);
        GrammarAST ruleParent = (GrammarAST)rule.ast.getParent();
        ruleParent.insertChild(rule.ast.getChildIndex() + 1, ast);
        ruleParent.freshenParentAndChildIndexes(rule.ast.getChildIndex());
        List<GrammarAST> alts = block.getAllChildrenWithType(74);
        Rule variant = new Rule(this._g, ast.getChild(0).getText(), ast, alts.size());
        this._g.defineRule(variant);
        for (i = 0; i < alts.size(); ++i) {
            variant.alt[i + 1].ast = (AltAST)alts.get(i);
        }
        if (unfactoredAst != null) {
            variantName = unfactoredAst.getChild(0).getText() + "$nolf$" + factoredElement;
            ((GrammarAST)unfactoredAst.getChild((int)0)).token = this.adaptor.createToken(unfactoredAst.getChild(0).getType(), variantName);
            ruleParent = (GrammarAST)rule.ast.getParent();
            ruleParent.insertChild(rule.ast.getChildIndex() + 1, unfactoredAst);
            ruleParent.freshenParentAndChildIndexes(rule.ast.getChildIndex());
            alts = unfactoredBlock.getAllChildrenWithType(74);
            variant = new Rule(this._g, unfactoredAst.getChild(0).getText(), unfactoredAst, alts.size());
            this._g.defineRule(variant);
            for (i = 0; i < alts.size(); ++i) {
                variant.alt[i + 1].ast = (AltAST)alts.get(i);
            }
        }
        return unfactoredAst == null ? RuleVariants.FULLY_FACTORED : RuleVariants.PARTIALLY_FACTORED;
    }

    protected static enum RuleVariants {
        NONE,
        PARTIALLY_FACTORED,
        FULLY_FACTORED;

    }

    protected static enum DecisionFactorMode {
        COMBINED_FACTOR(true, true),
        FULL_FACTOR(true, false),
        PARTIAL_FACTORED(true, false),
        PARTIAL_UNFACTORED(false, true);

        private final boolean includeFactoredAlts;
        private final boolean includeUnfactoredAlts;

        private DecisionFactorMode(boolean includeFactoredAlts, boolean includeUnfactoredAlts) {
            this.includeFactoredAlts = includeFactoredAlts;
            this.includeUnfactoredAlts = includeUnfactoredAlts;
        }

        public boolean includeFactoredAlts() {
            return this.includeFactoredAlts;
        }

        public boolean includeUnfactoredAlts() {
            return this.includeUnfactoredAlts;
        }
    }
}

