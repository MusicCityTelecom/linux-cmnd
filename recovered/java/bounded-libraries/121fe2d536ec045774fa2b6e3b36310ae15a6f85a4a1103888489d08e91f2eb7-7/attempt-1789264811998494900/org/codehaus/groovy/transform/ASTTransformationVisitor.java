/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import groovy.lang.Tuple3;
import groovy.transform.CompilationUnitAware;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.control.ASTTransformationsContext;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SimpleMessage;
import org.codehaus.groovy.control.messages.WarningMessage;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.ASTTransformationCollectorCodeVisitor;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.TransformWithPriority;
import org.codehaus.groovy.util.URLStreams;

public final class ASTTransformationVisitor
extends ClassCodeVisitorSupport {
    private static final PriorityComparator priorityComparator = new PriorityComparator();
    private final ASTTransformationsContext context;
    private final CompilePhase phase;
    private SourceUnit source;
    private List<ASTNode[]> targetNodes;
    private Map<ASTNode, List<ASTTransformation>> transforms;
    private static final Tuple3<String, String, String> COMPILEDYNAMIC_AND_COMPILESTATIC_AND_TYPECHECKED = Tuple.tuple("groovy.transform.CompileDynamic", "groovy.transform.CompileStatic", "groovy.transform.TypeChecked");

    private ASTTransformationVisitor(CompilePhase phase, ASTTransformationsContext context) {
        this.phase = phase;
        this.context = context;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    @Override
    public void visitClass(ClassNode classNode) {
        Map<Class<? extends ASTTransformation>, Set<ASTNode>> baseTransforms = classNode.getTransforms(this.phase);
        if (!baseTransforms.isEmpty()) {
            HashMap<Class<? extends ASTTransformation>, ASTTransformation> transformInstances = new HashMap<Class<? extends ASTTransformation>, ASTTransformation>();
            for (Class<? extends ASTTransformation> clazz : baseTransforms.keySet()) {
                try {
                    transformInstances.put(clazz, clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                }
                catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
                    this.source.getErrorCollector().addError(new SimpleMessage("Could not instantiate Transformation Processor " + clazz, this.source));
                }
            }
            this.transforms = new HashMap<ASTNode, List<ASTTransformation>>();
            for (Map.Entry entry : baseTransforms.entrySet()) {
                for (ASTNode node : (Set)entry.getValue()) {
                    List list = this.transforms.computeIfAbsent(node, k -> new ArrayList());
                    list.add((ASTTransformation)transformInstances.get(entry.getKey()));
                }
            }
            this.targetNodes = new LinkedList<ASTNode[]>();
            super.visitClass(classNode);
            ArrayList<Tuple2<ASTTransformation, ASTNode[]>> tuples = new ArrayList<Tuple2<ASTTransformation, ASTNode[]>>();
            for (ASTNode[] aSTNodeArray : this.targetNodes) {
                for (ASTTransformation snt : this.transforms.get(aSTNodeArray[0])) {
                    tuples.add(new Tuple2<ASTTransformation, ASTNode[]>(snt, aSTNodeArray));
                }
            }
            tuples.sort(priorityComparator);
            for (Tuple2 tuple2 : tuples) {
                if (tuple2.getV1() instanceof CompilationUnitAware) {
                    ((CompilationUnitAware)tuple2.getV1()).setCompilationUnit(this.context.getCompilationUnit());
                }
                ((ASTTransformation)tuple2.getV1()).visit((ASTNode[])tuple2.getV2(), this.source);
            }
        }
    }

    @Override
    public void visitAnnotations(AnnotatedNode node) {
        super.visitAnnotations(node);
        for (AnnotationNode annotation : this.distinctAnnotations(node)) {
            if (!this.transforms.containsKey(annotation)) continue;
            this.targetNodes.add(new ASTNode[]{annotation, node});
        }
    }

    private List<AnnotationNode> distinctAnnotations(AnnotatedNode node) {
        LinkedList<AnnotationNode> result = new LinkedList<AnnotationNode>();
        AnnotationNode resultAnnotationNode = null;
        int resultIndex = -1;
        for (AnnotationNode annotationNode : node.getAnnotations()) {
            int index = COMPILEDYNAMIC_AND_COMPILESTATIC_AND_TYPECHECKED.indexOf(annotationNode.getClassNode().getName());
            if (-1 != index) {
                Expression value;
                if (1 == index && null != (value = annotationNode.getMember("value")) && "groovy.transform.TypeCheckingMode.SKIP".equals(value.getText())) {
                    index = 0;
                }
                if (null != resultAnnotationNode && index >= resultIndex) continue;
                resultAnnotationNode = annotationNode;
                resultIndex = index;
                continue;
            }
            result.add(annotationNode);
        }
        if (null != resultAnnotationNode) {
            result.add(resultAnnotationNode);
        }
        return result;
    }

    @Override
    public void visitProperty(PropertyNode node) {
    }

    public static void addPhaseOperations(CompilationUnit compilationUnit) {
        ASTTransformationsContext context = compilationUnit.getASTTransformationsContext();
        ASTTransformationVisitor.addGlobalTransforms(context);
        compilationUnit.addPhaseOperation((source, ignore, classNode) -> {
            ASTTransformationCollectorCodeVisitor visitor = new ASTTransformationCollectorCodeVisitor(source, compilationUnit.getTransformLoader());
            visitor.visitClass(classNode);
        }, 4);
        block3: for (CompilePhase phase : CompilePhase.values()) {
            switch (phase) {
                case INITIALIZATION: 
                case PARSING: 
                case CONVERSION: {
                    continue block3;
                }
                default: {
                    compilationUnit.addPhaseOperation((source, ignore, classNode) -> {
                        ASTTransformationVisitor visitor = new ASTTransformationVisitor(phase, context);
                        visitor.source = source;
                        visitor.visitClass(classNode);
                    }, phase.getPhaseNumber());
                }
            }
        }
    }

    public static void addNewPhaseOperation(CompilationUnit compilationUnit, SourceUnit sourceUnit, ClassNode classNode) {
        int phase = compilationUnit.getPhase();
        if (phase < 4) {
            return;
        }
        ASTTransformationCollectorCodeVisitor visitor = new ASTTransformationCollectorCodeVisitor(sourceUnit, compilationUnit.getTransformLoader());
        visitor.visitClass(classNode);
        compilationUnit.addNewPhaseOperation(source -> {
            if (source == sourceUnit) {
                ASTTransformationVisitor visitor = new ASTTransformationVisitor(CompilePhase.fromPhaseNumber(phase), compilationUnit.getASTTransformationsContext());
                visitor.source = source;
                visitor.visitClass(classNode);
            }
        }, phase);
    }

    public static void addGlobalTransformsAfterGrab(ASTTransformationsContext context) {
        ASTTransformationVisitor.doAddGlobalTransforms(context, false);
    }

    public static void addGlobalTransforms(ASTTransformationsContext context) {
        ASTTransformationVisitor.doAddGlobalTransforms(context, true);
    }

    private static void doAddGlobalTransforms(ASTTransformationsContext context, boolean isFirstScan) {
        CompilationUnit compilationUnit = context.getCompilationUnit();
        GroovyClassLoader transformLoader = compilationUnit.getTransformLoader();
        LinkedHashMap<String, URL> transformNames = new LinkedHashMap<String, URL>();
        try {
            Enumeration<URL> globalServices = transformLoader.getResources("META-INF/services/org.codehaus.groovy.transform.ASTTransformation");
            while (globalServices.hasMoreElements()) {
                String className;
                URL service = globalServices.nextElement();
                BufferedReader svcIn = new BufferedReader(new InputStreamReader(URLStreams.openUncachedStream(service), StandardCharsets.UTF_8));
                try {
                    className = svcIn.readLine();
                }
                catch (IOException ioe) {
                    compilationUnit.getErrorCollector().addError(new SimpleMessage("IOException reading the service definition at " + service.toExternalForm() + " because of exception " + ioe.toString(), null));
                    svcIn.close();
                    continue;
                }
                try {
                    Set<String> disabledGlobalTransforms = compilationUnit.getConfiguration().getDisabledGlobalASTTransformations();
                    if (disabledGlobalTransforms == null) {
                        disabledGlobalTransforms = Collections.emptySet();
                    }
                    while (className != null) {
                        if (!className.startsWith("#") && className.length() > 0 && !disabledGlobalTransforms.contains(className)) {
                            if (transformNames.containsKey(className)) {
                                try {
                                    if (!service.toURI().equals(((URL)transformNames.get(className)).toURI())) {
                                        compilationUnit.getErrorCollector().addWarning(2, "The global transform for class " + className + " is defined in both " + ((URL)transformNames.get(className)).toExternalForm() + " and " + service.toExternalForm() + " - the former definition will be used and the latter ignored.", null, null);
                                    }
                                }
                                catch (URISyntaxException e) {
                                    compilationUnit.getErrorCollector().addWarning(2, "Failed to parse URL as URI because of exception " + e.toString(), null, null);
                                }
                            } else {
                                transformNames.put(className, service);
                            }
                        }
                        try {
                            className = svcIn.readLine();
                        }
                        catch (IOException ioe) {
                            compilationUnit.getErrorCollector().addError(new SimpleMessage("IOException reading the service definition at " + service.toExternalForm() + " because of exception " + ioe.toString(), null));
                        }
                    }
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                    svcIn.close();
                }
            }
        }
        catch (IOException e) {
            compilationUnit.getErrorCollector().addError(new SimpleMessage("IO Exception attempting to load global transforms:" + e.getMessage(), null));
        }
        if (isFirstScan) {
            for (Map.Entry entry2 : transformNames.entrySet()) {
                context.getGlobalTransformNames().add((String)entry2.getKey());
            }
            ASTTransformationVisitor.addPhaseOperationsForGlobalTransforms(context.getCompilationUnit(), transformNames, isFirstScan);
        } else {
            transformNames.entrySet().removeIf(entry -> !context.getGlobalTransformNames().add((String)entry.getKey()));
            ASTTransformationVisitor.addPhaseOperationsForGlobalTransforms(context.getCompilationUnit(), transformNames, isFirstScan);
        }
    }

    private static void addPhaseOperationsForGlobalTransforms(CompilationUnit compilationUnit, Map<String, URL> transformNames, boolean isFirstScan) {
        GroovyClassLoader transformLoader = compilationUnit.getTransformLoader();
        for (Map.Entry<String, URL> entry : transformNames.entrySet()) {
            try {
                Class gTransClass = transformLoader.loadClass(entry.getKey(), false, true, false);
                GroovyASTTransformation transformAnnotation = gTransClass.getAnnotation(GroovyASTTransformation.class);
                if (transformAnnotation == null) {
                    compilationUnit.getErrorCollector().addWarning(new WarningMessage(2, "Transform Class " + entry.getKey() + " is specified as a global transform in " + entry.getValue().toExternalForm() + " but it is not annotated by " + GroovyASTTransformation.class.getName() + " the global transform associated with it may fail and cause the compilation to fail.", null, null));
                    continue;
                }
                if (ASTTransformation.class.isAssignableFrom(gTransClass)) {
                    ASTTransformation instance = (ASTTransformation)gTransClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    if (instance instanceof CompilationUnitAware) {
                        ((CompilationUnitAware)((Object)instance)).setCompilationUnit(compilationUnit);
                    }
                    CompilationUnit.ISourceUnitOperation suOp = source -> instance.visit(new ASTNode[]{source.getAST()}, source);
                    if (isFirstScan) {
                        compilationUnit.addPhaseOperation(suOp, transformAnnotation.phase().getPhaseNumber());
                        continue;
                    }
                    compilationUnit.addNewPhaseOperation(suOp, transformAnnotation.phase().getPhaseNumber());
                    continue;
                }
                compilationUnit.getErrorCollector().addError(new SimpleMessage("Transform Class " + entry.getKey() + " specified at " + entry.getValue().toExternalForm() + " is not an ASTTransformation.", null));
            }
            catch (Exception e) {
                Throwable effectiveException = e instanceof InvocationTargetException ? e.getCause() : e;
                compilationUnit.getErrorCollector().addError(new SimpleMessage("Could not instantiate global transform class " + entry.getKey() + " specified at " + entry.getValue().toExternalForm() + "  because of exception " + effectiveException.toString(), null));
            }
        }
    }

    private static class PriorityComparator
    implements Comparator<Tuple2<ASTTransformation, ASTNode[]>> {
        private PriorityComparator() {
        }

        @Override
        public int compare(Tuple2<ASTTransformation, ASTNode[]> o1, Tuple2<ASTTransformation, ASTNode[]> o2) {
            Integer i1 = 0;
            Integer i2 = 0;
            if (o1.getV1() instanceof TransformWithPriority) {
                i1 = ((TransformWithPriority)((Object)o1.getV1())).priority();
            }
            if (o2.getV1() instanceof TransformWithPriority) {
                i2 = ((TransformWithPriority)((Object)o2.getV1())).priority();
            }
            return i2.compareTo(i1);
        }
    }
}

