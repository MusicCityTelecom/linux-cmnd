/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import groovy.lang.IntRange;
import java.util.List;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.sc.transformers.StaticCompilationTransformer;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class RangeExpressionTransformer {
    private static final ClassNode INTRANGE_TYPE = ClassHelper.make(IntRange.class);
    private static final MethodNode INTRANGE_CTOR;
    private final StaticCompilationTransformer transformer;

    public RangeExpressionTransformer(StaticCompilationTransformer transformer) {
        this.transformer = transformer;
    }

    public Expression transformRangeExpression(RangeExpression range) {
        if (INTRANGE_TYPE.equals(range.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE))) {
            ConstantExpression inclLeft = GeneralUtils.constX(!range.isExclusiveLeft(), true);
            ConstantExpression inclRight = GeneralUtils.constX(!range.isExclusiveRight(), true);
            ConstructorCallExpression cce = GeneralUtils.ctorX(INTRANGE_TYPE, GeneralUtils.args(inclLeft, inclRight, range.getFrom(), range.getTo()));
            cce.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, INTRANGE_CTOR);
            cce.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, INTRANGE_TYPE);
            cce.setSourcePosition(range);
            return this.transformer.transform(cce);
        }
        return this.transformer.superTransform(range);
    }

    static {
        List<ConstructorNode> declaredConstructors = INTRANGE_TYPE.getDeclaredConstructors();
        ConstructorNode target = null;
        for (ConstructorNode constructor : declaredConstructors) {
            Parameter[] parameters = constructor.getParameters();
            if (parameters.length != 4 || !ClassHelper.isPrimitiveBoolean(parameters[0].getOriginType()) || !ClassHelper.isPrimitiveBoolean(parameters[1].getOriginType())) continue;
            target = constructor;
            break;
        }
        INTRANGE_CTOR = target;
    }
}

