/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers;

import groovy.transform.CompilationUnitAware;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;

public abstract class DelegatingCustomizer
extends CompilationCustomizer
implements CompilationUnitAware {
    protected final CompilationCustomizer delegate;

    public DelegatingCustomizer(CompilationCustomizer delegate) {
        super(delegate.getPhase());
        this.delegate = delegate;
    }

    @Override
    public void setCompilationUnit(CompilationUnit compilationUnit) {
        if (this.delegate instanceof CompilationUnitAware) {
            ((CompilationUnitAware)((Object)this.delegate)).setCompilationUnit(compilationUnit);
        }
    }

    @Override
    public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
        this.delegate.call(source, context, classNode);
    }
}

