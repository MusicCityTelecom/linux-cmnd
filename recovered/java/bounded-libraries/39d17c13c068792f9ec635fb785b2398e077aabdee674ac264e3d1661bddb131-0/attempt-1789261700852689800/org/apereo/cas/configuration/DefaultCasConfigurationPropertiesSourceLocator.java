/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.env.CompositePropertySource
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertiesPropertySource
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.SystemEnvironmentPropertySource
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 */
package org.apereo.cas.configuration;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.configuration.CasConfigurationPropertiesEnvironmentManager;
import org.apereo.cas.configuration.api.CasConfigurationPropertiesSourceLocator;
import org.apereo.cas.configuration.loader.BaseConfigurationPropertiesLoader;
import org.apereo.cas.configuration.loader.ConfigurationPropertiesLoaderFactory;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class DefaultCasConfigurationPropertiesSourceLocator
implements CasConfigurationPropertiesSourceLocator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCasConfigurationPropertiesSourceLocator.class);
    private static final List<String> EXTENSIONS = Arrays.asList("yml", "yaml", "properties");
    private static final List<String> PROFILE_PATTERNS = Arrays.asList("application-%s.%s", "%s.%s");
    private final CasConfigurationPropertiesEnvironmentManager casConfigurationPropertiesEnvironmentManager;
    private final ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory;

    @Override
    public Optional<PropertySource<?>> locate(Environment environment, ResourceLoader resourceLoader) {
        CompositePropertySource compositePropertySource = new CompositePropertySource("casCompositePropertySource");
        compositePropertySource.addPropertySource(DefaultCasConfigurationPropertiesSourceLocator.loadEnvironmentAndSystemProperties());
        File config = this.casConfigurationPropertiesEnvironmentManager.getStandaloneProfileConfigurationDirectory(environment);
        LOGGER.debug("Located CAS standalone configuration directory at [{}]", (Object)config);
        if (config != null && config.isDirectory() && config.exists()) {
            CompositePropertySource sourceProfiles = this.loadSettingsByApplicationProfiles(environment, config);
            if (!sourceProfiles.getPropertySources().isEmpty()) {
                compositePropertySource.addPropertySource((PropertySource)sourceProfiles);
            }
        } else {
            LOGGER.info("Configuration directory [{}] is not a directory or cannot be found at the specific path", (Object)config);
        }
        PropertySource<?> embeddedProperties = this.loadEmbeddedProperties(resourceLoader, environment);
        compositePropertySource.addPropertySource(embeddedProperties);
        return Optional.of(compositePropertySource);
    }

    private List<File> getAllPossibleExternalConfigDirFilenames(Environment environment, File configDirectory, List<String> profiles) {
        String applicationName = this.casConfigurationPropertiesEnvironmentManager.getApplicationName(environment);
        String configName = this.casConfigurationPropertiesEnvironmentManager.getConfigurationName(environment);
        String appNameLowerCase = applicationName.toLowerCase();
        List appConfigNames = CollectionUtils.wrapList((Object[])new String[]{"application", appNameLowerCase, applicationName, configName});
        List<File> fileNames = appConfigNames.stream().distinct().flatMap(appName -> EXTENSIONS.stream().map(ext -> new File(configDirectory, String.format("%s.%s", appName, ext)))).filter(File::exists).collect(Collectors.toList());
        fileNames.addAll(profiles.stream().flatMap(profile -> EXTENSIONS.stream().flatMap(ext -> PROFILE_PATTERNS.stream().map(pattern -> new File(configDirectory, String.format(pattern, profile, ext))))).filter(File::exists).collect(Collectors.toList()));
        fileNames.addAll(profiles.stream().map(profile -> EXTENSIONS.stream().map(ext -> appConfigNames.stream().map(appName -> new File(configDirectory, String.format("%s-%s.%s", appName, profile, ext))).filter(File::exists).collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList())).flatMap(Collection::stream).collect(Collectors.toList()));
        File groovyFile = new File(configDirectory, appNameLowerCase.concat(".groovy"));
        FunctionUtils.doIf((boolean)groovyFile.exists(), o -> fileNames.add(groovyFile)).accept(groovyFile);
        return fileNames;
    }

    private List<Resource> scanForConfigurationResources(Environment environment, File config, List<String> profiles) {
        List<File> possibleFiles = this.getAllPossibleExternalConfigDirFilenames(environment, config, profiles);
        return possibleFiles.stream().filter(File::exists).filter(File::isFile).map(FileSystemResource::new).collect(Collectors.toList());
    }

    private CompositePropertySource loadSettingsByApplicationProfiles(Environment environment, File config) {
        List<String> profiles = ConfigurationPropertiesLoaderFactory.getApplicationProfiles(environment);
        List<Resource> resources = this.scanForConfigurationResources(environment, config, profiles);
        CompositePropertySource composite = new CompositePropertySource("applicationProfilesCompositeProperties");
        LOGGER.info("Configuration files found at [{}] are [{}] under profile(s) [{}]", new Object[]{config, resources, profiles});
        resources.forEach(Unchecked.consumer(f -> {
            LOGGER.debug("Loading configuration file [{}]", f);
            BaseConfigurationPropertiesLoader loader = this.configurationPropertiesLoaderFactory.getLoader((Resource)f, "applicationProfilesProperties-" + f.getFilename());
            composite.addFirstPropertySource(loader.load());
        }));
        return composite;
    }

    private static PropertySource<?> loadEnvironmentAndSystemProperties() {
        CompositePropertySource environmentAndSystemProperties = new CompositePropertySource("environmentAndSystemProperties");
        environmentAndSystemProperties.addPropertySource((PropertySource)new PropertiesPropertySource("systemProperties", System.getProperties()));
        environmentAndSystemProperties.addPropertySource((PropertySource)new SystemEnvironmentPropertySource("systemEnvironment", System.getenv()));
        return environmentAndSystemProperties;
    }

    private PropertySource<?> loadEmbeddedProperties(ResourceLoader resourceLoader, Environment environment) {
        List<String> profiles = ConfigurationPropertiesLoaderFactory.getApplicationProfiles(environment);
        List configFiles = profiles.stream().map(profile -> EXTENSIONS.stream().map(ext -> String.format("classpath:/application-%s.%s", profile, ext)).collect(Collectors.toList())).flatMap(Collection::stream).map(arg_0 -> ((ResourceLoader)resourceLoader).getResource(arg_0)).collect(Collectors.toList());
        configFiles.addAll(EXTENSIONS.stream().map(ext -> String.format("classpath:/application.%s", ext)).map(arg_0 -> ((ResourceLoader)resourceLoader).getResource(arg_0)).collect(Collectors.toList()));
        LOGGER.debug("Loading embedded configuration files [{}]", configFiles);
        CompositePropertySource composite = new CompositePropertySource("embeddedCompositeProperties");
        configFiles.stream().filter(ResourceUtils::doesResourceExist).forEach(resource -> {
            LOGGER.trace("Loading properties from [{}]", resource);
            PropertySource source = this.configurationPropertiesLoaderFactory.getLoader((Resource)resource, String.format("embeddedProperties-%s", resource.getFilename())).load();
            composite.addPropertySource(source);
        });
        return composite;
    }

    @Generated
    public DefaultCasConfigurationPropertiesSourceLocator(CasConfigurationPropertiesEnvironmentManager casConfigurationPropertiesEnvironmentManager, ConfigurationPropertiesLoaderFactory configurationPropertiesLoaderFactory) {
        this.casConfigurationPropertiesEnvironmentManager = casConfigurationPropertiesEnvironmentManager;
        this.configurationPropertiesLoaderFactory = configurationPropertiesLoaderFactory;
    }
}

