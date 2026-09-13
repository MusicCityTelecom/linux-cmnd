/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.misc.MultiMap
 */
package groovyjarjarantlr4.v4.semantics;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.semantics.RuleCollector;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.RuleRefAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.stringtemplate.v4.misc.MultiMap;

public class BasicSemanticChecks
extends GrammarTreeVisitor {
    public static MultiMap<Integer, Integer> validImportTypes = new MultiMap<Integer, Integer>(){
        {
            this.map(31, 31);
            this.map(31, 81);
            this.map(44, 44);
            this.map(44, 81);
            this.map(81, 81);
        }
    };
    public Grammar g;
    public RuleCollector ruleCollector;
    public ErrorManager errMgr;
    public boolean checkAssocElementOption = true;
    protected int nonFragmentRuleCount;
    private boolean inFragmentRule;

    public BasicSemanticChecks(Grammar g, RuleCollector ruleCollector) {
        this.g = g;
        this.ruleCollector = ruleCollector;
        this.errMgr = g.tool.errMgr;
    }

    @Override
    public ErrorManager getErrorManager() {
        return this.errMgr;
    }

    public void process() {
        this.visitGrammar(this.g.ast);
    }

    @Override
    public void discoverGrammar(GrammarRootAST root, GrammarAST ID) {
        this.checkGrammarName(ID.token);
    }

    @Override
    public void finishPrequels(GrammarAST firstPrequel) {
        if (firstPrequel == null) {
            return;
        }
        GrammarAST parent = (GrammarAST)firstPrequel.parent;
        List<GrammarAST> options = parent.getAllChildrenWithType(42);
        List<GrammarAST> imports = parent.getAllChildrenWithType(29);
        List<GrammarAST> tokens = parent.getAllChildrenWithType(65);
        this.checkNumPrequels(options, imports, tokens);
    }

    @Override
    public void importGrammar(GrammarAST label, GrammarAST ID) {
        this.checkImport(ID.token);
    }

    @Override
    public void discoverRules(GrammarAST rules) {
        this.checkNumRules(rules);
    }

    @Override
    protected void enterMode(GrammarAST tree) {
        this.nonFragmentRuleCount = 0;
    }

    @Override
    protected void exitMode(GrammarAST tree) {
        if (this.nonFragmentRuleCount == 0) {
            Token token = tree.getToken();
            String name = "?";
            if (tree.getChildCount() > 0) {
                name = tree.getChild(0).getText();
                if (name == null || name.isEmpty()) {
                    name = "?";
                }
                token = ((GrammarAST)tree.getChild(0)).getToken();
            }
            this.g.tool.errMgr.grammarError(ErrorType.MODE_WITHOUT_RULES, this.g.fileName, token, name, this.g);
        }
    }

    @Override
    public void modeDef(GrammarAST m, GrammarAST ID) {
        if (!this.g.isLexer()) {
            this.g.tool.errMgr.grammarError(ErrorType.MODE_NOT_IN_LEXER, this.g.fileName, ID.token, ID.token.getText(), this.g);
        }
    }

    @Override
    public void discoverRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, ActionAST arg, ActionAST returns, GrammarAST thrws, GrammarAST options, ActionAST locals, List<GrammarAST> actions, GrammarAST block) {
        this.checkInvalidRuleDef(ID.token);
    }

    @Override
    public void discoverLexerRule(RuleAST rule, GrammarAST ID, List<GrammarAST> modifiers, GrammarAST block) {
        this.checkInvalidRuleDef(ID.token);
        if (modifiers != null) {
            for (GrammarAST tree : modifiers) {
                if (tree.getType() != 24) continue;
                this.inFragmentRule = true;
            }
        }
        if (!this.inFragmentRule) {
            ++this.nonFragmentRuleCount;
        }
    }

    @Override
    protected void exitLexerRule(GrammarAST tree) {
        this.inFragmentRule = false;
    }

    @Override
    public void ruleRef(GrammarAST ref, ActionAST arg) {
        this.checkInvalidRuleRef(ref.token);
    }

    @Override
    public void ruleOption(GrammarAST ID, GrammarAST valueAST) {
        this.checkOptions((GrammarAST)ID.getAncestor(94), ID.token, valueAST);
    }

    @Override
    public void blockOption(GrammarAST ID, GrammarAST valueAST) {
        this.checkOptions((GrammarAST)ID.getAncestor(78), ID.token, valueAST);
    }

    @Override
    public void grammarOption(GrammarAST ID, GrammarAST valueAST) {
        boolean ok = this.checkOptions(this.g.ast, ID.token, valueAST);
    }

    @Override
    public void defineToken(GrammarAST ID) {
        this.checkTokenDefinition(ID.token);
    }

    @Override
    protected void enterChannelsSpec(GrammarAST tree) {
        if (this.g.isParser()) {
            this.g.tool.errMgr.grammarError(ErrorType.CHANNELS_BLOCK_IN_PARSER_GRAMMAR, this.g.fileName, tree.token, new Object[0]);
        } else if (this.g.isCombined()) {
            this.g.tool.errMgr.grammarError(ErrorType.CHANNELS_BLOCK_IN_COMBINED_GRAMMAR, this.g.fileName, tree.token, new Object[0]);
        }
    }

    @Override
    public void defineChannel(GrammarAST ID) {
        this.checkChannelDefinition(ID.token);
    }

    @Override
    public void elementOption(GrammarASTWithOptions elem, GrammarAST ID, GrammarAST valueAST) {
        Object v = null;
        boolean ok = this.checkElementOptions(elem, ID, valueAST);
    }

    @Override
    public void finishGrammar(GrammarRootAST root, GrammarAST ID) {
        MultiMap baseContexts = new MultiMap();
        for (Rule r : this.ruleCollector.rules.values()) {
            Token errorToken;
            GrammarAST optionAST = r.ast.getOptionAST("baseContext");
            if (r.ast.isLexerRule()) {
                if (optionAST == null) continue;
                errorToken = optionAST.getToken();
                this.g.tool.errMgr.grammarError(ErrorType.LEXER_RULE_CANNOT_HAVE_BASE_CONTEXT, this.g.fileName, errorToken, r.name);
                continue;
            }
            baseContexts.map((Object)r.getBaseContext(), (Object)r);
            if (optionAST != null) {
                boolean targetSpecifiesBaseContext;
                Rule targetRule = (Rule)this.ruleCollector.rules.get(r.getBaseContext());
                boolean bl = targetSpecifiesBaseContext = targetRule != null && targetRule.ast != null && (targetRule.ast.getOptionAST("baseContext") != null || !targetRule.name.equals(targetRule.getBaseContext()));
                if (targetSpecifiesBaseContext) {
                    Token errorToken2 = optionAST.getToken();
                    this.g.tool.errMgr.grammarError(ErrorType.BASE_CONTEXT_CANNOT_BE_TRANSITIVE, this.g.fileName, errorToken2, r.name);
                }
            }
            if (this.ruleCollector.rules.containsKey(r.getBaseContext())) continue;
            errorToken = optionAST != null ? optionAST.getToken() : ((CommonTree)r.ast.getChild(0)).getToken();
            this.g.tool.errMgr.grammarError(ErrorType.BASE_CONTEXT_MUST_BE_RULE_NAME, this.g.fileName, errorToken, r.name);
        }
        for (Map.Entry entry : baseContexts.entrySet()) {
            boolean suppressError = false;
            int altLabelCount = 0;
            int outerAltCount = 0;
            for (Rule rule : (List)entry.getValue()) {
                outerAltCount += rule.numberOfAlts;
                List altLabels = (List)this.ruleCollector.ruleToAltLabels.get((Object)rule.name);
                if (altLabels == null || altLabels.isEmpty()) continue;
                if (altLabels.size() != rule.numberOfAlts) {
                    suppressError = true;
                    break;
                }
                altLabelCount += altLabels.size();
            }
            if (suppressError || altLabelCount == 0 || altLabelCount == outerAltCount) continue;
            Rule errorRule = (Rule)((List)entry.getValue()).get(0);
            this.g.tool.errMgr.grammarError(ErrorType.RULE_WITH_TOO_FEW_ALT_LABELS_GROUP, this.g.fileName, ((CommonTree)errorRule.ast.getChild(0)).getToken(), errorRule.name);
        }
    }

    @Override
    public void finishRule(RuleAST rule, GrammarAST ID, GrammarAST block) {
        if (rule.isLexerRule()) {
            return;
        }
        BlockAST blk = (BlockAST)rule.getFirstChildWithType(78);
        int nalts = blk.getChildCount();
        GrammarAST idAST = (GrammarAST)rule.getChild(0);
        for (int i = 0; i < nalts; ++i) {
            String currentContextForLabel;
            String prevContextForLabel;
            String prevRuleForLabel;
            AltAST altAST = (AltAST)blk.getChild(i);
            if (altAST.altLabel == null) continue;
            String altLabel = altAST.altLabel.getText();
            Rule r = (Rule)this.ruleCollector.rules.get(Utils.decapitalize(altLabel));
            if (r != null) {
                this.g.tool.errMgr.grammarError(ErrorType.ALT_LABEL_CONFLICTS_WITH_RULE, this.g.fileName, altAST.altLabel.token, altLabel, r.name);
            }
            if ((prevRuleForLabel = this.ruleCollector.altLabelToRuleName.get(altLabel)) == null || (prevContextForLabel = ((Rule)this.ruleCollector.rules.get(prevRuleForLabel)).getBaseContext()).equals(currentContextForLabel = ((Rule)this.ruleCollector.rules.get(rule.getRuleName())).getBaseContext())) continue;
            this.g.tool.errMgr.grammarError(ErrorType.ALT_LABEL_REDEF, this.g.fileName, altAST.altLabel.token, altLabel, rule.getRuleName(), prevRuleForLabel);
        }
        List altLabels = (List)this.ruleCollector.ruleToAltLabels.get((Object)rule.getRuleName());
        int numAltLabels = 0;
        if (altLabels != null) {
            numAltLabels = altLabels.size();
        }
        if (numAltLabels > 0 && nalts != numAltLabels) {
            this.g.tool.errMgr.grammarError(ErrorType.RULE_WITH_TOO_FEW_ALT_LABELS, this.g.fileName, idAST.token, rule.getRuleName());
        }
    }

    void checkGrammarName(Token nameToken) {
        String fullyQualifiedName = nameToken.getInputStream().getSourceName();
        if (fullyQualifiedName == null) {
            return;
        }
        File f = new File(fullyQualifiedName);
        String fileName = f.getName();
        if (this.g.originalGrammar != null) {
            return;
        }
        if (!Utils.stripFileExtension(fileName).equals(nameToken.getText()) && !fileName.equals("<string>")) {
            this.g.tool.errMgr.grammarError(ErrorType.FILE_AND_GRAMMAR_NAME_DIFFER, fileName, nameToken, nameToken.getText(), fileName);
        }
    }

    void checkNumRules(GrammarAST rulesNode) {
        if (rulesNode.getChildCount() == 0) {
            GrammarAST root = (GrammarAST)rulesNode.getParent();
            GrammarAST IDNode = (GrammarAST)root.getChild(0);
            this.g.tool.errMgr.grammarError(ErrorType.NO_RULES, this.g.fileName, null, IDNode.getText(), this.g);
        }
    }

    void checkNumPrequels(List<GrammarAST> options, List<GrammarAST> imports, List<GrammarAST> tokens) {
        ArrayList<Token> secondOptionTokens = new ArrayList<Token>();
        if (options != null && options.size() > 1) {
            secondOptionTokens.add(options.get((int)1).token);
        }
        if (imports != null && imports.size() > 1) {
            secondOptionTokens.add(imports.get((int)1).token);
        }
        if (tokens != null && tokens.size() > 1) {
            secondOptionTokens.add(tokens.get((int)1).token);
        }
        for (Token t : secondOptionTokens) {
            String fileName = t.getInputStream().getSourceName();
            this.g.tool.errMgr.grammarError(ErrorType.REPEATED_PREQUEL, fileName, t, new Object[0]);
        }
    }

    void checkInvalidRuleDef(Token ruleID) {
        String fileName = null;
        if (ruleID.getInputStream() != null) {
            fileName = ruleID.getInputStream().getSourceName();
        }
        if (this.g.isLexer() && Character.isLowerCase(ruleID.getText().charAt(0))) {
            this.g.tool.errMgr.grammarError(ErrorType.PARSER_RULES_NOT_ALLOWED, fileName, ruleID, ruleID.getText());
        }
        if (this.g.isParser() && Grammar.isTokenName(ruleID.getText())) {
            this.g.tool.errMgr.grammarError(ErrorType.LEXER_RULES_NOT_ALLOWED, fileName, ruleID, ruleID.getText());
        }
    }

    void checkInvalidRuleRef(Token ruleID) {
        String fileName = ruleID.getInputStream().getSourceName();
        if (this.g.isLexer() && Character.isLowerCase(ruleID.getText().charAt(0))) {
            this.g.tool.errMgr.grammarError(ErrorType.PARSER_RULE_REF_IN_LEXER_RULE, fileName, ruleID, ruleID.getText(), this.currentRuleName);
        }
    }

    void checkTokenDefinition(Token tokenID) {
        String fileName = tokenID.getInputStream().getSourceName();
        if (!Grammar.isTokenName(tokenID.getText())) {
            this.g.tool.errMgr.grammarError(ErrorType.TOKEN_NAMES_MUST_START_UPPER, fileName, tokenID, tokenID.getText());
        }
    }

    void checkChannelDefinition(Token tokenID) {
    }

    @Override
    protected void enterLexerElement(GrammarAST tree) {
    }

    @Override
    protected void enterLexerCommand(GrammarAST tree) {
        this.checkElementIsOuterMostInSingleAlt(tree);
        if (this.inFragmentRule) {
            String fileName = tree.token.getInputStream().getSourceName();
            String ruleName = this.currentRuleName;
            this.g.tool.errMgr.grammarError(ErrorType.FRAGMENT_ACTION_IGNORED, fileName, tree.token, ruleName);
        }
    }

    @Override
    public void actionInAlt(ActionAST action) {
        if (this.inFragmentRule) {
            String fileName = action.token.getInputStream().getSourceName();
            String ruleName = this.currentRuleName;
            this.g.tool.errMgr.grammarError(ErrorType.FRAGMENT_ACTION_IGNORED, fileName, action.token, ruleName);
        }
    }

    protected void checkElementIsOuterMostInSingleAlt(GrammarAST tree) {
        CommonTree alt = tree.parent;
        CommonTree blk = alt.parent;
        boolean outerMostAlt = blk.parent.getType() == 94;
        Tree rule = tree.getAncestor(94);
        String fileName = tree.getToken().getInputStream().getSourceName();
        if (!outerMostAlt || blk.getChildCount() > 1) {
            ErrorType e = ErrorType.LEXER_COMMAND_PLACEMENT_ISSUE;
            this.g.tool.errMgr.grammarError(e, fileName, tree.getToken(), rule.getChild(0).getText());
        }
    }

    @Override
    public void label(GrammarAST op, GrammarAST ID, GrammarAST element) {
        switch (element.getType()) {
            case 39: 
            case 52: 
            case 57: 
            case 62: 
            case 66: 
            case 98: 
            case 100: {
                return;
            }
        }
        String fileName = ID.token.getInputStream().getSourceName();
        this.g.tool.errMgr.grammarError(ErrorType.LABEL_BLOCK_NOT_A_SET, fileName, ID.token, ID.getText());
    }

    @Override
    protected void enterLabeledLexerElement(GrammarAST tree) {
        Token label = ((GrammarAST)tree.getChild(0)).getToken();
        this.g.tool.errMgr.grammarError(ErrorType.V3_LEXER_LABEL, this.g.fileName, label, label.getText());
    }

    @Override
    protected void enterTerminal(GrammarAST tree) {
        String text = tree.getText();
        if (text.equals("''")) {
            this.g.tool.errMgr.grammarError(ErrorType.EMPTY_STRINGS_AND_SETS_NOT_ALLOWED, this.g.fileName, tree.token, "''");
        }
    }

    boolean checkOptions(GrammarAST parent, Token optionID, GrammarAST valueAST) {
        boolean ok = true;
        if (parent.getType() == 78) {
            if (this.g.isLexer() && !Grammar.LexerBlockOptions.contains(optionID.getText())) {
                this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, this.g.fileName, optionID, optionID.getText());
                ok = false;
            }
            if (!this.g.isLexer() && !Grammar.ParserBlockOptions.contains(optionID.getText())) {
                this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, this.g.fileName, optionID, optionID.getText());
                ok = false;
            }
        } else if (parent.getType() == 94) {
            if (!Grammar.ruleOptions.contains(optionID.getText())) {
                this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, this.g.fileName, optionID, optionID.getText());
                ok = false;
            }
        } else if (parent.getType() == 25 && !this.legalGrammarOption(optionID.getText())) {
            this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, this.g.fileName, optionID, optionID.getText());
            ok = false;
        }
        return ok;
    }

    boolean checkElementOptions(GrammarASTWithOptions elem, GrammarAST ID, GrammarAST valueAST) {
        String fileName;
        Token optionID;
        if (this.checkAssocElementOption && ID != null && "assoc".equals(ID.getText()) && elem.getType() != 74) {
            optionID = ID.token;
            fileName = optionID.getInputStream().getSourceName();
            this.g.tool.errMgr.grammarError(ErrorType.UNRECOGNIZED_ASSOC_OPTION, fileName, optionID, this.currentRuleName);
        }
        if (elem instanceof RuleRefAST) {
            return this.checkRuleRefOptions((RuleRefAST)elem, ID, valueAST);
        }
        if (elem instanceof TerminalAST) {
            return this.checkTokenOptions((TerminalAST)elem, ID, valueAST);
        }
        if (elem.getType() == 4) {
            return false;
        }
        if (elem.getType() == 59) {
            optionID = ID.token;
            fileName = optionID.getInputStream().getSourceName();
            if (valueAST != null && !Grammar.semPredOptions.contains(optionID.getText())) {
                this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, fileName, optionID, optionID.getText());
                return false;
            }
        }
        return false;
    }

    boolean checkRuleRefOptions(RuleRefAST elem, GrammarAST ID, GrammarAST valueAST) {
        Token optionID = ID.token;
        String fileName = optionID.getInputStream().getSourceName();
        if (valueAST != null && !Grammar.ruleRefOptions.contains(optionID.getText())) {
            this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, fileName, optionID, optionID.getText());
            return false;
        }
        return true;
    }

    boolean checkTokenOptions(TerminalAST elem, GrammarAST ID, GrammarAST valueAST) {
        Token optionID = ID.token;
        String fileName = optionID.getInputStream().getSourceName();
        if (valueAST != null && !Grammar.tokenOptions.contains(optionID.getText())) {
            this.g.tool.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, fileName, optionID, optionID.getText());
            return false;
        }
        return true;
    }

    boolean legalGrammarOption(String key) {
        switch (this.g.getType()) {
            case 31: {
                return Grammar.lexerOptions.contains(key);
            }
            case 44: {
                return Grammar.parserOptions.contains(key);
            }
        }
        return Grammar.parserOptions.contains(key);
    }

    void checkImport(Token importID) {
        Grammar delegate = this.g.getImportedGrammar(importID.getText());
        if (delegate == null) {
            return;
        }
        List validDelegators = (List)validImportTypes.get((Object)delegate.getType());
        if (validDelegators != null && !validDelegators.contains(this.g.getType())) {
            this.g.tool.errMgr.grammarError(ErrorType.INVALID_IMPORT, this.g.fileName, importID, this.g, delegate);
        }
        if (this.g.isCombined() && (delegate.name.equals(this.g.name + Grammar.getGrammarTypeToFileNameSuffix(31)) || delegate.name.equals(this.g.name + Grammar.getGrammarTypeToFileNameSuffix(44)))) {
            this.g.tool.errMgr.grammarError(ErrorType.IMPORT_NAME_CLASH, this.g.fileName, importID, this.g, delegate);
        }
    }
}

