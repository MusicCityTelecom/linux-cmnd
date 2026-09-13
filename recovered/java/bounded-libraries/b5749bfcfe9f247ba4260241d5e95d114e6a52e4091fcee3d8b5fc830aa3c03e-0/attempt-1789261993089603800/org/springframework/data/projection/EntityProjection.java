/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.data.projection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.projection.ProjectionInformation;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.Streamable;
import org.springframework.data.util.TypeInformation;
import org.springframework.lang.Nullable;

public class EntityProjection<M, D>
implements Streamable<PropertyProjection<?, ?>> {
    private final TypeInformation<M> mappedType;
    private final TypeInformation<D> domainType;
    private final List<PropertyProjection<?, ?>> properties;
    private final boolean projection;
    private final ProjectionType projectionType;

    EntityProjection(TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, boolean projection, ProjectionType projectionType) {
        this.mappedType = mappedType;
        this.domainType = domainType;
        this.properties = new ArrayList(properties);
        this.projection = projection;
        this.projectionType = projectionType;
    }

    public static <M, D> EntityProjection<M, D> projecting(TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, ProjectionType projectionType) {
        return new EntityProjection<M, D>(mappedType, domainType, properties, true, projectionType);
    }

    public static <M, D> EntityProjection<M, D> nonProjecting(TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties) {
        return new EntityProjection<M, D>(mappedType, domainType, properties, false, ProjectionType.CLOSED);
    }

    public static <T> EntityProjection<T, T> nonProjecting(Class<T> type) {
        ClassTypeInformation<T> typeInformation = ClassTypeInformation.from(type);
        return new EntityProjection<T, T>(typeInformation, typeInformation, Collections.emptyList(), false, ProjectionType.CLOSED);
    }

    public void forEachRecursive(Consumer<? super PropertyProjection<?, ?>> action) {
        for (PropertyProjection<?, ?> descriptor : this.properties) {
            if (descriptor instanceof ContainerPropertyProjection) {
                action.accept(descriptor);
                descriptor.forEachRecursive(action);
                continue;
            }
            if (descriptor.getProperties().isEmpty()) {
                action.accept(descriptor);
                continue;
            }
            descriptor.forEachRecursive(action);
        }
    }

    @Override
    public Iterator<PropertyProjection<?, ?>> iterator() {
        return this.properties.iterator();
    }

    public TypeInformation<M> getMappedType() {
        return this.mappedType;
    }

    public TypeInformation<?> getActualMappedType() {
        return this.mappedType.getRequiredActualType();
    }

    public TypeInformation<D> getDomainType() {
        return this.domainType;
    }

    public TypeInformation<?> getActualDomainType() {
        return this.domainType.getRequiredActualType();
    }

    public boolean isProjection() {
        return this.projection;
    }

    public boolean isClosedProjection() {
        return this.isProjection() && (ProjectionType.CLOSED.equals((Object)this.projectionType) || ProjectionType.DTO.equals((Object)this.projectionType));
    }

    List<PropertyProjection<?, ?>> getProperties() {
        return this.properties;
    }

    @Nullable
    public EntityProjection<?, ?> findProperty(String name) {
        for (PropertyProjection<?, ?> descriptor : this.properties) {
            if (!((PropertyProjection)descriptor).propertyPath.getLeafProperty().getSegment().equals(name)) continue;
            return descriptor;
        }
        return null;
    }

    public String toString() {
        if (this.isProjection()) {
            return String.format("Projection(%s AS %s): %s", this.getActualDomainType().getType().getName(), this.getActualMappedType().getType().getName(), this.properties);
        }
        return String.format("Domain(%s): %s", this.getActualDomainType().getType().getName(), this.properties);
    }

    public static enum ProjectionType {
        DTO,
        OPEN,
        CLOSED;


        public static ProjectionType from(ProjectionInformation information) {
            if (!information.getType().isInterface()) {
                return DTO;
            }
            return information.isClosed() ? CLOSED : OPEN;
        }
    }

    public static class ContainerPropertyProjection<M, D>
    extends PropertyProjection<M, D> {
        ContainerPropertyProjection(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, boolean projecting, ProjectionType projectionType) {
            super(propertyPath, mappedType, domainType, properties, projecting, projectionType);
        }

        public static <M, D> ContainerPropertyProjection<M, D> projecting(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, ProjectionType projectionType) {
            return new ContainerPropertyProjection<M, D>(propertyPath, mappedType, domainType, properties, true, projectionType);
        }

        public static <M, D> ContainerPropertyProjection<M, D> nonProjecting(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType) {
            return new ContainerPropertyProjection<M, D>(propertyPath, mappedType, domainType, Collections.emptyList(), false, ProjectionType.OPEN);
        }
    }

    public static class PropertyProjection<M, D>
    extends EntityProjection<M, D> {
        private final PropertyPath propertyPath;

        PropertyProjection(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, boolean projecting, ProjectionType projectionType) {
            super(mappedType, domainType, properties, projecting, projectionType);
            this.propertyPath = propertyPath;
        }

        public static <M, D> PropertyProjection<M, D> projecting(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType, List<PropertyProjection<?, ?>> properties, ProjectionType projectionType) {
            return new PropertyProjection<M, D>(propertyPath, mappedType, domainType, properties, true, projectionType);
        }

        public static <M, D> PropertyProjection<M, D> nonProjecting(PropertyPath propertyPath, TypeInformation<M> mappedType, TypeInformation<D> domainType) {
            return new PropertyProjection<M, D>(propertyPath, mappedType, domainType, Collections.emptyList(), false, ProjectionType.OPEN);
        }

        public PropertyPath getPropertyPath() {
            return this.propertyPath;
        }

        @Override
        public String toString() {
            return String.format("%s AS %s", this.propertyPath.toDotPath(), this.getActualMappedType().getType().getName());
        }
    }
}

