/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import org.springframework.boot.configurationprocessor.MetadataCollector;
import org.springframework.boot.configurationprocessor.MetadataGenerationEnvironment;
import org.springframework.boot.configurationprocessor.MetadataStore;
import org.springframework.boot.configurationprocessor.PropertyDescriptorResolver;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.InvalidConfigurationMetadataException;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

@SupportedAnnotationTypes(value={"org.springframework.boot.autoconfigure.AutoConfiguration", "org.springframework.boot.context.properties.ConfigurationProperties", "org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint", "org.springframework.boot.actuate.endpoint.annotation.Endpoint", "org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpoint", "org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint", "org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpoint", "org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint", "org.springframework.context.annotation.Configuration"})
public class ConfigurationMetadataAnnotationProcessor
extends AbstractProcessor {
    static final String ADDITIONAL_METADATA_LOCATIONS_OPTION = "org.springframework.boot.configurationprocessor.additionalMetadataLocations";
    static final String CONFIGURATION_PROPERTIES_ANNOTATION = "org.springframework.boot.context.properties.ConfigurationProperties";
    static final String NESTED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.context.properties.NestedConfigurationProperty";
    static final String DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION = "org.springframework.boot.context.properties.DeprecatedConfigurationProperty";
    static final String CONSTRUCTOR_BINDING_ANNOTATION = "org.springframework.boot.context.properties.ConstructorBinding";
    static final String DEFAULT_VALUE_ANNOTATION = "org.springframework.boot.context.properties.bind.DefaultValue";
    static final String CONTROLLER_ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint";
    static final String ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.annotation.Endpoint";
    static final String JMX_ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.jmx.annotation.JmxEndpoint";
    static final String REST_CONTROLLER_ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint";
    static final String SERVLET_ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpoint";
    static final String WEB_ENDPOINT_ANNOTATION = "org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint";
    static final String READ_OPERATION_ANNOTATION = "org.springframework.boot.actuate.endpoint.annotation.ReadOperation";
    static final String NAME_ANNOTATION = "org.springframework.boot.context.properties.bind.Name";
    static final String AUTO_CONFIGURATION_ANNOTATION = "org.springframework.boot.autoconfigure.AutoConfiguration";
    private static final Set<String> SUPPORTED_OPTIONS = Collections.unmodifiableSet(Collections.singleton("org.springframework.boot.configurationprocessor.additionalMetadataLocations"));
    private MetadataStore metadataStore;
    private MetadataCollector metadataCollector;
    private MetadataGenerationEnvironment metadataEnv;

    protected String configurationPropertiesAnnotation() {
        return CONFIGURATION_PROPERTIES_ANNOTATION;
    }

    protected String nestedConfigurationPropertyAnnotation() {
        return NESTED_CONFIGURATION_PROPERTY_ANNOTATION;
    }

    protected String deprecatedConfigurationPropertyAnnotation() {
        return DEPRECATED_CONFIGURATION_PROPERTY_ANNOTATION;
    }

    protected String constructorBindingAnnotation() {
        return CONSTRUCTOR_BINDING_ANNOTATION;
    }

    protected String defaultValueAnnotation() {
        return DEFAULT_VALUE_ANNOTATION;
    }

    protected Set<String> endpointAnnotations() {
        return new HashSet<String>(Arrays.asList(CONTROLLER_ENDPOINT_ANNOTATION, ENDPOINT_ANNOTATION, JMX_ENDPOINT_ANNOTATION, REST_CONTROLLER_ENDPOINT_ANNOTATION, SERVLET_ENDPOINT_ANNOTATION, WEB_ENDPOINT_ANNOTATION));
    }

    protected String readOperationAnnotation() {
        return READ_OPERATION_ANNOTATION;
    }

    protected String nameAnnotation() {
        return NAME_ANNOTATION;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedOptions() {
        return SUPPORTED_OPTIONS;
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        this.metadataStore = new MetadataStore(env);
        this.metadataCollector = new MetadataCollector(env, this.metadataStore.readMetadata());
        this.metadataEnv = new MetadataGenerationEnvironment(env, this.configurationPropertiesAnnotation(), this.nestedConfigurationPropertyAnnotation(), this.deprecatedConfigurationPropertyAnnotation(), this.constructorBindingAnnotation(), this.defaultValueAnnotation(), this.endpointAnnotations(), this.readOperationAnnotation(), this.nameAnnotation());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<TypeElement> endpointTypes;
        this.metadataCollector.processing(roundEnv);
        TypeElement annotationType = this.metadataEnv.getConfigurationPropertiesAnnotationElement();
        if (annotationType != null) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotationType)) {
                this.processElement(element);
            }
        }
        if (!(endpointTypes = this.metadataEnv.getEndpointAnnotationElements()).isEmpty()) {
            for (TypeElement endpointType : endpointTypes) {
                this.getElementsAnnotatedOrMetaAnnotatedWith(roundEnv, endpointType).forEach(this::processEndpoint);
            }
        }
        if (roundEnv.processingOver()) {
            try {
                this.writeMetadata();
            }
            catch (Exception exception) {
                throw new IllegalStateException("Failed to write metadata", exception);
            }
        }
        return false;
    }

    private Map<Element, List<Element>> getElementsAnnotatedOrMetaAnnotatedWith(RoundEnvironment roundEnv, TypeElement annotation) {
        LinkedHashMap<Element, List<Element>> result = new LinkedHashMap<Element, List<Element>>();
        for (Element element : roundEnv.getRootElements()) {
            List<Element> annotations = this.metadataEnv.getElementsAnnotatedOrMetaAnnotatedWith(element, annotation);
            if (annotations.isEmpty()) continue;
            result.put(element, annotations);
        }
        return result;
    }

    private void processElement(Element element) {
        try {
            AnnotationMirror annotation = this.metadataEnv.getConfigurationPropertiesAnnotation(element);
            if (annotation != null) {
                String prefix = this.getPrefix(annotation);
                if (element instanceof TypeElement) {
                    this.processAnnotatedTypeElement(prefix, (TypeElement)element, new Stack<TypeElement>());
                } else if (element instanceof ExecutableElement) {
                    this.processExecutableElement(prefix, (ExecutableElement)element, new Stack<TypeElement>());
                }
            }
        }
        catch (Exception ex) {
            throw new IllegalStateException("Error processing configuration meta-data on " + element, ex);
        }
    }

    private void processAnnotatedTypeElement(String prefix, TypeElement element, Stack<TypeElement> seen) {
        String type = this.metadataEnv.getTypeUtils().getQualifiedName(element);
        this.metadataCollector.add(ItemMetadata.newGroup(prefix, type, type, null));
        this.processTypeElement(prefix, element, null, seen);
    }

    private void processExecutableElement(String prefix, ExecutableElement element, Stack<TypeElement> seen) {
        Element returns;
        if (!element.getModifiers().contains((Object)Modifier.PRIVATE) && TypeKind.VOID != element.getReturnType().getKind() && (returns = this.processingEnv.getTypeUtils().asElement(element.getReturnType())) instanceof TypeElement) {
            ItemMetadata group = ItemMetadata.newGroup(prefix, this.metadataEnv.getTypeUtils().getQualifiedName(returns), this.metadataEnv.getTypeUtils().getQualifiedName(element.getEnclosingElement()), element.toString());
            if (this.metadataCollector.hasSimilarGroup(group)) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Duplicate @ConfigurationProperties definition for prefix '" + prefix + "'", element);
            } else {
                this.metadataCollector.add(group);
                this.processTypeElement(prefix, (TypeElement)returns, element, seen);
            }
        }
    }

    private void processTypeElement(String prefix, TypeElement element, ExecutableElement source, Stack<TypeElement> seen) {
        if (!seen.contains(element)) {
            seen.push(element);
            new PropertyDescriptorResolver(this.metadataEnv).resolve(element, source).forEach(descriptor -> {
                this.metadataCollector.add(descriptor.resolveItemMetadata(prefix, this.metadataEnv));
                if (descriptor.isNested(this.metadataEnv)) {
                    TypeElement nestedTypeElement = (TypeElement)this.metadataEnv.getTypeUtils().asElement(descriptor.getType());
                    String nestedPrefix = ConfigurationMetadata.nestedPrefix(prefix, descriptor.getName());
                    this.processTypeElement(nestedPrefix, nestedTypeElement, source, seen);
                }
            });
            seen.pop();
        }
    }

    private void processEndpoint(Element element, List<Element> annotations) {
        try {
            String annotationName = this.metadataEnv.getTypeUtils().getQualifiedName(annotations.get(0));
            AnnotationMirror annotation = this.metadataEnv.getAnnotation(element, annotationName);
            if (element instanceof TypeElement) {
                this.processEndpoint(annotation, (TypeElement)element);
            }
        }
        catch (Exception ex) {
            throw new IllegalStateException("Error processing configuration meta-data on " + element, ex);
        }
    }

    private void processEndpoint(AnnotationMirror annotation, TypeElement element) {
        Map<String, Object> elementValues = this.metadataEnv.getAnnotationElementValues(annotation);
        String endpointId = (String)elementValues.get("id");
        if (endpointId == null || endpointId.isEmpty()) {
            return;
        }
        String endpointKey = ItemMetadata.newItemMetadataPrefix("management.endpoint.", endpointId);
        Boolean enabledByDefault = (Boolean)elementValues.get("enableByDefault");
        String type = this.metadataEnv.getTypeUtils().getQualifiedName(element);
        this.metadataCollector.add(ItemMetadata.newGroup(endpointKey, type, type, null));
        this.metadataCollector.add(ItemMetadata.newProperty(endpointKey, "enabled", Boolean.class.getName(), type, null, String.format("Whether to enable the %s endpoint.", endpointId), enabledByDefault != null ? enabledByDefault : true, null));
        if (this.hasMainReadOperation(element)) {
            this.metadataCollector.add(ItemMetadata.newProperty(endpointKey, "cache.time-to-live", Duration.class.getName(), type, null, "Maximum time that a response can be cached.", "0ms", null));
        }
    }

    private boolean hasMainReadOperation(TypeElement element) {
        for (ExecutableElement method : ElementFilter.methodsIn(element.getEnclosedElements())) {
            if (this.metadataEnv.getReadOperationAnnotation(method) == null || TypeKind.VOID == method.getReturnType().getKind() || !this.hasNoOrOptionalParameters(method)) continue;
            return true;
        }
        return false;
    }

    private boolean hasNoOrOptionalParameters(ExecutableElement method) {
        for (VariableElement variableElement : method.getParameters()) {
            if (this.metadataEnv.hasNullableAnnotation(variableElement)) continue;
            return false;
        }
        return true;
    }

    private String getPrefix(AnnotationMirror annotation) {
        Map<String, Object> elementValues = this.metadataEnv.getAnnotationElementValues(annotation);
        Object prefix = elementValues.get("prefix");
        if (prefix != null && !"".equals(prefix)) {
            return (String)prefix;
        }
        Object value = elementValues.get("value");
        if (value != null && !"".equals(value)) {
            return (String)value;
        }
        return null;
    }

    protected ConfigurationMetadata writeMetadata() throws Exception {
        ConfigurationMetadata metadata = this.metadataCollector.getMetadata();
        if (!(metadata = this.mergeAdditionalMetadata(metadata)).getItems().isEmpty()) {
            this.metadataStore.writeMetadata(metadata);
            return metadata;
        }
        return null;
    }

    private ConfigurationMetadata mergeAdditionalMetadata(ConfigurationMetadata metadata) {
        try {
            ConfigurationMetadata merged = new ConfigurationMetadata(metadata);
            merged.merge(this.metadataStore.readAdditionalMetadata());
            return merged;
        }
        catch (FileNotFoundException merged) {
        }
        catch (InvalidConfigurationMetadataException ex) {
            this.log(ex.getKind(), ex.getMessage());
        }
        catch (Exception ex) {
            this.logWarning("Unable to merge additional metadata");
            this.logWarning(this.getStackTrace(ex));
        }
        return metadata;
    }

    private String getStackTrace(Exception ex) {
        StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();
    }

    private void logWarning(String msg) {
        this.log(Diagnostic.Kind.WARNING, msg);
    }

    private void log(Diagnostic.Kind kind, String msg) {
        this.processingEnv.getMessager().printMessage(kind, msg);
    }
}

