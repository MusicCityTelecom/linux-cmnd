/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.classgen.asm.ClosureWriter;
import org.codehaus.groovy.classgen.asm.WriterController;

public class LambdaWriter
extends ClosureWriter {
    public LambdaWriter(WriterController controller) {
        super(controller);
    }

    public void writeLambda(LambdaExpression expression) {
        super.writeClosure(expression);
    }

    protected Parameter[] getLambdaSharedVariables(LambdaExpression expression) {
        return super.getClosureSharedVariables(expression);
    }
}

