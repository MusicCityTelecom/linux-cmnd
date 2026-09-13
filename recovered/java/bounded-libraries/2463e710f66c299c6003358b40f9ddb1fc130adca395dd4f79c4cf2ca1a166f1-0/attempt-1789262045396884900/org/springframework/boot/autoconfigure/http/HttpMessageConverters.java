/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter
 *  org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter
 *  org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
 *  org.springframework.util.ClassUtils
 *  org.springframework.web.client.RestTemplate
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
 */
package org.springframework.boot.autoconfigure.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class HttpMessageConverters
implements Iterable<HttpMessageConverter<?>> {
    private static final List<Class<?>> NON_REPLACING_CONVERTERS;
    private static final Map<Class<?>, Class<?>> EQUIVALENT_CONVERTERS;
    private final List<HttpMessageConverter<?>> converters;

    public HttpMessageConverters(HttpMessageConverter<?> ... additionalConverters) {
        this(Arrays.asList(additionalConverters));
    }

    public HttpMessageConverters(Collection<HttpMessageConverter<?>> additionalConverters) {
        this(true, additionalConverters);
    }

    public HttpMessageConverters(boolean addDefaultConverters, Collection<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> combined = this.getCombinedConverters(converters, addDefaultConverters ? this.getDefaultConverters() : Collections.emptyList());
        combined = this.postProcessConverters(combined);
        this.converters = Collections.unmodifiableList(combined);
    }

    private List<HttpMessageConverter<?>> getCombinedConverters(Collection<HttpMessageConverter<?>> converters, List<HttpMessageConverter<?>> defaultConverters) {
        ArrayList combined = new ArrayList();
        ArrayList processing = new ArrayList(converters);
        for (HttpMessageConverter<?> defaultConverter : defaultConverters) {
            Iterator iterator = processing.iterator();
            while (iterator.hasNext()) {
                HttpMessageConverter candidate = (HttpMessageConverter)iterator.next();
                if (!this.isReplacement(defaultConverter, candidate)) continue;
                combined.add(candidate);
                iterator.remove();
            }
            combined.add(defaultConverter);
            if (!(defaultConverter instanceof AllEncompassingFormHttpMessageConverter)) continue;
            this.configurePartConverters((AllEncompassingFormHttpMessageConverter)defaultConverter, converters);
        }
        combined.addAll(0, processing);
        return combined;
    }

    private boolean isReplacement(HttpMessageConverter<?> defaultConverter, HttpMessageConverter<?> candidate) {
        for (Class<?> nonReplacingConverter : NON_REPLACING_CONVERTERS) {
            if (!nonReplacingConverter.isInstance(candidate)) continue;
            return false;
        }
        Class<?> converterClass = defaultConverter.getClass();
        if (ClassUtils.isAssignableValue(converterClass, candidate)) {
            return true;
        }
        Class<?> equivalentClass = EQUIVALENT_CONVERTERS.get(converterClass);
        return equivalentClass != null && ClassUtils.isAssignableValue(equivalentClass, candidate);
    }

    private void configurePartConverters(AllEncompassingFormHttpMessageConverter formConverter, Collection<HttpMessageConverter<?>> converters) {
        List partConverters = formConverter.getPartConverters();
        List<HttpMessageConverter<?>> combinedConverters = this.getCombinedConverters(converters, partConverters);
        combinedConverters = this.postProcessPartConverters(combinedConverters);
        formConverter.setPartConverters(combinedConverters);
    }

    protected List<HttpMessageConverter<?>> postProcessConverters(List<HttpMessageConverter<?>> converters) {
        return converters;
    }

    protected List<HttpMessageConverter<?>> postProcessPartConverters(List<HttpMessageConverter<?>> converters) {
        return converters;
    }

    private List<HttpMessageConverter<?>> getDefaultConverters() {
        ArrayList converters = new ArrayList();
        if (ClassUtils.isPresent((String)"org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport", null)) {
            converters.addAll(new WebMvcConfigurationSupport(){

                public List<HttpMessageConverter<?>> defaultMessageConverters() {
                    return super.getMessageConverters();
                }
            }.defaultMessageConverters());
        } else {
            converters.addAll(new RestTemplate().getMessageConverters());
        }
        this.reorderXmlConvertersToEnd(converters);
        return converters;
    }

    private void reorderXmlConvertersToEnd(List<HttpMessageConverter<?>> converters) {
        ArrayList xml = new ArrayList();
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (!(converter instanceof AbstractXmlHttpMessageConverter) && !(converter instanceof MappingJackson2XmlHttpMessageConverter)) continue;
            xml.add(converter);
            iterator.remove();
        }
        converters.addAll(xml);
    }

    @Override
    public Iterator<HttpMessageConverter<?>> iterator() {
        return this.getConverters().iterator();
    }

    public List<HttpMessageConverter<?>> getConverters() {
        return this.converters;
    }

    private static void addClassIfExists(List<Class<?>> list, String className) {
        try {
            list.add(Class.forName(className));
        }
        catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            // empty catch block
        }
    }

    private static void putIfExists(Map<Class<?>, Class<?>> map, String keyClassName, String valueClassName) {
        try {
            map.put(Class.forName(keyClassName), Class.forName(valueClassName));
        }
        catch (ClassNotFoundException | NoClassDefFoundError throwable) {
            // empty catch block
        }
    }

    static {
        ArrayList nonReplacingConverters = new ArrayList();
        HttpMessageConverters.addClassIfExists(nonReplacingConverters, "org.springframework.hateoas.server.mvc.TypeConstrainedMappingJackson2HttpMessageConverter");
        NON_REPLACING_CONVERTERS = Collections.unmodifiableList(nonReplacingConverters);
        HashMap equivalentConverters = new HashMap();
        HttpMessageConverters.putIfExists(equivalentConverters, "org.springframework.http.converter.json.MappingJackson2HttpMessageConverter", "org.springframework.http.converter.json.GsonHttpMessageConverter");
        EQUIVALENT_CONVERTERS = Collections.unmodifiableMap(equivalentConverters);
    }
}

