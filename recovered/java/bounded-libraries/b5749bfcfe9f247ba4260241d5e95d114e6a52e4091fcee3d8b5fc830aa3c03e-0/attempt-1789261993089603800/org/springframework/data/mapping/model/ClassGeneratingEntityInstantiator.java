/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.asm.ClassWriter
 *  org.springframework.asm.MethodVisitor
 *  org.springframework.asm.Type
 *  org.springframework.beans.BeanInstantiationException
 *  org.springframework.cglib.core.ReflectUtils
 *  org.springframework.core.NativeDetector
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 */
package org.springframework.data.mapping.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Type;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.core.NativeDetector;
import org.springframework.data.mapping.FactoryMethod;
import org.springframework.data.mapping.InstanceCreatorMetadata;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PreferredConstructor;
import org.springframework.data.mapping.model.BytecodeUtil;
import org.springframework.data.mapping.model.EntityInstantiator;
import org.springframework.data.mapping.model.MappingInstantiationException;
import org.springframework.data.mapping.model.ParameterValueProvider;
import org.springframework.data.mapping.model.ReflectionEntityInstantiator;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

class ClassGeneratingEntityInstantiator
implements EntityInstantiator {
    private static final Log LOGGER = LogFactory.getLog(ClassGeneratingEntityInstantiator.class);
    private static final Object[] EMPTY_ARGS = new Object[0];
    private final ObjectInstantiatorClassGenerator generator;
    private volatile Map<TypeInformation<?>, EntityInstantiator> entityInstantiators = new HashMap(32);
    private final boolean fallbackToReflectionOnError;

    public ClassGeneratingEntityInstantiator() {
        this(true);
    }

    ClassGeneratingEntityInstantiator(boolean fallbackToReflectionOnError) {
        this.generator = new ObjectInstantiatorClassGenerator();
        this.fallbackToReflectionOnError = fallbackToReflectionOnError;
    }

    @Override
    public <T, E extends PersistentEntity<? extends T, P>, P extends PersistentProperty<P>> T createInstance(E entity, ParameterValueProvider<P> provider) {
        EntityInstantiator instantiator = this.entityInstantiators.get(entity.getTypeInformation());
        if (instantiator == null) {
            instantiator = this.potentiallyCreateAndRegisterEntityInstantiator(entity);
        }
        return instantiator.createInstance(entity, provider);
    }

    private synchronized EntityInstantiator potentiallyCreateAndRegisterEntityInstantiator(PersistentEntity<?, ?> entity) {
        Map<TypeInformation<?>, EntityInstantiator> map = this.entityInstantiators;
        EntityInstantiator instantiator = map.get(entity.getTypeInformation());
        if (instantiator != null) {
            return instantiator;
        }
        instantiator = this.createEntityInstantiator(entity);
        map = new HashMap(map);
        map.put(entity.getTypeInformation(), instantiator);
        this.entityInstantiators = map;
        return instantiator;
    }

    private EntityInstantiator createEntityInstantiator(PersistentEntity<?, ?> entity) {
        if (this.shouldUseReflectionEntityInstantiator(entity)) {
            return ReflectionEntityInstantiator.INSTANCE;
        }
        if (Modifier.isAbstract(entity.getType().getModifiers())) {
            return MappingInstantiationExceptionEntityInstantiator.create(entity.getType());
        }
        if (this.fallbackToReflectionOnError) {
            try {
                return this.doCreateEntityInstantiator(entity);
            }
            catch (Throwable ex) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)String.format("Cannot create entity instantiator for %s; Falling back to ReflectionEntityInstantiator", entity.getName()), ex);
                }
                return ReflectionEntityInstantiator.INSTANCE;
            }
        }
        return this.doCreateEntityInstantiator(entity);
    }

    protected EntityInstantiator doCreateEntityInstantiator(PersistentEntity<?, ?> entity) {
        return new EntityInstantiatorAdapter(this.createObjectInstantiator(entity, entity.getInstanceCreatorMetadata()));
    }

    boolean shouldUseReflectionEntityInstantiator(PersistentEntity<?, ?> entity) {
        FactoryMethod factoryMethod;
        PreferredConstructor persistenceConstructor;
        if (NativeDetector.inNativeImage()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)String.format("graalvm.nativeimage - fall back to reflection for %s", entity.getName()));
            }
            return true;
        }
        Class<?> type = entity.getType();
        if (type.isInterface() || type.isArray() || Modifier.isPrivate(type.getModifiers()) || type.isMemberClass() && !Modifier.isStatic(type.getModifiers()) || ClassUtils.isCglibProxyClass(type)) {
            return true;
        }
        InstanceCreatorMetadata<?> entityCreator = entity.getInstanceCreatorMetadata();
        if (entityCreator == null) {
            return true;
        }
        if (entityCreator instanceof PreferredConstructor && Modifier.isPrivate((persistenceConstructor = (PreferredConstructor)entityCreator).getConstructor().getModifiers())) {
            return true;
        }
        if (entityCreator instanceof FactoryMethod && Modifier.isPrivate((factoryMethod = (FactoryMethod)entityCreator).getFactoryMethod().getModifiers())) {
            return true;
        }
        return !ClassUtils.isPresent((String)ObjectInstantiator.class.getName(), (ClassLoader)type.getClassLoader());
    }

    static Object[] allocateArguments(int argumentCount) {
        return argumentCount == 0 ? EMPTY_ARGS : new Object[argumentCount];
    }

    ObjectInstantiator createObjectInstantiator(PersistentEntity<?, ?> entity, @Nullable InstanceCreatorMetadata<?> constructor) {
        try {
            return (ObjectInstantiator)this.generator.generateCustomInstantiatorClass(entity, constructor).newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static <P extends PersistentProperty<P>, T> Object[] extractInvocationArguments(@Nullable InstanceCreatorMetadata<P> constructor, ParameterValueProvider<P> provider) {
        if (constructor == null || !constructor.hasParameters()) {
            return ClassGeneratingEntityInstantiator.allocateArguments(0);
        }
        Object[] params = ClassGeneratingEntityInstantiator.allocateArguments(constructor.getParameterCount());
        int index = 0;
        for (Parameter<Object, P> parameter : constructor.getParameters()) {
            params[index++] = provider.getParameterValue(parameter);
        }
        return params;
    }

    static class ObjectInstantiatorClassGenerator {
        private static final String INIT = "<init>";
        private static final String TAG = "_Instantiator_";
        private static final String JAVA_LANG_OBJECT = Type.getInternalName(Object.class);
        private static final String CREATE_METHOD_NAME = "newInstance";
        private static final String[] IMPLEMENTED_INTERFACES = new String[]{Type.getInternalName(ObjectInstantiator.class)};

        ObjectInstantiatorClassGenerator() {
        }

        public Class<?> generateCustomInstantiatorClass(PersistentEntity<?, ?> entity, @Nullable InstanceCreatorMetadata<?> constructor) {
            Class<?> type;
            ClassLoader classLoader;
            String className = this.generateClassName(entity);
            if (ClassUtils.isPresent((String)className, (ClassLoader)(classLoader = (type = entity.getType()).getClassLoader()))) {
                try {
                    return ClassUtils.forName((String)className, (ClassLoader)classLoader);
                }
                catch (Exception o_O) {
                    throw new IllegalStateException(o_O);
                }
            }
            byte[] bytecode = this.generateBytecode(className, entity, constructor);
            try {
                return ReflectUtils.defineClass((String)className, (byte[])bytecode, (ClassLoader)classLoader, (ProtectionDomain)type.getProtectionDomain(), type);
            }
            catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        private String generateClassName(PersistentEntity<?, ?> entity) {
            return entity.getType().getName() + TAG + Integer.toString(entity.hashCode(), 36);
        }

        public byte[] generateBytecode(String internalClassName, PersistentEntity<?, ?> entity, @Nullable InstanceCreatorMetadata<?> entityCreator) {
            ClassWriter cw = new ClassWriter(1);
            cw.visit(50, 33, internalClassName.replace('.', '/'), null, JAVA_LANG_OBJECT, IMPLEMENTED_INTERFACES);
            this.visitDefaultConstructor(cw);
            this.visitCreateMethod(cw, entity, entityCreator);
            cw.visitEnd();
            return cw.toByteArray();
        }

        private void visitDefaultConstructor(ClassWriter cw) {
            MethodVisitor mv = cw.visitMethod(1, INIT, "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            mv.visitMethodInsn(183, JAVA_LANG_OBJECT, INIT, "()V", false);
            mv.visitInsn(177);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        private void visitCreateMethod(ClassWriter cw, PersistentEntity<?, ?> entity, @Nullable InstanceCreatorMetadata<?> entityCreator) {
            String entityTypeResourcePath = Type.getInternalName(entity.getType());
            MethodVisitor mv = cw.visitMethod(129, CREATE_METHOD_NAME, "([" + BytecodeUtil.referenceName(Object.class) + ")" + BytecodeUtil.referenceName(Object.class), null, null);
            mv.visitCode();
            mv.visitTypeInsn(187, entityTypeResourcePath);
            mv.visitInsn(89);
            if (entityCreator instanceof PreferredConstructor) {
                ObjectInstantiatorClassGenerator.visitConstructorCreation((PreferredConstructor)entityCreator, mv, entityTypeResourcePath);
            }
            if (entityCreator instanceof FactoryMethod) {
                ObjectInstantiatorClassGenerator.visitFactoryMethodCreation((FactoryMethod)entityCreator, mv, entityTypeResourcePath);
            }
            mv.visitInsn(176);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }

        private static void visitConstructorCreation(PreferredConstructor<?, ?> constructor, MethodVisitor mv, String entityTypeResourcePath) {
            Constructor<?> ctor = constructor.getConstructor();
            Class<?>[] parameterTypes = ctor.getParameterTypes();
            List parameters = constructor.getParameters();
            ObjectInstantiatorClassGenerator.visitParameterTypes(mv, parameterTypes, parameters);
            mv.visitMethodInsn(183, entityTypeResourcePath, INIT, Type.getConstructorDescriptor(ctor), false);
        }

        private static void visitFactoryMethodCreation(FactoryMethod<?, ?> factoryMethod, MethodVisitor mv, String entityTypeResourcePath) {
            Method method = factoryMethod.getFactoryMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            List parameters = factoryMethod.getParameters();
            ObjectInstantiatorClassGenerator.visitParameterTypes(mv, parameterTypes, parameters);
            mv.visitMethodInsn(184, entityTypeResourcePath, method.getName(), Type.getMethodDescriptor((Method)method), false);
        }

        private static void visitParameterTypes(MethodVisitor mv, Class<?>[] parameterTypes, List<? extends Parameter<Object, ?>> parameters) {
            for (int i = 0; i < parameterTypes.length; ++i) {
                mv.visitVarInsn(25, 1);
                ObjectInstantiatorClassGenerator.visitArrayIndex(mv, i);
                mv.visitInsn(50);
                if (parameterTypes[i].isPrimitive()) {
                    mv.visitInsn(89);
                    String parameterName = parameters.size() > i ? parameters.get(i).getName() : null;
                    ObjectInstantiatorClassGenerator.insertAssertNotNull(mv, parameterName == null ? String.format("at index %d", i) : parameterName);
                    ObjectInstantiatorClassGenerator.insertUnboxInsns(mv, Type.getType(parameterTypes[i]).toString().charAt(0), "");
                    continue;
                }
                mv.visitTypeInsn(192, Type.getInternalName(parameterTypes[i]));
            }
        }

        private static void visitArrayIndex(MethodVisitor mv, int idx) {
            if (idx >= 0 && idx < 6) {
                mv.visitInsn(3 + idx);
                return;
            }
            mv.visitLdcInsn((Object)idx);
        }

        private static void insertAssertNotNull(MethodVisitor mv, String parameterName) {
            mv.visitLdcInsn((Object)String.format("Parameter %s must not be null", parameterName));
            mv.visitMethodInsn(184, Type.getInternalName(Assert.class), "notNull", String.format("(%s%s)V", BytecodeUtil.referenceName(JAVA_LANG_OBJECT), BytecodeUtil.referenceName(String.class)), false);
        }

        private static void insertUnboxInsns(MethodVisitor mv, char ch, String stackDescriptor) {
            switch (ch) {
                case 'Z': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Boolean.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Boolean.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Boolean.class), "booleanValue", "()Z", false);
                    break;
                }
                case 'B': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Byte.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Byte.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Byte.class), "byteValue", "()B", false);
                    break;
                }
                case 'C': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Character.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Character.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Character.class), "charValue", "()C", false);
                    break;
                }
                case 'D': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Double.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Double.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Double.class), "doubleValue", "()D", false);
                    break;
                }
                case 'F': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Float.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Float.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Float.class), "floatValue", "()F", false);
                    break;
                }
                case 'I': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Integer.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Integer.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Integer.class), "intValue", "()I", false);
                    break;
                }
                case 'J': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Long.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Long.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Long.class), "longValue", "()J", false);
                    break;
                }
                case 'S': {
                    if (!stackDescriptor.equals(BytecodeUtil.referenceName(Short.class))) {
                        mv.visitTypeInsn(192, Type.getInternalName(Short.class));
                    }
                    mv.visitMethodInsn(182, Type.getInternalName(Short.class), "shortValue", "()S", false);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unboxing should not be attempted for descriptor '" + ch + "'");
                }
            }
        }
    }

    static class MappingInstantiationExceptionEntityInstantiator
    implements EntityInstantiator {
        private final Class<?> typeToCreate;

        private MappingInstantiationExceptionEntityInstantiator(Class<?> typeToCreate) {
            this.typeToCreate = typeToCreate;
        }

        public static EntityInstantiator create(Class<?> typeToCreate) {
            return new MappingInstantiationExceptionEntityInstantiator(typeToCreate);
        }

        @Override
        public <T, E extends PersistentEntity<? extends T, P>, P extends PersistentProperty<P>> T createInstance(E entity, ParameterValueProvider<P> provider) {
            Object[] params = ClassGeneratingEntityInstantiator.extractInvocationArguments(entity.getInstanceCreatorMetadata(), provider);
            throw new MappingInstantiationException(entity, Arrays.asList(params), (Exception)new BeanInstantiationException(this.typeToCreate, "Class is abstract"));
        }
    }

    public static interface ObjectInstantiator {
        public Object newInstance(Object ... var1);
    }

    private static class EntityInstantiatorAdapter
    implements EntityInstantiator {
        private final ObjectInstantiator instantiator;

        public EntityInstantiatorAdapter(ObjectInstantiator instantiator) {
            this.instantiator = instantiator;
        }

        @Override
        public <T, E extends PersistentEntity<? extends T, P>, P extends PersistentProperty<P>> T createInstance(E entity, ParameterValueProvider<P> provider) {
            Object[] params = ClassGeneratingEntityInstantiator.extractInvocationArguments(entity.getInstanceCreatorMetadata(), provider);
            try {
                return (T)this.instantiator.newInstance(params);
            }
            catch (Exception e) {
                throw new MappingInstantiationException(entity, Arrays.asList(params), e);
            }
        }
    }
}

