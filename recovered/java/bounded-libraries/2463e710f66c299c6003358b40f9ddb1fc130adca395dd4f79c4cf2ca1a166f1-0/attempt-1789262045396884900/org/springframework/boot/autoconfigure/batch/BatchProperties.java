/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.sql.init.DatabaseInitializationMode
 */
package org.springframework.boot.autoconfigure.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.sql.init.DatabaseInitializationMode;

@ConfigurationProperties(prefix="spring.batch")
public class BatchProperties {
    private final Job job = new Job();
    private final Jdbc jdbc = new Jdbc();

    public Job getJob() {
        return this.job;
    }

    public Jdbc getJdbc() {
        return this.jdbc;
    }

    public static enum Isolation {
        DEFAULT,
        READ_UNCOMMITTED,
        READ_COMMITTED,
        REPEATABLE_READ,
        SERIALIZABLE;

        private static final String PREFIX = "ISOLATION_";

        String toIsolationName() {
            return PREFIX + this.name();
        }
    }

    public static class Jdbc {
        private static final String DEFAULT_SCHEMA_LOCATION = "classpath:org/springframework/batch/core/schema-@@platform@@.sql";
        private Isolation isolationLevelForCreate;
        private String schema = "classpath:org/springframework/batch/core/schema-@@platform@@.sql";
        private String platform;
        private String tablePrefix;
        private DatabaseInitializationMode initializeSchema = DatabaseInitializationMode.EMBEDDED;

        public Isolation getIsolationLevelForCreate() {
            return this.isolationLevelForCreate;
        }

        public void setIsolationLevelForCreate(Isolation isolationLevelForCreate) {
            this.isolationLevelForCreate = isolationLevelForCreate;
        }

        public String getSchema() {
            return this.schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getTablePrefix() {
            return this.tablePrefix;
        }

        public void setTablePrefix(String tablePrefix) {
            this.tablePrefix = tablePrefix;
        }

        public DatabaseInitializationMode getInitializeSchema() {
            return this.initializeSchema;
        }

        public void setInitializeSchema(DatabaseInitializationMode initializeSchema) {
            this.initializeSchema = initializeSchema;
        }
    }

    public static class Job {
        private String names = "";

        public String getNames() {
            return this.names;
        }

        public void setNames(String names) {
            this.names = names;
        }
    }
}

