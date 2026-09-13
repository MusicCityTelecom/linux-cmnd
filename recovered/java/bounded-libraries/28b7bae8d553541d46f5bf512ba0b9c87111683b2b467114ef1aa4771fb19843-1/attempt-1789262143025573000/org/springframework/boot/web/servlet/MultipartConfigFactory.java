/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.MultipartConfigElement
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.web.servlet;

import javax.servlet.MultipartConfigElement;
import org.springframework.util.unit.DataSize;

public class MultipartConfigFactory {
    private String location;
    private DataSize maxFileSize;
    private DataSize maxRequestSize;
    private DataSize fileSizeThreshold;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMaxFileSize(DataSize maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public void setMaxRequestSize(DataSize maxRequestSize) {
        this.maxRequestSize = maxRequestSize;
    }

    public void setFileSizeThreshold(DataSize fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
    }

    public MultipartConfigElement createMultipartConfig() {
        long maxFileSizeBytes = this.convertToBytes(this.maxFileSize, -1);
        long maxRequestSizeBytes = this.convertToBytes(this.maxRequestSize, -1);
        long fileSizeThresholdBytes = this.convertToBytes(this.fileSizeThreshold, 0);
        return new MultipartConfigElement(this.location, maxFileSizeBytes, maxRequestSizeBytes, (int)fileSizeThresholdBytes);
    }

    private long convertToBytes(DataSize size, int defaultValue) {
        if (size != null && !size.isNegative()) {
            return size.toBytes();
        }
        return defaultValue;
    }
}

