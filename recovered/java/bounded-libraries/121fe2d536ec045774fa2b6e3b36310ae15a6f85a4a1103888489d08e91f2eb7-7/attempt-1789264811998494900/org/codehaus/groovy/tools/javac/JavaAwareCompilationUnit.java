/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.AnnotationConstantsVisitor;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.tools.javac.JavaAwareResolveVisitor;
import org.codehaus.groovy.tools.javac.JavaCompiler;
import org.codehaus.groovy.tools.javac.JavaCompilerFactory;
import org.codehaus.groovy.tools.javac.JavaStubGenerator;
import org.codehaus.groovy.tools.javac.JavacCompilerFactory;
import org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor;

public class JavaAwareCompilationUnit
extends CompilationUnit {
    private final JavaStubGenerator stubGenerator;
    private final Set<String> javaSources = new HashSet<String>();
    private JavaCompilerFactory compilerFactory = new JavacCompilerFactory();
    private final File generationGoal;
    private final boolean keepStubs;
    private final boolean memStubEnabled;

    public JavaAwareCompilationUnit() {
        this((CompilerConfiguration)null, (GroovyClassLoader)null, (GroovyClassLoader)null);
    }

    public JavaAwareCompilationUnit(CompilerConfiguration configuration) {
        this(configuration, (GroovyClassLoader)null, (GroovyClassLoader)null);
    }

    public JavaAwareCompilationUnit(CompilerConfiguration configuration, GroovyClassLoader groovyClassLoader) {
        this(configuration, groovyClassLoader, (GroovyClassLoader)null);
    }

    public JavaAwareCompilationUnit(CompilerConfiguration configuration, GroovyClassLoader groovyClassLoader, GroovyClassLoader transformClassLoader) {
        super(configuration, null, groovyClassLoader, transformClassLoader);
        Map<String, Object> options = this.configuration.getJointCompilationOptions();
        String sourceEncoding = this.configuration.getSourceEncoding();
        this.keepStubs = Boolean.TRUE.equals(options.get("keepStubs"));
        this.memStubEnabled = Boolean.TRUE.equals(options.get("memStub"));
        this.generationGoal = this.memStubEnabled ? null : (File)options.get("stubDir");
        this.stubGenerator = new JavaStubGenerator(this.generationGoal, false, sourceEncoding);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            if (!this.javaSources.isEmpty()) {
                new JavaAwareResolveVisitor(this).startResolving(classNode, source);
                new AnnotationConstantsVisitor().visitClass(classNode, source);
            }
        }, 3);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            ASTTransformationCollectorCodeVisitor visitor = new ASTTransformationCollectorCodeVisitor(source, this.getTransformLoader());
            visitor.visitClass(classNode);
        }, 3);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            try {
                if (!this.javaSources.isEmpty()) {
                    this.stubGenerator.generateClass(classNode);
                }
            }
            catch (FileNotFoundException fnfe) {
                source.addException(fnfe);
            }
        }, 3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void gotoPhase(int phase) throws CompilationFailedException {
        super.gotoPhase(phase);
        if (phase == 4 && !this.javaSources.isEmpty()) {
            for (ModuleNode module : this.getAST().getModules()) {
                module.setImportsResolved(false);
            }
            try {
                this.addJavaCompilationUnits(this.stubGenerator.getJavaStubCompilationUnitSet());
                JavaCompiler compiler = this.compilerFactory.createCompiler(this.configuration);
                compiler.compile(new ArrayList<String>(this.javaSources), this);
            }
            finally {
                if (!this.keepStubs) {
                    this.stubGenerator.clean();
                }
                this.javaSources.clear();
            }
        }
    }

    @Override
    public void configure(CompilerConfiguration configuration) {
        super.configure(configuration);
        File targetDir = this.configuration.getTargetDirectory();
        if (targetDir != null) {
            String classOutput = targetDir.getAbsolutePath();
            this.getClassLoader().addClasspath(classOutput);
        }
    }

    private void addJavaSource(File file) {
        this.javaSources.add(file.getAbsolutePath());
    }

    @Override
    public void addSources(String[] paths) {
        for (String path : paths) {
            this.addJavaOrGroovySource(new File(path));
        }
    }

    @Override
    public void addSources(File[] files) {
        for (File file : files) {
            this.addJavaOrGroovySource(file);
        }
    }

    private void addJavaOrGroovySource(File file) {
        if (file.getName().endsWith(".java")) {
            this.addJavaSource(file);
        } else {
            this.addSource(file);
        }
    }

    public JavaCompilerFactory getCompilerFactory() {
        return this.compilerFactory;
    }

    public void setCompilerFactory(JavaCompilerFactory compilerFactory) {
        this.compilerFactory = compilerFactory;
    }
}

