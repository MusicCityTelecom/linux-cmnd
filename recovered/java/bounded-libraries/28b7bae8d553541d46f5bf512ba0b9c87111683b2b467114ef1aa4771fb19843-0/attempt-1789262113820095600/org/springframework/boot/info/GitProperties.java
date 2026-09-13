/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.info;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Properties;
import org.springframework.boot.info.InfoProperties;

public class GitProperties
extends InfoProperties {
    public GitProperties(Properties entries) {
        super(GitProperties.processEntries(entries));
    }

    public String getBranch() {
        return this.get("branch");
    }

    public String getCommitId() {
        return this.get("commit.id");
    }

    public String getShortCommitId() {
        String shortId = this.get("commit.id.abbrev");
        if (shortId != null) {
            return shortId;
        }
        String id = this.getCommitId();
        if (id == null) {
            return null;
        }
        return id.length() > 7 ? id.substring(0, 7) : id;
    }

    public Instant getCommitTime() {
        return this.getInstant("commit.time");
    }

    private static Properties processEntries(Properties properties) {
        GitProperties.coercePropertyToEpoch(properties, "commit.time");
        GitProperties.coercePropertyToEpoch(properties, "build.time");
        Object commitId = properties.get("commit.id");
        if (commitId != null) {
            properties.put("commit.id.full", commitId);
        }
        return properties;
    }

    private static void coercePropertyToEpoch(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value != null) {
            properties.setProperty(key, GitProperties.coerceToEpoch(value));
        }
    }

    private static String coerceToEpoch(String s) {
        Long epoch = GitProperties.parseEpochSecond(s);
        if (epoch != null) {
            return String.valueOf(epoch);
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            return String.valueOf(format.parse((CharSequence)s, Instant::from).toEpochMilli());
        }
        catch (DateTimeParseException ex) {
            return s;
        }
    }

    private static Long parseEpochSecond(String s) {
        try {
            return Long.parseLong(s) * 1000L;
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }
}

