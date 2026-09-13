/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.ExternalizeMethods;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ExternalizeMethodsASTTransformation
extends AbstractASTTransformation {
    static final Class MY_CLASS = ExternalizeMethods.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode EXTERNALIZABLE_TYPE = ClassHelper.make(Externalizable.class);
    private static final ClassNode OBJECTOUTPUT_TYPE = ClassHelper.make(ObjectOutput.class);
    private static final ClassNode OBJECTINPUT_TYPE = ClassHelper.make(ObjectInput.class);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
                return;
            }
            cNode.addInterface(EXTERNALIZABLE_TYPE);
            boolean includeFields = this.memberHasValue(anno, "includeFields", true);
            List<String> excludes = ExternalizeMethodsASTTransformation.getMemberStringList(anno, "excludes");
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields)) {
                return;
            }
            List<FieldNode> list = GeneralUtils.getInstancePropertyFields(cNode);
            if (includeFields) {
                list.addAll(GeneralUtils.getInstanceNonPropertyFields(cNode));
            }
            ExternalizeMethodsASTTransformation.createWriteExternal(cNode, excludes, list);
            ExternalizeMethodsASTTransformation.createReadExternal(cNode, excludes, list);
        }
    }

    private static void createWriteExternal(ClassNode cNode, List<String> excludes, List<FieldNode> list) {
        BlockStatement body = new BlockStatement();
        Parameter out = GeneralUtils.param(OBJECTOUTPUT_TYPE, "out");
        for (FieldNode fNode : list) {
            if (excludes != null && excludes.contains(fNode.getName()) || (fNode.getModifiers() & 0x80) != 0) continue;
            MethodCallExpression writeObject = GeneralUtils.callX((Expression)GeneralUtils.varX(out), "write" + ExternalizeMethodsASTTransformation.suffixForField(fNode), (Expression)GeneralUtils.varX(fNode));
            writeObject.setImplicitThis(false);
            body.addStatement(GeneralUtils.stmt(writeObject));
        }
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(IOException.class)};
        ClassNodeUtils.addGeneratedMethod(cNode, "writeExternal", 1, ClassHelper.VOID_TYPE, GeneralUtils.params(out), exceptions, body);
    }

    private static void createReadExternal(ClassNode cNode, List<String> excludes, List<FieldNode> list) {
        BlockStatement body = new BlockStatement();
        Parameter oin = GeneralUtils.param(OBJECTINPUT_TYPE, "oin");
        for (FieldNode fNode : list) {
            if (excludes != null && excludes.contains(fNode.getName()) || (fNode.getModifiers() & 0x80) != 0) continue;
            String suffix = ExternalizeMethodsASTTransformation.suffixForField(fNode);
            MethodCallExpression readObject = GeneralUtils.callX(GeneralUtils.varX(oin), "read" + suffix);
            readObject.setImplicitThis(false);
            body.addStatement(GeneralUtils.assignS(GeneralUtils.varX(fNode), suffix.equals("Object") ? GeneralUtils.castX(GenericsUtils.nonGeneric(fNode.getType()), readObject) : readObject));
        }
        ClassNodeUtils.addGeneratedMethod(cNode, "readExternal", 1, ClassHelper.VOID_TYPE, GeneralUtils.params(oin), ClassNode.EMPTY_ARRAY, body);
    }

    private static String suffixForField(FieldNode fNode) {
        if (ClassHelper.isPrimitiveInt(fNode.getType())) {
            return "Int";
        }
        if (ClassHelper.isPrimitiveBoolean(fNode.getType())) {
            return "Boolean";
        }
        if (ClassHelper.isPrimitiveLong(fNode.getType())) {
            return "Long";
        }
        if (ClassHelper.isPrimitiveShort(fNode.getType())) {
            return "Short";
        }
        if (ClassHelper.isPrimitiveByte(fNode.getType())) {
            return "Byte";
        }
        if (ClassHelper.isPrimitiveFloat(fNode.getType())) {
            return "Float";
        }
        if (ClassHelper.isPrimitiveDouble(fNode.getType())) {
            return "Double";
        }
        return "Object";
    }
}

