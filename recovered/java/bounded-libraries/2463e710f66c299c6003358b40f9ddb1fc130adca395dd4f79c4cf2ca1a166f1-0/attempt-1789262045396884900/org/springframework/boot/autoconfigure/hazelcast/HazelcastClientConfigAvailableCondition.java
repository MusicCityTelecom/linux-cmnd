/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.client.config.ClientConfigRecognizer
 *  com.hazelcast.config.ConfigStream
 *  org.springframework.context.annotation.ConditionContext
 *  org.springframework.core.io.Resource
 *  org.springframework.core.type.AnnotatedTypeMetadata
 */
package org.springframework.boot.autoconfigure.hazelcast;

import com.hazelcast.client.config.ClientConfigRecognizer;
import com.hazelcast.config.ConfigStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastConfigResourceCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;

class HazelcastClientConfigAvailableCondition
extends HazelcastConfigResourceCondition {
    HazelcastClientConfigAvailableCondition() {
        super("hazelcast.client.config", new String[]{"file:./hazelcast-client.xml", "classpath:/hazelcast-client.xml", "file:./hazelcast-client.yaml", "classpath:/hazelcast-client.yaml"});
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        if (context.getEnvironment().containsProperty("spring.hazelcast.config")) {
            ConditionOutcome configValidationOutcome = Hazelcast4ClientValidation.clientConfigOutcome(context, "spring.hazelcast.config", this.startConditionMessage());
            return configValidationOutcome != null ? configValidationOutcome : ConditionOutcome.match(this.startConditionMessage().foundExactly("property spring.hazelcast.config"));
        }
        return this.getResourceOutcome(context, metadata);
    }

    static class Hazelcast4ClientValidation {
        Hazelcast4ClientValidation() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static ConditionOutcome clientConfigOutcome(ConditionContext context, String propertyName, ConditionMessage.Builder builder) {
            String resourcePath = context.getEnvironment().getProperty(propertyName);
            Resource resource = context.getResourceLoader().getResource(resourcePath);
            if (!resource.exists()) {
                return ConditionOutcome.noMatch(builder.because("Hazelcast configuration does not exist"));
            }
            try (InputStream in = resource.getInputStream();){
                boolean clientConfig = new ClientConfigRecognizer().isRecognized(new ConfigStream(in));
                ConditionOutcome conditionOutcome = new ConditionOutcome(clientConfig, Hazelcast4ClientValidation.existingConfigurationOutcome(resource, clientConfig));
                return conditionOutcome;
            }
            catch (Throwable ex) {
                return null;
            }
        }

        private static String existingConfigurationOutcome(Resource resource, boolean client) throws IOException {
            URL location = resource.getURL();
            return client ? "Hazelcast client configuration detected at '" + location + "'" : "Hazelcast server configuration detected  at '" + location + "'";
        }
    }
}

