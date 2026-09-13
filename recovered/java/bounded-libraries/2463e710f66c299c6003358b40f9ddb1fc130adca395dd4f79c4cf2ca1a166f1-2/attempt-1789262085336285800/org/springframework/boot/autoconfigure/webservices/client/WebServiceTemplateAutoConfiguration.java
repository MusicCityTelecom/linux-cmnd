/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.webservices.client.WebServiceTemplateBuilder
 *  org.springframework.boot.webservices.client.WebServiceTemplateCustomizer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.oxm.Marshaller
 *  org.springframework.oxm.Unmarshaller
 *  org.springframework.ws.client.core.WebServiceTemplate
 */
package org.springframework.boot.autoconfigure.webservices.client;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.boot.webservices.client.WebServiceTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@AutoConfiguration
@ConditionalOnClass(value={WebServiceTemplate.class, Unmarshaller.class, Marshaller.class})
public class WebServiceTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WebServiceTemplateBuilder webServiceTemplateBuilder(ObjectProvider<WebServiceTemplateCustomizer> webServiceTemplateCustomizers) {
        WebServiceTemplateBuilder builder = new WebServiceTemplateBuilder(new WebServiceTemplateCustomizer[0]);
        List customizers = webServiceTemplateCustomizers.orderedStream().collect(Collectors.toList());
        if (!customizers.isEmpty()) {
            builder = builder.customizers(customizers);
        }
        return builder;
    }
}

