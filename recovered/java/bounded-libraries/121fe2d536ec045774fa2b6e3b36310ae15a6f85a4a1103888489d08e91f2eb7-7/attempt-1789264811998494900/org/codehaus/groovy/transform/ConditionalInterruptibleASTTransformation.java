/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.ConditionalInterrupt;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.transform.AbstractInterruptibleASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ConditionalInterruptibleASTTransformation
extends AbstractInterruptibleASTTransformation
implements GroovyObject {
    private ClosureExpression conditionNode;
    private String conditionMethod;
    private ClassNode currentClass;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    @Generated
    public ConditionalInterruptibleASTTransformation() {
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Override
    protected ClassNode type() {
        return ClassHelper.make(ConditionalInterrupt.class);
    }

    @Override
    protected void setupTransform(AnnotationNode node) {
        String string;
        ClosureExpression closureExpression;
        super.setupTransform(node);
        Expression member = node.getMember("value");
        if (!(member instanceof ClosureExpression)) {
            AbstractInterruptibleASTTransformation.internalError(ShortTypeHandling.castToString(new GStringImpl(new Object[]{member}, new String[]{"Expected closure value for annotation parameter 'value'. Found ", ""})));
        }
        this.conditionNode = closureExpression = (ClosureExpression)ScriptBytecodeAdapter.castToType(member, ClosureExpression.class);
        this.conditionMethod = string = StringGroovyMethods.plus(StringGroovyMethods.plus((CharSequence)"conditionalTransform", node.hashCode()), (CharSequence)"$condition");
    }

    @Override
    protected String getErrorMessage() {
        return StringGroovyMethods.plus("Execution interrupted. The following condition failed: ", (CharSequence)this.convertClosureToSource(this.conditionNode));
    }

    @Override
    public void visitClass(ClassNode type) {
        ClassNode classNode;
        this.currentClass = classNode = type;
        type.addSyntheticMethod(this.conditionMethod, ACC_PRIVATE, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, this.conditionNode.getCode());
        if (this.applyToAllMembers) {
            super.visitClass(type);
        }
    }

    @Override
    protected Expression createCondition() {
        return GeneralUtils.callThisX(this.conditionMethod);
    }

    @Override
    public void visitAnnotations(AnnotatedNode node) {
    }

    @Override
    public void visitField(FieldNode node) {
        if (!node.isStatic() && !node.isSynthetic()) {
            super.visitField(node);
        }
    }

    @Override
    public void visitProperty(PropertyNode node) {
        if (!node.isStatic() && !node.isSynthetic()) {
            super.visitProperty(node);
        }
    }

    @Override
    public void visitClosureExpression(ClosureExpression closureExpr) {
        if (ScriptBytecodeAdapter.compareEqual(closureExpr, this.conditionNode)) {
            return;
        }
        Statement code = closureExpr.getCode();
        Statement statement = this.wrapBlock(code);
        closureExpr.setCode(statement);
        super.visitClosureExpression(closureExpr);
    }

    @Override
    public void visitMethod(MethodNode node) {
        if (ScriptBytecodeAdapter.compareEqual(node.getName(), this.conditionMethod) && !node.isSynthetic()) {
            return;
        }
        if (ScriptBytecodeAdapter.compareEqual(node.getName(), "run") && this.currentClass.isScript() && node.getParameters().length == 0) {
            super.visitMethod(node);
        } else {
            if (this.checkOnMethodStart && !node.isSynthetic() && !node.isStatic() && !node.isAbstract()) {
                Statement code = node.getCode();
                Statement statement = this.wrapBlock(code);
                node.setCode(statement);
            }
            if (!node.isSynthetic() && !node.isStatic()) {
                super.visitMethod(node);
            }
        }
    }

    private String convertClosureToSource(ClosureExpression expression) {
        String string = ClosureUtils.convertClosureToSource(this.source.getSource(), expression);
        try {
            return string;
        }
        catch (Exception e) {
            String string2 = e.getMessage();
            return string2;
        }
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ConditionalInterruptibleASTTransformation.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Override
    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Override
    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }
}

