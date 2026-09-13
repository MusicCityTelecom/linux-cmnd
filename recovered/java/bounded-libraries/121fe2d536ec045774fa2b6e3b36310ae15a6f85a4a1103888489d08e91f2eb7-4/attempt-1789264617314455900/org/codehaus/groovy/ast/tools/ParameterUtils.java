/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.tools;

import java.util.function.BiPredicate;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

public class ParameterUtils {
    public static boolean isVargs(Parameter[] parameters) {
        if (parameters == null || parameters.length == 0) {
            return false;
        }
        return parameters[parameters.length - 1].getType().isArray();
    }

    public static boolean parametersEqual(Parameter[] a, Parameter[] b) {
        return ParameterUtils.parametersEqual(a, b, false);
    }

    public static boolean parametersEqualWithWrapperType(Parameter[] a, Parameter[] b) {
        return ParameterUtils.parametersEqual(a, b, true);
    }

    public static boolean parametersCompatible(Parameter[] source, Parameter[] target) {
        return ParameterUtils.parametersMatch(source, target, StaticTypeCheckingSupport::isAssignableTo);
    }

    private static boolean parametersEqual(Parameter[] a, Parameter[] b, boolean wrapType) {
        return ParameterUtils.parametersMatch(a, b, (aType, bType) -> {
            if (wrapType) {
                aType = ClassHelper.getWrapper(aType);
                bType = ClassHelper.getWrapper(bType);
            }
            return aType.equals(bType);
        });
    }

    private static boolean parametersMatch(Parameter[] a, Parameter[] b, BiPredicate<ClassNode, ClassNode> typeChecker) {
        if (a.length == b.length) {
            int n = a.length;
            for (int i = 0; i < n; ++i) {
                ClassNode bType;
                ClassNode aType = a[i].getType();
                if (typeChecker.test(aType, bType = b[i].getType())) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

