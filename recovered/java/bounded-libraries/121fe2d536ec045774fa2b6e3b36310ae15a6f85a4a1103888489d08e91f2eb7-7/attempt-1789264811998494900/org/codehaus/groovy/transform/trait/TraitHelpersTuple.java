/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.trait;

import org.codehaus.groovy.ast.ClassNode;

class TraitHelpersTuple {
    private final ClassNode helper;
    private final ClassNode fieldHelper;
    private final ClassNode staticFieldHelper;

    public TraitHelpersTuple(ClassNode helper, ClassNode fieldHelper) {
        this(helper, fieldHelper, null);
    }

    public TraitHelpersTuple(ClassNode helper, ClassNode fieldHelper, ClassNode staticFieldHelper) {
        this.helper = helper;
        this.fieldHelper = fieldHelper;
        this.staticFieldHelper = staticFieldHelper;
    }

    public ClassNode getHelper() {
        return this.helper;
    }

    public ClassNode getFieldHelper() {
        return this.fieldHelper;
    }

    public ClassNode getStaticFieldHelper() {
        return this.staticFieldHelper;
    }
}

