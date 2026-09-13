/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.BlockCipher
 *  org.bouncycastle.crypto.modes.AEADBlockCipher
 *  org.bouncycastle.crypto.modes.CCMBlockCipher
 *  org.bouncycastle.crypto.modes.EAXBlockCipher
 *  org.bouncycastle.crypto.modes.GCMBlockCipher
 *  org.bouncycastle.crypto.modes.OCBBlockCipher
 */
package org.cryptacular.spec;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.OCBBlockCipher;
import org.cryptacular.spec.BlockCipherSpec;
import org.cryptacular.spec.Spec;

public class AEADBlockCipherSpec
implements Spec<AEADBlockCipher> {
    public static final Pattern FORMAT = Pattern.compile("(?<alg>[A-Za-z0-9_-]+)/(?<mode>\\w+)");
    private final String algorithm;
    private final String mode;

    public AEADBlockCipherSpec(String algName, String cipherMode) {
        this.algorithm = algName;
        this.mode = cipherMode;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    public String getMode() {
        return this.mode;
    }

    @Override
    public AEADBlockCipher newInstance() {
        GCMBlockCipher aeadBlockCipher;
        BlockCipher blockCipher = new BlockCipherSpec(this.algorithm).newInstance();
        switch (this.mode) {
            case "GCM": {
                aeadBlockCipher = new GCMBlockCipher(blockCipher);
                break;
            }
            case "CCM": {
                aeadBlockCipher = new CCMBlockCipher(blockCipher);
                break;
            }
            case "OCB": {
                aeadBlockCipher = new OCBBlockCipher(blockCipher, new BlockCipherSpec(this.algorithm).newInstance());
                break;
            }
            case "EAX": {
                aeadBlockCipher = new EAXBlockCipher(blockCipher);
                break;
            }
            default: {
                throw new IllegalStateException("Unsupported mode " + this.mode);
            }
        }
        return aeadBlockCipher;
    }

    public String toString() {
        return this.algorithm + '/' + this.mode;
    }

    public static AEADBlockCipherSpec parse(String specification) {
        Matcher m = FORMAT.matcher(specification);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid specification " + specification);
        }
        return new AEADBlockCipherSpec(m.group("alg"), m.group("mode"));
    }
}

