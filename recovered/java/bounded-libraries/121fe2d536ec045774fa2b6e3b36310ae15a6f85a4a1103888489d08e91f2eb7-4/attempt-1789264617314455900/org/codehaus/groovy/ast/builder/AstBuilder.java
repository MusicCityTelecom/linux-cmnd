/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.builder;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GString;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.builder.AstSpecificationCompiler;
import org.codehaus.groovy.ast.builder.AstStringCompiler;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class AstBuilder
implements GroovyObject {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    @Generated
    public AstBuilder() {
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public List<ASTNode> buildFromCode(CompilePhase ignoredPhase, boolean ignoredStatementsOnly, Closure ignoredBlock) {
        throw (Throwable)new IllegalStateException("AstBuilder.build(CompilePhase, boolean, Closure):List<ASTNode> should never be called at runtime.\nAre you sure you are using it correctly?");
    }

    public List<ASTNode> buildFromString(CompilePhase phase, boolean statementsOnly, String source) {
        String string = source;
        if (!(string == null ? false : DefaultTypeTransformation.booleanUnbox(string)) || ScriptBytecodeAdapter.compareEqual("", source.trim())) {
            throw (Throwable)new IllegalArgumentException("A source must be specified");
        }
        return new AstStringCompiler().compile(source, phase, statementsOnly);
    }

    private List<ASTNode> buildFromBlock(CompilePhase phase, boolean statementsOnly, String source) {
        String string = source;
        if (!(string == null ? false : DefaultTypeTransformation.booleanUnbox(string)) || ScriptBytecodeAdapter.compareEqual("", source.trim())) {
            throw (Throwable)new IllegalArgumentException("A source must be specified");
        }
        GString labelledSource = new GStringImpl(new Object[]{System.currentTimeMillis()}, new String[]{"__synthesized__label__", "__:"}).plus(source);
        List<ASTNode> result = new AstStringCompiler().compile(ShortTypeHandling.castToString(labelledSource), phase, statementsOnly);
        public final class _buildFromBlock_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _buildFromBlock_closure1(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public ASTNode doCall(Object node) {
                if (node instanceof BlockStatement) {
                    return (ASTNode)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.getAt(((BlockStatement)ScriptBytecodeAdapter.castToType(node, BlockStatement.class)).getStatements(), 0), ASTNode.class);
                }
                return (ASTNode)ScriptBytecodeAdapter.castToType(node, ASTNode.class);
            }

            @Generated
            public Object call(Object args) {
                return this.doCall(args);
            }

            @Override
            @Generated
            public Object call() {
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _buildFromBlock_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        return DefaultGroovyMethods.collect(result, new _buildFromBlock_closure1(this, this));
    }

    public List<ASTNode> buildFromSpec(@DelegatesTo(value=AstSpecificationCompiler.class) Closure specification) {
        if (specification == null) {
            throw (Throwable)new IllegalArgumentException("Null: specification");
        }
        AstSpecificationCompiler properties = new AstSpecificationCompiler(specification);
        return properties.getExpression();
    }

    @Generated
    public List<ASTNode> buildFromCode(CompilePhase ignoredPhase, Closure ignoredBlock) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        return this.buildFromCode(ignoredPhase, true, ignoredBlock);
    }

    @Generated
    public List<ASTNode> buildFromCode(Closure ignoredBlock) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        return this.buildFromCode((CompilePhase)ShortTypeHandling.castToEnum(callSiteArray[0].callGetProperty(CompilePhase.class), CompilePhase.class), true, ignoredBlock);
    }

    @Generated
    public List<ASTNode> buildFromString(CompilePhase phase, String source) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return this.buildFromString(phase, true, source);
        }
        return this.buildFromString(phase, true, source);
    }

    @Generated
    public List<ASTNode> buildFromString(String source) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return this.buildFromString((CompilePhase)ShortTypeHandling.castToEnum(callSiteArray[1].callGetProperty(CompilePhase.class), CompilePhase.class), true, source);
        }
        return this.buildFromString((CompilePhase)ShortTypeHandling.castToEnum(callSiteArray[2].callGetProperty(CompilePhase.class), CompilePhase.class), true, source);
    }

    @Generated
    private List<ASTNode> buildFromBlock(CompilePhase phase, String source) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return this.buildFromBlock(phase, true, source);
        }
        return this.buildFromBlock(phase, true, source);
    }

    @Generated
    private List<ASTNode> buildFromBlock(String source) {
        CallSite[] callSiteArray = AstBuilder.$getCallSiteArray();
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return this.buildFromBlock((CompilePhase)ShortTypeHandling.castToEnum(callSiteArray[3].callGetProperty(CompilePhase.class), CompilePhase.class), true, source);
        }
        return this.buildFromBlock((CompilePhase)ShortTypeHandling.castToEnum(callSiteArray[4].callGetProperty(CompilePhase.class), CompilePhase.class), true, source);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != AstBuilder.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Override
    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Override
    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "CLASS_GENERATION";
        stringArray[1] = "CLASS_GENERATION";
        stringArray[2] = "CLASS_GENERATION";
        stringArray[3] = "CLASS_GENERATION";
        stringArray[4] = "CLASS_GENERATION";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[5];
        AstBuilder.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(AstBuilder.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = AstBuilder.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

