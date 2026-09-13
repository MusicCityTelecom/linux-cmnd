/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 */
package org.springframework.data.projection;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.projection.EntityProjection;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.ProjectionInformation;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class EntityProjectionIntrospector {
    private final ProjectionFactory projectionFactory;
    private final ProjectionPredicate projectionPredicate;
    private final MappingContext<?, ?> mappingContext;

    private EntityProjectionIntrospector(ProjectionFactory projectionFactory, ProjectionPredicate projectionPredicate, MappingContext<?, ?> mappingContext) {
        this.projectionFactory = projectionFactory;
        this.projectionPredicate = projectionPredicate;
        this.mappingContext = mappingContext;
    }

    public static EntityProjectionIntrospector create(ProjectionFactory projectionFactory, ProjectionPredicate projectionPredicate, MappingContext<?, ?> mappingContext) {
        Assert.notNull((Object)projectionFactory, (String)"ProjectionFactory must not be null");
        Assert.notNull((Object)projectionPredicate, (String)"ProjectionPredicate must not be null");
        Assert.notNull(mappingContext, (String)"MappingContext must not be null");
        return new EntityProjectionIntrospector(projectionFactory, projectionPredicate, mappingContext);
    }

    public <M, D> EntityProjection<M, D> introspect(Class<M> mappedType, Class<D> domainType) {
        ClassTypeInformation<M> returnedTypeInformation = ClassTypeInformation.from(mappedType);
        ClassTypeInformation<D> domainTypeInformation = ClassTypeInformation.from(domainType);
        boolean isProjection = this.projectionPredicate.test(mappedType, domainType);
        if (!isProjection) {
            return EntityProjection.nonProjecting(returnedTypeInformation, domainTypeInformation, Collections.emptyList());
        }
        ProjectionInformation projectionInformation = this.projectionFactory.getProjectionInformation(mappedType);
        if (!projectionInformation.isClosed()) {
            return EntityProjection.projecting(returnedTypeInformation, domainTypeInformation, Collections.emptyList(), EntityProjection.ProjectionType.OPEN);
        }
        Object persistentEntity = this.mappingContext.getRequiredPersistentEntity(domainType);
        List<EntityProjection.PropertyProjection<?, ?>> propertyDescriptors = this.getProperties(null, projectionInformation, (TypeInformation<?>)returnedTypeInformation, (PersistentEntity<?, ?>)persistentEntity, null);
        return EntityProjection.projecting(returnedTypeInformation, domainTypeInformation, propertyDescriptors, EntityProjection.ProjectionType.CLOSED);
    }

    private List<EntityProjection.PropertyProjection<?, ?>> getProperties(@Nullable PropertyPath propertyPath, ProjectionInformation projectionInformation, TypeInformation<?> projectionTypeInformation, PersistentEntity<?, ?> persistentEntity, @Nullable CycleGuard cycleGuard) {
        ArrayList propertyDescriptors = new ArrayList();
        for (PropertyDescriptor inputProperty : projectionInformation.getInputProperties()) {
            TypeInformation<?> unwrappedDomainType;
            Object persistentProperty = persistentEntity.getPersistentProperty(inputProperty.getName());
            if (persistentProperty == null) continue;
            CycleGuard cycleGuardToUse = cycleGuard != null ? cycleGuard : new CycleGuard();
            TypeInformation<?> property = projectionTypeInformation.getRequiredProperty(inputProperty.getName());
            TypeInformation<?> actualType = property.getRequiredActualType();
            boolean container = EntityProjectionIntrospector.isContainer(actualType);
            PropertyPath nestedPropertyPath = propertyPath == null ? PropertyPath.from(persistentProperty.getName(), persistentEntity.getTypeInformation()) : propertyPath.nested(persistentProperty.getName());
            TypeInformation<?> unwrappedReturnedType = EntityProjectionIntrospector.unwrapContainerType(actualType);
            if (this.isProjection(unwrappedReturnedType, unwrappedDomainType = EntityProjectionIntrospector.unwrapContainerType(persistentProperty.getTypeInformation().getRequiredActualType()))) {
                List<Object> nestedPropertyDescriptors = cycleGuardToUse.isCycleFree((PersistentProperty<?>)persistentProperty) ? this.getProjectedProperties(container ? null : nestedPropertyPath, unwrappedReturnedType, unwrappedDomainType, cycleGuardToUse) : Collections.emptyList();
                if (container) {
                    propertyDescriptors.add(EntityProjection.ContainerPropertyProjection.projecting(nestedPropertyPath, property, persistentProperty.getTypeInformation(), nestedPropertyDescriptors, EntityProjection.ProjectionType.from(projectionInformation)));
                    continue;
                }
                propertyDescriptors.add(EntityProjection.PropertyProjection.projecting(nestedPropertyPath, property, persistentProperty.getTypeInformation(), nestedPropertyDescriptors, EntityProjection.ProjectionType.from(projectionInformation)));
                continue;
            }
            if (container) {
                propertyDescriptors.add(EntityProjection.ContainerPropertyProjection.nonProjecting(nestedPropertyPath, property, persistentProperty.getTypeInformation()));
                continue;
            }
            propertyDescriptors.add(EntityProjection.PropertyProjection.nonProjecting(nestedPropertyPath, property, persistentProperty.getTypeInformation()));
        }
        return propertyDescriptors;
    }

    private static TypeInformation<?> unwrapContainerType(TypeInformation<?> type) {
        TypeInformation<?> unwrapped = type;
        while (EntityProjectionIntrospector.isContainer(unwrapped)) {
            unwrapped = unwrapped.getRequiredActualType();
        }
        return unwrapped;
    }

    private static boolean isContainer(TypeInformation<?> actualType) {
        return actualType.isCollectionLike() || actualType.isMap();
    }

    private boolean isProjection(TypeInformation<?> returnedType, TypeInformation<?> domainType) {
        return this.projectionPredicate.test(returnedType.getRequiredActualType().getType(), domainType.getRequiredActualType().getType());
    }

    private List<EntityProjection.PropertyProjection<?, ?>> getProjectedProperties(@Nullable PropertyPath propertyPath, TypeInformation<?> returnedType, TypeInformation<?> domainType, CycleGuard cycleGuard) {
        ProjectionInformation projectionInformation = this.projectionFactory.getProjectionInformation(returnedType.getType());
        Object persistentEntity = this.mappingContext.getRequiredPersistentEntity(domainType);
        return projectionInformation.isClosed() ? this.getProperties(propertyPath, projectionInformation, returnedType, (PersistentEntity<?, ?>)persistentEntity, cycleGuard) : Collections.emptyList();
    }

    static class CycleGuard {
        Set<PersistentProperty<?>> seen = new LinkedHashSet();

        CycleGuard() {
        }

        public boolean isCycleFree(PersistentProperty<?> property) {
            return this.seen.add(property);
        }
    }

    public static interface ProjectionPredicate {
        public boolean test(Class<?> var1, Class<?> var2);

        default public ProjectionPredicate and(ProjectionPredicate other) {
            return (target, underlyingType) -> this.test(target, underlyingType) && other.test(target, underlyingType);
        }

        default public ProjectionPredicate negate() {
            return (target, underlyingType) -> !this.test(target, underlyingType);
        }

        public static ProjectionPredicate typeHierarchy() {
            ProjectionPredicate predicate = (target, underlyingType) -> target.isAssignableFrom(underlyingType) || underlyingType.isAssignableFrom(target);
            return predicate.negate();
        }
    }
}

