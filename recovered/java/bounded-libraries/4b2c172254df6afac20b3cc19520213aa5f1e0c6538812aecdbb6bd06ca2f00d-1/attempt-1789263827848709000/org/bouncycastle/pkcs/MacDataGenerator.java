/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.pkcs.MacData
 *  org.bouncycastle.asn1.pkcs.PKCS12PBEParams
 *  org.bouncycastle.asn1.x509.DigestInfo
 */
package org.bouncycastle.pkcs;

import java.io.OutputStream;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.pkcs.PKCS12MacCalculatorBuilder;
import org.bouncycastle.pkcs.PKCSException;

class MacDataGenerator {
    private PKCS12MacCalculatorBuilder builder;

    MacDataGenerator(PKCS12MacCalculatorBuilder pKCS12MacCalculatorBuilder) {
        this.builder = pKCS12MacCalculatorBuilder;
    }

    public MacData build(char[] cArray, byte[] byArray) throws PKCSException {
        OutputStream outputStream;
        MacCalculator macCalculator;
        try {
            macCalculator = this.builder.build(cArray);
            outputStream = macCalculator.getOutputStream();
            outputStream.write(byArray);
            outputStream.close();
        }
        catch (Exception exception) {
            throw new PKCSException("unable to process data: " + exception.getMessage(), exception);
        }
        outputStream = macCalculator.getAlgorithmIdentifier();
        DigestInfo digestInfo = new DigestInfo(this.builder.getDigestAlgorithmIdentifier(), macCalculator.getMac());
        PKCS12PBEParams pKCS12PBEParams = PKCS12PBEParams.getInstance((Object)outputStream.getParameters());
        return new MacData(digestInfo, pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
    }
}

