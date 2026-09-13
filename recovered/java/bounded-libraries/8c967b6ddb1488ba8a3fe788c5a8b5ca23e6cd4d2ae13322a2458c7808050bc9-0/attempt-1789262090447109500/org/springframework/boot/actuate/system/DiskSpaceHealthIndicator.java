/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.unit.DataSize
 */
package org.springframework.boot.actuate.system;

import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.core.log.LogMessage;
import org.springframework.util.unit.DataSize;

public class DiskSpaceHealthIndicator
extends AbstractHealthIndicator {
    private static final Log logger = LogFactory.getLog(DiskSpaceHealthIndicator.class);
    private final File path;
    private final DataSize threshold;

    public DiskSpaceHealthIndicator(File path, DataSize threshold) {
        super("DiskSpace health check failed");
        this.path = path;
        this.threshold = threshold;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        long diskFreeInBytes = this.path.getUsableSpace();
        if (diskFreeInBytes >= this.threshold.toBytes()) {
            builder.up();
        } else {
            logger.warn((Object)LogMessage.format((String)"Free disk space below threshold. Available: %d bytes (threshold: %s)", (Object)diskFreeInBytes, (Object)this.threshold));
            builder.down();
        }
        builder.withDetail("total", this.path.getTotalSpace()).withDetail("free", diskFreeInBytes).withDetail("threshold", this.threshold.toBytes()).withDetail("exists", this.path.exists());
    }
}

