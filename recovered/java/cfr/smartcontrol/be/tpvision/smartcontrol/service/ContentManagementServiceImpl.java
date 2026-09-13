/*
 * Decompiled with CFR 0.152.
 */
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
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.service.ContentServiceJdbc;
import be.tpvision.smartcontrol.service.DeviceServiceJdbc;
import be.tpvision.smartcontrol.service.HardwareServiceJdbc;
import be.tpvision.smartcontrol.service.SettingsServiceJdbc;
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
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
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
public class ContentManagementServiceImpl
implements ContentManagementService {
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
    public ContentManagementServiceImpl(@Value(value="${smartcms.content.url}") String contentUrl, @Value(value="${smartcms.content.list.url}") String contentListUrl, @Value(value="${smartcms.content.temp.age.minutes}") int tempAgeMinutes, @Value(value="${smartcontrol.content.prefix}") String contentPrefix, @Value(value="${smartcontrol.content.file-extension}") String contentFileExtension, @Value(value="${smartcontrol.system.content.path}") String contentPath, @Value(value="${smartcontrol.system.content.temp-path}") String tempContentPath, @Value(value="${smartcontrol.content.system-config.root}") String systemConfigRoot, @Value(value="${server.port}") int smartControlServerPort, ContentServiceJdbc contentServiceJdbc, SettingsServiceJdbc settingsServiceJdbc, HardwareServiceJdbc hardwareServiceJdbc, DeviceServiceJdbc deviceServiceJdbc, RestTemplate restTemplate) {
        this.contentUrl = contentUrl;
        this.contentListUrl = contentListUrl;
        this.tempAgeMinutes = tempAgeMinutes;
        this.contentPrefix = contentPrefix;
        this.contentFileExtension = contentFileExtension;
        this.contentPath = Paths.get(contentPath, new String[0]);
        this.tempContentPath = Paths.get(tempContentPath, new String[0]);
        this.systemConfigRoot = systemConfigRoot;
        this.smartControlServerPort = smartControlServerPort;
        this.contentServiceJdbc = contentServiceJdbc;
        this.settingsServiceJdbc = settingsServiceJdbc;
        this.hardwareServiceJdbc = hardwareServiceJdbc;
        this.deviceServiceJdbc = deviceServiceJdbc;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getContentFileName(String hardwareKey) {
        Assert.state(this.contentPrefix != null, GetContentFileName.CONTENT_PREFIX_CAN_NOT_BE_NULL);
        Assert.state(this.contentFileExtension != null, GetContentFileName.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)hardwareKey, GetContentFileName.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), GetContentFileName.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        return String.format("%s%s%s", this.contentPrefix, hardwareKey, this.contentFileExtension);
    }

    private Path downloadContent(String contentId, boolean rotated) {
        String queryParameters;
        Path cmsPath;
        Assert.notNull((Object)this.contentPath, DownloadContentMessages.CONTENT_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)this.contentUrl, DownloadContentMessages.CONTENT_URL_CAN_NOT_BE_NULL);
        Assert.notNull((Object)this.contentServiceJdbc, DownloadContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentId, DownloadContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        try {
            Files.createDirectories(this.contentPath, new FileAttribute[0]);
            cmsPath = this.getCmsPath(contentId, rotated, false);
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
        Content content = this.contentServiceJdbc.getContent(contentId);
        Assert.state(content != null, DownloadContentMessages.CONTENT_CAN_NOT_BE_NULL);
        Content.Orientation orientation = content.getOrientation();
        Assert.state(orientation != null, DownloadContentMessages.ORIENTATION_CAN_NOT_BE_NULL);
        boolean isOrientationPortrait = orientation.equals((Object)Content.Orientation.PORTRAIT);
        if (isOrientationPortrait) {
            String rotationParameter = "?orientation=";
            queryParameters = rotationParameter = rotationParameter + (rotated ? 0 : 1);
        } else {
            queryParameters = "";
        }
        String cmsUrl = this.contentUrl + contentId + queryParameters;
        DownloadUtilities.downloadFile(cmsUrl, cmsPath);
        try {
            BasicFileAttributes basicFileAttributes = Files.readAttributes(cmsPath, BasicFileAttributes.class, new LinkOption[0]);
            FileTime lastModifiedTime = basicFileAttributes.lastModifiedTime();
            Instant instant = lastModifiedTime.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime lastModified = LocalDateTime.ofInstant(instant, zoneId);
            content.setLocalChanged(lastModified);
            this.contentServiceJdbc.updateContent(content);
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
        return cmsPath;
    }

    private List<Content> getLatestContentList() {
        Assert.state(this.restTemplate != null, GetLatestContentListMessages.REST_TEMPLATE_CAN_NOT_BE_NULL);
        Assert.state(this.contentListUrl != null, GetLatestContentListMessages.CONTENT_LIST_URL_CAN_NOT_BE_NULL);
        SmartCmsContentViewModel[] smartCmsContentViewModelArray = this.restTemplate.getForObject(this.contentListUrl, SmartCmsContentViewModel[].class, new Object[0]);
        if (smartCmsContentViewModelArray == null) {
            return Collections.emptyList();
        }
        List<SmartCmsContentViewModel> smartCmsContentViewModelList = Arrays.asList(smartCmsContentViewModelArray);
        return SmartCmsContentMapper.toContentList(smartCmsContentViewModelList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void updateContent(Content newContent, boolean redownloadIfUpdated, boolean updateHardwareIfUpdated) {
        Assert.state(this.contentServiceJdbc != null, UpdateContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
        Assert.notNull((Object)newContent, UpdateContentMessages.NEW_CONTENT_CAN_NOT_BE_NULL);
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
                    LocalDateTime localChanged;
                    if (newPublishDate.isAfter(oldPublishDate)) {
                        this.contentServiceJdbc.updateContent(newContent);
                        isUpdated = true;
                    }
                    if ((localChanged = oldContent.getLocalChanged()) == null || newPublishDate.isAfter(localChanged)) {
                        isUpdated = true;
                    }
                    if (redownloadIfUpdated && isUpdated) {
                        this.downloadContent(contentId, false);
                        Content.Orientation orientation = newContent.getOrientation();
                        if (orientation.equals((Object)Content.Orientation.PORTRAIT)) {
                            this.downloadContent(contentId, true);
                        }
                    }
                    if (updateHardwareIfUpdated && isUpdated) {
                        Set<Hardware> hardwareSet = this.hardwareServiceJdbc.getHardwareByContentId(contentId);
                        hardwareSet.stream().forEach(this::updateContent);
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void updateContent() {
        this.updateContent(false, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateContent(Hardware hardware) {
        Assert.notNull((Object)hardware, UpdateContentMessages.HARDWARE_CAN_NOT_BE_NULL);
        String hardwareKey = hardware.getHardwareKey();
        Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        boolean isHardwareBusy = this.isHardwareBusy(hardwareKey);
        if (isHardwareBusy) {
            return;
        }
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
                Files.createDirectories(hardwarePath, new FileAttribute[0]);
                boolean rotated = this.deviceServiceJdbc.getContentRotatedBySerialCode(hardwareKey);
                Path cmsPath = this.getCmsPath(contentId, rotated, true);
                Path hardwareCmsPath = this.getHardwareCmsPath(hardwareKey);
                try (InputStream inputStream = Files.newInputStream(cmsPath, new OpenOption[0]);
                     OutputStream outputStream = Files.newOutputStream(hardwareCmsPath, new OpenOption[0]);){
                    FileCopyUtils.copy(inputStream, outputStream);
                }
                File cmsFile = hardwareCmsPath.toFile();
                ZipFile zipFile = new ZipFile(cmsFile);
                File systemConfigFile = systemConfigPath.toFile();
                ZipParameters zipParameters = new ZipParameters();
                zipParameters.setFileNameInZip(SYSTEM_CONFIG_FILE_NAME);
                zipParameters.setSourceExternalStream(true);
                zipFile.addFile(systemConfigFile, zipParameters);
                File controlMetaFile = controlMetaPath.toFile();
                zipParameters.setFileNameInZip(CONTROL_META_FILE_NAME);
                zipFile.addFile(controlMetaFile, zipParameters);
                this.generateRevisionFile(hardwareKey);
            }
            catch (IOException | ZipException e) {
                throw new RuntimeException(e);
            }
            finally {
                String message;
                try {
                    Files.deleteIfExists(systemConfigPath);
                }
                catch (IOException e) {
                    message = e.getMessage();
                    logger.error(message, e);
                }
                try {
                    Files.deleteIfExists(controlMetaPath);
                }
                catch (IOException e) {
                    message = e.getMessage();
                    logger.error(message, e);
                }
            }
        }
        finally {
            this.setHardwareBusy(hardwareKey, false);
        }
    }

    @Override
    public void updateContent(boolean redownloadIfUpdated, boolean updateHardwareIfUpdated) {
        String contentId;
        Assert.state(this.contentServiceJdbc != null, UpdateContentMessages.CONTENT_SERVICE_JDBC_CAN_NOT_BE_NULL);
        Set<Content> contentBeforeProcessing = this.contentServiceJdbc.getContent();
        HashSet<String> processedContent = new HashSet<String>();
        List<Content> contentList = this.getLatestContentList();
        Assert.state(contentList != null, UpdateContentMessages.CONTENT_LIST_CAN_NOT_BE_NULL);
        Assert.state(!contentList.contains(null), UpdateContentMessages.CONTENT_LIST_CAN_NOT_CONTAIN_NULL_VALUES);
        for (Content content : contentList) {
            this.updateContent(content, redownloadIfUpdated, updateHardwareIfUpdated);
            contentId = content.getId();
            Assert.state(contentId != null, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
            processedContent.add(contentId);
        }
        for (Content content : contentBeforeProcessing) {
            contentId = content.getId();
            Assert.state(contentId != null, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
            if (processedContent.contains(contentId)) continue;
            try {
                Path plainCmsPath = this.getCmsPath(contentId, false, false);
                Assert.state(plainCmsPath != null, UpdateContentMessages.PLAIN_CMS_PATH_CAN_NOT_BE_NULL);
                Files.deleteIfExists(plainCmsPath);
                Path rotatedCmsPath = this.getCmsPath(contentId, true, false);
                Assert.state(rotatedCmsPath != null, UpdateContentMessages.ROTATED_CMS_PATH_CAN_NOT_BE_NULL);
                Files.deleteIfExists(rotatedCmsPath);
                Set<Hardware> hardwareSet = this.hardwareServiceJdbc.getHardwareByContentId(contentId);
                for (Hardware hardware : hardwareSet) {
                    String hardwareKey = hardware.getHardwareKey();
                    Assert.state(hardwareKey != null, UpdateContentMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
                    Assert.state(!hardwareKey.isEmpty(), UpdateContentMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
                    this.deleteHardwarePath(hardwareKey);
                    hardware.setContentId(null);
                    this.hardwareServiceJdbc.updateHardware(hardware);
                }
                this.contentServiceJdbc.deleteContent(content);
            }
            catch (IOException e) {
                String message = e.getMessage();
                logger.error(message, e);
            }
        }
    }

    @Override
    public void updateContent(String contentId, boolean redownloadIfUpdated, boolean updateHardwareIfUpdated) {
        Assert.notNull((Object)contentId, UpdateContentMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        List<Content> contentList = this.getLatestContentList();
        if (contentList.isEmpty()) {
            return;
        }
        Optional<Content> firstNewContent = contentList.stream().filter(content -> content.getId().equals(contentId)).findFirst();
        if (!firstNewContent.isPresent()) {
            return;
        }
        Content newContent = firstNewContent.get();
        this.updateContent(newContent, redownloadIfUpdated, updateHardwareIfUpdated);
    }

    @Override
    public void setContent(long deviceId, String contentId) {
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
    public void unassignContent(long deviceId) {
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
            boolean tempContentPathExists = Files.exists(this.tempContentPath, new LinkOption[0]);
            if (tempContentPathExists) {
                SimpleFileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){

                    @Override
                    public FileVisitResult postVisitDirectory(Path directoryPath, IOException e) throws IOException {
                        if (e == null) {
                            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);
                            Iterator<Path> directoryStreamIterator = directoryStream.iterator();
                            boolean isDirectoryEmpty = !directoryStreamIterator.hasNext();
                            directoryStream.close();
                            if (isDirectoryEmpty && !directoryPath.equals(ContentManagementServiceImpl.this.tempContentPath)) {
                                Files.delete(directoryPath);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        throw e;
                    }

                    @Override
                    public FileVisitResult visitFile(Path filePath, BasicFileAttributes basicFileAttributes) throws IOException {
                        FileTime lastModified = basicFileAttributes.lastModifiedTime();
                        long deltaMillis = System.currentTimeMillis() - lastModified.toMillis();
                        long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(deltaMillis);
                        if (deltaMinutes >= (long)ContentManagementServiceImpl.this.tempAgeMinutes) {
                            Files.delete(filePath);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path filePath, IOException e) throws IOException {
                        String message = e.getMessage();
                        logger.error(message, e);
                        return FileVisitResult.CONTINUE;
                    }
                };
                Files.walkFileTree(this.tempContentPath, (FileVisitor<? super Path>)fileVisitor);
            }
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Path getCmsPath(String contentId, boolean contentRotated, boolean downloadIfNotExists) throws IOException {
        Assert.notNull((Object)this.contentPath, GetCmsPathMessages.CONTENT_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)this.contentFileExtension, GetCmsPathMessages.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)contentId, GetCmsPathMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        Content content = this.contentServiceJdbc.getContent(contentId);
        Assert.state(content != null, GetCmsPathMessages.CONTENT_CAN_NOT_BE_NULL);
        Content.Orientation orientation = content.getOrientation();
        Assert.state(orientation != null, GetCmsPathMessages.ORIENTATION_CAN_NOT_BE_NULL);
        boolean shouldBeRotated = orientation.equals((Object)Content.Orientation.PORTRAIT) && contentRotated;
        String cmsFolder = shouldBeRotated ? "rotated" : "plain";
        Path cmsFolderPath = this.contentPath.resolve(cmsFolder);
        Files.createDirectories(cmsFolderPath, new FileAttribute[0]);
        Path cmsPath = cmsFolderPath.resolve(contentId + this.contentFileExtension);
        Lock lock = contentLock.get(contentId);
        try {
            lock.lock();
            boolean cmsFileExists = Files.exists(cmsPath, new LinkOption[0]);
            if (!cmsFileExists && downloadIfNotExists) {
                this.updateContent(contentId, false, false);
                Path path = this.downloadContent(contentId, contentRotated);
                return path;
            }
        }
        finally {
            lock.unlock();
        }
        return cmsPath;
    }

    private String getSystemConfig(String hardwareKey) {
        Assert.state(this.systemConfigRoot != null, GetSystemConfigMessages.SMART_CONTROL_CONTEXT_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)hardwareKey, GetSystemConfigMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        LinkedHashMap<String, String> systemConfigMap = new LinkedHashMap<String, String>();
        String serverIp = this.settingsServiceJdbc.getServerIp();
        Assert.state(serverIp != null, GetSystemConfigMessages.SERVER_IP_CAN_NOT_BE_NULL);
        String serverAddress = String.format("%s:%d", serverIp, this.smartControlServerPort);
        systemConfigMap.put("SERVER_ADDRESS", serverAddress);
        systemConfigMap.put("HARDWAREKEY", hardwareKey);
        systemConfigMap.put("ROOT", this.systemConfigRoot);
        String delimiter = ";&##&;";
        StringJoiner stringJoiner = new StringJoiner(";&##&;", "", ";&##&;");
        Set systemConfigEntrySet = systemConfigMap.entrySet();
        systemConfigEntrySet.stream().forEach(entry -> {
            String entryString = entry.toString();
            stringJoiner.add(entryString);
        });
        return stringJoiner.toString();
    }

    private Path getTempContentTimeStampPath(String path) {
        Assert.state(this.tempContentPath != null, GetTempContentTimeStampPathMessages.TEMP_CONTENT_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)path, GetTempContentTimeStampPathMessages.PATH_CAN_NOT_BE_NULL);
        try {
            Files.createDirectories(this.tempContentPath, new FileAttribute[0]);
            Path tempDirectory = Files.createTempDirectory(this.tempContentPath, null, new FileAttribute[0]);
            return tempDirectory.resolve(path);
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Path getTempSystemConfigPath(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetTempSystemConfigPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Path tempSystemConfigPath = this.getTempContentTimeStampPath(SYSTEM_CONFIG_FILE_NAME);
        Assert.state(tempSystemConfigPath != null, GetTempSystemConfigPathMessages.TEMP_SYSTEM_CONFIG_PATH_CAN_NOT_BE_NULL);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tempSystemConfigPath, new OpenOption[0]);){
            String systemConfigLine = this.getSystemConfig(hardwareKey);
            bufferedWriter.write(systemConfigLine);
            Path path = tempSystemConfigPath;
            return path;
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Path getTempControlMetaPath(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetTempControlMetaPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Path tempControlMetaPath = this.getTempContentTimeStampPath(CONTROL_META_FILE_NAME);
        Assert.state(tempControlMetaPath != null, GetTempControlMetaPathMessages.TEMP_CONTROL_META_PATH_CAN_NOT_BE_NULL);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tempControlMetaPath, new OpenOption[0]);){
            String systemConfigLine = this.getSystemConfig(hardwareKey);
            String controlMetaLine = String.format("const controlmeta = \"%s\";", systemConfigLine);
            bufferedWriter.write(controlMetaLine);
            Path path = tempControlMetaPath;
            return path;
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Path getTempCmsPath(String hardwareKey) {
        String message;
        Path path;
        Assert.state(this.tempContentPath != null, GetTempCmsPathMessages.TEMP_CONTENT_PATH_CAN_NOT_BE_NULL);
        Assert.state(this.contentPrefix != null, GetTempCmsPathMessages.CONTENT_PREFIX_CAN_NOT_BE_NULL);
        Assert.state(this.contentFileExtension != null, GetTempCmsPathMessages.CONTENT_FILE_EXTENSION_CAN_NOT_BE_NULL);
        Assert.notNull((Object)hardwareKey, GetTempCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
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
            zipParameters.setFileNameInZip(SYSTEM_CONFIG_FILE_NAME);
            zipParameters.setSourceExternalStream(true);
            zipFile.addFile(systemConfigFile, zipParameters);
            path = tempCmsPath;
            if (systemConfigPath == null) return path;
        }
        catch (ZipException e) {
            message = e.getMessage();
            logger.error(message, e);
            return null;
        }
        try {
            Files.deleteIfExists(systemConfigPath);
            return path;
        }
        catch (IOException e) {
            String message2 = e.getMessage();
            logger.error(message2, e);
        }
        return path;
        finally {
            if (systemConfigPath != null) {
                try {
                    Files.deleteIfExists(systemConfigPath);
                }
                catch (IOException e) {
                    message = e.getMessage();
                    logger.error(message, e);
                }
            }
        }
    }

    private Path getHardwarePath(String hardwareKey) {
        Assert.state(this.contentPath != null, GetHardwarePath.CONTENT_PATH_CAN_NOT_BE_NULL);
        Assert.notNull((Object)hardwareKey, GetHardwarePath.HARDWARE_KEY_CAN_NOT_BE_NULL);
        return this.contentPath.resolve(hardwareKey);
    }

    @Override
    public void deleteHardwarePath(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, DeleteHardwarePathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, DeleteHardwarePathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
        File hardwareFile = hardwarePath.toFile();
        Assert.state(hardwareFile != null, DeleteHardwarePathMessages.HARDWARE_FILE_CAN_NOT_BE_NULL);
        FileSystemUtils.deleteRecursively(hardwareFile);
    }

    @Override
    public Path getHardwareCmsPath(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetHardwareCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), GetHardwareCmsPathMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, GetHardwareCmsPathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
        String cmsFileName = String.format("%s%s%s", this.contentPrefix, hardwareKey, this.contentFileExtension);
        return hardwarePath.resolve(cmsFileName);
    }

    private Path getFlagPath(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetFlagPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), GetFlagPathMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, GetFlagPathMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
        return hardwarePath.resolve("busy.flag");
    }

    @Override
    public boolean isHardwareBusy(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, IsHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), IsHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        Path flagPath = this.getFlagPath(hardwareKey);
        return Files.exists(flagPath, new LinkOption[0]);
    }

    private void setHardwareBusy(String hardwareKey, boolean value) {
        Assert.notNull((Object)hardwareKey, SetHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), SetHardwareBusyMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, SetHardwareBusyMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
        try {
            Files.createDirectories(hardwarePath, new FileAttribute[0]);
            Path flagPath = this.getFlagPath(hardwareKey);
            Assert.state(flagPath != null, SetHardwareBusyMessages.FLAG_PATH_CAN_NOT_BE_NULL);
            if (value) {
                boolean flagPathExists = Files.exists(flagPath, new LinkOption[0]);
                if (!flagPathExists) {
                    Files.createFile(flagPath, new FileAttribute[0]);
                }
            } else {
                Files.deleteIfExists(flagPath);
            }
        }
        catch (IOException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
    }

    private void generateRevisionFile(String hardwareKey) throws IOException {
        Assert.notNull((Object)hardwareKey, GenerateRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), GenerateRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, GenerateRevisionFileMessages.HARDWARE_PATH_CAN_NOT_BE_NULL);
        Files.createDirectories(hardwarePath, new FileAttribute[0]);
        Path cmsPath = this.getHardwareCmsPath(hardwareKey);
        Assert.state(cmsPath != null, GenerateRevisionFileMessages.CMS_PATH_CAN_NOT_BE_NULL);
        boolean cmsPathExists = Files.exists(cmsPath, new LinkOption[0]);
        Assert.state(cmsPathExists, "Cms path does not exist");
        Path revisionPath = this.getRevisionPath(hardwareKey, false);
        Assert.state(revisionPath != null, GenerateRevisionFileMessages.REVISION_PATH_CAN_NOT_BE_NULL);
        try (InputStream inputStream = Files.newInputStream(cmsPath, new OpenOption[0]);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);){
            String md5Hex = DigestUtils.md5Hex(bufferedInputStream);
            byte[] md5HexBytes = md5Hex.getBytes();
            Files.write(revisionPath, md5HexBytes, new OpenOption[0]);
        }
    }

    @Override
    public Path getRevisionPath(String hardwareKey, boolean generateIfNotExists) {
        Path hardwarePath = this.getHardwarePath(hardwareKey);
        Assert.state(hardwarePath != null, GetRevisionPathMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Path revisionPath = hardwarePath.resolve("revision.txt");
        Assert.state(revisionPath != null, GetRevisionPathMessages.REVISION_PATH_CAN_NOT_BE_NULL);
        boolean revisionFileExists = Files.exists(revisionPath, new LinkOption[0]);
        if (!revisionFileExists && generateIfNotExists) {
            try {
                this.generateRevisionFile(hardwareKey);
            }
            catch (IOException e) {
                String message = e.getMessage();
                logger.error(message, e);
            }
        }
        return revisionPath;
    }

    @Override
    public void uploadSystemConfigToFtp(DeviceDTO device) {
        Integer port;
        Assert.notNull((Object)device, UploadSystemConfigToFtpMessages.DEVICE_CAN_NOT_BE_NULL);
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
        Assert.state((port = ftpSettings.getPort()) != null, UploadSystemConfigToFtpMessages.PORT_CAN_NOT_BE_NULL);
        String username = ftpSettings.getUsername();
        Assert.state(username != null, UploadSystemConfigToFtpMessages.USERNAME_CAN_NOT_BE_NULL);
        String encryptedPassword = ftpSettings.getPassword();
        Assert.state(encryptedPassword != null, UploadSystemConfigToFtpMessages.ENCRYPTED_PASSWORD_CAN_NOT_BE_NULL);
        String decryptedPassword = SecurityUtilities.decrypt(encryptedPassword);
        Assert.state(decryptedPassword != null, UploadSystemConfigToFtpMessages.DECRYPTED_PASSWORD_CAN_NOT_BE_NULL);
        UploadUtilities.uploadFile(cmsPath, ip, port, username, decryptedPassword);
    }
}

