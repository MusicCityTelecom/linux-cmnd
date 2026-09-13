/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import org.codehaus.groovy.ast.decompiled.MemberStub;

class FieldStub
extends MemberStub {
    final String fieldName;
    final int accessModifiers;
    final String desc;
    final String signature;
    final Object value;

    public FieldStub(String fieldName, int accessModifiers, String desc, String signature) {
        this(fieldName, accessModifiers, desc, signature, null);
    }

    public FieldStub(String fieldName, int accessModifiers, String desc, String signature, Object value) {
        this.fieldName = fieldName;
        this.accessModifiers = accessModifiers;
        this.desc = desc;
        this.signature = signature;
        this.value = value;
    }
}

