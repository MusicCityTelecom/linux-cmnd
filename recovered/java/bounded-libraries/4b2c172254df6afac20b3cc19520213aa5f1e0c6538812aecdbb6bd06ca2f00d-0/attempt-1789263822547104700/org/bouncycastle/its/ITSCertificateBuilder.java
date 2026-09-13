/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.oer.its.CrlSeries
 *  org.bouncycastle.oer.its.HashedId
 *  org.bouncycastle.oer.its.HashedId$HashedId3
 *  org.bouncycastle.oer.its.PsidGroupPermissions
 *  org.bouncycastle.oer.its.PsidSsp
 *  org.bouncycastle.oer.its.SequenceOfPsidGroupPermissions
 *  org.bouncycastle.oer.its.SequenceOfPsidSsp
 *  org.bouncycastle.oer.its.SequenceOfPsidSsp$Builder
 *  org.bouncycastle.oer.its.ToBeSignedCertificate$Builder
 */
package org.bouncycastle.its;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.ITSValidityPeriod;
import org.bouncycastle.oer.its.CrlSeries;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.PsidGroupPermissions;
import org.bouncycastle.oer.its.PsidSsp;
import org.bouncycastle.oer.its.SequenceOfPsidGroupPermissions;
import org.bouncycastle.oer.its.SequenceOfPsidSsp;
import org.bouncycastle.oer.its.ToBeSignedCertificate;

public class ITSCertificateBuilder {
    protected final ToBeSignedCertificate.Builder tbsCertificateBuilder;
    protected final ITSCertificate issuer;
    protected ASN1Integer version = new ASN1Integer(3L);
    protected HashedId cracaId = new HashedId.HashedId3(new byte[3]);
    protected CrlSeries crlSeries = new CrlSeries(0);

    public ITSCertificateBuilder(ToBeSignedCertificate.Builder builder) {
        this(null, builder);
    }

    public ITSCertificateBuilder(ITSCertificate iTSCertificate, ToBeSignedCertificate.Builder builder) {
        this.issuer = iTSCertificate;
        this.tbsCertificateBuilder = builder;
        this.tbsCertificateBuilder.setCracaId(this.cracaId);
        this.tbsCertificateBuilder.setCrlSeries(this.crlSeries);
    }

    public ITSCertificate getIssuer() {
        return this.issuer;
    }

    public ITSCertificateBuilder setVersion(int n) {
        this.version = new ASN1Integer((long)n);
        return this;
    }

    public ITSCertificateBuilder setCracaId(byte[] byArray) {
        this.cracaId = new HashedId.HashedId3(byArray);
        this.tbsCertificateBuilder.setCracaId(this.cracaId);
        return this;
    }

    public ITSCertificateBuilder setCrlSeries(int n) {
        this.crlSeries = new CrlSeries(n);
        this.tbsCertificateBuilder.setCrlSeries(this.crlSeries);
        return this;
    }

    public ITSCertificateBuilder setValidityPeriod(ITSValidityPeriod iTSValidityPeriod) {
        this.tbsCertificateBuilder.setValidityPeriod(iTSValidityPeriod.toASN1Structure());
        return this;
    }

    public ITSCertificateBuilder setCertIssuePermissions(PsidGroupPermissions ... psidGroupPermissionsArray) {
        this.tbsCertificateBuilder.setCertIssuePermissions(SequenceOfPsidGroupPermissions.builder().addGroupPermission(psidGroupPermissionsArray).createSequenceOfPsidGroupPermissions());
        return this;
    }

    public ITSCertificateBuilder setAppPermissions(PsidSsp ... psidSspArray) {
        SequenceOfPsidSsp.Builder builder = SequenceOfPsidSsp.builder();
        for (int i = 0; i != psidSspArray.length; ++i) {
            builder.setItem(new PsidSsp[]{psidSspArray[i]});
        }
        this.tbsCertificateBuilder.setAppPermissions(builder.createSequenceOfPsidSsp());
        return this;
    }
}

