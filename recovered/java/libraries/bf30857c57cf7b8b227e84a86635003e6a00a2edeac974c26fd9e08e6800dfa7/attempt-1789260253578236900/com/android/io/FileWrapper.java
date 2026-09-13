/*
 * Decompiled with CFR 0.152.
 */
package com.android.io;

import com.android.io.FolderWrapper;
import com.android.io.IAbstractFile;
import com.android.io.IAbstractFolder;
import com.android.io.StreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class FileWrapper
extends File
implements IAbstractFile {
    private static final long serialVersionUID = 1L;

    public FileWrapper(File file) {
        super(file.getAbsolutePath());
    }

    public FileWrapper(File parent, String child) {
        super(parent, child);
    }

    public FileWrapper(String osPathname) {
        super(osPathname);
    }

    public FileWrapper(String parent, String child) {
        super(parent, child);
    }

    public FileWrapper(URI uri) {
        super(uri);
    }

    @Override
    public InputStream getContents() throws StreamException {
        try {
            return new FileInputStream(this);
        }
        catch (FileNotFoundException e2) {
            throw new StreamException(e2, this, StreamException.Error.FILENOTFOUND);
        }
    }

    @Override
    public void setContents(InputStream source) throws StreamException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(this);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = source.read(buffer)) != -1) {
                fos.write(buffer, 0, count);
            }
        }
        catch (IOException e2) {
            throw new StreamException(e2, this);
        }
        finally {
            if (fos != null) {
                try {
                    fos.close();
                }
                catch (IOException e3) {
                    throw new StreamException(e3, this);
                }
            }
        }
    }

    @Override
    public OutputStream getOutputStream() throws StreamException {
        try {
            return new FileOutputStream(this);
        }
        catch (FileNotFoundException e2) {
            throw new StreamException(e2, this);
        }
    }

    @Override
    public IAbstractFile.PreferredWriteMode getPreferredWriteMode() {
        return IAbstractFile.PreferredWriteMode.OUTPUTSTREAM;
    }

    @Override
    public String getOsLocation() {
        return this.getAbsolutePath();
    }

    @Override
    public boolean exists() {
        return this.isFile();
    }

    @Override
    public long getModificationStamp() {
        return this.lastModified();
    }

    @Override
    public IAbstractFolder getParentFolder() {
        String p3 = this.getParent();
        if (p3 == null) {
            return null;
        }
        return new FolderWrapper(p3);
    }
}

