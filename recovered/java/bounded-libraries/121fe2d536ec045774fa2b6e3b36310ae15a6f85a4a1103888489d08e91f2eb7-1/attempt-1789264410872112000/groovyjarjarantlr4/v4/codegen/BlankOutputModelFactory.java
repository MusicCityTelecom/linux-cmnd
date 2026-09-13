/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Choice;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.codegen.model.LabeledOp;
import groovyjarjarantlr4.v4.codegen.model.Lexer;
import groovyjarjarantlr4.v4.codegen.model.LexerFile;
import groovyjarjarantlr4.v4.codegen.model.Parser;
import groovyjarjarantlr4.v4.codegen.model.ParserFile;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.Alternative;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public abstract class BlankOutputModelFactory
implements OutputModelFactory {
    @Override
    public ParserFile parserFile(String fileName) {
        return null;
    }

    @Override
    public Parser parser(ParserFile file) {
        return null;
    }

    @Override
    public RuleFunction rule(Rule r) {
        return null;
    }

    @Override
    public List<SrcOp> rulePostamble(RuleFunction function, Rule r) {
        return null;
    }

    @Override
    public LexerFile lexerFile(String fileName) {
        return null;
    }

    @Override
    public Lexer lexer(LexerFile file) {
        return null;
    }

    @Override
    public CodeBlockForAlt alternative(Alternative alt, boolean outerMost) {
        return null;
    }

    @Override
    public CodeBlockForAlt finishAlternative(CodeBlockForAlt blk, List<SrcOp> ops) {
        return blk;
    }

    @Override
    public CodeBlockForAlt epsilon(Alternative alt, boolean outerMost) {
        return null;
    }

    @Override
    public List<SrcOp> ruleRef(GrammarAST ID, GrammarAST label, GrammarAST args) {
        return null;
    }

    @Override
    public List<SrcOp> tokenRef(GrammarAST ID, GrammarAST label, GrammarAST args) {
        return null;
    }

    @Override
    public List<SrcOp> stringRef(GrammarAST ID, GrammarAST label) {
        return this.tokenRef(ID, label, null);
    }

    @Override
    public List<SrcOp> set(GrammarAST setAST, GrammarAST label, boolean invert) {
        return null;
    }

    @Override
    public List<SrcOp> wildcard(GrammarAST ast, GrammarAST labelAST) {
        return null;
    }

    @Override
    public List<SrcOp> action(ActionAST ast) {
        return null;
    }

    @Override
    public List<SrcOp> sempred(ActionAST ast) {
        return null;
    }

    @Override
    public Choice getChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts, GrammarAST label) {
        return null;
    }

    @Override
    public Choice getEBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        return null;
    }

    @Override
    public Choice getLL1ChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts) {
        return null;
    }

    @Override
    public Choice getComplexChoiceBlock(BlockAST blkAST, List<CodeBlockForAlt> alts) {
        return null;
    }

    @Override
    public Choice getLL1EBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        return null;
    }

    @Override
    public Choice getComplexEBNFBlock(GrammarAST ebnfRoot, List<CodeBlockForAlt> alts) {
        return null;
    }

    @Override
    public List<SrcOp> getLL1Test(IntervalSet look, GrammarAST blkAST) {
        return null;
    }

    @Override
    public boolean needsImplicitLabel(GrammarAST ID, LabeledOp op) {
        return false;
    }
}

