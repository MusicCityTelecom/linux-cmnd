/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.SmartuiRepository;
import com.tpvision.smartinstall.dao.core.Smartui;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartuiManager {
    private static final Logger LOG = LoggerFactory.getLogger(SmartuiManager.class);
    @Autowired
    private SmartuiRepository smartuiRepository;

    public String getSmartInfoPage(int current, int rowCount, String sortId, String sortTime, String userName) {
        int offset = 0;
        if (current > 0) {
            offset = (current - 1) * rowCount;
        }
        StringBuilder sql = new StringBuilder("select * from smartui where type='SINFOES' AND isDelete='N'");
        StringBuilder sqlCount = new StringBuilder("select count(*) from smartui where type='SINFOES' AND isDelete='N'");
        ArrayList<String> paramList = new ArrayList<String>();
        if (StringUtils.isNotBlank(userName)) {
            String condition = " and created_by = ? ";
            paramList.add(userName);
            sql.append(condition);
            sqlCount.append(condition);
        }
        if (null != sortId) {
            sql.append(" order by id " + sortId);
        } else {
            sql.append(" order by last_update_date " + sortTime);
        }
        sql.append(" limit " + offset + "," + rowCount);
        LOG.debug("query={}", (Object)sql);
        try {
            JSONObject data = new JSONObject();
            data.put("current", current);
            data.put("rowCount", rowCount);
            data.put("total", JpaManager.countRecordsInDB(sqlCount.toString(), paramList.toArray()));
            data.put("rows", JpaManager.getResultsetAsArray(sql.toString(), paramList.toArray()));
            return data.toString();
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<Smartui> findSmartuiByTypeAndIsDeleteAndId(String type, String isDelete, int id) {
        return this.smartuiRepository.findByTypeAndIsdeleteAndId(type, isDelete, id);
    }

    public List<Smartui> findSmartuiByTypeAndIsDeleteAndName(String type, String isDelete, String name) {
        return this.smartuiRepository.findByTypeAndIsdeleteAndName(type, isDelete, name);
    }

    public List<Smartui> findSmartuiByTypeAndIsDeleteAndNameContaining(String type, String isDelete, String name) {
        return this.smartuiRepository.findByTypeAndIsdeleteAndNameContaining(type, isDelete, name);
    }

    public Smartui loadByKey(int id) {
        return this.smartuiRepository.findById(id).orElse(null);
    }

    public void save(Smartui smartui) {
        this.smartuiRepository.save(smartui);
    }

    public List<Smartui> findSmartuiByName(String name) {
        return this.smartuiRepository.findByName(name);
    }
}

