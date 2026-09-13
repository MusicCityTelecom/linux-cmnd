/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.decompiled.FieldStub;
import org.codehaus.groovy.ast.decompiled.MemberStub;
import org.codehaus.groovy.ast.decompiled.MethodStub;
import org.codehaus.groovy.ast.decompiled.RecordComponentStub;

public class ClassStub
extends MemberStub {
    final String className;
    final int accessModifiers;
    final String signature;
    final String superName;
    final String[] interfaceNames;
    List<MethodStub> methods;
    List<FieldStub> fields;
    final List<String> permittedSubclasses = new ArrayList<String>(1);
    final List<RecordComponentStub> recordComponents = new ArrayList<RecordComponentStub>(1);
    int innerClassModifiers = -1;

    public ClassStub(String className, int accessModifiers, String signature, String superName, String[] interfaceNames) {
        this.className = className;
        this.accessModifiers = accessModifiers;
        this.signature = signature;
        this.superName = superName;
        this.interfaceNames = interfaceNames;
    }
}

