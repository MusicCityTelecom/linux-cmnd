/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.api.ReceptionSocket;
import com.tpvision.smartinstall.dao.RoomNotificationRepository;
import com.tpvision.smartinstall.dao.core.RoomNotification;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RoomNotificationManager {
    @Autowired
    private RoomNotificationRepository roomNotificationRepository;

    public long findCountByLevelAndReadStatus(int level, int readStatus) {
        return this.roomNotificationRepository.count((root, query, cb) -> cb.and((Expression<Boolean>)cb.equal(root.get("eventLevel"), level), (Expression<Boolean>)cb.equal(root.get("readStatus"), readStatus)));
    }

    public List<RoomNotification> findNotificationList(int level, int readStatus, RoomNotification.EventType type) {
        return this.roomNotificationRepository.findAll((root, query, cb) -> {
            ArrayList<Predicate> predicateList = new ArrayList<Predicate>();
            if (type != null) {
                predicateList.add(cb.equal(root.get("eventType"), (Object)type));
            }
            predicateList.add(cb.equal(root.get("eventLevel"), level));
            predicateList.add(cb.equal(root.get("readStatus"), readStatus));
            return cb.and(predicateList.toArray(new Predicate[0]));
        }, Sort.by(Sort.Direction.ASC, "id"));
    }

    public Page<RoomNotification> findByEventLevelAndKeyword(int eventLevel, String keyword, Pageable pageable) {
        Specification specs = (root, query, cb) -> {
            ArrayList<Predicate> predicateList = new ArrayList<Predicate>();
            if (eventLevel != -1) {
                predicateList.add(cb.equal(root.get("eventLevel"), eventLevel));
            }
            if (StringUtils.isNotBlank(keyword)) {
                String matchKeyword = "%" + keyword + "%";
                Predicate roomLike = cb.like(root.get("room").as(String.class), matchKeyword);
                Predicate messageLike = cb.like(root.get("message").as(String.class), matchKeyword);
                Predicate eventTimeLike = cb.like(root.get("eventTime").as(String.class), matchKeyword);
                Predicate allOrLike = cb.or(roomLike, messageLike, eventTimeLike);
                predicateList.add(allOrLike);
            }
            return cb.and(predicateList.toArray(new Predicate[0]));
        };
        return this.roomNotificationRepository.findAll(specs, pageable);
    }

    public void save(RoomNotification roomNotification) {
        this.roomNotificationRepository.save(roomNotification);
        ReceptionSocket.notifyRoomNotificationUpdate();
    }

    public RoomNotification queryById(int id) {
        return this.roomNotificationRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        RoomNotification deleteRoomNotification = this.queryById(id);
        if (deleteRoomNotification != null) {
            this.roomNotificationRepository.delete(deleteRoomNotification);
            ReceptionSocket.notifyRoomNotificationUpdate();
        }
    }

    public void save(String room, int eventLevel, RoomNotification.EventType eventType, String message) {
        RoomNotification roomNotification = new RoomNotification();
        roomNotification.setEventTime(TpvDateUtils.formatLocalDate(new Date(), "yyyy-MM-dd HH:mm"));
        roomNotification.setEventLevel(eventLevel);
        roomNotification.setEventType(eventType);
        roomNotification.setRoom(room);
        roomNotification.setMessage(message);
        roomNotification.setReadStatus(0);
        this.save(roomNotification);
    }

    public void deleteAll() {
        this.roomNotificationRepository.deleteAll();
        ReceptionSocket.notifyRoomNotificationUpdate();
    }

    public void delete(RoomNotification roomNotification) {
        this.roomNotificationRepository.delete(roomNotification);
        ReceptionSocket.notifyRoomNotificationUpdate();
    }

    @Transactional
    public void markAllAsRead() {
        String readTime = TpvDateUtils.formatLocalDate(new Date(), "yyyy-MM-dd HH:mm");
        this.roomNotificationRepository.markAllAsRead(readTime);
        ReceptionSocket.notifyRoomNotificationUpdate();
    }
}

