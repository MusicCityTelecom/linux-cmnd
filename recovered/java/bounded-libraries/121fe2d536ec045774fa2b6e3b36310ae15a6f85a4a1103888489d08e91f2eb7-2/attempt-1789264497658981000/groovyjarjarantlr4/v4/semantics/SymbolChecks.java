/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.automata.LexerATNFactory;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.semantics.SymbolCollector;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.AttributeDict;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LabelElementPair;
import groovyjarjarantlr4.v4.tool.LabelType;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SymbolChecks {
    Grammar g;
    SymbolCollector collector;
    Map<String, Rule> nameToRuleMap = new HashMap<String, Rule>();
    Set<String> tokenIDs = new HashSet<String>();
    Map<String, Set<String>> actionScopeToActionNames = new HashMap<String, Set<String>>();
    public ErrorManager errMgr;
    protected final Set<String> reservedNames = new HashSet<String>();

    public SymbolChecks(Grammar g, SymbolCollector collector) {
        this.reservedNames.addAll(LexerATNFactory.getCommonConstants());
        this.g = g;
        this.collector = collector;
        this.errMgr = g.tool.errMgr;
        for (GrammarAST tokenId : collector.tokenIDRefs) {
            this.tokenIDs.add(tokenId.getText());
        }
    }

    public void process() {
        if (this.g.rules != null) {
            for (Rule r : this.g.rules.values()) {
                this.nameToRuleMap.put(r.name, r);
            }
        }
        this.checkReservedNames(this.g.rules.values());
        this.checkActionRedefinitions(this.collector.namedActions);
        this.checkForLabelConflicts(this.g.rules.values());
    }

    public void checkActionRedefinitions(List<GrammarAST> actions) {
        if (actions == null) {
            return;
        }
        String scope = this.g.getDefaultActionScope();
        for (GrammarAST ampersandAST : actions) {
            String name;
            GrammarAST nameNode = (GrammarAST)ampersandAST.getChild(0);
            if (ampersandAST.getChildCount() == 2) {
                name = nameNode.getText();
            } else {
                scope = nameNode.getText();
                name = ampersandAST.getChild(1).getText();
            }
            Set<String> scopeActions = this.actionScopeToActionNames.get(scope);
            if (scopeActions == null) {
                scopeActions = new HashSet<String>();
                this.actionScopeToActionNames.put(scope, scopeActions);
            }
            if (!scopeActions.contains(name)) {
                scopeActions.add(name);
                continue;
            }
            this.errMgr.grammarError(ErrorType.ACTION_REDEFINITION, this.g.fileName, nameNode.token, name);
        }
    }

    public void checkForLabelConflicts(Collection<Rule> rules) {
        for (Rule r : rules) {
            this.checkForAttributeConflicts(r);
            HashMap<String, LabelElementPair> labelNameSpace = new HashMap<String, LabelElementPair>();
            for (int i = 1; i <= r.numberOfAlts; ++i) {
                Alternative a = r.alt[i];
                for (List pairs : a.labelDefs.values()) {
                    if (r.hasAltSpecificContexts()) {
                        HashMap labelPairs = new HashMap();
                        for (LabelElementPair p : pairs) {
                            List<LabelElementPair> list;
                            String labelName = this.findAltLabelName(p.label);
                            if (labelName == null) continue;
                            if (labelPairs.containsKey(labelName)) {
                                list = (List)labelPairs.get(labelName);
                            } else {
                                list = new ArrayList();
                                labelPairs.put(labelName, list);
                            }
                            list.add(p);
                        }
                        for (List internalPairs : labelPairs.values()) {
                            labelNameSpace.clear();
                            this.checkLabelPairs(r, labelNameSpace, internalPairs);
                        }
                        continue;
                    }
                    this.checkLabelPairs(r, labelNameSpace, pairs);
                }
            }
        }
    }

    private void checkLabelPairs(Rule r, Map<String, LabelElementPair> labelNameSpace, List<LabelElementPair> pairs) {
        for (LabelElementPair p : pairs) {
            this.checkForLabelConflict(r, p.label);
            String name = p.label.getText();
            LabelElementPair prev = labelNameSpace.get(name);
            if (prev == null) {
                labelNameSpace.put(name, p);
                continue;
            }
            this.checkForTypeMismatch(r, prev, p);
        }
    }

    private String findAltLabelName(CommonTree label) {
        if (label == null) {
            return null;
        }
        if (label instanceof AltAST) {
            AltAST altAST = (AltAST)label;
            if (altAST.altLabel != null) {
                return altAST.altLabel.toString();
            }
            if (altAST.leftRecursiveAltInfo != null) {
                return altAST.leftRecursiveAltInfo.altLabel.toString();
            }
            return this.findAltLabelName(label.parent);
        }
        return this.findAltLabelName(label.parent);
    }

    private void checkForTypeMismatch(Rule r, LabelElementPair prevLabelPair, LabelElementPair labelPair) {
        if (prevLabelPair.type != labelPair.type) {
            Token token = r instanceof LeftRecursiveRule ? ((GrammarAST)r.ast.getChild(0)).getToken() : labelPair.label.token;
            this.errMgr.grammarError(ErrorType.LABEL_TYPE_CONFLICT, this.g.fileName, token, labelPair.label.getText(), (Object)((Object)labelPair.type) + "!=" + (Object)((Object)prevLabelPair.type));
        }
        String prevLabelPairElementText = prevLabelPair.element.getText();
        String labelPairElementText = labelPair.element.getText();
        if (labelPair.type.equals((Object)LabelType.RULE_LABEL) || labelPair.type.equals((Object)LabelType.RULE_LIST_LABEL)) {
            if (this.g.getRule(prevLabelPairElementText) != null) {
                prevLabelPairElementText = this.g.getRule(prevLabelPairElementText).getBaseContext();
            }
            if (this.g.getRule(labelPairElementText) != null) {
                labelPairElementText = this.g.getRule(labelPairElementText).getBaseContext();
            }
        }
        if (!prevLabelPairElementText.equals(labelPairElementText) && (prevLabelPair.type.equals((Object)LabelType.RULE_LABEL) || prevLabelPair.type.equals((Object)LabelType.RULE_LIST_LABEL)) && (labelPair.type.equals((Object)LabelType.RULE_LABEL) || labelPair.type.equals((Object)LabelType.RULE_LIST_LABEL))) {
            Token token = r instanceof LeftRecursiveRule ? ((GrammarAST)r.ast.getChild(0)).getToken() : labelPair.label.token;
            String prevLabelOp = prevLabelPair.type.equals((Object)LabelType.RULE_LIST_LABEL) ? "+=" : "=";
            String labelOp = labelPair.type.equals((Object)LabelType.RULE_LIST_LABEL) ? "+=" : "=";
            this.errMgr.grammarError(ErrorType.LABEL_TYPE_CONFLICT, this.g.fileName, token, labelPair.label.getText() + labelOp + labelPairElementText, prevLabelPair.label.getText() + prevLabelOp + prevLabelPairElementText);
        }
    }

    public void checkForLabelConflict(Rule r, GrammarAST labelID) {
        ErrorType etype;
        String name = labelID.getText();
        if (this.nameToRuleMap.containsKey(name)) {
            etype = ErrorType.LABEL_CONFLICTS_WITH_RULE;
            this.errMgr.grammarError(etype, this.g.fileName, labelID.token, name, r.name);
        }
        if (this.tokenIDs.contains(name)) {
            etype = ErrorType.LABEL_CONFLICTS_WITH_TOKEN;
            this.errMgr.grammarError(etype, this.g.fileName, labelID.token, name, r.name);
        }
        if (r.args != null && r.args.get(name) != null) {
            etype = ErrorType.LABEL_CONFLICTS_WITH_ARG;
            this.errMgr.grammarError(etype, this.g.fileName, labelID.token, name, r.name);
        }
        if (r.retvals != null && r.retvals.get(name) != null) {
            etype = ErrorType.LABEL_CONFLICTS_WITH_RETVAL;
            this.errMgr.grammarError(etype, this.g.fileName, labelID.token, name, r.name);
        }
        if (r.locals != null && r.locals.get(name) != null) {
            etype = ErrorType.LABEL_CONFLICTS_WITH_LOCAL;
            this.errMgr.grammarError(etype, this.g.fileName, labelID.token, name, r.name);
        }
    }

    public void checkForAttributeConflicts(Rule r) {
        this.checkDeclarationRuleConflicts(r, r.args, this.nameToRuleMap.keySet(), ErrorType.ARG_CONFLICTS_WITH_RULE);
        this.checkDeclarationRuleConflicts(r, r.args, this.tokenIDs, ErrorType.ARG_CONFLICTS_WITH_TOKEN);
        this.checkDeclarationRuleConflicts(r, r.retvals, this.nameToRuleMap.keySet(), ErrorType.RETVAL_CONFLICTS_WITH_RULE);
        this.checkDeclarationRuleConflicts(r, r.retvals, this.tokenIDs, ErrorType.RETVAL_CONFLICTS_WITH_TOKEN);
        this.checkDeclarationRuleConflicts(r, r.locals, this.nameToRuleMap.keySet(), ErrorType.LOCAL_CONFLICTS_WITH_RULE);
        this.checkDeclarationRuleConflicts(r, r.locals, this.tokenIDs, ErrorType.LOCAL_CONFLICTS_WITH_TOKEN);
        this.checkLocalConflictingDeclarations(r, r.retvals, r.args, ErrorType.RETVAL_CONFLICTS_WITH_ARG);
        this.checkLocalConflictingDeclarations(r, r.locals, r.args, ErrorType.LOCAL_CONFLICTS_WITH_ARG);
        this.checkLocalConflictingDeclarations(r, r.locals, r.retvals, ErrorType.LOCAL_CONFLICTS_WITH_RETVAL);
    }

    protected void checkDeclarationRuleConflicts(@NotNull Rule r, @Nullable AttributeDict attributes, @NotNull Set<String> ruleNames, @NotNull ErrorType errorType) {
        if (attributes == null) {
            return;
        }
        for (Attribute attribute : attributes.attributes.values()) {
            if (!ruleNames.contains(attribute.name)) continue;
            this.errMgr.grammarError(errorType, this.g.fileName, attribute.token != null ? attribute.token : ((GrammarAST)r.ast.getChild((int)0)).token, attribute.name, r.name);
        }
    }

    protected void checkLocalConflictingDeclarations(@NotNull Rule r, @Nullable AttributeDict attributes, @Nullable AttributeDict referenceAttributes, @NotNull ErrorType errorType) {
        if (attributes == null || referenceAttributes == null) {
            return;
        }
        Set<String> conflictingKeys = attributes.intersection(referenceAttributes);
        for (String key : conflictingKeys) {
            this.errMgr.grammarError(errorType, this.g.fileName, attributes.get((String)key).token != null ? attributes.get((String)key).token : ((GrammarAST)r.ast.getChild((int)0)).token, key, r.name);
        }
    }

    protected void checkReservedNames(@NotNull Collection<Rule> rules) {
        for (Rule rule : rules) {
            if (!this.reservedNames.contains(rule.name)) continue;
            this.errMgr.grammarError(ErrorType.RESERVED_RULE_NAME, this.g.fileName, ((GrammarAST)rule.ast.getChild(0)).getToken(), rule.name);
        }
    }

    public void checkForModeConflicts(Grammar g) {
        if (g.isLexer()) {
            LexerGrammar lexerGrammar = (LexerGrammar)g;
            for (String modeName : lexerGrammar.modes.keySet()) {
                Rule rule;
                if (!modeName.equals("DEFAULT_MODE") && this.reservedNames.contains(modeName)) {
                    rule = (Rule)((List)lexerGrammar.modes.get(modeName)).iterator().next();
                    g.tool.errMgr.grammarError(ErrorType.MODE_CONFLICTS_WITH_COMMON_CONSTANTS, g.fileName, rule.ast.parent.getToken(), modeName);
                }
                if (g.getTokenType(modeName) == 0) continue;
                rule = (Rule)((List)lexerGrammar.modes.get(modeName)).iterator().next();
                g.tool.errMgr.grammarError(ErrorType.MODE_CONFLICTS_WITH_TOKEN, g.fileName, rule.ast.parent.getToken(), modeName);
            }
        }
    }

    public void checkForUnreachableTokens(Grammar g) {
        if (g.isLexer()) {
            LexerGrammar lexerGrammar = (LexerGrammar)g;
            for (List rules : lexerGrammar.modes.values()) {
                int i;
                ArrayList<Rule> stringLiteralRules = new ArrayList<Rule>();
                ArrayList<List<String>> stringLiteralValues = new ArrayList<List<String>>();
                for (i = 0; i < rules.size(); ++i) {
                    Rule rule = (Rule)rules.get(i);
                    List<String> ruleStringAlts = this.getSingleTokenValues(rule);
                    if (ruleStringAlts == null || ruleStringAlts.size() <= 0) continue;
                    stringLiteralRules.add(rule);
                    stringLiteralValues.add(ruleStringAlts);
                }
                for (i = 0; i < stringLiteralRules.size(); ++i) {
                    List firstTokenStringValues = (List)stringLiteralValues.get(i);
                    Rule rule1 = (Rule)stringLiteralRules.get(i);
                    this.checkForOverlap(g, rule1, rule1, firstTokenStringValues, (List)stringLiteralValues.get(i));
                    if (rule1.isFragment()) continue;
                    for (int j = i + 1; j < stringLiteralRules.size(); ++j) {
                        Rule rule2 = (Rule)stringLiteralRules.get(j);
                        if (rule2.isFragment()) continue;
                        this.checkForOverlap(g, rule1, (Rule)stringLiteralRules.get(j), firstTokenStringValues, (List)stringLiteralValues.get(j));
                    }
                }
            }
        }
    }

    private List<String> getSingleTokenValues(Rule rule) {
        ArrayList<String> values = new ArrayList<String>();
        for (Alternative alt : rule.alt) {
            AltAST rootNode;
            if (alt == null) continue;
            Tree tree = rootNode = alt.ast.getChildCount() == 2 && alt.ast.getChild(0) instanceof AltAST && alt.ast.getChild(1) instanceof GrammarAST ? alt.ast.getChild(0) : alt.ast;
            if (rootNode.getTokenStartIndex() == -1) continue;
            boolean ignore = false;
            StringBuilder currentValue = new StringBuilder();
            for (int i = 0; i < rootNode.getChildCount(); ++i) {
                Tree child = rootNode.getChild(i);
                if (!(child instanceof TerminalAST)) {
                    ignore = true;
                    break;
                }
                TerminalAST terminalAST = (TerminalAST)child;
                if (terminalAST.token.getType() != 62) {
                    ignore = true;
                    break;
                }
                String text = terminalAST.token.getText();
                currentValue.append(text.substring(1, text.length() - 1));
            }
            if (ignore) continue;
            values.add(currentValue.toString());
        }
        return values;
    }

    private void checkForOverlap(Grammar g, Rule rule1, Rule rule2, List<String> firstTokenStringValues, List<String> secondTokenStringValues) {
        for (int i = 0; i < firstTokenStringValues.size(); ++i) {
            int secondTokenInd = rule1 == rule2 ? i + 1 : 0;
            String str1 = firstTokenStringValues.get(i);
            for (int j = secondTokenInd; j < secondTokenStringValues.size(); ++j) {
                String str2 = secondTokenStringValues.get(j);
                if (!str1.equals(str2)) continue;
                this.errMgr.grammarError(ErrorType.TOKEN_UNREACHABLE, g.fileName, ((GrammarAST)rule2.ast.getChild((int)0)).token, rule2.name, str2, rule1.name);
            }
        }
    }

    public void checkRuleArgs(Grammar g, List<GrammarAST> rulerefs) {
        if (rulerefs == null) {
            return;
        }
        for (GrammarAST ref : rulerefs) {
            String ruleName = ref.getText();
            Rule r = g.getRule(ruleName);
            GrammarAST arg = (GrammarAST)ref.getFirstChildWithType(8);
            if (arg != null && (r == null || r.args == null)) {
                this.errMgr.grammarError(ErrorType.RULE_HAS_NO_ARGS, g.fileName, ref.token, ruleName);
                continue;
            }
            if (arg != null || r == null || r.args == null) continue;
            this.errMgr.grammarError(ErrorType.MISSING_RULE_ARGS, g.fileName, ref.token, ruleName);
        }
    }

    public void checkForQualifiedRuleIssues(Grammar g, List<GrammarAST> qualifiedRuleRefs) {
        for (GrammarAST dot : qualifiedRuleRefs) {
            GrammarAST grammar = (GrammarAST)dot.getChild(0);
            GrammarAST rule = (GrammarAST)dot.getChild(1);
            g.tool.log("semantics", grammar.getText() + "." + rule.getText());
            Grammar delegate = g.getImportedGrammar(grammar.getText());
            if (delegate == null) {
                this.errMgr.grammarError(ErrorType.NO_SUCH_GRAMMAR_SCOPE, g.fileName, grammar.token, grammar.getText(), rule.getText());
                continue;
            }
            if (g.getRule(grammar.getText(), rule.getText()) != null) continue;
            this.errMgr.grammarError(ErrorType.NO_SUCH_RULE_IN_SCOPE, g.fileName, rule.token, grammar.getText(), rule.getText());
        }
    }
}

