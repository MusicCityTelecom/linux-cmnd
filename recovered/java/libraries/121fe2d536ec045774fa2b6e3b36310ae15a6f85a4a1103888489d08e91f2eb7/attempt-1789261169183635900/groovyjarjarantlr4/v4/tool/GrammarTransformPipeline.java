/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeVisitor;
import groovyjarjarantlr4.runtime.tree.TreeVisitorAction;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.parse.BlockSetTransformer;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.parse.GrammarToken;
import groovyjarjarantlr4.v4.runtime.misc.DoubleKeyMap;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrammarTransformPipeline {
    public Grammar g;
    public Tool tool;

    public GrammarTransformPipeline(Grammar g, Tool tool) {
        this.g = g;
        this.tool = tool;
    }

    public void process() {
        GrammarRootAST root = this.g.ast;
        if (root == null) {
            return;
        }
        this.tool.log("grammar", "before: " + root.toStringTree());
        this.integrateImportedGrammars(this.g);
        this.reduceBlocksToSets(root);
        this.expandParameterizedLoops(root);
        this.tool.log("grammar", "after: " + root.toStringTree());
    }

    public void reduceBlocksToSets(GrammarAST root) {
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(new GrammarASTAdaptor(), root);
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor();
        BlockSetTransformer transformer = new BlockSetTransformer((TreeNodeStream)nodes, this.g);
        transformer.setTreeAdaptor(adaptor);
        transformer.downup(root);
    }

    public void expandParameterizedLoops(GrammarAST root) {
        TreeVisitor v = new TreeVisitor(new GrammarASTAdaptor());
        v.visit(root, new TreeVisitorAction(){

            @Override
            public Object pre(Object t) {
                if (((GrammarAST)t).getType() == 3) {
                    return GrammarTransformPipeline.this.expandParameterizedLoop((GrammarAST)t);
                }
                return t;
            }

            @Override
            public Object post(Object t) {
                return t;
            }
        });
    }

    public GrammarAST expandParameterizedLoop(GrammarAST t) {
        return t;
    }

    public static void setGrammarPtr(final Grammar g, GrammarAST tree) {
        if (tree == null) {
            return;
        }
        TreeVisitor v = new TreeVisitor(new GrammarASTAdaptor());
        v.visit(tree, new TreeVisitorAction(){

            @Override
            public Object pre(Object t) {
                ((GrammarAST)t).g = g;
                return t;
            }

            @Override
            public Object post(Object t) {
                return t;
            }
        });
    }

    public static void augmentTokensWithOriginalPosition(Grammar g, GrammarAST tree) {
        if (tree == null) {
            return;
        }
        List<GrammarAST> optionsSubTrees = tree.getNodesWithType(82);
        for (int i = 0; i < optionsSubTrees.size(); ++i) {
            Map<String, GrammarAST> options;
            GrammarAST t = optionsSubTrees.get(i);
            CommonTree elWithOpt = t.parent;
            if (!(elWithOpt instanceof GrammarASTWithOptions) || !(options = ((GrammarASTWithOptions)elWithOpt).getOptions()).containsKey("tokenIndex")) continue;
            GrammarToken newTok = new GrammarToken(g, elWithOpt.getToken());
            newTok.originalTokenIndex = Integer.valueOf(options.get("tokenIndex").getText());
            elWithOpt.token = newTok;
            GrammarAST originalNode = g.ast.getNodeWithTokenIndex(newTok.getTokenIndex());
            if (originalNode != null) {
                elWithOpt.setTokenStartIndex(originalNode.getTokenStartIndex());
                elWithOpt.setTokenStopIndex(originalNode.getTokenStopIndex());
                continue;
            }
            elWithOpt.setTokenStartIndex(newTok.getTokenIndex());
            elWithOpt.setTokenStopIndex(newTok.getTokenIndex());
        }
    }

    public void integrateImportedGrammars(Grammar rootGrammar) {
        List<Grammar> imports = rootGrammar.getAllImportedGrammars();
        if (imports == null) {
            return;
        }
        GrammarRootAST root = rootGrammar.ast;
        GrammarAST id = (GrammarAST)root.getChild(0);
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(id.token.getInputStream());
        GrammarAST channelsRoot = (GrammarAST)root.getFirstChildWithType(13);
        GrammarAST tokensRoot = (GrammarAST)root.getFirstChildWithType(65);
        List<GrammarAST> actionRoots = root.getNodesWithType(11);
        GrammarAST RULES = (GrammarAST)root.getFirstChildWithType(97);
        HashSet<String> rootRuleNames = new HashSet<String>();
        List<GrammarAST> rootRules = RULES.getNodesWithType(94);
        for (GrammarAST r : rootRules) {
            rootRuleNames.add(r.getChild(0).getText());
        }
        List<GrammarAST> rootModes = root.getNodesWithType(36);
        HashSet<String> rootModeNames = new HashSet<String>();
        for (GrammarAST m : rootModes) {
            rootModeNames.add(m.getChild(0).getText());
        }
        ArrayList addedModes = new ArrayList();
        for (Grammar imp : imports) {
            GrammarAST optionsRoot;
            List<GrammarAST> rules;
            List<GrammarAST> modes;
            GrammarAST imp_tokensRoot;
            GrammarAST imp_channelRoot = (GrammarAST)imp.ast.getFirstChildWithType(13);
            if (imp_channelRoot != null) {
                rootGrammar.tool.log("grammar", "imported channels: " + imp_channelRoot.getChildren());
                if (channelsRoot == null) {
                    channelsRoot = imp_channelRoot.dupTree();
                    channelsRoot.g = rootGrammar;
                    root.insertChild(1, channelsRoot);
                } else {
                    for (int c = 0; c < imp_channelRoot.getChildCount(); ++c) {
                        String channel = imp_channelRoot.getChild(c).getText();
                        boolean channelIsInRootGrammar = false;
                        for (int rc = 0; rc < channelsRoot.getChildCount(); ++rc) {
                            String rootChannel = channelsRoot.getChild(rc).getText();
                            if (!rootChannel.equals(channel)) continue;
                            channelIsInRootGrammar = true;
                            break;
                        }
                        if (channelIsInRootGrammar) continue;
                        channelsRoot.addChild(imp_channelRoot.getChild(c).dupNode());
                    }
                }
            }
            if ((imp_tokensRoot = (GrammarAST)imp.ast.getFirstChildWithType(65)) != null) {
                rootGrammar.tool.log("grammar", "imported tokens: " + imp_tokensRoot.getChildren());
                if (tokensRoot == null) {
                    tokensRoot = adaptor.create(65, "TOKENS");
                    tokensRoot.g = rootGrammar;
                    root.insertChild(1, tokensRoot);
                }
                tokensRoot.addChildren(Arrays.asList(imp_tokensRoot.getChildren().toArray(new Tree[0])));
            }
            ArrayList<GrammarAST> all_actionRoots = new ArrayList<GrammarAST>();
            List<GrammarAST> imp_actionRoots = imp.ast.getAllChildrenWithType(11);
            if (actionRoots != null) {
                all_actionRoots.addAll(actionRoots);
            }
            all_actionRoots.addAll(imp_actionRoots);
            if (imp_actionRoots != null) {
                DoubleKeyMap<String, String, GrammarAST> namedActions = new DoubleKeyMap<String, String, GrammarAST>();
                rootGrammar.tool.log("grammar", "imported actions: " + imp_actionRoots);
                for (GrammarAST at : all_actionRoots) {
                    GrammarAST action;
                    GrammarAST name;
                    String scopeName = rootGrammar.getDefaultActionScope();
                    if (at.getChildCount() > 2) {
                        GrammarAST scope = (GrammarAST)at.getChild(0);
                        scopeName = scope.getText();
                        name = (GrammarAST)at.getChild(1);
                        action = (GrammarAST)at.getChild(2);
                    } else {
                        name = (GrammarAST)at.getChild(0);
                        action = (GrammarAST)at.getChild(1);
                    }
                    GrammarAST prevAction = (GrammarAST)namedActions.get(scopeName, name.getText());
                    if (prevAction == null) {
                        namedActions.put(scopeName, name.getText(), action);
                        continue;
                    }
                    if (prevAction.g == at.g) {
                        rootGrammar.tool.errMgr.grammarError(ErrorType.ACTION_REDEFINITION, at.g.fileName, name.token, name.getText());
                        continue;
                    }
                    String s1 = prevAction.getText();
                    s1 = s1.substring(1, s1.length() - 1);
                    String s2 = action.getText();
                    s2 = s2.substring(1, s2.length() - 1);
                    String combinedAction = "{" + s1 + '\n' + s2 + "}";
                    prevAction.token.setText(combinedAction);
                }
                for (String scopeName : namedActions.keySet()) {
                    for (String name : namedActions.keySet(scopeName)) {
                        GrammarAST action = (GrammarAST)namedActions.get(scopeName, name);
                        rootGrammar.tool.log("grammar", action.g.name + " " + scopeName + ":" + name + "=" + action.getText());
                        if (action.g == rootGrammar) continue;
                        root.insertChild(1, action.getParent());
                    }
                }
            }
            if ((modes = imp.ast.getNodesWithType(36)) != null) {
                for (GrammarAST m : modes) {
                    rootGrammar.tool.log("grammar", "imported mode: " + m.toStringTree());
                    String name = m.getChild(0).getText();
                    boolean rootAlreadyHasMode = rootModeNames.contains(name);
                    GrammarAST destinationAST = null;
                    if (rootAlreadyHasMode) {
                        for (GrammarAST m2 : rootModes) {
                            if (!m2.getChild(0).getText().equals(name)) continue;
                            destinationAST = m2;
                            break;
                        }
                    } else {
                        destinationAST = m.dupNode();
                        destinationAST.addChild(m.getChild(0).dupNode());
                    }
                    int addedRules = 0;
                    List<GrammarAST> modeRules = m.getAllChildrenWithType(94);
                    for (GrammarAST r : modeRules) {
                        rootGrammar.tool.log("grammar", "imported rule: " + r.toStringTree());
                        String ruleName = r.getChild(0).getText();
                        boolean rootAlreadyHasRule = rootRuleNames.contains(ruleName);
                        if (rootAlreadyHasRule) continue;
                        destinationAST.addChild(r);
                        ++addedRules;
                        rootRuleNames.add(ruleName);
                    }
                    if (rootAlreadyHasMode || addedRules <= 0) continue;
                    rootGrammar.ast.addChild(destinationAST);
                    rootModeNames.add(name);
                    rootModes.add(destinationAST);
                }
            }
            if ((rules = imp.ast.getNodesWithType(94)) != null) {
                for (GrammarAST r : rules) {
                    rootGrammar.tool.log("grammar", "imported rule: " + r.toStringTree());
                    String name = r.getChild(0).getText();
                    boolean rootAlreadyHasRule = rootRuleNames.contains(name);
                    if (rootAlreadyHasRule) continue;
                    RULES.addChild(r);
                    rootRuleNames.add(name);
                }
            }
            if ((optionsRoot = (GrammarAST)imp.ast.getFirstChildWithType(42)) == null) continue;
            boolean hasNewOption = false;
            for (Map.Entry<String, GrammarAST> option : imp.ast.getOptions().entrySet()) {
                String rootOption;
                String importOption = imp.ast.getOptionString(option.getKey());
                if (importOption == null || importOption.equals(rootOption = rootGrammar.ast.getOptionString(option.getKey()))) continue;
                hasNewOption = true;
                break;
            }
            if (!hasNewOption) continue;
            rootGrammar.tool.errMgr.grammarError(ErrorType.OPTIONS_IN_DELEGATE, optionsRoot.g.fileName, optionsRoot.token, imp.name);
        }
        rootGrammar.tool.log("grammar", "Grammar: " + rootGrammar.ast.toStringTree());
    }

    public GrammarRootAST extractImplicitLexer(Grammar combinedGrammar) {
        GrammarRootAST combinedAST = combinedGrammar.ast;
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(combinedAST.token.getInputStream());
        GrammarAST[] elements = combinedAST.getChildren().toArray(new GrammarAST[0]);
        String lexerName = combinedAST.getChild(0).getText() + "Lexer";
        GrammarRootAST lexerAST = new GrammarRootAST(new CommonToken(25, "LEXER_GRAMMAR"), combinedGrammar.ast.tokenStream);
        lexerAST.grammarType = 31;
        lexerAST.token.setInputStream(combinedAST.token.getInputStream());
        lexerAST.addChild(adaptor.create(28, lexerName));
        GrammarAST optionsRoot = (GrammarAST)combinedAST.getFirstChildWithType(42);
        if (optionsRoot != null && optionsRoot.getChildCount() != 0) {
            GrammarAST[] options;
            GrammarAST lexerOptionsRoot = adaptor.dupNode(optionsRoot);
            lexerAST.addChild(lexerOptionsRoot);
            for (GrammarAST o : options = optionsRoot.getChildren().toArray(new GrammarAST[0])) {
                String string = o.getChild(0).getText();
                if (!Grammar.lexerOptions.contains(string) || Grammar.doNotCopyOptionsToLexer.contains(string)) continue;
                GrammarAST optionTree = (GrammarAST)adaptor.dupTree(o);
                lexerOptionsRoot.addChild(optionTree);
                lexerAST.setOption(string, (GrammarAST)optionTree.getChild(1));
            }
        }
        ArrayList<GrammarAST> actionsWeMoved = new ArrayList<GrammarAST>();
        for (GrammarAST e : elements) {
            if (e.getType() != 11) continue;
            lexerAST.addChild((Tree)adaptor.dupTree(e));
            if (!e.getChild(0).getText().equals("lexer")) continue;
            actionsWeMoved.add(e);
        }
        for (GrammarAST r : actionsWeMoved) {
            combinedAST.deleteChild(r);
        }
        GrammarAST combinedRulesRoot = (GrammarAST)combinedAST.getFirstChildWithType(97);
        if (combinedRulesRoot == null) {
            return lexerAST;
        }
        GrammarAST lexerRulesRoot = adaptor.create(97, "RULES");
        lexerAST.addChild(lexerRulesRoot);
        ArrayList<GrammarASTWithOptions> rulesWeMoved = new ArrayList<GrammarASTWithOptions>();
        GrammarASTWithOptions[] rules = combinedRulesRoot.getChildCount() > 0 ? combinedRulesRoot.getChildren().toArray(new GrammarASTWithOptions[0]) : new GrammarASTWithOptions[]{};
        for (GrammarASTWithOptions r : rules) {
            String ruleName = r.getChild(0).getText();
            if (!Grammar.isTokenName(ruleName)) continue;
            lexerRulesRoot.addChild((Tree)adaptor.dupTree(r));
            rulesWeMoved.add(r);
        }
        for (GrammarAST grammarAST : rulesWeMoved) {
            combinedRulesRoot.deleteChild(grammarAST);
        }
        List<Tuple2<GrammarAST, GrammarAST>> litAliases = Grammar.getStringLiteralAliasesFromLexerRules(lexerAST);
        Set<String> set = combinedGrammar.getStringLiterals();
        int insertIndex = 0;
        block5: for (String lit : set) {
            if (litAliases != null) {
                for (Tuple2<GrammarAST, GrammarAST> pair : litAliases) {
                    GrammarAST litAST = pair.getItem2();
                    if (!lit.equals(litAST.getText())) continue;
                    continue block5;
                }
            }
            String rname = combinedGrammar.getStringLiteralLexerRuleName(lit);
            RuleAST litRule = new RuleAST(94);
            BlockAST blk = new BlockAST(78);
            AltAST alt = new AltAST(74);
            TerminalAST slit = new TerminalAST(new CommonToken(62, lit));
            alt.addChild(slit);
            blk.addChild(alt);
            CommonToken idToken = new CommonToken(66, rname);
            litRule.addChild(new TerminalAST(idToken));
            litRule.addChild(blk);
            lexerRulesRoot.insertChild(insertIndex, litRule);
            lexerRulesRoot.freshenParentAndChildIndexes();
            ++insertIndex;
        }
        lexerAST.sanityCheckParentAndChildIndexes();
        combinedAST.sanityCheckParentAndChildIndexes();
        combinedGrammar.tool.log("grammar", "after extract implicit lexer =" + combinedAST.toStringTree());
        combinedGrammar.tool.log("grammar", "lexer =" + lexerAST.toStringTree());
        if (lexerRulesRoot.getChildCount() == 0) {
            return null;
        }
        return lexerAST;
    }
}

