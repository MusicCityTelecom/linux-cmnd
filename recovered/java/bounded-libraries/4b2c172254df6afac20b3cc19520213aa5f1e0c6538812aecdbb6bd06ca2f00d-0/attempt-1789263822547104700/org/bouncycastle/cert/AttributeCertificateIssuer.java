/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.AttCertIssuer
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.GeneralNames
 *  org.bouncycastle.asn1.x509.V2Form
 *  org.bouncycastle.util.Selector
 */
package org.bouncycastle.cert;

import java.util.ArrayList;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AttCertIssuer;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.V2Form;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.Selector;

public class AttributeCertificateIssuer
implements Selector {
    final ASN1Encodable form;

    public AttributeCertificateIssuer(AttCertIssuer attCertIssuer) {
        this.form = attCertIssuer.getIssuer();
    }

    public AttributeCertificateIssuer(X500Name x500Name) {
        this.form = new V2Form(new GeneralNames(new GeneralName(x500Name)));
    }

    public X500Name[] getNames() {
        GeneralNames generalNames = this.form instanceof V2Form ? ((V2Form)this.form).getIssuerName() : (GeneralNames)this.form;
        GeneralName[] generalNameArray = generalNames.getNames();
        ArrayList<X500Name> arrayList = new ArrayList<X500Name>(generalNameArray.length);
        for (int i = 0; i != generalNameArray.length; ++i) {
            if (generalNameArray[i].getTagNo() != 4) continue;
            arrayList.add(X500Name.getInstance((Object)generalNameArray[i].getName()));
        }
        return arrayList.toArray(new X500Name[arrayList.size()]);
    }

    private boolean matchesDN(X500Name x500Name, GeneralNames generalNames) {
        GeneralName[] generalNameArray = generalNames.getNames();
        for (int i = 0; i != generalNameArray.length; ++i) {
            GeneralName generalName = generalNameArray[i];
            if (generalName.getTagNo() != 4 || !X500Name.getInstance((Object)generalName.getName()).equals((Object)x500Name)) continue;
            return true;
        }
        return false;
    }

    public Object clone() {
        return new AttributeCertificateIssuer(AttCertIssuer.getInstance((Object)this.form));
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeCertificateIssuer)) {
            return false;
        }
        AttributeCertificateIssuer attributeCertificateIssuer = (AttributeCertificateIssuer)object;
        return this.form.equals(attributeCertificateIssuer.form);
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    public boolean match(Object object) {
        if (!(object instanceof X509CertificateHolder)) {
            return false;
        }
        X509CertificateHolder x509CertificateHolder = (X509CertificateHolder)object;
        if (this.form instanceof V2Form) {
            V2Form v2Form = (V2Form)this.form;
            if (v2Form.getBaseCertificateID() != null) {
                return v2Form.getBaseCertificateID().getSerial().hasValue(x509CertificateHolder.getSerialNumber()) && this.matchesDN(x509CertificateHolder.getIssuer(), v2Form.getBaseCertificateID().getIssuer());
            }
            GeneralNames generalNames = v2Form.getIssuerName();
            if (this.matchesDN(x509CertificateHolder.getSubject(), generalNames)) {
                return true;
            }
        } else {
            GeneralNames generalNames = (GeneralNames)this.form;
            if (this.matchesDN(x509CertificateHolder.getSubject(), generalNames)) {
                return true;
            }
        }
        return false;
    }
}

