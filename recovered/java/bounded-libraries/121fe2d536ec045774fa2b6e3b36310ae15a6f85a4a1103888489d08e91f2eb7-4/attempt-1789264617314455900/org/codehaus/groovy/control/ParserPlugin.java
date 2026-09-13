/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.GroovyClassLoader;
import java.io.Reader;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.ParserException;
import org.codehaus.groovy.syntax.Reduction;

public interface ParserPlugin {
    public Reduction parseCST(SourceUnit var1, Reader var2) throws CompilationFailedException;

    public ModuleNode buildAST(SourceUnit var1, ClassLoader var2, Reduction var3) throws ParserException;

    public static ModuleNode buildAST(CharSequence sourceText, CompilerConfiguration config, GroovyClassLoader loader, ErrorCollector errors) throws CompilationFailedException {
        SourceUnit sourceUnit = new SourceUnit("Script" + System.nanoTime() + ".groovy", sourceText.toString(), config, loader, errors);
        sourceUnit.parse();
        sourceUnit.completePhase();
        sourceUnit.nextPhase();
        sourceUnit.convert();
        return sourceUnit.getAST();
    }
}

