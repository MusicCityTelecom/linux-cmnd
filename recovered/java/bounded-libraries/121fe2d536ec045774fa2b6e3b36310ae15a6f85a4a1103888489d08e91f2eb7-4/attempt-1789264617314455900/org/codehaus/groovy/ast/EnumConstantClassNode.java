/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.InnerClassNode;

public class EnumConstantClassNode
extends InnerClassNode {
    public EnumConstantClassNode(ClassNode outerClass, String name, ClassNode superClass) {
        super(outerClass, name, 16400, superClass);
    }

    @Deprecated
    public EnumConstantClassNode(ClassNode outerClass, String name, int modifiers, ClassNode superClass) {
        super(outerClass, name, modifiers, superClass);
    }
}

