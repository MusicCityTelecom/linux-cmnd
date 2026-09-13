/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.WritableResource
 */
package org.apereo.cas.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.TemporaryFileSystemResource;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.WritableResource;

public final class CompressionUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CompressionUtils.class);
    private static final int INFLATED_ARRAY_LENGTH = 10000;

    public static String deflate(byte[] bytes) {
        String data = new String(bytes, StandardCharsets.UTF_8);
        return CompressionUtils.deflate(data);
    }

    public static String deflate(String data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data.getBytes(StandardCharsets.UTF_8));
        deflater.finish();
        byte[] buffer = new byte[data.length()];
        int resultSize = deflater.deflate(buffer);
        deflater.end();
        byte[] output = new byte[resultSize];
        System.arraycopy(buffer, 0, output, 0, resultSize);
        return EncodingUtils.encodeBase64(output);
    }

    public static String inflate(byte[] bytes) {
        Inflater inflater = new Inflater(true);
        byte[] xmlMessageBytes = new byte[10000];
        byte[] extendedBytes = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, extendedBytes, 0, bytes.length);
        extendedBytes[bytes.length] = 0;
        inflater.setInput(extendedBytes);
        return (String)FunctionUtils.doAndHandle(() -> {
            int resultLength = inflater.inflate(xmlMessageBytes);
            inflater.end();
            return new String(xmlMessageBytes, 0, resultLength, StandardCharsets.UTF_8);
        }, throwable -> null).get();
    }

    public static String decompress(String zippedBase64Str) {
        return (String)Unchecked.supplier(() -> {
            byte[] bytes = EncodingUtils.decodeBase64(zippedBase64Str);
            try (GZIPInputStream zi = new GZIPInputStream(new ByteArrayInputStream(bytes));){
                String string = IOUtils.toString((InputStream)zi, (Charset)Charset.defaultCharset());
                return string;
            }
        }).get();
    }

    public static String decodeByteArrayToString(byte[] bytes) {
        String string;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[bytes.length];
        InflaterInputStream iis = new InflaterInputStream(bais);
        try {
            int count = iis.read(buf);
            while (count != -1) {
                baos.write(buf, 0, count);
                count = iis.read(buf);
            }
            string = baos.toString(StandardCharsets.UTF_8);
        }
        catch (Throwable throwable) {
            try {
                try {
                    iis.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception e) {
                LoggingUtils.error(LOGGER, e);
                return null;
            }
        }
        iis.close();
        return string;
    }

    public static String compress(String srcTxt) {
        return (String)Unchecked.supplier(() -> {
            try (ByteArrayOutputStream rstBao = new ByteArrayOutputStream();){
                String string;
                try (GZIPOutputStream zos = new GZIPOutputStream(rstBao);){
                    zos.write(srcTxt.getBytes(StandardCharsets.UTF_8));
                    zos.flush();
                    zos.finish();
                    byte[] bytes = rstBao.toByteArray();
                    String base64 = StringUtils.remove((String)EncodingUtils.encodeBase64(bytes), (char)'\u0000');
                    string = new String(StandardCharsets.UTF_8.encode(base64).array(), StandardCharsets.UTF_8);
                }
                return string;
            }
        }).get();
    }

    public static WritableResource toZipFile(Stream<?> dataStream, Function<Object, File> converter, String prefix) {
        return (WritableResource)Unchecked.supplier(() -> {
            String date = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"));
            File file = File.createTempFile(String.format("%s-%s", prefix, date), ".zip");
            Files.deleteIfExists(file.toPath());
            HashMap<String, String> env = new HashMap<String, String>();
            env.put("create", "true");
            env.put("encoding", StandardCharsets.UTF_8.name());
            try (FileSystem zipfs = FileSystems.newFileSystem(URI.create("jar:" + file.toURI()), env);){
                dataStream.forEach(Unchecked.consumer(entry -> {
                    File sourceFile = (File)converter.apply(entry);
                    if (sourceFile.exists()) {
                        Path pathInZipfile = zipfs.getPath("/".concat(sourceFile.getName()), new String[0]);
                        Files.copy(sourceFile.toPath(), pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
                    }
                }));
            }
            return new TemporaryFileSystemResource(file);
        }).get();
    }

    @Generated
    private CompressionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

