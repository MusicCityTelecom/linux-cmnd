/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.jasypt.util.numeric.AES256IntegerNumberEncryptor
 */
package org.apereo.cas.util.cipher;

import java.math.BigInteger;
import java.security.Provider;
import java.security.Security;
import lombok.Generated;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.util.numeric.AES256IntegerNumberEncryptor;

public class JasyptNumberCipherExecutor
implements CipherExecutor<Number, Number> {
    private final AES256IntegerNumberEncryptor jasyptInstance = new AES256IntegerNumberEncryptor();
    private final String name;

    public JasyptNumberCipherExecutor(String password, String name) {
        this.jasyptInstance.setPassword(password);
        this.name = name;
    }

    public Number encode(Number value, Object[] parameters) {
        BigInteger input = new BigInteger(value.toString());
        return this.jasyptInstance.encrypt(input);
    }

    public Number decode(Number value, Object[] parameters) {
        BigInteger input = new BigInteger(value.toString());
        return this.jasyptInstance.decrypt(input);
    }

    @Generated
    public String getName() {
        return this.name;
    }

    static {
        Security.addProvider((Provider)new BouncyCastleProvider());
    }
}

