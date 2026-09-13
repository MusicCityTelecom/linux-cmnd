/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.security.AccessController;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.control.ParserPlugin;
import org.codehaus.groovy.control.ProcessingUnit;
import org.codehaus.groovy.control.XStreamUtils;
import org.codehaus.groovy.control.io.FileReaderSource;
import org.codehaus.groovy.control.io.ReaderSource;
import org.codehaus.groovy.control.io.StringReaderSource;
import org.codehaus.groovy.control.io.URLReaderSource;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.control.messages.SimpleMessage;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.Reduction;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.tools.Utilities;

public class SourceUnit
extends ProcessingUnit {
    private ParserPlugin parserPlugin;
    protected ReaderSource source;
    protected String name;
    protected Reduction cst;
    protected ModuleNode ast;

    public SourceUnit(String name, ReaderSource source, CompilerConfiguration flags, GroovyClassLoader loader, ErrorCollector er) {
        super(flags, loader, er);
        this.name = name;
        this.source = source;
    }

    public SourceUnit(File source, CompilerConfiguration configuration, GroovyClassLoader loader, ErrorCollector er) {
        this(source.getPath(), new FileReaderSource(source, configuration), configuration, loader, er);
    }

    public SourceUnit(URL source, CompilerConfiguration configuration, GroovyClassLoader loader, ErrorCollector er) {
        this(source.toExternalForm(), new URLReaderSource(source, configuration), configuration, loader, er);
    }

    public SourceUnit(String name, String source, CompilerConfiguration configuration, GroovyClassLoader loader, ErrorCollector er) {
        this(name, new StringReaderSource(source, configuration), configuration, loader, er);
    }

    public String getName() {
        return this.name;
    }

    public Reduction getCST() {
        return this.cst;
    }

    public ModuleNode getAST() {
        return this.ast;
    }

    public boolean failedWithUnexpectedEOF() {
        return this.getErrorCollector().hasErrors();
    }

    public static SourceUnit create(String name, String source) {
        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setTolerance(1);
        return new SourceUnit(name, source, configuration, null, new ErrorCollector(configuration));
    }

    public static SourceUnit create(String name, String source, int tolerance) {
        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.setTolerance(tolerance);
        return new SourceUnit(name, source, configuration, null, new ErrorCollector(configuration));
    }

    public void parse() throws CompilationFailedException {
        if (this.phase > 2) {
            throw new GroovyBugError("parsing is already complete");
        }
        if (this.phase == 1) {
            this.nextPhase();
        }
        try (Reader reader = this.source.getReader();){
            this.parserPlugin = this.getConfiguration().getPluginFactory().createParserPlugin();
            this.cst = this.parserPlugin.parseCST(this, reader);
        }
        catch (IOException e) {
            this.getErrorCollector().addFatalError(new SimpleMessage(e.getMessage(), this));
        }
    }

    public void convert() throws CompilationFailedException {
        if (this.phase == 2 && this.phaseComplete) {
            this.gotoPhase(3);
        }
        if (this.phase != 3) {
            throw new GroovyBugError("SourceUnit not ready for convert()");
        }
        this.buildAST();
        if ("xml".equals(this.getProperty("groovy.ast"))) {
            XStreamUtils.serialize(this.name, this.ast);
        }
    }

    private String getProperty(String key) {
        return AccessController.doPrivileged(() -> System.getProperty(key));
    }

    public ModuleNode buildAST() {
        if (this.ast == null) {
            try {
                this.ast = this.parserPlugin.buildAST(this, this.classLoader, this.cst);
                this.ast.setDescription(this.name);
            }
            catch (SyntaxException e) {
                if (this.ast == null) {
                    this.ast = new ModuleNode(this);
                }
                this.getErrorCollector().addError(new SyntaxErrorMessage(e, this));
            }
        }
        return this.ast;
    }

    public String getSample(int line, int column, Janitor janitor) {
        String sample = null;
        String text = this.source.getLine(line, janitor);
        if (text != null) {
            if (column > 0) {
                String marker = Utilities.repeatString(" ", column - 1) + "^";
                if (column > 40) {
                    int end;
                    int start = column - 30 - 1;
                    int n = end = column + 10 > text.length() ? text.length() : column + 10 - 1;
                    if (start >= text.length() || end < start) {
                        return null;
                    }
                    sample = "   " + text.substring(start, end) + Utilities.eol() + "   " + marker.substring(start);
                } else {
                    sample = "   " + text + Utilities.eol() + "   " + marker;
                }
            } else {
                sample = text;
            }
        }
        return sample;
    }

    public void addException(Exception e) throws CompilationFailedException {
        this.getErrorCollector().addException(e, this);
    }

    public void addError(SyntaxException se) throws CompilationFailedException {
        this.getErrorCollector().addError(se, this);
    }

    public void addFatalError(String msg, ASTNode node) throws CompilationFailedException {
        this.getErrorCollector().addFatalError(Message.create(new SyntaxException(msg, node), this));
    }

    public void addErrorAndContinue(SyntaxException se) {
        this.getErrorCollector().addErrorAndContinue(se, this);
    }

    public ReaderSource getSource() {
        return this.source;
    }

    public void setSource(ReaderSource source) {
        this.source = source;
    }
}

