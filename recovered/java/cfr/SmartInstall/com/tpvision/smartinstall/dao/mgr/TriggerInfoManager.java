/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.TriggerInfoRepository;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggerInfoManager {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerInfoManager.class);
    @Autowired
    private TriggerInfoRepository triggerInfoRepository;

    public JSONObject findTriggerInfoPageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("trigger_info", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<TriggerInfo> findTriggerInfosByTriggerActiveAndTriggerTypeAndTriggerCondition(String triggerActive, String triggerType, String triggerCondition) {
        return this.triggerInfoRepository.findByTriggerActiveAndTriggerTypeAndTriggerCondition(triggerActive, triggerType, triggerCondition);
    }

    public List<TriggerInfo> findTriggerInfosByTriggerActiveAndTriggerTypeAndTarget(String triggerActive, String triggerType, String target) {
        return this.triggerInfoRepository.findByTriggerActiveAndTriggerTypeAndTarget(triggerActive, triggerType, target);
    }

    public List<TriggerInfo> findTriggerInfosByTriggerTypeAndTarget(String triggerType, String target) {
        return this.triggerInfoRepository.findByTriggerTypeAndTarget(triggerType, target);
    }

    public List<TriggerInfo> findTriggerInfosByTarget(String target) {
        return this.triggerInfoRepository.findByTarget(target);
    }

    public List<TriggerInfo> findByTriggerActiveAndTriggerType(String triggerActive, String triggerType) {
        return this.triggerInfoRepository.findByTriggerActiveAndTriggerType(triggerActive, triggerType);
    }

    public List<TriggerInfo> findByTriggerActive(String triggerActive) {
        return this.triggerInfoRepository.findByTriggerActive(triggerActive);
    }

    public List<TriggerInfo> findByTriggerType(String triggerType) {
        return this.triggerInfoRepository.findByTriggerType(triggerType);
    }

    public TriggerInfo loadByKey(Integer id) {
        return this.triggerInfoRepository.findById(id).orElse(null);
    }

    public void deleteByKey(Integer id) {
        this.triggerInfoRepository.deleteById(id);
    }

    public TriggerInfo save(TriggerInfo bean) {
        return this.triggerInfoRepository.save(bean);
    }

    public List<TriggerInfo> loadAll() {
        return this.triggerInfoRepository.findAll();
    }

    public List<TriggerInfo> findByCloneTypeAndCloneId(String cloneType, Integer cloneId) {
        return this.triggerInfoRepository.findByCloneTypeAndCloneId(cloneType, cloneId);
    }
}

