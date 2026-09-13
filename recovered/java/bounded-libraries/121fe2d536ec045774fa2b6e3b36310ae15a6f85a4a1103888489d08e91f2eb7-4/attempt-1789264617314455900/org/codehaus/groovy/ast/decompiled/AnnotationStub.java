/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.util.LinkedHashMap;
import java.util.Map;

class AnnotationStub {
    final String className;
    final Map<String, Object> members = new LinkedHashMap<String, Object>();

    public AnnotationStub(String className) {
        this.className = className;
    }
}

