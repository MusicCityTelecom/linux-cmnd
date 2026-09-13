/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.boot.logging.LogFile
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.actuate.logging;

import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.boot.logging.LogFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@WebEndpoint(id="logfile")
public class LogFileWebEndpoint {
    private static final Log logger = LogFactory.getLog(LogFileWebEndpoint.class);
    private final LogFile logFile;
    private final File externalFile;

    public LogFileWebEndpoint(LogFile logFile, File externalFile) {
        this.logFile = logFile;
        this.externalFile = externalFile;
    }

    @ReadOperation(produces={"text/plain; charset=UTF-8"})
    public Resource logFile() {
        Resource logFileResource = this.getLogFileResource();
        if (logFileResource == null || !logFileResource.isReadable()) {
            return null;
        }
        return logFileResource;
    }

    private Resource getLogFileResource() {
        if (this.externalFile != null) {
            return new FileSystemResource(this.externalFile);
        }
        if (this.logFile == null) {
            logger.debug((Object)"Missing 'logging.file.name' or 'logging.file.path' properties");
            return null;
        }
        return new FileSystemResource(this.logFile.toString());
    }
}

