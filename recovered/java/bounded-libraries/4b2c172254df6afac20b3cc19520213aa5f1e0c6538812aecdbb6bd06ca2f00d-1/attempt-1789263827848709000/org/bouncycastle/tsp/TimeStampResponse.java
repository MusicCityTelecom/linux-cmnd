/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1InputStream
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.DLSequence
 *  org.bouncycastle.asn1.cmp.PKIFailureInfo
 *  org.bouncycastle.asn1.cmp.PKIFreeText
 *  org.bouncycastle.asn1.cms.Attribute
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  org.bouncycastle.asn1.tsp.TimeStampResp
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.tsp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.cmp.PKIFreeText;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TSPValidationException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.bouncycastle.util.Arrays;

public class TimeStampResponse {
    TimeStampResp resp;
    TimeStampToken timeStampToken;

    public TimeStampResponse(TimeStampResp timeStampResp) throws TSPException, IOException {
        this.resp = timeStampResp;
        if (timeStampResp.getTimeStampToken() != null) {
            this.timeStampToken = new TimeStampToken(timeStampResp.getTimeStampToken());
        }
    }

    public TimeStampResponse(byte[] byArray) throws TSPException, IOException {
        this(new ByteArrayInputStream(byArray));
    }

    public TimeStampResponse(InputStream inputStream) throws TSPException, IOException {
        this(TimeStampResponse.readTimeStampResp(inputStream));
    }

    TimeStampResponse(DLSequence dLSequence) throws TSPException, IOException {
        try {
            this.resp = TimeStampResp.getInstance((Object)dLSequence);
            this.timeStampToken = new TimeStampToken(ContentInfo.getInstance((Object)dLSequence.getObjectAt(1)));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new TSPException("malformed timestamp response: " + illegalArgumentException, illegalArgumentException);
        }
        catch (ClassCastException classCastException) {
            throw new TSPException("malformed timestamp response: " + classCastException, classCastException);
        }
    }

    private static TimeStampResp readTimeStampResp(InputStream inputStream) throws IOException, TSPException {
        try {
            return TimeStampResp.getInstance((Object)new ASN1InputStream(inputStream).readObject());
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new TSPException("malformed timestamp response: " + illegalArgumentException, illegalArgumentException);
        }
        catch (ClassCastException classCastException) {
            throw new TSPException("malformed timestamp response: " + classCastException, classCastException);
        }
    }

    public int getStatus() {
        return this.resp.getStatus().getStatus().intValue();
    }

    public String getStatusString() {
        if (this.resp.getStatus().getStatusString() != null) {
            StringBuffer stringBuffer = new StringBuffer();
            PKIFreeText pKIFreeText = this.resp.getStatus().getStatusString();
            for (int i = 0; i != pKIFreeText.size(); ++i) {
                stringBuffer.append(pKIFreeText.getStringAtUTF8(i).getString());
            }
            return stringBuffer.toString();
        }
        return null;
    }

    public PKIFailureInfo getFailInfo() {
        if (this.resp.getStatus().getFailInfo() != null) {
            return new PKIFailureInfo(this.resp.getStatus().getFailInfo());
        }
        return null;
    }

    public TimeStampToken getTimeStampToken() {
        return this.timeStampToken;
    }

    public void validate(TimeStampRequest timeStampRequest) throws TSPException {
        TimeStampToken timeStampToken = this.getTimeStampToken();
        if (timeStampToken != null) {
            TimeStampTokenInfo timeStampTokenInfo = timeStampToken.getTimeStampInfo();
            if (timeStampRequest.getNonce() != null && !timeStampRequest.getNonce().equals(timeStampTokenInfo.getNonce())) {
                throw new TSPValidationException("response contains wrong nonce value.");
            }
            if (this.getStatus() != 0 && this.getStatus() != 1) {
                throw new TSPValidationException("time stamp token found in failed request.");
            }
            if (!Arrays.constantTimeAreEqual((byte[])timeStampRequest.getMessageImprintDigest(), (byte[])timeStampTokenInfo.getMessageImprintDigest())) {
                throw new TSPValidationException("response for different message imprint digest.");
            }
            if (!timeStampTokenInfo.getMessageImprintAlgOID().equals((ASN1Primitive)timeStampRequest.getMessageImprintAlgOID())) {
                throw new TSPValidationException("response for different message imprint algorithm.");
            }
            Attribute attribute = timeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificate);
            Attribute attribute2 = timeStampToken.getSignedAttributes().get(PKCSObjectIdentifiers.id_aa_signingCertificateV2);
            if (attribute == null && attribute2 == null) {
                throw new TSPValidationException("no signing certificate attribute present.");
            }
            if (attribute == null || attribute2 != null) {
                // empty if block
            }
            if (timeStampRequest.getReqPolicy() != null && !timeStampRequest.getReqPolicy().equals((ASN1Primitive)timeStampTokenInfo.getPolicy())) {
                throw new TSPValidationException("TSA policy wrong for request.");
            }
        } else if (this.getStatus() == 0 || this.getStatus() == 1) {
            throw new TSPValidationException("no time stamp token found and one expected.");
        }
    }

    public byte[] getEncoded() throws IOException {
        return this.resp.getEncoded();
    }

    public byte[] getEncoded(String string) throws IOException {
        if ("DL".equals(string)) {
            if (this.timeStampToken == null) {
                return new DLSequence((ASN1Encodable)this.resp.getStatus()).getEncoded(string);
            }
            return new DLSequence(new ASN1Encodable[]{this.resp.getStatus(), this.timeStampToken.toCMSSignedData().toASN1Structure()}).getEncoded(string);
        }
        return this.resp.getEncoded(string);
    }
}

