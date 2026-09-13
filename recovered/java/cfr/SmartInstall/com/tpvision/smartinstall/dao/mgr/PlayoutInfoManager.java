/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.PlayoutInfoRepository;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlayoutInfoManager {
    private static final Logger LOG = LoggerFactory.getLogger(PlayoutInfoManager.class);
    @Autowired
    private PlayoutInfoRepository playoutInfoRepository;

    public JSONObject findPlayoutInfoPageBySearchParam(SearchParam sp) {
        try {
            return JpaManager.findSimpleLikeDataPageBySearchParam("playout_info", sp);
        }
        catch (SQLException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<PlayoutInfo> findPlayoutInfosByRoomsAndExculudeTypes(String rooms, String ... excludeTypeList) {
        Specification specification = (root, query, cb) -> {
            CriteriaBuilder.In in = cb.in(root.get("type"));
            Arrays.stream(excludeTypeList).forEach(in::value);
            Predicate roomsEq = cb.equal(root.get("rooms").as(String.class), rooms);
            return cb.and((Expression<Boolean>)roomsEq, (Expression<Boolean>)cb.not(in));
        };
        return this.playoutInfoRepository.findAll(specification);
    }

    public List<PlayoutInfo> findPlayoutInfosExculudeTypes(String ... excludeTypes) {
        return this.playoutInfoRepository.findByTypeNotIn(Arrays.asList(excludeTypes));
    }

    public List<PlayoutInfo> findPlayoutInfosIncludeTypes(String ... includeTypes) {
        return this.playoutInfoRepository.findByTypeIn(Arrays.asList(includeTypes));
    }

    public List<PlayoutInfo> loadAll() {
        return this.playoutInfoRepository.findAll();
    }

    public List<PlayoutInfo> findPlayoutInfoByTypeAndName(String type, String name) {
        return this.playoutInfoRepository.findByTypeAndName(type, name);
    }

    public List<PlayoutInfo> findPlayoutInfoByType(String type) {
        return this.playoutInfoRepository.findByType(type);
    }

    public List<PlayoutInfo> findPlayoutInfoByName(String name) {
        return this.playoutInfoRepository.findByName(name);
    }

    public List<PlayoutInfo> findPlayoutInfoByTypeAndSource(String type, String source) {
        return this.playoutInfoRepository.findByTypeAndSource(type, source);
    }

    public List<PlayoutInfo> findPlayoutInfoByRoomsAndSource(String rooms, String source) {
        return this.playoutInfoRepository.findByRoomsInAndSource(TpvStringUtils.getValidTvRoomIdSet(rooms), source);
    }

    public void deleteByKey(int id) {
        this.playoutInfoRepository.deleteById(id);
    }

    public PlayoutInfo loadByKey(int id) {
        return this.playoutInfoRepository.findById(id).orElse(null);
    }

    public PlayoutInfo save(PlayoutInfo playoutInfo) {
        PlatformUtils.fixPlayoutInfoRooms(playoutInfo);
        return this.playoutInfoRepository.save(playoutInfo);
    }

    public List<PlayoutInfo> findPlayoutInfoByTypeAndRoomsAndSource(String type, String roomId, String playoutSourcePms) {
        return this.playoutInfoRepository.findByTypeAndRoomsInAndSource(type, TpvStringUtils.getValidTvRoomIdSet(roomId), playoutSourcePms);
    }

    public List<PlayoutInfo> findPlayoutInfoByTypeAndPlatformAndSource(String type, String platform, String playoutSourcePms) {
        return this.playoutInfoRepository.findByTypeAndPlatformAndSource(type, platform, playoutSourcePms);
    }

    public void resetStatus() {
        this.playoutInfoRepository.resetStatus();
    }

    public void updateStatusByFilePath(String filePath, int status) {
        this.playoutInfoRepository.updateStatusByFilePath(filePath, status);
    }

    public int countByFilePath(String filePath) {
        return this.playoutInfoRepository.countByFilePath(filePath);
    }
}

