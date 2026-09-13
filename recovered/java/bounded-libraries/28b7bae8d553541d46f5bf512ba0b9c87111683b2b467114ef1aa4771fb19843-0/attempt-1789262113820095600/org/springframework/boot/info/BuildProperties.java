/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.info;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import org.springframework.boot.info.InfoProperties;

public class BuildProperties
extends InfoProperties {
    public BuildProperties(Properties entries) {
        super(BuildProperties.processEntries(entries));
    }

    public String getGroup() {
        return this.get("group");
    }

    public String getArtifact() {
        return this.get("artifact");
    }

    public String getName() {
        return this.get("name");
    }

    public String getVersion() {
        return this.get("version");
    }

    public Instant getTime() {
        return this.getInstant("time");
    }

    private static Properties processEntries(Properties properties) {
        BuildProperties.coerceDate(properties, "time");
        return properties;
    }

    private static void coerceDate(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                String updatedValue = String.valueOf(DateTimeFormatter.ISO_INSTANT.parse((CharSequence)value, Instant::from).toEpochMilli());
                properties.setProperty(key, updatedValue);
            }
            catch (DateTimeException dateTimeException) {
                // empty catch block
            }
        }
    }
}

