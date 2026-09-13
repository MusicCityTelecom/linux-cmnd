/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.tools;

import groovy.lang.Closure;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.io.ReaderSource;

public class ClosureUtils {
    public static String convertClosureToSource(ReaderSource readerSource, ClosureExpression expression) throws Exception {
        String source = GeneralUtils.convertASTToSource(readerSource, expression);
        if (!source.startsWith("{")) {
            throw new Exception("Error converting ClosureExpression into source code. Closures must start with {. Found: " + source);
        }
        return source;
    }

    public static boolean hasSingleCharacterArg(Closure c) {
        if (c.getMaximumNumberOfParameters() != 1) {
            return false;
        }
        String typeName = c.getParameterTypes()[0].getName();
        return typeName.equals("char") || typeName.equals("java.lang.Character");
    }

    public static boolean hasSingleStringArg(Closure c) {
        if (c.getMaximumNumberOfParameters() != 1) {
            return false;
        }
        String typeName = c.getParameterTypes()[0].getName();
        return typeName.equals("java.lang.String");
    }

    public static boolean hasImplicitParameter(ClosureExpression ce) {
        return ce.getParameters() != null && ce.getParameters().length == 0;
    }

    public static Parameter[] getParametersSafe(ClosureExpression ce) {
        return ce.getParameters() != null ? ce.getParameters() : Parameter.EMPTY_ARRAY;
    }

    public static String getResolveStrategyName(int resolveStrategy) {
        switch (resolveStrategy) {
            case 1: {
                return "DELEGATE_FIRST";
            }
            case 3: {
                return "DELEGATE_ONLY";
            }
            case 2: {
                return "OWNER_ONLY";
            }
            case 4: {
                return "TO_SELF";
            }
        }
        return "OWNER_FIRST";
    }
}

