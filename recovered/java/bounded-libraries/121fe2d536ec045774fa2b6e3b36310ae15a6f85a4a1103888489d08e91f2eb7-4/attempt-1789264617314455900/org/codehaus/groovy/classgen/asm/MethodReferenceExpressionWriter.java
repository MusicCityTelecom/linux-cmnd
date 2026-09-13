/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.classgen.asm.MethodPointerExpressionWriter;
import org.codehaus.groovy.classgen.asm.WriterController;

public class MethodReferenceExpressionWriter
extends MethodPointerExpressionWriter {
    public MethodReferenceExpressionWriter(WriterController controller) {
        super(controller);
    }

    public void writeMethodReferenceExpression(MethodReferenceExpression expression) {
        super.writeMethodPointerExpression(expression);
    }
}

