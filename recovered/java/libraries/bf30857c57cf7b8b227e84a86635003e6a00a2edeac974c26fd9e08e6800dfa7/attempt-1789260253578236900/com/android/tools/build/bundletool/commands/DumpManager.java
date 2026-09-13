/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.commands;

import com.android.aapt.ConfigurationOuterClass;
import com.android.aapt.Resources;
import com.android.bundle.Config;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.ResourceTableEntry;
import com.android.tools.build.bundletool.model.ZipPath;
import com.android.tools.build.bundletool.model.exceptions.BundleInvalidZipException;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.CollectorUtils;
import com.android.tools.build.bundletool.model.utils.ResourcesUtils;
import com.android.tools.build.bundletool.model.utils.ZipUtils;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoNode;
import com.android.tools.build.bundletool.model.utils.xmlproto.XmlProtoPrintUtils;
import com.android.tools.build.bundletool.xml.XPathResolver;
import com.android.tools.build.bundletool.xml.XmlNamespaceContext;
import com.android.tools.build.bundletool.xml.XmlProtoToXmlConverter;
import com.android.tools.build.bundletool.xml.XmlUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.protobuf.util.JsonFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

final class DumpManager {
    private final PrintStream printStream;
    private final Path bundlePath;

    DumpManager(OutputStream outputStream, Path bundlePath) {
        this.printStream = new PrintStream(outputStream);
        this.bundlePath = bundlePath;
    }

    void printManifest(BundleModuleName moduleName, Optional<String> xPathExpression) {
        String output;
        ZipPath manifestPath = ZipPath.create(moduleName.getName()).resolve(BundleModule.SpecialModuleEntry.ANDROID_MANIFEST.getPath());
        XmlProtoNode manifestProto = new XmlProtoNode(DumpManager.extractAndParse(this.bundlePath, manifestPath, Resources.XmlNode::parseFrom));
        Document document = XmlProtoToXmlConverter.convert(manifestProto);
        if (xPathExpression.isPresent()) {
            try {
                XPath xPath = XPathFactory.newInstance().newXPath();
                xPath.setNamespaceContext(new XmlNamespaceContext(manifestProto));
                XPathExpression compiledXPathExpression = xPath.compile(xPathExpression.get());
                XPathResolver.XPathResult xPathResult = XPathResolver.resolve(document, compiledXPathExpression);
                output = xPathResult.toString();
            }
            catch (XPathExpressionException e2) {
                throw new ValidationException("Error in the XPath expression: " + xPathExpression, e2);
            }
        } else {
            output = XmlUtils.documentToString(document);
        }
        this.printStream.println(output.trim());
    }

    void printResources(Predicate<ResourceTableEntry> resourcePredicate, boolean printValues) {
        ImmutableList resourceTables;
        try {
            ZipFile zipFile = new ZipFile(this.bundlePath.toFile());
            Object object = null;
            try {
                resourceTables = ZipUtils.allFileEntriesPaths(zipFile).filter(path -> path.endsWith(BundleModule.SpecialModuleEntry.RESOURCE_TABLE.getPath())).map(path -> DumpManager.extractAndParse(zipFile, path, Resources.ResourceTable::parseFrom)).collect(ImmutableList.toImmutableList());
            }
            catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            }
            finally {
                if (zipFile != null) {
                    if (object != null) {
                        try {
                            zipFile.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        zipFile.close();
                    }
                }
            }
        }
        catch (IOException e2) {
            throw new ValidationException("Error occurred when reading the bundle.", e2);
        }
        ImmutableListMultimap<String, ResourceTableEntry> entriesByPackageName = resourceTables.stream().flatMap(ResourcesUtils::entries).filter(resourcePredicate).collect(CollectorUtils.groupingBySortedKeys(entry -> entry.getPackage().getPackageName()));
        for (String packageName : entriesByPackageName.keySet()) {
            this.printStream.printf("Package '%s':%n", packageName);
            ((ImmutableList)entriesByPackageName.get((Object)packageName)).forEach(entry -> this.printEntry((ResourceTableEntry)entry, printValues));
            this.printStream.println();
        }
    }

    void printBundleConfig() {
        try (ZipFile zipFile = new ZipFile(this.bundlePath.toFile());){
            Config.BundleConfig bundleConfig = DumpManager.extractAndParse(zipFile, ZipPath.create("BundleConfig.pb"), Config.BundleConfig::parseFrom);
            this.printStream.println(JsonFormat.printer().print(bundleConfig));
        }
        catch (IOException e2) {
            throw new ValidationException("Error occurred when reading the bundle.", e2);
        }
    }

    private void printEntry(ResourceTableEntry entry, boolean printValues) {
        this.printStream.printf("0x%08x - %s/%s%n", entry.getResourceId().getFullResourceId(), entry.getType().getName(), entry.getEntry().getName());
        for (Resources.ConfigValue configValue : entry.getEntry().getConfigValueList()) {
            this.printStream.print('\t');
            if (configValue.getConfig().equals(ConfigurationOuterClass.Configuration.getDefaultInstance())) {
                this.printStream.print("(default)");
            } else {
                this.printStream.print(configValue.getConfig().toString().trim());
            }
            if (printValues) {
                this.printStream.printf(" - [%s] %s", XmlProtoPrintUtils.getValueTypeAsString(configValue.getValue()), XmlProtoPrintUtils.getValueAsString(configValue.getValue()));
            }
            this.printStream.println();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static <T> T extractAndParse(Path bundlePath, ZipPath filePath, ProtoParser<T> protoParser) {
        try (ZipFile zipFile = new ZipFile(bundlePath.toFile());){
            T t3 = DumpManager.extractAndParse(zipFile, filePath, protoParser);
            return t3;
        }
        catch (ZipException e2) {
            throw new BundleInvalidZipException(e2);
        }
        catch (IOException e3) {
            throw new UncheckedIOException("Error occurred when trying to open the bundle.", e3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static <T> T extractAndParse(ZipFile zipFile, ZipPath filePath, ProtoParser<T> protoParser) {
        ZipEntry fileEntry = zipFile.getEntry(filePath.toString());
        if (fileEntry == null) {
            throw ValidationException.builder().withMessage("File '%s' not found.", filePath).build();
        }
        try (InputStream inputStream = zipFile.getInputStream(fileEntry);){
            T t3 = protoParser.parse(inputStream);
            return t3;
        }
        catch (IOException e2) {
            throw ValidationException.builder().withMessage("Error occurred when trying to read file '%s' from bundle.", filePath).withCause(e2).build();
        }
    }

    private static interface ProtoParser<T> {
        public T parse(InputStream var1) throws IOException;
    }
}

