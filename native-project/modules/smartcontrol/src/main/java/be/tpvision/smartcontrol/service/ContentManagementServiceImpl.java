package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.domain.FtpSettings;
import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.messages.services.content_management.DeleteHardwarePathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.DeleteTempContentMessages;
import be.tpvision.smartcontrol.messages.services.content_management.DownloadContentMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GenerateRevisionFileMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetCmsPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetContentFileName;
import be.tpvision.smartcontrol.messages.services.content_management.GetFlagPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetHardwareCmsPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetHardwarePath;
import be.tpvision.smartcontrol.messages.services.content_management.GetLatestContentListMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetRevisionPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetSystemConfigMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetTempCmsPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetTempContentTimeStampPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetTempControlMetaPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.GetTempSystemConfigPathMessages;
import be.tpvision.smartcontrol.messages.services.content_management.IsHardwareBusyMessages;
import be.tpvision.smartcontrol.messages.services.content_management.SetContentMessages;
import be.tpvision.smartcontrol.messages.services.content_management.SetHardwareBusyMessages;
import be.tpvision.smartcontrol.messages.services.content_management.UnassignContentMessages;
import be.tpvision.smartcontrol.messages.services.content_management.UpdateContentMessages;
import be.tpvision.smartcontrol.messages.services.content_management.UploadSystemConfigToFtpMessages;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import be.tpvision.smartcontrol.rest.mappers.content.SmartCmsContentMapper;
import be.tpvision.smartcontrol.rest.view_models.content.SmartCmsContentViewModel;
import be.tpvision.smartcontrol.util.DownloadUtilities;
import be.tpvision.smartcontrol.util.SecurityUtilities;
import be.tpvision.smartcontrol.util.UploadUtilities;
import com.google.common.util.concurrent.Striped;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class ContentManagementServiceImpl implements ContentManagementService {
   private static final Logger logger = LoggerFactory.getLogger(ContentManagementServiceImpl.class);
   private static final Striped<Lock> contentLock = Striped.lock(50);
   private final String contentUrl;
   private final String contentListUrl;
   private final int tempAgeMinutes;
   private final String contentPrefix;
   private final String contentFileExtension;
   private static final String SYSTEM_CONFIG_FILE_NAME = "sysconfig.txt";
   private static final String CONTROL_META_FILE_NAME = "controlmeta.js";
   private final Path contentPath;
   private final Path tempContentPath;
   private final String systemConfigRoot;
   private final int smartControlServerPort;
   private final ContentServiceJdbc contentServiceJdbc;
   private final SettingsServiceJdbc settingsServiceJdbc;
   private final HardwareServiceJdbc hardwareServiceJdbc;
   private final DeviceServiceJdbc deviceServiceJdbc;
   private final RestTemplate restTemplate;

   @Autowired
   public ContentManagementServiceImpl(
      @Value("${smartcms.content.url}") final String contentUrl,
      @Value("${smartcms.content.list.url}") final String contentListUrl,
      @Value("${smartcms.content.temp.age.minutes}") final int tempAgeMinutes,
      @Value("${smartcontrol.content.prefix}") final String contentPrefix,
      @Value("${smartcontrol.content.file-extension}") final String contentFileExtension,
      @Value("${smartcontrol.system.content.path}") final String contentPath,
      @Value("${smartcontrol.system.content.temp-path}") final String tempContentPath,
      @Value("${smartcontrol.content.system-config.root}") final String systemConfigRoot,
      @Value("${server.port}") final int smartControlServerPort,
      final ContentServiceJdbc contentServiceJdbc,
      final SettingsServiceJdbc settingsServiceJdbc,
      final HardwareServiceJdbc hardwareServiceJdbc,
      final DeviceServiceJdbc deviceServiceJdbc,
      final RestTemplate restTemplate
   ) {
      this.contentUrl = contentUrl;
      this.contentListUrl = contentListUrl;
      this.tempAgeMinutes = tempAgeMinutes;
      this.contentPrefix = contentPrefix;
      this.contentFileExtension = contentFileExtension;
      this.contentPath = Paths.get(contentPath);
      this.tempContentPath = Paths.get(tempContentPath);
      this.systemConfigRoot = systemConfigRoot;
      this.smartControlServerPort = smartControlServerPort;
      this.contentServiceJdbc = contentServiceJdbc;
      this.settingsServiceJdbc = settingsServiceJdbc;
      this.hardwareServiceJdbc = hardwareServiceJdbc;
      this.deviceServiceJdbc = deviceServiceJdbc;
      this.restTemplate = restTemplate;
   }

   @Override
   public String getContentFileName(final String hardwareKey) {
      Assert.state(this.contentPrefix != null, GetContentFileName.CONTENT_PREFIX_CAN_NOT_BE_NULL);
      Assert.state(this.contentFileExtension != null, GetContentFileName.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetContentFileName.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetContentFileName.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      return String.format("%s%s%s", this.contentPrefix, hardwareKey, this.contentFileExtension);
   }

   private Path downloadContent(final String contentId, final boolean rotated) {
      Assert.notNull(this.contentPath, DownloadContentMessages.CONTENT_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(this.contentUrl, DownloadContentMessages.CONTENT_URL_CAN_NOT_BE_NULL);
      Assert.notNull(this.contentServiceJdbc, DownloadContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.notNull(contentId, DownloadContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);

      Path cmsPath;
      try {
         Files.createDirectories(this.contentPath);
         cmsPath = this.getCmsPath(contentId, rotated, false);
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
         return null;
      }

      Content content = this.contentServiceJdbc.getContent(contentId);
      Assert.state(content != null, DownloadContentMessages.CONTENT_CAN_NOT_BE_NULL);
      Content.Orientation orientation = content.getOrientation();
      Assert.state(orientation != null, DownloadContentMessages.ORIENTATION_CAN_NOT_BE_NULL);
      boolean isOrientationPortrait = orientation.equals(Content.Orientation.PORTRAIT);
      String queryParameters;
      if (isOrientationPortrait) {
         String rotationParameter = "?orientation=";
         rotationParameter = rotationParameter + (rotated ? 0 : 1);
         queryParameters = rotationParameter;
      } else {
         queryParameters = "";
      }

      String cmsUrl = this.contentUrl + contentId + queryParameters;
      DownloadUtilities.downloadFile(cmsUrl, cmsPath);

      try {
         BasicFileAttributes basicFileAttributes = Files.readAttributes(cmsPath, BasicFileAttributes.class);
         FileTime lastModifiedTime = basicFileAttributes.lastModifiedTime();
         Instant instant = lastModifiedTime.toInstant();
         ZoneId zoneId = ZoneId.systemDefault();
         LocalDateTime lastModified = LocalDateTime.ofInstant(instant, zoneId);
         content.setLocalChanged(lastModified);
         this.contentServiceJdbc.updateContent(content);
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
      }

      return cmsPath;
   }

   private List<Content> getLatestContentList() {
      Assert.state(this.restTemplate != null, GetLatestContentListMessages.REST_TEMPLATE_CAN_NOT_BE_NULL);
      Assert.state(this.contentListUrl != null, GetLatestContentListMessages.CONTENT_LIST_URL_CAN_NOT_BE_NULL);
      SmartCmsContentViewModel[] smartCmsContentViewModelArray = this.restTemplate.getForObject(this.contentListUrl, SmartCmsContentViewModel[].class);
      if (smartCmsContentViewModelArray == null) {
         return Collections.emptyList();
      }

      List<SmartCmsContentViewModel> smartCmsContentViewModelList = Arrays.asList(smartCmsContentViewModelArray);
      return SmartCmsContentMapper.toContentList(smartCmsContentViewModelList);
   }

   private void updateContent(final Content newContent, final boolean redownloadIfUpdated, final boolean updateHardwareIfUpdated) {
      Assert.state(this.contentServiceJdbc != null, UpdateContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.notNull(newContent, UpdateContentMessages.NEW_CONTENT_CAN_NOT_BE_NULL);
      String contentId = newContent.getId();
      Lock lock = contentLock.get(contentId);

      try {
         lock.lock();
         Content oldContent = this.contentServiceJdbc.getContent(contentId);
         if (oldContent == null) {
            this.contentServiceJdbc.addContent(newContent);
         } else {
            boolean isUpdated = false;
            LocalDateTime oldPublishDate = oldContent.getPublishDate();
            LocalDateTime newPublishDate = newContent.getPublishDate();
            if (newPublishDate != null) {
               if (newPublishDate.isAfter(oldPublishDate)) {
                  this.contentServiceJdbc.updateContent(newContent);
                  isUpdated = true;
               }

               LocalDateTime localChanged = oldContent.getLocalChanged();
               if (localChanged == null || newPublishDate.isAfter(localChanged)) {
                  isUpdated = true;
               }

               if (redownloadIfUpdated && isUpdated) {
                  this.downloadContent(contentId, false);
                  Content.Orientation orientation = newContent.getOrientation();
                  if (orientation.equals(Content.Orientation.PORTRAIT)) {
                     this.downloadContent(contentId, true);
                  }
               }

               if (updateHardwareIfUpdated && isUpdated) {
                  Set<Hardware> hardwareSet = this.hardwareServiceJdbc.getHardwareByContentId(contentId);
                  hardwareSet.stream().forEach(this::updateContent);
               }
            }
         }
      } finally {
         lock.unlock();
      }
   }

   @Override
   public void updateContent() {
      this.updateContent(false, false);
   }

   @Override
   public void updateContent(final Hardware hardware) {
      Assert.notNull(hardware, UpdateContentMessages.HARDWARE_CAN_NOT_BE_NULL);
      String hardwareKey = hardware.getHardwareKey();
      Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.isHardwareBusy(hardwareKey);
      if (!isHardwareBusy) {
         try {
            this.setHardwareBusy(hardwareKey, true);
            String contentId = hardware.getContentId();
            if (contentId == null) {
               return;
            }

            Path systemConfigPath = this.getTempSystemConfigPath(hardwareKey);
            Assert.state(systemConfigPath != null, UpdateContentMessages.SYSTEM_CONFIG_PATH_CAN_NOT_BE_NULL);
            Path controlMetaPath = this.getTempControlMetaPath(hardwareKey);
            Assert.state(controlMetaPath != null, UpdateContentMessages.CONTROL_META_PATH_CAN_NOT_BE_NULL);
            Path hardwarePath = this.getHardwarePath(hardwareKey);
            Assert.state(hardwarePath != null, UpdateContentMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);

            try {
               Files.createDirectories(hardwarePath);
               boolean rotated = this.deviceServiceJdbc.getContentRotatedBySerialCode(hardwareKey);
               Path cmsPath = this.getCmsPath(contentId, rotated, true);
               Path hardwareCmsPath = this.getHardwareCmsPath(hardwareKey);

               try (
                  InputStream inputStream = Files.newInputStream(cmsPath);
                  OutputStream outputStream = Files.newOutputStream(hardwareCmsPath);
               ) {
                  FileCopyUtils.copy(inputStream, outputStream);
               }

               File cmsFile = hardwareCmsPath.toFile();
               ZipFile zipFile = new ZipFile(cmsFile);
               File systemConfigFile = systemConfigPath.toFile();
               ZipParameters zipParameters = new ZipParameters();
               zipParameters.setFileNameInZip("sysconfig.txt");
               zipParameters.setSourceExternalStream(true);
               zipFile.addFile(systemConfigFile, zipParameters);
               File controlMetaFile = controlMetaPath.toFile();
               zipParameters.setFileNameInZip("controlmeta.js");
               zipFile.addFile(controlMetaFile, zipParameters);
               this.generateRevisionFile(hardwareKey);
            } catch (IOException | ZipException e) {
               throw new RuntimeException(e);
            } finally {
               try {
                  Files.deleteIfExists(systemConfigPath);
               } catch (IOException e) {
                  String message = e.getMessage();
                  logger.error(message, e);
               }

               try {
                  Files.deleteIfExists(controlMetaPath);
               } catch (IOException e) {
                  String message = e.getMessage();
                  logger.error(message, e);
               }
            }
         } finally {
            this.setHardwareBusy(hardwareKey, false);
         }
      }
   }

   @Override
   public void updateContent(final boolean redownloadIfUpdated, final boolean updateHardwareIfUpdated) {
      Assert.state(this.contentServiceJdbc != null, UpdateContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Set<Content> contentBeforeProcessing = this.contentServiceJdbc.getContent();
      HashSet<String> processedContent = new HashSet<>();
      List<Content> contentList = this.getLatestContentList();
      Assert.state(contentList != null, UpdateContentMessages.CONTENT_LIST_CAN_NOT_BE_NULL);
      Assert.state(!contentList.contains(null), UpdateContentMessages.CONTENT_LIST_CAN_NOT_CONTAIN_NULL_VALUES);

      for (Content content : contentList) {
         this.updateContent(content, redownloadIfUpdated, updateHardwareIfUpdated);
         String contentId = content.getId();
         Assert.state(contentId != null, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
         processedContent.add(contentId);
      }

      for (Content content : contentBeforeProcessing) {
         String contentId = content.getId();
         Assert.state(contentId != null, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
         if (!processedContent.contains(contentId)) {
            try {
               Path plainCmsPath = this.getCmsPath(contentId, false, false);
               Assert.state(plainCmsPath != null, UpdateContentMessages.PLAIN_CMS_PATH_CAN_NOT_BE_NULL);
               Files.deleteIfExists(plainCmsPath);
               Path rotatedCmsPath = this.getCmsPath(contentId, true, false);
               Assert.state(rotatedCmsPath != null, UpdateContentMessages.ROTATED_CMS_PATH_CAN_NOT_BE_NULL);
               Files.deleteIfExists(rotatedCmsPath);

               for (Hardware hardware : this.hardwareServiceJdbc.getHardwareByContentId(contentId)) {
                  String hardwareKey = hardware.getHardwareKey();
                  Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
                  Assert.state(!hardwareKey.isEmpty(), UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
                  this.deleteHardwarePath(hardwareKey);
                  hardware.setContentId(null);
                  this.hardwareServiceJdbc.updateHardware(hardware);
               }

               this.contentServiceJdbc.deleteContent(content);
            } catch (IOException e) {
               String message = e.getMessage();
               logger.error(message, e);
            }
         }
      }
   }

   @Override
   public void updateContent(final String contentId, final boolean redownloadIfUpdated, final boolean updateHardwareIfUpdated) {
      Assert.notNull(contentId, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
      List<Content> contentList = this.getLatestContentList();
      if (!contentList.isEmpty()) {
         Optional<Content> firstNewContent = contentList.stream().filter(content -> content.getId().equals(contentId)).findFirst();
         if (firstNewContent.isPresent()) {
            Content newContent = firstNewContent.get();
            this.updateContent(newContent, redownloadIfUpdated, updateHardwareIfUpdated);
         }
      }
   }

   @Override
   public void setContent(final long deviceId, final String contentId) {
      Assert.state(this.deviceServiceJdbc != null, SetContentMessages.DEVICE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.state(this.hardwareServiceJdbc != null, SetContentMessages.HARDWARE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.isTrue(deviceId >= 0L, SetContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      String hardwareKey = this.deviceServiceJdbc.getSerialCode(deviceId);
      Assert.state(hardwareKey != null, SetContentMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      boolean isHardwareBusy = this.isHardwareBusy(hardwareKey);
      Assert.state(!isHardwareBusy, "Hardware is busy.");
      Hardware hardware = this.hardwareServiceJdbc.getHardware(hardwareKey);
      if (hardware != null) {
         hardware.setContentId(contentId);
         this.hardwareServiceJdbc.updateHardware(hardware);
      } else {
         hardware = new Hardware();
         hardware.setHardwareKey(hardwareKey);
         hardware.setContentId(contentId);
         this.hardwareServiceJdbc.addHardware(hardware);
      }

      this.updateContent(contentId, true, false);
      this.updateContent(hardware);
   }

   @Override
   public void unassignContent(final long deviceId) {
      Assert.state(this.deviceServiceJdbc != null, UnassignContentMessages.DEVICE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.state(this.hardwareServiceJdbc != null, UnassignContentMessages.HARDWARE_SERVICE_JDBC_CAN_NOT_BE_NULL);
      Assert.isTrue(deviceId >= 0L, UnassignContentMessages.DEVICE_ID_HAS_TO_BE_A_POSITIVE_NUMBER);
      String hardwareKey = this.deviceServiceJdbc.getSerialCode(deviceId);
      Assert.state(hardwareKey != null, UnassignContentMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      boolean isHardwareBusy = this.isHardwareBusy(hardwareKey);
      Assert.state(!isHardwareBusy, "Hardware is busy.");
      Hardware hardware = this.hardwareServiceJdbc.getHardware(hardwareKey);
      Assert.state(hardware != null, UnassignContentMessages.HARDWARE_CAN_NOT_BE_NULL);
      hardware.setContentId(null);
      this.hardwareServiceJdbc.updateHardware(hardware);
      this.deleteHardwarePath(hardwareKey);
   }

   @Override
   public void deleteTempContent() {
      Assert.state(this.tempContentPath != null, DeleteTempContentMessages.TEMP_CONTENT_PATH_CAN_NOT_BE_NULL);

      try {
         boolean tempContentPathExists = Files.exists(this.tempContentPath);
         if (tempContentPathExists) {
            FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
               public FileVisitResult postVisitDirectory(final Path directoryPath, final IOException e) throws IOException {
                  if (e == null) {
                     DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);
                     Iterator<Path> directoryStreamIterator = directoryStream.iterator();
                     boolean isDirectoryEmpty = !directoryStreamIterator.hasNext();
                     directoryStream.close();
                     if (isDirectoryEmpty && !directoryPath.equals(ContentManagementServiceImpl.this.tempContentPath)) {
                        Files.delete(directoryPath);
                     }

                     return FileVisitResult.CONTINUE;
                  } else {
                     throw e;
                  }
               }

               public FileVisitResult visitFile(final Path filePath, final BasicFileAttributes basicFileAttributes) throws IOException {
                  FileTime lastModified = basicFileAttributes.lastModifiedTime();
                  long deltaMillis = System.currentTimeMillis() - lastModified.toMillis();
                  long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(deltaMillis);
                  if (deltaMinutes >= ContentManagementServiceImpl.this.tempAgeMinutes) {
                     Files.delete(filePath);
                  }

                  return FileVisitResult.CONTINUE;
               }

               public FileVisitResult visitFileFailed(final Path filePath, final IOException e) throws IOException {
                  String message = e.getMessage();
                  ContentManagementServiceImpl.logger.error(message, e);
                  return FileVisitResult.CONTINUE;
               }
            };
            Files.walkFileTree(this.tempContentPath, fileVisitor);
         }
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
      }
   }

   private Path getCmsPath(final String contentId, final boolean contentRotated, final boolean downloadIfNotExists) throws IOException {
      Assert.notNull(this.contentPath, GetCmsPathMessages.CONTENT_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(this.contentFileExtension, GetCmsPathMessages.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
      Assert.notNull(contentId, GetCmsPathMessages.CONTENT_ID_CAN_NOT_BE_NULL);
      Content content = this.contentServiceJdbc.getContent(contentId);
      Assert.state(content != null, GetCmsPathMessages.CONTENT_CAN_NOT_BE_NULL);
      Content.Orientation orientation = content.getOrientation();
      Assert.state(orientation != null, GetCmsPathMessages.ORIENTATION_CAN_NOT_BE_NULL);
      boolean shouldBeRotated = orientation.equals(Content.Orientation.PORTRAIT) && contentRotated;
      String cmsFolder = shouldBeRotated ? "rotated" : "plain";
      Path cmsFolderPath = this.contentPath.resolve(cmsFolder);
      Files.createDirectories(cmsFolderPath);
      Path cmsPath = cmsFolderPath.resolve(contentId + this.contentFileExtension);
      Lock lock = contentLock.get(contentId);

      try {
         lock.lock();
         boolean cmsFileExists = Files.exists(cmsPath);
         if (!cmsFileExists && downloadIfNotExists) {
            this.updateContent(contentId, false, false);
            return this.downloadContent(contentId, contentRotated);
         }
      } finally {
         lock.unlock();
      }

      return cmsPath;
   }

   private String getSystemConfig(final String hardwareKey) {
      Assert.state(this.systemConfigRoot != null, GetSystemConfigMessages.SMART_CONTROL_CONTEXT_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetSystemConfigMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      LinkedHashMap<String, String> systemConfigMap = new LinkedHashMap<>();
      String serverIp = this.settingsServiceJdbc.getServerIp();
      Assert.state(serverIp != null, GetSystemConfigMessages.SERVER_IP_CAN_NOT_BE_NULL);
      String serverAddress = String.format("%s:%d", serverIp, this.smartControlServerPort);
      systemConfigMap.put("SERVER_ADDRESS", serverAddress);
      systemConfigMap.put("HARDWAREKEY", hardwareKey);
      systemConfigMap.put("ROOT", this.systemConfigRoot);
      String delimiter = ";&##&;";
      StringJoiner stringJoiner = new StringJoiner(";&##&;", "", ";&##&;");
      Set<Entry<String, String>> systemConfigEntrySet = systemConfigMap.entrySet();
      systemConfigEntrySet.stream().forEach(entry -> {
         String entryString = entry.toString();
         stringJoiner.add(entryString);
      });
      return stringJoiner.toString();
   }

   private Path getTempContentTimeStampPath(final String path) {
      Assert.state(this.tempContentPath != null, GetTempContentTimeStampPathMessages.TEMP_CONTENT_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(path, GetTempContentTimeStampPathMessages.PATH_CAN_NOT_BE_NULL);

      try {
         Files.createDirectories(this.tempContentPath);
         Path tempDirectory = Files.createTempDirectory(this.tempContentPath, null);
         return tempDirectory.resolve(path);
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
         return null;
      }
   }

   private Path getTempSystemConfigPath(final String hardwareKey) {
      Assert.notNull(hardwareKey, GetTempSystemConfigPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path tempSystemConfigPath = this.getTempContentTimeStampPath("sysconfig.txt");
      Assert.state(tempSystemConfigPath != null, GetTempSystemConfigPathMessages.TEMP_SYSTEM_CONFIG_PATH_CAN_NOT_BE_NULL);

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tempSystemConfigPath)) {
         String systemConfigLine = this.getSystemConfig(hardwareKey);
         bufferedWriter.write(systemConfigLine);
         return tempSystemConfigPath;
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
         return null;
      }
   }

   private Path getTempControlMetaPath(final String hardwareKey) {
      Assert.notNull(hardwareKey, GetTempControlMetaPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path tempControlMetaPath = this.getTempContentTimeStampPath("controlmeta.js");
      Assert.state(tempControlMetaPath != null, GetTempControlMetaPathMessages.TEMP_CONTROL_META_PATH_CAN_NOT_BE_NULL);

      try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tempControlMetaPath)) {
         String systemConfigLine = this.getSystemConfig(hardwareKey);
         String controlMetaLine = String.format("const controlmeta = \"%s\";", systemConfigLine);
         bufferedWriter.write(controlMetaLine);
         return tempControlMetaPath;
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
         return null;
      }
   }

   public Path getTempCmsPath(final String hardwareKey) {
      Assert.state(this.tempContentPath != null, GetTempCmsPathMessages.TEMP_CONTENT_PATH_CAN_NOT_BE_NULL);
      Assert.state(this.contentPrefix != null, GetTempCmsPathMessages.CONTENT_PREFIX_CAN_NOT_BE_NULL);
      Assert.state(this.contentFileExtension != null, GetTempCmsPathMessages.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetTempCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path systemConfigPath = null;

      try {
         String tempCmsFileName = this.contentPrefix + hardwareKey + this.contentFileExtension;
         Path tempCmsPath = this.getTempContentTimeStampPath(tempCmsFileName);
         Assert.state(tempCmsPath != null, GetTempCmsPathMessages.TEMP_CMS_PATH_CAN_NOT_BE_NULL);
         File cmsFile = tempCmsPath.toFile();
         ZipFile zipFile = new ZipFile(cmsFile);
         ZipParameters zipParameters = new ZipParameters();
         zipParameters.setCompressionMethod(8);
         zipParameters.setCompressionLevel(5);
         systemConfigPath = this.getTempSystemConfigPath(hardwareKey);
         Assert.state(systemConfigPath != null, GetTempCmsPathMessages.SYSTEM_CONFIG_PATH_CAN_NOT_BE_NULL);
         File systemConfigFile = systemConfigPath.toFile();
         zipParameters.setFileNameInZip("sysconfig.txt");
         zipParameters.setSourceExternalStream(true);
         zipFile.addFile(systemConfigFile, zipParameters);
         return tempCmsPath;
      } catch (ZipException e) {
         String message = e.getMessage();
         logger.error(message, e);
      } finally {
         if (systemConfigPath != null) {
            try {
               Files.deleteIfExists(systemConfigPath);
            } catch (IOException e) {
               String message = e.getMessage();
               logger.error(message, e);
            }
         }
      }

      return null;
   }

   private Path getHardwarePath(final String hardwareKey) {
      Assert.state(this.contentPath != null, GetHardwarePath.CONTENT_PATH_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetHardwarePath.HARDWARE_KEY_CAN_NOT_BE_NULL);
      return this.contentPath.resolve(hardwareKey);
   }

   @Override
   public void deleteHardwarePath(final String hardwareKey) {
      Assert.notNull(hardwareKey, DeleteHardwarePathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, DeleteHardwarePathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
      File hardwareFile = hardwarePath.toFile();
      Assert.state(hardwareFile != null, DeleteHardwarePathMessages.HARDWARE_FILE_CAN_NOT_BE_NULL);
      FileSystemUtils.deleteRecursively(hardwareFile);
   }

   @Override
   public Path getHardwareCmsPath(final String hardwareKey) {
      Assert.notNull(hardwareKey, GetHardwareCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetHardwareCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, GetHardwareCmsPathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
      String cmsFileName = String.format("%s%s%s", this.contentPrefix, hardwareKey, this.contentFileExtension);
      return hardwarePath.resolve(cmsFileName);
   }

   private Path getFlagPath(final String hardwareKey) {
      Assert.notNull(hardwareKey, GetFlagPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetFlagPathMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, GetFlagPathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
      return hardwarePath.resolve("busy.flag");
   }

   @Override
   public boolean isHardwareBusy(final String hardwareKey) {
      Assert.notNull(hardwareKey, IsHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), IsHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      Path flagPath = this.getFlagPath(hardwareKey);
      return Files.exists(flagPath);
   }

   private void setHardwareBusy(final String hardwareKey, final boolean value) {
      Assert.notNull(hardwareKey, SetHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), SetHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, SetHardwareBusyMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);

      try {
         Files.createDirectories(hardwarePath);
         Path flagPath = this.getFlagPath(hardwareKey);
         Assert.state(flagPath != null, SetHardwareBusyMessages.FLAG_PATH_CAN_NOT_BE_NULL);
         if (value) {
            boolean flagPathExists = Files.exists(flagPath);
            if (!flagPathExists) {
               Files.createFile(flagPath);
            }
         } else {
            Files.deleteIfExists(flagPath);
         }
      } catch (IOException e) {
         String message = e.getMessage();
         logger.error(message, e);
      }
   }

   private void generateRevisionFile(final String hardwareKey) throws IOException {
      Assert.notNull(hardwareKey, GenerateRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GenerateRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, GenerateRevisionFileMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
      Files.createDirectories(hardwarePath);
      Path cmsPath = this.getHardwareCmsPath(hardwareKey);
      Assert.state(cmsPath != null, GenerateRevisionFileMessages.CMS_PATH_CAN_NOT_BE_NULL);
      boolean cmsPathExists = Files.exists(cmsPath);
      Assert.state(cmsPathExists, "Cms path does not exist");
      Path revisionPath = this.getRevisionPath(hardwareKey, false);
      Assert.state(revisionPath != null, GenerateRevisionFileMessages.REVISION_PATH_CAN_NOT_BE_NULL);

      try (
         InputStream inputStream = Files.newInputStream(cmsPath);
         BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
      ) {
         String md5Hex = DigestUtils.md5Hex(bufferedInputStream);
         byte[] md5HexBytes = md5Hex.getBytes();
         Files.write(revisionPath, md5HexBytes);
      }
   }

   @Override
   public Path getRevisionPath(final String hardwareKey, final boolean generateIfNotExists) {
      Path hardwarePath = this.getHardwarePath(hardwareKey);
      Assert.state(hardwarePath != null, GetRevisionPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path revisionPath = hardwarePath.resolve("revision.txt");
      Assert.state(revisionPath != null, GetRevisionPathMessages.REVISION_PATH_CAN_NOT_BE_NULL);
      boolean revisionFileExists = Files.exists(revisionPath);
      if (!revisionFileExists && generateIfNotExists) {
         try {
            this.generateRevisionFile(hardwareKey);
         } catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
         }
      }

      return revisionPath;
   }

   @Override
   public void uploadSystemConfigToFtp(final DeviceDTO device) {
      Assert.notNull(device, UploadSystemConfigToFtpMessages.DEVICE_CAN_NOT_BE_NULL);
      StringWrapper serialCode = device.getSerialCode();
      Assert.state(serialCode != null, UploadSystemConfigToFtpMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
      String hardwareKey = serialCode.getValue();
      Assert.state(hardwareKey != null, UploadSystemConfigToFtpMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Path cmsPath = this.getTempCmsPath(hardwareKey);
      Assert.state(cmsPath != null, UploadSystemConfigToFtpMessages.CMS_PATH_CAN_NOT_BE_NULL);
      IpDestination ipDestination = device.getAddress();
      Assert.state(ipDestination != null, UploadSystemConfigToFtpMessages.IP_DESTINATION_CAN_NOT_BE_NULL);
      InetSocketAddress inetSocketAddress = ipDestination.getAddress();
      Assert.state(inetSocketAddress != null, UploadSystemConfigToFtpMessages.INET_SOCKET_ADDRESS_CAN_NOT_BE_NULL);
      InetAddress inetAddress = inetSocketAddress.getAddress();
      Assert.state(inetAddress != null, UploadSystemConfigToFtpMessages.INET_ADDRESS_CAN_NOT_BE_NULL);
      String ip = inetAddress.getHostAddress();
      Assert.state(ip != null, UploadSystemConfigToFtpMessages.IP_CAN_NOT_BE_NULL);
      FtpSettings ftpSettings = device.getFtpSettings();
      Assert.state(ftpSettings != null, UploadSystemConfigToFtpMessages.FTP_SETTINGS_CAN_NOT_BE_NULL);
      boolean useDefault = ftpSettings.isUseDefault();
      if (useDefault) {
         ftpSettings = this.settingsServiceJdbc.getDefaultFtpSettings();
      }

      Integer port = ftpSettings.getPort();
      Assert.state(port != null, UploadSystemConfigToFtpMessages.PORT_CAN_NOT_BE_NULL);
      String username = ftpSettings.getUsername();
      Assert.state(username != null, UploadSystemConfigToFtpMessages.USERNAME_CAN_NOT_BE_NULL);
      String encryptedPassword = ftpSettings.getPassword();
      Assert.state(encryptedPassword != null, UploadSystemConfigToFtpMessages.ENCRYPTED_PASSWORD_CAN_NOT_BE_NULL);
      String decryptedPassword = SecurityUtilities.decrypt(encryptedPassword);
      Assert.state(decryptedPassword != null, UploadSystemConfigToFtpMessages.DECRYPTED_PASSWORD_CAN_NOT_BE_NULL);
      UploadUtilities.uploadFile(cmsPath, ip, port, username, decryptedPassword);
   }
}
