/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.IndexedProperty;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class IndexedPropertyASTTransformation
extends AbstractASTTransformation {
    private static final Class MY_CLASS = IndexedProperty.class;
    private static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode LIST_TYPE = ClassHelper.makeWithoutCaching(List.class, false);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode node = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(node.getClassNode())) {
            return;
        }
        if (parent instanceof FieldNode) {
            FieldNode fNode = (FieldNode)parent;
            ClassNode cNode = fNode.getDeclaringClass();
            if (cNode.getProperty(fNode.getName()) == null) {
                this.addError("Error during " + MY_TYPE_NAME + " processing. Field '" + fNode.getName() + "' doesn't appear to be a property; incorrect visibility?", fNode);
                return;
            }
            ClassNode fType = fNode.getType();
            boolean immutable = Boolean.TRUE.equals(fNode.getNodeMetaData("_IMMUTABLE_BREADCRUMB"));
            if (fType.isArray()) {
                if (!immutable) {
                    IndexedPropertyASTTransformation.addArraySetter(fNode);
                }
                IndexedPropertyASTTransformation.addArrayGetter(fNode);
            } else if (fType.isDerivedFrom(LIST_TYPE)) {
                if (!immutable) {
                    IndexedPropertyASTTransformation.addListSetter(fNode);
                }
                IndexedPropertyASTTransformation.addListGetter(fNode);
            } else {
                this.addError("Error during " + MY_TYPE_NAME + " processing. Non-Indexable property '" + fNode.getName() + "' found. Type must be array or list but found " + fType.getName(), fNode);
            }
        }
    }

    private static void addListGetter(FieldNode fNode) {
        IndexedPropertyASTTransformation.addGetter(fNode, IndexedPropertyASTTransformation.getComponentTypeForList(fNode.getType()));
    }

    private static void addListSetter(FieldNode fNode) {
        IndexedPropertyASTTransformation.addSetter(fNode, IndexedPropertyASTTransformation.getComponentTypeForList(fNode.getType()));
    }

    private static void addArrayGetter(FieldNode fNode) {
        IndexedPropertyASTTransformation.addGetter(fNode, fNode.getType().getComponentType());
    }

    private static void addArraySetter(FieldNode fNode) {
        IndexedPropertyASTTransformation.addSetter(fNode, fNode.getType().getComponentType());
    }

    private static void addGetter(FieldNode fNode, ClassNode componentType) {
        ClassNode cNode = fNode.getDeclaringClass();
        BlockStatement body = new BlockStatement();
        Parameter[] params = new Parameter[]{new Parameter(ClassHelper.int_TYPE, "index")};
        body.addStatement(GeneralUtils.stmt(GeneralUtils.indexX(GeneralUtils.varX(fNode), GeneralUtils.varX(params[0]))));
        ClassNodeUtils.addGeneratedMethod(cNode, IndexedPropertyASTTransformation.makeName(fNode, "get"), IndexedPropertyASTTransformation.getModifiers(fNode), componentType, params, null, body);
    }

    private static void addSetter(FieldNode fNode, ClassNode componentType) {
        ClassNode cNode = fNode.getDeclaringClass();
        BlockStatement body = new BlockStatement();
        Parameter[] theParams = GeneralUtils.params(new Parameter(ClassHelper.int_TYPE, "index"), new Parameter(componentType, "value"));
        body.addStatement(GeneralUtils.assignS(GeneralUtils.indexX(GeneralUtils.varX(fNode), GeneralUtils.varX(theParams[0])), GeneralUtils.varX(theParams[1])));
        ClassNodeUtils.addGeneratedMethod(cNode, GeneralUtils.getSetterName(fNode.getName()), IndexedPropertyASTTransformation.getModifiers(fNode), ClassHelper.VOID_TYPE, theParams, null, body);
    }

    private static ClassNode getComponentTypeForList(ClassNode fType) {
        if (fType.isUsingGenerics() && fType.getGenericsTypes().length == 1) {
            return fType.getGenericsTypes()[0].getType();
        }
        return ClassHelper.OBJECT_TYPE;
    }

    private static int getModifiers(FieldNode fNode) {
        int mods = 1;
        if (fNode.isStatic()) {
            mods |= 8;
        }
        return mods;
    }

    private static String makeName(FieldNode fNode, String prefix) {
        return prefix + BeanUtils.capitalize(fNode.getName());
    }
}

