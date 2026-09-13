/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestValidationException;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoAttribute;
import com.google.common.collect.ImmutableSet;

public class ManifestDuplicateAttributeException
extends ManifestValidationException {
    private final String attributeName;
    private final String moduleName;

    public ManifestDuplicateAttributeException(String attributeName, ImmutableSet<XmlProtoAttribute> attributes, String moduleName) {
        super("The attribute '%s' cannot be declared more than once (module '%s', values %s).", attributeName, moduleName, attributes.stream().map(attr -> "'" + attr.getValueAsString() + "'").collect(ImmutableSet.toImmutableSet()));
        this.attributeName = attributeName;
        this.moduleName = moduleName;
    }

    @Override
    protected void customizeProto(Errors.BundleToolError.Builder builder) {
        builder.setManifestDuplicateAttribute(Errors.ManifestDuplicateAttributeError.newBuilder().setAttributeName(this.attributeName).setModuleName(this.moduleName));
    }
}

