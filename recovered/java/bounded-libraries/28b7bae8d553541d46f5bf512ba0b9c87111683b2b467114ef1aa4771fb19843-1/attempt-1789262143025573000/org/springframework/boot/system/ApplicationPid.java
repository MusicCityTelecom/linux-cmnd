/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.log.LogMessage
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class ApplicationPid {
    private static final Log logger = LogFactory.getLog(ApplicationPid.class);
    private static final PosixFilePermission[] WRITE_PERMISSIONS = new PosixFilePermission[]{PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_WRITE, PosixFilePermission.OTHERS_WRITE};
    private static final long JVM_NAME_RESOLVE_THRESHOLD = 200L;
    private final String pid;

    public ApplicationPid() {
        this.pid = this.getPid();
    }

    protected ApplicationPid(String pid) {
        this.pid = pid;
    }

    private String getPid() {
        try {
            String jvmName = this.resolveJvmName();
            return jvmName.split("@")[0];
        }
        catch (Throwable ex) {
            return null;
        }
    }

    private String resolveJvmName() {
        long startTime = System.currentTimeMillis();
        String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > 200L) {
            logger.warn((Object)LogMessage.of(() -> {
                StringBuilder warning = new StringBuilder();
                warning.append("ManagementFactory.getRuntimeMXBean().getName() took ");
                warning.append(elapsed);
                warning.append(" milliseconds to respond.");
                warning.append(" This may be due to slow host name resolution.");
                warning.append(" Please verify your network configuration");
                if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    warning.append(" (macOS machines may need to add entries to /etc/hosts)");
                }
                warning.append(".");
                return warning;
            }));
        }
        return jvmName;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ApplicationPid) {
            return ObjectUtils.nullSafeEquals((Object)this.pid, (Object)((ApplicationPid)obj).pid);
        }
        return false;
    }

    public int hashCode() {
        return ObjectUtils.nullSafeHashCode((Object)this.pid);
    }

    public String toString() {
        return this.pid != null ? this.pid : "???";
    }

    public void write(File file) throws IOException {
        Assert.state((this.pid != null ? 1 : 0) != 0, (String)"No PID available");
        this.createParentDirectory(file);
        if (file.exists()) {
            this.assertCanOverwrite(file);
        }
        try (FileWriter writer = new FileWriter(file);){
            writer.append(this.pid);
        }
    }

    private void createParentDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
    }

    private void assertCanOverwrite(File file) throws IOException {
        if (!file.canWrite() || !this.canWritePosixFile(file)) {
            throw new FileNotFoundException(file.toString() + " (permission denied)");
        }
    }

    private boolean canWritePosixFile(File file) throws IOException {
        try {
            Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(file.toPath(), new LinkOption[0]);
            for (PosixFilePermission permission : WRITE_PERMISSIONS) {
                if (!permissions.contains((Object)permission)) continue;
                return true;
            }
            return false;
        }
        catch (UnsupportedOperationException ex) {
            return true;
        }
    }
}

