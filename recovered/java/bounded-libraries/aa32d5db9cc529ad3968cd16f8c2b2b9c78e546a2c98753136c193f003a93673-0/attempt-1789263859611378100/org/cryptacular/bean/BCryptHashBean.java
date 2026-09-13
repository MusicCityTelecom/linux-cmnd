/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.crypto.generators.BCrypt
 */
package org.cryptacular.bean;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import org.bouncycastle.crypto.generators.BCrypt;
import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;
import org.cryptacular.bean.HashBean;
import org.cryptacular.codec.Base64Decoder;
import org.cryptacular.codec.Base64Encoder;
import org.cryptacular.util.ByteUtil;

public class BCryptHashBean
implements HashBean<String> {
    private static final String ALPHABET = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private int cost = 12;
    private String version = "2b";

    public BCryptHashBean() {
    }

    public BCryptHashBean(int costFactor) {
        this.setCost(costFactor);
    }

    public void setCost(int costFactor) {
        if (costFactor < 4 || costFactor > 31) {
            throw new IllegalArgumentException("Cost must be in the range [4, 31].");
        }
        this.cost = costFactor;
    }

    public void setVersion(String ver) {
        if (!ver.startsWith("2") && ver.length() <= 2) {
            throw new IllegalArgumentException("Invalid version: " + ver);
        }
        this.version = ver;
    }

    @Override
    public String hash(Object ... data) throws CryptoException {
        if (data.length != 2) {
            throw new IllegalArgumentException("Expected exactly two elements in data array but got " + data.length);
        }
        return BCryptHashBean.encode(BCrypt.generate((byte[])BCryptHashBean.password(this.version, data[1]), (byte[])BCryptHashBean.salt(data[0]), (int)this.cost), 23);
    }

    @Override
    public boolean compare(String hash, Object ... data) throws CryptoException, StreamException {
        if (data.length != 1) {
            throw new IllegalArgumentException("Expected exactly one element in data array but got " + data.length);
        }
        BCryptParameters params = new BCryptParameters(hash);
        byte[] computed = BCrypt.generate((byte[])BCryptHashBean.password(params.getVersion(), data[0]), (byte[])params.getSalt(), (int)params.getCost());
        for (int i = 0; i < 23; ++i) {
            if (params.getHash()[i] == computed[i]) continue;
            return false;
        }
        return true;
    }

    private static String encode(byte[] bytes, int length) {
        Base64Encoder encoder = new Base64Encoder.Builder().setAlphabet(ALPHABET).setPadding(false).build();
        ByteBuffer input = ByteBuffer.wrap(bytes, 0, length);
        CharBuffer output = CharBuffer.allocate(encoder.outputSize(length));
        encoder.encode(input, output);
        encoder.finalize(output);
        return output.flip().toString();
    }

    private static byte[] decode(String input, int length) {
        Base64Decoder decoder = new Base64Decoder.Builder().setAlphabet(ALPHABET).setPadding(false).build();
        ByteBuffer output = ByteBuffer.allocate(decoder.outputSize(input.length()));
        decoder.decode(CharBuffer.wrap(input), output);
        decoder.finalize(output);
        output.flip();
        if (output.limit() != length) {
            throw new IllegalArgumentException("Input is not of the expected size: " + output.limit() + "!=" + length);
        }
        return ByteUtil.toArray(output);
    }

    private static byte[] salt(Object data) {
        if (data instanceof byte[]) {
            return (byte[])data;
        }
        if (data instanceof String) {
            return BCryptHashBean.decode((String)data, 16);
        }
        throw new IllegalArgumentException("Expected byte array or base-64 string.");
    }

    private static byte[] password(String version, Object data) {
        if (data instanceof byte[]) {
            byte[] newData;
            byte[] origData = (byte[])data;
            if (origData[origData.length - 1] != 0) {
                newData = new byte[origData.length + 1];
                System.arraycopy(origData, 0, newData, 0, origData.length);
                newData[newData.length - 1] = 0;
            } else {
                newData = origData;
            }
            return newData;
        }
        StringBuilder sb = new StringBuilder();
        if (data instanceof char[]) {
            sb.append((char[])data);
        } else if (data instanceof String) {
            sb.append((String)data);
        } else {
            throw new IllegalArgumentException("Expected byte array or string.");
        }
        if (sb.charAt(sb.length() - 1) != '\u0000') {
            sb.append('\u0000');
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static class BCryptParameters {
        private final String version;
        private final int cost;
        private final byte[] salt;
        private final byte[] hash;

        protected BCryptParameters(String bCryptString) {
            if (!bCryptString.startsWith("$2")) {
                throw new IllegalArgumentException("Expected bcrypt hash of the form $2n$cost$salthash");
            }
            String[] parts = bCryptString.split("\\$");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid bcrypt hash");
            }
            this.version = parts[1];
            this.cost = Integer.parseInt(parts[2]);
            this.salt = BCryptHashBean.decode(parts[3].substring(0, 22), 16);
            this.hash = BCryptHashBean.decode(parts[3].substring(22), 23);
        }

        public String getVersion() {
            return this.version;
        }

        public int getCost() {
            return this.cost;
        }

        public byte[] getSalt() {
            return this.salt;
        }

        public byte[] getHash() {
            return this.hash;
        }

        public String encode() {
            return new StringBuilder(60).append('$').append(this.version).append('$').append(this.cost).append('$').append(BCryptHashBean.encode(this.salt, 16)).append(BCryptHashBean.encode(this.hash, 23)).toString();
        }

        public String encode(String hash) {
            return new StringBuilder(60).append('$').append(this.version).append('$').append(this.cost).append('$').append(BCryptHashBean.encode(this.salt, 16)).append(hash).toString();
        }
    }
}

