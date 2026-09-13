/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.MixinNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.RecordTypeASTTransformation;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public class ClassNode
extends AnnotatedNode {
    public static final ClassNode[] EMPTY_ARRAY = new ClassNode[0];
    public static final ClassNode THIS = new ClassNode(Object.class);
    public static final ClassNode SUPER = new ClassNode(Object.class);
    private String name;
    private int modifiers;
    private boolean syntheticPublic;
    private ClassNode[] interfaces;
    private MixinNode[] mixins;
    private List<Statement> objectInitializers;
    private List<ConstructorNode> constructors;
    private final MapOfLists methods;
    private List<MethodNode> methodsList;
    private List<FieldNode> fields;
    private List<PropertyNode> properties;
    private Map<String, FieldNode> fieldIndex;
    private ModuleNode module;
    private CompileUnit compileUnit;
    private boolean staticClass;
    private boolean scriptBody;
    private boolean script;
    private ClassNode superClass;
    protected boolean isPrimaryNode;
    protected List<InnerClassNode> innerClasses;
    private List<ClassNode> permittedSubclasses = new ArrayList<ClassNode>(4);
    private List<AnnotationNode> typeAnnotations = Collections.emptyList();
    private List<RecordComponentNode> recordComponents = Collections.emptyList();
    private Map<CompilePhase, Map<Class<? extends ASTTransformation>, Set<ASTNode>>> transformInstances;
    protected final Object lazyInitLock = new Object();
    protected Class clazz;
    private volatile boolean lazyInitDone = true;
    private ClassNode componentType;
    private ClassNode redirect;
    private boolean annotated;
    private GenericsType[] genericsTypes;
    private boolean usesGenerics;
    private boolean placeholder;
    private MethodNode enclosingMethod;

    public ClassNode redirect() {
        return this.redirect == null ? this : this.redirect.redirect();
    }

    public boolean isRedirectNode() {
        return this.redirect != null;
    }

    public void setRedirect(ClassNode node) {
        if (this.isPrimaryNode) {
            throw new GroovyBugError("tried to set a redirect for a primary ClassNode (" + this.getName() + "->" + node.getName() + ").");
        }
        if (node != null && !this.isGenericsPlaceHolder()) {
            node = node.redirect();
        }
        if (node == this) {
            return;
        }
        this.redirect = node;
    }

    public ClassNode makeArray() {
        ClassNode node;
        if (this.redirect != null) {
            node = this.redirect.makeArray();
            node.componentType = this;
        } else if (this.clazz != null) {
            Class<?> type = Array.newInstance(this.clazz, 0).getClass();
            node = new ClassNode(type, this);
        } else {
            node = new ClassNode(this);
        }
        return node;
    }

    public boolean isPrimaryClassNode() {
        return this.redirect().isPrimaryNode || this.componentType != null && this.componentType.isPrimaryClassNode();
    }

    private ClassNode(ClassNode componentType) {
        this(componentType.getName() + "[]", 1, ClassHelper.OBJECT_TYPE);
        this.componentType = componentType.redirect();
        this.isPrimaryNode = false;
    }

    private ClassNode(Class<?> c, ClassNode componentType) {
        this(c);
        this.componentType = componentType;
    }

    public ClassNode(Class<?> c) {
        this(c.getName(), c.getModifiers(), null, null, MixinNode.EMPTY_ARRAY);
        this.clazz = c;
        this.lazyInitDone = false;
        this.isPrimaryNode = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lazyClassInit() {
        if (this.lazyInitDone) {
            return;
        }
        Object object = this.lazyInitLock;
        synchronized (object) {
            if (this.redirect != null) {
                throw new GroovyBugError("lazyClassInit called on a proxy ClassNode, that must not happen. A redirect() call is missing somewhere!");
            }
            if (this.lazyInitDone) {
                return;
            }
            VMPluginFactory.getPlugin().configureClassNode(this.compileUnit, this);
            this.lazyInitDone = true;
        }
    }

    public MethodNode getEnclosingMethod() {
        return this.redirect().enclosingMethod;
    }

    public void setEnclosingMethod(MethodNode enclosingMethod) {
        this.redirect().enclosingMethod = enclosingMethod;
    }

    public boolean isSyntheticPublic() {
        return this.syntheticPublic;
    }

    public void setSyntheticPublic(boolean syntheticPublic) {
        this.syntheticPublic = syntheticPublic;
    }

    public ClassNode(String name, int modifiers, ClassNode superClass) {
        this(name, modifiers, superClass, EMPTY_ARRAY, MixinNode.EMPTY_ARRAY);
    }

    public ClassNode(String name, int modifiers, ClassNode superClass, ClassNode[] interfaces, MixinNode[] mixins) {
        this.name = name;
        this.modifiers = modifiers;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.mixins = mixins;
        this.isPrimaryNode = true;
        if (superClass != null) {
            this.usesGenerics = superClass.isUsingGenerics();
        }
        if (!this.usesGenerics && interfaces != null) {
            this.usesGenerics = Arrays.stream(interfaces).anyMatch(ClassNode::isUsingGenerics);
        }
        this.methods = new MapOfLists();
        this.methodsList = Collections.emptyList();
    }

    public void setSuperClass(ClassNode superClass) {
        this.redirect().superClass = superClass;
    }

    public List<FieldNode> getFields() {
        if (this.redirect != null) {
            return this.redirect.getFields();
        }
        this.lazyClassInit();
        if (this.fields == null) {
            this.fields = new ArrayList<FieldNode>();
        }
        return this.fields;
    }

    public ClassNode[] getInterfaces() {
        if (this.redirect != null) {
            return this.redirect.getInterfaces();
        }
        this.lazyClassInit();
        return this.interfaces;
    }

    public void setInterfaces(ClassNode[] interfaces) {
        if (this.redirect != null) {
            this.redirect.setInterfaces(interfaces);
        } else {
            this.interfaces = interfaces;
        }
    }

    @Incubating
    public List<ClassNode> getPermittedSubclasses() {
        if (this.redirect != null) {
            return this.redirect.getPermittedSubclasses();
        }
        this.lazyClassInit();
        return this.permittedSubclasses;
    }

    @Incubating
    public void setPermittedSubclasses(List<ClassNode> permittedSubclasses) {
        if (this.redirect != null) {
            this.redirect.setPermittedSubclasses(permittedSubclasses);
        } else {
            this.permittedSubclasses = permittedSubclasses;
        }
    }

    public MixinNode[] getMixins() {
        return this.redirect().mixins;
    }

    public void setMixins(MixinNode[] mixins) {
        this.redirect().mixins = mixins;
    }

    public List<MethodNode> getMethods() {
        if (this.redirect != null) {
            return this.redirect.getMethods();
        }
        this.lazyClassInit();
        return this.methodsList;
    }

    public List<MethodNode> getAbstractMethods() {
        return this.getDeclaredMethodsMap().values().stream().filter(MethodNode::isAbstract).collect(Collectors.toList());
    }

    public List<MethodNode> getAllDeclaredMethods() {
        return new ArrayList<MethodNode>(this.getDeclaredMethodsMap().values());
    }

    public Set<ClassNode> getAllInterfaces() {
        LinkedHashSet<ClassNode> result = new LinkedHashSet<ClassNode>();
        this.getAllInterfaces(result);
        return result;
    }

    private void getAllInterfaces(Set<ClassNode> set) {
        if (this.isInterface()) {
            set.add(this);
        }
        for (ClassNode face : this.getInterfaces()) {
            set.add(face);
            face.getAllInterfaces(set);
        }
    }

    public Map<String, MethodNode> getDeclaredMethodsMap() {
        Map<String, MethodNode> result = ClassNodeUtils.getDeclaredMethodsFromSuper(this);
        ClassNodeUtils.addDeclaredMethodsFromInterfaces(this, result);
        for (MethodNode method : this.getMethods()) {
            result.put(method.getTypeDescriptor(), method);
        }
        return result;
    }

    public String getName() {
        return this.redirect().name;
    }

    public String getUnresolvedName() {
        return this.name;
    }

    public String setName(String name) {
        this.redirect().name = name;
        return this.redirect().name;
    }

    public int getModifiers() {
        return this.redirect().modifiers;
    }

    public void setModifiers(int modifiers) {
        this.redirect().modifiers = modifiers;
    }

    public List<PropertyNode> getProperties() {
        if (this.redirect != null) {
            return this.redirect.getProperties();
        }
        if (this.properties == null) {
            this.properties = new ArrayList<PropertyNode>();
        }
        return this.properties;
    }

    public List<ConstructorNode> getDeclaredConstructors() {
        if (this.redirect != null) {
            return this.redirect.getDeclaredConstructors();
        }
        this.lazyClassInit();
        if (this.constructors == null) {
            this.constructors = new ArrayList<ConstructorNode>();
        }
        return this.constructors;
    }

    public ConstructorNode getDeclaredConstructor(Parameter[] parameters) {
        for (ConstructorNode constructor : this.getDeclaredConstructors()) {
            if (!this.parametersEqual(constructor.getParameters(), parameters)) continue;
            return constructor;
        }
        return null;
    }

    public void removeConstructor(ConstructorNode node) {
        this.getDeclaredConstructors().remove(node);
    }

    public ModuleNode getModule() {
        return this.redirect().module;
    }

    public PackageNode getPackage() {
        return Optional.ofNullable(this.getModule()).map(ModuleNode::getPackage).orElse(null);
    }

    public void setModule(ModuleNode module) {
        this.redirect().module = module;
        if (module != null) {
            this.redirect().compileUnit = module.getUnit();
        }
    }

    public void addField(FieldNode node) {
        this.addField(node, false);
    }

    public void addFieldFirst(FieldNode node) {
        this.addField(node, true);
    }

    private void addField(FieldNode node, boolean isFirst) {
        ClassNode r = this.redirect();
        node.setDeclaringClass(r);
        node.setOwner(r);
        if (r.fields == null) {
            r.fields = new ArrayList<FieldNode>();
        }
        if (r.fieldIndex == null) {
            r.fieldIndex = new LinkedHashMap<String, FieldNode>();
        }
        if (isFirst) {
            r.fields.add(0, node);
        } else {
            r.fields.add(node);
        }
        r.fieldIndex.put(node.getName(), node);
    }

    public Map<String, FieldNode> getFieldIndex() {
        return this.fieldIndex;
    }

    public void addProperty(PropertyNode node) {
        node.setDeclaringClass(this.redirect());
        this.addField(node.getField());
        this.getProperties().add(node);
    }

    public PropertyNode addProperty(String name, int modifiers, ClassNode type, Expression initialValueExpression, Statement getterBlock, Statement setterBlock) {
        for (PropertyNode pn : this.getProperties()) {
            if (!pn.getName().equals(name)) continue;
            if (pn.getInitialExpression() == null && initialValueExpression != null) {
                pn.getField().setInitialValueExpression(initialValueExpression);
            }
            if (pn.getGetterBlock() == null && getterBlock != null) {
                pn.setGetterBlock(getterBlock);
            }
            if (pn.getSetterBlock() == null && setterBlock != null) {
                pn.setSetterBlock(setterBlock);
            }
            return pn;
        }
        PropertyNode node = new PropertyNode(name, modifiers, type, this.redirect(), initialValueExpression, getterBlock, setterBlock);
        this.addProperty(node);
        return node;
    }

    public boolean hasProperty(String name) {
        return this.getProperties().stream().map(PropertyNode::getName).anyMatch(name::equals);
    }

    public PropertyNode getProperty(String name) {
        return this.getProperties().stream().filter(pn -> pn.getName().equals(name)).findFirst().orElse(null);
    }

    public void addConstructor(ConstructorNode node) {
        node.setDeclaringClass(this);
        ClassNode r = this.redirect();
        if (r.constructors == null) {
            r.constructors = new ArrayList<ConstructorNode>();
        }
        r.constructors.add(node);
    }

    public ConstructorNode addConstructor(int modifiers, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        ConstructorNode node = new ConstructorNode(modifiers, parameters, exceptions, code);
        this.addConstructor(node);
        return node;
    }

    public void addMethod(MethodNode node) {
        node.setDeclaringClass(this);
        ClassNode r = this.redirect();
        if (r.methodsList.isEmpty()) {
            r.methodsList = new ArrayList<MethodNode>();
        }
        r.methodsList.add(node);
        r.methods.put(node.getName(), node);
    }

    public void removeMethod(MethodNode node) {
        ClassNode r = this.redirect();
        if (!r.methodsList.isEmpty()) {
            r.methodsList.remove(node);
        }
        r.methods.remove(node.getName(), node);
    }

    public MethodNode addMethod(String name, int modifiers, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        MethodNode other = this.getDeclaredMethod(name, parameters);
        if (other != null) {
            return other;
        }
        MethodNode node = new MethodNode(name, modifiers, returnType, parameters, exceptions, code);
        this.addMethod(node);
        return node;
    }

    public boolean hasDeclaredMethod(String name, Parameter[] parameters) {
        return this.getDeclaredMethod(name, parameters) != null;
    }

    public boolean hasMethod(String name, Parameter[] parameters) {
        return this.getMethod(name, parameters) != null;
    }

    public MethodNode addSyntheticMethod(String name, int modifiers, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        MethodNode node = this.addMethod(name, modifiers | 0x1000, returnType, parameters, exceptions, code);
        node.setSynthetic(true);
        return node;
    }

    public FieldNode addField(String name, int modifiers, ClassNode type, Expression initialValue) {
        FieldNode node = new FieldNode(name, modifiers, type, this.redirect(), initialValue);
        this.addField(node);
        return node;
    }

    public FieldNode addFieldFirst(String name, int modifiers, ClassNode type, Expression initialValue) {
        FieldNode node = new FieldNode(name, modifiers, type, this.redirect(), initialValue);
        this.addFieldFirst(node);
        return node;
    }

    public void addInterface(ClassNode type) {
        ClassNode[] interfaces;
        for (ClassNode face : interfaces = this.getInterfaces()) {
            if (!face.equals(type)) continue;
            return;
        }
        int n = interfaces.length;
        ClassNode[] classNodeArray = interfaces;
        interfaces = new ClassNode[n + 1];
        System.arraycopy(classNodeArray, 0, interfaces, 0, n);
        interfaces[n] = type;
        this.setInterfaces(interfaces);
    }

    public boolean equals(Object that) {
        if (that == this) {
            return true;
        }
        if (!(that instanceof ClassNode)) {
            return false;
        }
        if (this.redirect != null) {
            return this.redirect.equals(that);
        }
        if (this.componentType != null) {
            return this.componentType.equals(((ClassNode)that).componentType);
        }
        return ((ClassNode)that).getText().equals(this.getText());
    }

    public int hashCode() {
        return this.redirect != null ? this.redirect.hashCode() : this.getText().hashCode();
    }

    public void addMixin(MixinNode mixin) {
        MixinNode[] mixins = this.getMixins();
        boolean skip = false;
        for (MixinNode existing : mixins) {
            if (!mixin.equals(existing)) continue;
            skip = true;
            break;
        }
        if (!skip) {
            MixinNode[] newMixins = new MixinNode[mixins.length + 1];
            System.arraycopy(mixins, 0, newMixins, 0, mixins.length);
            newMixins[mixins.length] = mixin;
            this.redirect().mixins = newMixins;
        }
    }

    public FieldNode getDeclaredField(String name) {
        if (this.redirect != null) {
            return this.redirect.getDeclaredField(name);
        }
        this.lazyClassInit();
        return this.fieldIndex == null ? null : this.fieldIndex.get(name);
    }

    public FieldNode getField(String name) {
        for (ClassNode node = this; node != null; node = node.getSuperClass()) {
            FieldNode fn = node.getDeclaredField(name);
            if (fn == null) continue;
            return fn;
        }
        return null;
    }

    public FieldNode getOuterField(String name) {
        if (this.redirect != null) {
            return this.redirect.getOuterField(name);
        }
        return null;
    }

    public ClassNode getOuterClass() {
        if (this.redirect != null) {
            return this.redirect.getOuterClass();
        }
        return null;
    }

    public List<ClassNode> getOuterClasses() {
        ClassNode outer = this.getOuterClass();
        if (outer == null) {
            return Collections.emptyList();
        }
        LinkedList<ClassNode> result = new LinkedList<ClassNode>();
        do {
            result.add(outer);
        } while ((outer = outer.getOuterClass()) != null);
        return result;
    }

    public void addObjectInitializerStatements(Statement statements) {
        this.getObjectInitializerStatements().add(statements);
    }

    public List<Statement> getObjectInitializerStatements() {
        if (this.objectInitializers == null) {
            this.objectInitializers = new LinkedList<Statement>();
        }
        return this.objectInitializers;
    }

    private MethodNode getOrAddStaticConstructorNode() {
        MethodNode method;
        String classInitializer = "<clinit>";
        List<MethodNode> declaredMethods = this.getDeclaredMethods("<clinit>");
        if (declaredMethods.isEmpty()) {
            method = this.addMethod("<clinit>", 8, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, EMPTY_ARRAY, new BlockStatement());
            method.setSynthetic(true);
        } else {
            method = declaredMethods.get(0);
        }
        return method;
    }

    public void addStaticInitializerStatements(List<Statement> staticStatements, boolean fieldInit) {
        MethodNode method = this.getOrAddStaticConstructorNode();
        BlockStatement block = MethodNodeUtils.getCodeAsBlock(method);
        if (!fieldInit) {
            block.addStatements(staticStatements);
        } else {
            List<Statement> blockStatements = block.getStatements();
            staticStatements.addAll(blockStatements);
            blockStatements.clear();
            blockStatements.addAll(staticStatements);
        }
    }

    public void positionStmtsAfterEnumInitStmts(List<Statement> staticFieldStatements) {
        MethodNode constructor = this.getOrAddStaticConstructorNode();
        Statement statement = constructor.getCode();
        if (statement instanceof BlockStatement) {
            BlockStatement block = (BlockStatement)statement;
            List<Statement> blockStatements = block.getStatements();
            ListIterator<Statement> litr = blockStatements.listIterator();
            while (litr.hasNext()) {
                FieldExpression fExp;
                BinaryExpression bExp;
                Statement stmt = litr.next();
                if (!(stmt instanceof ExpressionStatement) || !(((ExpressionStatement)stmt).getExpression() instanceof BinaryExpression) || !((bExp = (BinaryExpression)((ExpressionStatement)stmt).getExpression()).getLeftExpression() instanceof FieldExpression) || !(fExp = (FieldExpression)bExp.getLeftExpression()).getFieldName().equals("$VALUES")) continue;
                for (Statement tmpStmt : staticFieldStatements) {
                    litr.add(tmpStmt);
                }
            }
        }
    }

    public List<MethodNode> getDeclaredMethods(String name) {
        if (this.redirect != null) {
            return this.redirect.getDeclaredMethods(name);
        }
        this.lazyClassInit();
        return this.methods.get(name);
    }

    public List<MethodNode> getMethods(String name) {
        ArrayList<MethodNode> result = new ArrayList<MethodNode>();
        for (ClassNode node = this; node != null; node = node.getSuperClass()) {
            result.addAll(node.getDeclaredMethods(name));
        }
        return result;
    }

    public MethodNode getDeclaredMethod(String name, Parameter[] parameters) {
        for (MethodNode method : this.getDeclaredMethods(name)) {
            if (!this.parametersEqual(method.getParameters(), parameters)) continue;
            return method;
        }
        return null;
    }

    public MethodNode getMethod(String name, Parameter[] parameters) {
        for (MethodNode method : this.getMethods(name)) {
            if (!this.parametersEqual(method.getParameters(), parameters)) continue;
            return method;
        }
        return null;
    }

    public boolean isDerivedFrom(ClassNode type) {
        if (ClassHelper.isPrimitiveVoid(this)) {
            return ClassHelper.isPrimitiveVoid(type);
        }
        if (ClassHelper.isObjectType(type)) {
            return true;
        }
        for (ClassNode node = this; node != null; node = node.getSuperClass()) {
            if (!type.equals(node)) continue;
            return true;
        }
        return false;
    }

    public boolean isDerivedFromGroovyObject() {
        return this.implementsInterface(ClassHelper.GROOVY_OBJECT_TYPE);
    }

    public boolean implementsAnyInterfaces(ClassNode ... classNodes) {
        for (ClassNode classNode : classNodes) {
            if (!this.implementsInterface(classNode)) continue;
            return true;
        }
        return false;
    }

    public boolean implementsInterface(ClassNode classNode) {
        ClassNode node = this.redirect();
        do {
            if (!node.declaresInterface(classNode)) continue;
            return true;
        } while ((node = node.getSuperClass()) != null);
        return false;
    }

    public boolean declaresAnyInterfaces(ClassNode ... classNodes) {
        for (ClassNode classNode : classNodes) {
            if (!this.declaresInterface(classNode)) continue;
            return true;
        }
        return false;
    }

    public boolean declaresInterface(ClassNode classNode) {
        ClassNode[] interfaces;
        for (ClassNode face : interfaces = this.getInterfaces()) {
            if (!face.equals(classNode)) continue;
            return true;
        }
        for (ClassNode face : interfaces) {
            if (!face.declaresInterface(classNode)) continue;
            return true;
        }
        return false;
    }

    public ClassNode getSuperClass() {
        if (!this.lazyInitDone && !this.isResolved()) {
            throw new GroovyBugError("ClassNode#getSuperClass for " + this.getName() + " called before class resolving");
        }
        ClassNode sn = this.redirect().getUnresolvedSuperClass();
        if (sn != null) {
            sn = sn.redirect();
        }
        return sn;
    }

    public ClassNode getUnresolvedSuperClass() {
        return this.getUnresolvedSuperClass(true);
    }

    public ClassNode getUnresolvedSuperClass(boolean useRedirect) {
        if (!useRedirect) {
            return this.superClass;
        }
        if (this.redirect != null) {
            return this.redirect.getUnresolvedSuperClass(true);
        }
        this.lazyClassInit();
        return this.superClass;
    }

    public void setUnresolvedSuperClass(ClassNode superClass) {
        this.superClass = superClass;
    }

    public ClassNode[] getUnresolvedInterfaces() {
        return this.getUnresolvedInterfaces(true);
    }

    public ClassNode[] getUnresolvedInterfaces(boolean useRedirect) {
        if (!useRedirect) {
            return this.interfaces;
        }
        if (this.redirect != null) {
            return this.redirect.getUnresolvedInterfaces(true);
        }
        this.lazyClassInit();
        return this.interfaces;
    }

    public CompileUnit getCompileUnit() {
        if (this.redirect != null) {
            return this.redirect.getCompileUnit();
        }
        if (this.compileUnit == null && this.module != null) {
            this.compileUnit = this.module.getUnit();
        }
        return this.compileUnit;
    }

    protected void setCompileUnit(CompileUnit cu) {
        if (this.redirect != null) {
            this.redirect.setCompileUnit(cu);
        }
        if (this.compileUnit != null) {
            this.compileUnit = cu;
        }
    }

    @Deprecated
    protected boolean parametersEqual(Parameter[] a, Parameter[] b) {
        return ParameterUtils.parametersEqual(a, b);
    }

    public String getPackageName() {
        int idx = this.getName().lastIndexOf(46);
        if (idx > 0) {
            return this.getName().substring(0, idx);
        }
        return null;
    }

    public String getNameWithoutPackage() {
        int idx = this.getName().lastIndexOf(46);
        if (idx > 0) {
            return this.getName().substring(idx + 1);
        }
        return this.getName();
    }

    public void visitContents(GroovyClassVisitor visitor) {
        for (PropertyNode pn : this.getProperties()) {
            visitor.visitProperty(pn);
        }
        for (FieldNode fn : this.getFields()) {
            visitor.visitField(fn);
        }
        for (ConstructorNode cn : this.getDeclaredConstructors()) {
            visitor.visitConstructor(cn);
        }
        this.visitMethods(visitor);
    }

    private void visitMethods(GroovyClassVisitor visitor) {
        ArrayList<MethodNode> changedMethodList;
        boolean changed;
        ArrayList<MethodNode> methodList = new ArrayList<MethodNode>(this.getMethods());
        for (MethodNode mn : methodList) {
            visitor.visitMethod(mn);
        }
        List<MethodNode> newMethodList = this.getMethods();
        if (newMethodList.size() > methodList.size() && (changed = (changedMethodList = new ArrayList<MethodNode>(newMethodList)).removeAll(methodList))) {
            for (MethodNode mn : changedMethodList) {
                visitor.visitMethod(mn);
            }
        }
    }

    public MethodNode getGetterMethod(String getterName) {
        return this.getGetterMethod(getterName, true);
    }

    public MethodNode getGetterMethod(String getterName, boolean searchSuperClasses) {
        ClassNode parent;
        AnnotatedNode getterMethod = null;
        boolean booleanReturnOnly = getterName.startsWith("is");
        for (MethodNode method : this.getDeclaredMethods(getterName)) {
            if (!method.getName().equals(getterName) || method.getParameters().length != 0 || !(booleanReturnOnly ? ClassHelper.isPrimitiveBoolean(method.getReturnType()) : !method.isVoidMethod()) || getterMethod != null && !getterMethod.isSynthetic()) continue;
            getterMethod = method;
        }
        if (getterMethod != null) {
            return getterMethod;
        }
        if (searchSuperClasses && (parent = this.getSuperClass()) != null) {
            return parent.getGetterMethod(getterName);
        }
        return null;
    }

    public MethodNode getSetterMethod(String setterName) {
        return this.getSetterMethod(setterName, true);
    }

    public MethodNode getSetterMethod(String setterName, boolean voidOnly) {
        for (MethodNode method : this.getDeclaredMethods(setterName)) {
            if (!setterName.equals(method.getName()) || method.getParameters().length != 1 || voidOnly && !method.isVoidMethod()) continue;
            return method;
        }
        ClassNode parent = this.getSuperClass();
        if (parent != null) {
            return parent.getSetterMethod(setterName, voidOnly);
        }
        return null;
    }

    public boolean isStaticClass() {
        return this.redirect().staticClass;
    }

    public void setStaticClass(boolean staticClass) {
        this.redirect().staticClass = staticClass;
    }

    public boolean isScriptBody() {
        return this.redirect().scriptBody;
    }

    public void setScriptBody(boolean scriptBody) {
        this.redirect().scriptBody = scriptBody;
    }

    public boolean isScript() {
        return this.redirect().script || this.isDerivedFrom(ClassHelper.SCRIPT_TYPE);
    }

    public void setScript(boolean script) {
        this.redirect().script = script;
    }

    public String toString() {
        return this.toString(true);
    }

    public String toString(boolean showRedirect) {
        if (this.isArray()) {
            return this.getComponentType().toString(showRedirect) + "[]";
        }
        boolean placeholder = this.isGenericsPlaceHolder();
        StringBuilder ret = new StringBuilder(!placeholder ? this.getName() : this.getUnresolvedName());
        GenericsType[] genericsTypes = this.getGenericsTypes();
        if (!placeholder && genericsTypes != null) {
            ret.append('<');
            int n = genericsTypes.length;
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    ret.append(", ");
                }
                ret.append(genericsTypes[i]);
            }
            ret.append('>');
        }
        if (showRedirect && this.redirect != null) {
            ret.append(" -> ").append(this.redirect);
        }
        return ret.toString();
    }

    public boolean hasPossibleMethod(String name, Expression arguments) {
        int count = arguments instanceof TupleExpression ? ((TupleExpression)arguments).getExpressions().size() : 0;
        for (ClassNode cn = this; cn != null; cn = cn.getSuperClass()) {
            for (MethodNode mn : cn.getDeclaredMethods(name)) {
                if (mn.isStatic() || !ClassNode.hasCompatibleNumberOfArgs(mn, count)) continue;
                return true;
            }
            for (ClassNode in : cn.getAllInterfaces()) {
                for (MethodNode mn : in.getDeclaredMethods(name)) {
                    if (!mn.isDefault() || !ClassNode.hasCompatibleNumberOfArgs(mn, count)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    public MethodNode tryFindPossibleMethod(String name, Expression arguments) {
        if (!(arguments instanceof TupleExpression)) {
            return null;
        }
        TupleExpression args = (TupleExpression)arguments;
        int nArgs = args.getExpressions().size();
        MethodNode method = null;
        for (ClassNode cn = this; cn != null; cn = cn.getSuperClass()) {
            for (MethodNode mn : cn.getDeclaredMethods(name)) {
                int i;
                if (!ClassNode.hasCompatibleNumberOfArgs(mn, nArgs)) continue;
                boolean match = true;
                for (i = 0; i < nArgs; ++i) {
                    if (ClassNode.hasCompatibleType(args, mn, i)) continue;
                    match = false;
                    break;
                }
                if (!match) continue;
                if (method == null) {
                    method = mn;
                    continue;
                }
                if (cn.equals(this) || method.getParameters().length != nArgs) {
                    return null;
                }
                for (i = 0; i < nArgs; ++i) {
                    if (ClassNode.hasExactMatchingCompatibleType(method, mn, i)) continue;
                    return null;
                }
            }
        }
        return method;
    }

    private static boolean hasExactMatchingCompatibleType(MethodNode match, MethodNode maybe, int i) {
        int lastParamIndex = maybe.getParameters().length - 1;
        return i <= lastParamIndex && match.getParameters()[i].getType().equals(maybe.getParameters()[i].getType()) || i >= lastParamIndex && ClassNode.isPotentialVarArg(maybe, lastParamIndex) && match.getParameters()[i].getType().equals(maybe.getParameters()[lastParamIndex].getType().getComponentType());
    }

    private static boolean hasCompatibleType(TupleExpression args, MethodNode method, int i) {
        int lastParamIndex = method.getParameters().length - 1;
        return i <= lastParamIndex && args.getExpression(i).getType().isDerivedFrom(method.getParameters()[i].getType()) || i >= lastParamIndex && ClassNode.isPotentialVarArg(method, lastParamIndex) && args.getExpression(i).getType().isDerivedFrom(method.getParameters()[lastParamIndex].getType().getComponentType());
    }

    private static boolean hasCompatibleNumberOfArgs(MethodNode method, int nArgs) {
        int lastParamIndex = method.getParameters().length - 1;
        return nArgs == method.getParameters().length || nArgs >= lastParamIndex && ClassNode.isPotentialVarArg(method, lastParamIndex);
    }

    private static boolean isPotentialVarArg(MethodNode method, int lastParamIndex) {
        return lastParamIndex >= 0 && method.getParameters()[lastParamIndex].getType().isArray();
    }

    public boolean hasPossibleStaticMethod(String name, Expression arguments) {
        return ClassNodeUtils.hasPossibleStaticMethod(this, name, arguments, false);
    }

    public boolean isInterface() {
        return (this.getModifiers() & 0x200) != 0;
    }

    @Incubating
    public boolean isRecord() {
        return RecordTypeASTTransformation.recordNative(this);
    }

    @Incubating
    public List<RecordComponentNode> getRecordComponents() {
        if (this.redirect != null) {
            return this.redirect.getRecordComponents();
        }
        this.lazyClassInit();
        return this.recordComponents;
    }

    @Deprecated
    public List<RecordComponentNode> getRecordComponentNodes() {
        return this.getRecordComponents();
    }

    @Incubating
    public void setRecordComponents(List<RecordComponentNode> recordComponents) {
        if (this.redirect != null) {
            this.redirect.setRecordComponents(recordComponents);
        } else {
            this.recordComponents = recordComponents;
        }
    }

    @Deprecated
    public void setRecordComponentNodes(List<RecordComponentNode> recordComponentNodes) {
        this.setRecordComponents(recordComponentNodes);
    }

    public boolean isAbstract() {
        return (this.getModifiers() & 0x400) != 0;
    }

    @Incubating
    public boolean isSealed() {
        if (this.redirect != null) {
            return this.redirect.isSealed();
        }
        this.lazyClassInit();
        return !this.getAnnotations(ClassHelper.SEALED_TYPE).isEmpty() || !this.getPermittedSubclasses().isEmpty();
    }

    public boolean isResolved() {
        if (this.clazz != null) {
            return true;
        }
        if (this.redirect != null) {
            return this.redirect.isResolved();
        }
        return this.componentType != null && this.componentType.isResolved();
    }

    public boolean isArray() {
        return this.componentType != null;
    }

    public ClassNode getComponentType() {
        return this.componentType;
    }

    public Class getTypeClass() {
        if (this.clazz != null) {
            return this.clazz;
        }
        if (this.redirect != null) {
            return this.redirect.getTypeClass();
        }
        ClassNode component = this.redirect().componentType;
        if (component != null && component.isResolved()) {
            return Array.newInstance(component.getTypeClass(), 0).getClass();
        }
        throw new GroovyBugError("ClassNode#getTypeClass for " + this.getName() + " called before the type class is set");
    }

    public boolean hasPackageName() {
        return this.redirect().name.indexOf(46) > 0;
    }

    public void setAnnotated(boolean annotated) {
        this.annotated = annotated;
    }

    public boolean isAnnotated() {
        return this.annotated;
    }

    public GenericsType asGenericsType() {
        if (!this.isGenericsPlaceHolder()) {
            return new GenericsType(this);
        }
        ClassNode upper = this.redirect != null ? this.redirect : this;
        return new GenericsType(this, new ClassNode[]{upper}, null);
    }

    public GenericsType[] getGenericsTypes() {
        return this.genericsTypes;
    }

    public void setGenericsTypes(GenericsType[] genericsTypes) {
        this.usesGenerics = this.usesGenerics || genericsTypes != null;
        this.genericsTypes = genericsTypes;
    }

    public void setGenericsPlaceHolder(boolean placeholder) {
        this.usesGenerics = this.usesGenerics || placeholder;
        this.placeholder = placeholder;
    }

    public boolean isGenericsPlaceHolder() {
        return this.placeholder;
    }

    public boolean isUsingGenerics() {
        return this.usesGenerics;
    }

    public void setUsingGenerics(boolean usesGenerics) {
        this.usesGenerics = usesGenerics;
    }

    public ClassNode getPlainNodeReference(boolean skipPrimitives) {
        if (skipPrimitives && ClassHelper.isPrimitiveType(this)) {
            return this;
        }
        ClassNode n = new ClassNode(this.name, this.modifiers, this.superClass, null, null);
        n.isPrimaryNode = false;
        n.setRedirect(this.redirect());
        if (this.isArray()) {
            n.componentType = this.redirect().getComponentType();
        }
        return n;
    }

    public ClassNode getPlainNodeReference() {
        return this.getPlainNodeReference(true);
    }

    public boolean isAnnotationDefinition() {
        return this.isInterface() && (this.getModifiers() & 0x2000) != 0;
    }

    @Override
    public List<AnnotationNode> getAnnotations() {
        if (this.redirect != null) {
            return this.redirect.getAnnotations();
        }
        this.lazyClassInit();
        return super.getAnnotations();
    }

    @Override
    public List<AnnotationNode> getAnnotations(ClassNode type) {
        if (this.redirect != null) {
            return this.redirect.getAnnotations(type);
        }
        this.lazyClassInit();
        return super.getAnnotations(type);
    }

    public void addTransform(Class<? extends ASTTransformation> transform, ASTNode node) {
        GroovyASTTransformation annotation = transform.getAnnotation(GroovyASTTransformation.class);
        if (annotation != null) {
            Map<Class<? extends ASTTransformation>, Set<ASTNode>> transforms = this.getTransforms(annotation.phase());
            Set nodes = transforms.computeIfAbsent(transform, k -> new LinkedHashSet());
            nodes.add(node);
        }
    }

    public Map<Class<? extends ASTTransformation>, Set<ASTNode>> getTransforms(CompilePhase phase) {
        return this.getTransformInstances().get((Object)phase);
    }

    public void renameField(String oldName, String newName) {
        ClassNode r = this.redirect();
        if (r.fieldIndex == null) {
            r.fieldIndex = new LinkedHashMap<String, FieldNode>();
        }
        Map<String, FieldNode> index = r.fieldIndex;
        index.put(newName, index.remove(oldName));
    }

    public void removeField(String oldName) {
        ClassNode r = this.redirect();
        if (r.fieldIndex == null) {
            r.fieldIndex = new LinkedHashMap<String, FieldNode>();
        }
        Map<String, FieldNode> index = r.fieldIndex;
        r.fields.remove(index.get(oldName));
        index.remove(oldName);
    }

    public boolean isEnum() {
        return (this.getModifiers() & 0x4000) != 0;
    }

    public Iterator<InnerClassNode> getInnerClasses() {
        return (this.innerClasses == null ? Collections.emptyList() : this.innerClasses).iterator();
    }

    private Map<CompilePhase, Map<Class<? extends ASTTransformation>, Set<ASTNode>>> getTransformInstances() {
        if (this.transformInstances == null) {
            this.transformInstances = new EnumMap<CompilePhase, Map<Class<? extends ASTTransformation>, Set<ASTNode>>>(CompilePhase.class);
            for (CompilePhase phase : CompilePhase.values()) {
                this.transformInstances.put(phase, new LinkedHashMap());
            }
        }
        return this.transformInstances;
    }

    @Override
    public String getText() {
        return this.getName();
    }

    public List<AnnotationNode> getTypeAnnotations() {
        return this.typeAnnotations;
    }

    public List<AnnotationNode> getTypeAnnotations(ClassNode type) {
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(this.typeAnnotations.size());
        for (AnnotationNode node : this.typeAnnotations) {
            if (!type.equals(node.getClassNode())) continue;
            ret.add(node);
        }
        return ret;
    }

    public void addTypeAnnotation(AnnotationNode annotation) {
        if (annotation != null) {
            if (this.typeAnnotations == Collections.EMPTY_LIST) {
                this.typeAnnotations = new ArrayList<AnnotationNode>(3);
            }
            this.typeAnnotations.add(annotation);
        }
    }

    public void addTypeAnnotations(List<AnnotationNode> annotations) {
        for (AnnotationNode annotation : annotations) {
            this.addTypeAnnotation(annotation);
        }
    }

    private static class MapOfLists {
        Map<Object, List<MethodNode>> map;

        private MapOfLists() {
        }

        List<MethodNode> get(Object key) {
            return Optional.ofNullable(this.map).map(m -> (List)m.get(key)).orElseGet(Collections::emptyList);
        }

        void put(Object key, MethodNode value) {
            if (this.map == null) {
                this.map = new LinkedHashMap<Object, List<MethodNode>>();
            }
            this.map.computeIfAbsent(key, k -> new ArrayList(2)).add(value);
        }

        void remove(Object key, MethodNode value) {
            this.get(key).remove(value);
        }
    }
}

