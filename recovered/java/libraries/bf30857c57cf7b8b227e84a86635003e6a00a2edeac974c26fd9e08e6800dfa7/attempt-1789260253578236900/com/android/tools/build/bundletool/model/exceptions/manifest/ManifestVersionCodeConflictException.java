/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions.manifest;

import com.android.bundle.Errors;
import com.android.tools.build.bundletool.model.exceptions.manifest.ManifestValidationException;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class ManifestVersionCodeConflictException
extends ManifestValidationException {
    private static final Joiner COMMA_JOINER = Joiner.on(',');
    private final ImmutableList<Integer> versionCodes;

    public ManifestVersionCodeConflictException(Integer ... versionCodes) {
        super("App Bundle modules should have the same version code but found [%s].", COMMA_JOINER.join(versionCodes));
        this.versionCodes = ImmutableList.copyOf(versionCodes);
    }

    @Override
    protected void customizeProto(Errors.BundleToolError.Builder builder) {
        builder.setManifestModulesDifferentVersionCodes(Errors.ManifestModulesDifferentVersionCodes.newBuilder().addAllVersionCodes(this.versionCodes));
    }
}

