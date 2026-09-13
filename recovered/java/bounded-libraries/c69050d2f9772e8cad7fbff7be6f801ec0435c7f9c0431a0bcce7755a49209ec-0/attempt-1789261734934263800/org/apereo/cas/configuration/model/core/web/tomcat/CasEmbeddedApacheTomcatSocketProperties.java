/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatSocketProperties")
public class CasEmbeddedApacheTomcatSocketProperties
implements Serializable {
    private static final long serialVersionUID = 3280755966422957481L;
    private int appReadBufSize;
    private int appWriteBufSize;
    private int bufferPool;
    private int performanceConnectionTime = -1;
    private int performanceLatency = -1;
    private int performanceBandwidth = -1;

    @Generated
    public int getAppReadBufSize() {
        return this.appReadBufSize;
    }

    @Generated
    public int getAppWriteBufSize() {
        return this.appWriteBufSize;
    }

    @Generated
    public int getBufferPool() {
        return this.bufferPool;
    }

    @Generated
    public int getPerformanceConnectionTime() {
        return this.performanceConnectionTime;
    }

    @Generated
    public int getPerformanceLatency() {
        return this.performanceLatency;
    }

    @Generated
    public int getPerformanceBandwidth() {
        return this.performanceBandwidth;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setAppReadBufSize(int appReadBufSize) {
        this.appReadBufSize = appReadBufSize;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setAppWriteBufSize(int appWriteBufSize) {
        this.appWriteBufSize = appWriteBufSize;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setBufferPool(int bufferPool) {
        this.bufferPool = bufferPool;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setPerformanceConnectionTime(int performanceConnectionTime) {
        this.performanceConnectionTime = performanceConnectionTime;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setPerformanceLatency(int performanceLatency) {
        this.performanceLatency = performanceLatency;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatSocketProperties setPerformanceBandwidth(int performanceBandwidth) {
        this.performanceBandwidth = performanceBandwidth;
        return this;
    }
}

