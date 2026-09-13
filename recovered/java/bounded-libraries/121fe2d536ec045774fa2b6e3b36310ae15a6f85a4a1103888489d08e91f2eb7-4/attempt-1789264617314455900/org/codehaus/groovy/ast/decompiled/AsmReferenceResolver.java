/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.Type;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.ClassNodeResolver;
import org.codehaus.groovy.control.CompilationUnit;

public class AsmReferenceResolver {
    private final ClassNodeResolver resolver;
    private final CompilationUnit unit;

    public AsmReferenceResolver(ClassNodeResolver resolver, CompilationUnit unit) {
        this.resolver = resolver;
        this.unit = unit;
    }

    public ClassNode resolveClass(String className) {
        ClassNode classNode = this.resolveClassNullable(className);
        if (classNode == null) {
            throw new NoClassDefFoundError(className);
        }
        return classNode;
    }

    public ClassNode resolveClassNullable(String className) {
        ClassNode beingCompiled = this.unit.getAST().getClass(className);
        if (beingCompiled != null) {
            return beingCompiled;
        }
        ClassNodeResolver.LookupResult lookupResult = this.resolver.resolveName(className, this.unit);
        return lookupResult != null ? lookupResult.getClassNode() : null;
    }

    public ClassNode resolveType(Type type) {
        if (type.getSort() == 9) {
            ClassNode result = this.resolveNonArrayType(type.getElementType());
            for (int n = type.getDimensions(); n > 0; --n) {
                result = result.makeArray();
            }
            return result;
        }
        return this.resolveNonArrayType(type);
    }

    private ClassNode resolveNonArrayType(Type type) {
        String className = type.getClassName();
        if (type.getSort() != 10) {
            return ClassHelper.make(className);
        }
        return this.resolveClass(className);
    }

    public Class resolveJvmClass(String name) {
        try {
            return this.unit.getClassLoader().loadClass(name, false, true);
        }
        catch (ClassNotFoundException e) {
            throw new GroovyBugError("JVM class can't be loaded for " + name, e);
        }
    }
}

