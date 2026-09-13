/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.convert.converter.Converter
 *  org.springframework.lang.NonNull
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mapping.InstanceCreatorMetadata;
import org.springframework.data.mapping.Parameter;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PersistentPropertyAccessor;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mapping.model.EntityInstantiator;
import org.springframework.data.mapping.model.EntityInstantiators;
import org.springframework.data.mapping.model.ParameterValueProvider;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class DtoInstantiatingConverter
implements Converter<Object, Object> {
    private final Class<?> targetType;
    private final MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> context;
    private final EntityInstantiator instantiator;

    public DtoInstantiatingConverter(Class<?> dtoType, MappingContext<? extends PersistentEntity<?, ?>, ? extends PersistentProperty<?>> context, EntityInstantiators instantiator) {
        Assert.notNull(dtoType, (String)"DTO type must not be null");
        Assert.notNull(context, (String)"MappingContext must not be null");
        Assert.notNull((Object)instantiator, (String)"EntityInstantiators must not be null");
        this.targetType = dtoType;
        this.context = context;
        this.instantiator = instantiator.getInstantiatorFor(context.getRequiredPersistentEntity((PersistentProperty<?>)((Object)dtoType)));
    }

    @NonNull
    public Object convert(Object source) {
        if (this.targetType.isInterface()) {
            return source;
        }
        final PersistentEntity<?, ?> sourceEntity = this.context.getRequiredPersistentEntity((PersistentProperty<?>)((Object)source.getClass()));
        final PersistentPropertyAccessor<Object> sourceAccessor = sourceEntity.getPropertyAccessor(source);
        PersistentEntity<?, ?> targetEntity = this.context.getRequiredPersistentEntity((PersistentProperty<?>)((Object)this.targetType));
        Object dto = this.instantiator.createInstance(targetEntity, new ParameterValueProvider(){

            @Nullable
            public Object getParameterValue(Parameter parameter) {
                String name = parameter.getName();
                if (name == null) {
                    throw new IllegalArgumentException(String.format("Parameter %s does not have a name", parameter));
                }
                return sourceAccessor.getProperty((PersistentProperty<?>)sourceEntity.getRequiredPersistentProperty(name));
            }
        });
        PersistentPropertyAccessor targetAccessor = targetEntity.getPropertyAccessor(dto);
        InstanceCreatorMetadata<?> constructor = targetEntity.getInstanceCreatorMetadata();
        targetEntity.doWithProperties(property -> {
            if (constructor != null && constructor.isCreatorParameter(property)) {
                return;
            }
            targetAccessor.setProperty(property, sourceAccessor.getProperty((PersistentProperty<?>)sourceEntity.getRequiredPersistentProperty(property.getName())));
        });
        return dto;
    }
}

