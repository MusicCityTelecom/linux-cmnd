/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Gauge
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.binder.MeterBinder
 *  org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider
 *  org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata
 *  org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider
 *  org.springframework.util.Assert
 *  org.springframework.util.ConcurrentReferenceHashMap
 */
package org.springframework.boot.actuate.metrics.jdbc;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;

public class DataSourcePoolMetrics
implements MeterBinder {
    private final DataSource dataSource;
    private final CachingDataSourcePoolMetadataProvider metadataProvider;
    private final Iterable<Tag> tags;

    public DataSourcePoolMetrics(DataSource dataSource, Collection<DataSourcePoolMetadataProvider> metadataProviders, String dataSourceName, Iterable<Tag> tags) {
        this(dataSource, (DataSourcePoolMetadataProvider)new CompositeDataSourcePoolMetadataProvider(metadataProviders), dataSourceName, tags);
    }

    public DataSourcePoolMetrics(DataSource dataSource, DataSourcePoolMetadataProvider metadataProvider, String name, Iterable<Tag> tags) {
        Assert.notNull((Object)dataSource, (String)"DataSource must not be null");
        Assert.notNull((Object)metadataProvider, (String)"MetadataProvider must not be null");
        this.dataSource = dataSource;
        this.metadataProvider = new CachingDataSourcePoolMetadataProvider(metadataProvider);
        this.tags = Tags.concat(tags, (String[])new String[]{"name", name});
    }

    public void bindTo(MeterRegistry registry) {
        if (this.metadataProvider.getDataSourcePoolMetadata(this.dataSource) != null) {
            this.bindPoolMetadata(registry, "active", "Current number of active connections that have been allocated from the data source.", DataSourcePoolMetadata::getActive);
            this.bindPoolMetadata(registry, "idle", "Number of established but idle connections.", DataSourcePoolMetadata::getIdle);
            this.bindPoolMetadata(registry, "max", "Maximum number of active connections that can be allocated at the same time.", DataSourcePoolMetadata::getMax);
            this.bindPoolMetadata(registry, "min", "Minimum number of idle connections in the pool.", DataSourcePoolMetadata::getMin);
        }
    }

    private <N extends Number> void bindPoolMetadata(MeterRegistry registry, String metricName, String description, Function<DataSourcePoolMetadata, N> function) {
        this.bindDataSource(registry, metricName, description, this.metadataProvider.getValueFunction(function));
    }

    private <N extends Number> void bindDataSource(MeterRegistry registry, String metricName, String description, Function<DataSource, N> function) {
        if (function.apply(this.dataSource) != null) {
            Gauge.builder((String)("jdbc.connections." + metricName), (Object)this.dataSource, m -> ((Number)function.apply((DataSource)m)).doubleValue()).tags(this.tags).description(description).register(registry);
        }
    }

    private static class CachingDataSourcePoolMetadataProvider
    implements DataSourcePoolMetadataProvider {
        private static final Map<DataSource, DataSourcePoolMetadata> cache = new ConcurrentReferenceHashMap();
        private final DataSourcePoolMetadataProvider metadataProvider;

        CachingDataSourcePoolMetadataProvider(DataSourcePoolMetadataProvider metadataProvider) {
            this.metadataProvider = metadataProvider;
        }

        <N extends Number> Function<DataSource, N> getValueFunction(Function<DataSourcePoolMetadata, N> function) {
            return dataSource -> (Number)function.apply(this.getDataSourcePoolMetadata((DataSource)dataSource));
        }

        public DataSourcePoolMetadata getDataSourcePoolMetadata(DataSource dataSource) {
            DataSourcePoolMetadata metadata = cache.get(dataSource);
            if (metadata == null) {
                metadata = this.metadataProvider.getDataSourcePoolMetadata(dataSource);
                cache.put(dataSource, metadata);
            }
            return metadata;
        }
    }
}

