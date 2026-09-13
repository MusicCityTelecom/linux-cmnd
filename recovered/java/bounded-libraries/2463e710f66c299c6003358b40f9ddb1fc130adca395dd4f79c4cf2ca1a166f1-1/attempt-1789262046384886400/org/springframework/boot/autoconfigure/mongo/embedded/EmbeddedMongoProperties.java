/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 *  org.springframework.boot.convert.DataSizeUnit
 *  org.springframework.util.unit.DataSize
 *  org.springframework.util.unit.DataUnit
 */
package org.springframework.boot.autoconfigure.mongo.embedded;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@ConfigurationProperties(prefix="spring.mongodb.embedded")
public class EmbeddedMongoProperties {
    private String version;
    private final Storage storage = new Storage();

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public static class Storage {
        @DataSizeUnit(value=DataUnit.MEGABYTES)
        private DataSize oplogSize;
        private String replSetName;
        private String databaseDir;

        public DataSize getOplogSize() {
            return this.oplogSize;
        }

        public void setOplogSize(DataSize oplogSize) {
            this.oplogSize = oplogSize;
        }

        public String getReplSetName() {
            return this.replSetName;
        }

        public void setReplSetName(String replSetName) {
            this.replSetName = replSetName;
        }

        public String getDatabaseDir() {
            return this.databaseDir;
        }

        public void setDatabaseDir(String databaseDir) {
            this.databaseDir = databaseDir;
        }
    }
}

