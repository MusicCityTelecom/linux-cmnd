/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.util.Assert
 */
package org.springframework.data.repository.core.support;

import java.util.function.Function;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.core.support.AbstractRepositoryMetadata;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;

public class AnnotationRepositoryMetadata
extends AbstractRepositoryMetadata {
    private static final String NO_ANNOTATION_FOUND = String.format("Interface %%s must be annotated with @%s", RepositoryDefinition.class.getName());
    private final TypeInformation<?> idType;
    private final TypeInformation<?> domainType;

    public AnnotationRepositoryMetadata(Class<?> repositoryInterface) {
        super(repositoryInterface);
        Assert.isTrue((AnnotationUtils.findAnnotation(repositoryInterface, RepositoryDefinition.class) != null ? 1 : 0) != 0, () -> String.format(NO_ANNOTATION_FOUND, repositoryInterface.getName()));
        this.idType = AnnotationRepositoryMetadata.resolveType(repositoryInterface, RepositoryDefinition::idClass);
        this.domainType = AnnotationRepositoryMetadata.resolveType(repositoryInterface, RepositoryDefinition::domainClass);
    }

    @Override
    public TypeInformation<?> getIdTypeInformation() {
        return this.idType;
    }

    @Override
    public TypeInformation<?> getDomainTypeInformation() {
        return this.domainType;
    }

    private static TypeInformation<?> resolveType(Class<?> repositoryInterface, Function<RepositoryDefinition, Class<?>> extractor) {
        RepositoryDefinition annotation = (RepositoryDefinition)AnnotationUtils.findAnnotation(repositoryInterface, RepositoryDefinition.class);
        if (annotation == null || extractor.apply(annotation) == null) {
            throw new IllegalArgumentException(String.format("Could not resolve domain type of %s", repositoryInterface));
        }
        return ClassTypeInformation.from(extractor.apply(annotation));
    }
}

