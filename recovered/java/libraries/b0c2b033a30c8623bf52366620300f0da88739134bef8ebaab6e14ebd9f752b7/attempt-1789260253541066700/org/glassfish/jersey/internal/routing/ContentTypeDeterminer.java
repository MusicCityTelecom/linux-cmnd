/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.routing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.routing.CombinedMediaType;
import org.glassfish.jersey.internal.routing.RequestSpecificConsumesProducesAcceptor;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.message.WriterModel;
import org.glassfish.jersey.message.internal.AcceptableMediaType;
import org.glassfish.jersey.message.internal.MediaTypes;

public class ContentTypeDeterminer {
    protected MessageBodyWorkers workers;

    protected ContentTypeDeterminer(MessageBodyWorkers workers) {
        this.workers = workers;
    }

    protected MediaType determineResponseMediaType(Class<?> entityClass, Type entityType, RequestSpecificConsumesProducesAcceptor<?> selectedMethod, List<AcceptableMediaType> acceptableMediaTypes, List<MediaType> methodProducesTypes, Annotation[] handlingMethodAnnotations) {
        Class<?> responseEntityClass = entityClass;
        List<WriterModel> writersForEntityType = this.workers.getWritersModelsForType(responseEntityClass);
        CombinedMediaType selected = null;
        for (MediaType mediaType : acceptableMediaTypes) {
            for (MediaType methodProducesType : methodProducesTypes) {
                if (!mediaType.isCompatible(methodProducesType)) continue;
                for (WriterModel model : writersForEntityType) {
                    for (MediaType writerProduces : model.declaredTypes()) {
                        CombinedMediaType.EffectiveMediaType effectiveProduces;
                        CombinedMediaType candidate;
                        if (!writerProduces.isCompatible(mediaType) || !methodProducesType.isCompatible(writerProduces) || (candidate = CombinedMediaType.create(mediaType, effectiveProduces = new CombinedMediaType.EffectiveMediaType(MediaTypes.mostSpecific(methodProducesType, writerProduces), false))) == CombinedMediaType.NO_MATCH || selected != null && CombinedMediaType.COMPARATOR.compare(candidate, selected) >= 0 || !model.isWriteable(responseEntityClass, entityType, handlingMethodAnnotations, candidate.getCombinedType())) continue;
                        selected = candidate;
                    }
                }
            }
        }
        if (selected != null) {
            return selected.getCombinedType();
        }
        return selectedMethod.getProduces().getCombinedType();
    }
}

