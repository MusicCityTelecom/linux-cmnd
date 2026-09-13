/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zfile;

import com.android.tools.build.apkzlib.sign.ManifestGenerationExtension;
import com.android.tools.build.apkzlib.sign.SigningExtension;
import com.android.tools.build.apkzlib.sign.SigningOptions;
import com.android.tools.build.apkzlib.zip.AlignmentRule;
import com.android.tools.build.apkzlib.zip.AlignmentRules;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZFileOptions;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.annotation.Nullable;

public class ZFiles {
    private static final AlignmentRule APK_DEFAULT_RULE = AlignmentRules.constant(4);
    private static final String DEFAULT_BUILD_BY = "Generated-by-ADT";
    private static final String DEFAULT_CREATED_BY = "Generated-by-ADT";

    public static ZFile apk(File f2, ZFileOptions options) throws IOException {
        options.setAlignmentRule(AlignmentRules.compose(options.getAlignmentRule(), APK_DEFAULT_RULE));
        return ZFile.openReadWrite(f2, options);
    }

    @Deprecated
    public static ZFile apk(File f2, ZFileOptions options, @Nullable PrivateKey key, @Nullable X509Certificate certificate, boolean v1SigningEnabled, boolean v2SigningEnabled, @Nullable String builtBy, @Nullable String createdBy, int minSdkVersion) throws IOException {
        return ZFiles.apk(f2, options, key, certificate == null ? ImmutableList.of() : ImmutableList.of(certificate), v1SigningEnabled, v2SigningEnabled, builtBy, createdBy, minSdkVersion);
    }

    @Deprecated
    public static ZFile apk(File f2, ZFileOptions options, @Nullable PrivateKey key, ImmutableList<X509Certificate> certificates, boolean v1SigningEnabled, boolean v2SigningEnabled, @Nullable String builtBy, @Nullable String createdBy, int minSdkVersion) throws IOException {
        Optional<SigningOptions> signingOptions = key == null ? Optional.absent() : Optional.of(SigningOptions.builder().setKey(key).setCertificates(certificates).setV1SigningEnabled(v1SigningEnabled).setV2SigningEnabled(v2SigningEnabled).setMinSdkVersion(minSdkVersion).build());
        return ZFiles.apk(f2, options, signingOptions, builtBy, createdBy);
    }

    public static ZFile apk(File f2, ZFileOptions options, Optional<SigningOptions> signingOptions, @Nullable String builtBy, @Nullable String createdBy) throws IOException {
        ZFile zfile = ZFiles.apk(f2, options);
        if (builtBy == null) {
            builtBy = "Generated-by-ADT";
        }
        if (createdBy == null) {
            createdBy = "Generated-by-ADT";
        }
        ManifestGenerationExtension manifestExt = new ManifestGenerationExtension(builtBy, createdBy);
        manifestExt.register(zfile);
        if (signingOptions.isPresent()) {
            try {
                new SigningExtension(signingOptions.get()).register(zfile);
            }
            catch (InvalidKeyException | NoSuchAlgorithmException e2) {
                throw new IOException("Failed to create signature extensions", e2);
            }
        }
        return zfile;
    }
}

