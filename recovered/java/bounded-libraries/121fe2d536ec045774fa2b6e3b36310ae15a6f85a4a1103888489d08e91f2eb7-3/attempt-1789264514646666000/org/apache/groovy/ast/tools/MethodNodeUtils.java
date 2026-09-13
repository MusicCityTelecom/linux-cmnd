/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;

public class MethodNodeUtils {
    private MethodNodeUtils() {
    }

    public static String methodDescriptorWithoutReturnType(MethodNode mNode) {
        StringBuilder sb = new StringBuilder();
        sb.append(mNode.getName()).append(':');
        for (Parameter p : mNode.getParameters()) {
            sb.append(ClassNodeUtils.formatTypeName(p.getType())).append(',');
        }
        return sb.toString();
    }

    public static String methodDescriptor(MethodNode mNode) {
        return MethodNodeUtils.methodDescriptor(mNode, false);
    }

    public static String methodDescriptor(MethodNode mNode, boolean pretty) {
        Parameter[] parameters = mNode.getParameters();
        int nParameters = parameters == null ? 0 : parameters.length;
        String name = mNode.getName();
        String prettyName = name.contains(" ") && pretty ? "\"" + name + "\"" : name;
        StringBuilder sb = new StringBuilder(mNode.getName().length() * 2 + nParameters * 10);
        sb.append(mNode.getReturnType().getName());
        sb.append(' ');
        sb.append(prettyName);
        sb.append('(');
        for (int i = 0; i < nParameters; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(parameters[i].getType().getName());
        }
        sb.append(')');
        return sb.toString();
    }

    public static String getPropertyName(MethodNode mNode) {
        String name = mNode.getName();
        int nameLength = name.length();
        if (nameLength > 2) {
            switch (name.charAt(0)) {
                case 'g': {
                    if (nameLength <= 3 || name.charAt(1) != 'e' || name.charAt(2) != 't' || mNode.getParameters().length != 0 || ClassHelper.isPrimitiveVoid(mNode.getReturnType())) break;
                    return BeanUtils.decapitalize(name.substring(3));
                }
                case 's': {
                    if (nameLength <= 3 || name.charAt(1) != 'e' || name.charAt(2) != 't' || mNode.getParameters().length != 1) break;
                    return BeanUtils.decapitalize(name.substring(3));
                }
                case 'i': {
                    if (name.charAt(1) != 's' || mNode.getParameters().length != 0 || !ClassHelper.isPrimitiveBoolean(mNode.getReturnType())) break;
                    return BeanUtils.decapitalize(name.substring(2));
                }
            }
        }
        return null;
    }

    public static BlockStatement getCodeAsBlock(MethodNode node) {
        BlockStatement block;
        Statement code = node.getCode();
        if (code == null) {
            block = new BlockStatement();
        } else if (!(code instanceof BlockStatement)) {
            block = new BlockStatement();
            block.addStatement(code);
        } else {
            block = (BlockStatement)code;
        }
        return block;
    }

    public static boolean isGetterCandidate(MethodNode m) {
        Parameter[] parameters = m.getParameters();
        return m.isPublic() && !m.isStatic() && !m.isAbstract() && (parameters == null || parameters.length == 0) && !ClassHelper.VOID_TYPE.equals(m.getReturnType());
    }
}

