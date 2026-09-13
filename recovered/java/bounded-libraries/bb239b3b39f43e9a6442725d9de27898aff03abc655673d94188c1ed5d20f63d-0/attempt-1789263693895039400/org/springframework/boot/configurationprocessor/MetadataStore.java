/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.InvalidConfigurationMetadataException;
import org.springframework.boot.configurationprocessor.metadata.JsonMarshaller;

public class MetadataStore {
    static final String METADATA_PATH = "META-INF/spring-configuration-metadata.json";
    private static final String ADDITIONAL_METADATA_PATH = "META-INF/additional-spring-configuration-metadata.json";
    private static final String RESOURCES_DIRECTORY = "resources";
    private static final String CLASSES_DIRECTORY = "classes";
    private final ProcessingEnvironment environment;

    public MetadataStore(ProcessingEnvironment environment) {
        this.environment = environment;
    }

    public ConfigurationMetadata readMetadata() {
        try {
            return this.readMetadata(this.getMetadataResource().openInputStream());
        }
        catch (IOException ex) {
            return null;
        }
    }

    public void writeMetadata(ConfigurationMetadata metadata) throws IOException {
        if (!metadata.getItems().isEmpty()) {
            try (OutputStream outputStream = this.createMetadataResource().openOutputStream();){
                new JsonMarshaller().write(metadata, outputStream);
            }
        }
    }

    public ConfigurationMetadata readAdditionalMetadata() throws IOException {
        return this.readMetadata(this.getAdditionalMetadataStream());
    }

    private ConfigurationMetadata readMetadata(InputStream in) throws IOException {
        try {
            ConfigurationMetadata configurationMetadata = new JsonMarshaller().read(in);
            return configurationMetadata;
        }
        catch (IOException ex) {
            ConfigurationMetadata configurationMetadata = null;
            return configurationMetadata;
        }
        catch (Exception ex) {
            throw new InvalidConfigurationMetadataException("Invalid additional meta-data in 'META-INF/spring-configuration-metadata.json': " + ex.getMessage(), Diagnostic.Kind.ERROR);
        }
        finally {
            in.close();
        }
    }

    private FileObject getMetadataResource() throws IOException {
        return this.environment.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", METADATA_PATH);
    }

    private FileObject createMetadataResource() throws IOException {
        return this.environment.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", METADATA_PATH, new Element[0]);
    }

    private InputStream getAdditionalMetadataStream() throws IOException {
        FileObject fileObject = this.environment.getFiler().getResource(StandardLocation.CLASS_OUTPUT, "", ADDITIONAL_METADATA_PATH);
        File file = this.locateAdditionalMetadataFile(new File(fileObject.toUri()));
        return file.exists() ? new FileInputStream(file) : fileObject.toUri().toURL().openStream();
    }

    File locateAdditionalMetadataFile(File standardLocation) throws IOException {
        if (standardLocation.exists()) {
            return standardLocation;
        }
        String locations = this.environment.getOptions().get("org.springframework.boot.configurationprocessor.additionalMetadataLocations");
        if (locations != null) {
            for (String location : locations.split(",")) {
                File candidate = new File(location, ADDITIONAL_METADATA_PATH);
                if (!candidate.isFile()) continue;
                return candidate;
            }
        }
        return new File(this.locateGradleResourcesDirectory(standardLocation), ADDITIONAL_METADATA_PATH);
    }

    private File locateGradleResourcesDirectory(File standardAdditionalMetadataLocation) throws FileNotFoundException {
        String path = standardAdditionalMetadataLocation.getPath();
        int index = path.lastIndexOf(CLASSES_DIRECTORY);
        if (index < 0) {
            throw new FileNotFoundException();
        }
        String buildDirectoryPath = path.substring(0, index);
        File classOutputLocation = standardAdditionalMetadataLocation.getParentFile().getParentFile();
        return new File(buildDirectoryPath, "resources/" + classOutputLocation.getName());
    }
}

