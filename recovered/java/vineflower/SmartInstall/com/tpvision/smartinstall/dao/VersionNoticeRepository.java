package com.tpvision.smartinstall.dao;

import com.tpvision.smartinstall.dao.core.VersionNotice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VersionNoticeRepository extends JpaRepository<VersionNotice, Integer>, JpaSpecificationExecutor<VersionNotice> {
   List<VersionNotice> findByNoticeTypeAndNoticeAdminAndEntityTypeAndEntityIdAndNoticeVersion(int var1, String var2, int var3, String var4, String var5);
}
