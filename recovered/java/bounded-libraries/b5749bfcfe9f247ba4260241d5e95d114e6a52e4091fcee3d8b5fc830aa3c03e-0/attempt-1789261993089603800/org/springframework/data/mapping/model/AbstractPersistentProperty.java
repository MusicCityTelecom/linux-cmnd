/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.mapping.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.util.Lazy;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public abstract class AbstractPersistentProperty<P extends PersistentProperty<P>>
implements PersistentProperty<P> {
    private static final Field CAUSE_FIELD = ReflectionUtils.findRequiredField(Throwable.class, "cause");
    private static final Class<?> ASSOCIATION_TYPE = ReflectionUtils.loadIfPresent("org.jmolecules.ddd.types.Association", AbstractPersistentProperty.class.getClassLoader());
    private final String name;
    private final TypeInformation<?> information;
    private final Class<?> rawType;
    private final Lazy<Association<P>> association;
    private final PersistentEntity<?, P> owner;
    private final Property property;
    private final Lazy<Integer> hashCode;
    private final Lazy<Boolean> usePropertyAccess;
    private final Lazy<Set<TypeInformation<?>>> entityTypeInformation;
    private final Lazy<Boolean> isAssociation;
    private final Lazy<TypeInformation<?>> associationTargetType;
    private final Method getter;
    private final Method setter;
    private final Field field;
    private final Method wither;
    private final boolean immutable;

    public AbstractPersistentProperty(Property property, PersistentEntity<?, P> owner, SimpleTypeHolder simpleTypeHolder) {
        Assert.notNull((Object)simpleTypeHolder, (String)"SimpleTypeHolder must not be null");
        Assert.notNull(owner, (String)"Owner entity must not be null");
        this.name = property.getName();
        this.information = owner.getTypeInformation().getRequiredProperty(this.getName());
        this.rawType = this.information.getType();
        this.property = property;
        this.association = Lazy.of(() -> this.isAssociation() ? this.createAssociation() : null);
        this.owner = owner;
        this.hashCode = Lazy.of(property::hashCode);
        this.usePropertyAccess = Lazy.of(() -> owner.getType().isInterface() || CAUSE_FIELD.equals(this.getField()));
        this.isAssociation = Lazy.of(() -> ASSOCIATION_TYPE != null && ASSOCIATION_TYPE.isAssignableFrom(this.rawType));
        this.associationTargetType = ASSOCIATION_TYPE == null ? Lazy.empty() : Lazy.of(() -> Optional.of(this.getTypeInformation()).map(it -> it.getSuperTypeInformation(ASSOCIATION_TYPE)).map(TypeInformation::getComponentType).orElse(null));
        this.entityTypeInformation = Lazy.of(() -> this.detectEntityTypes(simpleTypeHolder));
        this.getter = property.getGetter().orElse(null);
        this.setter = property.getSetter().orElse(null);
        this.field = property.getField().orElse(null);
        this.wither = property.getWither().orElse(null);
        this.immutable = this.setter == null && (this.field == null || Modifier.isFinal(this.field.getModifiers()));
    }

    protected abstract Association<P> createAssociation();

    @Override
    public PersistentEntity<?, P> getOwner() {
        return this.owner;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Class<?> getType() {
        return this.information.getType();
    }

    @Override
    public Class<?> getRawType() {
        return this.rawType;
    }

    @Override
    public TypeInformation<?> getTypeInformation() {
        return this.information;
    }

    @Override
    public Iterable<? extends TypeInformation<?>> getPersistentEntityTypes() {
        return this.getPersistentEntityTypeInformation();
    }

    @Override
    public Iterable<? extends TypeInformation<?>> getPersistentEntityTypeInformation() {
        if (this.isMap() || this.isCollectionLike()) {
            return this.entityTypeInformation.get();
        }
        if (!this.isEntity()) {
            return Collections.emptySet();
        }
        return this.entityTypeInformation.get();
    }

    @Override
    @Nullable
    public Method getGetter() {
        return this.getter;
    }

    @Override
    @Nullable
    public Method getSetter() {
        return this.setter;
    }

    @Override
    @Nullable
    public Method getWither() {
        return this.wither;
    }

    @Override
    @Nullable
    public Field getField() {
        return this.field;
    }

    @Override
    @Nullable
    public String getSpelExpression() {
        return null;
    }

    @Override
    public boolean isTransient() {
        return false;
    }

    @Override
    public boolean isWritable() {
        return !this.isTransient();
    }

    @Override
    public boolean isImmutable() {
        return this.immutable;
    }

    @Override
    public boolean isAssociation() {
        return this.isAssociation.get();
    }

    @Override
    @Nullable
    public Association<P> getAssociation() {
        return this.association.orElse(null);
    }

    @Override
    @Nullable
    public Class<?> getAssociationTargetType() {
        TypeInformation<?> result = this.getAssociationTargetTypeInformation();
        return result != null ? result.getType() : null;
    }

    @Override
    @Nullable
    public TypeInformation<?> getAssociationTargetTypeInformation() {
        return this.associationTargetType.getNullable();
    }

    @Override
    public boolean isCollectionLike() {
        return this.information.isCollectionLike();
    }

    @Override
    public boolean isMap() {
        return this.information.isMap();
    }

    @Override
    public boolean isArray() {
        return this.getType().isArray();
    }

    @Override
    public boolean isEntity() {
        return !this.isTransient() && !this.entityTypeInformation.get().isEmpty();
    }

    @Override
    @Nullable
    public Class<?> getComponentType() {
        return this.isMap() || this.isCollectionLike() ? this.information.getRequiredComponentType().getType() : null;
    }

    @Override
    @Nullable
    public Class<?> getMapValueType() {
        TypeInformation<?> mapValueType;
        if (this.isMap() && (mapValueType = this.information.getMapValueType()) != null) {
            return mapValueType.getType();
        }
        return null;
    }

    @Override
    public Class<?> getActualType() {
        return this.getActualTypeInformation().getType();
    }

    @Override
    public boolean usePropertyAccess() {
        return this.usePropertyAccess.get();
    }

    protected Property getProperty() {
        return this.property;
    }

    protected TypeInformation<?> getActualTypeInformation() {
        TypeInformation<?> targetType = this.associationTargetType.getNullable();
        return targetType == null ? this.information.getRequiredActualType() : targetType;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractPersistentProperty)) {
            return false;
        }
        AbstractPersistentProperty that = (AbstractPersistentProperty)obj;
        return this.property.equals(that.property);
    }

    public int hashCode() {
        return this.hashCode.get();
    }

    public String toString() {
        return this.property.toString();
    }

    private Set<TypeInformation<?>> detectEntityTypes(SimpleTypeHolder simpleTypes) {
        TypeInformation<?> typeToStartWith = this.getAssociationTargetTypeInformation();
        typeToStartWith = typeToStartWith == null ? this.information : typeToStartWith;
        Set<TypeInformation<?>> result = this.detectEntityTypes(typeToStartWith);
        return result.stream().filter(it -> !simpleTypes.isSimpleType(it.getType())).filter(it -> !it.getType().equals(ASSOCIATION_TYPE)).collect(Collectors.toSet());
    }

    private Set<TypeInformation<?>> detectEntityTypes(@Nullable TypeInformation<?> source) {
        TypeInformation<?> actualType;
        if (source == null) {
            return Collections.emptySet();
        }
        HashSet result = new HashSet();
        if (source.isMap()) {
            result.addAll(this.detectEntityTypes(source.getComponentType()));
        }
        if (source.equals(actualType = source.getActualType())) {
            result.add(source);
        } else {
            result.addAll(this.detectEntityTypes(actualType));
        }
        return result;
    }
}

