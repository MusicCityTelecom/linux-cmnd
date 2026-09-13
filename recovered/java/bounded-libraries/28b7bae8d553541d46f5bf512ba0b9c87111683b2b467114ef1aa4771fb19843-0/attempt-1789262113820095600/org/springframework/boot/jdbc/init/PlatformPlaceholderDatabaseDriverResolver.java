/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jdbc.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class PlatformPlaceholderDatabaseDriverResolver {
    private final String placeholder;
    private final Map<DatabaseDriver, String> driverMappings;

    public PlatformPlaceholderDatabaseDriverResolver() {
        this("@@platform@@");
    }

    public PlatformPlaceholderDatabaseDriverResolver(String placeholder) {
        this(placeholder, Collections.emptyMap());
    }

    private PlatformPlaceholderDatabaseDriverResolver(String placeholder, Map<DatabaseDriver, String> driverMappings) {
        this.placeholder = placeholder;
        this.driverMappings = driverMappings;
    }

    public PlatformPlaceholderDatabaseDriverResolver withDriverPlatform(DatabaseDriver driver, String platform) {
        LinkedHashMap<DatabaseDriver, String> driverMappings = new LinkedHashMap<DatabaseDriver, String>(this.driverMappings);
        driverMappings.put(driver, platform);
        return new PlatformPlaceholderDatabaseDriverResolver(this.placeholder, driverMappings);
    }

    public List<String> resolveAll(DataSource dataSource, String ... values) {
        Assert.notNull((Object)dataSource, (String)"DataSource must not be null");
        return this.resolveAll(() -> this.determinePlatform(dataSource), values);
    }

    public List<String> resolveAll(String platform, String ... values) {
        Assert.notNull((Object)platform, (String)"Platform must not be null");
        return this.resolveAll(() -> platform, values);
    }

    private List<String> resolveAll(Supplier<String> platformProvider, String ... values) {
        if (ObjectUtils.isEmpty((Object[])values)) {
            return Collections.emptyList();
        }
        ArrayList<String> resolved = new ArrayList<String>(values.length);
        String platform = null;
        for (String value : values) {
            if (StringUtils.hasLength((String)value) && value.contains(this.placeholder)) {
                platform = platform != null ? platform : platformProvider.get();
                value = value.replace(this.placeholder, platform);
            }
            resolved.add(value);
        }
        return Collections.unmodifiableList(resolved);
    }

    private String determinePlatform(DataSource dataSource) {
        DatabaseDriver databaseDriver = this.getDatabaseDriver(dataSource);
        Assert.state((databaseDriver != DatabaseDriver.UNKNOWN ? 1 : 0) != 0, (String)"Unable to detect database type");
        return this.driverMappings.getOrDefault((Object)databaseDriver, databaseDriver.getId());
    }

    DatabaseDriver getDatabaseDriver(DataSource dataSource) {
        return DatabaseDriver.fromDataSource(dataSource);
    }
}

