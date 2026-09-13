/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;

public class Receiver<T> {
    private final ClassNode type;
    private final T data;

    public static <T> Receiver<T> make(ClassNode type) {
        return new Receiver<T>(type == null ? ClassHelper.OBJECT_TYPE.getPlainNodeReference() : type);
    }

    public Receiver(ClassNode type) {
        this.type = type;
        this.data = null;
    }

    public Receiver(ClassNode type, T data) {
        this.data = data;
        this.type = type;
    }

    public T getData() {
        return this.data;
    }

    public ClassNode getType() {
        return this.type;
    }

    public String toString() {
        String sb = "Receiver{type=" + this.type + ", data=" + this.data + '}';
        return sb;
    }
}

