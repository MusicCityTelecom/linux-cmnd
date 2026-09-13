/*
 * Decompiled with CFR 0.152.
 */
package com.android.ide.common.blame;

import com.google.common.base.Objects;
import java.io.File;
import java.io.Serializable;

public final class SourceFile
implements Serializable {
    public static final SourceFile UNKNOWN = new SourceFile();
    private final File mSourceFile;
    private final String mDescription;

    public SourceFile(File sourceFile, String description) {
        this.mSourceFile = sourceFile;
        this.mDescription = description;
    }

    public SourceFile(File sourceFile) {
        this.mSourceFile = sourceFile;
        this.mDescription = null;
    }

    public SourceFile(String description) {
        this.mSourceFile = null;
        this.mDescription = description;
    }

    private SourceFile() {
        this.mSourceFile = null;
        this.mDescription = null;
    }

    public File getSourceFile() {
        return this.mSourceFile;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SourceFile)) {
            return false;
        }
        SourceFile other = (SourceFile)obj;
        return Objects.equal(this.mDescription, other.mDescription) && Objects.equal(this.mSourceFile, other.mSourceFile);
    }

    public int hashCode() {
        String filePath = this.mSourceFile != null ? this.mSourceFile.getPath() : null;
        return Objects.hashCode(filePath, this.mDescription);
    }

    public String toString() {
        return this.print(false);
    }

    public String print(boolean shortFormat) {
        String fileDisplayName;
        if (this.mSourceFile == null) {
            if (this.mDescription == null) {
                return "Unknown source file";
            }
            return this.mDescription;
        }
        String fileName = this.mSourceFile.getName();
        String string = fileDisplayName = shortFormat ? fileName : this.mSourceFile.getAbsolutePath();
        if (this.mDescription == null || this.mDescription.equals(fileName)) {
            return fileDisplayName;
        }
        return String.format("[%1$s] %2$s", this.mDescription, fileDisplayName);
    }
}

