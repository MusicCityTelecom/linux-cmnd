/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.web.servlet.server;

import java.io.File;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.util.Assert;

class SessionStoreDirectory {
    private File directory;

    SessionStoreDirectory() {
    }

    File getDirectory() {
        return this.directory;
    }

    void setDirectory(File directory) {
        this.directory = directory;
    }

    File getValidDirectory(boolean mkdirs) {
        File dir = this.getDirectory();
        if (dir == null) {
            return new ApplicationTemp().getDir("servlet-sessions");
        }
        if (!dir.isAbsolute()) {
            dir = new File(new ApplicationHome().getDir(), dir.getPath());
        }
        if (!dir.exists() && mkdirs) {
            dir.mkdirs();
        }
        this.assertDirectory(mkdirs, dir);
        return dir;
    }

    private void assertDirectory(boolean mkdirs, File dir) {
        Assert.state((!mkdirs || dir.exists() ? 1 : 0) != 0, () -> "Session dir " + dir + " does not exist");
        Assert.state((!dir.isFile() ? 1 : 0) != 0, () -> "Session dir " + dir + " points to a file");
    }
}

