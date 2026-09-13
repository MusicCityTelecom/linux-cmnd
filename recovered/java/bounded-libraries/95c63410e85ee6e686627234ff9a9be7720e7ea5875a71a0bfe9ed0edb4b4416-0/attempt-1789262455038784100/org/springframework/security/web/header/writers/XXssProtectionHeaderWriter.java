/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;

public final class XXssProtectionHeaderWriter
implements HeaderWriter {
    private static final String XSS_PROTECTION_HEADER = "X-XSS-Protection";
    private boolean enabled = true;
    private boolean block = true;
    private String headerValue;

    public XXssProtectionHeaderWriter() {
        this.updateHeaderValue();
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (!response.containsHeader(XSS_PROTECTION_HEADER)) {
            response.setHeader(XSS_PROTECTION_HEADER, this.headerValue);
        }
    }

    public void setEnabled(boolean enabled) {
        if (!enabled) {
            this.setBlock(false);
        }
        this.enabled = enabled;
        this.updateHeaderValue();
    }

    public void setBlock(boolean block) {
        if (!this.enabled && block) {
            throw new IllegalArgumentException("Cannot set block to true with enabled false");
        }
        this.block = block;
        this.updateHeaderValue();
    }

    private void updateHeaderValue() {
        if (!this.enabled) {
            this.headerValue = "0";
            return;
        }
        this.headerValue = "1";
        if (this.block) {
            this.headerValue = this.headerValue + "; mode=block";
        }
    }

    public String toString() {
        return this.getClass().getName() + " [headerValue=" + this.headerValue + "]";
    }
}

