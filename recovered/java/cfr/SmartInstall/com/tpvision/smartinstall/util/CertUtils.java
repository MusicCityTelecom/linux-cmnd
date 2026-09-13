/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Configs;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class CertUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CertUtils.class);
    public static final String CERT_SERVER_PASSWORD = Configs.getProperty("cert.ca.password");
    public static final String CA_NAME = Configs.getProperty("cert.ca.name");
    public static final String CA_PASSWORD = CertUtils.getCAPasswordFromSiConfig();
    private static final String CA_BEFORE_START_END_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();
    private static final String RSA_PUBLIC_FILE_NAME = "rsa_public_key.pem";
    private static final String RSA_PRIVATE_FILE_NAME = "rsa_private_key.pem";

    private CertUtils() {
    }

    public static void scheduleCheckDomainCertFiles() {
        NetworkUtils.refreshNetSegmentJsonArray();
        CertUtils.installDomainCertFiles(false);
    }

    public static void reGenerateCert() {
        CertUtils.installDomainCertFiles(true);
    }

    public static String getCaCertVersion() {
        String certFile = CommonConstants.servletContextPath + "/Cert/ca.p12";
        File caFile = new File(certFile);
        if (!caFile.exists()) {
            LOG.error("CA file not exist!");
            return "";
        }
        Date d = new Date(caFile.lastModified());
        return TpvDateUtils.formatLocalDate(d, "dd/MM/yyyy:HH:mm");
    }

    private static void installDomainCertFiles(boolean forceCreate) {
        LOG.info("installDomainCertFiles with forceCreate:{}", (Object)forceCreate);
        boolean isCaReCreated = CertUtils.generateCACert(forceCreate);
        LOG.info("generateCACert with return isCaReCreated value:{}", (Object)isCaReCreated);
        boolean isServerCertRecreated = CertUtils.generateServerCert(isCaReCreated || forceCreate);
        LOG.info("generateServerCert with return isServerCertRecreated value:{}", (Object)isServerCertRecreated);
    }

    private static boolean generateCACert(boolean forceCreate) {
        String certFile = CommonConstants.servletContextPath + "/Cert/ca.p12";
        File projectCertFile = new File(certFile);
        if (!forceCreate && projectCertFile.exists()) {
            return false;
        }
        String tempCAP12File = CommonConstants.CERT_WD + "ca.p12";
        CertUtils.generateCaCertInTheWorkDir();
        CertUtils.copyFileToDir(tempCAP12File, projectCertFile.getParent());
        CertUtils.autoImportToWindowsRootDir();
        return true;
    }

    private static void autoImportToWindowsRootDir() {
        String delExpireCommand = "certutil -delstore \"ROOT\" \"CMND\"";
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, delExpireCommand)) {
            throw new CertGenerateException("remove expire ca crt failure");
        }
        String addCommand = "certutil -addstore \"ROOT\" ca.pem";
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, addCommand)) {
            throw new CertGenerateException("ca import to windows system failure");
        }
    }

    private static boolean generateServerCert(boolean forceCreate) {
        String serverCertFile = CertUtils.getTomcatInstallPath() + "server.p12";
        List<String> serverIpList = NetworkUtils.getServerIpList();
        LOG.info("generateServerCert ips={}", (Object)serverIpList);
        JpaManager.getSIConfigManager().setCmndIp(StringUtils.arrayToCommaDelimitedString(serverIpList.toArray()));
        if (!forceCreate && CertUtils.isCertServerIPListValid(serverCertFile, serverIpList)) {
            return false;
        }
        String tempServerCert = CommonConstants.CERT_WD + "server.p12";
        CertUtils.generateServerCertInTheWorkDir(serverIpList);
        CertUtils.copyFileToDir(tempServerCert, CertUtils.getTomcatInstallPath());
        LOG.info("copy to project work directory success");
        CertUtils.deployServerCertToApache();
        return true;
    }

    private static void deployServerCertToApache() {
        String targetDir = CommonConstants.APACHE_WD + "bin/";
        try {
            FileUtils.copyFile(new File(CommonConstants.CERT_WD + "servercert.pem"), new File(targetDir + "mysite.cert"));
            FileUtils.copyFile(new File(CommonConstants.CERT_WD + "serverkey.pem"), new File(targetDir + "mysite.key"));
            ProcessUtils.execCommond(CommonConstants.CERT_WD, "net stop apache2.4");
            ProcessUtils.execCommond(CommonConstants.CERT_WD, "net start apache2.4");
            LOG.info("deploy apache cert success ,use 'https://localhost:8444/index.html' to have a test");
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean isCertServerIPListValid(String certPath, List<String> serverIpList) {
        if (!new File(certPath).exists()) {
            return false;
        }
        try (FileInputStream fis = new FileInputStream(certPath);){
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(fis, CERT_SERVER_PASSWORD.toCharArray());
            Enumeration<String> enum1 = ks.aliases();
            String keyAlias = null;
            if (enum1.hasMoreElements()) {
                keyAlias = enum1.nextElement();
            }
            X509Certificate cert = (X509Certificate)ks.getCertificate(keyAlias);
            ArrayList<String> sanList = new ArrayList<String>();
            Collection<List<?>> subjectCollection = cert.getSubjectAlternativeNames();
            if (subjectCollection != null) {
                for (List<?> list : subjectCollection) {
                    sanList.add(String.valueOf(list.get(1)));
                }
            }
            boolean bl = sanList.containsAll(serverIpList) && sanList.contains(CertUtils.getServerHostName());
            return bl;
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    private static synchronized void generateCaCertInTheWorkDir() {
        File file;
        File dir = new File(CommonConstants.CERT_WD);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String openssl = CommonConstants.OPENSSL_WD + "openssl.exe";
        if (Utils.getOSName().equalsIgnoreCase("linux")) {
            openssl = CommonConstants.OPENSSL_WD + "openssl";
        }
        if (!(file = new File(openssl)).exists()) {
            throw new CertGenerateException("openssl file not exists");
        }
        String command = String.format(Locale.ENGLISH, "%s genrsa -des3 -out ca.key -passout pass:%s 2048", openssl, CA_PASSWORD);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("ca key create failure");
        }
        command = String.format(Locale.ENGLISH, "%s req -new -days 3650 -key ca.key -out ca.csr -subj \"/C=CN/ST=XM/L=XM/O=tpv/OU=tpv/CN=CMND\" -passin pass:%s  -config C:/Apache24/conf/openssl.cnf", openssl, CA_PASSWORD);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("ca csr create failure");
        }
        try {
            CertUtils.resetCertDbPath();
        }
        catch (Exception ex) {
            throw new CertGenerateException("ca recreate db path failure", ex);
        }
        SimpleDateFormat spf = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(CA_BEFORE_START_END_TIME_FORMAT);
        String startDate = spf.format(TpvDateUtils.getBeforeDate(new Date(), 7));
        String endDate = spf.format(TpvDateUtils.getAfterDate(new Date(), 3650));
        command = String.format(Locale.ENGLISH, "%s ca -selfsign -md sha256 -keyfile ca.key -in ca.csr -out ca.pem -extensions v3_ca -config C:/Apache24/conf/openssl.cnf -key %s -startdate %sZ -enddate %sZ -batch", openssl, CA_PASSWORD, startDate, endDate);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("ca self sign failure");
        }
        command = String.format(Locale.ENGLISH, "%s pkcs12 -legacy -export -in ca.pem -inkey ca.key -out ca.p12 -passin pass:%s -passout pass:%s -name %s", openssl, CA_PASSWORD, CA_PASSWORD, CA_NAME);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("ca key/cert package failure");
        }
    }

    public static String getCaPublicKeyPath() {
        String caPublickeyFilePath = CommonConstants.CERT_WD + "ca.pem";
        boolean isCaPublicKeyFileExist = new File(caPublickeyFilePath).exists();
        if (!isCaPublicKeyFileExist) {
            CertUtils.installDomainCertFiles(true);
        }
        return caPublickeyFilePath;
    }

    public static String packCaCertificateUsingCustomPassword(String customPassword) {
        String openssl;
        String command;
        boolean isCaBaseFileExist;
        boolean bl = isCaBaseFileExist = new File(CommonConstants.CERT_WD + "ca.pem").exists() && new File(CommonConstants.CERT_WD + "ca.key").exists();
        if (!isCaBaseFileExist) {
            CertUtils.installDomainCertFiles(true);
        }
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command = String.format(Locale.ENGLISH, "%s pkcs12 -legacy -export -in ca.pem -inkey ca.key -out customPasswordCa.p12 -passin pass:%s -passout pass:%s -name %s", openssl = CommonConstants.OPENSSL_WD + "openssl.exe", CA_PASSWORD, customPassword, CA_NAME))) {
            throw new CertGenerateException("ca key/cert package failure");
        }
        return CommonConstants.CERT_WD + "customPasswordCa.p12";
    }

    private static synchronized void generateServerCertInTheWorkDir(List<String> serverIpList) {
        File file;
        File dir = new File(CommonConstants.CERT_WD);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String openssl = CommonConstants.OPENSSL_WD + "openssl.exe";
        if (Utils.getOSName().equalsIgnoreCase("linux")) {
            openssl = CommonConstants.OPENSSL_WD + "openssl";
        }
        if (!(file = new File(openssl)).exists()) {
            throw new CertGenerateException("server cert openssl file not found");
        }
        String command = String.format(Locale.ENGLISH, "%s genrsa -out serverkey.pem 2048", openssl);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("server key file create failure");
        }
        String cnIp = serverIpList.get(0);
        command = String.format(Locale.ENGLISH, "%s req -subj \"/C=CN/ST=XM/L=XM/O=tpv/OU=tpv/CN=%s/emailAddress=shuisheng.hong@tpv-tech.com\" -new -out serverreq.csr -key serverkey.pem -passin pass:%s -passout pass:%s -config C:/Apache24/conf/openssl.cnf", openssl, cnIp, CERT_SERVER_PASSWORD, CERT_SERVER_PASSWORD);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("server csr file create failure");
        }
        try {
            CertUtils.resetCertDbPath();
        }
        catch (Exception ex) {
            throw new CertGenerateException("server reset db failure", ex);
        }
        try {
            File extFile = new File(CommonConstants.CERT_WD + "/server_san.ext");
            if (extFile.exists()) {
                FileUtils.deleteQuietly(extFile);
            }
            StringBuilder fileStr = new StringBuilder();
            fileStr.append("[ v3_req_san ]").append("\r\n");
            fileStr.append("basicConstraints = CA:FALSE").append("\r\n");
            fileStr.append("keyUsage = nonRepudiation, digitalSignature, keyEncipherment").append("\r\n");
            fileStr.append("subjectAltName = @alt_names").append("\r\n");
            fileStr.append("[alt_names]").append("\r\n");
            int beginIndex = 1;
            for (String ip : serverIpList) {
                fileStr.append("IP." + beginIndex + "    = " + ip).append("\r\n");
                ++beginIndex;
            }
            fileStr.append("IP." + beginIndex + "    = 127.0.0.1").append("\r\n");
            fileStr.append("DNS.1    = " + CertUtils.getServerHostName()).append("\r\n");
            fileStr.append("DNS.2    = localhost").append("\r\n");
            File extraAltNamesFile = new File(CommonConstants.CERT_WD + "extra_alt_names.txt");
            if (extraAltNamesFile.exists()) {
                String extraAltNames = FileUtils.readFileToString(extraAltNamesFile, StandardCharsets.UTF_8);
                fileStr.append(extraAltNames).append("\r\n");
            }
            FileUtils.writeStringToFile(extFile, fileStr.toString(), StandardCharsets.UTF_8);
        }
        catch (Exception ex) {
            throw new CertGenerateException("server create san extension failure", ex);
        }
        SimpleDateFormat spf = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(CA_BEFORE_START_END_TIME_FORMAT);
        String startDate = spf.format(TpvDateUtils.getBeforeDate(new Date(), 3));
        String endDate = spf.format(TpvDateUtils.getAfterDate(new Date(), 3650));
        command = String.format(Locale.ENGLISH, "%s ca -md sha256 -cert ca.pem -keyfile ca.key -in serverreq.csr -out servercert.pem -extensions v3_req_san -config C:/Apache24/conf/openssl.cnf -key %s -startdate %sZ -enddate %sZ -batch -extfile server_san.ext", openssl, CA_PASSWORD, startDate, endDate);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("server cert create failure");
        }
        command = String.format(Locale.ENGLISH, "%s pkcs12 -legacy -export -clcerts -in servercert.pem -inkey serverkey.pem -out server.p12 -passin pass:%s -passout pass:%s", openssl, CERT_SERVER_PASSWORD, CERT_SERVER_PASSWORD);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("server cert export package failure");
        }
    }

    private static String getServerHostName() {
        return System.getenv().get("COMPUTERNAME");
    }

    private static void resetCertDbPath() throws IOException {
        String certPath = CommonConstants.CERT_WD + "/demoCA";
        if (new File(certPath).exists()) {
            FileUtils.cleanDirectory(new File(certPath));
        } else {
            FileUtils.forceMkdir(new File(certPath));
        }
        FileUtils.forceMkdir(new File(certPath + "/newcerts"));
        FileUtils.touch(new File(certPath + "/index.txt"));
        FileUtils.writeStringToFile(new File(certPath + "/serial"), "01\r\n", StandardCharsets.UTF_8);
    }

    private static String getTomcatInstallPath() {
        return CommonConstants.TOMCAT_WD + File.separator;
    }

    private static void copyFileToDir(String src, String dst) {
        try {
            FileUtils.copyFileToDirectory(new File(src), new File(dst));
        }
        catch (IOException e) {
            throw new CertGenerateException("copy file [" + src + "] to [" + dst + "] failed", e);
        }
    }

    private static String getCAPasswordFromSiConfig() {
        SIConfig siConfig = JpaManager.getSIConfigManager().getSIConfig();
        String caPassword = siConfig.getCaPassword();
        if (!StringUtils.hasLength(caPassword)) {
            caPassword = TpvStringUtils.getRandomString(6);
            siConfig.setCaPassword(caPassword);
            JpaManager.getSIConfigManager().saveSIConfig(siConfig);
        }
        return caPassword;
    }

    public static void installCert(String hostName) throws Exception {
        String[] args = new String[]{hostName};
        if (args.length != 1 && args.length != 2) {
            LOG.info("Usage: java InstallCert <host>[:port] [passphrase]");
            return;
        }
        String[] c = args[0].split(":");
        String host = c[0];
        int port = c.length == 1 ? 443 : Integer.parseInt(c[1]);
        String p = args.length == 1 ? "changeit" : args[1];
        char[] passphrase = p.toCharArray();
        KeyStore ks = CertUtils.getKeyStore();
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory factory = context.getSocketFactory();
        LOG.info("Opening connection to {}:{}", (Object)host, (Object)port);
        try (SSLSocket socket = (SSLSocket)factory.createSocket(host, port);){
            socket.setSoTimeout(10000);
            LOG.info("Starting SSL handshake...");
            socket.startHandshake();
            LOG.info("No errors, certificate is already trusted");
            return;
        }
        catch (SSLException e) {
            X509Certificate cert;
            LOG.error(e.getMessage(), e);
            X509Certificate[] chain = tm.chain;
            if (chain == null) {
                LOG.info("Could not obtain server certificate chain");
                return;
            }
            LOG.info("\n");
            LOG.info("Server sent {} certificate(s):", (Object)chain.length);
            LOG.info("\n");
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            for (int i = 0; i < chain.length; ++i) {
                cert = chain[i];
                LOG.info("{} Subject {}", (Object)(i + 1), (Object)cert.getSubjectDN());
                LOG.info("   Issuer  {}", (Object)cert.getIssuerDN());
                sha1.update(cert.getEncoded());
                LOG.info("   sha1    {}", (Object)CertUtils.toHexString(sha1.digest()));
                md5.update(cert.getEncoded());
                LOG.info("   md5     {}", (Object)CertUtils.toHexString(md5.digest()));
            }
            int k = 0;
            cert = chain[k];
            String alias = host + "-" + (k + 1);
            ks.setCertificateEntry(alias, cert);
            try (FileOutputStream out = new FileOutputStream(CommonConstants.CERT_WD + "jssecacerts");){
                ks.store(out, passphrase);
            }
            LOG.info("{}", (Object)cert);
            LOG.info("Added certificate to keystore 'jssecacerts' using alias '{}'", (Object)alias);
            return;
        }
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int n : bytes) {
            sb.append(HEXDIGITS[(n &= 0xFF) >> 4]);
            sb.append(HEXDIGITS[n & 0xF]);
            sb.append(' ');
        }
        return sb.toString();
    }

    public static KeyStore getKeyStore() throws Exception {
        File file = new File(CommonConstants.CERT_WD + "jssecacerts");
        if (!file.isFile()) {
            char SEP = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
            file = new File(dir, "jssecacerts");
            if (!file.isFile()) {
                file = new File(dir, "cacerts");
            }
        }
        LOG.info("Loading KeyStore {}...", (Object)file);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream in = new FileInputStream(file);){
            ks.load(in, "changeit".toCharArray());
        }
        return ks;
    }

    public static SSLContext getSSLContext() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(CertUtils.getKeyStore());
        X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        return context;
    }

    public static String getRSAPublicKey() {
        String rsaPublickeyFilePath = CommonConstants.CERT_WD + RSA_PUBLIC_FILE_NAME;
        if (!new File(rsaPublickeyFilePath).exists()) {
            CertUtils.initRSAKeyPari();
        }
        try {
            String publicKeyString = FileUtils.readFileToString(new File(rsaPublickeyFilePath), StandardCharsets.UTF_8);
            return publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").trim();
        }
        catch (Exception ex) {
            LOG.error("get rsa public key failure", ex);
            return "";
        }
    }

    public static String getRSAPrivateKey() {
        String rsaPrivateKeyFilePath = CommonConstants.CERT_WD + RSA_PRIVATE_FILE_NAME;
        if (new File(rsaPrivateKeyFilePath).exists()) {
            try {
                String privateKeyString = FileUtils.readFileToString(new File(rsaPrivateKeyFilePath), StandardCharsets.UTF_8);
                return privateKeyString.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").trim();
            }
            catch (Exception ex) {
                LOG.error("get rsa private key failure", ex);
            }
        }
        return "";
    }

    private static synchronized void initRSAKeyPari() {
        File file;
        File dir = new File(CommonConstants.CERT_WD);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String openssl = CommonConstants.OPENSSL_WD + "openssl.exe";
        if (Utils.getOSName().equalsIgnoreCase("linux")) {
            openssl = CommonConstants.OPENSSL_WD + "openssl";
        }
        if (!(file = new File(openssl)).exists()) {
            throw new CertGenerateException("openssl file not exists");
        }
        String pks1FormatPrivateKey = "rsa_private_key_pks1.pem";
        String command = String.format(Locale.ENGLISH, "%s genrsa -out %s 1024", openssl, pks1FormatPrivateKey);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("rsa private key create failure");
        }
        command = String.format(Locale.ENGLISH, "%s rsa -in %s -pubout -out %s", openssl, pks1FormatPrivateKey, RSA_PUBLIC_FILE_NAME);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("rsa public key create failure");
        }
        command = String.format(Locale.ENGLISH, "%s pkcs8 -topk8 -inform PEM -in %s -outform pem -nocrypt -out %s", openssl, pks1FormatPrivateKey, RSA_PRIVATE_FILE_NAME);
        if (!ProcessUtils.execCommond(CommonConstants.CERT_WD, command)) {
            throw new CertGenerateException("rsa private key format to pks8 failure");
        }
    }

    private static class SavingTrustManager
    implements X509TrustManager {
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.chain = chain;
        }
    }

    private static class CertGenerateException
    extends RuntimeException {
        private static final long serialVersionUID = -6210493402899123077L;

        public CertGenerateException(String msg) {
            super(msg);
        }

        public CertGenerateException(String msg, Exception ex) {
            super(msg, ex);
        }
    }
}

