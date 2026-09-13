/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.OutputModelController;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.Lexer;
import groovyjarjarantlr4.v4.codegen.model.LexerFile;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.Parser;
import groovyjarjarantlr4.v4.codegen.model.ParserFile;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.CodeBlock;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public interface OutputModelFactory {
    public Grammar getGrammar();

    @NotNull
    public CodeGenerator getGenerator();

    @NotNull
    public Target getTarget();

    public void setController(OutputModelController var1);

    public OutputModelController getController();

    public ParserFile parserFile(String var1);

    public Parser parser(ParserFile var1);

    public LexerFile lexerFile(String var1);

    public Lexer lexer(LexerFile var1);

    public RuleFunction rule(Rule var1);

    public List<SrcOp> rulePostamble(RuleFunction var1, Rule var2);

    public CodeBlockForAlt alternative(Alternative var1, boolean var2);

    public CodeBlockForAlt finishAlternative(CodeBlockForAlt var1, List<SrcOp> var2);

    public CodeBlockForAlt epsilon(Alternative var1, boolean var2);

    public List<SrcOp> ruleRef(GrammarAST var1, GrammarAST var2, GrammarAST var3);

    public List<SrcOp> tokenRef(GrammarAST var1, GrammarAST var2, GrammarAST var3);

    public List<SrcOp> stringRef(GrammarAST var1, GrammarAST var2);

    public List<SrcOp> set(GrammarAST var1, GrammarAST var2, boolean var3);

    public List<SrcOp> wildcard(GrammarAST var1, GrammarAST var2);

    public List<SrcOp> action(ActionAST var1);

    public List<SrcOp> sempred(ActionAST var1);

    public Choice getChoiceBlock(BlockAST var1, List<CodeBlockForAlt> var2, GrammarAST var3);

    public Choice getEBNFBlock(GrammarAST var1, List<CodeBlockForAlt> var2);

    public Choice getLL1ChoiceBlock(BlockAST var1, List<CodeBlockForAlt> var2);

    public Choice getComplexChoiceBlock(BlockAST var1, List<CodeBlockForAlt> var2);

    public Choice getLL1EBNFBlock(GrammarAST var1, List<CodeBlockForAlt> var2);

    public Choice getComplexEBNFBlock(GrammarAST var1, List<CodeBlockForAlt> var2);

    public List<SrcOp> getLL1Test(IntervalSet var1, GrammarAST var2);

    public boolean needsImplicitLabel(GrammarAST var1, LabeledOp var2);

    public OutputModelObject getRoot();

    public RuleFunction getCurrentRuleFunction();

    public Alternative getCurrentOuterMostAlt();

    public CodeBlock getCurrentBlock();

    public CodeBlockForOuterMostAlt getCurrentOuterMostAlternativeBlock();

    public int getCodeBlockLevel();

    public int getTreeLevel();
}

