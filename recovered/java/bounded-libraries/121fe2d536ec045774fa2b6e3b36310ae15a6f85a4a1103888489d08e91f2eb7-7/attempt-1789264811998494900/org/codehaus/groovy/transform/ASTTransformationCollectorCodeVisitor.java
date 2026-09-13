/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.transform.AnnotationCollector;
import groovy.transform.AnnotationCollectorMode;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.codehaus.groovy.control.messages.SimpleMessage;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.AnnotationCollectorTransform;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;
import org.codehaus.groovy.transform.SealedASTTransformation;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.trait.TraitASTTransformation;
import org.codehaus.groovy.transform.trait.Traits;

public class ASTTransformationCollectorCodeVisitor
extends ClassCodeVisitorSupport {
    private ClassNode classNode;
    private final SourceUnit source;
    private final GroovyClassLoader transformLoader;

    public ASTTransformationCollectorCodeVisitor(SourceUnit source, GroovyClassLoader transformLoader) {
        this.source = source;
        this.transformLoader = transformLoader;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    @Override
    public void visitClass(ClassNode classNode) {
        ClassNode oldClass = this.classNode;
        this.classNode = classNode;
        super.visitClass(classNode);
        this.classNode = oldClass;
    }

    @Override
    public void visitAnnotations(AnnotatedNode node) {
        List<AnnotationNode> nodeAnnotations = node.getAnnotations();
        if (nodeAnnotations.isEmpty()) {
            return;
        }
        super.visitAnnotations(node);
        while (true) {
            LinkedHashMap<Integer, AnnotationCollectorMode> modes = new LinkedHashMap<Integer, AnnotationCollectorMode>();
            LinkedHashMap<Integer, List<AnnotationNode>> existing = new LinkedHashMap<Integer, List<AnnotationNode>>();
            LinkedHashMap<Integer, List<AnnotationNode>> replacements = new LinkedHashMap<Integer, List<AnnotationNode>>();
            int index = 0;
            for (AnnotationNode annotationNode : nodeAnnotations) {
                this.findCollectedAnnotations(annotationNode, node, index, modes, existing, replacements);
                ++index;
            }
            for (Map.Entry entry : replacements.entrySet()) {
                Integer replacementIndex = (Integer)entry.getKey();
                List annotationNodeList = (List)entry.getValue();
                ASTTransformationCollectorCodeVisitor.mergeCollectedAnnotations((AnnotationCollectorMode)((Object)modes.get(replacementIndex)), existing, annotationNodeList);
                existing.put(replacementIndex, annotationNodeList);
            }
            ArrayList mergedList = new ArrayList();
            existing.values().forEach(mergedList::addAll);
            if (mergedList.equals(nodeAnnotations)) break;
            nodeAnnotations.clear();
            nodeAnnotations.addAll(mergedList);
        }
        for (AnnotationNode annotation : nodeAnnotations) {
            this.addTransformsToClassNode(annotation);
        }
    }

    private static void mergeCollectedAnnotations(AnnotationCollectorMode mode, Map<Integer, List<AnnotationNode>> existing, List<AnnotationNode> replacements) {
        switch (mode) {
            case PREFER_COLLECTOR: {
                ASTTransformationCollectorCodeVisitor.deleteExisting(false, existing, replacements);
                break;
            }
            case PREFER_COLLECTOR_MERGED: {
                ASTTransformationCollectorCodeVisitor.deleteExisting(true, existing, replacements);
                break;
            }
            case PREFER_EXPLICIT: {
                ASTTransformationCollectorCodeVisitor.deleteReplacement(false, existing, replacements);
                break;
            }
            case PREFER_EXPLICIT_MERGED: {
                ASTTransformationCollectorCodeVisitor.deleteReplacement(true, existing, replacements);
                break;
            }
        }
    }

    private static void deleteExisting(boolean mergeParams, Map<Integer, List<AnnotationNode>> existing, List<AnnotationNode> replacements) {
        for (AnnotationNode replacement : replacements) {
            for (Map.Entry<Integer, List<AnnotationNode>> entry : existing.entrySet()) {
                ArrayList annotationNodes = new ArrayList(entry.getValue());
                Iterator iterator = annotationNodes.iterator();
                while (iterator.hasNext()) {
                    AnnotationNode annotation = (AnnotationNode)iterator.next();
                    if (!replacement.getClassNode().getName().equals(annotation.getClassNode().getName())) continue;
                    if (mergeParams) {
                        ASTTransformationCollectorCodeVisitor.mergeParameters(replacement, annotation);
                    }
                    iterator.remove();
                }
                existing.put(entry.getKey(), annotationNodes);
            }
        }
    }

    private static void deleteReplacement(boolean mergeParams, Map<Integer, List<AnnotationNode>> existing, List<AnnotationNode> replacements) {
        Iterator<AnnotationNode> nodeIterator = replacements.iterator();
        while (nodeIterator.hasNext()) {
            boolean remove = false;
            AnnotationNode replacement = nodeIterator.next();
            for (Map.Entry<Integer, List<AnnotationNode>> entry : existing.entrySet()) {
                for (AnnotationNode annotation : entry.getValue()) {
                    if (!replacement.getClassNode().getName().equals(annotation.getClassNode().getName())) continue;
                    if (mergeParams) {
                        ASTTransformationCollectorCodeVisitor.mergeParameters(annotation, replacement);
                    }
                    remove = true;
                }
            }
            if (!remove) continue;
            nodeIterator.remove();
        }
    }

    private static void mergeParameters(AnnotationNode to, AnnotationNode from) {
        for (String name : from.getMembers().keySet()) {
            if (to.getMember(name) != null) continue;
            to.setMember(name, from.getMember(name));
        }
    }

    private void findCollectedAnnotations(AnnotationNode alias, AnnotatedNode origin, Integer index, Map<Integer, AnnotationCollectorMode> modes, Map<Integer, List<AnnotationNode>> existing, Map<Integer, List<AnnotationNode>> replacements) {
        for (AnnotationNode annotation : alias.getClassNode().getAnnotations()) {
            if (!annotation.getClassNode().getName().equals(AnnotationCollector.class.getName())) continue;
            Expression mode = annotation.getMember("mode");
            modes.put(index, Optional.ofNullable(mode).map(exp -> StaticTypeCheckingSupport.evaluateExpression(exp, this.source.getConfiguration())).map(val -> (AnnotationCollectorMode)((Object)((Object)val))).orElse(AnnotationCollectorMode.DUPLICATE));
            Expression processor = annotation.getMember("processor");
            AnnotationCollectorTransform act = null;
            if (processor != null) {
                String className = (String)StaticTypeCheckingSupport.evaluateExpression(processor, this.source.getConfiguration());
                Class<?> klass = this.loadTransformClass(className, alias);
                if (klass != null) {
                    try {
                        act = (AnnotationCollectorTransform)klass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (ReflectiveOperationException | RuntimeException e) {
                        this.source.getErrorCollector().addErrorAndContinue(new ExceptionMessage(e, true, this.source));
                    }
                }
            } else {
                act = new AnnotationCollectorTransform();
            }
            if (act == null) continue;
            List<AnnotationNode> result = act.visit(annotation, alias, origin, this.source);
            replacements.put(index, result);
            return;
        }
        if (!replacements.containsKey(index)) {
            existing.put(index, Collections.singletonList(alias));
        }
    }

    private Class<?> loadTransformClass(String transformClass, AnnotationNode annotation) {
        try {
            return this.transformLoader.loadClass(transformClass, false, true, false);
        }
        catch (LinkageError | ReflectiveOperationException e) {
            String error = "Could not find class for Transformation Processor " + transformClass + " declared by " + annotation.getClassNode().getName();
            this.source.getErrorCollector().addErrorAndContinue(new SimpleMessage(error, this.source));
            return null;
        }
    }

    private void addTransformsToClassNode(AnnotationNode annotation) {
        Annotation transformClassAnnotation = ASTTransformationCollectorCodeVisitor.getTransformClassAnnotation(annotation.getClassNode());
        if (transformClassAnnotation != null) {
            try {
                String error;
                Method classesMethod;
                Class[] transformClasses;
                Method valueMethod = transformClassAnnotation.getClass().getMethod("value", new Class[0]);
                String[] transformClassNames = (String[])valueMethod.invoke(transformClassAnnotation, new Object[0]);
                if (transformClassNames == null) {
                    transformClassNames = new String[]{};
                }
                if ((transformClasses = (Class[])(classesMethod = transformClassAnnotation.getClass().getMethod("classes", new Class[0])).invoke(transformClassAnnotation, new Object[0])) == null) {
                    transformClasses = new Class[]{};
                }
                if (transformClassNames.length == 0 && transformClasses.length == 0) {
                    error = "@GroovyASTTransformationClass in " + annotation.getClassNode().getName() + " does not specify any transform class names or types";
                    this.source.getErrorCollector().addError(new SimpleMessage(error, this.source));
                }
                if (transformClassNames.length > 0 && transformClasses.length > 0) {
                    error = "@GroovyASTTransformationClass in " + annotation.getClassNode().getName() + " should specify transforms by name or by type, not by both";
                    this.source.getErrorCollector().addError(new SimpleMessage(error, this.source));
                }
                Stream.concat(Stream.of(transformClassNames), Stream.of(transformClasses).map(Class::getName)).map(transformClassName -> this.loadTransformClass((String)transformClassName, annotation)).filter(Objects::nonNull).forEach(transformClass -> this.verifyAndAddTransform(annotation, (Class<?>)transformClass));
            }
            catch (ReflectiveOperationException | RuntimeException e) {
                this.source.getErrorCollector().addError(new ExceptionMessage(e, true, this.source));
            }
        }
    }

    private static Annotation getTransformClassAnnotation(ClassNode annotationType) {
        if (!annotationType.isResolved()) {
            return null;
        }
        for (Annotation a : annotationType.getTypeClass().getAnnotations()) {
            if (!a.annotationType().getName().equals(GroovyASTTransformationClass.class.getName())) continue;
            return a;
        }
        return null;
    }

    private void verifyAndAddTransform(AnnotationNode annotation, Class<?> transformClass) {
        CompilePhase specifiedCompilePhase;
        GroovyASTTransformation transformationClass;
        if (!ASTTransformation.class.isAssignableFrom(transformClass)) {
            String error = "Not an ASTTransformation: " + transformClass.getName() + " declared by " + annotation.getClassNode().getName();
            this.source.getErrorCollector().addError(new SimpleMessage(error, this.source));
        }
        if ((transformationClass = transformClass.getAnnotation(GroovyASTTransformation.class)) == null) {
            String error = "AST transformation implementation classes must be annotated with " + GroovyASTTransformation.class.getName() + ". " + transformClass.getName() + " lacks this annotation.";
            this.source.getErrorCollector().addError(new SimpleMessage(error, this.source));
        }
        if ((specifiedCompilePhase = transformationClass.phase()).getPhaseNumber() < CompilePhase.SEMANTIC_ANALYSIS.getPhaseNumber()) {
            String error = annotation.getClassNode().getName() + " is defined to be run in compile phase " + (Object)((Object)specifiedCompilePhase) + ". Local AST transformations must run in SEMANTIC_ANALYSIS or later!";
            this.source.getErrorCollector().addError(new SimpleMessage(error, this.source));
        }
        if (!Traits.isTrait(this.classNode) || transformClass == TraitASTTransformation.class || transformClass == SealedASTTransformation.class) {
            this.classNode.addTransform(transformClass, annotation);
        }
    }
}

