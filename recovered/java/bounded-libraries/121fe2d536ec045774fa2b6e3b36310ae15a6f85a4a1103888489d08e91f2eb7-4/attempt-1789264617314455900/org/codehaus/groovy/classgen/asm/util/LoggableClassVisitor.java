/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.util;

import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.util.TraceClassVisitor;
import org.codehaus.groovy.classgen.asm.util.LoggableTextifier;
import org.codehaus.groovy.control.CompilerConfiguration;

public class LoggableClassVisitor
extends ClassVisitor {
    public LoggableClassVisitor(ClassVisitor cv) {
        this(cv, CompilerConfiguration.DEFAULT);
    }

    public LoggableClassVisitor(ClassVisitor cv, CompilerConfiguration compilerConfiguration) {
        super(589824, new TraceClassVisitor(cv, new LoggableTextifier(compilerConfiguration), null));
    }
}

