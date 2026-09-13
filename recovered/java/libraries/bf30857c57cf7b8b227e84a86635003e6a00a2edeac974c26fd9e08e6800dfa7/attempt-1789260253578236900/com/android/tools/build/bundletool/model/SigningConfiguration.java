/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model;

import com.android.tools.build.bundletool.model.AutoValue_SigningConfiguration;
import com.android.tools.build.bundletool.model.Password;
import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;

@Immutable
@AutoValue
@AutoValue.CopyAnnotations
public abstract class SigningConfiguration {
    SigningConfiguration() {
    }

    public abstract PrivateKey getPrivateKey();

    public abstract ImmutableList<X509Certificate> getCertificates();

    public static Builder builder() {
        return new AutoValue_SigningConfiguration.Builder();
    }

    /*
     * Exception decompiling
     */
    public static SigningConfiguration extractFromKeystore(Path keystorePath, String keyAlias, Optional<Password> optionalKeystorePassword, Optional<Password> optionalKeyPassword) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static SigningConfiguration readSigningConfigFromLoadedKeyStore(KeyStore keystore, String keyAlias, char[] keyPassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        PrivateKey privateKey = (PrivateKey)keystore.getKey(keyAlias, keyPassword);
        Certificate[] certChain = keystore.getCertificateChain(keyAlias);
        if (certChain == null) {
            throw CommandExecutionException.builder().withMessage("No key found with alias '%s' in keystore.", keyAlias).build();
        }
        ImmutableList<X509Certificate> certificates = Arrays.stream(certChain).map(c2 -> (X509Certificate)c2).collect(ImmutableList.toImmutableList());
        return SigningConfiguration.builder().setPrivateKey(privateKey).setCertificates(certificates).build();
    }

    private static /* synthetic */ KeyStore.PasswordProtection lambda$extractFromKeystore$0() {
        return new KeyStore.PasswordProtection(System.console().readPassword("Enter keystore password: ", new Object[0]));
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder setPrivateKey(PrivateKey var1);

        public abstract Builder setCertificates(ImmutableList<X509Certificate> var1);

        public abstract SigningConfiguration build();
    }
}

