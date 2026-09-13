package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.util.upload_utilities.UploadFileMessages;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class UploadUtilities {
   private static final Logger logger = LoggerFactory.getLogger(UploadUtilities.class);

   private UploadUtilities() {
   }

   public static void uploadFile(final Path filePath, final String ip, final int port, final String username, final String password) {
      Assert.notNull(filePath, UploadFileMessages.FILE_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(ip, UploadFileMessages.IP_CAN_NOT_BE_NULL);
      Assert.notNull(port, UploadFileMessages.PORT_CAN_NOT_BE_NULL);
      Assert.notNull(username, UploadFileMessages.USERNAME_CAN_NOT_BE_NULL);
      Assert.notNull(password, UploadFileMessages.PASSWORD_CAN_NOT_BE_NULL);
      FTPClient ftpClient = new FTPClient();
      String failedToUploadFileMessage = UploadFileMessages.getFailedToUploadFileMessage(filePath);

      try (
         InputStream inputStream = Files.newInputStream(filePath);
         BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
      ) {
         ftpClient.setConnectTimeout(500);
         ftpClient.connect(ip, port);
         ftpClient.login(username, password);
         ftpClient.enterLocalPassiveMode();
         ftpClient.setFileType(2);
         Path fileNamePath = filePath.getFileName();
         Assert.state(fileNamePath != null, UploadFileMessages.FILE_NAME_PATH_CAN_NOT_BE_NULL);
         String fileNameString = fileNamePath.toString();
         boolean done = ftpClient.storeFile(fileNameString, bufferedInputStream);
         if (!done) {
            throw new FailedToUploadFileToFtpException(failedToUploadFileMessage);
         }
      } catch (IOException ioException) {
         throw new FailedToUploadFileToFtpException(failedToUploadFileMessage, ioException);
      } finally {
         try {
            if (ftpClient.isConnected()) {
               ftpClient.logout();
               ftpClient.disconnect();
            }
         } catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
         }
      }
   }
}
