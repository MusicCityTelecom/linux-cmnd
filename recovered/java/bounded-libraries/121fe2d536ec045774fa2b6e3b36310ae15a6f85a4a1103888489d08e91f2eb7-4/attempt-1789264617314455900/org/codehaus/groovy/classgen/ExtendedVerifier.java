/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.classgen.AnnotationVisitor;
import org.codehaus.groovy.control.AnnotationConstantsVisitor;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

public class ExtendedVerifier
extends ClassCodeVisitorSupport {
    public static final String JVM_ERROR_MESSAGE = "Please make sure you are running on a JVM >= 1.5";
    private static final String EXTENDED_VERIFIER_SEEN = "EXTENDED_VERIFIER_SEEN";
    private ClassNode currentClass;
    private final SourceUnit source;
    private final Map<String, Boolean> repeatableCache = new HashMap<String, Boolean>();

    public ExtendedVerifier(SourceUnit sourceUnit) {
        this.source = sourceUnit;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    @Override
    public void visitClass(ClassNode node) {
        ClassNode[] interfaces;
        AnnotationConstantsVisitor acv = new AnnotationConstantsVisitor();
        acv.visitClass(node, this.source);
        this.currentClass = node;
        if (node.isAnnotationDefinition()) {
            this.visitAnnotations(node, 64);
        } else {
            this.visitAnnotations(node, 65);
            this.visitTypeAnnotations(node);
        }
        PackageNode packageNode = node.getPackage();
        if (packageNode != null) {
            this.visitAnnotations(packageNode, 128);
        }
        this.visitTypeAnnotations(node.getUnresolvedSuperClass());
        for (ClassNode anInterface : interfaces = node.getInterfaces()) {
            this.visitTypeAnnotations(anInterface);
        }
        if (node.isRecord()) {
            this.visitRecordComponents(node);
        }
        node.visitContents(this);
    }

    private void visitRecordComponents(ClassNode node) {
        for (RecordComponentNode recordComponentNode : node.getRecordComponents()) {
            this.visitAnnotations(recordComponentNode, 1024);
            this.visitTypeAnnotations(recordComponentNode.getType());
            this.extractTypeUseAnnotations(recordComponentNode.getAnnotations(), recordComponentNode.getType(), 1024);
        }
    }

    @Override
    public void visitField(FieldNode node) {
        this.visitAnnotations(node, 8);
        if (!node.isStatic() && this.currentClass.isRecord()) {
            return;
        }
        this.visitTypeAnnotations(node.getType());
        this.extractTypeUseAnnotations(node.getAnnotations(), node.getType(), 8);
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        VariableExpression varx = expression.getVariableExpression();
        if (varx != null) {
            this.visitAnnotations(expression, 32);
            ClassNode type = varx.getType();
            this.visitTypeAnnotations(type);
            this.extractTypeUseAnnotations(expression.getAnnotations(), type, 32);
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        this.visitConstructorOrMethod((MethodNode)node, 2);
        this.extractTypeUseAnnotations(node.getAnnotations(), node.getReturnType(), 2);
    }

    @Override
    public void visitMethod(MethodNode node) {
        this.visitConstructorOrMethod(node, 4);
        this.visitGenericsTypeAnnotations(node);
        this.visitTypeAnnotations(node.getReturnType());
        this.extractTypeUseAnnotations(node.getAnnotations(), node.getReturnType(), 4);
    }

    private void visitTypeAnnotations(ClassNode node) {
        if (Boolean.TRUE.equals(node.getNodeMetaData(EXTENDED_VERIFIER_SEEN))) {
            return;
        }
        node.putNodeMetaData(EXTENDED_VERIFIER_SEEN, Boolean.TRUE);
        this.visitAnnotations(node, node.getTypeAnnotations(), 256);
        this.visitGenericsTypeAnnotations(node);
    }

    private void visitGenericsTypeAnnotations(ClassNode node) {
        GenericsType[] genericsTypes = node.getGenericsTypes();
        if (node.isUsingGenerics() && genericsTypes != null) {
            this.visitGenericsTypeAnnotations(genericsTypes);
        }
    }

    private void visitGenericsTypeAnnotations(MethodNode node) {
        GenericsType[] genericsTypes = node.getGenericsTypes();
        if (genericsTypes != null) {
            this.visitGenericsTypeAnnotations(genericsTypes);
        }
    }

    private void visitGenericsTypeAnnotations(GenericsType[] genericsTypes) {
        for (GenericsType gt : genericsTypes) {
            this.visitTypeAnnotations(gt.getType());
            if (gt.getLowerBound() != null) {
                this.visitTypeAnnotations(gt.getLowerBound());
            }
            if (gt.getUpperBounds() == null) continue;
            for (ClassNode ub : gt.getUpperBounds()) {
                this.visitTypeAnnotations(ub);
            }
        }
    }

    private void extractTypeUseAnnotations(List<AnnotationNode> mixed, ClassNode targetType, Integer keepTarget) {
        ArrayList<AnnotationNode> typeUseAnnos = new ArrayList<AnnotationNode>();
        for (AnnotationNode anno : mixed) {
            if (!anno.isTargetAllowed(512)) continue;
            typeUseAnnos.add(anno);
        }
        if (!typeUseAnnos.isEmpty()) {
            targetType.addTypeAnnotations(typeUseAnnos);
            targetType.setAnnotated(true);
            for (AnnotationNode anno : typeUseAnnos) {
                if (keepTarget == null || anno.isTargetAllowed(keepTarget)) continue;
                mixed.remove(anno);
            }
        }
    }

    private void visitConstructorOrMethod(MethodNode node, int methodTarget) {
        Statement code;
        this.visitAnnotations(node, methodTarget);
        for (Parameter parameter : node.getParameters()) {
            this.visitAnnotations(parameter, 16);
            this.visitTypeAnnotations(parameter.getType());
            this.extractTypeUseAnnotations(parameter.getAnnotations(), parameter.getType(), 16);
        }
        if (node.getExceptions() != null) {
            for (AnnotatedNode annotatedNode : node.getExceptions()) {
                this.visitTypeAnnotations((ClassNode)annotatedNode);
            }
        }
        if (this.currentClass.isAnnotationDefinition() && !node.isStaticConstructor()) {
            ReturnStatement code2;
            ErrorCollector errorCollector = new ErrorCollector(this.source.getConfiguration());
            AnnotationVisitor visitor = new AnnotationVisitor(this.source, errorCollector);
            visitor.setReportClass(this.currentClass);
            visitor.checkReturnType(node.getReturnType(), node);
            if (node.getParameters().length > 0) {
                this.addError("Annotation members may not have parameters.", node.getParameters()[0]);
            }
            if (node.getExceptions().length > 0) {
                this.addError("Annotation members may not have a throws clause.", node.getExceptions()[0]);
            }
            if ((code2 = (ReturnStatement)node.getCode()) != null) {
                visitor.visitExpression(node.getName(), code2.getExpression(), node.getReturnType());
                visitor.checkCircularReference(this.currentClass, node.getReturnType(), code2.getExpression());
            }
            this.source.getErrorCollector().addCollectorContents(errorCollector);
        }
        if ((code = node.getCode()) != null) {
            code.visit(this);
        }
    }

    @Override
    public void visitProperty(PropertyNode node) {
    }

    protected void visitAnnotations(AnnotatedNode node, int target) {
        List<AnnotationNode> annotations = node.getAnnotations();
        this.visitAnnotations(node, annotations, target);
    }

    private void visitAnnotations(AnnotatedNode node, List<AnnotationNode> annotations, int target) {
        if (annotations.isEmpty()) {
            return;
        }
        this.currentClass.setAnnotated(true);
        LinkedHashMap<String, List<AnnotationNode>> nonSourceAnnotations = new LinkedHashMap<String, List<AnnotationNode>>();
        boolean skippable = node.getNodeMetaData("_SKIPPABLE_ANNOTATIONS") != null;
        Iterator<AnnotationNode> iterator = annotations.iterator();
        while (iterator.hasNext()) {
            boolean isTargetAnnotation;
            AnnotationNode unvisited = iterator.next();
            ErrorCollector errorCollector = new ErrorCollector(this.source.getConfiguration());
            AnnotationVisitor visitor = new AnnotationVisitor(this.source, errorCollector);
            AnnotationNode visited = visitor.visit(unvisited);
            this.source.getErrorCollector().addCollectorContents(errorCollector);
            String name = visited.getClassNode().getName();
            if (skippable && this.shouldSkip(node, visited)) {
                iterator.remove();
                continue;
            }
            if (!visited.hasSourceRetention()) {
                ArrayList<AnnotationNode> seen = (ArrayList<AnnotationNode>)nonSourceAnnotations.get(name);
                if (seen == null) {
                    seen = new ArrayList<AnnotationNode>();
                } else if (!this.isRepeatable(visited)) {
                    this.addError("Cannot specify duplicate annotation on the same member : " + name, visited);
                }
                seen.add(visited);
                nonSourceAnnotations.put(name, seen);
            }
            if (!((isTargetAnnotation = name.equals("java.lang.annotation.Target")) || visited.isTargetAllowed(target) || this.isTypeUseScenario(visited, target))) {
                this.addError("Annotation @" + name + " is not allowed on element " + AnnotationNode.targetToName(target), visited);
            }
            ExtendedVerifier.visitDeprecation(node, visited);
            this.visitOverride(node, visited);
        }
        this.processDuplicateAnnotationContainers(node, nonSourceAnnotations);
    }

    private boolean shouldSkip(AnnotatedNode node, AnnotationNode visited) {
        return node instanceof ClassNode && !visited.isTargetAllowed(65) && !visited.isTargetAllowed(512) && visited.isTargetAllowed(2) || node instanceof ConstructorNode && !visited.isTargetAllowed(2) && visited.isTargetAllowed(65) || node instanceof FieldNode && !visited.isTargetAllowed(8) && !visited.isTargetAllowed(512) || node instanceof Parameter && !visited.isTargetAllowed(16) && !visited.isTargetAllowed(512) || node instanceof MethodNode && !(node instanceof ConstructorNode) && !visited.isTargetAllowed(4) && !visited.isTargetAllowed(512) || node instanceof RecordComponentNode && !visited.isTargetAllowed(1024) && !visited.isTargetAllowed(512);
    }

    private boolean isRepeatable(AnnotationNode annoNode) {
        ClassNode annoClassNode = annoNode.getClassNode();
        String name = annoClassNode.getName();
        if (!this.repeatableCache.containsKey(name)) {
            boolean result = false;
            for (AnnotationNode anno : annoClassNode.getAnnotations()) {
                if (!anno.getClassNode().getName().equals("java.lang.annotation.Repeatable")) continue;
                result = true;
                break;
            }
            this.repeatableCache.put(name, result);
        }
        return this.repeatableCache.get(name);
    }

    private boolean isTypeUseScenario(AnnotationNode visited, int target) {
        return visited.isTargetAllowed(512) && (target & 0x80) == 0;
    }

    private void processDuplicateAnnotationContainers(AnnotatedNode node, Map<String, List<AnnotationNode>> nonSourceAnnotations) {
        for (Map.Entry<String, List<AnnotationNode>> next : nonSourceAnnotations.entrySet()) {
            if (next.getValue().size() <= 1) continue;
            ClassNode repeatable = null;
            AnnotationNode repeatee = next.getValue().get(0);
            for (AnnotationNode anno : repeatee.getClassNode().getAnnotations()) {
                Expression value;
                if (!anno.getClassNode().getName().equals("java.lang.annotation.Repeatable") || !((value = anno.getMember("value")) instanceof ClassExpression) || !value.getType().isAnnotationDefinition()) continue;
                repeatable = value.getType();
                break;
            }
            if (repeatable == null) continue;
            if (nonSourceAnnotations.containsKey(repeatable.getName())) {
                this.addError("Cannot specify duplicate annotation on the same member. Explicit " + repeatable.getName() + " found when creating implicit container for " + next.getKey(), node);
            }
            AnnotationNode collector = new AnnotationNode(repeatable);
            if (repeatee.hasRuntimeRetention()) {
                collector.setRuntimeRetention(true);
            } else if (repeatable.isResolved()) {
                Class repeatableType = repeatable.getTypeClass();
                Retention retention = repeatableType.getAnnotation(Retention.class);
                collector.setRuntimeRetention(retention != null && retention.value().equals((Object)RetentionPolicy.RUNTIME));
            } else {
                for (AnnotationNode annotation : repeatable.getAnnotations()) {
                    if (!annotation.getClassNode().getName().equals("java.lang.annotation.Retention")) continue;
                    Expression value = annotation.getMember("value");
                    assert (value != null);
                    Object retention = StaticTypeCheckingSupport.evaluateExpression(value, this.source.getConfiguration());
                    collector.setRuntimeRetention(retention != null && retention.toString().equals("RUNTIME"));
                    break;
                }
            }
            collector.addMember("value", new ListExpression(next.getValue().stream().map(AnnotationConstantExpression::new).collect(Collectors.toList())));
            node.getAnnotations().removeAll((Collection)next.getValue());
            node.addAnnotation(collector);
        }
    }

    private static void visitDeprecation(AnnotatedNode node, AnnotationNode visited) {
        if (visited.getClassNode().isResolved() && visited.getClassNode().getName().equals("java.lang.Deprecated")) {
            if (node instanceof MethodNode) {
                MethodNode mn = (MethodNode)node;
                mn.setModifiers(mn.getModifiers() | 0x20000);
            } else if (node instanceof FieldNode) {
                FieldNode fn = (FieldNode)node;
                fn.setModifiers(fn.getModifiers() | 0x20000);
            } else if (node instanceof ClassNode) {
                ClassNode cn = (ClassNode)node;
                cn.setModifiers(cn.getModifiers() | 0x20000);
            }
        }
    }

    private void visitOverride(AnnotatedNode node, AnnotationNode visited) {
        ClassNode annotationType = visited.getClassNode();
        if (annotationType.isResolved() && annotationType.getName().equals("java.lang.Override") && node instanceof MethodNode && !Boolean.TRUE.equals(node.getNodeMetaData("DEFAULT_PARAMETER_GENERATED"))) {
            boolean override = false;
            MethodNode origMethod = (MethodNode)node;
            ClassNode cNode = origMethod.getDeclaringClass();
            if (origMethod.hasDefaultValue()) {
                List<MethodNode> variants = cNode.getDeclaredMethods(origMethod.getName());
                for (MethodNode m : variants) {
                    if (!m.getAnnotations().contains(visited) || !ExtendedVerifier.isOverrideMethod(m)) continue;
                    override = true;
                    break;
                }
            } else {
                override = ExtendedVerifier.isOverrideMethod(origMethod);
            }
            if (!override) {
                this.addError("Method '" + origMethod.getName() + "' from class '" + cNode.getName() + "' does not override method from its superclass or interfaces but is annotated with @Override.", visited);
            }
        }
    }

    private static boolean isOverrideMethod(MethodNode method) {
        ClassNode declaringClass;
        ClassNode next = declaringClass = method.getDeclaringClass();
        block0: while (next != null) {
            Map<String, ClassNode> nextSpec = GenericsUtils.createGenericsSpec(next);
            MethodNode mn = GenericsUtils.correctToGenericsSpec(nextSpec, method);
            if (next != declaringClass && ExtendedVerifier.getDeclaredMethodCorrected(nextSpec, mn, next) != null) break;
            for (ClassNode face : GeneralUtils.getInterfacesAndSuperInterfaces(next)) {
                Map<String, ClassNode> faceSpec = GenericsUtils.createGenericsSpec(face, nextSpec);
                if (ExtendedVerifier.getDeclaredMethodCorrected(faceSpec, mn, face) == null) continue;
                break block0;
            }
            ClassNode superClass = next.getUnresolvedSuperClass();
            if (superClass != null) {
                next = GenericsUtils.correctToGenericsSpecRecurse(nextSpec, superClass);
                continue;
            }
            next = null;
        }
        return next != null;
    }

    private static MethodNode getDeclaredMethodCorrected(Map<String, ClassNode> genericsSpec, MethodNode mn, ClassNode cn) {
        for (MethodNode declared : cn.getDeclaredMethods(mn.getName())) {
            MethodNode corrected = GenericsUtils.correctToGenericsSpec(genericsSpec, declared);
            if (!ParameterUtils.parametersEqual(corrected.getParameters(), mn.getParameters())) continue;
            return corrected;
        }
        return null;
    }

    @Deprecated
    protected boolean isAnnotationCompatible() {
        return CompilerConfiguration.isPostJDK5(this.source.getConfiguration().getTargetBytecode());
    }
}

