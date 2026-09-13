/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.PropertyEditorRegistry
 *  org.springframework.beans.SimpleTypeConverter
 *  org.springframework.beans.propertyeditors.CustomBooleanEditor
 *  org.springframework.beans.propertyeditors.CustomNumberEditor
 *  org.springframework.beans.propertyeditors.FileEditor
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.convert.ConversionException
 *  org.springframework.core.convert.ConversionFailedException
 *  org.springframework.core.convert.ConversionService
 *  org.springframework.core.convert.ConverterNotFoundException
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.ConditionalGenericConverter
 *  org.springframework.core.convert.converter.ConverterRegistry
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.core.convert.support.GenericConversionService
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.context.properties.bind;

import java.beans.PropertyEditor;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.util.CollectionUtils;

final class BindConverter {
    private static BindConverter sharedInstance;
    private final List<ConversionService> delegates;

    private BindConverter(List<ConversionService> conversionServices, Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
        ArrayList<TypeConverterConversionService> delegates = new ArrayList<TypeConverterConversionService>();
        delegates.add(new TypeConverterConversionService(propertyEditorInitializer));
        boolean hasApplication = false;
        if (!CollectionUtils.isEmpty(conversionServices)) {
            for (ConversionService conversionService : conversionServices) {
                delegates.add((TypeConverterConversionService)conversionService);
                hasApplication = hasApplication || conversionService instanceof ApplicationConversionService;
            }
        }
        if (!hasApplication) {
            delegates.add((TypeConverterConversionService)ApplicationConversionService.getSharedInstance());
        }
        this.delegates = Collections.unmodifiableList(delegates);
    }

    boolean canConvert(Object source, ResolvableType targetType, Annotation ... targetAnnotations) {
        return this.canConvert(TypeDescriptor.forObject((Object)source), new ResolvableTypeDescriptor(targetType, targetAnnotations));
    }

    private boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        for (ConversionService service : this.delegates) {
            if (!service.canConvert(sourceType, targetType)) continue;
            return true;
        }
        return false;
    }

    <T> T convert(Object source, Bindable<T> target) {
        return this.convert(source, target.getType(), target.getAnnotations());
    }

    <T> T convert(Object source, ResolvableType targetType, Annotation ... targetAnnotations) {
        if (source == null) {
            return null;
        }
        return (T)this.convert(source, TypeDescriptor.forObject((Object)source), new ResolvableTypeDescriptor(targetType, targetAnnotations));
    }

    private Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Object failure = null;
        for (ConversionService delegate : this.delegates) {
            try {
                if (!delegate.canConvert(sourceType, targetType)) continue;
                return delegate.convert(source, sourceType, targetType);
            }
            catch (ConversionException ex) {
                if (failure != null || !(ex instanceof ConversionFailedException)) continue;
                failure = ex;
            }
        }
        throw failure != null ? failure : new ConverterNotFoundException(sourceType, targetType);
    }

    static BindConverter get(List<ConversionService> conversionServices, Consumer<PropertyEditorRegistry> propertyEditorInitializer) {
        boolean sharedApplicationConversionService;
        boolean bl = sharedApplicationConversionService = conversionServices == null || conversionServices.size() == 1 && conversionServices.get(0) == ApplicationConversionService.getSharedInstance();
        if (propertyEditorInitializer == null && sharedApplicationConversionService) {
            return BindConverter.getSharedInstance();
        }
        return new BindConverter(conversionServices, propertyEditorInitializer);
    }

    private static BindConverter getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new BindConverter(null, null);
        }
        return sharedInstance;
    }

    private static class TypeConverterConverter
    implements ConditionalGenericConverter {
        private static final Set<Class<?>> EXCLUDED_EDITORS;
        private final Consumer<PropertyEditorRegistry> initializer;
        private final SimpleTypeConverter matchesOnlyTypeConverter;

        TypeConverterConverter(Consumer<PropertyEditorRegistry> initializer) {
            this.initializer = initializer;
            this.matchesOnlyTypeConverter = this.createTypeConverter();
        }

        public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
            return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, Object.class));
        }

        public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
            Class type = targetType.getType();
            if (type == null || type == Object.class || Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
                return false;
            }
            PropertyEditor editor = this.matchesOnlyTypeConverter.getDefaultEditor(type);
            if (editor == null) {
                editor = this.matchesOnlyTypeConverter.findCustomEditor(type, null);
            }
            if (editor == null && String.class != type) {
                editor = BeanUtils.findEditorByConvention((Class)type);
            }
            return editor != null && !EXCLUDED_EDITORS.contains(editor.getClass());
        }

        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            return this.createTypeConverter().convertIfNecessary(source, targetType.getType());
        }

        private SimpleTypeConverter createTypeConverter() {
            SimpleTypeConverter typeConverter = new SimpleTypeConverter();
            if (this.initializer != null) {
                this.initializer.accept((PropertyEditorRegistry)typeConverter);
            }
            return typeConverter;
        }

        static {
            HashSet<Class<FileEditor>> excluded = new HashSet<Class<FileEditor>>();
            excluded.add(CustomNumberEditor.class);
            excluded.add(CustomBooleanEditor.class);
            excluded.add(FileEditor.class);
            EXCLUDED_EDITORS = Collections.unmodifiableSet(excluded);
        }
    }

    private static class TypeConverterConversionService
    extends GenericConversionService {
        TypeConverterConversionService(Consumer<PropertyEditorRegistry> initializer) {
            this.addConverter((GenericConverter)new TypeConverterConverter(initializer));
            ApplicationConversionService.addDelimitedStringConverters((ConverterRegistry)this);
        }

        public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
            if (targetType.isArray() && targetType.getElementTypeDescriptor().isPrimitive()) {
                return false;
            }
            return super.canConvert(sourceType, targetType);
        }
    }

    private static class ResolvableTypeDescriptor
    extends TypeDescriptor {
        ResolvableTypeDescriptor(ResolvableType resolvableType, Annotation[] annotations) {
            super(resolvableType, null, annotations);
        }
    }
}

