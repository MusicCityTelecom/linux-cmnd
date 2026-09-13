/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClientSettings
 *  com.mongodb.MongoClientSettings$Builder
 *  com.mongodb.MongoDriverInformation
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;

public abstract class MongoClientFactorySupport<T> {
    private final List<MongoClientSettingsBuilderCustomizer> builderCustomizers;
    private final BiFunction<MongoClientSettings, MongoDriverInformation, T> clientCreator;

    protected MongoClientFactorySupport(List<MongoClientSettingsBuilderCustomizer> builderCustomizers, BiFunction<MongoClientSettings, MongoDriverInformation, T> clientCreator) {
        this.builderCustomizers = builderCustomizers != null ? builderCustomizers : Collections.emptyList();
        this.clientCreator = clientCreator;
    }

    public T createMongoClient(MongoClientSettings settings) {
        MongoClientSettings.Builder targetSettings = MongoClientSettings.builder((MongoClientSettings)settings);
        this.customize(targetSettings);
        return this.clientCreator.apply(targetSettings.build(), this.driverInformation());
    }

    private void customize(MongoClientSettings.Builder builder) {
        for (MongoClientSettingsBuilderCustomizer customizer : this.builderCustomizers) {
            customizer.customize(builder);
        }
    }

    private MongoDriverInformation driverInformation() {
        return MongoDriverInformation.builder((MongoDriverInformation)MongoDriverInformation.builder().build()).driverName("spring-boot").build();
    }
}

