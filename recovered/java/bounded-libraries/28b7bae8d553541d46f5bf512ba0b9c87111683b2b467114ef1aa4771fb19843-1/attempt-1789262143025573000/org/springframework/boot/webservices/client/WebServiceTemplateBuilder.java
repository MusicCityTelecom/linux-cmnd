/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeanUtils
 *  org.springframework.oxm.Marshaller
 *  org.springframework.oxm.Unmarshaller
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.ws.WebServiceMessageFactory
 *  org.springframework.ws.client.core.FaultMessageResolver
 *  org.springframework.ws.client.core.WebServiceTemplate
 *  org.springframework.ws.client.support.destination.DestinationProvider
 *  org.springframework.ws.client.support.interceptor.ClientInterceptor
 *  org.springframework.ws.transport.WebServiceMessageSender
 */
package org.springframework.boot.webservices.client;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.transform.TransformerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.webservices.client.HttpWebServiceMessageSenderBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateCustomizer;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.FaultMessageResolver;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.destination.DestinationProvider;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.transport.WebServiceMessageSender;

public class WebServiceTemplateBuilder {
    private final boolean detectHttpMessageSender;
    private final Set<ClientInterceptor> interceptors;
    private final Set<WebServiceTemplateCustomizer> internalCustomizers;
    private final Set<WebServiceTemplateCustomizer> customizers;
    private final WebServiceMessageSenders messageSenders;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    private final DestinationProvider destinationProvider;
    private final Class<? extends TransformerFactory> transformerFactoryClass;
    private final WebServiceMessageFactory messageFactory;

    public WebServiceTemplateBuilder(WebServiceTemplateCustomizer ... customizers) {
        this.detectHttpMessageSender = true;
        this.interceptors = null;
        this.internalCustomizers = null;
        this.customizers = Collections.unmodifiableSet(new LinkedHashSet<WebServiceTemplateCustomizer>(Arrays.asList(customizers)));
        this.messageSenders = new WebServiceMessageSenders();
        this.marshaller = null;
        this.unmarshaller = null;
        this.destinationProvider = null;
        this.transformerFactoryClass = null;
        this.messageFactory = null;
    }

    private WebServiceTemplateBuilder(boolean detectHttpMessageSender, Set<ClientInterceptor> interceptors, Set<WebServiceTemplateCustomizer> internalCustomizers, Set<WebServiceTemplateCustomizer> customizers, WebServiceMessageSenders messageSenders, Marshaller marshaller, Unmarshaller unmarshaller, DestinationProvider destinationProvider, Class<? extends TransformerFactory> transformerFactoryClass, WebServiceMessageFactory messageFactory) {
        this.detectHttpMessageSender = detectHttpMessageSender;
        this.interceptors = interceptors;
        this.internalCustomizers = internalCustomizers;
        this.customizers = customizers;
        this.messageSenders = messageSenders;
        this.marshaller = marshaller;
        this.unmarshaller = unmarshaller;
        this.destinationProvider = destinationProvider;
        this.transformerFactoryClass = transformerFactoryClass;
        this.messageFactory = messageFactory;
    }

    public WebServiceTemplateBuilder detectHttpMessageSender(boolean detectHttpMessageSender) {
        return new WebServiceTemplateBuilder(detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder messageSenders(WebServiceMessageSender ... messageSenders) {
        Assert.notNull((Object)messageSenders, (String)"MessageSenders must not be null");
        return this.messageSenders(Arrays.asList(messageSenders));
    }

    public WebServiceTemplateBuilder messageSenders(Collection<? extends WebServiceMessageSender> messageSenders) {
        Assert.notNull(messageSenders, (String)"MessageSenders must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders.set(messageSenders), this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder additionalMessageSenders(WebServiceMessageSender ... messageSenders) {
        Assert.notNull((Object)messageSenders, (String)"MessageSenders must not be null");
        return this.additionalMessageSenders(Arrays.asList(messageSenders));
    }

    public WebServiceTemplateBuilder additionalMessageSenders(Collection<? extends WebServiceMessageSender> messageSenders) {
        Assert.notNull(messageSenders, (String)"MessageSenders must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders.add(messageSenders), this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder interceptors(ClientInterceptor ... interceptors) {
        Assert.notNull((Object)interceptors, (String)"Interceptors must not be null");
        return this.interceptors(Arrays.asList(interceptors));
    }

    public WebServiceTemplateBuilder interceptors(Collection<? extends ClientInterceptor> interceptors) {
        Assert.notNull(interceptors, (String)"Interceptors must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, WebServiceTemplateBuilder.append(Collections.emptySet(), interceptors), this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder additionalInterceptors(ClientInterceptor ... interceptors) {
        Assert.notNull((Object)interceptors, (String)"Interceptors must not be null");
        return this.additionalInterceptors(Arrays.asList(interceptors));
    }

    public WebServiceTemplateBuilder additionalInterceptors(Collection<? extends ClientInterceptor> interceptors) {
        Assert.notNull(interceptors, (String)"Interceptors must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, WebServiceTemplateBuilder.append(this.interceptors, interceptors), this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder customizers(WebServiceTemplateCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.customizers(Arrays.asList(customizers));
    }

    public WebServiceTemplateBuilder customizers(Collection<? extends WebServiceTemplateCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, WebServiceTemplateBuilder.append(Collections.emptySet(), customizers), this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder additionalCustomizers(WebServiceTemplateCustomizer ... customizers) {
        Assert.notNull((Object)customizers, (String)"Customizers must not be null");
        return this.additionalCustomizers(Arrays.asList(customizers));
    }

    public WebServiceTemplateBuilder additionalCustomizers(Collection<? extends WebServiceTemplateCustomizer> customizers) {
        Assert.notNull(customizers, (String)"Customizers must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, WebServiceTemplateBuilder.append(this.customizers, customizers), this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setCheckConnectionForFault(boolean checkConnectionForFault) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.append(this.internalCustomizers, new CheckConnectionFaultCustomizer(checkConnectionForFault)), this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setCheckConnectionForError(boolean checkConnectionForError) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.append(this.internalCustomizers, new CheckConnectionForErrorCustomizer(checkConnectionForError)), this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setWebServiceMessageFactory(WebServiceMessageFactory messageFactory) {
        Assert.notNull((Object)messageFactory, (String)"MessageFactory must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, messageFactory);
    }

    public WebServiceTemplateBuilder setUnmarshaller(Unmarshaller unmarshaller) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setMarshaller(Marshaller marshaller) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setFaultMessageResolver(FaultMessageResolver faultMessageResolver) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.append(this.internalCustomizers, new FaultMessageResolverCustomizer(faultMessageResolver)), this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setTransformerFactoryClass(Class<? extends TransformerFactory> transformerFactoryClass) {
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, this.destinationProvider, transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplateBuilder setDefaultUri(String defaultUri) {
        Assert.hasText((String)defaultUri, (String)"DefaultUri must not be empty");
        return this.setDestinationProvider(() -> URI.create(defaultUri));
    }

    public WebServiceTemplateBuilder setDestinationProvider(DestinationProvider destinationProvider) {
        Assert.notNull((Object)destinationProvider, (String)"DestinationProvider must not be null");
        return new WebServiceTemplateBuilder(this.detectHttpMessageSender, this.interceptors, this.internalCustomizers, this.customizers, this.messageSenders, this.marshaller, this.unmarshaller, destinationProvider, this.transformerFactoryClass, this.messageFactory);
    }

    public WebServiceTemplate build() {
        return this.configure(new WebServiceTemplate());
    }

    public <T extends WebServiceTemplate> T build(Class<T> webServiceTemplateClass) {
        Assert.notNull(webServiceTemplateClass, (String)"WebServiceTemplateClass must not be null");
        return (T)this.configure((WebServiceTemplate)BeanUtils.instantiateClass(webServiceTemplateClass));
    }

    public <T extends WebServiceTemplate> T configure(T webServiceTemplate) {
        Assert.notNull(webServiceTemplate, (String)"WebServiceTemplate must not be null");
        this.configureMessageSenders(webServiceTemplate);
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        this.applyCustomizers(webServiceTemplate, this.internalCustomizers);
        map.from(this.marshaller).to(arg_0 -> webServiceTemplate.setMarshaller(arg_0));
        map.from(this.unmarshaller).to(arg_0 -> webServiceTemplate.setUnmarshaller(arg_0));
        map.from(this.destinationProvider).to(arg_0 -> webServiceTemplate.setDestinationProvider(arg_0));
        map.from(this.transformerFactoryClass).to(arg_0 -> webServiceTemplate.setTransformerFactoryClass(arg_0));
        map.from(this.messageFactory).to(arg_0 -> webServiceTemplate.setMessageFactory(arg_0));
        if (!CollectionUtils.isEmpty(this.interceptors)) {
            LinkedHashSet<ClientInterceptor> merged = new LinkedHashSet<ClientInterceptor>(this.interceptors);
            if (webServiceTemplate.getInterceptors() != null) {
                merged.addAll(Arrays.asList(webServiceTemplate.getInterceptors()));
            }
            webServiceTemplate.setInterceptors(merged.toArray(new ClientInterceptor[0]));
        }
        this.applyCustomizers(webServiceTemplate, this.customizers);
        return webServiceTemplate;
    }

    private void applyCustomizers(WebServiceTemplate webServiceTemplate, Set<WebServiceTemplateCustomizer> customizers) {
        if (!CollectionUtils.isEmpty(customizers)) {
            for (WebServiceTemplateCustomizer customizer : customizers) {
                customizer.customize(webServiceTemplate);
            }
        }
    }

    private <T extends WebServiceTemplate> void configureMessageSenders(T webServiceTemplate) {
        if (this.messageSenders.isOnlyAdditional() && this.detectHttpMessageSender) {
            Set<WebServiceMessageSender> merged = this.append(this.messageSenders.getMessageSenders(), new HttpWebServiceMessageSenderBuilder().build());
            webServiceTemplate.setMessageSenders(merged.toArray(new WebServiceMessageSender[0]));
        } else if (!CollectionUtils.isEmpty(this.messageSenders.getMessageSenders())) {
            webServiceTemplate.setMessageSenders(this.messageSenders.getMessageSenders().toArray(new WebServiceMessageSender[0]));
        }
    }

    private <T> Set<T> append(Set<T> set, T addition) {
        return WebServiceTemplateBuilder.append(set, Collections.singleton(addition));
    }

    private static <T> Set<T> append(Set<T> set, Collection<? extends T> additions) {
        LinkedHashSet<T> result = new LinkedHashSet<T>(set != null ? set : Collections.emptySet());
        result.addAll(additions != null ? additions : Collections.emptyList());
        return Collections.unmodifiableSet(result);
    }

    private static final class FaultMessageResolverCustomizer
    implements WebServiceTemplateCustomizer {
        private final FaultMessageResolver faultMessageResolver;

        private FaultMessageResolverCustomizer(FaultMessageResolver faultMessageResolver) {
            this.faultMessageResolver = faultMessageResolver;
        }

        @Override
        public void customize(WebServiceTemplate webServiceTemplate) {
            webServiceTemplate.setFaultMessageResolver(this.faultMessageResolver);
        }
    }

    private static final class CheckConnectionForErrorCustomizer
    implements WebServiceTemplateCustomizer {
        private final boolean checkConnectionForError;

        private CheckConnectionForErrorCustomizer(boolean checkConnectionForError) {
            this.checkConnectionForError = checkConnectionForError;
        }

        @Override
        public void customize(WebServiceTemplate webServiceTemplate) {
            webServiceTemplate.setCheckConnectionForError(this.checkConnectionForError);
        }
    }

    private static final class CheckConnectionFaultCustomizer
    implements WebServiceTemplateCustomizer {
        private final boolean checkConnectionFault;

        private CheckConnectionFaultCustomizer(boolean checkConnectionFault) {
            this.checkConnectionFault = checkConnectionFault;
        }

        @Override
        public void customize(WebServiceTemplate webServiceTemplate) {
            webServiceTemplate.setCheckConnectionForFault(this.checkConnectionFault);
        }
    }

    private static final class WebServiceMessageSenders {
        private final boolean onlyAdditional;
        private Set<WebServiceMessageSender> messageSenders;

        private WebServiceMessageSenders() {
            this(true, Collections.emptySet());
        }

        private WebServiceMessageSenders(boolean onlyAdditional, Set<WebServiceMessageSender> messageSenders) {
            this.onlyAdditional = onlyAdditional;
            this.messageSenders = messageSenders;
        }

        boolean isOnlyAdditional() {
            return this.onlyAdditional;
        }

        Set<WebServiceMessageSender> getMessageSenders() {
            return this.messageSenders;
        }

        WebServiceMessageSenders set(Collection<? extends WebServiceMessageSender> messageSenders) {
            return new WebServiceMessageSenders(false, new LinkedHashSet<WebServiceMessageSender>(messageSenders));
        }

        WebServiceMessageSenders add(Collection<? extends WebServiceMessageSender> messageSenders) {
            return new WebServiceMessageSenders(this.onlyAdditional, WebServiceTemplateBuilder.append(this.messageSenders, messageSenders));
        }
    }
}

