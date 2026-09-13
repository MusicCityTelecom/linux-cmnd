/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.CRLDistPoint
 *  org.bouncycastle.asn1.x509.DistributionPoint
 *  org.bouncycastle.asn1.x509.DistributionPointName
 *  org.bouncycastle.asn1.x509.Extension
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.GeneralNames
 *  org.bouncycastle.jcajce.PKIXCRLStore
 *  org.bouncycastle.jcajce.PKIXExtendedParameters
 *  org.bouncycastle.jcajce.PKIXExtendedParameters$Builder
 *  org.bouncycastle.jcajce.util.DefaultJcaJceHelper
 *  org.bouncycastle.jcajce.util.JcaJceHelper
 *  org.bouncycastle.jcajce.util.NamedJcaJceHelper
 *  org.bouncycastle.jcajce.util.ProviderJcaJceHelper
 *  org.bouncycastle.util.CollectionStore
 *  org.bouncycastle.util.Iterable
 *  org.bouncycastle.util.Selector
 *  org.bouncycastle.util.Store
 */
package org.bouncycastle.pkix.jcajce;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.jcajce.PKIXCRLStore;
import org.bouncycastle.jcajce.PKIXExtendedParameters;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.pkix.jcajce.AnnotatedException;
import org.bouncycastle.pkix.jcajce.CRLNotFoundException;
import org.bouncycastle.pkix.jcajce.CertStatus;
import org.bouncycastle.pkix.jcajce.RFC3280CertPathUtilities;
import org.bouncycastle.pkix.jcajce.ReasonsMask;
import org.bouncycastle.pkix.jcajce.RevocationUtilities;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Iterable;
import org.bouncycastle.util.Selector;
import org.bouncycastle.util.Store;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class X509RevocationChecker
extends PKIXCertPathChecker {
    public static final int PKIX_VALIDITY_MODEL = 0;
    public static final int CHAIN_VALIDITY_MODEL = 1;
    private static Logger LOG = Logger.getLogger(X509RevocationChecker.class.getName());
    private static final Map<GeneralName, WeakReference<X509CRL>> crlCache = Collections.synchronizedMap(new WeakHashMap());
    private final Map<X500Principal, Long> failures = new HashMap<X500Principal, Long>();
    private final Set<TrustAnchor> trustAnchors;
    private final boolean isCheckEEOnly;
    private final int validityModel;
    private final List<Store<CRL>> crls;
    private final List<CertStore> crlCertStores;
    private final JcaJceHelper helper;
    private final boolean canSoftFail;
    private final long failLogMaxTime;
    private final long failHardMaxTime;
    private Date currentDate;
    private X500Principal workingIssuerName;
    private PublicKey workingPublicKey;
    private X509Certificate signingCert;
    protected static final String[] crlReasons = new String[]{"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", "unknown", "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    private X509RevocationChecker(Builder builder) {
        this.crls = new ArrayList<Store<CRL>>(builder.crls);
        this.crlCertStores = new ArrayList<CertStore>(builder.crlCertStores);
        this.isCheckEEOnly = builder.isCheckEEOnly;
        this.validityModel = builder.validityModel;
        this.trustAnchors = builder.trustAnchors;
        this.canSoftFail = builder.canSoftFail;
        this.failLogMaxTime = builder.failLogMaxTime;
        this.failHardMaxTime = builder.failHardMaxTime;
        this.helper = builder.provider != null ? new ProviderJcaJceHelper(builder.provider) : (builder.providerName != null ? new NamedJcaJceHelper(builder.providerName) : new DefaultJcaJceHelper());
    }

    @Override
    public void init(boolean bl) throws CertPathValidatorException {
        if (bl) {
            throw new IllegalArgumentException("forward processing not supported");
        }
        this.currentDate = new Date();
        this.workingIssuerName = null;
    }

    @Override
    public boolean isForwardCheckingSupported() {
        return false;
    }

    @Override
    public Set<String> getSupportedExtensions() {
        return null;
    }

    @Override
    public void check(Certificate certificate, Collection<String> collection) throws CertPathValidatorException {
        int n;
        TrustAnchor trustAnchor3;
        X509Certificate x509Certificate = (X509Certificate)certificate;
        if (this.isCheckEEOnly && x509Certificate.getBasicConstraints() != -1) {
            this.workingIssuerName = x509Certificate.getSubjectX500Principal();
            this.workingPublicKey = x509Certificate.getPublicKey();
            this.signingCert = x509Certificate;
            return;
        }
        TrustAnchor trustAnchor2 = null;
        if (this.workingIssuerName == null) {
            this.workingIssuerName = x509Certificate.getIssuerX500Principal();
            for (TrustAnchor trustAnchor3 : this.trustAnchors) {
                if (!this.workingIssuerName.equals(trustAnchor3.getCA()) && !this.workingIssuerName.equals(trustAnchor3.getTrustedCert().getSubjectX500Principal())) continue;
                trustAnchor2 = trustAnchor3;
            }
            if (trustAnchor2 == null) {
                throw new CertPathValidatorException("no trust anchor found for " + this.workingIssuerName);
            }
            this.signingCert = trustAnchor2.getTrustedCert();
            this.workingPublicKey = this.signingCert.getPublicKey();
        }
        ArrayList arrayList = new ArrayList();
        try {
            PKIXParameters pKIXParameters = new PKIXParameters(this.trustAnchors);
            pKIXParameters.setRevocationEnabled(false);
            pKIXParameters.setDate(this.currentDate);
            for (int i = 0; i != this.crlCertStores.size(); ++i) {
                if (LOG.isLoggable(Level.INFO)) {
                    this.addIssuers((List<X500Principal>)arrayList, this.crlCertStores.get(i));
                }
                pKIXParameters.addCertStore(this.crlCertStores.get(i));
            }
            trustAnchor3 = new PKIXExtendedParameters.Builder(pKIXParameters);
            trustAnchor3.setValidityModel(this.validityModel);
        }
        catch (GeneralSecurityException generalSecurityException) {
            throw new RuntimeException("error setting up baseParams: " + generalSecurityException.getMessage());
        }
        for (n = 0; n != this.crls.size(); ++n) {
            if (LOG.isLoggable(Level.INFO)) {
                this.addIssuers((List<X500Principal>)arrayList, this.crls.get(n));
            }
            trustAnchor3.addCRLStore(new LocalCRLStore(this.crls.get(n)));
        }
        if (arrayList.isEmpty()) {
            LOG.log(Level.INFO, "configured with 0 pre-loaded CRLs");
        } else if (LOG.isLoggable(Level.FINE)) {
            for (n = 0; n != arrayList.size(); ++n) {
                LOG.log(Level.FINE, "configuring with CRL for issuer \"" + arrayList.get(n) + "\"");
            }
        } else {
            LOG.log(Level.INFO, "configured with " + arrayList.size() + " pre-loaded CRLs");
        }
        PKIXExtendedParameters pKIXExtendedParameters = trustAnchor3.build();
        Date date = RevocationUtilities.getValidityDate(pKIXExtendedParameters, this.currentDate);
        try {
            this.checkCRLs(pKIXExtendedParameters, this.currentDate, date, x509Certificate, this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
        }
        catch (AnnotatedException annotatedException) {
            throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException.getCause());
        }
        catch (CRLNotFoundException cRLNotFoundException) {
            CRL cRL;
            if (null == x509Certificate.getExtensionValue(Extension.cRLDistributionPoints.getId())) {
                throw cRLNotFoundException;
            }
            try {
                cRL = this.downloadCRLs(x509Certificate.getIssuerX500Principal(), this.currentDate, RevocationUtilities.getExtensionValue(x509Certificate, Extension.cRLDistributionPoints), this.helper);
            }
            catch (AnnotatedException annotatedException) {
                throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException.getCause());
            }
            if (cRL != null) {
                try {
                    trustAnchor3.addCRLStore(new LocalCRLStore((Store<CRL>)new CollectionStore(Collections.singleton(cRL))));
                    pKIXExtendedParameters = trustAnchor3.build();
                    date = RevocationUtilities.getValidityDate(pKIXExtendedParameters, this.currentDate);
                    this.checkCRLs(pKIXExtendedParameters, this.currentDate, date, x509Certificate, this.signingCert, this.workingPublicKey, new ArrayList(), this.helper);
                }
                catch (AnnotatedException annotatedException) {
                    throw new CertPathValidatorException(annotatedException.getMessage(), annotatedException.getCause());
                }
            }
            if (!this.canSoftFail) {
                throw cRLNotFoundException;
            }
            X500Principal x500Principal = x509Certificate.getIssuerX500Principal();
            Long l = this.failures.get(x500Principal);
            if (l != null) {
                long l2 = System.currentTimeMillis() - l;
                if (this.failHardMaxTime != -1L && this.failHardMaxTime < l2) {
                    throw cRLNotFoundException;
                }
                if (l2 < this.failLogMaxTime) {
                    LOG.log(Level.WARNING, "soft failing for issuer: \"" + x500Principal + "\"");
                } else {
                    LOG.log(Level.SEVERE, "soft failing for issuer: \"" + x500Principal + "\"");
                }
            }
            this.failures.put(x500Principal, System.currentTimeMillis());
        }
        this.signingCert = x509Certificate;
        this.workingPublicKey = x509Certificate.getPublicKey();
        this.workingIssuerName = x509Certificate.getSubjectX500Principal();
    }

    private void addIssuers(final List<X500Principal> list, CertStore certStore) throws CertStoreException {
        certStore.getCRLs(new X509CRLSelector(){

            public boolean match(CRL cRL) {
                if (!(cRL instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL)cRL).getIssuerX500Principal());
                return false;
            }
        });
    }

    private void addIssuers(final List<X500Principal> list, Store<CRL> store) {
        store.getMatches((Selector)new Selector<CRL>(){

            public boolean match(CRL cRL) {
                if (!(cRL instanceof X509CRL)) {
                    return false;
                }
                list.add(((X509CRL)cRL).getIssuerX500Principal());
                return false;
            }

            public Object clone() {
                return this;
            }
        });
    }

    private CRL downloadCRLs(X500Principal x500Principal, Date date, ASN1Primitive aSN1Primitive, JcaJceHelper jcaJceHelper) {
        CRLDistPoint cRLDistPoint = CRLDistPoint.getInstance((Object)aSN1Primitive);
        DistributionPoint[] distributionPointArray = cRLDistPoint.getDistributionPoints();
        for (int i = 0; i != distributionPointArray.length; ++i) {
            DistributionPoint distributionPoint = distributionPointArray[i];
            DistributionPointName distributionPointName = distributionPoint.getDistributionPoint();
            if (distributionPointName == null || distributionPointName.getType() != 0) continue;
            GeneralName[] generalNameArray = GeneralNames.getInstance((Object)distributionPointName.getName()).getNames();
            for (int j = 0; j != generalNameArray.length; ++j) {
                X509CRL x509CRL;
                GeneralName generalName = generalNameArray[j];
                if (generalName.getTagNo() != 6) continue;
                WeakReference<X509CRL> weakReference = crlCache.get(generalName);
                if (weakReference != null) {
                    x509CRL = (X509CRL)weakReference.get();
                    if (x509CRL != null && !date.before(x509CRL.getThisUpdate()) && !date.after(x509CRL.getNextUpdate())) {
                        return x509CRL;
                    }
                    crlCache.remove(generalName);
                }
                URL uRL = null;
                try {
                    uRL = new URL(generalName.getName().toString());
                    CertificateFactory certificateFactory = jcaJceHelper.createCertificateFactory("X.509");
                    InputStream inputStream = uRL.openStream();
                    x509CRL = (X509CRL)certificateFactory.generateCRL(new BufferedInputStream(inputStream));
                    inputStream.close();
                    LOG.log(Level.INFO, "downloaded CRL from CrlDP " + uRL + " for issuer \"" + x500Principal + "\"");
                    crlCache.put(generalName, new WeakReference<X509CRL>(x509CRL));
                    return x509CRL;
                }
                catch (Exception exception) {
                    if (LOG.isLoggable(Level.FINE)) {
                        LOG.log(Level.FINE, "CrlDP " + uRL + " ignored: " + exception.getMessage(), exception);
                        continue;
                    }
                    LOG.log(Level.INFO, "CrlDP " + uRL + " ignored: " + exception.getMessage());
                }
            }
        }
        return null;
    }

    static List<PKIXCRLStore> getAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, Map<GeneralName, PKIXCRLStore> map) throws AnnotatedException {
        DistributionPoint[] distributionPointArray;
        if (cRLDistPoint == null) {
            return Collections.emptyList();
        }
        try {
            distributionPointArray = cRLDistPoint.getDistributionPoints();
        }
        catch (Exception exception) {
            throw new AnnotatedException("could not read distribution points could not be read", exception);
        }
        ArrayList<PKIXCRLStore> arrayList = new ArrayList<PKIXCRLStore>();
        for (int i = 0; i < distributionPointArray.length; ++i) {
            DistributionPointName distributionPointName = distributionPointArray[i].getDistributionPoint();
            if (distributionPointName == null || distributionPointName.getType() != 0) continue;
            GeneralName[] generalNameArray = GeneralNames.getInstance((Object)distributionPointName.getName()).getNames();
            for (int j = 0; j < generalNameArray.length; ++j) {
                PKIXCRLStore pKIXCRLStore = map.get(generalNameArray[j]);
                if (pKIXCRLStore == null) continue;
                arrayList.add(pKIXCRLStore);
            }
        }
        return arrayList;
    }

    protected void checkCRLs(PKIXExtendedParameters pKIXExtendedParameters, Date date, Date date2, X509Certificate x509Certificate, X509Certificate x509Certificate2, PublicKey publicKey, List list, JcaJceHelper jcaJceHelper) throws AnnotatedException, CertPathValidatorException {
        PKIXExtendedParameters pKIXExtendedParameters2;
        Object object;
        Object object2;
        CRLDistPoint cRLDistPoint;
        try {
            cRLDistPoint = CRLDistPoint.getInstance((Object)RevocationUtilities.getExtensionValue(x509Certificate, Extension.cRLDistributionPoints));
        }
        catch (Exception exception) {
            throw new AnnotatedException("cannot read CRL distribution point extension", exception);
        }
        CertStatus certStatus = new CertStatus();
        ReasonsMask reasonsMask = new ReasonsMask();
        AnnotatedException annotatedException = null;
        boolean bl = false;
        if (cRLDistPoint != null) {
            try {
                object2 = cRLDistPoint.getDistributionPoints();
            }
            catch (Exception exception) {
                throw new AnnotatedException("cannot read distribution points", exception);
            }
            if (object2 != null) {
                Object object3;
                object = new PKIXExtendedParameters.Builder(pKIXExtendedParameters);
                try {
                    pKIXExtendedParameters2 = X509RevocationChecker.getAdditionalStoresFromCRLDistributionPoint(cRLDistPoint, pKIXExtendedParameters.getNamedCRLStoreMap());
                    object3 = pKIXExtendedParameters2.iterator();
                    while (object3.hasNext()) {
                        object.addCRLStore(object3.next());
                    }
                }
                catch (AnnotatedException annotatedException2) {
                    throw new AnnotatedException("no additional CRL locations could be decoded from CRL distribution point extension", annotatedException2);
                }
                pKIXExtendedParameters2 = object.build();
                object3 = RevocationUtilities.getValidityDate(pKIXExtendedParameters2, date);
                for (int i = 0; i < ((Object)object2).length && certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons(); ++i) {
                    try {
                        RFC3280CertPathUtilities.checkCRL((DistributionPoint)object2[i], pKIXExtendedParameters2, date, (Date)object3, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                        bl = true;
                        continue;
                    }
                    catch (AnnotatedException annotatedException3) {
                        annotatedException = annotatedException3;
                    }
                }
            }
        }
        if (certStatus.getCertStatus() == 11 && !reasonsMask.isAllReasons()) {
            try {
                object2 = x509Certificate.getIssuerX500Principal();
                object = new DistributionPoint(new DistributionPointName(0, (ASN1Encodable)new GeneralNames(new GeneralName(4, (ASN1Encodable)X500Name.getInstance((Object)((X500Principal)object2).getEncoded())))), null, null);
                pKIXExtendedParameters2 = (PKIXExtendedParameters)pKIXExtendedParameters.clone();
                RFC3280CertPathUtilities.checkCRL((DistributionPoint)object, pKIXExtendedParameters2, date, date2, x509Certificate, x509Certificate2, publicKey, certStatus, reasonsMask, list, jcaJceHelper);
                bl = true;
            }
            catch (AnnotatedException annotatedException4) {
                annotatedException = annotatedException4;
            }
        }
        if (!bl) {
            if (annotatedException instanceof AnnotatedException) {
                throw new CRLNotFoundException("no valid CRL found", annotatedException);
            }
            throw new CRLNotFoundException("no valid CRL found");
        }
        if (certStatus.getCertStatus() != 11) {
            object2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
            ((DateFormat)object2).setTimeZone(TimeZone.getTimeZone("UTC"));
            object = "certificate [issuer=\"" + x509Certificate.getIssuerX500Principal() + "\",serialNumber=" + x509Certificate.getSerialNumber() + ",subject=\"" + x509Certificate.getSubjectX500Principal() + "\"] revoked after " + ((DateFormat)object2).format(certStatus.getRevocationDate());
            object = (String)object + ", reason: " + crlReasons[certStatus.getCertStatus()];
            throw new AnnotatedException((String)object);
        }
        if (!reasonsMask.isAllReasons() && certStatus.getCertStatus() == 11) {
            certStatus.setCertStatus(12);
        }
        if (certStatus.getCertStatus() == 12) {
            throw new AnnotatedException("certificate status could not be determined");
        }
    }

    @Override
    public Object clone() {
        return this;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private Set<TrustAnchor> trustAnchors;
        private List<CertStore> crlCertStores = new ArrayList<CertStore>();
        private List<Store<CRL>> crls = new ArrayList<Store<CRL>>();
        private boolean isCheckEEOnly;
        private int validityModel = 0;
        private Provider provider;
        private String providerName;
        private boolean canSoftFail;
        private long failLogMaxTime;
        private long failHardMaxTime;

        public Builder(TrustAnchor trustAnchor) {
            this.trustAnchors = Collections.singleton(trustAnchor);
        }

        public Builder(Set<TrustAnchor> set) {
            this.trustAnchors = new HashSet<TrustAnchor>(set);
        }

        public Builder(KeyStore keyStore) throws KeyStoreException {
            this.trustAnchors = new HashSet<TrustAnchor>();
            Enumeration<String> enumeration = keyStore.aliases();
            while (enumeration.hasMoreElements()) {
                String string = enumeration.nextElement();
                if (!keyStore.isCertificateEntry(string)) continue;
                this.trustAnchors.add(new TrustAnchor((X509Certificate)keyStore.getCertificate(string), null));
            }
        }

        public Builder addCrls(CertStore certStore) {
            this.crlCertStores.add(certStore);
            return this;
        }

        public Builder addCrls(Store<CRL> store) {
            this.crls.add(store);
            return this;
        }

        public Builder setCheckEndEntityOnly(boolean bl) {
            this.isCheckEEOnly = bl;
            return this;
        }

        public Builder setSoftFail(boolean bl, long l) {
            this.canSoftFail = bl;
            this.failLogMaxTime = l;
            this.failHardMaxTime = -1L;
            return this;
        }

        public Builder setSoftFailHardLimit(boolean bl, long l) {
            this.canSoftFail = bl;
            this.failLogMaxTime = l * 3L / 4L;
            this.failHardMaxTime = l;
            return this;
        }

        public Builder setValidityModel(int n) {
            this.validityModel = n;
            return this;
        }

        public Builder usingProvider(Provider provider) {
            this.provider = provider;
            return this;
        }

        public Builder usingProvider(String string) {
            this.providerName = string;
            return this;
        }

        public X509RevocationChecker build() {
            return new X509RevocationChecker(this);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private class LocalCRLStore
    implements PKIXCRLStore<CRL>,
    Iterable<CRL> {
        private Collection<CRL> _local;

        public LocalCRLStore(Store<CRL> store) {
            this._local = new ArrayList<CRL>(store.getMatches(null));
        }

        public Collection<CRL> getMatches(Selector<CRL> selector) {
            if (selector == null) {
                return new ArrayList<CRL>(this._local);
            }
            ArrayList<CRL> arrayList = new ArrayList<CRL>();
            for (CRL cRL : this._local) {
                if (!selector.match((Object)cRL)) continue;
                arrayList.add(cRL);
            }
            return arrayList;
        }

        public Iterator<CRL> iterator() {
            return this.getMatches(null).iterator();
        }
    }
}

