/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.Sortable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.AbstractComparator;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class SortableASTTransformation
extends AbstractASTTransformation {
    private static final ClassNode MY_TYPE = ClassHelper.make(Sortable.class);
    private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode COMPARABLE_TYPE = GenericsUtils.makeClassSafe(Comparable.class);
    private static final ClassNode COMPARATOR_TYPE = GenericsUtils.makeClassSafe(Comparator.class);
    private static final String VALUE = "value";
    private static final String OTHER = "other";
    private static final String THIS_HASH = "thisHash";
    private static final String OTHER_HASH = "otherHash";
    private static final String ARG0 = "arg0";
    private static final String ARG1 = "arg1";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotationNode annotation = (AnnotationNode)nodes[0];
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        if (parent instanceof ClassNode) {
            this.createSortable(annotation, (ClassNode)parent);
        }
    }

    private void createSortable(AnnotationNode anno, ClassNode classNode) {
        boolean allProperties;
        List<String> includes = SortableASTTransformation.getMemberStringList(anno, "includes");
        List<String> excludes = SortableASTTransformation.getMemberStringList(anno, "excludes");
        boolean reversed = this.memberHasValue(anno, "reversed", true);
        boolean includeSuperProperties = this.memberHasValue(anno, "includeSuperProperties", true);
        boolean allNames = this.memberHasValue(anno, "allNames", true);
        boolean bl = allProperties = !this.memberHasValue(anno, "allProperties", false);
        if (!this.checkIncludeExcludeUndefinedAware(anno, excludes, includes, MY_TYPE_NAME)) {
            return;
        }
        if (!this.checkPropertyList(classNode, includes, "includes", anno, MY_TYPE_NAME, false, includeSuperProperties, allProperties)) {
            return;
        }
        if (!this.checkPropertyList(classNode, excludes, "excludes", anno, MY_TYPE_NAME, false, includeSuperProperties, allProperties)) {
            return;
        }
        if (classNode.isInterface()) {
            this.addError(MY_TYPE_NAME + " cannot be applied to interface " + classNode.getName(), anno);
        }
        List<PropertyNode> properties = this.findProperties(anno, classNode, includes, excludes, allProperties, includeSuperProperties, allNames);
        SortableASTTransformation.implementComparable(classNode);
        ClassNodeUtils.addGeneratedMethod(classNode, "compareTo", 1, ClassHelper.int_TYPE, GeneralUtils.params(GeneralUtils.param(GenericsUtils.newClass(classNode), OTHER)), ClassNode.EMPTY_ARRAY, SortableASTTransformation.createCompareToMethodBody(properties, reversed));
        for (PropertyNode property : properties) {
            SortableASTTransformation.createComparatorFor(classNode, property, reversed);
        }
        new VariableScopeVisitor(this.sourceUnit, true).visitClass(classNode);
    }

    private static void implementComparable(ClassNode classNode) {
        if (!classNode.implementsInterface(COMPARABLE_TYPE)) {
            classNode.addInterface(GenericsUtils.makeClassSafeWithGenerics(Comparable.class, classNode));
        }
    }

    private static Statement createCompareToMethodBody(List<PropertyNode> properties, boolean reversed) {
        ArrayList<Statement> statements = new ArrayList<Statement>();
        statements.add(GeneralUtils.ifS((Expression)GeneralUtils.callThisX("is", GeneralUtils.args(OTHER)), GeneralUtils.returnS(GeneralUtils.constX(0))));
        if (properties.isEmpty()) {
            statements.add(GeneralUtils.declS(GeneralUtils.localVarX(THIS_HASH, ClassHelper.Integer_TYPE), GeneralUtils.callX(GeneralUtils.varX("this"), "hashCode")));
            statements.add(GeneralUtils.declS(GeneralUtils.localVarX(OTHER_HASH, ClassHelper.Integer_TYPE), GeneralUtils.callX(GeneralUtils.varX(OTHER), "hashCode")));
            statements.add(GeneralUtils.returnS(SortableASTTransformation.compareExpr(GeneralUtils.varX(THIS_HASH), GeneralUtils.varX(OTHER_HASH), reversed)));
        } else {
            statements.add(GeneralUtils.declS(GeneralUtils.localVarX(VALUE, ClassHelper.int_TYPE), GeneralUtils.constX(0)));
            for (PropertyNode property : properties) {
                String propName = property.getName();
                statements.add(GeneralUtils.assignS(GeneralUtils.varX(VALUE), SortableASTTransformation.compareExpr(GeneralUtils.propX((Expression)GeneralUtils.varX("this"), propName), GeneralUtils.propX((Expression)GeneralUtils.varX(OTHER), propName), reversed)));
                statements.add(GeneralUtils.ifS((Expression)GeneralUtils.neX(GeneralUtils.varX(VALUE), GeneralUtils.constX(0)), GeneralUtils.returnS(GeneralUtils.varX(VALUE))));
            }
            statements.add(GeneralUtils.returnS(GeneralUtils.constX(0)));
        }
        BlockStatement body = new BlockStatement();
        body.addStatements(statements);
        return body;
    }

    private static Statement createCompareMethodBody(PropertyNode property, boolean reversed) {
        String propName = property.getName();
        return GeneralUtils.block(GeneralUtils.ifS((Expression)GeneralUtils.eqX(GeneralUtils.varX(ARG0), GeneralUtils.varX(ARG1)), GeneralUtils.returnS(GeneralUtils.constX(0))), GeneralUtils.ifS((Expression)GeneralUtils.andX(GeneralUtils.notNullX(GeneralUtils.varX(ARG0)), GeneralUtils.equalsNullX(GeneralUtils.varX(ARG1))), GeneralUtils.returnS(GeneralUtils.constX(-1))), GeneralUtils.ifS((Expression)GeneralUtils.andX(GeneralUtils.equalsNullX(GeneralUtils.varX(ARG0)), GeneralUtils.notNullX(GeneralUtils.varX(ARG1))), GeneralUtils.returnS(GeneralUtils.constX(1))), GeneralUtils.returnS(SortableASTTransformation.compareExpr(GeneralUtils.propX((Expression)GeneralUtils.varX(ARG0), propName), GeneralUtils.propX((Expression)GeneralUtils.varX(ARG1), propName), reversed)));
    }

    private static void createComparatorFor(ClassNode classNode, PropertyNode property, boolean reversed) {
        String propName = StringGroovyMethods.capitalize(property.getName());
        String className = classNode.getName() + "$" + propName + "Comparator";
        ClassNode superClass = GenericsUtils.makeClassSafeWithGenerics(AbstractComparator.class, classNode);
        InnerClassNode cmpClass = new InnerClassNode(classNode, className, 10, superClass);
        ClassNodeUtils.addGeneratedInnerClass(classNode, cmpClass);
        ClassNodeUtils.addGeneratedMethod(cmpClass, "compare", 1, ClassHelper.int_TYPE, GeneralUtils.params(GeneralUtils.param(GenericsUtils.newClass(classNode), ARG0), GeneralUtils.param(GenericsUtils.newClass(classNode), ARG1)), ClassNode.EMPTY_ARRAY, SortableASTTransformation.createCompareMethodBody(property, reversed));
        String fieldName = "this$" + propName + "Comparator";
        FieldNode cmpField = classNode.addField(fieldName, 4122, COMPARATOR_TYPE, GeneralUtils.ctorX(cmpClass));
        ClassNodeUtils.addGeneratedMethod(classNode, "comparatorBy" + propName, 9, COMPARATOR_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(GeneralUtils.fieldX(cmpField)));
    }

    private List<PropertyNode> findProperties(AnnotationNode annotation, ClassNode classNode, List<String> includes, List<String> excludes, boolean allProperties, boolean includeSuperProperties, boolean allNames) {
        HashSet<String> names = new HashSet<String>();
        List<PropertyNode> props = GeneralUtils.getAllProperties(names, classNode, classNode, true, false, allProperties, false, includeSuperProperties, false, false, allNames, false);
        ArrayList<PropertyNode> properties = new ArrayList<PropertyNode>();
        for (PropertyNode property : props) {
            String propertyName = property.getName();
            if (excludes != null && excludes.contains(propertyName) || includes != null && !includes.contains(propertyName)) continue;
            properties.add(property);
        }
        for (PropertyNode pNode : properties) {
            this.checkComparable(pNode);
        }
        if (includes != null) {
            Comparator<PropertyNode> includeComparator = Comparator.comparingInt(o -> includes.indexOf(o.getName()));
            properties.sort(includeComparator);
        }
        return properties;
    }

    private void checkComparable(PropertyNode pNode) {
        if (pNode.getType().implementsInterface(COMPARABLE_TYPE) || ClassHelper.isPrimitiveType(pNode.getType()) || this.hasAnnotation(pNode.getType(), MY_TYPE)) {
            return;
        }
        this.addError("Error during " + MY_TYPE_NAME + " processing: property '" + pNode.getName() + "' must be Comparable", pNode);
    }

    private static BinaryExpression compareExpr(Expression lhv, Expression rhv, boolean reversed) {
        return reversed ? GeneralUtils.cmpX(rhv, lhv) : GeneralUtils.cmpX(lhv, rhv);
    }
}

