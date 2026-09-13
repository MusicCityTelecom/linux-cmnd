/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.$AutoValue_AndroidManifest;
import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.version.Version;
import java.util.Optional;

final class AutoValue_AndroidManifest
extends $AutoValue_AndroidManifest {
    private volatile XmlProtoElement getManifestElement;
    private volatile Optional<ManifestDeliveryElement> getManifestDeliveryElement;
    private volatile Optional<ManifestDeliveryElement> getInstantManifestDeliveryElement;

    AutoValue_AndroidManifest(XmlProtoNode manifestRoot$, Version bundleToolVersion$) {
        super(manifestRoot$, bundleToolVersion$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    XmlProtoElement getManifestElement() {
        if (this.getManifestElement == null) {
            AutoValue_AndroidManifest autoValue_AndroidManifest = this;
            synchronized (autoValue_AndroidManifest) {
                if (this.getManifestElement == null) {
                    this.getManifestElement = super.getManifestElement();
                    if (this.getManifestElement == null) {
                        throw new NullPointerException("getManifestElement() cannot return null");
                    }
                }
            }
        }
        return this.getManifestElement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Optional<ManifestDeliveryElement> getManifestDeliveryElement() {
        if (this.getManifestDeliveryElement == null) {
            AutoValue_AndroidManifest autoValue_AndroidManifest = this;
            synchronized (autoValue_AndroidManifest) {
                if (this.getManifestDeliveryElement == null) {
                    this.getManifestDeliveryElement = super.getManifestDeliveryElement();
                    if (this.getManifestDeliveryElement == null) {
                        throw new NullPointerException("getManifestDeliveryElement() cannot return null");
                    }
                }
            }
        }
        return this.getManifestDeliveryElement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Optional<ManifestDeliveryElement> getInstantManifestDeliveryElement() {
        if (this.getInstantManifestDeliveryElement == null) {
            AutoValue_AndroidManifest autoValue_AndroidManifest = this;
            synchronized (autoValue_AndroidManifest) {
                if (this.getInstantManifestDeliveryElement == null) {
                    this.getInstantManifestDeliveryElement = super.getInstantManifestDeliveryElement();
                    if (this.getInstantManifestDeliveryElement == null) {
                        throw new NullPointerException("getInstantManifestDeliveryElement() cannot return null");
                    }
                }
            }
        }
        return this.getInstantManifestDeliveryElement;
    }
}

