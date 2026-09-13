/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.lang.reflect.Modifier;
import java.util.StringJoiner;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;

public class AstToTextHelper {
    public static String getClassText(ClassNode node) {
        if (node == null) {
            return "<unknown>";
        }
        return node.toString(false);
    }

    public static String getParameterText(Parameter node) {
        if (node == null) {
            return "<unknown>";
        }
        String name = node.getName();
        if (name == null) {
            name = "<unknown>";
        }
        String type = AstToTextHelper.getClassText(node.getType());
        String text = type + " " + name;
        if (node.hasInitialExpression()) {
            text = text + " = " + node.getInitialExpression().getText();
        }
        return text;
    }

    public static String getParametersText(Parameter[] parameters) {
        if (parameters == null || parameters.length == 0) {
            return "";
        }
        StringJoiner result = new StringJoiner(", ");
        for (Parameter parameter : parameters) {
            result.add(AstToTextHelper.getParameterText(parameter));
        }
        return result.toString();
    }

    public static String getThrowsClauseText(ClassNode[] exceptions) {
        if (exceptions == null || exceptions.length == 0) {
            return "";
        }
        StringJoiner result = new StringJoiner(", ");
        for (ClassNode exception : exceptions) {
            result.add(AstToTextHelper.getClassText(exception));
        }
        return " throws " + result.toString();
    }

    public static String getModifiersText(int modifiers) {
        StringJoiner result = new StringJoiner(" ");
        if (Modifier.isPrivate(modifiers)) {
            result.add("private");
        }
        if (Modifier.isProtected(modifiers)) {
            result.add("protected");
        }
        if (Modifier.isPublic(modifiers)) {
            result.add("public");
        }
        if (Modifier.isStatic(modifiers)) {
            result.add("static");
        }
        if (Modifier.isAbstract(modifiers)) {
            result.add("abstract");
        }
        if (Modifier.isFinal(modifiers)) {
            result.add("final");
        }
        if (Modifier.isInterface(modifiers)) {
            result.add("interface");
        }
        if (Modifier.isNative(modifiers)) {
            result.add("native");
        }
        if (Modifier.isSynchronized(modifiers)) {
            result.add("synchronized");
        }
        if (Modifier.isTransient(modifiers)) {
            result.add("transient");
        }
        if (Modifier.isVolatile(modifiers)) {
            result.add("volatile");
        }
        return result.toString();
    }
}

