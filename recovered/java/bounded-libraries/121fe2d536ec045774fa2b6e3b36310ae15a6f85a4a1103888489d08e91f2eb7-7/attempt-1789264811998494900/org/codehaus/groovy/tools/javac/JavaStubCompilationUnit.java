/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.tools.javac.JavaAwareResolveVisitor;
import org.codehaus.groovy.tools.javac.JavaStubGenerator;

public class JavaStubCompilationUnit
extends CompilationUnit {
    private final JavaStubGenerator stubGenerator;
    private int stubCount;

    public JavaStubCompilationUnit(CompilerConfiguration config, GroovyClassLoader gcl, File destDir) {
        super(config, null, gcl);
        if (destDir == null) {
            Map<String, Object> options = this.configuration.getJointCompilationOptions();
            destDir = (File)options.get("stubDir");
        }
        String encoding = this.configuration.getSourceEncoding();
        this.stubGenerator = new JavaStubGenerator(destDir, false, encoding);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            new VariableScopeVisitor(source).visitClass(classNode);
            new JavaAwareResolveVisitor(this).startResolving(classNode, source);
        }, 3);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            try {
                this.stubGenerator.generateClass(classNode);
                ++this.stubCount;
            }
            catch (FileNotFoundException e) {
                source.addException(e);
            }
        }, 3);
    }

    public JavaStubCompilationUnit(CompilerConfiguration config, GroovyClassLoader gcl) {
        this(config, gcl, (File)null);
    }

    public int getStubCount() {
        return this.stubCount;
    }

    @Override
    public void compile() throws CompilationFailedException {
        this.stubCount = 0;
        super.compile(3);
    }

    @Override
    public void configure(CompilerConfiguration config) {
        super.configure(config);
        File targetDir = this.configuration.getTargetDirectory();
        if (targetDir != null) {
            String classOutput = targetDir.getAbsolutePath();
            this.getClassLoader().addClasspath(classOutput);
        }
    }

    @Override
    public SourceUnit addSource(File file) {
        if (this.hasAcceptedFileExtension(file.getName())) {
            return super.addSource(file);
        }
        return null;
    }

    @Override
    public SourceUnit addSource(URL url) {
        if (this.hasAcceptedFileExtension(url.getPath())) {
            return super.addSource(url);
        }
        return null;
    }

    private boolean hasAcceptedFileExtension(String name) {
        String lowerCasedName = name.toLowerCase();
        String extension = lowerCasedName.substring(lowerCasedName.lastIndexOf(46) + 1);
        return this.configuration.getScriptExtensions().contains(extension);
    }
}

