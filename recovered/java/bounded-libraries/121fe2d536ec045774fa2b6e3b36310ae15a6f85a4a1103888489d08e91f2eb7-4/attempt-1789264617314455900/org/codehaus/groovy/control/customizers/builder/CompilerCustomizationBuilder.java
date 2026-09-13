/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers.builder;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.MetaClass;
import groovy.util.FactoryBuilderSupport;
import java.lang.invoke.MethodHandles;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.builder.ASTTransformationCustomizerFactory;
import org.codehaus.groovy.control.customizers.builder.CustomizersFactory;
import org.codehaus.groovy.control.customizers.builder.ImportCustomizerFactory;
import org.codehaus.groovy.control.customizers.builder.InlinedASTCustomizerFactory;
import org.codehaus.groovy.control.customizers.builder.PostCompletionFactory;
import org.codehaus.groovy.control.customizers.builder.SecureASTCustomizerFactory;
import org.codehaus.groovy.control.customizers.builder.SourceAwareCustomizerFactory;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class CompilerCustomizationBuilder
extends FactoryBuilderSupport {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;

    public CompilerCustomizationBuilder() {
        this.registerFactory("customizers", new CustomizersFactory());
        this.registerFactory("ast", new ASTTransformationCustomizerFactory());
        this.registerFactory("imports", new ImportCustomizerFactory());
        this.registerFactory("inline", new InlinedASTCustomizerFactory());
        this.registerFactory("secureAst", new SecureASTCustomizerFactory());
        this.registerFactory("source", new SourceAwareCustomizerFactory());
    }

    public static CompilerConfiguration withConfig(CompilerConfiguration config, @DelegatesTo(type="org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder") Closure spec) {
        DefaultGroovyMethods.invokeMethod(config, "addCompilationCustomizers", new CompilerCustomizationBuilder().invokeMethod("customizers", spec));
        return config;
    }

    @Override
    protected Object postNodeCompletion(Object parent, Object node) {
        Object value = super.postNodeCompletion(parent, node);
        Object factory = this.getContextAttribute(CURRENT_FACTORY);
        if (factory instanceof PostCompletionFactory) {
            Object object;
            value = object = ((PostCompletionFactory)factory).postCompleteNode(this, parent, value);
            this.setParent(parent, value);
        }
        return value;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CompilerCustomizationBuilder.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }
}

