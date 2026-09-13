package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import java.nio.file.Path;

public interface ContentManagementService {
   String getContentFileName(final String hardwareKey);

   void updateContent();

   void updateContent(Hardware hardware);

   void updateContent(boolean redownloadIfUpdated, boolean updateHardwareIfUpdated);

   void updateContent(String contentId, boolean redownloadIfUpdated, boolean updateHardwareIfUpdated);

   void setContent(final long deviceId, final String contentId);

   void unassignContent(final long deviceId);

   void deleteTempContent();

   void deleteHardwarePath(String hardwareKey);

   Path getHardwareCmsPath(String hardwareKey);

   boolean isHardwareBusy(String hardwareKey);

   Path getRevisionPath(String hardwareKey, boolean generateIfNotExists);

   void uploadSystemConfigToFtp(DeviceDTO device);
}
