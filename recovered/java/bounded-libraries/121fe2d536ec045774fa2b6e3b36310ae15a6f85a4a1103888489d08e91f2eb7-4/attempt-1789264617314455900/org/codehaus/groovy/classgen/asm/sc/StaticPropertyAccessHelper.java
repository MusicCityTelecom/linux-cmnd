/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import java.util.Arrays;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.transform.sc.ListOfExpressionsExpression;
import org.codehaus.groovy.transform.sc.TemporaryVariableExpression;

public abstract class StaticPropertyAccessHelper {
    public static Expression transformToSetterCall(Expression receiver, MethodNode setterMethod, Expression valueExpression, boolean implicitThis, boolean safe, boolean spreadSafe, boolean returnValue, Expression sourceExpression) {
        if (returnValue) {
            TemporaryVariableExpression tmp = new TemporaryVariableExpression(valueExpression);
            PoppingMethodCallExpression call = new PoppingMethodCallExpression(receiver, setterMethod, tmp);
            call.setSafe(safe);
            call.setSpreadSafe(spreadSafe);
            call.setImplicitThis(implicitThis);
            call.setSourcePosition(sourceExpression);
            PoppingListOfExpressionsExpression list = new PoppingListOfExpressionsExpression(tmp, call);
            list.setSourcePosition(sourceExpression);
            return list;
        }
        MethodCallExpression call = new MethodCallExpression(receiver, setterMethod.getName(), valueExpression);
        call.setSafe(safe);
        call.setSpreadSafe(spreadSafe);
        call.setImplicitThis(implicitThis);
        call.setMethodTarget(setterMethod);
        call.setSourcePosition(sourceExpression);
        return call;
    }

    private static class PoppingMethodCallExpression
    extends MethodCallExpression {
        private final TemporaryVariableExpression tmp;

        public PoppingMethodCallExpression(Expression receiver, MethodNode setterMethod, TemporaryVariableExpression tmp) {
            super(receiver, setterMethod.getName(), (Expression)tmp);
            this.setMethodTarget(setterMethod);
            this.tmp = tmp;
        }

        @Override
        public Expression transformExpression(ExpressionTransformer transformer) {
            PoppingMethodCallExpression call = new PoppingMethodCallExpression(transformer.transform(this.getObjectExpression()), this.getMethodTarget(), (TemporaryVariableExpression)this.tmp.transformExpression(transformer));
            call.copyNodeMetaData(this);
            call.setSourcePosition(this);
            call.setSafe(this.isSafe());
            call.setSpreadSafe(this.isSpreadSafe());
            call.setImplicitThis(this.isImplicitThis());
            return call;
        }

        @Override
        public void visit(GroovyCodeVisitor visitor) {
            super.visit(visitor);
            if (visitor instanceof AsmClassGenerator) {
                ((AsmClassGenerator)visitor).getController().getOperandStack().pop();
            }
        }
    }

    private static class PoppingListOfExpressionsExpression
    extends ListOfExpressionsExpression {
        private final TemporaryVariableExpression tmp;
        private final PoppingMethodCallExpression call;

        public PoppingListOfExpressionsExpression(TemporaryVariableExpression tmp, PoppingMethodCallExpression call) {
            super(Arrays.asList(tmp, call));
            this.tmp = tmp;
            this.call = call;
        }

        @Override
        public Expression transformExpression(ExpressionTransformer transformer) {
            PoppingMethodCallExpression call = (PoppingMethodCallExpression)this.call.transformExpression(transformer);
            return new PoppingListOfExpressionsExpression(call.tmp, call);
        }

        @Override
        public void visit(GroovyCodeVisitor visitor) {
            super.visit(visitor);
            if (visitor instanceof AsmClassGenerator) {
                this.tmp.remove(((AsmClassGenerator)visitor).getController());
            }
        }
    }
}

