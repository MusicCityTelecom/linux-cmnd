/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import groovy.lang.GroovyRuntimeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import org.codehaus.groovy.ast.ClassNode;

public class MemJavaFileObject
extends SimpleJavaFileObject {
    private final String className;
    private final String src;

    public MemJavaFileObject(ClassNode classNode, String src) {
        this(classNode.getName(), src);
    }

    public MemJavaFileObject(String className, String src) {
        super(MemJavaFileObject.createURI(className), JavaFileObject.Kind.SOURCE);
        this.className = className;
        this.src = src;
    }

    private static URI createURI(String className) {
        try {
            return new URI("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension);
        }
        catch (URISyntaxException e) {
            throw new GroovyRuntimeException(e);
        }
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return this.src;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemJavaFileObject)) {
            return false;
        }
        MemJavaFileObject that = (MemJavaFileObject)o;
        return Objects.equals(this.className, that.className);
    }

    public int hashCode() {
        return Objects.hash(this.className);
    }

    @Override
    public String toString() {
        return "MemJavaFileObject{className=" + this.className + '}';
    }
}

