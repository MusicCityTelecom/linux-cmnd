/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509;

public enum ExtensionType {
    AuthorityInformationAccess("1.3.6.1.5.5.7.1.1", false),
    AuthorityKeyIdentifier("2.5.29.35", false),
    BasicConstraints("2.5.29.19", true),
    CertificatePolicies("2.5.29.32", false),
    CRLDistributionPoints("2.5.29.31", false),
    ExtendedKeyUsage("2.5.29.37", false),
    IssuerAlternativeName("2.5.29.18", false),
    KeyUsage("2.5.29.15", true),
    NameConstraints("2.5.29.30", true),
    PolicyConstraints("2.5.29.36", false),
    PolicyMappings("2.5.29.33", false),
    PrivateKeyUsagePeriod("2.5.29.16", false),
    SubjectAlternativeName("2.5.29.17", false),
    SubjectKeyIdentifier("2.5.29.14", false),
    SubjectDirectoryAttributes("2.5.29.9", false);

    private final String oid;
    private final boolean critical;

    private ExtensionType(String oidString, boolean criticality) {
        this.oid = oidString;
        this.critical = criticality;
    }

    public static ExtensionType fromOid(String oid) {
        for (ExtensionType ext : ExtensionType.values()) {
            if (!ext.getOid().equals(oid)) continue;
            return ext;
        }
        throw new IllegalArgumentException("Invalid X.509v3 extension OID " + oid);
    }

    public static ExtensionType fromName(String name) {
        try {
            return ExtensionType.valueOf(ExtensionType.class, name);
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid X.509v3 extension name " + name);
        }
    }

    public boolean isCritical() {
        return this.critical;
    }

    public String getOid() {
        return this.oid;
    }
}

