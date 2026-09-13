/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.ClassNodeResolver;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.ProcessingUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.memoize.UnlimitedConcurrentCache;
import org.codehaus.groovy.transform.trait.Traits;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public class ResolveVisitor
extends ClassCodeExpressionTransformer {
    public static final String[] DEFAULT_IMPORTS = new String[]{"java.lang.", "java.util.", "java.io.", "java.net.", "groovy.lang.", "groovy.util."};
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final String QUESTION_MARK = "?";
    private final CompilationUnit compilationUnit;
    private ClassNodeResolver classNodeResolver;
    private SourceUnit source;
    private ClassNode currentClass;
    private ImportNode currentImport;
    private MethodNode currentMethod;
    private VariableScope currentScope;
    private Map<GenericsType.GenericsTypeName, GenericsType> genericParameterNames = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
    private final Set<FieldNode> fieldTypesChecked = new HashSet<FieldNode>();
    private boolean checkingVariableTypeInDeclaration;
    private boolean inClosure;
    private boolean inPropertyExpression;
    private boolean isTopLevelProperty = true;
    int phase;
    private static final Map<String, Set<String>> DEFAULT_IMPORT_CLASS_AND_PACKAGES_CACHE = new UnlimitedConcurrentCache<String, Set<String>>();

    public ResolveVisitor(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public void setClassNodeResolver(ClassNodeResolver classNodeResolver) {
        this.classNodeResolver = classNodeResolver;
    }

    public void startResolving(ClassNode node, SourceUnit source) {
        this.source = source;
        this.visitClass(node);
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    @Override
    public void visitMethod(MethodNode node) {
        super.visitMethod(node);
        this.visitGenericsTypeAnnotations(node);
        this.visitTypeAnnotations(node.getReturnType());
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        VariableScope oldScope = this.currentScope;
        this.currentScope = node.getVariableScope();
        Map<GenericsType.GenericsTypeName, GenericsType> oldPNames = this.genericParameterNames;
        this.genericParameterNames = node.isStatic() && !Traits.isTrait(node.getDeclaringClass()) ? new HashMap() : new HashMap(this.genericParameterNames);
        this.resolveGenericsHeader(node.getGenericsTypes());
        Parameter[] paras = node.getParameters();
        for (Parameter parameter : paras) {
            parameter.setInitialExpression(this.transform(parameter.getInitialExpression()));
            this.resolveOrFail(parameter.getType(), parameter.getType());
            this.visitAnnotations(parameter);
        }
        this.resolveOrFail(node.getReturnType(), node);
        if (node.getExceptions() != null) {
            for (AnnotatedNode annotatedNode : node.getExceptions()) {
                this.resolveOrFail((ClassNode)annotatedNode, node);
            }
        }
        MethodNode oldCurrentMethod = this.currentMethod;
        this.currentMethod = node;
        super.visitConstructorOrMethod(node, isConstructor);
        this.currentMethod = oldCurrentMethod;
        this.genericParameterNames = oldPNames;
        this.currentScope = oldScope;
    }

    @Override
    public void visitField(FieldNode node) {
        ClassNode t = node.getType();
        if (!this.fieldTypesChecked.contains(node)) {
            this.resolveOrFail(t, node);
        }
        super.visitField(node);
    }

    @Override
    public void visitProperty(PropertyNode node) {
        Map<GenericsType.GenericsTypeName, GenericsType> oldPNames = this.genericParameterNames;
        if (node.isStatic() && !Traits.isTrait(node.getDeclaringClass())) {
            this.genericParameterNames = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        }
        ClassNode t = node.getType();
        this.resolveOrFail(t, node);
        super.visitProperty(node);
        this.fieldTypesChecked.add(node.getField());
        this.genericParameterNames = oldPNames;
    }

    private void resolveOrFail(ClassNode type, ASTNode node) {
        this.resolveOrFail(type, "", node);
    }

    private void resolveOrFail(ClassNode type, String msg, ASTNode node) {
        this.resolveOrFail(type, msg, node, false);
    }

    private void resolveOrFail(ClassNode type, String msg, ASTNode node, boolean preferImports) {
        this.visitTypeAnnotations(type);
        if (preferImports) {
            this.resolveGenericsTypes(type.getGenericsTypes());
            if (this.resolveAliasFromModule(type)) {
                return;
            }
        }
        if (this.resolve(type)) {
            return;
        }
        if (this.resolveToInner(type)) {
            return;
        }
        this.addError("unable to resolve class " + type.toString(false) + msg, node);
    }

    protected boolean resolveToInner(ClassNode type) {
        String name;
        if (type instanceof ConstructedClassWithPackage) {
            return false;
        }
        if (type instanceof ConstructedNestedClass) {
            return false;
        }
        ClassNode t = type;
        while (t.isArray()) {
            t = t.getComponentType();
        }
        String temp = name = t.getName();
        while (temp.lastIndexOf(46) != -1) {
            temp = ResolveVisitor.replaceLastPointWithDollar(temp);
            t.setName(temp);
            if (!this.resolve(t, true, false, false)) continue;
            return true;
        }
        t.setName(name);
        return false;
    }

    protected boolean resolve(ClassNode type) {
        return this.resolve(type, true, true, true);
    }

    protected boolean resolve(ClassNode type, boolean testModuleImports, boolean testDefaultImports, boolean testStaticInnerClasses) {
        boolean resolved;
        GenericsType[] genericsTypes = type.getGenericsTypes();
        this.resolveGenericsTypes(genericsTypes);
        if (type.isPrimaryClassNode()) {
            return true;
        }
        if (type.isResolved()) {
            return true;
        }
        if (type.isArray()) {
            ClassNode element = type.getComponentType();
            boolean resolved2 = this.resolve(element, testModuleImports, testDefaultImports, testStaticInnerClasses);
            if (resolved2) {
                ClassNode cn = element.makeArray();
                type.setRedirect(cn);
            }
            return resolved2;
        }
        String typeName = type.getName();
        GenericsType typeParameter = this.genericParameterNames.get(new GenericsType.GenericsTypeName(typeName));
        if (typeParameter != null) {
            type.setRedirect(typeParameter.getType());
            type.setGenericsTypes(new GenericsType[]{typeParameter});
            type.setGenericsPlaceHolder(true);
            return true;
        }
        if (this.currentClass.getNameWithoutPackage().equals(typeName)) {
            type.setRedirect(this.currentClass);
            resolved = true;
        } else {
            boolean bl = resolved = !type.hasPackageName() && this.resolveNestedClass(type) || this.resolveFromModule(type, testModuleImports) || this.resolveFromCompileUnit(type) || testDefaultImports && !type.hasPackageName() && this.resolveFromDefaultImports(type) || this.resolveToOuter(type) || testStaticInnerClasses && type.hasPackageName() && this.resolveFromStaticInnerClasses(type);
        }
        if (resolved && genericsTypes != null) {
            ResolveVisitor.resolveWildcardBounding(genericsTypes, type);
        }
        return resolved;
    }

    protected boolean resolveNestedClass(ClassNode type) {
        ClassNode cn;
        if (type instanceof ConstructedNestedClass || type instanceof ConstructedClassWithPackage) {
            return false;
        }
        HashSet<ClassNode> cycleCheck = new HashSet<ClassNode>();
        for (cn = this.currentClass; cn != null && cycleCheck.add(cn) && !ClassHelper.isObjectType(cn); cn = cn.getSuperClass()) {
            if (!this.setRedirect(type, cn)) continue;
            return true;
        }
        List<ClassNode> outerClasses = this.currentClass.getOuterClasses();
        if (!outerClasses.isEmpty()) {
            ListIterator<ClassNode> it = outerClasses.listIterator(outerClasses.size());
            while (it.hasPrevious()) {
                cn = it.previous();
                if (!this.setRedirect(type, cn)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean setRedirect(ClassNode type, ClassNode classToCheck) {
        String typeName = type.getName();
        Predicate<ClassNode> resolver = maybeOuter -> {
            ConstructedNestedClass maybeNested;
            if (!typeName.equals(maybeOuter.getName()) && (this.resolveFromCompileUnit(maybeNested = new ConstructedNestedClass((ClassNode)maybeOuter, typeName)) || this.resolveToOuter(maybeNested))) {
                type.setRedirect(maybeNested);
                return true;
            }
            return false;
        };
        if (resolver.test(classToCheck)) {
            if (this.currentClass != classToCheck && !this.currentClass.getOuterClasses().contains(classToCheck) && !ResolveVisitor.isVisibleNestedClass(type.redirect(), this.currentClass)) {
                type.setRedirect(null);
            } else {
                return true;
            }
        }
        if (classToCheck.getInterfaces().length > 0) {
            for (ClassNode face : classToCheck.getAllInterfaces()) {
                if (!resolver.test(face)) continue;
                return true;
            }
        }
        return false;
    }

    private static String replaceLastPointWithDollar(String name) {
        int lastPointIndex = name.lastIndexOf(46);
        return name.substring(0, lastPointIndex) + "$" + name.substring(lastPointIndex + 1);
    }

    protected boolean resolveFromStaticInnerClasses(ClassNode type) {
        if (!(type instanceof LowerCaseClass) && !(type instanceof ConstructedNestedClass)) {
            if (type instanceof ConstructedClassWithPackage) {
                ConstructedClassWithPackage tmp = (ConstructedClassWithPackage)type;
                String savedName = tmp.className;
                tmp.className = ResolveVisitor.replaceLastPointWithDollar(savedName);
                if (this.resolve(tmp, false, true, true)) {
                    type.setRedirect(tmp.redirect());
                    return true;
                }
                tmp.className = savedName;
            } else {
                String savedName = type.getName();
                type.setName(ResolveVisitor.replaceLastPointWithDollar(savedName));
                if (this.resolve(type, false, true, true)) {
                    return true;
                }
                type.setName(savedName);
            }
        }
        return false;
    }

    protected boolean resolveFromDefaultImports(ClassNode type) {
        if (!(type instanceof LowerCaseClass)) {
            String typeName = type.getName();
            Set<String> packagePrefixSet = DEFAULT_IMPORT_CLASS_AND_PACKAGES_CACHE.get(typeName);
            if (packagePrefixSet != null && this.resolveFromDefaultImports(type, packagePrefixSet.toArray(EMPTY_STRING_ARRAY))) {
                return true;
            }
            if (this.resolveFromDefaultImports(type, DEFAULT_IMPORTS)) {
                return true;
            }
            if ("BigInteger".equals(typeName)) {
                type.setRedirect(ClassHelper.BigInteger_TYPE);
                return true;
            }
            if ("BigDecimal".equals(typeName)) {
                type.setRedirect(ClassHelper.BigDecimal_TYPE);
                return true;
            }
        }
        return false;
    }

    protected boolean resolveFromDefaultImports(ClassNode type, String[] packagePrefixes) {
        String typeName = type.getName();
        for (String packagePrefix : packagePrefixes) {
            ConstructedClassWithPackage tmp = new ConstructedClassWithPackage(packagePrefix, typeName);
            if (!this.resolve(tmp, false, false, false)) continue;
            type.setRedirect(tmp.redirect());
            if (DEFAULT_IMPORTS == packagePrefixes) {
                Set packagePrefixSet = DEFAULT_IMPORT_CLASS_AND_PACKAGES_CACHE.computeIfAbsent(typeName, key -> new HashSet(2));
                packagePrefixSet.add(packagePrefix);
            }
            return true;
        }
        return false;
    }

    protected boolean resolveFromCompileUnit(ClassNode type) {
        CompileUnit compileUnit = this.currentClass.getCompileUnit();
        if (compileUnit == null) {
            return false;
        }
        ClassNode cuClass = compileUnit.getClass(type.getName());
        if (cuClass != null) {
            if (type != cuClass) {
                type.setRedirect(cuClass);
            }
            return true;
        }
        return false;
    }

    private void ambiguousClass(ClassNode type, ClassNode iType, String name) {
        if (type.getName().equals(iType.getName())) {
            this.addError("reference to " + name + " is ambiguous, both class " + type.getName() + " and " + iType.getName() + " match", type);
        } else {
            type.setRedirect(iType);
        }
    }

    private boolean resolveAliasFromModule(ClassNode type) {
        String name;
        if (type instanceof ConstructedClassWithPackage) {
            return false;
        }
        ModuleNode module = this.currentClass.getModule();
        if (module == null) {
            return false;
        }
        String pname = name = type.getName();
        int index = name.length();
        do {
            ConstructedNestedClass tmp;
            pname = name.substring(0, index);
            ClassNode aliasedNode = null;
            ImportNode importNode = module.getImport(pname);
            if (importNode != null && importNode != this.currentImport) {
                aliasedNode = importNode.getType();
            }
            if (aliasedNode == null && (importNode = module.getStaticImports().get(pname)) != null && importNode != this.currentImport && this.resolve(tmp = new ConstructedNestedClass(importNode.getType(), importNode.getFieldName()), false, false, true) && Modifier.isStatic(tmp.getModifiers())) {
                type.setRedirect(tmp.redirect());
                return true;
            }
            if (aliasedNode == null) continue;
            if (pname.length() == name.length()) {
                type.setRedirect(aliasedNode);
                return true;
            }
            String className = aliasedNode.getNameWithoutPackage() + "$" + name.substring(pname.length() + 1).replace('.', '$');
            ConstructedClassWithPackage tmp2 = new ConstructedClassWithPackage(aliasedNode.getPackageName() + ".", className);
            if (!this.resolve(tmp2, true, true, false)) continue;
            type.setRedirect(tmp2.redirect());
            return true;
        } while ((index = pname.lastIndexOf(46)) != -1);
        return false;
    }

    protected boolean resolveFromModule(ClassNode type, boolean testModuleImports) {
        if (type instanceof ConstructedNestedClass) {
            return false;
        }
        if (type instanceof LowerCaseClass) {
            return this.resolveAliasFromModule(type);
        }
        String name = type.getName();
        ModuleNode module = this.currentClass.getModule();
        if (module == null) {
            return false;
        }
        boolean newNameUsed = false;
        if (!type.hasPackageName() && module.hasPackageName() && !(type instanceof ConstructedClassWithPackage)) {
            type.setName(module.getPackageName() + name);
            newNameUsed = true;
        }
        List<ClassNode> moduleClasses = module.getClasses();
        for (ClassNode mClass : moduleClasses) {
            if (!mClass.getName().equals(type.getName())) continue;
            if (mClass != type) {
                type.setRedirect(mClass);
            }
            return true;
        }
        if (newNameUsed) {
            type.setName(name);
        }
        if (testModuleImports) {
            ClassNode tmp;
            ConstructedClassWithPackage tmp2;
            if (this.resolveAliasFromModule(type)) {
                return true;
            }
            if (module.hasPackageName() && this.resolve(tmp2 = new ConstructedClassWithPackage(module.getPackageName(), name), false, false, false)) {
                this.ambiguousClass(type, tmp2, name);
                return true;
            }
            for (ImportNode importNode : module.getStaticImports().values()) {
                if (!importNode.getFieldName().equals(name) || !this.resolve(tmp = new ConstructedNestedClass(importNode.getType(), name), false, false, true) || !Modifier.isStatic(tmp.getModifiers())) continue;
                type.setRedirect(tmp.redirect());
                return true;
            }
            for (ImportNode importNode : module.getStaticStarImports().values()) {
                tmp = new ConstructedNestedClass(importNode.getType(), name);
                if (!this.resolve(tmp, false, false, true) || !Modifier.isStatic(tmp.getModifiers())) continue;
                this.ambiguousClass(type, tmp, name);
                return true;
            }
            for (ImportNode importNode : module.getStarImports()) {
                if (importNode.getType() != null) {
                    tmp = new ConstructedNestedClass(importNode.getType(), name);
                    if (!this.resolve(tmp, false, false, true) || !Modifier.isStatic(tmp.getModifiers())) continue;
                    this.ambiguousClass(type, tmp, name);
                    return true;
                }
                tmp = new ConstructedClassWithPackage(importNode.getPackageName(), name);
                if (!this.resolve(tmp, false, false, true)) continue;
                this.ambiguousClass(type, tmp, name);
                return true;
            }
        }
        return false;
    }

    protected boolean resolveToOuter(ClassNode type) {
        ClassNodeResolver.LookupResult lr;
        String name = type.getName();
        if (this.classNodeResolver == null) {
            this.classNodeResolver = new ClassNodeResolver();
        }
        if (type instanceof LowerCaseClass) {
            this.classNodeResolver.cacheClass(name, ClassNodeResolver.NO_CLASS);
        } else if (!(this.currentClass.getModule().hasPackageName() && name.indexOf(46) == -1 || (lr = this.classNodeResolver.resolveName(name, this.compilationUnit)) == null)) {
            if (lr.isSourceUnit()) {
                this.currentClass.getCompileUnit().addClassNodeToCompile(type, lr.getSourceUnit());
                throw new Interrupt(this.compilationUnit);
            }
            type.setRedirect(lr.getClassNode());
            return true;
        }
        return false;
    }

    @Override
    public Expression transform(Expression exp) {
        Expression ret;
        if (exp == null) {
            return null;
        }
        if (exp instanceof VariableExpression) {
            ret = this.transformVariableExpression((VariableExpression)exp);
        } else if (exp.getClass() == PropertyExpression.class) {
            ret = this.transformPropertyExpression((PropertyExpression)exp);
        } else if (exp instanceof DeclarationExpression) {
            ret = this.transformDeclarationExpression((DeclarationExpression)exp);
        } else if (exp instanceof BinaryExpression) {
            ret = this.transformBinaryExpression((BinaryExpression)exp);
        } else if (exp instanceof MethodCallExpression) {
            ret = this.transformMethodCallExpression((MethodCallExpression)exp);
        } else if (exp instanceof ClosureExpression) {
            ret = this.transformClosureExpression((ClosureExpression)exp);
        } else if (exp instanceof ConstructorCallExpression) {
            ret = this.transformConstructorCallExpression((ConstructorCallExpression)exp);
        } else if (exp instanceof AnnotationConstantExpression) {
            ret = this.transformAnnotationConstantExpression((AnnotationConstantExpression)exp);
        } else {
            this.resolveOrFail(exp.getType(), exp);
            ret = exp.transformExpression(this);
        }
        if (ret != null && ret != exp) {
            ret.setSourcePosition(exp);
        }
        return ret;
    }

    private static String lookupClassName(PropertyExpression pe) {
        boolean doInitialClassTest = true;
        StringBuilder name = new StringBuilder(32);
        for (Expression expr = pe; expr != null && name != null; expr = expr.getObjectExpression()) {
            if (expr instanceof VariableExpression) {
                VariableExpression ve = (VariableExpression)expr;
                if (ve.isSuperExpression() || ve.isThisExpression()) {
                    return null;
                }
                String varName = ve.getName();
                Tuple2<StringBuilder, Boolean> classNameInfo = ResolveVisitor.makeClassName(doInitialClassTest, name, varName);
                name = classNameInfo.getV1();
                doInitialClassTest = classNameInfo.getV2();
                break;
            }
            if (expr.getClass() != PropertyExpression.class) {
                return null;
            }
            String property = expr.getPropertyAsString();
            if (property == null || property.equals("class")) {
                return null;
            }
            Tuple2<StringBuilder, Boolean> classNameInfo = ResolveVisitor.makeClassName(doInitialClassTest, name, property);
            name = classNameInfo.getV1();
            doInitialClassTest = classNameInfo.getV2();
        }
        if (name == null || name.length() == 0) {
            return null;
        }
        return name.toString();
    }

    private static Tuple2<StringBuilder, Boolean> makeClassName(boolean doInitialClassTest, StringBuilder name, String varName) {
        if (doInitialClassTest) {
            if (!ResolveVisitor.testVanillaNameForClass(varName)) {
                return Tuple.tuple(null, Boolean.TRUE);
            }
            return Tuple.tuple(new StringBuilder(varName), Boolean.FALSE);
        }
        name.insert(0, varName + ".");
        return Tuple.tuple(name, Boolean.FALSE);
    }

    private static Expression correctClassClassChain(PropertyExpression pe) {
        LinkedList<PropertyExpression> stack = new LinkedList<PropertyExpression>();
        ClassExpression found = null;
        for (Expression it = pe; it != null; it = it.getObjectExpression()) {
            if (it instanceof ClassExpression) {
                found = (ClassExpression)it;
                break;
            }
            if (it.getClass() != PropertyExpression.class) {
                return pe;
            }
            stack.addFirst((PropertyExpression)it);
        }
        if (found == null) {
            return pe;
        }
        if (stack.isEmpty()) {
            return pe;
        }
        Object stackElement = stack.removeFirst();
        if (stackElement.getClass() != PropertyExpression.class) {
            return pe;
        }
        PropertyExpression classPropertyExpression = (PropertyExpression)stackElement;
        String propertyNamePart = classPropertyExpression.getPropertyAsString();
        if (propertyNamePart == null || !propertyNamePart.equals("class")) {
            return pe;
        }
        found.setSourcePosition(classPropertyExpression);
        if (stack.isEmpty()) {
            return found;
        }
        stackElement = stack.removeFirst();
        if (stackElement.getClass() != PropertyExpression.class) {
            return pe;
        }
        PropertyExpression classPropertyExpressionContainer = (PropertyExpression)stackElement;
        classPropertyExpressionContainer.setObjectExpression(found);
        return pe;
    }

    protected Expression transformPropertyExpression(PropertyExpression pe) {
        ClassNode type;
        boolean itlp = this.isTopLevelProperty;
        boolean ipe = this.inPropertyExpression;
        Expression objectExpression = pe.getObjectExpression();
        this.inPropertyExpression = true;
        this.isTopLevelProperty = objectExpression.getClass() != PropertyExpression.class;
        objectExpression = this.transform(objectExpression);
        this.inPropertyExpression = false;
        Expression property = this.transform(pe.getProperty());
        this.isTopLevelProperty = itlp;
        this.inPropertyExpression = ipe;
        boolean spreadSafe = pe.isSpreadSafe();
        PropertyExpression old = pe;
        pe = new PropertyExpression(objectExpression, property, pe.isSafe());
        pe.setSpreadSafe(spreadSafe);
        pe.setSourcePosition(old);
        String className = ResolveVisitor.lookupClassName(pe);
        if (className != null && this.resolve(type = ClassHelper.make(className))) {
            return new ClassExpression(type);
        }
        if (objectExpression instanceof ClassExpression && pe.getPropertyAsString() != null) {
            ClassExpression ce = (ClassExpression)objectExpression;
            for (ClassNode classNode = ce.getType(); classNode != null; classNode = classNode.getSuperClass()) {
                ConstructedNestedClass type2 = new ConstructedNestedClass(classNode, pe.getPropertyAsString());
                if (!this.resolve(type2, false, false, false) || classNode != ce.getType() && !ResolveVisitor.isVisibleNestedClass(type2, ce.getType())) continue;
                return new ClassExpression(type2);
            }
        }
        Expression ret = pe;
        this.checkThisAndSuperAsPropertyAccess(pe);
        if (this.isTopLevelProperty) {
            ret = ResolveVisitor.correctClassClassChain(pe);
        }
        return ret;
    }

    private static boolean isVisibleNestedClass(ClassNode innerType, ClassNode outerType) {
        int modifiers = innerType.getModifiers();
        return Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers) || !Modifier.isPrivate(modifiers) && Objects.equals(innerType.getPackageName(), outerType.getPackageName());
    }

    private boolean directlyImplementsTrait(ClassNode trait) {
        ClassNode[] interfaces = this.currentClass.getInterfaces();
        if (interfaces == null) {
            return this.currentClass.getSuperClass().equals(trait);
        }
        for (ClassNode node : interfaces) {
            if (!node.equals(trait)) continue;
            return true;
        }
        return this.currentClass.getSuperClass().equals(trait);
    }

    private void checkThisAndSuperAsPropertyAccess(PropertyExpression expression) {
        if (expression.isImplicitThis()) {
            return;
        }
        String prop = expression.getPropertyAsString();
        if (prop == null) {
            return;
        }
        if (!prop.equals("this") && !prop.equals("super")) {
            return;
        }
        ClassNode type = expression.getObjectExpression().getType();
        if (expression.getObjectExpression() instanceof ClassExpression) {
            ClassNode iterType;
            if (!(this.currentClass instanceof InnerClassNode) && !Traits.isTrait(type)) {
                this.addError("The usage of 'Class.this' and 'Class.super' is only allowed in nested/inner classes.", expression);
                return;
            }
            if (this.currentScope != null && !this.currentScope.isInStaticContext() && Traits.isTrait(type) && "super".equals(prop) && this.directlyImplementsTrait(type)) {
                return;
            }
            for (iterType = this.currentClass; iterType != null && !iterType.equals(type); iterType = iterType.getOuterClass()) {
            }
            if (iterType == null) {
                this.addError("The class '" + type.getName() + "' needs to be an outer class of '" + this.currentClass.getName() + "' when using '.this' or '.super'.", expression);
            }
            if (!Modifier.isStatic(this.currentClass.getModifiers())) {
                return;
            }
            if (this.currentScope != null && !this.currentScope.isInStaticContext()) {
                return;
            }
            this.addError("The usage of 'Class.this' and 'Class.super' within static nested class '" + this.currentClass.getName() + "' is not allowed in a static context.", expression);
        }
    }

    protected Expression transformVariableExpression(VariableExpression ve) {
        this.visitAnnotations(ve);
        Variable v = ve.getAccessedVariable();
        if (!(v instanceof DynamicVariable) && !this.checkingVariableTypeInDeclaration) {
            return ve;
        }
        if (v instanceof DynamicVariable) {
            String name = ve.getName();
            ClassNode t = ClassHelper.make(name);
            boolean isClass = t.isResolved();
            if (!isClass) {
                if (Character.isLowerCase(name.charAt(0))) {
                    t = new LowerCaseClass(name);
                }
                isClass = this.resolve(t);
            }
            if (isClass) {
                for (VariableScope scope = this.currentScope; scope != null && !scope.isRoot() && scope.removeReferencedClassVariable(ve.getName()) != null; scope = scope.getParent()) {
                }
                return new ClassExpression(t);
            }
        }
        this.resolveOrFail(ve.getType(), ve);
        ClassNode origin = ve.getOriginType();
        if (origin != ve.getType()) {
            this.resolveOrFail(origin, ve);
        }
        return ve;
    }

    private static boolean testVanillaNameForClass(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        return !Character.isLowerCase(name.charAt(0));
    }

    protected Expression transformBinaryExpression(BinaryExpression be) {
        Expression left = this.transform(be.getLeftExpression());
        if (be.getOperation().isA(1100) && left instanceof ClassExpression) {
            ClassExpression ce = (ClassExpression)left;
            String error = "you tried to assign a value to the class '" + ce.getType().getName() + "'";
            if (ce.getType().isScript()) {
                error = error + ". Do you have a script with this name?";
            }
            this.addError(error, be.getLeftExpression());
            return be;
        }
        if (left instanceof ClassExpression && be.getOperation().isOneOf(new int[]{1905, 810, 811})) {
            if (be.getRightExpression() instanceof ListExpression) {
                ListExpression list = (ListExpression)be.getRightExpression();
                if (list.getExpressions().isEmpty()) {
                    return new ClassExpression(left.getType().makeArray());
                }
                boolean map = true;
                for (Expression expression : list.getExpressions()) {
                    if (expression instanceof MapEntryExpression) continue;
                    map = false;
                    break;
                }
                if (map) {
                    MapExpression me = new MapExpression();
                    for (Expression expression : list.getExpressions()) {
                        me.addMapEntryExpression((MapEntryExpression)this.transform(expression));
                    }
                    me.setSourcePosition(list);
                    return CastExpression.asExpression(left.getType(), me);
                }
            } else if (be.getRightExpression() instanceof SpreadMapExpression) {
                SpreadMapExpression mapExpression = (SpreadMapExpression)be.getRightExpression();
                Expression right = this.transform(mapExpression.getExpression());
                return CastExpression.asExpression(left.getType(), right);
            }
            if (be.getRightExpression() instanceof MapEntryExpression) {
                MapExpression me = new MapExpression();
                me.addMapEntryExpression((MapEntryExpression)this.transform(be.getRightExpression()));
                me.setSourcePosition(be.getRightExpression());
                return new CastExpression(left.getType(), me);
            }
        }
        Expression right = this.transform(be.getRightExpression());
        be.setLeftExpression(left);
        be.setRightExpression(right);
        return be;
    }

    protected Expression transformClosureExpression(ClosureExpression ce) {
        boolean oldInClosure = this.inClosure;
        this.inClosure = true;
        for (Parameter para : ClosureUtils.getParametersSafe(ce)) {
            ClassNode t = para.getType();
            this.resolveOrFail(t, ce);
            this.visitAnnotations(para);
            if (para.hasInitialExpression()) {
                para.setInitialExpression(this.transform(para.getInitialExpression()));
            }
            this.visitAnnotations(para);
        }
        Statement code = ce.getCode();
        if (code != null) {
            code.visit(this);
        }
        this.inClosure = oldInClosure;
        return ce;
    }

    protected Expression transformConstructorCallExpression(ConstructorCallExpression cce) {
        if (!cce.isUsingAnonymousInnerClass()) {
            ClassNode cceType = cce.getType();
            this.resolveOrFail(cceType, cce);
            if (cceType.isAbstract()) {
                this.addError("You cannot create an instance from the abstract " + ResolveVisitor.getDescription(cceType) + ".", cce);
            }
        }
        return cce.transformExpression(this);
    }

    private static String getDescription(ClassNode node) {
        return (node.isInterface() ? "interface" : "class") + " '" + node.getName() + "'";
    }

    protected Expression transformMethodCallExpression(MethodCallExpression mce) {
        Expression args = this.transform(mce.getArguments());
        Expression method = this.transform(mce.getMethod());
        Expression object = this.transform(mce.getObjectExpression());
        this.resolveGenericsTypes(mce.getGenericsTypes());
        MethodCallExpression ret = new MethodCallExpression(object, method, args);
        ret.setGenericsTypes(mce.getGenericsTypes());
        ret.setMethodTarget(mce.getMethodTarget());
        ret.setImplicitThis(mce.isImplicitThis());
        ret.setSpreadSafe(mce.isSpreadSafe());
        ret.setSafe(mce.isSafe());
        return ret;
    }

    protected Expression transformDeclarationExpression(DeclarationExpression de) {
        this.visitAnnotations(de);
        Expression oldLeft = de.getLeftExpression();
        this.checkingVariableTypeInDeclaration = true;
        Expression left = this.transform(oldLeft);
        this.checkingVariableTypeInDeclaration = false;
        if (left instanceof ClassExpression) {
            ClassExpression ce = (ClassExpression)left;
            this.addError("you tried to assign a value to the class " + ce.getType().getName(), oldLeft);
            return de;
        }
        Expression right = this.transform(de.getRightExpression());
        if (right == de.getRightExpression()) {
            this.fixDeclaringClass(de);
            return de;
        }
        DeclarationExpression newDeclExpr = new DeclarationExpression(left, de.getOperation(), right);
        newDeclExpr.setDeclaringClass(de.getDeclaringClass());
        newDeclExpr.addAnnotations(de.getAnnotations());
        newDeclExpr.copyNodeMetaData(de);
        this.fixDeclaringClass(newDeclExpr);
        return newDeclExpr;
    }

    private void fixDeclaringClass(DeclarationExpression newDeclExpr) {
        if (newDeclExpr.getDeclaringClass() == null && this.currentMethod != null) {
            newDeclExpr.setDeclaringClass(this.currentMethod.getDeclaringClass());
        }
    }

    protected Expression transformAnnotationConstantExpression(AnnotationConstantExpression ace) {
        this.visitAnnotation((AnnotationNode)ace.getValue());
        return ace;
    }

    private void visitTypeAnnotations(ClassNode node) {
        this.visitAnnotations(node.getTypeAnnotations());
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
        }
    }

    @Override
    protected void visitAnnotation(AnnotationNode node) {
        this.resolveOrFail(node.getClassNode(), " for annotation", node, true);
        for (Map.Entry<String, Expression> entry : node.getMembers().entrySet()) {
            Expression value = this.transform(entry.getValue());
            value = ExpressionUtils.transformInlineConstants(value);
            this.checkAnnotationMemberValue(value);
            entry.setValue(value);
        }
    }

    private void checkAnnotationMemberValue(Expression value) {
        if (value instanceof PropertyExpression) {
            PropertyExpression pe = (PropertyExpression)value;
            if (!(pe.getObjectExpression() instanceof ClassExpression)) {
                this.addError("unable to find class '" + pe.getText() + "' for annotation attribute constant", pe.getObjectExpression());
            }
        } else if (value instanceof ListExpression) {
            ListExpression le = (ListExpression)value;
            for (Expression e : le.getExpressions()) {
                this.checkAnnotationMemberValue(e);
            }
        }
    }

    @Override
    public void visitClass(ClassNode node) {
        ClassNode oldNode = this.currentClass;
        this.currentClass = node;
        ModuleNode module = node.getModule();
        if (!module.hasImportsResolved()) {
            ClassNode type;
            Iterator<ImportNode> iterator = module.getImports().iterator();
            while (iterator.hasNext()) {
                ImportNode importNode;
                this.currentImport = importNode = iterator.next();
                type = importNode.getType();
                if (this.resolve(type, false, false, true)) {
                    this.currentImport = null;
                    continue;
                }
                this.currentImport = null;
                this.addError("unable to resolve class " + type.getName(), type);
            }
            for (ImportNode importNode : module.getStarImports()) {
                if (importNode.getLineNumber() <= 0) continue;
                this.currentImport = importNode;
                String importName = importNode.getPackageName();
                ClassNode type2 = ClassHelper.makeWithoutCaching(importName = importName.substring(0, importName.length() - 1));
                if (this.resolve(type2, false, false, true)) {
                    importNode.setType(type2);
                }
                this.currentImport = null;
            }
            for (ImportNode importNode : module.getStaticImports().values()) {
                type = importNode.getType();
                if (this.resolve(type, true, true, true)) continue;
                this.addError("unable to resolve class " + type.getName(), type);
            }
            for (ImportNode importNode : module.getStaticStarImports().values()) {
                type = importNode.getType();
                if (this.resolve(type, true, true, true)) continue;
                this.addError("unable to resolve class " + type.getName(), type);
            }
            module.setImportsResolved(true);
        }
        if (!(node instanceof InnerClassNode) || Modifier.isStatic(node.getModifiers())) {
            this.genericParameterNames = new HashMap<GenericsType.GenericsTypeName, GenericsType>();
        }
        this.resolveGenericsHeader(node.getGenericsTypes());
        switch (this.phase) {
            case 0: 
            case 1: {
                ClassNode sn = node.getUnresolvedSuperClass();
                if (sn != null) {
                    this.resolveOrFail(sn, "", node, true);
                }
                for (ClassNode classNode : node.getInterfaces()) {
                    this.resolveOrFail(classNode, "", node, true);
                }
                if (sn != null) {
                    this.checkCyclicInheritance(node, sn);
                }
                for (ClassNode classNode : node.getInterfaces()) {
                    this.checkCyclicInheritance(node, classNode);
                }
                if (node.getGenericsTypes() != null) {
                    for (GenericsType genericsType : node.getGenericsTypes()) {
                        if (genericsType == null || genericsType.getUpperBounds() == null) continue;
                        for (ClassNode variant : genericsType.getUpperBounds()) {
                            if (!variant.isGenericsPlaceHolder()) continue;
                            this.checkCyclicInheritance(variant, genericsType.getType());
                        }
                    }
                }
            }
            case 2: {
                Iterator<InnerClassNode> iterator = node.getInnerClasses();
                while (iterator.hasNext()) {
                    InnerClassNode cn = iterator.next();
                    if (!cn.isAnonymous()) continue;
                    MethodNode enclosingMethod = cn.getEnclosingMethod();
                    if (enclosingMethod != null) {
                        this.resolveGenericsHeader(enclosingMethod.getGenericsTypes());
                    }
                    this.resolveOrFail(cn.getUnresolvedSuperClass(false), cn);
                }
                if (this.phase == 1) break;
                new VariableScopeVisitor(this.source).visitClass(node);
                this.visitTypeAnnotations(node);
                super.visitClass(node);
            }
        }
        this.currentClass = oldNode;
    }

    private void addFatalError(String text, ASTNode node) {
        this.source.addFatalError(text, node);
    }

    private void checkCyclicInheritance(ClassNode node, ClassNode type) {
        if (type.redirect() == node || type.getOuterClasses().contains(node)) {
            this.addFatalError("Cycle detected: the type " + node.getName() + " cannot extend/implement itself or one of its own member types", type);
        } else if (type != ClassHelper.OBJECT_TYPE) {
            HashSet<ClassNode> done = new HashSet<ClassNode>();
            done.add(ClassHelper.OBJECT_TYPE);
            done.add(null);
            LinkedList<ClassNode> todo = new LinkedList<ClassNode>();
            Collections.addAll(todo, type.getInterfaces());
            todo.add(type.getUnresolvedSuperClass());
            todo.add(type.getOuterClass());
            do {
                ClassNode next;
                if (!done.add(next = (ClassNode)todo.poll())) continue;
                if (next.redirect() == node) {
                    ClassNode cn = type;
                    while (cn.getOuterClass() != null) {
                        cn = cn.getOuterClass();
                    }
                    this.addFatalError("Cycle detected: a cycle exists in the type hierarchy between " + node.getName() + " and " + cn.getName(), type);
                }
                Collections.addAll(todo, next.getInterfaces());
                todo.add(next.getUnresolvedSuperClass());
                todo.add(next.getOuterClass());
            } while (!todo.isEmpty());
        }
    }

    @Override
    public void visitCatchStatement(CatchStatement cs) {
        this.resolveOrFail(cs.getExceptionType(), cs);
        if (ClassHelper.isDynamicTyped(cs.getExceptionType())) {
            cs.getVariable().setType(ClassHelper.make(Exception.class));
        }
        super.visitCatchStatement(cs);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        this.resolveOrFail(forLoop.getVariableType(), forLoop);
        super.visitForLoop(forLoop);
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        VariableScope oldScope = this.currentScope;
        this.currentScope = block.getVariableScope();
        super.visitBlockStatement(block);
        this.currentScope = oldScope;
    }

    private boolean resolveGenericsTypes(GenericsType[] types) {
        if (types == null) {
            return true;
        }
        this.currentClass.setUsingGenerics(true);
        boolean resolved = true;
        for (GenericsType type : types) {
            resolved = this.resolveGenericsType(type) && resolved;
        }
        return resolved;
    }

    private void resolveGenericsHeader(GenericsType[] types) {
        this.resolveGenericsHeader(types, null, 0);
    }

    private void resolveGenericsHeader(GenericsType[] types, GenericsType rootType, int level) {
        if (types == null) {
            return;
        }
        this.currentClass.setUsingGenerics(true);
        LinkedList<Tuple2<ClassNode, GenericsType>> upperBoundsWithGenerics = new LinkedList<Tuple2<ClassNode, GenericsType>>();
        LinkedList<Tuple2<ClassNode, ClassNode>> upperBoundsToResolve = new LinkedList<Tuple2<ClassNode, ClassNode>>();
        for (GenericsType type : types) {
            boolean dealWithGenerics;
            if (level > 0 && type.getName().equals(rootType.getName())) continue;
            String name = type.getName();
            ClassNode typeType = type.getType();
            GenericsType.GenericsTypeName gtn = new GenericsType.GenericsTypeName(name);
            boolean isWildcardGT = QUESTION_MARK.equals(name);
            boolean bl = dealWithGenerics = level == 0 || level > 0 && this.genericParameterNames.get(gtn) != null;
            if (type.getUpperBounds() != null) {
                boolean nameAdded = false;
                for (ClassNode upperBound : type.getUpperBounds()) {
                    if (upperBound == null) continue;
                    if (!isWildcardGT) {
                        if (!(nameAdded && this.resolve(typeType) || !dealWithGenerics)) {
                            type.setPlaceholder(true);
                            typeType.setRedirect(upperBound);
                            this.genericParameterNames.put(gtn, type);
                            nameAdded = true;
                        }
                        upperBoundsToResolve.add(Tuple.tuple(upperBound, typeType));
                    }
                    if (!upperBound.isUsingGenerics()) continue;
                    upperBoundsWithGenerics.add(Tuple.tuple(upperBound, type));
                }
                continue;
            }
            if (isWildcardGT || !dealWithGenerics) continue;
            type.setPlaceholder(true);
            GenericsType last = this.genericParameterNames.put(gtn, type);
            typeType.setRedirect(last != null ? last.getType().redirect() : ClassHelper.OBJECT_TYPE);
        }
        for (Tuple2 tuple2 : upperBoundsToResolve) {
            ClassNode upperBound = (ClassNode)tuple2.getV1();
            ClassNode classNode = (ClassNode)tuple2.getV2();
            this.resolveOrFail(upperBound, classNode);
        }
        for (Tuple2 tuple2 : upperBoundsWithGenerics) {
            ClassNode upperBound = (ClassNode)tuple2.getV1();
            GenericsType gt = (GenericsType)tuple2.getV2();
            this.resolveGenericsHeader(upperBound.getGenericsTypes(), 0 == level ? gt : rootType, level + 1);
        }
    }

    private boolean resolveGenericsType(GenericsType genericsType) {
        if (genericsType.isResolved()) {
            return true;
        }
        this.currentClass.setUsingGenerics(true);
        ClassNode type = genericsType.getType();
        this.visitTypeAnnotations(type);
        GenericsType tp = this.genericParameterNames.get(new GenericsType.GenericsTypeName(type.getName()));
        if (tp != null) {
            ClassNode[] bounds = tp.getUpperBounds();
            if (bounds != null && (bounds.length > 1 || bounds[0].isRedirectNode() && bounds[0].redirect().getGenericsTypes() != null)) {
                type.setGenericsPlaceHolder(true);
                type.setRedirect(bounds[0]);
            } else {
                type.setRedirect(tp.getType());
            }
            genericsType.setPlaceholder(true);
        } else {
            ClassNode[] upperBounds = genericsType.getUpperBounds();
            if (upperBounds != null) {
                for (ClassNode upperBound : upperBounds) {
                    this.resolveOrFail(upperBound, genericsType);
                    type.setRedirect(upperBound);
                    this.resolveGenericsTypes(upperBound.getGenericsTypes());
                }
            } else if (genericsType.isWildcard()) {
                type.setRedirect(ClassHelper.OBJECT_TYPE);
            } else {
                this.resolveOrFail(type, genericsType);
            }
        }
        if (genericsType.getLowerBound() != null) {
            this.resolveOrFail(genericsType.getLowerBound(), genericsType);
        }
        if (this.resolveGenericsTypes(type.getGenericsTypes())) {
            genericsType.setResolved(genericsType.getType().isResolved());
        }
        return genericsType.isResolved();
    }

    private static void resolveWildcardBounding(GenericsType[] typeArguments, ClassNode type) {
        int n = typeArguments.length;
        for (int i = 0; i < n; ++i) {
            ClassNode implicitBound;
            GenericsType[] parameters;
            GenericsType argument = typeArguments[i];
            if (!argument.isWildcard() || argument.getUpperBounds() != null || (parameters = type.redirect().getGenericsTypes()) == null || i >= parameters.length || ClassHelper.isObjectType(implicitBound = parameters[i].getType())) continue;
            argument.getType().setRedirect(implicitBound);
        }
    }

    static {
        DEFAULT_IMPORT_CLASS_AND_PACKAGES_CACHE.putAll(VMPluginFactory.getPlugin().getDefaultImportClasses(DEFAULT_IMPORTS));
    }

    private static class ConstructedClassWithPackage
    extends ClassNode {
        final String prefix;
        String className;

        public ConstructedClassWithPackage(String pkg, String name) {
            super(pkg + name, 1, ClassHelper.OBJECT_TYPE);
            this.isPrimaryNode = false;
            this.prefix = pkg;
            this.className = name;
        }

        @Override
        public String getName() {
            if (this.redirect() != this) {
                return super.getName();
            }
            return this.prefix + this.className;
        }

        @Override
        public boolean hasPackageName() {
            if (this.redirect() != this) {
                return super.hasPackageName();
            }
            return this.className.indexOf(46) != -1;
        }

        @Override
        public String setName(String name) {
            if (this.redirect() != this) {
                return super.setName(name);
            }
            throw new GroovyBugError("ConstructedClassWithPackage#setName should not be called");
        }
    }

    private static class ConstructedNestedClass
    extends ClassNode {
        final ClassNode knownEnclosingType;

        public ConstructedNestedClass(ClassNode outer, String inner) {
            super(outer.getName() + "$" + inner.replace('.', '$'), 1, ClassHelper.OBJECT_TYPE);
            this.knownEnclosingType = outer;
            this.isPrimaryNode = false;
        }

        @Override
        public String getUnresolvedName() {
            return super.getUnresolvedName().replace(this.knownEnclosingType.getName(), this.knownEnclosingType.getUnresolvedName());
        }

        @Override
        public boolean hasPackageName() {
            if (this.redirect() != this) {
                return super.hasPackageName();
            }
            return this.knownEnclosingType.hasPackageName();
        }

        @Override
        public String setName(String name) {
            if (this.redirect() != this) {
                return super.setName(name);
            }
            throw new GroovyBugError("ConstructedNestedClass#setName should not be called");
        }
    }

    private static class LowerCaseClass
    extends ClassNode {
        final String className;

        public LowerCaseClass(String name) {
            super(name, 1, ClassHelper.OBJECT_TYPE);
            this.isPrimaryNode = false;
            this.className = name;
        }

        @Override
        public String getName() {
            if (this.redirect() != this) {
                return super.getName();
            }
            return this.className;
        }

        @Override
        public boolean hasPackageName() {
            if (this.redirect() != this) {
                return super.hasPackageName();
            }
            return false;
        }

        @Override
        public String setName(String name) {
            if (this.redirect() != this) {
                return super.setName(name);
            }
            throw new GroovyBugError("LowerCaseClass#setName should not be called");
        }
    }

    static class Interrupt
    extends CompilationFailedException {
        private Interrupt(ProcessingUnit unit) {
            super(4, unit);
        }
    }
}

