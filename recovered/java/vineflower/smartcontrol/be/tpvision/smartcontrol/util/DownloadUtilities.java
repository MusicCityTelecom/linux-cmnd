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
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.concurrent.locks.Lock;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

   public static void downloadFile(final String url, final Path filePath) {
      Assert.notNull(url, DownloadFileMessages.URL_CAN_NOT_BE_NULL);
      Assert.notNull(filePath, DownloadFileMessages.FILE_PATH_CAN_NOT_BE_NULL);
      HttpGet httpGet = new HttpGet(url);
      HttpClient httpClient = HttpClientBuilder.create().build();
      Lock lock = lockStriped.get(filePath);

      try {
         lock.lock();
         HttpResponse httpResponse = httpClient.execute(httpGet);
         HttpEntity httpEntity = httpResponse.getEntity();

         try (
            InputStream inputStream = httpEntity.getContent();
            OutputStream outputStream = Files.newOutputStream(filePath);
         ) {
            IOUtils.copy(inputStream, outputStream);
         }
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
      } finally {
         lock.unlock();
      }
   }

   public static HttpHeaders getHttpHeaders(final String fileName, final String contentType) {
      Assert.notNull(fileName, GetHttpHeadersMessages.FILE_NAME_CAN_NOT_BE_NULL);
      Assert.notNull(contentType, GetHttpHeadersMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
      httpHeaders.add("Content-Type", contentType);
      httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
      httpHeaders.add("Pragma", "no-cache");
      httpHeaders.add("Expires", "0");
      return httpHeaders;
   }

   public static ResponseEntity<InputStreamResource> getResponseEntity(final Path filePath, final String contentType) {
      Assert.notNull(filePath, GetResponseEntityMessages.FILE_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(contentType, GetResponseEntityMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);
      String fileName = filePath.getFileName().toString();
      return getResponseEntity(filePath, fileName, contentType);
   }

   public static ResponseEntity<InputStreamResource> getResponseEntity(final Path filePath, final String fileName, final String contentType) {
      Assert.notNull(filePath, GetResponseEntityMessages.FILE_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(fileName, GetResponseEntityMessages.FILE_NAME_CAN_NOT_BE_NULL);
      Assert.notNull(contentType, GetResponseEntityMessages.CONTENT_TYPE_CAN_NOT_BE_NULL);

      try {
         InputStream inputStream = Files.newInputStream(filePath);
         BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
         InputStreamResource inputStreamResource = new InputStreamResource(bufferedInputStream);
         HttpHeaders httpHeaders = getHttpHeaders(fileName, contentType);
         long contentLength = Files.size(filePath);
         FileTime lastModifiedTime = Files.getLastModifiedTime(filePath);
         long lastModified = lastModifiedTime.toMillis();
         return ResponseEntity.ok().headers(httpHeaders).contentLength(contentLength).lastModified(lastModified).body(inputStreamResource);
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
         return null;
      }
   }
}
