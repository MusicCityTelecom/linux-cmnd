/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.io.InputStream;
import java.util.Properties;

public final class Version {
    private static String buildId;
    private static String version;

    private Version() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }

    private static void _initiateProperties() {
        InputStream in = Version.getIntputStream();
        if (in != null) {
            try {
                Properties p = new Properties();
                p.load(in);
                String timestamp = p.getProperty("Build-Timestamp");
                version = p.getProperty("Build-Version");
                buildId = String.format("Jersey: %s %s", version, timestamp);
            }
            catch (Exception e) {
                buildId = "Jersey";
            }
            finally {
                Version.close(in);
            }
        }
    }

    private static void close(InputStream in) {
        try {
            in.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static InputStream getIntputStream() {
        try {
            return Version.class.getResourceAsStream("build.properties");
        }
        catch (Exception ex) {
            return null;
        }
    }

    public static String getBuildId() {
        return buildId;
    }

    public static String getVersion() {
        return version;
    }

    static {
        version = null;
        Version._initiateProperties();
    }
}

