/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Null
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.oer.its.CertificateId;
import org.bouncycastle.oer.its.CrlSeries;
import org.bouncycastle.oer.its.GeographicRegion;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.SequenceOfPsidGroupPermissions;
import org.bouncycastle.oer.its.SequenceOfPsidSsp;
import org.bouncycastle.oer.its.SubjectAssurance;
import org.bouncycastle.oer.its.Utils;
import org.bouncycastle.oer.its.ValidityPeriod;
import org.bouncycastle.oer.its.VerificationKeyIndicator;

public class ToBeSignedCertificate
extends ASN1Object {
    private final CertificateId certificateId;
    private final HashedId cracaId;
    private final CrlSeries crlSeries;
    private final ValidityPeriod validityPeriod;
    private final GeographicRegion geographicRegion;
    private final SubjectAssurance assuranceLevel;
    private final SequenceOfPsidSsp appPermissions;
    private final SequenceOfPsidGroupPermissions certIssuePermissions;
    private final SequenceOfPsidGroupPermissions certRequestPermissions;
    private final ASN1Null canRequestRollover;
    private final PublicEncryptionKey encryptionKey;
    private final VerificationKeyIndicator verificationKeyIndicator;

    public ToBeSignedCertificate(CertificateId certificateId, HashedId hashedId, CrlSeries crlSeries, ValidityPeriod validityPeriod, GeographicRegion geographicRegion, SubjectAssurance subjectAssurance, SequenceOfPsidSsp sequenceOfPsidSsp, SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions, SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions2, ASN1Null aSN1Null, PublicEncryptionKey publicEncryptionKey, VerificationKeyIndicator verificationKeyIndicator) {
        this.certificateId = certificateId;
        this.cracaId = hashedId;
        this.crlSeries = crlSeries;
        this.validityPeriod = validityPeriod;
        this.geographicRegion = geographicRegion;
        this.assuranceLevel = subjectAssurance;
        this.appPermissions = sequenceOfPsidSsp;
        this.certIssuePermissions = sequenceOfPsidGroupPermissions;
        this.certRequestPermissions = sequenceOfPsidGroupPermissions2;
        this.canRequestRollover = aSN1Null;
        this.encryptionKey = publicEncryptionKey;
        this.verificationKeyIndicator = verificationKeyIndicator;
    }

    public static ToBeSignedCertificate getInstance(Object object) {
        if (object == null || object instanceof ToBeSignedCertificate) {
            return (ToBeSignedCertificate)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        return new Builder().setCertificateId(CertificateId.getInstance(iterator.next())).setCracaId(HashedId.getInstance(iterator.next())).setCrlSeries(CrlSeries.getInstance(iterator.next())).setValidityPeriod(ValidityPeriod.getInstance(iterator.next())).setGeographicRegion(OEROptional.getValue(GeographicRegion.class, iterator.next())).setAssuranceLevel(OEROptional.getValue(SubjectAssurance.class, iterator.next())).setAppPermissions(OEROptional.getValue(SequenceOfPsidSsp.class, iterator.next())).setCertIssuePermissions(OEROptional.getValue(SequenceOfPsidGroupPermissions.class, iterator.next())).setCertRequestPermissions(OEROptional.getValue(SequenceOfPsidGroupPermissions.class, iterator.next())).setCanRequestRollover(OEROptional.getValue(ASN1Null.class, iterator.next())).setEncryptionKey(OEROptional.getValue(PublicEncryptionKey.class, iterator.next())).setVerificationKeyIndicator(VerificationKeyIndicator.getInstance(iterator.next())).createToBeSignedCertificate();
    }

    public CertificateId getCertificateId() {
        return this.certificateId;
    }

    public HashedId getCracaId() {
        return this.cracaId;
    }

    public CrlSeries getCrlSeries() {
        return this.crlSeries;
    }

    public ValidityPeriod getValidityPeriod() {
        return this.validityPeriod;
    }

    public GeographicRegion getGeographicRegion() {
        return this.geographicRegion;
    }

    public SubjectAssurance getAssuranceLevel() {
        return this.assuranceLevel;
    }

    public SequenceOfPsidSsp getAppPermissions() {
        return this.appPermissions;
    }

    public SequenceOfPsidGroupPermissions getCertIssuePermissions() {
        return this.certIssuePermissions;
    }

    public SequenceOfPsidGroupPermissions getCertRequestPermissions() {
        return this.certRequestPermissions;
    }

    public ASN1Null getCanRequestRollover() {
        return this.canRequestRollover;
    }

    public PublicEncryptionKey getEncryptionKey() {
        return this.encryptionKey;
    }

    public VerificationKeyIndicator getVerificationKeyIndicator() {
        return this.verificationKeyIndicator;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.certificateId, this.cracaId, this.crlSeries, this.validityPeriod, OEROptional.getInstance((Object)this.geographicRegion), OEROptional.getInstance((Object)this.assuranceLevel), OEROptional.getInstance((Object)this.appPermissions), OEROptional.getInstance((Object)this.certIssuePermissions), OEROptional.getInstance((Object)this.certRequestPermissions), OEROptional.getInstance(this.canRequestRollover), OEROptional.getInstance((Object)this.encryptionKey), this.verificationKeyIndicator});
    }

    public static class Builder {
        private CertificateId certificateId;
        private HashedId cracaId;
        private CrlSeries crlSeries;
        private ValidityPeriod validityPeriod;
        private GeographicRegion geographicRegion;
        private SubjectAssurance assuranceLevel;
        private SequenceOfPsidSsp appPermissions;
        private SequenceOfPsidGroupPermissions certIssuePermissions;
        private SequenceOfPsidGroupPermissions certRequestPermissions;
        private ASN1Null canRequestRollover;
        private PublicEncryptionKey encryptionKey;
        private VerificationKeyIndicator verificationKeyIndicator;

        public Builder() {
        }

        public Builder(Builder builder) {
            this.certificateId = builder.certificateId;
            this.cracaId = builder.cracaId;
            this.crlSeries = builder.crlSeries;
            this.validityPeriod = builder.validityPeriod;
            this.geographicRegion = builder.geographicRegion;
            this.assuranceLevel = builder.assuranceLevel;
            this.appPermissions = builder.appPermissions;
            this.certIssuePermissions = builder.certIssuePermissions;
            this.certRequestPermissions = builder.certRequestPermissions;
            this.canRequestRollover = builder.canRequestRollover;
            this.encryptionKey = builder.encryptionKey;
            this.verificationKeyIndicator = builder.verificationKeyIndicator;
        }

        public Builder(ToBeSignedCertificate toBeSignedCertificate) {
            this.certificateId = toBeSignedCertificate.certificateId;
            this.cracaId = toBeSignedCertificate.cracaId;
            this.crlSeries = toBeSignedCertificate.crlSeries;
            this.validityPeriod = toBeSignedCertificate.validityPeriod;
            this.geographicRegion = toBeSignedCertificate.geographicRegion;
            this.assuranceLevel = toBeSignedCertificate.assuranceLevel;
            this.appPermissions = toBeSignedCertificate.appPermissions;
            this.certIssuePermissions = toBeSignedCertificate.certIssuePermissions;
            this.certRequestPermissions = toBeSignedCertificate.certRequestPermissions;
            this.canRequestRollover = toBeSignedCertificate.canRequestRollover;
            this.encryptionKey = toBeSignedCertificate.encryptionKey;
            this.verificationKeyIndicator = toBeSignedCertificate.verificationKeyIndicator;
        }

        public Builder setCertificateId(CertificateId certificateId) {
            this.certificateId = certificateId;
            return this;
        }

        public Builder setCracaId(HashedId hashedId) {
            this.cracaId = hashedId;
            return this;
        }

        public Builder setCrlSeries(CrlSeries crlSeries) {
            this.crlSeries = crlSeries;
            return this;
        }

        public Builder setValidityPeriod(ValidityPeriod validityPeriod) {
            this.validityPeriod = validityPeriod;
            return this;
        }

        public Builder setGeographicRegion(GeographicRegion geographicRegion) {
            this.geographicRegion = geographicRegion;
            return this;
        }

        public Builder setAssuranceLevel(SubjectAssurance subjectAssurance) {
            this.assuranceLevel = subjectAssurance;
            return this;
        }

        public Builder setAppPermissions(SequenceOfPsidSsp sequenceOfPsidSsp) {
            this.appPermissions = sequenceOfPsidSsp;
            return this;
        }

        public Builder setCertIssuePermissions(SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions) {
            this.certIssuePermissions = sequenceOfPsidGroupPermissions;
            return this;
        }

        public Builder setCertRequestPermissions(SequenceOfPsidGroupPermissions sequenceOfPsidGroupPermissions) {
            this.certRequestPermissions = sequenceOfPsidGroupPermissions;
            return this;
        }

        public Builder setCanRequestRollover(ASN1Null aSN1Null) {
            this.canRequestRollover = aSN1Null;
            return this;
        }

        public Builder setEncryptionKey(PublicEncryptionKey publicEncryptionKey) {
            this.encryptionKey = publicEncryptionKey;
            return this;
        }

        public Builder setVerificationKeyIndicator(VerificationKeyIndicator verificationKeyIndicator) {
            this.verificationKeyIndicator = verificationKeyIndicator;
            return this;
        }

        public ToBeSignedCertificate createToBeSignedCertificate() {
            return new ToBeSignedCertificate(this.certificateId, this.cracaId, this.crlSeries, this.validityPeriod, this.geographicRegion, this.assuranceLevel, this.appPermissions, this.certIssuePermissions, this.certRequestPermissions, this.canRequestRollover, this.encryptionKey, this.verificationKeyIndicator);
        }
    }
}

