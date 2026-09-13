/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.util.spring.boot;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Formatter;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;
import org.apereo.cas.util.AsciiArtUtils;
import org.apereo.cas.util.CasVersion;
import org.apereo.cas.util.SystemUtils;
import org.apereo.cas.util.spring.boot.BannerContributor;
import org.apereo.cas.util.spring.boot.CasBanner;
import org.springframework.core.env.Environment;

public abstract class AbstractCasBanner
implements CasBanner {
    private static final int SEPARATOR_REPEAT_COUNT = 60;
    private static final String SEPARATOR_CHAR = "-";
    protected static final String LINE_SEPARATOR = String.join((CharSequence)"", Collections.nCopies(60, "-"));

    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        AsciiArtUtils.printAsciiArt(out, this.getTitle(), this.collectEnvironmentInfo(environment, sourceClass));
    }

    @Override
    public String getTitle() {
        return "\n     _    ____  _____ ____  _____ ___     ____    _    ____  \n    / \\  |  _ \\| ____|  _ \\| ____/ _ \\   / ___|  / \\  / ___| \n   / _ \\ | |_) |  _| | |_) |  _|| | | | | |     / _ \\ \\___ \\ \n  / ___ \\|  __/| |___|  _ <| |__| |_| | | |___ / ___ \\ ___) |\n /_/   \\_\\_|   |_____|_| \\_\\_____\\___/   \\____/_/   \\_\\____/ \n                                                             \n";
    }

    private String collectEnvironmentInfo(Environment environment, Class<?> sourceClass) {
        Properties properties = System.getProperties();
        if (properties.containsKey("CAS_BANNER_SKIP")) {
            try (Formatter formatter = new Formatter();){
                formatter.format("CAS Version: %s%n", CasVersion.getVersion());
                String string = formatter.toString();
                return string;
            }
        }
        try (Formatter formatter = new Formatter();){
            Map<String, Object> sysInfo = SystemUtils.getSystemInfo();
            sysInfo.forEach((key, v) -> {
                if (key.startsWith(SEPARATOR_CHAR)) {
                    formatter.format("%s%n", LINE_SEPARATOR);
                } else {
                    formatter.format("%s: %s%n", key, v);
                }
            });
            formatter.format("%s%n", LINE_SEPARATOR);
            this.injectEnvironmentInfo(formatter, environment, sourceClass);
            ServiceLoader.load(BannerContributor.class).stream().forEach(clz -> ((BannerContributor)clz.get()).contribute(formatter, environment));
            formatter.format("%s%n", LINE_SEPARATOR);
            String string = formatter.toString();
            return string;
        }
    }
}

