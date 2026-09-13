/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.google.errorprone.annotations.Immutable;

@Immutable
public final class XmlProtoNamespace {
    private final Resources.XmlNamespace namespace;

    public XmlProtoNamespace(Resources.XmlNamespace namespace) {
        this.namespace = namespace;
    }

    public Resources.XmlNamespace getProto() {
        return this.namespace;
    }

    public static XmlProtoNamespace create(String prefix, String uri) {
        return new XmlProtoNamespace(Resources.XmlNamespace.newBuilder().setPrefix(prefix).setUri(uri).build());
    }

    public String getPrefix() {
        return this.namespace.getPrefix();
    }

    public String getUri() {
        return this.namespace.getUri();
    }

    public int hashCode() {
        return this.namespace.hashCode();
    }

    public boolean equals(Object o3) {
        if (!(o3 instanceof XmlProtoNamespace)) {
            return false;
        }
        return this.namespace.equals(((XmlProtoNamespace)o3).getProto());
    }
}

