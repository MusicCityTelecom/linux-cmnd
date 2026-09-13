/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

public class DelegatingX509Certificate
extends X509Certificate {
    private static final long serialVersionUID = 1L;
    private final X509Certificate mDelegate;

    public DelegatingX509Certificate(X509Certificate delegate) {
        this.mDelegate = delegate;
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return this.mDelegate.getCriticalExtensionOIDs();
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return this.mDelegate.getExtensionValue(oid);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return this.mDelegate.getNonCriticalExtensionOIDs();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return this.mDelegate.hasUnsupportedCriticalExtension();
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.mDelegate.checkValidity();
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        this.mDelegate.checkValidity(date);
    }

    @Override
    public int getVersion() {
        return this.mDelegate.getVersion();
    }

    @Override
    public BigInteger getSerialNumber() {
        return this.mDelegate.getSerialNumber();
    }

    @Override
    public Principal getIssuerDN() {
        return this.mDelegate.getIssuerDN();
    }

    @Override
    public Principal getSubjectDN() {
        return this.mDelegate.getSubjectDN();
    }

    @Override
    public Date getNotBefore() {
        return this.mDelegate.getNotBefore();
    }

    @Override
    public Date getNotAfter() {
        return this.mDelegate.getNotAfter();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return this.mDelegate.getTBSCertificate();
    }

    @Override
    public byte[] getSignature() {
        return this.mDelegate.getSignature();
    }

    @Override
    public String getSigAlgName() {
        return this.mDelegate.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return this.mDelegate.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return this.mDelegate.getSigAlgParams();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return this.mDelegate.getIssuerUniqueID();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return this.mDelegate.getSubjectUniqueID();
    }

    @Override
    public boolean[] getKeyUsage() {
        return this.mDelegate.getKeyUsage();
    }

    @Override
    public int getBasicConstraints() {
        return this.mDelegate.getBasicConstraints();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.mDelegate.getEncoded();
    }

    @Override
    public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mDelegate.verify(key);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.mDelegate.verify(key, sigProvider);
    }

    @Override
    public String toString() {
        return this.mDelegate.toString();
    }

    @Override
    public PublicKey getPublicKey() {
        return this.mDelegate.getPublicKey();
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        return this.mDelegate.getIssuerX500Principal();
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        return this.mDelegate.getSubjectX500Principal();
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return this.mDelegate.getExtendedKeyUsage();
    }

    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getSubjectAlternativeNames();
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return this.mDelegate.getIssuerAlternativeNames();
    }

    @Override
    public void verify(PublicKey key, Provider sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        this.mDelegate.verify(key, sigProvider);
    }
}

