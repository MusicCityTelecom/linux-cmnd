/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  me.grison.jtoml.impl.Toml
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.core.io.Resource
 *  org.springframework.ldap.core.ContextSource
 *  org.springframework.ldap.core.LdapTemplate
 *  org.springframework.ldap.core.support.LdapContextSource
 */
package org.apereo.services.persondir.support;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import me.grison.jtoml.impl.Toml;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.support.QueryType;
import org.apereo.services.persondir.support.ldap.LdapPersonAttributeDao;
import org.springframework.core.io.Resource;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Deprecated
public class TomlLdapPersonAttributeDao
extends LdapPersonAttributeDao {
    private final Resource tomlConfigFile;

    public TomlLdapPersonAttributeDao(Resource tomlConfigFile) throws Exception {
        this.tomlConfigFile = tomlConfigFile;
        this.validateTomlResource();
        TomlLdapConfiguration config = this.buildTomlLdapConfiguration();
        this.applyTomlConfigurationToDao(config);
    }

    private void applyTomlConfigurationToDao(TomlLdapConfiguration config) {
        if (!StringUtils.isBlank((CharSequence)config.getBaseDN())) {
            this.setBaseDN(config.getBaseDN());
        }
        if (config.getQueryAttributeMappings() != null) {
            this.setQueryAttributeMapping(config.getQueryAttributeMappings());
        }
        if (!StringUtils.isBlank((CharSequence)config.getQueryType())) {
            this.setQueryType(QueryType.valueOf(config.getQueryType()));
        }
        if (config.isRequireAllQueryAttributes() != null) {
            this.setRequireAllQueryAttributes(config.isRequireAllQueryAttributes());
        }
        if (config.getResultAttributeMappings() != null) {
            this.setResultAttributeMapping(config.getResultAttributeMappings());
        }
        if (!StringUtils.isBlank((CharSequence)config.getUnmappedUsernameAttribute())) {
            this.setUnmappedUsernameAttribute(config.getUnmappedUsernameAttribute());
        }
        if (!StringUtils.isBlank((CharSequence)config.getQueryTemplate())) {
            this.setQueryTemplate(config.getQueryTemplate());
        }
        if (config.isUseAllQueryAttributes() != null) {
            this.setUseAllQueryAttributes(config.isUseAllQueryAttributes());
        }
        LdapContextSource ctxSource = new LdapContextSource();
        if (config.isPooled() != null) {
            ctxSource.setPooled(config.isPooled().booleanValue());
        }
        if (config.getBaseEnvironmentSettings() != null) {
            ctxSource.setBaseEnvironmentProperties(config.getBaseEnvironmentSettings());
        }
        if (!StringUtils.isBlank((CharSequence)config.getPassword())) {
            ctxSource.setPassword(config.getPassword());
        }
        if (config.getUrls() != null) {
            ctxSource.setUrls(config.getUrls().toArray(new String[0]));
        }
        if (!StringUtils.isBlank((CharSequence)config.getUserDN())) {
            ctxSource.setUserDn(config.getUserDN());
        }
        if (config.isCacheEnvironmentProperties() != null) {
            ctxSource.setCacheEnvironmentProperties(config.isCacheEnvironmentProperties().booleanValue());
        }
        if (!StringUtils.isBlank((CharSequence)config.getReferral())) {
            ctxSource.setReferral(config.getReferral());
        }
        LdapTemplate template = new LdapTemplate((ContextSource)ctxSource);
        if (config.isIgnoreNameNotFoundException() != null) {
            template.setIgnoreNameNotFoundException(config.isIgnoreNameNotFoundException().booleanValue());
        }
        if (config.isIgnorePartialResultException() != null) {
            template.setIgnorePartialResultException(config.isIgnorePartialResultException().booleanValue());
        }
        this.setLdapTemplate(template);
    }

    private TomlLdapConfiguration buildTomlLdapConfiguration() throws Exception {
        try {
            Toml toml = Toml.parse((File)this.tomlConfigFile.getFile());
            TomlLdapConfiguration config = (TomlLdapConfiguration)toml.getAs("ldap", TomlLdapConfiguration.class);
            return config;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            throw e;
        }
    }

    private void validateTomlResource() throws IOException {
        if (!this.tomlConfigFile.exists()) {
            throw new RuntimeException("Toml configuration file cannot be found at the specified path");
        }
        if (this.tomlConfigFile.isOpen()) {
            throw new RuntimeException("Another process/application has opened the Toml configuration file");
        }
        if (!this.tomlConfigFile.isReadable()) {
            throw new RuntimeException("Toml configuration file cannot be read at the specified path");
        }
        if (this.tomlConfigFile.contentLength() == 0L) {
            throw new RuntimeException("Toml configuration file is empty");
        }
    }

    public static final class TomlLdapConfiguration {
        private String baseDN;
        private Boolean requireAllQueryAttributes = false;
        private Boolean useAllQueryAttributes = false;
        private Map<String, String> queryAttributeMappings;
        private Map<String, String> resultAttributeMappings;
        private String queryType;
        private Boolean pooled;
        private List<String> urls;
        private String userDN;
        private String password;
        private Map<String, Object> baseEnvironmentSettings;
        private String unmappedUsernameAttribute;
        private String queryTemplate;
        private Boolean cacheEnvironmentProperties;
        private String referral;
        private Boolean ignoreNameNotFoundException;
        private Boolean ignorePartialResultException;

        public String getBaseDN() {
            return this.baseDN;
        }

        public String getUnmappedUsernameAttribute() {
            return this.unmappedUsernameAttribute;
        }

        public String getQueryTemplate() {
            return this.queryTemplate;
        }

        public void setBaseDN(String baseDN) {
            this.baseDN = baseDN;
        }

        public Boolean isRequireAllQueryAttributes() {
            return this.requireAllQueryAttributes;
        }

        public void setRequireAllQueryAttributes(Boolean requireAllQueryAttributes) {
            this.requireAllQueryAttributes = requireAllQueryAttributes;
        }

        public Boolean isUseAllQueryAttributes() {
            return this.useAllQueryAttributes;
        }

        public void setUseAllQueryAttributes(Boolean useAllQueryAttributes) {
            this.useAllQueryAttributes = useAllQueryAttributes;
        }

        public Map<String, String> getQueryAttributeMappings() {
            return this.queryAttributeMappings;
        }

        public void setQueryAttributeMappings(Map<String, String> queryAttributeMappings) {
            this.queryAttributeMappings = queryAttributeMappings;
        }

        public Map<String, String> getResultAttributeMappings() {
            return this.resultAttributeMappings;
        }

        public void setResultAttributeMappings(Map<String, String> resultAttributeMappings) {
            this.resultAttributeMappings = resultAttributeMappings;
        }

        public String getQueryType() {
            return this.queryType;
        }

        public void setQueryType(String queryType) {
            this.queryType = queryType;
        }

        public Boolean isPooled() {
            return this.pooled;
        }

        public void setPooled(Boolean pooled) {
            this.pooled = pooled;
        }

        public List<String> getUrls() {
            return this.urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

        public String getUserDN() {
            return this.userDN;
        }

        public void setUserDN(String userDN) {
            this.userDN = userDN;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Map<String, Object> getBaseEnvironmentSettings() {
            return this.baseEnvironmentSettings;
        }

        public void setBaseEnvironmentSettings(Map<String, Object> baseEnvironmentSettings) {
            this.baseEnvironmentSettings = baseEnvironmentSettings;
        }

        public void setUnmappedUsernameAttribute(String unmappedUsernameAttribute) {
            this.unmappedUsernameAttribute = unmappedUsernameAttribute;
        }

        public void setQueryTemplate(String queryTemplate) {
            this.queryTemplate = queryTemplate;
        }

        public Boolean isCacheEnvironmentProperties() {
            return this.cacheEnvironmentProperties;
        }

        public void setCacheEnvironmentProperties(Boolean cacheEnvironmentProperties) {
            this.cacheEnvironmentProperties = cacheEnvironmentProperties;
        }

        public String getReferral() {
            return this.referral;
        }

        public void setReferral(String referral) {
            this.referral = referral;
        }

        public Boolean isIgnoreNameNotFoundException() {
            return this.ignoreNameNotFoundException;
        }

        public void setIgnoreNameNotFoundException(Boolean ignoreNameNotFoundException) {
            this.ignoreNameNotFoundException = ignoreNameNotFoundException;
        }

        public Boolean isIgnorePartialResultException() {
            return this.ignorePartialResultException;
        }

        public void setIgnorePartialResultException(Boolean ignorePartialResultException) {
            this.ignorePartialResultException = ignorePartialResultException;
        }
    }
}

