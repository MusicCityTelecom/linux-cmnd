/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.GroovyClassLoader;
import java.security.AccessController;
import java.util.Objects;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.Phases;

public abstract class ProcessingUnit {
    protected int phase = 1;
    protected boolean phaseComplete;
    protected CompilerConfiguration configuration;
    protected GroovyClassLoader classLoader;
    protected ErrorCollector errorCollector;

    public ProcessingUnit(CompilerConfiguration configuration, GroovyClassLoader classLoader, ErrorCollector errorCollector) {
        this.setConfiguration(configuration != null ? configuration : CompilerConfiguration.DEFAULT);
        this.setClassLoader(classLoader);
        this.errorCollector = errorCollector != null ? errorCollector : new ErrorCollector(this.getConfiguration());
        this.configure(this.getConfiguration());
    }

    public void configure(CompilerConfiguration configuration) {
        this.setConfiguration(configuration);
    }

    public CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final void setConfiguration(CompilerConfiguration configuration) {
        this.configuration = Objects.requireNonNull(configuration);
    }

    public GroovyClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setClassLoader(GroovyClassLoader loader) {
        this.classLoader = loader != null ? loader : this.createClassLoader();
    }

    private GroovyClassLoader createClassLoader() {
        return AccessController.doPrivileged(() -> {
            ClassLoader parent = Thread.currentThread().getContextClassLoader();
            if (parent == null) {
                parent = ProcessingUnit.class.getClassLoader();
            }
            return new GroovyClassLoader(parent, this.getConfiguration());
        });
    }

    public ErrorCollector getErrorCollector() {
        return this.errorCollector;
    }

    public int getPhase() {
        return this.phase;
    }

    public String getPhaseDescription() {
        return Phases.getDescription(this.phase);
    }

    public boolean isPhaseComplete() {
        return this.phaseComplete;
    }

    public void completePhase() throws CompilationFailedException {
        this.errorCollector.failIfErrors();
        this.phaseComplete = true;
    }

    public void nextPhase() throws CompilationFailedException {
        this.gotoPhase(this.phase + 1);
    }

    public void gotoPhase(int phase) throws CompilationFailedException {
        if (!this.phaseComplete) {
            this.completePhase();
        }
        this.phase = phase;
        this.phaseComplete = false;
    }
}

