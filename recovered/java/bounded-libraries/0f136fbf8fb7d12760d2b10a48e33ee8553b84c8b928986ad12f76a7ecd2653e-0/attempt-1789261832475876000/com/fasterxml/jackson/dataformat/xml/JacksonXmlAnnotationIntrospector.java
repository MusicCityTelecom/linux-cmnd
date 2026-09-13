/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.fasterxml.jackson.databind.PropertyName
 *  com.fasterxml.jackson.databind.cfg.MapperConfig
 *  com.fasterxml.jackson.databind.introspect.Annotated
 *  com.fasterxml.jackson.databind.introspect.AnnotatedClass
 *  com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector
 *  com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder
 */
package com.fasterxml.jackson.dataformat.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.dataformat.xml.XmlAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlTypeResolverBuilder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import java.lang.annotation.Annotation;

public class JacksonXmlAnnotationIntrospector
extends JacksonAnnotationIntrospector
implements XmlAnnotationIntrospector {
    private static final long serialVersionUID = 1L;
    private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_XML_PROP = new Class[]{JacksonXmlText.class, JacksonXmlElementWrapper.class};
    public static final boolean DEFAULT_USE_WRAPPER = true;
    protected boolean _cfgDefaultUseWrapper;

    public JacksonXmlAnnotationIntrospector() {
        this(true);
    }

    public JacksonXmlAnnotationIntrospector(boolean defaultUseWrapper) {
        this._cfgDefaultUseWrapper = defaultUseWrapper;
    }

    public void setDefaultUseWrapper(boolean b) {
        this._cfgDefaultUseWrapper = b;
    }

    public PropertyName findWrapperName(Annotated ann) {
        JacksonXmlElementWrapper w = (JacksonXmlElementWrapper)this._findAnnotation(ann, JacksonXmlElementWrapper.class);
        if (w != null) {
            if (!w.useWrapping()) {
                return PropertyName.NO_NAME;
            }
            String localName = w.localName();
            if (localName == null || localName.length() == 0) {
                return PropertyName.USE_DEFAULT;
            }
            return PropertyName.construct((String)w.localName(), (String)w.namespace());
        }
        if (this._cfgDefaultUseWrapper) {
            return PropertyName.USE_DEFAULT;
        }
        return null;
    }

    public PropertyName findRootName(AnnotatedClass ac) {
        JacksonXmlRootElement root = (JacksonXmlRootElement)this._findAnnotation((Annotated)ac, JacksonXmlRootElement.class);
        if (root != null) {
            String local = root.localName();
            String ns = root.namespace();
            if (local.length() == 0 && ns.length() == 0) {
                return PropertyName.USE_DEFAULT;
            }
            return new PropertyName(local, ns);
        }
        return super.findRootName(ac);
    }

    public String findNamespace(MapperConfig<?> config, Annotated ann) {
        JacksonXmlProperty prop = (JacksonXmlProperty)this._findAnnotation(ann, JacksonXmlProperty.class);
        if (prop != null) {
            return prop.namespace();
        }
        JsonProperty jprop = (JsonProperty)this._findAnnotation(ann, JsonProperty.class);
        if (jprop != null) {
            return jprop.namespace();
        }
        return null;
    }

    public Boolean isOutputAsAttribute(MapperConfig<?> config, Annotated ann) {
        JacksonXmlProperty prop = (JacksonXmlProperty)this._findAnnotation(ann, JacksonXmlProperty.class);
        if (prop != null) {
            return prop.isAttribute() ? Boolean.TRUE : Boolean.FALSE;
        }
        return null;
    }

    public Boolean isOutputAsText(MapperConfig<?> config, Annotated ann) {
        JacksonXmlText prop = (JacksonXmlText)this._findAnnotation(ann, JacksonXmlText.class);
        if (prop != null) {
            return prop.value() ? Boolean.TRUE : Boolean.FALSE;
        }
        return null;
    }

    public Boolean isOutputAsCData(MapperConfig<?> config, Annotated ann) {
        JacksonXmlCData prop = (JacksonXmlCData)ann.getAnnotation(JacksonXmlCData.class);
        if (prop != null) {
            return prop.value() ? Boolean.TRUE : Boolean.FALSE;
        }
        return null;
    }

    public PropertyName findNameForSerialization(Annotated a) {
        PropertyName name = this._findXmlName(a);
        if (name == null && (name = super.findNameForSerialization(a)) == null && this._hasOneOf(a, ANNOTATIONS_TO_INFER_XML_PROP)) {
            return PropertyName.USE_DEFAULT;
        }
        return name;
    }

    public PropertyName findNameForDeserialization(Annotated a) {
        PropertyName name = this._findXmlName(a);
        if (name == null && (name = super.findNameForDeserialization(a)) == null && this._hasOneOf(a, ANNOTATIONS_TO_INFER_XML_PROP)) {
            return PropertyName.USE_DEFAULT;
        }
        return name;
    }

    protected StdTypeResolverBuilder _constructStdTypeResolverBuilder() {
        return new XmlTypeResolverBuilder();
    }

    protected PropertyName _findXmlName(Annotated a) {
        JacksonXmlProperty pann = (JacksonXmlProperty)this._findAnnotation(a, JacksonXmlProperty.class);
        if (pann != null) {
            return PropertyName.construct((String)pann.localName(), (String)pann.namespace());
        }
        return null;
    }
}

