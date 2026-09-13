/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.header.writers.frameoptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.util.Assert;

public final class XFrameOptionsHeaderWriter
implements HeaderWriter {
    public static final String XFRAME_OPTIONS_HEADER = "X-Frame-Options";
    private final AllowFromStrategy allowFromStrategy;
    private final XFrameOptionsMode frameOptionsMode;

    public XFrameOptionsHeaderWriter() {
        this(XFrameOptionsMode.DENY);
    }

    public XFrameOptionsHeaderWriter(XFrameOptionsMode frameOptionsMode) {
        Assert.notNull((Object)((Object)frameOptionsMode), (String)"frameOptionsMode cannot be null");
        Assert.isTrue((!XFrameOptionsMode.ALLOW_FROM.equals((Object)frameOptionsMode) ? 1 : 0) != 0, (String)"ALLOW_FROM requires an AllowFromStrategy. Please use FrameOptionsHeaderWriter(AllowFromStrategy allowFromStrategy) instead");
        this.frameOptionsMode = frameOptionsMode;
        this.allowFromStrategy = null;
    }

    @Deprecated
    public XFrameOptionsHeaderWriter(AllowFromStrategy allowFromStrategy) {
        Assert.notNull((Object)allowFromStrategy, (String)"allowFromStrategy cannot be null");
        this.frameOptionsMode = XFrameOptionsMode.ALLOW_FROM;
        this.allowFromStrategy = allowFromStrategy;
    }

    @Override
    public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (XFrameOptionsMode.ALLOW_FROM.equals((Object)this.frameOptionsMode)) {
            String allowFromValue = this.allowFromStrategy.getAllowFromValue(request);
            if (XFrameOptionsMode.DENY.getMode().equals(allowFromValue)) {
                if (!response.containsHeader(XFRAME_OPTIONS_HEADER)) {
                    response.setHeader(XFRAME_OPTIONS_HEADER, XFrameOptionsMode.DENY.getMode());
                }
            } else if (allowFromValue != null && !response.containsHeader(XFRAME_OPTIONS_HEADER)) {
                response.setHeader(XFRAME_OPTIONS_HEADER, XFrameOptionsMode.ALLOW_FROM.getMode() + " " + allowFromValue);
            }
        } else {
            response.setHeader(XFRAME_OPTIONS_HEADER, this.frameOptionsMode.getMode());
        }
    }

    public static enum XFrameOptionsMode {
        DENY("DENY"),
        SAMEORIGIN("SAMEORIGIN"),
        ALLOW_FROM("ALLOW-FROM");

        private final String mode;

        private XFrameOptionsMode(String mode) {
            this.mode = mode;
        }

        private String getMode() {
            return this.mode;
        }
    }
}

