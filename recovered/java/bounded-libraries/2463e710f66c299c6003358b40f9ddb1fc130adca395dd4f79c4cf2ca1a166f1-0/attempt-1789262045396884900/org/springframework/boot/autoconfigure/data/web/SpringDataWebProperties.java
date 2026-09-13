/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.ConfigurationProperties
 */
package org.springframework.boot.autoconfigure.data.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value="spring.data.web")
public class SpringDataWebProperties {
    private final Pageable pageable = new Pageable();
    private final Sort sort = new Sort();

    public Pageable getPageable() {
        return this.pageable;
    }

    public Sort getSort() {
        return this.sort;
    }

    public static class Sort {
        private String sortParameter = "sort";

        public String getSortParameter() {
            return this.sortParameter;
        }

        public void setSortParameter(String sortParameter) {
            this.sortParameter = sortParameter;
        }
    }

    public static class Pageable {
        private String pageParameter = "page";
        private String sizeParameter = "size";
        private boolean oneIndexedParameters = false;
        private String prefix = "";
        private String qualifierDelimiter = "_";
        private int defaultPageSize = 20;
        private int maxPageSize = 2000;

        public String getPageParameter() {
            return this.pageParameter;
        }

        public void setPageParameter(String pageParameter) {
            this.pageParameter = pageParameter;
        }

        public String getSizeParameter() {
            return this.sizeParameter;
        }

        public void setSizeParameter(String sizeParameter) {
            this.sizeParameter = sizeParameter;
        }

        public boolean isOneIndexedParameters() {
            return this.oneIndexedParameters;
        }

        public void setOneIndexedParameters(boolean oneIndexedParameters) {
            this.oneIndexedParameters = oneIndexedParameters;
        }

        public String getPrefix() {
            return this.prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getQualifierDelimiter() {
            return this.qualifierDelimiter;
        }

        public void setQualifierDelimiter(String qualifierDelimiter) {
            this.qualifierDelimiter = qualifierDelimiter;
        }

        public int getDefaultPageSize() {
            return this.defaultPageSize;
        }

        public void setDefaultPageSize(int defaultPageSize) {
            this.defaultPageSize = defaultPageSize;
        }

        public int getMaxPageSize() {
            return this.maxPageSize;
        }

        public void setMaxPageSize(int maxPageSize) {
            this.maxPageSize = maxPageSize;
        }
    }
}

