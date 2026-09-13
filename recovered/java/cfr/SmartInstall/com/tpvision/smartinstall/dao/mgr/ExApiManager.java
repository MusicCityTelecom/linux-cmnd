/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.dao.ExApiRepository;
import com.tpvision.smartinstall.dao.core.ExApi;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExApiManager {
    private static final Logger LOG = LoggerFactory.getLogger(ExApiManager.class);
    @Autowired
    private ExApiRepository exApiRepository;

    public void save(ExApi exApi) {
        exApi.setId(1);
        this.exApiRepository.save(exApi);
    }

    public List<ExApi> findExApisByApikey(String apikey) {
        return this.exApiRepository.findByApikey(apikey);
    }

    public ExApi loadExApi() {
        return this.loadByKey(1);
    }

    private ExApi loadByKey(int id) {
        return this.exApiRepository.findById(id).orElse(null);
    }

    public void deleteByKey(int id) {
        this.exApiRepository.deleteById(id);
    }

    public JsonObject findExapiPage(int current, int rowCount, String searchPhrase, String sortId, String apikeyname, String apikey) {
        JsonObject data = new JsonObject();
        StringBuilder sql = new StringBuilder("select * from exapi where 1=1");
        int count = 0;
        ArrayList<String> paramsList = new ArrayList<String>();
        if (StringUtils.isNotBlank(searchPhrase)) {
            String condition = " and apikeyname like ? ";
            paramsList.add("%" + searchPhrase + "%");
            sql.append(condition);
        }
        if (null != sortId) {
            sql.append(" order by id " + sortId);
        } else if (null != apikeyname) {
            sql.append(" order by apikeyname " + apikeyname);
        } else if (null != apikey) {
            sql.append(" order by apikey " + apikey);
        } else {
            sql.append(" order by id asc");
        }
        JsonObject jsonObj = null;
        JsonArray array = new JsonArray();
        try (Connection conn = JpaManager.getConnection();
             PreparedStatement stat = conn.prepareStatement(sql.toString());){
            for (int i = 1; i <= paramsList.size(); ++i) {
                stat.setObject(i, paramsList.get(i - 1));
            }
            try (ResultSet rs = stat.executeQuery();){
                ResultSetMetaData metaData = rs.getMetaData();
                metaData.getColumnCount();
                while (rs.next()) {
                    jsonObj = new JsonObject();
                    String value = rs.getString("id");
                    if ("0".equalsIgnoreCase(value)) continue;
                    if (count >= (current - 1) * rowCount && count < current * rowCount) {
                        jsonObj.addProperty("id", value);
                        value = rs.getString("apikey");
                        jsonObj.addProperty("apikey", value);
                        value = rs.getString("apikeyname");
                        jsonObj.addProperty("apikeyname", value);
                        array.add(jsonObj);
                    }
                    if (count > current * rowCount) break;
                    ++count;
                }
                LOG.info("page result json data=>{}", (Object)jsonObj);
            }
        }
        catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        data.addProperty("current", current);
        data.addProperty("rowCount", rowCount);
        data.addProperty("total", count);
        data.add("rows", array);
        return data;
    }
}

