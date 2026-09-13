/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.builder;

import groovy.transform.Undefined;
import java.util.ArrayList;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.transform.BuilderASTTransformation;

public class ExternalStrategy
extends BuilderASTTransformation.AbstractBuilderStrategy {
    private static final Expression DEFAULT_INITIAL_VALUE = null;

    @Override
    public void build(BuilderASTTransformation transform, AnnotatedNode annotatedNode, AnnotationNode anno) {
        if (!(annotatedNode instanceof ClassNode)) {
            transform.addError("Error during " + BuilderASTTransformation.MY_TYPE_NAME + " processing: building for " + annotatedNode.getClass().getSimpleName() + " not supported by " + this.getClass().getSimpleName(), annotatedNode);
            return;
        }
        ClassNode builder = (ClassNode)annotatedNode;
        String prefix = BuilderASTTransformation.getMemberStringValue(anno, "prefix", "");
        ClassNode buildee = transform.getMemberClassValue(anno, "forClass");
        if (buildee == null) {
            transform.addError("Error during " + BuilderASTTransformation.MY_TYPE_NAME + " processing: 'forClass' must be specified for " + this.getClass().getName(), anno);
            return;
        }
        ArrayList<String> excludes = new ArrayList<String>();
        ArrayList<String> includes = new ArrayList<String>();
        includes.add("<DummyUndefinedMarkerString-DoNotUse>");
        if (!this.getIncludeExclude(transform, anno, buildee, excludes, includes)) {
            return;
        }
        if (includes.size() == 1 && Undefined.isUndefined((String)includes.get(0))) {
            includes = null;
        }
        if (this.unsupportedAttribute(transform, anno, "builderClassName")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "builderMethodName")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "force")) {
            return;
        }
        boolean allNames = transform.memberHasValue(anno, "allNames", true);
        boolean allProperties = !transform.memberHasValue(anno, "allProperties", false);
        List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> props = this.getPropertyInfos(transform, anno, buildee, excludes, includes, allNames, allProperties);
        if (includes != null) {
            for (String name : includes) {
                this.checkKnownProperty(transform, anno, name, props);
            }
        }
        for (BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo prop : props) {
            builder.addField(ExternalStrategy.createFieldCopy(builder, prop));
            ClassNodeUtils.addGeneratedMethod(builder, this.createBuilderMethodForField(builder, prop, prefix));
        }
        ClassNodeUtils.addGeneratedMethod(builder, ExternalStrategy.createBuildMethod(transform, anno, buildee, props));
    }

    private static MethodNode createBuildMethod(BuilderASTTransformation transform, AnnotationNode anno, ClassNode sourceClass, List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> fields) {
        String buildMethodName = BuilderASTTransformation.getMemberStringValue(anno, "buildMethodName", "build");
        BlockStatement body = new BlockStatement();
        Expression sourceClassInstance = ExternalStrategy.initializeInstance(sourceClass, fields, body);
        body.addStatement(GeneralUtils.returnS(sourceClassInstance));
        return new MethodNode(buildMethodName, 1, sourceClass, BuilderASTTransformation.NO_PARAMS, BuilderASTTransformation.NO_EXCEPTIONS, body);
    }

    private MethodNode createBuilderMethodForField(ClassNode builderClass, BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo prop, String prefix) {
        String propName = prop.getName().equals("class") ? "clazz" : prop.getName();
        String setterName = this.getSetterName(prefix, prop.getName());
        return new MethodNode(setterName, 1, GenericsUtils.newClass(builderClass), GeneralUtils.params(GeneralUtils.param(GenericsUtils.newClass(prop.getType()), propName)), BuilderASTTransformation.NO_EXCEPTIONS, GeneralUtils.block(GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.propX((Expression)GeneralUtils.varX("this"), GeneralUtils.constX(propName)), GeneralUtils.varX(propName))), GeneralUtils.returnS(GeneralUtils.varX("this", GenericsUtils.newClass(builderClass)))));
    }

    private static FieldNode createFieldCopy(ClassNode builderClass, BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo prop) {
        String propName = prop.getName();
        return new FieldNode(propName.equals("class") ? "clazz" : propName, 2, GenericsUtils.newClass(prop.getType()), builderClass, DEFAULT_INITIAL_VALUE);
    }

    private static Expression initializeInstance(ClassNode sourceClass, List<BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo> props, BlockStatement body) {
        VariableExpression instance = GeneralUtils.localVarX("_the" + sourceClass.getNameWithoutPackage(), sourceClass);
        body.addStatement(GeneralUtils.declS(instance, GeneralUtils.ctorX(sourceClass)));
        for (BuilderASTTransformation.AbstractBuilderStrategy.PropertyInfo prop : props) {
            body.addStatement(GeneralUtils.stmt(GeneralUtils.assignX(GeneralUtils.propX((Expression)instance, prop.getName()), GeneralUtils.varX(prop.getName().equals("class") ? "clazz" : prop.getName(), GenericsUtils.newClass(prop.getType())))));
        }
        return instance;
    }
}

