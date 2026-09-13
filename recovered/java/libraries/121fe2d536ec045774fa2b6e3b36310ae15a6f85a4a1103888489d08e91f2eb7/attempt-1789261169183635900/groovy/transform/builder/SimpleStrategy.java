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
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.BuilderASTTransformation;

public class SimpleStrategy
extends BuilderASTTransformation.AbstractBuilderStrategy {
    @Override
    public void build(BuilderASTTransformation transform, AnnotatedNode annotatedNode, AnnotationNode anno) {
        if (!(annotatedNode instanceof ClassNode)) {
            transform.addError("Error during " + BuilderASTTransformation.MY_TYPE_NAME + " processing: building for " + annotatedNode.getClass().getSimpleName() + " not supported by " + this.getClass().getSimpleName(), annotatedNode);
            return;
        }
        ClassNode buildee = (ClassNode)annotatedNode;
        if (this.unsupportedAttribute(transform, anno, "builderClassName")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "buildMethodName")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "builderMethodName")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "forClass")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "includeSuperProperties")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "allProperties")) {
            return;
        }
        if (this.unsupportedAttribute(transform, anno, "force")) {
            return;
        }
        boolean useSetters = transform.memberHasValue(anno, "useSetters", true);
        boolean allNames = transform.memberHasValue(anno, "allNames", true);
        ArrayList<String> excludes = new ArrayList<String>();
        ArrayList<String> includes = new ArrayList<String>();
        includes.add("<DummyUndefinedMarkerString-DoNotUse>");
        if (!this.getIncludeExclude(transform, anno, buildee, excludes, includes)) {
            return;
        }
        if (includes.size() == 1 && Undefined.isUndefined((String)includes.get(0))) {
            includes = null;
        }
        String prefix = AbstractASTTransformation.getMemberStringValue(anno, "prefix", "set");
        List<FieldNode> fields = this.getFields(transform, anno, buildee);
        if (includes != null) {
            for (String name : includes) {
                this.checkKnownField(transform, anno, name, fields);
            }
        }
        for (FieldNode field : fields) {
            String fieldName = field.getName();
            if (AbstractASTTransformation.shouldSkipUndefinedAware(fieldName, excludes, includes, allNames)) continue;
            String methodName = this.getSetterName(prefix, fieldName);
            Parameter parameter = GeneralUtils.param(field.getType(), fieldName);
            ClassNodeUtils.addGeneratedMethod(buildee, methodName, 1, GenericsUtils.newClass(buildee), GeneralUtils.params(parameter), BuilderASTTransformation.NO_EXCEPTIONS, GeneralUtils.block(GeneralUtils.stmt(useSetters && !field.isFinal() ? GeneralUtils.callThisX(this.getSetterName("set", fieldName), GeneralUtils.varX(parameter)) : GeneralUtils.assignX(GeneralUtils.fieldX(field), GeneralUtils.varX(parameter))), GeneralUtils.returnS(GeneralUtils.varX("this"))));
        }
    }

    @Override
    protected List<FieldNode> getFields(BuilderASTTransformation transform, AnnotationNode anno, ClassNode buildee) {
        return GeneralUtils.getInstancePropertyFields(buildee);
    }
}

