/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.version.Version;

abstract class $AutoValue_AndroidManifest
extends AndroidManifest {
    private final XmlProtoNode manifestRoot;
    private final Version bundleToolVersion;

    $AutoValue_AndroidManifest(XmlProtoNode manifestRoot, Version bundleToolVersion) {
        if (manifestRoot == null) {
            throw new NullPointerException("Null manifestRoot");
        }
        this.manifestRoot = manifestRoot;
        if (bundleToolVersion == null) {
            throw new NullPointerException("Null bundleToolVersion");
        }
        this.bundleToolVersion = bundleToolVersion;
    }

    @Override
    public XmlProtoNode getManifestRoot() {
        return this.manifestRoot;
    }

    @Override
    Version getBundleToolVersion() {
        return this.bundleToolVersion;
    }

    public String toString() {
        return "AndroidManifest{manifestRoot=" + this.manifestRoot + ", bundleToolVersion=" + this.bundleToolVersion + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof AndroidManifest) {
            AndroidManifest that = (AndroidManifest)o3;
            return this.manifestRoot.equals(that.getManifestRoot()) && this.bundleToolVersion.equals(that.getBundleToolVersion());
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.manifestRoot.hashCode();
        h$ *= 1000003;
        return h$ ^= this.bundleToolVersion.hashCode();
    }
}

