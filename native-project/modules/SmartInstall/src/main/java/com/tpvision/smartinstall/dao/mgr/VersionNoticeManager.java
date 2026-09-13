package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.VersionNoticeRepository;
import com.tpvision.smartinstall.dao.core.VersionNotice;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionNoticeManager {
   private static final Logger LOG = LoggerFactory.getLogger(VersionNoticeManager.class);
   @Autowired
   private VersionNoticeRepository versionNoticeRepository;

   public VersionNotice save(VersionNotice bean) {
      this.versionNoticeRepository.save(bean);
      return bean;
   }

   public List<VersionNotice> findVersionNoticeList(int noticeType, String noticeAdmin, int entityType, String entityId, String noticeVersion) {
      return this.versionNoticeRepository
         .findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(noticeType, noticeAdmin, entityType, entityId, noticeVersion);
   }

   public List<VersionNotice> findEmailVersionNoticeList(String noticeAdmin, int entityType, String entityId, String noticeVersion) {
      return this.versionNoticeRepository
         .findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(1, noticeAdmin, entityType, entityId, noticeVersion);
   }

   public List<VersionNotice> findEmailCmndVersionNoticeList(String noticeAdmin, String noticeVersion) {
      return this.versionNoticeRepository.findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(1, noticeAdmin, 0, "CMND", noticeVersion);
   }

   public List<VersionNotice> findEmailReceptionVersionNoticeList(String noticeAdmin, String noticeVersion, String clientId) {
      return this.versionNoticeRepository.findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(1, noticeAdmin, 1, clientId, noticeVersion);
   }

   public List<VersionNotice> findEmailFirmwareVersionNoticeList(String noticeAdmin, String noticeVersion, String tvUnqiueId) {
      return this.versionNoticeRepository.findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(1, noticeAdmin, 2, tvUnqiueId, noticeVersion);
   }
}
