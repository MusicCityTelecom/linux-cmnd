/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.sql.init;

import java.nio.charset.Charset;
import java.util.List;
import org.springframework.boot.sql.init.DatabaseInitializationMode;

public class DatabaseInitializationSettings {
    private List<String> schemaLocations;
    private List<String> dataLocations;
    private boolean continueOnError = false;
    private String separator = ";";
    private Charset encoding;
    private DatabaseInitializationMode mode = DatabaseInitializationMode.EMBEDDED;

    public List<String> getSchemaLocations() {
        return this.schemaLocations;
    }

    public void setSchemaLocations(List<String> schemaLocations) {
        this.schemaLocations = schemaLocations;
    }

    public List<String> getDataLocations() {
        return this.dataLocations;
    }

    public void setDataLocations(List<String> dataLocations) {
        this.dataLocations = dataLocations;
    }

    public boolean isContinueOnError() {
        return this.continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public Charset getEncoding() {
        return this.encoding;
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    public DatabaseInitializationMode getMode() {
        return this.mode;
    }

    public void setMode(DatabaseInitializationMode mode) {
        this.mode = mode;
    }
}

