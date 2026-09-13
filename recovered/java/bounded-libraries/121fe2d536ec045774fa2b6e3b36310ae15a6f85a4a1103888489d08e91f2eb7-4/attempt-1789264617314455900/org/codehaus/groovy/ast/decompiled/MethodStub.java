/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.MemberStub;

class MethodStub
extends MemberStub {
    final String methodName;
    final int accessModifiers;
    final String desc;
    final String signature;
    final String[] exceptions;
    Map<Integer, List<AnnotationStub>> parameterAnnotations;
    List<String> parameterNames;
    Object annotationDefault;

    public MethodStub(String methodName, int accessModifiers, String desc, String signature, String[] exceptions) {
        this.methodName = methodName;
        this.accessModifiers = accessModifiers;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }
}

