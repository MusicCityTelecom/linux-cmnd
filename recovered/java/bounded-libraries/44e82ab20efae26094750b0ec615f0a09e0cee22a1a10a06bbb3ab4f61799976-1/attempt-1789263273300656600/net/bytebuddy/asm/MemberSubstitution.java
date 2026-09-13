/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package net.bytebuddy.asm;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.Removal;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.visitor.LocalVariableAwareMethodVisitor;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
@HashCodeAndEqualsPlugin.Enhance
public class MemberSubstitution
implements AsmVisitorWrapper.ForDeclaredMethods.MethodVisitorWrapper {
    private final MethodGraph.Compiler methodGraphCompiler;
    private final boolean strict;
    private final TypePoolResolver typePoolResolver;
    private final Replacement.Factory replacementFactory;

    protected MemberSubstitution(boolean strict) {
        this(MethodGraph.Compiler.DEFAULT, TypePoolResolver.OfImplicitPool.INSTANCE, strict, Replacement.NoOp.INSTANCE);
    }

    protected MemberSubstitution(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory) {
        this.methodGraphCompiler = methodGraphCompiler;
        this.typePoolResolver = typePoolResolver;
        this.strict = strict;
        this.replacementFactory = replacementFactory;
    }

    public static MemberSubstitution strict() {
        return new MemberSubstitution(true);
    }

    public static MemberSubstitution relaxed() {
        return new MemberSubstitution(false);
    }

    public WithoutSpecification element(ElementMatcher<? super ByteCodeElement> matcher) {
        return new WithoutSpecification.ForMatchedByteCodeElement(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, matcher);
    }

    public WithoutSpecification.ForMatchedField field(ElementMatcher<? super FieldDescription.InDefinedShape> matcher) {
        return new WithoutSpecification.ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, matcher);
    }

    public WithoutSpecification.ForMatchedMethod method(ElementMatcher<? super MethodDescription> matcher) {
        return new WithoutSpecification.ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, matcher);
    }

    public WithoutSpecification constructor(ElementMatcher<? super MethodDescription> matcher) {
        return this.invokable(ElementMatchers.isConstructor().and(matcher));
    }

    public WithoutSpecification invokable(ElementMatcher<? super MethodDescription> matcher) {
        return new WithoutSpecification.ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, matcher);
    }

    public MemberSubstitution with(MethodGraph.Compiler methodGraphCompiler) {
        return new MemberSubstitution(methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory);
    }

    public MemberSubstitution with(TypePoolResolver typePoolResolver) {
        return new MemberSubstitution(this.methodGraphCompiler, typePoolResolver, this.strict, this.replacementFactory);
    }

    public AsmVisitorWrapper.ForDeclaredMethods on(ElementMatcher<? super MethodDescription> matcher) {
        return new AsmVisitorWrapper.ForDeclaredMethods().invokable(matcher, this);
    }

    @Override
    public MethodVisitor wrap(TypeDescription instrumentedType, MethodDescription instrumentedMethod, MethodVisitor methodVisitor, Implementation.Context implementationContext, TypePool typePool, int writerFlags, int readerFlags) {
        typePool = this.typePoolResolver.resolve(instrumentedType, instrumentedMethod, typePool);
        return new SubstitutingMethodVisitor(methodVisitor, instrumentedType, instrumentedMethod, this.methodGraphCompiler, this.strict, this.replacementFactory.make(instrumentedType, instrumentedMethod, typePool), implementationContext, typePool, implementationContext.getClassFileVersion().isAtLeast(ClassFileVersion.JAVA_V11));
    }

    public boolean equals(@MaybeNull Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (this.strict != ((MemberSubstitution)object).strict) {
            return false;
        }
        if (!this.methodGraphCompiler.equals(((MemberSubstitution)object).methodGraphCompiler)) {
            return false;
        }
        if (!this.typePoolResolver.equals(((MemberSubstitution)object).typePoolResolver)) {
            return false;
        }
        return this.replacementFactory.equals(((MemberSubstitution)object).replacementFactory);
    }

    public int hashCode() {
        return (((this.getClass().hashCode() * 31 + this.methodGraphCompiler.hashCode()) * 31 + this.strict) * 31 + this.typePoolResolver.hashCode()) * 31 + this.replacementFactory.hashCode();
    }

    protected static class SubstitutingMethodVisitor
    extends LocalVariableAwareMethodVisitor {
        private final TypeDescription instrumentedType;
        private final MethodDescription instrumentedMethod;
        private final MethodGraph.Compiler methodGraphCompiler;
        private final boolean strict;
        private final Replacement replacement;
        private final Implementation.Context implementationContext;
        private final TypePool typePool;
        private final boolean virtualPrivateCalls;
        private int stackSizeBuffer;
        private int localVariableExtension;

        protected SubstitutingMethodVisitor(MethodVisitor methodVisitor, TypeDescription instrumentedType, MethodDescription instrumentedMethod, MethodGraph.Compiler methodGraphCompiler, boolean strict, Replacement replacement, Implementation.Context implementationContext, TypePool typePool, boolean virtualPrivateCalls) {
            super(methodVisitor, instrumentedMethod);
            this.instrumentedType = instrumentedType;
            this.instrumentedMethod = instrumentedMethod;
            this.methodGraphCompiler = methodGraphCompiler;
            this.strict = strict;
            this.replacement = replacement;
            this.implementationContext = implementationContext;
            this.typePool = typePool;
            this.virtualPrivateCalls = virtualPrivateCalls;
            this.stackSizeBuffer = 0;
            this.localVariableExtension = 0;
        }

        public void visitFieldInsn(int opcode, String owner, String internalName, String descriptor) {
            TypePool.Resolution resolution = this.typePool.describe(owner.replace('/', '.'));
            if (resolution.isResolved()) {
                FieldList candidates = (FieldList)resolution.resolve().getDeclaredFields().filter(this.strict ? ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor)) : ElementMatchers.failSafe(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor))));
                if (!candidates.isEmpty()) {
                    Replacement.Binding binding = this.replacement.bind(this.instrumentedType, this.instrumentedMethod, (FieldDescription.InDefinedShape)candidates.getOnly(), opcode == 181 || opcode == 179);
                    if (binding.isBound()) {
                        TypeDescription.Generic result;
                        AbstractList parameters;
                        switch (opcode) {
                            case 181: {
                                parameters = new TypeList.Generic.Explicit(((FieldDescription.InDefinedShape)candidates.getOnly()).getDeclaringType(), ((FieldDescription.InDefinedShape)candidates.getOnly()).getType());
                                result = TypeDescription.Generic.VOID;
                                break;
                            }
                            case 179: {
                                parameters = new TypeList.Generic.Explicit(((FieldDescription.InDefinedShape)candidates.getOnly()).getType());
                                result = TypeDescription.Generic.VOID;
                                break;
                            }
                            case 180: {
                                parameters = new TypeList.Generic.Explicit(((FieldDescription.InDefinedShape)candidates.getOnly()).getDeclaringType());
                                result = ((FieldDescription.InDefinedShape)candidates.getOnly()).getType();
                                break;
                            }
                            case 178: {
                                parameters = new TypeList.Generic.Empty();
                                result = ((FieldDescription.InDefinedShape)candidates.getOnly()).getType();
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Unexpected opcode: " + opcode);
                            }
                        }
                        this.stackSizeBuffer = Math.max(this.stackSizeBuffer, binding.make((TypeList.Generic)((Object)parameters), result, this.getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this.mv), this.implementationContext).getMaximalSize() - result.getStackSize().getSize());
                        return;
                    }
                } else if (this.strict) {
                    throw new IllegalStateException("Could not resolve " + owner.replace('/', '.') + "." + internalName + descriptor + " using " + this.typePool);
                }
            } else if (this.strict) {
                throw new IllegalStateException("Could not resolve " + owner.replace('/', '.') + " using " + this.typePool);
            }
            super.visitFieldInsn(opcode, owner, internalName, descriptor);
        }

        public void visitMethodInsn(int opcode, String owner, String internalName, String descriptor, boolean isInterface) {
            TypePool.Resolution resolution = this.typePool.describe(owner.replace('/', '.'));
            if (resolution.isResolved()) {
                MethodList candidates;
                if (opcode == 183 && internalName.equals("<init>")) {
                    candidates = (MethodList)resolution.resolve().getDeclaredMethods().filter(this.strict ? ElementMatchers.isConstructor().and(ElementMatchers.hasDescriptor(descriptor)) : ElementMatchers.failSafe(ElementMatchers.isConstructor().and(ElementMatchers.hasDescriptor(descriptor))));
                } else if (opcode == 184 || opcode == 183) {
                    candidates = (MethodList)resolution.resolve().getDeclaredMethods().filter(this.strict ? ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor)) : ElementMatchers.failSafe(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor))));
                } else if (this.virtualPrivateCalls) {
                    candidates = (MethodList)resolution.resolve().getDeclaredMethods().filter(this.strict ? ElementMatchers.isPrivate().and(ElementMatchers.not(ElementMatchers.isStatic())).and(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor))) : ElementMatchers.failSafe(ElementMatchers.isPrivate().and(ElementMatchers.not(ElementMatchers.isStatic())).and(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor)))));
                    if (candidates.isEmpty()) {
                        candidates = (MethodList)this.methodGraphCompiler.compile((TypeDefinition)resolution.resolve(), this.instrumentedType).listNodes().asMethodList().filter(this.strict ? ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor)) : ElementMatchers.failSafe(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor))));
                    }
                } else {
                    candidates = (MethodList)this.methodGraphCompiler.compile((TypeDefinition)resolution.resolve(), this.instrumentedType).listNodes().asMethodList().filter(this.strict ? ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor)) : ElementMatchers.failSafe(ElementMatchers.named(internalName).and(ElementMatchers.hasDescriptor(descriptor))));
                }
                if (!candidates.isEmpty()) {
                    Replacement.Binding binding = this.replacement.bind(this.instrumentedType, this.instrumentedMethod, resolution.resolve(), (MethodDescription)candidates.getOnly(), Replacement.InvocationType.of(opcode, (MethodDescription)candidates.getOnly()));
                    if (binding.isBound()) {
                        this.stackSizeBuffer = Math.max(this.stackSizeBuffer, binding.make(((MethodDescription)candidates.getOnly()).isStatic() || ((MethodDescription)candidates.getOnly()).isConstructor() ? ((MethodDescription)candidates.getOnly()).getParameters().asTypeList() : new TypeList.Generic.Explicit(CompoundList.of(resolution.resolve(), ((MethodDescription)candidates.getOnly()).getParameters().asTypeList())), ((MethodDescription)candidates.getOnly()).isConstructor() ? ((MethodDescription)candidates.getOnly()).getDeclaringType().asGenericType() : ((MethodDescription)candidates.getOnly()).getReturnType(), this.getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this.mv), this.implementationContext).getMaximalSize() - (((MethodDescription)candidates.getOnly()).isConstructor() ? StackSize.SINGLE : ((MethodDescription)candidates.getOnly()).getReturnType().getStackSize()).getSize());
                        if (((MethodDescription)candidates.getOnly()).isConstructor()) {
                            this.stackSizeBuffer = Math.max(this.stackSizeBuffer, new StackManipulation.Compound(Duplication.SINGLE.flipOver(TypeDescription.OBJECT), Removal.SINGLE, Removal.SINGLE, Duplication.SINGLE.flipOver(TypeDescription.OBJECT), Removal.SINGLE, Removal.SINGLE).apply(this.mv, this.implementationContext).getMaximalSize() + StackSize.SINGLE.getSize());
                        }
                        return;
                    }
                } else if (this.strict) {
                    throw new IllegalStateException("Could not resolve " + owner.replace('/', '.') + "." + internalName + descriptor + " using " + this.typePool);
                }
            } else if (this.strict) {
                throw new IllegalStateException("Could not resolve " + owner.replace('/', '.') + " using " + this.typePool);
            }
            super.visitMethodInsn(opcode, owner, internalName, descriptor, isInterface);
        }

        public void visitMaxs(int stackSize, int localVariableLength) {
            super.visitMaxs(stackSize + this.stackSizeBuffer, Math.max(this.localVariableExtension, localVariableLength));
        }

        private class LocalVariableTracingMethodVisitor
        extends MethodVisitor {
            private LocalVariableTracingMethodVisitor(MethodVisitor methodVisitor) {
                super(OpenedClassReader.ASM_API, methodVisitor);
            }

            @SuppressFBWarnings(value={"SF_SWITCH_NO_DEFAULT"}, justification="No action required on default option.")
            public void visitVarInsn(int opcode, int offset) {
                switch (opcode) {
                    case 54: 
                    case 56: 
                    case 58: {
                        SubstitutingMethodVisitor.this.localVariableExtension = Math.max(SubstitutingMethodVisitor.this.localVariableExtension, offset + 1);
                        break;
                    }
                    case 55: 
                    case 57: {
                        SubstitutingMethodVisitor.this.localVariableExtension = Math.max(SubstitutingMethodVisitor.this.localVariableExtension, offset + 2);
                    }
                }
                super.visitVarInsn(opcode, offset);
            }
        }
    }

    protected static interface Replacement {
        public Binding bind(TypeDescription var1, MethodDescription var2, FieldDescription.InDefinedShape var3, boolean var4);

        public Binding bind(TypeDescription var1, MethodDescription var2, TypeDescription var3, MethodDescription var4, InvocationType var5);

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        public static class ForFirstBinding
        implements Replacement {
            private final List<? extends Replacement> replacements;

            protected ForFirstBinding(List<? extends Replacement> replacements) {
                this.replacements = replacements;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, FieldDescription.InDefinedShape fieldDescription, boolean writeAccess) {
                for (Replacement replacement : this.replacements) {
                    Binding binding = replacement.bind(instrumentedType, instrumentedMethod, fieldDescription, writeAccess);
                    if (!binding.isBound()) continue;
                    return binding;
                }
                return Binding.Unresolved.INSTANCE;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypeDescription typeDescription, MethodDescription methodDescription, InvocationType invocationType) {
                for (Replacement replacement : this.replacements) {
                    Binding binding = replacement.bind(instrumentedType, instrumentedMethod, typeDescription, methodDescription, invocationType);
                    if (!binding.isBound()) continue;
                    return binding;
                }
                return Binding.Unresolved.INSTANCE;
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                return ((Object)this.replacements).equals(((ForFirstBinding)object).replacements);
            }

            public int hashCode() {
                return this.getClass().hashCode() * 31 + ((Object)this.replacements).hashCode();
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        public static class ForElementMatchers
        implements Replacement {
            private final ElementMatcher<? super FieldDescription.InDefinedShape> fieldMatcher;
            private final ElementMatcher<? super MethodDescription> methodMatcher;
            private final boolean matchFieldRead;
            private final boolean matchFieldWrite;
            private final boolean includeVirtualCalls;
            private final boolean includeSuperCalls;
            private final Substitution substitution;

            protected ForElementMatchers(ElementMatcher<? super FieldDescription.InDefinedShape> fieldMatcher, ElementMatcher<? super MethodDescription> methodMatcher, boolean matchFieldRead, boolean matchFieldWrite, boolean includeVirtualCalls, boolean includeSuperCalls, Substitution substitution) {
                this.fieldMatcher = fieldMatcher;
                this.methodMatcher = methodMatcher;
                this.matchFieldRead = matchFieldRead;
                this.matchFieldWrite = matchFieldWrite;
                this.includeVirtualCalls = includeVirtualCalls;
                this.includeSuperCalls = includeSuperCalls;
                this.substitution = substitution;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, FieldDescription.InDefinedShape fieldDescription, boolean writeAccess) {
                return (writeAccess ? this.matchFieldWrite : this.matchFieldRead) && this.fieldMatcher.matches(fieldDescription) ? new Binding.Resolved(fieldDescription.getDeclaringType(), fieldDescription, this.substitution) : Binding.Unresolved.INSTANCE;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypeDescription typeDescription, MethodDescription methodDescription, InvocationType invocationType) {
                return invocationType.matches(this.includeVirtualCalls, this.includeSuperCalls) && this.methodMatcher.matches(methodDescription) ? new Binding.Resolved(typeDescription, methodDescription, this.substitution) : Binding.Unresolved.INSTANCE;
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (this.matchFieldRead != ((ForElementMatchers)object).matchFieldRead) {
                    return false;
                }
                if (this.matchFieldWrite != ((ForElementMatchers)object).matchFieldWrite) {
                    return false;
                }
                if (this.includeVirtualCalls != ((ForElementMatchers)object).includeVirtualCalls) {
                    return false;
                }
                if (this.includeSuperCalls != ((ForElementMatchers)object).includeSuperCalls) {
                    return false;
                }
                if (!this.fieldMatcher.equals(((ForElementMatchers)object).fieldMatcher)) {
                    return false;
                }
                if (!this.methodMatcher.equals(((ForElementMatchers)object).methodMatcher)) {
                    return false;
                }
                return this.substitution.equals(((ForElementMatchers)object).substitution);
            }

            public int hashCode() {
                return ((((((this.getClass().hashCode() * 31 + this.fieldMatcher.hashCode()) * 31 + this.methodMatcher.hashCode()) * 31 + this.matchFieldRead) * 31 + this.matchFieldWrite) * 31 + this.includeVirtualCalls) * 31 + this.includeSuperCalls) * 31 + this.substitution.hashCode();
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            @HashCodeAndEqualsPlugin.Enhance
            protected static class Factory
            implements net.bytebuddy.asm.MemberSubstitution$Replacement$Factory {
                private final ElementMatcher<? super FieldDescription.InDefinedShape> fieldMatcher;
                private final ElementMatcher<? super MethodDescription> methodMatcher;
                private final boolean matchFieldRead;
                private final boolean matchFieldWrite;
                private final boolean includeVirtualCalls;
                private final boolean includeSuperCalls;
                private final Substitution.Factory substitutionFactory;

                protected Factory(ElementMatcher<? super FieldDescription.InDefinedShape> fieldMatcher, ElementMatcher<? super MethodDescription> methodMatcher, boolean matchFieldRead, boolean matchFieldWrite, boolean includeVirtualCalls, boolean includeSuperCalls, Substitution.Factory substitutionFactory) {
                    this.fieldMatcher = fieldMatcher;
                    this.methodMatcher = methodMatcher;
                    this.matchFieldRead = matchFieldRead;
                    this.matchFieldWrite = matchFieldWrite;
                    this.includeVirtualCalls = includeVirtualCalls;
                    this.includeSuperCalls = includeSuperCalls;
                    this.substitutionFactory = substitutionFactory;
                }

                protected static net.bytebuddy.asm.MemberSubstitution$Replacement$Factory of(ElementMatcher<? super ByteCodeElement> matcher, Substitution.Factory factory) {
                    return new Factory(matcher, matcher, true, true, true, true, factory);
                }

                protected static net.bytebuddy.asm.MemberSubstitution$Replacement$Factory ofField(ElementMatcher<? super FieldDescription.InDefinedShape> matcher, boolean matchFieldRead, boolean matchFieldWrite, Substitution.Factory factory) {
                    return new Factory(matcher, ElementMatchers.none(), matchFieldRead, matchFieldWrite, false, false, factory);
                }

                protected static net.bytebuddy.asm.MemberSubstitution$Replacement$Factory ofMethod(ElementMatcher<? super MethodDescription> matcher, boolean includeVirtualCalls, boolean includeSuperCalls, Substitution.Factory factory) {
                    return new Factory(ElementMatchers.none(), matcher, false, false, includeVirtualCalls, includeSuperCalls, factory);
                }

                @Override
                public Replacement make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForElementMatchers(this.fieldMatcher, this.methodMatcher, this.matchFieldRead, this.matchFieldWrite, this.includeVirtualCalls, this.includeSuperCalls, this.substitutionFactory.make(instrumentedType, instrumentedMethod, typePool));
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    if (this.matchFieldRead != ((Factory)object).matchFieldRead) {
                        return false;
                    }
                    if (this.matchFieldWrite != ((Factory)object).matchFieldWrite) {
                        return false;
                    }
                    if (this.includeVirtualCalls != ((Factory)object).includeVirtualCalls) {
                        return false;
                    }
                    if (this.includeSuperCalls != ((Factory)object).includeSuperCalls) {
                        return false;
                    }
                    if (!this.fieldMatcher.equals(((Factory)object).fieldMatcher)) {
                        return false;
                    }
                    if (!this.methodMatcher.equals(((Factory)object).methodMatcher)) {
                        return false;
                    }
                    return this.substitutionFactory.equals(((Factory)object).substitutionFactory);
                }

                public int hashCode() {
                    return ((((((this.getClass().hashCode() * 31 + this.fieldMatcher.hashCode()) * 31 + this.methodMatcher.hashCode()) * 31 + this.matchFieldRead) * 31 + this.matchFieldWrite) * 31 + this.includeVirtualCalls) * 31 + this.includeSuperCalls) * 31 + this.substitutionFactory.hashCode();
                }
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum NoOp implements Replacement,
        Factory
        {
            INSTANCE;


            @Override
            public Replacement make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                return this;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, FieldDescription.InDefinedShape fieldDescription, boolean writeAccess) {
                return Binding.Unresolved.INSTANCE;
            }

            @Override
            public Binding bind(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypeDescription typeDescription, MethodDescription methodDescription, InvocationType invocationType) {
                return Binding.Unresolved.INSTANCE;
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum InvocationType {
            VIRTUAL,
            SUPER,
            OTHER;


            protected static InvocationType of(int opcode, MethodDescription methodDescription) {
                switch (opcode) {
                    case 182: 
                    case 185: {
                        return VIRTUAL;
                    }
                    case 183: {
                        return methodDescription.isVirtual() ? SUPER : OTHER;
                    }
                }
                return OTHER;
            }

            protected boolean matches(boolean includeVirtualCalls, boolean includeSuperCalls) {
                switch (this) {
                    case VIRTUAL: {
                        return includeVirtualCalls;
                    }
                    case SUPER: {
                        return includeSuperCalls;
                    }
                }
                return true;
            }
        }

        public static interface Factory {
            public Replacement make(TypeDescription var1, MethodDescription var2, TypePool var3);

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            @HashCodeAndEqualsPlugin.Enhance
            public static class Compound
            implements Factory {
                private final List<Factory> factories = new ArrayList<Factory>();

                protected Compound(Factory ... factory) {
                    this(Arrays.asList(factory));
                }

                protected Compound(List<? extends Factory> factories) {
                    for (Factory factory : factories) {
                        if (factory instanceof Compound) {
                            this.factories.addAll(((Compound)factory).factories);
                            continue;
                        }
                        if (factory instanceof NoOp) continue;
                        this.factories.add(factory);
                    }
                }

                @Override
                public Replacement make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    ArrayList<Replacement> replacements = new ArrayList<Replacement>();
                    for (Factory factory : this.factories) {
                        replacements.add(factory.make(instrumentedType, instrumentedMethod, typePool));
                    }
                    return new ForFirstBinding(replacements);
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    return ((Object)this.factories).equals(((Compound)object).factories);
                }

                public int hashCode() {
                    return this.getClass().hashCode() * 31 + ((Object)this.factories).hashCode();
                }
            }
        }

        public static interface Binding {
            public boolean isBound();

            public StackManipulation make(TypeList.Generic var1, TypeDescription.Generic var2, int var3);

            @HashCodeAndEqualsPlugin.Enhance
            public static class Resolved
            implements Binding {
                private final TypeDescription targetType;
                private final ByteCodeElement target;
                private final Substitution substitution;

                protected Resolved(TypeDescription targetType, ByteCodeElement target, Substitution substitution) {
                    this.targetType = targetType;
                    this.target = target;
                    this.substitution = substitution;
                }

                public boolean isBound() {
                    return true;
                }

                public StackManipulation make(TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                    return this.substitution.resolve(this.targetType, this.target, parameters, result, freeOffset);
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    if (!this.targetType.equals(((Resolved)object).targetType)) {
                        return false;
                    }
                    if (!this.target.equals(((Resolved)object).target)) {
                        return false;
                    }
                    return this.substitution.equals(((Resolved)object).substitution);
                }

                public int hashCode() {
                    return ((this.getClass().hashCode() * 31 + this.targetType.hashCode()) * 31 + this.target.hashCode()) * 31 + this.substitution.hashCode();
                }
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            public static enum Unresolved implements Binding
            {
                INSTANCE;


                @Override
                public boolean isBound() {
                    return false;
                }

                @Override
                public StackManipulation make(TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                    throw new IllegalStateException("Cannot resolve unresolved binding");
                }
            }
        }
    }

    public static interface Substitution {
        public StackManipulation resolve(TypeDescription var1, ByteCodeElement var2, TypeList.Generic var3, TypeDescription.Generic var4, int var5);

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        public static class Chain
        implements Substitution {
            private final Assigner assigner;
            private final Assigner.Typing typing;
            private final List<Step> steps;

            protected Chain(Assigner assigner, Assigner.Typing typing, List<Step> steps) {
                this.assigner = assigner;
                this.typing = typing;
                this.steps = steps;
            }

            public static Factory withDefaultAssigner() {
                return Chain.with(Assigner.DEFAULT, Assigner.Typing.STATIC);
            }

            public static Factory with(Assigner assigner, Assigner.Typing typing) {
                return new Factory(assigner, typing, Collections.<Step.Factory>emptyList());
            }

            @Override
            public StackManipulation resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                ArrayList<StackManipulation> stackManipulations = new ArrayList<StackManipulation>(1 + parameters.size() + this.steps.size() * 2 + (result.represents(Void.TYPE) ? 0 : 2));
                HashMap<Integer, Integer> offsets = new HashMap<Integer, Integer>();
                for (int index = parameters.size() - 1; index >= 0; --index) {
                    stackManipulations.add(MethodVariableAccess.of((TypeDefinition)parameters.get(index)).storeAt(freeOffset));
                    offsets.put(index, freeOffset);
                    freeOffset += ((TypeDescription.Generic)parameters.get(index)).getStackSize().getSize();
                }
                stackManipulations.add(DefaultValue.of(result));
                TypeDescription.Generic current = result;
                for (Step step : this.steps) {
                    Step.Resolution resolution = step.resolve(targetType, target, parameters, current, offsets, freeOffset);
                    stackManipulations.add(resolution.getStackManipulation());
                    current = resolution.getResultType();
                }
                stackManipulations.add(this.assigner.assign(current, result, this.typing));
                return new StackManipulation.Compound(stackManipulations);
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (!this.typing.equals((Object)((Chain)object).typing)) {
                    return false;
                }
                if (!this.assigner.equals(((Chain)object).assigner)) {
                    return false;
                }
                return ((Object)this.steps).equals(((Chain)object).steps);
            }

            public int hashCode() {
                return ((this.getClass().hashCode() * 31 + this.assigner.hashCode()) * 31 + this.typing.hashCode()) * 31 + ((Object)this.steps).hashCode();
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            public static class Factory
            implements net.bytebuddy.asm.MemberSubstitution$Substitution$Factory {
                private final Assigner assigner;
                private final Assigner.Typing typing;
                private final List<Step.Factory> steps;

                protected Factory(Assigner assigner, Assigner.Typing typing, List<Step.Factory> steps) {
                    this.assigner = assigner;
                    this.typing = typing;
                    this.steps = steps;
                }

                @Override
                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    if (this.steps.isEmpty()) {
                        return Stubbing.INSTANCE;
                    }
                    ArrayList<Step> steps = new ArrayList<Step>(this.steps.size());
                    for (Step.Factory step : this.steps) {
                        steps.add(step.make(this.assigner, this.typing, instrumentedType, instrumentedMethod));
                    }
                    return new Chain(this.assigner, this.typing, steps);
                }

                public Factory executing(Step.Factory ... step) {
                    return this.executing(Arrays.asList(step));
                }

                public Factory executing(List<? extends Step.Factory> steps) {
                    return new Factory(this.assigner, this.typing, CompoundList.of(this.steps, steps));
                }
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            public static interface Step {
                public Resolution resolve(TypeDescription var1, ByteCodeElement var2, TypeList.Generic var3, TypeDescription.Generic var4, Map<Integer, Integer> var5, int var6);

                /*
                 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
                 */
                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple
                implements Step,
                Resolution,
                Factory {
                    private final StackManipulation stackManipulation;
                    private final TypeDescription.Generic resultType;

                    public Simple(StackManipulation stackManipulation, TypeDescription.Generic resultType) {
                        this.stackManipulation = stackManipulation;
                        this.resultType = resultType;
                    }

                    @Override
                    public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription instrumentedType, MethodDescription instrumentedMethod) {
                        return this;
                    }

                    @Override
                    public Resolution resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic current, Map<Integer, Integer> offsets, int freeOffset) {
                        return targetType.represents(Void.TYPE) ? this : new Simple(new StackManipulation.Compound(Removal.of(targetType), this.stackManipulation), this.resultType);
                    }

                    @Override
                    public StackManipulation getStackManipulation() {
                        return this.stackManipulation;
                    }

                    @Override
                    public TypeDescription.Generic getResultType() {
                        return this.resultType;
                    }

                    public boolean equals(@MaybeNull Object object) {
                        if (this == object) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }
                        if (this.getClass() != object.getClass()) {
                            return false;
                        }
                        if (!this.stackManipulation.equals(((Simple)object).stackManipulation)) {
                            return false;
                        }
                        return this.resultType.equals(((Simple)object).resultType);
                    }

                    public int hashCode() {
                        return (this.getClass().hashCode() * 31 + this.stackManipulation.hashCode()) * 31 + this.resultType.hashCode();
                    }
                }

                public static interface Factory {
                    public Step make(Assigner var1, Assigner.Typing var2, TypeDescription var3, MethodDescription var4);
                }

                public static interface Resolution {
                    public StackManipulation getStackManipulation();

                    public TypeDescription.Generic getResultType();
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMethodInvocation
        implements Substitution {
            private static final int THIS_REFERENCE = 0;
            private final TypeDescription instrumentedType;
            private final MethodResolver methodResolver;

            public ForMethodInvocation(TypeDescription instrumentedType, MethodResolver methodResolver) {
                this.instrumentedType = instrumentedType;
                this.methodResolver = methodResolver;
            }

            public StackManipulation resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                TypeList.Generic mapped;
                MethodDescription methodDescription = this.methodResolver.resolve(targetType, target, parameters, result);
                if (!methodDescription.isAccessibleTo(this.instrumentedType)) {
                    throw new IllegalStateException(this.instrumentedType + " cannot access " + methodDescription);
                }
                TypeList.Generic generic = mapped = methodDescription.isStatic() ? methodDescription.getParameters().asTypeList() : new TypeList.Generic.Explicit(CompoundList.of(methodDescription.getDeclaringType(), methodDescription.getParameters().asTypeList()));
                if (!methodDescription.getReturnType().asErasure().isAssignableTo(result.asErasure())) {
                    throw new IllegalStateException("Cannot assign return value of " + methodDescription + " to " + result);
                }
                if (mapped.size() != parameters.size()) {
                    throw new IllegalStateException("Cannot invoke " + methodDescription + " on " + parameters.size() + " parameters");
                }
                for (int index = 0; index < mapped.size(); ++index) {
                    if (((TypeDescription.Generic)parameters.get(index)).asErasure().isAssignableTo(((TypeDescription.Generic)mapped.get(index)).asErasure())) continue;
                    throw new IllegalStateException("Cannot invoke " + methodDescription + " on parameter " + index + " of type " + parameters.get(index));
                }
                return methodDescription.isVirtual() ? MethodInvocation.invoke(methodDescription).virtual(((TypeDescription.Generic)mapped.get(0)).asErasure()) : MethodInvocation.invoke(methodDescription);
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (!this.instrumentedType.equals(((ForMethodInvocation)object).instrumentedType)) {
                    return false;
                }
                return this.methodResolver.equals(((ForMethodInvocation)object).methodResolver);
            }

            public int hashCode() {
                return (this.getClass().hashCode() * 31 + this.instrumentedType.hashCode()) * 31 + this.methodResolver.hashCode();
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            @HashCodeAndEqualsPlugin.Enhance
            public static class OfMatchedMethod
            implements Factory {
                private final ElementMatcher<? super MethodDescription> matcher;
                private final MethodGraph.Compiler methodGraphCompiler;

                public OfMatchedMethod(ElementMatcher<? super MethodDescription> matcher, MethodGraph.Compiler methodGraphCompiler) {
                    this.matcher = matcher;
                    this.methodGraphCompiler = methodGraphCompiler;
                }

                @Override
                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForMethodInvocation(instrumentedType, new MethodResolver.Matching(instrumentedType, this.methodGraphCompiler, this.matcher));
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    if (!this.matcher.equals(((OfMatchedMethod)object).matcher)) {
                        return false;
                    }
                    return this.methodGraphCompiler.equals(((OfMatchedMethod)object).methodGraphCompiler);
                }

                public int hashCode() {
                    return (this.getClass().hashCode() * 31 + this.matcher.hashCode()) * 31 + this.methodGraphCompiler.hashCode();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfGivenMethod
            implements Factory {
                private final MethodDescription methodDescription;

                public OfGivenMethod(MethodDescription methodDescription) {
                    this.methodDescription = methodDescription;
                }

                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForMethodInvocation(instrumentedType, new MethodResolver.Simple(this.methodDescription));
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    return this.methodDescription.equals(((OfGivenMethod)object).methodDescription);
                }

                public int hashCode() {
                    return this.getClass().hashCode() * 31 + this.methodDescription.hashCode();
                }
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            static enum OfInstrumentedMethod implements Factory
            {
                INSTANCE;


                @Override
                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForMethodInvocation(instrumentedType, new MethodResolver.Simple(instrumentedMethod));
                }
            }

            public static interface MethodResolver {
                public MethodDescription resolve(TypeDescription var1, ByteCodeElement var2, TypeList.Generic var3, TypeDescription.Generic var4);

                /*
                 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
                 */
                @HashCodeAndEqualsPlugin.Enhance
                public static class Matching
                implements MethodResolver {
                    private final TypeDescription instrumentedType;
                    private final MethodGraph.Compiler methodGraphCompiler;
                    private final ElementMatcher<? super MethodDescription> matcher;

                    public Matching(TypeDescription instrumentedType, MethodGraph.Compiler methodGraphCompiler, ElementMatcher<? super MethodDescription> matcher) {
                        this.instrumentedType = instrumentedType;
                        this.methodGraphCompiler = methodGraphCompiler;
                        this.matcher = matcher;
                    }

                    @Override
                    public MethodDescription resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result) {
                        if (parameters.isEmpty()) {
                            throw new IllegalStateException("Cannot substitute parameterless instruction with " + target);
                        }
                        if (((TypeDescription.Generic)parameters.get(0)).isPrimitive() || ((TypeDescription.Generic)parameters.get(0)).isArray()) {
                            throw new IllegalStateException("Cannot invoke method on primitive or array type for " + target);
                        }
                        TypeDefinition typeDefinition = ((TypeDescription.Generic)parameters.get(0)).accept(new TypeDescription.Generic.Visitor.Substitutor.ForReplacement(this.instrumentedType));
                        List candidates = CompoundList.of(this.methodGraphCompiler.compile(typeDefinition, this.instrumentedType).listNodes().asMethodList().filter(this.matcher), typeDefinition.getDeclaredMethods().filter(ElementMatchers.isPrivate().and(ElementMatchers.isVisibleTo(this.instrumentedType)).and(this.matcher)));
                        if (candidates.size() == 1) {
                            return (MethodDescription)candidates.get(0);
                        }
                        throw new IllegalStateException("Not exactly one method that matches " + this.matcher + ": " + candidates);
                    }

                    public boolean equals(@MaybeNull Object object) {
                        if (this == object) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }
                        if (this.getClass() != object.getClass()) {
                            return false;
                        }
                        if (!this.instrumentedType.equals(((Matching)object).instrumentedType)) {
                            return false;
                        }
                        if (!this.methodGraphCompiler.equals(((Matching)object).methodGraphCompiler)) {
                            return false;
                        }
                        return this.matcher.equals(((Matching)object).matcher);
                    }

                    public int hashCode() {
                        return ((this.getClass().hashCode() * 31 + this.instrumentedType.hashCode()) * 31 + this.methodGraphCompiler.hashCode()) * 31 + this.matcher.hashCode();
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple
                implements MethodResolver {
                    private final MethodDescription methodDescription;

                    public Simple(MethodDescription methodDescription) {
                        this.methodDescription = methodDescription;
                    }

                    public MethodDescription resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result) {
                        return this.methodDescription;
                    }

                    public boolean equals(@MaybeNull Object object) {
                        if (this == object) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }
                        if (this.getClass() != object.getClass()) {
                            return false;
                        }
                        return this.methodDescription.equals(((Simple)object).methodDescription);
                    }

                    public int hashCode() {
                        return this.getClass().hashCode() * 31 + this.methodDescription.hashCode();
                    }
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForFieldAccess
        implements Substitution {
            private final TypeDescription instrumentedType;
            private final FieldResolver fieldResolver;

            public ForFieldAccess(TypeDescription instrumentedType, FieldResolver fieldResolver) {
                this.instrumentedType = instrumentedType;
                this.fieldResolver = fieldResolver;
            }

            @SuppressFBWarnings(value={"NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"}, justification="Assuming declaring type for type member.")
            public StackManipulation resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                FieldDescription fieldDescription = this.fieldResolver.resolve(targetType, target, parameters, result);
                if (!fieldDescription.isAccessibleTo(this.instrumentedType)) {
                    throw new IllegalStateException(this.instrumentedType + " cannot access " + fieldDescription);
                }
                if (result.represents(Void.TYPE)) {
                    if (parameters.size() != (fieldDescription.isStatic() ? 1 : 2)) {
                        throw new IllegalStateException("Cannot set " + fieldDescription + " with " + parameters);
                    }
                    if (!fieldDescription.isStatic() && !((TypeDescription.Generic)parameters.get(0)).asErasure().isAssignableTo(fieldDescription.getDeclaringType().asErasure())) {
                        throw new IllegalStateException("Cannot set " + fieldDescription + " on " + parameters.get(0));
                    }
                    if (!((TypeDescription.Generic)parameters.get(fieldDescription.isStatic() ? 0 : 1)).asErasure().isAssignableTo(fieldDescription.getType().asErasure())) {
                        throw new IllegalStateException("Cannot set " + fieldDescription + " to " + parameters.get(fieldDescription.isStatic() ? 0 : 1));
                    }
                    return FieldAccess.forField(fieldDescription).write();
                }
                if (parameters.size() != (fieldDescription.isStatic() ? 0 : 1)) {
                    throw new IllegalStateException("Cannot set " + fieldDescription + " with " + parameters);
                }
                if (!fieldDescription.isStatic() && !((TypeDescription.Generic)parameters.get(0)).asErasure().isAssignableTo(fieldDescription.getDeclaringType().asErasure())) {
                    throw new IllegalStateException("Cannot get " + fieldDescription + " on " + parameters.get(0));
                }
                if (!fieldDescription.getType().asErasure().isAssignableTo(result.asErasure())) {
                    throw new IllegalStateException("Cannot get " + fieldDescription + " as " + result);
                }
                return FieldAccess.forField(fieldDescription).read();
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (!this.instrumentedType.equals(((ForFieldAccess)object).instrumentedType)) {
                    return false;
                }
                return this.fieldResolver.equals(((ForFieldAccess)object).fieldResolver);
            }

            public int hashCode() {
                return (this.getClass().hashCode() * 31 + this.instrumentedType.hashCode()) * 31 + this.fieldResolver.hashCode();
            }

            /*
             * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
             */
            @HashCodeAndEqualsPlugin.Enhance
            public static class OfMatchedField
            implements Factory {
                private final ElementMatcher<? super FieldDescription> matcher;

                public OfMatchedField(ElementMatcher<? super FieldDescription> matcher) {
                    this.matcher = matcher;
                }

                @Override
                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForFieldAccess(instrumentedType, new FieldResolver.ForElementMatcher(instrumentedType, this.matcher));
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    return this.matcher.equals(((OfMatchedField)object).matcher);
                }

                public int hashCode() {
                    return this.getClass().hashCode() * 31 + this.matcher.hashCode();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfGivenField
            implements Factory {
                private final FieldDescription fieldDescription;

                public OfGivenField(FieldDescription fieldDescription) {
                    this.fieldDescription = fieldDescription;
                }

                public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                    return new ForFieldAccess(instrumentedType, new FieldResolver.Simple(this.fieldDescription));
                }

                public boolean equals(@MaybeNull Object object) {
                    if (this == object) {
                        return true;
                    }
                    if (object == null) {
                        return false;
                    }
                    if (this.getClass() != object.getClass()) {
                        return false;
                    }
                    return this.fieldDescription.equals(((OfGivenField)object).fieldDescription);
                }

                public int hashCode() {
                    return this.getClass().hashCode() * 31 + this.fieldDescription.hashCode();
                }
            }

            public static interface FieldResolver {
                public FieldDescription resolve(TypeDescription var1, ByteCodeElement var2, TypeList.Generic var3, TypeDescription.Generic var4);

                /*
                 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
                 */
                @HashCodeAndEqualsPlugin.Enhance
                public static class ForElementMatcher
                implements FieldResolver {
                    private final TypeDescription instrumentedType;
                    private final ElementMatcher<? super FieldDescription> matcher;

                    protected ForElementMatcher(TypeDescription instrumentedType, ElementMatcher<? super FieldDescription> matcher) {
                        this.instrumentedType = instrumentedType;
                        this.matcher = matcher;
                    }

                    @Override
                    public FieldDescription resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result) {
                        if (parameters.isEmpty()) {
                            throw new IllegalStateException("Cannot substitute parameterless instruction with " + target);
                        }
                        if (((TypeDescription.Generic)parameters.get(0)).isPrimitive() || ((TypeDescription.Generic)parameters.get(0)).isArray()) {
                            throw new IllegalStateException("Cannot access field on primitive or array type for " + target);
                        }
                        TypeDefinition current = ((TypeDescription.Generic)parameters.get(0)).accept(new TypeDescription.Generic.Visitor.Substitutor.ForReplacement(this.instrumentedType));
                        do {
                            FieldList fields;
                            if ((fields = (FieldList)current.getDeclaredFields().filter(ElementMatchers.not(ElementMatchers.isStatic()).and(ElementMatchers.isVisibleTo(this.instrumentedType)).and(this.matcher))).size() == 1) {
                                return (FieldDescription)fields.getOnly();
                            }
                            if (fields.size() <= 1) continue;
                            throw new IllegalStateException("Ambiguous field location of " + fields);
                        } while ((current = current.getSuperClass()) != null);
                        throw new IllegalStateException("Cannot locate field matching " + this.matcher + " on " + targetType);
                    }

                    public boolean equals(@MaybeNull Object object) {
                        if (this == object) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }
                        if (this.getClass() != object.getClass()) {
                            return false;
                        }
                        if (!this.instrumentedType.equals(((ForElementMatcher)object).instrumentedType)) {
                            return false;
                        }
                        return this.matcher.equals(((ForElementMatcher)object).matcher);
                    }

                    public int hashCode() {
                        return (this.getClass().hashCode() * 31 + this.instrumentedType.hashCode()) * 31 + this.matcher.hashCode();
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple
                implements FieldResolver {
                    private final FieldDescription fieldDescription;

                    public Simple(FieldDescription fieldDescription) {
                        this.fieldDescription = fieldDescription;
                    }

                    public FieldDescription resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result) {
                        return this.fieldDescription;
                    }

                    public boolean equals(@MaybeNull Object object) {
                        if (this == object) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }
                        if (this.getClass() != object.getClass()) {
                            return false;
                        }
                        return this.fieldDescription.equals(((Simple)object).fieldDescription);
                    }

                    public int hashCode() {
                        return this.getClass().hashCode() * 31 + this.fieldDescription.hashCode();
                    }
                }
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum Stubbing implements Substitution,
        Factory
        {
            INSTANCE;


            @Override
            public Substitution make(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                return this;
            }

            @Override
            public StackManipulation resolve(TypeDescription targetType, ByteCodeElement target, TypeList.Generic parameters, TypeDescription.Generic result, int freeOffset) {
                ArrayList<StackManipulation> stackManipulations = new ArrayList<StackManipulation>(parameters.size());
                for (int index = parameters.size() - 1; index >= 0; --index) {
                    stackManipulations.add(Removal.of((TypeDefinition)parameters.get(index)));
                }
                return new StackManipulation.Compound(CompoundList.of(stackManipulations, DefaultValue.of(result.asErasure())));
            }
        }

        public static interface Factory {
            public Substitution make(TypeDescription var1, MethodDescription var2, TypePool var3);
        }
    }

    public static interface TypePoolResolver {
        public TypePool resolve(TypeDescription var1, MethodDescription var2, TypePool var3);

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForClassFileLocator
        implements TypePoolResolver {
            private final ClassFileLocator classFileLocator;
            private final TypePool.Default.ReaderMode readerMode;

            public ForClassFileLocator(ClassFileLocator classFileLocator) {
                this(classFileLocator, TypePool.Default.ReaderMode.FAST);
            }

            public ForClassFileLocator(ClassFileLocator classFileLocator, TypePool.Default.ReaderMode readerMode) {
                this.classFileLocator = classFileLocator;
                this.readerMode = readerMode;
            }

            public static TypePoolResolver of(@MaybeNull ClassLoader classLoader) {
                return new ForClassFileLocator(ClassFileLocator.ForClassLoader.of(classLoader));
            }

            public TypePool resolve(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                return new TypePool.Default(new TypePool.CacheProvider.Simple(), this.classFileLocator, this.readerMode, typePool);
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (!this.readerMode.equals((Object)((ForClassFileLocator)object).readerMode)) {
                    return false;
                }
                return this.classFileLocator.equals(((ForClassFileLocator)object).classFileLocator);
            }

            public int hashCode() {
                return (this.getClass().hashCode() * 31 + this.classFileLocator.hashCode()) * 31 + this.readerMode.hashCode();
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForExplicitPool
        implements TypePoolResolver {
            private final TypePool typePool;

            public ForExplicitPool(TypePool typePool) {
                this.typePool = typePool;
            }

            public TypePool resolve(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                return this.typePool;
            }

            public boolean equals(@MaybeNull Object object) {
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                return this.typePool.equals(((ForExplicitPool)object).typePool);
            }

            public int hashCode() {
                return this.getClass().hashCode() * 31 + this.typePool.hashCode();
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        public static enum OfImplicitPool implements TypePoolResolver
        {
            INSTANCE;


            @Override
            public TypePool resolve(TypeDescription instrumentedType, MethodDescription instrumentedMethod, TypePool typePool) {
                return typePool;
            }
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    @HashCodeAndEqualsPlugin.Enhance
    public static abstract class WithoutSpecification {
        protected final MethodGraph.Compiler methodGraphCompiler;
        protected final TypePoolResolver typePoolResolver;
        protected final boolean strict;
        protected final Replacement.Factory replacementFactory;

        protected WithoutSpecification(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory) {
            this.methodGraphCompiler = methodGraphCompiler;
            this.typePoolResolver = typePoolResolver;
            this.strict = strict;
            this.replacementFactory = replacementFactory;
        }

        public MemberSubstitution stub() {
            return this.replaceWith(Substitution.Stubbing.INSTANCE);
        }

        public MemberSubstitution replaceWith(Field field) {
            return this.replaceWith(new FieldDescription.ForLoadedField(field));
        }

        public MemberSubstitution replaceWith(FieldDescription fieldDescription) {
            return this.replaceWith(new Substitution.ForFieldAccess.OfGivenField(fieldDescription));
        }

        public MemberSubstitution replaceWithField(ElementMatcher<? super FieldDescription> matcher) {
            return this.replaceWith(new Substitution.ForFieldAccess.OfMatchedField(matcher));
        }

        public MemberSubstitution replaceWith(Method method) {
            return this.replaceWith(new MethodDescription.ForLoadedMethod(method));
        }

        public MemberSubstitution replaceWith(MethodDescription methodDescription) {
            if (!methodDescription.isMethod()) {
                throw new IllegalArgumentException("Cannot use " + methodDescription + " as a replacement");
            }
            return this.replaceWith(new Substitution.ForMethodInvocation.OfGivenMethod(methodDescription));
        }

        public MemberSubstitution replaceWithMethod(ElementMatcher<? super MethodDescription> matcher) {
            return this.replaceWithMethod(matcher, this.methodGraphCompiler);
        }

        public MemberSubstitution replaceWithMethod(ElementMatcher<? super MethodDescription> matcher, MethodGraph.Compiler methodGraphCompiler) {
            return this.replaceWith(new Substitution.ForMethodInvocation.OfMatchedMethod(matcher, methodGraphCompiler));
        }

        public MemberSubstitution replaceWithInstrumentedMethod() {
            return this.replaceWith(Substitution.ForMethodInvocation.OfInstrumentedMethod.INSTANCE);
        }

        public MemberSubstitution replaceWithChain(Substitution.Chain.Step.Factory ... step) {
            return this.replaceWithChain(Arrays.asList(step));
        }

        public MemberSubstitution replaceWithChain(List<? extends Substitution.Chain.Step.Factory> steps) {
            return this.replaceWith(Substitution.Chain.withDefaultAssigner().executing(steps));
        }

        public abstract MemberSubstitution replaceWith(Substitution.Factory var1);

        public boolean equals(@MaybeNull Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            if (this.strict != ((WithoutSpecification)object).strict) {
                return false;
            }
            if (!this.methodGraphCompiler.equals(((WithoutSpecification)object).methodGraphCompiler)) {
                return false;
            }
            if (!this.typePoolResolver.equals(((WithoutSpecification)object).typePoolResolver)) {
                return false;
            }
            return this.replacementFactory.equals(((WithoutSpecification)object).replacementFactory);
        }

        public int hashCode() {
            return (((this.getClass().hashCode() * 31 + this.methodGraphCompiler.hashCode()) * 31 + this.typePoolResolver.hashCode()) * 31 + this.strict) * 31 + this.replacementFactory.hashCode();
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMatchedMethod
        extends WithoutSpecification {
            private final ElementMatcher<? super MethodDescription> matcher;
            private final boolean includeVirtualCalls;
            private final boolean includeSuperCalls;

            protected ForMatchedMethod(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory, ElementMatcher<? super MethodDescription> matcher) {
                this(methodGraphCompiler, typePoolResolver, strict, replacementFactory, matcher, true, true);
            }

            protected ForMatchedMethod(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory, ElementMatcher<? super MethodDescription> matcher, boolean includeVirtualCalls, boolean includeSuperCalls) {
                super(methodGraphCompiler, typePoolResolver, strict, replacementFactory);
                this.matcher = matcher;
                this.includeVirtualCalls = includeVirtualCalls;
                this.includeSuperCalls = includeSuperCalls;
            }

            public WithoutSpecification onVirtualCall() {
                return new ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, ElementMatchers.isVirtual().and(this.matcher), true, false);
            }

            public WithoutSpecification onSuperCall() {
                return new ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, ElementMatchers.isVirtual().and(this.matcher), false, true);
            }

            @Override
            public MemberSubstitution replaceWith(Substitution.Factory substitutionFactory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.ofMethod(this.matcher, this.includeVirtualCalls, this.includeSuperCalls, substitutionFactory)));
            }

            @Override
            public boolean equals(@MaybeNull Object object) {
                if (!super.equals(object)) {
                    return false;
                }
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (this.includeVirtualCalls != ((ForMatchedMethod)object).includeVirtualCalls) {
                    return false;
                }
                if (this.includeSuperCalls != ((ForMatchedMethod)object).includeSuperCalls) {
                    return false;
                }
                return this.matcher.equals(((ForMatchedMethod)object).matcher);
            }

            @Override
            public int hashCode() {
                return ((super.hashCode() * 31 + this.matcher.hashCode()) * 31 + this.includeVirtualCalls) * 31 + this.includeSuperCalls;
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMatchedField
        extends WithoutSpecification {
            private final ElementMatcher<? super FieldDescription.InDefinedShape> matcher;
            private final boolean matchRead;
            private final boolean matchWrite;

            protected ForMatchedField(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory, ElementMatcher<? super FieldDescription.InDefinedShape> matcher) {
                this(methodGraphCompiler, typePoolResolver, strict, replacementFactory, matcher, true, true);
            }

            protected ForMatchedField(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory, ElementMatcher<? super FieldDescription.InDefinedShape> matcher, boolean matchRead, boolean matchWrite) {
                super(methodGraphCompiler, typePoolResolver, strict, replacementFactory);
                this.matcher = matcher;
                this.matchRead = matchRead;
                this.matchWrite = matchWrite;
            }

            public WithoutSpecification onRead() {
                return new ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, this.matcher, true, false);
            }

            public WithoutSpecification onWrite() {
                return new ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.replacementFactory, this.matcher, false, true);
            }

            @Override
            public MemberSubstitution replaceWith(Substitution.Factory substitutionFactory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.ofField(this.matcher, this.matchRead, this.matchWrite, substitutionFactory)));
            }

            @Override
            public boolean equals(@MaybeNull Object object) {
                if (!super.equals(object)) {
                    return false;
                }
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                if (this.matchRead != ((ForMatchedField)object).matchRead) {
                    return false;
                }
                if (this.matchWrite != ((ForMatchedField)object).matchWrite) {
                    return false;
                }
                return this.matcher.equals(((ForMatchedField)object).matcher);
            }

            @Override
            public int hashCode() {
                return ((super.hashCode() * 31 + this.matcher.hashCode()) * 31 + this.matchRead) * 31 + this.matchWrite;
            }
        }

        /*
         * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
         */
        @HashCodeAndEqualsPlugin.Enhance
        protected static class ForMatchedByteCodeElement
        extends WithoutSpecification {
            private final ElementMatcher<? super ByteCodeElement> matcher;

            protected ForMatchedByteCodeElement(MethodGraph.Compiler methodGraphCompiler, TypePoolResolver typePoolResolver, boolean strict, Replacement.Factory replacementFactory, ElementMatcher<? super ByteCodeElement> matcher) {
                super(methodGraphCompiler, typePoolResolver, strict, replacementFactory);
                this.matcher = matcher;
            }

            @Override
            public MemberSubstitution replaceWith(Substitution.Factory substitutionFactory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.of(this.matcher, substitutionFactory)));
            }

            @Override
            public boolean equals(@MaybeNull Object object) {
                if (!super.equals(object)) {
                    return false;
                }
                if (this == object) {
                    return true;
                }
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                return this.matcher.equals(((ForMatchedByteCodeElement)object).matcher);
            }

            @Override
            public int hashCode() {
                return super.hashCode() * 31 + this.matcher.hashCode();
            }
        }
    }
}

