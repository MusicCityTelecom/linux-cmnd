/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header;

import java.util.Arrays;
import java.util.List;
import org.springframework.util.Assert;

public final class Header {
    private final String headerName;
    private final List<String> headerValues;

    public Header(String headerName, String ... headerValues) {
        Assert.hasText((String)headerName, (String)"headerName is required");
        Assert.notEmpty((Object[])headerValues, (String)"headerValues cannot be null or empty");
        Assert.noNullElements((Object[])headerValues, (String)"headerValues cannot contain null values");
        this.headerName = headerName;
        this.headerValues = Arrays.asList(headerValues);
    }

    public String getName() {
        return this.headerName;
    }

    public List<String> getValues() {
        return this.headerValues;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Header other = (Header)obj;
        if (!this.headerName.equals(other.headerName)) {
            return false;
        }
        return this.headerValues.equals(other.headerValues);
    }

    public int hashCode() {
        return this.headerName.hashCode() + this.headerValues.hashCode();
    }

    public String toString() {
        return "Header [name: " + this.headerName + ", values: " + this.headerValues + "]";
    }
}

