/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.InterfaceHelperClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.asm.AssertionWriter;
import org.codehaus.groovy.classgen.asm.BinaryExpressionHelper;
import org.codehaus.groovy.classgen.asm.BinaryExpressionMultiTypeDispatcher;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.CallSiteWriter;
import org.codehaus.groovy.classgen.asm.ClosureWriter;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.InvocationWriter;
import org.codehaus.groovy.classgen.asm.LambdaWriter;
import org.codehaus.groovy.classgen.asm.MethodPointerExpressionWriter;
import org.codehaus.groovy.classgen.asm.MethodReferenceExpressionWriter;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.OptimizingStatementWriter;
import org.codehaus.groovy.classgen.asm.StatementMetaTypeChooser;
import org.codehaus.groovy.classgen.asm.StatementWriter;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.UnaryExpressionHelper;
import org.codehaus.groovy.classgen.asm.indy.IndyBinHelper;
import org.codehaus.groovy.classgen.asm.indy.IndyCallSiteWriter;
import org.codehaus.groovy.classgen.asm.indy.InvokeDynamicWriter;
import org.codehaus.groovy.classgen.asm.util.LoggableClassVisitor;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;

public class WriterController {
    private AsmClassGenerator acg;
    private MethodVisitor methodVisitor;
    private CompileStack compileStack;
    private OperandStack operandStack;
    private ClassNode classNode;
    private CallSiteWriter callSiteWriter;
    private ClassVisitor cv;
    private ClosureWriter closureWriter;
    private LambdaWriter lambdaWriter;
    private String internalClassName;
    private InvocationWriter invocationWriter;
    private BinaryExpressionHelper binaryExpHelper;
    private BinaryExpressionHelper fastPathBinaryExpHelper;
    private UnaryExpressionHelper unaryExpressionHelper;
    private UnaryExpressionHelper fastPathUnaryExpressionHelper;
    private AssertionWriter assertionWriter;
    private String internalBaseClassName;
    private ClassNode outermostClass;
    private MethodNode methodNode;
    private ConstructorNode constructorNode;
    private GeneratorContext context;
    private InterfaceHelperClassNode interfaceClassLoadingClass;
    public boolean optimizeForInt = true;
    private StatementWriter statementWriter;
    private boolean fastPath;
    private TypeChooser typeChooser;
    private int bytecodeVersion = CompilerConfiguration.DEFAULT.getBytecodeVersion();
    private int lineNumber = -1;
    private int helperMethodIndex = 0;
    private List<String> superMethodNames = new ArrayList<String>();
    private MethodPointerExpressionWriter methodPointerExpressionWriter;
    private MethodReferenceExpressionWriter methodReferenceExpressionWriter;

    public void init(AsmClassGenerator asmClassGenerator, GeneratorContext gcon, ClassVisitor cv, ClassNode cn) {
        CompilerConfiguration config = cn.getCompileUnit().getConfig();
        Map<String, Boolean> optOptions = config.getOptimizationOptions();
        boolean invokedynamic = false;
        if (!optOptions.isEmpty()) {
            if (Boolean.FALSE.equals(optOptions.get("all"))) {
                this.optimizeForInt = false;
            } else {
                if (config.isIndyEnabled()) {
                    invokedynamic = true;
                }
                if (Boolean.FALSE.equals(optOptions.get("int"))) {
                    this.optimizeForInt = false;
                }
                if (invokedynamic) {
                    this.optimizeForInt = false;
                }
            }
        }
        this.classNode = cn;
        this.outermostClass = null;
        this.internalClassName = BytecodeHelper.getClassInternalName(cn);
        this.bytecodeVersion = config.getBytecodeVersion();
        if (invokedynamic) {
            this.invocationWriter = new InvokeDynamicWriter(this);
            this.callSiteWriter = new IndyCallSiteWriter(this);
            this.binaryExpHelper = new IndyBinHelper(this);
        } else {
            this.callSiteWriter = new CallSiteWriter(this);
            this.invocationWriter = new InvocationWriter(this);
            this.binaryExpHelper = new BinaryExpressionHelper(this);
        }
        this.unaryExpressionHelper = new UnaryExpressionHelper(this);
        if (this.optimizeForInt) {
            this.fastPathBinaryExpHelper = new BinaryExpressionMultiTypeDispatcher(this);
            this.fastPathUnaryExpressionHelper = new UnaryExpressionHelper(this);
        } else {
            this.fastPathBinaryExpHelper = this.binaryExpHelper;
            this.fastPathUnaryExpressionHelper = new UnaryExpressionHelper(this);
        }
        this.operandStack = new OperandStack(this);
        this.assertionWriter = new AssertionWriter(this);
        this.closureWriter = new ClosureWriter(this);
        this.lambdaWriter = new LambdaWriter(this);
        this.methodPointerExpressionWriter = new MethodPointerExpressionWriter(this);
        this.methodReferenceExpressionWriter = new MethodReferenceExpressionWriter(this);
        this.internalBaseClassName = BytecodeHelper.getClassInternalName(cn.getSuperClass());
        this.acg = asmClassGenerator;
        this.context = gcon;
        this.compileStack = new CompileStack(this);
        this.cv = WriterController.createClassVisitor(cv, config);
        this.statementWriter = this.optimizeForInt ? new OptimizingStatementWriter(this) : new StatementWriter(this);
        this.typeChooser = new StatementMetaTypeChooser();
    }

    private static ClassVisitor createClassVisitor(ClassVisitor cv, CompilerConfiguration config) {
        if (!config.isLogClassgen() || cv instanceof LoggableClassVisitor) {
            return cv;
        }
        return new LoggableClassVisitor(cv, config);
    }

    public AsmClassGenerator getAcg() {
        return this.acg;
    }

    @Deprecated
    public ClassVisitor getCv() {
        return this.cv;
    }

    public ClassVisitor getClassVisitor() {
        return this.cv;
    }

    public MethodVisitor getMethodVisitor() {
        return this.methodVisitor;
    }

    public void setMethodVisitor(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public GeneratorContext getContext() {
        return this.context;
    }

    public CompileStack getCompileStack() {
        return this.compileStack;
    }

    public OperandStack getOperandStack() {
        return this.operandStack;
    }

    public SourceUnit getSourceUnit() {
        return this.getAcg().getSourceUnit();
    }

    public TypeChooser getTypeChooser() {
        return this.typeChooser;
    }

    public UnaryExpressionHelper getUnaryExpressionHelper() {
        if (this.fastPath) {
            return this.fastPathUnaryExpressionHelper;
        }
        return this.unaryExpressionHelper;
    }

    public BinaryExpressionHelper getBinaryExpressionHelper() {
        if (this.fastPath) {
            return this.fastPathBinaryExpHelper;
        }
        return this.binaryExpHelper;
    }

    public AssertionWriter getAssertionWriter() {
        return this.assertionWriter;
    }

    public CallSiteWriter getCallSiteWriter() {
        return this.callSiteWriter;
    }

    public ClosureWriter getClosureWriter() {
        return this.closureWriter;
    }

    public LambdaWriter getLambdaWriter() {
        return this.lambdaWriter;
    }

    public StatementWriter getStatementWriter() {
        return this.statementWriter;
    }

    public InvocationWriter getInvocationWriter() {
        return this.invocationWriter;
    }

    public MethodPointerExpressionWriter getMethodPointerExpressionWriter() {
        return this.methodPointerExpressionWriter;
    }

    public MethodReferenceExpressionWriter getMethodReferenceExpressionWriter() {
        return this.methodReferenceExpressionWriter;
    }

    public String getClassName() {
        String className = !this.classNode.isInterface() || this.interfaceClassLoadingClass == null ? this.internalClassName : BytecodeHelper.getClassInternalName(this.interfaceClassLoadingClass);
        return className;
    }

    public ClassNode getClassNode() {
        return this.classNode;
    }

    public MethodNode getMethodNode() {
        return this.methodNode;
    }

    public void setMethodNode(MethodNode methodNode) {
        this.methodNode = methodNode;
        this.constructorNode = null;
    }

    public ConstructorNode getConstructorNode() {
        return this.constructorNode;
    }

    public void setConstructorNode(ConstructorNode constructorNode) {
        this.constructorNode = constructorNode;
        this.methodNode = null;
    }

    public ClassNode getThisType() {
        ClassNode thisType = this.getClassNode();
        while (ClassHelper.isGeneratedFunction(thisType)) {
            thisType = thisType.getOuterClass();
        }
        return thisType;
    }

    public ClassNode getReturnType() {
        if (this.methodNode != null) {
            return this.methodNode.getReturnType();
        }
        if (this.constructorNode != null) {
            return this.constructorNode.getReturnType();
        }
        throw new GroovyBugError("I spotted a return that is neither in a method nor in a constructor... I can not handle that");
    }

    public ClassNode getOutermostClass() {
        if (this.outermostClass == null) {
            List<ClassNode> outers = this.classNode.getOuterClasses();
            this.outermostClass = !outers.isEmpty() ? outers.get(outers.size() - 1) : this.classNode;
        }
        return this.outermostClass;
    }

    public String getInternalClassName() {
        return this.internalClassName;
    }

    public String getInternalBaseClassName() {
        return this.internalBaseClassName;
    }

    public List<String> getSuperMethodNames() {
        return this.superMethodNames;
    }

    public InterfaceHelperClassNode getInterfaceClassLoadingClass() {
        return this.interfaceClassLoadingClass;
    }

    public void setInterfaceClassLoadingClass(InterfaceHelperClassNode ihc) {
        this.interfaceClassLoadingClass = ihc;
    }

    public boolean isStaticContext() {
        if (this.compileStack != null && this.compileStack.getScope() != null) {
            return this.compileStack.getScope().isInStaticContext();
        }
        if (!this.isInGeneratedFunction()) {
            return false;
        }
        if (this.isConstructor()) {
            return false;
        }
        return this.classNode.isStaticClass() || this.isStaticMethod();
    }

    public boolean isStaticMethod() {
        return this.methodNode != null && this.methodNode.isStatic();
    }

    public boolean isNotClinit() {
        return this.methodNode == null || !this.methodNode.isStaticConstructor();
    }

    public boolean isStaticConstructor() {
        return this.methodNode != null && this.methodNode.isStaticConstructor();
    }

    public boolean isConstructor() {
        return this.constructorNode != null;
    }

    public boolean isInGeneratedFunction() {
        return this.classNode.getOuterClass() != null && ClassHelper.isGeneratedFunction(this.classNode);
    }

    public boolean isInGeneratedFunctionConstructor() {
        return this.isConstructor() && this.isInGeneratedFunction();
    }

    public boolean isInScriptBody() {
        return this.classNode.isScriptBody() || this.classNode.isScript() && this.methodNode != null && this.methodNode.getName().equals("run");
    }

    public boolean shouldOptimizeForInt() {
        return this.optimizeForInt;
    }

    public void switchToFastPath() {
        this.fastPath = true;
        this.resetLineNumber();
    }

    public void switchToSlowPath() {
        this.fastPath = false;
        this.resetLineNumber();
    }

    public boolean isFastPath() {
        return this.fastPath;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void resetLineNumber() {
        this.setLineNumber(-1);
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void visitLineNumber(int lineNumber) {
        if (lineNumber > 0 && lineNumber != this.lineNumber) {
            this.setLineNumber(lineNumber);
            MethodVisitor mv = this.getMethodVisitor();
            if (mv != null) {
                Label label = new Label();
                mv.visitLabel(label);
                mv.visitLineNumber(lineNumber, label);
            }
        }
    }

    public int getBytecodeVersion() {
        return this.bytecodeVersion;
    }

    public int getNextHelperMethodIndex() {
        return ++this.helperMethodIndex;
    }
}

