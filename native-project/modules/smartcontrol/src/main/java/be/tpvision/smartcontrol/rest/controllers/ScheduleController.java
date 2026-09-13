package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.messages.controllers.schedule.GetCmsSourceFileMessages;
import be.tpvision.smartcontrol.messages.controllers.schedule.GetFlagMessages;
import be.tpvision.smartcontrol.messages.controllers.schedule.GetRevisionFileMessages;
import be.tpvision.smartcontrol.messages.controllers.schedule.HeadCmsSourceFileMessages;
import be.tpvision.smartcontrol.messages.controllers.schedule.HeadRevisionFileMessages;
import be.tpvision.smartcontrol.messages.controllers.schedule.HeadScheduleFileMessages;
import be.tpvision.smartcontrol.service.ContentManagementService;
import be.tpvision.smartcontrol.util.DownloadUtilities;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
   private final ContentManagementService contentManagementService;

   @Autowired
   public ScheduleController(final ContentManagementService contentManagementService) {
      this.contentManagementService = contentManagementService;
   }

   private ResponseEntity<Void> getResponseEntityLocked() {
      return ResponseEntity.status(HttpStatus.LOCKED).build();
   }

   private ResponseEntity<Void> getResponseEntityNotFound() {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
   }

   @RequestMapping(value = "/info_{hardwareKey}.txt", method = RequestMethod.HEAD)
   public ResponseEntity<Void> headRevisionFile(@PathVariable("hardwareKey") final String hardwareKey) throws IOException {
      Assert.state(this.contentManagementService != null, HeadRevisionFileMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, HeadRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), HeadRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(hardwareKey);
      if (isHardwareBusy) {
         return this.getResponseEntityLocked();
      }

      Path revisionPath = this.contentManagementService.getRevisionPath(hardwareKey, false);
      Assert.state(revisionPath != null, HeadRevisionFileMessages.REVISION_PATH_CAN_NOT_BE_NULL);
      boolean revisionPathExists = Files.exists(revisionPath);
      if (!revisionPathExists) {
         return this.getResponseEntityNotFound();
      }

      String fileName = String.format("info_%s.txt", hardwareKey);
      String contentType = "text/plain";
      HttpHeaders httpHeaders = DownloadUtilities.getHttpHeaders(fileName, "text/plain");
      Assert.state(httpHeaders != null, HeadRevisionFileMessages.HTTP_HEADERS_CAN_NOT_BE_NULL);
      long contentLength = Files.size(revisionPath);
      httpHeaders.setContentLength(contentLength);
      return ResponseEntity.ok().headers(httpHeaders).build();
   }

   @GetMapping("/info_{hardwareKey}.txt")
   public ResponseEntity<?> getRevisionFile(@PathVariable("hardwareKey") final String hardwareKey) {
      Assert.state(this.contentManagementService != null, GetRevisionFileMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetRevisionFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(hardwareKey);
      if (isHardwareBusy) {
         return this.getResponseEntityLocked();
      }

      Path revisionPath = this.contentManagementService.getRevisionPath(hardwareKey, false);
      Assert.state(revisionPath != null, GetRevisionFileMessages.REVISION_PATH_CAN_NOT_BE_NULL);
      boolean revisionPathExists = Files.exists(revisionPath);
      if (!revisionPathExists) {
         return this.getResponseEntityNotFound();
      }

      String fileName = String.format("info_%s.txt", hardwareKey);
      String contentType = "text/plain";
      return DownloadUtilities.getResponseEntity(revisionPath, fileName, "text/plain");
   }

   @RequestMapping(value = "/{hardwareKey}.js", method = RequestMethod.HEAD)
   public ResponseEntity<Void> headScheduleFile(@PathVariable("hardwareKey") final String hardwareKey) {
      Assert.state(this.contentManagementService != null, HeadScheduleFileMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, HeadScheduleFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), HeadScheduleFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      String fileName = String.format("%s.js", hardwareKey);
      String contentType = "text/plain";
      HttpHeaders httpHeaders = DownloadUtilities.getHttpHeaders(fileName, "text/plain");
      Assert.state(httpHeaders != null, HeadScheduleFileMessages.HTTP_HEADERS_CAN_NOT_BE_NULL);
      Instant instant = Instant.EPOCH;
      long lastModified = instant.toEpochMilli();
      httpHeaders.setLastModified(lastModified);
      return ResponseEntity.ok().headers(httpHeaders).build();
   }

   @GetMapping("/{hardwareKey}.js")
   public ResponseEntity<Void> getScheduleFile(@PathVariable("hardwareKey") final String hardwareKey) {
      return this.headScheduleFile(hardwareKey);
   }

   @GetMapping("/flag_{hardwareKey}.txt")
   public ResponseEntity<Void> getFlag(@PathVariable("hardwareKey") final String hardwareKey) {
      Assert.state(this.contentManagementService != null, GetFlagMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetFlagMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetFlagMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(hardwareKey);
      return isHardwareBusy ? ResponseEntity.ok().build() : this.getResponseEntityNotFound();
   }

   @RequestMapping(value = "/cms_data_{hardwareKey}.cms", method = RequestMethod.HEAD)
   public ResponseEntity<Void> headCmsSourceFile(@PathVariable("hardwareKey") final String hardwareKey) throws IOException {
      Assert.state(this.contentManagementService != null, HeadCmsSourceFileMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, HeadCmsSourceFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), HeadCmsSourceFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(hardwareKey);
      if (isHardwareBusy) {
         return this.getResponseEntityLocked();
      }

      String fileName = this.contentManagementService.getContentFileName(hardwareKey);
      Assert.state(fileName != null, HeadCmsSourceFileMessages.FILE_NAME_CAN_NOT_BE_NULL);
      Assert.state(!fileName.isEmpty(), HeadCmsSourceFileMessages.FILE_NAME_CAN_NOT_BE_EMPTY);
      String contentType = "application/zip";
      HttpHeaders httpHeaders = DownloadUtilities.getHttpHeaders(fileName, "application/zip");
      Assert.state(httpHeaders != null, HeadCmsSourceFileMessages.HTTP_HEADERS_CAN_NOT_BE_NULL);
      Path cmsPath = this.contentManagementService.getHardwareCmsPath(hardwareKey);
      Assert.state(cmsPath != null, HeadCmsSourceFileMessages.CMS_PATH_CAN_NOT_BE_NULL);
      boolean hardwareCmsPathExists = Files.exists(cmsPath);
      if (!hardwareCmsPathExists) {
         return this.getResponseEntityNotFound();
      }

      long contentLength = Files.size(cmsPath);
      Assert.state(contentLength > 0L, "Content length has to be higher than 0.");
      httpHeaders.setContentLength(contentLength);
      return ResponseEntity.ok().headers(httpHeaders).build();
   }

   @GetMapping("/cms_data_{hardwareKey}.cms")
   public ResponseEntity<?> getCmsSourceFile(@PathVariable("hardwareKey") final String hardwareKey) {
      Assert.state(this.contentManagementService != null, GetCmsSourceFileMessages.CONTENT_MANAGEMENT_SERVICE_CAN_NOT_BE_NULL);
      Assert.notNull(hardwareKey, GetCmsSourceFileMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
      Assert.isTrue(!hardwareKey.isEmpty(), GetCmsSourceFileMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
      boolean isHardwareBusy = this.contentManagementService.isHardwareBusy(hardwareKey);
      if (isHardwareBusy) {
         return this.getResponseEntityLocked();
      }

      Path cmsPath = this.contentManagementService.getHardwareCmsPath(hardwareKey);
      Assert.state(cmsPath != null, GetCmsSourceFileMessages.CMS_PATH_CAN_NOT_BE_NULL);
      boolean cmsPathExists = Files.exists(cmsPath);
      if (!cmsPathExists) {
         return this.getResponseEntityNotFound();
      }

      String contentType = "application/zip";
      return DownloadUtilities.getResponseEntity(cmsPath, "application/zip");
   }
}
