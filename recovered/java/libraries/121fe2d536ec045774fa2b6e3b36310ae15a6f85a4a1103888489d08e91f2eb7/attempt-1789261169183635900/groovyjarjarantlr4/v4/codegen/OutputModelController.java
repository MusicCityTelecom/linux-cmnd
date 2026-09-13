/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.v4.analysis.LeftRecursiveRuleAltInfo;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.CodeGeneratorExtension;
import groovyjarjarantlr4.v4.codegen.DefaultOutputModelFactory;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.SourceGenTriggers;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.AltBlock;
import groovyjarjarantlr4.v4.codegen.model.BaseListenerFile;
import groovyjarjarantlr4.v4.codegen.model.BaseVisitorFile;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.LeftRecursiveRuleFunction;
import groovyjarjarantlr4.v4.codegen.model.Lexer;
import groovyjarjarantlr4.v4.codegen.model.LexerFile;
import groovyjarjarantlr4.v4.codegen.model.ListenerFile;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.Parser;
import groovyjarjarantlr4.v4.codegen.model.ParserFile;
import groovyjarjarantlr4.v4.codegen.model.RuleActionFunction;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.RuleSempredFunction;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.StarBlock;
import groovyjarjarantlr4.v4.codegen.model.VisitorFile;
import groovyjarjarantlr4.v4.codegen.model.decl.AltLabelStructDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.CodeBlock;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class OutputModelController {
    public OutputModelFactory delegate;
    public List<CodeGeneratorExtension> extensions = new ArrayList<CodeGeneratorExtension>();
    public SourceGenTriggers walker;
    public int codeBlockLevel = -1;
    public int treeLevel = -1;
    public OutputModelObject root;
    public Stack<RuleFunction> currentRule = new Stack();
    public Alternative currentOuterMostAlt;
    public CodeBlock currentBlock;
    public CodeBlockForOuterMostAlt currentOuterMostAlternativeBlock;

    public OutputModelController(OutputModelFactory factory) {
        this.delegate = factory;
    }

    public void addExtension(CodeGeneratorExtension ext) {
        this.extensions.add(ext);
    }

    public OutputModelObject buildParserOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        ParserFile file = this.parserFile(gen.getRecognizerFileName(header));
        this.setRoot(file);
        file.parser = this.parser(file);
        Grammar g = this.delegate.getGrammar();
        for (Rule r : g.rules.values()) {
            this.buildRuleFunction(file.parser, r);
        }
        return file;
    }

    public OutputModelObject buildLexerOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        LexerFile file = this.lexerFile(gen.getRecognizerFileName(header));
        this.setRoot(file);
        file.lexer = this.lexer(file);
        Grammar g = this.delegate.getGrammar();
        for (Rule r : g.rules.values()) {
            this.buildLexerRuleActions(file.lexer, r);
        }
        return file;
    }

    public OutputModelObject buildListenerOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        return new ListenerFile(this.delegate, gen.getListenerFileName(header));
    }

    public OutputModelObject buildBaseListenerOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        return new BaseListenerFile(this.delegate, gen.getBaseListenerFileName(header));
    }

    public OutputModelObject buildVisitorOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        return new VisitorFile(this.delegate, gen.getVisitorFileName(header));
    }

    public OutputModelObject buildBaseVisitorOutputModel(boolean header) {
        CodeGenerator gen = this.delegate.getGenerator();
        return new BaseVisitorFile(this.delegate, gen.getBaseVisitorFileName(header));
    }

    public ParserFile parserFile(String fileName) {
        ParserFile f = this.delegate.parserFile(fileName);
        for (CodeGeneratorExtension ext : this.extensions) {
            f = ext.parserFile(f);
        }
        return f;
    }

    public Parser parser(ParserFile file) {
        Parser p = this.delegate.parser(file);
        for (CodeGeneratorExtension ext : this.extensions) {
            p = ext.parser(p);
        }
        return p;
    }

    public LexerFile lexerFile(String fileName) {
        return new LexerFile(this.delegate, fileName);
    }

    public Lexer lexer(LexerFile file) {
        return new Lexer(this.delegate, file);
    }

    public void buildRuleFunction(Parser parser, Rule r) {
        RuleFunction function = this.rule(r);
        parser.funcs.add(function);
        this.pushCurrentRule(function);
        function.fillNamedActions(this.delegate, r);
        if (r instanceof LeftRecursiveRule) {
            this.buildLeftRecursiveRuleFunction((LeftRecursiveRule)r, (LeftRecursiveRuleFunction)function);
        } else {
            this.buildNormalRuleFunction(r, function);
        }
        Grammar g = this.getGrammar();
        for (ActionAST a : r.actions) {
            if (!(a instanceof PredAST)) continue;
            PredAST p = (PredAST)a;
            RuleSempredFunction rsf = (RuleSempredFunction)parser.sempredFuncs.get(r);
            if (rsf == null) {
                rsf = new RuleSempredFunction(this.delegate, r, function.ctxType);
                parser.sempredFuncs.put(r, rsf);
            }
            rsf.actions.put(g.sempreds.get(p), new Action(this.delegate, p));
        }
        this.popCurrentRule();
    }

    public void buildLeftRecursiveRuleFunction(LeftRecursiveRule r, LeftRecursiveRuleFunction function) {
        this.buildNormalRuleFunction(r, function);
        StructDecl ruleCtx = function.getEffectiveRuleContext(this);
        Map<String, AltLabelStructDecl> altLabelCtxs = function.getEffectiveAltLabelContexts(this);
        Target target = this.delegate.getTarget();
        STGroup codegenTemplates = target.getTemplates();
        CodeBlockForOuterMostAlt outerAlt = (CodeBlockForOuterMostAlt)function.code.get(0);
        ArrayList<CodeBlockForAlt> primaryAltsCode = new ArrayList<CodeBlockForAlt>();
        SrcOp primaryStuff = (SrcOp)outerAlt.ops.get(0);
        if (primaryStuff instanceof Choice) {
            Choice primaryAltBlock = (Choice)primaryStuff;
            primaryAltsCode.addAll(primaryAltBlock.alts);
        } else {
            primaryAltsCode.add((CodeBlockForAlt)primaryStuff);
        }
        StarBlock opAltStarBlock = (StarBlock)outerAlt.ops.get(1);
        CodeBlockForAlt altForOpAltBlock = (CodeBlockForAlt)opAltStarBlock.alts.get(0);
        ArrayList<CodeBlockForAlt> opAltsCode = new ArrayList<CodeBlockForAlt>();
        SrcOp opStuff = (SrcOp)altForOpAltBlock.ops.get(0);
        if (opStuff instanceof AltBlock) {
            AltBlock opAltBlock = (AltBlock)opStuff;
            opAltsCode.addAll(opAltBlock.alts);
        } else {
            opAltsCode.add((CodeBlockForAlt)opStuff);
        }
        for (int i = 0; i < primaryAltsCode.size(); ++i) {
            LeftRecursiveRuleAltInfo altInfo = r.recPrimaryAlts.get(i);
            if (altInfo.altLabel == null) continue;
            ST altActionST = codegenTemplates.getInstanceOf("recRuleReplaceContext");
            altActionST.add("ctxName", (Object)Utils.capitalize(altInfo.altLabel));
            Action altAction = new Action(this.delegate, (StructDecl)altLabelCtxs.get(altInfo.altLabel), altActionST);
            CodeBlockForAlt alt = (CodeBlockForAlt)primaryAltsCode.get(i);
            alt.insertOp(0, altAction);
        }
        ST setStopTokenAST = codegenTemplates.getInstanceOf("recRuleSetStopToken");
        Action setStopTokenAction = new Action(this.delegate, ruleCtx, setStopTokenAST);
        outerAlt.insertOp(1, setStopTokenAction);
        ST setPrevCtx = codegenTemplates.getInstanceOf("recRuleSetPrevCtx");
        Action setPrevCtxAction = new Action(this.delegate, ruleCtx, setPrevCtx);
        opAltStarBlock.addIterationOp(setPrevCtxAction);
        for (int i = 0; i < opAltsCode.size(); ++i) {
            ST altActionST;
            String templateName;
            LeftRecursiveRuleAltInfo altInfo = r.recOpAlts.getElement(i);
            if (altInfo.altLabel != null) {
                templateName = "recRuleLabeledAltStartAction";
                altActionST = codegenTemplates.getInstanceOf(templateName);
                altActionST.add("currentAltLabel", (Object)altInfo.altLabel);
                altActionST.add("ctxName", (Object)this.delegate.getTarget().getRuleFunctionContextStructName(function));
            } else {
                templateName = "recRuleAltStartAction";
                altActionST = codegenTemplates.getInstanceOf(templateName);
                altActionST.add("ctxName", (Object)this.delegate.getTarget().getRuleFunctionContextStructName(function));
            }
            altActionST.add("ruleName", (Object)r.name);
            altActionST.add("label", (Object)altInfo.leftRecursiveRuleRefLabel);
            if (altActionST.impl.formalArguments.containsKey("isListLabel")) {
                altActionST.add("isListLabel", (Object)altInfo.isListLabel);
            } else if (altInfo.isListLabel) {
                this.delegate.getGenerator().tool.errMgr.toolError(ErrorType.CODE_TEMPLATE_ARG_ISSUE, templateName, "isListLabel");
            }
            Action altAction = new Action(this.delegate, (StructDecl)altLabelCtxs.get(altInfo.altLabel), altActionST);
            CodeBlockForAlt alt = (CodeBlockForAlt)opAltsCode.get(i);
            alt.insertOp(0, altAction);
        }
    }

    public void buildNormalRuleFunction(Rule r, RuleFunction function) {
        CodeGenerator gen = this.delegate.getGenerator();
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(r.ast.token.getInputStream());
        GrammarAST blk = (GrammarAST)r.ast.getFirstChildWithType(78);
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(adaptor, blk);
        this.walker = new SourceGenTriggers((TreeNodeStream)nodes, this);
        try {
            function.code = DefaultOutputModelFactory.list(this.walker.block(null, null));
            function.hasLookaheadBlock = this.walker.hasLookaheadBlock;
        }
        catch (RecognitionException e) {
            e.printStackTrace(System.err);
        }
        function.ctxType = this.delegate.getTarget().getRuleFunctionContextStructName(function);
        function.postamble = this.rulePostamble(function, r);
    }

    public void buildLexerRuleActions(Lexer lexer, Rule r) {
        if (r.actions.isEmpty()) {
            return;
        }
        CodeGenerator gen = this.delegate.getGenerator();
        Grammar g = this.delegate.getGrammar();
        String ctxType = this.delegate.getTarget().getRuleFunctionContextStructName(r);
        RuleActionFunction raf = lexer.actionFuncs.get(r);
        if (raf == null) {
            raf = new RuleActionFunction(this.delegate, r, ctxType);
        }
        for (ActionAST a : r.actions) {
            if (a instanceof PredAST) {
                PredAST p = (PredAST)a;
                RuleSempredFunction rsf = (RuleSempredFunction)lexer.sempredFuncs.get(r);
                if (rsf == null) {
                    rsf = new RuleSempredFunction(this.delegate, r, ctxType);
                    lexer.sempredFuncs.put(r, rsf);
                }
                rsf.actions.put(g.sempreds.get(p), new Action(this.delegate, p));
                continue;
            }
            if (a.getType() != 4) continue;
            raf.actions.put(g.lexerActions.get(a), new Action(this.delegate, a));
        }
        if (!raf.actions.isEmpty() && !lexer.actionFuncs.containsKey(r)) {
            lexer.actionFuncs.put(r, raf);
        }
    }

    public RuleFunction rule(Rule r) {
        RuleFunction rf = this.delegate.rule(r);
        for (CodeGeneratorExtension ext : this.extensions) {
            rf = ext.rule(rf);
        }
        return rf;
    }

    public List<SrcOp> rulePostamble(RuleFunction function, Rule r) {
        List<SrcOp> ops = this.delegate.rulePostamble(function, r);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.rulePostamble(ops);
        }
        return ops;
    }

    public Grammar getGrammar() {
        return this.delegate.getGrammar();
    }

    public CodeGenerator getGenerator() {
        return this.delegate.getGenerator();
    }

    public CodeBlockForAlt alternative(Alternative alt, boolean outerMost) {
        CodeBlockForAlt blk = this.delegate.alternative(alt, outerMost);
        if (outerMost) {
            this.currentOuterMostAlternativeBlock = (CodeBlockForOuterMostAlt)blk;
        }
        for (CodeGeneratorExtension ext : this.extensions) {
            blk = ext.alternative(blk, outerMost);
        }
        return blk;
    }

    public CodeBlockForAlt finishAlternative(CodeBlockForAlt blk, List<SrcOp> ops, boolean outerMost) {
        blk = this.delegate.finishAlternative(blk, ops);
        for (CodeGeneratorExtension ext : this.extensions) {
            blk = ext.finishAlternative(blk, outerMost);
        }
        return blk;
    }

    public List<SrcOp> ruleRef(GrammarAST ID, GrammarAST label, GrammarAST args) {
        List<SrcOp> ops = this.delegate.ruleRef(ID, label, args);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.ruleRef(ops);
        }
        return ops;
    }

    public List<SrcOp> tokenRef(GrammarAST ID, GrammarAST label, GrammarAST args) {
        List<SrcOp> ops = this.delegate.tokenRef(ID, label, args);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.tokenRef(ops);
        }
        return ops;
    }

    public List<SrcOp> stringRef(GrammarAST ID, GrammarAST label) {
        List<SrcOp> ops = this.delegate.stringRef(ID, label);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.stringRef(ops);
        }
        return ops;
    }

    public List<SrcOp> set(GrammarAST setAST, GrammarAST labelAST, boolean invert) {
        List<SrcOp> ops = this.delegate.set(setAST, labelAST, invert);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.set(ops);
        }
        return ops;
    }

    public CodeBlockForAlt epsilon(Alternative alt, boolean outerMost) {
        CodeBlockForAlt blk = this.delegate.epsilon(alt, outerMost);
        for (CodeGeneratorExtension ext : this.extensions) {
            blk = ext.epsilon(blk);
        }
        return blk;
    }

    public List<SrcOp> wildcard(GrammarAST ast, GrammarAST labelAST) {
        List<SrcOp> ops = this.delegate.wildcard(ast, labelAST);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.wildcard(ops);
        }
        return ops;
    }

    public List<SrcOp> action(ActionAST ast) {
        List<SrcOp> ops = this.delegate.action(ast);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.action(ops);
        }
        return ops;
    }

    public List<SrcOp> sempred(ActionAST ast) {
        List<SrcOp> ops = this.delegate.sempred(ast);
        for (CodeGeneratorExtension ext : this.extensions) {
            ops = ext.sempred(ops);
        }
        return ops;
    }

    public Choice getChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts, GrammarAST label) {
        Choice c = this.delegate.getChoiceBlock(blkAST, alts, label);
        for (CodeGeneratorExtension ext : this.extensions) {
            c = ext.getChoiceBlock(c);
        }
        return c;
    }

    public Choice getEBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        Choice c = this.delegate.getEBNFBlock(ebnfRoot, alts);
        for (CodeGeneratorExtension ext : this.extensions) {
            c = ext.getEBNFBlock(c);
        }
        return c;
    }

    public boolean needsImplicitLabel(GrammarAST ID, LabeledOp op) {
        boolean needs = this.delegate.needsImplicitLabel(ID, op);
        for (CodeGeneratorExtension ext : this.extensions) {
            needs |= ext.needsImplicitLabel(ID, op);
        }
        return needs;
    }

    public OutputModelObject getRoot() {
        return this.root;
    }

    public void setRoot(OutputModelObject root) {
        this.root = root;
    }

    public RuleFunction getCurrentRuleFunction() {
        if (!this.currentRule.isEmpty()) {
            return this.currentRule.peek();
        }
        return null;
    }

    public void pushCurrentRule(RuleFunction r) {
        this.currentRule.push(r);
    }

    public RuleFunction popCurrentRule() {
        if (!this.currentRule.isEmpty()) {
            return this.currentRule.pop();
        }
        return null;
    }

    public Alternative getCurrentOuterMostAlt() {
        return this.currentOuterMostAlt;
    }

    public void setCurrentOuterMostAlt(Alternative currentOuterMostAlt) {
        this.currentOuterMostAlt = currentOuterMostAlt;
    }

    public void setCurrentBlock(CodeBlock blk) {
        this.currentBlock = blk;
    }

    public CodeBlock getCurrentBlock() {
        return this.currentBlock;
    }

    public void setCurrentOuterMostAlternativeBlock(CodeBlockForOuterMostAlt currentOuterMostAlternativeBlock) {
        this.currentOuterMostAlternativeBlock = currentOuterMostAlternativeBlock;
    }

    public CodeBlockForOuterMostAlt getCurrentOuterMostAlternativeBlock() {
        return this.currentOuterMostAlternativeBlock;
    }

    public int getCodeBlockLevel() {
        return this.codeBlockLevel;
    }
}

