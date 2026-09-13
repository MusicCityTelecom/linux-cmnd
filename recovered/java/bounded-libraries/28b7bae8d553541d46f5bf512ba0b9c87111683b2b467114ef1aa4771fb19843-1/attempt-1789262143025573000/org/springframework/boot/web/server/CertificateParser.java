/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Base64Utils
 *  org.springframework.util.FileCopyUtils
 *  org.springframework.util.ResourceUtils
 */
package org.springframework.boot.web.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

final class CertificateParser {
    private static final String HEADER = "-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+";
    private static final String BASE64_TEXT = "([a-z0-9+/=\\r\\n]+)";
    private static final String FOOTER = "-+END\\s+.*CERTIFICATE[^-]*-+";
    private static final Pattern PATTERN = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);

    private CertificateParser() {
    }

    static X509Certificate[] parse(String path) {
        CertificateFactory factory = CertificateParser.getCertificateFactory();
        ArrayList certificates = new ArrayList();
        CertificateParser.readCertificates(path, factory, certificates::add);
        return certificates.toArray(new X509Certificate[0]);
    }

    private static CertificateFactory getCertificateFactory() {
        try {
            return CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException ex) {
            throw new IllegalStateException("Unable to get X.509 certificate factory", ex);
        }
    }

    private static void readCertificates(String resource, CertificateFactory factory, Consumer<X509Certificate> consumer) {
        try {
            String text = CertificateParser.readText(resource);
            Matcher matcher = PATTERN.matcher(text);
            while (matcher.find()) {
                String encodedText = matcher.group(1);
                byte[] decodedBytes = CertificateParser.decodeBase64(encodedText);
                ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
                while (inputStream.available() > 0) {
                    consumer.accept((X509Certificate)factory.generateCertificate(inputStream));
                }
            }
        }
        catch (IOException | CertificateException ex) {
            throw new IllegalStateException("Error reading certificate from '" + resource + "' : " + ex.getMessage(), ex);
        }
    }

    private static String readText(String resource) throws IOException {
        URL url = ResourceUtils.getURL((String)resource);
        try (InputStreamReader reader = new InputStreamReader(url.openStream());){
            String string = FileCopyUtils.copyToString((Reader)reader);
            return string;
        }
    }

    private static byte[] decodeBase64(String content) {
        byte[] bytes = content.replaceAll("\r", "").replaceAll("\n", "").getBytes();
        return Base64Utils.decode((byte[])bytes);
    }
}

