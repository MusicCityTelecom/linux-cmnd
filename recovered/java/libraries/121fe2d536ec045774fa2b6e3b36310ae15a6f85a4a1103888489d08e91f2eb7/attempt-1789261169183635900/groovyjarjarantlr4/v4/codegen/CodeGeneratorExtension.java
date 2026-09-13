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
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;

public class CodeGeneratorExtension {
    public OutputModelFactory factory;

    public CodeGeneratorExtension(OutputModelFactory factory) {
        this.factory = factory;
    }

    public ParserFile parserFile(ParserFile f) {
        return f;
    }

    public Parser parser(Parser p) {
        return p;
    }

    public LexerFile lexerFile(LexerFile f) {
        return f;
    }

    public Lexer lexer(Lexer l) {
        return l;
    }

    public RuleFunction rule(RuleFunction rf) {
        return rf;
    }

    public List<SrcOp> rulePostamble(List<SrcOp> ops) {
        return ops;
    }

    public CodeBlockForAlt alternative(CodeBlockForAlt blk, boolean outerMost) {
        return blk;
    }

    public CodeBlockForAlt finishAlternative(CodeBlockForAlt blk, boolean outerMost) {
        return blk;
    }

    public CodeBlockForAlt epsilon(CodeBlockForAlt blk) {
        return blk;
    }

    public List<SrcOp> ruleRef(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> tokenRef(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> set(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> stringRef(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> wildcard(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> action(List<SrcOp> ops) {
        return ops;
    }

    public List<SrcOp> sempred(List<SrcOp> ops) {
        return ops;
    }

    public Choice getChoiceBlock(Choice c) {
        return c;
    }

    public Choice getEBNFBlock(Choice c) {
        return c;
    }

    public boolean needsImplicitLabel(GrammarAST ID, LabeledOp op) {
        return false;
    }
}

