/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.x509.Extension
 *  org.bouncycastle.asn1.x509.Extensions
 *  org.bouncycastle.asn1.x509.PolicyConstraints
 *  org.bouncycastle.util.Memoable
 */
package org.bouncycastle.cert.path.validations;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.PolicyConstraints;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.path.CertPathValidation;
import org.bouncycastle.cert.path.CertPathValidationContext;
import org.bouncycastle.cert.path.CertPathValidationException;
import org.bouncycastle.cert.path.validations.ValidationUtils;
import org.bouncycastle.util.Memoable;

public class CertificatePoliciesValidation
implements CertPathValidation {
    private int explicitPolicy;
    private int policyMapping;
    private int inhibitAnyPolicy;

    CertificatePoliciesValidation(int n) {
        this(n, false, false, false);
    }

    CertificatePoliciesValidation(int n, boolean bl, boolean bl2, boolean bl3) {
        this.explicitPolicy = bl ? 0 : n + 1;
        this.inhibitAnyPolicy = bl2 ? 0 : n + 1;
        this.policyMapping = bl3 ? 0 : n + 1;
    }

    public void validate(CertPathValidationContext certPathValidationContext, X509CertificateHolder x509CertificateHolder) throws CertPathValidationException {
        certPathValidationContext.addHandledExtension(Extension.policyConstraints);
        certPathValidationContext.addHandledExtension(Extension.inhibitAnyPolicy);
        if (!certPathValidationContext.isEndEntity() && !ValidationUtils.isSelfIssued(x509CertificateHolder)) {
            int n;
            BigInteger bigInteger;
            this.explicitPolicy = this.countDown(this.explicitPolicy);
            this.policyMapping = this.countDown(this.policyMapping);
            this.inhibitAnyPolicy = this.countDown(this.inhibitAnyPolicy);
            PolicyConstraints policyConstraints = PolicyConstraints.fromExtensions((Extensions)x509CertificateHolder.getExtensions());
            if (policyConstraints != null) {
                BigInteger bigInteger2;
                bigInteger = policyConstraints.getRequireExplicitPolicyMapping();
                if (bigInteger != null && bigInteger.intValue() < this.explicitPolicy) {
                    this.explicitPolicy = bigInteger.intValue();
                }
                if ((bigInteger2 = policyConstraints.getInhibitPolicyMapping()) != null && bigInteger2.intValue() < this.policyMapping) {
                    this.policyMapping = bigInteger2.intValue();
                }
            }
            if ((bigInteger = x509CertificateHolder.getExtension(Extension.inhibitAnyPolicy)) != null && (n = ASN1Integer.getInstance((Object)bigInteger.getParsedValue()).intValueExact()) < this.inhibitAnyPolicy) {
                this.inhibitAnyPolicy = n;
            }
        }
    }

    private int countDown(int n) {
        if (n != 0) {
            return n - 1;
        }
        return 0;
    }

    public Memoable copy() {
        return new CertificatePoliciesValidation(0);
    }

    public void reset(Memoable memoable) {
        CertificatePoliciesValidation certificatePoliciesValidation = (CertificatePoliciesValidation)memoable;
    }
}

