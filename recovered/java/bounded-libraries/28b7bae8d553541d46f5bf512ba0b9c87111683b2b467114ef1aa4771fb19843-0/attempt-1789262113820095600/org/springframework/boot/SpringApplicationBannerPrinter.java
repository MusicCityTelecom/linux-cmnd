/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.boot.Banner;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringBootBanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

class SpringApplicationBannerPrinter {
    static final String BANNER_LOCATION_PROPERTY = "spring.banner.location";
    static final String BANNER_IMAGE_LOCATION_PROPERTY = "spring.banner.image.location";
    static final String DEFAULT_BANNER_LOCATION = "banner.txt";
    static final String[] IMAGE_EXTENSION = new String[]{"gif", "jpg", "png"};
    private static final Banner DEFAULT_BANNER = new SpringBootBanner();
    private final ResourceLoader resourceLoader;
    private final Banner fallbackBanner;

    SpringApplicationBannerPrinter(ResourceLoader resourceLoader, Banner fallbackBanner) {
        this.resourceLoader = resourceLoader;
        this.fallbackBanner = fallbackBanner;
    }

    Banner print(Environment environment, Class<?> sourceClass, Log logger) {
        Banner banner = this.getBanner(environment);
        try {
            logger.info((Object)this.createStringFromBanner(banner, environment, sourceClass));
        }
        catch (UnsupportedEncodingException ex) {
            logger.warn((Object)"Failed to create String for banner", (Throwable)ex);
        }
        return new PrintedBanner(banner, sourceClass);
    }

    Banner print(Environment environment, Class<?> sourceClass, PrintStream out) {
        Banner banner = this.getBanner(environment);
        banner.printBanner(environment, sourceClass, out);
        return new PrintedBanner(banner, sourceClass);
    }

    private Banner getBanner(Environment environment) {
        Banners banners = new Banners();
        banners.addIfNotNull(this.getImageBanner(environment));
        banners.addIfNotNull(this.getTextBanner(environment));
        if (banners.hasAtLeastOneBanner()) {
            return banners;
        }
        if (this.fallbackBanner != null) {
            return this.fallbackBanner;
        }
        return DEFAULT_BANNER;
    }

    private Banner getTextBanner(Environment environment) {
        String location = environment.getProperty(BANNER_LOCATION_PROPERTY, DEFAULT_BANNER_LOCATION);
        Resource resource = this.resourceLoader.getResource(location);
        try {
            if (resource.exists() && !resource.getURL().toExternalForm().contains("liquibase-core")) {
                return new ResourceBanner(resource);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return null;
    }

    private Banner getImageBanner(Environment environment) {
        String location = environment.getProperty(BANNER_IMAGE_LOCATION_PROPERTY);
        if (StringUtils.hasLength((String)location)) {
            Resource resource = this.resourceLoader.getResource(location);
            return resource.exists() ? new ImageBanner(resource) : null;
        }
        for (String ext : IMAGE_EXTENSION) {
            Resource resource = this.resourceLoader.getResource("banner." + ext);
            if (!resource.exists()) continue;
            return new ImageBanner(resource);
        }
        return null;
    }

    private String createStringFromBanner(Banner banner, Environment environment, Class<?> mainApplicationClass) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        banner.printBanner(environment, mainApplicationClass, new PrintStream(baos));
        String charset = environment.getProperty("spring.banner.charset", "UTF-8");
        return baos.toString(charset);
    }

    private static class PrintedBanner
    implements Banner {
        private final Banner banner;
        private final Class<?> sourceClass;

        PrintedBanner(Banner banner, Class<?> sourceClass) {
            this.banner = banner;
            this.sourceClass = sourceClass;
        }

        @Override
        public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
            sourceClass = sourceClass != null ? sourceClass : this.sourceClass;
            this.banner.printBanner(environment, sourceClass, out);
        }
    }

    private static class Banners
    implements Banner {
        private final List<Banner> banners = new ArrayList<Banner>();

        private Banners() {
        }

        void addIfNotNull(Banner banner) {
            if (banner != null) {
                this.banners.add(banner);
            }
        }

        boolean hasAtLeastOneBanner() {
            return !this.banners.isEmpty();
        }

        @Override
        public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
            for (Banner banner : this.banners) {
                banner.printBanner(environment, sourceClass, out);
            }
        }
    }
}

