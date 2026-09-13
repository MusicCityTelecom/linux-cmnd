/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.transform.CompilationUnitAware;
import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.ClassWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import javax.tools.JavaFileObject;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.ClassCompletionVerifier;
import org.codehaus.groovy.classgen.EnumCompletionVisitor;
import org.codehaus.groovy.classgen.EnumVisitor;
import org.codehaus.groovy.classgen.ExtendedVerifier;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.InnerClassCompletionVisitor;
import org.codehaus.groovy.classgen.InnerClassVisitor;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.control.ASTTransformationsContext;
import org.codehaus.groovy.control.ClassNodeResolver;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.GenericsVisitor;
import org.codehaus.groovy.control.InstanceOfVerifier;
import org.codehaus.groovy.control.LabelVerifier;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.OptimizerVisitor;
import org.codehaus.groovy.control.ProcessingUnit;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.StaticImportVisitor;
import org.codehaus.groovy.control.StaticVerifier;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.io.InputStreamReaderSource;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.syntax.RuntimeParserException;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.tools.GroovyClass;
import org.codehaus.groovy.transform.ASTTransformationVisitor;
import org.codehaus.groovy.transform.AnnotationCollectorTransform;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.trait.TraitComposer;

public class CompilationUnit
extends ProcessingUnit {
    protected CompileUnit ast;
    protected Map<String, SourceUnit> sources = new LinkedHashMap<String, SourceUnit>();
    protected Queue<SourceUnit> queuedSources = new LinkedList<SourceUnit>();
    private List<GroovyClass> generatedClasses = new ArrayList<GroovyClass>();
    private Deque<PhaseOperation>[] phaseOperations;
    private Deque<PhaseOperation>[] newPhaseOperations;
    protected boolean debug;
    protected boolean configured;
    protected ClassgenCallback classgenCallback;
    protected ProgressCallback progressCallback;
    protected ClassNodeResolver classNodeResolver;
    protected ResolveVisitor resolveVisitor;
    protected ASTTransformationsContext astTransformationsContext;
    private Set<JavaFileObject> javaCompilationUnitSet;
    private final IPrimaryClassNodeOperation classgen;

    public CompilationUnit() {
        this((CompilerConfiguration)null, (CodeSource)null, (GroovyClassLoader)null);
    }

    public CompilationUnit(GroovyClassLoader loader) {
        this(null, null, loader);
    }

    public CompilationUnit(CompilerConfiguration configuration) {
        this(configuration, (CodeSource)null, (GroovyClassLoader)null);
    }

    public CompilationUnit(CompilerConfiguration configuration, CodeSource codeSource, GroovyClassLoader loader) {
        this(configuration, codeSource, loader, null);
    }

    public CompilationUnit(CompilerConfiguration configuration, CodeSource codeSource, GroovyClassLoader loader, GroovyClassLoader transformLoader) {
        super(configuration, loader, null);
        int n = 10;
        this.phaseOperations = new Deque[10];
        this.newPhaseOperations = new Deque[10];
        for (int i = 0; i < 10; ++i) {
            this.phaseOperations[i] = new LinkedList<PhaseOperation>();
            this.newPhaseOperations[i] = new LinkedList<PhaseOperation>();
        }
        this.classNodeResolver = new ClassNodeResolver();
        this.resolveVisitor = new ResolveVisitor(this);
        this.javaCompilationUnitSet = new HashSet<JavaFileObject>();
        this.classgen = new IPrimaryClassNodeOperation(){

            @Override
            public void call(final SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
                String sourceName;
                new OptimizerVisitor(CompilationUnit.this).visitClass(classNode, source);
                GroovyClassVisitor visitor = new Verifier();
                try {
                    visitor.visitClass(classNode);
                }
                catch (RuntimeParserException rpe) {
                    CompilationUnit.this.getErrorCollector().addError(new SyntaxException(rpe.getMessage(), rpe.getNode()), source);
                }
                visitor = new LabelVerifier(source);
                visitor.visitClass(classNode);
                visitor = new InstanceOfVerifier(){

                    @Override
                    protected SourceUnit getSourceUnit() {
                        return source;
                    }
                };
                visitor.visitClass(classNode);
                visitor = new ClassCompletionVerifier(source);
                visitor.visitClass(classNode);
                visitor = new ExtendedVerifier(source);
                visitor.visitClass(classNode);
                CompilationUnit.this.getErrorCollector().failIfErrors();
                ClassVisitor classVisitor = CompilationUnit.this.createClassVisitor();
                String string = sourceName = source == null ? classNode.getModule().getDescription() : source.getName();
                if (sourceName != null) {
                    sourceName = sourceName.substring(Math.max(sourceName.lastIndexOf(92), sourceName.lastIndexOf(47)) + 1);
                }
                visitor = new AsmClassGenerator(source, context, classVisitor, sourceName);
                visitor.visitClass(classNode);
                byte[] bytes = ((ClassWriter)classVisitor).toByteArray();
                CompilationUnit.this.getClasses().add(new GroovyClass(classNode.getName(), bytes));
                if (CompilationUnit.this.classgenCallback != null) {
                    CompilationUnit.this.classgenCallback.call(classVisitor, classNode);
                }
                LinkedList<ClassNode> innerClasses = ((AsmClassGenerator)visitor).getInnerClasses();
                while (!innerClasses.isEmpty()) {
                    CompilationUnit.this.classgen.call(source, context, innerClasses.removeFirst());
                }
            }

            @Override
            public boolean needSortedInput() {
                return true;
            }
        };
        this.astTransformationsContext = new ASTTransformationsContext(this, transformLoader);
        this.ast = new CompileUnit(this.getClassLoader(), codeSource, this.getConfiguration());
        this.addPhaseOperations();
        this.applyCompilationCustomizers();
    }

    private void addPhaseOperations() {
        this.addPhaseOperation(SourceUnit::parse, 2);
        this.addPhaseOperation((SourceUnit source) -> {
            source.convert();
            this.getAST().addModule(source.getAST());
            Optional.ofNullable(this.getProgressCallback()).ifPresent(callback -> callback.call(source, this.getPhase()));
        }, 3);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            EnumVisitor visitor = new EnumVisitor(this, source);
            visitor.visitClass(classNode);
        }, 3);
        this.addPhaseOperation((SourceUnit source) -> {
            try {
                this.resolveVisitor.phase = 1;
                this.resolveVisitor.setClassNodeResolver(this.classNodeResolver);
                for (ClassNode classNode : source.getAST().getClasses()) {
                    this.resolveVisitor.startResolving(classNode, source);
                }
            }
            finally {
                this.resolveVisitor.phase = 0;
            }
        }, 4);
        this.addPhaseOperation((SourceUnit source) -> {
            try {
                this.resolveVisitor.phase = 2;
                for (ClassNode classNode : source.getAST().getClasses()) {
                    this.resolveVisitor.startResolving(classNode, source);
                }
            }
            finally {
                this.resolveVisitor.phase = 0;
            }
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            StaticImportVisitor visitor = new StaticImportVisitor(classNode, source);
            visitor.visitClass(classNode);
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            InnerClassVisitor visitor = new InnerClassVisitor(this, source);
            visitor.visitClass(classNode);
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            if (!classNode.isSynthetic()) {
                GenericsVisitor visitor = new GenericsVisitor(source);
                visitor.visitClass(classNode);
            }
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            AnnotationCollectorTransform.ClassChanger xformer = new AnnotationCollectorTransform.ClassChanger();
            xformer.transformClass(classNode);
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> TraitComposer.doExtendTraits(classNode, source, this), 5);
        this.addPhaseOperation((SourceUnit source) -> {
            List<ClassNode> classes = source.getAST().getClasses();
            for (ClassNode node : classes) {
                CompileUnit cu = node.getCompileUnit();
                Iterator<String> it = cu.getClassesToCompile().keySet().iterator();
                while (it.hasNext()) {
                    String name = it.next();
                    StringBuilder message = new StringBuilder("Compilation incomplete: expected to find the class ").append(name).append(" in ").append(source.getName()).append(", but the file ");
                    if (classes.isEmpty()) {
                        message.append("seems not to contain any classes");
                    } else {
                        message.append("contains the classes: ");
                        boolean first = true;
                        for (ClassNode cn : classes) {
                            if (first) {
                                first = false;
                            } else {
                                message.append(", ");
                            }
                            message.append(cn.getName());
                        }
                    }
                    this.getErrorCollector().addErrorAndContinue(Message.create(message.toString(), this));
                    it.remove();
                }
            }
        }, 5);
        this.addPhaseOperation((SourceUnit source) -> {
            for (ClassNode cn : source.getAST().getClasses()) {
                if (cn.isInterface() || cn.isDerivedFromGroovyObject()) continue;
                boolean cs = false;
                boolean pojo = false;
                boolean trait = false;
                for (AnnotationNode an : cn.getAnnotations()) {
                    switch (an.getClassNode().getName()) {
                        case "groovy.transform.CompileStatic": {
                            cs = true;
                            break;
                        }
                        case "groovy.transform.stc.POJO": {
                            pojo = true;
                            break;
                        }
                        case "groovy.transform.Trait": {
                            trait = true;
                        }
                    }
                }
                if (cs && pojo || trait) continue;
                cn.addInterface(ClassHelper.GROOVY_OBJECT_TYPE);
            }
        }, 6);
        this.addPhaseOperation(this.classgen, 7);
        this.addPhaseOperation((GroovyClass groovyClass) -> {
            String name = groovyClass.getName().replace('.', File.separatorChar) + ".class";
            File path = new File(this.getConfiguration().getTargetDirectory(), name);
            File directory = path.getParentFile();
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            try (FileOutputStream stream = new FileOutputStream(path);){
                stream.write(groovyClass.getBytes());
            }
            catch (IOException e) {
                this.getErrorCollector().addError(Message.create(e.getMessage(), this));
            }
        });
        ASTTransformationVisitor.addPhaseOperations(this);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            StaticVerifier verifier = new StaticVerifier();
            verifier.visitClass(classNode, source);
        }, 4);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            ClassCodeVisitorSupport visitor = new InnerClassCompletionVisitor(this, source);
            visitor.visitClass(classNode);
            visitor = new EnumCompletionVisitor(this, source);
            visitor.visitClass(classNode);
        }, 5);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            Object callback = classNode.getNodeMetaData((Object)StaticCompilationMetadataKeys.DYNAMIC_OUTER_NODE_CALLBACK);
            if (callback instanceof IPrimaryClassNodeOperation) {
                ((IPrimaryClassNodeOperation)callback).call(source, context, classNode);
                classNode.removeNodeMetaData((Object)StaticCompilationMetadataKeys.DYNAMIC_OUTER_NODE_CALLBACK);
            }
        }, 6);
        this.addPhaseOperation((SourceUnit source, GeneratorContext context, ClassNode classNode) -> {
            ClassCodeExpressionTransformer visitor = new ClassCodeExpressionTransformer(){

                @Override
                protected SourceUnit getSourceUnit() {
                    return source;
                }

                @Override
                public Expression transform(Expression expression) {
                    ClassNode enumType;
                    if (expression instanceof VariableExpression && (enumType = (ClassNode)expression.getNodeMetaData((Object)StaticTypesMarker.SWITCH_CONDITION_EXPRESSION_TYPE)) != null) {
                        PropertyExpression propertyExpression = GeneralUtils.propX((Expression)GeneralUtils.classX(enumType), expression.getText());
                        1.setSourcePosition(propertyExpression, expression);
                        return propertyExpression;
                    }
                    return expression;
                }
            };
            visitor.visitClass(classNode);
        }, 6);
    }

    private void applyCompilationCustomizers() {
        for (CompilationCustomizer customizer : this.getConfiguration().getCompilationCustomizers()) {
            if (customizer instanceof CompilationUnitAware) {
                ((CompilationUnitAware)((Object)customizer)).setCompilationUnit(this);
            }
            this.addPhaseOperation(customizer, customizer.getPhase().getPhaseNumber());
        }
    }

    public void addPhaseOperation(IGroovyClassOperation op) {
        this.phaseOperations[8].addFirst(op);
    }

    public void addPhaseOperation(ISourceUnitOperation op, int phase) {
        CompilationUnit.validatePhase(phase);
        this.phaseOperations[phase].add(op);
    }

    public void addPhaseOperation(IPrimaryClassNodeOperation op, int phase) {
        CompilationUnit.validatePhase(phase);
        this.phaseOperations[phase].add(op);
    }

    public void addFirstPhaseOperation(IPrimaryClassNodeOperation op, int phase) {
        CompilationUnit.validatePhase(phase);
        this.phaseOperations[phase].addFirst(op);
    }

    public void addNewPhaseOperation(ISourceUnitOperation op, int phase) {
        CompilationUnit.validatePhase(phase);
        this.newPhaseOperations[phase].add(op);
    }

    private static void validatePhase(int phase) {
        if (phase < 1 || phase > 9) {
            throw new IllegalArgumentException("phase " + phase + " is unknown");
        }
    }

    @Override
    public void configure(CompilerConfiguration configuration) {
        super.configure(configuration);
        this.debug = this.getConfiguration().getDebug();
        this.configured = true;
    }

    public CompileUnit getAST() {
        return this.ast;
    }

    public List<GroovyClass> getClasses() {
        return this.generatedClasses;
    }

    public ClassNode getFirstClassNode() {
        return this.getAST().getModules().get(0).getClasses().get(0);
    }

    public ClassNode getClassNode(String name) {
        ClassNode[] result;
        block2: {
            result = new ClassNode[1];
            IPrimaryClassNodeOperation handler = (source, context, classNode) -> {
                if (classNode.getName().equals(name)) {
                    result[0] = classNode;
                }
            };
            try {
                handler.doPhaseOperation(this);
            }
            catch (CompilationFailedException e) {
                if (!this.debug) break block2;
                e.printStackTrace();
            }
        }
        return result[0];
    }

    public ASTTransformationsContext getASTTransformationsContext() {
        return this.astTransformationsContext;
    }

    public ClassNodeResolver getClassNodeResolver() {
        return this.classNodeResolver;
    }

    public void setClassNodeResolver(ClassNodeResolver classNodeResolver) {
        this.classNodeResolver = classNodeResolver;
    }

    public Set<JavaFileObject> getJavaCompilationUnitSet() {
        return this.javaCompilationUnitSet;
    }

    public void addJavaCompilationUnits(Set<JavaFileObject> javaCompilationUnitSet) {
        this.javaCompilationUnitSet.addAll(javaCompilationUnitSet);
    }

    public GroovyClassLoader getTransformLoader() {
        return Optional.ofNullable(this.getASTTransformationsContext().getTransformLoader()).orElseGet(this::getClassLoader);
    }

    public void addSources(String[] paths) {
        for (String path : paths) {
            this.addSource(new File(path));
        }
    }

    public void addSources(File[] files) {
        for (File file : files) {
            this.addSource(file);
        }
    }

    public SourceUnit addSource(File file) {
        return this.addSource(new SourceUnit(file, this.getConfiguration(), this.getClassLoader(), this.getErrorCollector()));
    }

    public SourceUnit addSource(URL url) {
        return this.addSource(new SourceUnit(url, this.getConfiguration(), this.getClassLoader(), this.getErrorCollector()));
    }

    public SourceUnit addSource(String name, InputStream stream) {
        InputStreamReaderSource source = new InputStreamReaderSource(stream, this.getConfiguration());
        return this.addSource(new SourceUnit(name, source, this.getConfiguration(), this.getClassLoader(), this.getErrorCollector()));
    }

    public SourceUnit addSource(String name, String scriptText) {
        return this.addSource(new SourceUnit(name, scriptText, this.getConfiguration(), this.getClassLoader(), this.getErrorCollector()));
    }

    public SourceUnit addSource(SourceUnit source) {
        String name = source.getName();
        source.setClassLoader(this.getClassLoader());
        for (SourceUnit su : this.queuedSources) {
            if (!name.equals(su.getName())) continue;
            return su;
        }
        this.queuedSources.add(source);
        return source;
    }

    public Iterator<SourceUnit> iterator() {
        return new Iterator<SourceUnit>(){
            private Iterator<String> nameIterator;
            {
                this.nameIterator = CompilationUnit.this.sources.keySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return this.nameIterator.hasNext();
            }

            @Override
            public SourceUnit next() {
                String name = this.nameIterator.next();
                return CompilationUnit.this.sources.get(name);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void addClassNode(ClassNode node) {
        ModuleNode module = new ModuleNode(this.getAST());
        this.getAST().addModule(module);
        module.addClass(node);
    }

    public ClassgenCallback getClassgenCallback() {
        return this.classgenCallback;
    }

    public void setClassgenCallback(ClassgenCallback visitor) {
        this.classgenCallback = visitor;
    }

    public ProgressCallback getProgressCallback() {
        return this.progressCallback;
    }

    public void setProgressCallback(ProgressCallback callback) {
        this.progressCallback = callback;
    }

    public void compile() throws CompilationFailedException {
        this.compile(9);
    }

    public void compile(int throughPhase) throws CompilationFailedException {
        this.gotoPhase(1);
        throughPhase = Math.min(throughPhase, 9);
        while (throughPhase >= this.phase && this.phase <= 9) {
            block4: {
                if (this.phase == 3) {
                    (this.sources.size() > 1 && Boolean.TRUE.equals(this.configuration.getOptimizationOptions().get("parallelParse")) ? this.sources.values().parallelStream() : this.sources.values().stream()).forEach(SourceUnit::buildAST);
                }
                try {
                    this.processPhaseOperations(this.phase);
                }
                catch (ResolveVisitor.Interrupt x) {
                    if ($assertionsDisabled || !this.queuedSources.isEmpty()) break block4;
                    throw new AssertionError();
                }
            }
            if (this.dequeued()) continue;
            this.processNewPhaseOperations(this.phase);
            Optional.ofNullable(this.getProgressCallback()).ifPresent(callback -> callback.call(this, this.phase));
            this.completePhase();
            this.mark();
            this.gotoPhase(this.phase + 1);
            if (this.phase != 7) continue;
            this.getAST().getModules().forEach(ModuleNode::sortClasses);
        }
        this.getErrorCollector().failIfErrors();
    }

    private void processPhaseOperations(int phase) {
        for (PhaseOperation op : this.phaseOperations[phase]) {
            op.doPhaseOperation(this);
        }
    }

    private void processNewPhaseOperations(int phase) {
        this.recordPhaseOpsInAllOtherPhases(phase);
        Deque<PhaseOperation> currentPhaseNewOps = this.newPhaseOperations[phase];
        while (!currentPhaseNewOps.isEmpty()) {
            PhaseOperation operation = currentPhaseNewOps.removeFirst();
            this.phaseOperations[phase].add(operation);
            operation.doPhaseOperation(this);
            this.recordPhaseOpsInAllOtherPhases(phase);
            currentPhaseNewOps = this.newPhaseOperations[phase];
        }
    }

    private void recordPhaseOpsInAllOtherPhases(int phase) {
        for (int ph = 1; ph <= 9; ++ph) {
            if (ph == phase || this.newPhaseOperations[ph].isEmpty()) continue;
            this.phaseOperations[ph].addAll(this.newPhaseOperations[ph]);
            this.newPhaseOperations[ph].clear();
        }
    }

    protected boolean dequeued() throws CompilationFailedException {
        if (!this.queuedSources.isEmpty()) {
            SourceUnit unit;
            while ((unit = this.queuedSources.poll()) != null) {
                this.sources.put(unit.getName(), unit);
            }
            this.gotoPhase(1);
            return true;
        }
        return false;
    }

    protected ClassVisitor createClassVisitor() {
        return new ClassWriter(3){

            private ClassNode getClassNode(String name) {
                CompileUnit cu = CompilationUnit.this.getAST();
                ClassNode cn = cu.getClass(name);
                if (cn != null) {
                    return cn;
                }
                cn = cu.getGeneratedInnerClass(name);
                if (cn != null) {
                    return cn;
                }
                ClassNodeResolver.LookupResult lookupResult = CompilationUnit.this.getClassNodeResolver().resolveName(name, CompilationUnit.this);
                return lookupResult == null ? null : lookupResult.getClassNode();
            }

            private ClassNode getCommonSuperClassNode(ClassNode c, ClassNode d) {
                if (c.isDerivedFrom(d)) {
                    return d;
                }
                if (d.isDerivedFrom(c)) {
                    return c;
                }
                if (c.isInterface() || d.isInterface()) {
                    return ClassHelper.OBJECT_TYPE;
                }
                while ((c = c.getSuperClass()) != null && !d.isDerivedFrom(c)) {
                }
                if (c == null) {
                    return ClassHelper.OBJECT_TYPE;
                }
                return c;
            }

            @Override
            protected String getCommonSuperClass(String arg1, String arg2) {
                ClassNode a = this.getClassNode(arg1.replace('/', '.'));
                ClassNode b = this.getClassNode(arg2.replace('/', '.'));
                return this.getCommonSuperClassNode(a, b).getName().replace('.', '/');
            }
        };
    }

    protected void mark() throws CompilationFailedException {
        ISourceUnitOperation mark = source -> {
            if (source.phase < this.phase) {
                source.gotoPhase(this.phase);
            }
            if (source.phase == this.phase && this.phaseComplete && !source.phaseComplete) {
                source.completePhase();
            }
        };
        mark.doPhaseOperation(this);
    }

    private static int getSuperClassCount(ClassNode classNode) {
        int count = 0;
        while (classNode != null) {
            ++count;
            classNode = classNode.getSuperClass();
        }
        return count;
    }

    private static int getSuperInterfaceCount(ClassNode classNode) {
        int count = 1;
        for (ClassNode face : classNode.getInterfaces()) {
            count = Math.max(count, CompilationUnit.getSuperInterfaceCount(face) + 1);
        }
        return count;
    }

    private List<ClassNode> getPrimaryClassNodes(boolean sort) {
        List<ClassNode> unsorted = this.getAST().getModules().stream().flatMap(module -> module.getClasses().stream()).collect(Collectors.toList());
        if (!sort) {
            return unsorted;
        }
        int n = unsorted.size();
        int[] indexClass = new int[n];
        int[] indexInterface = new int[n];
        int i = 0;
        for (ClassNode element : unsorted) {
            if (element.isInterface()) {
                indexInterface[i] = CompilationUnit.getSuperInterfaceCount(element);
                indexClass[i] = -1;
            } else {
                indexClass[i] = CompilationUnit.getSuperClassCount(element);
                indexInterface[i] = -1;
            }
            ++i;
        }
        List<ClassNode> sorted = CompilationUnit.getSorted(indexInterface, unsorted);
        sorted.addAll(CompilationUnit.getSorted(indexClass, unsorted));
        return sorted;
    }

    private static List<ClassNode> getSorted(int[] index, List<ClassNode> unsorted) {
        int unsortedSize = unsorted.size();
        ArrayList<ClassNode> sorted = new ArrayList<ClassNode>(unsortedSize);
        for (int i = 0; i < unsortedSize; ++i) {
            int min = -1;
            for (int j = 0; j < unsortedSize; ++j) {
                if (index[j] == -1 || min != -1 && index[j] >= index[min]) continue;
                min = j;
            }
            if (min == -1) break;
            sorted.add(unsorted.get(min));
            index[min] = -1;
        }
        return sorted;
    }

    private void changeBugText(GroovyBugError e, SourceUnit context) {
        e.setBugText("exception in phase '" + this.getPhaseDescription() + "' in source unit '" + (context != null ? context.getName() : "?") + "' " + e.getBugText());
    }

    @Deprecated
    public void addPhaseOperation(GroovyClassOperation op) {
        this.addPhaseOperation((IGroovyClassOperation)op);
    }

    @Deprecated
    public void addPhaseOperation(SourceUnitOperation op, int phase) {
        this.addPhaseOperation((ISourceUnitOperation)op, phase);
    }

    @Deprecated
    public void addPhaseOperation(PrimaryClassNodeOperation op, int phase) {
        this.addPhaseOperation((IPrimaryClassNodeOperation)op, phase);
    }

    @Deprecated
    public void addFirstPhaseOperation(PrimaryClassNodeOperation op, int phase) {
        this.addFirstPhaseOperation((IPrimaryClassNodeOperation)op, phase);
    }

    @Deprecated
    public void addNewPhaseOperation(SourceUnitOperation op, int phase) {
        this.addNewPhaseOperation((ISourceUnitOperation)op, phase);
    }

    @Deprecated
    public void applyToSourceUnits(SourceUnitOperation op) throws CompilationFailedException {
        op.doPhaseOperation(this);
    }

    @Deprecated
    public void applyToPrimaryClassNodes(PrimaryClassNodeOperation op) throws CompilationFailedException {
        op.doPhaseOperation(this);
    }

    @FunctionalInterface
    public static interface IPrimaryClassNodeOperation
    extends PhaseOperation {
        public void call(SourceUnit var1, GeneratorContext var2, ClassNode var3) throws CompilationFailedException;

        @Override
        default public void doPhaseOperation(CompilationUnit unit) throws CompilationFailedException {
            for (ClassNode classNode : unit.getPrimaryClassNodes(this.needSortedInput())) {
                SourceUnit context = null;
                try {
                    context = classNode.getModule().getContext();
                    if (context != null && context.phase >= unit.phase && (context.phase != unit.phase || context.phaseComplete)) continue;
                    int offset = 1;
                    Iterator<InnerClassNode> it = classNode.getInnerClasses();
                    while (it.hasNext()) {
                        it.next();
                        ++offset;
                    }
                    this.call(context, new GeneratorContext(unit.getAST(), offset), classNode);
                }
                catch (CompilationFailedException offset) {
                }
                catch (NullPointerException npe) {
                    GroovyBugError gbe = new GroovyBugError("unexpected NullPointerException", npe);
                    unit.changeBugText(gbe, context);
                    throw gbe;
                }
                catch (GroovyBugError e) {
                    unit.changeBugText(e, context);
                    throw e;
                }
                catch (Exception | LinkageError e) {
                    ErrorCollector errorCollector = null;
                    for (Throwable t = e.getCause(); t != e && t != null; t = t.getCause()) {
                        if (!(t instanceof MultipleCompilationErrorsException)) continue;
                        errorCollector = ((MultipleCompilationErrorsException)t).getErrorCollector();
                        break;
                    }
                    if (errorCollector != null) {
                        unit.getErrorCollector().addCollectorContents(errorCollector);
                        continue;
                    }
                    if (e instanceof GroovyRuntimeException) {
                        GroovyRuntimeException gre = (GroovyRuntimeException)e;
                        context = Optional.ofNullable(gre.getModule()).map(ModuleNode::getContext).orElse(context);
                    }
                    if (context != null) {
                        if (e instanceof SyntaxException) {
                            unit.getErrorCollector().addError((SyntaxException)e, context);
                            continue;
                        }
                        if (e.getCause() instanceof SyntaxException) {
                            unit.getErrorCollector().addError((SyntaxException)e.getCause(), context);
                            continue;
                        }
                        unit.getErrorCollector().addException(e instanceof Exception ? (Exception)e : new RuntimeException(e), context);
                        continue;
                    }
                    unit.getErrorCollector().addError(new ExceptionMessage(e instanceof Exception ? (Exception)e : new RuntimeException(e), unit.debug, unit));
                }
            }
            unit.getErrorCollector().failIfErrors();
        }

        default public boolean needSortedInput() {
            return false;
        }
    }

    @FunctionalInterface
    public static interface ISourceUnitOperation
    extends PhaseOperation {
        public void call(SourceUnit var1) throws CompilationFailedException;

        @Override
        default public void doPhaseOperation(CompilationUnit unit) throws CompilationFailedException {
            for (SourceUnit source : unit.sources.values()) {
                if (source.phase >= unit.phase && (source.phase != unit.phase || source.phaseComplete)) continue;
                try {
                    this.call(source);
                }
                catch (CompilationFailedException e) {
                    throw e;
                }
                catch (Exception e) {
                    GroovyBugError gbe = new GroovyBugError(e);
                    unit.changeBugText(gbe, source);
                    throw gbe;
                }
                catch (GroovyBugError e) {
                    unit.changeBugText(e, source);
                    throw e;
                }
            }
            unit.getErrorCollector().failIfErrors();
        }
    }

    @FunctionalInterface
    public static interface IGroovyClassOperation
    extends PhaseOperation {
        public void call(GroovyClass var1) throws CompilationFailedException;

        @Override
        default public void doPhaseOperation(CompilationUnit unit) throws CompilationFailedException {
            if (!(unit.phase == 8 || unit.phase == 7 && unit.phaseComplete)) {
                throw new GroovyBugError("CompilationUnit not ready for output(). Current phase=" + unit.getPhaseDescription());
            }
            for (GroovyClass groovyClass : unit.getClasses()) {
                try {
                    this.call(groovyClass);
                }
                catch (CompilationFailedException compilationFailedException) {
                }
                catch (NullPointerException npe) {
                    throw npe;
                }
                catch (GroovyBugError e) {
                    unit.changeBugText(e, null);
                    throw e;
                }
                catch (Exception e) {
                    throw new GroovyBugError(e);
                }
            }
            unit.getErrorCollector().failIfErrors();
        }
    }

    @FunctionalInterface
    public static interface ClassgenCallback {
        public void call(ClassVisitor var1, ClassNode var2) throws CompilationFailedException;
    }

    @FunctionalInterface
    public static interface ProgressCallback {
        public void call(ProcessingUnit var1, int var2) throws CompilationFailedException;
    }

    private static interface PhaseOperation {
        public void doPhaseOperation(CompilationUnit var1);
    }

    @Deprecated
    public static abstract class SourceUnitOperation
    implements ISourceUnitOperation {
    }

    @Deprecated
    public static abstract class PrimaryClassNodeOperation
    implements IPrimaryClassNodeOperation {
    }

    @Deprecated
    public static abstract class GroovyClassOperation
    implements IGroovyClassOperation {
    }
}

