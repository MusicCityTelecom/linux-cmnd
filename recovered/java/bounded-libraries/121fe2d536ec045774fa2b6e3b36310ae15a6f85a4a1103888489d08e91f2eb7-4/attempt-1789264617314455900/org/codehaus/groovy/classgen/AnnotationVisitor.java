/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public class AnnotationVisitor {
    private final SourceUnit source;
    private final ErrorCollector errorCollector;
    private AnnotationNode annotation;
    private ClassNode reportClass;

    public AnnotationVisitor(SourceUnit source, ErrorCollector errorCollector) {
        this.source = source;
        this.errorCollector = errorCollector;
    }

    public void setReportClass(ClassNode node) {
        this.reportClass = node;
    }

    public AnnotationNode visit(AnnotationNode node) {
        this.annotation = node;
        this.setReportClass(node.getClassNode());
        if (!AnnotationVisitor.isValidAnnotationClass(node.getClassNode())) {
            this.addError("class " + node.getClassNode().getName() + " is not an annotation");
            return node;
        }
        if (!this.checkIfMandatoryAnnotationValuesPassed(node)) {
            return node;
        }
        if (!this.checkIfValidEnumConstsAreUsed(node)) {
            return node;
        }
        for (Map.Entry<String, Expression> entry : node.getMembers().entrySet()) {
            String attrName = entry.getKey();
            ClassNode attrType = this.getAttributeType(node, attrName);
            Expression attrExpr = ExpressionUtils.transformInlineConstants(entry.getValue(), attrType);
            entry.setValue(attrExpr);
            this.visitExpression(attrName, attrExpr, attrType);
        }
        VMPluginFactory.getPlugin().configureAnnotation(node);
        return this.annotation;
    }

    private boolean checkIfValidEnumConstsAreUsed(AnnotationNode node) {
        Map<String, Expression> attributes = node.getMembers();
        for (Map.Entry<String, Expression> entry : attributes.entrySet()) {
            if (this.validateEnumConstant(entry.getValue())) continue;
            return false;
        }
        return true;
    }

    private boolean validateEnumConstant(Expression exp) {
        if (exp instanceof PropertyExpression) {
            ClassExpression ce;
            ClassNode type;
            PropertyExpression pe = (PropertyExpression)exp;
            String name = pe.getPropertyAsString();
            if (pe.getObjectExpression() instanceof ClassExpression && name != null && (type = (ce = (ClassExpression)pe.getObjectExpression()).getType()).isEnum()) {
                boolean ok = false;
                try {
                    FieldNode enumField = type.getDeclaredField(name);
                    ok = enumField != null && enumField.getType().equals(type);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (!ok) {
                    this.addError("No enum const " + type.getName() + "." + name, pe);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfMandatoryAnnotationValuesPassed(AnnotationNode node) {
        boolean ok = true;
        ClassNode classNode = node.getClassNode();
        for (MethodNode mn : classNode.getMethods()) {
            String methodName = mn.getName();
            if (mn.getCode() != null || node.getMembers().containsKey(methodName)) continue;
            this.addError("No explicit/default value found for annotation attribute '" + methodName + "'", node);
            ok = false;
        }
        return ok;
    }

    private ClassNode getAttributeType(AnnotationNode node, String attrName) {
        ClassNode classNode = node.getClassNode();
        List<MethodNode> methods = classNode.getMethods(attrName);
        if (methods.isEmpty()) {
            this.addError("'" + attrName + "' is not part of the annotation " + classNode.getNameWithoutPackage(), node);
            return ClassHelper.OBJECT_TYPE;
        }
        return methods.get(0).getReturnType();
    }

    private static boolean isValidAnnotationClass(ClassNode type) {
        return type.implementsInterface(ClassHelper.Annotation_TYPE);
    }

    protected void visitExpression(String attrName, Expression valueExpr, ClassNode attrType) {
        if (attrType.isArray()) {
            if (valueExpr instanceof ListExpression) {
                ListExpression le = (ListExpression)valueExpr;
                this.visitListExpression(attrName, le, attrType.getComponentType());
            } else if (valueExpr instanceof ClosureExpression) {
                this.addError("Annotation list attributes must use Groovy notation [el1, el2]", valueExpr);
            } else {
                ListExpression listExp = new ListExpression();
                listExp.addExpression(valueExpr);
                if (this.annotation != null) {
                    this.annotation.setMember(attrName, listExp);
                }
                this.visitExpression(attrName, listExp, attrType);
            }
        } else if (ClassHelper.isPrimitiveType(attrType) || ClassHelper.isStringType(attrType)) {
            this.visitConstantExpression(attrName, this.getConstantExpression(valueExpr, attrType), ClassHelper.getWrapper(attrType));
        } else if (ClassHelper.isClassType(attrType)) {
            if (!(valueExpr instanceof ClassExpression) && !(valueExpr instanceof ClosureExpression)) {
                this.addError("Only classes and closures can be used for attribute '" + attrName + "'", valueExpr);
            }
        } else if (attrType.isDerivedFrom(ClassHelper.Enum_Type)) {
            if (valueExpr instanceof PropertyExpression) {
                this.visitEnumExpression(attrName, (PropertyExpression)valueExpr, attrType);
            } else if (valueExpr instanceof ConstantExpression) {
                this.visitConstantExpression(attrName, this.getConstantExpression(valueExpr, attrType), attrType);
            } else {
                this.addError("Expected enum value for attribute " + attrName, valueExpr);
            }
        } else if (AnnotationVisitor.isValidAnnotationClass(attrType)) {
            if (valueExpr instanceof AnnotationConstantExpression) {
                this.visitAnnotationExpression(attrName, (AnnotationConstantExpression)valueExpr, attrType);
            } else {
                this.addError("Expected annotation of type '" + attrType.getName() + "' for attribute " + attrName, valueExpr);
            }
        } else {
            this.addError("Unexpected type " + attrType.getName(), valueExpr);
        }
    }

    public void checkReturnType(ClassNode attrType, ASTNode node) {
        if (attrType.isArray()) {
            this.checkReturnType(attrType.getComponentType(), node);
        } else if (!(ClassHelper.isPrimitiveType(attrType) || ClassHelper.isStringType(attrType) || ClassHelper.isClassType(attrType) || attrType.isDerivedFrom(ClassHelper.Enum_Type) || AnnotationVisitor.isValidAnnotationClass(attrType))) {
            this.addError("Unexpected return type " + attrType.getName(), node);
        }
    }

    private ConstantExpression getConstantExpression(Expression exp, ClassNode attrType) {
        Expression result = exp;
        if (!(result instanceof ConstantExpression)) {
            result = ExpressionUtils.transformInlineConstants(result, attrType);
        }
        if (result instanceof ConstantExpression) {
            return (ConstantExpression)result;
        }
        String base = "Expected '" + exp.getText() + "' to be an inline constant of type " + attrType.getName();
        if (exp instanceof PropertyExpression) {
            this.addError(base + " not a property expression", exp);
        } else if (exp instanceof VariableExpression && ((VariableExpression)exp).getAccessedVariable() instanceof FieldNode) {
            this.addError(base + " not a field expression", exp);
        } else {
            this.addError(base, exp);
        }
        ConstantExpression ret = new ConstantExpression(null);
        ret.setSourcePosition(exp);
        return ret;
    }

    protected void visitListExpression(String attrName, ListExpression listExpr, ClassNode elementType) {
        for (Expression expression : listExpr.getExpressions()) {
            this.visitExpression(attrName, expression, elementType);
        }
    }

    protected void visitEnumExpression(String attrName, PropertyExpression valueExpr, ClassNode attrType) {
        ClassNode valueType = valueExpr.getObjectExpression().getType();
        if (!valueType.isDerivedFrom(attrType)) {
            this.addError("Attribute '" + attrName + "' should have type '" + attrType.getName() + "' (Enum), but found " + valueType.getName(), valueExpr);
        }
    }

    protected void visitConstantExpression(String attrName, ConstantExpression valueExpr, ClassNode attrType) {
        ClassNode valueType = valueExpr.getType();
        if (!ClassHelper.getWrapper(valueType).isDerivedFrom(ClassHelper.getWrapper(attrType))) {
            this.addError("Attribute '" + attrName + "' should have type '" + attrType.getName() + "'; but found type '" + valueType.getName() + "'", valueExpr);
        }
    }

    protected void visitAnnotationExpression(String attrName, AnnotationConstantExpression valueExpr, ClassNode attrType) {
        AnnotationNode annotationNode = (AnnotationNode)valueExpr.getValue();
        AnnotationVisitor visitor = new AnnotationVisitor(this.source, this.errorCollector);
        visitor.visit(annotationNode);
    }

    protected void addError(String msg) {
        this.addError(msg, this.annotation);
    }

    protected void addError(String msg, ASTNode node) {
        this.errorCollector.addErrorAndContinue(msg + " in @" + this.reportClass.getName() + '\n', node, this.source);
    }

    public void checkCircularReference(ClassNode searchClass, ClassNode attrType, Expression startExp) {
        if (!AnnotationVisitor.isValidAnnotationClass(attrType)) {
            return;
        }
        if (!(startExp instanceof AnnotationConstantExpression)) {
            this.addError("Found '" + startExp.getText() + "' when expecting an Annotation Constant", startExp);
            return;
        }
        AnnotationConstantExpression ace = (AnnotationConstantExpression)startExp;
        AnnotationNode annotationNode = (AnnotationNode)ace.getValue();
        if (annotationNode.getClassNode().equals(searchClass)) {
            this.addError("Circular reference discovered in " + searchClass.getName(), startExp);
            return;
        }
        ClassNode cn = annotationNode.getClassNode();
        for (MethodNode method : cn.getMethods()) {
            ReturnStatement code;
            if (method.getReturnType().equals(searchClass)) {
                this.addError("Circular reference discovered in " + cn.getName(), startExp);
            }
            if ((code = (ReturnStatement)method.getCode()) == null) continue;
            this.checkCircularReference(searchClass, method.getReturnType(), code.getExpression());
        }
    }
}

