/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.FileWrapper;
import com.android.io.IAbstractFile;
import com.android.io.IAbstractFolder;
import com.android.io.IAbstractResource;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class FolderWrapper
extends File
implements IAbstractFolder {
    private static final long serialVersionUID = 1L;

    public FolderWrapper(File parent, String child) {
        super(parent, child);
    }

    public FolderWrapper(String pathname) {
        super(pathname);
    }

    public FolderWrapper(String parent, String child) {
        super(parent, child);
    }

    public FolderWrapper(URI uri) {
        super(uri);
    }

    public FolderWrapper(File file) {
        super(file.getAbsolutePath());
    }

    @Override
    public IAbstractResource[] listMembers() {
        File[] files = this.listFiles();
        int count = files == null ? 0 : files.length;
        IAbstractResource[] afiles = new IAbstractResource[count];
        if (files != null) {
            for (int i2 = 0; i2 < count; ++i2) {
                File f2 = files[i2];
                if (f2.isFile()) {
                    afiles[i2] = new FileWrapper(f2);
                    continue;
                }
                if (!f2.isDirectory()) continue;
                afiles[i2] = new FolderWrapper(f2);
            }
        }
        return afiles;
    }

    @Override
    public boolean hasFile(final String name) {
        String[] match = this.list(new IAbstractFolder.FilenameFilter(){

            @Override
            public boolean accept(IAbstractFolder dir, String filename) {
                return name.equals(filename);
            }
        });
        return match.length > 0;
    }

    @Override
    public IAbstractFile getFile(String name) {
        return new FileWrapper(this, name);
    }

    @Override
    public IAbstractFolder getFolder(String name) {
        return new FolderWrapper(this, name);
    }

    @Override
    public IAbstractFolder getParentFolder() {
        String p3 = this.getParent();
        if (p3 == null) {
            return null;
        }
        return new FolderWrapper(p3);
    }

    @Override
    public String getOsLocation() {
        return this.getAbsolutePath();
    }

    @Override
    public boolean exists() {
        return this.isDirectory();
    }

    @Override
    public String[] list(IAbstractFolder.FilenameFilter filter) {
        File[] files = this.listFiles();
        if (files != null && files.length > 0) {
            ArrayList<String> list = new ArrayList<String>();
            for (File file : files) {
                if (!filter.accept(this, file.getName())) continue;
                list.add(file.getName());
            }
            return list.toArray(new String[list.size()]);
        }
        return new String[0];
    }
}

