/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import java.util.Collections;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.asm.ClosureWriter;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesClosureWriter
extends ClosureWriter {
    public StaticTypesClosureWriter(WriterController wc) {
        super(wc);
    }

    @Override
    protected ClassNode createClosureClass(ClosureExpression expression, int mods) {
        ClassNode closureClass = super.createClosureClass(expression, mods);
        List<MethodNode> methods = closureClass.getDeclaredMethods("call");
        List<MethodNode> doCall = closureClass.getMethods("doCall");
        if (doCall.size() != 1) {
            throw new GroovyBugError("Expected to find one (1) doCall method on generated closure, but found " + doCall.size());
        }
        MethodNode doCallMethod = doCall.get(0);
        if (methods.isEmpty() && doCallMethod.getParameters().length == 1) {
            StaticTypesClosureWriter.createDirectCallMethod(closureClass, doCallMethod);
        }
        MethodTargetCompletionVisitor visitor = new MethodTargetCompletionVisitor(doCallMethod);
        Object dynamic = expression.getNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION);
        if (dynamic != null) {
            doCallMethod.putNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION, dynamic);
        }
        for (MethodNode method : methods) {
            visitor.visitMethod(method);
        }
        closureClass.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, Boolean.TRUE);
        return closureClass;
    }

    private static void createDirectCallMethod(ClassNode closureClass, MethodNode doCallMethod) {
        Parameter doCallParam = doCallMethod.getParameters()[0];
        Parameter args = new Parameter(doCallParam.getType(), "args");
        StaticTypesClosureWriter.addGeneratedCallMethod(closureClass, doCallMethod, GeneralUtils.varX(args), new Parameter[]{args});
        StaticTypesClosureWriter.addGeneratedCallMethod(closureClass, doCallMethod, StaticTypesClosureWriter.defaultArgument(doCallParam), Parameter.EMPTY_ARRAY);
    }

    private static Expression defaultArgument(Parameter parameter) {
        Expression argument;
        if (parameter.hasInitialExpression()) {
            argument = parameter.getInitialExpression();
        } else if (parameter.getType().isArray()) {
            ClassNode elementType = parameter.getType().getComponentType();
            argument = new ArrayExpression(elementType, null, Collections.singletonList(GeneralUtils.constX(0, true)));
        } else {
            argument = GeneralUtils.nullX();
        }
        return argument;
    }

    private static void addGeneratedCallMethod(ClassNode closureClass, MethodNode doCallMethod, Expression expression, Parameter[] params) {
        MethodCallExpression callDoCall = GeneralUtils.callX((Expression)GeneralUtils.varX("this", closureClass), "doCall", (Expression)GeneralUtils.args(expression));
        callDoCall.setImplicitThis(true);
        callDoCall.setMethodTarget(doCallMethod);
        MethodNode call = new MethodNode("call", 1, ClassHelper.OBJECT_TYPE, params, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(callDoCall));
        ClassNodeUtils.addGeneratedMethod(closureClass, call, true);
    }

    private static final class MethodTargetCompletionVisitor
    extends ClassCodeVisitorSupport {
        private final MethodNode doCallMethod;

        private MethodTargetCompletionVisitor(MethodNode doCallMethod) {
            this.doCallMethod = doCallMethod;
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return null;
        }

        @Override
        public void visitMethodCallExpression(MethodCallExpression call) {
            super.visitMethodCallExpression(call);
            MethodNode mn = call.getMethodTarget();
            if (mn == null) {
                call.setMethodTarget(this.doCallMethod);
            }
        }
    }
}

