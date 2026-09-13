/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.AutoIndentWriter
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STWriter
 */
package groovyjarjarantlr4.v4.codegen;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.codegen.OutputModelController;
import groovyjarjarantlr4.v4.codegen.OutputModelWalker;
import groovyjarjarantlr4.v4.codegen.ParserFactory;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STWriter;

public class CodeGenerator {
    public static final String TEMPLATE_ROOT = "groovyjarjarantlr4/v4/tool/templates/codegen";
    public static final String VOCAB_FILE_EXTENSION = ".tokens";
    public static final String DEFAULT_LANGUAGE = "Java";
    public static final String vocabFilePattern = "<tokens.keys:{t | <t>=<tokens.(t)>\n}><literals.keys:{t | <t>=<literals.(t)>\n}>";
    @NotNull
    public final Grammar g;
    @NotNull
    public final Tool tool;
    @NotNull
    public final String language;
    private Target target;
    public int lineWidth = 72;

    public CodeGenerator(@NotNull Grammar g) {
        this(g.tool, g, g.getOptionString("language"));
    }

    public CodeGenerator(@NotNull Tool tool, @NotNull Grammar g, String language) {
        this.g = g;
        this.tool = tool;
        this.language = language != null ? language : DEFAULT_LANGUAGE;
    }

    @Nullable
    public Target getTarget() {
        if (this.target == null) {
            this.loadLanguageTarget(this.language);
        }
        return this.target;
    }

    @Nullable
    public STGroup getTemplates() {
        Target target = this.getTarget();
        if (target == null) {
            return null;
        }
        return target.getTemplates();
    }

    protected void loadLanguageTarget(String language) {
        String targetName = "groovyjarjarantlr4.v4.codegen.target." + language + "Target";
        try {
            Class<Target> c = Class.forName(targetName).asSubclass(Target.class);
            Constructor<Target> ctor = c.getConstructor(CodeGenerator.class);
            this.target = ctor.newInstance(this);
        }
        catch (ClassNotFoundException cnfe) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_CREATE_TARGET_GENERATOR, cnfe, targetName);
        }
        catch (NoSuchMethodException nsme) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_CREATE_TARGET_GENERATOR, nsme, targetName);
        }
        catch (InvocationTargetException ite) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_CREATE_TARGET_GENERATOR, ite, targetName);
        }
        catch (InstantiationException ie) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_CREATE_TARGET_GENERATOR, ie, targetName);
        }
        catch (IllegalAccessException cnfe) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_CREATE_TARGET_GENERATOR, cnfe, targetName);
        }
    }

    private OutputModelController createController() {
        ParserFactory factory = new ParserFactory(this);
        OutputModelController controller = new OutputModelController(factory);
        factory.setController(controller);
        return controller;
    }

    private ST walk(OutputModelObject outputModel, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        OutputModelWalker walker = new OutputModelWalker(this.tool, target.getTemplates());
        return walker.walk(outputModel, header);
    }

    public ST generateLexer() {
        return this.generateLexer(false);
    }

    public ST generateLexer(boolean header) {
        return this.walk(this.createController().buildLexerOutputModel(header), header);
    }

    public ST generateParser() {
        return this.generateParser(false);
    }

    public ST generateParser(boolean header) {
        return this.walk(this.createController().buildParserOutputModel(header), header);
    }

    public ST generateListener() {
        return this.generateListener(false);
    }

    public ST generateListener(boolean header) {
        return this.walk(this.createController().buildListenerOutputModel(header), header);
    }

    public ST generateBaseListener() {
        return this.generateBaseListener(false);
    }

    public ST generateBaseListener(boolean header) {
        return this.walk(this.createController().buildBaseListenerOutputModel(header), header);
    }

    public ST generateVisitor() {
        return this.generateVisitor(false);
    }

    public ST generateVisitor(boolean header) {
        return this.walk(this.createController().buildVisitorOutputModel(header), header);
    }

    public ST generateBaseVisitor() {
        return this.generateBaseVisitor(false);
    }

    public ST generateBaseVisitor(boolean header) {
        return this.walk(this.createController().buildBaseVisitorOutputModel(header), header);
    }

    ST getTokenVocabOutput() {
        ST vocabFileST = new ST(vocabFilePattern);
        LinkedHashMap<String, Integer> tokens = new LinkedHashMap<String, Integer>();
        for (String t : this.g.tokenNameToTypeMap.keySet()) {
            int tokenType = this.g.tokenNameToTypeMap.get(t);
            if (tokenType < 1) continue;
            tokens.put(t, tokenType);
        }
        vocabFileST.add("tokens", tokens);
        LinkedHashMap<String, Integer> literals = new LinkedHashMap<String, Integer>();
        for (String literal : this.g.stringLiteralToTypeMap.keySet()) {
            int tokenType = this.g.stringLiteralToTypeMap.get(literal);
            if (tokenType < 1) continue;
            literals.put(literal, tokenType);
        }
        vocabFileST.add("literals", literals);
        return vocabFileST;
    }

    public void writeRecognizer(ST outputFileST, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        target.genFile(this.g, outputFileST, this.getRecognizerFileName(header));
    }

    public void writeListener(ST outputFileST, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        target.genFile(this.g, outputFileST, this.getListenerFileName(header));
    }

    public void writeBaseListener(ST outputFileST, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        target.genFile(this.g, outputFileST, this.getBaseListenerFileName(header));
    }

    public void writeVisitor(ST outputFileST, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        target.genFile(this.g, outputFileST, this.getVisitorFileName(header));
    }

    public void writeBaseVisitor(ST outputFileST, boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        target.genFile(this.g, outputFileST, this.getBaseVisitorFileName(header));
    }

    public void writeVocabFile() {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        ST tokenVocabSerialization = this.getTokenVocabOutput();
        String fileName = this.getVocabFileName();
        if (fileName != null) {
            target.genFile(this.g, tokenVocabSerialization, fileName);
        }
    }

    public void write(ST code, String fileName) {
        try {
            long start = System.currentTimeMillis();
            Writer w = this.tool.getOutputFileWriter(this.g, fileName);
            AutoIndentWriter wr = new AutoIndentWriter(w);
            wr.setLineWidth(this.lineWidth);
            code.write((STWriter)wr);
            w.close();
            long stop = System.currentTimeMillis();
        }
        catch (IOException ioe) {
            this.tool.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, ioe, fileName);
        }
    }

    public String getRecognizerFileName() {
        return this.getRecognizerFileName(false);
    }

    public String getListenerFileName() {
        return this.getListenerFileName(false);
    }

    public String getVisitorFileName() {
        return this.getVisitorFileName(false);
    }

    public String getBaseListenerFileName() {
        return this.getBaseListenerFileName(false);
    }

    public String getBaseVisitorFileName() {
        return this.getBaseVisitorFileName(false);
    }

    public String getRecognizerFileName(boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        return target.getRecognizerFileName(header);
    }

    public String getListenerFileName(boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        return target.getListenerFileName(header);
    }

    public String getVisitorFileName(boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        return target.getVisitorFileName(header);
    }

    public String getBaseListenerFileName(boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        return target.getBaseListenerFileName(header);
    }

    public String getBaseVisitorFileName(boolean header) {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        return target.getBaseVisitorFileName(header);
    }

    public String getVocabFileName() {
        return this.g.name + VOCAB_FILE_EXTENSION;
    }

    public String getHeaderFileName() {
        Target target = this.getTarget();
        if (target == null) {
            throw new UnsupportedOperationException("Cannot generate code without a target.");
        }
        ST extST = target.getTemplates().getInstanceOf("headerFileExtension");
        if (extST == null) {
            return null;
        }
        String recognizerName = this.g.getRecognizerName();
        return recognizerName + extST.render();
    }
}

