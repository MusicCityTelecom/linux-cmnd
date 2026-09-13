/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v2;

import com.android.apksig.internal.apk.v2.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.v2.SignatureAlgorithm;
import com.android.apksig.internal.util.ChainedDataSource;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.util.VerityTreeBuilder;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class V2SchemeSigner {
    private static final int CONTENT_DIGESTED_CHUNK_MAX_SIZE_BYTES = 0x100000;
    private static final int ANDROID_COMMON_PAGE_ALIGNMENT_BYTES = 4096;
    private static final byte[] APK_SIGNING_BLOCK_MAGIC = new byte[]{65, 80, 75, 32, 83, 105, 103, 32, 66, 108, 111, 99, 107, 32, 52, 50};
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final int VERITY_PADDING_BLOCK_ID = 1114793335;

    private V2SchemeSigner() {
    }

    public static List<SignatureAlgorithm> getSuggestedSignatureAlgorithms(PublicKey signingKey, int minSdkVersion, boolean apkSigningBlockPaddingSupported) throws InvalidKeyException {
        String keyAlgorithm = signingKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(keyAlgorithm)) {
            int modulusLengthBits = ((RSAKey)((Object)signingKey)).getModulus().bitLength();
            if (modulusLengthBits <= 3072) {
                ArrayList<SignatureAlgorithm> algorithms = new ArrayList<SignatureAlgorithm>();
                algorithms.add(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA256);
                if (apkSigningBlockPaddingSupported) {
                    algorithms.add(SignatureAlgorithm.VERITY_RSA_PKCS1_V1_5_WITH_SHA256);
                }
                return algorithms;
            }
            return Collections.singletonList(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA512);
        }
        if ("DSA".equalsIgnoreCase(keyAlgorithm)) {
            ArrayList<SignatureAlgorithm> algorithms = new ArrayList<SignatureAlgorithm>();
            algorithms.add(SignatureAlgorithm.DSA_WITH_SHA256);
            if (apkSigningBlockPaddingSupported) {
                algorithms.add(SignatureAlgorithm.VERITY_DSA_WITH_SHA256);
            }
            return algorithms;
        }
        if ("EC".equalsIgnoreCase(keyAlgorithm)) {
            int keySizeBits = ((ECKey)((Object)signingKey)).getParams().getOrder().bitLength();
            if (keySizeBits <= 256) {
                ArrayList<SignatureAlgorithm> algorithms = new ArrayList<SignatureAlgorithm>();
                algorithms.add(SignatureAlgorithm.ECDSA_WITH_SHA256);
                if (apkSigningBlockPaddingSupported) {
                    algorithms.add(SignatureAlgorithm.VERITY_ECDSA_WITH_SHA256);
                }
                return algorithms;
            }
            return Collections.singletonList(SignatureAlgorithm.ECDSA_WITH_SHA512);
        }
        throw new InvalidKeyException("Unsupported key algorithm: " + keyAlgorithm);
    }

    public static Pair<byte[], Integer> generateApkSigningBlock(DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<SignerConfig> signerConfigs, boolean apkSigningBlockPaddingSupported) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Map<ContentDigestAlgorithm, byte[]> contentDigests;
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("No signer configs provided. At least one is required");
        }
        HashSet<ContentDigestAlgorithm> contentDigestAlgorithms = new HashSet<ContentDigestAlgorithm>(1);
        for (SignerConfig signerConfig : signerConfigs) {
            for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
                contentDigestAlgorithms.add(signatureAlgorithm.getContentDigestAlgorithm());
            }
        }
        int padSizeBeforeSigningBlock = 0;
        if (apkSigningBlockPaddingSupported && beforeCentralDir.size() % 4096L != 0L) {
            padSizeBeforeSigningBlock = (int)(4096L - beforeCentralDir.size() % 4096L);
            beforeCentralDir = new ChainedDataSource(beforeCentralDir, DataSources.asDataSource(ByteBuffer.allocate(padSizeBeforeSigningBlock)));
        }
        long centralDirOffsetForDigesting = beforeCentralDir.size();
        ByteBuffer eocdBuf = ByteBuffer.allocate((int)eocd.size());
        eocdBuf.order(ByteOrder.LITTLE_ENDIAN);
        eocd.copyTo(0L, (int)eocd.size(), eocdBuf);
        eocdBuf.flip();
        ZipUtils.setZipEocdCentralDirectoryOffset(eocdBuf, centralDirOffsetForDigesting);
        try {
            contentDigests = V2SchemeSigner.computeContentDigests(contentDigestAlgorithms, beforeCentralDir, centralDir, DataSources.asDataSource(eocdBuf));
        }
        catch (IOException e2) {
            throw new IOException("Failed to read APK being signed", e2);
        }
        catch (DigestException e3) {
            throw new SignatureException("Failed to compute digests of APK", e3);
        }
        return Pair.of(V2SchemeSigner.generateApkSigningBlock(signerConfigs, contentDigests), padSizeBeforeSigningBlock);
    }

    static Map<ContentDigestAlgorithm, byte[]> computeContentDigests(Set<ContentDigestAlgorithm> digestAlgorithms, DataSource beforeCentralDir, DataSource centralDir, DataSource eocd) throws IOException, NoSuchAlgorithmException, DigestException {
        HashMap<ContentDigestAlgorithm, byte[]> contentDigests = new HashMap<ContentDigestAlgorithm, byte[]>();
        Set<ContentDigestAlgorithm> oneMbChunkBasedAlgorithm = digestAlgorithms.stream().filter(a2 -> a2 == ContentDigestAlgorithm.CHUNKED_SHA256 || a2 == ContentDigestAlgorithm.CHUNKED_SHA512).collect(Collectors.toSet());
        V2SchemeSigner.computeOneMbChunkContentDigests(oneMbChunkBasedAlgorithm, new DataSource[]{beforeCentralDir, centralDir, eocd}, contentDigests);
        if (digestAlgorithms.contains((Object)ContentDigestAlgorithm.VERITY_CHUNKED_SHA256)) {
            V2SchemeSigner.computeApkVerityDigest(beforeCentralDir, centralDir, eocd, contentDigests);
        }
        return contentDigests;
    }

    /*
     * WARNING - void declaration
     */
    private static void computeOneMbChunkContentDigests(Set<ContentDigestAlgorithm> digestAlgorithms, DataSource[] contents, Map<ContentDigestAlgorithm, byte[]> outputContentDigests) throws IOException, NoSuchAlgorithmException, DigestException {
        void var13_19;
        long chunkCountLong = 0L;
        for (DataSource input : contents) {
            chunkCountLong += V2SchemeSigner.getChunkCount(input.size(), 0x100000);
        }
        if (chunkCountLong > Integer.MAX_VALUE) {
            throw new DigestException("Input too long: " + chunkCountLong + " chunks");
        }
        int chunkCount = (int)chunkCountLong;
        ContentDigestAlgorithm[] digestAlgorithmsArray = digestAlgorithms.toArray(new ContentDigestAlgorithm[digestAlgorithms.size()]);
        MessageDigest[] mds = new MessageDigest[digestAlgorithmsArray.length];
        byte[][] digestsOfChunks = new byte[digestAlgorithmsArray.length][];
        int[] digestOutputSizes = new int[digestAlgorithmsArray.length];
        for (int i2 = 0; i2 < digestAlgorithmsArray.length; ++i2) {
            int digestOutputSizeBytes;
            ContentDigestAlgorithm digestAlgorithm = digestAlgorithmsArray[i2];
            digestOutputSizes[i2] = digestOutputSizeBytes = digestAlgorithm.getChunkDigestOutputSizeBytes();
            byte[] byArray = new byte[5 + chunkCount * digestOutputSizeBytes];
            byArray[0] = 90;
            V2SchemeSigner.setUnsignedInt32LittleEndian(chunkCount, byArray, 1);
            digestsOfChunks[i2] = byArray;
            String jcaAlgorithm = digestAlgorithm.getJcaMessageDigestAlgorithm();
            mds[i2] = MessageDigest.getInstance(jcaAlgorithm);
        }
        DataSink mdSink = DataSinks.asDataSink(mds);
        byte[] chunkContentPrefix = new byte[5];
        chunkContentPrefix[0] = -91;
        int chunkIndex = 0;
        for (DataSource input : contents) {
            long inputOffset = 0L;
            long inputRemaining = input.size();
            while (inputRemaining > 0L) {
                int chunkSize = (int)Math.min(inputRemaining, 0x100000L);
                V2SchemeSigner.setUnsignedInt32LittleEndian(chunkSize, chunkContentPrefix, 1);
                for (int i3 = 0; i3 < mds.length; ++i3) {
                    mds[i3].update(chunkContentPrefix);
                }
                try {
                    input.feed(inputOffset, chunkSize, mdSink);
                }
                catch (IOException e2) {
                    throw new IOException("Failed to read chunk #" + chunkIndex, e2);
                }
                for (int i2 = 0; i2 < digestAlgorithmsArray.length; ++i2) {
                    MessageDigest md = mds[i2];
                    byte[] concatenationOfChunkCountAndChunkDigests = digestsOfChunks[i2];
                    int expectedDigestSizeBytes = digestOutputSizes[i2];
                    int actualDigestSizeBytes = md.digest(concatenationOfChunkCountAndChunkDigests, 5 + chunkIndex * expectedDigestSizeBytes, expectedDigestSizeBytes);
                    if (actualDigestSizeBytes == expectedDigestSizeBytes) continue;
                    throw new RuntimeException("Unexpected output size of " + md.getAlgorithm() + " digest: " + actualDigestSizeBytes);
                }
                inputOffset += (long)chunkSize;
                inputRemaining -= (long)chunkSize;
                ++chunkIndex;
            }
        }
        boolean bl = false;
        while (var13_19 < digestAlgorithmsArray.length) {
            ContentDigestAlgorithm digestAlgorithm = digestAlgorithmsArray[var13_19];
            byte[] concatenationOfChunkCountAndChunkDigests = digestsOfChunks[var13_19];
            MessageDigest md = mds[var13_19];
            byte[] digest = md.digest(concatenationOfChunkCountAndChunkDigests);
            outputContentDigests.put(digestAlgorithm, digest);
            ++var13_19;
        }
    }

    private static void computeApkVerityDigest(DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, Map<ContentDigestAlgorithm, byte[]> outputContentDigests) throws IOException, NoSuchAlgorithmException {
        VerityTreeBuilder builder = new VerityTreeBuilder(new byte[8]);
        outputContentDigests.put(ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, builder.generateVerityTreeRootHash(beforeCentralDir, centralDir, eocd));
    }

    private static final long getChunkCount(long inputSize, int chunkSize) {
        return (inputSize + (long)chunkSize - 1L) / (long)chunkSize;
    }

    private static void setUnsignedInt32LittleEndian(int value, byte[] result, int offset) {
        result[offset] = (byte)(value & 0xFF);
        result[offset + 1] = (byte)(value >> 8 & 0xFF);
        result[offset + 2] = (byte)(value >> 16 & 0xFF);
        result[offset + 3] = (byte)(value >> 24 & 0xFF);
    }

    private static byte[] generateApkSigningBlock(List<SignerConfig> signerConfigs, Map<ContentDigestAlgorithm, byte[]> contentDigests) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        byte[] apkSignatureSchemeV2Block = V2SchemeSigner.generateApkSignatureSchemeV2Block(signerConfigs, contentDigests);
        return V2SchemeSigner.generateApkSigningBlock(apkSignatureSchemeV2Block);
    }

    private static byte[] generateApkSigningBlock(byte[] apkSignatureSchemeV2Block) {
        int resultSize = 20 + apkSignatureSchemeV2Block.length + 8 + 16;
        ByteBuffer paddingPair = null;
        if (resultSize % 4096 != 0) {
            int padding = 4096 - resultSize % 4096;
            if (padding < 12) {
                padding += 4096;
            }
            paddingPair = ByteBuffer.allocate(padding).order(ByteOrder.LITTLE_ENDIAN);
            paddingPair.putLong(padding - 8);
            paddingPair.putInt(1114793335);
            paddingPair.rewind();
            resultSize += padding;
        }
        ByteBuffer result = ByteBuffer.allocate(resultSize);
        result.order(ByteOrder.LITTLE_ENDIAN);
        long blockSizeFieldValue = (long)resultSize - 8L;
        result.putLong(blockSizeFieldValue);
        long pairSizeFieldValue = 4L + (long)apkSignatureSchemeV2Block.length;
        result.putLong(pairSizeFieldValue);
        result.putInt(1896449818);
        result.put(apkSignatureSchemeV2Block);
        if (paddingPair != null) {
            result.put(paddingPair);
        }
        result.putLong(blockSizeFieldValue);
        result.put(APK_SIGNING_BLOCK_MAGIC);
        return result.array();
    }

    private static byte[] generateApkSignatureSchemeV2Block(List<SignerConfig> signerConfigs, Map<ContentDigestAlgorithm, byte[]> contentDigests) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ArrayList<byte[]> signerBlocks = new ArrayList<byte[]>(signerConfigs.size());
        int signerNumber = 0;
        for (SignerConfig signerConfig : signerConfigs) {
            byte[] signerBlock;
            ++signerNumber;
            try {
                signerBlock = V2SchemeSigner.generateSignerBlock(signerConfig, contentDigests);
            }
            catch (InvalidKeyException e2) {
                throw new InvalidKeyException("Signer #" + signerNumber + " failed", e2);
            }
            catch (SignatureException e3) {
                throw new SignatureException("Signer #" + signerNumber + " failed", e3);
            }
            signerBlocks.add(signerBlock);
        }
        return V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements(signerBlocks)});
    }

    private static byte[] generateSignerBlock(SignerConfig signerConfig, Map<ContentDigestAlgorithm, byte[]> contentDigests) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (signerConfig.certificates.isEmpty()) {
            throw new SignatureException("No certificates configured for signer");
        }
        PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey();
        byte[] encodedPublicKey = V2SchemeSigner.encodePublicKey(publicKey);
        V2SignatureSchemeBlock.SignedData signedData = new V2SignatureSchemeBlock.SignedData();
        try {
            signedData.certificates = V2SchemeSigner.encodeCertificates(signerConfig.certificates);
        }
        catch (CertificateEncodingException e2) {
            throw new SignatureException("Failed to encode certificates", e2);
        }
        ArrayList<Pair<Integer, byte[]>> digests = new ArrayList<Pair<Integer, byte[]>>(signerConfig.signatureAlgorithms.size());
        for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
            ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithm.getContentDigestAlgorithm();
            byte[] contentDigest = contentDigests.get((Object)contentDigestAlgorithm);
            if (contentDigest == null) {
                throw new RuntimeException((Object)((Object)contentDigestAlgorithm) + " content digest for " + (Object)((Object)signatureAlgorithm) + " not computed");
            }
            digests.add(Pair.of(signatureAlgorithm.getId(), contentDigest));
        }
        signedData.digests = digests;
        V2SignatureSchemeBlock.Signer signer = new V2SignatureSchemeBlock.Signer();
        signer.signedData = V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{V2SchemeSigner.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signedData.digests), V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements(signedData.certificates), new byte[0]});
        signer.publicKey = encodedPublicKey;
        signer.signatures = new ArrayList<Pair<Integer, byte[]>>(signerConfig.signatureAlgorithms.size());
        for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
            byte[] signatureBytes;
            Signature signature;
            Pair<String, ? extends AlgorithmParameterSpec> sigAlgAndParams = signatureAlgorithm.getJcaSignatureAlgorithmAndParams();
            String jcaSignatureAlgorithm = sigAlgAndParams.getFirst();
            AlgorithmParameterSpec jcaSignatureAlgorithmParams = sigAlgAndParams.getSecond();
            try {
                signature = Signature.getInstance(jcaSignatureAlgorithm);
                signature.initSign(signerConfig.privateKey);
                if (jcaSignatureAlgorithmParams != null) {
                    signature.setParameter(jcaSignatureAlgorithmParams);
                }
                signature.update(signer.signedData);
                signatureBytes = signature.sign();
            }
            catch (InvalidKeyException e3) {
                throw new InvalidKeyException("Failed to sign using " + jcaSignatureAlgorithm, e3);
            }
            catch (InvalidAlgorithmParameterException | SignatureException e4) {
                throw new SignatureException("Failed to sign using " + jcaSignatureAlgorithm, e4);
            }
            try {
                signature = Signature.getInstance(jcaSignatureAlgorithm);
                signature.initVerify(publicKey);
                if (jcaSignatureAlgorithmParams != null) {
                    signature.setParameter(jcaSignatureAlgorithmParams);
                }
                signature.update(signer.signedData);
                if (!signature.verify(signatureBytes)) {
                    throw new SignatureException("Signature did not verify");
                }
            }
            catch (InvalidKeyException e5) {
                throw new InvalidKeyException("Failed to verify generated " + jcaSignatureAlgorithm + " signature using public key from certificate", e5);
            }
            catch (InvalidAlgorithmParameterException | SignatureException e6) {
                throw new SignatureException("Failed to verify generated " + jcaSignatureAlgorithm + " signature using public key from certificate", e6);
            }
            signer.signatures.add(Pair.of(signatureAlgorithm.getId(), signatureBytes));
        }
        return V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{signer.signedData, V2SchemeSigner.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signer.signatures), signer.publicKey});
    }

    private static byte[] encodePublicKey(PublicKey publicKey) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] encodedPublicKey = null;
        if ("X.509".equals(publicKey.getFormat())) {
            encodedPublicKey = publicKey.getEncoded();
        }
        if (encodedPublicKey == null) {
            try {
                encodedPublicKey = KeyFactory.getInstance(publicKey.getAlgorithm()).getKeySpec(publicKey, X509EncodedKeySpec.class).getEncoded();
            }
            catch (InvalidKeySpecException e2) {
                throw new InvalidKeyException("Failed to obtain X.509 encoded form of public key " + publicKey + " of class " + publicKey.getClass().getName(), e2);
            }
        }
        if (encodedPublicKey == null || encodedPublicKey.length == 0) {
            throw new InvalidKeyException("Failed to obtain X.509 encoded form of public key " + publicKey + " of class " + publicKey.getClass().getName());
        }
        return encodedPublicKey;
    }

    private static List<byte[]> encodeCertificates(List<X509Certificate> certificates) throws CertificateEncodingException {
        ArrayList<byte[]> result = new ArrayList<byte[]>(certificates.size());
        for (X509Certificate certificate : certificates) {
            result.add(certificate.getEncoded());
        }
        return result;
    }

    private static byte[] encodeAsSequenceOfLengthPrefixedElements(List<byte[]> sequence) {
        return V2SchemeSigner.encodeAsSequenceOfLengthPrefixedElements((byte[][])sequence.toArray((T[])new byte[sequence.size()][]));
    }

    private static byte[] encodeAsSequenceOfLengthPrefixedElements(byte[][] sequence) {
        int payloadSize = 0;
        for (byte[] element : sequence) {
            payloadSize += 4 + element.length;
        }
        ByteBuffer result = ByteBuffer.allocate(payloadSize);
        result.order(ByteOrder.LITTLE_ENDIAN);
        for (byte[] element : sequence) {
            result.putInt(element.length);
            result.put(element);
        }
        return result.array();
    }

    private static byte[] encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(List<Pair<Integer, byte[]>> sequence) {
        int resultSize = 0;
        for (Pair<Integer, byte[]> element : sequence) {
            resultSize += 12 + element.getSecond().length;
        }
        ByteBuffer result = ByteBuffer.allocate(resultSize);
        result.order(ByteOrder.LITTLE_ENDIAN);
        for (Pair<Integer, byte[]> element : sequence) {
            byte[] second = element.getSecond();
            result.putInt(8 + second.length);
            result.putInt(element.getFirst());
            result.putInt(second.length);
            result.put(second);
        }
        return result.array();
    }

    private static final class V2SignatureSchemeBlock {
        private V2SignatureSchemeBlock() {
        }

        private static final class SignedData {
            public List<Pair<Integer, byte[]>> digests;
            public List<byte[]> certificates;

            private SignedData() {
            }
        }

        private static final class Signer {
            public byte[] signedData;
            public List<Pair<Integer, byte[]>> signatures;
            public byte[] publicKey;

            private Signer() {
            }
        }
    }

    public static class SignerConfig {
        public PrivateKey privateKey;
        public List<X509Certificate> certificates;
        public List<SignatureAlgorithm> signatureAlgorithms;
    }
}

