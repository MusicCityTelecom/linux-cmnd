/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.$AutoValue_ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.ModuleConditions;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;

final class AutoValue_ManifestDeliveryElement
extends $AutoValue_ManifestDeliveryElement {
    private volatile boolean hasOnDemandElement;
    private volatile boolean hasOnDemandElement$Memoized;
    private volatile boolean hasInstallTimeElement;
    private volatile boolean hasInstallTimeElement$Memoized;
    private volatile ModuleConditions getModuleConditions;

    AutoValue_ManifestDeliveryElement(XmlProtoElement deliveryElement$, boolean fastFollowAllowed$) {
        super(deliveryElement$, fastFollowAllowed$);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasOnDemandElement() {
        if (!this.hasOnDemandElement$Memoized) {
            AutoValue_ManifestDeliveryElement autoValue_ManifestDeliveryElement = this;
            synchronized (autoValue_ManifestDeliveryElement) {
                if (!this.hasOnDemandElement$Memoized) {
                    this.hasOnDemandElement = super.hasOnDemandElement();
                    this.hasOnDemandElement$Memoized = true;
                }
            }
        }
        return this.hasOnDemandElement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean hasInstallTimeElement() {
        if (!this.hasInstallTimeElement$Memoized) {
            AutoValue_ManifestDeliveryElement autoValue_ManifestDeliveryElement = this;
            synchronized (autoValue_ManifestDeliveryElement) {
                if (!this.hasInstallTimeElement$Memoized) {
                    this.hasInstallTimeElement = super.hasInstallTimeElement();
                    this.hasInstallTimeElement$Memoized = true;
                }
            }
        }
        return this.hasInstallTimeElement;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ModuleConditions getModuleConditions() {
        if (this.getModuleConditions == null) {
            AutoValue_ManifestDeliveryElement autoValue_ManifestDeliveryElement = this;
            synchronized (autoValue_ManifestDeliveryElement) {
                if (this.getModuleConditions == null) {
                    this.getModuleConditions = super.getModuleConditions();
                    if (this.getModuleConditions == null) {
                        throw new NullPointerException("getModuleConditions() cannot return null");
                    }
                }
            }
        }
        return this.getModuleConditions;
    }
}

