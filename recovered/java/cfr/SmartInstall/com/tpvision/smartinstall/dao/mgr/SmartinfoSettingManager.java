/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.SmartinfoSettingRepository;
import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SmartinfoSettingManager {
    private static final Logger LOG = LoggerFactory.getLogger(SmartinfoSettingManager.class);
    @Autowired
    private SmartinfoSettingRepository smartinfoSettingRepository;

    public void deleteByKey(int id) {
        this.smartinfoSettingRepository.deleteById(id);
    }

    public SmartinfoSetting findLatestSmartinfoSetting() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Iterable all = this.smartinfoSettingRepository.findAll(sort);
        return all.isEmpty() ? null : (SmartinfoSetting)all.get(0);
    }

    public List<SmartinfoSetting> findSmartinfoSettingBySmartuiId(int smartuiId) {
        return this.smartinfoSettingRepository.findBySmartuiId(smartuiId);
    }

    public List<SmartinfoSetting> findSmartinfoSettingBySettingId(int settingId) {
        return this.smartinfoSettingRepository.findBySettingId(settingId);
    }

    public void save(SmartinfoSetting smartinfoSetting) {
        this.smartinfoSettingRepository.save(smartinfoSetting);
    }

    public String getSmartinfoSettingValue(int settingId) {
        String jssonVal = "0";
        String query = "select cm.id,cm.value,cm.name,templateId from smartinfo_setting info inner join cms cm where cm.id = info.templateId and info.setting_id = ? and cm.isDelete='N' ";
        try (Connection conn = JpaManager.getConnection();
             PreparedStatement stat = conn.prepareStatement(query);){
            stat.setInt(1, settingId);
            try (ResultSet rs = stat.executeQuery();){
                while (rs.next()) {
                    jssonVal = rs.getString("value");
                }
            }
        }
        catch (SQLException e1) {
            LOG.error(e1.getMessage(), e1);
        }
        return jssonVal;
    }
}

