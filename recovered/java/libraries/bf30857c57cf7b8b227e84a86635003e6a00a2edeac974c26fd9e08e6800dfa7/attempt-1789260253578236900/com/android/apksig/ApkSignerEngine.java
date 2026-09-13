/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

public interface ApkSignerEngine
extends Closeable {
    public void inputApkSigningBlock(DataSource var1) throws IOException, ApkFormatException, IllegalStateException;

    public InputJarEntryInstructions inputJarEntry(String var1) throws IllegalStateException;

    public InspectJarEntryRequest outputJarEntry(String var1) throws IllegalStateException;

    public InputJarEntryInstructions.OutputPolicy inputJarEntryRemoved(String var1) throws IllegalStateException;

    public void outputJarEntryRemoved(String var1) throws IllegalStateException;

    public OutputJarSignatureRequest outputJarEntries() throws ApkFormatException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IllegalStateException;

    @Deprecated
    public OutputApkSigningBlockRequest outputZipSections(DataSource var1, DataSource var2, DataSource var3) throws IOException, ApkFormatException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IllegalStateException;

    public OutputApkSigningBlockRequest2 outputZipSections2(DataSource var1, DataSource var2, DataSource var3) throws IOException, ApkFormatException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IllegalStateException;

    public void outputDone() throws IllegalStateException;

    @Override
    public void close();

    public static interface OutputApkSigningBlockRequest2 {
        public byte[] getApkSigningBlock();

        public void done();

        public int getPaddingSizeBeforeApkSigningBlock();
    }

    @Deprecated
    public static interface OutputApkSigningBlockRequest {
        public byte[] getApkSigningBlock();

        public void done();
    }

    public static interface OutputJarSignatureRequest {
        public List<JarEntry> getAdditionalJarEntries();

        public void done();

        public static class JarEntry {
            private final String mName;
            private final byte[] mData;

            public JarEntry(String name, byte[] data) {
                this.mName = name;
                this.mData = (byte[])data.clone();
            }

            public String getName() {
                return this.mName;
            }

            public byte[] getData() {
                return (byte[])this.mData.clone();
            }
        }
    }

    public static interface InspectJarEntryRequest {
        public DataSink getDataSink();

        public void done();

        public String getEntryName();
    }

    public static class InputJarEntryInstructions {
        private final OutputPolicy mOutputPolicy;
        private final InspectJarEntryRequest mInspectJarEntryRequest;

        public InputJarEntryInstructions(OutputPolicy outputPolicy) {
            this(outputPolicy, null);
        }

        public InputJarEntryInstructions(OutputPolicy outputPolicy, InspectJarEntryRequest inspectJarEntryRequest) {
            this.mOutputPolicy = outputPolicy;
            this.mInspectJarEntryRequest = inspectJarEntryRequest;
        }

        public OutputPolicy getOutputPolicy() {
            return this.mOutputPolicy;
        }

        public InspectJarEntryRequest getInspectJarEntryRequest() {
            return this.mInspectJarEntryRequest;
        }

        public static enum OutputPolicy {
            SKIP,
            OUTPUT,
            OUTPUT_BY_ENGINE;

        }
    }
}

