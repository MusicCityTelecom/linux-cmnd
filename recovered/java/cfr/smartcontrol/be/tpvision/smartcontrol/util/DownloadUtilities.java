/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.util.download_utilities.DownloadFileMessages;
import be.tpvision.smartcontrol.messages.util.download_utilities.GetHttpHeadersMessages;
import be.tpvision.smartcontrol.messages.util.download_utilities.GetResponseEntityMessages;
import com.google.common.util.concurrent.Striped;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.locks.Lock;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

public class DownloadUtilities {
    private static final Logger logger = LoggerFactory.getLogger(DownloadUtilities.class);
    private static final Striped<Lock> lockStriped = Striped.lock(50);

    private DownloadUtilities() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void downloadFile(String url, Path filePath) {
        Assert.notNull((Object)url, DownloadFileMessages.URL_CAN_NOT_BE_NULL);
        Assert.notNull((Object)filePath, DownloadFileMessages.FILE_PATH_CAN_NOT_BE_NULL);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        Lock lock = lockStriped.get(filePath);
        try {
            lock.lock();
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            try (InputStream inputStream = httpEntity.getContent();
                 OutputStream outputStream = Files.newOutputStream(filePath, new OpenOption[0]);){
                IOUtils.copy(inputStream, outputStream);
            }
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
        finally {
            lock.unlock();
        }
    }

    public static HttpHeaders getHttpHeaders(String fileName, String contentType) {
        Assert.notNull((Object)fileName, GetHttpHeadersMessages.FILE_NAME_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentType, GetHttpHeadersMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        httpHeaders.add("Content-Type", contentType);
        httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        return httpHeaders;
    }

    public static ResponseEntity<InputStreamResource> getResponseEntity(Path filePath, String contentType) {
        Assert.notNull((Object)filePath, GetResponseEntityMessages.FILE_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentType, GetResponseEntityMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);
        String fileName = filePath.getFileName().toString();
        return DownloadUtilities.getResponseEntity(filePath, fileName, contentType);
    }

    public static ResponseEntity<InputStreamResource> getResponseEntity(Path filePath, String fileName, String contentType) {
        Assert.notNull((Object)filePath, GetResponseEntityMessages.FILE_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)fileName, GetResponseEntityMessages.FILE_NAME_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentType, GetResponseEntityMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);
        try {
            InputStream inputStream = Files.newInputStream(filePath, new OpenOption[0]);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            InputStreamResource inputStreamResource = new InputStreamResource(bufferedInputStream);
            HttpHeaders httpHeaders = DownloadUtilities.getHttpHeaders(fileName, contentType);
            long contentLength = Files.size(filePath);
            FileTime lastModifiedTime = Files.getLastModifiedTime(filePath, new LinkOption[0]);
            long lastModified = lastModifiedTime.toMillis();
            return ((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)ResponseEntity.ok().headers(httpHeaders)).contentLength(contentLength).lastModified(lastModified)).body(inputStreamResource);
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }
}

