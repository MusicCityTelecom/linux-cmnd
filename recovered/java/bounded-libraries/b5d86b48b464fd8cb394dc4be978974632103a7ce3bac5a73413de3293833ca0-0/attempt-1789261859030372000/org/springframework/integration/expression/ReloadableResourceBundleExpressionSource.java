/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.expression.Expression
 *  org.springframework.expression.ExpressionParser
 *  org.springframework.expression.spel.SpelParserConfiguration
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.lang.Nullable
 *  org.springframework.util.Assert
 *  org.springframework.util.DefaultPropertiesPersister
 *  org.springframework.util.PropertiesPersister
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.expression;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.expression.ExpressionSource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

public class ReloadableResourceBundleExpressionSource
implements ExpressionSource,
ResourceLoaderAware {
    private static final Log LOGGER = LogFactory.getLog(ReloadableResourceBundleExpressionSource.class);
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static final String XML_SUFFIX = ".xml";
    private final Map<String, Map<Locale, List<String>>> cachedFilenames = new HashMap<String, Map<Locale, List<String>>>();
    private final Map<String, PropertiesHolder> cachedProperties = new HashMap<String, PropertiesHolder>();
    private final Map<Locale, PropertiesHolder> cachedMergedProperties = new HashMap<Locale, PropertiesHolder>();
    private final ExpressionParser parser = new SpelExpressionParser(new SpelParserConfiguration(true, true));
    private String[] basenames = new String[0];
    private String defaultEncoding;
    private Properties fileEncodings;
    private boolean fallbackToSystemLocale = true;
    private long cacheMillis = -1L;
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    public void setBasename(String basename) {
        this.setBasenames(new String[]{basename});
    }

    public void setBasenames(@Nullable String[] basenames) {
        if (basenames != null) {
            this.basenames = new String[basenames.length];
            for (int i = 0; i < basenames.length; ++i) {
                String basename = basenames[i];
                Assert.hasText((String)basename, (String)"Basename must not be empty");
                this.basenames[i] = basename.trim();
            }
        } else {
            this.basenames = new String[0];
        }
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public void setFileEncodings(Properties fileEncodings) {
        this.fileEncodings = fileEncodings;
    }

    public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
        this.fallbackToSystemLocale = fallbackToSystemLocale;
    }

    public void setCacheSeconds(int cacheSeconds) {
        this.cacheMillis = cacheSeconds * 1000;
    }

    public void setPropertiesPersister(@Nullable PropertiesPersister propertiesPersister) {
        this.propertiesPersister = propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister();
    }

    public void setResourceLoader(@Nullable ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader != null ? resourceLoader : new DefaultResourceLoader();
    }

    @Override
    public Expression getExpression(String key, Locale locale) {
        String expressionString = this.getExpressionString(key, locale);
        if (expressionString != null) {
            return this.parser.parseExpression(expressionString);
        }
        return null;
    }

    @Nullable
    private String getExpressionString(String key, Locale locale) {
        if (this.cacheMillis < 0L) {
            PropertiesHolder propHolder = this.getMergedProperties(locale);
            return propHolder.getProperty(key);
        }
        for (String basename : this.basenames) {
            List<String> filenames = this.calculateAllFilenames(basename, locale);
            for (String filename : filenames) {
                PropertiesHolder propHolder = this.getProperties(filename);
                String result = propHolder.getProperty(key);
                if (result == null) continue;
                return result;
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertiesHolder getMergedProperties(Locale locale) {
        Map<Locale, PropertiesHolder> map = this.cachedMergedProperties;
        synchronized (map) {
            PropertiesHolder mergedHolder = this.cachedMergedProperties.get(locale);
            if (mergedHolder != null) {
                return mergedHolder;
            }
            Properties mergedProps = new Properties();
            mergedHolder = new PropertiesHolder(mergedProps, -1L);
            for (int i = this.basenames.length - 1; i >= 0; --i) {
                List<String> filenames = this.calculateAllFilenames(this.basenames[i], locale);
                for (int j = filenames.size() - 1; j >= 0; --j) {
                    String filename = filenames.get(j);
                    PropertiesHolder propHolder = this.getProperties(filename);
                    Properties propHolderProperties = propHolder.getProperties();
                    if (propHolderProperties == null) continue;
                    mergedProps.putAll((Map<?, ?>)propHolderProperties);
                }
            }
            this.cachedMergedProperties.put(locale, mergedHolder);
            return mergedHolder;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<String> calculateAllFilenames(String basename, Locale locale) {
        Map<String, Map<Locale, List<String>>> map = this.cachedFilenames;
        synchronized (map) {
            List<String> filenames;
            Map<Locale, List<String>> localeMap = this.cachedFilenames.get(basename);
            if (localeMap != null && (filenames = localeMap.get(locale)) != null) {
                return filenames;
            }
            filenames = new ArrayList<String>(this.calculateFilenamesForLocale(basename, locale));
            if (this.fallbackToSystemLocale && !locale.equals(Locale.getDefault())) {
                List<String> fallbackFilenames = this.calculateFilenamesForLocale(basename, Locale.getDefault());
                for (String fallbackFilename : fallbackFilenames) {
                    if (filenames.contains(fallbackFilename)) continue;
                    filenames.add(fallbackFilename);
                }
            }
            filenames.add(basename);
            if (localeMap != null) {
                localeMap.put(locale, filenames);
            } else {
                localeMap = new HashMap<Locale, List<String>>();
                localeMap.put(locale, filenames);
                this.cachedFilenames.put(basename, localeMap);
            }
            return filenames;
        }
    }

    private List<String> calculateFilenamesForLocale(String basename, Locale locale) {
        ArrayList<String> result = new ArrayList<String>();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String variant = locale.getVariant();
        StringBuilder temp = new StringBuilder(basename);
        temp.append('_');
        if (language.length() > 0) {
            temp.append(language);
            result.add(0, temp.toString());
        }
        temp.append('_');
        if (country.length() > 0) {
            temp.append(country);
            result.add(0, temp.toString());
        }
        if (variant.length() > 0 && (language.length() > 0 || country.length() > 0)) {
            temp.append('_').append(variant);
            result.add(0, temp.toString());
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertiesHolder getProperties(String filename) {
        Map<String, PropertiesHolder> map = this.cachedProperties;
        synchronized (map) {
            PropertiesHolder propHolder = this.cachedProperties.get(filename);
            if (propHolder != null && (propHolder.getRefreshTimestamp() < 0L || propHolder.getRefreshTimestamp() > System.currentTimeMillis() - this.cacheMillis)) {
                return propHolder;
            }
            return this.refreshProperties(filename, propHolder);
        }
    }

    private PropertiesHolder refreshProperties(String filename, @Nullable PropertiesHolder propHolderArg) {
        PropertiesHolder propHolder = propHolderArg;
        long refreshTimestamp = this.cacheMillis < 0L ? -1L : System.currentTimeMillis();
        Resource resource = this.getResource(filename);
        if (resource.exists()) {
            long fileTimestamp = -1L;
            if (this.cacheMillis >= 0L) {
                try {
                    fileTimestamp = resource.lastModified();
                    if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug((Object)("Re-caching properties for filename [" + filename + "] - file hasn't been modified"));
                        }
                        propHolder.setRefreshTimestamp(refreshTimestamp);
                        return propHolder;
                    }
                }
                catch (IOException ex) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug((Object)(resource + " could not be resolved in the file system - assuming that is hasn't changed"), (Throwable)ex);
                    }
                    fileTimestamp = -1L;
                }
            }
            propHolder = this.load(filename, resource, fileTimestamp);
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("No properties file found for [" + filename + "] - neither plain properties nor XML"));
            }
            propHolder = new PropertiesHolder();
        }
        propHolder.setRefreshTimestamp(refreshTimestamp);
        this.cachedProperties.put(filename, propHolder);
        return propHolder;
    }

    private Resource getResource(String filename) {
        Resource resource = this.resourceLoader.getResource(filename + PROPERTIES_SUFFIX);
        if (!resource.exists()) {
            resource = this.resourceLoader.getResource(filename + XML_SUFFIX);
        }
        return resource;
    }

    private PropertiesHolder load(String filename, Resource resource, long fileTimestamp) {
        PropertiesHolder propHolder;
        try {
            Properties props = this.loadProperties(resource, filename);
            propHolder = new PropertiesHolder(props, fileTimestamp);
        }
        catch (IOException ex) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn((Object)("Could not parse properties file [" + resource.getFilename() + "]"), (Throwable)ex);
            }
            propHolder = new PropertiesHolder();
        }
        return propHolder;
    }

    private Properties loadProperties(Resource resource, String filename) throws IOException {
        try (InputStream is = resource.getInputStream();){
            Properties props = new Properties();
            String resourceFilename = resource.getFilename();
            if (resourceFilename != null && resourceFilename.endsWith(XML_SUFFIX)) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug((Object)("Loading properties [" + resourceFilename + "]"));
                }
                this.propertiesPersister.loadFromXml(props, is);
            } else {
                this.loadFromProperties(resource, filename, is, props, resourceFilename);
            }
            Properties properties = props;
            return properties;
        }
    }

    private void loadFromProperties(Resource resource, String filename, InputStream is, Properties props, @Nullable String resourceFilename) throws IOException {
        String encoding = null;
        if (this.fileEncodings != null) {
            encoding = this.fileEncodings.getProperty(filename);
        }
        if (encoding == null) {
            encoding = this.defaultEncoding;
        }
        if (encoding != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Loading properties [" + (resourceFilename == null ? resource : resourceFilename) + "] with encoding '" + encoding + "'"));
            }
            this.propertiesPersister.load(props, (Reader)new InputStreamReader(is, encoding));
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Loading properties [" + (resourceFilename == null ? resource : resourceFilename) + "]"));
            }
            this.propertiesPersister.load(props, is);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearCache() {
        LOGGER.debug((Object)"Clearing entire resource bundle cache");
        Map<Object, PropertiesHolder> map = this.cachedProperties;
        synchronized (map) {
            this.cachedProperties.clear();
        }
        map = this.cachedMergedProperties;
        synchronized (map) {
            this.cachedMergedProperties.clear();
        }
    }

    public String toString() {
        return this.getClass().getName() + ": basenames=[" + StringUtils.arrayToCommaDelimitedString((Object[])this.basenames) + "]";
    }

    private static final class PropertiesHolder {
        private Properties properties;
        private long fileTimestamp = -1L;
        private long refreshTimestamp = -1L;

        PropertiesHolder() {
        }

        PropertiesHolder(Properties properties, long fileTimestamp) {
            this.properties = properties;
            this.fileTimestamp = fileTimestamp;
        }

        @Nullable
        public Properties getProperties() {
            return this.properties;
        }

        public long getFileTimestamp() {
            return this.fileTimestamp;
        }

        public void setRefreshTimestamp(long refreshTimestamp) {
            this.refreshTimestamp = refreshTimestamp;
        }

        public long getRefreshTimestamp() {
            return this.refreshTimestamp;
        }

        @Nullable
        public String getProperty(String code) {
            if (this.properties == null) {
                return null;
            }
            return this.properties.getProperty(code);
        }
    }
}

