/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository
 *  org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder
 *  org.springframework.boot.context.event.ApplicationFailedEvent
 *  org.springframework.boot.context.event.ApplicationPreparedEvent
 *  org.springframework.boot.context.event.ApplicationReadyEvent
 *  org.springframework.boot.context.event.SpringApplicationEvent
 *  org.springframework.context.ApplicationListener
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 */
package org.springframework.boot.context.properties.migrator;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepository;
import org.springframework.boot.configurationmetadata.ConfigurationMetadataRepositoryJsonBuilder;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.boot.context.properties.migrator.PropertiesMigrationReport;
import org.springframework.boot.context.properties.migrator.PropertiesMigrationReporter;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PropertiesMigrationListener
implements ApplicationListener<SpringApplicationEvent> {
    private static final Log logger = LogFactory.getLog(PropertiesMigrationListener.class);
    private PropertiesMigrationReport report;
    private boolean reported;

    public void onApplicationEvent(SpringApplicationEvent event) {
        if (event instanceof ApplicationPreparedEvent) {
            this.onApplicationPreparedEvent((ApplicationPreparedEvent)event);
        }
        if (event instanceof ApplicationReadyEvent || event instanceof ApplicationFailedEvent) {
            this.logLegacyPropertiesReport();
        }
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        ConfigurationMetadataRepository repository = this.loadRepository();
        PropertiesMigrationReporter reporter = new PropertiesMigrationReporter(repository, event.getApplicationContext().getEnvironment());
        this.report = reporter.getReport();
    }

    private ConfigurationMetadataRepository loadRepository() {
        try {
            return this.loadRepository(ConfigurationMetadataRepositoryJsonBuilder.create());
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load metadata", ex);
        }
    }

    private ConfigurationMetadataRepository loadRepository(ConfigurationMetadataRepositoryJsonBuilder builder) throws IOException {
        Resource[] resources;
        for (Resource resource : resources = new PathMatchingResourcePatternResolver().getResources("classpath*:/META-INF/spring-configuration-metadata.json")) {
            try (InputStream inputStream = resource.getInputStream();){
                builder.withJsonResource(inputStream);
            }
        }
        return builder.build();
    }

    private void logLegacyPropertiesReport() {
        String errorReport;
        if (this.report == null || this.reported) {
            return;
        }
        String warningReport = this.report.getWarningReport();
        if (warningReport != null) {
            logger.warn((Object)warningReport);
        }
        if ((errorReport = this.report.getErrorReport()) != null) {
            logger.error((Object)errorReport);
        }
        this.reported = true;
    }
}

