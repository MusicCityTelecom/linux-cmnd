/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.ManifestDeliveryElement;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoElement;

abstract class $AutoValue_ManifestDeliveryElement
extends ManifestDeliveryElement {
    private final XmlProtoElement deliveryElement;
    private final boolean fastFollowAllowed;

    $AutoValue_ManifestDeliveryElement(XmlProtoElement deliveryElement, boolean fastFollowAllowed) {
        if (deliveryElement == null) {
            throw new NullPointerException("Null deliveryElement");
        }
        this.deliveryElement = deliveryElement;
        this.fastFollowAllowed = fastFollowAllowed;
    }

    @Override
    XmlProtoElement getDeliveryElement() {
        return this.deliveryElement;
    }

    @Override
    boolean isFastFollowAllowed() {
        return this.fastFollowAllowed;
    }

    public String toString() {
        return "ManifestDeliveryElement{deliveryElement=" + this.deliveryElement + ", fastFollowAllowed=" + this.fastFollowAllowed + "}";
    }

    public boolean equals(Object o3) {
        if (o3 == this) {
            return true;
        }
        if (o3 instanceof ManifestDeliveryElement) {
            ManifestDeliveryElement that = (ManifestDeliveryElement)o3;
            return this.deliveryElement.equals(that.getDeliveryElement()) && this.fastFollowAllowed == that.isFastFollowAllowed();
        }
        return false;
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.deliveryElement.hashCode();
        h$ *= 1000003;
        return h$ ^= this.fastFollowAllowed ? 1231 : 1237;
    }
}

