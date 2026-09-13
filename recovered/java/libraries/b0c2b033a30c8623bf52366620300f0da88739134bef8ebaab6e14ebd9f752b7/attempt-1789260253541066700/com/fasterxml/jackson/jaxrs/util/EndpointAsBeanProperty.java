/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.jaxrs.util;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyMetadata;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import java.lang.annotation.Annotation;

public class EndpointAsBeanProperty
extends BeanProperty.Std {
    private static final long serialVersionUID = 1L;
    public static final PropertyName ENDPOINT_NAME = new PropertyName("JAX-RS/endpoint");
    private static final AnnotationMap NO_ANNOTATIONS = new AnnotationMap();
    protected transient Annotation[] _rawAnnotations;
    public AnnotationMap _annotations;

    public EndpointAsBeanProperty(PropertyName name, JavaType type, Annotation[] annotations) {
        super(name, type, null, null, PropertyMetadata.STD_OPTIONAL);
        this._rawAnnotations = annotations;
        this._annotations = null;
    }

    protected EndpointAsBeanProperty(EndpointAsBeanProperty base, JavaType newType) {
        super(base, newType);
        this._rawAnnotations = base._rawAnnotations;
        this._annotations = base._annotations;
    }

    @Override
    public BeanProperty.Std withType(JavaType type) {
        if (this._type == type) {
            return this;
        }
        return new BeanProperty.Std(this._name, type, this._wrapperName, this._member, this._metadata);
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return this.annotations().get(acls);
    }

    protected AnnotationMap annotations() {
        AnnotationMap am = this._annotations;
        if (am == null) {
            Annotation[] raw = this._rawAnnotations;
            if (raw == null || raw.length == 0) {
                am = NO_ANNOTATIONS;
            } else {
                am = new AnnotationMap();
                for (Annotation a : raw) {
                    am.add(a);
                }
            }
            this._annotations = am;
        }
        return am;
    }
}

