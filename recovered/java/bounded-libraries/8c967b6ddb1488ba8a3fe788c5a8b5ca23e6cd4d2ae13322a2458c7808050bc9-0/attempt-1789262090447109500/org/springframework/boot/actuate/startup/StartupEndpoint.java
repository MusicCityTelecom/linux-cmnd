/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.SpringBootVersion
 *  org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup
 *  org.springframework.boot.context.metrics.buffering.StartupTimeline
 */
package org.springframework.boot.actuate.startup;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;

@Endpoint(id="startup")
public class StartupEndpoint {
    private final BufferingApplicationStartup applicationStartup;

    public StartupEndpoint(BufferingApplicationStartup applicationStartup) {
        this.applicationStartup = applicationStartup;
    }

    @ReadOperation
    public StartupResponse startupSnapshot() {
        StartupTimeline startupTimeline = this.applicationStartup.getBufferedTimeline();
        return new StartupResponse(startupTimeline);
    }

    @WriteOperation
    public StartupResponse startup() {
        StartupTimeline startupTimeline = this.applicationStartup.drainBufferedTimeline();
        return new StartupResponse(startupTimeline);
    }

    public static final class StartupResponse {
        private final String springBootVersion;
        private final StartupTimeline timeline;

        private StartupResponse(StartupTimeline timeline) {
            this.timeline = timeline;
            this.springBootVersion = SpringBootVersion.getVersion();
        }

        public String getSpringBootVersion() {
            return this.springBootVersion;
        }

        public StartupTimeline getTimeline() {
            return this.timeline;
        }
    }
}

