/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang.groovydoc;

import groovy.lang.groovydoc.Groovydoc;

public interface GroovydocHolder<T> {
    public static final String DOC_COMMENT = "_DOC_COMMENT";

    public Groovydoc getGroovydoc();

    public T getInstance();
}

