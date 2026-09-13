/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.tools.build.apkzlib.utils.CachedSupplier;
import com.android.tools.build.apkzlib.utils.IOExceptionRunnable;
import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.android.tools.build.apkzlib.zip.StoredEntry;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZFileExtension;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.annotation.Nullable;

public class ManifestGenerationExtension {
    private static final String META_INF_DIR = "META-INF";
    static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
    private final String builtBy;
    private final String createdBy;
    @Nullable
    private ZFile zFile;
    private final Manifest manifest;
    private final CachedSupplier<byte[]> manifestBytes;
    private boolean dirty;
    @Nullable
    private ZFileExtension extension;

    public ManifestGenerationExtension(String builtBy, String createdBy) {
        this.builtBy = builtBy;
        this.createdBy = createdBy;
        this.manifest = new Manifest();
        this.dirty = false;
        this.manifestBytes = new CachedSupplier<byte[]>(() -> {
            ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
            try {
                this.manifest.write(outBytes);
            }
            catch (IOException e2) {
                throw new IOExceptionWrapper(e2);
            }
            return outBytes.toByteArray();
        });
    }

    private void markDirty() {
        this.dirty = true;
        this.manifestBytes.reset();
    }

    public void register(ZFile zFile) throws IOException {
        Preconditions.checkState(this.extension == null, "register() has already been invoked.");
        this.zFile = zFile;
        this.rebuildManifest();
        this.extension = new ZFileExtension(){

            @Override
            @Nullable
            public IOExceptionRunnable beforeUpdate() {
                return () -> ManifestGenerationExtension.this.updateManifest();
            }
        };
        this.zFile.addZFileExtension(this.extension);
    }

    private void rebuildManifest() throws IOException {
        Attributes mainAttributes;
        String currentVersion;
        Verify.verifyNotNull(this.zFile, "zFile == null", new Object[0]);
        StoredEntry manifestEntry = this.zFile.get(MANIFEST_NAME);
        if (manifestEntry != null) {
            this.manifest.clear();
            byte[] manifestBytes = manifestEntry.read();
            this.manifest.read(new ByteArrayInputStream(manifestBytes));
            this.manifestBytes.precomputed(manifestBytes);
        }
        if ((currentVersion = (mainAttributes = this.manifest.getMainAttributes()).getValue("Manifest-Version")) == null) {
            this.setMainAttribute("Manifest-Version", "1.0");
        } else if (!currentVersion.equals("1.0")) {
            throw new IOException("Unsupported manifest version: " + currentVersion + ".");
        }
        this.setMainAttribute("Built-By", this.builtBy);
        this.setMainAttribute("Created-By", this.createdBy);
    }

    private void setMainAttribute(String attribute, String value) {
        Attributes mainAttributes = this.manifest.getMainAttributes();
        String current = mainAttributes.getValue(attribute);
        if (!value.equals(current)) {
            mainAttributes.putValue(attribute, value);
            this.markDirty();
        }
    }

    private void updateManifest() throws IOException {
        Verify.verifyNotNull(this.zFile, "zFile == null", new Object[0]);
        if (!this.dirty) {
            return;
        }
        this.zFile.add(MANIFEST_NAME, new ByteArrayInputStream(this.manifestBytes.get()));
        this.dirty = false;
    }
}

