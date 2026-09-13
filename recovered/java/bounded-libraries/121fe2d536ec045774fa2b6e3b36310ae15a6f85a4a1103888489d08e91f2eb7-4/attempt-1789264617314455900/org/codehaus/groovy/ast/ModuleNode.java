/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import groovy.lang.Binding;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.transform.BaseScriptASTTransformation;

public class ModuleNode
extends ASTNode {
    private List<ClassNode> classes = new LinkedList<ClassNode>();
    private final List<MethodNode> methods = new ArrayList<MethodNode>();
    private final List<ImportNode> imports = new ArrayList<ImportNode>();
    private final List<ImportNode> starImports = new ArrayList<ImportNode>();
    private final Map<String, ImportNode> staticImports = new LinkedHashMap<String, ImportNode>();
    private final Map<String, ImportNode> staticStarImports = new LinkedHashMap<String, ImportNode>();
    private CompileUnit unit;
    private PackageNode packageNode;
    private String description;
    private boolean createClassForStatements = true;
    private transient SourceUnit context;
    private boolean importsResolved;
    private ClassNode scriptDummy;
    private String mainClassName;
    private final BlockStatement statementBlock = new BlockStatement();

    public ModuleNode(SourceUnit context) {
        this.context = context;
    }

    public ModuleNode(CompileUnit unit) {
        this.unit = unit;
    }

    public List<ClassNode> getClasses() {
        if (this.createClassForStatements && (!this.statementBlock.isEmpty() || !this.methods.isEmpty() || this.isPackageInfo())) {
            ClassNode mainClass = this.createStatementsClass();
            this.mainClassName = mainClass.getName();
            this.createClassForStatements = false;
            this.classes.add(0, mainClass);
            mainClass.setModule(this);
            this.addToCompileUnit(mainClass);
        }
        return this.classes;
    }

    public List<MethodNode> getMethods() {
        return this.methods;
    }

    public List<ImportNode> getImports() {
        return new ArrayList<ImportNode>(this.imports);
    }

    public List<ImportNode> getStarImports() {
        return this.starImports;
    }

    public Map<String, ImportNode> getStaticImports() {
        return this.staticImports;
    }

    public Map<String, ImportNode> getStaticStarImports() {
        return this.staticStarImports;
    }

    public ClassNode getImportType(String alias) {
        ImportNode node = this.getImport(alias);
        return node != null ? node.getType() : null;
    }

    public ImportNode getImport(String alias) {
        Map aliases = this.getNodeMetaData("import.aliases", x -> this.imports.stream().collect(Collectors.toMap(ImportNode::getAlias, n -> n, (n, m) -> m)));
        return (ImportNode)aliases.get(alias);
    }

    public void addImport(String alias, ClassNode type) {
        this.addImport(alias, type, Collections.emptyList());
    }

    public void addImport(String alias, ClassNode type, List<AnnotationNode> annotations) {
        ImportNode importNode = new ImportNode(type, alias);
        importNode.addAnnotations(annotations);
        this.imports.add(importNode);
        this.removeNodeMetaData("import.aliases");
        this.storeLastAddedImportNode(importNode);
    }

    public void addStarImport(String packageName) {
        this.addStarImport(packageName, Collections.emptyList());
    }

    public void addStarImport(String packageName, List<AnnotationNode> annotations) {
        ImportNode importNode = new ImportNode(packageName);
        importNode.addAnnotations(annotations);
        this.starImports.add(importNode);
        this.storeLastAddedImportNode(importNode);
    }

    public void addStaticImport(ClassNode type, String fieldName, String alias) {
        this.addStaticImport(type, fieldName, alias, Collections.emptyList());
    }

    public void addStaticImport(ClassNode type, String fieldName, String alias, List<AnnotationNode> annotations) {
        ImportNode node = new ImportNode(type, fieldName, alias);
        node.addAnnotations(annotations);
        ImportNode prev = this.staticImports.put(alias, node);
        if (prev != null) {
            this.staticImports.put(prev.toString(), prev);
            this.staticImports.put(alias, this.staticImports.remove(alias));
        }
        this.storeLastAddedImportNode(node);
    }

    public void addStaticStarImport(String name, ClassNode type) {
        this.addStaticStarImport(name, type, Collections.emptyList());
    }

    public void addStaticStarImport(String name, ClassNode type, List<AnnotationNode> annotations) {
        ImportNode node = new ImportNode(type);
        node.addAnnotations(annotations);
        this.staticStarImports.put(name, node);
        this.storeLastAddedImportNode(node);
    }

    public void addStatement(Statement node) {
        this.statementBlock.addStatement(node);
    }

    public void addClass(ClassNode node) {
        if (this.classes.isEmpty()) {
            this.mainClassName = node.getName();
        }
        this.classes.add(node);
        node.setModule(this);
        this.addToCompileUnit(node);
    }

    private void addToCompileUnit(ClassNode node) {
        if (this.unit != null) {
            this.unit.addClass(node);
        }
    }

    public void addMethod(MethodNode node) {
        this.methods.add(node);
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
    }

    public String getPackageName() {
        return this.packageNode == null ? null : this.packageNode.getName();
    }

    public PackageNode getPackage() {
        return this.packageNode;
    }

    public boolean hasPackage() {
        return this.packageNode != null;
    }

    public void setPackage(PackageNode packageNode) {
        this.packageNode = packageNode;
    }

    public boolean hasPackageName() {
        return this.packageNode != null && this.packageNode.getName() != null;
    }

    public void setPackageName(String packageName) {
        this.setPackage(new PackageNode(packageName));
    }

    public String getDescription() {
        if (this.context != null) {
            return this.context.getName();
        }
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompileUnit getUnit() {
        return this.unit;
    }

    void setUnit(CompileUnit unit) {
        this.unit = unit;
    }

    public SourceUnit getContext() {
        return this.context;
    }

    private boolean isPackageInfo() {
        return this.context != null && this.context.getName() != null && this.context.getName().endsWith("package-info.groovy");
    }

    public ClassNode getScriptClassDummy() {
        ClassNode classNode;
        if (this.scriptDummy != null) {
            this.setScriptBaseClassFromConfig(this.scriptDummy);
            return this.scriptDummy;
        }
        String name = this.getPackageName();
        if (name == null) {
            name = "";
        }
        if (this.getDescription() == null) {
            throw new RuntimeException("Cannot generate main(String[]) class for statements when we have no file description");
        }
        name = name + GeneratorContext.encodeAsValidClassName(this.extractClassFromFileDescription());
        if (this.isPackageInfo()) {
            classNode = new ClassNode(name, 1536, ClassHelper.OBJECT_TYPE);
        } else {
            classNode = new ClassNode(name, 1, ClassHelper.SCRIPT_TYPE);
            this.setScriptBaseClassFromConfig(classNode);
            classNode.setScript(true);
            classNode.setScriptBody(true);
        }
        this.scriptDummy = classNode;
        return classNode;
    }

    private void setScriptBaseClassFromConfig(ClassNode cn) {
        String baseClassName = null;
        if (this.unit != null) {
            baseClassName = this.unit.getConfig().getScriptBaseClass();
        } else if (this.context != null) {
            baseClassName = this.context.getConfiguration().getScriptBaseClass();
        }
        if (baseClassName != null && !cn.getSuperClass().getName().equals(baseClassName)) {
            cn.setSuperClass(ClassHelper.make(baseClassName));
            AnnotationNode annotationNode = new AnnotationNode(BaseScriptASTTransformation.MY_TYPE);
            cn.addAnnotation(annotationNode);
        }
    }

    private static Parameter[] finalParam(ClassNode type, String name) {
        Parameter parameter = GeneralUtils.param(type, name);
        parameter.setModifiers(16);
        return GeneralUtils.params(parameter);
    }

    protected ClassNode createStatementsClass() {
        ClassNode classNode = this.getScriptClassDummy();
        if (classNode.getName().endsWith("package-info")) {
            return classNode;
        }
        MethodNode existingMain = this.handleMainMethodIfPresent(this.methods);
        classNode.addMethod(new MethodNode("main", 9, ClassHelper.VOID_TYPE, ModuleNode.finalParam(ClassHelper.STRING_TYPE.makeArray(), "args"), ClassNode.EMPTY_ARRAY, GeneralUtils.stmt(GeneralUtils.callX(ClassHelper.make(InvokerHelper.class), "runScript", (Expression)GeneralUtils.args(GeneralUtils.classX(classNode), GeneralUtils.varX("args"))))));
        MethodNode methodNode = new MethodNode("run", 1, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, this.statementBlock);
        methodNode.setIsScriptBody();
        if (existingMain != null) {
            methodNode.addAnnotations(existingMain.getAnnotations());
        }
        classNode.addMethod(methodNode);
        classNode.addConstructor(1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new BlockStatement());
        Statement stmt = classNode.getSuperClass().getDeclaredConstructor(GeneralUtils.params(GeneralUtils.param(ClassHelper.BINDING_TYPE, "context"))) != null ? GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.SUPER, GeneralUtils.args(GeneralUtils.varX("context")))) : GeneralUtils.stmt(GeneralUtils.callX((Expression)GeneralUtils.varX("super"), "setBinding", (Expression)GeneralUtils.args(GeneralUtils.varX("context"))));
        classNode.addConstructor(1, ModuleNode.finalParam(ClassHelper.make(Binding.class), "context"), ClassNode.EMPTY_ARRAY, stmt);
        for (MethodNode method : this.methods) {
            if (method.isAbstract()) {
                throw new RuntimeException("Cannot use abstract methods in a script, they are only available inside classes. Method: " + method.getName());
            }
            classNode.addMethod(method);
        }
        return classNode;
    }

    private MethodNode handleMainMethodIfPresent(List<MethodNode> methods) {
        boolean found = false;
        MethodNode result = null;
        Iterator<MethodNode> iter = methods.iterator();
        while (iter.hasNext()) {
            MethodNode node = iter.next();
            if (!node.getName().equals("main") || !node.isStatic() || node.getParameters().length != 1) continue;
            ClassNode argType = node.getParameters()[0].getType();
            ClassNode retType = node.getReturnType();
            boolean argTypeMatches = ClassHelper.isObjectType(argType) || argType.getName().contains("String[]");
            boolean retTypeMatches = ClassHelper.isPrimitiveVoid(retType) || ClassHelper.isObjectType(retType);
            if (!retTypeMatches || !argTypeMatches) continue;
            if (found) {
                throw new RuntimeException("Repetitive main method found.");
            }
            found = true;
            result = node;
            if (this.statementBlock.isEmpty()) {
                this.addStatement(node.getCode());
            }
            iter.remove();
        }
        return result;
    }

    protected String extractClassFromFileDescription() {
        String answer = this.getDescription();
        try {
            URI uri = new URI(answer);
            String path = uri.getPath();
            String schemeSpecific = uri.getSchemeSpecificPart();
            if (path != null && !path.isEmpty()) {
                answer = path;
            } else if (schemeSpecific != null && !schemeSpecific.isEmpty()) {
                answer = schemeSpecific;
            }
        }
        catch (URISyntaxException uri) {
            // empty catch block
        }
        int slashIdx = answer.lastIndexOf(47);
        int separatorIdx = answer.lastIndexOf(File.separatorChar);
        int dotIdx = answer.lastIndexOf(46);
        if (dotIdx > 0 && dotIdx > Math.max(slashIdx, separatorIdx)) {
            answer = answer.substring(0, dotIdx);
        }
        if (slashIdx >= 0) {
            answer = answer.substring(slashIdx + 1);
        }
        if ((separatorIdx = answer.lastIndexOf(File.separatorChar)) >= 0) {
            answer = answer.substring(separatorIdx + 1);
        }
        return answer;
    }

    public boolean isEmpty() {
        return this.classes.isEmpty() && this.statementBlock.getStatements().isEmpty();
    }

    public void sortClasses() {
        if (this.isEmpty()) {
            return;
        }
        LinkedList<ClassNode> sorted = new LinkedList<ClassNode>();
        List<ClassNode> todo = this.getClasses();
        int level = 1;
        while (!todo.isEmpty()) {
            Iterator<ClassNode> it = todo.iterator();
            while (it.hasNext()) {
                ClassNode cn;
                ClassNode sc = cn = it.next();
                for (int i = 0; sc != null && i < level; sc = sc.getSuperClass(), ++i) {
                }
                if (sc != null && sc.isPrimaryClassNode()) continue;
                sorted.add(cn);
                it.remove();
            }
            ++level;
        }
        this.classes = sorted;
    }

    public boolean hasImportsResolved() {
        return this.importsResolved;
    }

    public void setImportsResolved(boolean importsResolved) {
        this.importsResolved = importsResolved;
    }

    private void storeLastAddedImportNode(ImportNode node) {
        if (this.getNodeMetaData(ImportNode.class) == ImportNode.class) {
            this.putNodeMetaData(ImportNode.class, node);
        }
    }

    public String getMainClassName() {
        return this.mainClassName;
    }

    public BlockStatement getStatementBlock() {
        return this.statementBlock;
    }
}

