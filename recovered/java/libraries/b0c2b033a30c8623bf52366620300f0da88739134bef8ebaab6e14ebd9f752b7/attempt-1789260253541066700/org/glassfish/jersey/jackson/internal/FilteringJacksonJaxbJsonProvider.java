/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.jackson.internal.DefaultJacksonJaxbJsonProvider;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.EndpointConfigBase;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterInjector;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterModifier;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JsonEndpointConfig;
import org.glassfish.jersey.message.filtering.spi.ObjectProvider;

@Singleton
public final class FilteringJacksonJaxbJsonProvider
extends DefaultJacksonJaxbJsonProvider {
    @Inject
    private Provider<ObjectProvider<FilterProvider>> provider;

    @Override
    protected JsonEndpointConfig _configForWriting(ObjectMapper mapper, Annotation[] annotations, Class<?> defaultView) {
        AnnotationIntrospector customIntrospector = mapper.getSerializationConfig().getAnnotationIntrospector();
        ObjectMapper filteringMapper = mapper.setAnnotationIntrospector(AnnotationIntrospector.pair(customIntrospector, new JacksonAnnotationIntrospector(){

            @Override
            public Object findFilterId(Annotated a) {
                Method method;
                Object filterId = super.findFilterId(a);
                if (filterId != null) {
                    return filterId;
                }
                if (a instanceof AnnotatedMethod && ReflectionHelper.isGetter(method = ((AnnotatedMethod)a).getAnnotated())) {
                    return ReflectionHelper.getPropertyName(method);
                }
                if (a instanceof AnnotatedField || a instanceof AnnotatedClass) {
                    return a.getName();
                }
                return null;
            }
        }));
        return (JsonEndpointConfig)super._configForWriting(filteringMapper, annotations, defaultView);
    }

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        FilterProvider filterProvider = this.provider.get().getFilteringObject(genericType, true, annotations);
        if (filterProvider != null) {
            ObjectWriterInjector.set(new FilteringObjectWriterModifier(filterProvider, ObjectWriterInjector.getAndClear()));
        }
        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }

    private static final class FilteringObjectWriterModifier
    extends ObjectWriterModifier {
        private final ObjectWriterModifier original;
        private final FilterProvider filterProvider;

        private FilteringObjectWriterModifier(FilterProvider filterProvider, ObjectWriterModifier original) {
            this.original = original;
            this.filterProvider = filterProvider;
        }

        @Override
        public ObjectWriter modify(EndpointConfigBase<?> endpoint, MultivaluedMap<String, Object> responseHeaders, Object valueToWrite, ObjectWriter w, JsonGenerator g) throws IOException {
            ObjectWriter writer = this.original == null ? w : this.original.modify(endpoint, responseHeaders, valueToWrite, w, g);
            final FilterProvider customFilterProvider = writer.getConfig().getFilterProvider();
            return customFilterProvider == null ? writer.with(this.filterProvider) : writer.with(new FilterProvider(){

                @Override
                public BeanPropertyFilter findFilter(Object filterId) {
                    return customFilterProvider.findFilter(filterId);
                }

                @Override
                public PropertyFilter findPropertyFilter(Object filterId, Object valueToFilter) {
                    PropertyFilter filter = customFilterProvider.findPropertyFilter(filterId, valueToFilter);
                    if (filter != null) {
                        return filter;
                    }
                    return filterProvider.findPropertyFilter(filterId, valueToFilter);
                }
            });
        }
    }
}

