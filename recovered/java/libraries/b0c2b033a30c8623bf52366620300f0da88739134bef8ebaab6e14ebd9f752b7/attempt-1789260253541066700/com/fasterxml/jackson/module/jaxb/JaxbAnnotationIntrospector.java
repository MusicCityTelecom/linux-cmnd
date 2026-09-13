/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.module.jaxb;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.ObjectIdInfo;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.Converter;
import com.fasterxml.jackson.module.jaxb.AdapterConverter;
import com.fasterxml.jackson.module.jaxb.PackageVersion;
import com.fasterxml.jackson.module.jaxb.deser.DataHandlerJsonDeserializer;
import com.fasterxml.jackson.module.jaxb.ser.DataHandlerJsonSerializer;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

public class JaxbAnnotationIntrospector
extends AnnotationIntrospector
implements Versioned {
    private static final long serialVersionUID = -1L;
    protected static final String DEFAULT_NAME_FOR_XML_VALUE = "value";
    protected static final boolean DEFAULT_IGNORE_XMLIDREF = false;
    protected static final String MARKER_FOR_DEFAULT = "##default";
    protected static final JsonFormat.Value FORMAT_STRING = new JsonFormat.Value().withShape(JsonFormat.Shape.STRING);
    protected static final JsonFormat.Value FORMAT_INT = new JsonFormat.Value().withShape(JsonFormat.Shape.NUMBER_INT);
    protected final String _jaxbPackageName;
    protected final JsonSerializer<?> _dataHandlerSerializer;
    protected final JsonDeserializer<?> _dataHandlerDeserializer;
    protected final TypeFactory _typeFactory;
    protected final boolean _ignoreXmlIDREF;
    protected String _xmlValueName = "value";
    protected JsonInclude.Include _nonNillableInclusion = null;

    @Deprecated
    public JaxbAnnotationIntrospector() {
        this(TypeFactory.defaultInstance());
    }

    public JaxbAnnotationIntrospector(MapperConfig<?> config) {
        this(config.getTypeFactory());
    }

    public JaxbAnnotationIntrospector(TypeFactory typeFactory) {
        this(typeFactory, false);
    }

    public JaxbAnnotationIntrospector(TypeFactory typeFactory, boolean ignoreXmlIDREF) {
        this._typeFactory = typeFactory == null ? TypeFactory.defaultInstance() : typeFactory;
        this._ignoreXmlIDREF = ignoreXmlIDREF;
        this._jaxbPackageName = XmlElement.class.getPackage().getName();
        JsonSerializer dataHandlerSerializer = null;
        JsonDeserializer dataHandlerDeserializer = null;
        try {
            dataHandlerSerializer = (JsonSerializer)DataHandlerJsonSerializer.class.newInstance();
            dataHandlerDeserializer = (JsonDeserializer)DataHandlerJsonDeserializer.class.newInstance();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this._dataHandlerSerializer = dataHandlerSerializer;
        this._dataHandlerDeserializer = dataHandlerDeserializer;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public void setNameUsedForXmlValue(String name) {
        this._xmlValueName = name;
    }

    public String getNameUsedForXmlValue() {
        return this._xmlValueName;
    }

    public JaxbAnnotationIntrospector setNonNillableInclusion(JsonInclude.Include incl) {
        this._nonNillableInclusion = incl;
        return this;
    }

    public JsonInclude.Include getNonNillableInclusion() {
        return this._nonNillableInclusion;
    }

    public String findNamespace(Annotated ann) {
        String ns = null;
        if (ann instanceof AnnotatedClass) {
            XmlRootElement elem = this.findRootElementAnnotation((AnnotatedClass)ann);
            if (elem != null) {
                ns = elem.namespace();
            }
        } else {
            XmlAttribute attr;
            XmlElement elem = this.findAnnotation(XmlElement.class, ann, false, false, false);
            if (elem != null) {
                ns = elem.namespace();
            }
            if ((ns == null || MARKER_FOR_DEFAULT.equals(ns)) && (attr = this.findAnnotation(XmlAttribute.class, ann, false, false, false)) != null) {
                ns = attr.namespace();
            }
        }
        if (MARKER_FOR_DEFAULT.equals(ns)) {
            ns = null;
        }
        return ns;
    }

    public Boolean isOutputAsAttribute(Annotated ann) {
        XmlAttribute attr = this.findAnnotation(XmlAttribute.class, ann, false, false, false);
        if (attr != null) {
            return Boolean.TRUE;
        }
        XmlElement elem = this.findAnnotation(XmlElement.class, ann, false, false, false);
        if (elem != null) {
            return Boolean.FALSE;
        }
        return null;
    }

    public Boolean isOutputAsText(Annotated ann) {
        XmlValue attr = this.findAnnotation(XmlValue.class, ann, false, false, false);
        if (attr != null) {
            return Boolean.TRUE;
        }
        return null;
    }

    @Override
    public ObjectIdInfo findObjectIdInfo(Annotated ann) {
        XmlID idProp;
        if (!(ann instanceof AnnotatedClass)) {
            return null;
        }
        AnnotatedClass ac = (AnnotatedClass)ann;
        PropertyName idPropName = null;
        block4: for (AnnotatedMethod m : ac.memberMethods()) {
            idProp = m.getAnnotation(XmlID.class);
            if (idProp == null) continue;
            switch (m.getParameterCount()) {
                case 0: {
                    idPropName = this.findJaxbPropertyName(m, m.getRawType(), BeanUtil.okNameForGetter(m, true));
                    break block4;
                }
                case 1: {
                    idPropName = this.findJaxbPropertyName(m, m.getRawType(), BeanUtil.okNameForMutator(m, "set", true));
                    break block4;
                }
                default: {
                    continue block4;
                }
            }
        }
        if (idPropName == null) {
            for (AnnotatedField f : ac.fields()) {
                idProp = f.getAnnotation(XmlID.class);
                if (idProp == null) continue;
                idPropName = this.findJaxbPropertyName(f, f.getRawType(), f.getName());
                break;
            }
        }
        if (idPropName != null) {
            Class<Object> scope = Object.class;
            return new ObjectIdInfo(idPropName, scope, ObjectIdGenerators.PropertyGenerator.class, SimpleObjectIdResolver.class);
        }
        return null;
    }

    @Override
    public ObjectIdInfo findObjectReferenceInfo(Annotated ann, ObjectIdInfo base) {
        XmlIDREF idref;
        if (!this._ignoreXmlIDREF && (idref = ann.getAnnotation(XmlIDREF.class)) != null) {
            if (base == null) {
                base = ObjectIdInfo.empty();
            }
            base = base.withAlwaysAsId(true);
        }
        return base;
    }

    @Override
    public PropertyName findRootName(AnnotatedClass ac) {
        XmlRootElement elem = this.findRootElementAnnotation(ac);
        if (elem != null) {
            return JaxbAnnotationIntrospector._combineNames(elem.name(), elem.namespace(), "");
        }
        return null;
    }

    @Override
    public Boolean isIgnorableType(AnnotatedClass ac) {
        return null;
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return m.getAnnotation(XmlTransient.class) != null;
    }

    @Override
    public Boolean hasRequiredMarker(AnnotatedMember m) {
        XmlAttribute attr = m.getAnnotation(XmlAttribute.class);
        if (attr != null) {
            return attr.required();
        }
        XmlElement elem = m.getAnnotation(XmlElement.class);
        if (elem != null) {
            return elem.required();
        }
        return null;
    }

    @Override
    public PropertyName findWrapperName(Annotated ann) {
        XmlElementWrapper w = this.findAnnotation(XmlElementWrapper.class, ann, false, false, false);
        if (w != null) {
            PropertyName name = JaxbAnnotationIntrospector._combineNames(w.name(), w.namespace(), "");
            if (!name.hasSimpleName()) {
                AnnotatedMethod am;
                String str;
                if (ann instanceof AnnotatedMethod && (str = (am = (AnnotatedMethod)ann).getParameterCount() == 0 ? BeanUtil.okNameForGetter(am, true) : BeanUtil.okNameForMutator(am, "set", true)) != null) {
                    return name.withSimpleName(str);
                }
                return name.withSimpleName(ann.getName());
            }
            return name;
        }
        return null;
    }

    @Override
    public String findImplicitPropertyName(AnnotatedMember m) {
        XmlValue valueInfo = m.getAnnotation(XmlValue.class);
        if (valueInfo != null) {
            return this._xmlValueName;
        }
        return null;
    }

    @Override
    public JsonFormat.Value findFormat(Annotated m) {
        XmlEnum ann;
        if (m instanceof AnnotatedClass && (ann = m.getAnnotation(XmlEnum.class)) != null) {
            Class<?> type = ann.value();
            if (type == String.class || type.isEnum()) {
                return FORMAT_STRING;
            }
            if (Number.class.isAssignableFrom(type)) {
                return FORMAT_INT;
            }
        }
        return null;
    }

    @Override
    public VisibilityChecker<?> findAutoDetectVisibility(AnnotatedClass ac, VisibilityChecker<?> checker) {
        XmlAccessType at = this.findAccessType(ac);
        if (at == null) {
            return checker;
        }
        switch (at) {
            case FIELD: {
                return checker.withFieldVisibility(JsonAutoDetect.Visibility.ANY).withSetterVisibility(JsonAutoDetect.Visibility.NONE).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
            }
            case NONE: {
                return checker.withFieldVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE);
            }
            case PROPERTY: {
                return checker.withFieldVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY).withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY).withIsGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY);
            }
            case PUBLIC_MEMBER: {
                return checker.withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY).withSetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY).withGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY).withIsGetterVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY);
            }
        }
        return checker;
    }

    protected XmlAccessType findAccessType(Annotated ac) {
        XmlAccessorType at = this.findAnnotation(XmlAccessorType.class, ac, true, true, true);
        return at == null ? null : at.value();
    }

    @Override
    public TypeResolverBuilder<?> findTypeResolver(MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        return null;
    }

    @Override
    public TypeResolverBuilder<?> findPropertyTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType baseType) {
        if (baseType.isContainerType()) {
            return null;
        }
        return this._typeResolverFromXmlElements(am);
    }

    @Override
    public TypeResolverBuilder<?> findPropertyContentTypeResolver(MapperConfig<?> config, AnnotatedMember am, JavaType containerType) {
        if (containerType.getContentType() == null) {
            throw new IllegalArgumentException("Must call method with a container or reference type (got " + containerType + ")");
        }
        return this._typeResolverFromXmlElements(am);
    }

    protected TypeResolverBuilder<?> _typeResolverFromXmlElements(AnnotatedMember am) {
        XmlElements elems = this.findAnnotation(XmlElements.class, am, false, false, false);
        XmlElementRefs elemRefs = this.findAnnotation(XmlElementRefs.class, am, false, false, false);
        if (elems == null && elemRefs == null) {
            return null;
        }
        StdTypeResolverBuilder b = new StdTypeResolverBuilder();
        b = b.init(JsonTypeInfo.Id.NAME, null);
        b = b.inclusion(JsonTypeInfo.As.WRAPPER_OBJECT);
        return b;
    }

    @Override
    public List<NamedType> findSubtypes(Annotated a) {
        XmlSeeAlso ann;
        XmlElements elems = this.findAnnotation(XmlElements.class, a, false, false, false);
        ArrayList<NamedType> result = null;
        if (elems != null) {
            result = new ArrayList<NamedType>();
            for (XmlElement elem : elems.value()) {
                String name = elem.name();
                if (MARKER_FOR_DEFAULT.equals(name)) {
                    name = null;
                }
                result.add(new NamedType(elem.type(), name));
            }
        } else {
            XmlElementRefs elemRefs = this.findAnnotation(XmlElementRefs.class, a, false, false, false);
            if (elemRefs != null) {
                result = new ArrayList();
                for (XmlElementRef elemRef : elemRefs.value()) {
                    XmlRootElement rootElement;
                    Class refType = elemRef.type();
                    if (JAXBElement.class.isAssignableFrom(refType)) continue;
                    String name = elemRef.name();
                    if ((name == null || MARKER_FOR_DEFAULT.equals(name)) && (rootElement = refType.getAnnotation(XmlRootElement.class)) != null) {
                        name = rootElement.name();
                    }
                    if (name == null || MARKER_FOR_DEFAULT.equals(name)) {
                        name = Introspector.decapitalize(refType.getSimpleName());
                    }
                    result.add(new NamedType(refType, name));
                }
            }
        }
        if ((ann = a.getAnnotation(XmlSeeAlso.class)) != null) {
            if (result == null) {
                result = new ArrayList();
            }
            for (Class cls : ann.value()) {
                result.add(new NamedType(cls));
            }
        }
        return result;
    }

    @Override
    public String findTypeName(AnnotatedClass ac) {
        String name;
        XmlType type = this.findAnnotation(XmlType.class, ac, false, false, false);
        if (type != null && !MARKER_FOR_DEFAULT.equals(name = type.name())) {
            return name;
        }
        return null;
    }

    @Override
    public JsonSerializer<?> findSerializer(Annotated am) {
        Class<?> type = this._rawSerializationType(am);
        if (type != null && this._dataHandlerSerializer != null && this.isDataHandler(type)) {
            return this._dataHandlerSerializer;
        }
        return null;
    }

    private boolean isDataHandler(Class<?> type) {
        return type != null && Object.class != type && ("javax.activation.DataHandler".equals(type.getName()) || this.isDataHandler(type.getSuperclass()));
    }

    @Override
    public Object findContentSerializer(Annotated a) {
        return null;
    }

    @Override
    @Deprecated
    public Class<?> findSerializationType(Annotated a) {
        Class<?> rawPropType;
        Class<?> allegedType = this._getTypeFromXmlElement(a);
        if (allegedType != null && !this.isContainerType(rawPropType = this._rawSerializationType(a))) {
            return allegedType;
        }
        return null;
    }

    @Override
    public JsonInclude.Value findPropertyInclusion(Annotated a) {
        JsonInclude.Include incl = this._serializationInclusion(a, null);
        if (incl == null) {
            return JsonInclude.Value.empty();
        }
        return JsonInclude.Value.construct(incl, null);
    }

    JsonInclude.Include _serializationInclusion(Annotated a, JsonInclude.Include defValue) {
        XmlElement e;
        XmlElementWrapper w = a.getAnnotation(XmlElementWrapper.class);
        if (w != null) {
            if (w.nillable()) {
                return JsonInclude.Include.ALWAYS;
            }
            if (this._nonNillableInclusion != null) {
                return this._nonNillableInclusion;
            }
        }
        if ((e = a.getAnnotation(XmlElement.class)) != null) {
            if (e.nillable()) {
                return JsonInclude.Include.ALWAYS;
            }
            if (this._nonNillableInclusion != null) {
                return this._nonNillableInclusion;
            }
        }
        return defValue;
    }

    @Override
    public JavaType refineSerializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
        Class<?> serClass = this._getTypeFromXmlElement(a);
        if (serClass == null) {
            return baseType;
        }
        TypeFactory tf = config.getTypeFactory();
        if (baseType.getContentType() == null) {
            if (!serClass.isAssignableFrom(baseType.getRawClass())) {
                return baseType;
            }
            if (baseType.hasRawClass(serClass)) {
                return baseType.withStaticTyping();
            }
            try {
                return tf.constructGeneralizedType(baseType, serClass);
            }
            catch (IllegalArgumentException iae) {
                throw new JsonMappingException(null, String.format("Failed to widen type %s with annotation (value %s), from '%s': %s", baseType, serClass.getName(), a.getName(), iae.getMessage()), (Throwable)iae);
            }
        }
        JavaType contentType = baseType.getContentType();
        if (contentType != null) {
            if (!serClass.isAssignableFrom(contentType.getRawClass())) {
                return baseType;
            }
            if (contentType.hasRawClass(serClass)) {
                contentType = contentType.withStaticTyping();
            } else {
                try {
                    contentType = tf.constructGeneralizedType(contentType, serClass);
                }
                catch (IllegalArgumentException iae) {
                    throw new JsonMappingException(null, String.format("Failed to widen value type of %s with concrete-type annotation (value %s), from '%s': %s", baseType, serClass.getName(), a.getName(), iae.getMessage()), (Throwable)iae);
                }
            }
            return baseType.withContentType(contentType);
        }
        return baseType;
    }

    @Override
    public String[] findSerializationPropertyOrder(AnnotatedClass ac) {
        XmlType type = this.findAnnotation(XmlType.class, ac, true, true, true);
        if (type == null) {
            return null;
        }
        String[] order = type.propOrder();
        if (order == null || order.length == 0) {
            return null;
        }
        return order;
    }

    @Override
    public Boolean findSerializationSortAlphabetically(Annotated ann) {
        return this._findAlpha(ann);
    }

    private final Boolean _findAlpha(Annotated ann) {
        XmlAccessorOrder order = this.findAnnotation(XmlAccessorOrder.class, ann, true, true, true);
        return order == null ? null : Boolean.valueOf(order.value() == XmlAccessOrder.ALPHABETICAL);
    }

    @Override
    public Object findSerializationConverter(Annotated a) {
        Class<?> serType = this._rawSerializationType(a);
        XmlAdapter<Object, Object> adapter = this.findAdapter(a, true, serType);
        if (adapter != null) {
            return this._converter(adapter, true);
        }
        return null;
    }

    @Override
    public Object findSerializationContentConverter(AnnotatedMember a) {
        XmlAdapter<?, ?> adapter;
        Class<?> serType = this._rawSerializationType(a);
        if (this.isContainerType(serType) && (adapter = this._findContentAdapter(a, true)) != null) {
            return this._converter(adapter, true);
        }
        return null;
    }

    @Override
    public PropertyName findNameForSerialization(Annotated a) {
        if (a instanceof AnnotatedMethod) {
            AnnotatedMethod am = (AnnotatedMethod)a;
            return this.isVisible(am) ? this.findJaxbPropertyName(am, am.getRawType(), BeanUtil.okNameForGetter(am, true)) : null;
        }
        if (a instanceof AnnotatedField) {
            AnnotatedField af = (AnnotatedField)a;
            return this.isVisible(af) ? this.findJaxbPropertyName(af, af.getRawType(), null) : null;
        }
        return null;
    }

    @Override
    @Deprecated
    public boolean hasAsValueAnnotation(AnnotatedMethod am) {
        return false;
    }

    @Override
    public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
        HashMap<String, String> expl = null;
        for (Field f : ClassUtil.getDeclaredFields(enumType)) {
            String n;
            XmlEnumValue enumValue;
            if (!f.isEnumConstant() || (enumValue = f.getAnnotation(XmlEnumValue.class)) == null || (n = enumValue.value()).isEmpty()) continue;
            if (expl == null) {
                expl = new HashMap<String, String>();
            }
            expl.put(f.getName(), n);
        }
        if (expl != null) {
            int end = enumValues.length;
            for (int i = 0; i < end; ++i) {
                String defName = enumValues[i].name();
                String explValue = (String)expl.get(defName);
                if (explValue == null) continue;
                names[i] = explValue;
            }
        }
        return names;
    }

    @Override
    public Object findDeserializer(Annotated am) {
        Class<?> type = this._rawDeserializationType(am);
        if (type != null && this._dataHandlerDeserializer != null && this.isDataHandler(type)) {
            return this._dataHandlerDeserializer;
        }
        return null;
    }

    @Override
    public Object findKeyDeserializer(Annotated am) {
        return null;
    }

    @Override
    public Object findContentDeserializer(Annotated a) {
        return null;
    }

    protected Class<?> _doFindDeserializationType(Annotated a, JavaType baseType) {
        Class type;
        if (a.hasAnnotation(XmlJavaTypeAdapter.class)) {
            return null;
        }
        XmlElement annotation = this.findAnnotation(XmlElement.class, a, false, false, false);
        if (annotation != null && (type = annotation.type()) != XmlElement.DEFAULT.class) {
            return type;
        }
        return null;
    }

    @Override
    public JavaType refineDeserializationType(MapperConfig<?> config, Annotated a, JavaType baseType) throws JsonMappingException {
        Class<?> deserClass = this._getTypeFromXmlElement(a);
        if (deserClass == null) {
            return baseType;
        }
        TypeFactory tf = config.getTypeFactory();
        if (baseType.getContentType() == null) {
            if (baseType.hasRawClass(deserClass)) {
                return baseType;
            }
            if (!baseType.getRawClass().isAssignableFrom(deserClass)) {
                return baseType;
            }
            try {
                return tf.constructSpecializedType(baseType, deserClass);
            }
            catch (IllegalArgumentException iae) {
                throw new JsonMappingException(null, String.format("Failed to narrow type %s with annotation (value %s), from '%s': %s", baseType, deserClass.getName(), a.getName(), iae.getMessage()), (Throwable)iae);
            }
        }
        JavaType contentType = baseType.getContentType();
        if (contentType != null) {
            if (!contentType.getRawClass().isAssignableFrom(deserClass)) {
                return baseType;
            }
            try {
                contentType = tf.constructSpecializedType(contentType, deserClass);
                return baseType.withContentType(contentType);
            }
            catch (IllegalArgumentException iae) {
                throw new JsonMappingException(null, String.format("Failed to narrow type %s with annotation (value %s), from '%s': %s", baseType, deserClass.getName(), a.getName(), iae.getMessage()), (Throwable)iae);
            }
        }
        return baseType;
    }

    @Override
    public PropertyName findNameForDeserialization(Annotated a) {
        if (a instanceof AnnotatedMethod) {
            AnnotatedMethod am = (AnnotatedMethod)a;
            if (!this.isVisible(am)) {
                return null;
            }
            Class<?> rawType = am.getRawParameterType(0);
            return this.findJaxbPropertyName(am, rawType, BeanUtil.okNameForMutator(am, "set", true));
        }
        if (a instanceof AnnotatedField) {
            AnnotatedField af = (AnnotatedField)a;
            return this.isVisible(af) ? this.findJaxbPropertyName(af, af.getRawType(), null) : null;
        }
        return null;
    }

    @Override
    public Object findDeserializationConverter(Annotated a) {
        Class<?> deserType = this._rawDeserializationType(a);
        if (this.isContainerType(deserType)) {
            XmlAdapter<Object, Object> adapter = this.findAdapter(a, true, deserType);
            if (adapter != null) {
                return this._converter(adapter, false);
            }
        } else {
            XmlAdapter<Object, Object> adapter = this.findAdapter(a, true, deserType);
            if (adapter != null) {
                return this._converter(adapter, false);
            }
        }
        return null;
    }

    @Override
    public Object findDeserializationContentConverter(AnnotatedMember a) {
        XmlAdapter<?, ?> adapter;
        Class<?> deserType = this._rawDeserializationType(a);
        if (this.isContainerType(deserType) && (adapter = this._findContentAdapter(a, false)) != null) {
            return this._converter(adapter, false);
        }
        return null;
    }

    private boolean isVisible(AnnotatedField f) {
        for (Annotation annotation : f.getAnnotated().getDeclaredAnnotations()) {
            if (!this.isJAXBAnnotation(annotation)) continue;
            return true;
        }
        XmlAccessType accessType = XmlAccessType.PUBLIC_MEMBER;
        XmlAccessorType at = this.findAnnotation(XmlAccessorType.class, f, true, true, true);
        if (at != null) {
            accessType = at.value();
        }
        if (accessType == XmlAccessType.FIELD) {
            return true;
        }
        if (accessType == XmlAccessType.PUBLIC_MEMBER) {
            return Modifier.isPublic(f.getAnnotated().getModifiers());
        }
        return false;
    }

    private boolean isVisible(AnnotatedMethod m) {
        for (Annotation annotation : m.getAnnotated().getDeclaredAnnotations()) {
            if (!this.isJAXBAnnotation(annotation)) continue;
            return true;
        }
        XmlAccessType accessType = XmlAccessType.PUBLIC_MEMBER;
        XmlAccessorType at = this.findAnnotation(XmlAccessorType.class, m, true, true, true);
        if (at != null) {
            accessType = at.value();
        }
        if (accessType == XmlAccessType.PROPERTY || accessType == XmlAccessType.PUBLIC_MEMBER) {
            return Modifier.isPublic(m.getModifiers());
        }
        return false;
    }

    private <A extends Annotation> A findAnnotation(Class<A> annotationClass, Annotated annotated, boolean includePackage, boolean includeClass, boolean includeSuperclasses) {
        A annotation = annotated.getAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Class memberClass = null;
        if (annotated instanceof AnnotatedParameter) {
            memberClass = ((AnnotatedParameter)annotated).getDeclaringClass();
        } else {
            AnnotatedElement annType = annotated.getAnnotated();
            if (annType instanceof Member) {
                memberClass = ((Member)((Object)annType)).getDeclaringClass();
                if (includeClass && (annotation = memberClass.getAnnotation(annotationClass)) != null) {
                    return annotation;
                }
            } else if (annType instanceof Class) {
                memberClass = (Class)annType;
            }
        }
        if (memberClass != null) {
            Package pkg;
            if (includeSuperclasses) {
                for (Class<?> superclass = memberClass.getSuperclass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
                    annotation = superclass.getAnnotation(annotationClass);
                    if (annotation == null) continue;
                    return annotation;
                }
            }
            if (includePackage && (pkg = memberClass.getPackage()) != null) {
                return memberClass.getPackage().getAnnotation(annotationClass);
            }
        }
        return null;
    }

    protected boolean isJAXBAnnotation(Annotation ann) {
        String pkgName;
        Class<? extends Annotation> cls = ann.annotationType();
        Package pkg = cls.getPackage();
        String string = pkgName = pkg != null ? pkg.getName() : cls.getName();
        return pkgName.startsWith(this._jaxbPackageName);
    }

    private PropertyName findJaxbPropertyName(Annotated ae, Class<?> aeType, String defaultName) {
        boolean hasAName;
        XmlAttribute attribute = ae.getAnnotation(XmlAttribute.class);
        if (attribute != null) {
            return JaxbAnnotationIntrospector._combineNames(attribute.name(), attribute.namespace(), defaultName);
        }
        XmlElement element = ae.getAnnotation(XmlElement.class);
        if (element != null) {
            return JaxbAnnotationIntrospector._combineNames(element.name(), element.namespace(), defaultName);
        }
        XmlElementRef elementRef = ae.getAnnotation(XmlElementRef.class);
        boolean bl = hasAName = elementRef != null;
        if (hasAName) {
            XmlRootElement rootElement;
            if (!MARKER_FOR_DEFAULT.equals(elementRef.name())) {
                return JaxbAnnotationIntrospector._combineNames(elementRef.name(), elementRef.namespace(), defaultName);
            }
            if (aeType != null && (rootElement = aeType.getAnnotation(XmlRootElement.class)) != null) {
                String name = rootElement.name();
                if (!MARKER_FOR_DEFAULT.equals(name)) {
                    return JaxbAnnotationIntrospector._combineNames(name, rootElement.namespace(), defaultName);
                }
                return new PropertyName(Introspector.decapitalize(aeType.getSimpleName()));
            }
        }
        if (!hasAName) {
            hasAName = ae.hasAnnotation(XmlElementWrapper.class) || ae.hasAnnotation(XmlElements.class) || ae.hasAnnotation(XmlValue.class);
        }
        return hasAName ? PropertyName.USE_DEFAULT : null;
    }

    private static PropertyName _combineNames(String localName, String namespace, String defaultName) {
        if (MARKER_FOR_DEFAULT.equals(localName)) {
            if (MARKER_FOR_DEFAULT.equals(namespace)) {
                return new PropertyName(defaultName);
            }
            return new PropertyName(defaultName, namespace);
        }
        if (MARKER_FOR_DEFAULT.equals(namespace)) {
            return new PropertyName(localName);
        }
        return new PropertyName(localName, namespace);
    }

    private XmlRootElement findRootElementAnnotation(AnnotatedClass ac) {
        return this.findAnnotation(XmlRootElement.class, ac, true, false, true);
    }

    private XmlAdapter<Object, Object> findAdapter(Annotated am, boolean forSerialization, Class<?> type) {
        XmlAdapter<Object, Object> adapter;
        if (am instanceof AnnotatedClass) {
            return this.findAdapterForClass((AnnotatedClass)am, forSerialization);
        }
        XmlJavaTypeAdapter adapterInfo = this.findAnnotation(XmlJavaTypeAdapter.class, am, true, false, false);
        if (adapterInfo != null && (adapter = this.checkAdapter(adapterInfo, type, forSerialization)) != null) {
            return adapter;
        }
        XmlJavaTypeAdapters adapters = this.findAnnotation(XmlJavaTypeAdapters.class, am, true, false, false);
        if (adapters != null) {
            for (XmlJavaTypeAdapter info : adapters.value()) {
                XmlAdapter<Object, Object> adapter2 = this.checkAdapter(info, type, forSerialization);
                if (adapter2 == null) continue;
                return adapter2;
            }
        }
        return null;
    }

    private final XmlAdapter<Object, Object> checkAdapter(XmlJavaTypeAdapter adapterInfo, Class<?> typeNeeded, boolean forSerialization) {
        Class<?> adaptedType = adapterInfo.type();
        if (adaptedType == XmlJavaTypeAdapter.DEFAULT.class) {
            JavaType type = this._typeFactory.constructType(adapterInfo.value());
            JavaType[] params = this._typeFactory.findTypeParameters(type, XmlAdapter.class);
            adaptedType = params[1].getRawClass();
        }
        if (adaptedType.isAssignableFrom(typeNeeded)) {
            Class<? extends XmlAdapter> cls = adapterInfo.value();
            return ClassUtil.createInstance(cls, true);
        }
        return null;
    }

    private XmlAdapter<Object, Object> findAdapterForClass(AnnotatedClass ac, boolean forSerialization) {
        XmlJavaTypeAdapter adapterInfo = ((Class)ac.getAnnotated()).getAnnotation(XmlJavaTypeAdapter.class);
        if (adapterInfo != null) {
            Class<? extends XmlAdapter> cls = adapterInfo.value();
            return ClassUtil.createInstance(cls, true);
        }
        return null;
    }

    protected final TypeFactory getTypeFactory() {
        return this._typeFactory;
    }

    private boolean isContainerType(Class<?> raw) {
        return raw.isArray() || Collection.class.isAssignableFrom(raw) || Map.class.isAssignableFrom(raw);
    }

    private boolean adapterTypeMatches(XmlAdapter<?, ?> adapter, Class<?> targetType) {
        return this.findAdapterBoundType(adapter).isAssignableFrom(targetType);
    }

    private Class<?> findAdapterBoundType(XmlAdapter<?, ?> adapter) {
        JavaType adapterType;
        TypeFactory tf = this.getTypeFactory();
        JavaType[] params = tf.findTypeParameters(adapterType = tf.constructType(adapter.getClass()), XmlAdapter.class);
        if (params == null || params.length < 2) {
            return Object.class;
        }
        return params[1].getRawClass();
    }

    protected XmlAdapter<?, ?> _findContentAdapter(Annotated ann, boolean forSerialization) {
        JavaType fullType;
        Class<?> contentType;
        AnnotatedMember member;
        XmlAdapter<Object, Object> adapter;
        Class<?> rawType;
        Class<?> clazz = rawType = forSerialization ? this._rawSerializationType(ann) : this._rawDeserializationType(ann);
        if (this.isContainerType(rawType) && ann instanceof AnnotatedMember && (adapter = this.findAdapter(member = (AnnotatedMember)ann, forSerialization, contentType = (fullType = forSerialization ? this._fullSerializationType(member) : this._fullDeserializationType(member)).getContentType().getRawClass())) != null && this.adapterTypeMatches(adapter, contentType)) {
            return adapter;
        }
        return null;
    }

    protected String _propertyNameToString(PropertyName n) {
        return n == null ? null : n.getSimpleName();
    }

    protected Class<?> _rawDeserializationType(Annotated a) {
        AnnotatedMethod am;
        if (a instanceof AnnotatedMethod && (am = (AnnotatedMethod)a).getParameterCount() == 1) {
            return am.getRawParameterType(0);
        }
        return a.getRawType();
    }

    protected JavaType _fullDeserializationType(AnnotatedMember am) {
        AnnotatedMethod method;
        if (am instanceof AnnotatedMethod && (method = (AnnotatedMethod)am).getParameterCount() == 1) {
            return ((AnnotatedMethod)am).getParameterType(0);
        }
        return am.getType();
    }

    protected Class<?> _rawSerializationType(Annotated a) {
        return a.getRawType();
    }

    protected JavaType _fullSerializationType(AnnotatedMember am) {
        return am.getType();
    }

    protected Converter<Object, Object> _converter(XmlAdapter<?, ?> adapter, boolean forSerialization) {
        TypeFactory tf = this.getTypeFactory();
        JavaType adapterType = tf.constructType(adapter.getClass());
        JavaType[] pt = tf.findTypeParameters(adapterType, XmlAdapter.class);
        if (forSerialization) {
            return new AdapterConverter(adapter, pt[1], pt[0], forSerialization);
        }
        return new AdapterConverter(adapter, pt[0], pt[1], forSerialization);
    }

    protected Class<?> _getTypeFromXmlElement(Annotated a) {
        XmlElement annotation = this.findAnnotation(XmlElement.class, a, false, false, false);
        if (annotation != null) {
            if (a.getAnnotation(XmlJavaTypeAdapter.class) != null) {
                return null;
            }
            Class type = annotation.type();
            if (type != XmlElement.DEFAULT.class) {
                return type;
            }
        }
        return null;
    }
}

