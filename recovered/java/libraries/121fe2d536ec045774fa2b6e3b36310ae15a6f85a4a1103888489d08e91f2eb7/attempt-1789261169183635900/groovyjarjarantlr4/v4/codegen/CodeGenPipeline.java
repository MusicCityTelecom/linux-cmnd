/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.gui.STViz
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.List;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.gui.STViz;

public class CodeGenPipeline {
    Grammar g;

    public CodeGenPipeline(Grammar g) {
        this.g = g;
    }

    public void process() {
        CodeGenerator gen = new CodeGenerator(this.g);
        Target target = gen.getTarget();
        if (target == null) {
            return;
        }
        IntervalSet idTypes = new IntervalSet(new int[0]);
        idTypes.add(28);
        idTypes.add(57);
        idTypes.add(66);
        List<GrammarAST> idNodes = this.g.ast.getNodesWithType(idTypes);
        for (GrammarAST idNode : idNodes) {
            if (!target.grammarSymbolCausesIssueInGeneratedCode(idNode)) continue;
            this.g.tool.errMgr.grammarError(ErrorType.USE_OF_BAD_WORD, this.g.fileName, idNode.getToken(), idNode.getText());
        }
        int errorCount = this.g.tool.errMgr.getNumErrors();
        if (this.g.isLexer()) {
            ST lexer;
            if (target.needsHeader()) {
                lexer = gen.generateLexer(true);
                if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                    this.writeRecognizer(lexer, gen, true);
                }
            }
            lexer = gen.generateLexer(false);
            if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                this.writeRecognizer(lexer, gen, false);
            }
        } else {
            ST parser;
            if (target.needsHeader()) {
                parser = gen.generateParser(true);
                if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                    this.writeRecognizer(parser, gen, true);
                }
            }
            parser = gen.generateParser(false);
            if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                this.writeRecognizer(parser, gen, false);
            }
            if (this.g.tool.gen_listener) {
                ST baseListener;
                ST listener;
                if (target.needsHeader()) {
                    listener = gen.generateListener(true);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeListener(listener, true);
                    }
                }
                listener = gen.generateListener(false);
                if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                    gen.writeListener(listener, false);
                }
                if (target.needsHeader()) {
                    baseListener = gen.generateBaseListener(true);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeBaseListener(baseListener, true);
                    }
                }
                if (target.wantsBaseListener()) {
                    baseListener = gen.generateBaseListener(false);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeBaseListener(baseListener, false);
                    }
                }
            }
            if (this.g.tool.gen_visitor) {
                ST baseVisitor;
                ST visitor;
                if (target.needsHeader()) {
                    visitor = gen.generateVisitor(true);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeVisitor(visitor, true);
                    }
                }
                visitor = gen.generateVisitor(false);
                if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                    gen.writeVisitor(visitor, false);
                }
                if (target.needsHeader()) {
                    baseVisitor = gen.generateBaseVisitor(true);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeBaseVisitor(baseVisitor, true);
                    }
                }
                if (target.wantsBaseVisitor()) {
                    baseVisitor = gen.generateBaseVisitor(false);
                    if (this.g.tool.errMgr.getNumErrors() == errorCount) {
                        gen.writeBaseVisitor(baseVisitor, false);
                    }
                }
            }
        }
        gen.writeVocabFile();
    }

    protected void writeRecognizer(ST template, CodeGenerator gen, boolean header) {
        if (this.g.tool.launch_ST_inspector) {
            STViz viz = template.inspect();
            if (this.g.tool.ST_inspector_wait_for_close) {
                try {
                    viz.waitForClose();
                }
                catch (InterruptedException ex) {
                    this.g.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, ex, new Object[0]);
                }
            }
        }
        gen.writeRecognizer(template, header);
    }
}

